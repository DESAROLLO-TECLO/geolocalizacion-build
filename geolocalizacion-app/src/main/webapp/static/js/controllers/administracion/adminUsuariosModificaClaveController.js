angular.module(localizacionGps).controller('adminUsuariosModificaClaveController', function($scope, $filter, administracionService, ModalService) {
	$scope.objeto = {
			contra: "",
			nuevo : "",
			repetir : ""	
	}
	
	
	/* NOTIFICACIONES MODAL */
	$scope.showAviso = function(messageTo, action) {
        ModalService.showModal({
          templateUrl: 'views/templatemodal/templateModalAviso.html',
          controller: 'mensajeModalController',
          inputs:{ message: messageTo}
        }).then(function(modal) {
          modal.element.modal();
          modal.close.then(function(result) {
	        	if(result){
	        		action();
	        	}
	        }); 
        });
	};
	
	$scope.showError = function(messageTo) {
        ModalService.showModal({
          templateUrl: 'views/templatemodal/templateModalError.html',
          controller: 'mensajeModalController',
          	  inputs:{ message: messageTo}
        }).then(function(modal) {
          modal.element.modal();
        });
	};
	
	$scope.showConfirmacion = function(messageTo, action){
		ModalService.showModal({
	    	templateUrl: 'views/templatemodal/templateModalConfirmacion.html',
	        controller: 'mensajeModalController',
	        	inputs:{ message: messageTo}
	    }).then(function(modal) {
	        modal.element.modal();
	        modal.close.then(function(result) {
	        	if(result){
	        		action();
	        	}
	        }); 
	    });
	};
	
	$scope.guardar = function()
	{
		
		if($scope.objeto.contra == "" || $scope.objeto.nuevo == "" || $scope.objeto.repetir == "")
		{
			if ($scope.formChgPass.$invalid) {
		           angular.forEach($scope.formChgPass.$error, function (field) {
		             angular.forEach(field, function(errorField){
		           	 errorField.$setDirty();
		             })
		           });
		           $scope.showAviso("Formulario Incompleto", function(){});
		           return;
		     }
			
		}
		else
		{
			if($scope.objeto.nuevo != $scope.objeto.repetir)
			{
				$scope.showAviso("La contraseña nueva no coincide", function(){});
				$scope.objeto.contra = "";  $scope.objeto.nuevo = "";  $scope.objeto.repetir = "";
				$scope.formChgPass.$setPristine();
			}
			else
			{
				if($scope.objeto.contra == $scope.objeto.nuevo)
				{
					$scope.showAviso("La contraseña nueva no puede ser igual a la actual", function(){});
					$scope.objeto.contra = "";  $scope.objeto.nuevo = "";  $scope.objeto.repetir = "";
					$scope.formChgPass.$setPristine();
				}
				else
				{
					$scope.cambiarClave();
				}
			}
		}
	};
	
	$scope.cambiarClave = function(){
		administracionService.cambiarClave($scope.objeto.contra, $scope.objeto.nuevo, $scope.objeto.repetir)
		.success(function(data) {
			if(data[0] == 0){
				
				$scope.showAviso("El registro se actualizó correctamente", function(){});
			}
			else
			{
				$scope.showAviso(data[1]);
			}
			$scope.objeto.contra = "";  $scope.objeto.nuevo = "";  $scope.objeto.repetir = "";
			$scope.formChgPass.$setPristine();
		}).error(function(data) {
			if(data.message){
				$scope.showAviso(data.message);
			}else{
				$scope.showAviso(data);
			}
			$scope.objeto.contra = "";  $scope.objeto.nuevo = "";  $scope.objeto.repetir = "";
			$scope.formChgPass.$setPristine();
		});	
	};
});