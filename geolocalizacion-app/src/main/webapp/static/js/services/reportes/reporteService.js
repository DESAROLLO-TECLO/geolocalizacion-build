angular.module(localizacionGps).service("reporteService", function($http, config) {
	
	this.infraccionesPorZonaVial = function(zonaId, dia){
    	return $http.get(config.baseUrl+"/reportes/infraccionesporzonavial?zonavialId=" + zonaId + "&dia=" + dia);
    }
	
    this.infraccionesMensualPorZonaVial = function(zonaId, anio){
    	return $http.get(config.baseUrl+"/reportes/infraccionesMensualporzonavial?zonavialId="+ zonaId + "&anio=" + anio);
    }
    
    this.infraccionesAnualPorZonaVial = function(zonaId, anios){
    	return $http.get(config.baseUrl+"/reportes/infraccionesAnualporzonavial?zonavialId="+ zonaId + "&anios[]=" + anios);
    }
    
    this.reporteGeneralData = function(empPlaca){
    	return $http.get(config.baseUrl + "/reportes/reportegeneraldata?empPlaca=" + empPlaca);
    }
    
    this.reporteGeneralOficialDispositivoData = function(empPlaca){
    	return $http.get(config.baseUrl + "/reportes/reportegeneraloficialdispositivodata?empPlaca=" + empPlaca);
    }
    
    this.reporteGeneral = function(o){
    	return $http.get(config.baseUrl + "/reportes/reportegeneral" +
    			"?loginRestriction=" + o.loggedOfficers +
    			"&zonasViales=" + o.zonasViales +
    			"&fechaInicial=" + moment(new Date(o.fechaInicial)).format('DD/MM/YYYY') +
    			"&fechaFinal=" +moment(new Date(o.fechaFinal)).format('DD/MM/YYYY') + 
    			"&tipoEventos=" + ((o.tipoEventos == undefined || o.tipoEventos == null) ? [] : o.tipoEventos) + 
    			"&tipoInfracciones=" + ((o.tipoInfracciones == undefined || o.tipoInfracciones == null) ? [] : o.tipoInfracciones));
    }
    
    this.reporteGeneralOficialesDispositivos = function(o){
    	return $http.get(config.baseUrl + "/reportes/reportegeneralOficialesDispositivos" +
    			"?loginRestriction=" + o.loggedOfficers +
    			"&busquedaConjunta=" + o.officerUnionDisp +
    			"&oficiales=" + o.empleados +
    			"&dispositivos=" + o.devices +
    			"&fechaInicial=" + moment(new Date(o.fechaInicial)).format('DD/MM/YYYY') +
    			"&fechaFinal=" +moment(new Date(o.fechaFinal)).format('DD/MM/YYYY') + 
    			"&tipoEventos=" + ((o.tipoEventos == undefined || o.tipoEventos == null) ? [] : o.tipoEventos) + 
    			"&tipoInfracciones=" + ((o.tipoInfracciones == undefined || o.tipoInfracciones == null) ? [] : o.tipoInfracciones));
    }
    
    this.reporteGeneralExcel = function(reportKind) {
		return $http({
			method : 'GET',
			url : config.baseUrl + "/reportes/reportegeneral/xls",
			params : {
				reportKind:reportKind
			},
			dataType : "json",
			header : {
				"Content-type" : "application/json",
				"Accept" : "vnd.openxmlformats-officedocument.spreadsheetml.sheet"
			},
			responseType : 'arraybuffer'
		});
	}
    
})