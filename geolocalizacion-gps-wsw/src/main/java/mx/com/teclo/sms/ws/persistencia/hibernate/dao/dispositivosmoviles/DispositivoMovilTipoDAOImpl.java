package mx.com.teclo.sms.ws.persistencia.hibernate.dao.dispositivosmoviles;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilTipoDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class DispositivoMovilTipoDAOImpl extends BaseDaoHibernate<DispositivoMovilTipoDTO> implements DispositivoMovilTipoDAO{
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<DispositivoMovilTipoDTO> allActivos(){
		Criteria c = getCurrentSession().createCriteria(DispositivoMovilTipoDTO.class);
		c.add(Restrictions.eq("activo",1));
		return c.list();
	}
}
