package com.MapReduce;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

/**
 * 
 * 讲手机上行和下行流量 总流量封装到一个对象里面 如果在MapReduce中要使用对象去封装数据 必须实现hadoop的序列化
 * 
 * @author 李军锋
 *
 */
public class Bean implements Writable {
	public Bean() {

	}

	public Bean(int upFlum, int downFlum) {
		this.upFlow = upFlow;
		this.downFlow = downFlow;
		this.sumFlow = upFlow + downFlow;

	}

	private int upFlow;
	private int downFlow;
	private int sumFlow;

	public int getUpFlow() {
		return upFlow;
	}

	public void setUpFlow(int upFlow) {
		this.upFlow = upFlow;
	}

	public int getDownFlow() {
		return downFlow;
	}

	public void setDownFlow(int downFlow) {
		this.downFlow = downFlow;
	}

	public int getSumFlow() {
		return sumFlow;
	}

	public void setSumFlow(int sumFlow) {
		this.sumFlow = sumFlow;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return upFlow + "--" + downFlow + "--" + sumFlow;
	}

	/**
	 * 序列化 读和写的顺序不许和写的里面一致
	 */
	@Override
	public void readFields(DataInput in) throws IOException {
		// 读序列化
		upFlow = in.readInt();
		downFlow = in.readInt();
		sumFlow = in.readInt();

	}

	// 写序列化
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(this.upFlow);
		out.writeInt(this.downFlow);
		out.writeInt(this.sumFlow);

	}

}
