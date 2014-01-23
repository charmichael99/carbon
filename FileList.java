import java.io.File;


public class FileList {
	
	public boolean pres = false, live = false, pdf = false, audio = false, text= false, info = false;
	public String presS = "", liveS = "", pdfS = "", audioS = "", textS= "", infoS = "";
	
	public FileList(String pfad){
		File home = new File(pfad);
	
		String[] subdir = home.list();
		
		for (String file : subdir){
			String prefix = file.split("-")[0];
			System.out.println("FileList.java: "+pfad+"/"+file);			
			switch (prefix){
			case "pres":	pres = true;presS = pfad+"/"+file; break;
			case "live":	live = true;liveS = pfad+"/"+file; break;
			case "pdf":		pdf = true;pdfS = pfad+"/"+file; break;
			case "audio":	audio = true;audioS = pfad+"/"+file; break;
			case "text":	text = true;textS = pfad+"/"+file; break;
			case "info.txt":	info = true;infoS = pfad+"/"+file; break;
			}
		}
		
	}
	
}
