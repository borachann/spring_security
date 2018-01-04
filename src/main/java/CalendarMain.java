import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarMain {

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd");
		Date startDate = simpleDateFormat.parse("2015-01-01");
		Date endDate = simpleDateFormat.parse("2015-12-31");
		Calendar calStartDate = Calendar.getInstance();
		calStartDate.setTime(startDate);
		
		Calendar calEndDate = Calendar.getInstance();
		calEndDate.setTime(endDate);
		
		System.out.println("START DATE="+ calStartDate.get(Calendar.MONTH));
		System.out.println("END DATE="+ calEndDate.get(Calendar.MONTH));

		Calendar calendar = calStartDate;
		String[] months = new String[]{"Jan","Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		StringBuilder sb = new StringBuilder();
		for(int i=calStartDate.get(Calendar.MONTH) ; i<=calEndDate.get(Calendar.MONTH) ; i++){
			calendar.add(Calendar.MONTH, i);
			System.out.println(months[i]);
			sb.append(months[i] + " INTEGER[],");
		}
		System.out.println(sb.toString().substring(0, sb.toString().lastIndexOf(",")));
		
	}
}
