package mx.com.teclo.sms.ws.persistencia.hibernate.dao.dispositivosmoviles;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilMarcaDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class DispositivoMovilMarcaDAOImpl extends BaseDaoHibernate<DispositivoMovilMarcaDTO> implements DispositivoMovilMarcaDAO {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<DispositivoMovilMarcaDTO> allActivos(){
		Criteria c = getCurrentSession().createCriteria(DispositivoMovilMarcaDTO.class);
		c.add(Restrictions.eq("activo", 1));
		return c.list();
	}
}
