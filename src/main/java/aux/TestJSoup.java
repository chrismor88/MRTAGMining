package aux;

import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestJSoup {

	
	public static void main(String[] args) {
		File fileHTML = new File("/Volumes/DATA/workspace/html.txt");
		
	
		
		
				try {
					String aux= Jsoup.parse(fileHTML,"UTF-8").text();
					System.out.println(aux);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
			


	}

}
