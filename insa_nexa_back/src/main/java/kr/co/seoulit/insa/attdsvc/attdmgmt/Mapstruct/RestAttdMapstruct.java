package kr.co.seoulit.insa.attdsvc.attdmgmt.Mapstruct;

import kr.co.seoulit.insa.attdsvc.attdmgmt.entity.RestAttdEntity;
import kr.co.seoulit.insa.attdsvc.attdmgmt.to.RestAttdTO;
import kr.co.seoulit.insa.sys.mapstruct.EntityReqMapstrut;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring"
)
//@Override
public interface RestAttdMapstruct extends EntityReqMapstrut<RestAttdEntity, RestAttdTO> {

}
