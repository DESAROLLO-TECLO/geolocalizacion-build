package mx.com.teclo.sms.ws.util.enums;

public enum Codigos {
	SUCCESS("TCL200"), NOT_FOUND("TCL404"), ERROR_SERVIDOR("TCL500"), BAD_REQUEST("TCL400"),CREATE("TCL200"), LIST_CREATE("TCL200"), UPDATE("TCL204") ;

	private String codigoId;

	private Codigos(String codigoId) {
		this.codigoId = codigoId;

	}

	public String getProcesoId() {
		return codigoId;
	}

	public void setProcesoId(String codigoId) {
		this.codigoId = codigoId;
	}

	static Codigos getArchivoTipo(String x) {
		for (Codigos currentType : Codigos.values()) {
			if (x.equals(currentType.getProcesoId())) {
				return currentType;
			}
		}
		return null;
	}

}
