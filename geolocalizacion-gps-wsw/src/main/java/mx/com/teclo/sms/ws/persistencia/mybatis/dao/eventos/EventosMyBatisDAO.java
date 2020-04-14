package mx.com.teclo.sms.ws.persistencia.mybatis.dao.eventos;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.MyBatisDispositivoEventoVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.MyBatisOficialEventoVO;

@Mapper
public interface EventosMyBatisDAO {
	
	public String DISPOSITIVOS = "SELECT EVENTO_ID as idEvento, EMP_ID as idEmpleado, EVENTO_DISPOSITIVO_ID as idDispositivo, EVENTO_NUM_SERIE_HH as numSerieHH, EVENTO_GPS_LAT as latitudGps, "
			+ "EVENTO_GPS_LON as longitudGps, EVENTO_DIRECCION as direccion, "
			+ "te.EVENTO_TIPO_NOMBRE as nombreTipoEvento, NVL(CD_INDICADOR,'#CF0A2C') AS indicadorCodigo, EVENTO_ART_ID as idArticulo, EVENTO_INFRAC_NUM as numInfraccion, e.FECHA_CREACION as fechaCreacion, EVENTO_FECHA_HORA as fechaHoraEvento, "
			+ "TO_CHAR(TRUNC(TO_DATE(#{now}, 'DD/MM/YYYY HH24:MI:SS') - EVENTO_FECHA_HORA), '00') || ' Días' || "
			+ "TO_CHAR(TRUNC(MOD((TO_DATE(#{now}, 'DD/MM/YYYY HH24:MI:SS') - EVENTO_FECHA_HORA) * 24, 24)), '00') || ' Horas' || "
			+ "TO_CHAR(TRUNC(MOD((TO_DATE(#{now}, 'DD/MM/YYYY HH24:MI:SS') - EVENTO_FECHA_HORA) * (60 * 24), 60)), '00') || ' Minutos' as tiempoTranscurrido FROM "
			+ "(SELECT EVENTO_ID, EMP_ID, EVENTO_TIPO_ID, EVENTO_DISPOSITIVO_ID, EVENTO_ART_ID, EVENTO_NUM_SERIE_HH, EVENTO_GPS_LAT, EVENTO_GPS_LON, EVENTO_DIRECCION, EVENTO_INFRAC_NUM, "
			+ "EVENTO_FECHA_HORA, MAX(EVENTO_FECHA_HORA) over (PARTITION BY EVENTO_NUM_SERIE_HH) AS MaxDATEX, EVENTO_ESTATUS, CREADO_POR, FECHA_CREACION, MODIFICADO_POR, ULTIMA_MODIFICACION "
			+ "FROM GPS_EVENTOS WHERE EVENTO_GPS_LAT <> 0 and EVENTO_GPS_LON <> 0) e "
			+ "JOIN GPS_EVENTO_TIPO te ON te.EVENTO_TIPO_ID = e.EVENTO_TIPO_ID "
			+ "LEFT JOIN TMS009C_INDICADORES_EVENTOS ie ON  ROUND((TO_DATE(#{now}, 'DD/MM/YYYY HH24:MI:SS') - MaxDATEX) * 1440) BETWEEN ie.NU_TIEMPO_INICIAL AND ie.NU_TIEMPO_FINAL "
			+ "WHERE EVENTO_FECHA_HORA = MaxDATEX AND EVENTO_FECHA_HORA BETWEEN TO_DATE(#{fechaInicial}, 'DD/MM/YYYY HH24:MI:SS') AND TO_DATE(#{fechaLimite}, 'DD/MM/YYYY HH24:MI:SS') AND EVENTO_NUM_SERIE_HH IN "
			+ "(${series}) "
			+ "ORDER BY EVENTO_NUM_SERIE_HH DESC";
	
	public String OFICIALES = "SELECT EVENTO_ID as idEvento, ev.EMP_ID as idEmpleado, e.EMP_PLACA as empPlaca, e.EMP_NOMBRE as empNombre, e.EMP_APE_MATERNO as empApePaterno, "
			+ "e.EMP_APE_MATERNO as empApeMaterno, EVENTO_NUM_SERIE_HH as numSerieHH, "
			+ "EVENTO_GPS_LAT as latitudGps, EVENTO_GPS_LON as longitudGps, "
			+ "EVENTO_DIRECCION as direccion, te.EVENTO_TIPO_NOMBRE as nombreTipoEvento, "
			+ "NVL(CD_INDICADOR,'#CF0A2C') AS indicadorCodigo, EVENTO_ART_ID as idArticulo, EVENTO_INFRAC_NUM as numInfraccion, "
			+ "ev.FECHA_CREACION as fechaCreacion, EVENTO_FECHA_HORA as fechaHoraEvento, "
			+ "TO_CHAR(TRUNC(TO_DATE(#{now}, 'DD/MM/YYYY HH24:MI:SS') - EVENTO_FECHA_HORA), '00') || ' Días' || "
			+ "TO_CHAR(TRUNC(MOD((TO_DATE(#{now}, 'DD/MM/YYYY HH24:MI:SS') - EVENTO_FECHA_HORA) * 24, 24)), '00') || ' Horas' || "
			+ "TO_CHAR(TRUNC(MOD((TO_DATE(#{now}, 'DD/MM/YYYY HH24:MI:SS') - EVENTO_FECHA_HORA) * (60 * 24), 60)), '00') || 'Minutos' as tiempoTranscurrido "
			+ "FROM "
			+ "(SELECT EVENTO_ID, EMP_ID, EVENTO_TIPO_ID, EVENTO_DISPOSITIVO_ID, EVENTO_ART_ID, EVENTO_NUM_SERIE_HH, EVENTO_GPS_LAT, EVENTO_GPS_LON, EVENTO_DIRECCION, EVENTO_INFRAC_NUM, "
			+ "EVENTO_FECHA_HORA, MAX(EVENTO_FECHA_HORA) OVER (PARTITION BY EMP_ID) AS MaxDATEX, EVENTO_ESTATUS, CREADO_POR, FECHA_CREACION, MODIFICADO_POR, ULTIMA_MODIFICACION "
			+ "FROM GPS_EVENTOS WHERE EVENTO_GPS_LAT <> 0 and EVENTO_GPS_LON <> 0) ev JOIN EMPLEADOS e ON ev.EMP_ID = e.EMP_ID "
			+ "JOIN GPS_EVENTO_TIPO te ON te.EVENTO_TIPO_ID = ev.EVENTO_TIPO_ID "
			+ "LEFT JOIN TMS009C_INDICADORES_EVENTOS ie ON  ROUND((TO_DATE(#{now}, 'DD/MM/YYYY HH24:MI:SS') - MaxDATEX) * 1440) BETWEEN ie.NU_TIEMPO_INICIAL AND ie.NU_TIEMPO_FINAL "
			+ "WHERE ev.EVENTO_FECHA_HORA = ev.MaxDATEX AND ev.EVENTO_FECHA_HORA BETWEEN TO_DATE(#{fechaInicial}, 'DD/MM/YYYY HH24:MI:SS') AND TO_DATE(#{fechaLimite}, 'DD/MM/YYYY HH24:MI:SS') AND EMP_PLACA in (${placas})"
			+ "ORDER BY EVENTO_NUM_SERIE_HH DESC";
	
	@Select(DISPOSITIVOS)
	List<MyBatisDispositivoEventoVO> eventosPorDispositivo(@Param("series") String series, @Param("fechaInicial") String fechaInicial, @Param("fechaLimite") String fechaLimite, @Param("now") String now);
	
	@Select(OFICIALES)
	List<MyBatisOficialEventoVO> eventosPorOficiales(@Param("placas") String placas, @Param("fechaInicial") String fechaInicial, @Param("fechaLimite") String fechaLimite, @Param("now") String now);

}