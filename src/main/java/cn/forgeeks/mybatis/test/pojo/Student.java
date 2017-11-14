package cn.forgeeks.mybatis.test.pojo;

import java.util.Date;

public class Student {
    private String sid;

    private String sname;

    private Integer sage;

    private String ssex;

    private String snumber;

    private String sphone;

    private Date sleave;

    
    
    public Student() {
		super(); 
	}

	public Student(String sid, String sname, Integer sage, String ssex, String snumber, String sphone, Date sleave) {
		super();
		this.sid = sid;
		this.sname = sname;
		this.sage = sage;
		this.ssex = ssex;
		this.snumber = snumber;
		this.sphone = sphone;
		this.sleave = sleave;
	}
    
    public Student(String sid, String sname, Integer sage, String ssex, String snumber, String sphone ) {
    	super();
    	this.sid = sid;
    	this.sname = sname;
    	this.sage = sage;
    	this.ssex = ssex;
    	this.snumber = snumber;
    	this.sphone = sphone;
    }

	public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname == null ? null : sname.trim();
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
        this.ssex = ssex == null ? null : ssex.trim();
    }

    public String getSnumber() {
        return snumber;
    }

    public void setSnumber(String snumber) {
        this.snumber = snumber == null ? null : snumber.trim();
    }

    public String getSphone() {
        return sphone;
    }

    public void setSphone(String sphone) {
        this.sphone = sphone == null ? null : sphone.trim();
    }

    public Date getSleave() {
        return sleave;
    }

    public void setSleave(Date sleave) {
        this.sleave = sleave;
    }
}