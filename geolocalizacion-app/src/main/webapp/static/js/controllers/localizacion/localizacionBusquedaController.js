angular.module(localizacionGps).controller('localizacionBusquedaController', function($scope, $filter, gMapsService, $controller, localizacionService, tiposRecorridoService, ModalService) {

    $scope.listaRutas = [];
    $scope.listaCoordenadas = [];
    $scope.listaTiposRecorrido = [];
    $scope.place = {};
    $controller('modalController', {
        $scope: $scope
    });

    $scope.listaTipoBusqueda = [{
            nombreTipoBusqueda: "Por Fecha",
            idTipoBusqueda: 1
        },
        {
            nombreTipoBusqueda: "Por Rutas",
            idTipoBusqueda: 2
        }
    ];

    $scope.tipoBusqueda = $scope.listaTipoBusqueda[0];

    $scope.findTiposRecorridos = function() {
        tiposRecorridoService.findTiposRecorridos().success(function(data) {
            $scope.listaTiposRecorrido = data;
            $scope.tipoRecorrido = data[0];
            console.log($scope.tipoRecorrido)
        }).error(function(data) {

        });

    }

    $scope.searchCoordenadas = function(ruta) {
        localizacionService.searchCoordenadas(ruta).success(function(data) {
            $scope.listaCoordenadas = data;
            $scope.marcarCoordenadas($scope.listaCoordenadas);
        }).error(function(data) {

        });
    }

    $scope.marcarCoordenadas = function(listaCoordenadas) {
        gMapsService.init('map');
        angular.forEach(listaCoordenadas, function(value, key) {
            gMapsService.addMarkers(parseFloat(value.latitud), parseFloat(value.longitud));
        })
    }


    $scope.searchRutasCoordenadas = function(placaUsuario) {
    	if ($scope.localizacionForm.$invalid) {
            angular.forEach($scope.localizacionForm.$error, function(field) {
                angular.forEach(field, function(errorField) {
                    errorField.$setDirty();
                })
            });
            return;
        }
        if ($scope.tipoBusqueda.idTipoBusqueda == 1) {
            localizacionService.searchCoordenadasByFecha(placaUsuario, $scope.fInicio, $scope.fFin).success(function(data) {
                $scope.listaCoordenadas = data;
                $scope.marcarCoordenadas($scope.listaCoordenadas);
                $scope.listaRutas = [];
            }).error(function(data) {
                $scope.showAviso(data.message);
                $scope.listaRutas = [];
            });
        } else {

            localizacionService.searchRoutes(placaUsuario, $scope.tipoRecorrido.idTipoRecorrido).success(function(data) {
                $scope.listaRutas = data;
                $scope.ruta = $scope.listaRutas[0];
                $scope.searchCoordenadas($scope.ruta);
            }).error(function(data) {
                $scope.showAviso(data.message);
                $scope.listaRutas = [];
            });
        }
    }

    $scope.findTiposRecorridos();
    gMapsService.init('map');
});