angular.module(localizacionGps).service("showAlert", function(ModalService) {

	/* Modal aviso */
	this.aviso = function(messageTo, action, param) {
		ModalService.showModal({
			templateUrl : 'views/templatemodal/templateShowAlertas.html',
			controller : 'modalMensajeController',
			inputs : {
				message : messageTo,
				typeModal : 'aviso'
			}
		}).then(function(modal) {
			validateParams(modal, messageTo, action, param);
		});

		return ModalService.showModal;
	}

	/* Modal error */
	this.error = function(messageTo, action, param) {
		ModalService.showModal({
			templateUrl : 'views/templatemodal/templateShowAlertas.html',
			controller : 'modalMensajeController',
			inputs : {
				message : messageTo,
				typeModal : 'error'
			}
		}).then(function(modal) {
			validateParams(modal, messageTo, action, param);
		});
		
		return ModalService.showModal;
	}

	/* Modal confirmacion */
	this.confirmacion = function(messageTo, action, param) {
		ModalService.showModal({
			templateUrl : 'views/templatemodal/templateShowAlertas.html',
			controller : 'modalMensajeController',
			inputs : {
				message : messageTo,
				typeModal : 'confirmacion'
			}
		}).then(function(modal) {
			validateParams(modal, messageTo, action, param);
		});
		
		return ModalService.showModal;
	}
	
	this.requiredFields = function(formName) {
		angular.forEach(formName.$error, function (field) {
			angular.forEach(field, function(errorField){
				errorField.$setDirty();
			})
		});
	}
	
	validateParams = function(modal, m, a, p) {
		modal.element.modal();
		if (a) {
			modal.close.then(function() {
				if(p != undefined) {
					a(p);
				}else{
					a();
				}
			});
		}
	}

});