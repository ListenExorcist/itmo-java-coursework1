package ru.itmo.coursework1;

import java.time.LocalDate;

public class Client {
    private String firstName;
    private String lastName;
    private int birthYear;

    public Client(String firstName, String lastName, int birthYear) {
        setFirstName(firstName);
        setLastName(lastName);
        setBirthYear(birthYear);
    }

    private void setFirstName(String firstName) {
        if (firstName == null || firstName.equals("")) {
            throw new IllegalArgumentException("Illegal first name");
        }
        this.firstName = firstName;
    }

    private void setLastName(String lastName) {
        if (lastName == null || lastName.equals("")) {
            throw new IllegalArgumentException("Illegal last name");
        }
        this.lastName = lastName;
    }

    private void setBirthYear(int birthYear) {
        if (birthYear > LocalDate.now().getYear() - 14 || birthYear < LocalDate.now().getYear() - 100) {
            throw new IllegalArgumentException("Illegal birth year");
        }
        this.birthYear = birthYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (birthYear != client.birthYear) return false;
        if (!firstName.equals(client.firstName)) return false;
        return lastName.equals(client.lastName);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + birthYear;
        return result;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
