package ReadingClueWeb09;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import phrases.*;
import regex.TAGComponent;
import writer_text.TAGMiningFileWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import clean.CleanerPhrase;



public class ReadingClueWeb {
	
	static final String PhrasesPath = "util/phrasesClueWeb.txt";
	
	public static void main(String[] args) throws IOException {
		System.out.println("inizio");
		long start = System.currentTimeMillis();
		
		final String REGEX_PHONE = "(\\+)*(\\d+-)*\\(\\d+\\)(-|\\s)+\\d+(-|\\s)*\\d+|\\d+\\.\\d+\\.\\d+|(\\+)*\\d+\\s\\d+\\s\\d+(\\s\\d+)*|\\d+-\\d+-\\d+\\d+\\s\\d+|(\\+)+\\d*\\s(\\()+\\d+(\\))+\\s\\d+\\s\\d+|(\\d+\\s)+\\d+\\s(\\()+\\d+(\\))+\\s\\d+\\s\\d+|(\\+\\d+\\s)*\\d+-\\d+\\s\\d+"+
				"|\\d+\\s\\(\\d+\\)\\s\\d+\\s\\d+|\\d{3,4}\\s\\d{4}";
		
		final String TAG_PHONE = " #CALL ";
		
		
		final String REGEX_ORD = "((\\d+(st|nd|rd|th)) | (\\d+°))";
		
		final String MONTH = "(\\s*(January|january|jan(\\.)|Jan(\\.)?|JAN(\\.)?|February|february|Feb(\\.)?|FEB(\\.)?|feb(\\.)?|March|march|Mar(\\.)?|MAR(\\.)?|mar(\\.)?|April|april|Apr(\\.)?|APR(\\.)?|"+
				"May|MAY|may|June|JUNE|june|July|JULY|july|August|august|Aug(\\.)?|AUG(\\.)?|aug(\\.)?|"+
				"September|september|Sept(\\.)?|SEPT(\\.)?|sept(\\.)?|October|october|Oct(\\.)?|OCT(\\.)?|oct(\\.)?|November|november|Nov(\\.)?|NOV(\\.)?|nov(\\.)?|December|december|Dec(\\.)?|DEC(\\.)?|dec(\\.)?)\\s*)";

		//for format dd/mm/yyyy, dd/mm/yy, dd.mm.yyyy, dd.mm.yy, dd-mm-yyyy, dd-mm-yy, dd mm yyyy, dd mm yy
		final String date1 = "(([0]?[1-9]|[1|2][0-9]|[3][0|1])(\\.|/|-|\\s+)([0]?[1-9]|[1][0-2])(\\.|/|-|\\s+)([0-9]{4}|[0-9]{2}))";


		//for format mm/dd/yyyy, mm-dd-yyyy, mm dd yyyy, mm.dd.yyyy
		final String date2 = "(([0]?[1-9]|[1][0-2])(\\.|/|-|\\s+)([0]?[1-9]|[1|2][0-9]|[3][0|1])(\\.|/|-|\\s+)([0-9]{4}))";

		//for format dd Month yyyy, dd Month yy,ddMonthyyyy
		final String date3 = "([0]?[1-9]|[1|2][0-9]|[3][0|1])"+MONTH+"([0-9]{4}|[0-9]{2})";

		//for format Month dd,    Month dd, yyyy, Month ddth, yyyy, Month dd-yyyy 
		final String date4 = "("+MONTH+"\\d+"+"(st|nd|rd|th)?((,|-)*\\s+([1|2]\\d{3})?)?)";

		//for format ddth Month   ddth Month yyyy
		final String date5 = "("+REGEX_ORD+MONTH+"(\\d{4})?)";

		//for format yyyy-mm-dd, yyyy/mm/dd, yyyy mm dd
		final String date6 = "([1|2]\\d{3}(\\.|-|/|\\s+)([0]?[1-9]|[1][0-2])(\\.|-|/|\\s+)(\\d{1,2}))";


		final String REGEX_DATE = date1+"|"+date2+"|"+date3+"|"+date4+"|"+date5+"|"+date6;
		
		final String TAG_DATE = " #DATE ";
		
		
		
		final  String REGEX_URL = "(http:\\/\\/)*([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-z]{2,6}(\\/\\w+|\\?\\w+|=\\w+|&\\w+)*";

		final String TAG_URL = " #URL ";
		
		
		
		
		
		// Set up a local compressed WARC file for reading 
        String inputWarcFile="util/00.warc.gz";
            // open our gzip input stream
            GZIPInputStream gzInputStream=new GZIPInputStream(new FileInputStream(inputWarcFile));
            
            // cast to a data input stream...
            DataInputStream inStream=new DataInputStream(gzInputStream);
            
            // iterate through our stream
            WarcRecord thisWarcRecord;

            //creo array di stringhe per memorizzare le frasi
            String[] phrases;
            while ((thisWarcRecord=WarcRecord.readNextWarcRecord(inStream))!=null) {
              // see if it's a response record
              if (thisWarcRecord.getHeaderRecordType().equals("response")) {
                // it is - create a WarcHTML record
                WarcHTMLResponseRecord htmlRecord=new WarcHTMLResponseRecord(thisWarcRecord);
                // get our TREC ID and target URI
                String trecID=htmlRecord.getTargetTrecID();
                
                String thisTargetURI=htmlRecord.getTargetURI();
                // print our data
                //System.out.println(trecID + " : " + thisTargetURI);
                
                String HTMLContent = htmlRecord.getRawRecord().getContentUTF8();
				String HTMLContent2="";
				if (HTMLContent.indexOf("<")!=-1){
					HTMLContent2 = HTMLContent.substring(HTMLContent.indexOf("<"));
				}
				
				
				
				//PARTE CHRIS
				String textBody = getHTMLBody(HTMLContent2);
								
				phrases = SentenceDetector.sentenceDetect(textBody);
				
				for(String phrase : phrases){
					String cleanedPhrase = CleanerPhrase.deleteWastedHTML(phrase);
					String[] temp = cleanedPhrase.split(" ");

					if(temp.length>3 && temp.length<40){
						PrintWriter out=null;
						
						try {
							out = new PrintWriter(new BufferedWriter(new FileWriter(PhrasesPath, true)));
							out.println(trecID+"#"+cleanedPhrase);
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						finally{
							out.close();
						}
					}
										
				}
				
              }
            }
            inStream.close();
            System.out.println("CONCLUSO");
            long end = System.currentTimeMillis();
            System.out.println("in: "+(end-start));
          }
	private static String getHTMLBody(String HTMLContent){
		String aux="";
		try {


			Element bodyHTML = Jsoup.parse(HTMLContent, "UTF-8").body();


			Elements pTags = bodyHTML.getElementsByTag("p");

			for(Element p:pTags){

				String[] parole = p.text().split(" ");

				if(parole.length > 4){
					aux += p.text();
				}
			}




		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return aux;
	}
}
