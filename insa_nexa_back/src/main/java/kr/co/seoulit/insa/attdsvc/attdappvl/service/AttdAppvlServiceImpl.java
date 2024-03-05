package kr.co.seoulit.insa.attdsvc.attdappvl.service;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.seoulit.insa.attdsvc.attdappvl.to.DayAttdMgtTO;
import kr.co.seoulit.insa.attdsvc.attdappvl.to.MonthAttdMgtTO;
import kr.co.seoulit.insa.attdsvc.attdappvl.mapper.AnnualVacationMgtMapper;
import kr.co.seoulit.insa.attdsvc.attdappvl.mapper.DailyAttndMgtMapper;
import kr.co.seoulit.insa.attdsvc.attdappvl.mapper.MonthlyAttndMgtMapper;
import kr.co.seoulit.insa.attdsvc.attdappvl.to.AnnualLeaveMgtTO;
import kr.co.seoulit.insa.attdsvc.attdmgmt.mapper.ExcusedAttndMapper;
import kr.co.seoulit.insa.attdsvc.attdmgmt.to.RestAttdTO;



@Service
@RequiredArgsConstructor
public class AttdAppvlServiceImpl implements AttdAppvlService {
	
	private final DailyAttndMgtMapper dayAttdMgtMapper;
	private final ExcusedAttndMapper excusedAttndMapper;
	private final MonthlyAttndMgtMapper monthAttdMgtMapper;
	private final AnnualVacationMgtMapper annualVacationMgtMapper;


	//✔일근태관리 - [전체마감하기] (조회)
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<DayAttdMgtTO> findDayAttdMgtList(String applyDay, String workplaceCode) {

		HashMap<String, Object> map = new HashMap<>();
		map.put("applyDay",applyDay);
		map.put("workplaceCode",workplaceCode);
		dayAttdMgtMapper.batchDayAttdMgtProcess(map);
		//applyDay와 workplaceCode를 map에 담아서 DB쪽으로 보냄
		
		return (ArrayList<DayAttdMgtTO>) map.get("result");
		// map.get("result")안에 프로시저 'result커서'에 결과가 담겨있다(16개)
		// 결과는 DayAttdMgtTo리스트 형태로 형변환해야함 프로시저에서 결과를 받아왓고(아직 DayAttdMgtTO가아님),
		// Dataset에 매핑해야하기때문에(Datset에는 DayAttdMgtTO로 등록되어있음) 형변환해주는걸로 보인다
	}


	//✔일근태관리 - [전체마감취소] : 뷰단에 로직이 있기 때문에 안쓰이는거 같음
	//attndPut
	@Override
	public void modifyDayAttdMgtList(ArrayList<DayAttdMgtTO> dayAttdMgtList) {

		for (DayAttdMgtTO dayAttdMgt : dayAttdMgtList) { //마감여부가 N으로 바뀐 리스트의 행들을 업데이트함 (리스트 이기때문에 iter사용)
				dayAttdMgtMapper.updateDayAttdMgtList(dayAttdMgt); //한행씩 업데이트한거를 데이터베이스에 넣어야하기때문에 iter를 사용하는것
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<MonthAttdMgtTO> findMonthAttdMgtList(String applyYearMonth, String workplaceCode) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("applyYearMonth", applyYearMonth);
		map.put("workplaceCode", workplaceCode);

		monthAttdMgtMapper.batchMonthAttdMgtProcess(map);

		return (ArrayList<MonthAttdMgtTO>) map.get("result");
	}

	@Override
	public void modifyMonthAttdMgtList(ArrayList<MonthAttdMgtTO> monthAttdMgtList) {

		for (MonthAttdMgtTO monthAttdMgt : monthAttdMgtList) {
				monthAttdMgtMapper.updateMonthAttdMgtList(monthAttdMgt);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<AnnualLeaveMgtTO> findAnnualVacationMgtList(String applyYearMonth, String workplaceCode) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("applyYearMonth", applyYearMonth);
		map.put("workplaceCode", workplaceCode);

		annualVacationMgtMapper.batchAnnualVacationMgtProcess(map);

		System.out.println("map ============= " + map);

		return (ArrayList<AnnualLeaveMgtTO>) map.get("result");
	}

	@Override
	public void modifyAnnualVacationMgtList(ArrayList<AnnualLeaveMgtTO> annualVacationMgtList) {

		for (AnnualLeaveMgtTO annualVacationMgt : annualVacationMgtList) {
			annualVacationMgtMapper.updateAnnualVacationMgtList(annualVacationMgt);
				annualVacationMgtMapper.updateAnnualVacationList(annualVacationMgt);
		}
	}

	@Override
	public void cancelAnnualVacationMgtList(ArrayList<AnnualLeaveMgtTO> annualVacationMgtList) {

		for (AnnualLeaveMgtTO annualVacationMgt : annualVacationMgtList) {
			if (annualVacationMgt.getStatus().equals("update")) {
				annualVacationMgtMapper.cancelAnnualVacationMgtList(annualVacationMgt);
				annualVacationMgtMapper.cancelAnnualVacationList(annualVacationMgt);
			}
		}

	}

	//✔️근태외 승인관리 - [전체조회하기]
	@Override
	public ArrayList<RestAttdTO> findRestAttdListByDept(String empCode, String startDate, String endDate) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<RestAttdTO> restAttdList = null;
		map.put("empCode", empCode);
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		System.out.println("empCode =============== " + empCode);
		System.out.println("startDate =============== " + startDate);
		System.out.println("endDate =============== " + endDate);

		if (empCode.equals("undefined")) {
			restAttdList = excusedAttndMapper.selectRestAttdListByAllDept(map);
		} else {

			restAttdList = excusedAttndMapper.selectRestAttdListByDept(map);
		}

		return restAttdList;
	}


	//✔️근태외 승인관리 - [저장]
	//attnd-approval2
	@Override
	public void modifyRestAttdList(ArrayList<RestAttdTO> restAttdList) {

		for (RestAttdTO restAttd : restAttdList) {
			System.out.println("===================="+restAttd.getStatus());
			if (restAttd.getStatus().equals("update")) { //⭐BaseTO 관련) status가 평소엔 'normal'이였다가 [승인]버튼을 누르면
				//앞단에서 APPLOVAL_STATUS 칼럼값을 승인으로 바꾸는 동시에 STATUS칼럽값을 'update'로 세팅해주는 로직이있음
				excusedAttndMapper.updateRestAttd(restAttd);
			}
		}
	}

	//✔일근태관리 - [조회하기]
	@Override
	public ArrayList<DayAttdMgtTO> findAttdList(String applyDay) { //이 findAttdList라는 메서드가 여러행을 반환하는역할을 하는메서드를 호출하는 메서드임

		ArrayList<DayAttdMgtTO> attdList = dayAttdMgtMapper.findAttdList(applyDay);
		//Select문이 여러행이 조회되었기 때문에 List에 담아서 봔환
		return attdList;
	}

	//✔일근태관리 - [저장]
	@Override
	public void saveAttd(ArrayList<DayAttdMgtTO> dayAttdList) { //dayAttdList는 리스트 이기때문에 iter사용

		for (DayAttdMgtTO restAttd : dayAttdList) {
			System.out.println(restAttd.getStatus());
			if (restAttd.getStatus().equals("update")) { //getStatus() 메서드는 각 DayAttdMgtTO 객체의 상태를확인하고, 해당 상태가 "update"인 경우에만 업데이트 작업을 수행하는 역할
				dayAttdMgtMapper.updateDayAttd(restAttd); //즉, 수정되었을때만 한항목씩 확인하여 업데이트역할을 수행한다는의미
			}
		}

	}

	//✔️초과근무 관리 - [조회하기]
	@Override
	public ArrayList<RestAttdTO> overtimeFindList(String requestDate, String restTypeCode) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<RestAttdTO> restAttdList = null;
		map.put("requestDate", requestDate);
		map.put("restTypeCode", restTypeCode);

		System.out.println("empName =============== " + requestDate);
		System.out.println("startDate =============== " + restTypeCode);

		restAttdList = excusedAttndMapper.overtimeFindList(map);


		return restAttdList;
	}


	//✔️초과근무 관리 - [저장]
	@Override
	public void overtimeApplovalSave(ArrayList<RestAttdTO> restAttdList) {

		for (RestAttdTO restAttd : restAttdList) {
			System.out.println("===================="+restAttd.getStatus());
			if (restAttd.getStatus().equals("update")) {
				excusedAttndMapper.updateOvertime(restAttd);
			}
		}
	}

}
