package mx.com.teclo.alg.ws.persistencia.hibernate.dao.parametro;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.parametro.ParametroDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class ParametroDAOImpl extends BaseDaoHibernate<ParametroDTO> implements ParametroDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<ParametroDTO> restriction() {
		Criteria c = getCurrentSession().createCriteria(ParametroDTO.class);
		c.add(Restrictions.eq("stActivo", 1));
		return (List<ParametroDTO>)c.list();
	}

	@Override
	public ParametroDTO restrictionByCode(String cdParametro) {
		Criteria c = getCurrentSession().createCriteria(ParametroDTO.class);
		c.add(Restrictions.eq("cdParametro", cdParametro));
		c.add(Restrictions.eq("stActivo", 1));
		return (ParametroDTO)c.uniqueResult();
	}

}
