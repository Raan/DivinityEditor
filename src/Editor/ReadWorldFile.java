package Editor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadWorldFile {
	public static int[][][] readWorld(String file) {
		int[][][] TileArray = new int[1024][512][136];
	    System.out.print("Читаем World...");
		int tileLine = 0;
		int tileLine2 = 0;
		int tileColumn = 0;
		int objects = 0;
	//-------------------------------------------------------------------------------------------		
		// Заполняем массив TileArray
		for(int i=0; i < 1024; i++) {
			for(int j=0; j < 512; j++){
				for(int k=0; k < 136; k++){
					TileArray[i][j][k] = 255;
				}
			}
		}
	//-------------------------------------------------------------------------------------------		
		try {
			byte[] buf = Files.readAllBytes(Paths.get(file));
			for (int i = 4096; i < buf.length-4; i++) {
				i+=1024;
				for (int a = 0; a < 512; a++) {
					int objCountb = buf[i+6];
					for (int j = 0; j < 16 + objCountb*8; j++) {
						TileArray[tileLine][tileColumn][tileLine2] = buf[i] & 0xff;
						tileLine2++;
						i++;
					}
					objects+= objCountb;
					tileColumn++;
					tileLine2 = 0;
				}
				i--;
				tileLine++;
				tileColumn = 0;
			}
		} catch (IOException z) {
			z.printStackTrace();
			}
	//-------------------------------------------------------------------------------------------	
		System.out.println("OK");
		System.out.println("Objects: " + objects);
	    return TileArray;
	}
}
	
