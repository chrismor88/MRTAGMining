package regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.omg.IOP.TAG_MULTIPLE_COMPONENTS;

import writer_text.TAGMiningFileWriter;

public class TAGComponent {
	
	/* REGEX */

	//for format time hh:mm:ss, hh:mm, hh:mm am, hh:mm:ss pm ...........
	private final String time1 ="(([0-9]|0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(:[0-5][0-9])?(\\s*(am|AM|pm|PM))?)";

	//for format time xx h yy m  ,  xx h yy min  , xx h  yy m zz s, xx m yy s ............
	private final String time2 = "((\\d{1,2}\\s*h)?\\s*\\d{1,2}\\s*m(in)?\\s*(\\d{1,2}\\s*(s(ec)?)))";

	private final static String CURRENCY = "(USD|usd|EUR|euro|dollar(s)?|pound(s)?|GBP|yen|JPY|CHF|AUD|AFN|ALL|DZD|AOA|ARS|AMD|AWG|"+
			"AZN|BSD|BHD|BDT|BBD|BYR|BZD|BMD|BTN|BOB|BAM|BWP|BRL|BND|BGN|BIF|XOF|XAF|XPF|KHR|CAD|CVE|KYD|"+
			"CLP|CNY|COP|KMF|CDF|CRC|HRK|CUC|CUP|CYP|RUB|€|£|\\$)";

	
	private final String REGEX_ORD = "((\\d+(st|nd|rd|th)) | (\\d+°))";


	private final String MONTH = "(\\s*(January|january|jan(\\.)|Jan(\\.)?|JAN(\\.)?|February|february|Feb(\\.)?|FEB(\\.)?|feb(\\.)?|March|march|Mar(\\.)?|MAR(\\.)?|mar(\\.)?|April|april|Apr(\\.)?|APR(\\.)?|"+
			"May|MAY|may|June|JUNE|june|July|JULY|july|August|august|Aug(\\.)?|AUG(\\.)?|aug(\\.)?|"+
			"September|september|Sept(\\.)?|SEPT(\\.)?|sept(\\.)?|October|october|Oct(\\.)?|OCT(\\.)?|oct(\\.)?|November|november|Nov(\\.)?|NOV(\\.)?|nov(\\.)?|December|december|Dec(\\.)?|DEC(\\.)?|dec(\\.)?)\\s*)";

	//for format dd/mm/yyyy, dd/mm/yy, dd.mm.yyyy, dd.mm.yy, dd-mm-yyyy, dd-mm-yy, dd mm yyyy, dd mm yy
	private final String date1 = "(([0]?[1-9]|[1|2][0-9]|[3][0|1])(\\.|/|-|\\s+)([0]?[1-9]|[1][0-2])(\\.|/|-|\\s+)([0-9]{4}|[0-9]{2}))";


	//for format mm/dd/yyyy, mm-dd-yyyy, mm dd yyyy, mm.dd.yyyy
	private final String date2 = "(([0]?[1-9]|[1][0-2])(\\.|/|-|\\s+)([0]?[1-9]|[1|2][0-9]|[3][0|1])(\\.|/|-|\\s+)([0-9]{4}))";

	//for format dd Month yyyy, dd Month yy,ddMonthyyyy
	private final String date3 = "([0]?[1-9]|[1|2][0-9]|[3][0|1])"+MONTH+"([0-9]{4}|[0-9]{2})";

	//for format Month dd,    Month dd, yyyy, Month ddth, yyyy, Month dd-yyyy 
	private final String date4 = "("+MONTH+"\\d+"+"(st|nd|rd|th)?((,|-)*\\s+([1|2]\\d{3})?)?)";

	//for format ddth Month   ddth Month yyyy
	private final String date5 = "("+REGEX_ORD+MONTH+"(\\d{4})?)";

	//for format yyyy-mm-dd, yyyy/mm/dd, yyyy mm dd
	private final String date6 = "([1|2]\\d{3}(\\.|-|/|\\s+)([0]?[1-9]|[1][0-2])(\\.|-|/|\\s+)(\\d{1,2}))";
	

	private final String LENGTH_MEASURE = "((\\s*(k|c|d|m)?m(s?))|(\\s*(kilo|deci|centi|milli)?meter)|\\s*(mi|ft|yd|yard|naut mi|hand|span|mile(s?)|in(ches)?|NM|nm))";
	private final String AREA_MEASURE = "((\\s*(k|c|d|m)?m\\^2)|\\s*(mi\\^2|ha|a|ca)|\\s+((in|yd|ft|NM|nm)\\^2)|\\s+(ro|acro))";
	private final String SPEED_MEASURE = "\\s*((m/s)||(m/min)|(m/h)|(km/h)||(in/s)|(ft/s)|(ft/m)|(ft/h)|(km|m|miles)ph|mps|knot|kn|rpm|(rad/s)|(rad/min))\\s*";
	private final String WEIGHT_MEASURE = "\\s*(t|kg|hg|g|dg|cg|mg|µg|carat|lb|cwt|ton|(milli|kilo|centi|deci|deka|hecto)?gram(s?)|once(s)?|pound(s?)|grain(s?))\\s*";
	private final String VOLUME_MEASURE = "\\s*((m|d|k|c)?m\\^3|(d|c|m)?l|in\\^3|ft\\^3|yd\\^3|gal|bbl|pt|(dry|fluid)\\spt)\\s*";
	private final String TEMPERATURE_MEASURE = "\\s*((c|C)elsius|°C|(k|K)elvin|°K|(f|F)ahrenheit|°F)\\s*";
	private final String PRESSURE_MEASURE = "\\s*(bar|Pa(scal)?|at(m?)|Torr|psi)\\s*";
	private final String DATA_MEASURE = "\\s*((giga|Giga||mega|Mega|kilo|Kilo|Tera|tera|peta|Peta|Exa|exa|Yotta|yotta)?byte(s?)|bit|(G|M|K|T|E|Y)B)\\s*";
	private final String DATA_RATE_MEASURE = "\\s*(Kbps|Mbps|Gbps|(Kb/s(ec)?)|(Mb/s(ec)?)|(Gb/s(ec?))|(KB/s(ec)?)|(MB/s(ec)?)|(GB/s(ec)?))\\s*";
	

	
	private final String REGEX_NUM = "((\\+|-)?\\d+((\\.|,)\\d+)?(\\^\\d+)?((\\s*\\*\\s*)10\\^\\d+)?(\\s*E\\^(\\+|-)?\\d+)?)";
	private final String REGEX_URL_old = "<(A|a)\\s*(\\w|\\s|=|\"|:|\\/|\\.|-|\\?|@|_|&|%)*>(.)*<\\/(a|A)>";
	//private final static String REGEX_URL_2 = "(\\w|\\.|:|\\/|@)*(\\.)([a-z]{2,3})(\\w|\\.|:|\\/|@|\\?|=|-)*";
	private final static String REGEX_URL = "(http(s?):\\/\\/)*([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-z]{2,6}(\\/\\w+|\\?\\w+|=\\w+|&\\w+)*";
	
	private final String REGEX_DISTANCE = REGEX_NUM+LENGTH_MEASURE;
	private final String REGEX_AREA = REGEX_NUM+AREA_MEASURE+")";
	private final String REGEX_SPEED = REGEX_NUM+SPEED_MEASURE+")";
	private final String REGEX_WEIGHT = REGEX_NUM+WEIGHT_MEASURE+")";
	private final String REGEX_VOLUME = REGEX_NUM+VOLUME_MEASURE+")";
	private final String REGEX_TEMPERATURE = REGEX_NUM+TEMPERATURE_MEASURE+")";
	private final String REGEX_PRESSURE = REGEX_NUM+PRESSURE_MEASURE+")";
	private final String REGEX_DATA_RATE = REGEX_NUM+DATA_RATE_MEASURE;
	private final String REGEX_TIME = time1+"|"+time2;
	private final static String REGEX_MONEY = CURRENCY+"\\s*\\d+(\\.|,)*\\d*|\\d+(\\.|,)*\\d*\\s*"+CURRENCY;
	private final String REGEX_DATE = date1+"|"+date2+"|"+date3+"|"+date4+"|"+date5+"|"+date6;
	private final String REGEX_PHONE = "(\\+)*(\\d+-)*\\(\\d+\\)(-|\\s)+\\d+(-|\\s)*\\d+|\\d+\\.\\d+\\.\\d+|(\\+)*\\d+\\s\\d+\\s\\d+(\\s\\d+)*|\\d+-\\d+-\\d+\\d+\\s\\d+|(\\+)+\\d*\\s(\\()+\\d+(\\))+\\s\\d+\\s\\d+|(\\d+\\s)+\\d+\\s(\\()+\\d+(\\))+\\s\\d+\\s\\d+|(\\+\\d+\\s)*\\d+-\\d+\\s\\d+"+
			"|\\d+\\s\\(\\d+\\)\\s\\d+\\s\\d+|\\d{3,4}\\s\\d{4}";
	




	

	/* TAG STRING*/
	final String TAG_DATE = " #DATE ";
	final String TAG_TIME = " #TIME ";
	final String TAG_ORD = " #ORD ";
	final String TAG_NUM = " #NUM ";
	final String TAG_DISTANCE = " #DISTANCE ";
	final String TAG_AREA = " #AREA ";
	final String TAG_SPEED = " #SPEED ";
	final static String TAG_URL = " #URL ";
	final String TAG_WEIGHT = " #WEIGHT ";
	final String TAG_VOLUME = " #VOLUME ";
	final String TAG_DATA_RATE = " #DATA_RATE ";
	final static String TAG_MONEY = " #MONEY ";
	final String TAG_PHONE = " #CALL ";

	

	
	
	
	public static String tagPhrase(String trecID,String phrase){
		String firstPart = phrase.substring(0,(phrase.length()/2));
		String secondPart = phrase.substring(phrase.length()/2);
		
				
		if(firstPart.equals(secondPart)){
			System.out.println("First Part: "+firstPart);
			System.out.println("Second Part: "+secondPart);
			phrase = firstPart+" "+secondPart;
			System.out.println("FRASE SPEZZATA: "+phrase);
			System.out.println();
			
			
		}
		
	
		
		String changedPhrase = phrase;
		changedPhrase = tagPhraseURL(trecID,phrase,changedPhrase);
		
		
		
		
		if(!phrase.equals(changedPhrase)){
			TAGMiningFileWriter.writeOutput2(trecID,phrase);
			TAGMiningFileWriter.writeOutput3(trecID,changedPhrase);
		}
		
		return changedPhrase;
	}
	
	
	
	
	private static String tagPhraseURL(String trecID, String phrase,String changedPhrase) {

		int startIndex, endIndex = 0;
		
		
		Pattern patternURL = Pattern.compile(REGEX_URL);
		Matcher matcherURL = patternURL.matcher(phrase);
		while(matcherURL.find()){
			
			startIndex = matcherURL.start();
			endIndex = matcherURL.end();
			String matchedSubString = matcherURL.group();
		
			try {
				TAGMiningFileWriter.writeOutput1(trecID,matchedSubString, TAG_URL);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			changedPhrase = changedPhrase.substring(0, startIndex)+TAG_URL+changedPhrase.substring(endIndex);
			matcherURL = patternURL.matcher(changedPhrase);
		}
		
		return changedPhrase;
	}
	
	public static List<String> tagSingleURL(String trecID, String phrase,String changedPhrase) {

		int startIndex, endIndex = 0;
		
		
		Pattern patternURL = Pattern.compile(REGEX_URL);
		Matcher matcherURL = patternURL.matcher(phrase);
		
		List<String> URLS = new ArrayList<String>();
		while(matcherURL.find()){
			
			startIndex = matcherURL.start();
			endIndex = matcherURL.end();
			String matchedSubString = matcherURL.group();
			URLS.add(matchedSubString);
		    
			changedPhrase = changedPhrase.substring(0, startIndex)+TAG_URL+changedPhrase.substring(endIndex);
			matcherURL = patternURL.matcher(changedPhrase);
		}
		
		return URLS;
	}

	public static void main(String[] args) {
		
		/*
		String phrase = " dsklfjsk $43   lks $ 43 dlòkfsd $0,43  fsd 0,43 $  fsdwq fdfs sdf £ ";
		
		String phraseChanged = phrase;
		int startIndex, endIndex = 0;
		
		
		Pattern patternMONEY = Pattern.compile(REGEX_MONEY);
		Matcher matcherMONEY = patternMONEY.matcher(phrase);
		while(matcherMONEY.find()){
			
			startIndex = matcherMONEY.start();
			endIndex = matcherMONEY.end();
			String stringMatched = matcherMONEY.group();
			System.out.println("=================");
			System.out.println("start index: "+startIndex);
			System.out.println("end index: "+endIndex);
			System.out.println("Matched substring: "+stringMatched);
			
			phraseChanged = phraseChanged.substring(0, startIndex)+TAG_MONEY+phraseChanged.substring(endIndex+1);
			matcherMONEY = patternMONEY.matcher(phraseChanged);		
			
			System.out.println("Original phrase: "+phrase);
			System.out.println("Tagged phrase: "+phraseChanged);
			System.out.println("=================");
			System.out.println();
		
		}
		
		if(!phrase.equals(phraseChanged)){
			TAGMiningFileWriter.writeOutput2("DASDASD",phrase);
			TAGMiningFileWriter.writeOutput3("DASDASD",phraseChanged);
		}
		*/

		
		
		/*
		String cleanedText = matcherPHONE_NUMBER.replaceAll(TAG_MONEY);
		
		System.out.println(phrase);
		System.out.println("============");
		System.out.println(cleanedText);
		System.out.println("\n\n"+REGEX_MONEY);
		*/
		
		
		String phrase = "ABCnews.comABCnews.com";
		if((phrase.substring(0,(phrase.length()/2))).equals((phrase.substring((phrase.length())/2, phrase.length())))){
			phrase = phrase.substring(0,(phrase.length()/2))+ " "+ phrase.substring((phrase.length())/2, phrase.length());
			System.out.println("FRASE SPEZZATA: "+phrase);
		
		}
		
		
		
	}
	
	
}
