angular.module(localizacionGps)
.config(['$localStorageProvider',
function ($localStorageProvider) {
	
	$localStorageProvider.setKeyPrefix('-');

}])