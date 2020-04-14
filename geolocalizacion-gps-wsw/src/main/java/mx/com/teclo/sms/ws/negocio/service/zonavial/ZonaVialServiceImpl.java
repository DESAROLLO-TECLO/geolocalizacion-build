package mx.com.teclo.sms.ws.negocio.service.zonavial;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.lang.Collections;
import mx.com.teclo.arquitectura.ortogonales.util.ResponseConverter;
import mx.com.teclo.sms.ws.negocio.service.comun.UsuarioFirmadoService;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.dispositivosmoviles.DispositivoMovilDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.empleado.CargoDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.empleado.EmpleadoDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.empleado.EmpleadosCargosDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.zonavial.ZonaVialDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.zonavial.ZonaVialDispositivoDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.zonavial.ZonaVialEmpleadoDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.CargoDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.EmpleadosCargosDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.EmpleadosCargosIdDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.EmpleadosDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial.ZonaVialCargoEmpleadoDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial.ZonaVialCargoEmpleadoIdDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial.ZonaVialDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial.ZonaVialDispositivoDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial.ZonaVialDispositivoIdDTO;
import mx.com.teclo.sms.ws.persistencia.mybatis.dao.zonas.ZonasVialesMyBatisDAO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.DispositivoMovilVO;
import mx.com.teclo.sms.ws.persistencia.vo.empleado.EmpleadosRespectivosAreaOperativaVO;
import mx.com.teclo.sms.ws.persistencia.vo.zonavial.ZonaVialAsignacionDispositivosOficialesVO;
import mx.com.teclo.sms.ws.persistencia.vo.zonavial.ZonaVialAsignacionesResponseVO;
import mx.com.teclo.sms.ws.persistencia.vo.zonavial.ZonaVialDispositivosOfficersVO;
import mx.com.teclo.sms.ws.persistencia.vo.zonavial.ZonaVialVO;
import mx.com.teclo.sms.ws.util.enums.Cargos;
import mx.com.teclo.sms.ws.util.enums.Codigos;
import mx.com.teclo.sms.ws.util.enums.Mensajes;

@Service
public class ZonaVialServiceImpl implements ZonaVialService {
	
	@Autowired
	private ZonaVialDAO zonaVialDAO;
	
	@Autowired
	private EmpleadosCargosDAO empleadosCargosDAO;
	
	@Autowired
	private ZonaVialEmpleadoDAO zonaVialEmpleadoDAO;
	
	@Autowired
	private ZonaVialDispositivoDAO zonaVialDispositivoDAO;
	
	@Autowired
	private DispositivoMovilDAO dispositivoMovilDAO;
	
	@Autowired
	private UsuarioFirmadoService usuarioFirmadoService;
	
	@Autowired
	private EmpleadoDAO empleadoDAO;
	
	@Autowired
	private CargoDAO cargoDAO;
	
	@Autowired ZonasVialesMyBatisDAO zonasVialesMyBatisDAO;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<ZonaVialVO> zonasByPlaca(String empPlaca) {
		List<ZonaVialCargoEmpleadoDTO> interseccion = zonaVialEmpleadoDAO.findZonasByPlaca(empPlaca);
		if(Collections.isEmpty(interseccion))return null;
		List<ZonaVialVO> zonas = new ArrayList<ZonaVialVO>();
		for(ZonaVialCargoEmpleadoDTO inter : interseccion){
			zonas.add(ResponseConverter.copiarPropiedadesFull(inter.getZonaVial(), ZonaVialVO.class));
		}
		return zonas;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<ZonaVialDispositivosOfficersVO> zonasAndDispositivosByPlaca(String empPlaca, String codeRequest, String procedence) {
//		if(empleadosCargosDAO.byCodeAndPlaca(Cargos.SUPERVISOR.getCargo(), empPlaca) == null) return null;
		List<ZonaVialDispositivosOfficersVO> zonas = zonasVialesMyBatisDAO.getZonas(empPlaca);
		if(Collections.isEmpty(zonas))return null;
		Long idDefDisp = 0L;
		Long idDefOfic = 0L;
		if(procedence.equals("M")){
			for(ZonaVialDispositivosOfficersVO zona : zonas){
				zona.getDispositivos().add(0, new DispositivoMovilVO(--idDefDisp,"Todos", "Todos", "Todos"));
				zona.getOficiales().add(0, new EmpleadosRespectivosAreaOperativaVO("all","Todos", "", "", --idDefOfic));
			}
		}
		zonas.add(zonasAndDispositivosSinAsignaciónForEvents("h",procedence,--idDefDisp));
		return zonas;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ZonaVialDispositivosOfficersVO zonasAndDispositivosSinAsignaciónForEvents(String codeRequest , String procedence, Long lastIdDefDisp) {
		ZonaVialDispositivosOfficersVO zonaYDisp = new ZonaVialDispositivosOfficersVO();
		zonaYDisp.setCodigoZonaVial("DEF");
		zonaYDisp.setIdZonaVial(lastIdDefDisp);
		zonaYDisp.setNombreZonaVial("SIN ZONA ASIGNADA");
		if(codeRequest.equals("h") || codeRequest.equals("a")){
			
			List<DispositivoMovilDTO> dispositivosSinAsignacion = dispositivoMovilDAO.dispositivosSinAsignacion();
			List<DispositivoMovilVO> dispositivos = ResponseConverter.converterLista(new ArrayList<DispositivoMovilVO>(), dispositivosSinAsignacion, DispositivoMovilVO.class);
			if(procedence.equals("M")){
				dispositivos.add(0, new DispositivoMovilVO(lastIdDefDisp,"Todos", "Todos", "Todos"));
			}
			zonaYDisp.setDispositivos(dispositivos);
		}else if(codeRequest.equals("o") || codeRequest.equals("a")){
			List<EmpleadosDTO> oficialesSinAsignacion = empleadoDAO.oficialesSinAsignacion();
			List<EmpleadosRespectivosAreaOperativaVO> oficiales = ResponseConverter.converterLista(new ArrayList<EmpleadosRespectivosAreaOperativaVO>(), oficialesSinAsignacion, EmpleadosRespectivosAreaOperativaVO.class);
			zonaYDisp.setOficiales(oficiales);
		}
		return zonaYDisp;
	}
	
	/**
	 * {@inheritDoc}
	 */
//	@Override
//	@Transactional
//	public List<ZonaVialDispositivosOfficersVO> zonasAndDispositivosByPlaca(String empPlaca, String codeRequest, String procedence) {
						//		if(empleadosCargosDAO.byCodeAndPlaca(Cargos.SUPERVISOR.getCargo(), empPlaca) == null) return null;
//		List<ZonaVialCargoEmpleadoDTO> interseccion = zonaVialEmpleadoDAO.findZonasByPlaca(empPlaca);
//		if(Collections.isEmpty(interseccion))return null;
//		List<ZonaVialDispositivosOfficersVO> zonasYDispositivos = new ArrayList<ZonaVialDispositivosOfficersVO>();
//		List<ZonaVialVO> zonas = new ArrayList<ZonaVialVO>();
//		Long idDefDisp = 0L;
//		Long idDefOfic = 0L;
//		for(ZonaVialCargoEmpleadoDTO inter : interseccion){
//			ZonaVialDispositivosOfficersVO zonaYDisp = new ZonaVialDispositivosOfficersVO();
//			
//			ZonaVialVO zona = ResponseConverter.copiarPropiedadesFull(inter.getZonaVial(), ZonaVialVO.class);
//			zonaYDisp.setCodigoZonaVial(zona.getCodigoZonaVial());
//			zonaYDisp.setIdZonaVial(zona.getIdZonaVial());
//			zonaYDisp.setNombreZonaVial(zona.getNombreZonaVial());
//			if(codeRequest.equals("a") || codeRequest.equals("h")){
//				List<ZonaVialDispositivoDTO> subInterseccion = zonaVialDispositivoDAO.activosByZona(zona.getIdZonaVial());
//				List<DispositivoMovilVO> moviles = new ArrayList<DispositivoMovilVO>();
//				
//				if(procedence.equals("M") && subInterseccion.size()>0)
//					moviles.add(new DispositivoMovilVO(--idDefDisp,"Todos", "Todos", "Todos"));
//				for(ZonaVialDispositivoDTO subInter : subInterseccion){
//					moviles.add(ResponseConverter.copiarPropiedadesFull(subInter.getDispositivo(), DispositivoMovilVO.class));
//				}
//				zonaYDisp.setDispositivos(moviles);
//			}
//			
//			if(codeRequest.equals("a") || codeRequest.equals("o")){
//				List<ZonaVialCargoEmpleadoDTO> subInterseccion1 = zonaVialEmpleadoDAO.oficialesActivosByZona(zona.getIdZonaVial());
//				List<EmpleadosRespectivosAreaOperativaVO> oficiales = new ArrayList<EmpleadosRespectivosAreaOperativaVO>();
//				
//				if(procedence.equals("M") && subInterseccion1.size()>0)
//					oficiales.add(new EmpleadosRespectivosAreaOperativaVO("all","Todos", "", "", --idDefOfic));
//				for(ZonaVialCargoEmpleadoDTO subInter : subInterseccion1){
//					oficiales.add(ResponseConverter.copiarPropiedadesFull(subInter.getEmpleado(), EmpleadosRespectivosAreaOperativaVO.class));
//				}
//				zonaYDisp.setOficiales(oficiales);
//			}
//			zonasYDispositivos.add(zonaYDisp);
//			
//		}
//		zonasYDispositivos.add(zonasAndDispositivosSinAsignación("h"));
//		return zonasYDispositivos;
//	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ZonaVialDispositivosOfficersVO zonasAndDispositivosAndOfficersByZonaCode(String zonaCode, String codeRequest) {
		ZonaVialDTO zonaDTO = zonaVialDAO.activoByCode(zonaCode);
		if(zonaDTO == null) return null;
		ZonaVialDispositivosOfficersVO zonaYDisp = new ZonaVialDispositivosOfficersVO();
		zonaYDisp.setCodigoZonaVial(zonaDTO.getCodigoZonaVial());
		zonaYDisp.setIdZonaVial(zonaDTO.getIdZonaVial());
		zonaYDisp.setNombreZonaVial(zonaDTO.getNombreZonaVial());
		if(codeRequest.equals("a") || codeRequest.equals("h")){
			List<ZonaVialDispositivoDTO> subInterseccion = zonaVialDispositivoDAO.activosByZona(zonaDTO.getIdZonaVial());
			List<DispositivoMovilVO> moviles = new ArrayList<DispositivoMovilVO>();
			for(ZonaVialDispositivoDTO subInter : subInterseccion){
				moviles.add(ResponseConverter.copiarPropiedadesFull(subInter.getDispositivo(), DispositivoMovilVO.class));
			}
			zonaYDisp.setDispositivos(moviles);
		}
		if(codeRequest.equals("a") || codeRequest.equals("o")){
			List<ZonaVialCargoEmpleadoDTO> subInterseccion1 = zonaVialEmpleadoDAO.oficialesActivosByZona(zonaDTO.getIdZonaVial());
			List<EmpleadosRespectivosAreaOperativaVO> oficiales = new ArrayList<EmpleadosRespectivosAreaOperativaVO>();
			for(ZonaVialCargoEmpleadoDTO subInter : subInterseccion1){
				oficiales.add(ResponseConverter.copiarPropiedadesFull(subInter.getEmpleado(), EmpleadosRespectivosAreaOperativaVO.class));
			}
			zonaYDisp.setOficiales(oficiales);
		}
		return zonaYDisp;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ZonaVialDispositivosOfficersVO zonasAndDispositivosSinAsignación(String codeRequest, Long lastIdDefDisp) {
		ZonaVialDispositivosOfficersVO zonaYDisp = new ZonaVialDispositivosOfficersVO();
		zonaYDisp.setCodigoZonaVial("DEF");
		zonaYDisp.setIdZonaVial(lastIdDefDisp);
		zonaYDisp.setNombreZonaVial("SIN ZONA ASIGNADA");
		if(codeRequest.equals("h") || codeRequest.equals("a")){
			List<DispositivoMovilDTO> dispositivosSinAsignacion = dispositivoMovilDAO.dispositivosSinAsignacion();
			List<DispositivoMovilVO> dispositivos = ResponseConverter.converterLista(new ArrayList<DispositivoMovilVO>(), dispositivosSinAsignacion, DispositivoMovilVO.class);
			zonaYDisp.setDispositivos(dispositivos);
		}else if(codeRequest.equals("o") || codeRequest.equals("a")){
			List<EmpleadosDTO> oficialesSinAsignacion = empleadoDAO.oficialesSinAsignacion();
			List<EmpleadosRespectivosAreaOperativaVO> oficiales = ResponseConverter.converterLista(new ArrayList<EmpleadosRespectivosAreaOperativaVO>(), oficialesSinAsignacion, EmpleadosRespectivosAreaOperativaVO.class);
			zonaYDisp.setOficiales(oficiales);
		}
		return zonaYDisp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ZonaVialDispositivosOfficersVO allDataByDispoInfo(String serie, String sim, String ip){
		ZonaVialDispositivoDTO zonaVialDispo = zonaVialDispositivoDAO.activoByDispoInfo(serie, sim, ip);
		if(zonaVialDispo == null){
			DispositivoMovilDTO dispo = dispositivoMovilDAO.activoByDispoInfo(serie, sim, ip);
			if(dispo!=null)
				return zonasAndDispositivosSinAsignación("h",-1L);
			return null;
		}
		return zonasAndDispositivosAndOfficersByZonaCode(zonaVialDispo.getZonaVial().getCodigoZonaVial(), "h");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<ZonaVialDispositivosOfficersVO> allDataByOfficerInfo(String placa, String nombre, String paterno, String materno){
		List<ZonaVialDispositivosOfficersVO> response = new ArrayList<ZonaVialDispositivosOfficersVO>();
		
		List<ZonaVialCargoEmpleadoDTO> zonaVialOfficerList = zonaVialEmpleadoDAO.activoByOfficerInfo(placa, nombre, paterno, materno);
		for(ZonaVialCargoEmpleadoDTO dto : zonaVialOfficerList){
			ZonaVialDispositivosOfficersVO vo = ResponseConverter.copiarPropiedadesFull(dto.getZonaVial(), ZonaVialDispositivosOfficersVO.class);
			EmpleadosRespectivosAreaOperativaVO empVO = ResponseConverter.copiarPropiedadesFull(dto.getEmpleado(), EmpleadosRespectivosAreaOperativaVO.class);
			vo.setOficiales(new ArrayList<EmpleadosRespectivosAreaOperativaVO>());
			vo.getOficiales().add(empVO);
			response.add(vo);
		}
		
		List<EmpleadosDTO> empleadoSinZona = empleadoDAO.activoByOfficerInfo(placa, nombre, paterno, materno);
		for(Iterator<EmpleadosDTO> iterator = empleadoSinZona.iterator(); iterator.hasNext();){
			EmpleadosDTO empleado = (EmpleadosDTO) iterator.next();
			for(ZonaVialDispositivosOfficersVO vo : response){
				if(vo.getOficiales().get(0).getEmpPlaca().equals(empleado.getEmpPlaca())){
					iterator.remove();
					break;
				}
			}
		}
		
		ZonaVialDispositivosOfficersVO zonaDef = new ZonaVialDispositivosOfficersVO();
		zonaDef.setIdZonaVial(-1L);
		zonaDef.setCodigoZonaVial("DEF");
		zonaDef.setNombreZonaVial("Sin zona vial");
		for(EmpleadosDTO empleado : empleadoSinZona){
			ZonaVialDispositivosOfficersVO vo = new ZonaVialDispositivosOfficersVO();
			EmpleadosRespectivosAreaOperativaVO empVO = ResponseConverter.copiarPropiedadesFull(empleado, EmpleadosRespectivosAreaOperativaVO.class);
			vo.setOficiales(new ArrayList<EmpleadosRespectivosAreaOperativaVO>());
			vo.getOficiales().add(empVO);
			vo.setIdZonaVial(-1L);
			vo.setCodigoZonaVial("DEF");
			vo.setNombreZonaVial("Sin zona vial");
			response.add(vo);
		}
		
		return Collections.isEmpty(response) ? null : response;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional 
	public ZonaVialDispositivosOfficersVO allOfficerDataByZonaCode(String zonaCode, Long empId){
		EmpleadosDTO empleado = empleadoDAO.findOne(empId);
		
		if(empleado == null) return null;
		
		ZonaVialCargoEmpleadoDTO zonaVial = zonaVialEmpleadoDAO.zonaByEmpPlaca(empleado.getEmpPlaca());
		
		if(zonaVial == null){
			ZonaVialDispositivosOfficersVO zonaDef = new ZonaVialDispositivosOfficersVO();
			zonaDef.setIdZonaVial(-1L);
			zonaDef.setCodigoZonaVial("DEF");
			zonaDef.setNombreZonaVial("Sin zona vial");
			EmpleadosRespectivosAreaOperativaVO empVO = ResponseConverter.copiarPropiedadesFull(empleado, EmpleadosRespectivosAreaOperativaVO.class);
			List<EmpleadosRespectivosAreaOperativaVO> oficiales = new ArrayList<EmpleadosRespectivosAreaOperativaVO>();
			oficiales.add(empVO);
			zonaDef.setOficiales(oficiales);
			return zonaDef;
		}
		
		return zonasAndDispositivosAndOfficersByZonaCode(zonaVial.getZonaVial().getCodigoZonaVial(), "o");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<ZonaVialVO> zonasActivas() {
		List<ZonaVialDTO> zonaDTO = zonaVialDAO.allActivos();
		if(Collections.isEmpty(zonaDTO)) return null;
		return ResponseConverter.converterLista(new ArrayList<>(), zonaDTO, ZonaVialVO.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<ZonaVialVO> zonasActivasPorPlacaSupervisor(String empPlaca) {
		List<ZonaVialCargoEmpleadoDTO> zonasEmp = zonaVialEmpleadoDAO.findZonasByPlaca(empPlaca);
		List<ZonaVialDTO> zonasDTO = new ArrayList<ZonaVialDTO>();
		for(ZonaVialCargoEmpleadoDTO z : zonasEmp){
			zonasDTO.add(z.getZonaVial());
		}
		if(Collections.isEmpty(zonasDTO)) return null;
		return ResponseConverter.converterLista(new ArrayList<>(), zonasDTO, ZonaVialVO.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<ZonaVialVO> zonasActivasPlusZonaDefault(String codeRequest) {
		List<ZonaVialDTO> zonaDTO = zonaVialDAO.allActivos();
		if(Collections.isEmpty(zonaDTO)) return null;
		
		
		List<ZonaVialVO> listVO = ResponseConverter.converterLista(new ArrayList<>(), zonaDTO, ZonaVialVO.class);
		
		if(codeRequest.equals("h")){
			ZonaVialVO defaultVO = new ZonaVialVO();
			defaultVO.setIdZonaVial(-1L);
			defaultVO.setCodigoZonaVial("DEF");
			defaultVO.setNombreZonaVial("SIN ZONA ASIGNADA");
			defaultVO.setActivo(1);
			listVO.add(0, defaultVO);
		}
		//java.util.Collections.swap(listVO, 0, listVO.size() -1);
		
		return listVO;
	}
	
	@Override
	@Transactional
	public ZonaVialAsignacionesResponseVO persistAsignaciones(ZonaVialAsignacionesResponseVO vo){
		CargoDTO cargoOficial = cargoDAO.activoByCode(Cargos.OFICIAL.getCargo());
		Long user = usuarioFirmadoService.getUsuarioFirmadoVO().getId();
		Date c = Calendar.getInstance().getTime();
		for(ZonaVialAsignacionDispositivosOficialesVO zona : vo.getAsignaciones()){
			ZonaVialDTO zonaDTO;
			List<ZonaVialCargoEmpleadoDTO> empleadosFound = zonaVialEmpleadoDAO.oficialesByZona(zona.getIdZonaVial());
			List<ZonaVialDispositivoDTO> dispositivosFound = zonaVialDispositivoDAO.byZona(zona.getIdZonaVial());
			
			
			if(Collections.isEmpty(empleadosFound)){
				if(Collections.isEmpty(dispositivosFound)){
					zonaDTO = zonaVialDAO.findOne(zona.getIdZonaVial());
				}else{
					zonaDTO = dispositivosFound.get(0).getZonaVial();
				}
			}else{
				zonaDTO = empleadosFound.get(0).getZonaVial();
			}
			if(zonaDTO == null) continue;
			
			if(zona.getOficiales()!=null){
				for(ZonaVialCargoEmpleadoDTO empleado : empleadosFound){
					boolean found = false;
					for(Iterator<Long> newOficialIterator = zona.getOficiales().iterator(); newOficialIterator.hasNext();){
						Long newOficial = (Long) newOficialIterator.next();
						if(newOficial.equals(empleado.getId().getEmpleadoId())){
							newOficialIterator.remove();
							found = true;
							break;
						}
					}
					if(found){
						if(empleado.getActivo() == 0){
							empleado.setActivo(1);
							empleado.setModificadoPor(user);
							empleado.setFechaModificacion(c);
							zonaVialEmpleadoDAO.update(empleado);
						}
					}
					else{
						empleado.setActivo(0);
						empleado.setModificadoPor(user);
						empleado.setFechaModificacion(c);
						zonaVialEmpleadoDAO.update(empleado);
					}
				}
				for(Long newOficial : zona.getOficiales()){
					EmpleadosDTO emp = empleadoDAO.findOne(newOficial);
					if(emp == null) continue;
					EmpleadosCargosDTO empleadoCargo = empleadosCargosDAO.byCodeAndPlaca(Cargos.OFICIAL.getCargo(), emp.getEmpPlaca());
					if(empleadoCargo == null){
						empleadoCargo = new EmpleadosCargosDTO();
						EmpleadosCargosIdDTO empleadoCargoId = new EmpleadosCargosIdDTO(); 
						empleadoCargoId.setCargoId(cargoOficial.getCargoId());
						empleadoCargoId.setEmpId(emp.getEmpId());
						empleadoCargo.setId(empleadoCargoId);
						empleadoCargo.setActivo(1);
						empleadoCargo.setCargo(cargoOficial);
						empleadoCargo.setEmpleado(emp);
						empleadoCargo.setCreadoPor(user);
						empleadoCargo.setFechaCreacion(c);
						empleadoCargo.setFechaModificacion(c);
						empleadoCargo.setModificadoPor(user);
						empleadosCargosDAO.save(empleadoCargo);	
					}
					
					ZonaVialCargoEmpleadoDTO newDTO = new ZonaVialCargoEmpleadoDTO();
					newDTO.setEmpleado(emp);
					if(newDTO.getEmpleado() == null) continue;
					newDTO.setZonaVial(zonaDTO);
					ZonaVialCargoEmpleadoIdDTO id = new ZonaVialCargoEmpleadoIdDTO();
					id.setEmpleadoId(newDTO.getEmpleado().getEmpId());
					id.setZonaVialId(newDTO.getZonaVial().getIdZonaVial());
					id.setCargoId(cargoOficial.getCargoId());
					newDTO.setEmpleadoCargo(empleadoCargo);
					newDTO.setId(id);
					newDTO.setActivo(1);
					newDTO.setCreadoPor(user);
					newDTO.setModificadoPor(user);
					newDTO.setFechaCreacion(c);
					newDTO.setFechaModificacion(c);
					zonaVialEmpleadoDAO.save(newDTO);
					
					
					
//					}else{
//						empleadoCargo.setFechaModificacion(c);
//						empleadoCargo.setModificadoPor(user);
//						empleadosCargosDAO.update(empleadoCargo);
//					}
				}
			}
			
			if(zona.getDispositivos()!=null){
				for(ZonaVialDispositivoDTO dispositivo : dispositivosFound){
					boolean found = false;
					for(Iterator<Long> newDispositivoIterator = zona.getDispositivos().iterator(); newDispositivoIterator.hasNext();){
						Long newDispositivo = (Long) newDispositivoIterator.next();
						if(newDispositivo.equals(dispositivo.getId().getDispositivoId())){
							newDispositivoIterator.remove();
							found = true;
							break;
						}
					}
					if(found){
						if(dispositivo.getActivo() == 0){
							dispositivo.setActivo(1);
							dispositivo.setModificadoPor(user);
							dispositivo.setFechaModificacion(c);
							zonaVialDispositivoDAO.update(dispositivo);
						}
					}
					else{
						dispositivo.setActivo(0);
						dispositivo.setModificadoPor(user);
						dispositivo.setFechaModificacion(c);
						zonaVialDispositivoDAO.update(dispositivo);
					}
				}
				for(Long newDispositivo : zona.getDispositivos()){
					ZonaVialDispositivoDTO newDTO = new ZonaVialDispositivoDTO();
					newDTO.setDispositivo(dispositivoMovilDAO.findOne(newDispositivo));
					if(newDTO.getDispositivo() == null) continue;
					newDTO.setZonaVial(zonaDTO);
					ZonaVialDispositivoIdDTO id = new ZonaVialDispositivoIdDTO();
					id.setDispositivoId(newDTO.getDispositivo().getIdDispositivo());
					id.setZonaVialId(newDTO.getZonaVial().getIdZonaVial());
					newDTO.setId(id);
					newDTO.setActivo(1);
					newDTO.setCreadoPor(user);
					newDTO.setModificadoPor(user);
					newDTO.setFechaCreacion(c);
					newDTO.setFechaModificacion(c);
					zonaVialDispositivoDAO.save(newDTO);
					
				}
			}
		}
		vo.setCodigoHttp(Codigos.CREATE.getProcesoId());
		vo.setDescripcion(Mensajes.MSJ_CREATE_LIST.getProcesoId());
		return vo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}