package mapreduce;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import regex.TAGComponent;

public class prova {
	public static void main(String[] args) throws IOException {
		FileReader f;
	    //f=new FileReader("/media/roberto/ROB/Università (Roma Tre)/Big Data/ratebeer.txt");
		f=new FileReader("util/phrases.txt");

	    BufferedReader b;
	    b=new BufferedReader(f);

	    String s;

	    while(true) {
	      s=b.readLine();
	      if(s==null)
	        break;
	      //System.out.println("frase: "+s);
	      String[] spittedLine = s.split("#");
	      String trecID = spittedLine[0];
	      String singlePhrase = spittedLine[1];
	      String TaggedPhase = TAGComponent.tagPhrase("", singlePhrase);
	      if (!singlePhrase.equals(TaggedPhase)){
				System.out.println("singlePhrase: "+singlePhrase);
				System.out.println("TaggedPhase: "+TaggedPhase);
				/*List<String> URLS = TAGComponent.tagSingleURL(trecID, singlePhrase, TaggedPhase);
				for (int i = 0; i < URLS.size()-1; i++) {
					System.out.println("singolo url: "+trecID+"#"+URLS.get(i));
					
				}*/
				
	    	  	System.out.println("Frase taggata: "+TaggedPhase);
				
			}
	    }
	}
}
