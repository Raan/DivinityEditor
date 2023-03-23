package Editor;

import java.io.FileOutputStream;
import java.io.IOException;

public class WriteWorldFile {
	public static void writeWorld(String outFile, int[][][] outArray) {
		System.out.print("Записываем World...");
		int i= 0;
		int buffer  = 0;
		int bufStart = 4096;
		int bufStart1 = 0;
		String bufferStr = "";
		String bufferStr2 = "";
		String bufferStr3 = "";
		String bufferStr4 = "";
		String bufferStr5 = "";
	    try(FileOutputStream fos=new FileOutputStream(outFile)){// Записываем стартовые данные
	    	fos.write(0);
	    	fos.write(16);
	    	fos.write(0);
	    	fos.write(0);
	        for(int j = 0; j < 1023; j++) {
	        	for(int k = 0; k < 512; k++) {
	        		bufStart1 += outArray[j][k][6];
	        	}
	        	if (bufStart1 == 0) bufStart += 9216;
	        	else bufStart += bufStart1*8+9216; //232016
	        	bufStart1 = 0;
	        	bufferStr = Integer.toHexString(bufStart);//38A50
	            if (bufferStr.length() < 2)   bufferStr = "0000000" +bufferStr;
	            if (bufferStr.length() < 3)   bufferStr = "000000" +bufferStr;
	            if (bufferStr.length() < 4)   bufferStr = "00000" +bufferStr;
	            if (bufferStr.length() < 5)   bufferStr = "0000" +bufferStr;
	            if (bufferStr.length() < 6)   bufferStr = "000" +bufferStr;
	            if (bufferStr.length() < 7)   bufferStr = "00" +bufferStr;
	            if (bufferStr.length() < 8)   bufferStr = "0" +bufferStr;//00038A50
	            bufferStr2 = "" + bufferStr.charAt(6);	//5
	            bufferStr2 += bufferStr.charAt(7);		//0
	            bufferStr3 = "" + bufferStr.charAt(4);	//8
	            bufferStr3 += bufferStr.charAt(5);		//A
	            bufferStr4 = "" + bufferStr.charAt(2);	//0
	            bufferStr4 += bufferStr.charAt(3);		//3
	            bufferStr5 = "" + bufferStr.charAt(0);	//0
	            bufferStr5 += bufferStr.charAt(1);		//0
	            fos.write(Integer.parseInt(bufferStr2, 16));
	            fos.write(Integer.parseInt(bufferStr3, 16));
	            fos.write(Integer.parseInt(bufferStr4, 16));
	            fos.write(Integer.parseInt(bufferStr5, 16));
	        }
	        for (i = 0; i < 1024; i++) {
	        	buffer  = 0;
	        	fos.write(0);
	        	fos.write(0);
	        	for (int j = 0; j < 1022; j+=2) { // Записываем данные о плитках
	        		buffer = buffer + outArray[i][j/2][6]*8+16;//10
	        		bufferStr = Integer.toHexString(buffer); //0010
	        		if (bufferStr.length() < 2)   bufferStr = "000" +bufferStr;
	        		if (bufferStr.length() < 3)   bufferStr = "00" +bufferStr;
	        		if (bufferStr.length() < 4)   bufferStr = "0" +bufferStr;
	        		bufferStr2 = "" + bufferStr.charAt(2);
	        		bufferStr2 += bufferStr.charAt(3);
	        		bufferStr3 = "" + bufferStr.charAt(0);
	        		bufferStr3 += bufferStr.charAt(1);
	        		fos.write(Integer.parseInt(bufferStr2, 16));//10
	        		fos.write(Integer.parseInt(bufferStr3, 16));//00
	        		bufferStr = "";
	        	}
	        	for (int j = 0; j < 512; j++) { // Записываем плитки
	        		for(int k = 0; k < outArray[i][j][6]*8+16; k++) fos.write(outArray[i][j][k]);
	        	}
	        }
	        fos.write(103); // Записываем конец файла
	        fos.write(0);        	
	        fos.write(0);
	        fos.write(0);
	    }
	    catch(IOException ex){System.out.println(ex.getMessage());}
	    System.out.println("OK");
	}
}
