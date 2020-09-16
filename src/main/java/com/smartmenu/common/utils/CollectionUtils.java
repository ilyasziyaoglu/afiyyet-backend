package com.smartmenu.common.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-06-29
 */

@Component
public class CollectionUtils {

	public List<Object> andCollections(List<Object> list1, List<Object> list2) {
		return list1.stream().filter(list2::contains).collect(Collectors.toList());
	}

	public List<Object> orCollections(List<Object> list1, List<Object> list2) {
		List<Object> result = new ArrayList<>(list2);
		result.addAll(list1.stream().filter(o -> !list2.contains(o)).collect(Collectors.toList()));
		return result;
	}

	public List<Object> diffCollections(List<Object> list1, List<Object> list2) {
		return list1.stream().filter(o -> !list2.contains(o)).collect(Collectors.toList());
	}


}
