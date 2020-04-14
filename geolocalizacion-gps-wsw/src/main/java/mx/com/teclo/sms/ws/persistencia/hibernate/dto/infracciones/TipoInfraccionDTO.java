package mx.com.teclo.sms.ws.persistencia.hibernate.dto.infracciones;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TSAI001C_TIPO_INFRACCION")
public class TipoInfraccionDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 672250199093335052L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID_TIPO_INFRACCION", unique = true)
	private Long idTipoInfraccion;

	@Column(name = "CD_TIPO_INFRACCION")
	private String codigoTipoInfraccion;

	@Column(name = "NB_TIPO_INFRACCION")
	private String nombreTipoInfraccion;
	
	@Column(name = "TX_DESC_INFRACCION")
	private String descripcion;
	
	@Column(name = "NB_DISPOSITIVO")
	private String nombreDispositivo;
	
	@Column(name = "NB_TABLA_DESTINO")
	private String tablaDestino;
	
	@Column(name = "ST_ACTIVO")
	private Integer estatus;
	
	@Column(name = "ST_MAPA")
	private Integer estatusMapa;
	
	@Column(name = "ID_USR_CREACION")
	private Long creadoPor;

	@Column(name = "FH_CREACION")
	private Date fechaCreacion;

	@Column(name = "ID_USR_MODIFICA")
	private Long modificadoPor;

	@Column(name = "FH_MODIFICACION")
	private Date ultimaModificacion;

	public Long getIdTipoInfraccion() {
		return idTipoInfraccion;
	}

	public void setIdTipoInfraccion(Long idTipoInfraccion) {
		this.idTipoInfraccion = idTipoInfraccion;
	}

	public String getCodigoTipoInfraccion() {
		return codigoTipoInfraccion;
	}

	public void setCodigoTipoInfraccion(String codigoTipoInfraccion) {
		this.codigoTipoInfraccion = codigoTipoInfraccion;
	}

	public String getNombreTipoInfraccion() {
		return nombreTipoInfraccion;
	}

	public void setNombreTipoInfraccion(String nombreTipoInfraccion) {
		this.nombreTipoInfraccion = nombreTipoInfraccion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombreDispositivo() {
		return nombreDispositivo;
	}

	public void setNombreDispositivo(String nombreDispositivo) {
		this.nombreDispositivo = nombreDispositivo;
	}

	public String getTablaDestino() {
		return tablaDestino;
	}

	public void setTablaDestino(String tablaDestino) {
		this.tablaDestino = tablaDestino;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public Integer getEstatusMapa() {
		return estatusMapa;
	}

	public void setEstatusMapa(Integer estatusMapa) {
		this.estatusMapa = estatusMapa;
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
		return "TipoInfraccionDTO [idTipoInfraccion=" + idTipoInfraccion + ", codigoTipoInfraccion="
				+ codigoTipoInfraccion + ", nombreTipoInfraccion=" + nombreTipoInfraccion + ", descripcion="
				+ descripcion + ", nombreDispositivo=" + nombreDispositivo + ", tablaDestino=" + tablaDestino
				+ ", estatus=" + estatus + ", estatusMapa=" + estatusMapa + ", creadoPor=" + creadoPor
				+ ", fechaCreacion=" + fechaCreacion + ", modificadoPor=" + modificadoPor + ", ultimaModificacion="
				+ ultimaModificacion + "]";
	}

}
