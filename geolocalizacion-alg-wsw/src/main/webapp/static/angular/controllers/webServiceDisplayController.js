angular.module(localizacionGps).controller("WebServiceDisplayController", function($rootScope, $scope, wsData) {
	$rootScope.wsData = wsData.data;
	$scope.setVersionReview = function(version){
		
			$scope.versionReview = angular.copy(version);
			$("#fechaProdu").html(version.fechaLiberacionProduccion != null ? "Fecha de Liberación " + moment(version.fechaLiberacionProduccion).format("DD/MM/YYYY") : "Sin Fecha de Liberación");
			$scope.versionReview.details = $scope.versionReview.versionDescripcion.split("|");
			for(var x in $rootScope.wsData.versiones){
				$rootScope.wsData.versiones[x].selected = false;
			}
			version.selected = true;
		
	}
	if($rootScope.wsData.version!=null){
		$scope.setVersionReview($rootScope.wsData.version);
	}
	else{
		$rootScope.wsData.version = {versionNombre : "Sin Versión"};
	}

 });