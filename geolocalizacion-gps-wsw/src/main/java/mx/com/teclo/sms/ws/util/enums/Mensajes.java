package mx.com.teclo.sms.ws.util.enums;

public enum Mensajes {

	MSJ_SUCCESS("Resultado encontrado."),
	MSJ_NOT_DATA_FOUND("No se encontraron resultados."),
	MSJ_ERROR_SERVIDOR("Error en el servidor."),
	MSJ_BAD_REQUEST("El parametro requerido es nulo."),
	MSJ_CREATE("Registro Almacenado Correctamente."),
	MSJ_UPDATE("Registro Actualizado Correctamente."),
	MSJ_CREATE_LIST("Registros Almacenados Correctamente."),
	MSJ_WARNING_OBJECT_NOT_FOUND(" No se encontró el código parar el catálogo "),
	MSJ_DUPLICATE_OBJECT("Registro almacenado con los mismos valores anteriormente.");

	private String mensaje;

	private Mensajes(String mensaje) {
		this.mensaje = mensaje;

	}

	public String getProcesoId() {
		return mensaje;
	}

	public void setProcesoId(String mensaje) {
		this.mensaje = mensaje;
	}

	static Mensajes getArchivoTipo(String x) {
		for (Mensajes currentType : Mensajes.values()) {
			if (x.equals(currentType.getProcesoId())) {
				return currentType;
			}
		}
		return null;
	}
}
