package com.jorn.Eindopdracht;


import com.jorn.Eindopdracht.controller.DistanceMonitor;
import com.jorn.Eindopdracht.controller.databaseController;
import com.jorn.Eindopdracht.controller.ledController;
import com.jorn.Eindopdracht.helperClasses.cpu;
import com.jorn.Eindopdracht.helperClasses.devices;
import com.jorn.Eindopdracht.helperClasses.distanceSensor;
import com.jorn.Eindopdracht.helperClasses.ledAcuator;
import com.pi4j.system.SystemInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost", maxAge = 3600)
@SpringBootApplication
@EnableScheduling
@RestController
public class Main {

    public DistanceMonitor distance = new DistanceMonitor();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @RequestMapping(value = "/devices", method = RequestMethod.GET, produces = "application/json")
    public List<devices> home() throws IOException, InterruptedException {
        databaseController databaseController = new databaseController();
        System.out.println("CPU Temperature   :  " + SystemInfo.getCpuTemperature());
        return databaseController.selectAll();
    }

    @RequestMapping("/devices/{deviceId}")
    public @ResponseBody List getAttr(@PathVariable(value="deviceId") int id) {
        databaseController databaseController = new databaseController();
        databaseController.selectById("devices", "id", id);

        return databaseController.selectById("devices", "id", id);
    }

    @RequestMapping("/devices/1")
    public distanceSensor sensor() throws IllegalArgumentException {
        distanceSensor distanceSensor = new distanceSensor();
        databaseController databaseController = new databaseController();
        List elements = new ArrayList();
        // get info abount the HC-SR04.
        databaseController.selectByName("HC-SR04");

        distanceSensor.id = databaseController.id;
        distanceSensor.name = "HC-SR04";
        distanceSensor.type = databaseController.type;
        distanceSensor.datas = databaseController.selectDatasById(1, 0);

        try {
            distanceSensor.distance = distance.getDistance();
        } catch (IllegalArgumentException e) {
            System.out.println("Fout: " + e.getMessage());
            distanceSensor.distance = 0;
        } catch (InterruptedException e) {
            e.printStackTrace();
            distanceSensor.distance = 0;
        }

//        databaseController.addData(distanceSensor.id, Double.toString(distanceSensor.distance), "CURRENT_TIMESTAMP");
        return distanceSensor;
    }
    @RequestMapping("/devices/1/lastday")
    public distanceSensor lastDaySensor() throws IllegalArgumentException {
        distanceSensor distanceSensor = new distanceSensor();
        databaseController databaseController = new databaseController();
        List elements = new ArrayList();
        // get info abount the HC-SR04.
        databaseController.selectByName("HC-SR04");

        distanceSensor.id = databaseController.id;
        distanceSensor.name = "HC-SR04";
        distanceSensor.type = databaseController.type;
        distanceSensor.datas = databaseController.selectDatasById(1, 1);
        distanceSensor.distance = 0.0;

        return distanceSensor;
    }

    @RequestMapping("/devices/2")
    public cpu cpuTemp() throws IOException, InterruptedException {
        databaseController databaseController = new databaseController();
        databaseController.selectByName("CPU Raspberrypi");
        cpu cpu = new cpu();
        cpu.id = databaseController.id;
        cpu.name = "CPU Raspberrypi";
        cpu.type = databaseController.type;
        cpu.temp = SystemInfo.getCpuTemperature();
        cpu.datas = databaseController.selectDatasById(2, 0);
//        databaseController.addData(cpu.id, Float.toString(cpu.temp), "CURRENT_TIMESTAMP");

        return cpu;
    }

    @RequestMapping("/devices/2/lastday")
    public cpu lastDaycpuTemp() throws IOException, InterruptedException {
        databaseController databaseController = new databaseController();
        databaseController.selectByName("CPU Raspberrypi");
        cpu cpu = new cpu();
        cpu.id = databaseController.id;
        cpu.name = "CPU Raspberrypi";
        cpu.type = databaseController.type;
        cpu.temp = SystemInfo.getCpuTemperature();
        cpu.datas = databaseController.selectDatasById(2, 1);
//        databaseController.addData(cpu.id, Float.toString(cpu.temp), "CURRENT_TIMESTAMP");

        return cpu;
    }

    @RequestMapping("/devices/3/on")
    public ledAcuator ledOn(){
        ledController ledController = new ledController();
        ledAcuator ledAcuator = new ledAcuator();
        ledAcuator.status = "Succes";
        ledController.ledOn();

        return ledAcuator;
    }

    @RequestMapping("/devices/3/off")
    public ledAcuator ledOff() {
        ledController ledController = new ledController();
        ledAcuator ledAcuator = new ledAcuator();
        ledAcuator.status = "Succes";
        ledController.ledOff();

        return ledAcuator;
    }

}
