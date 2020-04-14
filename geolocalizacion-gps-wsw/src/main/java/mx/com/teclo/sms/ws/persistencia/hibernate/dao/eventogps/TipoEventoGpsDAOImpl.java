package mx.com.teclo.sms.ws.persistencia.hibernate.dao.eventogps;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.hh.eventogps.TipoEventoGpsDTO;
import mx.com.teclo.sms.ws.rest.geolocalizacion.LocalizacionRestController;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class TipoEventoGpsDAOImpl extends BaseDaoHibernate<TipoEventoGpsDTO> implements TipoEventoGpsDAO {
	private static final Logger logger = LoggerFactory.getLogger(LocalizacionRestController.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TipoEventoGpsDTO getTipoEvento(Long idTipoEvento) {
		Criteria criteria = getCurrentSession().createCriteria(TipoEventoGpsDTO.class);
		criteria.add(Restrictions.eq("idTipoEvento", idTipoEvento));
		criteria.add(Restrictions.eq("estatusEvento", "A"));

 		return  (TipoEventoGpsDTO) criteria.uniqueResult();		 
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TipoEventoGpsDTO getTipoEventoByCode(String code) {
		Criteria criteria = getCurrentSession().createCriteria(TipoEventoGpsDTO.class);
		criteria.add(Restrictions.eq("codigoTipoEvento", code));
		criteria.add(Restrictions.eq("estatusEvento", "A"));

 		return  (TipoEventoGpsDTO) criteria.uniqueResult();		 
	}
 
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<TipoEventoGpsDTO> getTipoEventosActivos() {
		Criteria criteria = getCurrentSession().createCriteria(TipoEventoGpsDTO.class);
		criteria.add(Restrictions.eq("estatusEvento", "A"));
		criteria.addOrder(Order.asc("idTipoEvento"));
 		return criteria.list();	 
	}
}
