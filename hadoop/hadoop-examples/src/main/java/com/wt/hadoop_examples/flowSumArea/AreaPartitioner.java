package com.wt.hadoop_examples.flowSumArea;

import java.util.HashMap;

import org.apache.hadoop.mapreduce.Partitioner;

public class AreaPartitioner<KEY, VALUE> extends Partitioner<KEY, VALUE> {

	private static HashMap<String, Integer> areaMap;
	
	static {
		loadTableToAreaMap();
	}
	
	@Override
	public int getPartition(KEY key, VALUE value, int numPartitiones) {
		// 从key中拿到手机号，查询手机归属地，不同的省份返回不同的组号
		int areaCode = areaMap.get(key.toString().substring(0, 3))==null ? 0:areaMap.get(key.toString().substring(0, 3));
		return areaCode;
	}

	private static void loadTableToAreaMap() {
		// 建立数据库连接将手机号前缀和省份对应关系加载到areaMap中
		areaMap.put("131", 1);
		areaMap.put("132", 2);
		areaMap.put("133", 3);
//		areaMap.put("134", 4);
//		areaMap.put("135", 5);
	}

}
