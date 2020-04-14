package mx.com.teclo.sms.ws.negocio.service.localizacion;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.lang.Collections;
import mx.com.teclo.sms.ws.negocio.service.comun.UsuarioFirmadoService;
import mx.com.teclo.sms.ws.negocio.service.user.UsuarioService;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.coordenadas.CoordenadaDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.coordenadas.IndicadorDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.empleado.EmpleadoDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.recorridos.TiposRecorridoDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.ruta.RutaDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.EmpleadosDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.geolocalizacion.CoordenadaDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.geolocalizacion.IndicadorDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.geolocalizacion.RutaDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.geolocalizacion.TipoRecorridoDTO;
import mx.com.teclo.sms.ws.persistencia.vo.empleado.EmpleadosRespectivosAreaOperativaVO;
import mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion.BitacoraCoordenadaVO;
import mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion.CoordenadaVO;
import mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion.IndicadorVO;
import mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion.RutasCoordenadasVO;
import mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion.SimpleCoordenadaVO;
import mx.com.teclo.sms.ws.persistencia.vo.login.UsuarioVO;
import mx.com.teclo.sms.ws.persistencia.vo.ruta.RutaVO;
import mx.com.teclo.sms.ws.util.comun.ResponseConverter;

@Service
public class LocalizacionServiceImpl implements LocalizacionService {

	@Autowired
	private CoordenadaDAO coordenadaDAO;
	
	@Autowired
	private RutaDAO rutaDAO;
	
	@Autowired
	private EmpleadoDAO empleadoDAO;
	
	@Autowired
	private TiposRecorridoDAO tiposRecorridoDAO;
	
	@Autowired
	private IndicadorDAO indicadorDAO;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioFirmadoService usuarioFirmadoService;
	
	@Override
	@Transactional
	public CoordenadaVO saveCoordenada(CoordenadaVO coordenadaVO) {
		CoordenadaVO responsecoordenadaVO = null;
		CoordenadaDTO coordenadaDTO = ResponseConverter.copiarPropiedadesFull(coordenadaVO, CoordenadaDTO.class);
		RutaDTO rutaDTO = rutaDAO.findOne(coordenadaVO.getIdRuta());
		coordenadaDTO.setRuta(rutaDTO);
		coordenadaDTO.setFechaCreacion(new Date());
		coordenadaDTO.setUltimaModificacion(new Date());
		EmpleadosDTO emp = empleadoDAO.findOne(usuarioFirmadoService.getUsuarioFirmadoVO().getId());
		if(emp!=null)
			coordenadaDTO.setEmpleado(emp);
		coordenadaDTO.setCreadoPor(emp.getEmpId());
		coordenadaDTO.setModificadoPor(emp.getEmpId());
		coordenadaDAO.saveOrUpdate(coordenadaDTO);
		responsecoordenadaVO = ResponseConverter.copiarPropiedadesFull(coordenadaDTO, CoordenadaVO.class);

		return responsecoordenadaVO;
	}

	@Override
	@Transactional
	public List<CoordenadaVO> getCoordenadasByRuta(Long idRuta) {
		coordenadaDAO.getCoordenadasByRuta(idRuta);

		List<CoordenadaDTO> listCoordenadaDTO = coordenadaDAO.getCoordenadasByRuta(idRuta);
		List<CoordenadaVO> listCoordenadaVO = ResponseConverter.converterLista(new ArrayList<>(), listCoordenadaDTO,
				CoordenadaVO.class);
		return listCoordenadaVO;
	}

	@Override
	@Transactional
	public List<CoordenadaVO> getCoordenadasByEmpleado(Long idEmpleado) {
		List<CoordenadaDTO> listCoordenadaDTO = coordenadaDAO.getCoordenadasByEmpleado(idEmpleado);
		List<CoordenadaVO> listCoordenadaVO = ResponseConverter.converterLista(new ArrayList<>(), listCoordenadaDTO,
				CoordenadaVO.class);

		return listCoordenadaVO;
	}

	@Override
	@Transactional
	public List<RutaVO> getRutasByUser(String placa, Long tipoRecorrido) {
		UsuarioVO usuarioVO = usuarioService.findUserByPlaca(placa);
		if (usuarioVO == null) return null;
		List<RutaDTO> listRutaDTO = rutaDAO.getRutasByUser(usuarioVO.getEmpId(), tipoRecorrido);
		List<RutaVO> listRutaVO = ResponseConverter.converterLista(new ArrayList<>(), listRutaDTO, RutaVO.class);

		return listRutaVO;
	}

	@Override
	@Transactional
	public RutaVO saveRuta(RutaVO rutaVO) {
		RutaVO responseRutaVO = null;

		RutaDTO rutaDTO = ResponseConverter.copiarPropiedadesFull(rutaVO, RutaDTO.class);
		EmpleadosDTO empleadoDTO = empleadoDAO.findOne(usuarioFirmadoService.getUsuarioFirmadoVO().getId());
		TipoRecorridoDTO tipoRecorridoDTO = tiposRecorridoDAO.getTiposRecorridoByCodigo(rutaVO.getCdTipoRecorrido().toUpperCase());
		rutaDTO.setEmpleado(empleadoDTO);
		rutaDTO.setTipoRecorrido(tipoRecorridoDTO);
		rutaDTO.setCreadoPor(empleadoDTO.getEmpId());
		rutaDTO.setModificadoPor(empleadoDTO.getEmpId());
		rutaDAO.saveOrUpdate(rutaDTO);
		responseRutaVO = ResponseConverter.copiarPropiedadesFull(rutaDTO, RutaVO.class);

		return responseRutaVO;
	}

	@Override
	@Transactional
	public RutaVO saveCoordenadasXRuta(RutasCoordenadasVO rutasCoordenadasVO) {
		RutaDTO rutaDTO = ResponseConverter.copiarPropiedadesFull(rutasCoordenadasVO.getRutaVO(), RutaDTO.class);
		EmpleadosDTO empleadoDTO = empleadoDAO.findOne(rutasCoordenadasVO.getRutaVO().getUsuarioId()); 
		rutaDTO.setEmpleado(empleadoDTO);
		rutaDTO.setCreadoPor(rutasCoordenadasVO.getRutaVO().getUsuarioId());
		rutaDTO.setModificadoPor(rutasCoordenadasVO.getRutaVO().getUsuarioId());
		rutaDTO.setFechaCreacion(new Date());
		rutaDTO.setUltimaModificacion(new Date());

		rutaDAO.save(rutaDTO);

		List<CoordenadaDTO> listaCoordenadaDTO = ResponseConverter.converterLista(new ArrayList<>(), rutasCoordenadasVO.getListaCordenadasVO(),  CoordenadaDTO.class);
		for (CoordenadaDTO coordenadaDTO : listaCoordenadaDTO) {
			coordenadaDTO.setRuta(rutaDTO);
			coordenadaDAO.save(coordenadaDTO);

		}
		return null;
	}

	@Override
	@Transactional
	public List<CoordenadaVO> getCoordenadasByDate(String placa, String fechaInicio, String fechaFin  ) throws ParseException {
		UsuarioVO usuarioVO = usuarioService.findUserByPlaca(placa);
		if (usuarioVO == null) return null;
		List<CoordenadaDTO> listCoordenadaDTO = coordenadaDAO.getCoordenadasByDate(usuarioVO.getEmpId(), fechaInicio, fechaFin);
		List<CoordenadaVO> listCoordenadaVO = ResponseConverter.converterLista(new ArrayList<>(), listCoordenadaDTO,CoordenadaVO.class);
		return listCoordenadaVO;
	}

	@Override
	@Transactional
	public List<BitacoraCoordenadaVO> coordenadasBitacora(String[] placas, Date fI, Date fF){
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
		List<BitacoraCoordenadaVO> bitacoras = new ArrayList<BitacoraCoordenadaVO>();
		
		for(String placa : placas){
			List<CoordenadaDTO> listCoordenadaDTO = coordenadaDAO.getCoordenadasByDate(placa, fI, fF);
			if(Collections.isEmpty(listCoordenadaDTO)){
				listCoordenadaDTO = coordenadaDAO.getLastCoordenadaByRange(placa, 30, fI, fF);
				if(Collections.isEmpty(listCoordenadaDTO)) continue;
			}
			BitacoraCoordenadaVO bitacora = new BitacoraCoordenadaVO();
			
			bitacora.setCoordenadas(new ArrayList<SimpleCoordenadaVO>());
			for(CoordenadaDTO coordenadaDTO : listCoordenadaDTO){
				bitacora.getCoordenadas().add(ResponseConverter.copiarPropiedadesFull(coordenadaDTO, SimpleCoordenadaVO.class));
			}
			
			bitacora.setEmpleado(ResponseConverter.copiarPropiedadesFull(listCoordenadaDTO.get(0).getEmpleado(), EmpleadosRespectivosAreaOperativaVO.class));
			
			Calendar now = Calendar.getInstance();
			
			Long diff = now.getTime().getTime() - listCoordenadaDTO.get(listCoordenadaDTO.size()-1).getFechaCoordenada().getTime();
			
			Long minutosTranscurridos = diff / (60 * 1000);
			IndicadorDTO indicador = indicadorDAO.lastIndicador(minutosTranscurridos);
			if (indicador== null){
				bitacora.setIndicador(new IndicadorVO(0L,0L,"#607D8B","DEFAULT"));
				
			}
			else{
				bitacora.setIndicador(new IndicadorVO());
				bitacora.setIndicador(ResponseConverter.copiarPropiedadesFull(indicador, IndicadorVO.class));
				
			}
			Long horasTranscurridas = minutosTranscurridos /60;
			Math.floor(horasTranscurridas);
			minutosTranscurridos -= (horasTranscurridas*60);
			Long diasTransCurridos = 0L;
			if(horasTranscurridas.longValue()>24L){
				diasTransCurridos = horasTranscurridas / 24;
				Math.floor(diasTransCurridos);
			}
			horasTranscurridas -= (diasTransCurridos*24);
			
			String tiempoTranscurrido ="";
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

			bitacora.getIndicador().setTiempoTranscurrido(tiempoTranscurrido);
			bitacoras.add(bitacora);
		}
		return bitacoras;
	}
}
