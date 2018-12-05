package com.jorn.Eindopdracht.controller;

import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.component.temperature.impl.TmpDS18B20DeviceType;
import com.pi4j.io.w1.W1Device;
import com.pi4j.io.w1.W1Master;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class TempController {

    // Return temp
    @RequestMapping("/temp")
    public String getTemp(){
        // master controller
        W1Master master = new W1Master();

        // create list of One Wire devices
        List<W1Device> w1Devices = master.getDevices(TmpDS18B20DeviceType.FAMILY_CODE);


        // check every connected sensor
        for (W1Device device : w1Devices)
        {
            // this line is enough if you want to read the temperature
            // returns the temperature as double rounded to one decimal place after the point
            System.out.println("Temperature: " + ((TemperatureSensor) device).getTemperature());

            try
            {
                // returns the ID of the Sensor and the full text of the data
                System.out.println("1-Wire ID: " + device.getId() + " value: " + device.getValue());
                return "Temperature: " + ((TemperatureSensor) device).getTemperature();

            } catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        return "Something went wrong :(";
    }
}
