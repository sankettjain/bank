package com.eltropy.bank.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class StringUtils {

	public static final String EMPTY = "";

	public static boolean isEmpty(String s) {

		return null == s || EMPTY.equals(s.trim());
	}

	public static boolean isNotEmpty(String s) {

		return !isEmpty(s);
	}

	public static String toLowerCase(String s) {

		return isEmpty(s) ? EMPTY : s.toLowerCase();
	}

	public static String defaultIfEmpty(String s) {

		return isEmpty(s) ? EMPTY : s;
	}

	public static String captialize(String s) {

		return isEmpty(s) ? EMPTY : (s.substring(0, 1).toUpperCase() + s
				.substring(1).toLowerCase());
	}

//	public static Map<String, Object> toKeyValueMap(String encResponse,
//			String seprator1, String separator2) {
//
//		Map<String, Object> responseParams = new HashMap<String, Object>();
//		for (String keyValue : encResponse.split(seprator1)) {
//			if (StringUtils.isEmpty(keyValue)) {
//				continue;
//			}
//			String[] keyValueArray = keyValue.split(separator2);
//			if (keyValueArray.length != 2) {
//				continue;
//			}
//			responseParams.put(keyValueArray[0], keyValueArray[1]);
//		}
//
//		return responseParams;
//	}

	public static <E extends Object> String collectionSize(
			Collection<E> collection) {

		return null != collection ? String.valueOf(collection.size()) : "null";
	}

	public static String join(Object[] array, String separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	public static String join(Object[] array, String separator, int startIndex,
                              int endIndex) {
		if (array == null) {
			return null;
		}
		int noOfItems = (endIndex - startIndex);
		if (noOfItems <= 0) {
			return EMPTY;
		}

		StringBuilder buf = new StringBuilder(noOfItems * 16);

		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	public static String join(Iterator<?> iterator, char separator) {

		// handle null, zero and one elements before building a buffer
		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return EMPTY;
		}
		Object first = iterator.next();
		if (!iterator.hasNext()) {
			return first.toString();
		}

		// two or more elements
		StringBuilder buf = new StringBuilder(256); // Java default is 16,
													// probably too small
		if (first != null) {
			buf.append(first);
		}

		while (iterator.hasNext()) {
			buf.append(separator);
			Object obj = iterator.next();
			if (obj != null) {
				buf.append(obj);
			}
		}

		return buf.toString();
	}

	static final String AB = "123456789ABCDEFGHJKLMNPQRSTUVWXYZ";
	static final String AB_LOWER = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        //TODO: zero
        static final String NM = "123456789";
	static Random rnd = new Random();

	public static String randomAlphaNumericString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}
        
        public static String randomNumericString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(NM.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	public static String randomAlphaNumericStringWithLowerCase(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB_LOWER.charAt(rnd.nextInt(AB_LOWER.length())));
		return sb.toString();
	}
}
