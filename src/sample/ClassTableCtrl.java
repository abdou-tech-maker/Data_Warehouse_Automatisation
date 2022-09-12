package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import module.Attribute;
import module.ClassTable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClassTableCtrl implements Initializable {
    @FXML
    private Label className;

    @FXML
    VBox tableContainer;

    public void drawTable(ClassTable tab) throws IOException {
        className.setText(tab.getClassName());
        ArrayList<Attribute> attrs = tab.getRelatedAttributes();
        System.out.println(tab.getClassName() + ": " +attrs.toString());
        if (attrs!= null && attrs.size() > 0) {
            for(Attribute a:attrs){
                HBox attributeContainer = new HBox();
                attributeContainer.setPadding(new Insets(2, 10, 2, 10));
                attributeContainer.getChildren().add(new Label(a.getName()));
                tableContainer.getChildren().add(attributeContainer);
            }
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
