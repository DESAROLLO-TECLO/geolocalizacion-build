package mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles;

public class DispositivoMovilVO{

	private Long idDispositivo;
	private String numSerie;
	private String numSim;
	private String numIp;

	public DispositivoMovilVO(Long idDispositivo, String numSerie, String numSim, String numIp) {
		super();
		this.idDispositivo = idDispositivo;
		this.numSerie = numSerie;
		this.numSim = numSim;
		this.numIp = numIp;
	}

	public DispositivoMovilVO() {
		super();
	}

	public Long getIdDispositivo() {
		return idDispositivo;
	}

	public void setIdDispositivo(Long idDispositivo) {
		this.idDispositivo = idDispositivo;
	}

	public String getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public String getNumSim() {
		return numSim;
	}

	public void setNumSim(String numSim) {
		this.numSim = numSim;
	}

	public String getNumIp() {
		return numIp;
	}

	public void setNumIp(String numIp) {
		this.numIp = numIp;
	}

	@Override
	public String toString() {
		return "DispositivoMovilVO [idDispositivo=" + idDispositivo + ", numSerie=" + numSerie + ", numSim=" + numSim
				+ ", numIp=" + numIp + "]";
	}

}
