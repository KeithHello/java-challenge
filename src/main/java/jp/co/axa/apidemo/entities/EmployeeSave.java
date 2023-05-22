package jp.co.axa.apidemo.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class EmployeeSave {

    private String name;

    private Integer salary;

    private String department;
}
