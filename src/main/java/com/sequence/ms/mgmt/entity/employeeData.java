package com.sequence.ms.mgmt.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

//Mongodb annotation
//marks a class for the domain object that we want to persist in the db
@Document(collection = "db_sequence")
//Lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
//Spring stereotype annotation
@Component
public class employeeData {

   
   // describes the field name as it will be represented in mongodb bson document
   // offers the name to be different than the field name of the class
	@Id
   @Field("sequence_number")
   String sequence;
	
	@Field("employeeName")
	   String employeeName;
	
	@Field("position")
	   String position;
	
	@Field("createdDate")
	   String createdDate;

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

}