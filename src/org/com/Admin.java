package org.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class Admin {
    public static void main(String[] args) throws IOException, SQLException {
        Connection con = DBConnection.getConnection();
        Statement stmt=con.createStatement();

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("-                         Hello ADMIN                        -");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        int choice;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            System.out.println("Press 1 to enter new medicine");
            System.out.println("Press 2 to update medicine");
            System.out.println("Press 0 to exit");
            System.out.print("\nEnter your choice: ");
            choice = Integer.parseInt(reader.readLine());
            if(choice == 1){
                int id, quantity;
                String name;
                double price;
                System.out.print("Enter id of medicine: ");
                id = Integer.parseInt(reader.readLine());
                String sql = "select * from medics where id="+id;
                ResultSet r = stmt.executeQuery(sql);
                if(!r.next()){
                    System.out.print("Enter name of medicine: ");
                    name = reader.readLine();
                    System.out.print("Enter quantity of medicine: ");
                    quantity = Integer.parseInt(reader.readLine());
                    System.out.print("Enter price of medicine: ");
                    price = Double.parseDouble(reader.readLine());
                    String insertSql = "insert into medics(id,name,quantity,price) values("+id+",'"+name+"',"+quantity+","+price+")";
                    PreparedStatement preparedStmt = con.prepareStatement(insertSql);
                    preparedStmt.executeUpdate();
                }
                else {
                    System.out.println("Id is already present");
                    System.out.println("Please enter details again");
                }
            }
            else if(choice==0){
                break;
            }
            else if(choice==2){
                ResultSet rs=stmt.executeQuery("select * from medics");
                System.out.println("Available medicines in machine:");
                while(rs.next())
                    System.out.println("ID: "+rs.getInt(1)+"  TabletName: "+rs.getString(2)+"  Quantity:"+rs.getInt(3)+"  Price:"+rs.getDouble(4));
                System.out.println("\n");
                int id;
                System.out.print("Enter id you want to change: ");
                id = Integer.parseInt(reader.readLine());
                String sql = "select * from medics where id="+id;
                ResultSet r = stmt.executeQuery(sql);
                if(r.next()){
                    int quantity, ch;
                    String name;
                    double price;
                    System.out.print("Do you want to change name of tablet? Press 1 - yes or 0 - No: ");
                    ch = Integer.parseInt(reader.readLine());
                    if(ch == 1){
                        System.out.print("Enter new tablet name: ");
                        name = reader.readLine();
                        String queryName = "update medics set name = ? where id = ?";
                        PreparedStatement preparedStmt = con.prepareStatement(queryName);
                        preparedStmt.setString(1, name);
                        preparedStmt.setInt(2, id);
                        preparedStmt.executeUpdate();
                    }
                    System.out.print("Do you want to change quantity of tablet? Press 1 - yes or 0 - No: ");
                    ch = Integer.parseInt(reader.readLine());
                    if(ch == 1){
                        System.out.print("Enter updated quantity of tablet name: ");
                        quantity = Integer.parseInt(reader.readLine());
                        String queryQuantity = "update medics set quantity = ? where id = ?";
                        PreparedStatement preparedStmt = con.prepareStatement(queryQuantity);
                        preparedStmt.setInt(1, (r.getInt(3)+quantity));
                        preparedStmt.setInt(2, id);
                        preparedStmt.executeUpdate();
                    }
                    System.out.print("Do you want to change price of tablet? Press 1 - yes or 0 - No: ");
                    ch = Integer.parseInt(reader.readLine());
                    if(ch == 1){
                        System.out.print("Enter new price of tablet: ");
                        price = Double.parseDouble(reader.readLine());
                        String queryQuantity = "update medics set price = ? where id = ?";
                        PreparedStatement preparedStmt = con.prepareStatement(queryQuantity);
                        preparedStmt.setDouble(1, price);
                        preparedStmt.setInt(2, id);
                        preparedStmt.executeUpdate();
                    }
                }
                else {
                    System.out.println("Please enter correct id");
                }
            }
            else {
                System.out.println("Wrong input! Please try again");
            }
        }
        DBConnection.closeConnection();
    }
}
