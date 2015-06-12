package regex;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

public class TestHtmlTagging {

	public static void main(String[] args) throws IOException {
		
		final String REGEX_URL = "<(A|a)\\s*(\\w|\\s|=|\"|:|\\/|\\.|-|\\?|@|_|&|%)*>(.)*<\\/(a|A)>";
		final  String TAG_URL = " #URL ";
		
		FileInputStream text = new FileInputStream("util/html5.txt");
		StringBuilder builder = new StringBuilder();
		int ch;
		while((ch = text.read()) != -1){
		    builder.append((char)ch);
		}
		String htmlString = builder.toString();
		
		
		
		Pattern patternURL = Pattern.compile(REGEX_URL);
		Matcher matcherURL = patternURL.matcher(htmlString);
		String HTMLtaggato = matcherURL.replaceAll(TAG_URL);
		
		System.out.println(htmlString);
		System.out.println("--------------------------------------");
		System.out.println("--------------------------------------");
		System.out.println(HTMLtaggato);
		System.out.println();
		
		String textExtracted = getHTMLBody(HTMLtaggato);
		
		System.out.println("=========== TESTO ESTRATTO ==============");
		System.out.println(textExtracted);
		System.out.println("=========================================");
		System.out.println();
		
		final String REGEX_CLEAN_TEXT = "<(\\w)*|(\\/)(\\w)*>";
		
		Pattern patternCleaner = Pattern.compile(REGEX_CLEAN_TEXT);
		Matcher matcherCleaner = patternCleaner.matcher(textExtracted);
		String testoPulito = matcherCleaner.replaceAll("");
		
		System.out.println("=========== TESTO ESTRATTO PULITO ==============");
		System.out.println(testoPulito);
		System.out.println("=========================================");
		
		
	}
	
	
	private static String getHTMLBody(String HTMLContent){
		String aux="";
		try {
			aux= Jsoup.parse(HTMLContent).body().text();

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return aux;
	}

}
