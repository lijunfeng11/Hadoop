package com.bigdata.Mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.simple.wordcount.WordCountReduce;
import com.simple.wordcount.WorsCountMapper;
/**
 * 
 * @author 李军锋
 *去重
 */
public class Test {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		// 1、 配置连接Hadoop集群的参数
		Configuration conf = new Configuration();

		// 2、获取对象Job对象实例
		Job job = Job.getInstance(conf);

		// 3、指定本业务job的路劲
		job.setJarByClass(Test.class);

		// 4、指定本业务job要使用的Mapper类
		job.setMapperClass(MapTask.class);
		// 5、指定Mapper类的输出数据的kv的类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(NullWritable.class);

		// 6、 指定本业务job要使用Reduce类
		job.setReducerClass(ReduceTask.class);

		// 7、设置程序的最终输出结果的kv的类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);

		// 8、设置job要处理的数据的输入源
		FileInputFormat.addInputPath(job, new Path("wc.txt"));
		// 9、设置job的输出目录 （注：路径之前不能有）
		FileOutputFormat.setOutputPath(job, new Path("out2"));

		// 10、提交job
		job.waitForCompletion(true);

	}
}
