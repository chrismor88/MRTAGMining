package mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class taggerReducer extends Reducer<Text,IntWritable,Text,IntWritable>{

}
