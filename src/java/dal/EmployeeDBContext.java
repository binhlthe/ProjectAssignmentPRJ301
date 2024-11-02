/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.plan.Employee;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.plan.Department;

/**
 *
 * @author ASUS
 */
public class EmployeeDBContext extends DBContext<Employee> {

    @Override
    public void insert(Employee model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Employee model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Employee model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Employee> list() {
        return null;
        
    }

    @Override
    public Employee get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public ArrayList<Employee> list(int sid){
        String sql = "select eid,ename,e.did,dname\n"
                + "from Employees e\n"
                + "join Departments d on e.did=d.did\n"
                + "where d.type='Production' and sid=?";
        PreparedStatement stm=null;
        ArrayList<Employee> emps=new ArrayList<>();
        try {
            stm=connection.prepareStatement(sql);
            stm.setInt(1, sid);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                Employee e=new Employee();
                e.setId(rs.getInt("eid"));
                e.setName(rs.getString("ename"));
                Department d= new Department();
                d.setId(rs.getInt("did"));
                d.setName(rs.getString("dname"));
                e.setDepartment(d);
                emps.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emps;
    }

}
