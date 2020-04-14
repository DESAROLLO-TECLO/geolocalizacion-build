package mx.com.teclo.sms.ws.util.enums;

public enum Cargos {
	
	OFICIAL("OFICIAL"), SUPERVISOR("SUPERVISOR");
	
	public String cargo;
	
	private Cargos(String cargo){
		this.cargo = cargo;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	
}
