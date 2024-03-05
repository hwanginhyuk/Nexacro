package kr.co.seoulit.insa.empmgmtsvc.documentmgmt.to;

import kr.co.seoulit.insa.commsvc.systemmgmt.to.BaseTO;
import kr.co.seoulit.insa.sys.annotation.Dataset;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Dataset(name="ds_documents")
@EqualsAndHashCode(callSuper=false)
public class DocumentsTO extends BaseTO{
    private String empName;
    private String empCode;
    private String proofTypeCode;
    private String proofTypeName;
    private String startDate;
    private String position;
    private String dept;
    private String cause;
    private String applovalStatus;
}
