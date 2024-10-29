/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.productionplan;

import dal.DepartmentDBContext;
import dal.ProductDBContext;
import dal.ProductionPlanDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import model.Department;
import model.Product;
import model.ProductionPlan;
import model.ProductionPlanHeader;

/**
 *
 * @author ASUS
 */
public class ProductionPlanCreateController extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DepartmentDBContext dbDept= new DepartmentDBContext();
        ProductDBContext dbProduct= new ProductDBContext();
        
        request.setAttribute("depts", dbDept.get("Production"));
        request.setAttribute("products", dbProduct.list());
        request.getRequestDispatcher("../view/productionplan/create.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductionPlan plan = new ProductionPlan();
        plan.setName(request.getParameter("name"));
        plan.setStart(Date.valueOf(request.getParameter("from")));
        plan.setEnd(Date.valueOf(request.getParameter("to")));
        
        Department d = new Department();
        d.setId(Integer.parseInt(request.getParameter("did")));
        
        plan.setDept(d);
        String[] pids= request.getParameterValues("pid");
        for(String pid: pids){
            Product p = new Product();
            p.setId(Integer.parseInt(pid));
            
            ProductionPlanHeader header= new ProductionPlanHeader();
            header.setProduct(p);
            
            String raw_quantity= request.getParameter("quantity"+pid);
            String raw_estimatedeffort= request.getParameter("estimatedeffort"+pid);
            header.setQuantity(raw_quantity.length()>0 && raw_quantity!= null ? Integer.parseInt(raw_quantity):0);
            header.setEstimatedeffort(raw_estimatedeffort.length()>0 && raw_estimatedeffort!= null ? Integer.parseInt(raw_estimatedeffort):0);
            
            if(header.getQuantity()>0 && header.getEstimatedeffort()>0){
                plan.getHeaders().add(header);
            }
        }
        
        if(plan.getHeaders().size()>0){
            ProductionPlanDBContext dbPlan= new ProductionPlanDBContext();
            dbPlan.insert(plan);
            response.sendRedirect("../pp/list");
        }
        
        else{
            response.getWriter().println("Your plan does not have any headers! it is not allowed!");
        }
        
     
    }

}
