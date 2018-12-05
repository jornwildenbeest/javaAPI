package com.jorn.Eindopdracht.controller;

import com.pi4j.io.gpio.*;

public class ledController {

    // define the pin number that has the LED connected
    public final static Pin LED_PIN = RaspiPin.GPIO_01;

    // Initialize the Wiring Pi Library
    public final static GpioController gpio = GpioFactory.getInstance();

    public static GpioPinDigitalOutput led = gpio.provisionDigitalOutputPin(LED_PIN, "led", PinState.LOW);

    public static void ledOn()
    {
        led.low ();
    }
    public static void ledOff()
    {
        led.high();
    }
}
