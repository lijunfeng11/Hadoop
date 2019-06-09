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

/**
 * 统计手机的上行流量 下行流量 总流量
 * 
 * @author hp
 * 
 */
public class Test1 {
	/**
	 * mapper 分数据的 最后以手机号为key以对象为value输出到reduce 文件中的每一行会执行一次map方法
	 * 
	 * @author hp
	 * 
	 */
	private static class FlowMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
		Text k = new Text();
		FlowBean v = new FlowBean();

		protected void map(LongWritable key, Text value, Context context)
				throws java.io.IOException, InterruptedException {
			// value代表每一行的内容
			String[] fields = value.toString().split("\t");
			String phone = fields[1];
			int upFlow = Integer.parseInt(fields[fields.length - 3]);
			int downFlow = Integer.parseInt(fields[fields.length - 2]);
			k.set(phone);
			v.setPhone(phone);
			v.setUpFlow(upFlow);
			v.setDownFlow(downFlow);
			v.setSunFlow(upFlow + downFlow);
			context.write(k, v);
		};

	}

	/**
	 * 按照phone聚合数据 每一组相同的key调用一次reduce方法
	 * 
	 * @author hp
	 * 
	 */
	private static class FlowReducer extends Reducer<Text, FlowBean, NullWritable, FlowBean> {
		FlowBean v = new FlowBean();

		protected void reduce(Text key, Iterable<FlowBean> values, Context context)
				throws java.io.IOException, InterruptedException {
			int upFlow = 0;
			int downFlow = 0;
			int sumFlow = 0;
			for (FlowBean flowBean : values) {
				upFlow += flowBean.getUpFlow();
				downFlow += flowBean.getDownFlow();
				sumFlow += flowBean.getSunFlow();
			}

			v.setPhone(key.toString());
			v.setUpFlow(upFlow);
			v.setDownFlow(downFlow);
			v.setSunFlow(sumFlow);

			context.write(NullWritable.get(), v);

		};

	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(Test1.class);

		// 2 指定本业务job要使用的mapper/Reducer业务类
		job.setMapperClass(FlowMapper.class);
		job.setReducerClass(FlowReducer.class);

		// 自定义分区,将不同省份的手机号输出到不同的文件夹
		job.setPartitionerClass(FlowPartiton.class);
		job.setNumReduceTasks(5);
		
		// 3 指定mapper输出数据的kv类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(FlowBean.class);

		// 4 指定最终输出的数据的kv类型
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(FlowBean.class);

		/**
		 * 在hadoop运行程序的时候 如果输出路径存在 会报错 所以这个方法 是删除存在的输出路径
		 */
		Path output = new Path("output");
		FileSystem fs = FileSystem.get(conf);
		if (fs.exists(output)) {
			fs.delete(output, true);
		}
		

		// 5 指定job的输入原始文件所在目录
		FileInputFormat.setInputPaths(job, new Path("input1"));
		FileOutputFormat.setOutputPath(job, output);

		// 7 将job中配置的相关参数，以及job所用的java类所在的jar包， 提交给yarn去运行
		boolean result = job.waitForCompletion(true);
		System.exit(result ? 0 : 1);

	}

}