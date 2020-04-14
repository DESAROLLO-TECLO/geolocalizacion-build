angular.module(localizacionGps).controller('graficaTipoInfraccionDiaController', function($scope, $filter, gMapsRutasService, $controller, reporteService, ModalService, data) {
	
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
	
	var gBar = document.getElementById('gDBar').getContext('2d');
	var gLineal = document.getElementById('gDLineal').getContext('2d');
	var gPie = document.getElementById('gDPie').getContext('2d');
	var gRadar = document.getElementById('gDRadar').getContext('2d');
	
	$scope.dateTimePickerOptions = {
			minDate: false,
	        //maxDate: moment(),
			maxDate: false,
	        format: 'DD/MM/YYYY'
	}
	
	$scope.zonasViales = data.data;
	$scope.graphicOriginalForm = {zonaId : null, intervalo : 1, dia : null};
	$scope.resetForm = function(){
		$scope.gForm = angular.copy($scope.graphicOriginalForm);
	}
	$scope.resetForm();
	
	$scope.searchZoneData = function(){
		if($scope.searchAreaForm.$invalid) {
            
            angular.forEach($scope.searchAreaForm.$error, function (field) {
              angular.forEach(field, function(errorField){
            	  errorField.$setDirty();
              })
            });
            return;
        }
		$scope.graficaDiaria();
	}
	
	$scope.graficaDiaria = function(){
		reporteService.infraccionesPorZonaVial($scope.gForm.zonaId, moment($scope.gForm.dia).format("DD/MM/YYYY"))
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
//		infracTipoData=[{label : $scope.serviceData[0].tipoInfraccion, fill: false, borderWidth: 4, borderColor: bgColors[0], backgroundColor : bgColors[0], data : $scope.serviceData[0].totalInfracciones, pointBorderColor: "#fff", fillColor:  bgColors[0], strokeColor:  bgColors[0], highlightFill : bgColors[0], highlightStroke: bgColors[0], pointBackgroundColor: bgColors[0]},// pointBorderColor: "#fff", pointHoverBackgroundColor: "#fff"},
		max = 0;
		infracTipoData=[
		{
			data:[],
			backgroundColor:[],
			label : 'Infracciones: ',
			borderWidth: 4,
			borderColor: bgColors[1],
			pointBackgroundColor: bgColors[1],
			pointBorderColor: "#fff",
			fill: false}];
		labels = [];
		for(var i in $scope.serviceData){
			
			max = $scope.serviceData[i].totalInfracciones > max ? $scope.serviceData[i].totalInfracciones : max;
			labels.push($scope.serviceData[i].tipoInfraccion);
			infracTipoData[0].data.push($scope.serviceData[i].totalInfracciones);
			infracTipoData[0].backgroundColor.push(bgColors[i]);
		}
		step = getStep(max);
		max = getMaxStep(max, step);
		
		$scope.buildBarGraphic(labels, infracTipoData, max, step);
		$scope.buildLinearGraphic(labels, infracTipoData, max, step);
		$scope.buildPieGraphic(labels, infracTipoData);
		$scope.buildRadarGraphic(labels, infracTipoData, max, step);
	}

	var chart1;
	$scope.buildBarGraphic = function(labels, infracTipoData, max, step){
		cop = angular.copy(infracTipoData);
		cop[0].borderWidth = 1;
		//cop[0].borderColor = null;
		gObj = {
			type : 'bar',
			data:{
				labels : labels,
				datasets: cop
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
		        }
			}
		}
		chart1 = new Chart(gBar, gObj);
	}
	
	var chart2;
	$scope.buildLinearGraphic = function(labels, infracTipoData, max, step){
		
		gObj = {
			type : 'line',
			
			data:{
				labels : labels,
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
		        }
		    }
		}
		chart2 = new Chart(gLineal, gObj);
	}
	
	var chart3;
	$scope.buildPieGraphic = function(labels, infracTipoData){
		cop = angular.copy(infracTipoData);
		cop[0].borderWidth = 1;
		gObj = {
			type : 'pie',
			
			data:{
				labels : labels,
				datasets: cop
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
	$scope.buildRadarGraphic = function(labels, infracTipoData, max, step){
		gObj = {
			type : 'radar',
			data:{
				labels : labels,
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