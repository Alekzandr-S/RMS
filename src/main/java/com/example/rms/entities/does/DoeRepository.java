package com.example.rms.entities.does;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoeRepository extends JpaRepository<Doe, Long> {

    @Query("select d from Doe d " +
            "where lower(d.cageRef) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(d.cageRef) like lower(concat('%', :searchTerm, '%'))")
    List<Doe> search(@Param("searchTerm") String searchTerm);

    @Query("SELECT c FROM Doe c WHERE c.mated = true ")
    List<Doe> searchPlaceB(@Param("searchTerm") Boolean active);

    @Query("SELECT c FROM Doe c WHERE c.pregnant = true ")
    List<Doe> searchP(@Param("searchTerm") Boolean active);
}
