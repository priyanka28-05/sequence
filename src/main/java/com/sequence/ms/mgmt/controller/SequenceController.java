package com.sequence.ms.mgmt.controller;

import com.sequence.ms.mgmt.dataModel.DbSequenceRepo;
import com.sequence.ms.mgmt.entity.employeeData;
import lombok.extern.slf4j.Slf4j;

import org.bson.json.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

//Lombok annotation for logger
@Slf4j
//Spring annotations
@RestController
@RequestMapping("/api")

public class SequenceController {
    // Url - http://localhost:9500/api/generateSeq

	int min = 00000;
	

    @Autowired
    private employeeData employee;
    @Autowired
    private DbSequenceRepo repository;
    
    @Autowired
    private MongoOperations mongoOperations;

    @RequestMapping(value = "/addRecord", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity addRecord(@RequestBody employeeData dbSequence) throws JSONException {
        
    	min = 00000;
        String creationDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        String sequence_num = generateId(++min);
        
        
	   employee.setSequence(sequence_num);
       employee.setEmployeeName(dbSequence.getEmployeeName());
       employee.setPosition(dbSequence.getPosition());
       employee.setCreatedDate(creationDate);
       repository.save(employee);
       return new ResponseEntity(employee,HttpStatus.OK);

        
    }
    
    @GetMapping("/records")
    public List<employeeData> getRecords()
    {
    	List<employeeData> records = repository.findAll();
    	return records;
    }

    @GetMapping("/Get/{id}/record")
    public Optional<employeeData> getRecord(@PathVariable String id)
    {
    	Optional<employeeData> employeeRecord = repository.findById(id);
		return employeeRecord;
    }
    
    @DeleteMapping("/Delete/{id}/record")
    public String deleteRecord(@PathVariable String id) throws JSONException
    {
    	repository.deleteById(id);
    	JSONObject status = new JSONObject();
    	status.put("status", "success");
    	return status.toString();
    }
    
    @PutMapping("/update/{id}/record")
    public ResponseEntity<employeeData> updateRecord(@PathVariable String id,@RequestBody employeeData employee )
    {
    	Optional<employeeData> employeeRecord = repository.findById(id);
    	employee.setSequence(id);
        employee.setEmployeeName(employee.getEmployeeName());
        employee.setPosition(employee.getPosition());
        employee.setCreatedDate(employeeRecord.get().getCreatedDate());
        repository.save(employee);
        return new ResponseEntity(employee,HttpStatus.OK);
    }
    
    public String generateId(int id) {
    	
    	String seqNum =  "AB" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + String.format("%05d", id);
        
        boolean recordPresent = checkRecord(seqNum);
        if(recordPresent)
        {
        	return generateId(++min);
        }
        
		return seqNum;
    }
    
    public boolean checkRecord(String sequence)
    {
    	
    	 employeeData data = mongoOperations.findOne(
                 Query.query(Criteria.where("_id").is(sequence)),
                 employeeData.class,
                 "db_sequence");
    	 
    	 return data!=null;
    }

}
