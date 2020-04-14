package mx.com.teclo.sms.ws.persistencia.hibernate.dao.infracciones;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.infracciones.TipoInfraccionDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class TipoInfraccionDAOImpl extends BaseDaoHibernate<TipoInfraccionDTO> implements TipoInfraccionDAO {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoInfraccionDTO> allActivosByNombreDispositivo(String dispositivo){
		return getCurrentSession().createCriteria(TipoInfraccionDTO.class).add(Restrictions.eq("estatus",1)).add(Restrictions.eq("nombreDispositivo",dispositivo)).list();
	}
}