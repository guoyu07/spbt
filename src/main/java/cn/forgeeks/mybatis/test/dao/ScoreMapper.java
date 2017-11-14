package cn.forgeeks.mybatis.test.dao;

import cn.forgeeks.mybatis.test.pojo.Score;
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
public interface ScoreMapper {
    @Delete({
        "delete from score",
        "where scid = #{scid,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String scid);

    @Insert({
        "insert into score (scid, sid, ",
        "cid, score, scdate)",
        "values (#{scid,jdbcType=VARCHAR}, #{sid,jdbcType=VARCHAR}, ",
        "#{cid,jdbcType=VARCHAR}, #{score,jdbcType=DECIMAL}, #{scdate,jdbcType=DATE})"
    })
    int insert(Score record);

    @InsertProvider(type=ScoreSqlProvider.class, method="insertSelective")
    int insertSelective(Score record);

    @Select({
        "select",
        "scid, sid, cid, score, scdate",
        "from score",
        "where scid = #{scid,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="scid", property="scid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="sid", property="sid", jdbcType=JdbcType.VARCHAR),
        @Result(column="cid", property="cid", jdbcType=JdbcType.VARCHAR),
        @Result(column="score", property="score", jdbcType=JdbcType.DECIMAL),
        @Result(column="scdate", property="scdate", jdbcType=JdbcType.DATE)
    })
    Score selectByPrimaryKey(String scid);

    @UpdateProvider(type=ScoreSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Score record);

    @Update({
        "update score",
        "set sid = #{sid,jdbcType=VARCHAR},",
          "cid = #{cid,jdbcType=VARCHAR},",
          "score = #{score,jdbcType=DECIMAL},",
          "scdate = #{scdate,jdbcType=DATE}",
        "where scid = #{scid,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Score record);
}