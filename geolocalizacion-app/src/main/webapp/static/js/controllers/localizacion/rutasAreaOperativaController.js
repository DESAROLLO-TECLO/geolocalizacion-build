angular.module(localizacionGps).controller('rutasAreaOperativaController', 
		function($scope, $filter, $timeout, gMapsAreaOperativaService, $controller, localizacionService, tiposRecorridoService, ModalService, data, $q, $window,  $compile) {
	
	$scope.newPlacas = [];
	$scope.searchMapButtons = {
			"poligonVisible" : false,
			"routVisible" : false
	}
	$scope.toggleFlag="V";
	$scope.toggleSearch = function(){
		$scope.toggleFlag = $scope.toggleFlag == "V"?"H":"V";
	}
	$scope.init = function(){
		$scope.areas = data.data;
		$scope.oficiales = [];
		$scope.fecha = moment().format('L');
		$scope.fechaCons = angular.copy($scope.fecha);
		for(var x in $scope.areas){
			$scope.areas[x].placas = [];
		}
	}
	$scope.init();
	
	$scope.addPlacasByArea = function(){
		for(var x in $scope.newOficiales){
			$('.x').find('[value='+$scope.newOficiales[x].empPlaca+']').remove();
		}
		$scope.newOficiales = [];
		
		
		for(var x in $scope.areasOperativas){
			found = false;
			for(var y in $scope.areas){
				if($scope.areasOperativas[x] == $scope.areas[y].areaOperativaId){
					for(var z in $scope.areas[y].empleados){
						oficialN = $scope.areas[y].areaOperativaNombre + " - " + $scope.areas[y].empleados[z].empNombre;
						$scope.newOficiales.push(angular.copy($scope.areas[x].empleados[z]));
						$scope.newOficiales[$scope.newOficiales.length-1].empNombre = oficialN;
					}
					found = true;
					break;
				}
			}	
		}
		$('.x').selectpicker('refresh');

	}
	
	$scope.toogleAllOfficers = function(i){
		for(var x in $scope.areas[i].empleados){
			$scope.areas[i].empleados[x].checked = $scope.areas[i].allOfficers;
		}
	}
	
	$scope.toogleSingle = function(i){
		broken = false;
		for(var x in $scope.areas[i].empleados){
			if(!$scope.areas[i].empleados[x].checked){
				$scope.areas[i].allOfficers = false;
				broken = true;
				break;
			}
		}
		if(!broken){
			$scope.areas[i].allOfficers = true;
		}
	}
	

	$scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
		$('.selectpicker').selectpicker();
		$('.selectpicker').selectpicker('refresh');
	});
	
	$scope.rutax = function(){
		$scope.fechaCons = angular.copy($scope.fecha);
		if($scope.rutasForm.$invalid) {
			angular.forEach($scope.rutasForm.$error, function (field) {
				angular.forEach(field, function(errorField){
            		errorField.$setDirty();
            	})
            });
			return;
        }

		var placasString = "";
		$scope.newPlacas = [];
		for(var x in $scope.areas){
			for (var y in $scope.areas[x].empleados){
				if($scope.areas[x].empleados[y].checked)
					$scope.newPlacas.push($scope.areas[x].empleados[y].empPlaca)
			}
		}
		if($scope.newPlacas.length == 0){
			$scope.showError("No se ha seleccionado al menos una placa")
			return;
		}
		for(var x in $scope.newPlacas){
			placasString += $scope.newPlacas[x];
			if(parseInt(x)+1<$scope.newPlacas.length)
				placasString+=","
		}
		localizacionService.searchRutas(placasString, $scope.fecha)
		.success(function(data){
			$scope.initMap('map2');
			$scope.rutass = data;
			$scope.marcarCoordenadas("principal",null);
		})
		.error(function(data){
			$scope.showError("No se encontaron registros")
			$scope.rutass = [];
		})
	}
	
	
	$scope.clearRoutes = function(route,hideFirsMarker){
		for(var x in route.renderers){
			route.renderers[x].setMap(null);
			
		}
		for(var x in route.allMarkers){
			route.allMarkers[x].setMap(null);
		}
		if(route.road!=undefined)
			route.road.setMap(null);
		hideFirsMarker ? route.firstMarker[0].setMap(null) : route.firstMarker[0].setMap($scope.gMap);
		if( route.firstInfoWindow != null && route.firstInfoWindow != undefined)
			route.firstInfoWindow.close();
	}
	
	
	$scope.marcarCoordenadas = function(kind, id) { //kind -> principal, delete, polygon, route
		if(kind=='delete'){
			angular.forEach($scope.rutass, function(value, key){
				if(id==value.empleado.empId){
					$scope.clearRoutes(value,false);
					return;
				}
			})
		}
		else if(kind=='principal'){
			angular.forEach($scope.rutass, function(value, key){
				content = {
					lat : parseFloat(value.coordenadas[value.coordenadas.length-1].latitud),
					lng : parseFloat(value.coordenadas[value.coordenadas.length-1].longitud),
					numSerie: value.coordenadas[value.coordenadas.length-1].numSerie,
					principalInfoWindow : true,
					shownRoute : false,
					oficial : value.empleado,
					indicador : value.indicador,
					fechaCoordenada : value.coordenadas[value.coordenadas.length-1].fechaCoordenada,
					coordenadas : value.coordenadas,
					index : -1
				}
				value.shownRoute = false;
				value.firstMarker = [];
				value.firstMarker.push($scope.addMarkers(content,-1));
			})
		}
		else{
			var x = 0;
			for(x in $scope.rutass){
				if(id==$scope.rutass[x].empleado.empId){
					$scope.clearRoutes($scope.rutass[x],true);
					$scope.rutass[x].allMarkers = [];
					$scope.rutass[x].shownRoute = true;
					break;
				}
			}
			contents = [];
			for(var y in $scope.rutass[x].coordenadas){
				if(parseInt(y)+1 == $scope.rutass[x].coordenadas.length){
					content ={
						lat : parseFloat($scope.rutass[x].coordenadas[y].latitud),
						lng : parseFloat($scope.rutass[x].coordenadas[y].longitud),
						numSerie : $scope.rutass[x].coordenadas[y].numSerie,
						principalInfoWindow : true,
						shownRoute : $scope.rutass[x].shownRoute,
						oficial : $scope.rutass[x].empleado,
						indicador : $scope.rutass[x].indicador,
						fechaCoordenada : $scope.rutass[x].coordenadas[y].fechaCoordenada,
						coordenadas : $scope.rutass[x].coordenadas,
						index : y
					}
				}else{
					content ={
						lat : parseFloat($scope.rutass[x].coordenadas[y].latitud),
						lng : parseFloat($scope.rutass[x].coordenadas[y].longitud),
						numSerie : $scope.rutass[x].coordenadas[y].numSerie,
						principalInfoWindow : false,
						oficial : $scope.rutass[x].empleado,
						indicador : $scope.rutass[x].indicador,
						fechaCoordenada : $scope.rutass[x].coordenadas[y].fechaCoordenada,
						startPoint : false,
						index : y
					}
					if(y==0)content.startPoint = true;
				}
				$scope.rutass[x].allMarkers.push($scope.addMarkers(content, y));
				contents.push(content);
		    }
			
		    if(kind == 'route')
		    	$scope.routePrototype2(contents, id);
		    else{
		    	$scope.rutass[x].road = new google.maps.Polyline({
		        path: contents,
		        geodesic: false,
		        strokeColor: '#'+Math.floor(Math.random()*16777215).toString(16),
		        strokeOpacity: 1.0,
		        strokeWeight: 2
		        });
		    	$scope.rutass[x].road.setMap($scope.gMap);
		    	var bounds = new google.maps.LatLngBounds();
		        var points = $scope.rutass[x].road.getPath().getArray();
		        for (var n = 0; n < points.length ; n++){
		            bounds.extend(points[n]);
		        }
		        $scope.gMap.fitBounds(bounds);
		    }
		    
		}
	}
	
	$scope.showError = function(messageTo) {
    	ModalService.showModal({
    		templateUrl: 'views/templatemodal/templateModalError.html',
    		controller: 'mensajeModalController',
    		inputs:{ message: messageTo}
        }).then(function(modal) {
        	modal.element.modal();
        });
    };
	
	
	/*
	 * 
	 * 
	 * Inicia logica de mapa
	 * 
	 * 
	 */
	
	var cdmx = {
        lat: 19.321908,
        lng: -99.24251699999999
    };
    $scope.gMap = null;

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
	 
//	 var markers = [];
	 $scope.addMarkers = function(content) {
		 if(content.startPoint){
			 for(x in $scope.rutass){
				 if(content.oficial.empId == $scope.rutass[x].empleado.empId){
					 if(content.startPoint){
						 $scope.rutass[x].infoWindows = [];
					 }
				 }
			 }
		 }
	 
        var coordenadas = {
            lat: content.lat,
            lng: content.lng
        };
        
        if(content.principalInfoWindow){
        	var icon = {
//        		path: 'M12,16c0,1.1,1.8,2,4,2s4-0.9,4-2c4.4,0,8,5.4,8,12H4C4,21.4,7.6,16,12,16z'
//		   			+'M14.9,14.9c-1.9-0.5-3.4-2-3.9-3.9c-0.4-1.9,0.1-3.8,1.5-5.1l0.3-0.3c1.7-1.3,4-1.6,5.4-1 c1.7,0.8,3.1,2.3,3.3,4.2l-0.1-0.1c0.4,1.9-0.2,4-1.7,5.2c-1.5,1.2-3.5,1.6-5.3,0.9',
        		path : 'M3.8,4.5c2.3-0.6,4.5-1.2,6.7-1.8c1.2-0.3,2.4-0.6,3.5-1c0.5-0.1,0.9-0.1,1.3,0'+
        		'c2.1,0.6,4.2,1.1,6.3,1.7c1.2,0.3,2.5,0.7,3.7,1c-0.5,0.9-1,1.8-1.4,2.7c-0.1,0.2-0.1,0.5-0.2,0.7c0,0.2,0,0.4,0,0.6'+
        		'c0.1,0.8-0.3,1.3-0.9,1.6c-0.6,0.3-0.7,0.7-0.7,1.2c0,0.9,0.1,1.8-0.3,2.6c-1,2.6-2.8,4.2-5.6,4.8c-2.1,0.5-4.1,0.1-5.9-1'+
        		'c-1.3-0.9-2.3-2-2.9-3.5c-0.4-1-0.5-2.1-0.5-3.1c0-0.1,0-0.1,0.1-0.2c0-0.3,0-0.4-0.3-0.6c-0.5-0.2-1-0.5-1.2-1'+
        		'C5.4,9.1,5.4,8.9,5.4,8.6c0.1-0.8-0.1-1.5-0.5-2.2C4.5,5.9,4.2,5.2,3.8,4.5z M19,11.6c-0.1,0-0.3-0.1-0.5-0.1c-1,0.1-2,0.2-3,0.3'+
        		'c-1,0-2,0-3-0.1c-0.3,0-0.6-0.1-0.9-0.1c-0.5-0.1-1-0.1-1.5-0.2c-0.1,0.4-0.1,1.2,0.3,2.1c0.1,0.3,0.5,1.3,1.6,1.9'+
        		'c0.8,0.5,1.7,0.5,2.2,0.6c0.3,0,0.6,0,1.1,0c0.7-0.1,1.4-0.4,2-0.9c0.7-0.6,1.1-1.1,1.2-1.3c0.3-0.4,0.4-0.8,0.4-0.8'+
        		'C19.1,12.4,19,11.9,19,11.6z M17.6,4.6c-1,0-2.1,0-3.1,0c-1,0-1.9,0-2.9,0c-0.2,0.5,0,1.5,0,2.6c0,0.1,0.1,0.2,0.2,0.2'+
        		'c0.9,0.4,1.8,0.9,2.7,1.3c0.1,0,0.2,0,0.3,0c0.9-0.4,1.8-0.8,2.7-1.3c0.1,0,0.2-0.2,0.2-0.2C17.6,6.3,17.6,5.5,17.6,4.6z'+
        		'M26.5,27.8c-0.1-0.5-0.4-1.9-1.3-3.5c-0.7-1.1-1.5-2.1-2.6-3c-0.5-0.5-1.1-0.9-1.7-1.2'+
        		'c-1.4-0.8-1.9-1.2-2.2-1c-0.1,0.1-0.1,0.1-0.3,0.4c-0.1,0.1-0.3,0.4-0.7,0.7c-0.1,0.1-0.4,0.3-1,0.4c-0.8,0.2-1.5,0.3-2,0.3'+
        		'c-0.5,0-1.1,0-1.9-0.1c-0.6-0.1-1-0.3-1.1-0.3c-0.2-0.1-0.6-0.3-1-0.6c-0.3-0.3-0.3-0.5-0.5-0.6c-0.3-0.1-0.7,0.1-1.6,0.7'+
        		'c-1.8,1.2-2.2,1.5-2.4,1.7c-0.3,0.3-0.6,0.6-0.9,0.9c-1.3,1.5-2.1,3.2-2.6,5c-0.1,0.6-0.1,1.3-0.2,2c0,0.1,0,1.1,0.1,1.4'+
        		'c0.2,1.7,0.7,3.4,1.5,5c1.6,3.5,3.7,6.6,6,9.6c1.2,1.6,2.6,3.1,3.9,4.6c0.2,0.2,0.3,0.3,0.5,0.5c0,0,0,0,0.1,0c0.1,0,0.2,0,0.3,0'+
        		'c0.5-0.6,1.1-1.1,1.6-1.7c1.5-1.9,3.1-3.8,4.5-5.7c1.2-1.6,3.9-5.6,5.1-9.3c0.4-1.3,0.6-2.3,0.7-3.3C26.7,29.9,26.7,29,26.5,27.8z'+
        		'M14.6,35.8c-1.2,0-2.3-0.3-3.2-0.8c-0.7-0.4-1.2-0.8-1.4-1.1c-0.3-0.3-0.7-0.8-1.1-1.5c-0.6-1.1-0.6-2.2-0.6-2.6'+
        		'c0-1.6,0.7-2.8,1-3.2c0.3-0.4,1.2-1.6,2.9-2.3c1.1-0.4,2.1-0.5,2.7-0.4c0.5,0,2.3,0.2,3.9,1.5c0.2,0.2,1.1,1,1.6,2.2'+
        		'c0.4,1.1,0.5,1.9,0.5,2.2c0,0.4,0,1.7-0.9,3c-0.6,1-1.4,1.6-1.7,1.8C16.7,35.7,15,35.8,14.6,35.8z',


		        fillOpacity: 0.8,
		        scale: 1,
		        width :100, 
		        fillColor: content.indicador.indicadorCodigo,
		        strokeColor: content.indicador.indicadorCodigo,
		        strokeWeight: 2,
		        scaledSize: new google.maps.Size(20, 20), // scaled size
		        origin: new google.maps.Point(0, 0), // origin
		        anchor: new google.maps.Point(14, 50)
//		        anchor: new google.maps.Point(14, 24)
		   };
        	   
    	   var marker = new google.maps.Marker({
    		   position: coordenadas,
    		   map: $scope.gMap,
    		   draggable: false,
    		   icon : icon,
    		   animation: google.maps.Animation.DROP
    	   });
        }else
        if(content.startPoint){
            
            
        	var icon = {
    		        path: 'M24.5,1c7.3,0,13.1,5.8,13.1,13c0,9.8-13.1,24.2-13.1,24.2S11.4,23.8,11.4,14C11.4,6.8,17.3,1,24.5,1 M24.5,9.8'+
    		        'c-2.4,0-4.4,2-4.4,4.4s2,4.4,4.4,4.4s4.4-2,4.4-4.4S26.9,9.8,24.5,9.8 M42,38.2c0,4.8-7.8,8.8-17.5,8.8S7,43.1,7,38.2'+
    		        'c0-2.8,2.7-5.3,6.8-6.9l1.4,2c-2.4,1-3.8,2.3-3.8,3.9c0,3,5.9,5.5,13.1,5.5s13.1-2.5,13.1-5.5c0-1.5-1.5-2.9-3.8-3.9l1.4-2'+
    		        'C39.4,32.9,42,35.4,42,38.2z',
            		fillOpacity: 1,
    		        scale: 1,
    		        width :100, 
    		        fillColor: '#2E2EFE',//content.indicador.indicadorCodigo,
    		        strokeColor: content.indicador.indicadorCodigo,
    		        strokeWeight: 2,
    		        scaledSize: new google.maps.Size(40, 40), // scaled size
    		        origin: new google.maps.Point(0, 0), // origin
    		        anchor: new google.maps.Point(25, 38)
    		   };
            var marker = new google.maps.Marker({
                position: coordenadas,
                map: $scope.gMap,
                draggable: false,
                icon : icon,
                animation: google.maps.Animation.DROP
            });
        }else{
        	var marker = new google.maps.Marker({
	            position: coordenadas,
	            map: $scope.gMap,
	            draggable: false,
	            animation: google.maps.Animation.DROP
	        });
        }

        google.maps.event.addListener(marker, 'click', (function (marker) {
            return function () {
            	var geocoder = new google.maps.Geocoder;
            	
	            geocoder.geocode({
	                'location': coordenadas
	            }, function(results, status) {
	                if (status === 'OK')
	                    address = results;
	                content.direccion = results[1].formatted_address
	                if(content.principalInfoWindow)
	                	$scope.openLastPointInfoWindow($scope.gMap, marker, content)
	                else
	                	$scope.openInfoWindow($scope.gMap, marker, content);
	            });
            }
        })(marker));
        
        $scope.gMap.setZoom(content.principalWindow ? 14 : 11);
        $scope.gMap.setCenter(coordenadas);
        return marker;
    }

	 $scope.openInfoWindow = function(map, marker, content) {
		 var x = 0;
		 for(x in $scope.rutass){
			 if(content.oficial.empId == $scope.rutass[x].empleado.empId){
				 if($scope.rutass[x].infoWindows[content.index] == null || $scope.rutass[x].infoWindows[content.index] == undefined){
					 $scope.rutass[x].infoWindows[content.index] = new google.maps.InfoWindow;
				 }else{
					 if(!isInfoWindowOpen($scope.rutass[x].infoWindows[content.index]))
						 $scope.rutass[x].infoWindows[content.index].open(map, marker);
					return;
				 }
				 break;
			 }
		 }
//		 var infoWindow = new google.maps.InfoWindow;
		 var markerLatLng = marker.getPosition();
        
		 $scope.rutass[x].infoWindows[content.index].setContent([
            '<div style="text-align:center;">',
            content.direccion,
            '</strong></b><br/>Actualización: ',
            moment(content.fechaCoordenada).format('DD/MM/YYYY hh:mm A'),
            '</b><br/>Latitud:',
            markerLatLng.lat(),
            '<br/>Longitud:',
            markerLatLng.lng(),
            '<br/>Num. Serie:',
            content.numSerie
            ].join(''));
		 $scope.rutass[x].infoWindows[content.index].open(map, marker);
	}
	    
   $scope.openLastPointInfoWindow =  function (map, marker, content) {
	   var x = 0;
	 	
	   for(x in $scope.rutass){
		   if(content.oficial.empId == $scope.rutass[x].empleado.empId){
				if(content.index <= -1){//
					if($scope.rutass[x].firstInfoWindow == null || $scope.rutass[x].firstInfoWindow == undefined){
						$scope.rutass[x].firstInfoWindow = new google.maps.InfoWindow;
					}else{ 
						if(!isInfoWindowOpen($scope.rutass[x].firstInfoWindow))
							$scope.rutass[x].firstInfoWindow.open(map, marker);
						return;
					}
				}
				else if(content.startPoint){
			 		$scope.rutass[x].infoWindows = [];
		 		}else{
		 			if($scope.rutass[x].infoWindows[content.index] == null || $scope.rutass[x].infoWindows[content.index] == undefined){
		 				$scope.rutass[x].infoWindows[content.index] = new google.maps.InfoWindow;
		 			}else{
		 				if(!isInfoWindowOpen($scope.rutass[x].infoWindows[content.index]))
		 					$scope.rutass[x].infoWindows[content.index].open(map, marker);
		 				return;
		 			}
		 		}
			break;
			}
	 	}
        var deletes = "'delete'";
        btn = content.shownRoute ? 
        		('<button type="button" class="btn btn-primary btn-xs" ng-click="marcarCoordenadas('+deletes+','+content.oficial.empId+')">Ocultar</button>')
        		:
        		('<button type="button" class="btn btn-primary btn-xs" data-toggle="modal" data-target="#myModal" ng-click="setTheOfficerRoute('+content.oficial.empId+')">Ruta</button>');	
        var tooltipHtml = 
        	'<div style="text-align:center;"><strong>'+
            content.oficial.empNombre+" "+content.oficial.empApePaterno+" "+content.oficial.empApeMaterno+
            '</strong></b><br/>Actualización: '+
            moment(content.fechaCoordenada).format('DD/MM/YYYY hh:mm A')+
            '<br/>T. Transcurrido: '+
            content.indicador.tiempoTranscurrido+
            '<br/>Num. Serie:'+
            content.numSerie+btn;
        var elTooltip = $compile(tooltipHtml)($scope);
        if(content.index<=-1){
        	$scope.rutass[x].firstInfoWindow.setContent(elTooltip[0]);
        	$scope.rutass[x].firstInfoWindow.open(map, marker);
        }else{
        	$scope.rutass[x].infoWindows[content.index].setContent(elTooltip[0]);
        	$scope.rutass[x].infoWindows[content.index].open(map, marker);
        }
    }
   
   function isInfoWindowOpen(infoWindow){
	    var map = infoWindow.getMap();
	    return (map !== null && typeof map !== "undefined");
	}

   $scope.setTheOfficerRoute = function(id){
	   $scope.officerRoute = id
   }
	   
   $scope.clearAllRoutes = function(){
	   for(var x in $scope.rutass){
		   $scope.clearRoutes($scope.rutass[x],false);
	   }
   }
   //--------------Ruteo------------------------------------------------
   // prototipo 1
   $scope.calculateAndDisplayRoute = function(id){
	   $scope.initMap('map2');
	   var waypnts = [];
	   for(var x in $scope.rutass){
		   if(id==$scope.rutass[x].empleado.empId){
			   for(var y in $scope.rutass[x].coordenadas)
				   waypnts.push({
				   location : {
					   lat : parseFloat($scope.rutass[x].coordenadas[y].latitud),
					   lng : parseFloat($scope.rutass[x].coordenadas[y].longitud)
				   },
				   stopover : true
			   })
			   break;
		   }
	   }
	   var start = new google.maps.LatLng(waypnts[0].location.lat, waypnts[0].location.lng);
	   var end = new google.maps.LatLng(waypnts[waypnts.length-1].location.lat, waypnts[waypnts.length-1].location.lng);
	   
	   $scope.directionsService = new google.maps.DirectionsService;
	   $scope.directionsDisplay = new google.maps.DirectionsRenderer;
	   $scope.directionsDisplay.setMap($scope.gMap);
       
       $scope.directionsService.route({
           origin: start,
           destination: end,
           waypoints: waypnts,
//           optimizeWaypoints: true,
           travelMode: 'DRIVING'
         }, function(response, status) {
         if (status === 'OK') {
        	 $scope.directionsDisplay.setDirections(response);
         } else {
        	 window.alert('Directions request failed due to ' + status);
         }
         });
   }
   
   
   //prototipo 2
   
   
   
   $scope.routePrototype2 = function(contents, id){
	   var x = 0;
	   for(x in $scope.rutass){
			if(id==$scope.rutass[x].empleado.empId){
//				rutaSelected = $scope.rutass[x]; 
				break;
			}
		}
	   $scope.directionsService = new google.maps.DirectionsService;
	   var lngs = contents.map(function(station) { return station.lng; });
	   var lats = contents.map(function(station) { return station.lat; });
	   $scope.gMap.fitBounds({
	       west: Math.min.apply(null, lngs),
	       east: Math.max.apply(null, lngs),
	       north: Math.min.apply(null, lats),
	       south: Math.max.apply(null, lats),
	   });
	   rndColor = '#'+Math.floor(Math.random()*16777215).toString(16);
	   for (var i = 0, parts = [], max = 25 - 1; i < contents.length; i = i + max)
	        parts.push(contents.slice(i, i + max + 1));

	    // Callback function to process service results
	    var service_callback = function(response, status) {
	        if (status != 'OK') {
	            console.log('Directions request failed due to ' + status);
	            return;
	        }
	        var renderer = new google.maps.DirectionsRenderer({
	            polylineOptions: {
	                strokeColor: rndColor
	              }
	            });
	        renderer.setMap($scope.gMap);
	        renderer.setOptions({ suppressMarkers: true, preserveViewport: true });
	        renderer.setDirections(response);
	        $scope.rutass[x].renderers.push(renderer);
	    };
	    $scope.rutass[x].renderers = [];
	    for (var i = 0; i < parts.length; i++) {
	        var waypoints = [];
	        for (var j = 1; j < parts[i].length - 1; j++)
	            waypoints.push({location: parts[i][j], stopover: true});
	        var service_options = {
	            origin: parts[i][0],
	            destination: parts[i][parts[i].length - 1],
	            waypoints: waypoints,
	            optimizeWaypoints:false,
	            travelMode: 'DRIVING'
	        };
	        $scope.directionsService.route(service_options, service_callback);
	    }
   }
   
   
   //prototipo 3
   //Incomplete
   
   $scope.routePrototype3 = function(contents){
	   $scope.directionsService = new google.maps.DirectionsService;
	   var lngs = contents.map(function(station) { return station.lng; });
	   var lats = contents.map(function(station) { return station.lat; });
	   $scope.gMap.fitBounds({
	       west: Math.min.apply(null, lngs),
	       east: Math.max.apply(null, lngs),
	       north: Math.min.apply(null, lats),
	       south: Math.max.apply(null, lats),
	   });
	   for (var i = 0, parts = [], max = 8 - 1; i < contents.length; i = i + max){
	        parts.push(contents.slice(i, i + max + 1));
	   }

//	    var service_callback = function(response, status,i) {
//	        if (status != 'OK') {
//	            console.log('Directions request failed due to ' + status);
//	            wait = true;
//                setTimeout("wait = true", 2000);
//	            return;
//	        }
//	        var renderer = new google.maps.DirectionsRenderer;
//	        renderer.setMap($scope.gMap);
//	        renderer.setOptions({ suppressMarkers: true, preserveViewport: true });
//	        renderer.setDirections(response);
//	    };
	    
	    for (var i = 0; i < parts.length; i++) {
	        var waypoints = [];
	        for (var j = 1; j < parts[i].length - 1; j++){
	            waypoints.push({location: parts[i][j], stopover: true});
	        }

	        var service_options = {
	            origin: parts[i][0],
	            destination: parts[i][parts[i].length - 1],
	            waypoints: waypoints,
	            optimizeWaypoints:false,
	            travelMode: 'DRIVING'
	        };
	        $scope.directionsService.route(service_options, service_callback);
	    }
   }
   
	$scope.initMap('map2');
});