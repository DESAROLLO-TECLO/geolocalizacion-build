package mx.com.teclo.geolocalizacionalgwsw;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mx.com.teclo.arquitectura.ortogonales.persistencia.configuracion.vo.ConfiguracionVO;
import mx.com.teclo.arquitectura.ortogonales.service.configuracion.ConfiguracionAplicacionService;
import mx.com.teclo.sms.GeolocalizacionGpsApplication;
import mx.com.teclomexicana.arquitectura.ortogonales.exception.NotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GeolocalizacionGpsApplication.class})
public class WebServiceAlgApplicationTests {

	@Autowired
	private ConfiguracionAplicacionService configuracionAplicacionService;
	
    @Value("${app.config.codigo}")
    private String codeApplication;
    
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void buscarMenuUsuario() throws NotFoundException {
			
		ConfiguracionVO configuracionVO = null;
		
		configuracionVO = configuracionAplicacionService.buscarConfiguracionXAplicacion(codeApplication);
		if (configuracionVO.equals(null)) {
			throw new NotFoundException("No se encontraron las configuraciones de la aplicación.");
		}	
	}
	
	@Test
	@Transactional
	public void testObtenerOficiosVencidos(){
	 
	}

}
