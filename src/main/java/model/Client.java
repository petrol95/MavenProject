package model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Client {

    private int id;
    private String firstName;
    private String lastName;
    private LocalDate birthDay;

    private final Set<Phone> phones = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    @Override
    public String toString() {
        return "model.Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDay=" + birthDay +
                ", phones=" + phones +
                '}';
    }
}
