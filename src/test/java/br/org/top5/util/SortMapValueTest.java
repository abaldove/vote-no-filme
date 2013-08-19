package br.org.top5.util;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class SortMapValueTest {

	@Test
	public void testSortByComparator() {
		Map<String, Integer> unsortMap = new HashMap<String, Integer>();
		unsortMap.put("Superman", 1);
		unsortMap.put("Wolverine", 3);
		unsortMap.put("Iron Man", 2);
		Map<String, Integer> sortedMap = SortMapValue.descendingOrder(unsortMap );
	}

}
