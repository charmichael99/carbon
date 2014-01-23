import java.io.File;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;




public class createOrdner {
	
	public static boolean createTree(String pfad, String Vorlesung, int[] start, int[] end){
		if(new File(pfad).exists()){	
			//CREATE NEW DIRCTORY
			pfad += Vorlesung;
		System.out.println(pfad);
			File ordner = new File(pfad);
			if(ordner.exists()==false){
				ordner.mkdir();
			}
			pfad += "/Material";
			ordner = new File(pfad);
			if(ordner.exists()==false){
				ordner.mkdir();
			}
			
			//ADD DIRECTRYS
			LocalDate startDate = new LocalDate(start[2],start[1],start[0]);
			LocalDate endDate = new LocalDate(end[2],end[1],end[0]);
			//DateTimeFormatter fmt = DateTimeFormat.forPattern("dd.MM.yyyy");
			DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy.MM.dd");
			
			File f;
			if(endDate.isAfter(startDate)){
				while(startDate.isBefore(endDate)){
					startDate = startDate.plusDays(7);
					String s = fmt.print(startDate);
					
					f = new File(pfad+"/"+s);
					if(f.exists()==false){
						f.mkdir();
					}
				}
			}
			return true;
		}else{
			return false;
		}
	}
	
}
