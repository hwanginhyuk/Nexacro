package kr.co.seoulit.insa.attdsvc.attdmgmt.to;

import kr.co.seoulit.insa.commsvc.systemmgmt.to.BaseTO;
import kr.co.seoulit.insa.sys.annotation.Dataset;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "DAY_ATTD")
@Data
@EqualsAndHashCode(callSuper=false)
@Dataset(name="gds_dayAttd")

@SequenceGenerator(
		name="DAY_ATTD_SEQ_GEN", //시퀀스 제너레이터 이름
		sequenceName="DAY_ATTD_CODE_SEQ", //시퀀스 이름
		initialValue=1, //시작값
		allocationSize=1 //메모리를 통해 할당할 범위 사이즈
)
public class DayAttdTO extends BaseTO{

	@Id
	private String empCode;
	private String empName;

	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "DAY_ATTD_SEQ_GEN"
	)
	private String dayAttdCode;
	private String applyDay;
	private String attdTypeCode;
	private String attdTypeName;
	private String time;
}