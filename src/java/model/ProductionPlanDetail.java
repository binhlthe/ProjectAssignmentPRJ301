/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.*;
/**
 *
 * @author ASUS
 */
public class ProductionPlanDetail {
    private ProductionPlanHeader header;
    private int sid;
    private Date date;
    private int quantity;

    public ProductionPlanHeader getHeader() {
        return header;
    }

    public void setHeader(ProductionPlanHeader header) {
        this.header = header;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
    
}
