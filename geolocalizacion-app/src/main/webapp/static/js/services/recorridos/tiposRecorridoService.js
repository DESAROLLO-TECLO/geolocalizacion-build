angular.module(localizacionGps).service("tiposRecorridoService", function ($http,config) {
	
 
	this.findTiposRecorridos = function() {
		return $http.get(config.baseUrl + "/movil/recorridos");
	};
});



