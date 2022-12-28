package jp.co.axa.apidemo.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EMPLOYEE")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    private String name;

    @Getter
    @Setter
//    @Column(name="EMPLOYEE_SALARY")   // remove `EMPLOYEE_` prefix and use default because the table is already an EMPLOYEE
    private Integer salary;

    @Getter
    @Setter
//    @Column(name="DEPARTMENT")
    private String department;

}
