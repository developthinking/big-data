package com.wt.hadoop_examples.flowSort;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FSSortMR {
	
	public static class Map extends Mapper<LongWritable, Text, FlowBean, NullWritable> {

		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, FlowBean, NullWritable>.Context context) throws IOException, InterruptedException {
			
			String line = value.toString();
			String[] fields = StringUtils.split(line, "\t");
			
			String phoneNB = fields[1];
			long upFlow = Long.parseLong(fields[3]);
			long downFlow = Long.parseLong(fields[4]);
			
			context.write(new FlowBean(phoneNB, upFlow, downFlow), NullWritable.get());
			
		}
	}
	
	public static class Reduce extends Reducer<FlowBean, NullWritable, Text, FlowBean> {

		@Override
		protected void reduce(FlowBean key, Iterable<NullWritable> values, Reducer<FlowBean, NullWritable, Text, FlowBean>.Context context) throws IOException, InterruptedException {

			String phoneNB = key.getPhoneNB();
			context.write(new Text(phoneNB), key);

		}
		
	}
	
	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		job.setJarByClass(FSSortMR.class);
		
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		
		job.setMapOutputKeyClass(FlowBean.class);
		job.setMapOutputValueClass(NullWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FlowBean.class);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		FileOutputFormat.setCompressOutput(job, false); 
		
		System.exit(job.waitForCompletion(true) ? 0:1);
		
	}
	
}
