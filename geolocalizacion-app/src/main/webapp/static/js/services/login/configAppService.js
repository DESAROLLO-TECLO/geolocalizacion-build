angular.module(localizacionGps).service('configAppService', function($http, config) {
	
	var app = "/aplicacion";
	var urlConfig = config.baseUrl+app;
	
	/*
	 * @author: Cesar Gomez
	 * @return: data
	*/
	this.configuracionAplicacion = function(data) {
		return $http.get(urlConfig + "/configuraciones");	
	};
	
	/*
	 * @author: Cesar Gomez
	 * @return: data
	*/
	this.obtenerResoluciones = function(data) {
		return $http.get(urlConfig + "/resoluciones");
	}
	
	/*
	 * @author: Cesar Gomez
	 * @return: data
	*/
	this.obtenerTemas = function(data) {
		return $http.get(urlConfig + "/temas");
	}
	
	/*
	 * @author: Cesar Gomez
	 * @param: configuracionVO
	*/
	this.guardarConfiguración = function(configuracionVO) {
		return $http.post(urlConfig + "/guardarConfiguracion", configuracionVO);
	}
	
});