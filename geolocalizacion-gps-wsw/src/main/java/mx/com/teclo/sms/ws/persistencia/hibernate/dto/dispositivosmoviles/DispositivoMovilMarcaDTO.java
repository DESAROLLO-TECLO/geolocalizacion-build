package mx.com.teclo.sms.ws.persistencia.hibernate.dto.dispositivosmoviles;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Kevin Ojeda
 *
 */
@Entity
@Table(name = "TMS011C_DISPOSITIVO_MARCA")
public class DispositivoMovilMarcaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2492518884337240614L;

	@Id
	@Column(name = "ID_DISPOSITIVO_MARCA", unique = true, nullable = false)
	private Long marcaId;
	
	@Column(name = "CD_DIPOSITIVO_MARCA")
	private String marcaCodigo;
	
	@Column(name = "NB_DISPOSITIVO_MARCA")
	private String marca;
	
	@Column(name = "ST_ACTIVO")
	private Integer activo;

	@Column(name = "ID_USR_CREACION")
	private Long creadoPor;

	@Column(name = "FH_CREACION")
	private Date fechaCreacion;

	@Column(name = "ID_USR_MODIFICA")
	private Long modificadoPor;

	@Column(name = "FH_MODIFICACION")
	private Date fechaModificacion;

	public Long getMarcaId() {
		return marcaId;
	}

	public void setMarcaId(Long marcaId) {
		this.marcaId = marcaId;
	}

	public String getMarcaCodigo() {
		return marcaCodigo;
	}

	public void setMarcaCodigo(String marcaCodigo) {
		this.marcaCodigo = marcaCodigo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
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

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	@Override
	public String toString() {
		return "DispositivoMovilMarcaDTO [marcaId=" + marcaId + ", marcaCodigo=" + marcaCodigo + ", marca=" + marca
				+ ", activo=" + activo + ", creadoPor=" + creadoPor + ", fechaCreacion=" + fechaCreacion
				+ ", modificadoPor=" + modificadoPor + ", fechaModificacion=" + fechaModificacion + "]";
	}
	
}
