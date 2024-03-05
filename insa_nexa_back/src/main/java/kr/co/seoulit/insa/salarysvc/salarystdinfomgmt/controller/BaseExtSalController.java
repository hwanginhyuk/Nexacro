package kr.co.seoulit.insa.salarysvc.salarystdinfomgmt.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexacro17.xapi.data.PlatformData;

import kr.co.seoulit.insa.salarysvc.salarystdinfomgmt.service.SalaryStdInfoMgmtService;
import kr.co.seoulit.insa.salarysvc.salarystdinfomgmt.to.BaseExtSalTO;
import kr.co.seoulit.insa.sys.mapper.DatasetBeanMapper;


@RequestMapping("/salarystdinfomgmt/*")
@RestController
public class BaseExtSalController {
	
	@Autowired
	private SalaryStdInfoMgmtService salaryStdInfoMgmtService;	
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
	ModelMap map = null;

	@RequestMapping("/salarystdinfomgmt/over-sal")
	public ModelMap findBaseExtSalList(@RequestAttribute("reqData") PlatformData reqData,
			@RequestAttribute("resData") PlatformData resData) throws Exception{

		ArrayList<BaseExtSalTO> baseExtSalList = salaryStdInfoMgmtService.findBaseExtSalList();
		
		datasetBeanMapper.beansToDataset(resData, baseExtSalList, BaseExtSalTO.class);
		return map;
	}

	
	@RequestMapping("/salarystdinfomgmt/over-sal-create")
	public ModelMap modifyBaseExtSalList(@RequestAttribute("reqData") PlatformData reqData,
			@RequestAttribute("resData") PlatformData resData) throws Exception{
		
		ArrayList<BaseExtSalTO> baseExtSalList = (ArrayList<BaseExtSalTO>)datasetBeanMapper.datasetToBeans(reqData, BaseExtSalTO.class);
		
		salaryStdInfoMgmtService.modifyBaseExtSalList(baseExtSalList);
		
		return null;
	}
	
}
