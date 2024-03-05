package kr.co.seoulit.insa.commsvc.systemmgmt.repository;

import kr.co.seoulit.insa.attdsvc.attdmgmt.to.DayAttdTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DayAttdRepository extends JpaRepository<DayAttdTO, String> {
}
