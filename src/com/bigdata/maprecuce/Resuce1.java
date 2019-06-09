package com.bigdata.maprecuce;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Resuce1 extends Reducer<Text, IntWritable, Text, DoubleWritable> {

	@Override
	protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		int count=0;
		int sum=0;
		double avg=0.0;
		for (IntWritable value : values) {
			count++;
			sum+=value.get();
		}
		avg=sum/count;
		context.write(key,new DoubleWritable(avg));
	}
}
