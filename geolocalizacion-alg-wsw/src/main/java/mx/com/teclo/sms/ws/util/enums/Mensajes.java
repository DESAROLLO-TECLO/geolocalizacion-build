package mx.com.teclo.sms.ws.util.enums;

public enum Mensajes {

	MSJ_SUCCESS("Resultado encontrado."), 
	MSJ_NOT_DATA_FOUND("No se encontraron resultados."), 
	MSJ_ERROR_SERVIDOR("Error en el servidor."), 
	MSJ_BAD_REQUEST("El parámetro requerido es nulo."),
	MSJ_CREATE("Registro almacenado correctamente."),
	MSJ_CREATE_LIST("Registros almacenados correctamente."),
	MSJ_WARNING_OBJECT_NOT_FOUND(" No se encontró el código para el o los catálogo(s): "),
	MSJ_NOT_ENOUGH_DATA("No se cuenta con información necesaria para persistir uno o varios registros.");

	private String mensaje;

	private Mensajes(String mensaje) {
		this.mensaje = mensaje;

	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	static Mensajes getMensajeTipo(String x) {
		for (Mensajes currentType : Mensajes.values()) {
			if (x.equals(currentType.getMensaje())) {
				return currentType;
			}
		}
		return null;
	}
}
