package mx.com.teclo.sms.ws.negocio.service.eventogps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.lang.Collections;
import mx.com.teclo.arquitectura.ortogonales.responsehttp.PersistenceHttpResponse;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.coordenadas.IndicadorDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.dispositivosmoviles.DispositivoMovilDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.empleado.EmpleadoDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.eventogps.EventoGpsDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.eventogps.TipoEventoGpsDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.EmpleadosDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.geolocalizacion.IndicadorDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.hh.eventogps.EventoGpsDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.hh.eventogps.TipoEventoGpsDTO;
import mx.com.teclo.sms.ws.persistencia.mybatis.dao.eventos.EventosMyBatisDAO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.DispositivoMovilVO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.DispositoMovilEventosGpsVO;
import mx.com.teclo.sms.ws.persistencia.vo.empleado.EmpleadoEventosGpsVO;
import mx.com.teclo.sms.ws.persistencia.vo.empleado.EmpleadosRespectivosAreaOperativaVO;
import mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion.IndicadorVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.EventoGpsIndicadorVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.EventoGpsOficialesVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.EventoGpsVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.MyBatisDispositivoEventoVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.MyBatisOficialEventoVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.SimpleEventoVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.TipoEventoGpsVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.response.ReponseEventoGpsVO;
import mx.com.teclo.sms.ws.util.comun.ResponseConverter;
import mx.com.teclo.sms.ws.util.enums.Catalogos;
import mx.com.teclo.sms.ws.util.enums.Codigos;
import mx.com.teclo.sms.ws.util.enums.Mensajes;

@Service
public class EventoGpsServiceImpl implements EventoGpsService {

	@Autowired
	private EventoGpsDAO eventoGpsDAO;
	
	@Autowired
	private TipoEventoGpsDAO tipoEventoGpsDAO;
	
	@Autowired
	private DispositivoMovilDAO dispositivoMovilDAO;

	@Autowired
	private EmpleadoDAO empleadoDAO;
	
	@Autowired
	private IndicadorDAO indicadorDAO;
	
	@Autowired
	private EventosMyBatisDAO eventosMyBatisDAO;
	
	private final static String DEFAULT_CODE = "DEF";
	
	private final static String DEFUALT_NUM_SERIE = "00000000000000";
	
	private final static Long EMPLEADO_DEFAULT = 99L;

//	@Autowired
//	private UsuarioService usuarioService;
//
//	@Autowired
//	private UsuarioFirmadoService usuarioFirmadoService;
	
	/*
	 *
	 * Persistencia de eventos
	 * 
	 */
	
	/**
	 * {@inheritDoc}
	 * @throws PersistenceHttpResponse 
	 */
	@Override
	@Transactional
	public ReponseEventoGpsVO saveEvento(EventoGpsVO eventoGpsVO){
		String descripcionMensaje="";
		ReponseEventoGpsVO reponseEventoGpsVO = new ReponseEventoGpsVO();

		EventoGpsDTO eventoGpsDTO = ResponseConverter.copiarPropiedadesFull(eventoGpsVO, EventoGpsDTO.class);
		
		TipoEventoGpsDTO tipoEventoGpsDTO = tipoEventoGpsDAO.getTipoEventoByCode(eventoGpsVO.getTipoEventoCodigo());
		Boolean foundObject = tipoEventoGpsDTO != null; 
		eventoGpsDTO.setEstatusEvento("A");
		eventoGpsDTO.setFechaCreacion(eventoGpsVO.getFechaCreacion() == null ? Calendar.getInstance().getTime() : eventoGpsVO.getFechaCreacion());
		eventoGpsDTO.setUltimaModificacion(eventoGpsVO.getFechaCreacion() == null ? Calendar.getInstance().getTime() : eventoGpsVO.getFechaCreacion());
		eventoGpsDTO.setCreadoPor(eventoGpsDTO.getIdEmpleado() == null ? EMPLEADO_DEFAULT : eventoGpsDTO.getIdEmpleado());
		eventoGpsDTO.setModificadoPor(eventoGpsDTO.getIdEmpleado() == null ? EMPLEADO_DEFAULT : eventoGpsDTO.getIdEmpleado());
		eventoGpsDTO.setIdEmpleado(eventoGpsDTO.getIdEmpleado() == null ? EMPLEADO_DEFAULT : eventoGpsDTO.getIdEmpleado());
		if(!foundObject){
			eventoGpsDTO.setTipoEvento(tipoEventoGpsDAO.getTipoEventoByCode(DEFAULT_CODE));
			descripcionMensaje+=Mensajes.MSJ_WARNING_OBJECT_NOT_FOUND.getProcesoId()+Catalogos.TipoEvento.getNombreCatalogo()+".";
		}
		else
			eventoGpsDTO.setTipoEvento(tipoEventoGpsDTO);
		
		
		DispositivoMovilDTO dispo = dispositivoMovilDAO.activoBySerie(eventoGpsVO.getNumSerieHH());
		Boolean foundObject1 = dispo != null; 
		if(!foundObject1){
			eventoGpsDTO.setDispositivoMovil(dispositivoMovilDAO.activoBySerie(DEFUALT_NUM_SERIE));
			descripcionMensaje+=Mensajes.MSJ_WARNING_OBJECT_NOT_FOUND.getProcesoId()+Catalogos.Dispositivos.getNombreCatalogo();
		}
		else
			eventoGpsDTO.setDispositivoMovil(dispo);
		
		
		EventoGpsDTO eventoDTO = eventoGpsDAO.saveOrUpdate(eventoGpsDTO);
 		reponseEventoGpsVO.setEventoGpsVO(ResponseConverter.copiarPropiedadesFull(eventoDTO , EventoGpsVO.class));
 		reponseEventoGpsVO.getEventoGpsVO().setTipoEvento(ResponseConverter.copiarPropiedadesFull(eventoDTO.getTipoEvento(), TipoEventoGpsVO.class));
		if (eventoGpsDTO!=null){
			reponseEventoGpsVO.setCodigoHttp(Codigos.CREATE.getProcesoId());
			reponseEventoGpsVO.setDescripcion(Mensajes.MSJ_CREATE.getProcesoId() + descripcionMensaje);
		}
		return reponseEventoGpsVO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ReponseEventoGpsVO saveListaEvento(List<EventoGpsVO> listaEventoGpsVO){
		ReponseEventoGpsVO reponseEventoGpsVO = new ReponseEventoGpsVO();
		List<TipoEventoGpsDTO> tipoEventos = tipoEventoGpsDAO.getTipoEventosActivos();
		Map<String, DispositivoMovilDTO> dispositivos = new HashMap<String, DispositivoMovilDTO>();
		Map<String, DispositivoMovilVO> dispositivosVO = new HashMap<String, DispositivoMovilVO>();
		DispositivoMovilDTO dispositivoMovilDefault = dispositivoMovilDAO.activoBySerie(DEFUALT_NUM_SERIE);
		dispositivos.put(dispositivoMovilDefault.getNumSerie(), dispositivoMovilDefault);
		dispositivosVO.put(dispositivoMovilDefault.getNumSerie(), ResponseConverter.copiarPropiedadesFull(dispositivoMovilDefault, DispositivoMovilVO.class));
		List<EventoGpsDTO> listaEventoGpsDTO = new ArrayList<>();
		boolean foundObject = true;
		boolean foundObject1 = true;
		List<TipoEventoGpsVO> tipoEventosRegistradosVO = new ArrayList<TipoEventoGpsVO>();
		String descripcionMensaje="";
		for (EventoGpsVO eventoGpsVO : listaEventoGpsVO) {
			EventoGpsDTO dto = ResponseConverter.copiarPropiedadesFull(eventoGpsVO, EventoGpsDTO.class);
			dto.setDispositivoMovil(dispositivos.get(eventoGpsVO.getNumSerieHH()));
			if(dto.getDispositivoMovil() == null){
				DispositivoMovilDTO dispositivoMovil= dispositivoMovilDAO.activoBySerie(eventoGpsVO.getNumSerieHH());
				if(dispositivoMovil != null){
					dto.setDispositivoMovil(dispositivoMovil);
					dispositivos.put(dispositivoMovil.getNumSerie(), dispositivoMovil);
					dispositivosVO.put(dispositivoMovil.getNumSerie(), ResponseConverter.copiarPropiedadesFull(dispositivoMovil, DispositivoMovilVO.class));
					eventoGpsVO.setDispositivoMovil(dispositivosVO.get(dispositivoMovil.getNumSerie()));
				}else{
					foundObject1 = false;
					dto.setDispositivoMovil(dispositivoMovilDefault);
					eventoGpsVO.setDispositivoMovil(dispositivosVO.get(dispositivoMovilDefault.getNumSerie()));
				}
			}else{
				eventoGpsVO.setDispositivoMovil(dispositivosVO.get(dto.getDispositivoMovil().getNumSerie()));
			}
			dto.setEstatusEvento("A");
			dto.setFechaCreacion(eventoGpsVO.getFechaCreacion() == null ? Calendar.getInstance().getTime() : eventoGpsVO.getFechaCreacion());
			dto.setUltimaModificacion(eventoGpsVO.getFechaCreacion() == null ? Calendar.getInstance().getTime() : eventoGpsVO.getFechaCreacion());
			dto.setCreadoPor(dto.getIdEmpleado() == null ? EMPLEADO_DEFAULT : dto.getIdEmpleado());
			dto.setModificadoPor(dto.getIdEmpleado() == null ? EMPLEADO_DEFAULT : dto.getIdEmpleado());
			dto.setIdEmpleado(dto.getIdEmpleado() == null ? EMPLEADO_DEFAULT : dto.getIdEmpleado());
			boolean found = false;
			for(TipoEventoGpsDTO tipoEvento : tipoEventos){
				if(tipoEvento.getCodigoTipoEvento().equals(eventoGpsVO.getTipoEventoCodigo())){
					dto.setTipoEvento(tipoEvento);
					boolean subFound = false;
					for(TipoEventoGpsVO tipoEventoVO : tipoEventosRegistradosVO){
						eventoGpsVO.setTipoEvento(tipoEventoVO);
						subFound = true;
						break;
					}
					if(!subFound){
						eventoGpsVO.setTipoEvento(new TipoEventoGpsVO(tipoEvento.getIdTipoEvento(), tipoEvento.getCodigoTipoEvento(), tipoEvento.getNombreTipoEvento(), tipoEvento.getEstatusEvento()));
						tipoEventosRegistradosVO.add(eventoGpsVO.getTipoEvento());
					}
					found = true;
					break;
				}
			}
			if(!found){
				dto.setTipoEvento(tipoEventos.get(0));
				eventoGpsVO.setTipoEvento(ResponseConverter.copiarPropiedadesFull(tipoEventos.get(0), TipoEventoGpsVO.class));
				foundObject = false;
			}
			
			listaEventoGpsDTO.add(dto);
		}
		
		if(!foundObject){
			descripcionMensaje+=Mensajes.MSJ_WARNING_OBJECT_NOT_FOUND.getProcesoId()+Catalogos.TipoEvento.getNombreCatalogo()+".";
		}
		if(!foundObject1){
			descripcionMensaje+=Mensajes.MSJ_WARNING_OBJECT_NOT_FOUND.getProcesoId()+Catalogos.Dispositivos.getNombreCatalogo()+".";
		}
		eventoGpsDAO.saveListaEvento(listaEventoGpsDTO);
		reponseEventoGpsVO.setListaEventoGpsVO(listaEventoGpsVO);

		if (!listaEventoGpsDTO.equals(null)){
			reponseEventoGpsVO.setCodigoHttp(Codigos.CREATE.getProcesoId());
			reponseEventoGpsVO.setDescripcion(Mensajes.MSJ_CREATE_LIST.getProcesoId() + (foundObject && foundObject1 ? "" : descripcionMensaje));
		}
		
		return reponseEventoGpsVO;
	}
	
	/*
	 * 
	 * Eventos por numero de serie de HH (Optimizado)
	 * 
	 */
		
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<MyBatisDispositivoEventoVO> optimizedEventosByHHAndFechas(String[] series, Date fI){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(fI);
		c1.set(Calendar.HOUR_OF_DAY, 23);
		c1.set(Calendar.MINUTE, 59);
		fI = c1.getTime();
		Date fF = fI;
		Calendar c2 = Calendar.getInstance();
		c2.setTime(fF);
		c2.add(Calendar.DAY_OF_MONTH, -30);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MINUTE, 0);
		fF = c2.getTime();
		Date now = Calendar.getInstance().getTime();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sd = sdf.format(fI);
		String sd2 = sdf.format(fF);
		String list = "'" + StringUtils.join(series,"','") + "'";
		List<MyBatisDispositivoEventoVO> eventos = eventosMyBatisDAO.eventosPorDispositivo(list, sd2, sd, sdf.format(now));
		if(!eventos.isEmpty()){
			return eventos;
		}
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<MyBatisOficialEventoVO> optimizedEventosByPlacasAndFechas(String[] placas, Date fI){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(fI);
		c1.set(Calendar.HOUR_OF_DAY, 23);
		c1.set(Calendar.MINUTE, 59);
		fI = c1.getTime();
		Date fF = fI;
		Calendar c2 = Calendar.getInstance();
		c2.setTime(fF);
		c2.add(Calendar.DAY_OF_MONTH, -30);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MINUTE, 0);
		fF = c2.getTime();
		Date now = Calendar.getInstance().getTime();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sd = sdf.format(fI);
		String sd2 = sdf.format(fF);
		String list = "'" + StringUtils.join(placas,"','") + "'";
		List<MyBatisOficialEventoVO> eventos = eventosMyBatisDAO.eventosPorOficiales(list, sd2, sd, sdf.format(now));
		if(!eventos.isEmpty()){
			return eventos;
		}
		return null;
	}
	
//	@Override
//	@Transactional
//	public List<EventoGpsOficialesVO> optimizedEventosByPlacasAndFechas(String[] plcs, Date fI) {
//		Calendar c1 = Calendar.getInstance();
//		c1.setTime(fI);
//		c1.set(Calendar.HOUR_OF_DAY, 0);
//		c1.set(Calendar.MINUTE, 0);
//		fI = c1.getTime();
//		Date fF = fI;
//		Calendar c2 = Calendar.getInstance();
//		c2.setTime(fF);
//		c2.set(Calendar.HOUR_OF_DAY, 23);
//		c2.set(Calendar.MINUTE, 59);
//		fF = c2.getTime();
//		List<EventoGpsDTO> eventosDTO = eventoGpsDAO.optimizedLastEventsByNumDiasPlacasAndFecha( new ArrayList<>(Arrays.asList(plcs)), 30, fI, fF);
//		if(!eventosDTO.isEmpty()){
//			return ResponseConverter.converterLista(new ArrayList<EventoGpsOficialesVO>(), eventosDTO, EventoGpsOficialesVO.class);
//			return assignIndicadorOfficials(ResponseConverter.converterLista(new ArrayList<EventoGpsOficialesVO>(), eventosDTO, EventoGpsOficialesVO.class));
//		}
//		return null;
//	}
	
	public List<EventoGpsIndicadorVO> assignIndicador(List<EventoGpsIndicadorVO> vos){
		Calendar now = Calendar.getInstance();
		Long diff;
		Long miliS;
		Long minutosTranscurridos;
		Long horasTranscurridas;
		Long diasTransCurridos;
		String tiempoTranscurrido;
		IndicadorVO indicadorDef = new IndicadorVO(0L,0L,"#CF0A2C","DEFAULT");
		List<IndicadorVO>indicadores = lDtoTolVO(indicadorDAO.allActivos());
		
//		for(EventoGpsIndicadorVO event : vos){
//			event.setIndicador(indicadorDef);
//			miliS = event.getFechaHoraEvento().getTime();
//			diff = now.getTime().getTime() - miliS;
//			minutosTranscurridos = diff / (60 * 1000);
//			for(IndicadorVO indicador : indicadores){
//				if(indicador.getIndicadorTiempoFinal() >= minutosTranscurridos && indicador.getIndicadorTiempoInicial() <= minutosTranscurridos){
//					event.setIndicador(indicador);
//					break;
//				}
//			}
//			
//			horasTranscurridas = minutosTranscurridos /60;
//			Math.floor(horasTranscurridas);
//			minutosTranscurridos -= (horasTranscurridas*60);
//			diasTransCurridos = 0L;
//			if(horasTranscurridas.longValue()>24L){
//				diasTransCurridos = horasTranscurridas / 24;
//				Math.floor(diasTransCurridos);
//			}
//			horasTranscurridas -= (diasTransCurridos*24);
//			
//			tiempoTranscurrido = "";
//			Boolean dias = false;
//			if(diasTransCurridos.longValue()>0L){
//				dias = true;
//				tiempoTranscurrido = diasTransCurridos.toString() + (diasTransCurridos.longValue()>1L ? " días" : " día");
//			}
//			Boolean horas = false;
//			if(horasTranscurridas.longValue()>0L){
//				horas = true;
//				tiempoTranscurrido += dias ? ", " : "";
//				tiempoTranscurrido += horasTranscurridas.toString() + (horasTranscurridas.longValue()>1L ? " Horas" : " Hora");
//			}
//			if(minutosTranscurridos.longValue()>0L){
//				tiempoTranscurrido += horas || dias ? " y " : " ";
//				tiempoTranscurrido += minutosTranscurridos.toString() + (minutosTranscurridos.longValue()>1L ? " Minutos " : " Minuto ");
//			}
//			event.getIndicador().setTiempoTranscurrido(tiempoTranscurrido);
//		}
		return vos;
	}
	
	public List<EventoGpsOficialesVO> assignIndicadorOfficials(List<EventoGpsOficialesVO> vos){
		Calendar now = Calendar.getInstance();
		Long diff;
		Long miliS;
		Long minutosTranscurridos;
		Long horasTranscurridas;
		Long diasTransCurridos;
		String tiempoTranscurrido;
		IndicadorVO indicadorDef = new IndicadorVO(0L,0L,"#CF0A2C","DEFAULT");
		List<IndicadorVO>indicadores = lDtoTolVO(indicadorDAO.allActivos());
		
//		List<TipoEventoGpsDTO> tipoEventos = tipoEventoGpsDAO.getTipoEventosActivos();
//		List<TipoEventoGpsVO> tipoEventosVO = ResponseConverter.converterLista(new ArrayList<ReponseEventoGpsVO>(), tipoEventos, TipoEventoGpsVO.class);
		for(EventoGpsOficialesVO event : vos){
			event.setIndicador(indicadorDef);
			miliS = event.getFechaHoraEvento().getTime();
			diff = now.getTime().getTime() - miliS;
			minutosTranscurridos = diff / (60 * 1000);
			for(IndicadorVO indicador : indicadores){
				if(indicador.getIndicadorTiempoFinal() >= minutosTranscurridos && indicador.getIndicadorTiempoInicial() <= minutosTranscurridos){
					event.setIndicador(indicador);
					break;
				}
			}
			
			horasTranscurridas = minutosTranscurridos /60;
			Math.floor(horasTranscurridas);
			minutosTranscurridos -= (horasTranscurridas*60);
			diasTransCurridos = 0L;
			if(horasTranscurridas.longValue()>24L){
				diasTransCurridos = horasTranscurridas / 24;
				Math.floor(diasTransCurridos);
			}
			horasTranscurridas -= (diasTransCurridos*24);
			
			tiempoTranscurrido = "";
			Boolean dias = false;
			if(diasTransCurridos.longValue()>0L){
				dias = true;
				tiempoTranscurrido = diasTransCurridos.toString() + (diasTransCurridos.longValue()>1L ? " días" : " día");
			}
			Boolean horas = false;
			if(horasTranscurridas.longValue()>0L){
				horas = true;
				tiempoTranscurrido += dias ? ", " : "";
				tiempoTranscurrido += horasTranscurridas.toString() + (horasTranscurridas.longValue()>1L ? " Horas" : " Hora");
			}
			if(minutosTranscurridos.longValue()>0L){
				tiempoTranscurrido += horas || dias ? " y " : " ";
				tiempoTranscurrido += minutosTranscurridos.toString() + (minutosTranscurridos.longValue()>1L ? " Minutos " : " Minuto ");
			}
			event.getIndicador().setTiempoTranscurrido(tiempoTranscurrido);
		}
		return vos;
	}
	
	@Transactional
	@Override
	public List<SimpleEventoVO> eventosByItem(Long empId, String numSerie, Date fI){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(fI);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		fI = c1.getTime();
		Date fF = fI;
		Calendar c2 = Calendar.getInstance();
		c2.setTime(fF);
		c2.set(Calendar.HOUR_OF_DAY, 23);
		c2.set(Calendar.MINUTE, 59);
		fF = c2.getTime();
		List<EventoGpsDTO> dtos;
		if(empId.longValue()!=-1L)
			dtos = eventoGpsDAO.byUniqueEmpIdAndFecha(empId, fI, fF);
		else if(!numSerie.equals("-1"))
			dtos = eventoGpsDAO.byUniqueHHAndFecha(numSerie, fI, fF);
		else
			return null;
		if(Collections.isEmpty(dtos)) return null;
		return assignSimpleEventIndicador(ResponseConverter.converterLista(new ArrayList<SimpleEventoVO>(), dtos, SimpleEventoVO.class));
	}
	
	public List<SimpleEventoVO> assignSimpleEventIndicador(List<SimpleEventoVO> vos){
		Calendar now = Calendar.getInstance();
		Long diff;
		Long miliS;
		Long minutosTranscurridos;
		Long horasTranscurridas;
		Long diasTransCurridos;
		String tiempoTranscurrido;
		IndicadorVO indicadorDef = new IndicadorVO(0L,0L,"#CF0A2C","DEFAULT");
		List<IndicadorVO>indicadores = lDtoTolVO(indicadorDAO.allActivos());
		
//		List<TipoEventoGpsDTO> tipoEventos = tipoEventoGpsDAO.getTipoEventosActivos();
//		List<TipoEventoGpsVO> tipoEventosVO = ResponseConverter.converterLista(new ArrayList<ReponseEventoGpsVO>(), tipoEventos, TipoEventoGpsVO.class);
		for(SimpleEventoVO event : vos){
			event.setIndicador(indicadorDef);
			miliS = event.getFechaHoraEvento().getTime();
			diff = now.getTime().getTime() - miliS;
			minutosTranscurridos = diff / (60 * 1000);
			for(IndicadorVO indicador : indicadores){
				if(indicador.getIndicadorTiempoFinal() >= minutosTranscurridos && indicador.getIndicadorTiempoInicial() <= minutosTranscurridos){
					event.setIndicador(indicador);
					break;
				}
			}
			
			horasTranscurridas = minutosTranscurridos /60;
			Math.floor(horasTranscurridas);
			minutosTranscurridos -= (horasTranscurridas*60);
			diasTransCurridos = 0L;
			if(horasTranscurridas.longValue()>24L){
				diasTransCurridos = horasTranscurridas / 24;
				Math.floor(diasTransCurridos);
			}
			horasTranscurridas -= (diasTransCurridos*24);
			
			tiempoTranscurrido = "";
			Boolean dias = false;
			if(diasTransCurridos.longValue()>0L){
				dias = true;
				tiempoTranscurrido = diasTransCurridos.toString() + (diasTransCurridos.longValue()>1L ? " días" : " día");
			}
			Boolean horas = false;
			if(horasTranscurridas.longValue()>0L){
				horas = true;
				tiempoTranscurrido += dias ? ", " : "";
				tiempoTranscurrido += horasTranscurridas.toString() + (horasTranscurridas.longValue()>1L ? " Horas" : " Hora");
			}
			if(minutosTranscurridos.longValue()>0L){
				tiempoTranscurrido += horas || dias ? " y " : " ";
				tiempoTranscurrido += minutosTranscurridos.toString() + (minutosTranscurridos.longValue()>1L ? " Minutos " : " Minuto ");
			}
			event.getIndicador().setTiempoTranscurrido(tiempoTranscurrido);
		}
		return vos;
	}
	/*
	 * 
	 * Eventos por numero de serie de HanHeld
	 * 
	 */
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<DispositoMovilEventosGpsVO> eventosByHHAndFechas(String[] series, Date fI, Date fF){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(fI);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		fI = c1.getTime();
		Calendar c2 = Calendar.getInstance();
		c2.setTime(fF);
		c2.set(Calendar.HOUR_OF_DAY, 23);
		c2.set(Calendar.MINUTE, 59);
		fF = c2.getTime();
		ArrayList<String> numSeries = new ArrayList<>(Arrays.asList(series));
		
		List<EventoGpsDTO> eventosDTO = eventoGpsDAO.byHHAndFecha(numSeries, fI, fF);
		List<List<EventoGpsDTO>> allSplitEvents = splitEventosByHHSerie(eventosDTO);
		
		numSeries = (ArrayList<String>) seriesNotFoundOnDate(allSplitEvents, numSeries);
		allSplitEvents.addAll(eventoGpsDAO.lastEventsByNumDiasHHSerieAndFecha(numSeries, 30, fI, fF));
		
		if(Collections.isEmpty(allSplitEvents)) return null;
		return generateDispositoMovilEventosList(allSplitEvents);
	}
	
	private List<List<EventoGpsDTO>> splitEventosByHHSerie(List<EventoGpsDTO> allMixedEvents){
		List<List<EventoGpsDTO>> allSplitEvents = new ArrayList<>();
		for(EventoGpsDTO singleEvent : allMixedEvents){
			boolean found = false;
			for(List<EventoGpsDTO> conjunto : allSplitEvents){
				if(conjunto.get(0).getNumSerieHH().equals(singleEvent.getNumSerieHH())){
					conjunto.add(singleEvent);
					found = true;
					break;
				}
			}
			if(!found){
				List<EventoGpsDTO> newHHSerie = new ArrayList<EventoGpsDTO>();
				newHHSerie.add(singleEvent);
				allSplitEvents.add(newHHSerie);
			}
		}
		return allSplitEvents;
	}
	
	private List<String> seriesNotFoundOnDate(List<List<EventoGpsDTO>> allSplitEvents, List<String> allSeries){
		List<String> seriesNotFound = new ArrayList<String>();
		for(String serie : allSeries){
			boolean found = false;
			for(List<EventoGpsDTO> listEvent : allSplitEvents){
				if(listEvent.get(0).getNumSerieHH().equals(serie)){
					found = true;
					break;
				}
			}
			if(!found){
				seriesNotFound.add(serie);
			}
		}
		return seriesNotFound;
	}
	
	private List<DispositoMovilEventosGpsVO> generateDispositoMovilEventosList (List<List<EventoGpsDTO>> allSplitEvents){
		List<DispositoMovilEventosGpsVO> generatedObjects = new ArrayList<DispositoMovilEventosGpsVO>();
		DispositivoMovilVO dispositivo;
		List<EventoGpsVO> eventosVO;
		DispositoMovilEventosGpsVO generatedObject;
		List<IndicadorVO> indicadores = new ArrayList<IndicadorVO>();
		
		//ResponseConverter.converterLista(indicadores, indicadorDAO.allActivos(), IndicadorVO.class);
		indicadores = lDtoTolVO(indicadorDAO.allActivos());
		Calendar now = Calendar.getInstance();
		Long diff;
		Long miliS;
		Long minutosTranscurridos;
		Long horasTranscurridas;
		Long diasTransCurridos;
		String tiempoTranscurrido;
		IndicadorVO indicadorDef = new IndicadorVO(0L,0L,"#CF0A2C","DEFAULT");
		
		List<TipoEventoGpsDTO> tipoEventos = tipoEventoGpsDAO.getTipoEventosActivos();
		List<TipoEventoGpsVO> tipoEventosVO = ResponseConverter.converterLista(new ArrayList<ReponseEventoGpsVO>(), tipoEventos, TipoEventoGpsVO.class);
		
		for(List<EventoGpsDTO> listEvent : allSplitEvents){
			if(listEvent.size() == 0) continue;
			dispositivo = ResponseConverter.copiarPropiedadesFull(listEvent.get(0).getDispositivoMovil(),DispositivoMovilVO.class);
			eventosVO = ResponseConverter.converterLista(new ArrayList<>(), listEvent, EventoGpsVO.class);
			generatedObject = new DispositoMovilEventosGpsVO();
			generatedObject.setDispositivo(dispositivo);
			generatedObject.setIndicador(indicadorDef);
			
			for(int i = 0; i <  listEvent.size(); i ++){
				boolean found = false;
				for(TipoEventoGpsVO tipoEvento : tipoEventosVO){
					if(listEvent.get(i).getTipoEvento().getIdTipoEvento().equals(tipoEvento.getIdTipoEvento())){
						eventosVO.get(i).setTipoEvento(tipoEvento);
						found = true;
						break;
					}
				}
				if(!found){
					eventosVO.get(i).setTipoEvento(tipoEventosVO.get(0));
				}
			}
			generatedObject.setEventos(eventosVO);
			miliS = listEvent.get(listEvent.size()-1).getFechaHoraEvento().getTime();
			diff = now.getTime().getTime() - miliS;
			minutosTranscurridos = diff / (60 * 1000);
			
			for(IndicadorVO indicador : indicadores){
				if(indicador.getIndicadorTiempoFinal() >= minutosTranscurridos && indicador.getIndicadorTiempoInicial() <= minutosTranscurridos){
					generatedObject.setIndicador(indicador);
					break;
				}
			}
			
			horasTranscurridas = minutosTranscurridos /60;
			Math.floor(horasTranscurridas);
			minutosTranscurridos -= (horasTranscurridas*60);
			diasTransCurridos = 0L;
			if(horasTranscurridas.longValue()>24L){
				diasTransCurridos = horasTranscurridas / 24;
				Math.floor(diasTransCurridos);
			}
			horasTranscurridas -= (diasTransCurridos*24);
			
			tiempoTranscurrido = "";
			Boolean dias = false;
			if(diasTransCurridos.longValue()>0L){
				dias = true;
				tiempoTranscurrido = diasTransCurridos.toString() + (diasTransCurridos.longValue()>1L ? " días" : " día");
			}
			Boolean horas = false;
			if(horasTranscurridas.longValue()>0L){
				horas = true;
				tiempoTranscurrido += dias ? ", " : "";
				tiempoTranscurrido += horasTranscurridas.toString() + (horasTranscurridas.longValue()>1L ? " Horas" : " Hora");
			}
			if(minutosTranscurridos.longValue()>0L){
				tiempoTranscurrido += horas || dias ? " y " : " ";
				tiempoTranscurrido += minutosTranscurridos.toString() + (minutosTranscurridos.longValue()>1L ? " Minutos " : " Minuto ");
			}
			generatedObject.getIndicador().setTiempoTranscurrido(tiempoTranscurrido);
			
			generatedObjects.add(generatedObject);
		}
		if(Collections.isEmpty(generatedObjects)) return null;
		return generatedObjects;
	}
	
	private List<IndicadorVO> lDtoTolVO(List<IndicadorDTO> lDTO){
		List<IndicadorVO> lVO = new ArrayList<IndicadorVO>();
		for(IndicadorDTO dto : lDTO){
			IndicadorVO vo = new IndicadorVO();
			vo.setIndicador(dto.getIndicador());
			vo.setIndicadorCodigo(dto.getIndicadorCodigo());
			vo.setIndicadorId(dto.getIndicadorId());
			vo.setIndicadorTiempoFinal(dto.getIndicadorTiempoFinal());
			vo.setIndicadorTiempoInicial(dto.getIndicadorTiempoInicial());
			lVO.add(vo);
		}
		return lVO;
	}

	/*
	 * 
	 * Eventos por placa de oficial
	 * 
	 */
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<EmpleadoEventosGpsVO> eventosByPlacasAndFechas(String[] plcs, Date fI, Date fF){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(fI);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		fI = c1.getTime();
		Calendar c2 = Calendar.getInstance();
		c2.setTime(fF);
		c2.set(Calendar.HOUR_OF_DAY, 23);
		c2.set(Calendar.MINUTE, 59);
		fF = c2.getTime();
		ArrayList<String> placas = new ArrayList<>(Arrays.asList(plcs));
		ArrayList<Long> ids = new ArrayList<Long>();
		for(EmpleadosDTO emp : empleadoDAO.findUsersByPlacas(placas)){
			ids.add(emp.getEmpId());
		}
		
		List<EventoGpsDTO> eventosDTO = eventoGpsDAO.byEmpIdsAndFecha(ids, fI, fF);
		List<List<EventoGpsDTO>> allSplitEvents = splitEventosByEmpId(eventosDTO);
		
		ids = (ArrayList<Long>) idsNotFoundOnDate(allSplitEvents, ids);
		allSplitEvents.addAll(eventoGpsDAO.lastEventsByEmpIdsAndFecha(ids, 30, fI, fF));
		if(Collections.isEmpty(allSplitEvents)) return null;
		return generateEmpleadoEventosGps(allSplitEvents);
	}
	
	private List<List<EventoGpsDTO>> splitEventosByEmpId(List<EventoGpsDTO> allMixedEvents){
		List<List<EventoGpsDTO>> allSplitEvents = new ArrayList<>();
		for(EventoGpsDTO singleEvent : allMixedEvents){
			boolean found = false;
			for(List<EventoGpsDTO> conjunto : allSplitEvents){
				if(conjunto.get(0).getIdEmpleado().equals(singleEvent.getIdEmpleado())){
					conjunto.add(singleEvent);
					found = true;
					break;
				}
			}
			if(!found){
				List<EventoGpsDTO> newHHSerie = new ArrayList<EventoGpsDTO>();
				newHHSerie.add(singleEvent);
				allSplitEvents.add(newHHSerie);
			}
		}
		return allSplitEvents;
	}
	
	private List<Long> idsNotFoundOnDate(List<List<EventoGpsDTO>> allSplitEvents, List<Long> allIds){
		List<Long> idsNotFound = new ArrayList<Long>();
		for(Long id : allIds){
			boolean found = false;
			for(List<EventoGpsDTO> listEvent : allSplitEvents){
				if(listEvent.get(0).getIdEmpleado().equals(id)){
					found = true;
					break;
				}
			}
			if(!found){
				idsNotFound.add(id);
			}
		}
		return idsNotFound;
	}
	
	private List<EmpleadoEventosGpsVO> generateEmpleadoEventosGps (List<List<EventoGpsDTO>> allSplitEvents){
		List<EmpleadoEventosGpsVO> generatedObjects = new ArrayList<EmpleadoEventosGpsVO>();
		EmpleadosRespectivosAreaOperativaVO empleado;
		List<EventoGpsVO> eventosVO;
		EmpleadoEventosGpsVO generatedObject;
		List<IndicadorVO> indicadores = new ArrayList<IndicadorVO>();
		indicadores = lDtoTolVO(indicadorDAO.allActivos());
		Calendar now = Calendar.getInstance();
		Long diff;
		Long miliS;
		Long minutosTranscurridos;
		Long horasTranscurridas;
		Long diasTransCurridos;
		String tiempoTranscurrido;
		IndicadorVO indicadorDef = new IndicadorVO(0L,0L,"#607D8B","DEFAULT");
		
		List<TipoEventoGpsDTO> tipoEventos = tipoEventoGpsDAO.getTipoEventosActivos();
		List<TipoEventoGpsVO> tipoEventosVO = ResponseConverter.converterLista(new ArrayList<ReponseEventoGpsVO>(), tipoEventos, TipoEventoGpsVO.class);
		
		for(List<EventoGpsDTO> listEvent : allSplitEvents){
			empleado = ResponseConverter.copiarPropiedadesFull(empleadoDAO.findOne(listEvent.get(0).getIdEmpleado()),EmpleadosRespectivosAreaOperativaVO.class);
			//eventosVO = new ArrayList<EventoGpsVO>();
			eventosVO = ResponseConverter.converterLista(new ArrayList<>(), listEvent, EventoGpsVO.class);
			generatedObject = new EmpleadoEventosGpsVO();
			generatedObject.setEmpleado(empleado);
			generatedObject.setIndicador(indicadorDef);

			for(int i = 0; i <  listEvent.size(); i ++){
				boolean found = false;
				for(TipoEventoGpsVO tipoEvento : tipoEventosVO){
					if(listEvent.get(i).getTipoEvento().getIdTipoEvento().equals(tipoEvento.getIdTipoEvento())){
						eventosVO.get(i).setTipoEvento(tipoEvento);
						found = true;
						break;
					}
				}
				if(!found){
					eventosVO.get(i).setTipoEvento(tipoEventosVO.get(0));
				}
			}
			generatedObject.setEventos(eventosVO);
			
			miliS = listEvent.get(listEvent.size()-1).getFechaHoraEvento().getTime();
			diff = now.getTime().getTime() - miliS;
			minutosTranscurridos = diff / (60 * 1000);
			
			for(IndicadorVO indicador : indicadores){
				if(indicador.getIndicadorTiempoFinal() >= minutosTranscurridos && indicador.getIndicadorTiempoInicial() <= minutosTranscurridos){
					generatedObject.setIndicador(indicador);
					break;
				}
			}
			
			horasTranscurridas = minutosTranscurridos /60;
			Math.floor(horasTranscurridas);
			minutosTranscurridos -= (horasTranscurridas*60);
			diasTransCurridos = 0L;
			if(horasTranscurridas.longValue()>24L){
				diasTransCurridos = horasTranscurridas / 24;
				Math.floor(diasTransCurridos);
			}
			horasTranscurridas -= (diasTransCurridos*24);
			
			tiempoTranscurrido = "";
			Boolean dias = false;
			if(diasTransCurridos.longValue()>0L){
				dias = true;
				tiempoTranscurrido = diasTransCurridos.toString() + (diasTransCurridos.longValue()>1L ? " días" : " día");
			}
			Boolean horas = false;
			if(horasTranscurridas.longValue()>0L){
				horas = true;
				tiempoTranscurrido += dias ? ", " : "";
				tiempoTranscurrido += horasTranscurridas.toString() + (horasTranscurridas.longValue()>1L ? " Horas" : " Hora");
			}
			if(minutosTranscurridos.longValue()>0L){
				tiempoTranscurrido += horas || dias ? " y " : " ";
				tiempoTranscurrido += minutosTranscurridos.toString() + (minutosTranscurridos.longValue()>1L ? " Minutos " : " Minuto ");
			}
			generatedObject.getIndicador().setTiempoTranscurrido(tiempoTranscurrido);
			
			generatedObjects.add(generatedObject);
		}
		return generatedObjects;
	}

}