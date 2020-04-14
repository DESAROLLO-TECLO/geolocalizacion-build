angular.module(localizacionGps).service("WebServiceSecurityService", function($http,constante/*,config*/) {

	var absUrl = constante.url;
	var protocol = absUrl.protocol+ "//";
	var host = absUrl.host;
	var pathname = absUrl.pathname;
	var contextComplete = protocol+host + pathname;
	 
	this.getServicesAvailable= function(){
		return $http.get(contextComplete + "wsAuthorized/wsInfo");
	};
	
});