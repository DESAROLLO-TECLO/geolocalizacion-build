angular.module(localizacionGps)
.controller("headerController",
function($rootScope,$scope,$localStorage,$location,$window,$document,$translate,$timeout,
		loginService,storageService,logOutService,jwtService,menuDinamicoService) {
	
	$scope.b_detalleUsuario = false;
	$scope.b_configUsuario	= false;
	
	$scope.logout = function() {
		loginService.logout();
		logOutService.StopTimer();
		$location.path('/login');
	};
	
	nombreUsuario = function() {
		if (storageService.getToken()) {
			return jwtService.getNombreUsuario(storageService.getToken());
		}
		
		return null;
	};
	
	perfilUsuario = function() {
		if (storageService.getToken()) {			
			return jwtService.getPerfilUsuario(storageService.getToken());
		}
		
		return null;
	};
		
	$rootScope.currentDate = function(lang) {
		
		var date = new Date();
 		
		$scope.fechaHoy = "";
		
 		switch(lang) {
			case "es_ES" :
				$scope.fechaActual = moment(date).locale('es').format('dddd DD [de]  MMMM [de] YYYY');
				break;
			case "en_US" :
				$scope.fechaActual = moment(date).locale('en').format('dddd DD [of]  MMMM [of] YYYY');
				break;
			default :
				
		}
	}
	
//	MENUS DETALLE DE USUARIO | CAMBIO TAMAÑOS | CERRAR SESIÓN
	$('.user-menu, .menuopc').click(function(e) {
		 e.stopPropagation();
	});
	
	$document.on('click', function () {
		
		$scope.$apply(function() {
			$scope.b_detalleUsuario = false;
			$scope.b_configUsuario	= false;
		});
		
	});
	
	$scope.mostrarDetalleUsuario = function(b) {
					
		if(b == true)
			$scope.b_detalleUsuario = false;
		else
			$scope.b_detalleUsuario = true;
		
		$scope.b_configUsuario = false;
		
	}
	
	$scope.mostrarConfigUsuario = function(b) {
		
		if(b == true)
			$scope.b_configUsuario = false;
		else
			$scope.b_configUsuario = true;
		
		$scope.b_detalleUsuario = false;
	
	}
		
	// BOTON IR ARRIBA
	$scope.btnUp = function() {
		
		var window	 = angular.element($window);
		var scrollUp = $('.scrollup');
		var page 	 = $('html, body');
		
		window.bind("scroll", function(){
			
			if ($(this).scrollTop() > 300) {
				
				scrollUp.fadeIn();
			
			} else {
				
				scrollUp.fadeOut();
			
			}
		});
	  
		scrollUp.click(function(){
			
			page.animate({ scrollTop: 0 }, 600);
	    	
	    	return false;
	    });
	}
	
	pluginsController = function () {
		// MENU HAMBUERGUESA
		$('.main-nav').on('click', function(){
			$('#menu').toggleClass('mostrar');
		});
		
		if ($(window).width() < 768){
			$('body').on('click', '.treeview-menu', function(){
				$('#menu').removeClass('mostrar');
			});
		}else{
			$('body').on('click', '.menuItem', function(){
				$('#menu').removeClass('mostrar');
			});
		}
	}
	
	initController = function () {
		$scope.pluginsController = pluginsController();
		$scope.btnUp();
		$timeout(function() {
			$rootScope.currentDate($rootScope.currentLanguage);
		});
		$scope.perfilUsuario = perfilUsuario();
		$scope.nombreUsuario = nombreUsuario();
	}
	
	initController();
});