package mx.com.teclo.sms.ws.negocio.service.dispositivosmoviles;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.lang.Collections;
import mx.com.teclo.sms.ws.negocio.service.comun.UsuarioFirmadoService;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.dispositivosmoviles.DispositivoMovilDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.dispositivosmoviles.DispositivoMovilMarcaDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.dispositivosmoviles.DispositivoMovilModeloDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.dispositivosmoviles.DispositivoMovilTipoDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.zonavial.ZonaVialDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.zonavial.ZonaVialDispositivoDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial.ZonaVialDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial.ZonaVialDispositivoDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial.ZonaVialDispositivoIdDTO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.DispositivoMovilFullConsultaVO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.DispositivoMovilMarcaVO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.DispositivoMovilModeloSinMarcaVO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.DispositivoMovilPersistenciaVO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.DispositivoMovilTipoVO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.MarcaModeloVO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.RequestDispositivoMovilFullConsultaVO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.ResponseDispositivoMovilPersistenciaVO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.ResponseDispositivosMovilFullConsultaVO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.newDispositivoMovilRequierementsVO;
import mx.com.teclo.sms.ws.persistencia.vo.zonavial.ZonaVialVO;
import mx.com.teclo.sms.ws.util.comun.ResponseConverter;
import mx.com.teclo.sms.ws.util.enums.Codigos;
import mx.com.teclo.sms.ws.util.enums.Mensajes;

@Service
public class DispositivosMovilesServiceImpl implements DispositivosMovilesService {

	@Autowired
	private DispositivoMovilDAO dispositivoMovilDAO;
	
	@Autowired
	private DispositivoMovilModeloDAO dispositivoMovilModeloDAO;
	
	@Autowired
	private DispositivoMovilMarcaDAO dispositivoMovilMarcaDAO;
	
	@Autowired
	private DispositivoMovilTipoDAO dispositivoMovilTipoDAO;
	
	@Autowired
	private ZonaVialDispositivoDAO ZonaVialDispositivoDAO;
	
	@Autowired
	private UsuarioFirmadoService usuarioFirmadoService;
	
	@Autowired
	private ZonaVialDAO zonaVialDAO;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public newDispositivoMovilRequierementsVO newVoRequierements(){
		newDispositivoMovilRequierementsVO response = new newDispositivoMovilRequierementsVO();
		
		List<DispositivoMovilMarcaVO> marcasVO = ResponseConverter.converterLista(new ArrayList<>(), dispositivoMovilMarcaDAO.allActivos(), DispositivoMovilMarcaVO.class);
		
		List<MarcaModeloVO> marcasModelos = new ArrayList<MarcaModeloVO>();
		for(DispositivoMovilMarcaVO marca : marcasVO){
			List<DispositivoMovilModeloSinMarcaVO> modelos =  
					ResponseConverter.converterLista(new ArrayList<>(),dispositivoMovilModeloDAO.byMarcaCode(marca.getMarcaCodigo()), DispositivoMovilModeloSinMarcaVO.class);
			
			marcasModelos.add(new MarcaModeloVO(marca, modelos));
		}
		response.setTiposDispositivos(ResponseConverter.converterLista(new ArrayList<>(), dispositivoMovilTipoDAO.allActivos(), DispositivoMovilTipoVO.class));
		response.setZonasViales(ResponseConverter.converterLista(new ArrayList<>(), zonaVialDAO.allActivos(), ZonaVialVO.class));
		response.setMarcasModelos(marcasModelos);
		
		return response;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDispositivoMovilPersistenciaVO persistDevice(DispositivoMovilPersistenciaVO dispositivoVO){
		ResponseDispositivoMovilPersistenciaVO response = new ResponseDispositivoMovilPersistenciaVO();
		dispositivoVO.setFlagRecord(dispositivoMovilDAO.flagDispositivo(dispositivoVO.getNumIp(), dispositivoVO.getNumSerie(), dispositivoVO.getNumSim()));
		if(!Collections.isEmpty(dispositivoVO.getFlagRecord())){
			response.setCodigoHttp(Codigos.BAD_REQUEST.getProcesoId());
			response.setDescripcion(Mensajes.MSJ_DUPLICATE_OBJECT.getProcesoId());
			response.setDispositivo(dispositivoVO);
			return response;
		}
		
		DispositivoMovilDTO dispositivoDTO = ResponseConverter.copiarPropiedadesFull(dispositivoVO, DispositivoMovilDTO.class);
		dispositivoDTO.setDispositivoModelo(dispositivoMovilModeloDAO.findOne(dispositivoVO.getModelo()));
		dispositivoDTO.setDispositivoMovil(dispositivoMovilTipoDAO.findOne(dispositivoVO.getTipoDispositivo()));
		dispositivoDTO.setCreadoPor(usuarioFirmadoService.getUsuarioFirmadoVO().getId());
		dispositivoDTO.setModificadoPor(dispositivoDTO.getCreadoPor());
		dispositivoDTO.setFechaCreacion(Calendar.getInstance().getTime());
		dispositivoDTO.setFechaModificacion(dispositivoDTO.getFechaCreacion());
		dispositivoDTO.setActivo(1);
		dispositivoMovilDAO.save(dispositivoDTO);
		//dispositivoMovilDAO.flush();
		
		if(dispositivoVO.getZonaVial()!=null){
			ZonaVialDTO zonaDTO = zonaVialDAO.findOne(dispositivoVO.getZonaVial());
			ZonaVialDispositivoDTO zonaDisp = new ZonaVialDispositivoDTO();
			ZonaVialDispositivoIdDTO id = new ZonaVialDispositivoIdDTO();
			id.setDispositivoId(dispositivoDTO.getIdDispositivo());
			id.setZonaVialId(zonaDTO.getIdZonaVial());
			zonaDisp.setId(id);
			zonaDisp.setZonaVial(zonaDTO);
			zonaDisp.setDispositivo(dispositivoDTO);
			zonaDisp.setActivo(1);
			zonaDisp.setFechaCreacion(dispositivoDTO.getFechaCreacion());
			zonaDisp.setFechaModificacion(dispositivoDTO.getFechaCreacion());
			zonaDisp.setCreadoPor(dispositivoDTO.getCreadoPor());
			zonaDisp.setModificadoPor(dispositivoDTO.getCreadoPor());
			ZonaVialDispositivoDAO.save(zonaDisp);
			//ZonaVialDispositivoDAO.flush();
		}
		
		dispositivoVO.setIdDispositivo(dispositivoDTO.getIdDispositivo());
		response.setCodigoHttp(Codigos.CREATE.getProcesoId());
		response.setDescripcion(Mensajes.MSJ_CREATE.getProcesoId());
		response.setDispositivo(dispositivoVO);
		return response;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDispositivoMovilPersistenciaVO updateDevice(DispositivoMovilPersistenciaVO dispositivoVO){
		ResponseDispositivoMovilPersistenciaVO response = new ResponseDispositivoMovilPersistenciaVO();
		dispositivoVO.setFlagRecord(dispositivoMovilDAO.flagDispositivoForUpdate(dispositivoVO.getIdDispositivo(), dispositivoVO.getNumIp(), dispositivoVO.getNumSerie(), dispositivoVO.getNumSim()));
		DispositivoMovilDTO dispositivoDTO = dispositivoMovilDAO.findOne(dispositivoVO.getIdDispositivo());
		if(!Collections.isEmpty(dispositivoVO.getFlagRecord()) || dispositivoDTO == null){
			response.setCodigoHttp(Codigos.BAD_REQUEST.getProcesoId());
			response.setDescripcion(Mensajes.MSJ_DUPLICATE_OBJECT.getProcesoId());
			response.setDispositivo(dispositivoVO);
			return response;
		}
		
		dispositivoDTO.setNumIp(dispositivoVO.getNumIp());
		dispositivoDTO.setNumSim(dispositivoVO.getNumSim());
		dispositivoDTO.setNumSerie(dispositivoVO.getNumSerie());
		dispositivoDTO.setDispositivoModelo(dispositivoMovilModeloDAO.findOne(dispositivoVO.getModelo()));
		dispositivoDTO.setDispositivoMovil(dispositivoMovilTipoDAO.findOne(dispositivoVO.getTipoDispositivo()));
		dispositivoDTO.setModificadoPor(usuarioFirmadoService.getUsuarioFirmadoVO().getId());
		dispositivoDTO.setFechaModificacion(Calendar.getInstance().getTime());
		dispositivoMovilDAO.update(dispositivoDTO);
		
		if(dispositivoVO.getZonaVial()!=null && dispositivoDTO.getZonaVialDispositivos() != null){
			boolean newRequiredZone = true;
			for(ZonaVialDispositivoDTO zona : dispositivoDTO.getZonaVialDispositivos()){
				if(zona.getActivo() == 1){
					if(zona.getZonaVial().getIdZonaVial().longValue() != dispositivoVO.getZonaVial().longValue()){
						zona.setActivo(0);
						zona.setModificadoPor(usuarioFirmadoService.getUsuarioFirmadoVO().getId());
						zona.setFechaModificacion(Calendar.getInstance().getTime());
						ZonaVialDispositivoDAO.update(zona);
					}else{
						newRequiredZone = false;
						break;
					}
				}else{
					if(zona.getZonaVial().getIdZonaVial().longValue() == dispositivoVO.getZonaVial().longValue()){
						zona.setActivo(1);
						zona.setModificadoPor(usuarioFirmadoService.getUsuarioFirmadoVO().getId());
						zona.setFechaModificacion(Calendar.getInstance().getTime());
						ZonaVialDispositivoDAO.update(zona);
						newRequiredZone = false;
					}
				}
			}
			if(newRequiredZone){
				ZonaVialDTO zonaDTO = zonaVialDAO.findOne(dispositivoVO.getZonaVial());
				ZonaVialDispositivoDTO zonaDisp = new ZonaVialDispositivoDTO();
				ZonaVialDispositivoIdDTO id = new ZonaVialDispositivoIdDTO();
				id.setDispositivoId(dispositivoDTO.getIdDispositivo());
				id.setZonaVialId(zonaDTO.getIdZonaVial());
				zonaDisp.setId(id);
				zonaDisp.setZonaVial(zonaDTO);
				zonaDisp.setDispositivo(dispositivoDTO);
				zonaDisp.setActivo(1);
				zonaDisp.setFechaCreacion(Calendar.getInstance().getTime());
				zonaDisp.setFechaModificacion(Calendar.getInstance().getTime());
				zonaDisp.setCreadoPor(usuarioFirmadoService.getUsuarioFirmadoVO().getId());
				zonaDisp.setModificadoPor(usuarioFirmadoService.getUsuarioFirmadoVO().getId());
				ZonaVialDispositivoDAO.save(zonaDisp);
				//ZonaVialDispositivoDAO.flush();
			}
		}
		
		dispositivoVO.setIdDispositivo(dispositivoDTO.getIdDispositivo());
		response.setCodigoHttp(Codigos.UPDATE.getProcesoId());
		response.setDescripcion(Mensajes.MSJ_UPDATE.getProcesoId());
		response.setDispositivo(dispositivoVO);
		return response;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDispositivosMovilFullConsultaVO searchDevice
	(String numSerie, String numSim, String numIp, List<Long> modelos, List<Long> tipoDispositivo, List<Long> zonaVial, Boolean complementacion, Boolean sinZonaVial){
		RequestDispositivoMovilFullConsultaVO vo = new RequestDispositivoMovilFullConsultaVO(numSerie, numSim, numIp, modelos, tipoDispositivo, zonaVial, complementacion, sinZonaVial);
		List<DispositivoMovilDTO> dtos = dispositivoMovilDAO.fullSearch(vo);
		ResponseDispositivosMovilFullConsultaVO response = new ResponseDispositivosMovilFullConsultaVO();
		
		if(Collections.isEmpty(dtos)){
			response.setCodigoHttp(Codigos.NOT_FOUND.getProcesoId());
			response.setDescripcion(Mensajes.MSJ_NOT_DATA_FOUND.getProcesoId());
			response.setDispositivos(null);
		}else{
			response.setCodigoHttp(Codigos.SUCCESS.getProcesoId());
			response.setDescripcion(Mensajes.MSJ_SUCCESS.getProcesoId());
			response.setDispositivos(ResponseConverter.converterLista(new ArrayList<DispositivoMovilFullConsultaVO>(), dtos, DispositivoMovilFullConsultaVO.class));
			String zonaDefault = "Sin zona vial";
			for(int x = 0; x < response.getDispositivos().size(); x++){
				String zona = zonaDefault;
				for(ZonaVialDispositivoDTO zDTO : dtos.get(x).getZonaVialDispositivos()){
					if(zDTO.getActivo() == 1 && zDTO.getZonaVial().getActivo() == 1){
						zona = zDTO.getZonaVial().getNombreZonaVial();
						break;
					}
				}
				response.getDispositivos().get(x).setZonaVial(zona);
			}
		}
		return response;
	}
}























































