angular.module(localizacionGps).controller('reporteGeneralEventosOficialesDispositivosController', function($scope, $filter, gMapsRutasService, $controller, ModalService, reporteService, data, table, dateTimePicker) {
	$("[data-toggle=popover]").popover();
//	$("#dsds").tooltip();
	oficialesPropiedadZonaVial(data.data);
	$scope.dataForm = angular.copy($scope.originalDataForm);
	$scope.dateTimePickerOptions = dateTimePicker;
	$scope.table = table;
	$scope.originalReportForm = {empleados:[], devices:[]};
	$scope.tables = $scope.originalReportForm 
	$scope.reset = function(){
		$scope.dataForm.tipoInfracciones = [];
		$scope.dataRequest = angular.copy($scope.originalReportForm);
	}
	$scope.reset();
	
	$scope.dataRequest.officerUnionDisp = true;
	$scope.toggleUnion = function(){
		$scope.dataRequest.officerUnionDisp = !$scope.dataRequest.officerUnionDisp;
		$("#dsds").tooltip('hide');
	}
	
	$scope.officerAuto = null;
    
    $scope.autoObj = {
    	officer : {
    		filters : ["nombreZonaVial", "empPlaca", "empNombre", "empApePaterno", "empApeMaterno", "fullEmpleadoData", "fullName"],
    		mainFilter : "empPlaca",
    		mainText : "fullEmpleadoData",
	        minimumChars: 1,
	        dropdownHeight: '200px',
	        data: function (searchedText) {
	        	return $scope.autoObj.assembleAutoComplete(searchedText, $scope.originalDataForm.officers, $scope.autoObj.officer.filters, $scope.autoObj.officer.mainFilter, $scope.autoObj.officer.mainText)
	        }
    	},
    	device : {
    		filters : ["nombreZonaVial", "numSerie", "numSim", "numIp", "fullDataDevice"],
    		mainFilter : "numSerie",
    		mainText : "fullDataDevice",
	        minimumChars: 1,
            dropdownHeight: '200px',
	        data: function (searchedText) {
	        	return $scope.autoObj.assembleAutoComplete(searchedText, $scope.originalDataForm.devices, $scope.autoObj.device.filters, $scope.autoObj.device.mainFilter, $scope.autoObj.device.mainText)
	        }
    	},
    	assembleAutoComplete : function(searchedText, collection, filters, mainFilter, mainText){
        	var mainArray = [];
            for(var filter in filters){
            	var filteredData = _.filter(collection, function (iterator) {
                    return iterator[filters[filter]].toUpperCase().includes(searchedText.toUpperCase());
                });
            	mainArray = $scope.autoObj.clearArrayRepeatedData(mainArray, filteredData, mainFilter);
            }
            return _.pluck(mainArray, mainText);
    	},
    	clearArrayRepeatedData : function clearArrayRepeatedData(masterArray, innerArray, key){
        	filterObj = {};
        	for(var i in innerArray){
        		filterObj[key] = innerArray[i][key];
        		if($filter('filter')(masterArray, filterObj, true)[0] == undefined)
            		masterArray.push(innerArray[i])
        	}
        	return masterArray;
        }
    }
    
    $scope.submitOfficer = function(empData){
    	if(empData == null || empData == "") return;
    	empleado = $filter('filter')($scope.originalDataForm.officers, {fullEmpleadoData: empData}, true)[0];
    	if(empleado == undefined){
    		$scope.showError("No existe oficial con datos " + "'"+ empData +"'"); 
    		$scope.officerAuto = ""; 
    		$('.autoCompleteOfficerInput').blur();
    		return;
    	}
    	
    	newEmpleado = $filter('filter')($scope.tables.empleados, {empPlaca: empleado.empPlaca}, true)[0];
    	if(newEmpleado == undefined){
    		$scope.dataRequest.empleados.push(empleado.empId);
    		$scope.tables.empleados.push(empleado);
    	}
    	$scope.officerAuto = "";
    }
    
    $scope.submitDevice = function(deviceData){
    	if(deviceData == null || deviceData == "") return;
    	device = $filter('filter')($scope.originalDataForm.devices, {fullDataDevice: deviceData}, true)[0];
    	if(device == undefined){
    		$scope.showError("No existe dispositivo con datos " + "'"+ deviceData +"'"); 
    		$scope.deviceAuto = ""; 
    		$('.autoCompleteOfficerInput').blur();
    		return;
    	}
    	
    	newDevice = $filter('filter')($scope.tables.devices, {numSerie: device.numSerie}, true)[0];
    	if(newDevice == undefined){
    		$scope.dataRequest.devices.push(device.idDispositivo);
    		$scope.tables.devices.push(device);
    	}
    	$scope.deviceAuto = "";
    }
    
    
    $scope.removeElement = function(array,array1, index){
    	array.splice(index, 1);
    	array1.splice(index, 1);
    }
    
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
		if($scope.reportFormII.$invalid) {
            angular.forEach($scope.reportFormII.$error, function (field) {
              angular.forEach(field, function(errorField){
            	  errorField.$setDirty();
              })
            });
            return;
        }
		if($scope.dataRequest.devices.length == 0 && $scope.dataRequest.empleados.length == 0){
			$scope.showError("No se ha buscado al menos un oficial o un dispositivo");
			return;
		}
		
		reporteService.reporteGeneralOficialesDispositivos($scope.dataRequest)
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
		reporteService.reporteGeneralExcel(1)
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
	
	function oficialesPropiedadZonaVial(data){
		$scope.originalDataForm = data;
		$scope.originalDataForm.officers = [];
		$scope.originalDataForm.devices = [];
		for(var i in $scope.originalDataForm.zonasViales){
			for(var y in $scope.originalDataForm.zonasViales[i].empleados){
				$scope.originalDataForm.zonasViales[i].empleados[y].nombreZonaVial = $scope.originalDataForm.zonasViales[i].nombreZonaVial;
				$scope.originalDataForm.zonasViales[i].empleados[y].fullName = 
					$scope.originalDataForm.zonasViales[i].empleados[y].empNombre + " " +
					$scope.originalDataForm.zonasViales[i].empleados[y].empApePaterno + " " +
					$scope.originalDataForm.zonasViales[i].empleados[y].empApeMaterno;
				
				$scope.originalDataForm.zonasViales[i].empleados[y].fullEmpleadoData = 
					$scope.originalDataForm.zonasViales[i].empleados[y].fullName + ", " +
					$scope.originalDataForm.zonasViales[i].empleados[y].empPlaca + ", Zona víal: " +
					$scope.originalDataForm.zonasViales[i].empleados[y].nombreZonaVial;
				
				$scope.originalDataForm.officers.push($scope.originalDataForm.zonasViales[i].empleados[y]);
			}
			
			for(var y in $scope.originalDataForm.zonasViales[i].dispositivos){
				$scope.originalDataForm.zonasViales[i].dispositivos[y].nombreZonaVial = $scope.originalDataForm.zonasViales[i].nombreZonaVial;
				$scope.originalDataForm.zonasViales[i].dispositivos[y].fullDataDevice = "N. Serie: " +
					$scope.originalDataForm.zonasViales[i].dispositivos[y].numSerie + ", N. Sim: " +
					$scope.originalDataForm.zonasViales[i].dispositivos[y].numSim + ", IP: " +
					$scope.originalDataForm.zonasViales[i].dispositivos[y].numIp;
					
				$scope.originalDataForm.devices.push($scope.originalDataForm.zonasViales[i].dispositivos[y]);
			}
		}
	}
	
	function dispositivosPropiedadZonaVial(){
		$scope.originalDataForm.devices = [];
	}
	
});