angular.module(localizacionGps).controller('editaDispositivoController', 
		function($scope, $filter, $timeout, gMapsAreaOperativaService, zonasVialesService, $controller, dispositivosMovilesService, ModalService, $q, $window, data, dispositivo) {
	$scope.catalogo = data.data;	
	
	$scope.cambiaMarca = function(){
		$scope.catalogo.modelos = $scope.marca.modelos;
		$scope.dispositivo.modelo = $scope.marca.modelos[0].modeloId;
	}
	
	$scope.prepareOriginalDevice = function(){
		dev = JSON.parse(dispositivo);
		$scope.originalDispositivo = {};
		$scope.marca = $filter('filter')($scope.catalogo.marcasModelos, {marca:{marcaId : dev.dispositivoModelo.modeloMarca.marcaId }}, true)[0];
		$scope.catalogo.modelos = $scope.marca.modelos;
		$scope.originalDispositivo.modelo = $filter('filter')($scope.catalogo.modelos, {modeloId : dev.dispositivoModelo.modeloId}, true)[0].modeloId;
		$scope.originalDispositivo.tipoDispositivo = dev.dispositivoMovil.idTipoDispositivo;
		if(dev.zonaVial != "Sin zona vial") $scope.originalDispositivo.zonaVial = $filter('filter')($scope.catalogo.zonasViales, {nombreZonaVial : dev.zonaVial}, true)[0].idZonaVial;
		$scope.originalDispositivo.numSerie = dev.numSerie;
		$scope.originalDispositivo.numSim = dev.numSim;
		$scope.originalDispositivo.numIp = dev.numIp;
		$scope.originalDispositivo.idDispositivo = dev.idDispositivo;
	}
	$scope.prepareOriginalDevice();
	
	$scope.dispositivo = angular.copy($scope.originalDispositivo);
	
	$scope.duplicate = {};
	$scope.resetDuplicate = function(obj){
		switch(obj){
			case 'ip': $scope.duplicate.ip = null; break;
			case 'numSim': $scope.duplicate.numSim = null; break;
			case 'numSerie': $scope.duplicate.numSerie = null; break;
		}
	}
	
	$scope.guardar = function(){
		if ($scope.editaDispositivo.$invalid) {
            
            angular.forEach($scope.editaDispositivo.$error, function (field) {
              angular.forEach(field, function(errorField){
            	  errorField.$setDirty();
              })
            });
            $scope.showAviso("Formulario Incompleto");
            return;
		}
		dispositivosMovilesService.updateDispositivo($scope.dispositivo)
		.success(function(data){
			response = data;
			if(response.status.codigo == "TCL400"){
				$scope.duplicate.ip = response.data.flagRecord.ip == null ? null : response.data.flagRecord.ip;
				$scope.duplicate.numSim = response.data.flagRecord.numSim == null ? null : response.data.flagRecord.numSim;
				$scope.duplicate.numSerie = response.data.flagRecord.numSerie == null ? null : response.data.flagRecord.numSerie;
			}
			else{
				$scope.showAviso(response.status.descripcion);				
			}
				
		})
		.error(function(data){
			
		})
	}
	
	$scope.showAviso = function(messageTo, action) {
        ModalService.showModal({
          templateUrl: 'views/templatemodal/templateModalAviso.html',
          controller: 'mensajeModalController',
          inputs:{ message: messageTo}
        }).then(function(modal) {
          modal.element.modal();
        });
	};
})