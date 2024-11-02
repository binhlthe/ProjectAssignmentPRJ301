/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.workassignment;

import controller.auth.BaseRBACController;
import dal.ProductDBContext;
import dal.ProductionPlanDBContext;
import dal.ShiftDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import model.auth.User;
import model.plan.Product;
import model.plan.ProductionPlan;
import model.plan.Shift;
import java.sql.*;

/**
 *
 * @author ASUS
 */
public class WAFilterController extends BaseRBACController{

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
        
       
    }
    
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//         processRequest(req, resp);
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        processRequest(req, resp);
//    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        
    }

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        String raw_plid=req.getParameter("plid");
        String raw_pid=req.getParameter("pid");
        String raw_sid=req.getParameter("sid");
        String raw_date=req.getParameter("date");
        
        
        Integer plid=(raw_plid!=null)&&(!raw_plid.isBlank())?Integer.parseInt(raw_plid):null;
        Integer pid=(raw_pid!=null)&&(!raw_pid.isBlank())?Integer.parseInt(raw_pid):null;
        Integer sid=(raw_sid!=null)&&(!raw_sid.isBlank())?Integer.parseInt(raw_sid):null;
        Date date=(raw_date!=null)&&(!raw_date.isBlank())
                ?Date.valueOf(raw_date):null;
        
        
        
        ProductionPlanDBContext dbPlan=new ProductionPlanDBContext();
       ProductDBContext dbProduct= new ProductDBContext();
        ArrayList<ProductionPlan> plans=dbPlan.listPlan();
        ArrayList<ProductionPlan> planresults=dbPlan.search(plid, pid, sid, date);
        ShiftDBContext dbShift= new ShiftDBContext();
        ArrayList<Shift> shifts= dbShift.list();
        ArrayList<Product> products= dbProduct.list();
        req.setAttribute("products", products);
        req.setAttribute("shifts", shifts);
        
        req.setAttribute("plans", plans);
        req.setAttribute("planresults", planresults);
        req.getRequestDispatcher("../view/workassignment/filter.jsp").forward(req, resp);
    }
    
    
}
