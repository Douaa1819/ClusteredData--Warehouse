package com.progresssoft.clustereddata.repository;

import com.progresssoft.clustereddata.entity.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DealRepository extends JpaRepository<Deal,String> {
}
