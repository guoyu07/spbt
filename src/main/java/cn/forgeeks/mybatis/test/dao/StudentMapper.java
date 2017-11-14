package cn.forgeeks.mybatis.test.dao;

import cn.forgeeks.mybatis.test.pojo.Student;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface StudentMapper {
    @Delete({
        "delete from student",
        "where sid = #{sid,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String sid);

    @Insert({
        "insert into student (sid, sname, ",
        "sage, ssex, snumber, ",
        "sphone, sleave)",
        "values (#{sid,jdbcType=VARCHAR}, #{sname,jdbcType=VARCHAR}, ",
        "#{sage,jdbcType=INTEGER}, #{ssex,jdbcType=VARCHAR}, #{snumber,jdbcType=VARCHAR}, ",
        "#{sphone,jdbcType=VARCHAR}, #{sleave,jdbcType=DATE})"
    })
    int insert(Student record);

    @InsertProvider(type=StudentSqlProvider.class, method="insertSelective")
    int insertSelective(Student record);

    @Select({
        "select",
        "sid, sname, sage, ssex, snumber, sphone, sleave",
        "from student",
        "where sid = #{sid,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="sid", property="sid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="sname", property="sname", jdbcType=JdbcType.VARCHAR),
        @Result(column="sage", property="sage", jdbcType=JdbcType.INTEGER),
        @Result(column="ssex", property="ssex", jdbcType=JdbcType.VARCHAR),
        @Result(column="snumber", property="snumber", jdbcType=JdbcType.VARCHAR),
        @Result(column="sphone", property="sphone", jdbcType=JdbcType.VARCHAR),
        @Result(column="sleave", property="sleave", jdbcType=JdbcType.DATE)
    })
    Student selectByPrimaryKey(String sid);
    
    @Select({
    	"select",
    	"sid, sname, sage, ssex, snumber, sphone, sleave",
    	"from student"
    })
    @Results({
    	@Result(column="sid", property="sid", jdbcType=JdbcType.VARCHAR, id=true),
    	@Result(column="sname", property="sname", jdbcType=JdbcType.VARCHAR),
    	@Result(column="sage", property="sage", jdbcType=JdbcType.INTEGER),
    	@Result(column="ssex", property="ssex", jdbcType=JdbcType.VARCHAR),
    	@Result(column="snumber", property="snumber", jdbcType=JdbcType.VARCHAR),
    	@Result(column="sphone", property="sphone", jdbcType=JdbcType.VARCHAR),
    	@Result(column="sleave", property="sleave", jdbcType=JdbcType.DATE)
    })
    List<Student> selectAll();
    

    @UpdateProvider(type=StudentSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Student record);

    @Update({
        "update student",
        "set sname = #{sname,jdbcType=VARCHAR},",
          "sage = #{sage,jdbcType=INTEGER},",
          "ssex = #{ssex,jdbcType=VARCHAR},",
          "snumber = #{snumber,jdbcType=VARCHAR},",
          "sphone = #{sphone,jdbcType=VARCHAR},",
          "sleave = #{sleave,jdbcType=DATE}",
        "where sid = #{sid,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Student record);
}