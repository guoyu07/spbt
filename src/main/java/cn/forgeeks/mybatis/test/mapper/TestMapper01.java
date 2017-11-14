package cn.forgeeks.mybatis.test.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

/**
 * 50个重要sql
 * 
 * @author hechao
 *
 */
@Mapper
public interface TestMapper01 {

	/**
	 * 1.查询“001”课程比“002”课程成绩高的所有学生的学号；
	 */
	@Select({ " SELECT s2.snumber FROM ( SELECT sc1.sid, * sc1.score FROM score sc1 ",
			"  left join course c on c.cid=sc1.cid WHERE c.cname = '高等数学上' ) t1, ( ",
			" SELECT sc1.sid, sc1.score FROM score sc1 left join course c on ",
			" c.cid=sc1.cid WHERE c.cname = '高等数学下' ) t2 , student s2 where ",
			" t1.sid = s2.sid and t1.score > t2.score AND t1.sid = t2.sid " })
	@Results({ @Result(column = "snumber", property = "snumber", jdbcType = JdbcType.VARCHAR) })
	List<HashMap<String, Object>> select01();

	/**
	 * 2 查询平均成绩大于60分的同学的学号和平均成绩；
	 */

	@Select({
			"select avg(sc.score) as avg_score, s.snumber  from score sc left join student s  on s.sid=sc.sid group by sc.sid  having  avg_score > 60" })
	@Results({ @Result(column = "tid", property = "tid", jdbcType = JdbcType.VARCHAR) })
	List<HashMap<String, Object>> select02();

	/**
	 * 3 、查询所有同学的学号、姓名、选课数、总成绩；
	 */

	@Select({
			"select SUM(sc.score) as sumscore, count(sc.sid) as coursecount , s.snumber ,s.sname ,sc.cid from score sc left join student s on sc.sid = s.sid group  by sc.sid " })
	List<HashMap<String, Object>> select03();

	/**
	 * 4、查询姓“李”的老师的个数
	 * 
	 */

	@Select({
			"select SUM(sc.score) as sumscore, count(sc.sid) as coursecount , s.snumber ,s.sname ,sc.cid from score sc left join student s on sc.sid = s.sid group  by sc.sid " })
	List<HashMap<String, Object>> select04();

	/**
	 * 
	 * 5、查询没学过“叶平”老师课的同学的学号、姓名；
	 */
	@Select({ "select s.snumber ,s.sname from score t left join student s on s.sid=t.sid ",
			"where t.cid not in  ( select cid from course cou left join teacher tea on tea.tid=cou.tid where tea.tname='心且工')" })
	List<HashMap<String, Object>> select05();

	
	
	
	
	
	
}
