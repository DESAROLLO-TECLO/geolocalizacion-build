package mx.com.teclo.alg.ws.negocio.service.eventogps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.com.teclo.alg.ws.persistencia.hibernate.dao.dispositivosmoviles.DispositivoMovilTipoDAO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dao.empleados.EmpleadoDAO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dao.parametro.ParametroDAO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilTipoDTO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.empleados.EmpleadosDTO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.parametro.ParametroDTO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.dispositivosmoviles.DispositivoMovilTipoVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.EventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.TipoEventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.request.RequestEventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.response.ReponseEventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.layout.LayoutVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.parametro.ParametroVO;
import mx.com.teclo.arquitectura.ortogonales.responsehttp.BadRequestHttpResponse;
import mx.com.teclo.arquitectura.ortogonales.responsehttp.BusinessHttpResponse;
import mx.com.teclo.arquitectura.ortogonales.util.ResponseConverter;
import mx.com.teclo.smm.wsw.util.bd.ConnectionUtilBd;
import mx.com.teclo.smm.wsw.util.dataType.DataTypeValidator;
import mx.com.teclo.smm.wsw.util.google.ServicioGoogle;
import mx.com.teclo.sms.ws.util.enums.Catalogos;
import mx.com.teclo.sms.ws.util.enums.Codigos;
import mx.com.teclo.sms.ws.util.enums.Mensajes;
import mx.com.teclo.sms.ws.util.enums.NumeroServicio;
import mx.com.teclo.sms.ws.util.enums.TipoOperacion;

@Service
public class EventoAlgFileServiceImpl implements EventoAlgFileService{

	@Autowired
	private EventoAlgServiceOptBDService eventoAlgServiceOptBDService;
	
	@Autowired
	private CollectionService collectionService;
	
	@Autowired
	private DataTypeValidator dataTypeValidator;
	
	@Autowired
	private EventoAlgService eventoAlgService;
	
	@Autowired
	private EventoAlgFileService eventoAlgFileService;
	
	@Autowired
	private DispositivoMovilTipoDAO dispositivoMovilTipoDAO;
	
	@Autowired
	private ConnectionUtilBd conection;
	
	@Autowired
	private ParametroDAO parametroDAO;
	
	@Autowired
	private EmpleadoDAO empleadoDAO;
	
	private static final long  MEGABYTE = 1024L * 1024L;
	
	private final static Long EMPLEADO_DEFAULT = 99L;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> processFile(MultipartFile file, String voInfo) throws BusinessHttpResponse, IOException, ParseException, SQLException, NamingException{
		
		// OBTENER EL TIEMPO ACTUAL ANTES DE EMPEZAR TODA LA OPERACIÓN
		Long start = System.currentTimeMillis();
		
		List<ParametroVO> pListVO = eventoAlgServiceOptBDService.restriction();
		RequestEventoAlgVO objectReturn = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		Date today = new Date();
		String todayStr = sdf.format(today);
		ReponseEventoAlgVO reponseEventoAlgVO= new ReponseEventoAlgVO() ;
		
		// DEBEMOS VALIDAR SI EL SERVICIO SOPORTA MULTIPLES PROCESOS
		// OBTENER EL NÚMERO DE SERVICIOS ACTIVOS
		ParametroVO p0VO = restriction("TALG004_PROCESS_MUL", pListVO);
		ParametroVO p0_1VO = restriction("TALG004_PROCESS_ACT", pListVO);
		
		// CUANDO SEA UN PROCESO A LA VEZ DEBEMOS COMPROBAR SI
		// ACTUALMENTE EXISTEN UNO ACTIVO
		if(p0VO.getTxValor().equals("0")) {
			if(p0_1VO.getTxValor().equals("1"))
				throw new BusinessHttpResponse(TipoOperacion.PROCESS_MULTI_NOT_PERMIT.getTpOperacion(), TipoOperacion.PROCESS_MULTI_NOT_PERMIT.getCdOperacion()+NumeroServicio.SERVICIO3.getNumServicio(), null);
		}else {
			// EL SISTEMA SOPORTA OPERACIONES MULTIPLES
			// DEBEMOS COMPROBAR EL NÚMERO DE PROCESOS QUE PUEDEN ESTAR ACTIVOS
			ParametroVO p0_2VO = restriction("TALG004_NUM_PROCESS", pListVO);
			if(Integer.parseInt(p0_1VO.getTxValor()) >=  Integer.parseInt(p0_2VO.getTxValor())) 
				throw new BusinessHttpResponse(TipoOperacion.PROCESS_NUM_MAX_PROCESS.getTpOperacion(), TipoOperacion.PROCESS_NUM_MAX_PROCESS.getCdOperacion()+NumeroServicio.SERVICIO3.getNumServicio(), null);
		}

		// VALIDAMOS QUE EL ARCHIVO RECIBIDO SEA DE UN TEXTO PLANO
		ParametroVO p1VO = restriction("TALG004_FORMAT_FILE", pListVO);
		
		if (!file.getContentType().toString().equals(p1VO.getTxValor()))
			throw new BusinessHttpResponse(TipoOperacion.FILE_NOT_TEXT_PLAIN.getTpOperacion(), TipoOperacion.FILE_NOT_TEXT_PLAIN.getCdOperacion()+NumeroServicio.SERVICIO3.getNumServicio(), null);
		
		// VALIDAMOS QUE EL ARCHIVO NO ESTÉ VACÍO
		if (file.getSize() <= 0)
			throw new BusinessHttpResponse(TipoOperacion.FILE_IS_EMPTY.getTpOperacion(), TipoOperacion.FILE_IS_EMPTY.getCdOperacion()+NumeroServicio.SERVICIO3.getNumServicio(), null);
		
		// VALIDAR EL TAMAÑO MAXIMO DEL ARCHIVO
		ParametroVO p2VO = restriction("TALG004_MAX_TAM_FILE", pListVO);
		Long l = new Long(p2VO.getTxValor());
		Long fileSizeInMB = getToMb(file.getSize());
		if (fileSizeInMB >  l.longValue())
			throw new BusinessHttpResponse(TipoOperacion.FILE_SIZE.getTpOperacion(), TipoOperacion.FILE_SIZE.getCdOperacion()+NumeroServicio.SERVICIO3.getNumServicio(), null);
		
		// VALIDAR TAMAÑO MÁXIMO DE REGISTROS
		ParametroVO p3VO = restriction("TALG004_MAX_REGI", pListVO);
		Map<String, Object> map = readFromIS(file.getInputStream());
		Integer nuRecords = (Integer) map.get("TOTAL");
		Integer nuRecordsPermited = Integer.parseInt(p3VO.getTxValor()) ;
		if(nuRecords > nuRecordsPermited)
			throw new BusinessHttpResponse(TipoOperacion.MAX_RECORDS.getTpOperacion(), TipoOperacion.MAX_RECORDS.getCdOperacion()+NumeroServicio.SERVICIO3.getNumServicio(), null);
		
		// VALIDAR EL OBJETO ACTUAL DE DATOS
		if(voInfo == null) {
			objectReturn = new RequestEventoAlgVO();
			//String nbArchivo = file.getOriginalFilename().toString().substring(0,file.getOriginalFilename().toString().length() - 4);
			String txExtencion = file.getOriginalFilename().toString().substring(file.getOriginalFilename().toString().length() - 3);
			objectReturn.setTxExtension(txExtencion);
		}else {
			objectReturn = (RequestEventoAlgVO) makeObject(voInfo, RequestEventoAlgVO.class);
		}
		
		// CUANDO PASE TODAS LAS VALIDACIONES CORRESPONDIENTES DEBEMOS INCREMENTAR
		// EL NÚMERO DE PROCESOS ACTIVOS
		eventoAlgServiceOptBDService.updateProcess("ADICION");
		
		Map<String, Object> fileContentStatus = processRecords(file.getInputStream());
		
		List<Map<String, Object>> lineCool = (List<Map<String, Object>>) fileContentStatus.get("LISTSUCCESS");
		List<String> lineError = (List<String>) fileContentStatus.get("LISTERROR");
		
		objectReturn.setNuRegistrosCorrectos((Long) fileContentStatus.get("COUNTESUCCESS"));
		objectReturn.setNuRegistrosCorruptos((Long) fileContentStatus.get("COUNTEERROR"));
		
		ParametroVO p4VO = restriction("TALG004_PATH_FILE", pListVO);
		
		Boolean result = eventoAlgServiceOptBDService.saveEvent(lineCool);
		Map<String, Object> mapWritefile = createFile(file.getBytes(), file.getOriginalFilename(), todayStr, p4VO.getTxValor());
		Map<String, Object> errorFile = null;
		if(!lineError.isEmpty()) {			
			errorFile = writeErrorFile(lineError, (String) mapWritefile.get("NBFINAL"), todayStr, p4VO.getTxValor());
		}
		if(result) {
			// DESPUÉS DE TERMINAR TODOS LOS PROCESOS DEVEMOS DECREMENTAR
			// EL CONTADOR DE PROCESOS JEJEJE
			eventoAlgServiceOptBDService.updateProcess("SUSTRACCION");
		}
		
		// DESPUÉS DE GUARDAR TODOS LOS REGISTROS ESCRIBIR ARCHIVOS
		objectReturn.setNuRegistroOriginal(new Long(nuRecords));
		objectReturn.setNbArchivoFinal((String) mapWritefile.get("PATH"));
		objectReturn.setNbArchivo((String) mapWritefile.get("NBFINAL"));
		objectReturn.setTxRuta(p4VO.getTxValor());
		objectReturn.setIdServicio(NumeroServicio.SERVICIO3.getNumServicio());
		objectReturn.setTxTipoEvento("REQUEST");
		objectReturn.setTxFormato(file.getContentType().toString());
		objectReturn.setNuPesoArchivo(fileSizeInMB+" MB");
		objectReturn.setFhEnvio(new Date());
		
		reponseEventoAlgVO.setCodigoHttp(Codigos.CREATE.getCodigoId());
		reponseEventoAlgVO.setDescripcion(Mensajes.MSJ_CREATE.getMensaje());
		
		Map<String, Object> mapReturn = new HashMap<>();
		mapReturn.put("DATAFILE", objectReturn);
		mapReturn.put("RESULTBD", result);
		if(errorFile != null) {
			mapReturn.put("ERRORFILE", errorFile.get("BUFFERWRITER"));
			mapReturn.put("ERRORFILENAME", errorFile.get("ERRORFILENAME"));
			mapReturn.put("ERRORNAME", errorFile.get("ERRORNAME"));
			reponseEventoAlgVO.setDescripcion(Mensajes.MSJ_NOT_ENOUGH_DATA.getMensaje());
			reponseEventoAlgVO.setCodigoHttp(Codigos.BAD_REQUEST.getCodigoId());
		}
		
		//ReponseEventoAlgVO
		mapReturn.put("reponseEventoAlgVO", reponseEventoAlgVO);
		mapReturn.put("TIMEIN",start);
		return mapReturn;
	}
	
	private Map<String, Object> writeErrorFile(List<String> l,String nbFile, String fh, String path) throws IOException {
		Map<String, Object> mapReturn = new HashMap<>();
		String [] nbFileSplit = nbFile.split("\\.");
		StringBuilder sb = new StringBuilder();
		for(int i=0; i < nbFileSplit.length; i++) {
			if(i != nbFileSplit.length - 1) {
				sb.append(nbFileSplit[i]);
			}
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(path+sb.toString()+"_ERROR.txt"))) {
            for(String str: l){
                writer.write(str+"\n");
            }
            mapReturn.put("ERRORNAME",sb.toString()+"_ERROR.txt");
            mapReturn.put("ERRORFILENAME", path+sb.toString()+"_ERROR.txt");
            mapReturn.put("BUFFERWRITER", writer);
            return mapReturn;
        }
	}
	
	@Override
	public ParametroVO restriction(String cdParam, List<ParametroVO> l) {
		ParametroVO vo = null;
		for(ParametroVO pVO: l) {
			if(pVO.getCdParametro().equals(cdParam)) {
				vo = pVO;
				break;
			}
		}
		return vo;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object makeObject(String json, Class classType) {
		Object objectReturn = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			objectReturn = mapper.readValue(json, classType);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return objectReturn;
	}
	
	private Map<String, Object> createFile(byte [] fileBytes, String originalFileName, String fh, String filePath){
		String absolutePath = "";
		Map<String, Object> mapReturn = new HashMap<>();
		try {
			absolutePath = filePath;
			File archivo = new File(absolutePath);
			if (!archivo.exists()) {
				archivo.mkdir();
			}
			absolutePath = absolutePath + fh + "_" + originalFileName;
			Path path = Paths.get(absolutePath);
			Files.write(path, fileBytes);
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		mapReturn.put("PATH", absolutePath);
		mapReturn.put("NBFINAL", fh + "_" + originalFileName);
		return mapReturn;
	}
	
	@SuppressWarnings("unused")
	private Map<String, Object> readFromIS(InputStream inputStream) throws IOException {
		Map<String, Object> mapReturn = new HashMap<>();
		Integer registers = 0;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				registers ++;
			}
		}
		mapReturn.put("TOTAL", registers);
		return mapReturn;
	}
	
	private Map<String, Object> processRecords (InputStream inputStream) throws IOException{
		// INICIALIZAMOS EL MAPA DE RESULTADO QUE SERÁ DEVUELTO
		Map<String, Object> mapReturn = new HashMap<>();
		// INCIALIZAMOS LAS VARIABLES QUE SE RETORNARÁN EN EL MAP
		Long registersError = 0L;// NÚMERO DE REGISTROS CORRUPTOS
		List<String> errorsLine = new ArrayList<>();// LISTA DE REGISTROS CON ERRORES
		Map<String, Object> mapSingle = null;
		TipoEventoAlgVO teVO = null;
		
		// OBTENEMOS EL LAYOUT DEL ARCHIVO ACTUAL 
		List<LayoutVO> lListVO = collectionService.layout("TALG007C_GPS_EVENT");
		// OBTENEMOS LOS TIPOS DE EVENTO QUE EXISTEN EN BD
        List<TipoEventoAlgVO> teListVO = collectionService.typeEvent();
        // EXTREAMOS LA CABECERAS ACTUALES DEL LAYOUT
		String [] headers = getHeaders(lListVO);
		List<Map<String, Object>> successLine = new ArrayList<>();// LISTA DE MAPAS DE REGISTRO CORRECTOS
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] values = separateLine(line, "\\|");
				if(headers.length == values.length){
					mapSingle = new LinkedHashMap<>();
					for (int i=0; i < headers.length; i++) {
            			mapSingle.put(headers[i], values[i]);
            		}
					// OBTENEMOS EL TIPO DE EVENTO QUE CONTIENE EL REGISTRO
					//MEDIANTE SU CÓDIGO IDENTIFICADOR
					teVO = filterTypeEvent(teListVO, (String) mapSingle.get("tipoEventoCodigo"));
					Boolean stValues = checkHeaderValues(mapSingle, headers, values, lListVO);
					if(teVO == null || !stValues) {
						registersError ++;
						errorsLine.add(line);
					}else {
						successLine.add(mapSingle);
					}
				}else {
					// CUANDO NO SE CUMPLE, SIGNIFICA QUE HACEN FALTA CABECERAS, SE CUENTA COMO REGISTRO CORRUPTO
					registersError ++;
					errorsLine.add(line);
				}
			}
		}
		mapReturn.put("COUNTEERROR", registersError);
		mapReturn.put("COUNTESUCCESS", new Long(successLine.size()));
		mapReturn.put("LISTERROR", errorsLine);
		mapReturn.put("LISTSUCCESS", successLine);
		return mapReturn;
	}
	
	private Boolean checkHeaderValues (Map<String, Object> mapSingle, String [] headers, String[] values, List<LayoutVO> l) {
		LayoutVO lVO = null;
		for(int i=0; i < headers.length; i++) {
			// OBTENEMOS EL EL REGISTRO DEL LAYOUT MEDIANTE EL NOMBRE 
			// IDENTIFICADOR
			lVO = filterLayout(headers[i], l);
			boolean isRquired = lVO.getStRequerido() == 1 ? true: false;
			boolean requireCheck = lVO.getStAplicaValidacion() == 1 ? true: false;
			Boolean b = dataTypeValidator.validate(lVO.getCdTipoDato(), values[i], isRquired, lVO.getTxMascara(), lVO.getNuLongitudMin(), lVO.getNuLongitudMax(), requireCheck);
			if(b)
				continue;
			else
				return false;
		}
		return true;
	}
	
	private LayoutVO filterLayout(String nbCampo, List<LayoutVO> l) {
		for(LayoutVO vo: l) {
			if(vo.getNbCampo().equals(nbCampo))
				return vo; 
		}
		return null;
	}
	
	private String[] separateLine(String line, String separator){
        String[] strVector = line.split(separator);
        return strVector;
    }
    	
	@Override
	public TipoEventoAlgVO filterTypeEvent (List<TipoEventoAlgVO> l, String cdTipoEvento) {
		if(cdTipoEvento == null || l.isEmpty())
			return null;
		TipoEventoAlgVO objectReturn = null;
		for(TipoEventoAlgVO teVO: l) {
			if(teVO.getCdTipoEvento().equals(cdTipoEvento)) {
				objectReturn = teVO;
				break;
			}
		}
		return objectReturn;
	}

	@Transactional
	private DispositivoMovilTipoVO filterTypeDevice(String cdEvent) {
		DispositivoMovilTipoDTO dispo = dispositivoMovilTipoDAO.activoByCode(cdEvent);
		DispositivoMovilTipoVO voReturn = new DispositivoMovilTipoVO();
		ResponseConverter.copiarPropriedades(voReturn, dispo);
		return voReturn;
	}
	
	@Override
	public DispositivoMovilTipoDTO filterTypeDevice(List<DispositivoMovilTipoVO> l, String cdTipoDispositivo) {
		if(cdTipoDispositivo == null || l.isEmpty())
			return null;
		DispositivoMovilTipoDTO objectReturn = new DispositivoMovilTipoDTO();
		for(DispositivoMovilTipoVO tdVO : l) {
			if(tdVO.getCdTipoDispositivo().equals(cdTipoDispositivo)) {
				//ResponseConverter.copiarPropriedades(objectReturn, tdVO);
				objectReturn.setIdTipoDispositivo(tdVO.getIdTipoDispositivo());
				break;
			}
		}
		return objectReturn;
	}
	
	private Long getToMb (Long filebyte) {
		return filebyte / MEGABYTE ;
	}
	
	private String[] getHeaders (List<LayoutVO> l) {
		String[] vectorReturn = new String[l.size()];
		for(int i=0; i < l.size(); i++) {
			vectorReturn[i] = l.get(i).getNbCampo();
		}
		return vectorReturn;
	}

	@Transactional
	private ParametroVO restriction(String cd) {
		ParametroDTO pDTO = parametroDAO.restrictionByCode(cd);
		ParametroVO pVO = new ParametroVO();
		ResponseConverter.copiarPropriedades(pVO, pDTO);
		return pVO;
	} 
	
	@Transactional
	@Override
	public ReponseEventoAlgVO saveEvent(EventoAlgVO eventoVO) throws BadRequestHttpResponse, SQLException, NamingException {
		//List<TipoEventoAlgDTO> tipoEventosAlg = tipoEventoAlgDAO.getTipoEventosActivos();
		List<TipoEventoAlgVO> tipoEventosAlg = collectionService.typeEvent();
		ParametroVO pVO = restriction("TALG004_DIR");
		boolean searchDir = (pVO != null && pVO.getTxValor() != null && (pVO.getTxValor().equals("1"))) ? true: true;
		
		if(!eventoAlgService.validObject(eventoVO, tipoEventosAlg)){
			eventoVO.setGuardado(false);
			throw new BadRequestHttpResponse(Mensajes.MSJ_NOT_ENOUGH_DATA.getMensaje(), Codigos.BAD_REQUEST.getCodigoId(), eventoVO);
		} else {
			eventoVO.setGuardado(true);
		}
		
		String descripcionMensaje = Mensajes.MSJ_WARNING_OBJECT_NOT_FOUND.getMensaje();
		int flagCatalogos = 0;
		ReponseEventoAlgVO response = new ReponseEventoAlgVO();
		//EventoAlgDTO eventoDTO=null;
	
        //eventoDTO = ResponseConverter.copiarPropiedadesFull(eventoVO, EventoAlgDTO.class);
        EmpleadosDTO empleado = empleadoDAO.findUserByPlaca(eventoVO.getPlacaOficial());
        Long empId = (empleado == null ? EMPLEADO_DEFAULT : empleado.getEmpId());
        
        eventoVO.setFechaCreacion(Calendar.getInstance().getTime());
        eventoVO.setUltimaModificacion(Calendar.getInstance().getTime());
        
        /*
        eventoVO.setIdEmpleado(empId);
		eventoDTO.setEstatusEvento(1);
		eventoDTO.setFechaCreacion(Calendar.getInstance().getTime());
		eventoDTO.setUltimaModificacion(eventoDTO.getFechaCreacion());
		eventoDTO.setCreadoPor(empId);
		eventoDTO.setModificadoPor(empId);
		eventoDTO.setHistorico(0);*/
		//Se consulta direccion en google maps 
		//eventoDTO.setDireccion(ServicioGoogle.buscarDireccion(eventoVO.getLatitudGps(),eventoVO.getLongitudGps()));
		//eventoDTO.setDireccion("--");
		// MIENTRAS LA BANDERA Y EL PARÁMETRO EXISTAN ENTONCES DE BUSCAR LA DIRECCIÓN
		String txtDir = null; 
		if(searchDir) {
			txtDir = ServicioGoogle.buscarDireccion(eventoVO.getLatitudGps(),eventoVO.getLongitudGps());
			if(txtDir != null) {
				//mapSingle.put("direccion", txtDir);
				eventoVO.setDireccion(txtDir);
			}else {					
				eventoVO.setDireccion("--");
			}
		}
		// TipoEventoAlgDTO tipoEventoDTO = tipoEventoAlgDAO.activoByCode(eventoVO.getTipoEventoCodigo());
		TipoEventoAlgVO teVO = eventoAlgFileService.filterTypeEvent(tipoEventosAlg, eventoVO.getTipoEventoCodigo());
		Boolean foundTipoEvento = teVO != null; 
		if(!foundTipoEvento){
			eventoVO.setTipoEventoAlg(teVO);
		    descripcionMensaje += Catalogos.TIPO_EVENTO.getNombreCatalogo();
		    flagCatalogos += 1;
		} else {
			TipoEventoAlgVO teNVO = new TipoEventoAlgVO();
			teNVO.setIdTipoEvento(teVO.getIdTipoEvento());
			eventoVO.setTipoEventoAlg(teNVO);
		}
		
		
		//DispositivoMovilTipoDTO dispo = dispositivoMovilTipoDAO.activoByCode(eventoVO.getTipoDispositivoCodigo());
		//DispositivoMovilTipoDTO dtDTO = eventoAlgFileService.filterTypeDevice(typeDevice, eventoVO.getTipoDispositivoCodigo());
		DispositivoMovilTipoVO tdVO = filterTypeDevice(eventoVO.getTipoDispositivoCodigo());
		Boolean foundTipoDispositivo = tdVO != null; 
		if(!foundTipoDispositivo){
			flagCatalogos += 1;
			//eventoDTO.setTipoDispositivoMovil(dispositivoMovilTipoDAO.activoByCode(DEFUALT_TIPO_DISP));
			eventoVO.setTipoDispositivoMovil(tdVO);
			if(flagCatalogos > 0) {
				descripcionMensaje += ", " + Catalogos.TIPO_DISPOSITIVO.getNombreCatalogo();
			}else {
				descripcionMensaje += Catalogos.TIPO_DISPOSITIVO.getNombreCatalogo();
			}
		}else {			
			eventoVO.setTipoDispositivoMovil(tdVO);
		}
		
		if(empId == EMPLEADO_DEFAULT.longValue()) {
			flagCatalogos += 1;
			if(flagCatalogos > 0) {
				descripcionMensaje += ", " + Catalogos.EMPLEADO.getNombreCatalogo();
			}else {
				descripcionMensaje += Catalogos.EMPLEADO.getNombreCatalogo();
			}
		}
		
		eventoVO.setEstatusEvento(1);
		// Abrimos la conexión a BD
        Connection con = conection.conectarBD();
        con.setAutoCommit(false);
        
        Timestamp t1 = null;
        Timestamp t2 = null;
        Timestamp t3 = null;
        
        PreparedStatement stmt = con.prepareStatement("INSERT INTO TALG002D_EVENTOS_GPS(ID_TIPO_EVENTO, EMP_ID, ID_TIPO_DISPOSITIVO, NU_GPS_LATITUD, NU_GPS_LONGITUD,"
        		+ "NU_SERIE, NU_IMEI, NU_INFRACCION_NUM, FH_FECHA_HORA_EVENTO, ST_EVENTO, ID_USR_CREACION, FH_CREACION, ID_USR_MODIFICA,"
        		+ "FH_MODIFICACION, TX_DIRECCION, ST_HISTORICO)"
        		+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        
        t1 = new Timestamp(eventoVO.getFechaCreacion().getTime());
    	t2 = new Timestamp(eventoVO.getUltimaModificacion().getTime());
        t3 = new Timestamp(eventoVO.getFechaHoraEvento().getTime());
        
        
        stmt.setLong(1, (Long) eventoVO.getTipoEventoAlg().getIdTipoEvento());
    	stmt.setLong(2, (Long) empId);
    	stmt.setLong(3, (Long) eventoVO.getTipoDispositivoMovil().getIdTipoDispositivo());
    	stmt.setDouble(4, (Double) eventoVO.getLatitudGps());
    	stmt.setDouble(5, (Double) eventoVO.getLongitudGps());
    	stmt.setString(6, (String) eventoVO.getNumSerie());
    	stmt.setInt(7, (Integer) eventoVO.getNumImei());
    	stmt.setLong(8, (Long) eventoVO.getNumInfraccion());
    	stmt.setTimestamp(9, t3);
    	stmt.setInt(10, (Integer) eventoVO.getEstatusEvento());
    	stmt.setLong(11, (Long) eventoVO.getCreadoPor());
    	stmt.setTimestamp(12, t1);
    	stmt.setLong(13, (Long) eventoVO.getModificadoPor());
    	stmt.setTimestamp(14, t2);
    	stmt.setString(15, (String) eventoVO.getDireccion());
    	stmt.setInt(16, 0);
    	stmt.addBatch();
    	stmt.executeBatch();
    	con.commit();
    	stmt.close();
		con.close();
		//eventoAlgDAO.save(eventoDTO);
		//response.setEventoAlgVO(ResponseConverter.copiarPropiedadesFull(eventoDTO , EventoAlgVO.class));
		response.setEventoAlgVO(eventoVO);
		response.getEventoAlgVO().setTipoEventoAlg(teVO);
		//DispositivoMovilTipoVO dtVO = new DispositivoMovilTipoVO();
		//ResponseConverter.copiarPropriedades(dtVO, dtDTO);
		response.getEventoAlgVO().setTipoDispositivoMovil(tdVO);
		if (eventoVO != null){
			response.setCodigoHttp(Codigos.CREATE.getCodigoId());
			response.setDescripcion(Mensajes.MSJ_CREATE.getMensaje() + ( (flagCatalogos > 0) ? descripcionMensaje : "") );
		}
		return response;
	}
}
