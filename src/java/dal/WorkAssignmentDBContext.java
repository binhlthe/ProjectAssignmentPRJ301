/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.plan.Attendance;
import model.plan.Employee;
import model.plan.ProductionPlanDetail;
import model.plan.WorkAssignments;

/**
 *
 * @author ASUS
 */
public class WorkAssignmentDBContext extends DBContext<WorkAssignments> {

    public ArrayList<WorkAssignments> list(int pdid) {
        String sql = "select waid,pdid ,w.eid,quantity,ename\n"
                + "from WorkAssignments w join Employees e on w.eid=e.eid\n"
                + "where pdid=?";
        PreparedStatement stm = null;
        ArrayList<WorkAssignments> was = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, pdid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                WorkAssignments wa = new WorkAssignments();
                wa.setId(rs.getInt("waid"));
                Employee e = new Employee();
                e.setId(rs.getInt("eid"));
                e.setName(rs.getString("ename"));
                wa.setEmployee(e);
                wa.setQuantity(rs.getInt("quantity"));
                was.add(wa);
            }
        } catch (SQLException ex) {
            Logger.getLogger(WorkAssignmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return was;
    }

    public ArrayList<Integer> listID(int did) {
        String sql = "select waid,pdid,eid,quantity\n"
                + "from WorkAssignments\n"
                + "where pdid=?";
        PreparedStatement stm = null;
        ArrayList<Integer> waids = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, did);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                waids.add(rs.getInt("waid"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(WorkAssignmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return waids;
    }

    @Override
    public void insert(WorkAssignments model) {
        String sql = "INSERT INTO [WorkAssignments]\n"
                + "           ([pdid]\n"
                + "           ,[eid]\n"
                + "           ,[quantity])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getDetail().getId());
            stm.setInt(2, model.getEmployee().getId());
            stm.setInt(3, model.getQuantity());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WorkAssignmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void update(WorkAssignments model) {
        String sql = "UPDATE [WorkAssignments]\n"
                + "   SET \n"
                + "      [quantity] = ?\n"
                + " WHERE pdid=? and eid=?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getQuantity());
            stm.setInt(2, model.getDetail().getId());
            stm.setInt(3, model.getEmployee().getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WorkAssignmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(WorkAssignments model) {
        String sql = "DELETE FROM [Attendances]\n"
                + "WHERE waid=?\n"
                + "\n"
                + "DELETE FROM [WorkAssignments]\n"
                + "      WHERE waid=?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getId());
            stm.setInt(2, model.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WorkAssignmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<WorkAssignments> list() {
        String sql = "select w.waid,  pdid ,w.eid,e.ename,quantity\n"
                + "from WorkAssignments w\n"
                + "join Employees e on w.eid=e.eid\n"
                + "order by pdid asc";
        PreparedStatement stm = null;
        ArrayList<WorkAssignments> was = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                WorkAssignments wa = new WorkAssignments();
                wa.setId(rs.getInt("waid"));
                wa.setQuantity(rs.getInt("quantity"));
                ProductionPlanDetail detail = new ProductionPlanDetail();
                detail.setId(rs.getInt("pdid"));
                wa.setDetail(detail);
                Employee e = new Employee();
                e.setId(rs.getInt("eid"));
                e.setName(rs.getString("ename"));
                wa.setEmployee(e);
                was.add(wa);
            }
        } catch (SQLException ex) {
            Logger.getLogger(WorkAssignmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return was;
    }

    @Override
    public WorkAssignments get(int id) {
        String sql = "select waid,pdid,eid,quantity\n"
                + "from WorkAssignments\n"
                + "where waid=?";
        PreparedStatement stm = null;
        WorkAssignments wa = new WorkAssignments();
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                wa.setId(id);
                wa.setQuantity(rs.getInt("quantity"));
                Employee e = new Employee();
                e.setId(rs.getInt("eid"));
                ProductionPlanDetail detail = new ProductionPlanDetail();
                detail.setId(rs.getInt("pdid"));
                wa.setDetail(detail);
                wa.setEmployee(e);

            }
        } catch (SQLException ex) {
            Logger.getLogger(WorkAssignmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wa;
    }

    public WorkAssignments getAtd(int waid) {
        String sql = "select w.waid,actualquantity,alpha\n"
                + "from WorkAssignments w join Attendances a on w.waid=a.waid\n"
                + "where w.waid=?";
        PreparedStatement stm=null;
        WorkAssignments w=new WorkAssignments();
        w.setId(waid);
        EmployeeDBContext dbEmp=new EmployeeDBContext();
        Employee e=dbEmp.getEmp(waid);
        w.setEmployee(e);
               
                
        try {
            stm=connection.prepareStatement(sql);
            stm.setInt(1, waid);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                
                
                
                Attendance a=new Attendance();
                a.setActualquantity(rs.getInt("actualquantity"));
                a.setAlpha(rs.getFloat("alpha"));
                w.setAttendance(a);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(WorkAssignmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return w;
    }
    
    

}
