package Editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ReadObjectsFile {
	public static int readObjectsCount(String file){
		System.out.print("Считаем количество объектов... ");
		int lines = 0;
		File objectsFile = new File(file);
        if(objectsFile.exists()){
        	lines = (int) (objectsFile.length()/28);
        }
        else System.out.println("Файла нет!");
        System.out.println(lines);
	    return lines;
	}
	public static int[][] readObjects(String file, int count) {
		System.out.print("Читаем список объектов...");
		int[][] objects = new int[200000][28];

		try {
			byte[] buf = Files.readAllBytes(Paths.get(file));
			int n = 0;
			for (int j = 0; j < count; j++) {
				for (int k = 0; k < 28; k++) {
					objects[j][k] = buf[n] & 0xff;
					n++;
				}
			}
		} catch (IOException z) {
			z.printStackTrace();
			}
		System.out.println("OK");
		return objects;
	}
}