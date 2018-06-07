package com.wt.hadoop_examples.flowSumArea;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.wt.hadoop_examples.flowSum.FlowBean;

public class FSSumArea {
	
	public static class Map extends Mapper<LongWritable, Text, Text, FlowBean> {

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
	
	public static class Reduce extends Reducer<Text, FlowBean, Text, FlowBean> {

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
	
	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		job.setJarByClass(FSSumArea.class);
		
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		
		job.setPartitionerClass(AreaPartitioner.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(FlowBean.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FlowBean.class);
		
		// 设置reduce任务的并发数，应该跟分组的数量保持一致
		job.setNumReduceTasks(4);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		FileOutputFormat.setCompressOutput(job, false); 
		
		System.exit(job.waitForCompletion(true) ? 0:1);
		
	}
	
}
