import java.sql.*;
import java.util.Scanner;

public class Main {

	public static Integer menu(Scanner sc) {
		System.out.println("Restaurant reservation");
		System.out.println("1. Show data");
		System.out.println("2. Insert Data");
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
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/booksmanagement","root","");	
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
						System.out.println("Data in books table: ");
						viewData(conn,"select * from books");
						break;
						
					case 2:
						String title, author, publisher;
						System.out.println("Input new title: ");
						title = sc.nextLine();
						System.out.println("Input book author: ");
						author = sc.nextLine();
						System.out.println("Input publisher: ");
						publisher = sc.nextLine();
						insertData(conn, title, author, publisher);
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
	
	public static void viewData(Connection conn, String query) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				System.out.println("title: "+rs.getString("title")+" , author: "+rs.getString("author")+ " , publisher: "+rs.getString("publisher"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void insertData(Connection conn, String title, String author, String publisher) {
		try {
			PreparedStatement pst = conn.prepareStatement("insert into books (title, author, publisher) values (?,?,?)");
			pst.setString(1, title);
			pst.setString(2, author);
			pst.setString(3, publisher);
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
