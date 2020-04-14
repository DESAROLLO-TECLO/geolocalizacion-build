package mx.com.teclo.sms.ws.util.enums;

public enum Reportes {
	REPORTE_GENERAL("REPORTE GENERAL DE EVENTOS");
	
	private String reporte;

	private Reportes(String reporte) {
		this.reporte = reporte;
	}
	
	public String getReporte() {
		return reporte;
	}

	public void setReporte(String reporte) {
		this.reporte = reporte;
	}
	
}
