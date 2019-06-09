package com.simple.wordcount;

import java.io.IOException;

import org.apache.hadoop.examples.SecondarySort.Reduce;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
	@Override
	protected void reduce(Text key, Iterable<IntWritable> iter, Context context)
			throws IOException, InterruptedException {
		
		int sum = 0;
		for (IntWritable i : iter) {
			sum += i.get();
		}
		
		context.write(new Text(key), new IntWritable(sum));
	}

	
}
