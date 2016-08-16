/**
 * 
 */
package flink.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

/**
 *
 */
public abstract class AbstractState implements State {
	@SuppressWarnings("unchecked")
	protected static List getOrderedList(Map all) {
		List orderedList = new ArrayList();
		orderedList.addAll(all.values());

		Collections.sort(orderedList, new Comparator() {
			public int compare(Object o1, Object o2) {
				AbstractState state1 = (AbstractState) o1;
				AbstractState state2 = (AbstractState) o2;

				return state1.getValue().compareTo(state2.getValue());
			}
		});

		return orderedList;
	}

	public static String[] values(State[] states) {
		if (ArrayUtils.isEmpty(states)) {
			return null;
		}

		String[] result = new String[states.length];

		for (int i = 0; i < states.length; i++) {
			result[i] = states[i] == null ? null : states[i].getValue();
		}

		return result;
	}

	private NameValuePair nameValue = new NameValuePair();

	protected AbstractState(String name, String value) {
		this.nameValue.setName(name);
		this.nameValue.setValue(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}

		return this.nameValue.getValue().equals(((State) obj).getValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flink.model.State#getName()
	 */
	public String getName() {
		return this.nameValue.getName();
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
	public int hashCode() {
		return this.nameValue.getValue().hashCode();
	}

	@Override
	public String toString() {
		return this.nameValue.toString();
	}
}
