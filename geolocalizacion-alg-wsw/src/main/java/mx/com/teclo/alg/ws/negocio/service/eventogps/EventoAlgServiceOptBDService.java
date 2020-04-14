package mx.com.teclo.alg.ws.negocio.service.eventogps;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.request.RequestEventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.parametro.ParametroVO;

public interface EventoAlgServiceOptBDService {

	/**
	 * @Descripcion: Servicio para actualizar el valor
	 * del parámetro que indica procesos activos
	 * @author jorgeluis
	 * @param option
	 * @return Boolean
	 */
	public Boolean updateProcess(String option);
	
	/**
	 * @Descripcion: Servicio para consultar todos los parámetros
	 * @author jorgeluis
	 * @return List<ParametroVO>
	 */
	public List<ParametroVO> restriction();

	/**
	 * @Descripcion: Servicio para guardar los registros
	 * cotenidos en el documento recibido
	 * @author jorgeluis
	 * @return Boolean
	 */
	public Boolean saveEvent(List<Map<String, Object>> map) throws ParseException, SQLException, NamingException; 
	
	/**
	 * @Descripcion: Servicio para guardar bitacora del procesamiento
	 * del archivo
	 * @author jorgeluis
	 * @return RequestEventoAlgVO
	 */
	public RequestEventoAlgVO saveBitac(Map<String, Object> mapResultProcess) throws FileNotFoundException;
}
