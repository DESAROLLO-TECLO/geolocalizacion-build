package mx.com.teclo.sms.ws.negocio.service.areaoperativa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.lang.Collections;
import mx.com.teclo.arquitectura.ortogonales.util.ResponseConverter;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.areaoperativa.AreaOperativaDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.areaoperativa.AreaOperativaEmpleadoDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dao.empleado.EmpleadoDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.areaoperativa.AreaOperativaDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.areaoperativa.AreaOperativaEmpleadoDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.areaoperativa.EnumCargosCodigos;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.EmpleadosDTO;
import mx.com.teclo.sms.ws.persistencia.vo.areaoperativa.AreaOperativaSupervisorVO;
import mx.com.teclo.sms.ws.persistencia.vo.areaoperativa.AreaOperativaVO;
import mx.com.teclo.sms.ws.persistencia.vo.empleado.EmpleadosRespectivosAreaOperativaVO;

@Service
public class AreaOperativaServiceImpl implements AreaOperativaService {

	@Autowired
	private AreaOperativaEmpleadoDAO areaOperativaEmpleadoDAO;
	
	@Autowired
	private AreaOperativaDAO areaOperativaDAO;
	
	@Autowired
	private EmpleadoDAO empleadoDAO;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<AreaOperativaVO> areasByEmp(String empPlaca, String procedence){
		EmpleadosDTO empleado = empleadoDAO.findUserByPlaca(empPlaca);
		if(empleado == null) return null;
		
		List<AreaOperativaEmpleadoDTO> interseccion = 
				areaOperativaEmpleadoDAO.findByPlacaAndCargo(empleado.getEmpId(),
						EnumCargosCodigos.SUPERVISOR.getCode());
		
		if(Collections.isEmpty(interseccion))return null;
		
		List<AreaOperativaVO> lVO = new ArrayList<AreaOperativaVO>();
		
		for(AreaOperativaEmpleadoDTO interDTO : interseccion){
			//AreaOperativaDTO areaDTO = areaOperativaDAO.findOne(interDTO.getId().getAreaOperativaId());
			AreaOperativaDTO areaDTO = areaOperativaDAO.findOne(1L);// Este servicio ya no funciona
			AreaOperativaVO areaVO = ResponseConverter.copiarPropiedadesFull(areaDTO, AreaOperativaVO.class);
			
			
			List<AreaOperativaEmpleadoDTO> interseccionSecundaria = 
					areaOperativaEmpleadoDAO.findByAreaAndCargo(areaDTO.getAreaOperativaId(),
							EnumCargosCodigos.OFICIAL.getCode());
			
			areaVO.setEmpleados(new ArrayList<EmpleadosRespectivosAreaOperativaVO>());
			if(procedence.equals("M") && interseccionSecundaria.size()>0)
				areaVO.getEmpleados().add(new EmpleadosRespectivosAreaOperativaVO("all","Todos", "", "", -1));
			for(AreaOperativaEmpleadoDTO interSecDTO : interseccionSecundaria){
				areaVO.getEmpleados().
				add(ResponseConverter.copiarPropiedadesFull(interSecDTO.getEmpleado(), EmpleadosRespectivosAreaOperativaVO.class));
			}
			lVO.add(areaVO);
		}
		return lVO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<AreaOperativaSupervisorVO> areasSupervisor(){
		
		List<AreaOperativaEmpleadoDTO> interseccion = 
				areaOperativaEmpleadoDAO.findByCargo(EnumCargosCodigos.SUPERVISOR.getCode());
		
		if(Collections.isEmpty(interseccion))return null;
		
		List<AreaOperativaSupervisorVO> lVO = new ArrayList<AreaOperativaSupervisorVO>();
		
		for(AreaOperativaEmpleadoDTO interDTO : interseccion){
			//AreaOperativaDTO areaDTO = areaOperativaDAO.findOne(interDTO.getId().getAreaOperativaId());
			AreaOperativaDTO areaDTO = areaOperativaDAO.findOne(1L);// Este servicio ya no funciona
			
			AreaOperativaSupervisorVO areaVO = ResponseConverter.copiarPropiedadesFull(areaDTO, AreaOperativaSupervisorVO.class);
			
			areaVO.setSupervisor(ResponseConverter.copiarPropiedadesFull(interDTO.getEmpleado(), EmpleadosRespectivosAreaOperativaVO.class));
			lVO.add(areaVO);
		}
		return lVO;
	}
}
