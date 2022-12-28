package jp.co.axa.apidemo.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="EMPLOYEE")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

//    @Column(name="EMPLOYEE_SALARY")   // remove `EMPLOYEE_` prefix and use default because the table is already an EMPLOYEE
    private Integer salary;

//    @Column(name="DEPARTMENT")
    private String department;

    private Long lastModified;

    @Column(name = "created_date", updatable = false)
    private Long createdDate;
}
