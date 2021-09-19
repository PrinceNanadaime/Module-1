package jm.task.core.jdbc.model;

import javax.persistence.*;

@Entity
@Table(name = "testtable", schema = "mysql")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String first;

    @Column
    private String last;

    @Column
    private Byte age;

    public User() {

    }

    public User(String name, String lastName, Byte age) {
        this.first = name;
        this.last = lastName;
        this.age = age;
    }

    @Override
    public String toString(){
        return "User " + getName() + " " +
                getLastName() + " with ID: " + getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return first;
    }

    public void setName(String name) {
        this.first = name;
    }

    public String getLastName() {
        return last;
    }

    public void setLastName(String lastName) {
        this.last = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

}
