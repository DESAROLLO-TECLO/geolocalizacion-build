angular.module(localizacionGps).constant("constante", {
	urlWs : "/sspcdmxsupervisores_v100r6_qa_wsw"
});

angular.module(localizacionGps).factory(
		'config',
		[ '$http', '$location', 'constante', '$rootScope',
				function($http, $location, constante, $rootScope) {

					var protocol = $location.protocol() + "://";
					var host = location.host;
					var url = protocol + host + constante.urlWs;
					var absUrl = $location.absUrl();

					let
					contextApp = absUrl.split("/")[3];

					return {
						baseUrl : url,
						absUrl : absUrl,
						contextApp : contextApp
					}
				} 
		]);
