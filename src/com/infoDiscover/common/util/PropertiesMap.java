package com.infoDiscover.common.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PropertiesMap {

	private final Map<String, Object> values = new HashMap<String, Object>();

	public PropertiesMap(Map<String, Object> map) {
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			values.put(entry.getKey(), toInternalType(entry.getValue()));
		}
	}

	public Object getValue(String key) {
		return values.get(key);
	}

	public Map<String, Object> serialize() {
		// TODO Nice with sorted, but TreeMap the best?
		Map<String, Object> result = new TreeMap<String, Object>();
		for (Map.Entry<String, Object> entry : values.entrySet()) {
			result.put(entry.getKey(), toSerializedType(entry.getValue()));
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object toInternalType(Object value) {
		if (value instanceof List) {
			List list = (List) value;
			if (list.isEmpty()) {
				return new byte[0];
			} else {
				Object first = list.get(0);
				if (first instanceof String) {
					return StringUtil.listToArray(list);
				} else if (first instanceof Number) {
					return numberArray(list);
				} else if (first instanceof Boolean) {
					return booleanArray(list);
				} else {
					throw new RuntimeException(
							"Unsupported array type "
									+ first.getClass()
									+ ". Supported array types are arrays of all java primitives ("
									+ "byte[], char[], short[], int[], long[], float[], double[]) "
									+ "and String[]");
				}
			}
		} else {
			return assertSupportedPropertyValue(value);
		}
	}

	public static Object assertSupportedPropertyValue(Object value) {
		if (value == null) {
			throw new RuntimeException("null value not supported");
		}
		final Class<?> type = value.getClass();
		/*
		 * 1、此类型是否是支持类型 2、如果为数组类型，则此数组组件类型是否是支持类型
		 */
		if (isSupportedType(type) || type.isArray()
				&& isSupportedType(type.getComponentType())) {
			return value;
		}
		throw new RuntimeException(
				"Unsupported value type "
						+ type
						+ "."
						+ " Supported value types are all java primitives (byte, char, short, int, "
						+ "long, float, double) and String, as well as arrays of all those types");
	}

	private static boolean isSupportedType(Class<?> type) {
		/*
		 * 1、是否是基础类型。isPrimitive（）判断 2、判断是否是String类型或者其子类类型
		 * 3、判断是否是number类型或者其子类类型 4、判断是否是boolean类型或者其子类类型
		 */
		return type.isPrimitive() || String.class.isAssignableFrom(type)
				|| Number.class.isAssignableFrom(type)
				|| Boolean.class.isAssignableFrom(type);
	}

	private static Boolean[] booleanArray(List<Boolean> list) {
		return list.toArray(new Boolean[list.size()]);
	}

	private static Number[] numberArray(List<Number> numbers) {
		Number[] internal = new Number[numbers.size()];
		for (int i = 0; i < internal.length; i++) {
			Number number = numbers.get(i);
			if (number instanceof Float || number instanceof Double) {
				number = number.doubleValue();
			} else {
				number = number.longValue();
			}
			internal[i] = number;
		}
		final Number[] result;
		if (internal[0] instanceof Double) {
			result = new Double[internal.length];
		} else {
			result = new Long[internal.length];
		}
		System.arraycopy(internal, 0, result, 0, internal.length);
		return result;
	}

	private Object toSerializedType(Object value) {
		if (value.getClass().isArray()) {
			// getComponentType（）返回表示数组组件类型的 Class。如果此类不表示数组类，则此方法返回 null
			// isPrimitive（）判定指定的 Class 对象是否表示一个基本类型
			if (value.getClass().getComponentType().isPrimitive()) {
				int size = Array.getLength(value);
				List<Object> result = new ArrayList<Object>();
				for (int i = 0; i < size; i++) {
					result.add(Array.get(value, i));
				}
				return result;
			} else {
				return Arrays.asList((Object[]) value);
			}
		} else {
			return value;
		}
	}

	public boolean isEmpty() {
		return values.isEmpty();
	}
}