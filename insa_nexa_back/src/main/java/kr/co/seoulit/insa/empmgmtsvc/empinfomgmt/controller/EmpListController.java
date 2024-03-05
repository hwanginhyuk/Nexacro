package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexacro17.xapi.data.PlatformData;

import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.service.EmpInfoService;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpTO;
import kr.co.seoulit.insa.sys.mapper.DatasetBeanMapper;


@RequestMapping("/empinfomgmt/*")
@RestController
public class EmpListController {
	@Autowired
	private EmpInfoService empInfoService;
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;

//	private final EmpInfoService empInfoService;
//	private final DatasetBeanMapper datasetBeanMapper;
//
//	public EmpListController(EmpInfoService empInfoService,
//							 DatasetBeanMapper datasetBeanMapper) {
//		this.empInfoService = empInfoService;
//		this.datasetBeanMapper = datasetBeanMapper;
//	}
	// Autowired 어노테이션 : 필요한 Bean들을 얻어오는 어노테이션
	// EmpInfoService 내부의 값들을 empInfoService의 bean으로 받아옴
	// DatasetBeanMapper 내부의 값들을 datasetBeanMapper bean으로 받아옴

	// ✔️사원리스트 - 조회
	@RequestMapping("/emplist")
	// "/emplist의" 경로에 있는 정보들에 대한 Mapping을 요구
	public void emplist(@RequestAttribute("resData") PlatformData resData,
						@RequestAttribute("reqData") PlatformData reqData) throws Exception {
		// datasetBeanMapper 내부의 "resData" & "reqData" 값들을 요구
		System.out.println("==============조회시작==============");
		String value = reqData.getVariable("value").getString();
		// value 변수에 "value"라는 변수를 String 타입으로 가져와 넣는다
		System.out.println(value);
		ArrayList<EmpTO> list = empInfoService.findEmpList(value);
		// value 값을 기준으로 empInfoService에 있는 findEmpList를 이용하여
		// ArrayList형태의 EmpTO를 list로 반환
		System.out.println("list");
		System.out.println("==============조회종료==============");
		datasetBeanMapper.beansToDataset(resData, list, EmpTO.class);
		// datasetBeanMapper의 beansToDataset을 이용하여 list의 데이터를 EmpTo객체의 속성에
		// Mapping 해준 후 결과를 resData에 저장

	}

}
