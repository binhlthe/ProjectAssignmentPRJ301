/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author ASUS
 */
public class ProductionPlan {
    private int id;
    private String name;
    private Date start;
    private Date end;
    private Department dept;
    private ArrayList<ProductionPlanHeader> headers = new ArrayList<>();
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    public ArrayList<ProductionPlanHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(ArrayList<ProductionPlanHeader> headers) {
        this.headers = headers;
    }

   
    
    
    
    
    
}
