package mx.com.teclo.sms.ws.persistencia.hibernate.dao.dispositivosmoviles;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilModeloDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class DispositivoMovilModeloDAOImpl extends BaseDaoHibernate<DispositivoMovilModeloDTO> implements DispositivoMovilModeloDAO{

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<DispositivoMovilModeloDTO> allActivos(){
		Criteria c = getCurrentSession().createCriteria(DispositivoMovilModeloDTO.class);
		c.add(Restrictions.eq("activo", 1));
		return c.list();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<DispositivoMovilModeloDTO> byMarcaCode(String code){
		Criteria c = getCurrentSession().createCriteria(DispositivoMovilModeloDTO.class);
		c.createAlias("modeloMarca", "modeloMarca");
		c.add(Restrictions.eq("activo", 1));
		c.add(Restrictions.eq("modeloMarca.marcaCodigo", code));
		return c.list();
	}
}
