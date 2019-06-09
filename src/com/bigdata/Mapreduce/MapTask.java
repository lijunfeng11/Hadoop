package com.bigdata.Mapreduce;


import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class MapTask extends Mapper<LongWritable, Text, Text, NullWritable>  {

	@Override
	protected void map(LongWritable key, Text value, Context context) throws java.io.IOException, InterruptedException {
		String word = value.toString();
		context.write(new Text(word),NullWritable.get());
	}
}
