package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.seoulit.insa.attdsvc.attdmgmt.to.DayAttdTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.nexacro17.xapi.data.PlatformData;

import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.service.EmpInfoService;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpTO;
import kr.co.seoulit.insa.sys.mapper.DatasetBeanMapper;


@RequestMapping("/empinfomgmt/*")
@RestController
public class EmpRegisterController {

	@Autowired
	private EmpInfoService empInfoService;
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
	ModelMap map = null;
//	private final EmpInfoService empInfoService;
//	private final DatasetBeanMapper datasetBeanMapper;
//
//	public EmpRegisterController(EmpInfoService empInfoService, DatasetBeanMapper datasetBeanMapper) {
//		this.empInfoService = empInfoService;
//		this.datasetBeanMapper = datasetBeanMapper;
//
//	}

	//✔️사원등록 - [등록]
	@RequestMapping("/empinfomgmt/employee")
	public ModelMap registEmployee(HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("===========사원번호 등록 컨트롤러 실행==========="	);

		PlatformData reqData = (PlatformData)request.getAttribute("reqData");
		// 뷰단에서 넘어온 데이터 값들을 reqData에 담는다
		System.out.println("여기찍히니?1번"+ "reqData ==================== " + reqData);
		EmpTO emp = datasetBeanMapper.datasetToBean(reqData, EmpTO.class);
		// 담아온 reqData를 datasetBeanMapper(스프링컨테이너)에 등록한다
		// 담아온 값을 EmpTO emp(변수값)에 담는다
		System.out.println("여기찍히니?2번" +"emp ================ " + emp);
		empInfoService.registEmployee(emp);
		// 구현체에 넘겨주기 위해 emp를 담아 호출한다

		System.out.println("===========사원번호 등록 컨트롤러 종료==========="	);
		return null;
	}

	//✔️사원등록 - [사원번호생성]
	@GetMapping("/employee")
	   public ModelMap findLastEmpCode(@RequestAttribute("reqData") PlatformData reqData,
	         @RequestAttribute("resData") PlatformData resData) throws Exception{

		  System.out.println("===========사원번호 생성 컨트롤러 실행==========="	);
		  
	      EmpTO lastEmpCode= new EmpTO();
	      
	      String lastCode = empInfoService.findLastEmpCode().substring(1);  //lastEmpCode
		  // 앞의 문자열을 자르기 위해 substring(1)로 숫자만 반환해준다
	      String result = "B" + (Integer.parseInt(lastCode)+1);
		  // 마지막에 생성된 사번번호에 + 1을 하여 result에 담아준다
	      
	      lastEmpCode.setEmpCode(result);
	         
	      datasetBeanMapper.beanToDataset(resData, lastEmpCode, EmpTO.class);
		  System.out.println("===========사원번호 생성 컨트롤러 종료==========="	);
	      return null;
	   }

}
