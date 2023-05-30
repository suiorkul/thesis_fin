package com.example.qualitycontroll.dal.repository;

import com.example.qualitycontroll.dal.entity.AwsDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<AwsDocument, Long> {
}
