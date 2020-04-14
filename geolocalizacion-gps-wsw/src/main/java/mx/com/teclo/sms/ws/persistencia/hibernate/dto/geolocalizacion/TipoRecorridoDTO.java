package mx.com.teclo.sms.ws.persistencia.hibernate.dto.geolocalizacion;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TLG003C_TIPO_RECORRIDO")
public class TipoRecorridoDTO implements Serializable {

 
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID_TIPO_RECORRIDO", unique = true, nullable = false)	
	private Long idTipoRecorrido;

	@Column(name = "CD_TIPO_RECORRIDO")
	private String cdTipoRecorrido;
	
	@Column(name = "TX_TIPO_RECORRIDO")
	private String txTipoRecorrido;

	@Column(name = "ST_ACTIVO")
	private Integer stActivo;

	@Column(name = "FH_CREACION")
	private Date fechaCreacion;
	 
	@Column(name = "ID_USR_CREACION")
	private Long creadoPor;

	@Column(name = "FH_MODIFICACION")
	private Date ultimaModificacion;

	@Column(name = "ID_USR_MODIFICA")
	private Long modificadoPor;

	public Long getIdTipoRecorrido() {
		return idTipoRecorrido;
	}

	public void setIdTipoRecorrido(Long idTipoRecorrido) {
		this.idTipoRecorrido = idTipoRecorrido;
	}

	public String getCdTipoRecorrido() {
		return cdTipoRecorrido;
	}

	public void setCdTipoRecorrido(String cdTipoRecorrido) {
		this.cdTipoRecorrido = cdTipoRecorrido;
	}

	public String getTxTipoRecorrido() {
		return txTipoRecorrido;
	}

	public void setTxTipoRecorrido(String txTipoRecorrido) {
		this.txTipoRecorrido = txTipoRecorrido;
	}

	public Integer getStActivo() {
		return stActivo;
	}

	public void setStActivo(Integer stActivo) {
		this.stActivo = stActivo;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Long getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(Long creadoPor) {
		this.creadoPor = creadoPor;
	}

	public Date getUltimaModificacion() {
		return ultimaModificacion;
	}

	public void setUltimaModificacion(Date ultimaModificacion) {
		this.ultimaModificacion = ultimaModificacion;
	}

	public Long getModificadoPor() {
		return modificadoPor;
	}

	public void setModificadoPor(Long modificadoPor) {
		this.modificadoPor = modificadoPor;
	}
	 

}
