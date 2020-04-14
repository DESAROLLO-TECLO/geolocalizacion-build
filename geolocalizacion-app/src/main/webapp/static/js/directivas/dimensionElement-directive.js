/*
 * Author: César Gómez
 * Directive: dimensionElement-directive
 * Versión: 1.1.0
 */

angular.module(localizacionGps).directive('detectDimension', function() {
	return {
		restrict : 'A',
		scope : {
			sizeHeight : '=',
			sizeWidth : '='
		},
		link : function(scope, element, attr) {
			
			var $s = scope;
			var $e = element;
			
			$e.bind('DOMSubtreeModified', function() {
				if(($s.sizeHeight || $s.sizeWidth) !== undefined) {
					$s.sizeHeight = $e.height() + "px";
					$s.sizeWidth = $e.width() + "px";
				} else {
					$s.sizeHeight = "";
					$s.sizeWidth = "";
				}
			});
		}
	}
});