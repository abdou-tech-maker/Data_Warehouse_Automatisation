package module;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import sample.ClassTableCtrl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Diagram {
    /*
    public void getDiagram() throws SQLException, IOException {
        Group root = new Group();

        GridPane gp = new GridPane();
        gp.setPadding( new Insets(10) );
        gp.setHgap( 50 );
        gp.setVgap( 50 );

        Pane stack = new Pane();
        stack.getChildren().add(gp);
        root.getChildren().add(stack);

        DiagramGrid dg = new DiagramGrid(1
        );
        HashMap<Integer, ArrayList<ClassTable>> tabsByLevel = dg.tabsByLevel;

        // Store the vbox that contain the table diagram to get it later
        // to draw a line between each two associated tabs
        VBox[][] vboxGrid = new VBox[dg.size][dg.size];

        VBox faitTable ;
        FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("classTable.fxml"));
        faitTable = fxmlLoader2.load();
        ((ClassTableCtrl) fxmlLoader2.getController()).setClassName("Fait");
        int mid = dg.size/2;
        gp.add(faitTable, mid, mid);
        vboxGrid[mid][mid] = faitTable;

        // Loop through each level
        for(int level=2; level<= tabsByLevel.size(); level++){
            ArrayList<ClassTable> currentLevel = tabsByLevel.get(level);
            for(ClassTable tab:currentLevel){

                // Draw the table
                VBox dimTable ;
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("classTable.fxml"));
                dimTable = fxmlLoader.load();
                ((ClassTableCtrl) fxmlLoader.getController()).setClassName(tab.getClassName());
                int row = tab.row;
                int col = tab.col;
                gp.add(dimTable, tab.col, tab.row);

                //get the parent tab
                ClassTable parentTab = tab.getParentTable(tabsByLevel.get(level-1));
                // Get the parent vbox in the diagram
                VBox parentVBox = vboxGrid[parentTab.row][parentTab.col];

                // Draw a line between between the parent and the child table
                Line line = new Line();
                line.setStrokeWidth(2);
                line.setStroke(Color.BROWN);

                root.getChildren().add(line);
                ObjectBinding<Bounds> dimTableInStack = Bindings.createObjectBinding(() -> {
                    Bounds dimTableInScene = dimTable.localToScene(dimTable.getBoundsInLocal());
                    return stack.sceneToLocal(dimTableInScene);
                }, dimTable.boundsInLocalProperty(), dimTable.localToSceneTransformProperty(), stack.localToSceneTransformProperty());

                ObjectBinding<Bounds> parentTableInStack = Bindings.createObjectBinding(() -> {
                    Bounds faitTableInScene = parentVBox.localToScene(parentVBox.getBoundsInLocal());
                    return stack.sceneToLocal(faitTableInScene);
                }, faitTable.boundsInLocalProperty(), faitTable.localToSceneTransformProperty(), stack.localToSceneTransformProperty());


                DoubleBinding startX, endX, startY, endY;
                if(col == parentTab.col){
                    // Set the X position of the line in the middle of the table
                    // System.out.println("firstCondition: randJ="+randJ +"\tfaitCol="+faitCol);
                    startX  =  Bindings.createDoubleBinding(() -> {
                        Bounds b = dimTableInStack.get();
                        return b.getMinX() + b.getWidth() / 2 ;
                    }, dimTableInStack);

                    endX = Bindings.createDoubleBinding(() -> {
                        Bounds b = parentTableInStack.get();
                        return b.getMinX() + b.getWidth() / 2 ;
                    }, parentTableInStack);

                    if(row < parentTab.row){

                        startY = Bindings.createDoubleBinding(() -> dimTableInStack.get().getMaxY(), dimTableInStack);
                        endY = Bindings.createDoubleBinding(() -> parentTableInStack.get().getMinY(), parentTableInStack);

                    }else{

                        startY = Bindings.createDoubleBinding(() -> dimTableInStack.get().getMinY(), dimTableInStack);
                        endY = Bindings.createDoubleBinding(() -> parentTableInStack.get().getMaxY(), parentTableInStack);
                    }
                }else{
                    //System.out.println("second Condition: randJ="+randJ +"\tfaitCol="+faitCol);
                    startY = Bindings.createDoubleBinding(() -> {
                        Bounds b = dimTableInStack.get();
                        return b.getMinY() + b.getHeight() / 2 ;
                    }, dimTableInStack);

                    endY = Bindings.createDoubleBinding(() -> {
                        Bounds b = parentTableInStack.get();
                        return b.getMinY() + b.getHeight() / 2 ;
                    }, parentTableInStack);

                    if(col < parentTab.col){

                        startX = Bindings.createDoubleBinding(() -> dimTableInStack.get().getMaxX(), dimTableInStack);
                        endX = Bindings.createDoubleBinding(() -> parentTableInStack.get().getMinX(), parentTableInStack);

                    }else{

                        startX = Bindings.createDoubleBinding(() -> dimTableInStack.get().getMinX(), dimTableInStack);
                        endX = Bindings.createDoubleBinding(() -> parentTableInStack.get().getMaxX(), parentTableInStack);
                    }

                }


                line.startXProperty().bind(startX);
                line.startYProperty().bind(startY);
                line.endXProperty().bind(endX);
                line.endYProperty().bind(endY);

                vboxGrid[row][col] = dimTable;



            }
        }
        BorderPane pane = new BorderPane();
        ScrollPane scrollPane = new ScrollPane();
        pane.setCenter(scrollPane);

        scrollPane.setContent(root);
    }

     */
}
