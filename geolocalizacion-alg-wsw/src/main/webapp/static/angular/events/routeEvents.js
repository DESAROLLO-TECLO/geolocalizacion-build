angular.module(localizacionGps).run(function($rootScope, $location, $localStorage, $route) {

	$rootScope.$on("$routeChangeStart", function(event, next, current) {
		  		 
		if(next.$$route.originalPath === "/") {
			
			$location.path('/index');
			
		}
	});
});