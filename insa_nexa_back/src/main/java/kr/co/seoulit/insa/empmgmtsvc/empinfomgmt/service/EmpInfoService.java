package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.service;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.seoulit.insa.attdsvc.attdappvl.to.DayAttdMgtTO;
import kr.co.seoulit.insa.commsvc.foudinfomgmt.to.DeptTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.*;

public interface EmpInfoService {
	public EmpTO getEmp(String name); //selectEmp
	public String findLastEmpCode();
	public EmpTO findAllEmpInfo(String empCode);
	public ArrayList<EmpTO> findEmpList(String dept); //findEmployeeListByDept
	public void registEmployee(EmpTO empto);
	public void modifyEmployee(EmpTO emp);
	public void deleteEmpList(ArrayList<EmpTO> empList);
	public ArrayList<DeptTO> findDeptList();

	public void registEmpEval2(EmpEvalTO empevalto,String workInfo);
	public ArrayList<EmpEvalTO> findEmpEval(String deptCode);
	public ArrayList<EmpEvalTO> findEmpEval2(String dept, String year);
	public void removalevaluation(String emp_code , String apply_day);

	public void modifyEmpEvalList(ArrayList<EmpEvalTO> empevalList);


	public void registAppoint(HashMap<String, Object> map,EmpAppointmentTO emp);
	public void updateAppoint(ArrayList<EmpAppointmentTypeTO> type);
	public EmpAppointmentInfoTO generateHosu();
	public void registAppointmentinfo(EmpAppointmentInfoTO empAppointmentInfoTO);
	public ArrayList<EmpAppointmentInfoTO> findAppointmentinfo(String searchType);
	public ArrayList<EmpAppointmentTypeTO> findAppointmentinfoEmp(String hosu, String type);
	//  인사발령 - [조회]
	public ArrayList<EmpAppointmentTypeTO> findAppointmentEmp(String empCode);
	//	public ArrayList<EmpAppointmentTypeListTO> findAppointmentEmp();
	public EmpAppointmentTO countAppointmentEmp(String hosu);
	public ArrayList<EmpAppointmentTypeTO> findAllAppointEmp(String hosu);

	// public void registEval(EmpTO empTO); //신규❗❗❗

	//❗❗❗신규
	public void registEmpEval(EmpEvalTO empeval);
	public void evalTest4(EmpEvalTO empEvalTO);

	public void modifyEmployee2(EmpTO emp);
}
