package Editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
		ArrayList<Integer> buf = new ArrayList<Integer>();			
		// Читаем файл побайтно в массив buf
		try (FileInputStream fin = new FileInputStream(file)) {
			int i = -1;
			while ((i = fin.read()) != -1) {
				buf.add(i);
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		// Разбираем массив на части
		int n = 0;
		for (int j = 0; j < count; j++) {
			for (int k = 0; k < 28; k++) {
				objects[j][k] = buf.get(n);
				n++;
			}
		}
		System.out.println("OK");
		return objects;
	}
}