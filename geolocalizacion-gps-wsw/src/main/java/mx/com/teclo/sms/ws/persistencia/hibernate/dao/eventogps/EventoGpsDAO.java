package mx.com.teclo.sms.ws.persistencia.hibernate.dao.eventogps;

import java.util.Date;
import java.util.List;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.hh.eventogps.EventoGpsDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface EventoGpsDAO extends BaseDao<EventoGpsDTO> {

	/**
	 * @author Francisco Martinez
	 * @param eventoGpsDTO
	 * @return EventoGpsDTO
	 */
	public EventoGpsDTO saveEvento(EventoGpsDTO eventoGpsDTO);

	/**
	 * @author Francisco Martinez
	 * @param listaEventoGpsDTO
	 * @return List<EventoGpsDTO>
	 */
	public List<EventoGpsDTO> saveListaEvento(List<EventoGpsDTO> listaEventoGpsDTO);

	/**
	 * @author Kevin Ojeda
	 * @param numerosSerie
	 * @param fechaInicial
	 * @param fechaFin
	 * @return List<EventoGpsDTO>
	 */
	public List<EventoGpsDTO> byHHAndFecha(List<String> numerosSerie, Date fechaInicial, Date fechaFin);
	
	/**
	 * @author Kevin Ojeda
	 * @param dispositivoId
	 * @param fechaInicial
	 * @param fechaFin
	 * @return List<EventoGpsDTO>
	 */
	public List<EventoGpsDTO> byHHIdsAndFecha(List<Long> dispositivoId, Date fechaInicial, Date fechaFin);

	/**
	 * @author Kevin Ojeda
	 * @param series
	 * @param days
	 * @param fechaInicial
	 * @param fechaFin
	 * @return List<List<EventoGpsDTO>>
	 */
	public List<List<EventoGpsDTO>> lastEventsByNumDiasHHSerieAndFecha(List<String> series, int days, Date fechaInicial, Date fechaFin);

	/**
	 * @author Kevin Ojeda
	 * @param placas
	 * @param fechaInicial
	 * @param fechaFin
	 * @return List<EventoGpsDTO>
	 */
	public List<EventoGpsDTO> byEmpIdsAndFecha(List<Long> placas, Date fechaInicial, Date fechaFin);

	/**
	 * @author Kevin Ojeda
	 * @param ids
	 * @param days
	 * @param fechaInicial
	 * @param fechaFin
	 * @return List<List<EventoGpsDTO>>
	 */
	public List<List<EventoGpsDTO>> lastEventsByEmpIdsAndFecha(List<Long> ids, int days, Date fechaInicial, Date fechaFin);

	/**
	 * @author Kevin Ojeda
	 * @param zonasVialesId
	 * @param fI
	 * @param fF
	 * @param idTipoEvento
	 * @param codigosTipoInfraccion
	 * @param dispistivosId
	 * @param loginRestriction
	 * @return List<EventoGpsDTO>
	 */
	public List<EventoGpsDTO> reporteGeneral(Integer loginRestriction, List<Long> zonasVialesId, Date fI, Date fF, List<Long> idTipoEvento,
			List<String> codigosTipoInfraccion);

	/**
	 * @author Kevin Ojeda
	 * @param oficiales
	 * @param dispositivos
	 * @param fI
	 * @param fF
	 * @param idsTipoEvento
	 * @param codigosTipoInfraccion
	 * @return List<EventoGpsDTO>
	 */
	public List<EventoGpsDTO> reporteGeneralOficialesDispositivos(Integer loginRestriction, List<Long> oficiales, List<Long> dispositivos, Date fI, Date fF, List<Long> idsTipoEvento, List<String> codigosTipoInfraccion);

	/**
	 * @author Kevin Ojeda
	 * @param oficiales
	 * @param dispositivos
	 * @param fI
	 * @param fF
	 * @param idsTipoEvento
	 * @param codigosTipoInfraccion
	 * @return
	 */
	public List<EventoGpsDTO> reporteGeneralOficialesConDispositivos(Integer loginRestriction, List<Long> oficiales, List<Long> dispositivos, Date fI,
			Date fF, List<Long> idsTipoEvento, List<String> codigosTipoInfraccion);

	/**
	 * @author Kevin Ojeda
	 * @param series
	 * @param days
	 * @param fechaInicial
	 * @param fechaFin
	 * @return List<EventoGpsDTO>
	 */
	public List<EventoGpsDTO> optimizedLastEventsByNumDiasHHSerieAndFecha(List<String> series, int days, Date fechaInicial, Date fechaFin);
	
	/**
	 * @author Jesus Gutierrez
	 * @param placas
	 * @param days
	 * @param fechaInicial
	 * @param fechaFin
	 * @return List<EventoGpsDTO>
	 */
	public List<EventoGpsDTO> optimizedLastEventsByNumDiasPlacasAndFecha(List<String> placas, int days, Date fechaInicial, Date fechaFin);

	/**
	 * @author Kevin Ojeda
	 * @param numerosSerie
	 * @param fechaInicial
	 * @param fechaFin
	 * @return List<EventoGpsDTO>
	 */
	public List<EventoGpsDTO> byUniqueHHAndFecha(String numerosSerie, Date fechaInicial, Date fechaFin);

	/**
	 * @author Kevin Ojeda
	 * @param id
	 * @param fechaInicial
	 * @param fechaFin
	 * @return List<EventoGpsDTO>
	 */
	public List<EventoGpsDTO> byUniqueEmpIdAndFecha(Long id, Date fechaInicial, Date fechaFin);
}