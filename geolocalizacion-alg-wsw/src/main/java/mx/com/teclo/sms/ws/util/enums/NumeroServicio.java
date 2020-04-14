package mx.com.teclo.sms.ws.util.enums;

public enum NumeroServicio {
	SERVICIO1("01"),
	SERVICIO2("02"),
	SERVICIO3("03");

	private String numServicio;

	private NumeroServicio(String numServicio) {
		this.numServicio = numServicio;

	}

	public String getNumServicio() {
		return numServicio;
	}

	public void setNumServicio(String numServicio) {
		this.numServicio = numServicio;
	}

	static NumeroServicio getNumServicio(String x) {
		for (NumeroServicio currentType : NumeroServicio.values()) {
			if (x.equals(currentType.getNumServicio())) {
				return currentType;
			}
		}
		return null;
	}

}
