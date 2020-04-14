package mx.com.teclo.sms.ws.persistencia.hibernate.dao.zonavial;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial.ZonaVialDispositivoDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class ZonaVialDispositivoDAOImpl extends BaseDaoHibernate<ZonaVialDispositivoDTO> implements ZonaVialDispositivoDAO {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ZonaVialDispositivoDTO> byZona(Long zona){
		Criteria c = getCurrentSession().createCriteria(ZonaVialDispositivoDTO.class);
		c.add(Restrictions.eq("id.zonaVialId",zona));
		return (List<ZonaVialDispositivoDTO>) c.list();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ZonaVialDispositivoDTO> activosByZona(Long zona){
		Criteria c = getCurrentSession().createCriteria(ZonaVialDispositivoDTO.class);
		c.add(Restrictions.eq("id.zonaVialId",zona));
		c.add(Restrictions.eq("activo",1));
		return (List<ZonaVialDispositivoDTO>) c.list();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ZonaVialDispositivoDTO activoByDispoInfo(String serie, String sim, String ip){
		Criteria c = getCurrentSession().createCriteria(ZonaVialDispositivoDTO.class);
		c.createAlias("dispositivo", "dispositivo");
		if(serie != null && !serie.equals(""))
			c.add(Restrictions.eq("dispositivo.numSerie", serie));
		if(sim != null && !sim.equals(""))
			c.add(Restrictions.eq("dispositivo.numSim", sim));
		if(ip != null && !ip.equals(""))
			c.add(Restrictions.eq("dispositivo.numIp", ip));
		c.add(Restrictions.eq("dispositivo.activo", 1));
		c.add(Restrictions.eq("activo", 1));
		return (ZonaVialDispositivoDTO) c.uniqueResult();
	}
}
