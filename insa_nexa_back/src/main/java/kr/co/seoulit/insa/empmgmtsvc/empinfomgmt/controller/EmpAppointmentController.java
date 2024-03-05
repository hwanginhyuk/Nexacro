package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.controller;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpAppointmentTypeListTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexacro17.xapi.data.PlatformData;

import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.service.EmpInfoService;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpAppointmentInfoTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpAppointmentTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpAppointmentTypeTO;
import kr.co.seoulit.insa.sys.mapper.DatasetBeanMapper;


@RequestMapping("/empinfomgmt/*")
@RestController
public class EmpAppointmentController {

	@Autowired
 		private EmpInfoService empInfoService;
 	@Autowired
		private DatasetBeanMapper datasetBeanMapper;
//	private final EmpInfoService empInfoService;
//
//	private final DatasetBeanMapper datasetBeanMapper;
//
//	public EmpAppointmentController(EmpInfoService empInfoService, DatasetBeanMapper datasetBeanMapper) {
//		this.empInfoService = empInfoService;
//		this.datasetBeanMapper = datasetBeanMapper;
//	}

	//✔️인사발령등록 - [등록]
	@RequestMapping("/appointment")
	public void registAppointment(@RequestAttribute("reqData") PlatformData reqData,
								  @RequestAttribute("resData") PlatformData resData) throws Exception{
		EmpAppointmentTO emp = datasetBeanMapper.datasetToBean(reqData, EmpAppointmentTO.class);
		EmpAppointmentTypeTO empType = datasetBeanMapper.datasetToBean(reqData, EmpAppointmentTypeTO.class);
		String type = reqData.getVariable("type").getString();
		HashMap<String, Object> map = new HashMap<String, Object>();
		System.out.println(type+"@@@@@@@@@@@@@@@");
		map.put("empCode", emp.getEmpCode());
		map.put("hosu", emp.getHosu());
		map.put("appointNext", empType.getNextDept());
		map.put("retirementDate", empType.getRetirementDate());
		map.put("reinstatementDate", empType.getReinstatementDate());
		map.put("type", type);

		empInfoService.registAppoint(map,emp);
	}

	// ✔️인사발령등록 - [수정]
	@RequestMapping("/appointment2")
	public void updateAppoint(@RequestAttribute("reqData") PlatformData reqData,
							  @RequestAttribute("resData") PlatformData resData) throws Exception{

		ArrayList<EmpAppointmentTypeTO> type = (ArrayList<EmpAppointmentTypeTO>)datasetBeanMapper.datasetToBeans(reqData, EmpAppointmentTypeTO.class);
		empInfoService.updateAppoint(type);
	}
	
	// ✔️인사발령등록 - [호수]
	@RequestMapping("/appointment3")
	public void generateHosu(@RequestAttribute("reqData") PlatformData reqData,
							 @RequestAttribute("resData") PlatformData resData) throws Exception{


		EmpAppointmentInfoTO empAppointmentInfoTO = empInfoService.generateHosu();

		datasetBeanMapper.beanToDataset(resData, empAppointmentInfoTO, EmpAppointmentInfoTO.class);
	}

	/*
	 * @RequestMapping("/appointment4") public void
	 * registAppointmentinfo(@RequestAttribute("reqData") PlatformData reqData,
	 *
	 * @RequestAttribute("resData") PlatformData resData) throws Exception{
	 *
	 * EmpAppointmentInfoTO empAppointmentInfoTO =
	 * datasetBeanMapper.datasetToBean(reqData, EmpAppointmentInfoTO.class);
	 * ArrayList<EmpAppointmentTO>
	 * empAppointmentlist=(ArrayList<EmpAppointmentTO>)datasetBeanMapper.
	 * datasetToBeans(reqData, EmpAppointmentTO.class);
	 *
	 *
	 * empAppointmentInfoTO.setAppointmentDate(empAppointmentInfoTO.
	 * getAppointmentDate().substring(0,8));
	 *
	 *
	 * empInfoService.registAppointmentinfo(empAppointmentInfoTO,
	 * empAppointmentlist);
	 *
	 * }
	 */

	@RequestMapping("/appointment4")
	public void registAppointmentinfo(@RequestAttribute("reqData") PlatformData reqData,
									  @RequestAttribute("resData") PlatformData resData) throws Exception{

		EmpAppointmentInfoTO empAppointmentInfoTO = datasetBeanMapper.datasetToBean(reqData, EmpAppointmentInfoTO.class);

		System.out.println(empAppointmentInfoTO.getAppointmentDate()+"@@@@@@@@@@@");


		empInfoService.registAppointmentinfo(empAppointmentInfoTO);

	}

	@RequestMapping("/appointment5")
	public void findAppointmentinfo(@RequestAttribute("reqData") PlatformData reqData,
									@RequestAttribute("resData") PlatformData resData) throws Exception{

		String searchType = reqData.getVariable("searchType").getString();

		ArrayList<EmpAppointmentInfoTO> empAppointmentInfoTO = empInfoService.findAppointmentinfo(searchType);

		datasetBeanMapper.beansToDataset(resData, empAppointmentInfoTO, EmpAppointmentInfoTO.class);
	}

	@RequestMapping("/appointment6")
	public void findAppointmentinfoEmp(@RequestAttribute("reqData") PlatformData reqData,
									   @RequestAttribute("resData") PlatformData resData) throws Exception{

		String hosu = reqData.getVariable("hosu").getString();
		String type = reqData.getVariable("type").getString();
		System.out.println(hosu+"@@@"+type+"@@@");

		ArrayList<EmpAppointmentTypeTO> empAppointmentTypeTO = empInfoService.findAppointmentinfoEmp(hosu,type);
		datasetBeanMapper.beansToDataset(resData, empAppointmentTypeTO, EmpAppointmentTypeTO.class);
	}
 // 인사발령 - [조회]
	@RequestMapping("/appointment7")
	public void findAppointmentEmp(@RequestAttribute("reqData") PlatformData reqData,
								   @RequestAttribute("resData") PlatformData resData) throws Exception{
		System.out.println("=============😐인사발령 조회 컨트롤러 시작😐===============");

		String empCode = reqData.getVariable("empCode").getString();

		System.out.println("empCode : " + empCode);

		ArrayList<EmpAppointmentTypeTO> empAppointmentTypeTO = empInfoService.findAppointmentEmp(empCode);

		System.out.println("empAppointmentTypeTO : " + empAppointmentTypeTO);

		datasetBeanMapper.beansToDataset(resData, empAppointmentTypeTO, EmpAppointmentTypeTO.class);


		System.out.println("=============😐인사발령 조회 컨트롤러 종료😐===============");
	}
//	@RequestMapping("/appointment7")
//	public ArrayList<EmpAppointmentTypeListTO> findAppointmentEmp(@RequestAttribute("reqData") PlatformData reqData,
//								   								  @RequestAttribute("resData") PlatformData resData) throws Exception{
//
//		ArrayList<EmpAppointmentTypeListTO> empAppointmentTypeListTO = empInfoService.findAppointmentEmp();
//		datasetBeanMapper.beansToDataset(resData, empAppointmentTypeListTO, EmpAppointmentTypeListTO.class);
//        return null;
//    }


	@RequestMapping("/appointment8")
	public void countAppointmentEmp(@RequestAttribute("reqData") PlatformData reqData,
									@RequestAttribute("resData") PlatformData resData) throws Exception{

		String hosu = reqData.getVariable("hosu").getString();
		ArrayList<EmpAppointmentTypeTO> empAppointmentTypeTO = empInfoService.findAllAppointEmp(hosu);
		EmpAppointmentTO empAppointmentTO = empInfoService.countAppointmentEmp(hosu);
		datasetBeanMapper.beansToDataset(resData, empAppointmentTypeTO, EmpAppointmentTypeTO.class);
		datasetBeanMapper.beanToDataset(resData, empAppointmentTO, EmpAppointmentTO.class);
	}
}
