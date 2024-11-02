/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.productionplan;

import dal.DBContext;
import java.util.ArrayList;
import model.plan.Shift;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class ShiftDBContext extends DBContext<Shift> {

    @Override
    public void insert(Shift model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Shift model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Shift model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Shift> list() {
        String sql = "select [sid],sname\n"
                + "from [Shifts]";
        PreparedStatement stm=null;
        ArrayList<Shift> shifts=new ArrayList<>();
        try {
            stm=connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                Shift s=new Shift();
                s.setId(rs.getInt("sid"));
                s.setName(rs.getString("sname"));
                shifts.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ShiftDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return shifts;
    }

    @Override
    public Shift get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
