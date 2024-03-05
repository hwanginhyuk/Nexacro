package kr.co.seoulit.insa.salarysvc.salarystdinfomgmt.to;

import kr.co.seoulit.insa.commsvc.systemmgmt.to.BaseTO;
import kr.co.seoulit.insa.sys.annotation.Dataset;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Dataset(name="gds_socialIns")
@EqualsAndHashCode(callSuper=false)
public class SocialInsTO extends BaseTO{
	
	private String attributionYear;
	private String healthinsureRates;
	private String longtermcareRates;
	private String nationpenisionRates;
	private String teachpenisionRates;
	private String empinsureRates;
	private String wrkinsureRates;
	private String jobstabilRates;
	private String vocacompetencyRates;
	private String industinsureRates;
	private String industinsurecharRates;

}