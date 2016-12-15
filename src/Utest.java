import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;

@RunWith(MockitoJUnitRunner.class)

public class Utest {
	DAOImpl realDAO = new DAOImpl();
	@Mock 
	Connection conn;
	@Mock
	PreparedStatement psmt;
	@InjectMocks
	DAOImpl testingDAO = new DAOImpl();	
	
	@SuppressWarnings("deprecation")
	@Test
	public void testProductCon()
	{
		Product newp = new Product(1);
		Assert.assertTrue(newp.getId() == 1);
		}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testsetType()
	{
		Product newp = new Product(1);
		newp.setType("myType");
		Assert.assertTrue(newp.getType().equals("myType"));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testsetManufacturer()
	{
		Product newp = new Product(1);
		newp.setManufacturer("myManufacturer");
		Assert.assertTrue(newp.getManufacturer().equals("myManufacturer"));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testsetProductionDate()
	{
		Product newp = new Product(1);
		newp.setProductionDate("myProductionDate");
		Assert.assertTrue(newp.getProductionDate().equals("myProductionDate"));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testsetExpiryDate()
	{
		Product newp = new Product(1);
		newp.setExpiryDate("myExpiryDate");
		Assert.assertTrue(newp.getExpiryDate().equals("myExpiryDate"));
	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void HappyTest2() throws SQLException, DAOException{
		when(conn.prepareStatement(anyString())).thenReturn(psmt);
		ArgumentCaptor<Integer> integercaptor = ArgumentCaptor.forClass(Integer.class);
		Product p = new Product(1);
		ArgumentCaptor<String> stringcaptor = ArgumentCaptor.forClass(String.class);
		p.setType("myType");
		p.setManufacturer("myManufacturer");
		p.setProductionDate("myProductionDate");
		p.setExpiryDate("myExpiryDate");
		
		testingDAO.insertProduct(p);
		verify(psmt, times(1)).setInt(anyInt(), integercaptor.capture());
		Assert.assertTrue(integercaptor.getAllValues().get(0).equals(1));
		verify(psmt, times(4)).setString(anyInt(), stringcaptor.capture());
		Assert.assertTrue(stringcaptor.getAllValues().get(0).equals("myType"));
		Assert.assertTrue(stringcaptor.getAllValues().get(1).equals("myManufacturer"));
		Assert.assertTrue(stringcaptor.getAllValues().get(2).equals("myProductionDate"));
		Assert.assertTrue(stringcaptor.getAllValues().get(3).equals("myExpiryDate"));
	}
	
	@Test (expected = DAOException.class)
	public void ExceptionCase() throws SQLException, DAOException{
		when(conn.prepareStatement(anyString())).thenReturn(psmt);
		when(psmt.executeUpdate()).thenThrow(new SQLException());
		Product p1 = new Product(2);
		testingDAO.insertProduct(p1);
	}
	
	@SuppressWarnings("deprecation")
	
	@Test
	public void LiveHappyTest()throws SQLException, DAOException
	{
		Product p2 = new Product(3);
		p2.setType("myType");
		p2.setManufacturer("myManufacturer");
		p2.setProductionDate("myProductionDate");
		p2.setExpiryDate("myExpiryDate");
		
		realDAO.insertProduct(p2);
		Product retrieved_p = realDAO.getProduct(3);
		Assert.assertTrue(retrieved_p.getId() == 3);
		Assert.assertTrue(retrieved_p.getType().equals("myType"));
		Assert.assertTrue(retrieved_p.getManufacturer().equals("myManufacturer"));
		Assert.assertTrue(retrieved_p.getProductionDate().equals("myProductionDate"));
		Assert.assertTrue(retrieved_p.getExpiryDate().equals("myExpiryDate"));
		realDAO.deleteProduct(3);
		Assert.assertTrue(realDAO.getProduct(3)== null);	
		
		
	}
	
}
