package ru.itmo.coursework1;

import java.time.LocalDate;

public class Subscription {
    private SubscriptionType subscriptionType;
    private LocalDate registrationDate;
    private LocalDate expirationDate;
    private Client client;

    public Subscription(SubscriptionType subscriptionType, LocalDate registrationDate, LocalDate expirationDate, Client client) {
        this.subscriptionType = subscriptionType;
        this.registrationDate = registrationDate;
        setExpirationDate(expirationDate);
        this.client = client;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subscription subscription = (Subscription) o;

        return client.equals(subscription.client);
    }

    @Override
    public int hashCode() {
        int result = subscriptionType != null ? subscriptionType.hashCode() : 0;
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return client.toString();
    }
}
