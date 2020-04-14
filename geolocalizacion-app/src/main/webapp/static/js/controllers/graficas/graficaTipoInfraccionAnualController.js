angular.module(localizacionGps).controller('graficaTipoInfraccionAnualController', function($scope, $filter, gMapsRutasService, $controller, reporteService, ModalService, data) {
	
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
	
	$scope.anios =
		[{nbOpcion: "2016", valor: 2016}, {nbOpcion: "2017", valor: 2017},
		 {nbOpcion: "2018", valor: 2018}, {nbOpcion: "2019", valor: 2019}];
	
	var gBar = document.getElementById('gABar').getContext('2d');
	var gLineal = document.getElementById('gALineal').getContext('2d');
	var gRadar = document.getElementById('gARadar').getContext('2d');
	
	$scope.zonasViales = data.data;
	$scope.graphicOriginalForm = {zonaId : null, intervalo : null, anios : []};
	$scope.resetForm = function(){
		$scope.gForm = angular.copy($scope.graphicOriginalForm);
	}
	$scope.resetForm();
	
	$scope.searchZoneData = function(){
		if($scope.searchAreaFormA.$invalid) {
            
            angular.forEach($scope.searchAreaFormA.$error, function (field) {
              angular.forEach(field, function(errorField){
            	  errorField.$setDirty();
              })
            });
            return;
        }
		$scope.graficaAnual();
	}
	
	$scope.graficaAnual = function(){
		reporteService.infraccionesAnualPorZonaVial($scope.gForm.zonaId, $scope.gForm.anios)
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
			$scope.showError($scope.error.message);
			$scope.serviceData = [];
		})
	}
   
	$scope.buildGraphics = function(){
		infracTipoData=[{label : $scope.serviceData[0][0].tipoInfraccion, fill: false, borderWidth: 4, borderColor: bgColors[0], backgroundColor : bgColors[0], data : [], pointBorderColor: "#fff", fillColor:  bgColors[0],strokeColor:  bgColors[0], highlightFill : bgColors[0], highlightStroke: bgColors[0], pointBackgroundColor: bgColors[0]},// pointBorderColor: "#fff", pointHoverBackgroundColor: "#fff"},
		                {label : $scope.serviceData[0][1].tipoInfraccion, fill: false, borderWidth: 4, borderColor: bgColors[1], backgroundColor : bgColors[1], data : [], pointBorderColor: "#fff", fillColor:  bgColors[0],strokeColor:  bgColors[0], highlightFill : bgColors[0], highlightStroke: bgColors[0], pointBackgroundColor: bgColors[1]},
		                {label : $scope.serviceData[0][2].tipoInfraccion, fill: false, borderWidth: 4, borderColor: bgColors[2], backgroundColor : bgColors[2], data : [], pointBorderColor: "#fff", fillColor:  bgColors[0],strokeColor:  bgColors[0], highlightFill : bgColors[0], highlightStroke: bgColors[0], pointBackgroundColor: bgColors[2]},
		                {label : $scope.serviceData[0][3].tipoInfraccion, fill: false, borderWidth: 4, borderColor: bgColors[3], backgroundColor : bgColors[3], data : [], pointBorderColor: "#fff", fillColor:  bgColors[0],strokeColor:  bgColors[0], highlightFill : bgColors[0], highlightStroke: bgColors[0], pointBackgroundColor: bgColors[3]}];
		max = 0;
		years = [];
		for(var i in $scope.serviceData){
			years.push($scope.serviceData[i][0].yr);
			for(var y in $scope.serviceData[i]){
				max = $scope.serviceData[i][y].totalInfracciones > max ? $scope.serviceData[i][y].totalInfracciones : max;
				infracTipoData[y].data.push($scope.serviceData[i][y].totalInfracciones);
			}
		}
		step = getStep(max);
		max = getMaxStep(max, step);
		
		$scope.buildBarGraphic(infracTipoData, max, step);
		$scope.buildLinearGraphic(infracTipoData, max, step);
		$scope.buildRadarGraphic(infracTipoData, max, step);
	}
	
	var chart1;
	$scope.buildBarGraphic = function(infracTipoData, max, step){
		gObj = {
			type : 'bar',
			data:{
				labels : years,
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
				labels : years,
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
		chart2 = new Chart(gLineal, gObj);
	}
	
	var chart3;
	$scope.buildPieGraphic = function(infracTipoData){
		gObj = {
			type : 'pie',
			
			data:{
				labels : years,
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
				labels : years,
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
		chart4 = new Chart(gRadar, gObj);
	}

    borderColor: [
		'rgba(255,99,132,1)',
		'rgba(54, 162, 235, 1)',
		'rgba(255, 206, 86, 1)',
		'rgba(75, 192, 192, 1)'
	];
    
	function getStep(max){
		return (max<=10) ? 1 :
				(max <= 20) ? 2 :
					(max <= 50) ? 5 :
						(max<=100) ? 10 :
							(max<=200) ? 20 :
								(max<=300) ? 30 :
									(max<=500) ? 50 : 
										(max<=1000) ? 100 : 
											(max<=1500) ? 150 : 
												(max<=2500) ? 250 : 500;
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