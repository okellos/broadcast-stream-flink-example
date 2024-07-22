/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bcast;

import java.io.Serializable;

/**
 *
 * @author kenomik
 */
public class EventLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
//    private int id; 

    public EventLogs(String name) {
        this.name = name;
    }

//    public EventLogs(String name, int id) {
//        this.name = name;
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    @Override
    public String toString() {
        return "EventLogs{"
                + "name='" + name + '\''
//                + ", id=" + id
                + '}';
    }
}
