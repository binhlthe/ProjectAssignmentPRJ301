/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.productionplan;

import dal.ProductDBContext;
import dal.ProductionPlanDBContext;
import dal.ProductionPlanDetailDBContext;
import dal.ShiftDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ProductionPlan;
import java.sql.Date;
import java.util.ArrayList;
import model.Product;
import model.ProductionPlanDetail;
import model.ProductionPlanHeader;
import model.Shift;

/**
 *
 * @author ASUS
 */
public class ProductionPlanDetailController extends HttpServlet {


    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int  plid= Integer.parseInt(request.getParameter("plid"));
        ProductionPlan plan= new ProductionPlan();
        ProductionPlanDBContext dbPlan= new ProductionPlanDBContext();
        plan=dbPlan.get(plid);
        ArrayList<Date> datePlan= new ArrayList<>();
        Date start= plan.getStart();
        Date end= plan.getEnd();
        long milisecondsinDay = 24*60*60*1000;
        
        while(!start.after(end)){
            datePlan.add(start);
            start=new Date(milisecondsinDay+start.getTime());
        }
       
        ArrayList<Shift> shifts= new ArrayList<>();
        ShiftDBContext dbShift= new ShiftDBContext();
        shifts= dbShift.list();
        
        ArrayList<ProductionPlanDetail> details= new ArrayList<>();
        ProductionPlanDetailDBContext dbDetail= new ProductionPlanDetailDBContext();
        details= dbDetail.list();
        
        
        
        request.setAttribute("details", details);
        request.setAttribute("shifts", shifts);
        request.setAttribute("datePlan", datePlan);
        
        request.setAttribute("plan", plan);
        request.getRequestDispatcher("../view/productionplan/listdetail.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] dates= request.getParameterValues("date");
        ProductionPlanDetailDBContext dbDetail=new ProductionPlanDetailDBContext();
        ArrayList<ProductionPlanDetail> details= new ArrayList<>();
        details=dbDetail.list();
        boolean exist=false;  //ktra co detail nao trung voi detail trong dbDetail
        
        
        for(String d: dates){
            
            String[] hids= request.getParameterValues("hid"+d);
            String[] sids= request.getParameterValues("sid"+d);
            for(String s: sids){
            for(String h: hids){
                
                    
                    String raw_quantity= request.getParameter("quantity"+h+s+d);  // h1,sK1, h1,sK2,h2,sK1,h2,SK2
                    
                        
                        
                         if(!isNumber(raw_quantity)){
                            response.getWriter().print("You must enter number >0");
                            return;
                        }
                    
                    if(raw_quantity != null&&!raw_quantity.isEmpty()){
                        ProductionPlanDetail detail=new ProductionPlanDetail();
                        int hid=Integer.parseInt(h);
                        Date date= Date.valueOf(d);
                        int sid=Integer.parseInt(s);
                        int quantity= Integer.parseInt(raw_quantity);
                        detail.setSid(sid);
                        ProductionPlanHeader header= new ProductionPlanHeader();
                        header.setId(hid);
                        detail.setHeader(header);
                        detail.setDate(date);
                        detail.setQuantity(quantity);
                        for(ProductionPlanDetail dt: details){
                            if(dt.getHeader().getId()==hid &&dt.getDate().compareTo(date)==0&&dt.getSid()==sid){
                                dbDetail.update(detail);
                                exist=true;
                            }
                            
                            

                        }
                        if(exist==false){
                            dbDetail.insert(detail);
                        }
                        
                        exist=false;
                        
                       
                        
                    }
                   
                }
                
            }
            
        }
       
           response.sendRedirect("list"); 
        
        
        
        
        
        
    }
    
    public boolean isNumber(String str){
        if(str.isEmpty()){
                return true;
            }
        try{
            int number= Integer.parseInt(str);
            if(number<=0){
                return false;
            }
            
        } catch(NumberFormatException exception){
            return false;
        }
        return true;
    }

   

}
