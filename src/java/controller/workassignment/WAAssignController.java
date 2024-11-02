package controller.workassignment;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import dal.EmployeeDBContext;
import dal.ProductionPlanDetailDBContext;
import dal.WorkAssignmentDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.plan.Employee;
import model.plan.ProductionPlanDetail;
import model.plan.WorkAssignments;

/**
 *
 * @author ASUS
 */
public class WAAssignController extends HttpServlet {

    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pdid=request.getParameter("pdid");
        ProductionPlanDetailDBContext dbDetail=new ProductionPlanDetailDBContext();
        ProductionPlanDetail detail=dbDetail.get(Integer.parseInt(pdid));
        EmployeeDBContext dbEmp=new EmployeeDBContext();
        ArrayList<Employee> emps=dbEmp.list(detail.getSid());
        request.setAttribute("emps", emps);
        request.setAttribute("detail", detail);
        request.getRequestDispatcher("../view/workassignment/assign.jsp").forward(request, response);
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean exist=true;
        WorkAssignmentDBContext dbWA=new WorkAssignmentDBContext();
        ArrayList<WorkAssignments> was=dbWA.list();  //list tat ca cac wa
        
        
        String indexes=request.getParameter("indexes");
        int pdid=Integer.parseInt(request.getParameter("pdid"));
        ProductionPlanDetailDBContext dbDetail=new ProductionPlanDetailDBContext();
        ProductionPlanDetail detail=new ProductionPlanDetail();
        detail=dbDetail.get(pdid);
        ArrayList<WorkAssignments> wasResult=dbWA.list(pdid);  //list tat ca cac wa trung voi pdid
        int totalquantity=0;
        for(WorkAssignments w: wasResult){
            totalquantity+=w.getQuantity();
        }
        
        String[] ids= indexes.split(",");
        for(String id:ids){
           
            exist=true;
            WorkAssignments wa=new WorkAssignments();
            Employee e=new Employee();
            String eid= request.getParameter("eid"+id);
            e.setId(Integer.parseInt(eid));
            String quantity= request.getParameter("quantity"+id);
            wa.setQuantity(Integer.parseInt(quantity));
            wa.setEmployee(e);
            wa.setDetail(detail);
            totalquantity+=Integer.parseInt(quantity);
            for(WorkAssignments w: was){
                if((w.getEmployee().getId()==Integer.parseInt(eid)) && (w.getDetail().getId()==pdid)){
                    dbWA.update(wa);
                    exist=false;
                }
            }
            if(totalquantity>detail.getQuantity()){
                response.getWriter().print("You must...");
                return;
            }
           
            
            if(exist==true){
                dbWA.insert(wa);
            }
            
            
        }
    }


}
