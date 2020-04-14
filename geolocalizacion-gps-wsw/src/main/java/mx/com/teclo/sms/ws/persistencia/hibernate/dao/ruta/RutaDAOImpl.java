package mx.com.teclo.sms.ws.persistencia.hibernate.dao.ruta;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.geolocalizacion.RutaDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class RutaDAOImpl extends BaseDaoHibernate<RutaDTO> implements RutaDAO {

	private static final Logger logger = LoggerFactory.getLogger(RutaDAOImpl.class);
 

	@Override
	public List<RutaDTO> getRutasByUser(Long idEmpleado , Long tipoRecorrido) {
 		Criteria criteria = getCurrentSession().createCriteria(RutaDTO.class);
		criteria.add(Restrictions.ne("idRuta", 1L));
		criteria.add(Restrictions.eq("empleado.empId", idEmpleado));
		criteria.add(Restrictions.eq("tipoRecorrido.idTipoRecorrido", tipoRecorrido));
		criteria.addOrder(Order.asc("fechaCreacion"));
		return criteria.list();
	}

}
