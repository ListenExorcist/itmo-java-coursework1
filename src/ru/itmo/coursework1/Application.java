package ru.itmo.coursework1;

import java.time.LocalDate;
import java.util.Random;

public class Application {
    public static void main(String[] args) {
        FitnessClub fitnessClub = new FitnessClub();

        Subscription[] clients = new Subscription[80];
        String[] names = {"Mike", "Tom", "Alex", "John", "Peter", "Jack", "Charlie", "Max", "Jenifer", "Linda", "Elizabeth", "Anna", "Antony", "Victoria", "James"};
        String[] lastNames = {"Carpenter", "Carmack", "Scott", "White", "Black", "Jackson", "Chaser", "Payne", "Freeman", "McGill", "Mitchell", "Law", "Gummer", "Draper", "Rodriguez"};

        Random random = new Random();
        SubscriptionType[] subscriptionTypes = SubscriptionType.values();
        Zone[] zones = Zone.values();

        fitnessClub.addClient(new Subscription(SubscriptionType.FULL, LocalDate.now().minusDays(2), LocalDate.now().minusDays(1), "Late", "Dude", 2000), Zone.GYM);
        Subscription doubleDude = new Subscription(SubscriptionType.FULL, LocalDate.now(), LocalDate.now().plusDays(10), "Double", "Dude", 2000);
        fitnessClub.addClient(doubleDude, Zone.SWIMMING_POOL);
        fitnessClub.addClient(doubleDude, Zone.GYM);

        for (int i = 0; i < clients.length; i++) {
            SubscriptionType subscriptionType = subscriptionTypes[random.nextInt(subscriptionTypes.length)];
            if (subscriptionType == SubscriptionType.ONE_TIME) {
                clients[i] = new Subscription(subscriptionType, LocalDate.now(), LocalDate.now().plusDays(1), names[random.nextInt(names.length)], lastNames[random.nextInt(lastNames.length)], 1970 + random.nextInt(39));
            } else {
                clients[i] = new Subscription(subscriptionType, LocalDate.now(), LocalDate.now().plusDays(1 + random.nextInt(365)), names[random.nextInt(names.length)], lastNames[random.nextInt(lastNames.length)], 1970 + random.nextInt(39));
            }
            fitnessClub.addClient(clients[i], zones[random.nextInt(zones.length)]);
        }

        System.out.println();
        fitnessClub.displayRegisteredClientsInfo();
        System.out.println();
        fitnessClub.close();
    }
}
