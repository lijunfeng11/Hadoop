package com.uek.bigdata;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Job;
/**
 * map step
 *
 */
import org.junit.Test;


import com.sun.jersey.server.wadl.generators.resourcedoc.WadlGeneratorResourceDocSupport;

class Map extends Mapper<LongWritable, Test, Test, IntWritable> {
	@Override
	protected void map(LongWritable key, Test word,Context context)
			throws IOException, InterruptedException {
	
		//application
		// 接收数据  get one Line
		context.write(word,new IntWritable(1));
	}

}

class Reduce extends Reducer<Text, IntWritable, Text, IntWritable>{
	@Override
	protected void reduce(Text word, Iterable<IntWritable> iter,Context arg2)
			throws IOException, InterruptedException {
		int sum=0;
	for (IntWritable one :iter) {
		sum=sum+one.get();
	}
	
	}
	
}

/**
 * mapreduce在编写代码是主要包含3部分 1 map 2 reduce 3 driver(通过该驱动来封装MapReduce作业)
 */
public class MapReduceTest {

	public static void main(String[] args) throws IOException {

		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration,"wordcount");
		
	}
}
