package mx.com.teclo.sms.ws.persistencia.hibernate.dto.geolocalizacion;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TMS009C_INDICADORES_EVENTOS")
public class IndicadorDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5102447105186585922L;
	
	@Id
	@Column(name = "ID_INDICADOR_GPS", unique = true, nullable = false)
	private Long indicadorId;
	
	@Column(name = "NU_TIEMPO_INICIAL")
	private Long indicadorTiempoInicial;
	
	@Column(name = "NU_TIEMPO_FINAL")
	private Long indicadorTiempoFinal;
	
	@Column(name = "CD_INDICADOR")
	private String indicadorCodigo;
	
	@Column(name = "TX_INDICADOR")
	private String indicador;
	
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

	public Long getIndicadorId() {
		return indicadorId;
	}

	public void setIndicadorId(Long indicadorId) {
		this.indicadorId = indicadorId;
	}

	public Long getIndicadorTiempoInicial() {
		return indicadorTiempoInicial;
	}

	public void setIndicadorTiempoInicial(Long indicadorTiempoInicial) {
		this.indicadorTiempoInicial = indicadorTiempoInicial;
	}

	public Long getIndicadorTiempoFinal() {
		return indicadorTiempoFinal;
	}

	public void setIndicadorTiempoFinal(Long indicadorTiempoFinal) {
		this.indicadorTiempoFinal = indicadorTiempoFinal;
	}

	public String getIndicadorCodigo() {
		return indicadorCodigo;
	}

	public void setIndicadorCodigo(String indicadorCodigo) {
		this.indicadorCodigo = indicadorCodigo;
	}

	public String getIndicador() {
		return indicador;
	}

	public void setIndicador(String indicador) {
		this.indicador = indicador;
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
		return "IndicadorDTO [indicadorId=" + indicadorId + ", indicadorTiempoInicial=" + indicadorTiempoInicial
				+ ", indicadorTiempoFinal=" + indicadorTiempoFinal + ", indicadorCodigo=" + indicadorCodigo
				+ ", indicador=" + indicador + ", activo=" + activo + ", creadoPor=" + creadoPor + ", fechaCreacion="
				+ fechaCreacion + ", modificadoPor=" + modificadoPor + ", fechaModificacion=" + fechaModificacion + "]";
	}

}
