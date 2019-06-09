package com.bigdata.Mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class ReduceTask extends Reducer<Text, NullWritable, Text, NullWritable>{
	@Override
	protected void reduce(Text key, Iterable<NullWritable> iter, Context context)
			throws IOException, InterruptedException {
		context.write(key, NullWritable.get());
		
	}
}
