package com.MapReduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.bigdata.Mapreduce.Test;

public class test3 {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		// 1、 配置连接Hadoop集群的参数
		Configuration conf = new Configuration();

		// 2、获取对象Job对象实例
		Job job = Job.getInstance(conf);

		// 3、指定本业务job的路劲
		job.setJarByClass(Test.class);

		// 4、指定本业务job要使用的Mapper类
		job.setMapperClass(Map2.class);
		// 5、指定Mapper类的输出数据的kv的类型s
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		// 6、 指定本业务job要使用Reduce类
		job.setReducerClass(Reduce2.class);

		//将不同省份
		job.setPartitionerClass(FlowPartiton.class);
		
		// 7、设置程序的最终输出结果的kv的类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DoubleWritable.class);

		/**
		 * 在hadoop运行程序的时候 如果输出存在 会报错 所以这个方法是删除存在的输出路径
		 */
		Path output = new Path("out");
		FileSystem fs = FileSystem.get(conf);
		if (fs.exists(output)) {
			fs.delete(output, true);
		}

		// 8、设置job要处理的数据的输入源
		FileInputFormat.addInputPath(job, new Path("input1"));
		// 9、设置job的输出目录 （注：路径之前不能有）
		FileOutputFormat.setOutputPath(job, new Path("out"));

		// 10、提交job
		job.waitForCompletion(true);

	}

}
