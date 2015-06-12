package mapreduce;

import java.io.IOException;

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
		String TaggedPhase = TAGComponent.tagPhrase("", line);
		if (!line.equals(TaggedPhase)){
			phrase.set(line);
			phrase2.set(TaggedPhase);
			context.write(phrase, phrase2);
		}
    } 
}
