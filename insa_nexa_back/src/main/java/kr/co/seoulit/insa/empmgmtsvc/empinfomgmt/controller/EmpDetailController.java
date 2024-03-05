package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexacro17.xapi.data.PlatformData;

import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.service.EmpInfoService;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.FamilyInfoTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.LicenseInfoTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.WorkInfoTO;
import kr.co.seoulit.insa.sys.mapper.DatasetBeanMapper;



@RequestMapping("/empinfomgmt/*")
@RestController
public class EmpDetailController {

	@Autowired
	private EmpInfoService empInfoService;
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;

//	private final EmpInfoService empInfoService;
//
//	private final DatasetBeanMapper datasetBeanMapper;
//
//	public EmpDetailController(EmpInfoService empInfoService, DatasetBeanMapper datasetBeanMapper) {
//		this.empInfoService = empInfoService;
//		this.datasetBeanMapper = datasetBeanMapper;
//	}

	// 사원상세 - [조회]
	@RequestMapping("/empdetail/all")
	public void findAllEmployeeInfo(@RequestAttribute("reqData") PlatformData reqData,
									@RequestAttribute("resData") PlatformData resData) throws Exception {


		System.out.println("=============🤢사원 상세조회 Cell클릭 컨트롤러🤢===============");
		long start1 = System.currentTimeMillis();
		// 걸린시간을 위한 로직처리
		String empCode = reqData.getVariable("empCode").getString();
		// reqData.getVariable("empCode").getString()로 받아온 데이터를 empCode에 담는다

		EmpTO empTO = empInfoService.findAllEmpInfo(empCode);
		// findAllEmpInfo() 메서드를 호출한다
		// 호출해서 넘오온 값을 EmpTO empTO(변수값)에 담는다
		ArrayList<WorkInfoTO> workInfoTO = empTO.getWorkInfo();
		// empTO에 담긴값을 WoerInfoTO에 담는다.
		// NextTapPage에 담기는 재직정보, 가족정보를 저장하기위하여
		empTO.setHobong(workInfoTO.get(0).getHobong());
		ArrayList<LicenseInfoTO> licenseInfoTO = empTO.getLicenseInfoList();
		ArrayList<FamilyInfoTO> familyInfoTO = empTO.getFamilyInfoList();
		// 값들을 각각의 beanToDataset에 올린다

		datasetBeanMapper.beanToDataset(resData, empTO, EmpTO.class);
		datasetBeanMapper.beansToDataset(resData, workInfoTO, WorkInfoTO.class);
		datasetBeanMapper.beansToDataset(resData, familyInfoTO, FamilyInfoTO.class);
		datasetBeanMapper.beansToDataset(resData, licenseInfoTO, LicenseInfoTO.class);
		long end1 = System.currentTimeMillis();
		System.out.println("걸린 시간⏰⌚⏱️🕰️" + (end1 - start1));
		System.out.println("=============🤢사원 상세조회 Cell클릭 컨트롤러 종료🤢===============");

	}

	//✔️사원조회 - 사원정보 [저장]
	@RequestMapping("/empdetail/empcode")
	public void modifyEmployee(@RequestAttribute("reqData") PlatformData reqData,
							   @RequestAttribute("resData") PlatformData resData) throws Exception {

		System.out.println("================🤢사원 상세조회 저장 컨트롤러🤢==================");
		EmpTO emp = datasetBeanMapper.datasetToBean(reqData, EmpTO.class);
		ArrayList<WorkInfoTO> workinfo = (ArrayList<WorkInfoTO>) datasetBeanMapper.datasetToBeans(reqData, WorkInfoTO.class);
		ArrayList<FamilyInfoTO> familyinfo = (ArrayList<FamilyInfoTO>) datasetBeanMapper.datasetToBeans(reqData, FamilyInfoTO.class);
		ArrayList<LicenseInfoTO> licenseinfo = (ArrayList<LicenseInfoTO>) datasetBeanMapper.datasetToBeans(reqData, LicenseInfoTO.class);

		emp.setWorkInfo(workinfo);
		emp.setFamilyInfoList(familyinfo);
		emp.setLicenseInfoList(licenseinfo);

		empInfoService.modifyEmployee(emp);
		empInfoService.modifyEmployee2(emp);

		System.out.println("================🤢사원 상세조회 저장 컨트롤러 종료🤢==================");
	}

	// ✔️사원조회 - 사원정보 [삭제]
	// 작성중
	@RequestMapping("/empdetail/empdelete")
	public void removeEmployeeList(@RequestAttribute("reqData") PlatformData reqData,
								   @RequestAttribute("resData") PlatformData resData) throws Exception {
		System.out.println("===================🤢사원 삭제 컨트롤러🤢=====================");

		String empCodeToDelete = reqData.getVariable("empCode").getString();
		String applyDayDelete = reqData.getVariable("applyDay").getString();

		empInfoService.removalevaluation(empCodeToDelete, applyDayDelete);

		System.out.println("==================🤢사원 삭제 컨트롤러 종료🤢====================");
	}
}
