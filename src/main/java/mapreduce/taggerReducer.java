package mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class taggerReducer extends Reducer<Text,Text,Text,Text>{
	public void reduce(Text key, Text values,Context context) throws IOException, InterruptedException {
	     
	      context.write(key, values);
	    }
		
}
