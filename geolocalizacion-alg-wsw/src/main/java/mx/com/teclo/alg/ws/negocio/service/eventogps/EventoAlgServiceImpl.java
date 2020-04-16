package mx.com.teclo.alg.ws.negocio.service.eventogps;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.teclo.alg.ws.persistencia.hibernate.dao.dispositivosmoviles.DispositivoMovilTipoDAO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dao.empleados.EmpleadoDAO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dao.eventogps.EventoAlgDAO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilTipoDTO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.empleados.EmpleadosDTO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.eventoalg.EventoAlgDTO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.eventoalg.TipoEventoAlgDTO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.dispositivosmoviles.DispositivoMovilTipoVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.EventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.TipoEventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.response.ReponseEventoAlgVO;
import mx.com.teclo.arquitectura.ortogonales.util.ResponseConverter;
import mx.com.teclo.sms.ws.util.enums.Catalogos;
import mx.com.teclo.sms.ws.util.enums.Codigos;
import mx.com.teclo.sms.ws.util.enums.Mensajes;

@Service
public class EventoAlgServiceImpl implements EventoAlgService{

	@Autowired
	private EventoAlgDAO eventoAlgDAO;
	
	@Autowired
	private DispositivoMovilTipoDAO dispositivoMovilTipoDAO;
	
	@Autowired
	private CollectionService collectionService;
	
	@Autowired
	private EmpleadoDAO empleadoDAO;
	
	private final static Long EMPLEADO_DEFAULT = 99L;
	
	private static Boolean regInfo = false;
	
	@Autowired
	private EventoAlgServiceOptBDService eventoAlgServiceOptBDService;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ReponseEventoAlgVO saveListaEventoAlg(List<EventoAlgVO> listaEventoAlgVO, Integer stHistorico){
		ReponseEventoAlgVO reponseEventoAlgVO = new ReponseEventoAlgVO();
		List<TipoEventoAlgVO> tipoEventosAlg = collectionService.typeEvent();
		List<DispositivoMovilTipoDTO> dispositivoMovilTipos = dispositivoMovilTipoDAO.tiposDispositivosActivos();
		
		Map<String, Long> empleados = new HashMap<String, Long>();
		EmpleadosDTO empleadoDefault = empleadoDAO.findOne(EMPLEADO_DEFAULT);
		empleados.put(empleadoDefault.getEmpPlaca(), empleadoDefault.getEmpId());
		
		List<EventoAlgDTO> listaEventoAlgDTO = new ArrayList<>();
		
		boolean foundObject = true;
		boolean foundObject1 = true;
		boolean foundObject2 = true;
		List<TipoEventoAlgVO> tipoEventosRegistradosVO = new ArrayList<TipoEventoAlgVO>();
		List<DispositivoMovilTipoVO> tipoDispositivosRegistradosVO = new ArrayList<DispositivoMovilTipoVO>();
		String descripcionMensaje=Mensajes.MSJ_WARNING_OBJECT_NOT_FOUND.getMensaje();
		String descripcionMensajeNotPersistedObject="";
		int flagCatalogos = 0;
		
		for (EventoAlgVO eventoGpsVO : listaEventoAlgVO) {
			
			eventoGpsVO.setGuardado(true);
			
			if(!validObject(eventoGpsVO, tipoEventosAlg)){
				eventoGpsVO.setGuardado(false);
				descripcionMensajeNotPersistedObject = Mensajes.MSJ_NOT_ENOUGH_DATA.getMensaje();
				continue;
			} else {
				eventoGpsVO.setGuardado(true);
			}
			
			EventoAlgDTO dto = ResponseConverter.copiarPropiedadesFull(eventoGpsVO, EventoAlgDTO.class);
			//dto.setTipoDispositivoMovil(dispositivoMovilTipo);
			
			Long empId = empleados.get(eventoGpsVO.getPlacaOficial());
			if(empId == null){
				EmpleadosDTO empleado = empleadoDAO.findUserByPlaca(eventoGpsVO.getPlacaOficial());
				empId = (empleado == null ? EMPLEADO_DEFAULT : empleado.getEmpId());
				if(empId.longValue() == EMPLEADO_DEFAULT.longValue())
					foundObject2 = false;
				else
					empleados.put(empleado.getEmpPlaca(), empleado.getEmpId());
			}
	        
			dto.setEstatusEvento(1);
			dto.setFechaCreacion(Calendar.getInstance().getTime());
			dto.setUltimaModificacion(dto.getFechaCreacion());
			dto.setCreadoPor(empId);
			dto.setModificadoPor(empId);
			dto.setIdEmpleado(empId);
			dto.setHistorico(stHistorico);
			//Se consulta direccion en google maps 
			//dto.setDireccion(ServicioGoogle.buscarDireccion(eventoGpsVO.getLatitudGps(),eventoGpsVO.getLongitudGps()));
			dto.setDireccion("---");

			boolean found = false;
			TipoEventoAlgDTO teDTO = null;
			for(TipoEventoAlgVO tipoEvento : tipoEventosAlg){
				if(tipoEvento.getCdTipoEvento().equals(eventoGpsVO.getTipoEventoCodigo())){
					teDTO = new TipoEventoAlgDTO();
					teDTO.setIdTipoEvento(tipoEvento.getIdTipoEvento());
					dto.setTipoEventoAlg(teDTO);
					boolean subFound = false;
					for(TipoEventoAlgVO tipoEventoVO : tipoEventosRegistradosVO){
						if(tipoEventoVO.getIdTipoEvento().equals(tipoEvento.getIdTipoEvento())){
							eventoGpsVO.setTipoEventoAlg(tipoEventoVO);
							subFound = true;
							break;
						}
					}
					if(!subFound){
						eventoGpsVO.setTipoEventoAlg(new TipoEventoAlgVO(tipoEvento.getIdTipoEvento(), tipoEvento.getCdTipoEvento(), tipoEvento.getNbTipoEvento()));
						tipoEventosRegistradosVO.add(eventoGpsVO.getTipoEventoAlg());
					}
					found = true;
					break;
				}
			}
			if(!found){
				teDTO = new TipoEventoAlgDTO();
				teDTO.setIdTipoEvento(tipoEventosAlg.get(0).getIdTipoEvento());
				dto.setTipoEventoAlg(teDTO);
				eventoGpsVO.setTipoEventoAlg(tipoEventosAlg.get(0));
				foundObject = false;
			}
			
			found = false;
			for(DispositivoMovilTipoDTO tipoDispositivo : dispositivoMovilTipos){
				if(tipoDispositivo.getCdTipoDispositivo().equals(eventoGpsVO.getTipoDispositivoCodigo())){
					dto.setTipoDispositivoMovil(tipoDispositivo);
					found = true;
					boolean subFound = false;
					for(DispositivoMovilTipoVO tipoDispositivoVO : tipoDispositivosRegistradosVO){
						if(tipoDispositivoVO.getIdTipoDispositivo().equals(tipoDispositivo.getIdTipoDispositivo())){
							eventoGpsVO.setTipoDispositivoMovil(tipoDispositivoVO);
							subFound = true;
							break;
						}
					}
					if(!subFound){
						eventoGpsVO.setTipoDispositivoMovil(new DispositivoMovilTipoVO(tipoDispositivo.getIdTipoDispositivo(), tipoDispositivo.getCdTipoDispositivo(), tipoDispositivo.getNbTipoDispositivo()));
						tipoDispositivosRegistradosVO.add(eventoGpsVO.getTipoDispositivoMovil());
					}
					break;
				}
			}
			if(!found){
				dto.setTipoDispositivoMovil(dispositivoMovilTipos.get(0));
				eventoGpsVO.setTipoDispositivoMovil(ResponseConverter.copiarPropiedadesFull(dispositivoMovilTipos.get(0), DispositivoMovilTipoVO.class));
				foundObject1 = false;
			}
			
			listaEventoAlgDTO.add(dto);
		}
		
		if(!foundObject){
			descripcionMensaje += Catalogos.TIPO_EVENTO.getNombreCatalogo();
			flagCatalogos += 1;
		}
		if(!foundObject1){
			if(flagCatalogos > 0) {
				descripcionMensaje += ", " + Catalogos.TIPO_DISPOSITIVO.getNombreCatalogo();
			}else {
				descripcionMensaje += Catalogos.TIPO_DISPOSITIVO.getNombreCatalogo();
			}
		}
		if(!foundObject2){
			if(flagCatalogos > 0) {
				descripcionMensaje += ", " + Catalogos.EMPLEADO.getNombreCatalogo();
			}else {
				descripcionMensaje += Catalogos.EMPLEADO.getNombreCatalogo();
			}
		}
		
		if(listaEventoAlgDTO.size() > 0) {
			eventoAlgDAO.saveListaEvento(listaEventoAlgDTO);
			reponseEventoAlgVO.setCodigoHttp(Codigos.CREATE.getCodigoId());
			if(!regInfo) {				
				reponseEventoAlgVO.setDescripcion(Mensajes.MSJ_CREATE_LIST.getMensaje() + ( (foundObject && foundObject1 && foundObject2) ? "" : descripcionMensaje) );
			} else {
				reponseEventoAlgVO.setDescripcion(Mensajes.MSJ_CREATE_LIST.getMensaje()+ " " +descripcionMensajeNotPersistedObject
				+ ((foundObject && foundObject1 && foundObject2) ? "" : descripcionMensaje));
			}
		} else {
			reponseEventoAlgVO.setCodigoHttp(Codigos.BAD_REQUEST.getCodigoId());
			reponseEventoAlgVO.setDescripcion(descripcionMensaje + descripcionMensajeNotPersistedObject);
		}
		
		reponseEventoAlgVO.setListaEventoAlgVO(listaEventoAlgVO);
		return reponseEventoAlgVO;
	}
	
	@Override
	public ReponseEventoAlgVO saveArrayEvent (List<EventoAlgVO> listaEventoAlgVO, Integer stHistorico) throws ParseException, SQLException, NamingException{
		
		ReponseEventoAlgVO reponseEventoAlgVO = new ReponseEventoAlgVO();
		List<TipoEventoAlgVO> tipoEventosAlg = collectionService.typeEvent();
		List<EventoAlgVO> eventosNoProcesados = new ArrayList<>();
		
		List<Map<String, Object>> finalsave = new ArrayList<>();
        Map<String, Object> mapSingle = null;
		
		if(listaEventoAlgVO.isEmpty()) {
			reponseEventoAlgVO.setCodigoHttp(Codigos.BAD_REQUEST.getCodigoId());
			reponseEventoAlgVO.setDescripcion(Mensajes.MSJ_NOT_ENOUGH_DATA.getMensaje());
			return reponseEventoAlgVO;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String fhEvento = null;
		
		for (EventoAlgVO eventoGpsVO : listaEventoAlgVO) {
			
			Boolean validObject = validObject(eventoGpsVO, tipoEventosAlg); 
			if(validObject) {
				mapSingle = new HashMap<>();
				mapSingle.put("tipoEventoCodigo", eventoGpsVO.getTipoEventoCodigo());
				mapSingle.put("placaOficial", eventoGpsVO.getPlacaOficial());
				mapSingle.put("tipoDispositivoCodigo", eventoGpsVO.getTipoDispositivoCodigo());
				mapSingle.put("latitudGps", eventoGpsVO.getLatitudGps().toString());
				mapSingle.put("longitudGps",eventoGpsVO.getLongitudGps().toString());
				mapSingle.put("numSerie",eventoGpsVO.getNumSerie());
				mapSingle.put("numImei",eventoGpsVO.getNumImei().toString());
				mapSingle.put("numInfraccion",eventoGpsVO.getNumInfraccion().toString());
			
				fhEvento = sdf.format(eventoGpsVO.getFechaHoraEvento());
				mapSingle.put("fechaHoraEvento",fhEvento);
				mapSingle.put("historico",stHistorico);
				finalsave.add(mapSingle);
			}else {
				eventosNoProcesados.add(eventoGpsVO);
				continue;
			}
		}
		
		// Procesamos todos los datos reutilizando el servicio de guardado del archivo de texto
		eventoAlgServiceOptBDService.saveEvent(finalsave);
		
		if(!eventosNoProcesados.isEmpty()) {
			reponseEventoAlgVO.setCodigoHttp(Codigos.BAD_REQUEST.getCodigoId());
			reponseEventoAlgVO.setDescripcion(Mensajes.MSJ_NOT_ENOUGH_DATA.getMensaje());
			reponseEventoAlgVO.setEventosNoProcesados(eventosNoProcesados);
		}else {
			reponseEventoAlgVO.setCodigoHttp(Codigos.CREATE.getCodigoId());
			reponseEventoAlgVO.setDescripcion(Mensajes.MSJ_CREATE_LIST.getMensaje());
		}
		return reponseEventoAlgVO;
	}
	
	
	@Override
	public Boolean validObject(EventoAlgVO object, List<TipoEventoAlgVO> listEventos){	
		boolean flag = true;
		boolean existeCodigo = false;
		for(TipoEventoAlgVO tipoEvento : listEventos) {
			if(tipoEvento.getCdTipoEvento().equals(object.getTipoEventoCodigo())){
				existeCodigo = true;
				break;
			}
		}
		if((object.getNumSerie() == null || object.getNumSerie().equals("")) ||
			(object.getTipoEventoCodigo() == null || object.getTipoEventoCodigo().equals("")) ||
			(!existeCodigo && !listEventos.get(0).getCdTipoEvento().equals("DEF")) ||
			(object.getLatitudGps() == null || object.getLongitudGps() == null) ||
			(object.getFechaHoraEvento() == null)){
			flag = false;
			regInfo = true;
		}
		
		return flag;
	}
	
}
