package mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TipoEventoAlgVO {
	@JsonIgnore
	private Long idTipoEvento;
	private String cdTipoEvento;
	private String nbTipoEvento;

	public TipoEventoAlgVO(Long idTipoEvento, String cdTipoEvento, String nbTipoEvento) {
		super();
		this.idTipoEvento = idTipoEvento;
		this.cdTipoEvento = cdTipoEvento;
		this.nbTipoEvento = nbTipoEvento;
	}

	public TipoEventoAlgVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getIdTipoEvento() {
		return idTipoEvento;
	}

	public String getCdTipoEvento() {
		return cdTipoEvento;
	}

	public void setCdTipoEvento(String cdTipoEvento) {
		this.cdTipoEvento = cdTipoEvento;
	}

	public String getNbTipoEvento() {
		return nbTipoEvento;
	}

	public void setNbTipoEvento(String nbTipoEvento) {
		this.nbTipoEvento = nbTipoEvento;
	}

	public void setIdTipoEvento(Long idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
	}

	@Override
	public String toString() {
		return "TipoEventoAlgVO [idTipoEvento=" + idTipoEvento + ", cdTipoEvento=" + cdTipoEvento + ", nbTipoEvento="
				+ nbTipoEvento + "]";
	}

}
