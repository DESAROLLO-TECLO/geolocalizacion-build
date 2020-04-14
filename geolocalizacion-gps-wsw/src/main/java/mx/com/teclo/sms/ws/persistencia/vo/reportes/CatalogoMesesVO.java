package mx.com.teclo.sms.ws.persistencia.vo.reportes;

public class CatalogoMesesVO {
	
	private Integer monthId;
	
	private String monthName;
	
	private String firstDay;
	
	private String lastDay;
	
	
	public CatalogoMesesVO(Integer monthId, String monthName, String firstDay, String lastDay) {
		this.monthId = monthId;
		this.monthName = monthName;
		this.firstDay = firstDay;
		this.lastDay = lastDay;
	}

	public Integer getMonthId() {
		return monthId;
	}

	public void setMonthId(Integer monthId) {
		this.monthId = monthId;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public String getFirstDay() {
		return firstDay;
	}

	public void setFirstDay(String firstDay) {
		this.firstDay = firstDay;
	}

	public String getLastDay() {
		return lastDay;
	}

	public void setLastDay(String lastDay) {
		this.lastDay = lastDay;
	}
}
