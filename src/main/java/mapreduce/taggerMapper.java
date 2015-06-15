package mapreduce;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

import regex.TAGComponent;

public class taggerMapper extends Mapper<Object, Text, Text, Text> {
	private Text phrase = new Text();
	private Text phrase2 = new Text();
    
	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString();
		String[] spittedLine = line.split("#");
		String trecID = spittedLine[0];
		String singlePhrase = spittedLine[1];
		String TaggedPhase = TAGComponent.tagPhrase("", singlePhrase);
		
		if (!singlePhrase.equals(TaggedPhase)){
			
			List<String> URLS = TAGComponent.tagSingleURL(trecID, singlePhrase, TaggedPhase);
			for (int i = 0; i < URLS.size()-1; i++) {
				phrase.set(trecID+"#"+URLS.get(i));
				phrase2.set("#URL");
				context.write(phrase, phrase2);
			}
			
			phrase.set(line);
			phrase2.set(TaggedPhase);
			context.write(phrase, phrase2);
		}
    } 
}
