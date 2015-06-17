package ReadingCommonCrawl;

import java.io.File;
import java.io.FileInputStream;

import org.archive.io.ArchiveReader;
import org.archive.io.ArchiveRecord;
import org.archive.io.warc.WARCReaderFactory;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import phrases.SentenceDetector;


public class ReadingCommonCrawl {
	
	static final String WarcPath ="";
	
	public static void main(String[] args) throws IOException {
		File f = new File(WarcPath);
		String INPUT_GZIP_FILE = WarcPath+f.getName();
		FileInputStream is = null;
		
		//ritorna contenuto URL cercato
		is = new FileInputStream(INPUT_GZIP_FILE);
		ArchiveReader ar = WARCReaderFactory.get(INPUT_GZIP_FILE, is, true);

		//int k = 0;
		String UrlWarc;    
		byte[] rawData=null;
		String[] phrases;
		for(ArchiveRecord r : ar) {
			UrlWarc =r.getHeader().getUrl();
			//System.out.println(UrlWarc);
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

					}
				}
			}
			//k++;
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
					//System.out.println(p.text());
					aux += p.text();
				}
			}



			//aux= Jsoup.parse(HTMLContent).body().text();


		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return aux;
	}
}
