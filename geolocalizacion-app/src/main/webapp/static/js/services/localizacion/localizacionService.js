angular.module(localizacionGps).service("localizacionService", function($http, config) {


    this.searchRoutes = function(placaUsuario, tipoRecorrido) {
        return $http.get(config.baseUrl + "/movil/rutas/users" , {
            params: {
            	"placaUsuario" : placaUsuario,
                "tipoRecorrido": tipoRecorrido
            }
        });
    };
    this.searchCoordenadasByFecha = function(placaUsuario, fInicio, fFin) {
        return $http.get(config.baseUrl + "/movil/coordenadas/fecha/users", {
            params: {
            	placaUsuario: placaUsuario,
                fInicio: fInicio,
                fFin: fFin
            }
        });
    };


    this.searchCoordenadas = function(ruta) {
        return $http.get(config.baseUrl + "/movil/coordenadas/rutas/" + ruta.idRuta);
    };

    this.findUserByPlaca = function(placa) {
        return $http.get(config.baseUrl + "/movil/users/" + placa);
    };
    
    this.searchAreas = function(placa) {
        return $http.get(config.baseUrl + "/movil/areasOperativas", {
            params: {
            	placa: placa,
            	procedence: 'W'
            }
        });
    };
    
    this.searchRutas = function(placas, fecha){
    	return $http.get(config.baseUrl+"/movil/users/coordenadas?fechaInicial="+fecha+"&fechaFinal="+fecha+"&placas[]="+placas);
    }
});