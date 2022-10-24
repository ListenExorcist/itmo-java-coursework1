package ru.itmo.coursework1;

import java.time.LocalDate;

public class Subscription {
    private SubscriptionType subscriptionType;
    private LocalDate registrationDate;
    private LocalDate expirationDate;
    private String ownerFirstName;
    private String ownerLastName;
    private int ownerBirthYear;

    public Subscription(SubscriptionType subscriptionType, LocalDate registrationDate, LocalDate expirationDate, String ownerFirstName, String ownerLastName, int ownerBirthYear) {
        this.subscriptionType = subscriptionType;
        this.registrationDate = registrationDate;
        setExpirationDate(expirationDate);
        setOwnerFirstName(ownerFirstName);
        setOwnerLastName(ownerLastName);
        setOwnerBirthYear(ownerBirthYear);
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    private void setExpirationDate(LocalDate expirationDate) {
        if (expirationDate.isBefore(registrationDate)) {
            throw new IllegalArgumentException("Illegal expiration date");
        }
        if (subscriptionType == SubscriptionType.ONE_TIME && !(expirationDate.equals(registrationDate.plusDays(1)))) {
            throw new IllegalArgumentException("Illegal expiration date for one time subscription");
        }
        this.expirationDate = expirationDate;
    }

    private void setOwnerFirstName(String ownerFirstName) {
        if (ownerFirstName == null || ownerFirstName.equals("")) {
            throw new IllegalArgumentException("Illegal first name");
        }
        this.ownerFirstName = ownerFirstName;
    }

    private void setOwnerLastName(String ownerLastName) {
        if (ownerLastName == null || ownerLastName.equals("")) {
            throw new IllegalArgumentException("Illegal last name");
        }
        this.ownerLastName = ownerLastName;
    }

    private void setOwnerBirthYear(int ownerBirthYear) {
        if (ownerBirthYear > LocalDate.now().getYear() - 14 || ownerBirthYear < LocalDate.now().getYear() - 100) {
            throw new IllegalArgumentException("Illegal birth year");
        }
        this.ownerBirthYear = ownerBirthYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subscription subscription = (Subscription) o;

        if (ownerBirthYear != subscription.ownerBirthYear) return false;
        if (!ownerFirstName.equals(subscription.ownerFirstName)) return false;
        return ownerLastName.equals(subscription.ownerLastName);
    }

    @Override
    public int hashCode() {
        int result = ownerFirstName.hashCode();
        result = 31 * result + ownerLastName.hashCode();
        result = 31 * result + ownerBirthYear;
        return result;
    }

    @Override
    public String toString() {
        return ownerFirstName + " " + ownerLastName;
    }
}
