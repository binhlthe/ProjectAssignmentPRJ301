/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.productionplan;

import dal.DepartmentDBContext;
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
import model.Department;
import model.ProductionPlan;
import model.ProductionPlanDetail;
import model.ProductionPlanHeader;

/**
 *
 * @author ASUS
 */
public class ProductionPlanListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductionPlanDBContext dbPlan= new ProductionPlanDBContext();
        ArrayList<ProductionPlan> plans= new ArrayList<>();
        plans= dbPlan.list();
        
       
        
        ProductionPlanHeaderDBContext dbHeader= new ProductionPlanHeaderDBContext();
        for(ProductionPlan plan: plans){
            ArrayList<ProductionPlanHeader> headers= dbHeader.listHeader(plan.getId());
            for(ProductionPlanHeader header: headers){
                int sum=dbHeader.getRMQuantity(header);
                header.setRemainedquantity(sum);
                
            }
            plan.setHeaders(headers);
        }
        
        request.setAttribute("plans", plans);
        
        request.getRequestDispatcher("../view/productionplan/listtest.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }


}
