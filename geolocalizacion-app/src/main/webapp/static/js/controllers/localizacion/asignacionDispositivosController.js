angular.module(localizacionGps).controller('asignacionDispositivosController', function($scope, $filter, gMapsRutasService, $controller, zonasVialesService, ModalService, data) {
	
	$scope.zonasViales = angular.copy(data.data);
	$scope.zonasVialesSelect = [];
	$scope.zonasVialesSelect[0] = angular.copy($scope.zonasViales);
	$scope.zonasVialesSelect[1] = angular.copy($scope.zonasViales);
	$scope.zonasModelsOriginal = [];
	$scope.zonasModels = [];
	$scope.dispositivo = {numSerie : "",numSim : "", numIp : ""};
	
	$scope.heightDiv = "";
	
	$scope.searchDispositivos = function(position, zonaObject){
		if(zonaObject == null){
			$scope.zonasModels[position] = null;
			return;
		}
		zv = angular.copy($scope.zonasViales);
		indexx = zv.indexOf($filter('filter')(zv, {codigoZonaVial: zonaObject.codigoZonaVial}, true)[0])
		zv.splice(indexx, 1);
		$scope.zonasVialesSelect[position == 0 ? 1 : 0] = angular.copy(zv);
		
		(zonaObject.codigoZonaVial == 'DEF' ?
		zonasVialesService.dispositivosOficialesSinAsignacion('h')
		: zonasVialesService.zonasDispositivosAndOficialesByZonaCode(zonaObject.codigoZonaVial, 'h'))
		.success(function(data){
			$scope.zonasModelsOriginal[position] = data;
			$scope.zonasModels[position] = angular.copy($scope.zonasModelsOriginal[position]);
			$scope.zonasModels[position].originalList = true;
			$scope.flag = true;
		})
		.error(function(data){
			$scope.zonasModelsOriginal[position] = {};
			$scope.zonasModels[position] = angular.copy($scope.zonasModelsOriginal[position]);
		})
	}
	
	$scope.searchSingleDispoAndData = function(){
		zonasVialesService.allInfoBySingleDisp($scope.dispositivo)
		.success(function(data){
			
			$scope.zonasModels[1] = null;
			$scope.zonasModels[0] = data;
			$scope.zonasModels[0].originalList = true;
			$scope.zonasModels[0].label = 
				$scope.dispositivo.numSerie != null && $scope.dispositivo.numSerie != "" ?  $scope.dispositivo.numSerie : 
					$scope.dispositivo.numSim != null && $scope.dispositivo.numSim != "" ?  $scope.dispositivo.numSim : 
						$scope.dispositivo.numIp != null && $scope.dispositivo.numIp != "" ?  $scope.dispositivo.numIp : '';
			
			$scope.zonasVialesSelect[0] = angular.copy($scope.zonasViales);
			$scope.zonaSelect1 = $filter('filter')($scope.zonasVialesSelect[0], {codigoZonaVial: $scope.zonasModels[0].codigoZonaVial}, true)[0];
			
			zv = angular.copy($scope.zonasViales);
			indexx = zv.indexOf($filter('filter')(zv, {codigoZonaVial: $scope.zonasModels[0].codigoZonaVial}, true)[0])
			zv.splice(indexx, 1);
			$scope.zonasVialesSelect[1] = angular.copy(zv);
			$scope.zonaSelect2 = null;
			$scope.flag = true;
		})
		.error(function(){
			$scope.showError("No existen dispositivos registrados con estos datos");
		})
	}
	
	$scope.changeZona = function(position, nextPosition, zonaObject, auxiliarForm) {
		if(auxiliarForm){
			var allNull = true;
			for(var x in $scope.dispositivo){
				if($scope.dispositivo[x] != null && $scope.dispositivo[x] != ""){
					allNull = false; break;
				}	 
			}
			if(allNull){
				$scope.showError("Debe buscar al menos con un parámetro")
				return;
			}
		}
		if(($scope.zonasModels[0] == null || $scope.zonasModels[0].originalList) && ($scope.zonasModels[1] == null || $scope.zonasModels[1].originalList)){
			if(auxiliarForm)
				$scope.searchSingleDispoAndData();
			else
				$scope.searchDispositivos(position, zonaObject)
		}else
		ModalService.showModal({
			templateUrl: 'views/templatemodal/templateModalGuardaDescartaCancela.html',
			controller: 'mensajeModalController',
			inputs:{ message: "¿Desea guardar cambios antes de cambiar de zona?"}
		}).then(function(modal) {
			modal.element.modal();
			modal.close.then(function(result) {
	        	if(result == '1'){
	        		$scope.persistDispositivos(auxiliarForm);
	        		nextPosition == 1 ? 
	    				$scope.zonaSelect1 = $filter('filter')($scope.zonasVialesSelect[0], {codigoZonaVial: $scope.zonasModels[0].codigoZonaVial}, true)[0]:
	    					$scope.zonaSelect2 = $filter('filter')($scope.zonasVialesSelect[1], {codigoZonaVial: $scope.zonasModels[1].codigoZonaVial}, true)[0];
	        					
	        	}else if(result == '2'){
	        		if(auxiliarForm)
	        			$scope.searchSingleDispoAndData();
	        		else{
		        		$scope.searchDispositivos(position, zonaObject);
		        		$scope.zonasModels[nextPosition] = angular.copy($scope.zonasModelsOriginal[nextPosition]);
		    			$scope.zonasModels[nextPosition].originalList = true;
	        		}
	        	}
	        	else{
	        		if(auxiliarForm) return;
	        		$scope.zonaSelect1 = $filter('filter')($scope.zonasVialesSelect[0], {codigoZonaVial: $scope.zonasModels[0].codigoZonaVial}, true)[0];
	        		$scope.zonaSelect2 = $filter('filter')($scope.zonasVialesSelect[1], {codigoZonaVial: $scope.zonasModels[1].codigoZonaVial}, true)[0];
	        	};
			});
		});
	}
	
	$scope.persistDispositivos = function(auxiliarForm){
		$scope.vo = {asignaciones :[]};
		angular.forEach($scope.zonasModels, function(value, key) {
			if(value.codigoZonaVial != 'DEF'){
				dispos = []
				angular.forEach(value.dispositivos, function(dispo, key) {
					dispos.push(dispo.idDispositivo);
				});
				$scope.vo.asignaciones.push({idZonaVial : value.idZonaVial, dispositivos : dispos});
			}
		});
		zonasVialesService.persitDispositivosZonasAsignadas($scope.vo)
		.success(function(data){
			for(position = 0; position <2; position++){
				$scope.zonasModelsOriginal[position] = angular.copy($scope.zonasModels[position]);
				$scope.zonasModels[position].originalList = true;
			}
			$scope.showAviso(data.status.descripcion, auxiliarForm);
		})
		.error(function(){
			
		})
	}
	
	//LOGICA DRAG AND DROP

	$scope.hardPush = function(x,y){
		for(var sx in $scope.zonasModels[x].dispositivos){
			var obj = $scope.zonasModels[x].dispositivos[sx]
			$scope.zonasModels[y].dispositivos.push(obj);
		}
		while($scope.zonasModels[x].dispositivos.length>0){
			$scope.zonasModels[x].dispositivos.splice($scope.zonasModels[x].dispositivos.length-1, 1);
		}
	}
	
	/**
    * dnd-dragging determines what data gets serialized and send to the receiver
    * of the drop. While we usually just send a single object, we send the array
    * of all selected items here.
    */
	$scope.getSelectedItemsIncluding = function(list, item) {
		item.selected = true;
		return list.dispositivos.filter(function(item) { return item.selected; });
	};

	$scope.onDragstart = function(list, event) {
		list.dragging = true;
		if (event.dataTransfer.setDragImage) {
			var img = new Image();
			img.src = 'static/dist/img/handheld.png';
			event.dataTransfer.setDragImage(img, 0, 0);
		}
	};

	/**
	 * In the dnd-drop callback, we now have to handle the data array that we
	 * sent above. We handle the insertion into the list ourselves. By returning
	 * true, the dnd-list directive won't do the insertion itself.
	 */
	$scope.onDrop = function(list, items, index) {
		list.originalList = false;
		angular.forEach(items, function(item) { item.selected = false; });
		list.dispositivos = list.dispositivos.slice(0, index)
		.concat(items)
		.concat(list.dispositivos.slice(index));
		return true;
	}

   /**
    * Last but not least, we have to remove the previously dragged items in the
    * dnd-moved callback.
    */
	$scope.onMoved = function(list) {
		list.originalList = false;
		list.dispositivos = list.dispositivos.filter(function(item) { return !item.selected; });
	};

   // Generate the initial model
	angular.forEach($scope.models, function(list) {
		for (var i = 1; i <= 4; ++i) {
			list.items.push({label: "Item " + list.listName + i});
		}
	});

   // Model to JSON for demo purpose
	$scope.$watch('models', function(model) {
	   $scope.modelAsJson = angular.toJson(model, true);
	}, true);
   
	$scope.showAviso = function(messageTo, auxiliarForm) {
		ModalService.showModal({
			templateUrl: 'views/templatemodal/templateModalAviso.html',
			controller: 'mensajeModalController',
			inputs:{ message: messageTo}
		}).then(function(modal) {
			modal.element.modal();
			modal.close.then(function(result) {
				if(auxiliarForm){
					$scope.searchSingleDispoAndData();
				}
			});
		});
	};
	
	$scope.showError = function(messageTo) {
		ModalService.showModal({
			templateUrl: 'views/templatemodal/templateModalError.html',
			controller: 'mensajeModalController',
			inputs:{ message: messageTo}
		}).then(function(modal) {
			modal.element.modal();
		});
	};

	
});