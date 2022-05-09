package utils;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;

public class Save {
	 public static boolean save(String path,String content)  {
		 
		 File f = new File(path);
		 File f_parentfile=f.getParentFile();
		 if(!f_parentfile.exists()) {
			 f_parentfile.mkdirs();
		 }
		 
		 if(!f.exists()) {
			 try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 
		 
		 FileWriter fw=null;
		 try {
			 fw = new FileWriter(path);
			 fw.write(content);
			 fw.flush();
			 fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return true;
	 }
}
