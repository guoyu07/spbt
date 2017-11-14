package cn.forgeeks.mybatis.test.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Score {
    private String scid;

    private String sid;

    private String cid;

    private BigDecimal score;

    private Date scdate;

    public Score() {
		super(); 
	}

	public Score(String scid, String sid, String cid, BigDecimal score, Date scdate) {
		super();
		this.scid = scid;
		this.sid = sid;
		this.cid = cid;
		this.score = score;
		this.scdate = scdate;
	}

	public String getScid() {
        return scid;
    }

    public void setScid(String scid) {
        this.scid = scid == null ? null : scid.trim();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Date getScdate() {
        return scdate;
    }

    public void setScdate(Date scdate) {
        this.scdate = scdate;
    }
}