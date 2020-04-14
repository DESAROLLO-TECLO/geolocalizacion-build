angular.module(localizacionGps).controller('graficaTipoInfraccionMensualController', function($scope, $filter, gMapsRutasService, $controller, reporteService, ModalService, data) {
	
	$("[data-toggle=popover]").popover();
	
	var bgColors=
		['rgba(7, 30, 55, 0.3)',
		 'rgba(38, 153, 43, 0.2)',
		 'rgba(255, 58, 43, 0.2)',
		 'rgba(217, 58, 144, 0.3)'];
//		 'rgba(158, 84, 20, 0.3)',
//		 'rgba(46, 255, 207, 0.3)',
//		 'rgba(28, 27, 143, 0.3)',
//		 'rgba(154, 108, 180, 0.3)',
//		 'rgba(36, 74, 142, 0.3',
//		 'rgba(177, 35, 237, 0.3)',
//		 'rgba(139, 203, 84, 0.3)',
//		 'rgba(139, 203, 193, 0.3)'];
	
	var months =
		['Enero',
		 'Febrero',
		 'Marzo',
		 'Abril',
		 'Mayo',
		 'Junio',
		 'Julio',
		 'Agosto',
		 'Septiembre',
		 'Octubre',
		 'Noviembre',
		 'Diciembre',];
	
//	var gBar = document.getElementById('gBar').getContext('2d');
	$scope.gLineal = document.getElementById('gMLineal').getContext('2d');
//	var gPie = document.getElementById('gPie').getContext('2d');
	$scope.gRadar = document.getElementById('gMRadar').getContext('2d');
	
	$scope.zonasViales = data.data;
	$scope.graphicOriginalForm = {zonaId : null, intervalo : "1", anio : null};
	$scope.resetForm = function(){
		$scope.gForm = angular.copy($scope.graphicOriginalForm);
	}
	$scope.resetForm();
	
	$scope.searchZoneData = function(){
		if($scope.searchMAreaForm.$invalid) {
            angular.forEach($scope.searchMAreaForm.$error, function (field) {
              angular.forEach(field, function(errorField){
            	  errorField.$setDirty();
              })
            });
            return;
        }
		$scope.graficaMensual();
	}
	
	$scope.graficaMensual = function(){
		reporteService.infraccionesMensualPorZonaVial($scope.gForm.zonaId, $scope.gForm.anio)
		.success(function(data){
			$scope.serviceData = data;
			chart1 != undefined ? chart1.destroy() : '';
			chart2 != undefined ? chart2.destroy() : '';
			chart3 != undefined ? chart3.destroy() : '';
			chart4 != undefined ? chart4.destroy() : '';
			$scope.buildGraphics();
		})
		.error(function(data){
			$scope.error = data;
			$scope.showError($scope.error .message);
			$scope.serviceData = [];
		})
	}
   
	$scope.buildGraphics = function(){
		infracTipoData=[{label : $scope.serviceData[0][0].tipoInfraccion, fill: false, borderWidth: 4, borderColor: bgColors[0], backgroundColor : bgColors[0], data : [], pointBorderColor: "#fff", fillColor:  bgColors[0],strokeColor:  bgColors[0], highlightFill : bgColors[0], highlightStroke: bgColors[0], pointBackgroundColor: bgColors[0]},// pointBorderColor: "#fff", pointHoverBackgroundColor: "#fff"},
		                {label : $scope.serviceData[0][1].tipoInfraccion, fill: false, borderWidth: 4, borderColor: bgColors[1], backgroundColor : bgColors[1], data : [], pointBorderColor: "#fff", fillColor:  bgColors[0],strokeColor:  bgColors[0], highlightFill : bgColors[0], highlightStroke: bgColors[0], pointBackgroundColor: bgColors[1]},
		                {label : $scope.serviceData[0][2].tipoInfraccion, fill: false, borderWidth: 4, borderColor: bgColors[2], backgroundColor : bgColors[2], data : [], pointBorderColor: "#fff", fillColor:  bgColors[0],strokeColor:  bgColors[0], highlightFill : bgColors[0], highlightStroke: bgColors[0], pointBackgroundColor: bgColors[2]},
		                {label : $scope.serviceData[0][3].tipoInfraccion, fill: false, borderWidth: 4, borderColor: bgColors[3], backgroundColor : bgColors[3], data : [], pointBorderColor: "#fff", fillColor:  bgColors[0],strokeColor:  bgColors[0], highlightFill : bgColors[0], highlightStroke: bgColors[0], pointBackgroundColor: bgColors[3]}];
		max = 0;
		for(var i in $scope.serviceData){
			for(var y in $scope.serviceData[i]){
				max = $scope.serviceData[i][y].totalInfracciones > max ? $scope.serviceData[i][y].totalInfracciones : max;
				infracTipoData[y].data.push($scope.serviceData[i][y].totalInfracciones);
			}
		}
		step = getStep(max);
		max = getMaxStep(max, step);
		
//		$scope.buildBarGraphic(infracTipoData, max, step);
		$scope.buildLinearGraphic(infracTipoData, max, step);
//		$scope.buildPieGraphic(infracTipoData, max, step);
		$scope.buildRadarGraphic(infracTipoData, max, step);
	}

	var chart1;
	$scope.buildBarGraphic = function(infracTipoData, max, step){
		gObj = {
			type : 'bar',
			data:{
				labels : months,
				datasets: infracTipoData
			},
			options: {
				scales: {
		            yAxes: [{
		                ticks: {
		                    beginAtZero:false,
		                    min: 0,
		                    max: max,
		                    stepSize: step
		                }
		            }]
		        },
				legend: {
				      display: true,
				      position: 'bottom',
				      labels: {
				        fontColor: "#000080",
				      }
				}
			}
		}
		chart1 = new Chart(gBar, gObj);
	}
	
	var chart2;
	$scope.buildLinearGraphic = function(infracTipoData, max, step){
		
		gObj = {
			type : 'line',
			
			data:{
				labels : months,
				datasets: infracTipoData
			},
			options: {
				scales: {
		            yAxes: [{
		                ticks: {
		                    beginAtZero:false,
		                    min: 0,
		                    max: max,
		                    stepSize: step
		                }
		            }]
		        },
				legend: {
				      display: true,
				      position: 'bottom',
				      labels: {
				        fontColor: "#000080",
				      }
				}
		    }
		}
		chart2 = new Chart($scope.gLineal, gObj);
	}
	
	var chart3;
	$scope.buildPieGraphic = function(infracTipoData){
//		labels =[$scope.serviceData[0][0].tipoInfraccion,
//		         $scope.serviceData[0][1].tipoInfraccion,
//		         $scope.serviceData[0][2].tipoInfraccion,
//		         $scope.serviceData[0][3].tipoInfraccion]
		gObj = {
			type : 'pie',
			
			data:{
				labels : months,
				datasets: infracTipoData
			},
			options: {
				legend: {
				      display: true,
				      position: 'bottom',
				      labels: {
				        fontColor: "#000080",
				      }
				}
		    }
		}
		chart3 = new Chart(gPie, gObj);
	}
	
	var chart4;
	$scope.buildRadarGraphic = function(infracTipoData, max, step){
		gObj = {
			type : 'radar',
			data:{
				labels : months,
				datasets: infracTipoData
			},
			options: {
				tooltips: {
				    mode: 'label'
				},
				scale: {
			        ticks: {
			            beginAtZero: true,
			            max: max,
	                    stepSize: step
			        }
			    },
			    legend: {
			    	display: true,
			    	position: 'bottom',
			    	labels: {
			    		fontColor: "#000080",
			    	}
			    }
	    	}
		}
		chart4 = new Chart($scope.gRadar, gObj);
	}

    borderColor: [
		'rgba(255,99,132,1)',
		'rgba(54, 162, 235, 1)',
		'rgba(255, 206, 86, 1)',
		'rgba(75, 192, 192, 1)'
	];
    
	function getStep(max){
		return (max<=20) ? 1 :
				(max <= 40 ) ? 2 :
					(max <= 100) ? 5 :
						(max<=200) ? 10 :
							(max<=400) ? 20 :
								(max<=600) ? 30 :
									(max<=1000) ? 50 : 
										(max<=2000) ? 100 : 
											(max<=3000) ? 150 : 
												(max<=5000) ? 250 : 500;
	}
	
	function getMaxStep(max, step){
		return (Math.floor(max / step) * step) + step;
	}
	
	function drawSegmentValues()
	{
	    for(var i=0; i<chart3.segments.length; i++) 
	    {
	        // Default properties for text (size is scaled)
	    	gPie.fillStyle="white";
	        var textSize = canvas.width/10;
	        gPie.font= textSize+"px Verdana";

	        // Get needed variables
	        var value = myPieChart.segments[i].value;
	        var startAngle = myPieChart.segments[i].startAngle;
	        var endAngle = myPieChart.segments[i].endAngle;
	        var middleAngle = startAngle + ((endAngle - startAngle)/2);

	        // Compute text location
	        var posX = (radius/2) * Math.cos(middleAngle) + midX;
	        var posY = (radius/2) * Math.sin(middleAngle) + midY;

	        // Text offside to middle of text
	        var w_offset = gPie.measureText(value).width/2;
	        var h_offset = textSize/4;

	        gPie.fillText(value, posX - w_offset, posY + h_offset);
	    }
	}
	
    $scope.showError = function(messageTo) {
		ModalService.showModal({
			templateUrl: 'views/templatemodal/templateModalError.html',
			controller: 'mensajeModalController',
			inputs:{ message: messageTo}
		}).then(function(modal) {
			modal.element.modal();
		});
	};
	
});