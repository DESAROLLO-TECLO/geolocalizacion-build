package mx.com.teclo.alg.ws.negocio.service.eventogps;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.teclo.alg.ws.persistencia.hibernate.dao.dispositivosmoviles.DispositivoMovilTipoDAO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dao.empleados.EmpleadoDAO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dao.eventogps.EventoAlgDAO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dao.eventogps.TipoEventoAlgDAO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilTipoDTO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.empleados.EmpleadosDTO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.eventoalg.EventoAlgDTO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.eventoalg.TipoEventoAlgDTO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.dispositivosmoviles.DispositivoMovilTipoVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.EventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.TipoEventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.response.ReponseEventoAlgVO;
import mx.com.teclo.arquitectura.ortogonales.responsehttp.BadRequestHttpResponse;
import mx.com.teclo.arquitectura.ortogonales.responsehttp.BusinessHttpResponse;
import mx.com.teclo.arquitectura.ortogonales.util.ResponseConverter;
import mx.com.teclo.smm.wsw.util.google.ServicioGoogle;
import mx.com.teclo.sms.ws.util.enums.Catalogos;
import mx.com.teclo.sms.ws.util.enums.Codigos;
import mx.com.teclo.sms.ws.util.enums.Mensajes;

@Service
public class EventoAlgServiceImpl implements EventoAlgService{

	@Autowired
	private EventoAlgDAO eventoAlgDAO;
	
	@Autowired
	private TipoEventoAlgDAO tipoEventoAlgDAO;
	
	@Autowired
	private DispositivoMovilTipoDAO dispositivoMovilTipoDAO;
	
	@Autowired
	private EventoAlgFileService eventoAlgFileService;
	
	@Autowired
	private CollectionService collectionService;
	
	@Autowired
	private EmpleadoDAO empleadoDAO;
	
	private final static Long EMPLEADO_DEFAULT = 99L;
	
	private final static String DEFAULT_CODE = "DEF";
	
	private final static String DEFUALT_TIPO_DISP = "DEF";
	
	private static Boolean regInfo = false;
	
	/**
	 * {@inheritDoc}
	 * @throws BadRequestHttpResponse 
	 */
	@Override
	@Transactional
	public ReponseEventoAlgVO saveEventoAlg(EventoAlgVO eventoVO) throws BusinessHttpResponse, BadRequestHttpResponse{
		
		//List<TipoEventoAlgDTO> tipoEventosAlg = tipoEventoAlgDAO.getTipoEventosActivos();
		List<TipoEventoAlgVO> tipoEventosAlg = collectionService.typeEvent();
		List<DispositivoMovilTipoVO> typeDevice = collectionService.typeDevice();
		
		if(!validObject(eventoVO, tipoEventosAlg)){
			eventoVO.setGuardado(false);
			throw new BadRequestHttpResponse(Mensajes.MSJ_NOT_ENOUGH_DATA.getMensaje(), Codigos.BAD_REQUEST.getCodigoId(), eventoVO);
		} else {
			eventoVO.setGuardado(true);
		}
		
		String descripcionMensaje = Mensajes.MSJ_WARNING_OBJECT_NOT_FOUND.getMensaje();
		int flagCatalogos = 0;
		ReponseEventoAlgVO response = new ReponseEventoAlgVO();
		EventoAlgDTO eventoDTO=null;
	
        eventoDTO = ResponseConverter.copiarPropiedadesFull(eventoVO, EventoAlgDTO.class);
        EmpleadosDTO empleado = empleadoDAO.findUserByPlaca(eventoVO.getPlacaOficial());
        Long empId = (empleado == null ? EMPLEADO_DEFAULT : empleado.getEmpId());
        //Long empId = EMPLEADO_DEFAULT;
        eventoDTO.setIdEmpleado(empId);
		eventoDTO.setEstatusEvento(1);
		eventoDTO.setFechaCreacion(Calendar.getInstance().getTime());
		eventoDTO.setUltimaModificacion(eventoDTO.getFechaCreacion());
		eventoDTO.setCreadoPor(empId);
		eventoDTO.setModificadoPor(empId);
		eventoDTO.setHistorico(0);
		//Se consulta direccion en google maps 
		//eventoDTO.setDireccion(ServicioGoogle.buscarDireccion(eventoVO.getLatitudGps(),eventoVO.getLongitudGps()));
		eventoDTO.setDireccion("--");
		
		// TipoEventoAlgDTO tipoEventoDTO = tipoEventoAlgDAO.activoByCode(eventoVO.getTipoEventoCodigo());
		TipoEventoAlgVO teVO = eventoAlgFileService.filterTypeEvent(tipoEventosAlg, eventoVO.getTipoEventoCodigo());
		Boolean foundTipoEvento = teVO != null; 
		if(!foundTipoEvento){
			eventoDTO.setTipoEventoAlg(tipoEventoAlgDAO.activoByCode(DEFAULT_CODE));
		    descripcionMensaje += Catalogos.TIPO_EVENTO.getNombreCatalogo();
		    flagCatalogos += 1;
		} else {
			TipoEventoAlgDTO tipoEventoDTO = new TipoEventoAlgDTO();
			tipoEventoDTO.setIdTipoEvento(teVO.getIdTipoEvento());
			eventoDTO.setTipoEventoAlg(tipoEventoDTO);
		}
		
		
		//DispositivoMovilTipoDTO dispo = dispositivoMovilTipoDAO.activoByCode(eventoVO.getTipoDispositivoCodigo());
		DispositivoMovilTipoDTO dtDTO = eventoAlgFileService.filterTypeDevice(typeDevice, eventoVO.getTipoDispositivoCodigo());
		Boolean foundTipoDispositivo = dtDTO != null; 
		if(!foundTipoDispositivo){
			flagCatalogos += 1;
			//eventoDTO.setTipoDispositivoMovil(dispositivoMovilTipoDAO.activoByCode(DEFUALT_TIPO_DISP));
			eventoDTO.setTipoDispositivoMovil(eventoAlgFileService.filterTypeDevice(typeDevice, DEFUALT_TIPO_DISP));
			if(flagCatalogos > 0) {
				descripcionMensaje += ", " + Catalogos.TIPO_DISPOSITIVO.getNombreCatalogo();
			}else {
				descripcionMensaje += Catalogos.TIPO_DISPOSITIVO.getNombreCatalogo();
			}
		}else {			
			eventoDTO.setTipoDispositivoMovil(dtDTO);
		}
		
		if(eventoDTO.getIdEmpleado().longValue() == EMPLEADO_DEFAULT.longValue()) {
			flagCatalogos += 1;
			if(flagCatalogos > 0) {
				descripcionMensaje += ", " + Catalogos.EMPLEADO.getNombreCatalogo();
			}else {
				descripcionMensaje += Catalogos.EMPLEADO.getNombreCatalogo();
			}
			
		}
		eventoAlgDAO.save(eventoDTO);
		//response.setEventoAlgVO(ResponseConverter.copiarPropiedadesFull(eventoDTO , EventoAlgVO.class));
		response.setEventoAlgVO(eventoVO);
		response.getEventoAlgVO().setTipoEventoAlg(teVO);
		DispositivoMovilTipoVO dtVO = new DispositivoMovilTipoVO();
		ResponseConverter.copiarPropriedades(dtVO, dtDTO);
		response.getEventoAlgVO().setTipoDispositivoMovil(dtVO);
		if (eventoDTO!=null){
			response.setCodigoHttp(Codigos.CREATE.getCodigoId());
			response.setDescripcion(Mensajes.MSJ_CREATE.getMensaje() + ( (flagCatalogos > 0) ? descripcionMensaje : "") );
		}
		return response;
		
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ReponseEventoAlgVO saveListaEventoAlg(List<EventoAlgVO> listaEventoAlgVO, Integer stHistorico){
		ReponseEventoAlgVO reponseEventoAlgVO = new ReponseEventoAlgVO();
		//List<TipoEventoAlgDTO> tipoEventos = tipoEventoAlgDAO.getTipoEventosActivos();
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
			dto.setDireccion(ServicioGoogle.buscarDireccion(eventoGpsVO.getLatitudGps(),eventoGpsVO.getLongitudGps()));

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
		
//		if (!listaEventoAlgDTO.equals(null)){
//			reponseEventoAlgVO.setCodigoHttp(Codigos.CREATE.getCodigoId());
//			reponseEventoAlgVO.setDescripcion(Mensajes.MSJ_CREATE_LIST.getMensaje() + ((foundObject && foundObject1 && foundObject2) ? "" : descripcionMensaje));
//		}
		
		reponseEventoAlgVO.setListaEventoAlgVO(listaEventoAlgVO);
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
