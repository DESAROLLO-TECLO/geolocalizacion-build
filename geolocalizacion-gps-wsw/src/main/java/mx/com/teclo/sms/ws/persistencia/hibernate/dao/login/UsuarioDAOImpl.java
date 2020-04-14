package mx.com.teclo.sms.ws.persistencia.hibernate.dao.login;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.Query;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.EmpleadosDTO;
import mx.com.teclomexicana.arquitectura.ortogonales.seguridad.vo.AuthenticationRequestVO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository("usuario2DaoImp")
public class UsuarioDAOImpl extends BaseDaoHibernate<EmpleadosDTO> implements UsuarioDAO {

	@Override
	public EmpleadosDTO loginByUserPassword(AuthenticationRequestVO authenticationRequestVO) {

		String hql = "select emp from EmpleadosDTO emp where emp.empPlaca =:placa and emp.empStatus = 'A'";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("placa", authenticationRequestVO.getUsername());
		final EmpleadosDTO empleadosDTO = (EmpleadosDTO) query.uniqueResult();
		if (empleadosDTO == null) {
			return null;
		}
		final String pwd = empleadosDTO.getEmpPwd();
		getCurrentSession().doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				CallableStatement call = connection.prepareCall("{ ? = call pkg_encripcion.desencripta(?) }");
				call.registerOutParameter(1, Types.LONGNVARCHAR); 
				call.setString(2, pwd);
				call.execute();
				String result = call.getString(1);  
				empleadosDTO.setEmpPwd(result);
			}
		});
		return empleadosDTO;

	}

}
