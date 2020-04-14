angular.module(localizacionGps).controller('modalMensajeController', function($scope, $element, message, typeModal, close) {
	
	$scope.message = message;
	$scope.typeModal = typeModal;
	
	$scope.close = function(result){
		close(result, 500);
	};
});