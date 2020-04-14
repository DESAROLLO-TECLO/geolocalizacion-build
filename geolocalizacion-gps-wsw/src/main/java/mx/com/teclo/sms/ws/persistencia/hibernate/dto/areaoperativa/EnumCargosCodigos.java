package mx.com.teclo.sms.ws.persistencia.hibernate.dto.areaoperativa;

public enum EnumCargosCodigos {
	
	SUPERVISOR("SUPERVISOR"),
	OFICIAL("OFICIAL");

	private String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private EnumCargosCodigos(String code) {
		this.code=code;
	}
}
