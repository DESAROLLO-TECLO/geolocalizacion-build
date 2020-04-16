package mx.com.teclo.geolocalizacionalgwsw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.com.teclo.alg.ws.negocio.service.eventogps.CollectionService;
import mx.com.teclo.alg.ws.negocio.service.eventogps.EventoAlgFileService;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.EventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.TipoEventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.response.ReponseEventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.layout.LayoutVO;
import mx.com.teclo.arquitectura.ortogonales.responsehttp.BadRequestHttpResponse;
import mx.com.teclo.arquitectura.ortogonales.seguridad.service.webservice.WSService;
import mx.com.teclo.arquitectura.ortogonales.seguridad.vo.webservice.NewTokenDataVO;
import mx.com.teclo.arquitectura.ortogonales.seguridad.vo.webservice.TokenUsuarioFuncionVO;
import mx.com.teclo.smm.wsw.util.dataType.DataTypeValidator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MsAlgWswApplication.class })
public class WebServiceAlgApplicationTests {

	@Autowired
	private WSService wsService;
	
	@Autowired
	private CollectionService collectionService;
	
	@Autowired
	private DataTypeValidator dataTypeValidator;
	
	@Autowired
	private EventoAlgFileService eventoAlgFileService;
	
	@Value("${app.config.codigo}")
	private String codeApplication;
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Test
	public void processTwo () throws FileNotFoundException, IOException {
		File f = new File("/files/eventos.txt"); 
		InputStream inputStream = new FileInputStream(f);
	
		// finding the time before the operation is executed
		long start = System.currentTimeMillis();
		
		Map<String, Object> map = processRecords(inputStream);
		List<String> errorsLine = (List<String>) map.get("LISTERROR"); 
		List<Map<String, Object>> successLine = (List<Map<String, Object>>) map.get("LISTSUCCESS");
		
		System.out.println("COUNTEERROR: " + map.get("COUNTEERROR"));
		System.out.println("COUNTESUCCESS: " + map.get("COUNTESUCCESS"));
		
		long end = System.currentTimeMillis();
		// finding the time difference and converting it into seconds
		float sec = (end - start) / 1000F;
		System.out.println(sec + " seconds");
		
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
	
	private String[] getHeaders (List<LayoutVO> l) {
		String[] vectorReturn = new String[l.size()];
		for(int i=0; i < l.size(); i++) {
			vectorReturn[i] = l.get(i).getNbCampo();
		}
		return vectorReturn;
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
	
	private TipoEventoAlgVO filterTypeEvent (List<TipoEventoAlgVO> l, String cdTipoEvento) {
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
	
	@Test
	public void contextLoads() throws URISyntaxException, IOException {
		File f = new File("/files/test.txt");
		InputStream inputStream = new FileInputStream(f);
		// finding the time before the operation is executed
		long start = System.currentTimeMillis();	
		Map<String, Object> result = readFromInputStream(inputStream);
		// finding the time after the operation is executed
		long end = System.currentTimeMillis();
		// finding the time difference and converting it into seconds
		float sec = (end - start) / 1000F;
		System.out.println(sec + " seconds");
		System.out.println(result.get("TOTAL") + " registers");
		
		byte fileContent[] = new byte[(int)f.length()];
		System.out.println(fileContent.length);
		
	}

	private Map<String, Object> readFromInputStream(InputStream inputStream) throws IOException {
		Map<String, Object> mapReturn = new HashMap<>();
		Integer registers = 0;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line = null;
			while(br.readLine() != null) {
				line = br.readLine();
				System.out.println(line);
				registers ++;
			}
		}
		mapReturn.put("TOTAL", registers);
		return mapReturn;
	}
	
	@Test
	public void createToken () throws Exception {
		NewTokenDataVO vo = new NewTokenDataVO();
		vo.setCodigoUsuario("tecloalg");
		vo.setCodigoFuncion("ALG004");
		TokenUsuarioFuncionVO voReturn =null;
		try{
			voReturn = wsService.newTokenData(vo);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		if(voReturn != null) {
			System.out.println("data: " + voReturn.getToken());
		}else {
			
		}
	}
	
	@Test
	public void newSave () throws JsonParseException, JsonMappingException, IOException, BadRequestHttpResponse, SQLException, NamingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String voStr = getEvent();  //5512349674
		EventoAlgVO vo = objectMapper.readValue(voStr, EventoAlgVO.class);
		ReponseEventoAlgVO voReturn = eventoAlgFileService.saveEvent(vo);
		if(voReturn != null) { 
			System.err.println(voReturn.getCodigoHttp());
			System.err.println(voReturn.getDescripcion());
		}
	}
	
	public String getEvent () {
		return "{\n" + 
				"\"numSerie\"     : \"13164521405565\",\n" + 
				"\"numImei\"      :  \"128838245\",\n" + 
				"\"tipoDispositivoCodigo\" : \"HANHDHELD\",\n" + 
				"\"tipoEventoCodigo\"   : \"INFRACCION\",\n" + 
				"\"latitudGps\"     : 19.483096, \n" + 
				"\"longitudGps\"    : -99.153678,\n" + 
				"\"direccion\"      :\"S/N\",\n" + 
				"\"placaOficial\"     : \"1\",\n" + 
				"\"numInfraccion\"   : \"1\",\n" + 
				"\"fechaHoraEvento\"   : \"20/09/2018 16:09:39\"\n" + 
				"}";
	}
	
}
