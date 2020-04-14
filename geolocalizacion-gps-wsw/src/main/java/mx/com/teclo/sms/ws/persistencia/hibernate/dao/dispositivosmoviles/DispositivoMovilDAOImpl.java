package mx.com.teclo.sms.ws.persistencia.hibernate.dao.dispositivosmoviles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilDTO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.RequestDispositivoMovilFullConsultaVO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class DispositivoMovilDAOImpl extends BaseDaoHibernate<DispositivoMovilDTO> implements DispositivoMovilDAO {

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DispositivoMovilDTO activoBySerie(String code){
		Criteria c = getCurrentSession().createCriteria(DispositivoMovilDTO.class);
		c.add(Restrictions.eq("numSerie", code));
		c.add(Restrictions.eq("activo", 1));
		return (DispositivoMovilDTO) c.uniqueResult();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DispositivoMovilDTO activoByDispoInfo(String serie, String sim, String ip){
		Criteria c = getCurrentSession().createCriteria(DispositivoMovilDTO.class);
		if(serie != null && !serie.equals(""))
			c.add(Restrictions.eq("numSerie", serie));
		if(sim != null && !sim.equals(""))
			c.add(Restrictions.eq("numSim", sim));
		if(ip != null && !ip.equals(""))
			c.add(Restrictions.eq("numIp", ip));
		c.add(Restrictions.eq("activo", 1));
		return (DispositivoMovilDTO) c.uniqueResult();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> flagDispositivo(String ip, String numSerie, String numSim){
		Map<String,String> response = new HashMap<String,String>();
		String found = "registro encontrado con  ";
		
		Criteria c = getCurrentSession().createCriteria(DispositivoMovilDTO.class);
		c.add(Restrictions.eq("numSerie", numSerie));
		if(c.uniqueResult()!= null)
			response.put("numSerie", found + " este número de serie " + numSerie); 
		
		c = getCurrentSession().createCriteria(DispositivoMovilDTO.class);
		c.add(Restrictions.eq("numSim", numSim));
		if(c.uniqueResult()!= null)
			response.put("numSim", found + " este número SIM " + numSim); 
		
		c = getCurrentSession().createCriteria(DispositivoMovilDTO.class);
		c.add(Restrictions.eq("numIp", ip));
		if(c.uniqueResult()!= null)
			response.put("ip", found + " esta IP " + ip); 
		
		return response;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> flagDispositivoForUpdate(Long dispId, String ip, String numSerie, String numSim){
		Map<String,String> response = new HashMap<String,String>();
		String found = "registro encontrado con  ";
		
		Criteria c = getCurrentSession().createCriteria(DispositivoMovilDTO.class);
		c.add(Restrictions.not(Restrictions.eq("idDispositivo", dispId)));
		c.add(Restrictions.eq("numSerie", numSerie));
		if(c.uniqueResult()!= null)
			response.put("numSerie", found + " este número de serie " + numSerie); 
		
		c = getCurrentSession().createCriteria(DispositivoMovilDTO.class);
		c.add(Restrictions.not(Restrictions.eq("idDispositivo", dispId)));
		c.add(Restrictions.eq("numSim", numSim));
		if(c.uniqueResult()!= null)
			response.put("numSim", found + " este número SIM " + numSim); 
		
		c = getCurrentSession().createCriteria(DispositivoMovilDTO.class);
		c.add(Restrictions.not(Restrictions.eq("idDispositivo", dispId)));
		c.add(Restrictions.eq("numIp", ip));
		if(c.uniqueResult()!= null)
			response.put("ip", found + " esta IP " + ip); 
		
		return response;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<DispositivoMovilDTO> dispositivosSinAsignacion(){
		return getCurrentSession().createSQLQuery(
			"SELECT DM1.* FROM TMS001C_DISPOSITIVOS_MOVILES DM1 "+
			"LEFT JOIN TMS003D_DISPOSITIVO_ZONA_VIAL DZM1 ON DM1.ID_DISPOSITIVO = DZM1.ID_DISPOSITIVO "+
			"WHERE DZM1.ID_ZONA_VIAL IS NULL AND DM1.ID_DISPOSITIVO <> 0 AND  DZM1.ID_DISPOSITIVO IS NULL "+
			"UNION "+
			"SELECT  DISTINCT DM.* FROM TMS001C_DISPOSITIVOS_MOVILES DM "+
			"INNER JOIN  TMS003D_DISPOSITIVO_ZONA_VIAL A ON DM.ID_DISPOSITIVO = A.ID_DISPOSITIVO WHERE A.ST_ACTIVO = 0 AND A.ID_DISPOSITIVO NOT IN "+
			"(SELECT B.ID_DISPOSITIVO FROM TMS003D_DISPOSITIVO_ZONA_VIAL B WHERE B.ST_ACTIVO = 1 AND B.ID_DISPOSITIVO = A.ID_DISPOSITIVO)").addEntity(DispositivoMovilDTO.class).list();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DispositivoMovilDTO> fullSearch(RequestDispositivoMovilFullConsultaVO vo){
		Boolean firstCondition = false;
		String principalQuery= "";
		
		if(!vo.getNumSerie().equals("") || !vo.getNumSim().equals("") || !vo.getNumIp().equals("") || vo.getComplementacion()
		|| (vo.getModelos() != null && vo.getModelos().size()>0) || (vo.getTipoDispositivo() != null && vo.getTipoDispositivo().size()>0) || (vo.getZonaVial() != null && vo.getZonaVial().size()>0)){
			principalQuery = "SELECT DISTINCT DM.* FROM TMS001C_DISPOSITIVOS_MOVILES DM "
					+ "LEFT JOIN TMS003D_DISPOSITIVO_ZONA_VIAL DZM ON DM.ID_DISPOSITIVO = DZM.ID_DISPOSITIVO "
					+ "INNER JOIN TMS012C_DISPOSITIVO_MODELO MOD ON DM.ID_DISPOSITIVO_MODELO = MOD.ID_DISPOSITIVO_MODELO "
					+ "INNER JOIN TMS011C_DISPOSITIVO_MARCA MARCA ON MOD.ID_DISPOSITIVO_MARCA = MARCA.ID_DISPOSITIVO_MARCA "
					+ "WHERE DM.ST_ACTIVO = 1 AND MOD.ST_ACTIVO = 1 AND MARCA.ST_ACTIVO = 1 AND DM.ID_DISPOSITIVO <> 0";// AND DZM.ST_ACTIVO = 1 
			principalQuery += "AND (";
			if(vo.getComplementacion()){
				principalQuery += "DM.NUM_IP = '' OR DM.NUM_IP IS NULL OR ";
				principalQuery += "DM.NUM_SERIE = '' OR DM.NUM_SERIE IS NULL OR ";
				principalQuery += "DM.NUM_SIM = '' OR DM.NUM_SIM IS NULL ";
				firstCondition = true;
			}
			if(vo.getNumSerie() != null && !vo.getNumSerie().equals("")){
				principalQuery = preOperator(firstCondition, principalQuery);
				principalQuery += "DM.NUM_SERIE = '"+ vo.getNumSerie() +"' ";
				firstCondition = true;
			}
			if(vo.getNumSim() != null && !vo.getNumSim().equals("")){
				principalQuery = preOperator(firstCondition, principalQuery);
				principalQuery += "DM.NUM_SIM = '"+ vo.getNumSim() +"' ";
				firstCondition = true;
			}
			if(vo.getNumIp() != null && !vo.getNumIp().equals("")){
				principalQuery = preOperator(firstCondition, principalQuery);
				principalQuery += "DM.NUM_IP = '"+ vo.getNumIp() +"' ";
				firstCondition = true;
			}
			if(vo.getModelos() != null && vo.getModelos().size()>0){
				principalQuery = preOperator(firstCondition, principalQuery) + "MOD.ID_DISPOSITIVO_MODELO IN(";
				for(Integer i = 0; i< vo.getModelos().size(); i++){
					if(i!=0) principalQuery += ", ";
						principalQuery += ""+vo.getModelos().get(i)+"";
				}
				principalQuery += ") ";
				firstCondition = true;
			}
			if(vo.getTipoDispositivo() != null && vo.getTipoDispositivo().size()>0){
				principalQuery = preOperator(firstCondition, principalQuery) + "DM.ID_TIPO_DISPOSITIVO IN(";
				for(Integer i = 0; i< vo.getTipoDispositivo().size(); i++){
					if(i!=0) principalQuery += ", ";
						principalQuery += ""+vo.getTipoDispositivo().get(i)+"";
				}
				principalQuery += ") ";
				firstCondition = true;
			}
			if(vo.getZonaVial() != null && vo.getZonaVial().size()>0){
				principalQuery = preOperator(firstCondition, principalQuery) + "(DZM.ID_ZONA_VIAL IN(";
				for(Integer i = 0; i< vo.getZonaVial().size(); i++){
					if(i!=0) principalQuery += ", ";
						principalQuery += ""+vo.getZonaVial().get(i)+"";
				}
				principalQuery += ") AND DZM.ST_ACTIVO = 1)";
				firstCondition = true;
			}
			principalQuery += ") ";
		}
		if(vo.getSinZonaVial()){
			if(firstCondition)
				principalQuery += "UNION ";
			principalQuery +=  "SELECT DISTINCT DM1.* FROM TMS001C_DISPOSITIVOS_MOVILES DM1 "
					+ "LEFT JOIN TMS003D_DISPOSITIVO_ZONA_VIAL DZM1 ON DM1.ID_DISPOSITIVO = DZM1.ID_DISPOSITIVO "
					+ "WHERE DZM1.ID_ZONA_VIAL IS NULL AND DM1.ID_DISPOSITIVO <> 0 AND  DZM1.ID_DISPOSITIVO IS NULL "
					+ "UNION "
					+ "SELECT DISTINCT DM2.* FROM TMS001C_DISPOSITIVOS_MOVILES DM2 "
					+ "INNER JOIN TMS003D_DISPOSITIVO_ZONA_VIAL A ON DM2.ID_DISPOSITIVO = A.ID_DISPOSITIVO WHERE A.ST_ACTIVO = 0 AND A.ID_DISPOSITIVO NOT IN "
					+ "(SELECT B.ID_DISPOSITIVO FROM TMS003D_DISPOSITIVO_ZONA_VIAL B WHERE B.ST_ACTIVO = 1 AND B.ID_DISPOSITIVO = A.ID_DISPOSITIVO)";
		}
		System.out.println(principalQuery);
		return getCurrentSession().createSQLQuery(principalQuery).addEntity(DispositivoMovilDTO.class).list();

	}
	
	private String preOperator(Boolean firstCondition, String query){
		if(firstCondition)
			query = query + "OR ";
		return query;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<DispositivoMovilDTO> dispositivosPorZonasViales(List<Long> ids){
		return getCurrentSession().createCriteria(DispositivoMovilDTO.class)
				.createAlias("zonaVialDispositivos", "zonas")
				.createAlias("zonas.zonaVial", "zona")
				.add(Restrictions.in("zona.idZonaVial", ids))
				.add(Restrictions.eq("activo", 1))
				.add(Restrictions.eq("zonas.activo", 1))
				.add(Restrictions.eq("zona.activo", 1)).list();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<DispositivoMovilDTO> dispositivosPorZonaVial(Long id){
		return getCurrentSession().createCriteria(DispositivoMovilDTO.class)
				.createAlias("zonaVialDispositivos", "zonas")
				.createAlias("zonas.zonaVial", "zona")
				.add(Restrictions.eq("zona.idZonaVial", id))
				.add(Restrictions.eq("activo", 1))
				.add(Restrictions.eq("zonas.activo", 1))
				.add(Restrictions.eq("zona.activo", 1)).list();
	}
	
//	/**
//	 * {@inheritDoc}
//	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<DispositivoMovilDTO> fullSearch(RequestDispositivoMovilFullConsultaVO vo){
//		Criteria c = getCurrentSession().createCriteria(DispositivoMovilDTO.class);
//		Conjunction and = Restrictions.conjunction();
//		and.add(Restrictions.eq("activo", 1));
//		and.add(Restrictions.not(Restrictions.eq("idDispositivo", 0L)));
//		c.createAlias("zonaVialDispositivos", "zonas",  JoinType.LEFT_OUTER_JOIN);
//		c.createAlias("zonas.zonaVial", "zona",  JoinType.LEFT_OUTER_JOIN);
//		
//		c.createAlias("dispositivoMovil", "tipoDispositivo");
//		and.add(Restrictions.eq("tipoDispositivo.activo", 1));
//		
//		c.createAlias("dispositivoModelo", "modelos");
//		and.add(Restrictions.eq("modelos.activo", 1));
//		
//		c.createAlias("modelos.modeloMarca", "marcas");
//		and.add(Restrictions.eq("marcas.activo", 1));
//		
//		
//		Disjunction or = Restrictions.disjunction();
//		
//		
////		else{
////			and.add(Restrictions.eq("zonas.activo", 1));
////			and.add(Restrictions.eq("zona.activo", 1));
////		}
//		
//		
//		if(vo.getNumSerie() != null && !vo.getNumSerie().equals(""))
//			or.add(Restrictions.eq("numSerie", vo.getNumSerie()));
//		
//		if(vo.getNumSim() != null && !vo.getNumSim().equals(""))
//			or.add(Restrictions.eq("numSim", vo.getNumSim()));
//		
//		if(vo.getNumIp() != null && !vo.getNumIp().equals(""))
//			or.add(Restrictions.eq("numIp", vo.getNumIp()));
//		
//		if(vo.getModelos() != null && vo.getModelos().size()>0){
//			or.add(Restrictions.in("modelos.modeloId", vo.getModelos()));
//		}
//		
//		if(vo.getTipoDispositivo() != null && vo.getTipoDispositivo().size()>0){
//			or.add(Restrictions.in("tipoDispositivo.idTipoDispositivo", vo.getTipoDispositivo()));
//		}
//		
//		if((vo.getZonaVial() != null && vo.getZonaVial().size()>0 ) || vo.getSinZonaVial()){
//			if(vo.getZonaVial() != null && vo.getZonaVial().size()>0){
//				Conjunction zonasActivas = Restrictions.conjunction();
//				zonasActivas.add(Restrictions.in("zona.idZonaVial", vo.getZonaVial()));
//				zonasActivas.add(Restrictions.eq("zonas.activo", 1));
//				zonasActivas.add(Restrictions.eq("zona.activo", 1));
//				or.add(zonasActivas);
//			}
//			if(vo.getSinZonaVial()){
//				Disjunction noMatch = Restrictions.disjunction();
//				Conjunction match = Restrictions.conjunction();
//					match.add(Restrictions.eq("zonas.activo", 1));
//					match.add(Restrictions.eq("zona.activo", 1));
//				
//				Disjunction secondMatch = Restrictions.disjunction();
//					secondMatch.add(Restrictions.isEmpty("zonaVialDispositivos"));
//					secondMatch.add(Restrictions.eq("zonas.activo", 0));
//					secondMatch.add(Restrictions.eq("zona.activo", 0));
////					noMatch.add(match);
//				noMatch.add(secondMatch);
//				or.add(noMatch);
//			}
//		}
//		
//		if(vo.getComplementacion()){
//			Disjunction complementacion = Restrictions.disjunction();
//			complementacion.add(Restrictions.isNull("numSerie"));
//			complementacion.add(Restrictions.isNull("numSim"));
//			complementacion.add(Restrictions.isNull("numIp"));
//			or.add(complementacion);
//		}
//		and.add(or);
//		c.add(and);
//		
//		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		List<DispositivoMovilDTO> response = c.list();
//		
//		return response;
//	}
	
	
}
