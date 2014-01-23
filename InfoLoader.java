import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;


public class InfoLoader {

	public InfoLoader(){
		
	}
	
	public static String[] save(String pfad, String[] content) throws IOException{
		//CHECK IF DIRECTORY EXISTS
		File dir = new File(pfad);
		if(dir.exists()==false){
			dir.mkdir();
		}
		
		char lastChar = pfad.charAt(pfad.length()-1);
		if(lastChar=='\\' || lastChar=='/')		pfad = pfad+"info.txt";
		else	pfad = pfad+"/info.txt";
		
	System.out.println("InfoLoader:"+pfad);
		File infoFile = new File(pfad);
		String[] loadedData;
		
		//INFO FILE DOESNT EXIST --> SO CREATE IT
		if(infoFile.exists()==false){
			infoFile.createNewFile();
			PrintWriter writer = new PrintWriter(pfad, "UTF-8");
			for(String s : content){
				writer.println(s);
			}
			writer.close();
			return content;
		}else{
		
		//LOAD THE FILE
			FileReader fileReader = new FileReader(infoFile);
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	        ArrayList<String> lines = new ArrayList<String>();
	        String line = null;
	        
	        while ((line = bufferedReader.readLine()) != null) {
	            lines.add(line);
	        }
	        bufferedReader.close();
	        loadedData = lines.toArray(new String[lines.size()]);
		}
		
		//IF THERE IS A LINE MISSING
		if(content.length != loadedData.length){
			String[] help = new String[content.length];
			for(int i=0;i<loadedData.length;i++){
				help[i] = loadedData[i];
			}
			for(int i=loadedData.length;i<help.length;i++){
				help[i] = "";
			}
			loadedData = help;
		}
		
		//FILL IN NEW DATA
		for(int i = 0;i<content.length;i++){
			if(content[i].equals(""))	content[i] = loadedData[i];
		}
		PrintWriter writer = new PrintWriter(pfad, "UTF-8");
		for(String s:content){
			writer.println(s);
		}
		writer.close();
		
		return content;
	}
	
	public static String[] load(String pfad) throws IOException{
		return save(pfad, new String[] {"", "", "", ""});
	}
	
	public static boolean saveZustand(String pfad, String user, boolean vorlesungeBearbeitet) throws IOException{
		
		//LOAD DATA SO FAR
		String[] info = load(pfad);
		        
				String userString;
				if(vorlesungeBearbeitet){
				//ADD USER
					if(info[3].length()<1){
					//NOCH KEINE USER BEARBEITUNG
						userString = user;
					}else{
					//SCHON ANDERE USER --> DRANHÄNGEN
						userString = info[3] + "," + user;
					}
				}else{
				//DELETE USER
					String[] users = info[3].split(",");
					if(users.length <= 1){
						userString = "-";					
						//WORKAROUND NOT QUITE HAPPY WITH IT
						//THE PROBLEM IS WITH THE SAVE/LOAD FUNCTION SINCE IT WORKS FOR BOTH
					}else{
						userString = deleteUser(users, user);
					}
				}
				info[3] = userString;
				System.out.println(Arrays.toString(info));
				save(pfad, info);
		
		return true;
	}
	
	private static String deleteUser(String[] users, String user){
		String[] newUsers = new String[users.length-1];
		newUsers[0] = "";
		int c=0;
		for(int i=0;i<users.length-1;i++){
			if(users[i].equals(user)){
				i++;
				newUsers[c] = users[i];
				c++;
			}else{
				newUsers[c] = users[i];
				c++;
			}
		}
		
		//MAKE STRING FROM STRING[]
		String returnString=newUsers[0];
		for(int i=1;i<newUsers.length;i++){
			returnString += "," + newUsers[i];
		}
		
		return returnString;
	}
	
}
