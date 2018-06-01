package com.wt.hadoop_examples.ch03_hdfs;

import java.io.InputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class FileSystemCat {

	public static void main(String[] args) throws Exception {
//		String uri = args[0];
		String uri = "hdfs://172.16.13.122:9000/demo1/wc.input";
		Configuration conf = new Configuration();
//		conf.set("fs.hdfs.impl",org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
		FileSystem fs = FileSystem.get(URI.create(uri), conf);
		InputStream in = null;
		try {
			in = fs.open(new Path(uri));
			IOUtils.copyBytes(in, System.out, 4096, false);
		} finally {
			IOUtils.closeStream(in);
		}
	}
	
}
