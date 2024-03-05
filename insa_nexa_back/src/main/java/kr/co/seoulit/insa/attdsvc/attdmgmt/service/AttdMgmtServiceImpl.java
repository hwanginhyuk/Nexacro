package kr.co.seoulit.insa.attdsvc.attdmgmt.service;

import java.util.ArrayList;
import java.util.HashMap;


import kr.co.seoulit.insa.attdsvc.attdmgmt.Mapstruct.RestAttdMapstruct;
import kr.co.seoulit.insa.attdsvc.attdmgmt.entity.RestAttdEntity;
import kr.co.seoulit.insa.attdsvc.attdmgmt.repository.DeptsRepository;
import kr.co.seoulit.insa.attdsvc.attdmgmt.to.DeptListTO;
import kr.co.seoulit.insa.attdsvc.attdmgmt.to.EmpListTO;
import kr.co.seoulit.insa.commsvc.systemmgmt.repository.DayAttdRepository;
import kr.co.seoulit.insa.commsvc.systemmgmt.repository.RestAttdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import kr.co.seoulit.insa.attdsvc.attdmgmt.mapper.DailyAttndMapper;
import kr.co.seoulit.insa.attdsvc.attdmgmt.mapper.ExcusedAttndMapper;
import kr.co.seoulit.insa.attdsvc.attdmgmt.to.DayAttdTO;
import kr.co.seoulit.insa.attdsvc.attdmgmt.to.RestAttdTO;
import kr.co.seoulit.insa.commsvc.systemmgmt.to.ResultTO;

@Service
@RequiredArgsConstructor
public class AttdMgmtServiceImpl implements AttdMgmtService {
	
	private final DailyAttndMapper dayAttdMapper;
	private final ExcusedAttndMapper restAttdMapper;
	private final DeptsRepository deptRepository;
	private final RestAttdRepository restAttdRepository;
	private final DayAttdRepository dayAttdRepository;
	private final RestAttdMapstruct restAttdMapstruct;

	// ✔️일근태 - [리스트조회]
	@Override
	public ArrayList<DayAttdTO> findDayAttdList(String empCode, String applyDay) {
		//⭐ 사용자에게 특정 매개변수를 받아오는 경우 HashMap을 통해 키,값의 자료구조로 저장한다
		HashMap<String , Object> map = new HashMap<>();
		map.put("empCode", empCode);
		map.put("applyDay", applyDay);
		
		ArrayList<DayAttdTO> dayAttdList = null;
		dayAttdList = dayAttdMapper.selectDayAttdList(map);
		//dayAttdList = dayAttdRepository.findAll(map);
		return dayAttdList;

	}


	//✔️일근태 등록 - [출근]/[퇴근]
	@Override
	public ResultTO registDayAttd(DayAttdTO dayAttd) {
		
		HashMap<String , Object> map = new HashMap<>(); 
		map.put("empCode",dayAttd.getEmpCode());
		map.put("attdTypeCode",dayAttd.getAttdTypeCode());
		map.put("attdTypeName",dayAttd.getAttdTypeName());
		map.put("applyDay",dayAttd.getApplyDay());
		map.put("time",dayAttd.getTime());
		
		dayAttdMapper.batchInsertDayAttd(map);
		ResultTO resultTO = new ResultTO();
		resultTO.setErrorCode((String) map.get("errorCode"));
		resultTO.setErrorMsg((String) map.get("errorMsg")); 
		return resultTO;

	}

	@Override
	public void removeDayAttdList(ArrayList<DayAttdTO> dayAttdList) {

		for (DayAttdTO dayAttd : dayAttdList) {
			dayAttdMapper.deleteDayAttd(dayAttd);
		}

	}

	@Override
	public void insertDayAttd(DayAttdTO dayAttd) {
		dayAttdRepository.save(dayAttd);
	}


	//✔️ 근태외 조회 - [조회]
	@Override
	@Cacheable(key = "#empCode + ':' + #code",value = "findRestAttdList")
	public ArrayList<RestAttdTO> findRestAttdList(String empCode, String startDate, String endDate, String code) {
	// 매개변수로 code를 가져옴
		System.out.println("=============😐근태외 조회 퍼사드 시작😐===============");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("empCode", empCode);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		System.out.println("map ======== " + map); //{code=ASC002, empCode=A490091, endDate=20230831, startDate=20230801}

		ArrayList<RestAttdTO> restAttdList = null;
		if (code.equals("ASC001") || code.equals("ASC004") || code.equals("ASC009")) {
			restAttdList = restAttdMapper.selectRestAttdList1(map); //근태외 조회
		}else if(code.equals("ASC002") || code.equals("ASC003") ){
			restAttdList = restAttdMapper.selectRestAttdList2(map); //출장 조회
		}else{
			restAttdList = restAttdMapper.selectRestAttdList3(map); //연차 조회
		}
		System.out.println("restAttdList =============== " + restAttdList);
		System.out.println("=============😐근태외 조회 퍼사드 종료😐===============");
		return restAttdList;

	}

	// ✔️근태외신청 - [신청하기] + 🆕출장/교육 신청 - [신청하기]
	@Override
	public void registRestAttd(RestAttdTO restAttd) {

		restAttdMapper.insertRestAttd(restAttd);

	}



	//✔️ 근태외신청 - [삭제] -jpa
	@Override
	public void removeRestAttdList(ArrayList<RestAttdTO> restAttdList) {

		System.out.println("jpa 되나 테스트 🥶");
		for (RestAttdTO restAttd : restAttdList) {
			RestAttdEntity restAttdEntity = this.restAttdMapstruct.toEntity(restAttd);
			restAttdRepository.delete(restAttdEntity);
//			restAttdMapper.deleteRestAttd(restAttd);
		}
		System.out.println("jpa 되나 테스트 🥶");
	}

	@Override
	public ArrayList<DeptListTO> findDeptList() {

		ArrayList<DeptListTO> deptLists = null;
		deptLists = (ArrayList<DeptListTO>)deptRepository.findAll();

		System.out.println("deptLists ======================= " + deptLists);

		return deptLists;
	}

	@Override
	public ArrayList<EmpListTO> findEmpList(String deptName) {

		System.out.println("deptName ============ " + deptName);

		ArrayList<EmpListTO> empList = dayAttdMapper.findEmpList(deptName);

		System.out.println("empList============== " + empList);

		return empList;
	}




	@Override
	public void registRestAttdjpa(RestAttdTO restAttdTo) {
		RestAttdEntity restAttdEntity = this.restAttdMapstruct.toEntity(restAttdTo);
		restAttdRepository.save(restAttdEntity);
	}
	// 오류남 - JPA에서는 숫자형 데이터 타입을 ID로 사용하는 것이 일반적이며, 문자열(String) 타입을 ID로 사용할 때 발생하는 문제라고함.
	// ID까지 String으로 받아서 나는 오류인듯.



	//✔️신규 출퇴근정보조회 - [조회하기]
	@Override
	public ArrayList<DayAttdTO> findAttdList1(String applyDay) {
		ArrayList<DayAttdTO> empList = dayAttdMapper.findEmpList1(applyDay);
		return empList;
	}
}
