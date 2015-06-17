package ReadingClueWeb09;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.GZIPInputStream;

import phrases.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import clean.CleanerPhrase;



public class ReadingClueWeb {
	
	static final String PhrasesPath = "util/phrasesClueWeb.txt";
	
	public static void main(String[] args) throws IOException {
		System.out.println("inizio estrazione frasi da ClueWeb");
		long start = System.currentTimeMillis();
		
		
		
		
		
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


			Elements pTags = null;
			try {
				pTags = bodyHTML.getElementsByTag("p");
			} catch (NullPointerException e) {
				// TODO: handle exception
			}
			for(Element p:pTags){

				String[] parole = p.text().split(" ");

				if(parole.length > 4){
					aux =aux+" "+p.text();
				}
			}




		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return aux;
	}
}
