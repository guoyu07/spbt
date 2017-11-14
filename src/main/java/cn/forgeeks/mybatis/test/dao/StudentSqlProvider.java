package cn.forgeeks.mybatis.test.dao;

import cn.forgeeks.mybatis.test.pojo.Student;
import org.apache.ibatis.jdbc.SQL;

public class StudentSqlProvider {

    public String insertSelective(Student record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("student");
        
        if (record.getSid() != null) {
            sql.VALUES("sid", "#{sid,jdbcType=VARCHAR}");
        }
        
        if (record.getSname() != null) {
            sql.VALUES("sname", "#{sname,jdbcType=VARCHAR}");
        }
        
        if (record.getSage() != null) {
            sql.VALUES("sage", "#{sage,jdbcType=INTEGER}");
        }
        
        if (record.getSsex() != null) {
            sql.VALUES("ssex", "#{ssex,jdbcType=VARCHAR}");
        }
        
        if (record.getSnumber() != null) {
            sql.VALUES("snumber", "#{snumber,jdbcType=VARCHAR}");
        }
        
        if (record.getSphone() != null) {
            sql.VALUES("sphone", "#{sphone,jdbcType=VARCHAR}");
        }
        
        if (record.getSleave() != null) {
            sql.VALUES("sleave", "#{sleave,jdbcType=DATE}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Student record) {
        SQL sql = new SQL();
        sql.UPDATE("student");
        
        if (record.getSname() != null) {
            sql.SET("sname = #{sname,jdbcType=VARCHAR}");
        }
        
        if (record.getSage() != null) {
            sql.SET("sage = #{sage,jdbcType=INTEGER}");
        }
        
        if (record.getSsex() != null) {
            sql.SET("ssex = #{ssex,jdbcType=VARCHAR}");
        }
        
        if (record.getSnumber() != null) {
            sql.SET("snumber = #{snumber,jdbcType=VARCHAR}");
        }
        
        if (record.getSphone() != null) {
            sql.SET("sphone = #{sphone,jdbcType=VARCHAR}");
        }
        
        if (record.getSleave() != null) {
            sql.SET("sleave = #{sleave,jdbcType=DATE}");
        }
        
        sql.WHERE("sid = #{sid,jdbcType=VARCHAR}");
        
        return sql.toString();
    }
}