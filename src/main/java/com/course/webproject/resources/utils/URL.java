package com.course.webproject.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {
	
	public static String decodeParam(String param) {
		try {
			return URLDecoder.decode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static List<Integer> decodeIntList(String param){
		String[] vet = param.split(",");
		List<Integer> list = new ArrayList<>();
		for(String u: vet) {
			list.add(Integer.parseInt(u));
		}
		return list;
		//return Arrays.asList(param.split(",")).stream().map(u -> Integer.parseInt(u)).collect(Collectors.toList());
	}
}
