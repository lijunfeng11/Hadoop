package com.simple.wordcount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WorsCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	@Override
	protected void map(LongWritable key, Text value, Context context) throws java.io.IOException, InterruptedException {
		// 获取一行数据
		String line = value.toString();
		// 切割一行数据,用空格切割 存放到数组里面
		String[] strs = line.split(" ");
		// 做标记，输出
		for (String word : strs) {
			context.write(new Text(word), new IntWritable(1));
		}
	}

}
