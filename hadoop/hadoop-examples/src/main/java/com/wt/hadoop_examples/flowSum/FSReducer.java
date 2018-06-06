package com.wt.hadoop_examples.flowSum;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FSReducer extends Reducer<Text, FlowBean, Text, FlowBean> {

	//遍历values然后进行累加求和
	@Override
	protected void reduce(Text key, Iterable<FlowBean> values, Reducer<Text, FlowBean, Text, FlowBean>.Context context) throws IOException, InterruptedException {
		
		long upFlowCounter = 0;
		long downFlowCounter = 0;
		
		for (FlowBean fb : values) {
			upFlowCounter += fb.getUpFlow();
			downFlowCounter += fb.getDownFlow();
		}
		
		context.write(key, new FlowBean(key.toString(), upFlowCounter, downFlowCounter));
		
	}

}
