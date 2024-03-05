package kr.co.seoulit.insa.attdsvc.attdappvl.controller;


import java.util.ArrayList;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexacro17.xapi.data.PlatformData;

import kr.co.seoulit.insa.attdsvc.attdappvl.service.AttdAppvlService;
import kr.co.seoulit.insa.attdsvc.attdappvl.to.DayAttdMgtTO;
import kr.co.seoulit.insa.sys.mapper.DatasetBeanMapper;

@RestController
@RequestMapping("/attdappvl/*")
@RequiredArgsConstructor
public class DailyAttendanceMgtController {
	
	private final AttdAppvlService attdAppvlService;
	final
	DatasetBeanMapper datasetBeanMapper;
	
	ModelMap map = null; //MVC패턴에 그 Model맞음. 확장성때문에쓴다는데 정확히 어떻게 쓰이는지는 모르겠음



	//✔️일근태관리 - [전체마감하기] (조회 프로시저호출)
	@RequestMapping("/attdappvl/day-attnd")
	public ModelMap findDayAttdMgtList(@RequestAttribute("reqData") PlatformData reqData,
            @RequestAttribute("resData") PlatformData resData) throws Exception{
		
		String applyDay = reqData.getVariable("applyDay").getString();
		String workplaceCode = reqData.getVariable("workplaceCode").getString();
		ArrayList<DayAttdMgtTO> dayAttdMgtList = attdAppvlService.findDayAttdMgtList(applyDay, workplaceCode);
		System.out.println("dayAttdMgtList ============================ " + dayAttdMgtList);
		datasetBeanMapper.beansToDataset(resData, dayAttdMgtList, DayAttdMgtTO.class);
		//(ArrayList<DayAttdMgtTO>) map.get("result")에 담긴 커서는 Dataset에 매핑될수 있게끔 가공되었으므로 화면에뿌릴수있음
		return null;
	}


	//✔️일근태관리 - [전체마감취소] (UPDATE문) 안쓰이는거같음 - 전체마감취소는 뷰단에서 마감여부칼럼을 N으로 바꿔주는 로직이있음
	@RequestMapping("/attdappvl/day-attndPut")
	public ModelMap modifyDayAttdList(@RequestAttribute("reqData") PlatformData reqData,
            @RequestAttribute("resData") PlatformData resData) throws Exception{

		ArrayList<DayAttdMgtTO> dayAttdMgtList = (ArrayList<DayAttdMgtTO>)datasetBeanMapper.datasetToBeans(reqData, DayAttdMgtTO.class);
		//여러행을 dataset(화면단)으로부터 받아왔고, DayAttdMgtTO형태의 리스트가 아니였기때문에 형변환해서 DayAttdMgtTO형태의 리스트로 변환해서 담았음
		//형변환은 왜 해주나? : 화면단(dataset)에서 받아온 리스트는 그 리스트가 DayAttdMgtTO리스트인지 뭔 리스트인지 모르기때문에 형변환을 해줘야하는것으로 보임
		attdAppvlService.modifyDayAttdMgtList(dayAttdMgtList); //DayAttdMgtTO리스트 형태로 형변환된 리스트를 변수에담아서 보냄

		return null;
	}


	//✔️일근태관리 - [조회하기] (SELECT문)
	@RequestMapping("/daySearch")
	public ModelMap findAttdList(@RequestAttribute PlatformData reqData,
								 @RequestAttribute PlatformData resData) throws Exception{

		String applyDay = reqData.getVariable("applyDay").getString();

		ArrayList<DayAttdMgtTO> dayAttdList = attdAppvlService.findAttdList(applyDay);
		//이 안에는 여러행이 담겨있고 (dayAttdList에는 여러행이 담겨있다.)
		datasetBeanMapper.beansToDataset(resData, dayAttdList, DayAttdMgtTO.class);
		//여러행을 화면에 뿌릴껀데, 타입은 DayAttdMgtTo임을 지정
		return map;
	}


	//✔️일근태관리 - [저장] (UPDATE문)
	@RequestMapping("/saveAttd")
	public ModelMap saveAttd(@RequestAttribute PlatformData reqData,
							 @RequestAttribute PlatformData resData) throws Exception {

		ArrayList<DayAttdMgtTO> dayAttdList = (ArrayList<DayAttdMgtTO>)datasetBeanMapper.datasetToBeans(reqData, DayAttdMgtTO.class);
		//여러행을 dataset으로부터 받아와서 저장한다. -- 왜 형변환? : 데이터셋에 받아온 리스트의 형태가 DayAttdMgtTO가 아니라서
		//뷰단에서 리스트를받아올때는 어떤형태의 리스트인지 모르기때문에 형변환해줘야함 ✔️
		attdAppvlService.saveAttd(dayAttdList); //데이터베이스에는 저장하기위한 알맞은형태의 List로 바뀌어 전달됨

		return null;
	}
}
//✅여러행을 처리해야한다면(조회든 삭제든 업데이트든) 리스트에 담아서 써야함
//✅뷰단에서 행들을 받아와서 뒷단에서 처리할때에는 형변환해줘야함(그에 맞는 DTO로)
//✅beansToDataset (조회) <--> DatasetToBean(삭제,수정,등록)