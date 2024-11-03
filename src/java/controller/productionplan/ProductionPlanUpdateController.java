/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.productionplan;

import dal.DepartmentDBContext;
import dal.ProductDBContext;
import dal.ProductionPlanDBContext;
import dal.ProductionPlanDetailDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import model.plan.Department;
import model.plan.Product;
import model.plan.ProductionPlan;
import model.plan.ProductionPlanDetail;
import model.plan.ProductionPlanHeader;
/**
 *
 * @author ASUS
 */
public class ProductionPlanUpdateController extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductionPlan plan = new ProductionPlan();
        ProductionPlanDetailDBContext dbDetail= new ProductionPlanDetailDBContext();
        String id= request.getParameter("plid");
        plan.setId(Integer.parseInt(id));
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
        
        //xóa các detail có date < start và date > end
        for(ProductionPlanHeader header: plan.getHeaders()){
            ArrayList<Integer> listIDDetail = new ArrayList<>();
            listIDDetail= dbDetail.listID(header.getId());
            for(Integer index: listIDDetail){
                ProductionPlanDetail detail= new ProductionPlanDetail();
                detail=dbDetail.get(index);
                if(detail.getDate().before(Date.valueOf(startDate))||detail.getDate().after(Date.valueOf(endDate))){
                    dbDetail.delete(detail);
                }
            }
        }
        
        Department d = new Department();
        int did= Integer.parseInt(request.getParameter("did"));
        d.setId(did);
        
        plan.setDept(d);
        
        String[] pids= request.getParameterValues("pid");
        for(String pid: pids){
            int hid=Integer.parseInt(request.getParameter("hid"+pid));
            
            Product p = new Product();
            p.setId(Integer.parseInt(pid));
            
            ProductionPlanHeader header= new ProductionPlanHeader();
            header.setId(hid);
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
            dbPlan.update(plan);
            response.sendRedirect("../home");
        }
        
        else{
            response.getWriter().println("Your plan does not have any headers! it is not allowed!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int plid= Integer.parseInt(req.getParameter("plid"));
        ProductionPlanDBContext dbPlan= new ProductionPlanDBContext();
        ProductionPlan plan= dbPlan.get(plid);
        
        DepartmentDBContext dbDept= new DepartmentDBContext();
        ProductDBContext dbProduct= new ProductDBContext();
        
        req.setAttribute("depts", dbDept.get("Production"));
        req.setAttribute("products", dbProduct.list());
        req.setAttribute("plan", plan);
        req.getRequestDispatcher("../view/productionplan/update.jsp").forward(req, resp);
    }
    
}
