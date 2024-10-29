/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class Attendance {
    private int id;
    private WorkAssignments ws;
    private int actualquantity;
    private float alpha;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WorkAssignments getWs() {
        return ws;
    }

    public void setWs(WorkAssignments ws) {
        this.ws = ws;
    }

    public int getActualquantity() {
        return actualquantity;
    }

    public void setActualquantity(int actualquantity) {
        this.actualquantity = actualquantity;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
    
    
}
