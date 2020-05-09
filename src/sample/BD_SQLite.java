package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

import org.sqlite.SQLite;
import org.sqlite.JDBC;


public class BD_SQLite {

    int currId;
    Statement st;

    public int getCurrId() {
        return currId;
    }

    public Statement getSt() {
        return st;
    }

    public void setCurrId(int currId) {
        this.currId = currId;
    }

    public void setSt(Statement st) {
        this.st = st;
    }


    public BD_SQLite(){
        this.currId = 0;

    }

    void add(int x, int y) throws SQLException {
        
        String query = String.format("insert into 'RESULT' ('id', 'x', 'y') values (%d, %d, %d); ", currId,x,y);
        System.out.println("\nadd:   "+query);
        st.execute(query);//add


        this.currId++;
    }

/*
    <T> void update(int id, String columnName, T value) throws SQLException {

        String query = String.format("update 'RESULT' set %s = '%s' where id=%d;", columnName.trim(), value.toString(),id );
        System.out.println("\nupdate:   "+query);
        st.execute(query);
    }*/

    void delete(int id) throws SQLException {
        //st.execute("UPDATE COMPANY set "+columnName+" = '"+value.toString() +"' where ID="+id+";"); //update

        String query = String.format("delete from RESULT where id=%d;",id );
        System.out.println("\ndelete:   "+query);
        st.execute(query);


    }

    void selectWithOutput() throws SQLException {
        ResultSet rs = st.executeQuery("select * from RESULT");
        while (rs.next())  //output
        {

            System.out.print (rs.getString("id")+" ");
            System.out.print (rs.getString("x")+" ");
            System.out.println(rs.getString("y"));
        }
        rs.close();

    }

    String selectAndReturnAsMultilineString() throws SQLException {
        ResultSet rs = st.executeQuery("select * from RESULT");
        ArrayList<String> lines = new ArrayList<>(),reverseLines = new ArrayList<>();

        while (rs.next())  //output
        {

            int id = rs.getInt("id");
            int x = rs.getInt("x");
            int y = rs.getInt("y");
            lines.add(String.format("%d | %d | %d",id,x,y));
        }
        //lines.add("№ | x | y");


        //reverse order, because of the LIFO data order

        String resAsStr = "№ | x | y\n";
        int linesSize = lines.size();
        for(int i=0;i<linesSize;i++)
            resAsStr+=lines.get(linesSize-1-i);
            //reverseLines.set(i, lines.get(linesSize-1-i));


        rs.close();
        return resAsStr;

    }




/*
    public static void main(String[] args) throws Exception{

        BD_SQLite_test bd_sqLite_test = new BD_SQLite_test();


        bd_sqLite_test.currId = 0;

        Class.forName("org.sqlite.JDBC");
        Connection bd = DriverManager.getConnection("jdbc:sqlite:sqlite.db3");
        bd_sqLite_test.st = bd.createStatement();

        bd_sqLite_test.st.execute("create table if not exists 'RESULT' ('id' int, 'x' text, 'y' text);");//create DB



        bd_sqLite_test.add("John", "King");//add
        bd_sqLite_test.add("Dorothy", "Jones");//add
        bd_sqLite_test.add("Jacob", "Jones");//add
        bd_sqLite_test.add("Elisabeth", "Smith");//add


        bd_sqLite_test.update(1, "x","veryStrangeName");//update


        bd_sqLite_test.delete(2);//delete


        bd_sqLite_test.selectWithOutput();//output

        bd_sqLite_test.st.execute("drop table RESULT ");

        bd.close();
        bd_sqLite_test.st.close();


    }*/


}
