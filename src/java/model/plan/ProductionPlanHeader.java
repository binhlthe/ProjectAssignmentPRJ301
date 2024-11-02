/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.plan;

import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class ProductionPlanHeader {
    private int id;
    private Product product;
    private ProductionPlan plan;
    private int quantity;
    private float estimatedeffort;
    private int remainedquantity;
    private ArrayList<ProductionPlanDetail> detail;
    
 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductionPlan getPlan() {
        return plan;
    }

    public void setPlan(ProductionPlan plan) {
        this.plan = plan;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getEstimatedeffort() {
        return estimatedeffort;
    }

    public void setEstimatedeffort(float estimatedeffort) {
        this.estimatedeffort = estimatedeffort;
    }

    public int getRemainedquantity() {
        return remainedquantity;
    }

    public void setRemainedquantity(int remainedquantity) {
        this.remainedquantity = remainedquantity;
    }

    public ArrayList<ProductionPlanDetail> getDetail() {
        return detail;
    }

    public void setDetail(ArrayList<ProductionPlanDetail> detail) {
        this.detail = detail;
    }
    
    
    
}
