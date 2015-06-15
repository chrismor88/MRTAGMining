package writer_text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

public class TAGMiningFileWriter {
	
	private final static String PATH = "/home/roberto/Scrivania/TAGMining_output/";
	private final static String PATH1 = PATH+"output1.txt";
	private final static String PATH2 = PATH+"output2.txt";
	private final static String PATH3 = PATH+"output3.txt";
	

	public static void writeOutput1(String warcTrecID,String stringaDaRimpiazzare, String tag){
		

		try {
			File file = new File(PATH1);

			if (file.exists()){
				try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
					String line = null;
					/*while ((line = reader.readLine()) != null) {
						System.out.println(line);
					}*/
				} catch (IOException x) {
					System.err.format("IOException: %s%n", x);
				}

				PrintWriter pw = new PrintWriter(new FileWriter(file, true));
				pw.write(warcTrecID+"\t\t\t"+stringaDaRimpiazzare+"\t\t\t"+tag+"\n");
				pw.flush();
				pw.close();
			}

			else{
				
					PrintWriter pw = new PrintWriter(file);
					pw.write("TRACK-ID\t\tSTRINGA DA SOSTITUIRE\t\t\tTAG\n");
					pw.write(warcTrecID+"\t\t"+stringaDaRimpiazzare+"\t\t\t"+tag+"\n");
					pw.flush();
					pw.close();
					
					
				
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}




	}

	public static void writeOutput2(String warcTrecID, String phrase) {
	

		try {
			File file = new File(PATH2);

			if (file.exists()){
				try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
					String line = null;
					/*while ((line = reader.readLine()) != null) {
						System.out.println(line);
					}*/
				} catch (IOException x) {
					System.err.format("IOException: %s%n", x);
				}

				PrintWriter pw = new PrintWriter(new FileWriter(file, true));
				pw.write(warcTrecID+"\t\t"+phrase+"\n");
				pw.flush();
				pw.close();
			}

			else{
				
					PrintWriter pw = new PrintWriter(file);
					pw.write("TRACK-ID\t\tSTRINGA DA SOSTITUIRE\n");
					pw.write(warcTrecID+"\t\t"+phrase+"\n");
					pw.flush();
					pw.close();
				
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void writeOutput3(String warcTrecID, String phraseChanged) {
		

		try {
			File file = new File(PATH3);

			if (file.exists()){
				try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
					String line = null;
					/*while ((line = reader.readLine()) != null) {
						System.out.println(line);
					}*/
				} catch (IOException x) {
					System.err.format("IOException: %s%n", x);
				}

				PrintWriter pw = new PrintWriter(new FileWriter(file, true));
				pw.write(warcTrecID+"\t\t"+phraseChanged+"\n");
				pw.flush();
				pw.close();
			}

			else{
				
					PrintWriter pw = new PrintWriter(file);
					pw.write("TRACK-ID\t\tSTRINGA DA SOSTITUIRE\n");
					pw.write(warcTrecID+"\t\t"+phraseChanged+"\n");
					pw.flush();
					pw.close();
				
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
