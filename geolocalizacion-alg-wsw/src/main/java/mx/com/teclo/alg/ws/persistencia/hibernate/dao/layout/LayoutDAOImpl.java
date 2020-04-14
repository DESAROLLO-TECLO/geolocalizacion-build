package mx.com.teclo.alg.ws.persistencia.hibernate.dao.layout;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.layout.LayoutDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class LayoutDAOImpl extends BaseDaoHibernate<LayoutDTO> implements LayoutDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<LayoutDTO> layout(String cdTpLayout) {
		Criteria c = getCurrentSession().createCriteria(LayoutDTO.class);
		c.createAlias("tpLayout", "tp");
		c.add(Restrictions.eq("tp.stActivo", 1));
		c.add(Restrictions.eq("tp.cdTpLayout", cdTpLayout));
		c.add(Restrictions.eq("stActivo", 1));
		c.addOrder(Order.asc("nuOrdenRegistro"));
		return (List<LayoutDTO>)c.list();
	}

}
