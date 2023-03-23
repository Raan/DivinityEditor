package Editor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ReadWorldFile {
	public static int[][][] readWorld(String file) {
		int[][][] TileArray = new int[1024][512][136];
	    System.out.print("Читаем World...");
		int tileLine = 0;
		int tileLine2 = 0;
		int tileColumn = 0;
		int objects = 0;
		int bufferSize = 0;
		ArrayList<Integer> buf = new ArrayList<Integer>();
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
		// Читаем файл побайтно в массив buf
		try (FileInputStream fin = new FileInputStream(file)) {
			int i = -1;
			while ((i = fin.read()) != -1) {
				buf.add(i);
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		bufferSize = buf.size() - 1; // Размер масссива buf с нуля
	//-------------------------------------------------------------------------------------------
		//Читаем плитки
		for (int i = 4096; i < bufferSize-3; i++) {
			i+=1024;
			for (int a = 0; a < 512; a++) {
				int b;
				switch (buf.get(i+6)) {
					case 0:	for(b = 0; b < 16; b++) {TileArray[tileLine][tileColumn][tileLine2] = buf.get(i); tileLine2++; i++;} break;
					case 1:	{for(b = 0; b < 24; b++) {TileArray[tileLine][tileColumn][tileLine2] = buf.get(i); tileLine2++; i++;} objects+=1; break;}
					case 2:	{for(b = 0; b < 32; b++) {TileArray[tileLine][tileColumn][tileLine2] = buf.get(i); tileLine2++; i++;} objects+=2; break;}
					case 3:	{for(b = 0; b < 40; b++) {TileArray[tileLine][tileColumn][tileLine2] = buf.get(i); tileLine2++; i++;} objects+=3; break;}
					case 4:	{for(b = 0; b < 48; b++) {TileArray[tileLine][tileColumn][tileLine2] = buf.get(i); tileLine2++; i++;} objects+=4; break;}
					case 5:	{for(b = 0; b < 56; b++) {TileArray[tileLine][tileColumn][tileLine2] = buf.get(i); tileLine2++; i++;} objects+=5; break;}
					case 6:	{for(b = 0; b < 64; b++) {TileArray[tileLine][tileColumn][tileLine2] = buf.get(i); tileLine2++; i++;} objects+=6; break;}
					case 7:	{for(b = 0; b < 72; b++) {TileArray[tileLine][tileColumn][tileLine2] = buf.get(i); tileLine2++; i++;} objects+=7; break;}
					case 8:	{for(b = 0; b < 80; b++) {TileArray[tileLine][tileColumn][tileLine2] = buf.get(i); tileLine2++; i++;} objects+=8; break;}
					case 9:	{for(b = 0; b < 88; b++) {TileArray[tileLine][tileColumn][tileLine2] = buf.get(i); tileLine2++; i++;} objects+=9; break;}
					case 10:{for(b = 0; b < 96; b++) {TileArray[tileLine][tileColumn][tileLine2] = buf.get(i); tileLine2++; i++;} objects+=10; break;}
					case 11:{for(b = 0; b < 104; b++) {TileArray[tileLine][tileColumn][tileLine2] = buf.get(i); tileLine2++; i++;} objects+=11; break;}
					case 12:{for(b = 0; b < 112; b++) {TileArray[tileLine][tileColumn][tileLine2] = buf.get(i); tileLine2++; i++;} objects+=12; break;}
					case 13:{for(b = 0; b < 120; b++) {TileArray[tileLine][tileColumn][tileLine2] = buf.get(i); tileLine2++; i++;} objects+=13; break;}
					case 14:{for(b = 0; b < 128; b++) {TileArray[tileLine][tileColumn][tileLine2] = buf.get(i); tileLine2++; i++;} objects+=14; break;}
					case 15:{for(b = 0; b < 136; b++) {TileArray[tileLine][tileColumn][tileLine2] = buf.get(i); tileLine2++; i++;} objects+=15; break;}
					default: break;
				}
				tileColumn++;
				tileLine2 = 0;
			}
			i--;
			tileLine++;
			tileColumn = 0;
		}
		System.out.println("OK");
		System.out.println("Objects: " + objects);
	    return TileArray;
	}
}
	
