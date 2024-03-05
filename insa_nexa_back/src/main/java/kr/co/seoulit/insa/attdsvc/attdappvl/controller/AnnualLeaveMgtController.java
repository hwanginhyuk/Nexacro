package kr.co.seoulit.insa.attdsvc.attdappvl.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nexacro17.xapi.data.PlatformData;

import kr.co.seoulit.insa.attdsvc.attdappvl.service.AttdAppvlService;
import kr.co.seoulit.insa.attdsvc.attdappvl.to.AnnualLeaveMgtTO;
import kr.co.seoulit.insa.sys.mapper.DatasetBeanMapper;

@RestController
@RequestMapping("/attdappvl/*")
@RequiredArgsConstructor
public class AnnualLeaveMgtController {

   @Autowired
   private final AttdAppvlService attdAppvlService;
   @Autowired
   private final DatasetBeanMapper datasetBeanMapper;
   ModelMap map = null;
   //연차관리(안됨) - 조회
//   @PostMapping("/annual-leaveMgt")
//   public ModelMap findAnnualVacationMgtList(@RequestAttribute("reqData") PlatformData reqData,
//			                                 @RequestAttribute("resData") PlatformData resData) throws Exception{
//
//      String applyYearMonth = reqData.getVariable("applyYearMonth").getString();
//      String workplaceCode = reqData.getVariable("workplaceCode").getString();
//
//      ArrayList<AnnualLeaveMgtTO> annualVacationMgtList = attdAppvlService.findAnnualVacationMgtList(applyYearMonth, workplaceCode);
//      datasetBeanMapper.beansToDataset(resData, annualVacationMgtList, AnnualLeaveMgtTO.class);
//
//      return map;
//   }

      // 연차관리(수정중) - [조회]
   @RequestMapping (value = "/annual-leaveMgt")
   public ModelMap findAnnualVacationMgtList(@RequestAttribute("reqData") PlatformData reqData,
                                             @RequestAttribute("resData") PlatformData resData) throws Exception {
      String applyYearMonth = null;
      String workplaceCode = null;

      // reqData에서 변수를 가져오기 전에 null 체크를 수행
      if (reqData.getVariable("applyYearMonth") != null) {
         applyYearMonth = reqData.getVariable("applyYearMonth").getString();
      }
      if (reqData.getVariable("workplaceCode") != null) {
         workplaceCode = reqData.getVariable("workplaceCode").getString();
      }

      // applyYearMonth 또는 workplaceCode가 null이면 오류 처리를 수행
      if (applyYearMonth == null || workplaceCode == null) {
         // 오류 처리 로직 추가
      } else {
         // findAnnualVacationMgtList 메서드 호출
         ArrayList<AnnualLeaveMgtTO> annualVacationMgtList = attdAppvlService.findAnnualVacationMgtList(applyYearMonth, workplaceCode);

         // datasetBeanMapper를 사용하여 데이터 매핑
         datasetBeanMapper.beansToDataset(resData, annualVacationMgtList, AnnualLeaveMgtTO.class);
      }

      return map;
   }

   @RequestMapping("/annual-leaveMgt/1")
   public ModelMap modifyAnnualVacationMgtList(@RequestAttribute("reqData") PlatformData reqData,
			@RequestAttribute("resData") PlatformData resData) throws Exception{
	   
     ArrayList<AnnualLeaveMgtTO> annualVacationMgtList = (ArrayList<AnnualLeaveMgtTO>)datasetBeanMapper.datasetToBeans(reqData, AnnualLeaveMgtTO.class);
     attdAppvlService.modifyAnnualVacationMgtList(annualVacationMgtList);
      return null;
   } 
   
   
   @PutMapping("/annual-leaveMgt/2")
   public ModelMap cancelAnnualVacationMgtList(HttpServletRequest request, HttpServletResponse response){
	   map = new ModelMap();
      String sendData = request.getParameter("sendData");
      response.setContentType("application/json; charset=UTF-8");
      try {
         Gson gson = new Gson();
         ArrayList<AnnualLeaveMgtTO> annualVacationMgtList = gson.fromJson(sendData, new TypeToken<ArrayList<AnnualLeaveMgtTO>>(){}.getType());
         attdAppvlService.cancelAnnualVacationMgtList(annualVacationMgtList);
         map.put("errorMsg","success");
         map.put("errorCode", 0);
         
      }catch (Exception dae){
    	  map.clear();
    	  map.put("errorCode", -1);
    	  map.put("errorMsg", dae.getMessage());
      }
      return map;
   } 
   

}