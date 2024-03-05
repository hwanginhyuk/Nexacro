package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.controller;

import java.util.ArrayList;

import kr.co.seoulit.insa.attdsvc.attdappvl.to.DayAttdMgtTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.WorkInfoTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexacro17.xapi.data.PlatformData;

import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.service.EmpInfoService;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpEvalTO;
import kr.co.seoulit.insa.sys.mapper.DatasetBeanMapper;


@RequestMapping("/empinfomgmt/*")
@RestController
public class EmpEvalController {

	 @Autowired
	 private EmpInfoService empInfoService;
	 @Autowired
		private DatasetBeanMapper datasetBeanMapper;
//	private final EmpInfoService empInfoService;
//
//	private final DatasetBeanMapper datasetBeanMapper;
//
//	public EmpEvalController(EmpInfoService empInfoService, DatasetBeanMapper datasetBeanMapper) {
//		this.empInfoService = empInfoService;
//		this.datasetBeanMapper = datasetBeanMapper;
//	}
	ModelMap map = null;

//	✔️인사고과등록 - [등록]
//	@PostMapping("/registevaluation")
//	public ModelMap registEmpEval2(@RequestAttribute("reqData") PlatformData reqData,
//							  @RequestAttribute("resData") PlatformData resData) throws Exception{
//
//		System.out.println("==============인사고과등록 컨트롤러 시작==============");
//		EmpEvalTO empevalto = datasetBeanMapper.datasetToBean(reqData,EmpEvalTO.class);
//		System.out.println("<0>인사고과데이터<0> = " + empevalto);
//
//		String workInfo = reqData.getDataSetList().get(0).getString(0, 26);
//		System.out.println("<0>workInfo<0> = "+ workInfo);
//		empInfoService.registEmpEval2(empevalto,workInfo);
//		System.out.println("==============인사고과등록 컨트롤러 종료==============");
//
//		return null;
//	}

	//✔️인사고과등록(신규) - [등록]
	@PostMapping("/registevaluation")
	public ModelMap registEmpEval(@RequestAttribute("reqData") PlatformData reqData,
							      @RequestAttribute("resData") PlatformData resData) throws Exception{

		System.out.println("==============인사고과등록 컨트롤러 시작==============");
		EmpEvalTO empevalto = datasetBeanMapper.datasetToBean(reqData,EmpEvalTO.class);
		System.out.println("<0>인사고과데이터<0> = " + empevalto);

		empInfoService.registEmpEval(empevalto);
		System.out.println("==============인사고과등록 컨트롤러 종료==============");

		return null;
	}
	
	//		//detailCodeMapper.registDetailCode(detailCodeto);
	//		// 각 코드별 분류를 위해 detailCode도 넘겨준다(동기화작업)인사고과등록 - [조회]
	@PostMapping("/evaluation")
	public ModelMap findEmpEval(@RequestAttribute("reqData") PlatformData reqData,
								@RequestAttribute("resData") PlatformData resData) throws Exception{

		System.out.println("@@@deptCode가찍히니?@@@ = " + reqData);
		String deptCode = reqData.getVariable("deptCode").getString();
		ArrayList<EmpEvalTO> empevalList = empInfoService.findEmpEval(deptCode);
		datasetBeanMapper.beansToDataset(resData, empevalList, EmpEvalTO.class);

		return null;
	}

//	✔️인사고과등록 - 인사고과조회 [삭제]
	@PostMapping("/removalevaluation")
	public ModelMap removalevaluation(@RequestAttribute("reqData") PlatformData reqData,
									  @RequestAttribute("resData") PlatformData resData) throws Exception{

		String empCode = reqData.getVariable("empCode").getString();
		String applyDay = reqData.getVariable("applyDay").getString();

		empInfoService.removalevaluation(empCode, applyDay);
		return null;
	}
}