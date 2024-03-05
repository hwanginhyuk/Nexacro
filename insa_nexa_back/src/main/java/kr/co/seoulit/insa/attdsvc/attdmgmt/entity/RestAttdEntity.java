package kr.co.seoulit.insa.attdsvc.attdmgmt.entity;

import kr.co.seoulit.insa.commsvc.systemmgmt.to.BaseTO;
import kr.co.seoulit.insa.sys.annotation.Dataset;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "REST_ATTD")
@EqualsAndHashCode(callSuper=false)
@Dataset(name="gds_restAttd")
//Oracle에 있는 시퀀스객체는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트이다.
@SequenceGenerator( //⭐ORACLE 데이터베이스에있는 시퀀스 오브젝트를  Entity객체 자체적으로 만들어서쓰는거라고 생각
        name="REST_ATTD_SEQ_GEN", //시퀀스 제너레이터 이름
        sequenceName="REST_ATTD_CODE_SEQ", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
)
@IdClass(value= RestAttdEntity.class)
public class RestAttdEntity extends BaseTO implements Serializable {

    @Id
    @Column(nullable=false)
    private String empCode;

    @Id
    @Column(nullable=false)
    @GeneratedValue( //✅REST_ATTD_SEQ_GEN라는 시퀀스객체를 시퀀스 generator에서 만들어서 이 시퀀스 오브젝트를 쓰겠다는 거임
            strategy = GenerationType.SEQUENCE,
            generator = "REST_ATTD_CODE_SEQ.NEXTVAL"
    )
    private String restAttdCode;

    private String restTypeCode;

    private String restTypeName;

    private String requestDate;

    private String startDate;

    private String endDate;

    private String cause;

    private String applovalStatus;

    private String rejectCause;

    private String cost;

    private String startTime;

    private String endTime;

    private String numberOfDays;
}
