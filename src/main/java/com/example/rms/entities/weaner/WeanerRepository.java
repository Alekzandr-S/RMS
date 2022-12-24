package com.example.rms.entities.weaner;

import com.example.rms.entities.bucks.Buck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeanerRepository extends JpaRepository<Weaner, Long> {

    @Query("select w from Weaner w " +
            "where lower(w.cageRef) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(w.cageRef) like lower(concat('%', :searchTerm, '%'))")
    List<Weaner> search(@Param("searchTerm") String searchTerm);

}
