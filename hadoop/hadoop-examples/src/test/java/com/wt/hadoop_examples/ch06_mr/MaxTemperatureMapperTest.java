package com.wt.hadoop_examples.ch06_mr;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;

public class MaxTemperatureMapperTest {
	
	public void processesValidRecord() throws IOException,InterruptedException {
		Text value = new Text("0043011990999991950051518004+68750+023550FM-12+0382" + 
									// Year ^^^^
				"99999V0203201N00261220001CN9999999N9-00111+99999999999");
								// Temperature ^^^^
		new MapDriver<LongWritable, Text, Text, IntWritable>();
	}
}
