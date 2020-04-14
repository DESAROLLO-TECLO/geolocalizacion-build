package mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles;

import java.util.List;

public class MarcaModeloVO {
	
	private DispositivoMovilMarcaVO marca;
	private List<DispositivoMovilModeloSinMarcaVO> modelos;
	
	public MarcaModeloVO(DispositivoMovilMarcaVO marca,
			List<DispositivoMovilModeloSinMarcaVO> modelos) {
		super();
		this.marca = marca;
		this.modelos = modelos;
	}

	public MarcaModeloVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DispositivoMovilMarcaVO getMarca() {
		return marca;
	}
	public void setMarca(DispositivoMovilMarcaVO marca) {
		this.marca = marca;
	}
	public List<DispositivoMovilModeloSinMarcaVO> getModelos() {
		return modelos;
	}
	public void setModelos(List<DispositivoMovilModeloSinMarcaVO> modelos) {
		this.modelos = modelos;
	}
}
