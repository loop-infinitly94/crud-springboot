package com.crudapplication.controller;

import com.crudapplication.dto.GetStudents;
import com.crudapplication.model.StudentDetails;
import com.crudapplication.repository.StudentDetailsRepository;
import com.crudapplication.services.StudentService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@Slf4j
public class StudentDetailsController {

  @Autowired
  StudentDetailsRepository studentDetailsRepository;

  @Autowired
  StudentService studentService;

  /**
   * Method to get the Student record by id
   *
   * @param id
   * @return Student
   */
  private StudentDetails getStudRecord(long id) {
    Optional<StudentDetails> stdObj = studentDetailsRepository.findById(id);

    if (stdObj.isPresent()) {
      return stdObj.get();
    }
    return null;
  }

  /**
   * Get all the students
   *
   * @return ResponseEntity
   */
  @GetMapping("/students")
  public ResponseEntity<List<GetStudents>> getStudents() {
    try {
      List<Map<String, Object>> carts = studentService.getExternalApiData();

      // System.out.println(carts);
      log.info("get all students from db");
      List<StudentDetails> dbContents = studentDetailsRepository.findAll();
      // Create a list to hold the combined responses
      List<GetStudents> combinedResponses = new ArrayList<>();

      // get only 2 items from carts the combined responses with data from both
      // sources
      // TODO: rework on the types, new mock data
      for (int i = 0; i < dbContents.size(); i++) {
        GetStudents combinedResponse = new GetStudents();

        Map<String, ?> cartData = carts.get(i);
        combinedResponse.setId(dbContents.get(i).getId());
        combinedResponse.setName(dbContents.get(i).getName());
        combinedResponse.setClas(dbContents.get(i).getClas());
        combinedResponse.setRollno(dbContents.get(i).getRollno());
        List<Map<String, ?>> productsList = (List<Map<String, ?>>) cartData.get(
          "products"
        );

        Map<String, ?> productData = productsList.get(i);
        String title = (String) productData.get("title");
        Integer price = (Integer) productData.get("price");

        // Set titles and prices in the combined response
        combinedResponse.setTitle(title);
        combinedResponse.setPrice(price);

        combinedResponses.add(combinedResponse);
      }

      return new ResponseEntity<>(combinedResponses, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Get the Student by id
   *
   * @param id
   * @return ResponseEntity
   */
  @GetMapping("/students/{id}")
  public ResponseEntity<StudentDetails> getStudentsById(
    @PathVariable("id") long id
  ) {
    try {
      // check if Student exist in database
      StudentDetails stdObj = getStudRecord(id);

      if (stdObj != null) {
        return new ResponseEntity<>(stdObj, HttpStatus.OK);
      }

      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Get the Student by id
   *
   * @param id
   * @return ResponseEntity
   */
  @PostMapping("/students")
  public ResponseEntity<StudentDetails> postStudents(
    @RequestBody StudentDetails studentDetails
  ) {
    StudentDetails newStudent = studentDetailsRepository.save(
      StudentDetails
        .builder()
        .name(studentDetails.getName())
        .rollno(studentDetails.getRollno())
        .clas(studentDetails.getClas())
        .build()
    );
    return new ResponseEntity<>(newStudent, HttpStatus.OK);
  }

  /**
   * Get the Student by id
   *
   * @param id
   * @return ResponseEntity
   */
  @PatchMapping("/students/{id}")
  public ResponseEntity<StudentDetails> patchStudents(
    @PathVariable Integer id,
    @RequestBody StudentDetails studentDetails
  ) {
    StudentDetails student = getStudRecord(id);

    if (student == null) {
      return ResponseEntity.notFound().build();
    }

    if (studentDetails.getName() != null) {
      student.setName(studentDetails.getName());
    }
    if (studentDetails.getClas() != null) {
      student.setClas(studentDetails.getClas());
    }
    if (studentDetails.getRollno() != null) {
      student.setRollno(studentDetails.getRollno());
    }

    StudentDetails updatedUser = studentDetailsRepository.save(student);
    return ResponseEntity.ok(updatedUser);
  }

  /**
   * Get the Student by id
   *
   * @param id
   * @return ResponseEntity
   */
  @DeleteMapping("/students/{id}")
  public ResponseEntity<StudentDetails> patchStudents(@PathVariable long id) {
    StudentDetails student = getStudRecord(id);

    if (student == null) {
      return ResponseEntity.notFound().build();
    }
    studentDetailsRepository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
