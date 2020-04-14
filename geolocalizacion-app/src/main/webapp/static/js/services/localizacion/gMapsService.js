angular.module(localizacionGps).service("gMapsService", function($q, $window) {

    var cdmx = {
        lat: 19.321908,
        lng: -99.24251699999999
    };
    var gMap = null;

//    this.init = function(idd) {
//        var options = {
//            center: cdmx,
//            zoom: 7,
//            mapTypeId: google.maps.MapTypeId.ROADMAP
//        }
//        gMap = new google.maps.Map(document.getElementById(idd), options);
//        this.places = new google.maps.places.PlacesService(gMap);
//    }
    
	 this.init = function(idd) {
		 var options = {
			center: cdmx,
	     	zoom: 7,
	     	mapTypeId: google.maps.MapTypeId.ROADMAP
		 }
		 mapInstancesPool.reset();
		 gMap = mapInstancesPool.getInstance(idd,options).map;
		 this.places = new google.maps.places.PlacesService(gMap);
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
   //	            div: div
	        }
    	}
	}
    var markers = [];
    this.trazarRuta = function(start, end, mode, callback) {

         var configDirectionService = {
             origin: start, // Latitud Longitud o String Direccion
             destination: end, // Latitud Longitud o String Direccion
             travelMode: google.maps.DirectionsTravelMode[mode],
             provideRouteAlternatives: true
             //WALKING, TRANSIT, DRIVING
         }

         var configDirectionRenderer = {
             map: gMap,
             suppressMarkers: false
         };

         directionsService = new google.maps.DirectionsService()
         directionsRenderer = new google.maps.DirectionsRenderer(configDirectionRenderer) //Traduce las coordenadas a ruta 
         markers.push(directionsRenderer);

         directionsService.route(configDirectionService, function(response, status) {

             if (status == google.maps.DirectionsStatus.OK) {     
                 directionsRenderer.setMap(gMap);
                 gMap.setZoom(17);
                 directionsRenderer.setDirections(response);
                 callback("good to go")
             } else {      
            	 callback(status);    
             }  
             
         });
     }

    this.search = function(str) {
        var d = $q.defer();
        this.places.textSearch({
            query: str
        }, function(results, status) {
            if (status == 'OK') {
                d.resolve(results[0]);
            } else d.reject(status);
        });
        return d.promise;
    }

    this.addMarker = function(res) {
        if (this.marker) this.marker.setMap(null);
        this.marker = new google.maps.Marker({
            map: gMap,
            position: res.geometry.location,
            animation: google.maps.Animation.DROP
        });
        gMap.setCenter(res.geometry.location);
    }

    this.addMarkers = function(latitud, longitud) {

        var coordenadas = {
            lat: latitud,
            lng: longitud
        };

        var icon = {
        	url: "static/dist/img/location-black.png", // url
            scaledSize: new google.maps.Size(40, 40), // scaled size
            origin: new google.maps.Point(0, 0), // origin
            anchor: new google.maps.Point(0, 0) // anchor
        };

        var marker = new google.maps.Marker({
            position: coordenadas,
            map: gMap,
            draggable: true,
            //icon : icon,
            animation: google.maps.Animation.DROP
        });

        marker.addListener('click', function() {
            var geocoder = new google.maps.Geocoder;

            geocoder.geocode({
                'location': coordenadas
            }, function(results, status) {
                if (status === 'OK')
                    address = results;
                openInfoWindow(gMap, marker, results[1].formatted_address);
            });
        });

//        gMap.setZoom(29);
        gMap.setCenter(coordenadas);

    }


    function openInfoWindow(map, marker, content) {
        var infoWindow = new google.maps.InfoWindow;
        var markerLatLng = marker.getPosition();

        infoWindow.setContent([
            '<div style="text-align:center;">',
            content,
            '</b><br/>Latitud:',
            markerLatLng.lat(),
            '<br/>Latitud:',
            markerLatLng.lng()
        ].join(''));
        infoWindow.open(map, marker);
    }

});