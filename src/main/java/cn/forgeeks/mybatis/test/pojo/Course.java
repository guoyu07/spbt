package cn.forgeeks.mybatis.test.pojo;

import java.util.Date;

public class Course {
    private String cid;

    private String cname;

    private String tid;

    private String cdesc;

    private Date cpublic;

    private String cplace;

    
    
    public Course() {
		super(); 
	}

	public Course(String cid, String cname, String tid, String cdesc, Date cpublic, String cplace) {
		super();
		this.cid = cid;
		this.cname = cname;
		this.tid = tid;
		this.cdesc = cdesc;
		this.cpublic = cpublic;
		this.cplace = cplace;
	}

	public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid == null ? null : tid.trim();
    }

    public String getCdesc() {
        return cdesc;
    }

    public void setCdesc(String cdesc) {
        this.cdesc = cdesc == null ? null : cdesc.trim();
    }

    public Date getCpublic() {
        return cpublic;
    }

    public void setCpublic(Date cpublic) {
        this.cpublic = cpublic;
    }

    public String getCplace() {
        return cplace;
    }

    public void setCplace(String cplace) {
        this.cplace = cplace == null ? null : cplace.trim();
    }
}