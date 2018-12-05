package com.jorn.Eindopdracht.controller;

import com.jorn.Eindopdracht.helperClasses.datas;
import com.jorn.Eindopdracht.helperClasses.devices;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RestController
public class databaseController {

    public int id;
    public String name;
    public String type;
    public double distance;
    public int device;
    public String data;
    public String time;


//    @Scheduled(fixedDelay = 600000)
//    public void scheduleFixedDelayTask() throws IOException, InterruptedException {
//        System.out.println(
//                "Fixed delay task - " + System.currentTimeMillis() / 1000);
//        databaseController databaseController = new databaseController();
//        databaseController.selectByName("CPU Raspberrypi");
//        cpu cpu = new cpu();
//        cpu.id = databaseController.id;
//        cpu.name = "CPU Raspberrypi";
//        cpu.type = databaseController.type;
//        cpu.temp = SystemInfo.getCpuTemperature();
//        cpu.datas = databaseController.selectDatasById(2);
//        databaseController.addData(cpu.id, Float.toString(cpu.temp), "CURRENT_TIMESTAMP");
//    }

    public List<devices> selectAll() {
        List<devices> elements = new ArrayList<devices>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eindopdrachtapi", "eindopdrachtapi", "gc9167");
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM devices;";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                devices device = new devices();

                System.out.println(rs.getString("deviceName"));
                device.id = rs.getInt("id");
                device.name = rs.getString("deviceName");
                device.type = rs.getString("type");
                elements.add(device);
            }
            rs.close();
            return elements;
        } catch (Exception e) {
            System.out.println("Fout: " + e);
        }

        return elements;
    }

    public List selectById(String column, String where, int id) {

        List elements = new ArrayList();
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eindopdrachtapi", "eindopdrachtapi", "gc9167");
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM "+ column +" WHERE "+ where +" = " + id;
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                devices device = new devices();
                System.out.println(rs.getString("deviceName"));
                device.id = rs.getInt("id");
                device.name = rs.getString("deviceName");
                device.type = rs.getString("type");
                elements.add(device);
            }
//
            rs.close();

            return elements;

        } catch (Exception e) {
            System.out.println("Fout: " + e);
        }

        return elements;
    }

    public List selectDatasById(int id, int lastday) {

        List elements = new ArrayList();
//        List<> elements = new ArrayList<devices>();
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eindopdrachtapi", "eindopdrachtapi", "gc9167");
            Statement stmt = conn.createStatement();
            String query = "";
            if(lastday == 0){
                query = "SELECT * FROM datas WHERE device = " + id;
            } else if(lastday == 1) {
                System.out.println("last day");
                query = "SELECT * FROM datas WHERE device = '"+ id +"' AND time > date_sub(now(), interval 1 DAY)";
            }
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                datas datas = new datas();
                datas.id = rs.getInt("id");
                datas.device = rs.getInt("device");
                datas.data = rs.getString("data");
                datas.time = rs.getString("time");
                elements.add(datas);
            }
            rs.close();

            return elements;

        } catch (Exception e) {
            System.out.println("Fout: " + e);
        }

        return elements;
    }

    public void selectByName(String name) {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eindopdrachtapi", "eindopdrachtapi", "gc9167");
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM devices WHERE deviceName = '"+ name +"'";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                this.id = rs.getInt("id");
                this.name = rs.getString("deviceName");
                this.type = rs.getString("type");
            }

            rs.close();
        } catch (Exception e) {
            System.out.println("Fout: " + e);
        }
    }

    // insert into database.
    public String addData(int deviceid, String value, String timestamp) {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eindopdrachtapi", "eindopdrachtapi", "gc9167");
            Statement stmt = conn.createStatement();
            String query = "INSERT into datas(id, device, data, time) VALUES (null, "+deviceid+", "+value+", "+timestamp+" )";
            stmt.execute(query);

            return "succes";

        } catch (Exception e) {
            System.out.println("Fout: " + e);
        }
        return "failed!";
    }

}

