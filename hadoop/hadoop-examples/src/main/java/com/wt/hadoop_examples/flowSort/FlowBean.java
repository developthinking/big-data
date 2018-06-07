package com.wt.hadoop_examples.flowSort;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class FlowBean implements WritableComparable<FlowBean> {
	
	/** 手机号 */
	private String phoneNB;
	/** 上行流量 */
	private long upFlow;
	/** 下行流量 */
	private long downFlow;
	/** 总流量 */
	private long sumFlow;
	
	public FlowBean() {}
	
	public FlowBean(String phoneNB, long upFlow, long downFlow) {
		this.phoneNB = phoneNB;
		this.upFlow = upFlow;
		this.downFlow = downFlow;
		this.sumFlow = upFlow + downFlow;
	}

	//从数据流中反序列出对象的数据
	public void readFields(DataInput in) throws IOException {
		
		phoneNB = in.readUTF();
		upFlow = in.readLong();
		downFlow = in.readLong();
		sumFlow = in.readLong();
		
	}

	//将对象数据序列化到流中
	public void write(DataOutput out) throws IOException {
		
		out.writeUTF(phoneNB);
		out.writeLong(upFlow);
		out.writeLong(downFlow);
		out.writeLong(sumFlow);
		
	}

	/** setter getter */
	public String getPhoneNB() {
		return phoneNB;
	}

	public void setPhoneNB(String phoneNB) {
		this.phoneNB = phoneNB;
	}

	public long getUpFlow() {
		return upFlow;
	}

	public void setUpFlow(long upFlow) {
		this.upFlow = upFlow;
	}

	public long getDownFlow() {
		return downFlow;
	}

	public void setDownFlow(long downFlow) {
		this.downFlow = downFlow;
	}

	public long getSumFlow() {
		return sumFlow;
	}

	public void setSumFlow(long sumFlow) {
		this.sumFlow = sumFlow;
	}

	/** toString */
	@Override
	public String toString() {
		return "" + upFlow + "\t" + downFlow + "\t" + sumFlow;
	}
	
	/** sort */
	public int compareTo(FlowBean obj) {
		return sumFlow>obj.getSumFlow() ? -1:1;
	}

}
