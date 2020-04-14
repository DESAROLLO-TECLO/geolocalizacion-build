package mx.com.teclo.alg.ws.persistencia.hibernate.dao.dispositivosmoviles;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.alg.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilTipoDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class DispositivoMovilTipoDAOImpl extends BaseDaoHibernate<DispositivoMovilTipoDTO> implements DispositivoMovilTipoDAO {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DispositivoMovilTipoDTO activoByCode(String code){
		Criteria c = getCurrentSession().createCriteria(DispositivoMovilTipoDTO.class);
		c.add(Restrictions.eq("cdTipoDispositivo", code));
		c.add(Restrictions.eq("activo", 1));
		return (DispositivoMovilTipoDTO) c.uniqueResult();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<DispositivoMovilTipoDTO> tiposDispositivosActivos() {
		Criteria criteria = getCurrentSession().createCriteria(DispositivoMovilTipoDTO.class);
		criteria.add(Restrictions.eq("activo", 1));
		criteria.addOrder(Order.asc("idTipoDispositivo"));
 		return criteria.list();	 
	}
	
}