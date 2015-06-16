package writer_text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

public class TAGMiningFileWriter {
	
	private final static String PATH = "util/TAGMining_output/";
	private final static String PATH1 = PATH+"output1.txt";
	private final static String PATH2 = PATH+"output2.txt";
	private final static String PATH3 = PATH+"output3.txt";
	
/*
	public static void writeOutput1(String warcTrecID,String stringaDaRimpiazzare, String tag){

		PrintWriter out=null;
		
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(PATH1, true)));
			out.println(warcTrecID+"\t\t"+stringaDaRimpiazzare+"\t\t"+tag);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			out.close();
		}
		
	}
*/
	public static void writeOutput2(String warcTrecID, String phrase) {
		PrintWriter out=null;
		
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(PATH2, true)));
			out.println(warcTrecID+"\t\t"+phrase);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			out.close();
		}
	}

	public static void writeOutput3(String warcTrecID, String phraseChanged) {
		PrintWriter out=null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(PATH3, true)));
			out.println(warcTrecID+"\t\t"+phraseChanged);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			out.close();
		}
	}

}
