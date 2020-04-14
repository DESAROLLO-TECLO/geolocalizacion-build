package mx.com.teclo.alg.ws.persistencia.hibernate.dao.eventogps;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.eventoalg.TipoEventoAlgDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class TipoEventoAlgDAOImpl extends BaseDaoHibernate<TipoEventoAlgDTO> implements TipoEventoAlgDAO {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TipoEventoAlgDTO activoByCode(String code){
		Criteria c = getCurrentSession().createCriteria(TipoEventoAlgDTO.class);
		c.add(Restrictions.eq("cdTipoEvento", code));
		c.add(Restrictions.eq("estatusEvento", 1));
		return (TipoEventoAlgDTO) c.uniqueResult();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<TipoEventoAlgDTO> getTipoEventosActivos() {
		Criteria criteria = getCurrentSession().createCriteria(TipoEventoAlgDTO.class);
		criteria.add(Restrictions.eq("estatusEvento", 1));
		criteria.addOrder(Order.asc("idTipoEvento"));
 		return criteria.list();	 
	}
}