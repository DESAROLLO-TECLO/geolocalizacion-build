package mx.com.teclo.sms.ws.util.enums;

public enum Catalogos {
	TipoEvento("Tipo de evento"),
	Dispositivos("Dispositivos");
	private String nombreCatalogo;

	private Catalogos(String nombreCatalogo) {
		this.nombreCatalogo = nombreCatalogo;

	}

	public String getNombreCatalogo() {
		return nombreCatalogo;
	}

	public void setNombreCatalogo(String nombreCatalogo) {
		this.nombreCatalogo = nombreCatalogo;
	}

	static Catalogos getNumServicio(String x) {
		for (Catalogos currentType : Catalogos.values()) {
			if (x.equals(currentType.getNombreCatalogo())) {
				return currentType;
			}
		}
		return null;
	}

}
