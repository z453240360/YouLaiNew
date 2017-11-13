package com.bm.chengshiyoutian.youlaiwang.oldall.oldview.utils;

import java.text.Collator;
import java.util.Comparator;

/**
 * 
 * @author xiaanming
 * 
 */
public class PinyinComparator implements Comparator<SortModel> {
	Collator collator = Collator.getInstance();

	public int compare(SortModel o1, SortModel o2) {
		String k1 = o1.getSortLetters();
		String k2 = o2.getSortLetters();
		if (k1.equals("@") || k2.equals("#")) {
			return -1;
		} else if (k1.equals("#") || k2.equals("@")) {
			return 1;
		} else {
			return k1.compareTo(k2);
		}
	}
}