angular.module(localizacionGps).config(function($routeProvider, $locationProvider) {
	
	$routeProvider.when("/", {
		templateUrl : "index.html",
		//controller: "loginController"
	});
	
	$routeProvider.when("/servicios", {
		templateUrl : "views/webServiceDisplay.html",
		controller: "WebServiceDisplayController",
		resolve:{ 
			wsData : function(WebServiceSecurityService){
				return WebServiceSecurityService.getServicesAvailable();
			}
		}
	});
	
	$routeProvider.otherwise({redirectTo: "/servicios"});
});