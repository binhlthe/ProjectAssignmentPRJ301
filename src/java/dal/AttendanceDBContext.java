/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.plan.Attendance;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.plan.WorkAssignments;

/**
 *
 * @author ASUS
 */
public class AttendanceDBContext extends DBContext<Attendance> {

    @Override
    public void insert(Attendance model) {
        String sql = "INSERT INTO [Attendances]\n"
                + "           ([waid]\n"
                + "           ,[actualquantity]\n"
                + "           ,[alpha]\n"
                + "           )\n"
                + "     VALUES\n"
                + "           (?,?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getWs().getId());
            stm.setInt(2, model.getActualquantity());
            stm.setFloat(3, model.getAlpha());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Attendance model) {
        String sql = "UPDATE [Attendances]\n"
                + "   SET [waid] = ?\n"
                + "      ,[actualquantity] = ?\n"
                + "      ,[alpha] = ?\n"
                + "      \n"
                + " WHERE waid=?";
        PreparedStatement stm=null;
        try {
            stm=connection.prepareStatement(sql);
            stm.setInt(1, model.getWs().getId());
            stm.setInt(2, model.getActualquantity());
            stm.setFloat(3, model.getAlpha());
            stm.setInt(4, model.getWs().getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Attendance model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Attendance> list() {
        String sql = "select waid, actualquantity, alpha\n"
                + "from Attendances";
        PreparedStatement stm = null;
        ArrayList<Attendance> attendances = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Attendance a = new Attendance();
                a.setActualquantity(rs.getInt("actualquantity"));
                a.setAlpha(rs.getFloat("alpha"));
                WorkAssignments w = new WorkAssignments();
                w.setId(rs.getInt("waid"));
                a.setWs(w);
                attendances.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return attendances;
    }

    @Override
    public Attendance get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
