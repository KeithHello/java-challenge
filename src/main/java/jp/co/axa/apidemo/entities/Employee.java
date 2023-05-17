package jp.co.axa.apidemo.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EMPLOYEE_NAME")
    private String name;

    @Column(name = "EMPLOYEE_SALARY")
    private Integer salary;

    @Column(name = "DEPARTMENT")
    private String department;

}
