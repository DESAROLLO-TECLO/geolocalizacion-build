package mx.com.teclo.sms.ws.persistencia.hibernate.dao.areaoperativa;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.areaoperativa.AreaOperativaEmpleadoDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class AreaOperativaEmpleadoDAOImpl extends BaseDaoHibernate<AreaOperativaEmpleadoDTO> 
implements AreaOperativaEmpleadoDAO {
	
	@Override
	@SuppressWarnings("unchecked")
	public List<AreaOperativaEmpleadoDTO> findByPlacaAndCargo(Long empId, String cargoCodigo){
		Criteria c = getCurrentSession().createCriteria(AreaOperativaEmpleadoDTO.class);
		c.createAlias("cargo", "cargo");
		c.add(Restrictions.eq("id.empId", empId));
		c.add(Restrictions.eq("cargo.cargoCodigo", cargoCodigo));
		return c.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<AreaOperativaEmpleadoDTO> findByAreaAndCargo(Long area, String cargoCodigo){
		Criteria c = getCurrentSession().createCriteria(AreaOperativaEmpleadoDTO.class);
		c.createAlias("cargo", "cargo");
		c.add(Restrictions.eq("id.areaOperativaId", area));
		c.add(Restrictions.eq("cargo.cargoCodigo", cargoCodigo));
		return c.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<AreaOperativaEmpleadoDTO> findByCargo(String cargoCodigo){
		Criteria c = getCurrentSession().createCriteria(AreaOperativaEmpleadoDTO.class);
		c.createAlias("cargo", "cargo");
		c.add(Restrictions.eq("cargo.cargoCodigo", cargoCodigo));
		return c.list();
	}
}
