package com.MapReduce;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Reduce2 extends Reducer<Text, Bean, Text, DoubleWritable> {
	@Override
	protected void reduce(Text key, Iterable<Bean> value, Context context) throws IOException, InterruptedException {
		int sumUpFlow = 0;
		int sumDownFlow = 0;
		int sumFlow = 0;

		for (Bean bean : value) {
			sumUpFlow += bean.getUpFlow();
			sumDownFlow += bean.getDownFlow();
			sumFlow += bean.getSumFlow();
		}
		Bean bean = new Bean(sumFlow, sumFlow);
		bean.setUpFlow(sumUpFlow);
		bean.setDownFlow(sumDownFlow);
		bean.setSumFlow(sumFlow);
	}
}
