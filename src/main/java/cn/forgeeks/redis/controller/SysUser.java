package cn.forgeeks.redis.controller;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * 
 * @author hechao
 *
 */
class SysUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String addr;
	private String phone;

	private Date birth;

	private DetaiInfo detail;

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public DetaiInfo getDetail() {
		return detail;
	}

	public void setDetail(DetaiInfo detail) {
		this.detail = detail;
	}

	public SysUser(String id, String name, String addr, String phone, Date birth, DetaiInfo detail) {
		super();
		this.id = id;
		this.name = name;
		this.addr = addr;
		this.phone = phone;
		this.birth = birth;
		this.detail = detail;
	}

	public SysUser(String id, String name, String addr, String phone, Date birth) {
		super();
		this.id = id;
		this.name = name;
		this.addr = addr;
		this.phone = phone;
		this.birth = birth;
	}

	public SysUser(String id, String name, String addr, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.addr = addr;
		this.phone = phone;
	}

	public SysUser(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SysUser(String id) {
		super();
		this.id = id;
	}

	@Override
	public String toString() {
		return "SysUser [id=" + id + ", name=" + name + ", addr=" + addr + ", phone=" + phone + ", birth=" + birth
				+ ", detail=" + detail + "]";
	}

}
