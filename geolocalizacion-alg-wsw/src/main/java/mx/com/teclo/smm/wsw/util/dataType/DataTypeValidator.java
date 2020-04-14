package mx.com.teclo.smm.wsw.util.dataType;

import org.springframework.stereotype.Component;

@Component
public class DataTypeValidator extends ParseValidator{

	public Boolean validate(String datatype, String value, Boolean isRquired, String regExp, Integer min, Integer max, Boolean requireCheck) {
		
		// SI NO SE REQUIERE NINGUNA VALIDACIÓN RETORNA true
		if(!requireCheck)
			return true;
		
		// EL VALOR ES REQUERIDO PERO NO ES ENCONTRADO
		if(!required(value, isRquired))
			return false;
		
		// EL VALOR DEBE CONTAR CON UNA LONGITUS MÍNIMA Y MÁXIMA
		if(!minAndMax(value, min, max))
			return false;
		
		Boolean b = false;
		switch (datatype) {
		case "String":
			b = checkString(value);
			break;
		case "Alphanumeric":
			b = checkAlphanumeric(value);
			break;
		case "Double":
			b = checkDouble(value);
			break;
		case "Long":
			b = checkLong(value);
			break;
		case "Date":
			b = checkDate(value, regExp);
			break;
		default:
			break;
		}
		return b;
	}
	
	private static Boolean required(String value, Boolean isRquired) {
		if(isRquired && (value == null || value.equals("")))
			return false;
		return true;
	}
	
	private static Boolean minAndMax(String value, Integer min, Integer max) {
		if(value == null)
			return true;
		if(min == null && max == null)
			return true;
		if(value.length() >= min && value.length() <= max)
			return true;
		return false;
	}
	
}
