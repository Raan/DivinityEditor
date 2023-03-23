package Editor;

import java.io.FileOutputStream;
import java.io.IOException;

public class WriteObjectsFile {
	public static void writeObjects(String outFile, int[][] objects, int objectsCount) {
		System.out.print("Записываем Objects...");
	    try(FileOutputStream fos=new FileOutputStream(outFile)){
	        for(int j = 0; j < objectsCount; j++) {
	        	for(int k = 0; k < 28; k++) {
	        		fos.write(objects[j][k]);
	        	}
	        }
	    }
	    catch(IOException ex){System.out.println(ex.getMessage());}
	    System.out.println("OK");
	}
}
