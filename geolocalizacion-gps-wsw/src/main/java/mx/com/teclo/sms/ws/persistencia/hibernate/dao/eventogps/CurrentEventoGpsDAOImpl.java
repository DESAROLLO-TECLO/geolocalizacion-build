package mx.com.teclo.sms.ws.persistencia.hibernate.dao.eventogps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.hh.eventogps.CurrentEventoGpsDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class CurrentEventoGpsDAOImpl extends BaseDaoHibernate<CurrentEventoGpsDTO> implements CurrentEventoGpsDAO {
	private static final Logger logger = LoggerFactory.getLogger(CurrentEventoGpsDAOImpl.class);
	
	
}
