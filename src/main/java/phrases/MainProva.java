package phrases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class MainProva {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		String testo ="As they rounded a bend in the path that ran beside the river, Lara recognized the silhouette of a fig tree atop a nearby hill. The weather was hot and the days were long. The fig tree was in full leaf, but not yet bearing fruit.Soon Lara spotted other landmarks—an outcropping of limestone beside the path that had a silhouette like a man’s face, a marshy spot beside the river where the waterfowl were easily startled, a tall tree that looked like a man with his arms upraised. They were drawing near to the place where there was an island in the river. The island was a good spot to make camp. They would sleep on the island tonight.";
		String paragraph = "The Apache OpenNLP library is a machine learning based toolkit for processing of natural language text. It includes a sentence detector, a tokenizer, a name finder, a parts-of-speech (POS) tagger, a chunker, and a parser. It has very good APIs that can be easily integrated with a Java program. However, the documentation contains unupdated information.In this tutorial, I will show you how to use Apache OpenNLP through a set of simple examples. ";
		
		String [] result = SentenceDetector.sentenceDetect(paragraph);
//		1NumberDetector.findNumber();
		for(String s : result){
			System.out.println(s);
		}
	}
}
