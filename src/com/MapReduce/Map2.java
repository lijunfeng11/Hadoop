package com.MapReduce;

import java.io.IOException;

import org.apache.catalina.tribes.util.Arrays;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Map2 extends Mapper<LongWritable, Text, Text, Bean>{
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		String[] s = line.split("\t");
		
		String phoneNum = s[1];
		
		int upFlow = Integer.parseInt(s[8]) ;
		int downFlow = Integer.parseInt(s[9]);
		Bean bean = new Bean(upFlow,downFlow);
		
		context.write(new Text(phoneNum), bean);
		
	}
}
