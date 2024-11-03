/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.attendance;

import dal.AttendanceDBContext;
import dal.WorkAssignmentDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.plan.Attendance;
import model.plan.WorkAssignments;

/**
 *
 * @author ASUS
 */
public class WorkMarkController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String waid = request.getParameter("waid");
        WorkAssignmentDBContext dbWA = new WorkAssignmentDBContext();
        WorkAssignments wa = dbWA.getAtd(Integer.parseInt(waid));
        request.setAttribute("wa", wa);
        request.getRequestDispatcher("../view/attendance/workmark.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String waid = request.getParameter("waid");
        String raw_actualquantity = request.getParameter("actualquantity");
        String raw_alpha = request.getParameter("alpha");
        boolean exist = false; //check xem co ton tai waid trong attendance list chua
        if (!isNumber(raw_actualquantity)) {
            response.getWriter().print("You must enter number >0");
        }
        if (!isFloatNumber(raw_alpha)) {
            response.getWriter().print("You must enter float number >0");
            return;
        }
        
        AttendanceDBContext dbAtd = new AttendanceDBContext();
        ArrayList<Attendance> attendances = dbAtd.list();
        
        Attendance a = new Attendance();
        WorkAssignments w = new WorkAssignments();
        w.setId(Integer.parseInt(waid));
        a.setWs(w);
        a.setActualquantity(Integer.parseInt(raw_actualquantity));
        a.setAlpha(Float.parseFloat(raw_alpha));
        
        for (Attendance attendance : attendances) {
            if ((attendance.getWs().getId() == Integer.parseInt(waid))) {

                dbAtd.update(a);
                exist = true;
                
            }
        }
        
        if (exist==false) {
            dbAtd.insert(a);
        }
        response.sendRedirect("worklist");
    }

    public boolean isNumber(String str) {
        
        try {
            int number = Integer.parseInt(str);
            if (number < 0) {
                return false;
            }

        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }

    public boolean isFloatNumber(String str) {

        try {
            float number = Float.parseFloat(str);
            if (number < 0) {
                return false;
            }

        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }

}
