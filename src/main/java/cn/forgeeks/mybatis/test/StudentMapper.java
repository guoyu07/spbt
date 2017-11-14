package cn.forgeeks.mybatis.test;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentMapper {
	
	@Select(" select * from student ")
	Student sleectStudent01(Student student);
}
