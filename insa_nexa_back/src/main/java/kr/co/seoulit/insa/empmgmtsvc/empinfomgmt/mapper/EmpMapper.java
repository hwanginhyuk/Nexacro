package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpTO;

@Mapper
public interface EmpMapper {

//	public void insertEval(EmpTO empTO);

	public EmpTO selectEmp(String empName);
	public String selectLastEmpCode(); //
	public ArrayList<EmpTO> selectEmpList();
	public ArrayList<EmpTO> selectEmpListD(String dept);
	public ArrayList<EmpTO> selectEmpListN(String name);
	public String getEmpCode(String name);
	public EmpTO selectEmployee(String empCode);

	//✔️사원등록 - [등록]
	public void registEmployee(HashMap<String, Object> map);
	public void updateEmployee(EmpTO emp);
	public void deleteEmployee(HashMap<String, String> map);

	public void updateEmpTest(HashMap<String, Object> map);


}
