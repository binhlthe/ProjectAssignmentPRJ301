/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.plan.ProductionPlan;
import java.sql.*;
import model.plan.Department;
import model.plan.Product;
import model.plan.ProductionPlanDetail;
import model.plan.ProductionPlanHeader;
import model.plan.Shift;

/**
 *
 * @author ASUS
 */
public class ProductionPlanDBContext extends DBContext<ProductionPlan> {

    public ArrayList<ProductionPlan> listPlan() {
        String sql = "select plid,plname,startdate,enddate,did\n"
                + "from Plans ";
        PreparedStatement stm = null;

        ArrayList<ProductionPlan> plans = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ProductionPlan plan = new ProductionPlan();
                plan.setId(rs.getInt("plid"));
                plan.setName(rs.getString("plname"));
                plan.setStart(rs.getDate("startdate"));
                plan.setEnd(rs.getDate("enddate"));

                plans.add(plan);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plans;
    }

    @Override
    public void insert(ProductionPlan model) {
        try {
            connection.setAutoCommit(false);
            String sql_insert_plan = "INSERT INTO [Plans]\n"
                    + "           ([plname]\n"
                    + "           ,[startdate]\n"
                    + "           ,[enddate]\n"
                    + "           ,[did])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";

            PreparedStatement stm_insert_plan = connection.prepareStatement(sql_insert_plan);

            stm_insert_plan.setString(1, model.getName());
            stm_insert_plan.setDate(2, model.getStart());
            stm_insert_plan.setDate(3, model.getEnd());
            stm_insert_plan.setInt(4, model.getDept().getId());
            stm_insert_plan.executeUpdate();

            String sql_select_plan = "SELECT @@IDENTITY as plid";
            PreparedStatement stm_select_plan = connection.prepareStatement(sql_select_plan);
            ResultSet rs = stm_select_plan.executeQuery();
            if (rs.next()) {
                model.setId(rs.getInt("plid"));
            }

            String sql_insert_header = "INSERT INTO [PlanHeaders]\n"
                    + "           ([plid]\n"
                    + "           ,[pid]\n"
                    + "           ,[quantity]\n"
                    + "           ,[estimatedeffort])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement stm_insert_header = null;
            for (ProductionPlanHeader header : model.getHeaders()) {
                stm_insert_header = connection.prepareStatement(sql_insert_header);
                stm_insert_header.setInt(1, model.getId());
                stm_insert_header.setInt(2, header.getProduct().getId());
                stm_insert_header.setInt(3, header.getQuantity());
                stm_insert_header.setFloat(4, header.getEstimatedeffort());
                stm_insert_header.executeUpdate();
            }

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
    public void update(ProductionPlan model) {

        try {
            connection.setAutoCommit(false);
            String update_plan_sql = "UPDATE [Plans]\n"
                    + "   SET [plname] = ?\n"
                    + "      ,[startdate] =?\n"
                    + "      ,[enddate] = ?\n"
                    + "      ,[did] = ?\n"
                    + " WHERE plid=?";
            PreparedStatement stm_update_plan = null;
            stm_update_plan = connection.prepareStatement(update_plan_sql);
            stm_update_plan.setString(1, model.getName());
            stm_update_plan.setDate(2, model.getStart());
            stm_update_plan.setDate(3, model.getEnd());
            stm_update_plan.setInt(4, model.getDept().getId());
            stm_update_plan.setInt(5, model.getId());
            stm_update_plan.executeUpdate();

            String update_header_sql = "UPDATE [PlanHeaders]\n"
                    + "   SET\n"
                    + "      [quantity] = ?\n"
                    + "      ,[estimatedeffort] = ?\n"
                    + " WHERE phid=?and pid=?";
            PreparedStatement stm_update_header = null;
            stm_update_header = connection.prepareStatement(update_header_sql);
            for (ProductionPlanHeader header : model.getHeaders()) {
                stm_update_header.setInt(1, header.getQuantity());
                stm_update_header.setFloat(2, header.getEstimatedeffort());
                stm_update_header.setInt(3, header.getId());
                stm_update_header.setInt(4, header.getProduct().getId());
                stm_update_header.executeUpdate();
            }
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
    public void delete(ProductionPlan model) {
        try {
            connection.setAutoCommit(false);

            ProductionPlanDetailDBContext dbDetail = new ProductionPlanDetailDBContext();
            WorkAssignmentDBContext dbWA = new WorkAssignmentDBContext();

            for (ProductionPlanHeader header : model.getHeaders()) {
                for (Integer did : dbDetail.listID(header.getId())) {
                    for (Integer id : dbWA.listID(did)) {
                        String delete_attendance_sql = "DELETE FROM [Attendances]  WHERE waid=?";
                        PreparedStatement stm_delete_attendance = connection.prepareStatement(delete_attendance_sql);
                        stm_delete_attendance.setInt(1, id);
                        stm_delete_attendance.executeUpdate();
                    }
                }
            }

            for (ProductionPlanHeader header : model.getHeaders()) {
                for (Integer did : dbDetail.listID(header.getId())) {
                    String delete_workassignment_sql = "DELETE FROM [WorkAssignments]  WHERE pdid=?";
                    PreparedStatement stm_delete_workassignment = connection.prepareStatement(delete_workassignment_sql);
                    stm_delete_workassignment.setInt(1, did);
                    stm_delete_workassignment.executeUpdate();
                }
            }

            for (ProductionPlanHeader header : model.getHeaders()) {
                String delete_detail_sql = "DELETE FROM [PlanDetails]\n"
                        + "      WHERE phid=?";
                PreparedStatement stm_delete_detail = connection.prepareStatement(delete_detail_sql);
                stm_delete_detail.setInt(1, header.getId());
                stm_delete_detail.executeUpdate();
            }

            String delete_header_sql = "DELETE FROM [PlanHeaders]\n"
                    + "      WHERE plid=?";
            PreparedStatement stm_delete_headers = connection.prepareStatement(delete_header_sql);
            stm_delete_headers.setInt(1, model.getId());
            stm_delete_headers.executeUpdate();

            String delete_plan_sql = "DELETE FROM [Plans]\n"
                    + "      WHERE plid=?";
            PreparedStatement stm_delete_plans = connection.prepareStatement(delete_plan_sql);
            stm_delete_plans.setInt(1, model.getId());
            stm_delete_plans.executeUpdate();
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
    public ArrayList<ProductionPlan> list() {
        ArrayList<ProductionPlan> plans = new ArrayList<>();
        ArrayList<ProductionPlanHeader> headers = new ArrayList<>();

        PreparedStatement stm = null;
        String sql = "select p.plid, plname,startdate,enddate,did,pr.pid,pname,quantity,estimatedeffort\n"
                + "from Plans p join PlanHeaders  h on p.plid=h.plid\n"
                + "join Products pr on pr.pid=h.pid";
        try {

            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            ProductionPlan cPlan = new ProductionPlan();
            cPlan.setId(-1);
            while (rs.next()) {
                int plid = rs.getInt("plid");
                if (plid != cPlan.getId()) {
                    cPlan = new ProductionPlan();
                    cPlan.setId(plid);
                    cPlan.setName(rs.getString("plname"));
                    cPlan.setStart(rs.getDate("startdate"));
                    cPlan.setEnd(rs.getDate("enddate"));
                    plans.add(cPlan);

                    Department d = new Department();
                    d.setId(rs.getInt("did"));
                    cPlan.setDept(d);
                    headers = new ArrayList<>();

                }

                Product p = new Product();
                p.setId(rs.getInt("pid"));
                p.setName(rs.getString("pname"));

                ProductionPlanHeader h = new ProductionPlanHeader();

                h.setProduct(p);
                h.setQuantity(rs.getInt("quantity"));
                h.setEstimatedeffort(rs.getFloat("estimatedeffort"));
                headers.add(h);
                cPlan.setHeaders(headers);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return plans;
    }

    @Override
    public ProductionPlan get(int id) {
        PreparedStatement stm = null;
        String sql = "select p.plid,pname, plname,startdate,enddate,did,h.phid,h.pid,quantity,estimatedeffort\n"
                + "from Plans p join PlanHeaders h on p.plid=h.plid\n"
                + "join Products pr on pr.pid=h.pid\n"
                + "where p.plid=?";
        ArrayList<ProductionPlanHeader> headers = new ArrayList<>();
        ProductionPlan cPlan = new ProductionPlan();
        try {

            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            cPlan.setId(-1);
            while (rs.next()) {
                int plid = rs.getInt("plid");
                if (plid != cPlan.getId()) {
                    cPlan = new ProductionPlan();
                    cPlan.setId(plid);
                    cPlan.setName(rs.getString("plname"));
                    cPlan.setStart(rs.getDate("startdate"));
                    cPlan.setEnd(rs.getDate("enddate"));

                    Department d = new Department();
                    d.setId(rs.getInt("did"));
                    cPlan.setDept(d);
                    headers = new ArrayList<>();

                }

                Product p = new Product();
                p.setId(rs.getInt("pid"));
                p.setName(rs.getString("pname"));

                ProductionPlanHeader h = new ProductionPlanHeader();
                h.setProduct(p);
                h.setId(rs.getInt("phid"));
                h.setQuantity(rs.getInt("quantity"));
                h.setEstimatedeffort(rs.getFloat("estimatedeffort"));
                headers.add(h);
                cPlan.setHeaders(headers);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cPlan;
    }

   

    public ArrayList<ProductionPlan> search(Integer plid, Integer pid,Integer sid,Date date) {
        String sql = "select p.plid,p.plname,h.phid,pr.pid,pr.pname,d.[sid],[date],s.sname,d.pdid\n"
                + "from Plans p join PlanHeaders h on p.plid=h.plid\n"
                + "join Products pr on pr.pid=h.pid\n"
                + "join PlanDetails d on d.phid=h.phid\n"
                + "join Shifts s on d.[sid]=s.sid\n"
                + "where (1=1) ";
        
        ArrayList<Object> paramValues = new ArrayList<>();
        if(plid != null){
            sql+="and p.plid=?";
            paramValues.add(plid);
        }
        if(pid != null){
            sql+="and pr.pid=?";
            paramValues.add(pid);
        }
        if(sid != null){
            sql+="and d.[sid]=?";
            paramValues.add(sid);
        }
        if(date !=null){
            sql+="and [date]=?";
            paramValues.add(date);
        }
        
        sql+="order by plid asc, phid asc";
        
        PreparedStatement stm=null;
         ArrayList<ProductionPlan> plans = new ArrayList<>();
        ArrayList<ProductionPlanHeader> headers = new ArrayList<>();
        ArrayList<ProductionPlanDetail> details = new ArrayList<>();
        try {
            stm=connection.prepareStatement(sql);
            
            for(int i=0;i<paramValues.size();i++){
                stm.setObject(i+1, paramValues.get(i));
            }
            ResultSet rs = stm.executeQuery();
            ProductionPlan plan = new ProductionPlan();
            ProductionPlanHeader header = new ProductionPlanHeader();
            header.setId(-1);
            plan.setId(-1);
            while(rs.next()){
                int planid = rs.getInt("plid");
                if (planid != plan.getId()) {
                    plan = new ProductionPlan();
                    plan.setId(planid);
                    plan.setName(rs.getString("plname"));
                    plans.add(plan);
                    headers = new ArrayList<>();
                }
                int hid = rs.getInt("phid");
                if (hid != header.getId()) {
                    Product p = new Product();
                    p.setId(rs.getInt("pid"));
                    p.setName(rs.getString("pname"));

                    header = new ProductionPlanHeader();
                    header.setProduct(p);
                    header.setId(hid);
                    headers.add(header);
                    plan.setHeaders(headers);
                    details = new ArrayList<>();
                }
                ProductionPlanDetail detail = new ProductionPlanDetail();
                detail.setId(rs.getInt("pdid"));
                detail.setSid(rs.getInt("sid"));
                detail.setDate(rs.getDate("date"));
                Shift s = new Shift();
                s.setId(rs.getInt("sid"));
                s.setName(rs.getString("sname"));
                detail.setShift(s);
                details.add(detail);
                header.setDetail(details);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductionPlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return plans;
    }

}
