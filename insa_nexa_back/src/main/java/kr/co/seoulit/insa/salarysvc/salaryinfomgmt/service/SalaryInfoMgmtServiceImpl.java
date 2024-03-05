package kr.co.seoulit.insa.salarysvc.salaryinfomgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import kr.co.seoulit.insa.salarysvc.salaryinfomgmt.mapper.FullTimeSalaryMapper;
import kr.co.seoulit.insa.salarysvc.salaryinfomgmt.mapper.RetirementSalMapper;
import kr.co.seoulit.insa.salarysvc.salaryinfomgmt.mapper.SalaryBonusMapper;
//import kr.co.seoulit.insa.salarysvc.salaryinfomgmt.repository.FullTimeSalRepository;
import kr.co.seoulit.insa.salarysvc.salaryinfomgmt.to.FullTimeSalTO;
import kr.co.seoulit.insa.salarysvc.salaryinfomgmt.to.PayDayTO;
import kr.co.seoulit.insa.salarysvc.salaryinfomgmt.to.RetirementSalaryTO;
import kr.co.seoulit.insa.salarysvc.salaryinfomgmt.to.SalaryBonusTO;

@Service
@RequiredArgsConstructor
public class SalaryInfoMgmtServiceImpl implements SalaryInfoMgmtService{
	

	private final FullTimeSalaryMapper fullTimeSalaryMapper;

	private final RetirementSalMapper retirementSalMapper;

	private final SalaryBonusMapper salaryBonusMapper;


	//✔️급여조회 - [급여조회]
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<FullTimeSalTO> findselectSalary(String applyYearMonth, String empCode) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("applyYearMonth", applyYearMonth);
		map.put("empCode", empCode);

		System.out.println(map);
		fullTimeSalaryMapper.selectFullTimeSalary(map);

		ArrayList<FullTimeSalTO> fullTimeSalaryList = (ArrayList<FullTimeSalTO>) map.get("result");
		System.out.println("✅✅✅"+map+"✅✅✅");
		return fullTimeSalaryList;
		
	}



	@Override
	public ArrayList<FullTimeSalTO> findAllMoney(String applyYearMonth) {
		
		ArrayList<FullTimeSalTO> findAllMoneyList=null;
		findAllMoneyList = fullTimeSalaryMapper.findAllMoney(applyYearMonth);
		return findAllMoneyList;
		
	}
	
	@Override
	@Cacheable("findPayDayList")
	public ArrayList<PayDayTO> findPayDayList() {

		ArrayList<PayDayTO> PayDayList=null;
		PayDayList = fullTimeSalaryMapper.selectPayDayList();
		return PayDayList;
		
	}
	
	@Override
	public void modifyFullTimeSalary(List<FullTimeSalTO> fullTimeSalary) {
		
		for(FullTimeSalTO fullTimeSalTO : fullTimeSalary) {
			fullTimeSalaryMapper.updateFullTimeSalary(fullTimeSalTO);
		}
		
	}
	
	@Override
	public ArrayList<RetirementSalaryTO> findretirementSalaryList(String empCode) {
		ArrayList<RetirementSalaryTO> retirementSalaryList = retirementSalMapper.selectretirementSalaryList(empCode);	
		return retirementSalaryList;
		
	}
	
	@Override	
	public ArrayList<SalaryBonusTO> findBonusSalary(String empCode){
		
		ArrayList<SalaryBonusTO> SalaryBonusList=null;
		SalaryBonusList = salaryBonusMapper.selectBonusSalary(empCode);
		return SalaryBonusList;
		
	}
	
}
