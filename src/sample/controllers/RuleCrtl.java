package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import dao.ClassTableDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import dao.CubeDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import module.Association;
import module.ClassTable;
import module.Cube;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RuleCrtl  implements Initializable {
    @FXML
    private JFXTextArea area;
    @FXML
    private JFXButton btnAdd;

    @FXML
    private ChoiceBox <String> actions;
    @FXML
    private ChoiceBox<ClassTable> dimension;
    @FXML
    private TextField action;
    @FXML
    private TextField condition;
    @FXML
    private ChoiceBox events;
    @FXML
    private ChoiceBox<Cube> cubeChoiceBox;
    @FXML
    private TextField ruleName;
    @FXML
    private JFXButton BtnValidate;

    // private CubeDao cDao = new CubeDao();

    private CubeDao cubeDao = new CubeDao();
    private ClassTableDao cDao = new ClassTableDao();


    @FXML
    void onChangeCube() throws IOException {
        Cube c = cubeChoiceBox.getValue();

        setTablesBasedOnCube(c);

    }
    public void setTablesBasedOnCube(Cube c){
        try {
            ArrayList<ClassTable> tabs = cDao.getAllTables(c.getId());
            ObservableList<ClassTable> list = FXCollections.observableArrayList(tabs);
            dimension.setItems(list);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void setBtnAdd (ActionEvent event ) throws IOException {

        String Rule = "Rule :  " + ruleName.getText();
        String Type = "Type :  event  " + events.getValue();
        String Condition = "Condition : If  " + condition.getText();
        String txtaction = ""+action.getText();
        String Action = "Action : " + actions.getValue() + "   "+ txtaction+" from " + dimension.getValue() ;
        String Complete = (Rule + "\n" + Type + "\n" + Condition + "\n" + Action);

        area.setText(Complete);
    }

    @FXML
    private void setBtnValidate (ActionEvent event) throws IOException {
        area.clear();
        ruleName.clear();
        events.setValue("");
        condition.clear();
        action.clear();
        actions.setValue("");



    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("Insert","Update","Delete");
        actions.setItems(list);

        ObservableList<String> list1 = FXCollections.observableArrayList("OLTP : Frequency ","OLAP:Result","OLAP-ExtemporesAbsolute"
                ,"OLAP-temporalPeriodic","OLAP : Temporal-Relative");
        events.setItems(list1);
/*
        ArrayList<Cube> cubes = cDao.getAllCubes();
        ObservableList<Cube> cubeList = FXCollections.observableArrayList(cubes);
        cubeChoiceBox.setItems(cubeList);*/

        ArrayList<Cube> cubes = cubeDao.getAllCubes();
        ObservableList<Cube> cubeList = FXCollections.observableArrayList(cubes);

        cubeChoiceBox.setItems(cubeList);






    }
}
