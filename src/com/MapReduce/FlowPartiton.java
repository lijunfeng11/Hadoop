package com.MapReduce;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class FlowPartiton extends Partitioner<Text, Bean> {
	@Override
	public int getPartition(Text key, Bean value, int num) {
		
	String preNum=key.toString().substring(0, 3);
	int	partition=4;
	if ("136".equals(preNum)) {
	 int partitioner = 0;
	}
	
	return partition;
		
	}
}
