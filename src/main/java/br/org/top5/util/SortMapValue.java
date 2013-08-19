package br.org.top5.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SortMapValue {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Integer> descendingOrder(
			Map<String, Integer> unsortMap) {
		List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(
				unsortMap.entrySet());

		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				int left = o1.getValue();
				int right = o2.getValue();
				final int BEFORE = -1;
				final int EQUAL = 0;
				final int AFTER = 1;
				if (left == right)
					return EQUAL;

				if (left > right)
					return BEFORE;
				if (left < right)
					return AFTER;

				return EQUAL;

			}
		});

		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

}
