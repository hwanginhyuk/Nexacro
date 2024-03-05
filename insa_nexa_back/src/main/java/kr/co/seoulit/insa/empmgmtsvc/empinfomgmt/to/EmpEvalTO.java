package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


import kr.co.seoulit.insa.commsvc.systemmgmt.to.BaseTO;
import kr.co.seoulit.insa.sys.annotation.Dataset;
import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Entity
@Data
@Table(name="EMP_EVAL")
@Dataset(name="gds_empEval")
@EqualsAndHashCode(callSuper=false)
public class EmpEvalTO extends BaseTO{

	@Id
	@Column(nullable = false)
	private String empCode;
	private String empName;
	private String deptCode;
	private String positionCode;
	private int achievement;
	private int ability;
	private int attitude;
	private String grade;
	private String approvalStatus;
	private String applyDay;
	private String status;



}
