package mx.com.teclo.sms.ws.persistencia.hibernate.dto.hh.eventogps;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GPS_EVENTO_TIPO")
public class TipoEventoGpsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7192737741924296186L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "EVENTO_TIPO_ID", unique = true)
	private Long idTipoEvento;

	@Column(name = "EVENTO_TIPO_COD")
	private String codigoTipoEvento;

	@Column(name = "EVENTO_TIPO_NOMBRE")
	private String nombreTipoEvento;

	@Column(name = "EVENTO_TIPO_ESTATUS")
	private String estatusEvento;
	
	@Column(name = "CREADO_POR")
	private Long creadoPor;

	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;

	@Column(name = "MODIFICADO_POR")
	private Long modificadoPor;

	@Column(name = "ULTIMA_MODIFICACION")
	private Date ultimaModificacion;

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
		return "TipoEventoGps [idTipoEvento=" + idTipoEvento + ", codigoTipoEvento=" + codigoTipoEvento
				+ ", nombreTipoEvento=" + nombreTipoEvento + ", estatusEvento=" + estatusEvento + ", creadoPor="
				+ creadoPor + ", fechaCreacion=" + fechaCreacion + ", modificadoPor=" + modificadoPor
				+ ", ultimaModificacion=" + ultimaModificacion + "]";
	}

}
