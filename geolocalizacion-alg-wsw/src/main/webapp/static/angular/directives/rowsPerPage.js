/*
 * Author: Jesús Gutierrez
 * Directive: rowsperpage
 * Versión: 1.1.0
 */

angular.module(localizacionGps).directive('rowsPerPage', function ($rootScope) {
    return {
        restrict: 'E',
        templateUrl: 'views/viewTemplates/rowsperpage.html',
        scope: {
        	rows: '=',
            filtro: '=',
            array: '='
        },
        controller: function ($scope, $rootScope) { 
        	
            $scope.rowsx = function(){
        		var array = [5, 10, 20, 50, 100];
        		
        		if(!angular.isDefined($scope.array)){return;}
        		
        		var rows = $scope.array.length;
        		
        		var mayorItems = 0;
        		
        		var currentLang = $rootScope.currentLanguage;
        		for(var i = 0; i < array.length; i++){
        			if(array[i] < rows){
        				$scope.customarray.push({"legend" : array[i] , "rows" : array[i]});
        			}else{
        				mayorItems += 1;
        				if(mayorItems < 2) {
        					
        					$scope.customarray.push({"legend" : currentLang == 'es_ES' ? 'Todos' : 'All' , "rows" : rows});
        					
        					$rootScope.$watch('currentLanguage', function(newLang) {
        						var customArray = $scope.customarray;
	        					for(var e = 0; e < customArray.length ; e++) {
	        						if(customArray[e].legend == 'Todos' || customArray[e].legend == 'All') {
	        							var lastElement = customArray.length-1;
	        							var elementDelete = customArray.splice(lastElement, 1);
	        							customArray.push({"legend" : newLang == 'es_ES' ? 'Todos' : 'All' , "rows" : rows});
	        						}
	        					}
        					});
        				}
        			}
        		}
        		
        		if(mayorItems == 0){
        			
        			$scope.customarray.push({"legend" : currentLang == 'es_ES' ? 'Todos' : 'All' , "rows" : rows});
        			
        			$rootScope.$watch('currentLanguage', function(newLang) {
        				var customArray = $scope.customarray;
    					for(var e = 0; e < customArray.length ; e++) {
    						if(customArray[e].legend == 'Todos' || customArray[e].legend == 'All') {
    							var lastElement = customArray.length-1;
    							var elementDelete = customArray.splice(lastElement, 1);
    							customArray.push({"legend" : newLang == 'es_ES' ? 'Todos' : 'All' , "rows" : rows});
    						}
    					}
					});
        		}
        		
        		$scope.rowsPerPage = $scope.customarray[0].rows;
        		$scope.rows = $scope.rowsPerPage;
        	}

            $scope.select = function(i, option) {
            	$scope.selectedIndex = i;
        		$scope.rowsPerPage = option.rows;
        		$scope.rows = $scope.rowsPerPage;
            } 
            
            $scope.selectwithvalue = function(customarray, value){
            	for(var i = 0; i < customarray.length; i++){
            		if(customarray[i].rows == value){
            			$scope.selectedIndex = i;
            			break;
            		}
            	}
            }
            
            $scope.$watch('array', function(newValue, oldValue) {
            	$scope.selectedIndex = 0;
            	$scope.customarray = [];
            	
            	$scope.rowsx();
            	
            }, true);
        } 
    }
});