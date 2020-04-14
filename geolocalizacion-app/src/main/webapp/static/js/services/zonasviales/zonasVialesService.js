angular.module(localizacionGps).service("zonasVialesService", function($http, config) {
	
	this.zonas = function(placa) {
        return $http.get(config.baseUrl + "/zonasviales/dispositivosoficiales", {
            params: {
            	placa: placa,
            	procedence: 'W',
            	codeRequest: 'a'
            }
        });
    };
    
    this.zonasDispositivosAndOficialesByZonaCode = function(codigo, codeRequest) {
        return $http.get(config.baseUrl + "/zonasviales/dispositivosoficialesPorZonaCodigo", {
            params: {
            	codigo: codigo,
            	codeRequest: codeRequest
            }
        });
    };
    
    this.dispositivosOficialesSinAsignacion = function(codeRequest) {
        return $http.get(config.baseUrl + "/zonasviales/dispositivosOficialesSinAsignacion", {
            params: {
            	codeRequest: codeRequest
            }
        });
    };
    
    this.allInfoBySingleDisp = function(dispositivo) {
        return $http.get(config.baseUrl + "/zonasviales/allInfoBySingleDisp", {
            params: {
            	numSerie : dispositivo.numSerie != null ? dispositivo.numSerie : "",
            	numSim : dispositivo.numSim != null ? dispositivo.numSim : "",
            	numIp : dispositivo.numIp != null ? dispositivo.numIp : ""
            }
        });
    };
    
    this.allInfoBySingleOfficer = function(zonaCode, empId) {
        return $http.get(config.baseUrl + "/zonasviales/allInfoBySingleOfficer", {
            params: {
            	zonaCode : zonaCode,
            	empId : empId
            }
        });
    };
    
    this.searchOfficer = function(empleado) {
        return $http.get(config.baseUrl + "/zonasviales/officersInfo", {
            params: {
            	placa : empleado.placa != null ? empleado.placa : "",
            	nombre : empleado.nombre != null ? empleado.nombre : "",
            	paterno : empleado.paterno != null ? empleado.paterno : "",
            	materno : empleado.materno != null ? empleado.materno : ""
            }
        });
    };
    
    this.zonasActivas = function(codeRequest){
    	return $http.get(config.baseUrl+"/zonasviales/zonasActivas?codeRequest="+ codeRequest);
    }
    
    this.zonasActivasPorPlacaSupervisor = function(empPlaca){
    	return $http.get(config.baseUrl+"/zonasviales/zonasCatalogo?empPlaca=" + empPlaca);
    }
    
    this.searchRutasByOfficers = function(placas, fecha){
    	return $http.get(config.baseUrl+"/eventos/emp?fechaInicial="+fecha+"&fechaFinal="+fecha+"&emp[]="+placas);
    }
    
    this.searchRutasByNumSereHH = function(series, fecha){
    	return $http.get(config.baseUrl+"/eventos/hh?fechaInicial="+fecha+"&fechaFinal="+fecha+"&series[]="+series);
    }
    
    this.searchLastPointByHH = function(series, fecha){
    	return $http.get(config.baseUrl+"/optimizedEventos/hh",{
    			params:{
    				"fechaInicial" : fecha,
    				"series[]": series
    			}});
    }
    
    this.searchLastPointByOfficers = function(placas, fecha){
    	return $http.get(config.baseUrl+"/optimizedEventos/officers",{
    		params:{
    			"fechaInicial" : fecha,
    			"emp[]":placas
    		}
    	});
    }
    
    this.eventosByItem = function(emp, numSerie, fecha){
    	return $http.get(config.baseUrl+"/eventos/item", {
    			params:{
    				"emp" : emp,
    				"numSerie" : numSerie,
    				"fechaInicial" : fecha
    			}}
    	    )};
    
    this.persitDispositivosZonasAsignadas = function(asignaciones) {
        return $http.post(config.baseUrl + "/zonasviales/asignaciones", asignaciones);
    };
})