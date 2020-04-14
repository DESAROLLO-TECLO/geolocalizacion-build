angular.module(localizacionGps).directive('fecha', function() {
    return {
        require: 'ngModel',
        scope: {
        	mindate: '=',
        	maxdate: '=',
        	elemento: '=' 
         },
        link: function(scope, el, attr, ngModel) {
            $(el).datepicker({
                format: 'dd/mm/yyyy',
                autoclose: true,
                todayHighlight: true,
                todayBtn: "linked" 
            }).on('changeDate', function (selected) {
            	
            	if(scope.maxdate != undefined){
    	        	$('#'+scope.elemento).datepicker('setEndDate', scope.maxdate);
            	}
            	
            	if(scope.mindate != undefined){
    	        	$('#'+scope.elemento).datepicker('setStartDate', scope.mindate);
            	}
	        	
	    	});
        }
    }
});