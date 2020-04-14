package mx.com.teclo.sms.ws.negocio.service.reportes;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.lang.Collections;
import mx.com.teclo.arquitectura.ortogonales.util.ResponseConverter;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.dispositivosmoviles.DispositivoMovilDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.empleado.EmpleadoDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.eventogps.EventoGpsDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.eventogps.TipoEventoGpsDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.infracciones.TipoInfraccionDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.zonavial.ZonaVialDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.zonavial.ZonaVialEmpleadoDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.EmpleadosDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.hh.eventogps.EventoGpsDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.hh.eventogps.TipoEventoGpsDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.infracciones.TipoInfraccionDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial.ZonaVialDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial.ZonaVialCargoEmpleadoDTO;
import mx.com.teclo.sms.ws.persistencia.mybatis.dao.reportes.ReportesMybatisDAO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.DispositivoMovilVO;
import mx.com.teclo.sms.ws.persistencia.vo.empleado.EmpleadoVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.EventoGpsReporteGeneralVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.TipoEventoGpsVO;
import mx.com.teclo.sms.ws.persistencia.vo.infracciones.TipoInfraccionVO;
import mx.com.teclo.sms.ws.persistencia.vo.reportes.CatalogoMesesVO;
import mx.com.teclo.sms.ws.persistencia.vo.reportes.InfraccionesPorZonaVialVO;
import mx.com.teclo.sms.ws.persistencia.vo.reportes.ReporteGeneralDataVO;
import mx.com.teclo.sms.ws.persistencia.vo.reportes.ReporteGeneralDispositivosOficialesDataVO;
import mx.com.teclo.sms.ws.persistencia.vo.reportes.ZonaOficialesDispositivosVO;
import mx.com.teclo.sms.ws.persistencia.vo.zonavial.ZonaVialVO;
import mx.com.teclomexicana.arquitectura.ortogonales.exception.BusinessException;

@Service
public class ReporteServiceImpl implements ReporteService {
	
	@Autowired
	private ReportesMybatisDAO reportesMybatisDAO;
	
	@Autowired
	private ZonaVialEmpleadoDAO zonaVialEmpleadoDAO;
	
	@Autowired
	private TipoEventoGpsDAO tipoEventoGpsDAO;
	
	@Autowired
	private EventoGpsDAO eventoGpsDAO;
	
	@Autowired
	private TipoInfraccionDAO tipoInfraccionDAO;
	
	@Autowired
	private ZonaVialDAO zonaVialDAO;
	
	@Autowired
	private EmpleadoDAO empleadoDAO;
	
	@Autowired
	private DispositivoMovilDAO dispositivoMovilDAO;
	
	@Autowired
	private ReporteExcel reporteExcel;
	
	private List<EventoGpsReporteGeneralVO> reporteGeneral;
	
	private List<EventoGpsReporteGeneralVO> reporteGeneralOficialesDispostivos;
	
	private static String CODIGO_GRUA = "01", CODIGO_PIE_TIERRA = "04", CODIGO_INMOVILIZADOR = "05", CODIGO_PARQUIMETRO = "06", HANDHELD="Hand Held";
	private String[] codes = new String[]{CODIGO_GRUA, CODIGO_PIE_TIERRA, CODIGO_INMOVILIZADOR, CODIGO_PARQUIMETRO};
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public List<Map> infraccionesMensualesPorZonaVial(Long zonaVial, Integer anio){
		List<Map> response = new ArrayList<Map>();
		List<InfraccionesPorZonaVialVO> list = reportesMybatisDAO.busquedaInfraccionesMensualPorZonaVial(zonaVial, anio);
		for(int i = 1; i<=12; i++){
			List<InfraccionesPorZonaVialVO> listaMensual = new ArrayList<InfraccionesPorZonaVialVO>(); 
			for(InfraccionesPorZonaVialVO cuentaMensual : list){
				if(cuentaMensual.getMes().intValue()== i){
					listaMensual.add(cuentaMensual);
				}
			}
			if(listaMensual.size()==0)
				listaMensual.add(new InfraccionesPorZonaVialVO(anio, i, zonaVialDAO.findOne(zonaVial).getNombreZonaVial()));
			response.add(mapSeccion(listaMensual, anio, i));
		}
		return response;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public List<Map> infraccionesAnualesPorZonaVial(Long zonaVial, List<Integer> anios){
		List<Map> response = new ArrayList<Map>();
		List<InfraccionesPorZonaVialVO> list = reportesMybatisDAO.busquedaInfraccionesAnualPorZonaVial(zonaVial, anios);
		for(Integer anio : anios){
			List<InfraccionesPorZonaVialVO> listaAnual = new ArrayList<InfraccionesPorZonaVialVO>(); 
			for(InfraccionesPorZonaVialVO cuentaAnual : list){
				if(cuentaAnual.getYr().intValue()== anio){
					listaAnual.add(cuentaAnual);
				}
			}
			if(listaAnual.size()==0)
				listaAnual.add(new InfraccionesPorZonaVialVO(anio, null, zonaVialDAO.findOne(zonaVial).getNombreZonaVial()));
			response.add(mapSeccion(listaAnual, anio, null));
		}
		return response;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map mapSeccion(List<InfraccionesPorZonaVialVO> list, Integer anio, Integer mes){
		Map customize = new HashMap();
		boolean flag = true;
		String notExistCode = "";
		String zonaVialNombre = "";
		
		if(list.size() > 0){
			for (String code : codes){
				for(InfraccionesPorZonaVialVO object : list) {
					if(!code.equals(object.getCodigoInfraccion())){
						flag = false;
						notExistCode = code;
						zonaVialNombre = object.getZonaVial();
					}else{
						flag = true;
						break;
					}
				}
				
				if(!flag){
					if(notExistCode.equals(CODIGO_GRUA)){
						list.add(new InfraccionesPorZonaVialVO(anio, mes, zonaVialNombre, "Grúa", CODIGO_GRUA, 0, 0.00f));
					}else if(notExistCode.equals(CODIGO_PIE_TIERRA)){
						list.add(new InfraccionesPorZonaVialVO(anio, mes, zonaVialNombre, "Pie Tierra", CODIGO_PIE_TIERRA, 0, 0.00f));
					}else if(notExistCode.equals(CODIGO_INMOVILIZADOR)){
						list.add(new InfraccionesPorZonaVialVO(anio, mes, zonaVialNombre, "Inmovilizador", CODIGO_INMOVILIZADOR, 0, 0.00f));
					}else if(notExistCode.equals(CODIGO_PARQUIMETRO)){
						list.add(new InfraccionesPorZonaVialVO(anio, mes, zonaVialNombre, "Parquímetro", CODIGO_PARQUIMETRO, 0, 0.00f));
					}
				}
			}
			if(list.size()>codes.length)
				list.remove(0);
			customize.put("isEmpty", false);
			customize.put("list", list);
		} else {
			customize.put("isEmpty", true);
		}
		return customize;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map infraccionesPorZonaVial(Long zonaVial, String dia) {
		Map customize = new HashMap();
		
		List<InfraccionesPorZonaVialVO> list = reportesMybatisDAO.busquedaInfraccionesDiaPorZonaVial(zonaVial, dia);
		
		boolean flag = true;
		String notExistCode = "";
		String zonaVialNombre = "";
		
		if(list.size() > 0){
			for (String code : codes){
				for(InfraccionesPorZonaVialVO object : list) {
					if(!code.equals(object.getCodigoInfraccion())){
						flag = false;
						notExistCode = code;
						zonaVialNombre = object.getZonaVial();
					}else{
						flag = true;
						break;
					}
				}
				
				if(!flag){
					if(notExistCode.equals(CODIGO_GRUA)){
						list.add(new InfraccionesPorZonaVialVO(null, null, zonaVialNombre, "Grúa", CODIGO_GRUA, 0, 0.00f));
					}else if(notExistCode.equals(CODIGO_PIE_TIERRA)){
						list.add(new InfraccionesPorZonaVialVO(null, null, zonaVialNombre, "Pie Tierra", CODIGO_PIE_TIERRA, 0, 0.00f));
					}else if(notExistCode.equals(CODIGO_INMOVILIZADOR)){
						list.add(new InfraccionesPorZonaVialVO(null, null, zonaVialNombre, "Inmovilizador", CODIGO_INMOVILIZADOR, 0, 0.00f));
					}else if(notExistCode.equals(CODIGO_PARQUIMETRO)){
						list.add(new InfraccionesPorZonaVialVO(null, null, zonaVialNombre, "Parquímetro", CODIGO_PARQUIMETRO, 0, 0.00f));
					}
				}
			}
			
			customize.put("isEmpty", false);
			customize.put("list", list);
		} else {
			customize.put("isEmpty", true);
		}
		return customize;
	}

	/**
	 * @author Kevin Ojeda
	 */
	@Transactional
	@Override
	public ReporteGeneralDataVO reporteGeneralData(String empPlaca){
		ReporteGeneralDataVO response = new ReporteGeneralDataVO();
		List<ZonaVialCargoEmpleadoDTO> zonas = zonaVialEmpleadoDAO.findZonasByPlaca(empPlaca);
		List<TipoEventoGpsDTO> tipoEventos = tipoEventoGpsDAO.getTipoEventosActivos();
		List<TipoInfraccionDTO> tipoInfraccion = tipoInfraccionDAO.allActivosByNombreDispositivo(HANDHELD);
		//List<DispositivoMovilDTO> dispositivos = new ArrayList<DispositivoMovilDTO>();
		List<ZonaVialDTO>zonasDTO = new ArrayList<ZonaVialDTO>();
		if(!Collections.isEmpty(zonas)){
			//List<Long> zonasIds = new ArrayList<Long>();
			for(ZonaVialCargoEmpleadoDTO z : zonas){
				zonasDTO.add(z.getZonaVial());
				//zonasIds.add(z.getZonaVial().getIdZonaVial());
			}
			//dispositivos = dispositivoMovilDAO.dispositivosPorZonasViales(zonasIds);
		}
		response.setZonasViales(Collections.isEmpty(zonasDTO) ? new ArrayList<ZonaVialVO>() : ResponseConverter.converterLista(new ArrayList<ZonaVialVO>(), zonasDTO, ZonaVialVO.class));
		response.setTipoEventos(Collections.isEmpty(tipoEventos) ? new ArrayList<TipoEventoGpsVO>() : ResponseConverter.converterLista(new ArrayList<TipoEventoGpsVO>(), tipoEventos, TipoEventoGpsVO.class));
		response.setTipoInfracciones(Collections.isEmpty(tipoInfraccion) ? new ArrayList<TipoInfraccionVO>() : ResponseConverter.converterLista(new ArrayList<TipoInfraccionVO>(), tipoInfraccion, TipoInfraccionVO.class));
		//response.setDispositivos(Collections.isEmpty(dispositivos) ? new ArrayList<DispositivoMovilVO>() : ResponseConverter.converterLista(new ArrayList<DispositivoMovilVO>(), dispositivos, DispositivoMovilVO.class));
		return response;
	}
	
	/**
	 * @author Kevin Ojeda
	 */
	@Transactional
	@Override
	public ReporteGeneralDispositivosOficialesDataVO reporteGeneralOficialesDispositivosData(String empPlaca){
		ReporteGeneralDispositivosOficialesDataVO response = new ReporteGeneralDispositivosOficialesDataVO();
		List<ZonaVialCargoEmpleadoDTO> zonas = zonaVialEmpleadoDAO.findZonasByPlaca(empPlaca);
		List<TipoEventoGpsDTO> tipoEventos = tipoEventoGpsDAO.getTipoEventosActivos();
		List<TipoInfraccionDTO> tipoInfraccion = tipoInfraccionDAO.allActivosByNombreDispositivo(HANDHELD);
		
		List<ZonaOficialesDispositivosVO> zonasVO = new ArrayList<ZonaOficialesDispositivosVO>();
		if(!Collections.isEmpty(zonas)){
			for(ZonaVialCargoEmpleadoDTO z : zonas){
				ZonaOficialesDispositivosVO zonaVO = ResponseConverter.copiarPropiedadesFull(z.getZonaVial(), ZonaOficialesDispositivosVO.class);
				
				List<DispositivoMovilDTO> dispositivos = new ArrayList<DispositivoMovilDTO>();
				dispositivos = dispositivoMovilDAO.dispositivosPorZonaVial(z.getZonaVial().getIdZonaVial());
				zonaVO.setDispositivos(Collections.isEmpty(dispositivos) ? new ArrayList<DispositivoMovilVO>() : ResponseConverter.converterLista(new ArrayList<DispositivoMovilVO>(), dispositivos, DispositivoMovilVO.class));
				
				List<EmpleadosDTO> empleados = new ArrayList<EmpleadosDTO>();
				empleados = empleadoDAO.oficialesPorZonaVial(z.getZonaVial().getIdZonaVial());
				zonaVO.setEmpleados(Collections.isEmpty(empleados) ? new ArrayList<EmpleadoVO>() : ResponseConverter.converterLista(new ArrayList<EmpleadoVO>(), empleados, EmpleadoVO.class));
				
				zonasVO.add(zonaVO);
			}
		}
		response.setZonasViales(zonasVO);
		response.setTipoEventos(Collections.isEmpty(tipoEventos) ? new ArrayList<TipoEventoGpsVO>() : ResponseConverter.converterLista(new ArrayList<TipoEventoGpsVO>(), tipoEventos, TipoEventoGpsVO.class));
		response.setTipoInfracciones(Collections.isEmpty(tipoInfraccion) ? new ArrayList<TipoInfraccionVO>() : ResponseConverter.converterLista(new ArrayList<TipoInfraccionVO>(), tipoInfraccion, TipoInfraccionVO.class));
		return response;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional
	public Map catalogos(String empPlaca) throws BusinessException{
		Map catalogos = new HashMap();
		
		/*Meses*/
		List<CatalogoMesesVO> listMonth = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		int index = 0;
		int year = calendar.get(Calendar.YEAR);
		
		DateFormatSymbols dfs = new DateFormatSymbols(Locale.getDefault());
        String[] months = dfs.getMonths();
        for (String month : months) {
        	if(!month.isEmpty()){
        		GregorianCalendar gc = new GregorianCalendar(year, index, 1);
                Date firstDay = new Date(gc.getTime().getTime());
                
                gc.add(Calendar.MONTH, 1);
                gc.add(Calendar.DAY_OF_MONTH, -1);
                Date endDay = new Date(gc.getTime().getTime());
            	
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");  
            	
            	listMonth.add(new CatalogoMesesVO(index, StringUtils.capitalize(month), sdf.format(firstDay), sdf.format(endDay)));
        	} 
        	index++;
        }
        
        List<ZonaVialCargoEmpleadoDTO> interseccion = zonaVialEmpleadoDAO.findZonasByPlaca(empPlaca);
        List<ZonaVialVO> zonas = new ArrayList<ZonaVialVO>();
        if(!Collections.isEmpty(interseccion)){
        	for(ZonaVialCargoEmpleadoDTO inter : interseccion){
    			zonas.add(ResponseConverter.copiarPropiedadesFull(inter.getZonaVial(), ZonaVialVO.class));
    		}
        }
		
		
        catalogos.put("months", listMonth);
		catalogos.put("roadzones", zonas);
        
		return catalogos;
	}
	
	/**
	 * {@inheritDoc} 
	 */
	@Override
	@Transactional
	public List<EventoGpsReporteGeneralVO> reporteGeneralFiltrado(Integer loginRestriction, List<Long> zonasVialesId, Date fI, Date fF, List<Long> idsTipoEvento, List<String> codigosTipoInfraccion){
		List<EventoGpsDTO> lDTO = eventoGpsDAO.reporteGeneral(
				loginRestriction,
				zonasVialesId == null || Collections.isEmpty(zonasVialesId) ? null : zonasVialesId,
				fI,
				fF,
				idsTipoEvento == null || Collections.isEmpty(idsTipoEvento) ? null : idsTipoEvento,
				codigosTipoInfraccion == null || Collections.isEmpty(codigosTipoInfraccion) ? null : codigosTipoInfraccion);
		reporteGeneral = ResponseConverter.converterLista(new ArrayList<EventoGpsReporteGeneralVO>(), lDTO, EventoGpsReporteGeneralVO.class);
		return reporteGeneral;
	}
	
	/**
	 * {@inheritDoc} 
	 */
	@Override
	@Transactional
	public List<EventoGpsReporteGeneralVO> reporteGeneralFiltradoOficialesDispostivos(Integer loginRestriction, boolean busquedaConjunta, List<Long> oficiales, List<Long> dispositivos, Date fI, Date fF, List<Long> idsTipoEvento, List<String> codigosTipoInfraccion){
		oficiales = oficiales == null || Collections.isEmpty(oficiales) ? null : oficiales;
		dispositivos = dispositivos == null || Collections.isEmpty(dispositivos) ? null : dispositivos;
		idsTipoEvento = idsTipoEvento == null || Collections.isEmpty(idsTipoEvento) ? null : idsTipoEvento;
		codigosTipoInfraccion = codigosTipoInfraccion == null || Collections.isEmpty(codigosTipoInfraccion) ? null : codigosTipoInfraccion;
		List<EventoGpsDTO> lDTO = busquedaConjunta  ?  
			eventoGpsDAO.reporteGeneralOficialesConDispositivos(loginRestriction, oficiales, dispositivos, fI, fF, idsTipoEvento, codigosTipoInfraccion) :
			eventoGpsDAO.reporteGeneralOficialesDispositivos(loginRestriction, oficiales, dispositivos, fI, fF, idsTipoEvento, codigosTipoInfraccion);
			
		reporteGeneralOficialesDispostivos = ResponseConverter.converterLista(new ArrayList<EventoGpsReporteGeneralVO>(), lDTO, EventoGpsReporteGeneralVO.class);
		 return reporteGeneralOficialesDispostivos;
	}
	
	@Override
	public Map<String, Object> reporteGeneralXls(Integer reportKind){
		switch(reportKind){
			case 0:
			return reporteExcel.reporteGeneral(reporteGeneral);
			case 1:
			return reporteExcel.reporteGeneral(reporteGeneralOficialesDispostivos);
			default:
				return null;
		}
	}
}
