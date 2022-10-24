package ru.itmo.coursework1;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FitnessClub {
    private Subscription[] gymSubscriptions = new Subscription[20];
    private Subscription[] swimmingPoolSubscriptions = new Subscription[20];
    private Subscription[] groupSessionSubscriptions = new Subscription[20];
    private final LocalTime OPENING_TIME = LocalTime.parse("08:00:00");
    private final LocalTime END_OF_DAYTIME = LocalTime.parse("16:00:00");
    private final LocalTime CLOSING_TIME = LocalTime.parse("22:00:00");
    private final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public void addClient(Subscription subscription, Zone zone) {
        if (isClubClosed()) return;
        if (isExpired(subscription)) return;
        if (isZoneForbidden(subscription, zone)) return;
        if (isZoneFull(zone)) return;
        if (isAlreadyRegistered(subscription)) return;
        switch (zone) {
            case GYM:
                for (int i = 0; i < gymSubscriptions.length; i++) {
                    if (gymSubscriptions[i] == null) {
                        gymSubscriptions[i] = subscription;
                        displayAddedClientInfo(subscription, zone);
                        return;
                    }
                }
                break;
            case SWIMMING_POOL:
                for (int i = 0; i < swimmingPoolSubscriptions.length; i++) {
                    if (swimmingPoolSubscriptions[i] == null) {
                        swimmingPoolSubscriptions[i] = subscription;
                        displayAddedClientInfo(subscription, zone);
                        return;
                    }
                }
                break;
            case GROUP_SESSION:
                for (int i = 0; i < groupSessionSubscriptions.length; i++) {
                    if (groupSessionSubscriptions[i] == null) {
                        groupSessionSubscriptions[i] = subscription;
                        displayAddedClientInfo(subscription, zone);
                        return;
                    }
                }
        }
    }

    public void removeClient(Subscription subscription) {
        for (int i = 0; i < gymSubscriptions.length; i++) {
            if (subscription.equals(gymSubscriptions[i])) {
                gymSubscriptions[i] = null;
                displayRemovedClientInfo(subscription, Zone.GYM);
                return;
            }
        }
        for (int i = 0; i < swimmingPoolSubscriptions.length; i++) {
            if (subscription.equals(swimmingPoolSubscriptions[i])) {
                swimmingPoolSubscriptions[i] = null;
                displayRemovedClientInfo(subscription, Zone.SWIMMING_POOL);
                return;
            }
        }
        for (int i = 0; i < groupSessionSubscriptions.length; i++) {
            if (subscription.equals(groupSessionSubscriptions[i])) {
                groupSessionSubscriptions[i] = null;
                displayRemovedClientInfo(subscription, Zone.GROUP_SESSION);
                return;
            }
        }
        System.out.println("This client is not currently registered");
    }

    public void displayRegisteredClientsInfo() {
        System.out.println("Clients currently registered in the gym:");
        for (int i = 0; i < gymSubscriptions.length; i++) {
            if (gymSubscriptions[i] != null) {
                System.out.println(gymSubscriptions[i]);
            }
        }
        System.out.println();
        System.out.println("Clients currently registered in the swimming pool:");
        for (int i = 0; i < swimmingPoolSubscriptions.length; i++) {
            if (swimmingPoolSubscriptions[i] != null) {
                System.out.println(swimmingPoolSubscriptions[i]);
            }
        }
        System.out.println();
        System.out.println("Clients currently registered in the group session:");
        for (int i = 0; i < groupSessionSubscriptions.length; i++) {
            if (groupSessionSubscriptions[i] != null) {
                System.out.println(groupSessionSubscriptions[i]);
            }
        }
    }

    public void close() {
        for (int i = 0; i < gymSubscriptions.length; i++) {
            if (gymSubscriptions[i] != null) {
                removeClient(gymSubscriptions[i]);
            }
        }
        for (int i = 0; i < swimmingPoolSubscriptions.length; i++) {
            if (swimmingPoolSubscriptions[i] != null) {
                removeClient(swimmingPoolSubscriptions[i]);
            }
        }
        for (int i = 0; i < groupSessionSubscriptions.length; i++) {
            if (groupSessionSubscriptions[i] != null) {
                removeClient(groupSessionSubscriptions[i]);
            }
        }
    }

    private boolean isClubClosed() {
        if (LocalTime.now().isBefore(OPENING_TIME) || LocalTime.now().isAfter(CLOSING_TIME)) {
            System.out.println("Fitness club is closed");
            return true;
        }
        return false;
    }

    private boolean isExpired(Subscription subscription) {
        if (subscription.getExpirationDate().isBefore(LocalDate.now())) {
            System.out.println("Your subscription is expired");
            return true;
        }
        return false;
    }

    private boolean isZoneForbidden(Subscription subscription, Zone zone) {
        switch (subscription.getSubscriptionType()) {
            case ONE_TIME:
                if (zone == Zone.GROUP_SESSION) {
                    System.out.println("Group sessions are not covered by your subscription");
                    return true;
                }
                break;
            case DAYTIME:
                if (zone == Zone.SWIMMING_POOL) {
                    System.out.println("Swimming pool is not covered by your subscription");
                    return true;
                }
                if (LocalTime.now().isAfter(END_OF_DAYTIME)) {
                    System.out.println("Your subscription is only valid until " + END_OF_DAYTIME.format(TIME_FORMATTER));
                    return true;
                }
        }
        return false;
    }

    private boolean isZoneFull(Zone zone) {
        switch (zone) {
            case GYM:
                for (int i = 0; i < gymSubscriptions.length; i++) {
                    if (gymSubscriptions[i] == null) {
                        return false;
                    }
                }
                break;
            case SWIMMING_POOL:
                for (int i = 0; i < swimmingPoolSubscriptions.length; i++) {
                    if (swimmingPoolSubscriptions[i] == null) {
                        return false;
                    }
                }
                break;
            case GROUP_SESSION:
                for (int i = 0; i < groupSessionSubscriptions.length; i++) {
                    if (groupSessionSubscriptions[i] == null) {
                        return false;
                    }
                }
        }
        System.out.println("Zone you are trying to attend is full");
        return true;
    }

    private boolean isAlreadyRegistered(Subscription subscription) {
        for (int i = 0; i < gymSubscriptions.length; i++) {
            if (subscription.equals(gymSubscriptions[i])) {
                System.out.println("You are already registered in the gym");
                return true;
            }
        }
        for (int i = 0; i < swimmingPoolSubscriptions.length; i++) {
            if (subscription.equals(swimmingPoolSubscriptions[i])) {
                System.out.println("You are already registered in the swimming pool");
                return true;
            }
        }
        for (int i = 0; i < groupSessionSubscriptions.length; i++) {
            if (subscription.equals(groupSessionSubscriptions[i])) {
                System.out.println("You are already registered in the group session");
                return true;
            }
        }
        return false;
    }

    private void displayAddedClientInfo(Subscription subscription, Zone zone) {
        String zoneName = "";
        switch (zone) {
            case GYM -> zoneName = "gym";
            case SWIMMING_POOL -> zoneName = "swimming pool";
            case GROUP_SESSION -> zoneName = "group session";
        }
        System.out.println("Client: " + subscription +
                " has registered in the " + zoneName +
                " at " + LocalTime.now().format(TIME_FORMATTER) + " " + LocalDate.now().format(DATE_FORMATTER));
    }

    private void displayRemovedClientInfo(Subscription subscription, Zone zone) {
        String zoneName = "";
        switch (zone) {
            case GYM -> zoneName = "gym";
            case SWIMMING_POOL -> zoneName = "swimming pool";
            case GROUP_SESSION -> zoneName = "group session";
        }
        System.out.println("Client: " + subscription +
                " has left the " + zoneName +
                " at " + LocalTime.now().format(TIME_FORMATTER) + " " + LocalDate.now().format(DATE_FORMATTER));
    }
}
