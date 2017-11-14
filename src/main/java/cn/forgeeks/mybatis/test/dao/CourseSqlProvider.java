package cn.forgeeks.mybatis.test.dao;

import cn.forgeeks.mybatis.test.pojo.Course;
import org.apache.ibatis.jdbc.SQL;

public class CourseSqlProvider {

    public String insertSelective(Course record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("course");
        
        if (record.getCid() != null) {
            sql.VALUES("cid", "#{cid,jdbcType=VARCHAR}");
        }
        
        if (record.getCname() != null) {
            sql.VALUES("cname", "#{cname,jdbcType=VARCHAR}");
        }
        
        if (record.getTid() != null) {
            sql.VALUES("tid", "#{tid,jdbcType=VARCHAR}");
        }
        
        if (record.getCdesc() != null) {
            sql.VALUES("cdesc", "#{cdesc,jdbcType=VARCHAR}");
        }
        
        if (record.getCpublic() != null) {
            sql.VALUES("cpublic", "#{cpublic,jdbcType=DATE}");
        }
        
        if (record.getCplace() != null) {
            sql.VALUES("cplace", "#{cplace,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Course record) {
        SQL sql = new SQL();
        sql.UPDATE("course");
        
        if (record.getCname() != null) {
            sql.SET("cname = #{cname,jdbcType=VARCHAR}");
        }
        
        if (record.getTid() != null) {
            sql.SET("tid = #{tid,jdbcType=VARCHAR}");
        }
        
        if (record.getCdesc() != null) {
            sql.SET("cdesc = #{cdesc,jdbcType=VARCHAR}");
        }
        
        if (record.getCpublic() != null) {
            sql.SET("cpublic = #{cpublic,jdbcType=DATE}");
        }
        
        if (record.getCplace() != null) {
            sql.SET("cplace = #{cplace,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("cid = #{cid,jdbcType=VARCHAR}");
        
        return sql.toString();
    }
}