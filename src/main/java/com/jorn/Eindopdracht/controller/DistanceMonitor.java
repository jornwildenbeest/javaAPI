package com.jorn.Eindopdracht.controller;

import com.pi4j.io.gpio.*;
public class DistanceMonitor {
    public final static GpioController gpio = GpioFactory.getInstance();
    public GpioPinDigitalOutput sensorTriggerPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00); // Trigger pin as OUTPUT
    public GpioPinDigitalInput sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02,PinPullResistance.PULL_DOWN); // Echo pin as INPUT

    public double getDistance() throws InterruptedException{
        double distance = 0;
        try {
                // run
                Thread.sleep(2000);
                sensorTriggerPin.high(); // Make trigger pin HIGH
                Thread.sleep((long) 0.01);// Delay for 10 microseconds
                sensorTriggerPin.low(); //Make trigger pin LOW

                while(sensorEchoPin.isLow()){ //Wait until the ECHO pin gets HIGH

                }
                long startTime= System.nanoTime(); // Store the surrent time to calculate ECHO pin HIGH time.
                while(sensorEchoPin.isHigh()){ //Wait until the ECHO pin gets LOW

                }
                long endTime= System.nanoTime(); // Store the echo pin HIGH end time to calculate ECHO pin HIGH time.
                distance = ((((endTime-startTime)/1e3)/2) / 29.1);
                System.out.println("Distance :"+((((endTime-startTime)/1e3)/2) / 29.1) +" cm"); //Printing out the distance in cm
                Thread.sleep(1000);
                return distance;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return distance;
    }

}