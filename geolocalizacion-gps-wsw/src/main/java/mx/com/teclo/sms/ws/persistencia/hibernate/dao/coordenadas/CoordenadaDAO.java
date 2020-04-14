package mx.com.teclo.sms.ws.persistencia.hibernate.dao.coordenadas;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.geolocalizacion.CoordenadaDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface CoordenadaDAO  extends  BaseDao<CoordenadaDTO>{
	
  	public List<CoordenadaDTO> getCoordenadasByRuta(Long idRuta );
	public List<CoordenadaDTO>   getCoordenadasByEmpleado(Long idEmpleado);
	public List<CoordenadaDTO>   getCoordenadasByDate(Long idEmpleado, String fechaInicio, String fechaFin ) throws ParseException ;
	
	/**
	 * @author Kevin Ojeda
	 * @param placa
	 * @param fechaInicial
	 * @param fechaFin
	 * @return
	 * @throws ParseException
	 */
	public List<CoordenadaDTO> getCoordenadasByDate(String placa, Date fechaInicial, Date fechaFin);
	
	/**
	 * @author Kevin Ojeda
	 * @param placa
	 * @param days
	 * @param fI
	 * @param fF
	 * @return List<CoordenadaDTO>
	 */
	public List<CoordenadaDTO> getLastCoordenadaByRange(String placa, int days, Date fI, Date fF);

}