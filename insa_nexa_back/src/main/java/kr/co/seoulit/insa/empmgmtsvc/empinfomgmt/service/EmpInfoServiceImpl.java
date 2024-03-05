package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.co.seoulit.insa.commsvc.foudinfomgmt.mapper.DeptMapper;
import kr.co.seoulit.insa.commsvc.systemmgmt.entity.DetailCode;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

//import kr.co.seoulit.insa.commsvc.foudinfomgmt.mapper.DeptMapper;
import kr.co.seoulit.insa.commsvc.foudinfomgmt.repository.DeptRepository;
import kr.co.seoulit.insa.commsvc.foudinfomgmt.to.DeptTO;
import kr.co.seoulit.insa.commsvc.systemmgmt.mapper.DetailCodeMapper;
import kr.co.seoulit.insa.commsvc.systemmgmt.repository.DetailCodeRepository;
import kr.co.seoulit.insa.commsvc.systemmgmt.to.DetailCodeTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.mapper.EmpMapper;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.mapper.EmpAppointmentMapper;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.mapper.EmpEvalMapper;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.mapper.FamilyInfoMapper;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.mapper.LicenseInfoMapper;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.mapper.WorkInfoMapper;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.repository.EmpEvalRepository;

@Service
public class EmpInfoServiceImpl implements EmpInfoService {

	@Autowired
	private DeptMapper deptMapper;
	@Autowired
	private EmpMapper empMapper;
	@Autowired
	private DetailCodeMapper detailCodeMapper;
	@Autowired
	private FamilyInfoMapper familyInfoMapper;
	@Autowired
	private WorkInfoMapper workInfoMapper;
	@Autowired
	private LicenseInfoMapper licenseInfoMapper;
	@Autowired
	private EmpEvalMapper empEvalMapper;
	@Autowired
	private DeptRepository deptrepository;
	@Autowired
	private DetailCodeRepository detailcoderepository;
	@Autowired
	private EmpEvalRepository empEvalrepository;
	@Autowired
	private EmpAppointmentMapper empAppointmentMapper;


//	private final EmpMapper empMapper;
//	private final DetailCodeMapper detailCodeMapper;
//	private final FamilyInfoMapper familyInfoMapper;
//	private final WorkInfoMapper workInfoMapper;
//	private final LicenseInfoMapper licenseInfoMapper;
//	private final EmpEvalMapper empEvalMapper;
//	private final DeptRepository deptrepository;
//	private final DetailCodeRepository detailcoderepository;
//	private final EmpEvalRepository empEvalrepository;
//	private final EmpAppointmentMapper empAppointmentMapper;
//
//	public EmpInfoServiceImpl(EmpMapper empMapper,
//							  DetailCodeMapper detailCodeMapper,
//							  FamilyInfoMapper familyInfoMapper,
//							  WorkInfoMapper workInfoMapper,
//							  LicenseInfoMapper licenseInfoMapper,
//							  EmpEvalMapper empEvalMapper,
//							  DeptRepository deptrepository,
//							  DetailCodeRepository detailcoderepository,
//							  EmpEvalRepository empEvalrepository,
//							  EmpAppointmentMapper empAppointmentMapper)
//	{   this.empMapper = empMapper;
//		this.detailCodeMapper = detailCodeMapper;
//		this.familyInfoMapper = familyInfoMapper;
//		this.workInfoMapper = workInfoMapper;
//		this.licenseInfoMapper = licenseInfoMapper;
//		this.empEvalMapper = empEvalMapper;
//		this.deptrepository = deptrepository;
//		this.detailcoderepository = detailcoderepository;
//		this.empEvalrepository = empEvalrepository;
//		this.empAppointmentMapper = empAppointmentMapper;
//	}

	@Override
	public EmpTO getEmp(String name) {

		EmpTO empto = null;
		empto = empMapper.selectEmp(name);
		return empto;

	}

	//✔️사원번호 - [생성]
	@Override
	public String findLastEmpCode() {

		String empCode = null;
		empCode = empMapper.selectLastEmpCode();
		return empCode;

	}

	@Override
	@Cacheable(key = "#empCode",value = "findAllEmp")
	public EmpTO findAllEmpInfo(String empCode) {
		System.out.println("=============😐사원 조회 Cell클릭😐===============");
		EmpTO empTO = null;
		empTO = empMapper.selectEmployee(empCode);
		System.out.println("=============😐사원 조회 Cell클릭😐===============" + empCode);
		return empTO;

	}

	// ✔️사원리스트 - [조회]
	@Override
	public ArrayList<EmpTO> findEmpList(String parameter) {

		System.out.println("parameter = " + parameter);
		String deptName = parameter.split(":")[0];
		// EX) 인사팀:DEP001로 넘어온 파라메터값을 deptName담는다

		ArrayList<EmpTO> empList = null;
		if (parameter.equals("전체부서")) {
			empList = empMapper.selectEmpList();
		} else if (parameter.substring(deptName.length() - 1, deptName.length()).equals("팀")) {
			empList = empMapper.selectEmpListD(deptName);

		} else {
			empList = empMapper.selectEmpListN(deptName);
		}
		return empList;

	}

	//✔️사원등록 - [등록]
	@Override
	public void registEmployee(EmpTO emp) {
	// 호출된 구현체 로직
		HashMap<String, Object> map = new HashMap<>();
		map.put("empCode", emp.getEmpCode());
		map.put("empName", emp.getEmpName());
		map.put("imgExtend", emp.getImgExtend());
		map.put("address", emp.getAddress());
		map.put("detailAddress", emp.getDetailAddress());
		map.put("postNumber", emp.getPostNumber());
		map.put("mobileNumber", emp.getMobileNumber());
		map.put("email", emp.getEmail());
		map.put("positionCode", emp.getPositionCode());
		map.put("gender", emp.getGender());
		map.put("lastSchool", emp.getLastSchool());
		map.put("hobong", emp.getHobong());
		map.put("employment", emp.getEmployment());
		map.put("occupation", emp.getOccupation());
		map.put("deptCode", emp.getDeptCode());
		map.put("birthdate", emp.getBirthdate());
		map.put("WorkplaceCode",emp.getWorkplaceCode());

		for(String key : map.keySet()) {
			System.out.println("여기가 찍히니?" + key +" : "+ map.get(key));
		}

		empMapper.registEmployee(map);
		// ✅empMapper.registEval(map);

		DetailCode detailCode = new DetailCode();
		detailCode.setDetailCodeNumber(emp.getEmpCode());
		detailCode.setDetailCodeName(emp.getEmpName());
		detailCode.setCodeNumber("CO-17");
		detailCode.setDetailCodeNameusing("N");
		detailcoderepository.save(detailCode);
		//detailCodeMapper.registDetailCode(detailCodeto);
		// 각 코드별 분류를 위해 detailCode도 넘겨준다(동기화작업)
	}

	@Cacheable(key = "#emp", value = "modifyEmployee2")
	public void modifyEmployee2(EmpTO emp) {

		HashMap<String, Object> map = new HashMap<>();
		map.put("empCode", emp.getEmpCode()); //
		map.put("empName", emp.getEmpName());//
		//map.put("birthdate", emp.getBirthdate());
		//map.put("gender", emp.getGender());//
		//map.put("mobileNumber", emp.getMobileNumber());//
		//map.put("address", emp.getAddress());//
		//map.put("detailAddress", emp.getDetailAddress());//
		//map.put("postNumber", emp.getPostNumber());//
		//map.put("email", emp.getEmail());//
		//map.put("lastSchool", emp.getLastSchool());//
		//map.put("imgExtend", emp.getImgExtend());//
		map.put("deptCode", emp.getDeptCode());
		map.put("positionCode", emp.getPositionCode());
		map.put("deptName", emp.getDeptName());//
		map.put("position", emp.getPosition());//

		empMapper.updateEmpTest(map);
	}


	// 현재 무반응... 에러도 안뜬다...
	@Override
	@Cacheable(key = "#emp", value = "modifyEmployee")
	public void modifyEmployee(EmpTO emp) {

		HashMap<String, Object> map = new HashMap<>();
		map.put("empCode", emp.getEmpCode());
		map.put("empName", emp.getEmpName());
		map.put("birthdate", emp.getBirthdate());
		map.put("gender", emp.getGender());
		map.put("mobileNumber", emp.getMobileNumber());
		map.put("address", emp.getAddress());
		map.put("detailAddress", emp.getDetailAddress());
		map.put("postNumber", emp.getPostNumber());
		map.put("email", emp.getEmail());
		map.put("lastSchool", emp.getLastSchool());
		map.put("imgExtend", emp.getImgExtend());
		map.put("deptCode", emp.getDeptCode());
		map.put("positionCode", emp.getPositionCode());
		map.put("deptName",emp.getDeptName());
		map.put("position",emp.getPosition());

		empMapper.updateEmpTest(map);

		if (emp.getStatus().equals("update")) {
			empMapper.updateEmployee(emp);
		}
		if (emp.getWorkInfo() != null) {
			ArrayList<WorkInfoTO> workInfoList = emp.getWorkInfo();
			for(WorkInfoTO workInfo : workInfoList) {
				switch (workInfo.getStatus()) {
					case "insert":
						workInfoMapper.insertWorkInfo(workInfo);
						break;
					case "update":
						workInfoMapper.updateWorkInfo(workInfo);
						break;
					case "delete":
						workInfoMapper.deleteWorkInfo(workInfo);
						break;
				}
			}
		}

		if (emp.getLicenseInfoList() != null && emp.getLicenseInfoList().size() > 0) {
			ArrayList<LicenseInfoTO> licenseInfoList = emp.getLicenseInfoList();
			for (LicenseInfoTO licenseInfo : licenseInfoList) {
				switch (licenseInfo.getStatus()) {
					case "insert":
						licenseInfoMapper.insertLicenseInfo(licenseInfo);
						break;
					case "update":
						licenseInfoMapper.updateLicenseInfo(licenseInfo);
						break;
					case "delete":
						licenseInfoMapper.deleteLicenseInfo(licenseInfo);
						break;
				}
			}
		}

		if (emp.getFamilyInfoList() != null && emp.getFamilyInfoList().size() > 0) {
			ArrayList<FamilyInfoTO> familyInfoList = emp.getFamilyInfoList();
			for (FamilyInfoTO familyInfo : familyInfoList) {
				switch (familyInfo.getStatus()) {
					case "insert":
						familyInfoMapper.insertFamilyInfo(familyInfo);
						break;
					case "update":
						familyInfoMapper.updateFamilyInfo(familyInfo);
						break;
					case "delete":
						familyInfoMapper.deleteFamilyInfo(familyInfo);
						break;
				}
			}
		}

	}

	@Override
	public void deleteEmpList(ArrayList<EmpTO> empList) {
		HashMap<String, String> map = new HashMap<>();
		for (EmpTO emp : empList) {
			map.put("empCode", emp.getEmpCode());
			empMapper.deleteEmployee(map);
			DetailCodeTO detailCodeto = new DetailCodeTO();
			detailCodeto.setDetailCodeNumber(emp.getEmpCode());
			detailCodeto.setDetailCodeName(emp.getEmpName());
			detailCodeMapper.deleteDetailCode(detailCodeto);
		}

	}

	@Override
	public ArrayList<DeptTO> findDeptList() {

		ArrayList<DeptTO> deptList = null;
		deptList =(ArrayList<DeptTO>)deptrepository.findAll();
		//deptList = deptMapper.selectDeptList();
		return deptList;

	}


	//✔️인사고과등록(수정전) - [등록]
	@Override
	public void registEmpEval2(EmpEvalTO empeval,String workInfo) {

		System.out.println("workInfo = " + workInfo);


		// 정규표현식 패턴
		String positionCodePattern = "positionCode=([^,]+)";
		String deptCodePattern = "deptCode=([^\\]]+)";

		// 패턴 컴파일
		Pattern positionRegex = Pattern.compile(positionCodePattern); //Pattern 객체 생성(인자에 정규식)
		Pattern deptNameRegex = Pattern.compile(deptCodePattern);

		// 매칭하기
		Matcher positionMatcher = positionRegex.matcher(workInfo);//Matcher 객체 생성(인자에 검사할 문자열)
		Matcher deptNameMatcher = deptNameRegex.matcher(workInfo);//"^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$"; // 이메일 정규식

		// 원하는 문자열 추출
		String positionCode = "";
		String deptName = "";

		if (positionMatcher.find()) {
			//Matcher클래스의 find() :Pattern에 일치하는 문자열이 발견 되면 true, 발견 되지 않으면 false 반환.
			// 반복문을 통해 계속 호출하면 두 번째, 세 번째 ...로 Pattern에 일치하는 문자열을 찾는 것도가능합니다.
			positionCode = positionMatcher.group(1);
			//Matcher클래스의 group() : Pattern에 일치하는 문자열을 반환.
			//정규식 형식에 맞으면 PositionCode를 반환
		}

		if (deptNameMatcher.find()) {
			deptName = deptNameMatcher.group(1);
			//정규식 형식에 맞으면 deptName을 반환
		}

		// 괄호 뒤에 불필요한 문자 제거
		deptName = deptName.replaceAll("\\)", "");

		System.out.println("사원: " + positionCode);
		System.out.println("부서: " + deptName);


		LocalDate now = LocalDate.now();

		int year = now.getYear();
		String yearData = Integer.toString(year);

		empeval.setApplyDay(yearData);
       	//empeval.setDeptName(deptName);
      	//empeval.setPosition(positionCode);

		int a =empeval.getAchievement();
		int b = empeval.getAbility();
		int c = empeval.getAttitude();

		String grade = null;


		if((a+b+c)/3>=90){
			grade = "A";
		} else if ((a+b+c)/3>=80){
			grade = "B";
		} else if ((a+b+c)/3>=70) {
			grade = "C";
		} else {
			grade = "D";
		}

		System.out.println(grade+"그레이드가 찍히니1?");
		empeval.setGrade(grade);
		//empEvalrepository.save(empeval);
		empEvalMapper.insertEmpEval(empeval);

	}


	//✔️인사고과등록(최종) - [등록]
	@Override
	public void registEmpEval(EmpEvalTO empeval) {
		//dayAttdList는 리스트 이기때문에 iter사용
		//만약 고과테이블에 있는 사원이면 update, 고과테이블에 없는사원이면 insert

		LocalDate now = LocalDate.now();

		int year = now.getYear();
		String yearData = Integer.toString(year);

		empeval.setApplyDay(yearData);
		//empeval.setDeptName(deptName);
		//empeval.setPosition(positionCode);

		int a = empeval.getAchievement();
		int b = empeval.getAbility();
		int c = empeval.getAttitude();

		String grade = null;


		if ((a + b + c) / 3 >= 90) {
			grade = "A";
		} else if ((a + b + c) / 3 >= 80) {
			grade = "B";
		} else if ((a + b + c) / 3 >= 70) {
			grade = "C";
		} else {
			grade = "D";
		}

		System.out.println(grade + " = 그레이드가 찍히니2?");
		empeval.setGrade(grade);
		empeval.setApprovalStatus("승인대기");
		empeval.setEmpName(empeval.getEmpName());



		ArrayList<String> empCode = empEvalMapper.selectEmpEval6();

		if(empCode.contains(empeval.getEmpCode())) {
			System.out.println("❌❌❌❌❌업데이트");
			empEvalMapper.updateEval(empeval);
			//empEvalrepository.save(empeval);
		}else{
			empEvalMapper.insertEval(empeval);
			System.out.println("⭕⭕⭕⭕⭕인서트");
		}

		}

	@Override
	public void evalTest4(EmpEvalTO empEvalTO) {
		empEvalMapper.updateEval4(empEvalTO);
	}

	//✔️인사고과등록 - [조회]
	@Override
	public ArrayList<EmpEvalTO> findEmpEval(String deptCode) {
		ArrayList<EmpEvalTO> empevallsit = null;
		if (deptCode.equals("전체부서")) {
			empevallsit = empEvalMapper.selectEmpEval(deptCode);
		} else if (deptCode.substring(deptCode.length() - 1, deptCode.length()).equals("팀")) {

			empevallsit = empEvalMapper.selectEmpEval(deptCode);
		}
		return empevallsit;
	}


	//✔️인사고과관리 - [조회]
	@Override
	public ArrayList<EmpEvalTO> findEmpEval2(String dept, String year) {
		HashMap<String, String> map = new HashMap<>();
		map.put("deptCode", dept);
		map.put("applyDay", year);
		ArrayList<EmpEvalTO> empevallsit = null;
		if (dept.equals("모든부서")) {
			//empevallsit = (ArrayList<EmpEvalTO>)empEvalrepository.findAllByApplyDay(year);
			empevallsit = empEvalMapper.selectEmpEval7(year);
			empevallsit = empEvalMapper.selectEmpEval4(year);
			System.out.println(empevallsit+" = 나와라제발나와라잇");
		} else {
			empevallsit = empEvalMapper.selectEmpEvalDept(map);
		}
		return empevallsit;

	}



	// ✔️인사고과 - [수정]
	@Override
	public void modifyEmpEvalList(ArrayList<EmpEvalTO> empevalList) {
	// 수정시 등급을 재조정하는 역할을 한다
		for (EmpEvalTO empeval : empevalList) {
			int evalhap = empeval.getAchievement() + empeval.getAbility() + empeval.getAttitude();
			if (evalhap > 260) {
				empeval.setGrade("A");
			} else if (evalhap > 240) {
				empeval.setGrade("B");
			} else if (evalhap > 220) {
				empeval.setGrade("C");
			} else {
				empeval.setGrade("D");
			}
			//empEvalrepository.save(empeval);
			empEvalMapper.updateEval(empeval);
		}

	}

	@Override
	public void removalevaluation(String empCode, String applyDay) {
		HashMap<String, String> map = new HashMap<>();
		map.put("empCode", empCode);
		map.put("applyDay", applyDay);

		empEvalMapper.deleteEmpEval(map);

	}

	@Override
	public void registAppointmentinfo(EmpAppointmentInfoTO empAppointmentInfoTO) {

		empAppointmentMapper.insertAppointmentInfo(empAppointmentInfoTO);
	}

	@Override
	public ArrayList<EmpAppointmentInfoTO> findAppointmentinfo(String searchType){
		ArrayList<EmpAppointmentInfoTO> empAppointmentInfoTO = empAppointmentMapper.selectAppintmentInfo(searchType);
		return empAppointmentInfoTO;
	}

	@Override
	public ArrayList<EmpAppointmentTypeTO> findAppointmentinfoEmp(String hosu,String type){

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("hosu", hosu);
		map.put("type", type);
		ArrayList<EmpAppointmentTypeTO> empAppointmentTypeTO = empAppointmentMapper.selectAppointmentinfoEmp(map);
		return empAppointmentTypeTO;
	}

	// 🚩인사발령등록 - [조회]
	@Override
	public ArrayList<EmpAppointmentTypeTO> findAppointmentEmp(String empCode){
		System.out.println("=============😐인사발령 조회 퍼사드 시작😐===============");
		ArrayList<EmpAppointmentTypeTO> empAppointmentTypeTO = empAppointmentMapper.selectAppointmentEmp(empCode);

		System.out.println("이건 찍히니?" + empAppointmentTypeTO);

		System.out.println("=============😐인사발령 조회 퍼사드 종료😐===============");
		return empAppointmentTypeTO;
	}
	
	@Override
	public ArrayList<EmpAppointmentTypeTO> findAllAppointEmp(String hosu){
		ArrayList<EmpAppointmentTypeTO> empAppointmentTypeTO = empAppointmentMapper.selectAllAppointEmp(hosu);
		return empAppointmentTypeTO;
	}


	@Override
	public EmpAppointmentTO countAppointmentEmp(String hosu){
		EmpAppointmentTO empAppointmentTO = empAppointmentMapper.countAppointmentEmp(hosu);
		return empAppointmentTO;
	}

	// 🚩 인사발령 - [호수생성]
	@Override
	public EmpAppointmentInfoTO generateHosu(){
		
		Calendar now = Calendar.getInstance();
		// 호수의 날짜를 설정하기 위한 로직
		String hosu = empAppointmentMapper.getHosu();
		if(hosu==null) {
			String year = Integer.toString(now.get(Calendar.YEAR));
			String month = Integer.toString((now.get(Calendar.MONTH)) +1);
			if(month.length()<2)
				month='0'+month;
			hosu = year+month+"-1";
		}
		EmpAppointmentInfoTO empAppointmentInfoTO = new EmpAppointmentInfoTO();
		empAppointmentInfoTO.setHosu(hosu);
		return empAppointmentInfoTO;
	}

	// 🚩인사발령 - [등록]
	@Override
	public void registAppoint(HashMap<String, Object> map, EmpAppointmentTO emp) {
		empAppointmentMapper.insertEmpAppointment2(map);
	}

	// 🚩인사발령 - [수정]
	@Override
	public void updateAppoint(ArrayList<EmpAppointmentTypeTO> typeList) {
	//
		for (EmpAppointmentTypeTO type : typeList) {
			empAppointmentMapper.updateAppintmentList(type);
		}
	}
}