package mx.com.teclo.sms.ws.persistencia.hibernate.dao.coordenadas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import io.jsonwebtoken.lang.Collections;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.geolocalizacion.CoordenadaDTO;
import mx.com.teclo.sms.ws.rest.geolocalizacion.LocalizacionRestController;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class CoordenadaDAOImpl extends BaseDaoHibernate<CoordenadaDTO> implements CoordenadaDAO {
	private static final Logger logger = LoggerFactory.getLogger(LocalizacionRestController.class);

	@Override
	public List<CoordenadaDTO> getCoordenadasByRuta(Long idRuta) {
		
		Criteria criteria = getCurrentSession().createCriteria(CoordenadaDTO.class);
		criteria.add(Restrictions.eq("ruta.idRuta", idRuta));
		criteria.addOrder(Order.asc("fechaCreacion"));

		return criteria.list();
	}

	@Override
	public List<CoordenadaDTO> getCoordenadasByEmpleado(Long idEmpleado) {
		
		Criteria criteria = getCurrentSession().createCriteria(CoordenadaDTO.class);
		criteria.createAlias("ruta", "ruta");
		criteria.add(Restrictions.eq("ruta.empleado.empId", idEmpleado));
		criteria.addOrder(Order.asc("fechaCreacion"));

		return criteria.list();
	}

	@Override
	public List<CoordenadaDTO> getCoordenadasByDate(Long idEmpleado, String fechaInicio, String fechaFin) throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		Date fi = formatter.parse(fechaInicio);
		Date ff = formatter.parse(fechaFin);
 
		 StringBuilder hql = new StringBuilder("select coor from CoordenadaDTO coor");
		 hql.append(" where coor.creadoPor = :creadoPor");
		 hql.append(" and TO_CHAR(coor.fechaCoordenada, 'dd/MM/yyyy') between :fechaInicio AND :fechaFin ");
		 hql.append(" order by coor.fechaCreacion asc");
		
		 Query query = getCurrentSession().createQuery(hql.toString());
		 query.setParameter("creadoPor", idEmpleado);
		 query.setParameter("fechaInicio", fechaInicio);
		 query.setParameter("fechaFin",fechaFin);

		 
		return query.list();

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CoordenadaDTO> getCoordenadasByDate(String empPlaca, Date fechaInicial, Date fechaFin){
		Criteria c = getCurrentSession().createCriteria(CoordenadaDTO.class);
		
		c.add(Restrictions.le("fechaCoordenada", fechaFin));
		c.add(Restrictions.ge("fechaCoordenada", fechaInicial));
		c.addOrder(Order.asc("fechaCoordenada"));
		
		c.createAlias("empleado", "empleado");
		c.add(Restrictions.eq("empleado.empPlaca",empPlaca));
		return c.list();
	}
	
	@Override
	public List<CoordenadaDTO> getLastCoordenadaByRange(String placa, int days, Date fI, Date fF){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(fI);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(fF);
		List<CoordenadaDTO> coords;
		boolean recordFoud = false;
		do{
			c1.add(Calendar.DAY_OF_MONTH, -1);
			c2.add(Calendar.DAY_OF_MONTH, -1);
			fI = c1.getTime();
			fF = c2.getTime();
			coords = getCoordenadasByDate(placa, fI, fF);
			recordFoud =!Collections.isEmpty(coords);
			days-=1;
		}while(!recordFoud && days >0);
		return coords;
	}
}
