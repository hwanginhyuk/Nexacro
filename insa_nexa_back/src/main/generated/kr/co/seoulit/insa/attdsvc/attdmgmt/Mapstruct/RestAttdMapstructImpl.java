package kr.co.seoulit.insa.attdsvc.attdmgmt.Mapstruct;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import kr.co.seoulit.insa.attdsvc.attdmgmt.entity.RestAttdEntity;
import kr.co.seoulit.insa.attdsvc.attdmgmt.to.RestAttdTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-29T13:03:11+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class RestAttdMapstructImpl implements RestAttdMapstruct {

    @Override
    public RestAttdEntity toEntity(RestAttdTO d) {
        if ( d == null ) {
            return null;
        }

        RestAttdEntity restAttdEntity = new RestAttdEntity();

        restAttdEntity.setStatus( d.getStatus() );
        restAttdEntity.setEmpCode( d.getEmpCode() );
        restAttdEntity.setRestAttdCode( d.getRestAttdCode() );
        restAttdEntity.setRestTypeCode( d.getRestTypeCode() );
        restAttdEntity.setRestTypeName( d.getRestTypeName() );
        restAttdEntity.setRequestDate( d.getRequestDate() );
        restAttdEntity.setStartDate( d.getStartDate() );
        restAttdEntity.setEndDate( d.getEndDate() );
        restAttdEntity.setCause( d.getCause() );
        restAttdEntity.setApplovalStatus( d.getApplovalStatus() );
        restAttdEntity.setRejectCause( d.getRejectCause() );
        restAttdEntity.setCost( d.getCost() );
        restAttdEntity.setStartTime( d.getStartTime() );
        restAttdEntity.setEndTime( d.getEndTime() );
        restAttdEntity.setNumberOfDays( d.getNumberOfDays() );

        return restAttdEntity;
    }

    @Override
    public List<RestAttdEntity> toEntity(List<RestAttdTO> d) {
        if ( d == null ) {
            return null;
        }

        List<RestAttdEntity> list = new ArrayList<RestAttdEntity>( d.size() );
        for ( RestAttdTO restAttdTO : d ) {
            list.add( toEntity( restAttdTO ) );
        }

        return list;
    }

    @Override
    public ArrayList<RestAttdEntity> toEntity(ArrayList<RestAttdTO> d) {
        if ( d == null ) {
            return null;
        }

        ArrayList<RestAttdEntity> arrayList = new ArrayList<RestAttdEntity>();
        for ( RestAttdTO restAttdTO : d ) {
            arrayList.add( toEntity( restAttdTO ) );
        }

        return arrayList;
    }
}
