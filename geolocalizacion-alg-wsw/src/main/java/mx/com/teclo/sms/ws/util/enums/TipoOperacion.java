package mx.com.teclo.sms.ws.util.enums;

public enum TipoOperacion {

	/*
	 * @TCL409 Conflict
	 * */
	
	REQUEST("REQUEST",null), 
	RESPONSE("RESPONSE",null),
	FILE_NOT_TEXT_PLAIN("El documento actual no es un archivo de texto","TCL409"),
	FILE_IS_EMPTY("El documento actual no incluye contenido","TCL409"),
	PROCESS_MULTI_NOT_PERMIT("Actualmente existe un procesamiento activo","TCL409"),
	PROCESS_NUM_MAX_PROCESS("Ya existen multiples procesos activos","TCL409"),
	FILE_SIZE("El tamaño del documento supera el máximo permitido","TCL409"),
	MAX_RECORDS("En documento actual supera los registros máximos permitidos","TCL409"),
	DEFAULT_EMPLOYE("Empleado por defecto", "99");

	private String tpOperacion;
	private String cdOperacion;

	private TipoOperacion(String tpOperacion, String cdOperacion) {
		this.tpOperacion = tpOperacion;
		this.cdOperacion = cdOperacion;
	}

	/**
	 * @return the tpOperacion
	 */
	public String getTpOperacion() {
		return tpOperacion;
	}

	/**
	 * @return the cdOperacion
	 */
	public String getCdOperacion() {
		return cdOperacion;
	}

	/**
	 * @param cdOperacion the cdOperacion to set
	 */
	public void setCdOperacion(String cdOperacion) {
		this.cdOperacion = cdOperacion;
	}

	/**
	 * @param tpOperacion the tpOperacion to set
	 */
	public void setTpOperacion(String tpOperacion) {
		this.tpOperacion = tpOperacion;
	}

	static TipoOperacion getNumServicio(String x) {
		for (TipoOperacion currentType : TipoOperacion.values()) {
			if (x.equals(currentType.getTpOperacion())) {
				return currentType;
			}
		}
		return null;
	}

}
