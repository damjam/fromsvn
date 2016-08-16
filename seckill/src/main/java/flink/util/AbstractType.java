/**
 * 
 */
package flink.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 *
 */
public abstract class AbstractType implements Type {
	private NameValuePair nameValue = new NameValuePair();

	protected AbstractType(String name, String value) {
		this.nameValue.setName(name);
		this.nameValue.setValue(value);
	}

	@SuppressWarnings("unchecked")
	protected static List getOrderedList(Map all) {
		List orderedList = new ArrayList();
		orderedList.addAll(all.values());

		Collections.sort(orderedList, new Comparator() {
			public int compare(Object o1, Object o2) {
				AbstractType type1 = (AbstractType) o1;
				AbstractType type2 = (AbstractType) o2;

				return type1.getValue().compareTo(type2.getValue());
			}
		});

		return orderedList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flink.model.State#getName()
	 */
	public String getName() {
		return this.nameValue.getName();
	}

	public void setName(String name) {
		this.nameValue.setName(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flink.model.State#getValue()
	 */
	public String getValue() {
		return this.nameValue.getValue();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}

		return this.nameValue.getValue().equals(((Type) obj).getValue());
	}

	@Override
	public int hashCode() {
		return this.nameValue.getValue().hashCode();
	}

	@Override
	public String toString() {
		return this.nameValue.toString();
	}
}
