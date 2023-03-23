package Other;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Story {
	public static void StoryRead (String inpFile, String outFile) {
	    System.out.print("Читаем Story...");
		int bufferSize = 0;
		ArrayList<Integer> buf = new ArrayList<Integer>();
	//-------------------------------------------------------------------------------------------		
		// Читаем файл побайтно в массив buf
		try (FileInputStream fin = new FileInputStream(inpFile)) {
			int i = -1;
			while ((i = fin.read()) != -1) {
				buf.add(i);
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		bufferSize = buf.size() - 1; // Размер масссива buf с нуля
	//-------------------------------------------------------------------------------------------
		System.out.println("OK");
		System.out.print("Записываем Story...");
		try(FileOutputStream fos=new FileOutputStream(outFile)){// Записываем стартовые данные
	        for (int i = 0; i <= bufferSize; i++)
	        fos.write(buf.get(i)^173);
	    }
	    catch(IOException ex){System.out.println(ex.getMessage());}
	    System.out.println("OK");
		
	}
	public static void main (String[] arg) {
		StoryRead("F:\\SteamLibrary\\steamapps\\common\\divine_divinity\\savegames\\Test_10\\story.000","F:\\SteamLibrary\\steamapps\\common\\divine_divinity\\savegames\\Test_10\\story.100");
	}
}
