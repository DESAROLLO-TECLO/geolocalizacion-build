angular.module(localizacionGps).service("gMapsRutasService", function($q, $http, $window) {

    var currentPosition = {};
    var address = {};
    var directionsService = null;
    var directionsRenderer = null;
    var geocoder = null;
    var infowindow = null;
    var markers = [];



    var cdmx = {
        lat: 19.321908,
        lng: -99.24251699999999
    };
    var gMap = null;

    this.init = function() {
        var options = {
            center: cdmx,
            zoom: 7,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        }
        gMap = new google.maps.Map(document.getElementById('map1'), options);
        this.places = new google.maps.places.PlacesService(gMap)
    }

    this.getCurrentPosition = function(currentPosition) {
        var latLong = currentPosition.split(",");
        this.currentPosition = {
            lat: parseInt(latLong[0]),
            lng: parseInt(latLong[1])
        }

        geocoder = new google.maps.Geocoder;
        return geocodeLatLng(geocoder, gMap, this.currentPosition);
    }



    this.trazarRuta = function(start, end, mode) {
       // deleteMarkers()
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

                //directionsRenderer.setPanel($("#directions_panel").get(0));
                     
                directionsRenderer.setDirections(response);    
            } else {      
                alert("No hay direcciones disponibles entre esos puntos");    
            }  
        });
    }

    function geoCordToDireccion(geocoder, map, marker, latLong) {
        geocoder.geocode({
            'location': latLong
        }, function(results, status) {

            if (status === 'OK') {
                address = results;
                if (results[1]) {
                    map.setZoom(17);
                    // map.setCenter(latLong);
                    marker.addListener('click', function() {
                        console.log(marker)
                        openInfoWindow(gMap, marker, results[1].formatted_address);

                    });

                } else {
                    window.alert('No encontro resultados');
                }

            } else {
                window.alert('La geocordenada fallo: ' + status);
            }
        });
    }

    function geocodeLatLng(geocoder, map, latLong) {
        infowindow = new google.maps.InfoWindow;
        geocoder.geocode({
            'location': latLong
        }, function(results, status) {

            if (status === 'OK') {
                address = results;
                $('#origen').val(address[0].formatted_address);
                if (results[1]) {
                    map.setZoom(17);

                    var marker = new google.maps.Marker({
                        position: latLong,
                        map: map
                    });
                    markers.push(marker);

                    openInfoWindow(gMap, marker, results[1].formatted_address);


                } else {
                    window.alert('No results found');
                }

            } else {
                window.alert('Geocoder failed due to: ' + status);
            }
        });
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

    // Agrega un marker al mapa y la setea en el array
    function addMarker(location) {
        var marker = new google.maps.Marker({
            position: location,
            draggable: true,
            map: gMap,
        });
        markers.push(marker);
        geocoder = new google.maps.Geocoder;
        geoCordToDireccion(geocoder, gMap, marker, location);


    }


    // Setea el mapa con todos los markers array
    this.calcularDistancia = function(origin, destination, mode) {
        service = new google.maps.DistanceMatrixService();

        service.getDistanceMatrix({
                origins: [origin],
                destinations: [destination],
                travelMode: google.maps.DirectionsTravelMode[mode],
                avoidHighways: false,
                avoidTolls: false
            },
            callback
        );


    }

    function callback(response, status) {
        if (status == "OK") {
            console.log('Distancia: ' + response.rows[0].elements[0].distance.text + ', Tiempo Aproximado :' + response.rows[0].elements[0].duration.text)
        } else {
            alert("Error: " + status);
        }
    }


    // Setea el mapa con todos los markers array
    function setMapOnAll(map) {
        for (var i = 0; i < markers.length; i++) {
            markers[i].setMap(map);
        }
    }

    // Elimina los markers del mapa , pero los mantiene en el array.
    function clearMarkers() {
        setMapOnAll(null);
    }

    // Muestra algunos markers recientes del array  
    function showMarkers() {
        setMapOnAll(gMap);
    }

    //Elimina todos los markers del array 
    function deleteMarkers() {
        clearMarkers();
        markers = [];
    }


    //Elimina todos los markers del array 
    this.clearMarkers = function() {
        clearMarkers();
        markers = [];
    }


});