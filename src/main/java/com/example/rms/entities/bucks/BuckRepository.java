package com.example.rms.entities.bucks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuckRepository extends JpaRepository<Buck, Long> {

    @Query("select b from Buck b " +
            "where lower(b.cageRef) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(b.cageRef) like lower(concat('%', :searchTerm, '%'))")
    List<Buck> search(@Param("searchTerm") String searchTerm);

}
