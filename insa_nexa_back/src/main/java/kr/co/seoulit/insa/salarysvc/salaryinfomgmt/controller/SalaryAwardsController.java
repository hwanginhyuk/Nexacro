package kr.co.seoulit.insa.salarysvc.salaryinfomgmt.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexacro17.xapi.data.PlatformData;

import kr.co.seoulit.insa.salarysvc.salaryinfomgmt.service.SalaryInfoMgmtService;
import kr.co.seoulit.insa.salarysvc.salaryinfomgmt.to.SalaryBonusTO;
import kr.co.seoulit.insa.sys.mapper.DatasetBeanMapper;


@RequestMapping("/salaryinfomgmt/*")
@RestController
public class SalaryAwardsController {
   
   @Autowired
   private SalaryInfoMgmtService salaryInfoMgmtService;
   @Autowired
   private DatasetBeanMapper datasetBeanMapper;
   
   
   @PostMapping("awards")
   public void salInfo(@RequestAttribute("reqData") PlatformData reqData,
            @RequestAttribute("resData") PlatformData resData) throws Exception{
      
      String empCode = reqData.getVariable("empCode").getString();
         
      ArrayList<SalaryBonusTO> list = salaryInfoMgmtService.findBonusSalary(empCode);   
      
      datasetBeanMapper.beansToDataset(resData, list, SalaryBonusTO.class);
   }
   
}