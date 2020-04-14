package mx.com.teclo.alg.ws.persistencia.hibernate.dao.empleados;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.empleados.EmpleadosDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class EmpleadoDAOImpl extends BaseDaoHibernate<EmpleadosDTO> implements EmpleadoDAO{
	
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
}