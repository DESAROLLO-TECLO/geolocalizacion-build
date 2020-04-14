angular.module(localizacionGps).controller('localizacionRutasController', function($scope, $filter, gMapsRutasService, $controller, tiposRecorridoService, ModalService) {
	$scope.param= {tiempoDistancia : null};
	
    
    var autoCompInic = new google.maps.places.Autocomplete(document.getElementById('origen'));
    autoCompInic.setComponentRestrictions({'country': ['mx']});
    var autoCompFin = new google.maps.places.Autocomplete(document.getElementById('destino'));
    autoCompFin.setComponentRestrictions({'country': ['mx']});
    var autoCompPoint = new google.maps.places.Autocomplete(document.getElementById('nuevoPunto'));
    autoCompPoint.setComponentRestrictions({'country': ['mx']});
	
    google.maps.event.addListener(autoCompInic, 'place_changed', function() {
    	$scope.$apply(function(){
    		$scope.param.origen = $("#origen").val();
    		return false;
    	})
    });
    
    google.maps.event.addListener(autoCompFin, 'place_changed', function() {
    	$scope.$apply(function(){
    		$scope.param.destino = $("#destino").val();
    		return false;
    	})
    });
    
	$scope.puntos = [];
	$scope.puntosGeoCoder = new google.maps.Geocoder();
    google.maps.event.addListener(autoCompPoint, 'place_changed', function() {
    	$scope.$apply(function(){
    		if($scope.puntos.length < 23){
    			$scope.puntos.push({"descripcion":$("#nuevoPunto").val()});
    			$scope.resetPoints();
    		}
    		else{
    			alert("No se puede tener más de 23 paradas");
    		}
    		return false;
    	})
    });
    
	$scope.clearPoints = function(){
		$scope.puntos = [];
	}
	
	$scope.clearPoint = function(index){
		$scope.puntos.splice(index, 1);
	}
	
	$scope.resetPoints = function(){
		$scope.nuevoPunto = angular.copy($scope.nuevoPuntoLimpio);
		$scope.stopsForm.$setPristine();
	}
	
	$scope.calculateAndDisplayRoute = function(){
		   $scope.initMap('map1');
		   var waypnts = [];
		   for(var x in $scope.puntos)
			   waypnts.push({
			   location : $scope.puntos[x].descripcion,
			   stopover : true
		   })
				   
			   
		   var start = $scope.param.origen;//new google.maps.LatLng(waypnts[0].location.lat, waypnts[0].location.lng);
		   var end = $scope.param.destino;//new google.maps.LatLng(waypnts[waypnts.length-1].location.lat, waypnts[waypnts.length-1].location.lng);
		   
		   $scope.directionsService = new google.maps.DirectionsService;
		   $scope.directionsDisplay = new google.maps.DirectionsRenderer;
		   $scope.directionsDisplay.setMap($scope.gMap);
	       
	       $scope.directionsService.route({
	           origin: start,
	           destination: end,
	           waypoints: waypnts,
//		           optimizeWaypoints: true,
	           travelMode: $scope.param.tipoRecorrido.cdTipoRecorrido//'DRIVING'
	         }, function(response, status) {
	         if (status === 'OK') {
	        	 $scope.directionsDisplay.setDirections(response);
	        	 $scope.$apply(function(){
	        	
//	        	 var summaryPanel = document.getElementById('directions-panel');
	        	 var route = response.routes[0];
	        	 $scope.points = [];
//	        	 summaryPanel.innerHTML = '';
	        	 for (var i = 0; i < route.legs.length; i++) {
        	        var routeSegment = i + 1;
        	        $scope.points.push({
        	        	"beg" : route.legs[i].start_address,
        	        	"end" : route.legs[i].end_address,
        	        	"dist" : route.legs[i].distance.text,
        	        	"time" : route.legs[i].duration.text
        	        })
//        	        summaryPanel.innerHTML += '<b>Segmento de ruta ' + routeSegment + '</b><br>';
//        	        summaryPanel.innerHTML += route.legs[i].start_address + ' a ';
//        	        summaryPanel.innerHTML += route.legs[i].end_address + '<br>';
//        	        summaryPanel.innerHTML += 'Distancia: '+route.legs[i].distance.text + '<br>';
//        	        summaryPanel.innerHTML += 'Tiempo: '+route.legs[i].duration.text + '<br><br>';
	        	 }
	        	 })
	         } else {
	        	 window.alert('Directions request failed due to ' + status);
	         }
	         });
		}
	
	var mapInstancesPool = {
    	pool: [],
    	used: 0,
    	getInstance: function(idd, options){
	    	if(mapInstancesPool.used >= mapInstancesPool.pool.length){
	    		mapInstancesPool.used++;
	    		mapInstancesPool.pool.push (mapInstancesPool.createNewInstance(idd, options));
	    	} else { 
	    	    mapInstancesPool.used++;
	    	}
	    	return mapInstancesPool.pool[mapInstancesPool.used-1];
    	},
    	reset: function(){
    		mapInstancesPool.used = 0;
    		mapInstancesPool.pool = [];
    	},
    	createNewInstance: function(idd, options){
    		var map = new google.maps.Map(document.getElementById(idd), options);
	        return {
	            map: map
	        }
    	}
	}
	
	$scope.initMap = function(idd) {
		 var options = {
			center: cdmx,
	     	zoom: 7,
	     	mapTypeId: google.maps.MapTypeId.ROADMAP,
	     	gestureHandling:'greedy'
		 }
		 mapInstancesPool.reset();
		 $scope.gMap = mapInstancesPool.getInstance(idd,options).map;
		 this.places = new google.maps.places.PlacesService($scope.gMap);
	}
	
	var cdmx = {
	        lat: 19.321908,
	        lng: -99.24251699999999
	    };
	$scope.gMap = null;
	$scope.initMap('map1');
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	$controller('modalController', {
        $scope: $scope
    });
	
	
	$scope.initMap = function(idd) {
		 var options = {
			center: cdmx,
	     	zoom: 7,
	     	mapTypeId: google.maps.MapTypeId.ROADMAP,
	     	gestureHandling:'greedy'
		 }
		 mapInstancesPool.reset();
		 $scope.gMap = mapInstancesPool.getInstance(idd,options).map;
		 this.places = new google.maps.places.PlacesService($scope.gMap);
	}
	
	
	
	
    var currentPosition = {};
    
    function initialize() {
        $scope.getPosition();
    }
    
    
    $scope.getCurrentPosition = function() {

        geocoder = new google.maps.Geocoder;
       
        return geocodeLatLng(geocoder, gMap, currentPosition);
    }
    
    function geocodeLatLng(geocoder, map, latLong) {
        infowindow = new google.maps.InfoWindow;
        geocoder.geocode({'location': latLong }, function(results, status) {
    		
             if (status === 'OK') {
                address = results;
                $('#start').val(address[0].formatted_address);
                if (results[1]) {
                    map.setZoom(14);

                    var marker = new google.maps.Marker({
                        position: latLong,
                        map: map
                    });
    				    markers.push(marker);
    				 
                        openInfoWindow(gMap,marker,results[1].formatted_address);


                } else {
                    window.alert('No results found');
                }

            } else {
                window.alert('Geocoder failed due to: ' + status);
            }
        });
    }



    $scope.findTiposRecorridos = function() {
        tiposRecorridoService.findTiposRecorridos().success(function(data) {
            $scope.listaTiposRecorrido = data;
            $scope.tipoRecorrido = data[0];
            $scope.param.tipoRecorrido = data[0];
        }).error(function(data) {

        });
    }
 
//    $scope.trazarRuta = function(param) {
//        gMapsRutasService.trazarRuta(param.origen, param.destino, "DRIVING");
//        gMapsRutasService.calcularDistancia(param.origen, param.destino, "DRIVING");
//        $scope.calcularDistancia(param.origen,param.destino,"DRIVING");
//    }
//
//    $scope.init = function() {
//        gMapsRutasService.init();
//
//    }

    $scope.calcularDistancia = function(origin,destination,mode) {
        service = new google.maps.DistanceMatrixService();
        
        service.getDistanceMatrix(
            {
                origins: [origin],
                destinations: [destination],
                travelMode: google.maps.DirectionsTravelMode[mode],
                avoidHighways: false,
                avoidTolls: false
            }, 
            callback
        );
        function callback(response, status) {
            
            
            if(status=="OK") {
//            	$scope.param.tiempoDistancia = 'Distancia: ' + response.rows[0].elements[0].distance.text  + ', Tiempo Aproximado :' + response.rows[0].elements[0].duration.text;
            	 $('#distancia').val('Distancia: ' + response.rows[0].elements[0].distance.text  + ', Tiempo Aproximado :' + response.rows[0].elements[0].duration.text);
             } else {
                alert("Error: " + status);
            }
        }
       
}

    $scope.findTiposRecorridos();
    //$scope.init();
    //initialize();
});