angular.module(localizacionGps).directive('addedButton',function(){
	return{
		restrict: 'A',
		template: '',
		link: function(scope, element, attrs) {
			$(element).click(function(){
				alert(":D");
			})
        }
	}
})
