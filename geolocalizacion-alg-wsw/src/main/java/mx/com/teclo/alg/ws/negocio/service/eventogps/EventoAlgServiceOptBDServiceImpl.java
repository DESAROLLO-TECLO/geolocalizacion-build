package mx.com.teclo.alg.ws.negocio.service.eventogps;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.teclo.alg.ws.persistencia.hibernate.dao.archivo.ArchivoDAO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dao.dispositivosmoviles.DispositivoMovilTipoDAO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dao.empleados.EmpleadoDAO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dao.parametro.ParametroDAO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilTipoDTO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.archivo.ArchivoDTO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.empleados.EmpleadosDTO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.parametro.ParametroDTO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.dispositivosmoviles.DispositivoMovilTipoVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.TipoEventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.request.RequestEventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.parametro.ParametroVO;
import mx.com.teclo.arquitectura.ortogonales.util.ResponseConverter;
import mx.com.teclo.smm.wsw.util.bd.ConnectionUtilBd;
import mx.com.teclo.smm.wsw.util.google.ServicioGoogle;

@Service
public class EventoAlgServiceOptBDServiceImpl implements EventoAlgServiceOptBDService{

	@Autowired
	private ParametroDAO parametroDAO;
	
	@Autowired
	private CollectionService collectionService;
	
	@Autowired
	private EventoAlgFileService eventoAlgFileService;
	
	@Autowired
	private ConnectionUtilBd conection;
	
	@Autowired
	private ArchivoDAO archivoDAO;
	
	@Autowired
	private EmpleadoDAO empleadoDAO;
	
	@Autowired
	private EventoAlgServiceOptBDService eventoAlgServiceOptBDService;
	
	@Autowired
	private DispositivoMovilTipoDAO dispositivoMovilTipoDAO;
	
	@Autowired
	private ServicioGoogle servicioGoogle;
	
	private final static Long EMPLEADO_DEFAULT = 99L;
	
	@Transactional
	@Override
	public Boolean updateProcess(String option) {
		ParametroDTO pDTO = parametroDAO.restrictionByCode("TALG004_PROCESS_ACT");
		Integer val = Integer.parseInt(pDTO.getTxValor());
		switch (option) {
		case "ADICION":
			val += 1;
			break;
		case "SUSTRACCION":
			if(val <= 0) {
				val = 0;	
			}else {
				val -= 1;
			}
			break;
		default:
			break;
		}
		pDTO.setTxValor(val.toString());
		parametroDAO.update(pDTO);
		return true;
	}
	
	@Transactional
	@Override
	public List<ParametroVO> restriction() {
		List<ParametroDTO> pListDTO = parametroDAO.restriction();
		List<ParametroVO> pListVO = ResponseConverter.converterLista(new ArrayList<>(), pListDTO, ParametroVO.class);
		return pListVO;
	}

	@Override
	public Boolean saveEvent(List<Map<String, Object>> map) throws ParseException, SQLException, NamingException {
		TipoEventoAlgVO teVO = null;
		
		// OBTENER EL PARÁMETRO QUE IDENTIFICA SI SE BUSCARÁ LA DIRECCIÓN DEL EVENTO MEDIANTE
		// LA ALTITUD Y LONGITUD
		List<ParametroVO> pListVO = eventoAlgServiceOptBDService.restriction();
		ParametroVO p0VO = eventoAlgFileService.restriction("TALG004_DIR", pListVO);
		boolean searchDir = (p0VO != null && p0VO.getTxValor() != null && (p0VO.getTxValor().equals("1"))) ? true: true; 
		// OBTENEMOS LOS TIPOS DE EVENTO QUE EXISTEN EN BD
        List<TipoEventoAlgVO> teListVO = collectionService.typeEvent();
        List<DispositivoMovilTipoVO> tdListDTO = collectionService.typeDevice();
        //List<EventoAlgDTO> ealgList = new ArrayList<>();
        List<Map<String, Object>> finalsave = new ArrayList<>();
        Map<String, Object> mapSingle = null;
        String txtDir = null;
        Date fhEvent = null;
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        
		for(Map<String, Object> m : map) {
			mapSingle = new HashMap<>();
			teVO = eventoAlgFileService.filterTypeEvent(teListVO, (String) m.get("tipoEventoCodigo"));
			mapSingle.put("tipoEventoAlg", teVO.getIdTipoEvento());
			// OBTENER EL TIPO DE DISPOSITIVO
			DispositivoMovilTipoDTO tdDTO = eventoAlgFileService.filterTypeDevice(tdListDTO, (String) m.get("tipoDispositivoCodigo"));
			mapSingle.put("tipoDispositivoMovil", tdDTO.getIdTipoDispositivo());
			String latitudGps = (String) m.get("latitudGps");
			String longitudGps = (String) m.get("longitudGps");
			mapSingle.put("latitudGps", new Double(latitudGps));
			mapSingle.put("longitudGps", new Double(longitudGps));
			// MIENTRAS LA BANDERA Y EL PARÁMETRO EXISTAN ENTONCES DE BUSCAR LA DIRECCIÓN
			if(searchDir) {
				txtDir = servicioGoogle.buscarDireccion(Double.parseDouble(latitudGps),Double.parseDouble(longitudGps));
				if(txtDir != null) {
					mapSingle.put("direccion", txtDir);
				}else {					
					mapSingle.put("direccion", "--");
				}
			}
			
			// Buscamos el empleado mediante su placa
			Long idEmp = eventoAlgServiceOptBDService.idEmpleado((String)m.get("placaOficial"));
			Long empId = (idEmp == null ? EMPLEADO_DEFAULT : idEmp);
			// AGREGAR EL EMPLEADO
			mapSingle.put("idEmpleado", empId);
			mapSingle.put("numSerie", (String)m.get("numSerie"));
			String numImei = (String) m.get("numImei");
			mapSingle.put("numImei", Integer.parseInt(numImei));
			String numInfraccion = (String)m.get("numInfraccion");
			mapSingle.put("numInfraccion", new Long(numInfraccion));
			fhEvent = sdf.parse((String) m.get("fechaHoraEvento"));
			mapSingle.put("fechaHoraEvento",fhEvent);
			mapSingle.put("estatusEvento", 1);
			mapSingle.put("creadoPor", empId);
			mapSingle.put("fechaCreacion", Calendar.getInstance().getTime());
			mapSingle.put("modificadoPor", empId);
			mapSingle.put("ultimaModificacion", Calendar.getInstance().getTime());
			mapSingle.put("historico", m.get("historico"));
			finalsave.add(mapSingle);
		}
		// Abrimos la conexión a BD
        Connection con = conection.conectarBD();
        
        con.setAutoCommit(false);
        PreparedStatement stmt = con.prepareStatement("INSERT INTO TALG002D_EVENTOS_GPS(ID_TIPO_EVENTO, EMP_ID, ID_TIPO_DISPOSITIVO, NU_GPS_LATITUD, NU_GPS_LONGITUD,"
        		+ "NU_SERIE, NU_IMEI, NU_INFRACCION_NUM, FH_FECHA_HORA_EVENTO, ST_EVENTO, ID_USR_CREACION, FH_CREACION, ID_USR_MODIFICA,"
        		+ "FH_MODIFICACION, TX_DIRECCION, ST_HISTORICO)"
        		+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        
        
        Iterator<Map<String, Object>> it = finalsave.iterator();
        Date d1 = null;
        Date d2 = null;
        Date d3 = null;
        
        Timestamp t1 = null;
        Timestamp t2 = null;
        Timestamp t3 = null;
        
        while(it.hasNext()) {
        	Map<String, Object> m = it.next();
        	
        	d1 = (Date) m.get("fechaCreacion");
        	d2 = (Date) m.get("ultimaModificacion");
        	d3 = (Date) m.get("fechaHoraEvento");
        	
        	t1 = new Timestamp(d1.getTime());
        	t2 = new Timestamp(d2.getTime());
        	t3 = new Timestamp(d3.getTime());
        	
        	
        	stmt.setLong(1, (Long) m.get("tipoEventoAlg"));
        	stmt.setLong(2, (Long) m.get("idEmpleado"));
        	stmt.setLong(3, (Long) m.get("tipoDispositivoMovil"));
        	stmt.setDouble(4, (Double) m.get("latitudGps"));
        	stmt.setDouble(5, (Double) m.get("longitudGps"));
        	stmt.setString(6, (String) m.get("numSerie"));
        	stmt.setInt(7, (Integer) m.get("numImei"));
        	stmt.setLong(8, (Long) m.get("numInfraccion"));
        	stmt.setTimestamp(9, t3);
        	stmt.setInt(10, (Integer) m.get("estatusEvento"));
        	stmt.setLong(11, (Long) m.get("creadoPor"));
        	stmt.setTimestamp(12, t1);
        	stmt.setLong(13, (Long) m.get("modificadoPor"));
        	stmt.setTimestamp(14, t2);
        	stmt.setString(15, (String) m.get("direccion"));
        	stmt.setInt(16, (Integer) m.get("historico"));
        	stmt.addBatch();
        }
        stmt.executeBatch();
        con.commit();
        stmt.close();
		con.close();
		return true;
	}

	@Transactional
	@Override
	public RequestEventoAlgVO saveBitac(Map<String, Object> mapResultProcess) throws FileNotFoundException {
		
		RequestEventoAlgVO rVO = (RequestEventoAlgVO) mapResultProcess.get("DATAFILE");
		
		//OBTENER EL TIEMPO DE INICIO QUE VIENE DENTRO DEL MAPA
		Long start = (Long) mapResultProcess.get("TIMEIN");
		
		Long end = System.currentTimeMillis();
		// finding the time difference and converting it into seconds
		
		float sec = (end - start) / 1000F;
		StringBuilder sb = new StringBuilder();
		sb.append(sec + " segundos");
		
		ArchivoDTO aDTO = new ArchivoDTO();
		aDTO.setNuTiempoProcesamiento(sb.toString());
		aDTO.setNbArchivo(rVO.getNbArchivo());
		aDTO.setNbFinalPath(rVO.getNbArchivoFinal());
		aDTO.setTxPath(rVO.getTxRuta());
		aDTO.setTxFormato(rVO.getTxFormato());
		aDTO.setIdServicio(rVO.getIdServicio());
		aDTO.setTxTipoEvento(rVO.getTxTipoEvento());
		aDTO.setNuRegistroOriginal(rVO.getNuRegistroOriginal());
		aDTO.setNuRegistroGuardado(rVO.getNuRegistrosCorrectos());
		aDTO.setNuRegistroIgnorado(rVO.getNuRegistrosCorruptos());
		aDTO.setNuPesoArchivo(rVO.getNuPesoArchivo());
		aDTO.setStActivo(1);
		aDTO.setIdUsrCreacion(99L);
		aDTO.setFhCreacion(new Date());
		aDTO.setIdUsrModifica(99L);
		aDTO.setFhModificacion(new Date());
		archivoDAO.save(aDTO);
		
		// VALIDAMOS SI HUBO ARCHIVO DE ERRORES
		if(mapResultProcess.get("ERRORFILE") != null) {
			// BufferedWriter bw = (BufferedWriter) mapResultProcess.get("ERRORFILE");
			// DEBEMOS CREAR UNA NUEVA INSTANCIA DEL ArchivoDTO PARA BITACOREAR EL ERROR
			ArchivoDTO aErrorfile = getNewFileDTO(aDTO);
			aErrorfile.setNbFinalPath((String) mapResultProcess.get("ERRORFILENAME"));
			File f = new File(aErrorfile.getNbFinalPath());
			byte fileContent[] = new byte[(int)f.length()];
			rVO.setErrorFile(fileContent);
			aErrorfile.setNbArchivo((String) mapResultProcess.get("ERRORNAME"));
			rVO.setNbArchivoError(aErrorfile.getNbArchivo());
			archivoDAO.save(aErrorfile);
			rVO.setNbArchivoErrorFinal(aErrorfile.getNbFinalPath());
		}
		return rVO;
	}
	
	private ArchivoDTO getNewFileDTO (ArchivoDTO dto) {
		ArchivoDTO fDTOReturn = new ArchivoDTO();
		fDTOReturn.setNbArchivo(dto.getNbArchivo());
		fDTOReturn.setNbFinalPath(dto.getNbFinalPath());
		fDTOReturn.setTxPath(dto.getTxPath());
		fDTOReturn.setTxFormato(dto.getTxFormato());
		fDTOReturn.setIdServicio(dto.getIdServicio());
		fDTOReturn.setTxTipoEvento("RESPONSE");
		fDTOReturn.setNuRegistroOriginal(dto.getNuRegistroIgnorado());
		fDTOReturn.setNuRegistroGuardado(0L);
		fDTOReturn.setNuRegistroIgnorado(dto.getNuRegistroIgnorado());
		fDTOReturn.setStActivo(1);
		fDTOReturn.setIdUsrCreacion(99L);
		fDTOReturn.setFhCreacion(new Date());
		fDTOReturn.setIdUsrModifica(99L);
		fDTOReturn.setFhModificacion(new Date());
		return fDTOReturn;
	}

	@Transactional
	@Override
	public Long idEmpleado(String cdPlaca) {
		EmpleadosDTO empleado = empleadoDAO.findUserByPlaca(cdPlaca);
		if(empleado == null)
			return null;
		return empleado.getEmpId();
	}
	
	@Override
	@Transactional
	public DispositivoMovilTipoVO filterTypeDevice(String cdEvent) {
		DispositivoMovilTipoDTO dispo = dispositivoMovilTipoDAO.activoByCode(cdEvent);
		DispositivoMovilTipoVO voReturn = new DispositivoMovilTipoVO();
		ResponseConverter.copiarPropriedades(voReturn, dispo);
		return voReturn;
	}
	
	@Override
	@Transactional
	public ParametroVO restriction(String cd) {
		ParametroDTO pDTO = parametroDAO.restrictionByCode(cd);
		ParametroVO pVO = new ParametroVO();
		ResponseConverter.copiarPropriedades(pVO, pDTO);
		return pVO;
	}
	
}
