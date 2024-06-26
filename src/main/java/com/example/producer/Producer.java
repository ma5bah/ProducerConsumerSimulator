package com.example.producer;

import com.example.model.Customer;
import com.example.queue.Queues;

import java.util.Queue;
import java.util.Random;

public class Producer implements Runnable {
    private final Queues groceryQueues;
    private final Random random;
    private final int minimumArrivalTime;
    private final int maximumArrivalTime;
    private final int minimumServiceTime;
    private final int maximumServiceTime;

    public Producer(Queues groceryQueues, int minimumArrivalTime, int maximumArrivalTime, int minimumServiceTime, int maximumServiceTime) {
        this.groceryQueues = groceryQueues;
        this.random = new Random();
        this.minimumArrivalTime = minimumArrivalTime;
        this.maximumArrivalTime = maximumArrivalTime;
        this.minimumServiceTime = minimumServiceTime;
        this.maximumServiceTime = maximumServiceTime;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int arrivalTime = random.nextInt(maximumArrivalTime - minimumArrivalTime + 1) + minimumArrivalTime;
                int serviceTime = random.nextInt(maximumServiceTime - minimumServiceTime + 1) + minimumServiceTime;

                Customer customer = new Customer(serviceTime);
                groceryQueues.addCustomer(customer);

                Thread.sleep(arrivalTime);
                System.out.println("Customer arrived: " + customer.getCustomerId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Acknowledge the interrupt
            }
        }
    }
}
