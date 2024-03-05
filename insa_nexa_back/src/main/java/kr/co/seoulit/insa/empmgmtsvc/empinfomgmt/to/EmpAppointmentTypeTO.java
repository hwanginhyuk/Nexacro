package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to;

import kr.co.seoulit.insa.commsvc.systemmgmt.to.BaseTO;
import kr.co.seoulit.insa.sys.annotation.Dataset;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Dataset(name="gds_appointmenttype")
@EqualsAndHashCode(callSuper=false)
public class EmpAppointmentTypeTO extends BaseTO {
	
	private String empCode;
	private String hosu;
	private String lastDept;
	private String nextDept;
	private String appointmentDate;
	private String dispatchDate;
	private String dispatchReturnDate;
	private String dispatchPosition;
	private String lastWorkplace;
	private String lastRegion;
	private String lastHobong;
	private String nextHobong;
	private String promotionDate;
	private String lastPosition;
	private String nextPosition;
	private String retirementDate;
	private String leaveDate;
	private String reinstatementDate;
	private String leaveType;
	private String empName;
	private String approvalStatus;
}
