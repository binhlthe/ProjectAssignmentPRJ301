/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.productionplan;

import controller.auth.BaseRBACController;
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
import model.auth.User;
import model.plan.Department;
import model.plan.Product;
import model.plan.ProductionPlan;
import model.plan.ProductionPlanHeader;

/**
 *
 * @author ASUS
 */
public class ProductionPlanCreateController extends BaseRBACController {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         ProductionPlan plan = new ProductionPlan();
        
        String raw_name=request.getParameter("name");
        if(raw_name.isEmpty()){
            response.getWriter().print("You must enter the name");
            return;
        }
        plan.setName(raw_name);
        
        String startDate= request.getParameter("from");
        String endDate= request.getParameter("to");
        
        if(startDate.isEmpty()){
            response.getWriter().print("You must enter the value");
            return;
        }
        plan.setStart(Date.valueOf(startDate));
        
        
        if(endDate.isEmpty()){
            response.getWriter().print("You must enter the value of date");
            return;
        }
        
        if(Date.valueOf(endDate).before(Date.valueOf(startDate))){
            response.getWriter().print("You must enter the end date greater than the start date");
        
        return;
        }
        
        plan.setEnd(Date.valueOf(endDate));
        
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
            header.setEstimatedeffort(raw_estimatedeffort.length()>0 && raw_estimatedeffort!= null ? Float.parseFloat(raw_estimatedeffort):0);
            
            if(header.getQuantity()>0 && header.getEstimatedeffort()>0){
                plan.getHeaders().add(header);
            }
        }
        
        if(plan.getHeaders().size()>0){
            ProductionPlanDBContext dbPlan= new ProductionPlanDBContext();
            dbPlan.insert(plan);
            response.sendRedirect("../home");
        }
        
        else{
            response.getWriter().println("Your plan does not have any headers! it is not allowed!");
        }
    }

    
    
    @Override
    protected void doAuthorizedPost(HttpServletRequest request, HttpServletResponse response, User account) throws ServletException, IOException {
        
    }

    @Override
    protected void doAuthorizedGet(HttpServletRequest request, HttpServletResponse response, User account) throws ServletException, IOException {
        DepartmentDBContext dbDept= new DepartmentDBContext();
        ProductDBContext dbProduct= new ProductDBContext();
        
        request.setAttribute("depts", dbDept.get("Production"));
        request.setAttribute("products", dbProduct.list());
        request.getRequestDispatcher("../view/productionplan/create.jsp").forward(request, response);
    }

}
