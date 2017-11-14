package cn.forgeeks.mybatis.test;

import java.util.Date;

/**
 * sid VARCHAR (32) PRIMARY KEY, sname VARCHAR (32), sage INT, ssex VARCHAR (8),
 * snumber VARCHAR (16), sphone VARCHAR (16), sleave DATE
 * 
 * @author hechao
 *
 */
public class Student {
	private String sid;
	private String sname;
	private Integer sage;
	private String ssex;
	private String snumber;
	private Date sleave;

	
	public Student() {
		super(); 
	}

	public Student(String sid, String sname, Integer sage, String ssex, String snumber, Date sleave) {
		super();
		this.sid = sid;
		this.sname = sname;
		this.sage = sage;
		this.ssex = ssex;
		this.snumber = snumber;
		this.sleave = sleave;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public Integer getSage() {
		return sage;
	}

	public void setSage(Integer sage) {
		this.sage = sage;
	}

	public String getSsex() {
		return ssex;
	}

	public void setSsex(String ssex) {
		this.ssex = ssex;
	}

	public String getSnumber() {
		return snumber;
	}

	public void setSnumber(String snumber) {
		this.snumber = snumber;
	}

	public Date getSleave() {
		return sleave;
	}

	public void setSleave(Date sleave) {
		this.sleave = sleave;
	}

	@Override
	public String toString() {
		return "Student [sid=" + sid + ", sname=" + sname + ", sage=" + sage + ", ssex=" + ssex + ", snumber=" + snumber
				+ ", sleave=" + sleave + "]";
	}

}
