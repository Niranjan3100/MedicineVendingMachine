package org.com;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class MedicineVendingMachine {
    public static void main(String[] args) throws SQLException, IOException {
        Connection con = DBConnection.getConnection();
        Statement stmt=con.createStatement();

        ResultSet rs=stmt.executeQuery("select * from medics");
        System.out.println("-------------------------------------------------------------------");
        System.out.println("+               Welcome to Medicine Vending machine               +");
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Available medicines in machine:");
        while(rs.next())
            System.out.println("ID: "+rs.getInt(1)+"  TabletName: "+rs.getString(2)+"  Quantity:"+rs.getInt(3)+"  Price:"+rs.getDouble(4));
        System.out.println("\n");
        System.out.println("Select your medicine by choosing corresponding id");
        System.out.println("If you want to exit press 0");
        System.out.println("\n");
        int id, quantity;
        String bill = "";
        double price = 0.0;
        bill += "++++++++++++++++++++++++++++++++++++++++++++++++++\n";
        bill += "-                 Generated Bill                 -\n";
        bill += "++++++++++++++++++++++++++++++++++++++++++++++++++\n";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try{
                System.out.print("Choose medicine id: ");
                id = Integer.parseInt(reader.readLine());
                if(id == 0){
                    if(price>0){
                        System.out.println("\n");
                        System.out.println("Generating Bill");
                        System.out.println(bill);
                        System.out.println("Total Price: "+price);
                        double amount;
                        while (true){
                            System.out.print("Please insert amount: ");
                            amount = Double.parseDouble(reader.readLine());
                            if(amount >= price){
                                System.out.println("Collect your pack of medicines");
                                if(amount-price != 0){
                                    System.out.println("Collect your change: "+(amount-price));
                                }
                                break;
                            }
                            else {
                                System.out.println("Please correct or higher amount");
                            }
                        }
                        break;
                    }
                    else {
                        break;
                    }
                }
                else {
                    String sql = "select * from medics where id="+id;
                    ResultSet r = stmt.executeQuery(sql);
                    r.next();
                    System.out.print("Choose quantity of " + r.getString(2) +": ");
                    quantity = Integer.parseInt(reader.readLine());
                    if(quantity > r.getInt(3)) {
                        System.out.println("You entered more quantity than available quantity");
                    }
                    else {
                        bill += "Tablet: "+r.getString(2) + "  Quantity: " + quantity +" Price: " + (quantity * r.getDouble(4))+"\n";
                        price += quantity * r.getDouble(4);
                        String query = "update medics set quantity = ? where id = ?";
                        PreparedStatement preparedStmt = con.prepareStatement(query);
                        preparedStmt.setInt(1, r.getInt(3)-quantity);
                        preparedStmt.setInt(2, r.getInt(1));
                        preparedStmt.executeUpdate();
                    }
                }
            }
            catch (Exception ex){
                System.out.println("Wrong medicine, please enter correct id.");
            }
        }
        DBConnection.closeConnection();
    }
}
