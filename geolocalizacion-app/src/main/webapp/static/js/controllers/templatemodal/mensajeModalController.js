angular.module(localizacionGps).controller('mensajeModalController', function($scope, $element, message, close) {
	
	$scope.message = message;
	
	$scope.close = function(result){
		close(result, 500);
	};
});