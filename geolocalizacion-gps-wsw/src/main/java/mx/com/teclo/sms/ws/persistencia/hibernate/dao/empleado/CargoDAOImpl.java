package mx.com.teclo.sms.ws.persistencia.hibernate.dao.empleado;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.CargoDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class CargoDAOImpl extends BaseDaoHibernate<CargoDTO> implements CargoDAO{

	/**
	 * @author Kevin Ojeda
	 */
	@Override
	public CargoDTO activoByCode(String code){
		return (CargoDTO) getCurrentSession().createCriteria(CargoDTO.class).add(Restrictions.eq("cargoCodigo", code)).uniqueResult();
	}
}
