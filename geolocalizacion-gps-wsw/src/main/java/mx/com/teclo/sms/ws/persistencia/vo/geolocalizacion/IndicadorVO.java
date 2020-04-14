package mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion;

public class IndicadorVO {
	
	private Long indicadorId;
	private Long indicadorTiempo;//No se ha detectado funcionalidad
	private Long indicadorTiempoInicial;
	private Long indicadorTiempoFinal;
	private String indicadorCodigo;
	private String indicador;
	private String tiempoTranscurrido;
	
	public IndicadorVO(Long indicadorId, Long indicadorTiempo, String indicadorCodigo, String indicador) {
		super();
		this.indicadorId = indicadorId;
		this.indicadorTiempo = indicadorTiempo;
		this.indicadorCodigo = indicadorCodigo;
		this.indicador = indicador;
	}
	
	public IndicadorVO(){
		this(0L,0L,"","");
	}
	public Long getIndicadorId() {
		return indicadorId;
	}
	public void setIndicadorId(Long indicadorId) {
		this.indicadorId = indicadorId;
	}
	public Long getIndicadorTiempo() {
		return indicadorTiempo;
	}
	public void setIndicadorTiempo(Long indicadorTiempo) {
		this.indicadorTiempo = indicadorTiempo;
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
	public String getTiempoTranscurrido() {
		return tiempoTranscurrido;
	}
	public void setTiempoTranscurrido(String tiempoTranscurrido) {
		this.tiempoTranscurrido = tiempoTranscurrido;
	}
	@Override
	public String toString() {
		return "IndicadorVO [indicadorId=" + indicadorId + ", indicadorTiempo=" + indicadorTiempo
				+ ", indicadorTiempoInicial=" + indicadorTiempoInicial + ", indicadorTiempoFinal="
				+ indicadorTiempoFinal + ", indicadorCodigo=" + indicadorCodigo + ", indicador=" + indicador
				+ ", tiempoTranscurrido=" + tiempoTranscurrido + "]";
	}
}
