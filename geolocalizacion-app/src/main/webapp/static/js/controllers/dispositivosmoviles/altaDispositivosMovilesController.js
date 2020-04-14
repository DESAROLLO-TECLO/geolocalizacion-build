angular.module(localizacionGps).controller('altaDispositivosMovilesController', 
		function($scope, $filter, $timeout, gMapsAreaOperativaService, zonasVialesService, $controller, dispositivosMovilesService, ModalService, $q, $window, data) {
	$scope.catalogo = data.data;
	
	$scope.originalDispositivo = { 
			modelo : $scope.catalogo.marcasModelos[0].modelos[0].modeloId,
			tipoDispositivo : $scope.catalogo.tiposDispositivos[0].idTipoDispositivo,
	};
	$scope.duplicate = {};
	
	$scope.modeloCatalogo =  $scope.catalogo.marcasModelos[0];
	
	$scope.cambiaModelo = function(){
		$scope.dispositivo.modelo = $scope.modeloCatalogo.modelos[0].modeloId;
	}
	
	$scope.dispositivo = angular.copy($scope.originalDispositivo);
	
	$scope.resetDuplicate = function(obj){
		switch(obj){
			case 'ip': $scope.duplicate.ip = null; break;
			case 'numSim': $scope.duplicate.numSim = null; break;
			case 'numSerie': $scope.duplicate.numSerie = null; break;
		}
	}
	
	$scope.guardar = function(){
		if ($scope.altaDispositivo.$invalid) {
            
            angular.forEach($scope.altaDispositivo.$error, function (field) {
              angular.forEach(field, function(errorField){
            	  errorField.$setDirty();
              })
            });
            $scope.showAviso("Formulario Incompleto");
            return;
		}
		dispositivosMovilesService.altaDispositivo($scope.dispositivo)
		.success(function(data){
			response = data;
			if(response.status.codigo == "TCL400"){
				$scope.duplicate.ip = response.data.flagRecord.ip == null ? null : response.data.flagRecord.ip;
				$scope.duplicate.numSim = response.data.flagRecord.numSim == null ? null : response.data.flagRecord.numSim;
				$scope.duplicate.numSerie = response.data.flagRecord.numSerie == null ? null : response.data.flagRecord.numSerie;
			}
			else{
				$scope.showAviso(response.status.descripcion);
				$scope.modeloCatalogo =  $scope.catalogo.marcasModelos[0];
				$scope.dispositivo = angular.copy($scope.originalDispositivo);
				$scope.altaDispositivo.$setPristine();
				
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