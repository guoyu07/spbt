package cn.forgeeks.mybatis.test.dao;

import cn.forgeeks.mybatis.test.pojo.Teacher;
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
public interface TeacherMapper {
    @Delete({
        "delete from teacher",
        "where tid = #{tid,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String tid);

    @Insert({
        "insert into teacher (tid, tname, ",
        "tnumber)",
        "values (#{tid,jdbcType=VARCHAR}, #{tname,jdbcType=VARCHAR}, ",
        "#{tnumber,jdbcType=VARCHAR})"
    })
    int insert(Teacher record);

    @InsertProvider(type=TeacherSqlProvider.class, method="insertSelective")
    int insertSelective(Teacher record);

    @Select({
        "select",
        "tid, tname, tnumber",
        "from teacher",
        "where tid = #{tid,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="tid", property="tid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="tname", property="tname", jdbcType=JdbcType.VARCHAR),
        @Result(column="tnumber", property="tnumber", jdbcType=JdbcType.VARCHAR)
    })
    Teacher selectByPrimaryKey(String tid);

    @UpdateProvider(type=TeacherSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Teacher record);

    @Update({
        "update teacher",
        "set tname = #{tname,jdbcType=VARCHAR},",
          "tnumber = #{tnumber,jdbcType=VARCHAR}",
        "where tid = #{tid,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Teacher record);
}