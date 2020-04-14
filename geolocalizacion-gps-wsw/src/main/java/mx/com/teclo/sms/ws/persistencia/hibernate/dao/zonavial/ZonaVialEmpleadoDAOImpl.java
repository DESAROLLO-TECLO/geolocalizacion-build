package mx.com.teclo.sms.ws.persistencia.hibernate.dao.zonavial;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial.ZonaVialCargoEmpleadoDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class ZonaVialEmpleadoDAOImpl extends BaseDaoHibernate<ZonaVialCargoEmpleadoDTO> implements ZonaVialEmpleadoDAO {
	
 
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	@SuppressWarnings("unchecked")
//	public List<ZonaVialEmpleadoDTO> findZonasByPlaca(String  empPlaca ) {
//		Criteria c = getCurrentSession().createCriteria(ZonaVialEmpleadoDTO.class);
//		c.createAlias("empleado", "empleado");
//		c.add(Restrictions.eq("empleado.empPlaca", empPlaca));
// 		return c.list();
//	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ZonaVialCargoEmpleadoDTO> findZonasByPlaca(String  empPlaca){
		return getCurrentSession().createCriteria(ZonaVialCargoEmpleadoDTO.class).
		createAlias("empleadoCargo", "empleadoCargo").
		add(Restrictions.eq("empleadoCargo.activo", 1)).
		createAlias("empleadoCargo.cargo", "cargo").
		add(Restrictions.eq("cargo.cargoCodigo", "SUPERVISOR")).
		add(Restrictions.eq("cargo.activo", 1)).
		createAlias("empleado", "empleado").
		add(Restrictions.eq("empleado.empStatus", "A")).
		add(Restrictions.eq("empleado.empPlaca", empPlaca)).
		add(Restrictions.eq("activo", 1)).list();
		
//		return getCurrentSession().createSQLQuery(
//			"SELECT * FROM TMS004D_ZONA_VIAL_CARGO_EMP WHERE ST_ACTIVO = 1 AND EMP_ID IN ( "+
//			"SELECT SUB.EMP_ID FROM TMS008D_EMPLEADOS_CARGOS SUB "+
//			"JOIN EMPLEADOS SUB1 ON SUB.EMP_ID = SUB1.EMP_ID "+
//			"WHERE "+
//			"SUB.ST_ACTIVO = 1 "+
//			"AND SUB1.EMP_STATUS = 'A' "+
//			"AND SUB.ID_CARGO = 1 "+
//			"AND SUB1.EMP_PLACA = :empPlaca)")
//			.addEntity(ZonaVialCargoEmpleadoDTO.class).setParameter("empPlaca", empPlaca).list();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ZonaVialCargoEmpleadoDTO> oficialesByZona(Long zona){
		return getCurrentSession().createCriteria(ZonaVialCargoEmpleadoDTO.class).
				createAlias("empleadoCargo", "empleadoCargo").
				add(Restrictions.eq("empleadoCargo.activo", 1)).
				createAlias("empleadoCargo.cargo", "cargo").
				add(Restrictions.eq("cargo.cargoCodigo", "OFICIAL")).
				add(Restrictions.eq("cargo.activo", 1)).
				createAlias("empleado", "empleado").
				add(Restrictions.eq("empleado.empStatus", "A")).
				createAlias("zonaVial", "zonaVial").
				add(Restrictions.eq("zonaVial.activo", 1)).
				add(Restrictions.eq("zonaVial.idZonaVial", zona)).list();
	}
	
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	@SuppressWarnings("unchecked")
//	public List<ZonaVialEmpleadoDTO> oficialesActivosByZona(Long zona){
//		Criteria c = getCurrentSession().createCriteria(ZonaVialEmpleadoDTO.class);
//		c.add(Restrictions.eq("id.zonaVialId",zona));
//		c.add(Restrictions.eq("activo",1));
//		return (List<ZonaVialEmpleadoDTO>) c.list();
//	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ZonaVialCargoEmpleadoDTO> oficialesActivosByZona(Long zona){
		return getCurrentSession().createCriteria(ZonaVialCargoEmpleadoDTO.class).
				createAlias("empleadoCargo", "empleadoCargo").
				add(Restrictions.eq("empleadoCargo.activo", 1)).
				createAlias("empleadoCargo.cargo", "cargo").
				add(Restrictions.eq("cargo.cargoCodigo", "OFICIAL")).
				add(Restrictions.eq("cargo.activo", 1)).
				createAlias("empleado", "empleado").
				add(Restrictions.eq("empleado.empStatus", "A")).
				createAlias("zonaVial", "zonaVial").
				add(Restrictions.eq("zonaVial.activo", 1)).
				add(Restrictions.eq("zonaVial.idZonaVial", zona)).
				add(Restrictions.eq("activo", 1)).list();
		
//		return getCurrentSession().createSQLQuery(
//			"SELECT * FROM TMS004D_ZONA_VIAL_EMPLEADOS  "+
//			"WHERE ID_ZONA_VIAL = :zona_id AND ST_ACTIVO = 1 AND EMP_ID IN ( "+
//			"SELECT SUB.EMP_ID FROM EMPLEADOS SUB LEFT JOIN "+
//			"TMS008D_EMPLEADOS_CARGOS SUB1  "+
//			"ON SUB1.EMP_ID = SUB.EMP_ID "+
//			"WHERE (SUB1.ID_CARGO = 2 OR SUB1.ID_CARGO IS NULL OR SUB.EMP_TIP_ID IN (1,10,14)) "+
//			"AND SUB.EMP_STATUS = 'A'  "+
//			"AND SUB1.ST_ACTIVO = 1 OR SUB1.ST_ACTIVO IS NULL )")
//				.addEntity(ZonaVialCargoEmpleadoDTO.class).setParameter("zona_id", zona).list();
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ZonaVialCargoEmpleadoDTO> activoByOfficerInfo(String placa, String nombre, String paterno, String materno){
		Criteria c = getCurrentSession().createCriteria(ZonaVialCargoEmpleadoDTO.class);
		Criteria c2 = getCurrentSession().createCriteria(ZonaVialCargoEmpleadoDTO.class);
		
		c.createAlias("empleado", "empleado1");
		
		c2.createAlias("empleado", "empleado2");
		c2.createAlias("empleado2.cargos", "cargos");
		c2.createAlias("cargos.cargo", "cargo");
		
		Disjunction orBasic1 = Restrictions.disjunction();
		Disjunction orBasic2 = Restrictions.disjunction();
		Disjunction or2 = Restrictions.disjunction();
		Disjunction or3 = Restrictions.disjunction();
		
		if(placa != null && !placa.equals("")){
			orBasic1.add(Restrictions.eq("empleado1.empPlaca", placa));
			orBasic2.add(Restrictions.eq("empleado2.empPlaca", placa));
		}
		if(nombre != null && !nombre.equals("")){
			orBasic1.add(Restrictions.eq("empleado1.empNombre", nombre));
			orBasic2.add(Restrictions.eq("empleado2.empNombre", nombre));
		}
		if(paterno != null && !paterno.equals("")){
			orBasic1.add(Restrictions.eq("empleado1.empApePaterno", paterno));
			orBasic2.add(Restrictions.eq("empleado2.empApePaterno", paterno));
		}
		if(materno != null && !materno.equals("")){
			orBasic1.add(Restrictions.eq("empleado1.empApeMaterno", materno));
			orBasic2.add(Restrictions.eq("empleado2.empApeMaterno", materno));
		}
		
		or2.add(Restrictions.eq("empleado1.empTipId", 1L));
		or2.add(Restrictions.eq("empleado1.empTipId", 10L));
		or2.add(Restrictions.eq("empleado1.empTipId", 14L));
		
		or3.add(Restrictions.eq("cargo.cargoCodigo", "OFICIAL"));
		
		c.add(Restrictions.eq("activo", 1));
		c2.add(Restrictions.eq("activo", 1));
//		c.add(Restrictions.eq("empleado.empStatus", "A"));
//		c.add(or);
//		c.add(or2);
		
		List<ZonaVialCargoEmpleadoDTO> response = (List<ZonaVialCargoEmpleadoDTO>) c.add(orBasic1).add(or2).add(Restrictions.eq("empleado1.empStatus", "A")).list();
		List<ZonaVialCargoEmpleadoDTO> response2 = (List<ZonaVialCargoEmpleadoDTO>) c2.add(orBasic2).add(or3).add(Restrictions.eq("empleado2.empStatus", "A")).list();
		response.addAll(response2); 
		
		List<ZonaVialCargoEmpleadoDTO> buf = new ArrayList<ZonaVialCargoEmpleadoDTO>();
		for(ZonaVialCargoEmpleadoDTO empleado : response){
			boolean found = false;
			for(ZonaVialCargoEmpleadoDTO sEmpleado : response2){
				if(empleado.getEmpleado().getEmpPlaca().equals(sEmpleado.getEmpleado().getEmpPlaca())){
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
	public ZonaVialCargoEmpleadoDTO zonaByEmpPlaca(String placa){
		Criteria c = getCurrentSession().createCriteria(ZonaVialCargoEmpleadoDTO.class);
		c.createAlias("empleado", "empleado");
		c.add(Restrictions.eq("empleado.empPlaca", placa));
		c.add(Restrictions.eq("empleado.empStatus", "A"));
		c.add(Restrictions.eq("activo", 1));
		return (ZonaVialCargoEmpleadoDTO) c.uniqueResult();
	}
}
