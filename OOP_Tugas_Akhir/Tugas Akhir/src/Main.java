import java.sql.*;
import java.util.Scanner;

public class Main {

	public static Integer menu(Scanner sc) {
		System.out.println("Restaurant reservation");
		System.out.println("1. Show menu");
		System.out.println("2. Make reservation");
		System.out.println("3. Exit");
		System.out.println("Input your choice");
		Integer choice = sc.nextInt();
		sc.nextLine();
		return choice;
	}
	
	public static Connection init() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			//create connection
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/restomanagement","root","");	
			if(conn != null) {
				System.out.println("connection established");
				return conn;
			}else {
				System.out.println("cannot connect");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn;
		try {
			conn = init();
//			//execute query
//			//select
			Integer choice = 0;
			//scanner input
			Scanner sc = new Scanner(System.in);
			while(choice != 3) {
				choice = menu(sc);
				switch (choice) {
					case 1:
						System.out.println("Menus: ");
						viewData(Menu,"select * from Menu");
						break;
						
					case 2:
						String EmployeeID, Name, Place;
						int Desks, Type, People;
						System.out.println("Input Employee ID: ");
						EmployeeID = sc.nextLine();
						System.out.println("Input Customer Name: ");
						Name = sc.nextLine();
						System.out.println("Input Total Number of Desks: ");
						Desks = sc.nextInt();
						System.out.println("Input Type of Desk: ");
						Type = sc.nextLine();
						System.out.println("Input Total Number of People: ");
						People = sc.nextInt();
						insertData(Reservation, Name, Desks, Type, People);
						break;
						
					default:
						break;
				}
				if(choice == 3) {
					break;
				}
			}
			
//			//insert
//			insertData(conn,"cerpen","malik","Gunung Agung");
//			//update data
//			updateData(conn,"lari sore","mamak",3);
//			viewData(conn,"select * from books");
//			deleteData(conn,5);
//			viewData(conn,"select * from books");
			
			conn.close();
			
		}catch(Exception e) {
			
		}
	}
	
	public static void viewData(Connection Menu, String query) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				System.out.println("Menu ID: "+rs.getString("MenuId")+" , Name: "+rs.getString("MenuName")+ " , Narration: "+rs.getString("Narration"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void insertData(Connection Reserve, String Name, String Desks, String Type, String People) {
		try {
			PreparedStatement pst = conn.prepareStatement("insert into restomanagement (Name, Desks, Type, People) values (?,?,?,?)");
			pst.setString(1, Name);
			pst.setString(2, Desks);
			pst.setString(3, Type);
			pst.setString(4, People);
			int check = pst.executeUpdate();
			if(check != 0) {
				System.out.println("Success insert data");
			}else {
				System.out.println("failed insert data");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateData(Connection conn, String title, String author, int id) {
		try {
			PreparedStatement pst = conn.prepareStatement("update books set title = ? ,author = ? where id = ? ");
			pst.setString(1, title);
			pst.setString(2, author);
			pst.setInt(3, id);
			int check = pst.executeUpdate();
			if(check != 0) {
				System.out.println("Succesfully update data");
			}else {
				System.out.println("failed to update");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteData(Connection conn, int id) {
		try {
			PreparedStatement pst = conn.prepareStatement("delete from books where id = ?");
			pst.setInt(1, id);
			int check = pst.executeUpdate();
			if(check != 0) {
				System.out.println("Success delete data");
			}else {
				System.out.println("failed delete data");
			}			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
