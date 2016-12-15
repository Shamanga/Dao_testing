import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;


public class DAOImpl implements DAO {

	private Connection conn;
	
	public DAOImpl(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/equipment", "root", "");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
    public void insertProduct (Product product) throws SQLException, DAOException{
        try{
        	
            PreparedStatement psmt = conn.prepareStatement("INSERT INTO product VALUES (?,?,?,?,?);");
            psmt.setInt(1, product.getId());
            psmt.setString(2, product.getType());
            psmt.setString(3, product.getManufacturer());
            psmt.setString(4, product.getProductionDate());
            psmt.setString(5, product.getExpiryDate());
            psmt.executeUpdate();
        }
        catch (SQLException e) {
        	throw new DAOException(e);
        }
    }
    public Product getProduct(int ID) throws SQLException, DAOException
    {
    	Product product = null;
    	try
    	{
    		
    		PreparedStatement psmt = conn.prepareStatement("SELECT * FROM product WHERE id = ?");
    		psmt.setInt(1, ID);
            ResultSet res =  psmt.executeQuery();
            while (res.next()) {
            	product = new Product(res.getInt("id"));
            	product.setType(res.getString("type"));
            	product.setManufacturer(res.getString("manufacturer"));
            	product.setProductionDate(res.getString("productionDate"));
            	product.setExpiryDate(res.getString("expiryDate"));
			}
    	}
    	catch (SQLException e) {
    		throw new DAOException(e);
		}
    	
    	return product;
    }
    public void updateProduct (Product product) throws SQLException, DAOException{
        try{
            PreparedStatement psmt = conn.prepareStatement("UPDATE `store`.`product` SET `Type` = ?, `Manufacturer` = ?, `Production_Date` = ?, `Expiry_Date` = ? WHERE `product`.`Product_ID` = ?;");
            psmt.setString(1, product.getType());
            psmt.setString(2, product.getManufacturer());
            psmt.setString(3, product.getProductionDate());
            psmt.setString(4, product.getExpiryDate());
            psmt.setInt(5, product.getId());
            psmt.executeUpdate();
        }
        catch (SQLException e) {
        	throw new DAOException(e);
        }
    }
    public void deleteProduct (int id) throws SQLException, DAOException{
        try{
            PreparedStatement psmt = conn.prepareStatement("DELETE FROM product WHERE id = ?;");
            psmt.setInt(1, id);
            psmt.executeUpdate();
        }
        catch (SQLException e) {
        	throw new DAOException(e);
        }
    }
}
