package kr.co.seoulit.insa.newempsvc.newempinfomgmt.mapper;

import kr.co.seoulit.insa.newempsvc.newempinfomgmt.to.NewResumeTO;
import kr.co.seoulit.insa.newempsvc.newempinfomgmt.to.PersonalityInterviewTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;



@Mapper
public interface NewEmpMapper {
	public ArrayList<NewResumeTO> findresumeList(Map<String, Object> map);

	public ArrayList<PersonalityInterviewTO> findPInewempList(Map<String, Object> map);

	public ArrayList<PersonalityInterviewTO> AllfindPInewempList();

	public void UpdateResumeNewemp(NewResumeTO nemp);

	public String produceNewcode(Map<String, Object> map);

	public String insertNewcode(Map<String, Object> map);

	public void InsertResume(NewResumeTO resume);

	public void InsertPI(PersonalityInterviewTO pi);
}
