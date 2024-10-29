/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.ProductionPlanDetail;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ProductionPlanHeader;

/**
 *
 * @author ASUS
 */
public class ProductionPlanDetailDBContext extends DBContext<ProductionPlanDetail> {

    public void delete(int phid){
        String sql="delete from PlanDetails where phid=?"; 
        PreparedStatement stm=null;
        try {
            stm=connection.prepareStatement(sql);
            stm.setInt(1, phid);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductionPlanDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            
        }
    }
    
    @Override
    public void insert(ProductionPlanDetail model) {
        String sql = "INSERT INTO [PlanDetails]\n"
                + "           ([phid]\n"
                + "           ,[sid]\n"
                + "           ,[date]\n"
                + "           ,[quantity])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
        PreparedStatement stm = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getHeader().getId());
            stm.setInt(2, model.getSid());
            stm.setDate(3, model.getDate());
            stm.setInt(4, model.getQuantity());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductionPlanDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }

    @Override
    public void update(ProductionPlanDetail model) {
        String sql = "UPDATE [PlanDetails]\n"
                    + "   SET \n"
                  
                    + "     [quantity] = ?\n"
                    + " WHERE phid=? and [sid]=? and [date]=?";
            PreparedStatement stm= null;
        try {
            
            stm= connection.prepareStatement(sql);
            
            stm.setInt(1, model.getQuantity());
            stm.setInt(2, model.getHeader().getId());
            stm.setInt(3, model.getSid());
            stm.setDate(4, model.getDate());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductionPlanDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            
        }
    }

    @Override
    public void delete(ProductionPlanDetail model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ProductionPlanDetail> list() {
        String sql = "SELECT [pdid]\n"
                + "      ,[phid]\n"
                + "      ,[sid]\n"
                + "      ,[date]\n"
                + "      ,[quantity]\n"
                + "  FROM [PlanDetails]";
        ArrayList<ProductionPlanDetail> details = new ArrayList<>();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ProductionPlanDetail detail = new ProductionPlanDetail();
                detail.setDate(rs.getDate("date"));
                detail.setQuantity(rs.getInt("quantity"));
                detail.setSid(rs.getInt("sid"));
                ProductionPlanHeader header = new ProductionPlanHeader();
                header.setId(rs.getInt("phid"));
                detail.setHeader(header);
                details.add(detail);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductionPlanDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
        }

        return details;
    }

    @Override
    public ProductionPlanDetail get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
