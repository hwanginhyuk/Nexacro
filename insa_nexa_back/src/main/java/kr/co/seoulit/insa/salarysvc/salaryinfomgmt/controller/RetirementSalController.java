package kr.co.seoulit.insa.salarysvc.salaryinfomgmt.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexacro17.xapi.data.PlatformData;

import kr.co.seoulit.insa.salarysvc.salaryinfomgmt.service.SalaryInfoMgmtService;
import kr.co.seoulit.insa.salarysvc.salaryinfomgmt.to.RetirementSalaryTO;
import kr.co.seoulit.insa.sys.mapper.DatasetBeanMapper;


@RequestMapping("/salaryinfomgmt/*")
@RestController
public class RetirementSalController {
   
   @Autowired
   private SalaryInfoMgmtService salaryInfoMgmtService;   
   @Autowired
   private DatasetBeanMapper datasetBeanMapper;

   @PostMapping("retirement")
   public void retirementSalaryList(@RequestAttribute("reqData") PlatformData reqData,
            @RequestAttribute("resData") PlatformData resData) throws Exception {

      String empCode = reqData.getVariable("empCode").getString();
      
      ArrayList<RetirementSalaryTO> retirementSalaryList = salaryInfoMgmtService.findretirementSalaryList(empCode);
      
      datasetBeanMapper.beansToDataset(resData, retirementSalaryList, RetirementSalaryTO.class);
   }
}