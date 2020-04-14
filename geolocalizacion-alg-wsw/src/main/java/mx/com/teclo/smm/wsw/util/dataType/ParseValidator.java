package mx.com.teclo.smm.wsw.util.dataType;

import java.text.SimpleDateFormat;

public class ParseValidator {

	/**
	 * Método que valida si el valor es explicitamente letras y números
	 * @param valor
	 * @author jorgeluis
	 */
	public boolean checkAlphanumeric(String valor){	
		return valor.matches("^[A-Za-z0-9 ]+$");
	}
	
	/**
	 * Método que valida si el valor es explicitamente letras
	 * 
	 * @param valor
	 * @author jorgeluis
	 */
	public boolean checkString(String valor){	
		return valor.matches("^[A-Za-z]+$");
	}
	
	/**
	 * Método que valida si el valor es un decimal 
	 * @param valor
	 * @author jorgeluis
	 */
	public boolean checkDouble(String valor){	
		return valor.matches("[+-]?\\d+\\.?(\\d+)?");
	}
	
	/**
	 * Método que valida si el valor es un número Long 
	 * @param valor
	 * @author jorgeluis
	 */
	public boolean checkLong(String valor){	
		return valor.matches("^-?\\d{1,19}$");
	}
	
	/**
	 * Método que valida si el la fecha es valida y cumple
	 * con el formato especificado en el parámetro 
	 * @param valor
	 * @param format
	 * @author jorgeluis
	 */
	
	 public boolean checkDate(String valor, String format){
        if(valor == null || format == null)
            return false;
        try{
            SimpleDateFormat sd = new SimpleDateFormat(format);
            sd.parse(valor);
            return true;
        }catch(Exception ex){
            return false;
        }
	 } 
}
