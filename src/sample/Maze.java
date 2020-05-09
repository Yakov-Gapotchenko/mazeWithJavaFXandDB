package sample;

import java.util.Objects;
import java.util.Scanner;
import java.sql.*;
import org.sqlite.SQLite;
import org.sqlite.JDBC;



class Pos{
    private int x,y;

    public Pos(int x0,int y0){
        x=x0;
        y=y0;
    }

    public void setX(int x0){
        x=x0;
    }

    public void setY(int y0){
        y=y0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pos pos = (Pos) o;
        return x == pos.x &&
                y == pos.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

public class Maze {

    static int nrows,ncols;


    public static void setNcols(int ncols) {
        Maze.ncols = ncols;
    }

    public static void setNrows(int nrows) {
        Maze.nrows = nrows;
    }

    public static int getNcols() {
        return ncols;
    }

    public static int getNrows() {
        return nrows;
    }


    public static Pos findElem(String[][]arr, String str){

        for(int i=0;i<nrows;i++)
            for(int j=0;j<ncols;j++) {
                if (arr[i][j].equals(str))
                    return new Pos(j, i);
            }

        return null;
    }

    public static Pos getPreviousPos(int[][] solution,String[][] maze,Pos pos){
        int i=pos.getY(),j=pos.getX();

        int k = solution[i][j];

        if(j-1>=0 && solution[i][j-1]==k-1)
            return new Pos(j-1,i);

        else if(i-1>=0 && solution[i-1][j]==k-1)
            return new Pos(j,i-1);

        else if(j+1<=ncols-1 && solution[i][j+1]==k-1)
            return new Pos(j+1,i);

        else if(i+1<=nrows-1 && solution[i+1][j]==k-1)
            return new Pos(j,i+1);

        else
            try {
                throw new Exception("Can't find previous cell...");
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }


        return null;
    }




    public static BD_SQLite getPathAsDB(int[][]solution,String[][] maze) throws Exception { // reverse - from the end to the beginning
        BD_SQLite result = new BD_SQLite();

        //prepare db
        BD_SQLite bd_sqLite = new BD_SQLite();


        bd_sqLite.currId = 0;

        Class.forName("org.sqlite.JDBC");
        Connection bd = DriverManager.getConnection("jdbc:sqlite:sqlite.db3");
        bd_sqLite.st = bd.createStatement();


        bd_sqLite.st.execute("create table if not exists 'RESULT' ('id' int, 'x' text, 'y' text);");//create DB






        Pos currPos = findElem(maze,"F");//the end pos

        Pos startPos = findElem(maze,"S");

        while(!currPos.equals(startPos)){
            bd_sqLite.add(currPos.getX(),currPos.getY());
            currPos = getPreviousPos(solution,maze,currPos);
        }

        bd_sqLite.add(currPos.getX(),currPos.getY());
        bd_sqLite.st.execute("drop table RESULT ");//truncate table

        return bd_sqLite;
    }



    public static void markNeighbors(int[][] solution,String[][] maze, int y0,int x0, int k){


        if(x0-1>=0&&( k+1<solution[y0][x0-1]) && !maze[y0][x0-1].equals("*"))
        {
            solution[y0][x0-1]=k+1;
            markNeighbors(solution,maze,y0,x0-1,k+1);
        }


        if(y0-1>=0&&( k+1<solution[y0-1][x0]) && !maze[y0-1][x0].equals("*"))
        {
            solution[y0-1][x0]=k+1;
            markNeighbors(solution,maze,y0-1,x0,k+1);
        }


        if((x0+1<=ncols-1)&&( k+1<solution[y0][x0+1]) && !maze[y0][x0+1].equals("*"))
        {
            solution[y0][x0+1]=k+1;
            markNeighbors(solution,maze,y0,x0+1,k+1);
        }

        if((y0+1<=nrows-1)&&( k+1<solution[y0+1][x0]) && !maze[y0+1][x0].equals("*"))
        {
            solution[y0+1][x0]=k+1;
            markNeighbors(solution,maze,y0+1,x0,k+1);
        }

    }

    public static void solutionOutput(int [][] solution, String[][] maze){
        for(int i=0;i<nrows;i++)
        {
            for(int j=0;j<ncols;j++) {
                if(maze[i][j].equals("*"))
                    System.out.print("*");
                else
                    System.out.print(solution[i][j]);


                System.out.print("   ");
            }


            System.out.println();


        }
    }

}
