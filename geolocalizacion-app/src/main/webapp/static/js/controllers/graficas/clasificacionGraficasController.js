angular.module(localizacionGps).controller('clasificacionGraficasController', function($scope, $filter, gMapsRutasService, $controller, ModalService) {
	
	$("[data-toggle=popover]").popover();
	
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