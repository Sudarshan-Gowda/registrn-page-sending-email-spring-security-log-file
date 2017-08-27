package com.star.sud.framework.type;
/*@Author Sudarshan*/
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GenderType implements StarKeyValueInterface {

	private static final Map<String, GenderType> TYPES = new LinkedHashMap<String, GenderType>();

	public static final GenderType MALE = new GenderType("MALE", "Male");
	public static final GenderType FEMALE = new GenderType("FEMALE", "Female");

	public GenderType() {
	}

	@Override
	public GenderType getInstance(String type) {
		return TYPES.get(type);
	}

	protected String friendlyType;
	protected String type;

	public String getType() {
		return type;
	}

	private void setType(String type) {
		this.type = type;
		if (!TYPES.containsKey(type)) {
			TYPES.put(type, this);
		}
	}

	public GenderType(String type, String friendlyType) {
		this.friendlyType = friendlyType;
		setType(type);
	}

	public String getFriendlyType() {
		return friendlyType;
	}

	public void setFriendlyType(String friendlyType) {
		this.friendlyType = friendlyType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!getClass().isAssignableFrom(obj.getClass()))
			return false;
		GenderType other = (GenderType) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public static List<GenderType> getList() {
		List<GenderType> list = new ArrayList<GenderType>(TYPES.values());
		return list;
	}
}
