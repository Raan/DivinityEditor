package Editor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ReadObjectsInfo {
	public static int[][] readObjectsInfo(String file) {
		System.out.print("Читаем описание объектов...");
		String[] ObjectsName = new String[7208];
		int[][] ObjectsInfo = new int[7208][132];
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
		for (int i = 0; i < 7207; i++) {
			for (int j = 0; j < 32; j++) {
				ObjectsInfo[i][j] = buf.get(n);
				n++;
			}
			ObjectsName[i] = "";
			for (int j = 0; j < 16; j++) {
				char c = 0;
				c += buf.get(n);
				ObjectsName[i] += c;
				n++;
			}
			for (int j = 32; j < 132; j++) {
				ObjectsInfo[i][j] = buf.get(n);
				n++;
			}
		}
		System.out.println("OK");
		return ObjectsInfo;
	}
//-------------------------------------------------------------------------------------------
	public static String[] readObjectsName(String file) {
		System.out.print("Читаем имена объектов...");
		String[] ObjectsName = new String[7208];
		int[][] ObjectsInfo = new int[7208][132];
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
		for (int i = 0; i < 7207; i++) {
			for (int j = 0; j < 32; j++) {
				ObjectsInfo[i][j] = buf.get(n);
				n++;
			}
			ObjectsName[i] = "";
			for (int j = 0; j < 16; j++) {
				char c = 0;
				c += buf.get(n);
				ObjectsName[i] += c;
				n++;
			}
			for (int j = 32; j < 132; j++) {
				ObjectsInfo[i][j] = buf.get(n);
				n++;
			}
		}
		System.out.println("OK");
		return ObjectsName;
	}
}
