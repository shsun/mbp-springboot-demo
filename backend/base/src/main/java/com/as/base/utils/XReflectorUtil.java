package com.as.base.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.apache.commons.beanutils.PropertyUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @author shsun
 * 
 */
public class XReflectorUtil {
	/**
	 * 
	 * @param source
	 * @param target
	 */
	public static Object merge(Object source, Object target) {
		if (source != null && target != null) {
			PropertyDescriptor[] list = PropertyUtils.getPropertyDescriptors(source);
			for (int i = 0; i < list.length; i++) {
				try {
					String name = list[i].getName();
					Object value = PropertyUtils.getProperty(source, name);
					if (value != null) {
						PropertyUtils.setProperty(target, name, value);
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// e.printStackTrace();
				}
			}
		}
		return target;
	}

	/**
	 *
	 * @param list
	 * @return
	 */
	public static List<Map<String, Object>> pojos2maps(List list) {
		List<Map<String, Object>> rst = Lists.newArrayList();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = Maps.newHashMap();
			XReflectorUtil.merge(list.get(i), map);
			rst.add(map);
		}
		return rst;
	}


	/**
	 *
	 * @param source
	 * @param target
	 * @return
	 */
	public static Object map2Object(Map<String, Object> source, Object target) {
		if (source != null && target != null) {
			for (String key : source.keySet()) {
				try {
					PropertyUtils.setProperty(target, key, source.get(key));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return target;
	}

	public static PropertyDescriptor[] getPropertyDescriptorsWithoutClass(Object object) {
		Map<String, PropertyDescriptor> map = getPropertyDescriptorMapWithoutClass(object);
		PropertyDescriptor[] result = new PropertyDescriptor[map.size()];
		//
		int i = 0;
		Set<Map.Entry<String, PropertyDescriptor>> set = map.entrySet();
		for (Iterator<Map.Entry<String, PropertyDescriptor>> it = set.iterator(); it.hasNext();) {
			Map.Entry<String, PropertyDescriptor> entry = (Map.Entry<String, PropertyDescriptor>) it.next();
			result[i++] = entry.getValue();
		}
		//
		return result;
	}

	public static Map<String, PropertyDescriptor> getPropertyDescriptorMapWithoutClass(Object object) {
		String CLASS = "class";
		Map<String, PropertyDescriptor> map = new HashMap<String, PropertyDescriptor>();
		PropertyDescriptor[] list = PropertyUtils.getPropertyDescriptors(object);
		//
		for (int i = 0; i < list.length; i++) {
			if (!CLASS.equals(list[i].getName())) {
				map.put(list[i].getName(), list[i]);
			}
		}
		return map;
	}

}
