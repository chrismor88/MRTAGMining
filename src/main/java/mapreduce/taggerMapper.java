package mapreduce;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

import regex.TAGComponent;
import writer_text.TAGMiningFileWriter;

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
			/*
			List<String> URLS = TAGComponent.tagSingleURL(trecID, singlePhrase);
			for (int i = 0; i < URLS.size(); i++) {
				TAGMiningFileWriter.writeOutput1(trecID, URLS.get(i), "#URL");
				phrase.set(trecID+"#"+URLS.get(i));
				phrase2.set("#URL");
				context.write(phrase, phrase2);
			}
			PrintWriter pw = new PrintWriter("util/output2.txt");
			pw.println(trecID+"\t"+singlePhrase);
			PrintWriter pw2 = new PrintWriter("util/output3.txt");
			pw2.println(trecID+"\t"+TaggedPhase);
			pw.close();
			pw2.close();*/
			TAGMiningFileWriter.writeOutput2(trecID,singlePhrase);
			TAGMiningFileWriter.writeOutput3(trecID, TaggedPhase);
			phrase.set(line);
			phrase2.set(TaggedPhase);
			context.write(phrase, phrase2);
		}
    } 
}
