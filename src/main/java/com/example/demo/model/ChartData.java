package com.example.demo.model;

import java.sql.Date;

import lombok.Data;

@Data

public class ChartData {
	private Date date;      
    private double totalAmount;
    
	public ChartData(Date date, double totalAmount) {
		super();
		this.date = date;
		this.totalAmount = totalAmount;
	}

	

	

   

}
