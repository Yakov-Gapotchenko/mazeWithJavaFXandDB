package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.Scanner;

public class Controller {

    @FXML
    private Button btnFindWay;

    @FXML
    private TextArea mazeInputTextArea;

    @FXML
    private Label mazeResOutputLabel;

    @FXML
    private void onBtnFindWay(ActionEvent event) {


        Maze myMaze = new Maze();

        //Scanner in = new Scanner(System.in);
        String src = mazeInputTextArea.getText();
        String[] textAsLinesArray = src.split("\n");

        myMaze.setNrows(textAsLinesArray.length);
        myMaze.setNcols(textAsLinesArray[0].trim().split("[ ]+").length);

        int nrows = myMaze.getNrows(), ncols = myMaze.getNcols();

        System.out.println("nrows :   " + nrows);

        System.out.println("ncols :   " + ncols);



        String[][] maze = new String[nrows][ncols];


        //System.out.println("maze :   ");

        /*for(int i=0;i<nrows;i++)
            for(int j=0;j<ncols;j++)
                maze [i][j] = in.next("[S*0F]");*/

        for(int i=0;i<nrows;i++){
            maze[i] = textAsLinesArray[i].split("[ ]+");


        }



        int[][] solution = new int[nrows][ncols];

        for(int i=0;i<nrows;i++)
            for(int j=0;j<ncols;j++)
                solution[i][j]=Integer.MAX_VALUE;// distance to the cell

        Pos start = Maze.findElem(maze,"S");



        int startX=start.getX(),startY=start.getY();
        solution[startY][startX]=0;


        Maze.markNeighbors(solution,maze,startY,startX,0);

        Maze.solutionOutput(solution,maze);


        //work with JDBC


        String resAsMultilineString="";
        BD_SQLite bd_sqLite = null;
        try {
            bd_sqLite = Maze.getPathAsDB(solution,maze);
            resAsMultilineString = bd_sqLite.selectAndReturnAsMultilineString();

        } catch (Exception e) {
            e.printStackTrace();
        }





        mazeResOutputLabel.setText(resAsMultilineString);
    }
}

