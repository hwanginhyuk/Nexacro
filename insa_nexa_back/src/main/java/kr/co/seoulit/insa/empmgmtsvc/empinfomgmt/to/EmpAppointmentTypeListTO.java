package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to;

import kr.co.seoulit.insa.commsvc.systemmgmt.to.BaseTO;
import kr.co.seoulit.insa.sys.annotation.Dataset;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Dataset(name="gds_appointmenttypelist")
@EqualsAndHashCode(callSuper=false)
public class EmpAppointmentTypeListTO extends BaseTO {

    private String hosu;
    private String lastDept;
    private String nextDept;
    private String appointmentDate;
    private String lastHobong;
    private String nextHobong;
    private String lastPosition;
    private String nextPosition;
    private String empName;
    private String empCode;
    private String approvalStatus;
}
