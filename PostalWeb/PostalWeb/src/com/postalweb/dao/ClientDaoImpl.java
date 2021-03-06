package com.postalweb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.postalweb.config.Db;
import com.postalweb.guid.GenerateUUID;
import com.postalweb.model.Client;
import com.postalweb.states.FindStatesCites;

public class ClientDaoImpl implements ClientDao {
	Connection con=null;

	
	@Override
	public Client addlead(Client client) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt=null;
		Client statusModel=new Client();
		try{

			Db db=new Db();

			con=db.mysqlConnect();

			GenerateUUID guid=new GenerateUUID();

			String client_leadid=guid.generateDelearListingId();

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Date today = Calendar.getInstance().getTime();        

			String currentDate = df.format(today);

			String lead_creat_by="1";


			FindStatesCites cObj=new FindStatesCites();

			Map<String, Integer> states = cObj.getStates();

			Map<Integer, Map<String, Integer>> cites=cObj.getCites();


			String state = client.getState();

			String city = client.getCity();

			Integer state_id=null;

			Integer citi_id=null;


			if(!state.equals("")){

				state_id=states.get(state.replaceAll("\\s+","").toLowerCase().trim());

			}


			if(state_id!=null){

				Map<String, Integer> citi_map=cites.get(state_id);

				citi_id=citi_map.get(city.replaceAll("\\s+","").toLowerCase().trim());

			}

			String state_value = String.valueOf(state_id);

			String city_value = String.valueOf(citi_id);


			String adding_client_query="insert into lead_details(lead_id,clientid,first_name,last_name,gender,age,door_no,street_name,area_name,taluk,city,pincode,state,lead_created_by,lead_created_date,lead_verified_date)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			pstmt=con.prepareStatement(adding_client_query);

			pstmt.setString(1, client_leadid);

			pstmt.setString(2, client.getMobileNumber());

			pstmt.setString(3, client.getFirstName());

			pstmt.setString(4, client.getLastName());

			String gender = client.getGender();

			if(gender.equalsIgnoreCase("Male")){

				gender="0";

			}else if(gender.equalsIgnoreCase("Female")){

				gender="1";
			}else if(gender.equalsIgnoreCase("Others")){

				gender="2";
			}

			pstmt.setString(5, gender);

			pstmt.setString(6, client.getAge());

			pstmt.setString(7, client.getDoorNo());

			pstmt.setString(8, client.getStreetName());

			pstmt.setString(9, client.getAreaName());

			pstmt.setString(10, client.getTaluk());

			pstmt.setString(11, city_value);

			pstmt.setString(12, client.getPincode());

			pstmt.setString(13, state_value);

			pstmt.setString(14, lead_creat_by);

			pstmt.setString(15, currentDate);

			pstmt.setString(16, currentDate);

			int client_data=pstmt.executeUpdate();

			System.out.println("Date_insertd:"+pstmt);

			if(client_data==1){


				statusModel.setStatus("SUCCESS");

			}else{

				statusModel.setStatus("FAILURE");

			}



		}catch(Exception e){

			e.printStackTrace();

		}finally{

			try{

				pstmt.close();

				con.close();

			}catch(Exception e){

				e.printStackTrace();
			}


		}


		return statusModel;
	}
	@Override
	public Client login(Client client) {
		// TODO Auto-generated method stub
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		Client login_model = new Client();

		boolean flg= false;
		try {

			Db db = new Db();

			con = db.mysqlConnect();

			stmt = con.createStatement();

			String clientname=client.getClientname();
			String password=client.getPassword();

			String login_query = "SELECT clientname,password FROM client_login_details WHERE clientname='"+ clientname + "'AND password='"+ password + "'";

			System.out.println("beatlogin_query:"+login_query);

			rs = stmt.executeQuery(login_query);

			boolean last = rs.last();

			int row = rs.getRow();

			rs.beforeFirst();

			if (row == 0) {
				login_model.setStatus("No user found");

			} else {

				while (rs.next()) {

					String password1= rs.getString("password");

					if(password1!=null && password1.length()> 0){

						login_model.setStatus("SUCCESS");
						
						login_model.setClientname(rs.getString("clientname"));
						login_model.setClientid(rs.getString("password"));
						
						login_model.setPassword("0");
					}
					else{
						login_model.setStatus("SUCCESS");
						/*login_model.setMessage("First time login");*/
						login_model.setPassword("1");
					}
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			try {

				if (rs != null) {

					rs.close();
				} else if (stmt != null) {

					stmt.close();

				} else if (con != null) {

					con.close();
				}

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		return login_model;

	}


	
	@Override
	public Client leadcount(Client client) {
		List<Client> addressList = new ArrayList<>();

		Connection con = null;
		Statement stmt = null;
		
		ResultSet rs = null;
		
	

		try {

			Db db = new Db();

			con = db.mysqlConnect();

			stmt = con.createStatement();
			
		String postal_area_address_query="SELECT lead_status FROM lead_details WHERE lead_status ='1'";
			
			 System.out.println("postal_address:" + postal_area_address_query);

			 rs = stmt.executeQuery(postal_area_address_query);

			 boolean last = rs.last();

			 int row = rs.getRow();
			
			 rs.beforeFirst();

			

			

				while (rs.next()) {

					Client address_model = new Client();
					
					address_model.setLead_status(rs.getString("lead_status"));
					addressList.add(address_model);

				}
				client.setTotalcount(String.valueOf(row));
				

			

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			try {
				
				

				if (rs != null) {

					rs.close();
				} else if (stmt != null) {

					stmt.close();

				} else if (con != null) {

					con.close();
				}

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		return client;
}
	
	@Override
	public List<Client> list() {
		// TODO Auto-generated method stub
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		List<Client> list = new ArrayList<Client>();
		
		try {

			String sql = "SELECT lead_id,clientid,first_name,area_name,city,pincode,state,lead_created_date,lead_status FROM lead_details WHERE lead_status='1'";
			connection = Db.mysqlConnect();
			preparedStatement = connection.prepareStatement(sql);
			System.out.println("query-------------------->"+sql);
			resultSet = preparedStatement.executeQuery();
			System.out.println("row---------->"+resultSet);
			int row = resultSet.getRow();
			System.out.println("row---------->"+row);
			while (resultSet.next()){
				Client outob = new Client();

				outob.setLead_id(resultSet.getString("lead_id"));
				outob.setClientid(resultSet.getString("clientid"));
				outob.setFirstName(resultSet.getString("first_name"));
				outob.setAreaName(resultSet.getString("area_name"));
				outob.setCity(resultSet.getString("city"));
				outob.setPincode(resultSet.getString("pincode"));
				outob.setState(resultSet.getString("state"));
				outob.setLead_created_date(resultSet.getDate("lead_created_date"));
				outob.setLead_status(resultSet.getString("lead_status"));
				outob.setTotalcount(String.valueOf(row));
				list.add(outob);
			}
                
		} catch (Exception e) {

			e.printStackTrace();

		} 
		finally{
			try {
				preparedStatement.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return list;
	}
	@Override
	public List<Client> atelist() {
		// TODO Auto-generated method stub
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		List<Client> atelist = new ArrayList<Client>();
		try {

			String sql = "SELECT lead_id,clientid,first_name,area_name,city,pincode,state,lead_created_date,lead_status FROM lead_details WHERE lead_status='2'";
			connection = Db.mysqlConnect();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()){
				Client outob = new Client();

				outob.setLead_id(resultSet.getString("lead_id"));
				outob.setClientid(resultSet.getString("clientid"));
				outob.setFirstName(resultSet.getString("first_name"));
				outob.setAreaName(resultSet.getString("area_name"));
				outob.setCity(resultSet.getString("city"));
				outob.setPincode(resultSet.getString("pincode"));
				outob.setState(resultSet.getString("state"));
				outob.setLead_created_date(resultSet.getDate("lead_created_date"));
				outob.setLead_status(resultSet.getString("lead_status"));
				atelist.add(outob);

			}




		} catch (Exception e) {

			e.printStackTrace();

		} 
		finally{
			try {
				preparedStatement.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return atelist;

	}
	@Override
	public List<Client> complete() {
		// TODO Auto-generated method stub
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		List<Client> complete = new ArrayList<Client>();
		try {

			String sql = "SELECT lead_id,clientid,first_name,area_name,city,pincode,state,lead_created_date,lead_status,lead_verified_date,lead_approved_date,lead_assigned_to,remarks,approved_status,billing_price FROM lead_details WHERE lead_status='3' OR lead_status='4'";
			connection = Db.mysqlConnect();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()){
				Client outob = new Client();
				outob.setLead_id(resultSet.getString("lead_id"));
				outob.setClientid(resultSet.getString("clientid"));
				outob.setFirstName(resultSet.getString("first_name"));
				outob.setAreaName(resultSet.getString("area_name"));
				outob.setCity(resultSet.getString("city"));
				outob.setPincode(resultSet.getString("pincode"));
				outob.setState(resultSet.getString("state"));
				outob.setLead_created_date(resultSet.getDate("lead_created_date"));
				outob.setLead_status(resultSet.getString("lead_status"));
				outob.setLead_verified_date(resultSet.getDate("lead_verified_date"));
				outob.setLead_approved_date(resultSet.getDate("lead_approved_date"));
				outob.setLead_assigned_to(resultSet.getString("lead_assigned_to"));
				outob.setRemarks(resultSet.getInt("remarks"));
				outob.setApproved_status(resultSet.getInt("approved_status"));
				outob.setBilling_price(resultSet.getString("billing_price"));
				complete.add(outob);
			}	

		} catch (Exception e) {

			e.printStackTrace();

		} 
		finally{
			try {
				preparedStatement.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return complete;

	}
	@Override
	public Client update(Client client) {
		// TODO Auto-generated method stub
		Client outputObj = new Client();
		Connection connection = null;
		PreparedStatement preparedStatement = null;



		try {

			connection = Db.mysqlConnect();
			String del = "UPDATE postal_billing_update SET online_price=?,offline_price=?";
			preparedStatement = connection
					.prepareStatement(del);
			preparedStatement.setString(1, client.getOnline_price());
			preparedStatement.setString(2, client.getOffline_price());
			int deleteCount =  preparedStatement.executeUpdate();
			if(deleteCount >0)
			{
				outputObj.setStatus("success");
			}else
			{
				outputObj.setStatus("failure");
			}

		}

		catch (Exception e) {

			e.printStackTrace();

			outputObj.setStatusa(false);
		} 
		finally
		{
			try {
				preparedStatement.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return outputObj;

	}
	@Override
	public Client leadprocess(Client client) {
		// TODO Auto-generated method stub
		List<Client> addressList = new ArrayList<>();

		Connection con = null;
		Statement stmt = null;
		
		ResultSet rs = null;
		
	

		try {

			Db db = new Db();

			con = db.mysqlConnect();

			stmt = con.createStatement();
			
		String postal_area_address_query="SELECT lead_status FROM lead_details WHERE lead_status ='2'";
			
			 System.out.println("postal_address:" + postal_area_address_query);

			 rs = stmt.executeQuery(postal_area_address_query);

			 boolean last = rs.last();

			 int row = rs.getRow();
			
			 rs.beforeFirst();

			

			

				while (rs.next()) {

					Client address_model = new Client();
					
					address_model.setLead_status(rs.getString("lead_status"));
					addressList.add(address_model);

				}
				client.setCount(String.valueOf(row));
				

			

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			try {
				
				

				if (rs != null) {

					rs.close();
				} else if (stmt != null) {

					stmt.close();

				} else if (con != null) {

					con.close();
				}

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		return client;
	}
	@Override
	public Client leadverify(Client client) {
		// TODO Auto-generated method stub
		List<Client> addressList = new ArrayList<>();

		Connection con = null;
		Statement stmt = null;
		
		ResultSet rs = null;
		
	

		try {

			Db db = new Db();

			con = db.mysqlConnect();

			stmt = con.createStatement();
			
		String postal_area_address_query="SELECT lead_status FROM lead_details WHERE lead_status ='3'";
			
			 System.out.println("postal_address:" + postal_area_address_query);

			 rs = stmt.executeQuery(postal_area_address_query);

			 boolean last = rs.last();

			 int row = rs.getRow();
			
			 rs.beforeFirst();

			

			

				while (rs.next()) {

					Client address_model = new Client();
					
					address_model.setLead_status(rs.getString("lead_status"));
					addressList.add(address_model);

				}
				client.setTotal(String.valueOf(row));;
				

			

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			try {
				
				

				if (rs != null) {

					rs.close();
				} else if (stmt != null) {

					stmt.close();

				} else if (con != null) {

					con.close();
				}

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		return client;
	}
	@Override
	public Client leadnotverify(Client client) {
		// TODO Auto-generated method stub
		List<Client> addressList = new ArrayList<>();

		Connection con = null;
		Statement stmt = null;
		
		ResultSet rs = null;
		
	

		try {

			Db db = new Db();

			con = db.mysqlConnect();

			stmt = con.createStatement();
			
		String postal_area_address_query="SELECT lead_status FROM lead_details WHERE lead_status ='4'";
			
			 System.out.println("postal_address:" + postal_area_address_query);

			 rs = stmt.executeQuery(postal_area_address_query);

			 boolean last = rs.last();

			 int row = rs.getRow();
			
			 rs.beforeFirst();

			

			

				while (rs.next()) {

					Client address_model = new Client();
					
					address_model.setLead_status(rs.getString("lead_status"));
					addressList.add(address_model);

				}
				client.setCount2(String.valueOf(row));;
				

			

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			try {
				
				

				if (rs != null) {

					rs.close();
				} else if (stmt != null) {

					stmt.close();

				} else if (con != null) {

					con.close();
				}

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		return client;
	}
	@Override
	public Client bill(Client client) {
		// TODO Auto-generated method stub
		

		Connection con = null;
		Statement stmt = null;
		
		ResultSet rs = null;
		
	

		try {

			Db db = new Db();

			con = db.mysqlConnect();

			stmt = con.createStatement();
			
		String postal_area_address_query="SELECT online_price,offline_price FROM postal_billing_update";
			
			 System.out.println("postal_address:" + postal_area_address_query);

			 rs = stmt.executeQuery(postal_area_address_query);

			 boolean last = rs.last();

			 int row = rs.getRow();
			
			 rs.beforeFirst();

			

			

				while (rs.next()) {

					
					
					
					client.setOnline_price(rs.getString("online_price"));
					client.setOffline_price(rs.getString("offline_price"));
					

				}
				

			

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			try {
				
				

				if (rs != null) {

					rs.close();
				} else if (stmt != null) {

					stmt.close();

				} else if (con != null) {

					con.close();
				}

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		return client;
	}



}
