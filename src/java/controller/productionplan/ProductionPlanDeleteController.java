/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.productionplan;

import dal.ProductionPlanDBContext;
import dal.ProductionPlanDetailDBContext;
import dal.ProductionPlanHeaderDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.ProductionPlan;
import model.ProductionPlanHeader;

/**
 *
 * @author ASUS
 */
public class ProductionPlanDeleteController extends HttpServlet {

   

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().println("<p>You are not allowed to access</p>");
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id= Integer.parseInt(request.getParameter("id"));
        ProductionPlan p = new ProductionPlan();
        
        
        ProductionPlanDBContext dbPlan= new ProductionPlanDBContext();
        p=dbPlan.get(id);
        dbPlan.delete(p);
        
        response.sendRedirect("../pp/list");
    }

   

}
