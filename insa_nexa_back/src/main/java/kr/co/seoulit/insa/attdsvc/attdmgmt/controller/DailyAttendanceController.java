package kr.co.seoulit.insa.attdsvc.attdmgmt.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.seoulit.insa.attdsvc.attdappvl.to.DayAttdMgtTO;
import kr.co.seoulit.insa.attdsvc.attdmgmt.to.DeptListTO;
import kr.co.seoulit.insa.commsvc.foudinfomgmt.to.DeptTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nexacro17.xapi.data.PlatformData;

import kr.co.seoulit.insa.attdsvc.attdmgmt.service.AttdMgmtService;
import kr.co.seoulit.insa.attdsvc.attdmgmt.to.DayAttdTO;
import kr.co.seoulit.insa.sys.mapper.DatasetBeanMapper;

@RestController
@RequestMapping("/attdmgmt/*")
public class DailyAttendanceController {
	
	@Autowired
	private AttdMgmtService attdMgmtService;

	@Autowired
	private DatasetBeanMapper datasetBeanMapper;

	ModelMap map = null;

	@RequestMapping("daily-attnd2") //같은 조회인데 왜 반환형이 없지..**
	public void findDayAttdList(@RequestAttribute("reqData") PlatformData reqData,
            				    @RequestAttribute("resData") PlatformData resData) throws Exception {
		
			String empCode = reqData.getVariable("empCode").getString();
			String applyDay = reqData.getVariable("applyDay").getString();
		
			ArrayList<DayAttdTO> dayAttdList = attdMgmtService.findDayAttdList(empCode, applyDay);
		
			datasetBeanMapper.beansToDataset(resData,dayAttdList,DayAttdTO.class);
	}

	@RequestMapping("daily-attnd") //✔️일근태 등록 - [출근]/[퇴근]
	public void registDayAttd(@RequestAttribute("reqData") PlatformData reqData,
            @RequestAttribute("resData") PlatformData resData) throws Exception {

		System.out.println("=============일근태 등록 컨트롤러 시작 =============");
		System.out.println("reqData ===================== " + reqData);

			DayAttdTO dayAttd = datasetBeanMapper.datasetToBean(reqData, DayAttdTO.class);
			System.out.println("============"+dayAttd);
			System.out.println(dayAttd.getEmpCode());
			attdMgmtService.registDayAttd(dayAttd);

		System.out.println("=============일근태 등록 컨트롤러 시작 =============");


	}

	@RequestMapping("daily-attnd3")
	public void removeDayAttdList(@RequestAttribute("reqData") PlatformData reqData,
            			@RequestAttribute("resData") PlatformData resData) throws Exception {

		ArrayList<DayAttdTO> dayAttdList = (ArrayList<DayAttdTO>)datasetBeanMapper.datasetToBeans(reqData, DayAttdTO.class);
		attdMgmtService.removeDayAttdList(dayAttdList);
	}


	@RequestMapping("daily-attnd-jpa") //✔️일근태 등록 - [출근]/[퇴근]
	public void insertDayAttd(@RequestAttribute("reqData") PlatformData reqData,
							  @RequestAttribute("resData") PlatformData resData) throws Exception {

		System.out.println("=============😐😐일근태 등록 컨트롤러 시작 =============");
		System.out.println("reqData ===================== " + reqData);

		DayAttdTO dayAttd = datasetBeanMapper.datasetToBean(reqData, DayAttdTO.class);
		System.out.println("============"+dayAttd);
		System.out.println(dayAttd.getEmpCode());
		attdMgmtService.insertDayAttd(dayAttd);

		System.out.println("=============😐😐일근태 등록 컨트롤러 시작 =============");


	}


//	public ModelMap insertDayAttd(HttpServletRequest request, HttpServletResponse response){
//
//		map = new ModelMap();
//		String sendData = request.getParameter("sendData");
//		response.setContentType("application/json; charset=UTF-8");
//
//		try {
//			Gson gson = new Gson();
//			DayAttdTO dayAttd = gson.fromJson(sendData, new TypeToken<DayAttdTO>(){}.getType());
//			attdMgmtService.insertDayAttd(dayAttd);
//			map.put("errorMsg","success");
//			map.put("errorCode", 0);
//
//		}catch (Exception dae){
//			map.clear();
//			map.put("errorCode", -1);
//			map.put("errorMsg", dae.getMessage());
//		}
//		return map;
//	}

	@RequestMapping("/deptlist") // 조회인데 반환형이 있다.
	public ArrayList<DeptListTO> findDeptList(@RequestAttribute("reqData") PlatformData reqData,
								 @RequestAttribute("resData") PlatformData resData) throws Exception{

		ArrayList<DeptListTO> deptList = attdMgmtService.findDeptList();

		datasetBeanMapper.beansToDataset(resData, deptList, DeptListTO.class);

		System.out.println("deptList =============== " + deptList);

		return null;
	}

	//✔️신규 출퇴근정보조회 - [조회하기]  // 퇴근은 18시 이후로만 찍히는 거같음
	@RequestMapping("/daySearch1")
	public ModelMap findAttdList1(@RequestAttribute PlatformData reqData,
								 @RequestAttribute PlatformData resData) throws Exception{
		String applyDay = reqData.getVariable("applyDay").getString();

		ArrayList<DayAttdTO> dayAttdList = attdMgmtService.findAttdList1(applyDay);
		datasetBeanMapper.beansToDataset(resData, dayAttdList, DayAttdTO.class);
		return null;
	}

}
