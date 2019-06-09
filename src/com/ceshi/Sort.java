package com.ceshi;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.MapReduce.FlowPartiton;

public class Sort {
	private static class SortMapper extends Mapper<LongWritable, Text,FlowBean ,Text> {
		Text k = new Text();
		FlowBean v = new FlowBean();

		protected void map(LongWritable key, Text value, Context context)
				throws java.io.IOException, InterruptedException {
			// value代表每一行的内容
			String[] fields = value.toString().split("--");
			String phone = fields[0];
			int upFlow = Integer.parseInt(fields[1]);
			int downFlow = Integer.parseInt(fields[2]);
			k.set(phone);
			v.setPhone(phone);
			v.setUpFlow(upFlow);
			v.setDownFlow(downFlow);
			v.setSunFlow(upFlow + downFlow);
			context.write(v,k);
		};

	}

	/**
	 * 按照phone聚合数据 每一组相同的key调用一次reduce方法
	 * 
	 * @author hp
	 * 
	 */
	private static class SortReducer extends Reducer< FlowBean,Text, NullWritable, FlowBean> {
	
		@Override
		protected void reduce(FlowBean bean, Iterable<Text> values,Context context) throws IOException, InterruptedException {
			
			context.write(NullWritable.get(), bean);
		}
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(Test1.class);

		// 2 指定本业务job要使用的mapper/Reducer业务类
		job.setMapperClass(SortMapper.class);
		job.setReducerClass(SortReducer.class);

		// 将不同省份
		job.setPartitionerClass(FlowPartiton.class);

		// 3 指定mapper输出数据的kv类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(FlowBean.class);

		// 4 指定最终输出的数据的kv类型
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(FlowBean.class);

		/**
		 * 在hadoop运行程序的时候 如果输出路径存在 会报错
		 * 所以这个方法 是删除存在的输出路径
		 */

		// 5 指定job的输入原始文件所在目录
		FileInputFormat.setInputPaths(job, new Path("input1/part-r-00000"));
		FileOutputFormat.setOutputPath(job,new Path("output1"));

		// 7 将job中配置的相关参数，以及job所用的java类所在的jar包， 提交给yarn去运行
		boolean result = job.waitForCompletion(true);
		System.exit(result ? 0 : 1);

	}

}
