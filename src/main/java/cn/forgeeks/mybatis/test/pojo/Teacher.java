package cn.forgeeks.mybatis.test.pojo;

public class Teacher {
    private String tid;

    private String tname;

    private String tnumber;

    
    public Teacher(String tid, String tname, String tnumber) {
		super();
		this.tid = tid;
		this.tname = tname;
		this.tnumber = tnumber;
	}

	public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid == null ? null : tid.trim();
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname == null ? null : tname.trim();
    }

    public String getTnumber() {
        return tnumber;
    }

    public void setTnumber(String tnumber) {
        this.tnumber = tnumber == null ? null : tnumber.trim();
    }
}