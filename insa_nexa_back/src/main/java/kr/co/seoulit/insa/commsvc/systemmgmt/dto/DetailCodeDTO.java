package kr.co.seoulit.insa.commsvc.systemmgmt.dto;

import kr.co.seoulit.insa.sys.annotation.Dataset;
import lombok.Data;

import java.io.Serializable;

@Data
@Dataset(name="gds_detailcode")
public class DetailCodeDTO implements Serializable { //Serializable : redis를 적용할때 반드시 필요


    private String detailCodeNumber;

    private String detailCodeName;



}
