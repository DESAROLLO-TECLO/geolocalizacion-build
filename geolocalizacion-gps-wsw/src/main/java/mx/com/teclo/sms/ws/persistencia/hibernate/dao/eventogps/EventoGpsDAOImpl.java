package mx.com.teclo.sms.ws.persistencia.hibernate.dao.eventogps;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.hh.eventogps.EventoGpsDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class EventoGpsDAOImpl extends BaseDaoHibernate<EventoGpsDTO> implements EventoGpsDAO {
	private static final Logger logger = LoggerFactory.getLogger(EventoGpsDAOImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EventoGpsDTO saveEvento(EventoGpsDTO eventoGpsDTO) {
		logger.info("Objeto Evento [{}]",eventoGpsDTO.toString());
		return saveOrUpdate(eventoGpsDTO);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<EventoGpsDTO> saveListaEvento(List<EventoGpsDTO> listaEventoGpsDTO) {
		List<EventoGpsDTO> responseListaEventoGpsDTO = new ArrayList<EventoGpsDTO>();
		for (EventoGpsDTO eventoGpsDTO : listaEventoGpsDTO) {
			responseListaEventoGpsDTO.add(saveOrUpdate(eventoGpsDTO));
		}
		return responseListaEventoGpsDTO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<EventoGpsDTO> byHHAndFecha(List<String> numerosSerie, Date fechaInicial, Date fechaFin){
		Criteria c = getCurrentSession().createCriteria(EventoGpsDTO.class);
		Disjunction or = Restrictions.disjunction();
		Conjunction and = Restrictions.conjunction();
		for(String ns : numerosSerie){
			or.add(Restrictions.eq("numSerieHH", ns));
		}
		and.add(Restrictions.ge("fechaHoraEvento", fechaInicial));
		and.add(Restrictions.le("fechaHoraEvento", fechaFin));
		and.add(Restrictions.not(Restrictions.eq("latitudGps",0D)));
		and.add(Restrictions.not(Restrictions.eq("longitudGps",0D)));
		and.add(or);
		c.add(and);
		c.addOrder(Order.asc("fechaHoraEvento"));
//		c.addOrder(Order.desc("fechaHoraEvento"));
//		c.setMaxResults(1);
		return c.list();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<EventoGpsDTO> byUniqueHHAndFecha(String numerosSerie, Date fechaInicial, Date fechaFin){
		Criteria c = getCurrentSession().createCriteria(EventoGpsDTO.class);
		c.add(Restrictions.eq("numSerieHH", numerosSerie));
		c.add(Restrictions.ge("fechaHoraEvento", fechaInicial));
		c.add(Restrictions.le("fechaHoraEvento", fechaFin));
		c.add(Restrictions.not(Restrictions.eq("latitudGps",0D)));
		c.add(Restrictions.not(Restrictions.eq("longitudGps",0D)));
		c.addOrder(Order.asc("fechaHoraEvento"));
		return c.list();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<EventoGpsDTO> byUniqueEmpIdAndFecha(Long id, Date fechaInicial, Date fechaFin){
		Criteria c = getCurrentSession().createCriteria(EventoGpsDTO.class);
		Disjunction or = Restrictions.disjunction();
		c.add(Restrictions.eq("idEmpleado", id));
		c.add(Restrictions.ge("fechaHoraEvento", fechaInicial));
		c.add(Restrictions.le("fechaHoraEvento", fechaFin));
		c.add(Restrictions.not(Restrictions.eq("latitudGps",0D)));
		c.add(Restrictions.not(Restrictions.eq("longitudGps",0D)));
		c.addOrder(Order.asc("fechaHoraEvento"));
		c.add(or);
		return c.list();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<EventoGpsDTO> byHHIdsAndFecha(List<Long> dispositivosId, Date fechaInicial, Date fechaFin){
		Criteria c = getCurrentSession().createCriteria(EventoGpsDTO.class);
		Disjunction or = Restrictions.disjunction();
		c.createAlias("dispositivoMovil", "dispositivoMovil");
		for(Long id : dispositivosId){
			or.add(Restrictions.eq("dispositivoMovil.idDispositivo", id));
		}
		c.add(Restrictions.ge("fechaHoraEvento", fechaInicial));
		c.add(Restrictions.le("fechaHoraEvento", fechaFin));
//		c.add(Restrictions.not(Restrictions.eq("latitudGps",0D)));
//		c.add(Restrictions.not(Restrictions.eq("longitudGps",0D)));
		c.addOrder(Order.asc("numSerieHH")).addOrder(Order.asc("fechaHoraEvento"));
		c.add(or);
		return c.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<EventoGpsDTO> optimizedLastEventsByNumDiasHHSerieAndFecha(List<String> series, int days, Date fechaInicial, Date fechaFin){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(fechaInicial);
		c1.add(Calendar.DAY_OF_MONTH, -days);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(fechaFin);
		String principalQuery = "SELECT EVENTO_ID, EMP_ID, EVENTO_TIPO_ID, EVENTO_DISPOSITIVO_ID, EVENTO_ART_ID, EVENTO_NUM_SERIE_HH, EVENTO_GPS_LAT, EVENTO_GPS_LON, EVENTO_DIRECCION, EVENTO_INFRAC_NUM, "+
				"EVENTO_FECHA_HORA, EVENTO_ESTATUS, CREADO_POR, FECHA_CREACION, MODIFICADO_POR, ULTIMA_MODIFICACION "+
				"FROM (SELECT EVENTO_ID, EMP_ID, EVENTO_TIPO_ID, EVENTO_DISPOSITIVO_ID, EVENTO_ART_ID, EVENTO_NUM_SERIE_HH, EVENTO_GPS_LAT, EVENTO_GPS_LON, EVENTO_DIRECCION, EVENTO_INFRAC_NUM, "+
				"EVENTO_FECHA_HORA, MAX(EVENTO_FECHA_HORA) over (PARTITION BY EVENTO_NUM_SERIE_HH) AS MaxDATEX, EVENTO_ESTATUS, CREADO_POR, FECHA_CREACION, MODIFICADO_POR, ULTIMA_MODIFICACION "+
				"FROM GPS_EVENTOS WHERE EVENTO_GPS_LAT <> 0 and EVENTO_GPS_LON <> 0) "+
				"WHERE EVENTO_FECHA_HORA = MaxDATEX AND EVENTO_FECHA_HORA BETWEEN to_date('"+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(c1.getTime()) +"', 'dd/MM/yyyy hh24:MI:ss')  and to_date('"+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(c2.getTime()) +"', 'dd/MM/yyyy hh24:MI:ss') ";
		
//		String principalQuery = "SELECT "
//			+ "MAX(EVENTO_ID) KEEP (DENSE_RANK LAST ORDER BY EVENTO_FECHA_HORA) AS EVENTO_ID,"
//			+ "MAX(EMP_ID) KEEP (DENSE_RANK LAST ORDER BY EVENTO_FECHA_HORA) AS EMP_ID,"
//			+ "MAX(EVENTO_TIPO_ID) KEEP (DENSE_RANK LAST ORDER BY EVENTO_FECHA_HORA) AS EVENTO_TIPO_ID,"
//			+ "MAX(EVENTO_DISPOSITIVO_ID) KEEP (DENSE_RANK LAST ORDER BY EVENTO_FECHA_HORA) AS EVENTO_DISPOSITIVO_ID,"
//			+ "MAX(EVENTO_ART_ID) KEEP (DENSE_RANK LAST ORDER BY EVENTO_FECHA_HORA) AS EVENTO_ART_ID,"
//			+ "MAX(EVENTO_NUM_SERIE_HH)	KEEP (DENSE_RANK LAST ORDER BY EVENTO_FECHA_HORA) AS EVENTO_NUM_SERIE_HH,"
//			+ "MAX(EVENTO_GPS_LAT) KEEP (DENSE_RANK LAST ORDER BY EVENTO_FECHA_HORA) AS EVENTO_GPS_LAT,"
//			+ "MAX(EVENTO_GPS_LON) KEEP (DENSE_RANK LAST ORDER BY EVENTO_FECHA_HORA) AS EVENTO_GPS_LON,"
//			+ "MAX(EVENTO_DIRECCION) KEEP (DENSE_RANK LAST ORDER BY EVENTO_FECHA_HORA) AS EVENTO_DIRECCION,"
//			+ "MAX(EVENTO_INFRAC_NUM) KEEP (DENSE_RANK LAST ORDER BY EVENTO_FECHA_HORA) AS EVENTO_INFRAC_NUM,"
//			+ "MAX(EVENTO_FECHA_HORA) KEEP (DENSE_RANK LAST ORDER BY EVENTO_FECHA_HORA) AS EVENTO_FECHA_HORA,"
//			+ "MAX(EVENTO_ESTATUS) KEEP (DENSE_RANK LAST ORDER BY EVENTO_FECHA_HORA) AS EVENTO_ESTATUS,"
//			+ "MAX(CREADO_POR) KEEP (DENSE_RANK LAST ORDER BY EVENTO_FECHA_HORA) AS CREADO_POR,"
//			+ "MAX(FECHA_CREACION) KEEP (DENSE_RANK LAST ORDER BY EVENTO_FECHA_HORA) AS FECHA_CREACION,"
//			+ "MAX(MODIFICADO_POR) KEEP (DENSE_RANK LAST ORDER BY EVENTO_FECHA_HORA) AS MODIFICADO_POR,"
//			+ "MAX(ULTIMA_MODIFICACION)	KEEP (DENSE_RANK LAST ORDER BY EVENTO_FECHA_HORA) AS ULTIMA_MODIFICACION"
//			+ "from GPS_EVENTOS where EVENTO_FECHA_HORA between '"+ new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(c1.getTime()) +"' and '"+ new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(c2.getTime()) +"' and EVENTO_GPS_LAT <> 0 and EVENTO_GPS_LON <> 0 ";
		if(series.size()>0){
			principalQuery += " AND EVENTO_NUM_SERIE_HH IN(";
			for(Integer i = 0; i<series.size(); i++){
				principalQuery += "'"+series.get(i)+"'";
				principalQuery += i+1 == series.size() ? ") " : ", ";
			}
		}
//		principalQuery += "GROUP BY EVENTO_DISPOSITIVO_ID";
		principalQuery += "ORDER BY EVENTO_NUM_SERIE_HH DESC";
		System.out.println(principalQuery);
		return getCurrentSession().createSQLQuery(principalQuery).addEntity(EventoGpsDTO.class).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EventoGpsDTO> optimizedLastEventsByNumDiasPlacasAndFecha(List<String> placas, int days, Date fechaInicial, Date fechaFin) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(fechaInicial);
		c1.add(Calendar.DAY_OF_MONTH, -days);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(fechaFin);
		
		String principalQuery = "SELECT EVENTO_ID, s.EMP_ID, EVENTO_TIPO_ID, EVENTO_DISPOSITIVO_ID, EVENTO_ART_ID, EVENTO_NUM_SERIE_HH, EVENTO_GPS_LAT, EVENTO_GPS_LON, EVENTO_DIRECCION, EVENTO_INFRAC_NUM, "+
				"EVENTO_FECHA_HORA, EVENTO_ESTATUS, s.CREADO_POR, s.FECHA_CREACION, s.MODIFICADO_POR, s.ULTIMA_MODIFICACION "+
				"FROM (SELECT EVENTO_ID, EMP_ID, EVENTO_TIPO_ID, EVENTO_DISPOSITIVO_ID, EVENTO_ART_ID, EVENTO_NUM_SERIE_HH, EVENTO_GPS_LAT, EVENTO_GPS_LON, EVENTO_DIRECCION, EVENTO_INFRAC_NUM, "+
				"EVENTO_FECHA_HORA, MAX(EVENTO_FECHA_HORA) over (PARTITION BY EMP_ID) AS MaxDATEX, EVENTO_ESTATUS, CREADO_POR, FECHA_CREACION, MODIFICADO_POR, ULTIMA_MODIFICACION "+
				"FROM GPS_EVENTOS WHERE EVENTO_GPS_LAT <> 0 and EVENTO_GPS_LON <> 0) s JOIN EMPLEADOS e ON s.EMP_ID = e.EMP_ID "+
				"WHERE EVENTO_FECHA_HORA = MaxDATEX AND EVENTO_FECHA_HORA BETWEEN to_date('"+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(c1.getTime()) +"', 'dd/MM/yyyy hh24:MI:ss')  and to_date('"+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(c2.getTime()) +"', 'dd/MM/yyyy hh24:MI:ss') ";
		
		if(placas.size() > 0) {
			principalQuery += " AND e.EMP_PLACA IN(";
			for(Integer i = 0; i < placas.size(); i++){
				principalQuery += "'"+ placas.get(i)+"'";
				principalQuery += i+1 == placas.size() ? ") " : ", ";
			}
		}
		principalQuery += "ORDER BY EVENTO_NUM_SERIE_HH DESC";
		System.out.println(principalQuery);
		return getCurrentSession().createSQLQuery(principalQuery).addEntity(EventoGpsDTO.class).list();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<List<EventoGpsDTO>> lastEventsByNumDiasHHSerieAndFecha(List<String> series, int days, Date fechaInicial, Date fechaFin){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(fechaInicial);
		c1.add(Calendar.DAY_OF_MONTH, -days);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(fechaFin);
		new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(c1.getTime());
		
		List<EventoGpsDTO> lastReportedDay = new ArrayList<EventoGpsDTO>();
		for(String serie : series){
			Criteria c = getCurrentSession().createCriteria(EventoGpsDTO.class);
			c.add(Restrictions.eq("numSerieHH", serie));
			c.add(Restrictions.ge("fechaHoraEvento", c1.getTime()));
			c.add(Restrictions.le("fechaHoraEvento", c2.getTime()));
			c.add(Restrictions.not(Restrictions.eq("latitudGps",0D)));
			c.add(Restrictions.not(Restrictions.eq("longitudGps",0D)));
			c.setMaxResults(1);
			c.addOrder(Order.desc("fechaHoraEvento"));
			EventoGpsDTO evento = (EventoGpsDTO) c.uniqueResult();
			if(evento != null)
				lastReportedDay.add((EventoGpsDTO) c.uniqueResult());
		}
		
		List<List<EventoGpsDTO>> allEvents = new ArrayList<>();
		
		for(EventoGpsDTO ultimoEvento : lastReportedDay){
			List<EventoGpsDTO> lastEvents = new ArrayList<EventoGpsDTO>();
			
			c1 = Calendar.getInstance();
			c1.setTime(ultimoEvento.getFechaHoraEvento());
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			
			c2 = Calendar.getInstance();
			c2.setTime(ultimoEvento.getFechaHoraEvento());
			c2.set(Calendar.HOUR_OF_DAY, 23);
			c2.set(Calendar.MINUTE, 59);
			
			Criteria c = getCurrentSession().createCriteria(EventoGpsDTO.class);
			c.add(Restrictions.eq("numSerieHH", ultimoEvento.getDispositivoMovil().getNumSerie()));
			c.add(Restrictions.ge("fechaHoraEvento", c1.getTime()));
			c.add(Restrictions.le("fechaHoraEvento", c2.getTime()));
			c.addOrder(Order.asc("fechaHoraEvento"));
			c.add(Restrictions.not(Restrictions.eq("latitudGps",0D)));
			c.add(Restrictions.not(Restrictions.eq("longitudGps",0D)));
			lastEvents.addAll(c.list());
			allEvents.add(lastEvents);
		}
		return allEvents;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<EventoGpsDTO> byEmpIdsAndFecha(List<Long> ids, Date fechaInicial, Date fechaFin){
		Criteria c = getCurrentSession().createCriteria(EventoGpsDTO.class);
		Disjunction or = Restrictions.disjunction();
		for(Long id : ids){
			or.add(Restrictions.eq("idEmpleado", id));
		}
		c.add(Restrictions.ge("fechaHoraEvento", fechaInicial));
		c.add(Restrictions.le("fechaHoraEvento", fechaFin));
		c.add(Restrictions.not(Restrictions.eq("latitudGps",0D)));
		c.add(Restrictions.not(Restrictions.eq("longitudGps",0D)));
		c.addOrder(Order.asc("fechaHoraEvento"));
//		c.addOrder(Order.desc("fechaHoraEvento"));
//		c.setMaxResults(1);
		c.add(or);
		return c.list();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<List<EventoGpsDTO>> lastEventsByEmpIdsAndFecha(List<Long> ids, int days, Date fechaInicial, Date fechaFin){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(fechaInicial);
		c1.add(Calendar.DAY_OF_MONTH, -days);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(fechaFin);
		
		
		List<EventoGpsDTO> lastReportedDay = new ArrayList<EventoGpsDTO>();
		for(Long id : ids){
			Criteria c = getCurrentSession().createCriteria(EventoGpsDTO.class);
			c.add(Restrictions.eq("idEmpleado", id));
			c.add(Restrictions.ge("fechaHoraEvento", c1.getTime()));
			c.add(Restrictions.le("fechaHoraEvento", c2.getTime()));
			c.add(Restrictions.not(Restrictions.eq("latitudGps",0D)));
			c.add(Restrictions.not(Restrictions.eq("longitudGps",0D)));
			c.setMaxResults(1);
			c.addOrder(Order.desc("fechaHoraEvento"));
			EventoGpsDTO evento = (EventoGpsDTO) c.uniqueResult();
			if(evento != null)
				lastReportedDay.add((EventoGpsDTO) c.uniqueResult());
		}
		
		List<List<EventoGpsDTO>> allEvents = new ArrayList<>();
		
		for(EventoGpsDTO ultimoEvento : lastReportedDay){
			List<EventoGpsDTO> lastEvents = new ArrayList<EventoGpsDTO>();
			
			c1 = Calendar.getInstance();
			c1.setTime(ultimoEvento.getFechaHoraEvento());
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			
			c2 = Calendar.getInstance();
			c2.setTime(ultimoEvento.getFechaHoraEvento());
			c2.set(Calendar.HOUR_OF_DAY, 23);
			c2.set(Calendar.MINUTE, 59);
			
			Criteria c = getCurrentSession().createCriteria(EventoGpsDTO.class);
			c.add(Restrictions.eq("idEmpleado", ultimoEvento.getIdEmpleado()));
			c.add(Restrictions.ge("fechaHoraEvento", c1.getTime()));
			c.add(Restrictions.le("fechaHoraEvento", c2.getTime()));
			c.add(Restrictions.not(Restrictions.eq("latitudGps",0D)));
			c.add(Restrictions.not(Restrictions.eq("longitudGps",0D)));
			c.addOrder(Order.asc("fechaHoraEvento"));
			lastEvents.addAll(c.list());
			allEvents.add(lastEvents);
		}
		return allEvents;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<EventoGpsDTO> reporteGeneral
	(Integer loginRestriction, List<Long> zonasVialesId, Date fI, Date fF, List<Long> idsTipoEvento, List<String> codigosTipoInfraccion){
		List<EventoGpsDTO> response = new ArrayList<EventoGpsDTO>();
		for(int i= 0; i < loginRestriction; i++){
			Criteria c = getCurrentSession().createCriteria(EventoGpsDTO.class);
			c.createAlias("dispositivoMovil", "dispositivoMovil");
			c.add(Restrictions.eq("dispositivoMovil.activo", 1));
			c.createAlias("dispositivoMovil.zonaVialDispositivos", "zonas");
			c.add(Restrictions.eq("zonas.activo", 1));
			c.createAlias("zonas.zonaVial", "zona");
			
			if(i== 0){
				c.createAlias("empleado", "empleado");
				c.createAlias("empleado.zonaVialEmpleados", "zonasEmp");
				c.add(Restrictions.isNotNull("idEmpleado")).add(Restrictions.eq("empleado.empStatus", "A")).add(Restrictions.eq("zonasEmp.activo", 1));

			}
//			else{
//				Disjunction empOr = Restrictions.disjunction();
//				empOr.add(Restrictions.isNull("idEmpleado"));
//				c.add(empOr);
//			}
			
			if(zonasVialesId!=null) c.add(Restrictions.in("zona.idZonaVial", zonasVialesId));
			
			Calendar c1 = Calendar.getInstance();
			c1.setTime(fI);
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			
			Calendar c2 = Calendar.getInstance();
			c2.setTime(fF);
			c2.set(Calendar.HOUR_OF_DAY, 23);
			c2.set(Calendar.MINUTE, 59);
			
			c.add(Restrictions.ge("fechaHoraEvento", c1.getTime()));
			c.add(Restrictions.le("fechaHoraEvento", c2.getTime()));
			
			c.createCriteria("tipoEvento", "tipoEvento");
			if(idsTipoEvento!=null) c.add(Restrictions.in("tipoEvento.idTipoEvento", idsTipoEvento));
			Disjunction or = Restrictions.disjunction();
			if(codigosTipoInfraccion!=null){
				or.add(Restrictions.eq("numInfraccion", "0"));
				for(String codigo : codigosTipoInfraccion){
					or.add(Restrictions.sqlRestriction("SUBSTR(EVENTO_INFRAC_NUM,1,2) = ?", codigo,  StringType.INSTANCE));
				}
			}
			response.addAll(c.add(or).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list());
		}
		return response;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<EventoGpsDTO> reporteGeneralOficialesDispositivos
	(Integer loginRestriction, List<Long> oficiales, List<Long> dispositivos, Date fI, Date fF, List<Long> idsTipoEvento, List<String> codigosTipoInfraccion){
		List<EventoGpsDTO> response = new ArrayList<EventoGpsDTO>();
		for(int i= 0; i < loginRestriction; i++){
			Criteria c = getCurrentSession().createCriteria(EventoGpsDTO.class);
			Conjunction primaryAnd = Restrictions.conjunction();
			
			Calendar c1 = Calendar.getInstance();
			c1.setTime(fI);
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			
			Calendar c2 = Calendar.getInstance();
			c2.setTime(fF);
			c2.set(Calendar.HOUR_OF_DAY, 23);
			c2.set(Calendar.MINUTE, 59);
			
			primaryAnd.add(Restrictions.ge("fechaHoraEvento", c1.getTime()));
			primaryAnd.add(Restrictions.le("fechaHoraEvento", c2.getTime()));
			if(idsTipoEvento!=null) primaryAnd.add(Restrictions.in("tipoEvento.idTipoEvento", idsTipoEvento));
			
			c.createAlias("dispositivoMovil", "dispositivoMovil");
			primaryAnd.add(Restrictions.eq("dispositivoMovil.activo", 1));
			c.createAlias("dispositivoMovil.zonaVialDispositivos", "zonas");
			primaryAnd.add(Restrictions.eq("zonas.activo", 1));
			Disjunction or = Restrictions.disjunction();
			if(dispositivos!=null){
				or.add(Restrictions.in("dispositivoMovil.idDispositivo", dispositivos));
			}
			
			if(i== 0){
				c.createAlias("empleado", "empleado");
				c.createAlias("empleado.zonaVialEmpleados", "zonasEmp");
				primaryAnd.add(Restrictions.eq("empleado.empStatus", "A"))
				.add(Restrictions.eq("zonasEmp.activo", 1));
				if(oficiales!=null){
					or.add(Restrictions.in("empleado.empId", oficiales));
				}
				
			}
//			else{
//				Disjunction empOr = Restrictions.disjunction();
//				empOr.add(Restrictions.isNull("idEmpleado"));
//				primaryAnd.add(empOr);
//			}
			primaryAnd.add(or);
			
			Disjunction or1 = Restrictions.disjunction();
			if(codigosTipoInfraccion!=null){
				or1.add(Restrictions.eq("numInfraccion", "0"));
				for(String codigo : codigosTipoInfraccion){
					or1.add(Restrictions.sqlRestriction("SUBSTR(EVENTO_INFRAC_NUM,1,2) = ?", codigo,  StringType.INSTANCE));
				}
				primaryAnd.add(or1);
			}
			response.addAll(c.add(primaryAnd).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list());
		}
		return response;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<EventoGpsDTO> reporteGeneralOficialesConDispositivos
	(Integer loginRestriction, List<Long> oficiales, List<Long> dispositivos, Date fI, Date fF, List<Long> idsTipoEvento, List<String> codigosTipoInfraccion){
		List<EventoGpsDTO> response = new ArrayList<EventoGpsDTO>();
		for(int i= 0; i < loginRestriction; i++){
			Criteria c = getCurrentSession().createCriteria(EventoGpsDTO.class);
			
			Conjunction primaryAnd = Restrictions.conjunction();
			
			Calendar c1 = Calendar.getInstance();
			c1.setTime(fI);
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			
			Calendar c2 = Calendar.getInstance();
			c2.setTime(fF);
			c2.set(Calendar.HOUR_OF_DAY, 23);
			c2.set(Calendar.MINUTE, 59);
			
			primaryAnd.add(Restrictions.ge("fechaHoraEvento", c1.getTime()));
			primaryAnd.add(Restrictions.le("fechaHoraEvento", c2.getTime()));
			if(idsTipoEvento!=null) primaryAnd.add(Restrictions.in("tipoEvento.idTipoEvento", idsTipoEvento));
			
			c.createAlias("dispositivoMovil", "dispositivoMovil");
			primaryAnd.add(Restrictions.eq("dispositivoMovil.activo", 1));
			c.createAlias("dispositivoMovil.zonaVialDispositivos", "zonas");
			primaryAnd.add(Restrictions.eq("zonas.activo", 1));
			
			Disjunction or = Restrictions.disjunction();
			

			if(i== 0){//Exige login
				c.createAlias("empleado", "empleado");
				c.createAlias("empleado.zonaVialEmpleados", "zonasEmp");
				primaryAnd.add(Restrictions.eq("empleado.empStatus", "A"))
				.add(Restrictions.eq("zonasEmp.activo", 1));
				if(dispositivos != null && oficiales != null){//Exige union
					for(Long oficial : oficiales){
						for(Long dispositivo : dispositivos){
							Conjunction and = Restrictions.conjunction();
							and.add(Restrictions.eq("empleado.empId", oficial)).add(Restrictions.eq("dispositivoMovil.idDispositivo", dispositivo));
							or.add(and);
						}
					}
				}else{
					if(oficiales != null && dispositivos == null){
						or.add(Restrictions.in("empleado.empId", oficiales));
					}
					if(oficiales == null && dispositivos != null){
						or.add(Restrictions.in("dispositivoMovil.idDispositivo", dispositivos));
					}
				}
			}else{//Sin login
					if(dispositivos != null)
						or.add(Restrictions.in("dispositivoMovil.idDispositivo", dispositivos));
					//Disjunction empOr = Restrictions.disjunction();
					//empOr.add(Restrictions.isNull("idEmpleado"));
					//primaryAnd.add(empOr);
//				}
			}
			primaryAnd.add(or);
			
			Disjunction or1 = Restrictions.disjunction();
			if(codigosTipoInfraccion!=null){
				or1.add(Restrictions.eq("numInfraccion", "0"));
				for(String codigo : codigosTipoInfraccion){
					or1.add(Restrictions.sqlRestriction("SUBSTR(EVENTO_INFRAC_NUM,1,2) = ?", codigo,  StringType.INSTANCE));
				}
				primaryAnd.add(or1);
			}
			response.addAll(c.add(primaryAnd).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list());
		}
		return response;
	}	
}
