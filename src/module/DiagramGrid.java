package module;

import dao.AssociationDao;
import dao.AttributeDao;
import dao.ClassTableDao;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.util.Pair;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class DiagramGrid {

    public int size;
    public int  [][] grid;
    public HashMap<Integer, ArrayList<ClassTable>> tabsByLevel;
    public int cube;

    public DiagramGrid(int cube) throws SQLException {

        ClassTableDao tabDao = new ClassTableDao();
        this.cube = cube;
        this.tabsByLevel = tabDao.getTablesByLevel(cube);
        this.size = (tabsByLevel.size()*2)-1;
        this.grid  = new int[size][size];
        this.fillGrid();

    }

    // Fill the grid with the table Ids level by level
    // Each level will be allowed to draw its tables in a specific range
    // The fait table will be drawn in the centre of the grid
    // The next level will be drawn one cell further from the fait table
    // And so on, each level drawn one cell further from the previous level
    // And each table except the fait table will be linked to one parent table(low level table)

    public void drawTable(ClassTable tab) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        BorderPane pane = loader.load();

    }

    public void fillGrid() throws SQLException {
        ClassTableDao tabDao = new ClassTableDao();
        if(tabsByLevel.size() == 0) return;
        //this.tabsByLevel = tabDao.getTablesByLevel();
        //System.out.println(tabsByLevel.get(1));
        ClassTable fait = tabDao.getFaitTables(tabsByLevel.get(1));
        boolean tempExist = false;
        // If temp table doesn't exist in level 2, add it
        for(ClassTable tab:tabsByLevel.get(2)){
            if(tab.getClassName().equals("Temp")) tempExist = true;
        }

        if(!tempExist){
            int tempId = tabDao.createClassTable("Temp", "dimention", this.cube);
            ClassTable temp = new ClassTable(tempId, "Temp", "dimention", cube);
            tabsByLevel.get(2).add(temp);

            // Create association between that table and the fait table
            AssociationDao aDoa = new AssociationDao();
            aDoa.createAssociation(fait.getId(), temp.getId());

            // Add some attributes to it
            AttributeDao atDao = new AttributeDao();
            atDao.createAttribute("year", "property", tempId);
            atDao.createAttribute("month", "property", tempId);
            atDao.createAttribute("day", "property", tempId);
        }

        int mid = size/2;
        grid[mid][mid] = fait.getId();
        fait.row = mid;fait.col=mid;

        for(int level=2; level<= this.tabsByLevel.size(); level++ ){
            //System.out.println(level);
            // Get the current level tables
            ArrayList<ClassTable> currentLevelTabs = this.tabsByLevel.get(level);
            // If the current level is the first dimension after fait table
            if(level==2){
                // Get the potential cells for the first dimension
                ArrayList<Pair<Integer, Integer>> potentialCells = this.getRandomValidCellsOneStepFurtherFromParent(mid, mid, 1);
                // For each table in the first dimension(2end level)
                for(ClassTable currentTab:currentLevelTabs){
                    Pair<Integer, Integer> randCell = pickRandomCell(currentTab.getId(), potentialCells);
                    currentTab.row = randCell.getKey();
                    currentTab.col = randCell.getValue();
                    // Remove the already picked ones
                    potentialCells.remove(randCell);
                }
            }else{
                // For each table in the 3d level and up
                for(ClassTable currentTab:currentLevelTabs){
                   // Get the parent related table
                    ClassTable parentTab = currentTab.getParentTable(this.tabsByLevel.get(level-1));
                    int parentRow = parentTab.row;
                    int parentCol = parentTab.col;

                    // pick a cell based on its parent cell
                    ArrayList<Pair<Integer, Integer>> potentialCells = this.getRandomValidCellsOneStepFurtherFromParent(parentRow, parentCol, level-1);
                    Pair<Integer, Integer> randCell = pickRandomCell(currentTab.getId(), potentialCells);
                    currentTab.row = randCell.getKey();
                    currentTab.col = randCell.getValue();
                }

            }
        }

        //System.out.println(tabsByLevel.get(2).get(0).row);
    }

    // Pick a random cell for a table
    public Pair<Integer, Integer> pickRandomCell(int tabId, ArrayList<Pair<Integer, Integer>> potentialCells){
        Random randomGenerator = new Random();
        Pair<Integer, Integer> randCell;
        int row = 0, col;
        // Get random valid cell
        boolean validCell = false;
        do {
            randCell = potentialCells.get(randomGenerator.nextInt(potentialCells.size()));
            row = randCell.getKey();
            col = randCell.getValue();
            if(this.grid[row][col] == 0) validCell = true;
            potentialCells.remove(randCell);

        }while (!validCell);
        this.grid[row][col] = tabId;

        return randCell;
    }

    public boolean checkValidRange(int level, int row, int col){
        int leftBorder, rightBorder, topBorder, bottomBorder;
        // Get the most left column, top row that table can take
        leftBorder = topBorder = (this.size /2) - (level-1);

        // Get the most right column, bottom row that table can take
        rightBorder = bottomBorder = (this.size /2) + (level-1);

        if( topBorder <= row  && row <= bottomBorder ){
            if(row == topBorder || row == bottomBorder){
                if(leftBorder <=col && col <= rightBorder){
                    return true;
                }
            }else{
                if(col == leftBorder || col==rightBorder){
                    return  true;
                }
            }
        }
        return false;
    }



    // Get the potentials cells that table could be drawn at based
    // Based on it's parent table(low level table associated with) cell
    public ArrayList<Pair<Integer, Integer>> getRandomValidCellsOneStepFurtherFromParent(int parentRow, int parentCol, int parentLevel){
        int parentLeftBorder, parentTopBorder, parentRightBorder, parentBottomBorder;
        parentLeftBorder = parentTopBorder = (this.size /2) - (parentLevel-1);
        ArrayList<Pair<Integer, Integer>> potentialCells = new ArrayList<>();

        // Get the most right column, bottom row that table can take
        parentRightBorder = parentBottomBorder = (this.size /2) + (parentLevel-1);

        if(parentRow == parentTopBorder){

            // row would be parentRow -1
            // col would be from colParent-1 to colParent+1
            for(int j=parentCol-1; j<=parentCol+1; j++){
                potentialCells.add(new Pair(parentRow-1, j));
            }

            if(parentCol == parentLeftBorder){
                // addition rows would be from parentRow to parentRow+1
                // with parentCol-1
                for(int j=parentRow; j<=parentRow+1; j++){
                    potentialCells.add(new Pair(j, parentCol-1));
                }
            }
            if(parentCol == parentRightBorder){
                // addition rows would be from parentRow to parentRow+1
                // with parentCol+1
                for(int j=parentRow; j<=parentRow+1; j++){
                    potentialCells.add(new Pair(j, parentCol+1));
                }
            }
        }else{
            // row would be parentRow +1
            // col would be from colParent-1 to colParent+1
            for(int j=parentCol-1; j<=parentCol+1; j++){
                potentialCells.add(new Pair(parentRow+1, j));
            }

            if(parentCol == parentLeftBorder){
                // addition rows would be from parentRow to parentRow-1
                // with parentCol-1
                for(int j=parentRow; j>=parentRow-1; j--){
                    potentialCells.add(new Pair(j, parentCol-1));
                }
            }
            if(parentCol == parentRightBorder){
                // addition rows would be from parentRow to parentRow+1
                // with parentCol+1
                for(int j=parentRow; j>=parentRow-1; j--){
                    potentialCells.add(new Pair(j, parentCol+1));
                }
            }

        }

        return potentialCells;

    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    // Check valid cell for a


}
