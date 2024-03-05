package kr.co.seoulit.insa.commsvc.systemmgmt.mapstructMapper;

import javax.annotation.processing.Generated;
import kr.co.seoulit.insa.commsvc.systemmgmt.dto.DetailCodeDTO;
import kr.co.seoulit.insa.commsvc.systemmgmt.entity.DetailCode;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-29T13:03:12+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
public class DetailCodeMapperImpl implements DetailCodeMapper {

    @Override
    public DetailCodeDTO entityToDetailCodeDTO(DetailCode detailCode) {
        if ( detailCode == null ) {
            return null;
        }

        DetailCodeDTO detailCodeDTO = new DetailCodeDTO();

        detailCodeDTO.setDetailCodeNumber( detailCode.getDetailCodeNumber() );
        detailCodeDTO.setDetailCodeName( detailCode.getDetailCodeName() );

        return detailCodeDTO;
    }
}
