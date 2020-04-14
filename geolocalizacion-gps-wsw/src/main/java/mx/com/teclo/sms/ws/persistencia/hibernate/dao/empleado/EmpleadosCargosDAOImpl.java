package mx.com.teclo.sms.ws.persistencia.hibernate.dao.empleado;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.EmpleadosCargosDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class EmpleadosCargosDAOImpl extends BaseDaoHibernate<EmpleadosCargosDTO> implements EmpleadosCargosDAO {
	
	@Override
	public EmpleadosCargosDTO byCodeAndPlaca(String codigo, String placa){
		Criteria c = getCurrentSession().createCriteria(EmpleadosCargosDTO.class);
		c.createAlias("empleado", "empleado");
		c.createAlias("cargo", "cargo");
		c.add(Restrictions.eq("empleado.empPlaca", placa));
		c.add(Restrictions.eq("cargo.cargoCodigo", codigo));
		c.add(Restrictions.eq("activo", 1));
		return (EmpleadosCargosDTO) c.uniqueResult();
	}
}
