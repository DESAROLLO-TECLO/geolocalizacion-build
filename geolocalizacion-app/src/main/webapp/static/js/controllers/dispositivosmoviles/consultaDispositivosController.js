angular.module(localizacionGps).controller('consultaDispositivosController', 
		function($scope, $filter, $timeout, zonasVialesService, $controller, dispositivosMovilesService, ModalService, $q, $window, data, lastDeviceSearched) {
	$scope.originalDispositivo =  {"numSerie":"", "numSim":"", "numIp":"", "zonaVial":[], "modelos":[], "tipoDispositivo":[], "marcas":[], "complementacion":false, "sinZonaVial":false};
	
	$scope.catalogo = data.data;
	$scope.catalogo.zonasViales.push({"idZonaVial":-1,"nombreZonaVial":"SIN ZONA ASIGNADA","codigoZonaVial":"-1", "activo":1});
	
	$scope.cambiaMarca = function(){
		$scope.catalogo.modelos = [];
		angular.forEach($scope.dispositivo.marcas, function(v, k, o){
			$scope.catalogo.modelos = $scope.catalogo.modelos.concat(v.modelos);
		})
	}
	
	$scope.cambiaZona = function(){
		noZone = $filter('filter')($scope.dispositivo.zonaVial, -1, true);
		$scope.dispositivo.sinZonaVial = noZone != undefined;
	}
	
	$scope.validRequest= function(){
		if(!validDevice($scope.dispositivo)) return;
		$scope.requestDeviceInfo($scope.dispositivo);
	}
	
	$scope.requestDeviceInfo = function(dev){
		dispositivosMovilesService.dipositivos(dev)
		.success(function(data){
			$scope.deviceRecords = data;
		})
		.error(function(data){
			if(lastDeviceSearched != null)
				$scope.showAviso(data.status.descripcion);
			$scope.deviceRecords = [];
		})
		dispositivosMovilesService.setLastSearch(dev);
	}
	
	function validDevice(obj){
		if(obj.marcas.length > 0 && obj.modelos.length == 0){
			$scope.showAviso("Por favor seleccione al menos un modelo");
			return false;
		}
		if(obj.numSerie == "" && obj.numSim == "" && obj.numIp == "" && obj.zonaVial.length == 0 && obj.modelos.length == 0 && obj.tipoDispositivo.length == 0 && !obj.complementacion && !obj.sinZonaVial){
			$scope.showAviso("Por favor llene al menos un parámetro de búsqueda. Si selecciona una marca, debe seleccionar al menos un modelo");
			return false;
		}
		if($scope.dispoInfoForm.numIp.$dirty && $scope.dispoInfoForm.numIp.$invalid){
			$scope.showAviso("La IP introducida es incorrecta");
			return false;
		}
		return true;
	}
	
	$scope.reset = function(){
		$scope.dispositivo = angular.copy($scope.originalDispositivo);
	}
	$scope.reset();
	
	$scope.showAviso = function(messageTo, action) {
        ModalService.showModal({
          templateUrl: 'views/templatemodal/templateModalAviso.html',
          controller: 'mensajeModalController',
          inputs:{ message: messageTo}
        }).then(function(modal) {
          modal.element.modal();
        });
	};
	
	$scope.firstRequest = function(){
		if(lastDeviceSearched == null || isEmpty(lastDeviceSearched)){
			dev = angular.copy($scope.originalDispositivo);
			dev.complementacion = true;
			dev.sinZonaVial = true;
			dev.zonaVial = [-1];
			$scope.requestDeviceInfo(dev);
		}else{
			$scope.dispositivo = lastDeviceSearched;
			$scope.cambiaZona();
			$scope.cambiaMarca();
			asignMarcas();
			$scope.requestDeviceInfo($scope.dispositivo);
		}
	}
	
	asignMarcas = function(){
		marcas = [];
		for(var x in $scope.dispositivo.marcas){
			marcas.push($filter('filter')($scope.catalogo.marcasModelos, {marca:{marcaId: $scope.dispositivo.marcas[x].marca.marcaId}}, true)[0]);
		}
		$scope.dispositivo.marcas = marcas;
	}
	$scope.firstRequest();
	
	
	function isEmpty(obj) {
	    for(var key in obj) {
	        if(obj.hasOwnProperty(key))
	            return false;
	    }
	    return true;
	}

})