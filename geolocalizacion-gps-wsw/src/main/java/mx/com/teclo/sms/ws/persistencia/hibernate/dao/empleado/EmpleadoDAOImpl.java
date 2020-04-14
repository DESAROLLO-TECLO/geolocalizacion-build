package mx.com.teclo.sms.ws.persistencia.hibernate.dao.empleado;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.EmpleadosDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class EmpleadoDAOImpl extends BaseDaoHibernate<EmpleadosDTO> implements EmpleadoDAO {
 
	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmpleadosDTO findUserByPlaca(String placa) {
		Criteria criteria = getCurrentSession().createCriteria(EmpleadosDTO.class);
		criteria.add(Restrictions.eq("empPlaca", placa));
		criteria.add(Restrictions.eq("empStatus", "A"));

 		return (EmpleadosDTO) criteria.uniqueResult();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<EmpleadosDTO> findUsersByPlacas(ArrayList<String> placas) {
		Criteria criteria = getCurrentSession().createCriteria(EmpleadosDTO.class);
		Disjunction or = Restrictions.disjunction();
		for(String placa : placas){
			or.add(Restrictions.eq("empPlaca", placa));
		}
		criteria.add(Restrictions.eq("empStatus", "A"));
		criteria.add(or);

 		return (ArrayList<EmpleadosDTO>) criteria.list();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<EmpleadosDTO> oficialesSinAsignacion(){
		return getCurrentSession().createSQLQuery(
			"SELECT EMP1.* FROM EMPLEADOS EMP1 "+
			"LEFT JOIN TMS004D_ZONA_VIAL_CARGO_EMP SUB1 ON SUB1.EMP_ID = EMP1.EMP_ID "+
			"WHERE SUB1.EMP_ID IS NULL AND SUB1.ID_ZONA_VIAL IS NULL AND EMP1.EMP_ID <> 99 "+ 
			"AND EMP1.EMP_TIP_ID IN (1,10,14) AND EMP1.EMP_STATUS = 'A' "+
			"UNION "+
			"SELECT EMP2.* FROM EMPLEADOS EMP2 "+ 
			"INNER JOIN TMS004D_ZONA_VIAL_CARGO_EMP SUB2 ON SUB2.EMP_ID = EMP2.EMP_ID "+ 
			"WHERE EMP2.EMP_TIP_ID IN (1,10,14) AND EMP2.EMP_STATUS = 'A' AND SUB2.ST_ACTIVO = 0 "+ 
			"AND SUB2.EMP_ID NOT IN(SELECT SUB3.EMP_ID FROM TMS004D_ZONA_VIAL_CARGO_EMP SUB3 WHERE SUB3.ST_ACTIVO = 1 AND SUB3.EMP_ID = SUB2.EMP_ID)") 
			.addEntity(EmpleadosDTO.class).list();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<EmpleadosDTO> activoByOfficerInfo(String placa, String nombre, String paterno, String materno){
		Criteria c = getCurrentSession().createCriteria(EmpleadosDTO.class);
		Criteria c2 = getCurrentSession().createCriteria(EmpleadosDTO.class);
		c2.createAlias("cargos", "cargos");
		c2.createAlias("cargos.cargo", "cargo");
		
		Disjunction or = Restrictions.disjunction();
		Disjunction or2 = Restrictions.disjunction();
		Disjunction or3 = Restrictions.disjunction();
		if(placa != null && !placa.equals(""))
			or.add(Restrictions.eq("empPlaca", placa));
		if(nombre != null && !nombre.equals(""))
			or.add(Restrictions.eq("empNombre", nombre));
		if(paterno != null && !paterno.equals(""))
			or.add(Restrictions.eq("empApePaterno", paterno));
		if(materno != null && !materno.equals(""))
			or.add(Restrictions.eq("empApeMaterno", materno));

		or2.add(Restrictions.eq("empTipId", 1L))
		.add(Restrictions.eq("empTipId", 10L))
		.add(Restrictions.eq("empTipId", 14L));
		
		or3.add(Restrictions.eq("cargo.cargoCodigo", "OFICIAL"));
		
		List<EmpleadosDTO> response = (List<EmpleadosDTO>) c.add(or).add(or2).add(Restrictions.eq("empStatus", "A")).list();
		List<EmpleadosDTO> response2 = (List<EmpleadosDTO>) c2.add(or).add(or3).add(Restrictions.eq("empStatus", "A")).list();
		response.addAll(response2); 
		
		List<EmpleadosDTO> buf = new ArrayList<EmpleadosDTO>();
		for(EmpleadosDTO empleado : response){
			boolean found = false;
			for(EmpleadosDTO sEmpleado : response2){
				if(empleado.getEmpPlaca().equals(sEmpleado.getEmpPlaca())){
					found = true;
					break;
				}
			}
			if(!found){
				buf.add(empleado);
			}
		}
		response2.addAll(buf);

		return response2;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<EmpleadosDTO> oficialesPorZonaVial(Long id){
		return getCurrentSession().createCriteria(EmpleadosDTO.class)
			.createAlias("zonaVialEmpleados", "zonas")
			.createAlias("zonas.zonaVial", "zona")
			
			.createAlias("cargos", "cargos")
			.createAlias("cargos.cargo", "cargo")
			
			.add(Restrictions.eq("zona.idZonaVial", id))
			.add(Restrictions.eq("cargo.cargoCodigo", "OFICIAL"))
			.add(Restrictions.eq("empStatus", "A"))
			.add(Restrictions.eq("zonas.activo", 1))
			.add(Restrictions.eq("zona.activo", 1))
			
			.list();
	}
	
}
