package com.crudapplication.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "studentdetails")
public class StudentDetails {
    @Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "class")
	private Integer clas;

    @Column(name = "rollno")
	private Integer rollno;
}
