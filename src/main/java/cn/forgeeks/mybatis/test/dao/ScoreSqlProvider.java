package cn.forgeeks.mybatis.test.dao;

import cn.forgeeks.mybatis.test.pojo.Score;
import org.apache.ibatis.jdbc.SQL;

public class ScoreSqlProvider {

    public String insertSelective(Score record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("score");
        
        if (record.getScid() != null) {
            sql.VALUES("scid", "#{scid,jdbcType=VARCHAR}");
        }
        
        if (record.getSid() != null) {
            sql.VALUES("sid", "#{sid,jdbcType=VARCHAR}");
        }
        
        if (record.getCid() != null) {
            sql.VALUES("cid", "#{cid,jdbcType=VARCHAR}");
        }
        
        if (record.getScore() != null) {
            sql.VALUES("score", "#{score,jdbcType=DECIMAL}");
        }
        
        if (record.getScdate() != null) {
            sql.VALUES("scdate", "#{scdate,jdbcType=DATE}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Score record) {
        SQL sql = new SQL();
        sql.UPDATE("score");
        
        if (record.getSid() != null) {
            sql.SET("sid = #{sid,jdbcType=VARCHAR}");
        }
        
        if (record.getCid() != null) {
            sql.SET("cid = #{cid,jdbcType=VARCHAR}");
        }
        
        if (record.getScore() != null) {
            sql.SET("score = #{score,jdbcType=DECIMAL}");
        }
        
        if (record.getScdate() != null) {
            sql.SET("scdate = #{scdate,jdbcType=DATE}");
        }
        
        sql.WHERE("scid = #{scid,jdbcType=VARCHAR}");
        
        return sql.toString();
    }
}