package mx.com.teclo.sms.ws.persistencia.hibernate.dao.coordenadas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.geolocalizacion.CoordenadaDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class CoordenadaHandHeldDAOImpl extends BaseDaoHibernate<CoordenadaDTO> implements CoordenadaHandHeldDAO {
	private static final Logger logger = LoggerFactory.getLogger(CoordenadaHandHeldDAOImpl.class);

	 
}
