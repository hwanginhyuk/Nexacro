package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpEvalTO;

@Mapper
public interface EmpEvalMapper {



	public ArrayList<EmpEvalTO> selectEmpEval4(String deptCode);
	public ArrayList<EmpEvalTO> selectEmpEval2(String deptCode);
	public ArrayList<EmpEvalTO> selectEmpEval(String deptCode); //수정된거 ⭐⭐⭐
	public ArrayList<EmpEvalTO> selectEmpEvalDept(HashMap<String, String> map);	//✔️인사고과관리 - [조회]
	public void insertEmpEval(EmpEvalTO empevalto); //✔️인사고과등록 - [등록]
	public void updateEmpEval(EmpEvalTO empeval);
	public void deleteEmpEval(HashMap<String, String> map);
	void updateEval4(EmpEvalTO empEvalTO);
	ArrayList<String> selectEmpEval6();

	void updateEval(EmpEvalTO empEvalTO);
	void insertEval(EmpEvalTO empEvalTO);

	ArrayList<EmpEvalTO> selectEmpEval7(String year);
}
