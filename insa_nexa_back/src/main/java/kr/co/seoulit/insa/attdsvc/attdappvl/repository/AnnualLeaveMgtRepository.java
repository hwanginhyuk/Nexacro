package kr.co.seoulit.insa.attdsvc.attdappvl.repository;

import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

@Transactional
public interface AnnualLeaveMgtRepository {

//    @Query("select e.emp")
    public void findByAnnualVacationMgtList(String applyYearMonth, String workplaceCode);
}
