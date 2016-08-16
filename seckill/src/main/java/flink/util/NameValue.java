/**
 * 
 */
package flink.util;

/**
 * name value pair.
 * 
 */
public interface NameValue {
	String getName();

	String getValue();
}

class NameValuePair implements NameValue {
	private String name;
	private String value;

	public NameValuePair() {
	}

	public NameValuePair(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append(this.getName());
		sb.append(":");
		sb.append("\"");
		sb.append(this.getValue());
		sb.append("\"");
		sb.append("}");

		return sb.toString();
	}

}