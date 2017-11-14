package cn.forgeeks.redis.controller;

import java.io.Serializable;

public class DetaiInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String useId;
	private String desc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUseId() {
		return useId;
	}

	public void setUseId(String useId) {
		this.useId = useId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public DetaiInfo(String id, String useId, String desc) {
		super();
		this.id = id;
		this.useId = useId;
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "DetaiInfo [id=" + id + ", useId=" + useId + ", desc=" + desc + "]";
	}

}
