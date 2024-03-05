package kr.co.seoulit.insa.attdsvc.attdmgmt.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.insa.attdsvc.attdmgmt.to.RestAttdTO;

@Mapper
public interface ExcusedAttndMapper {
	
	public ArrayList<RestAttdTO> selectRestAttdList1(HashMap<String, String> map);// ✔️예비군, 경조사, 병가 조회
	public ArrayList<RestAttdTO> selectRestAttdList2(HashMap<String, String> map);// ✔️출장 조회
	public ArrayList<RestAttdTO> selectRestAttdList3(HashMap<String, String> map);// ✔️연차 조회
	public void insertRestAttd(RestAttdTO restAttd); // ✔️근태외신청 - [신청하기]
	public void deleteRestAttd(RestAttdTO restAttd); // ✔️근태외신청 - [삭제]

	//⭐⭐ 파라미터로 HashMap이 넘어오는경우는 뷰단에서쓰인 값(var)이 그대로 넘어오는경우 map에넣어서 보내는거같음 (DTO로 안넘기고) - 정확하진않음 (parameterType 관련)
	public ArrayList<RestAttdTO> selectRestAttdListByAllDept(HashMap<String , Object> map); //✔️근태외 승인관리 - [전체조회하기] - 전체부서조회
	public ArrayList<RestAttdTO> selectRestAttdListByDept(HashMap<String , Object> map); //✔️근태외 승인관리 - [전체조회하기] - 부서별조회
	public void updateRestAttd(RestAttdTO restAttd); //✔️근태외 승인관리 - [저장]
	public ArrayList<RestAttdTO> overtimeFindList(HashMap<String , Object> map); //✔️초과근무 관리 - [조회하기]
	public void updateOvertime(RestAttdTO restAttd); //✔️초과근무 관리 - [저장]
	
}
