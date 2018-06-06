package com.wt.hadoop_examples.flowSum;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FSMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
	
	//拿到日志中的一行数据，切分各个字段，抽取出我们需要的字段：手机号，上行流量，下行流量
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, FlowBean>.Context context) throws IOException, InterruptedException {
		
		String line = value.toString();
		String[] fields = StringUtils.split(line, "\t");
		
		String phoneNB = fields[1];
		long upFlow = Long.parseLong(fields[3]);
		long downFlow = Long.parseLong(fields[4]);
		
		context.write(new Text(phoneNB), new FlowBean(phoneNB, upFlow, downFlow));
		
	}

}
