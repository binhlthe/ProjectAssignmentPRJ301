/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.plan.ProductionPlanDetail;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.plan.ProductionPlanHeader;

/**
 *
 * @author ASUS
 */
public class ProductionPlanDetailDBContext extends DBContext<ProductionPlanDetail> {

    public ArrayList<Integer> listID(int phid) {
        String sql = "select pdid,phid,[sid],[date],quantity\n"
                + "from PlanDetails\n"
                + "where phid=?";
        PreparedStatement stm = null;
        ArrayList<Integer> dids = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, phid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                dids.add(rs.getInt("pdid"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductionPlanDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dids;

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
        PreparedStatement stm = null;
        try {

            stm = connection.prepareStatement(sql);

            stm.setInt(1, model.getQuantity());
            stm.setInt(2, model.getHeader().getId());
            stm.setInt(3, model.getSid());
            stm.setDate(4, model.getDate());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductionPlanDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
    }

    @Override
    public void delete(ProductionPlanDetail model) {
        try {
            connection.setAutoCommit(false);

            ProductionPlanDetailDBContext dbDetail = new ProductionPlanDetailDBContext();
            WorkAssignmentDBContext dbWA = new WorkAssignmentDBContext();
            int id=0;

            String select_get_id = "SELECT [pdid]\n"
                    + "  FROM [PlanDetails]"
                    + "where phid=? and [sid]=? and [date]=?";
            PreparedStatement stm_select = connection.prepareStatement(select_get_id);
            stm_select.setInt(1, model.getHeader().getId());
            stm_select.setInt(2, model.getSid());
            stm_select.setDate(3, model.getDate());
            ResultSet rs = stm_select.executeQuery();
            if (rs.next()) {
                id = rs.getInt("pdid");
            }

            for (Integer waid : dbWA.listID(id)) {
                String delete_attendance_sql = "DELETE FROM [Attendances]  WHERE waid=?";
                PreparedStatement stm_delete_attendance = connection.prepareStatement(delete_attendance_sql);
                stm_delete_attendance.setInt(1, waid);
                stm_delete_attendance.executeUpdate();
            }

            String delete_workassignment_sql = "DELETE FROM [WorkAssignments]  WHERE pdid=?";
            PreparedStatement stm_delete_workassignment = connection.prepareStatement(delete_workassignment_sql);
            stm_delete_workassignment.setInt(1, id);
            stm_delete_workassignment.executeUpdate();

            String delete_detail_sql = "DELETE FROM [PlanDetails]\n"
                    + "      WHERE pdid=?";
            PreparedStatement stm_delete_detail = connection.prepareStatement(delete_detail_sql);
            stm_delete_detail.setInt(1, id);
            stm_delete_detail.executeUpdate();

            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

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
                detail.setId(rs.getInt("pdid"));
                
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
        String sql = "SELECT [pdid]\n"
                + "      ,[phid]\n"
                + "      ,[sid]\n"
                + "      ,[date]\n"
                + "      ,[quantity]\n"
                + "  FROM [PlanDetails]"
                + "where pdid=?";
        PreparedStatement stm = null;
        ProductionPlanDetail detail = new ProductionPlanDetail();
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                detail.setId(rs.getInt("pdid"));
                detail.setQuantity(rs.getInt("quantity"));
                detail.setSid(rs.getInt("sid"));
                detail.setDate(rs.getDate("date"));
                ProductionPlanHeader header = new ProductionPlanHeader();
                header.setId(rs.getInt("phid"));
                detail.setHeader(header);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductionPlanDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return detail;
    }

}
