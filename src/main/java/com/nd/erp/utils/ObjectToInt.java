package com.nd.erp.utils;

public class ObjectToInt {
	public static int parseInt(Object o, int defaultValue) {
		int num = defaultValue;
		try {
			num = Integer.parseInt(o.toString());
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
		return num;
	}
}
