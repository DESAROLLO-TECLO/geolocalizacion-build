angular.module(localizacionGps).config(function($routeProvider, $locationProvider, blockUIConfig) {
	
	$routeProvider.when("/", {
		templateUrl : "login.html",
		controller: "loginController"
	});
	
	$routeProvider.when("/login", {
		templateUrl : "login.html",
		controller: "loginController"
	});
	
	$routeProvider.when("/error", {
		templateUrl : "views/error.html",
	});
	
	$routeProvider.when("/index",{
		templateUrl : "views/index.html",
	});
	
	$routeProvider.when("/accesoNegado", {
		templateUrl : "views/accesoNegado.html",
	});	
	
	
	/*___________________________________________________________
	________** INICIO -> ADMINISTRACIÓN CONTROLLERS ** ________*/
	$routeProvider.when("/adminUsuariosModificaClave",{
		templateUrl : "views/administracion/adminUsuariosModificaClave.html",
		controller : "adminUsuariosModificaClaveController"
	});
	
	$routeProvider.when("/asignacionDispositivos", {
		templateUrl : "views/localizacion/asignacionDispositivos.html",
		controller : "asignacionDispositivosController",
		resolve : {
			data : function(zonasVialesService, jwtService, storageService){
				return zonasVialesService.zonasActivas('h');
			}
		}
	});
	
	$routeProvider.when("/asignacionOficiales", {
		templateUrl : "views/localizacion/asignacionOficiales.html",
		controller : "asignacionOficialesController",
		resolve : {
			data : function(zonasVialesService, jwtService, storageService){
				return zonasVialesService.zonasActivas('o');
			}
		}
	});
	
	/*___________________________________________________________
	________** FIN -> ADMINISTRACIÓN CONTROLLERS ** ___________*/
	
	/*___________________________________________________________
	_______** INICIO -> GRAFICAS Y REPORTES CONTROLLERS ** ______*/
	
	
	$routeProvider.when("/clasificaciongraficas",{
		templateUrl : "views/graficas/clasificaciongraficas.html",
		controller : "clasificacionGraficasController",
	});
	
	$routeProvider.when("/graficaTipoInfraccionDia",{
		templateUrl : "views/graficas/graficatipoinfracciondia.html",
		controller : "graficaTipoInfraccionDiaController",
		resolve : {
			data : function(zonasVialesService, storageService, jwtService){
				return zonasVialesService.zonasActivasPorPlacaSupervisor(jwtService.getPlacaUsuario(storageService.getToken()));
			}
		}
	});
	
	$routeProvider.when("/graficaTipoInfraccionMensual",{
		templateUrl : "views/graficas/graficatipoinfraccionmensual.html",
		controller : "graficaTipoInfraccionMensualController",
		resolve : {
			data : function(zonasVialesService, storageService, jwtService){
				return zonasVialesService.zonasActivasPorPlacaSupervisor(jwtService.getPlacaUsuario(storageService.getToken()));
			}
		}
	});
	
	$routeProvider.when("/graficaTipoInfraccionAnual",{
		templateUrl : "views/graficas/graficatipoinfraccionanual.html",
		controller : "graficaTipoInfraccionAnualController",
		resolve : {
			data : function(zonasVialesService, storageService, jwtService){
				return zonasVialesService.zonasActivasPorPlacaSupervisor(jwtService.getPlacaUsuario(storageService.getToken()));
			}
		}
	});
	
	$routeProvider.when("/clasificacionreportes",{
		templateUrl : "views/reportes/clasificacionreportes.html",
		controller : "clasificacionReportesController",
	});
	
	$routeProvider.when("/reporteGeneralEventos",{
		templateUrl : "views/reportes/reportegeneraleventos.html",
		controller : "reporteGeneralEventosController",
		resolve : {
			data : function(reporteService, storageService, jwtService){
				return reporteService.reporteGeneralData(jwtService.getPlacaUsuario(storageService.getToken()));
			},
			dateTimePicker : function(){
				return {
						minDate: false,
						defaultDate: new Date(),
						maxDate: new Date(),
						showClear: false,
				        format: 'DD/MM/YYYY'
					}
			},
			table : function(){
				return {
					searchSomething : "",
					order : "fechaHoraEvento",
					reverse : true,
					rowsPerPage: 10}
			}
		}
	});
	
	$routeProvider.when("/reporteGeneralEventosOficialesDispositivos",{
		templateUrl : "views/reportes/reportegeneraleventosOficialesDispositivos.html",
		controller : "reporteGeneralEventosOficialesDispositivosController",
		resolve : {
			data : function(reporteService, storageService, jwtService){
				return reporteService.reporteGeneralOficialDispositivoData(jwtService.getPlacaUsuario(storageService.getToken()));
			},
			dateTimePicker : function(){
				return {
						minDate: false,
						defaultDate: new Date(),
						maxDate: new Date(),
						showClear: false,
				        format: 'DD/MM/YYYY'
					}
			},
			table : function(){
				return {
					searchSomething : "",
					order : "fechaHoraEvento",
					reverse : true,
					rowsPerPage: 10}
			}
		}
	});
	/*___________________________________________________________
	________** FIN -> GRAFICAS Y REPORTES CONTROLLERS ** ________*/
	
	$routeProvider.when("/localizacionGps", {
		templateUrl : "views/localizacion/localizacionGps.html",
		controller : "localizacionBusquedaController"

	});
	
	$routeProvider.when("/localizacionRutas", {
		templateUrl : "views/localizacion/localizacionRutas.html",
		controller : "localizacionRutasController"

	});
	
	$routeProvider.when("/altaDispositivos", {
		templateUrl : "views/dispositivosmoviles/altaDispositivosMoviles.html",
		controller : "altaDispositivosMovilesController",
		resolve:{
			data: function(dispositivosMovilesService){
				return dispositivosMovilesService.catalogosAltaDispositivo();
			}
		}
	});
	
	$routeProvider.when("/consultaDispositivos", {
		templateUrl : "views/dispositivosmoviles/consultaDispositivos.html",
		controller : "consultaDispositivosController",
		resolve:{
			lastDeviceSearched : function(){
				return null;
			},
			data: function(dispositivosMovilesService){
				return dispositivosMovilesService.catalogosAltaDispositivo();
			}
		}
	});
	
	$routeProvider.when("/consultaDispositivos/:lastSearch", {
		templateUrl : "views/dispositivosmoviles/consultaDispositivos.html",
		controller : "consultaDispositivosController",
		resolve:{
			lastDeviceSearched : function(dispositivosMovilesService){
				return dispositivosMovilesService.getLastSearch();
			},
			data: function(dispositivosMovilesService){
				return dispositivosMovilesService.catalogosAltaDispositivo();
			}
		}
	});
	
	$routeProvider.when("/editaDispositivo/:dispId", {
		templateUrl : "views/dispositivosmoviles/editaDispositivo.html",
		controller : "editaDispositivoController",
		resolve:{
			dispositivo : function($route){
				return $route.current.params.dispId;
			},
			data: function(dispositivosMovilesService){
				return dispositivosMovilesService.catalogosAltaDispositivo();
			}
		}
	});
	
//	$routeProvider.when("/rutasPorArea", {
//		templateUrl : "views/localizacion/rutasAreaOperativa.html",
//		controller : "rutasAreaOperativaController",
//		resolve : {
//			data : function(localizacionService, jwtService, loginService){
//				return localizacionService.searchAreas(jwtService.getPlacaUsuario(loginService.getToken()));
//			}
//		}
//	});
	
	blockUIConfig.requestFilter = function(config) {
		// var re = new
		// RegExp('\/pagos\/infracciones\/deposito$');
		if (config.url.match(/\/optimizedEventos\/hh$/) || config.url.match(/\/eventos\/item$/) || config.url.match(/\/optimizedEventos\/officers$/)) {
			return false;
		}
	};
	$routeProvider.when("/ubicacion", {
		templateUrl : "views/zonasviales/zonasViales.html",
		controller : "zonasVialesController",
		resolve : {
			data : function(zonasVialesService, jwtService, storageService){
				return zonasVialesService.zonas(jwtService.getPlacaUsuario(storageService.getToken()));
			}
		}
	});
	
	/**
	 * Autor: César Gómez
	 * Módulo: Administración
	 * Sub-módulo: Configuracion Aplicación
	 */
//	Configurar Aplicación
	$routeProvider.when("/configuracion", {
		templateUrl : "views/administracion/configuracionApp.html",
		controller: "configuracionAppController"
    });
	
//	Componentes Web
	$routeProvider.when("/componentesWeb",{
		templateUrl : "views/administracion/resources/pluginsWeb.html",
		controller : "pluginsWebController"
	});
	
	$routeProvider.otherwise({redirectTo: "/index"});
	
});