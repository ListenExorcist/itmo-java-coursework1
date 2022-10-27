package ru.itmo.coursework1;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FitnessClub {
    private Subscription[] gymSubscriptions = new Subscription[20];
    private Subscription[] swimmingPoolSubscriptions = new Subscription[20];
    private Subscription[] groupSessionSubscriptions = new Subscription[20];
    private final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public void addClient(Subscription subscription, Zone zone) {
        if (isTimeNotCovered(subscription)) return;
        if (isExpired(subscription)) return;
        if (isZoneForbidden(subscription, zone)) return;
        switch (zone) {
            case GYM:
                if (isZoneFull(gymSubscriptions)) return;
                break;
            case SWIMMING_POOL:
                if (isZoneFull(swimmingPoolSubscriptions)) return;
                break;
            case GROUP_SESSION:
                if (isZoneFull(groupSessionSubscriptions)) return;
        }
        if (isAlreadyRegistered(subscription)) return;
        switch (zone) {
            case GYM -> addClientToZone(subscription, gymSubscriptions, Zone.GYM);
            case SWIMMING_POOL -> addClientToZone(subscription, swimmingPoolSubscriptions, Zone.SWIMMING_POOL);
            case GROUP_SESSION -> addClientToZone(subscription, groupSessionSubscriptions, Zone.GROUP_SESSION);
        }
    }

    public void removeClient(Subscription subscription) {
        if (removeClientFromZone(subscription, gymSubscriptions, Zone.GYM)) return;
        if (removeClientFromZone(subscription, swimmingPoolSubscriptions, Zone.SWIMMING_POOL)) return;
        if (removeClientFromZone(subscription, groupSessionSubscriptions, Zone.GROUP_SESSION)) return;
        System.out.println("This client is not currently registered");
    }

    public void displayRegisteredClientsInfo() {
        System.out.println("Clients currently registered in the gym:");
        displayRegisteredInZoneClientsInfo(gymSubscriptions);
        System.out.println();
        System.out.println("Clients currently registered in the swimming pool:");
        displayRegisteredInZoneClientsInfo(swimmingPoolSubscriptions);
        System.out.println();
        System.out.println("Clients currently registered in the group session:");
        displayRegisteredInZoneClientsInfo(groupSessionSubscriptions);
    }

    public void close() {
        clearZone(gymSubscriptions);
        clearZone(swimmingPoolSubscriptions);
        clearZone(groupSessionSubscriptions);
    }

    private void addClientToZone(Subscription subscription, Subscription[] zoneArray, Zone zone) {
        for (int i = 0; i < zoneArray.length; i++) {
            if (zoneArray[i] == null) {
                zoneArray[i] = subscription;
                displayAddedClientInfo(subscription, zone);
                return;
            }
        }
    }

    private boolean removeClientFromZone(Subscription subscription, Subscription[] zoneArray, Zone zone) {
        for (int i = 0; i < zoneArray.length; i++) {
            if (subscription.equals(zoneArray[i])) {
                zoneArray[i] = null;
                displayRemovedClientInfo(subscription, zone);
                return true;
            }
        }
        return false;
    }

    private void displayRegisteredInZoneClientsInfo(Subscription[] zone) {
        for (int i = 0; i < zone.length; i++) {
            if (zone[i] != null) {
                System.out.println(zone[i]);
            }
        }
    }

    private void clearZone(Subscription[] zone) {
        for (int i = 0; i < zone.length; i++) {
            if (zone[i] != null) {
                removeClient(zone[i]);
            }
        }
    }

    private boolean isTimeNotCovered(Subscription subscription) {
        if (LocalTime.now().isBefore(subscription.getSubscriptionType().getAvailableTimeBegin()) ||
                LocalTime.now().isAfter(subscription.getSubscriptionType().getAvailableTimeEnd())) {
            System.out.println("Your subscription is only valid from " +
                    subscription.getSubscriptionType().getAvailableTimeBegin().format(TIME_FORMATTER) +
                    " to " + subscription.getSubscriptionType().getAvailableTimeEnd().format(TIME_FORMATTER));
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
        for (int i = 0; i < subscription.getSubscriptionType().getAvailableZones().length; i++) {
            if (zone == subscription.getSubscriptionType().getAvailableZones()[i]) return false;
        }
        System.out.println("Zone you are trying to attend is not covered by your subscription");
        return true;
    }

    private boolean isZoneFull(Subscription[] zone) {
        for (int i = 0; i < zone.length; i++) {
            if (zone[i] == null) {
                return false;
            }
        }
        System.out.println("Zone you are trying to attend is full");
        return true;
    }

    private boolean isAlreadyRegistered(Subscription subscription) {
        if (isAlreadyRegisteredInZone(subscription, gymSubscriptions) ||
                isAlreadyRegisteredInZone(subscription, swimmingPoolSubscriptions) ||
                isAlreadyRegisteredInZone(subscription, groupSessionSubscriptions)) {
            System.out.println("You are already registered");
            return true;
        }
        return false;
    }

    private boolean isAlreadyRegisteredInZone(Subscription subscription, Subscription[] zone) {
        for (int i = 0; i < zone.length; i++) {
            if (subscription.equals(zone[i])) {
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
