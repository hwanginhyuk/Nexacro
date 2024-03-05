package kr.co.seoulit.insa.salarysvc.salarystdinfomgmt.mapper;


import org.apache.ibatis.annotations.Mapper;
import kr.co.seoulit.insa.salarysvc.salarystdinfomgmt.to.BaseSalaryTO;

import java.util.ArrayList;

@Mapper
public interface BaseSalaryMapper {
	public ArrayList<BaseSalaryTO> selectBaseSalaryList(); //base-salary1
	public void updateBaseSalary(BaseSalaryTO baseSalary);
//	public void insertPosition(BaseSalaryTO position);
//	public void deletePosition(BaseSalaryTO position);
}
