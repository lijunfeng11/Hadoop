package com.ceshi;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;


/**
 * 讲手机的上行流量   下行流量   总流量封装到一个对象里头
 * 如果在Mapreduce中要使用对象去封装数据    必须实现hadoop的序列化
 * @author hp
 *
 */
public class FlowBean implements WritableComparable<FlowBean>{
	private String phone;
	private int upFlow;
	private int downFlow;
	private int sunFlow;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
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
	public int getSunFlow() {
		return sunFlow;
	}
	public void setSunFlow(int sunFlow) {
		this.sunFlow = sunFlow;
	}
	
	@Override
	public String toString() {
		return phone+"--"+upFlow+"--"+downFlow+"--"+sunFlow;
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		phone = in.readUTF();
		upFlow = in.readInt();
		downFlow = in.readInt();
		sunFlow = in.readInt();
	}
	
	/**
	 *写的顺序  必须和这个readFields中的字段信息保持一致
	 */
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(phone);
		out.writeInt(upFlow);
		out.writeInt(downFlow);
		out.writeInt(sunFlow);
	}
	@Override
	public int compareTo(FlowBean o) {
		
		return this.sunFlow>o.getSunFlow()?1:-1;
	}

}