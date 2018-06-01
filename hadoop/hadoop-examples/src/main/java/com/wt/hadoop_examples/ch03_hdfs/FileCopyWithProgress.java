package com.wt.hadoop_examples.ch03_hdfs;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

public class FileCopyWithProgress {

	public static void main(String[] args) throws Exception {
		String localSrc = args[0];
		String dst = args[1];
//		String localSrc = "C:\\Users\\Administrator\\Desktop\\10w\\opc_1.csv";
//		String dst = "hdfs://172.16.13.122:9000/demo1/opc_1.input";
		
		InputStream in = new BufferedInputStream(new FileInputStream(localSrc));
		
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(dst), conf);
		OutputStream out = fs.create(new Path(dst), new Progressable() {
			
			public void progress() {
				System.out.println(".");
			}
		});
		
		IOUtils.copyBytes(in, out, 4096, true);
	}
	
}
