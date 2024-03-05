package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpAppointmentTypeListTO;
import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpAppointmentInfoTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpAppointmentTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpAppointmentTypeTO;

@Mapper
public interface EmpAppointmentMapper {
	
	public void insertEmpAppointment(EmpAppointmentTO emp);
	
	public void insertEmpAppointment2(HashMap<String, Object> map);
	
	public ArrayList<EmpAppointmentTO> updateEmpAppintmentList();
	
	public void updateAppintmentList(EmpAppointmentTypeTO type);
	
	public String getHosu();
	
	public void insertAppointmentInfo(EmpAppointmentInfoTO empAppointmentInfoTO);
	
	public void updateEmpAppointmentlist(EmpAppointmentTO empAppointment);
	
	public void updateEmpAppoint(EmpAppointmentTO empAppointment);
	
	public void updateEmp(EmpAppointmentTO empAppointment);
	
	public ArrayList<EmpAppointmentInfoTO> selectAppintmentInfo(String searchType);
	
	public ArrayList<EmpAppointmentTypeTO> selectAppointmentinfoEmp(HashMap<String, String> map);
	// 인사발령 - [조회]
	public ArrayList<EmpAppointmentTypeTO> selectAppointmentEmp(String empCode);
//	public ArrayList<EmpAppointmentTypeListTO> selectAppointmentEmp();

	public EmpAppointmentTO countAppointmentEmp(String hosu);

	public ArrayList<EmpAppointmentTypeTO> selectAllAppointEmp(String hosu);
}
