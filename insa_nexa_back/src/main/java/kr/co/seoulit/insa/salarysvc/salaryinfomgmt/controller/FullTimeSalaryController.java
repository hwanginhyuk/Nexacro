package kr.co.seoulit.insa.salarysvc.salaryinfomgmt.controller;

import java.util.ArrayList;

import com.nexacro17.xapi.data.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.seoulit.insa.salarysvc.salaryinfomgmt.service.SalaryInfoMgmtService;
import kr.co.seoulit.insa.salarysvc.salaryinfomgmt.to.FullTimeSalTO;
import kr.co.seoulit.insa.salarysvc.salaryinfomgmt.to.PayDayTO;
import kr.co.seoulit.insa.sys.mapper.DatasetBeanMapper;



@RequestMapping("/salaryinfomgmt/*")
@RestController
@Slf4j
public class FullTimeSalaryController {
   
   private final SalaryInfoMgmtService salaryInfoMgmtService;
   private final DatasetBeanMapper datasetBeanMapper;

   public FullTimeSalaryController(SalaryInfoMgmtService salaryInfoMgmtService, DatasetBeanMapper datasetBeanMapper) {
      this.salaryInfoMgmtService = salaryInfoMgmtService;
      this.datasetBeanMapper = datasetBeanMapper;
   }


   @GetMapping("salary")
   public void AllMoneyList(@RequestAttribute("reqData") PlatformData reqData,
            @RequestAttribute("resData") PlatformData resData) throws Exception {
      
      String applyYearMonth = reqData.getVariable("date").toString();
      
      ArrayList<FullTimeSalTO> AllMoneyList = salaryInfoMgmtService.findAllMoney(applyYearMonth);
      
      datasetBeanMapper.beansToDataset(resData, AllMoneyList, FullTimeSalTO.class);
   }


   //✔️급여조회 - [급여조회]
   @RequestMapping("/salary/empcode")
// @PostMapping("/salary/empcode")
   public void selectSalary(@RequestAttribute("reqData") PlatformData reqData,
                            @RequestAttribute("resData") PlatformData resData) throws Exception {

      try {
         String applyYearMonth = reqData.getVariable("apply_year_month").getString();
         String empCode = reqData.getVariable("empCode").getString();
//       String applyYearMonth = reqData.getVariable("applyYearMonth").getString();
//       String empCode = reqData.getVariable("empCode").getString();

         ArrayList<FullTimeSalTO> fullTimeSalaryList = salaryInfoMgmtService.findselectSalary(applyYearMonth, empCode);
         datasetBeanMapper.beansToDataset(resData, fullTimeSalaryList, FullTimeSalTO.class);

      } catch (Exception e) {
         log.error("🚨에러위치🚨_FullTimeSalaryController.selectSalary", e.getMessage());
         e.printStackTrace();
      }
   }

   @PostMapping("salary")
   public void modifyFullTimeSalary(@RequestAttribute("reqData") PlatformData reqData,
            @RequestAttribute("resData") PlatformData resData) throws Exception {
      
      ArrayList<FullTimeSalTO> fullTimeSalary
      = (ArrayList<FullTimeSalTO>)datasetBeanMapper.datasetToBeans(reqData, FullTimeSalTO.class);

      salaryInfoMgmtService.modifyFullTimeSalary(fullTimeSalary);
      }
   
   public void paydayList(@RequestAttribute("reqData") PlatformData reqData,
            @RequestAttribute("resData") PlatformData resData) throws Exception  {
      
      ArrayList<PayDayTO> list = salaryInfoMgmtService.findPayDayList();
      
      datasetBeanMapper.beansToDataset(resData, list, PayDayTO.class);
   }
   
   
}
