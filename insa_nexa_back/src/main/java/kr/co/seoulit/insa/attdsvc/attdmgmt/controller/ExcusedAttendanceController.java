package kr.co.seoulit.insa.attdsvc.attdmgmt.controller;

import java.util.ArrayList;

import kr.co.seoulit.insa.attdsvc.attdmgmt.entity.RestAttdEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexacro17.xapi.data.PlatformData;

import kr.co.seoulit.insa.attdsvc.attdmgmt.service.AttdMgmtService;
import kr.co.seoulit.insa.attdsvc.attdmgmt.to.RestAttdTO;
import kr.co.seoulit.insa.sys.mapper.DatasetBeanMapper;
@Slf4j
@RestController
@RequestMapping("/attdappvl/*")
public class ExcusedAttendanceController {

	@Autowired
	private AttdMgmtService attdMgmtService;

	@Autowired
	private DatasetBeanMapper datasetBeanMapper;

	// ✔️근태외신청 - [신청하기]
	@RequestMapping("excused-attnd-create")
	public void registRestAttd(@RequestAttribute("reqData") PlatformData reqData,
							   @RequestAttribute("resData") PlatformData resData) throws Exception{
		log.info("✅✅✅========== 근태신청 컨드롤러 시작===========");
		RestAttdTO restAttd = datasetBeanMapper.datasetToBean(reqData, RestAttdTO.class);
		System.out.println("근태외restAttd : " + restAttd);
		attdMgmtService.registRestAttd(restAttd);
		log.info("✅✅✅========== 근태신청 컨드롤러 끝=============");
	}


	//🆕출장/교육 신청 - [신청하기]
	@RequestMapping("/chulzang")
	public void registRestAttd1(@RequestAttribute("reqData") PlatformData reqData,
							   @RequestAttribute("resData") PlatformData resData) throws Exception{
		System.out.println("========== 근태신청 컨드롤러 시작===========");

		RestAttdTO restAttd = datasetBeanMapper.datasetToBean(reqData, RestAttdTO.class);
		attdMgmtService.registRestAttd(restAttd);

		System.out.println("========== 근태신청 컨드롤러 끝===========");
	}


	//✔️근태외 신청 - [신청하기]
//	@RequestMapping("/excused-attnd-create-jpa")
//	public void registRestAttd2(@RequestAttribute("reqData") PlatformData reqData,
//							   @RequestAttribute("resData") PlatformData resData) throws Exception{
//		System.out.println("========== 근태신청 컨드롤러 시작===========");
//
//		RestAttdTO restAttdTo = datasetBeanMapper.datasetToBean(reqData, RestAttdTO.class);
//		System.out.println("restAttd ============= " + restAttdTo);
//		attdMgmtService.registRestAttdjpa(restAttdTo);
//
//		System.out.println("========== 근태신청 컨드롤러 끝===========");
//	}


	//✔️ 근태외신청 - [조회]
	@RequestMapping("/excused-attnd2")
	public void findRestAttdList(@RequestAttribute("reqData") PlatformData reqData,
								 @RequestAttribute("resData") PlatformData resData) throws Exception{

		System.out.println("=============🤢근태외 조회 컨트롤러 시작🤢===============");
		long start1 = System.currentTimeMillis();

		// 뷰단에서 세팅된 파라미터들이 넘어옴
		String empCode = reqData.getVariable("empCode").getString(); // 페이지화면이 로드될때 세팅되서 넘어옴
		String startDate = reqData.getVariable("startDate").getString();//첫번째 달력컴포넌트에 값이 세팅되서 넘어옴
		String endDate = reqData.getVariable("endDate").getString();//두번쨰 달력컴포넌트에 값이 세팅되서 넘어옴
		String code = reqData.getVariable("code").getString();
		System.out.println("code가 넘어오니? : " + code);
		// 넥사 innerdataset에서 값이 넘어옴
		// 근태외신청에서 근태구분에 innerdataset인 ds_restType의 DETAIL_CODE_NAME이 바인딩되어있고, code에는 innerdataset의 DETAIL_CODE_NUMBER가 들어가있음
		ArrayList<RestAttdTO> restAttdList = attdMgmtService.findRestAttdList(empCode, startDate, endDate, code);
		datasetBeanMapper.beansToDataset(resData, restAttdList, RestAttdTO.class);

		long end1 = System.currentTimeMillis();
		System.out.println("걸린 시간⏰⌚" + (end1 - start1));
		System.out.println("=============🤢근태외 조회 컨트롤러 종료🤢===============");

	}





	//✔️ 근태외신청 - [삭제] -jpa 적용
	@RequestMapping("/excused-attnd-elimination")
	public void removeRestAttdList(@RequestAttribute("reqData") PlatformData reqData,
			                       @RequestAttribute("resData") PlatformData resData) throws Exception{

		System.out.println("========== 연차삭제 컨드롤러 끝===========");
		System.out.println("reqData ============== " + reqData);

		ArrayList<RestAttdTO> restAttdList = (ArrayList<RestAttdTO>)datasetBeanMapper.datasetToBeans(reqData, RestAttdTO.class);
		//뷰단에서 뒷단으로 리스트가 넘어올때에는 형변환해줘야함
		System.out.println("restAttdList ================ " + restAttdList);
		attdMgmtService.removeRestAttdList(restAttdList);

		System.out.println("========== 연차삭제 컨드롤러 끝===========");
	}
}