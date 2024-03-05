package kr.co.seoulit.insa.attdsvc.attdappvl.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.ibatis.annotations.Mapper;
import kr.co.seoulit.insa.attdsvc.attdappvl.to.DayAttdMgtTO;

@Mapper
public interface DailyAttndMgtMapper {
	public HashMap<String, Object> batchDayAttdMgtProcess(HashMap<String, Object> map);
	//✔️[전체마감하기] - (프로시저 조회) 프로시저 결과가 Hashmap에 담김
	// (왜 Hashmap에 담기지?) //earlyLeaveHour,HalfHolidayStatus를 결과는 프로시저에 없음
	public void updateDayAttdMgtList(DayAttdMgtTO dayAttdMgt); //✔️[전체마감취소](안쓰임)
	public ArrayList<DayAttdMgtTO> findAttdList(String applyDay); //✔️[조회하기](SELECT문)
	//Select문이 여러행이 조회되는데, 여러행이 조회되면 List에 담아서 반환해야한다.
	public void updateDayAttd(DayAttdMgtTO restAttd); //✔️[저장](UPDATE문)
}
