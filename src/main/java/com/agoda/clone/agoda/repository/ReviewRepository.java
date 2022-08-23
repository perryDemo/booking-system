package com.agoda.clone.agoda.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agoda.clone.agoda.model.Reviews;

@Repository
public interface ReviewRepository extends PagingAndSortingRepository<Reviews, Integer>{
    long countByPropertyid(Integer id);
    List<Reviews> findByPropertyid(int id);
    Page<Reviews> findByPropertyid(int id, Pageable pageable);
}
