package cn.forgeeks.mybatis.test.dao;

import cn.forgeeks.mybatis.test.pojo.Course;

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
public interface CourseMapper {
    @Delete({
        "delete from course",
        "where cid = #{cid,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String cid);

    @Insert({
        "insert into course (cid, cname, ",
        "tid, cdesc, cpublic, ",
        "cplace)",
        "values (#{cid,jdbcType=VARCHAR}, #{cname,jdbcType=VARCHAR}, ",
        "#{tid,jdbcType=VARCHAR}, #{cdesc,jdbcType=VARCHAR}, #{cpublic,jdbcType=DATE}, ",
        "#{cplace,jdbcType=VARCHAR})"
    })
    int insert(Course record);

    @InsertProvider(type=CourseSqlProvider.class, method="insertSelective")
    int insertSelective(Course record);

    @Select({
        "select",
        "cid, cname, tid, cdesc, cpublic, cplace",
        "from course",
        "where cid = #{cid,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="cid", property="cid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="cname", property="cname", jdbcType=JdbcType.VARCHAR),
        @Result(column="tid", property="tid", jdbcType=JdbcType.VARCHAR),
        @Result(column="cdesc", property="cdesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="cpublic", property="cpublic", jdbcType=JdbcType.DATE),
        @Result(column="cplace", property="cplace", jdbcType=JdbcType.VARCHAR)
    })
    Course selectByPrimaryKey(String cid);

    
    @Select({
        "select",
        "cid, cname, tid, cdesc, cpublic, cplace",
        "from course" 
    })
    @Results({
        @Result(column="cid", property="cid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="cname", property="cname", jdbcType=JdbcType.VARCHAR),
        @Result(column="tid", property="tid", jdbcType=JdbcType.VARCHAR),
        @Result(column="cdesc", property="cdesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="cpublic", property="cpublic", jdbcType=JdbcType.DATE),
        @Result(column="cplace", property="cplace", jdbcType=JdbcType.VARCHAR)
    })
	List<Course> selectAll();
    
    @UpdateProvider(type=CourseSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Course record);

    @Update({
        "update course",
        "set cname = #{cname,jdbcType=VARCHAR},",
          "tid = #{tid,jdbcType=VARCHAR},",
          "cdesc = #{cdesc,jdbcType=VARCHAR},",
          "cpublic = #{cpublic,jdbcType=DATE},",
          "cplace = #{cplace,jdbcType=VARCHAR}",
        "where cid = #{cid,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Course record);


}