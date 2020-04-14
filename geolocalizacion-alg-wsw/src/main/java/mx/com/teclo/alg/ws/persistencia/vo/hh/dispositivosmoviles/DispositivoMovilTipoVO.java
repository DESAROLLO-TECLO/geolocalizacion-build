package mx.com.teclo.alg.ws.persistencia.vo.hh.dispositivosmoviles;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DispositivoMovilTipoVO {
    @JsonIgnore
	private Long idTipoDispositivo;
	private String cdTipoDispositivo;
	private String nbTipoDispositivo;
	
	public DispositivoMovilTipoVO(Long idTipoDispositivo, String cdTipoDispositivo, String nbTipoDispositivo) {
		super();
		this.idTipoDispositivo = idTipoDispositivo;
		this.cdTipoDispositivo = cdTipoDispositivo;
		this.nbTipoDispositivo = nbTipoDispositivo;
	}
		
	public DispositivoMovilTipoVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Long getIdTipoDispositivo() {
		return idTipoDispositivo;
	}

	public void setIdTipoDispositivo(Long idTipoDispositivo) {
		this.idTipoDispositivo = idTipoDispositivo;
	}

	public String getCdTipoDispositivo() {
		return cdTipoDispositivo;
	}

	public void setCdTipoDispositivo(String cdTipoDispositivo) {
		this.cdTipoDispositivo = cdTipoDispositivo;
	}

	public String getNbTipoDispositivo() {
		return nbTipoDispositivo;
	}

	public void setNbTipoDispositivo(String nbTipoDispositivo) {
		this.nbTipoDispositivo = nbTipoDispositivo;
	}

	@Override
	public String toString() {
		return "DispositivoMovilTipoVO [idTipoDispositivo=" + idTipoDispositivo + ", cdTipoDispositivo="
				+ cdTipoDispositivo + ", nbTipoDispositivo=" + nbTipoDispositivo + "]";
	}

	 
}
