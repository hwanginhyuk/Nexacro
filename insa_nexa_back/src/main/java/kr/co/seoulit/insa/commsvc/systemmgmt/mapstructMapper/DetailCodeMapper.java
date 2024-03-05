package kr.co.seoulit.insa.commsvc.systemmgmt.mapstructMapper;

import kr.co.seoulit.insa.commsvc.systemmgmt.dto.DetailCodeDTO;
import kr.co.seoulit.insa.commsvc.systemmgmt.entity.DetailCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper // MapStruct인터페이스도 @Mapper를 사용하는데, 인터페이스를 매퍼로 지정합니다. 이 인터페이스는 MapStruct에 의해 구현체가 생성됩니다.
public interface DetailCodeMapper {
    DetailCodeMapper INSTANCE = Mappers.getMapper(DetailCodeMapper.class); // 싱글톤 형태의 매퍼 인스턴스를 생성하는 객체

    // DetailCodeEntity -> DetailCodeDTO 매핑
    // (조회이므로 resDTO만 필요함. 그래서 굳이 resDTO,reqDTO둘다 안만들고 DTO하나만 만든거같음 - 조회만 하니까)

    @Mapping(target = "detailCodeNumber", source = "detailCode.detailCodeNumber")
    @Mapping(target = "detailCodeName", source = "detailCode.detailCodeName")
//    @Mapping: 해당 애너테이션은 매핑을 지정할 때 사용됩니다. 엔티티와 DTO의 필드 간 매핑을 정의합니다.
//    target: 매핑의 대상이 될 DTO의 필드 이름을 지정합니다.
//    source: 매핑할 Entity의 필드 이름을 지정합니다.
    DetailCodeDTO entityToDetailCodeDTO(DetailCode detailCode); //Entity에서 DTO로 바꿔주는 메서드
}
