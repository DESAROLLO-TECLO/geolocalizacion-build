angular.module(localizacionGps).service("dispositivosMovilesService", function($http, config) {
	
	this.catalogosAltaDispositivo = function(placaUsuario, tipoRecorrido) {
        return $http.get(config.baseUrl + "/catalogosNuevoDispositivo");
    };
    
    this.altaDispositivo = function(dispositivo) {
        return $http.post(config.baseUrl + "/dispositivo", dispositivo);
    };

    this.updateDispositivo = function(dispositivo) {
        return $http.put(config.baseUrl + "/dispositivo", dispositivo);
    };
    
    this.dipositivos = function(dispositivoRequestBody) {
        return $http.get(config.baseUrl + "/dispositivos?"+
        			"numSerie="+dispositivoRequestBody.numSerie+
        			"&numSim="+dispositivoRequestBody.numSim+
        			"&numIp="+dispositivoRequestBody.numIp+
        			"&modelos[]="+dispositivoRequestBody.modelos+
        			"&tipoDispositivo[]="+dispositivoRequestBody.tipoDispositivo+
        			"&zonaVial[]="+dispositivoRequestBody.zonaVial+
        			"&complementacion="+dispositivoRequestBody.complementacion+
        			"&sinZonaVial="+dispositivoRequestBody.sinZonaVial);
        		
    };
    
    lastSearch = {};
    
    this.setLastSearch = function(dev){
    	lastSearch = dev;
    }
    
    this.getLastSearch = function(){
    	return lastSearch;
    }
})