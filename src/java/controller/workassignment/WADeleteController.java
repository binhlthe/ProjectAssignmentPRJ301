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
import model.plan.WorkAssignments;

/**
 *
 * @author ASUS
 */
public class WADeleteController extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().println("<p>You are not allowed to access</p>");
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id= Integer.parseInt(request.getParameter("id"));
        int pdid=Integer.parseInt(request.getParameter("pdid"));
        WorkAssignmentDBContext  dbWa= new WorkAssignmentDBContext();
        WorkAssignments wa= dbWa.get(id);
        dbWa.delete(wa);
        response.sendRedirect("../wa/view?pdid="+pdid);
    }

}
