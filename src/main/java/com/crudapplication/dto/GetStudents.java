package com.crudapplication.dto;

import lombok.Data;

@Data
public class GetStudents {
    private long id;
	private String name;
	private Integer clas;
	private Integer rollno;

    private String title;
    private Integer price;
}
