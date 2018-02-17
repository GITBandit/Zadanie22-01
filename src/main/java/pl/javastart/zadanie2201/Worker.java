package pl.javastart.zadanie2201;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.hibernate.annotations.Generated;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Worker {

    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;
    @Column(nullable = false)
    @Size(min = 11, max = 11)
    @Pattern(regexp = "[0-9]+")
    private String PESEL;
    private double salary;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate workingSince;
    @ManyToOne
    private Company company;
    @Transient
    private long companyAsLong;

    public Worker (){

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPESEL() {
        return PESEL;
    }

    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDate getWorkingSince() {
        return workingSince;
    }

    public void setWorkingSince(LocalDate workingSince) {
        this.workingSince = workingSince;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public long getCompanyAsLong() {
        return companyAsLong;
    }

    public void setCompanyAsLong(long companyAsLong) {
        this.companyAsLong = companyAsLong;
    }
}
