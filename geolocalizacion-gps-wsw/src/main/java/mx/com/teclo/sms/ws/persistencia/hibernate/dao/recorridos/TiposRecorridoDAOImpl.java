package mx.com.teclo.sms.ws.persistencia.hibernate.dao.recorridos;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.geolocalizacion.TipoRecorridoDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class TiposRecorridoDAOImpl extends BaseDaoHibernate<TipoRecorridoDTO> implements TiposRecorridoDAO {
 
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoRecorridoDTO> getTiposRecorrido() {
		Criteria criteria = getCurrentSession().createCriteria(TipoRecorridoDTO.class);
		criteria.add(Restrictions.eq("stActivo", 1));
 		criteria.addOrder(Order.asc("idTipoRecorrido"));

		return criteria.list();
	}

	@Override
	public  TipoRecorridoDTO  getTiposRecorridoByCodigo(String cdRecorrido) {
		Criteria criteria = getCurrentSession().createCriteria(TipoRecorridoDTO.class);
		criteria.add(Restrictions.eq("stActivo", 1));
		criteria.add(Restrictions.eq("cdTipoRecorrido", cdRecorrido));

		return (TipoRecorridoDTO) criteria.uniqueResult();
		
 	}

}
