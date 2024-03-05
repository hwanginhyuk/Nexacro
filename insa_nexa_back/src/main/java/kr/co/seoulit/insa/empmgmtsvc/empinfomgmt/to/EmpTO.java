package kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to;


import java.io.Serializable;
import java.util.ArrayList;
import kr.co.seoulit.insa.commsvc.systemmgmt.to.BaseTO;
import kr.co.seoulit.insa.sys.annotation.Dataset;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;

@Data
@Dataset(name="gds_emp")
@EqualsAndHashCode(callSuper=false)
public class EmpTO extends BaseTO implements Serializable {

   private String empCode;
   private String empName;
   private String imgExtend;
   private String address;
   private String detailAddress;
   private String postNumber;
   private String mobileNumber;
   private String email;
   private String positionCode;
   private String gender;
   private String lastSchool;
   private String hobong;
   private String employment;
   private String occupation;
   private String deptCode;
   private String birthdate;
   private String workplaceCode;

   private String authority;
   private String hiredate;
   private String deptName;
   private String position;

   private int achievement;
   private int ability;
   private int attitude;

   private ArrayList<FamilyInfoTO> familyInfoList;
   private ArrayList<LicenseInfoTO> licenseInfoList;
   private ArrayList<WorkInfoTO> workInfo;

//   public String getImgExtend() {
//      return "jpg";
   }


