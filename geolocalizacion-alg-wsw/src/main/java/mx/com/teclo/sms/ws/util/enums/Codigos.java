package mx.com.teclo.sms.ws.util.enums;

public enum Codigos {
	SUCCESS("TCL200"), NOT_FOUND("TCL404"), ERROR_SERVIDOR("TCL500"), BAD_REQUEST("TCL400"),CREATE("TCL200");

	private String codigoId;

	private Codigos(String codigoId) {
		this.codigoId = codigoId;

	}

	public String getCodigoId() {
		return codigoId;
	}

	public void setProcesoId(String codigoId) {
		this.codigoId = codigoId;
	}

	static Codigos getCodigoTipo(String x) {
		for (Codigos currentType : Codigos.values()) {
			if (x.equals(currentType.getCodigoId())) {
				return currentType;
			}
		}
		return null;
	}

}
