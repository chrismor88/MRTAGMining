package ReadingCommonCrawl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.archive.io.ArchiveReader;
import org.archive.io.ArchiveRecord;
import org.archive.io.warc.WARCReaderFactory;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import clean.CleanerPhrase;
import phrases.SentenceDetector;


public class ReadingCommonCrawl {
	
	static final String WarcPath ="util/CC-MAIN-20150417045713-00000-ip-10-235-10-82.ec2.internal.warc.gz";
	static final String PhrasesPath = "util/phrasesCommonCrawl.txt";
	
	public static void main(String[] args) throws IOException {
		File f = new File(WarcPath);
		String INPUT_GZIP_FILE = WarcPath+f.getName();
		FileInputStream is = null;
		

		is = new FileInputStream(INPUT_GZIP_FILE);
		ArchiveReader ar = WARCReaderFactory.get(INPUT_GZIP_FILE, is, true);


		String UrlWarc;    
		byte[] rawData=null;
		String[] phrases;
		String trecID = "";
		for(ArchiveRecord r : ar) {
			
			UrlWarc =r.getHeader().getUrl();

			if (UrlWarc!=null){
				if ((r.getHeader().getMimetype().equals("application/http; msgtype=response"))&&
						(r.getHeader().getContentLength()>300)){
					//String url = r.getHeader().getUrl();
					rawData = IOUtils.toByteArray(r, r.available());

					String HTMLContent = new String(rawData);
					String HTMLContent2="";
					if (HTMLContent.indexOf("Content-Type: text/html")!=-1){
						if (HTMLContent.indexOf("<")!=-1){
							HTMLContent2 = HTMLContent.substring(HTMLContent.indexOf("<"));
						}
						
							
						
						String content = getHTMLBody(HTMLContent2);
						phrases = SentenceDetector.sentenceDetect(content);
						
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
			}
		
		}
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
