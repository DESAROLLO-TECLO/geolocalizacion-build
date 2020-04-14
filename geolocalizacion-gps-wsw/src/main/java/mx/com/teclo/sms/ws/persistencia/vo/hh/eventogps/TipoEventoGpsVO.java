package mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps;

public class TipoEventoGpsVO {
	//@JsonIgnore
	private Long idTipoEvento;
	private String codigoTipoEvento;
	private String nombreTipoEvento;
	private String estatusEvento;
	
	
	public TipoEventoGpsVO(Long idTipoEvento, String codigoTipoEvento, String nombreTipoEvento, String estatusEvento) {
		super();
		this.idTipoEvento = idTipoEvento;
		this.codigoTipoEvento = codigoTipoEvento;
		this.nombreTipoEvento = nombreTipoEvento;
		this.estatusEvento = estatusEvento;
	}

	public TipoEventoGpsVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getIdTipoEvento() {
		return idTipoEvento;
	}
	public void setIdTipoEvento(Long idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
	}
	public String getCodigoTipoEvento() {
		return codigoTipoEvento;
	}
	public void setCodigoTipoEvento(String codigoTipoEvento) {
		this.codigoTipoEvento = codigoTipoEvento;
	}
	public String getNombreTipoEvento() {
		return nombreTipoEvento;
	}
	public void setNombreTipoEvento(String nombreTipoEvento) {
		this.nombreTipoEvento = nombreTipoEvento;
	}
	public String getEstatusEvento() {
		return estatusEvento;
	}
	public void setEstatusEvento(String estatusEvento) {
		this.estatusEvento = estatusEvento;
	}
	
	@Override
	public String toString() {
		return "TipoEventoGpsVO [idTipoEvento=" + idTipoEvento + ", codigoTipoEvento=" + codigoTipoEvento
				+ ", nombreTipoEvento=" + nombreTipoEvento + ", estatusEvento=" + estatusEvento + "]";
	}
}
