angular.module(localizacionGps).controller('asignacionOficialesController', function($scope, $filter, gMapsRutasService, $controller, zonasVialesService, ModalService, data) {
	
	$scope.auxiliarForm = false;
	$scope.view = {rowsPerPage:5};
	$scope.order = 'fecha';
	$scope.reverse = true;
	$scope.toggleAuxiliarForm = function(){
		$scope.auxiliarForm = !$scope.auxiliarForm ;
	}
	
	$scope.zonasViales = angular.copy(data.data);
	$scope.zonasVialesSelect = [angular.copy($scope.zonasViales),angular.copy($scope.zonasViales)];
//	$scope.zonasVialesSelect[0] = angular.copy($scope.zonasViales);
//	$scope.zonasVialesSelect[1] = angular.copy($scope.zonasViales);
	$scope.zonasModelsOriginal = [];
	$scope.zonasModels = [];
	$scope.oficial = {placa:"", nombre:"", materno:"", materno:""};
	
//	$scope.heightDiv = "";
	
	$scope.searchOfficers = function(position, zonaObject){
		if(zonaObject == null){
			$scope.zonasModels[position] = null;
			return;
		}
		zv = angular.copy($scope.zonasViales);
		zv.splice(zv.indexOf($filter('filter')(zv, {codigoZonaVial: zonaObject.codigoZonaVial}, true)[0]), 1);
		$scope.zonasVialesSelect[position == 0 ? 1 : 0] = angular.copy(zv);
		
		(zonaObject.codigoZonaVial == 'DEF' ?
		zonasVialesService.dispositivosOficialesSinAsignacion('o')
		: zonasVialesService.zonasDispositivosAndOficialesByZonaCode(zonaObject.codigoZonaVial, 'o'))
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
	
	var lastOfficer;
	$scope.searchOfficer = function(officer){
		var allNull = true;
		for(var x in officer){
			if(officer[x] != null && officer[x] != ""){
				allNull = false; break;
			}	 
		}
		if(allNull){
			$scope.showError("Debe buscar al menos con un parámetro")
			return;
		}
		lastOfficer = angular.copy(officer);
		zonasVialesService.searchOfficer(officer)
		.success(function(data){
			$scope.foundOfficers = data;
		})
		.error(function(data){
			$scope.foundOfficers = [];
			$scope.showError("No existen oficiales registrados con estos datos")
		})
	}
	
	$scope.searchSingleOfficerAndData = function(zonaOfficer){
		zonasVialesService.allInfoBySingleOfficer(zonaOfficer.codigoZonaVial, zonaOfficer.oficiales[0].empId)
		.success(function(data){
			
			$scope.zonasModels[1] = null;
			$scope.zonasModels[0] = data;
			$scope.zonasModels[0].originalList = true;
			$scope.zonasModels[0].label = zonaOfficer.oficiales[0].empPlaca;
			
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
			$scope.showError("No existen oficiales registrados con estos datos")
		})
	}
	
	$scope.changeZona = function(position, nextPosition, zonaObject, auxiliarForm) {
		if(($scope.zonasModels[0] == null || $scope.zonasModels[0].originalList) &&
			($scope.zonasModels[1] == null || $scope.zonasModels[1].originalList)){
			if(auxiliarForm)
				$scope.searchSingleOfficerAndData(zonaObject);
			else
				$scope.searchOfficers(position, zonaObject)
		}else
		ModalService.showModal({
			templateUrl: 'views/templatemodal/templateModalGuardaDescartaCancela.html',
			controller: 'mensajeModalController',
			inputs:{ message: "¿Desea guardar cambios antes de cambiar de zona?"}
		}).then(function(modal) {
			modal.element.modal();
			modal.close.then(function(result) {
	        	if(result == '1'){
	        		$scope.persistOfficers(auxiliarForm, zonaObject);
	        		nextPosition == 1 ? 
	    				$scope.zonaSelect1 = $filter('filter')($scope.zonasVialesSelect[0], {codigoZonaVial: $scope.zonasModels[0].codigoZonaVial}, true)[0]:
	    					$scope.zonaSelect2 = $filter('filter')($scope.zonasVialesSelect[1], {codigoZonaVial: $scope.zonasModels[1].codigoZonaVial}, true)[0];
	        					
	        	}else if(result == '2'){
	        		if(auxiliarForm)
	        			$scope.searchSingleOfficerAndData(zonaObject);
	        		else{
		        		$scope.searchOfficers(position, zonaObject);
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
	
	$scope.persistOfficers = function(auxiliarForm, zonaObject){
		$scope.vo = {asignaciones :[]};
		angular.forEach($scope.zonasModels, function(value, key) {
			if(value.codigoZonaVial != 'DEF'){
				officers = []
				angular.forEach(value.oficiales, function(officer, key) {
					officers.push(officer.empId);
				});
				$scope.vo.asignaciones.push({idZonaVial : value.idZonaVial, oficiales : officers});
			}
		});
		zonasVialesService.persitDispositivosZonasAsignadas($scope.vo)
		.success(function(data){
			for(position = 0; position <2; position++){
				$scope.zonasModelsOriginal[position] = angular.copy($scope.zonasModels[position]);
				$scope.zonasModels[position].originalList = true;
			}
			$scope.showAviso(data.status.descripcion, auxiliarForm, zonaObject);
			if($scope.foundOfficers!=undefined && $scope.foundOfficers.length > 0)
				$scope.searchOfficer(lastOfficer);
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
   
	$scope.showAviso = function(messageTo, auxiliarForm, zonaObject) {
		ModalService.showModal({
			templateUrl: 'views/templatemodal/templateModalAviso.html',
			controller: 'mensajeModalController',
			inputs:{ message: messageTo}
		}).then(function(modal) {
			modal.element.modal();
			modal.close.then(function(result) {
				if(auxiliarForm){
					$scope.searchSingleOfficerAndData(zonaObject);
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