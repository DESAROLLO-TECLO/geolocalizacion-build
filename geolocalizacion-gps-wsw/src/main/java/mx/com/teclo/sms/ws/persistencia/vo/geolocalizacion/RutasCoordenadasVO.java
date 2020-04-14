package mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.vo.ruta.RutaVO;

public class RutasCoordenadasVO {

	private List<CoordenadaVO> listaCordenadasVO;
	private RutaVO rutaVO;

	public List<CoordenadaVO> getListaCordenadasVO() {
		return listaCordenadasVO;
	}

	public void setListaCordenadasVO(List<CoordenadaVO> listaCordenadasVO) {
		this.listaCordenadasVO = listaCordenadasVO;
	}

	public RutaVO getRutaVO() {
		return rutaVO;
	}

	public void setRutaVO(RutaVO rutaVO) {
		this.rutaVO = rutaVO;
	}

}
