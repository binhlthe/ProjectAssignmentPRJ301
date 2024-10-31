/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.productionplan;

import controller.auth.BaseRBACController;
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
import model.auth.User;
import model.plan.Department;
import model.plan.ProductionPlan;
import model.plan.ProductionPlanDetail;
import model.plan.ProductionPlanHeader;

/**
 *
 * @author ASUS
 */
public class ProductionPlanListController extends BaseRBACController {


    
    

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void doAuthorizedGet(HttpServletRequest request, HttpServletResponse response, User account) throws ServletException, IOException {
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
        
        request.getRequestDispatcher("../view/productionplan/list.jsp").forward(request, response);
    }


}
