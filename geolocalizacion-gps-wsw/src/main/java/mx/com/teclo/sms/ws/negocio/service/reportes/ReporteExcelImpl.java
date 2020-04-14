package mx.com.teclo.sms.ws.negocio.service.reportes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.EventoGpsReporteGeneralVO;
import mx.com.teclo.sms.ws.util.enums.Reportes;
import mx.com.teclomexicana.generaexcel.reporteexcel.PeticionReporteBOImpl;
import mx.com.teclomexicana.generaexcel.vo.PeticioReporteVO;
import mx.com.teclomexicana.generaexcel.vo.PropiedadesReporte;

@Service
public class ReporteExcelImpl implements ReporteExcel {
	
	private ByteArrayOutputStream byteArrayOutputStream = null;
	
	/**
	 * {@inheritDoc} 
	 */
	@Override
	public Map<String, Object> reporteGeneral(List<EventoGpsReporteGeneralVO> listaRegistroVO) {
		PeticioReporteVO peticionReporteVO = new PeticioReporteVO();
		PropiedadesReporte propiedadesReporte = new PropiedadesReporte();
		propiedadesReporte.setTituloExcel(Reportes.REPORTE_GENERAL.getReporte());
		propiedadesReporte.setNombreReporte(Reportes.REPORTE_GENERAL.getReporte());
		propiedadesReporte.setExtencionArchvio(".xls");
		PeticionReporteBOImpl peticionReporteBOImpl = new PeticionReporteBOImpl();

		List<String> titulos = new ArrayList<String>();
		titulos.add("PLACA");
		titulos.add("EMPLEADO");
		titulos.add("DISPOSITIVO NUM SERIE");
		titulos.add("DISPOSITIVO NUM SIM");
		titulos.add("DISPOSITIVO NUM IP");
		titulos.add("TIPO DE EVENTO");
		titulos.add("FECHA DE EVENTO");
		titulos.add("DIRECCION");
		titulos.add("INFRACCIÓN ");
		
		
		List<Object> encabezadoTitulo = new ArrayList<Object>();
		encabezadoTitulo.add(titulos);
		
		List<Object> hojas = new ArrayList<Object>();
		List<Object> hoja1 = new ArrayList<Object>();
		List<String> listaContenido1;
		DateFormat formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
		for(EventoGpsReporteGeneralVO record : listaRegistroVO) {
			listaContenido1 = new ArrayList<String>();
			listaContenido1.add(record.getEmpleado() == null ? " - " : record.getEmpleado().getEmpPlaca());
			listaContenido1.add(record.getEmpleado() == null ? " - " : record.getEmpleado().getEmpNombre() + " " + record.getEmpleado().getEmpApePaterno() + " " + record.getEmpleado().getEmpApeMaterno());
			listaContenido1.add(record.getDispositivoMovil().getNumSerie());
			listaContenido1.add(record.getDispositivoMovil().getNumSim());
			listaContenido1.add(record.getDispositivoMovil().getNumIp());
			listaContenido1.add(record.getTipoEvento().getNombreTipoEvento());
			listaContenido1.add(formatter.format(record.getFechaHoraEvento().getTime()));
			listaContenido1.add(record.getDireccion());
			listaContenido1.add(record.getNumInfraccion());
			
			hoja1.add(listaContenido1);
		}
		hojas.add(hoja1);

		peticionReporteVO.setPropiedadesReporte(propiedadesReporte);
		peticionReporteVO.setEncabezado(encabezadoTitulo);
		peticionReporteVO.setContenido(hojas);
		
		try {
			byteArrayOutputStream = peticionReporteBOImpl.generaReporteExcel(peticionReporteVO);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    	final byte[] bytes = byteArrayOutputStream.toByteArray();
    	
    	String filename = "Reporte_General.xls";
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
        headers.add("Content-Disposition", "attachment; filename=" + filename);
    	headers.add("filename", filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        headers.setContentLength(bytes.length);
        
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("headers", headers);
        response.put("bytes", bytes);
        
		return response;
	}
}