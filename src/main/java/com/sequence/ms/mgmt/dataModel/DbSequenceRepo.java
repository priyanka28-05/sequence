package com.sequence.ms.mgmt.dataModel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sequence.ms.mgmt.entity.employeeData;

@Repository
public interface DbSequenceRepo   extends MongoRepository<employeeData, String> {

}
