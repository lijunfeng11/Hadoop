package com.bigdata.maprecuce;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Map1 extends Mapper<LongWritable, Text, Text, IntWritable>{

	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		String[] s = line.split("，");
//		System.out.println(Arrays.toString(s));
		String name=s[0];
		int fenshu = Integer.parseInt(s[1]);
		
		context.write(new Text(name), new IntWritable(fenshu));
	}
}
