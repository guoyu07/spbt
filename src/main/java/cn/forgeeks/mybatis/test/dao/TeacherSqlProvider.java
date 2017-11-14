package cn.forgeeks.mybatis.test.dao;

import cn.forgeeks.mybatis.test.pojo.Teacher;
import org.apache.ibatis.jdbc.SQL;

public class TeacherSqlProvider {

    public String insertSelective(Teacher record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("teacher");
        
        if (record.getTid() != null) {
            sql.VALUES("tid", "#{tid,jdbcType=VARCHAR}");
        }
        
        if (record.getTname() != null) {
            sql.VALUES("tname", "#{tname,jdbcType=VARCHAR}");
        }
        
        if (record.getTnumber() != null) {
            sql.VALUES("tnumber", "#{tnumber,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Teacher record) {
        SQL sql = new SQL();
        sql.UPDATE("teacher");
        
        if (record.getTname() != null) {
            sql.SET("tname = #{tname,jdbcType=VARCHAR}");
        }
        
        if (record.getTnumber() != null) {
            sql.SET("tnumber = #{tnumber,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("tid = #{tid,jdbcType=VARCHAR}");
        
        return sql.toString();
    }
}