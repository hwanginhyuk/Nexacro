package kr.co.seoulit.insa.attdsvc.attdmgmt.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.seoulit.insa.attdsvc.attdmgmt.to.EmpListTO;
import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.insa.attdsvc.attdmgmt.to.DayAttdTO;
import kr.co.seoulit.insa.commsvc.systemmgmt.to.ResultTO;
@Mapper
public interface DailyAttndMapper {
	public ArrayList<DayAttdTO> selectDayAttdList(HashMap<String , Object> map);
	public ResultTO batchInsertDayAttd(HashMap<String , Object> map); //✔️일근태 등록 - [출근]/[퇴근]
	public void insertDayAttd(DayAttdTO dayAttd);
	public void deleteDayAttd(DayAttdTO dayAttd);
	public ArrayList<EmpListTO> findEmpList(String deptName);
	public ArrayList<DayAttdTO> findEmpList1(String applyDay);	//✔️신규 출퇴근정보조회 - [조회하기]
}
