/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.workassignment;

import dal.WorkAssignmentDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.plan.WorkAssignments;

/**
 *
 * @author ASUS
 */
public class WAListController extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pdid= Integer.parseInt(request.getParameter("pdid"));
        WorkAssignmentDBContext dbWa=new WorkAssignmentDBContext();
        ArrayList<WorkAssignments> was=dbWa.list(pdid);
        request.setAttribute("pdid", pdid);
        request.setAttribute("was", was);
        request.getRequestDispatcher("../view/workassignment/list.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

    

}
