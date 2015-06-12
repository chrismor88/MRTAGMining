package clean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CleanerPhrase {

	private final static String REGEX_CLEAN_TEXT = "<(\\w)*|(\\/)(\\w)*>";
	
	
	public CleanerPhrase() {
		// TODO Auto-generated constructor stub
	}

	public static String deleteWastedHTML(String text){
		Pattern patternPHONE_NUMBER = Pattern.compile(REGEX_CLEAN_TEXT);
		Matcher matcherPHONE_NUMBER = patternPHONE_NUMBER.matcher(text);
		String cleanedText = matcherPHONE_NUMBER.replaceAll("");
		
		return cleanedText;	
	}
	
	
}
