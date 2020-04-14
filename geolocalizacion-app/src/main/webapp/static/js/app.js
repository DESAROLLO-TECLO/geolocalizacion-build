
//Nombre de la aplicación para control con Angular
var localizacionGps = "localizacionGps";

angular.module(localizacionGps,
	[
		'ngRoute',
		'ngStorage',
		'ngCookies',
		'ngResource',
		'angular-jwt',
		'ng.deviceDetector',
		'ngAnimate',
		'blockUI',
		'myUpload',
		'angularUtils.directives.dirPagination',
		'angularModalService',
		'checklist-model',
		'pascalprecht.translate',
		'ui.bootstrap',
		'angular-responsive',
		'FSAngular',
		'ae-datetimepicker',
		'ui.select2',
		'switcher',
		'mgo-angular-wizard',
		'angular-growl',
		'dndLists',
		'chart.js',
		'autoCompleteInput'
	]
);
