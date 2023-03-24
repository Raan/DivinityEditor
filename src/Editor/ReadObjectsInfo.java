package Editor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadObjectsInfo {
	public static int[][] readObjectsInfo(String file) {
		System.out.print("Читаем описание объектов...");
		String[] objName = new String[7208];
		int[][] objSize = new int[7208][7];
		String b;
		try {
			byte[] buf = Files.readAllBytes(Paths.get(file));
			int k = 0;
			for (int i = 0; i < 7208; i++) {
				objName[i] = "";
				while (buf[k] != 9) {
					char c = 0;
					c += buf[k];
					objName[i] += c;
					k++;
				}
				k++;
				
				for (int j = 0; j < 6; j++) {
					b = "";
					while (buf[k] != 9) {
						b += (char) (buf[k] & 0xFF);
						k++;
					}
					k++;
					objSize[i][j] = Integer.parseInt(b);
				}
				
				b = "";
				while (buf[k] != 13 ) {
					b += (char) (buf[k] & 0xFF);
					k++;
				}
				k++;
				objSize[i][6] = Integer.parseInt(b);
				
				k++;
			}
			} catch (IOException z) {
			z.printStackTrace();
			}
		System.out.println("OK");
		return objSize;
	}
//-------------------------------------------------------------------------------------------
	public static String[] readObjectsName(String file) {
		System.out.print("Читаем имена объектов...");
		System.out.print("Читаем описание объектов...");
		String[] objName = new String[7208];
		int[][] objSize = new int[7208][7];
		String b;
		try {
			byte[] buf = Files.readAllBytes(Paths.get(file));
			int k = 0;
			for (int i = 0; i < 7208; i++) {
				objName[i] = "";
				while (buf[k] != 9) {
					char c = 0;
					c += buf[k];
					objName[i] += c;
					k++;
				}
				k++;
				
				for (int j = 0; j < 6; j++) {
					b = "";
					while (buf[k] != 9) {
						b += (char) (buf[k] & 0xFF);
						k++;
					}
					k++;
					objSize[i][j] = Integer.parseInt(b);
				}
				
				b = "";
				while (buf[k] != 13 ) {
					b += (char) (buf[k] & 0xFF);
					k++;
				}
				k++;
				objSize[i][6] = Integer.parseInt(b);
				
				k++;
			}
			} catch (IOException z) {
			z.printStackTrace();
			}
		System.out.println("OK");
		return objName;
	}
}
