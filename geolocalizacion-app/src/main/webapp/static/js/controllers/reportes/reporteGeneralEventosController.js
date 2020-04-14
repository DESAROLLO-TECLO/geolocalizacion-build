angular.module(localizacionGps).controller('reporteGeneralEventosController', function($scope, $filter, gMapsRutasService, $controller, ModalService, reporteService, data, table, dateTimePicker) {
	$("[data-toggle=popover]").popover();
	$scope.originalDataForm = data.data;
	$scope.dataForm = angular.copy($scope.originalDataForm);
	$scope.dateTimePickerOptions = dateTimePicker;
	$scope.table = table;
	$scope.originalReportForm = {};
	$scope.reset = function(){
		$scope.dataForm.tipoInfracciones = [];
		$scope.dataRequest = angular.copy($scope.originalReportForm);
	}
	$scope.reset();
	
	$scope.changeTipoEvento = function(){
		$scope.tipoInfraccionRequired = 
			($scope.dataRequest.tipoEventos == null || $scope.dataRequest.tipoEventos == undefined) ? false : 
				$scope.tipoInfraccionRequired = $scope.dataRequest.tipoEventos.filter(id => id == 2).length == 1;
		if (!$scope.tipoInfraccionRequired){
			$scope.dataRequest.tipoInfracciones = [];
			$scope.dataForm.tipoInfracciones = [];
		}else{
			$scope.dataForm.tipoInfracciones = angular.copy($scope.originalDataForm.tipoInfracciones);
		}
	}
	
	$scope.getReportData = function(){
		
		if($scope.reportForm.$invalid) {
            angular.forEach($scope.reportForm.$error, function (field) {
              angular.forEach(field, function(errorField){
            	  errorField.$setDirty();
              })
            });
            return;
        }
		
		reporteService.reporteGeneral($scope.dataRequest)
		.success(function(data){
			$scope.tipoInfraccionRequiredPostPet = $scope.dataRequest.tipoEventos == null || $scope.dataRequest.tipoEventos == undefined || $scope.tipoInfraccionRequired;
			$scope.report = data;
			if($scope.report.length<1) $scope.showError("No se encontraron resultados");
		})
		.error(function(data){
			$scope.report = [];
		});
	}
	
	$scope.downloadReporte = function() {
		reporteService.reporteGeneralExcel(0)
		.success(function(data, status, headers) {
			var filename = headers('filename');
			var contentType = headers('content-type');
			var file = new Blob([data], {type: 'application/vnd.ms-excel;base64,'});
			save(file , filename);
			$scope.error = false;
		})
		.error(function(data) {
			$scope.error = data;
			$scope.listaBloqueohhVO = {};
		});
	}
	
	function save(file, fileName) {
		 var url 	   = window.URL || window.webkitURL;
	   	 var blobUrl   = url.createObjectURL(file);
	   	 var a         = document.createElement('a');
	   	 a.href        = blobUrl; 
		 a.target      = '_blank';
		 a.download    = fileName;
		 document.body.appendChild(a);
		 a.click();   
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