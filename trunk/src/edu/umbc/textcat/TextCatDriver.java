/**
 * 
 */
package edu.umbc.textcat;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;



/**
 * @author audumbar
 *
 */
public class TextCatDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//createFP();
		printCategory();

	}
	public static void printCategory() {
		char[] data = new char[1024];
		int read;
		String s = "";
		try {
			FileReader fr = new FileReader(new File("sports-blog.txt"));
			while ((read = fr.read(data)) != -1) {
				s += new String(data, 0, read);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		TextCategorizer guesser = new TextCategorizer();         
		//guesser.setConfFile("textcat-inf/textcat.conf");
		//System.out.println(guesser.categorize(s));
	}
	public static void createFP() {
		FingerPrint fp = new FingerPrint();
        try {
			fp.create(new File("sports.txt"));
			fp.setCategory("sports");
			fp.save();
        }
        catch(Exception e) {
        }
	}

}
