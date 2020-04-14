package mx.com.teclo.sms.ws.persistencia.hibernate.dao.coordenadas;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.geolocalizacion.IndicadorDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class IndicadorDAOImpl extends BaseDaoHibernate<IndicadorDTO> implements IndicadorDAO {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
//	@SuppressWarnings("unchecked")
	public IndicadorDTO lastIndicador(Long minutos){
		Criteria c = getCurrentSession().createCriteria(IndicadorDTO.class);
		c.add(Restrictions.le("indicadorTiempoInicial",minutos));
		c.add(Restrictions.ge("indicadorTiempoFinal",minutos));
//		c.addOrder(Order.desc("indicadorTiempo"));
//		List<IndicadorDTO> list = c.list();
		return (IndicadorDTO) c.uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<IndicadorDTO> allActivos(){
		Criteria c = getCurrentSession().createCriteria(IndicadorDTO.class);
		c.add(Restrictions.eq("activo",1));
		c.addOrder(Order.asc("indicadorTiempoInicial"));
		return c.list();
	}
}
