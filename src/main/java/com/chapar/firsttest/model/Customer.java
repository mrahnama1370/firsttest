package com.chapar.firsttest.model;

import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Config(entityAccessors=true)
@Entity
@Table(name = "tb_customer")
public class Customer {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "char(50)")
    private String id;

    @Column(length = 500)
    private String description;

    @Column(columnDefinition = "char(100)", nullable = false)
    private String email;

    @Column(length = 100)
    private String firstName;

    @Column
    private String lastName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
