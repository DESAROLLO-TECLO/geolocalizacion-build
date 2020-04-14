package mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.eventoalg;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TALG001C_TIPOS_EVENTO_GPS")
public class TipoEventoAlgDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2392837170046767986L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_TIPO_EVENTO", unique = true)
	private Long idTipoEvento;

	@Column(name = "CD_TIPO_EVENTO")
	private String cdTipoEvento;

	@Column(name = "NB_TIPO_EVENTO")
	private String nbTipoEvento;

	@Column(name = "ST_ACTIVO")
	private Integer estatusEvento;

	@Column(name = "ID_USR_CREACION")
	private Long creadoPor;

	@Column(name = "FH_CREACION")
	private Date fechaCreacion;

	@Column(name = "ID_USR_MODIFICA")
	private Long modificadoPor;

	@Column(name = "FH_MODIFICACION")
	private Date ultimaModificacion;

	public Long getIdTipoEvento() {
		return idTipoEvento;
	}

	public void setIdTipoEvento(Long idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
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

	public Integer getEstatusEvento() {
		return estatusEvento;
	}

	public void setEstatusEvento(Integer estatusEvento) {
		this.estatusEvento = estatusEvento;
	}

	public Long getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(Long creadoPor) {
		this.creadoPor = creadoPor;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Long getModificadoPor() {
		return modificadoPor;
	}

	public void setModificadoPor(Long modificadoPor) {
		this.modificadoPor = modificadoPor;
	}

	public Date getUltimaModificacion() {
		return ultimaModificacion;
	}

	public void setUltimaModificacion(Date ultimaModificacion) {
		this.ultimaModificacion = ultimaModificacion;
	}

	@Override
	public String toString() {
		return "TipoEventoAlgDTO [idTipoEvento=" + idTipoEvento + ", cdTipoEvento=" + cdTipoEvento + ", nbTipoEvento="
				+ nbTipoEvento + ", estatusEvento=" + estatusEvento + ", creadoPor=" + creadoPor + ", fechaCreacion="
				+ fechaCreacion + ", modificadoPor=" + modificadoPor + ", ultimaModificacion=" + ultimaModificacion
				+ "]";
	}

}
