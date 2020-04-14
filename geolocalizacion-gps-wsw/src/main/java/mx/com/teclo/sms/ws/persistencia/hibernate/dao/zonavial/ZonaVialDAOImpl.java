package mx.com.teclo.sms.ws.persistencia.hibernate.dao.zonavial;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial.ZonaVialDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class ZonaVialDAOImpl extends BaseDaoHibernate<ZonaVialDTO> implements ZonaVialDAO {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ZonaVialDTO> allActivos(){
		Criteria c = getCurrentSession().createCriteria(ZonaVialDTO.class);
		c.add(Restrictions.eq("activo", 1));
		return c.list();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ZonaVialDTO activoByCode(String code){
		Criteria c = getCurrentSession().createCriteria(ZonaVialDTO.class);
		c.add(Restrictions.eq("codigoZonaVial", code));
		c.add(Restrictions.eq("activo", 1));
		return (ZonaVialDTO) c.uniqueResult();
	}
	
}