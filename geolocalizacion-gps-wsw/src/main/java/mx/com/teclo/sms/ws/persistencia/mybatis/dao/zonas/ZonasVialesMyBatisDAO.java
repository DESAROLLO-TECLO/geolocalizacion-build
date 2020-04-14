package mx.com.teclo.sms.ws.persistencia.mybatis.dao.zonas;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.DispositivoMovilVO;
import mx.com.teclo.sms.ws.persistencia.vo.empleado.EmpleadosRespectivosAreaOperativaVO;
import mx.com.teclo.sms.ws.persistencia.vo.zonavial.ZonaVialDispositivosOfficersVO;

@Mapper
public interface ZonasVialesMyBatisDAO {
	
	String ZONAS = "SELECT Z.ID_ZONA_VIAL, Z.NB_ZONA_VIAL, Z.CD_ZONA_VIAL, Z.ST_ACTIVO FROM TMS002C_ZONA_VIAL Z WHERE Z.ID_ZONA_VIAL IN("
			+ "SELECT CE.ID_ZONA_VIAL FROM TMS004D_ZONA_VIAL_CARGO_EMP CE "
			+ "JOIN EMPLEADOS E ON CE.EMP_ID = E.EMP_ID "
			+ "JOIN TMS007C_CARGOS C ON CE.ID_CARGO = C.ID_CARGO "
			+ "WHERE E.EMP_PLACA = #{placa} AND C.CD_CARGO = 'SUPERVISOR')";
	
	String DEVICES = "SELECT D.ID_DISPOSITIVO AS idDispositivo, D.NUM_SERIE AS numSerie, D.NUM_SIM AS numSim, D.NUM_IP AS numIp "
			+ "FROM TMS001C_DISPOSITIVOS_MOVILES D WHERE D.ID_DISPOSITIVO IN( SELECT  DZ.ID_DISPOSITIVO FROM TMS003D_DISPOSITIVO_ZONA_VIAL DZ WHERE DZ.ID_ZONA_VIAL = #{idZonaVial})";
	
	String OFFICERS = "SELECT E.EMP_PLACA AS empPlaca, E.EMP_NOMBRE AS empNombre, E.EMP_APE_PATERNO AS empApePaterno, E.EMP_APE_MATERNO AS empApeMaterno, E.EMP_ID AS empId "
			+ "FROM EMPLEADOS E WHERE E.EMP_ID IN( SELECT  EZ.EMP_ID FROM TMS004D_ZONA_VIAL_CARGO_EMP EZ JOIN TMS007C_CARGOS C ON EZ.ID_CARGO = C.ID_CARGO WHERE C.CD_CARGO = 'OFICIAL' AND EZ.ID_ZONA_VIAL = #{idZonaVial})";
	
	@Select(ZONAS)
    @Results(value = {
                      @Result(property="idZonaVial", column="ID_ZONA_VIAL"),
                      @Result(property="nombreZonaVial", column="NB_ZONA_VIAL"),
                      @Result(property="codigoZonaVial", column="CD_ZONA_VIAL"),
                      @Result(property="activo", column="ST_ACTIVO"),
                      @Result(property="dispositivos", javaType=List.class, column="ID_ZONA_VIAL", many=@Many(select="getDevices")),
                      @Result(property="oficiales", javaType=List.class, column="ID_ZONA_VIAL", many=@Many(select="getOfficers"))
                      })
	List<ZonaVialDispositivosOfficersVO> getZonas(@Param("placa") String placa);
 
    @Select(DEVICES)
    List<DispositivoMovilVO> getDevices(Long idZonaVial);
    
    @Select(OFFICERS)
    List<EmpleadosRespectivosAreaOperativaVO> getOfficers(Long idZonaVial);
}
