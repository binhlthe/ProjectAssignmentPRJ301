/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.plan.ProductionPlanHeader;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.plan.Product;

/**
 *
 * @author ASUS
 */
public class ProductionPlanHeaderDBContext extends DBContext<ProductionPlanHeader> {

    public int getRMQuantity(ProductionPlanHeader model) {
        String sql = "select d.pdid,phid,[sid],[date],d.quantity,w.waid,eid,w.quantity,atid,a.actualquantity,alpha\n"
                + "from PlanDetails d join WorkAssignments w on d.pdid= w.pdid\n"
                + "join Attendances a on w.waid=a.waid\n"
                + "where phid=?";
        PreparedStatement stm = null;
        int sumAQ = 0;  //sum actual quantity
        int sumRMQ = 0;  //sum remained quantity
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getId());
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                sumAQ += rs.getInt("actualquantity");
            }

            sumRMQ = model.getQuantity() - sumAQ;
            model.setRemainedquantity(sumRMQ);
        } catch (SQLException ex) {
            Logger.getLogger(ProductionPlanHeaderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
        return sumRMQ;
    }

    public ArrayList<ProductionPlanHeader> listHeader(int plid) {
        String sql = "select phid,plid,p.pid,pname,quantity,estimatedeffort\n"
                + "from PlanHeaders h join Products p on h.pid=p.pid\n"
                + "where plid=?";
        PreparedStatement stm = null;
        ArrayList<ProductionPlanHeader> headers = new ArrayList<>();
        try {

            stm = connection.prepareStatement(sql);
            stm.setInt(1, plid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ProductionPlanHeader header = new ProductionPlanHeader();
                header.setId(rs.getInt("phid"));
                header.setEstimatedeffort(rs.getFloat("estimatedeffort"));
                header.setQuantity(rs.getInt("quantity"));

                Product product = new Product();
                product.setId(rs.getInt("pid"));
                product.setName(rs.getString("pname"));
                header.setProduct(product);
                headers.add(header);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductionPlanHeaderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
        return headers;
    }

    @Override
    public void insert(ProductionPlanHeader model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(ProductionPlanHeader model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(ProductionPlanHeader model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ProductionPlanHeader> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ProductionPlanHeader get(int id) {
        String sql = "select phid,quantity,estimatedeffort\n"
                + "from PlanHeaders\n"
                + "where phid=?";
        PreparedStatement stm=null;
        ProductionPlanHeader header=new ProductionPlanHeader();
        try {
            stm=connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                header.setId(id);
                header.setQuantity(rs.getInt("quantity"));
                header.setEstimatedeffort(rs.getFloat("estimatedeffort"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductionPlanHeaderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return header;
    }

}
