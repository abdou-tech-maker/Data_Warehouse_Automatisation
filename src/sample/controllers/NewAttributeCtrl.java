package sample.controllers;

import dao.AttributeDao;
import dao.ClassTableDao;
import dao.CubeDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import module.Attribute;
import module.ClassTable;
import module.Cube;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewAttributeCtrl implements Initializable {
    @FXML
    ChoiceBox<ClassTable> classTableChoiceBox;

    @FXML
    TextField attrNameField;

    @FXML
    ChoiceBox attrTypeChoiceBox;

    @FXML
    private TableView<?> attrListView;
    @FXML
    private ChoiceBox<Cube> cubeChoiceBox;


    ArrayList<Attribute> attributes;
    AttributeDao aDao = new AttributeDao();
    ClassTableDao ctDao = new ClassTableDao();

    private CubeDao cubeDao = new CubeDao();

    ArrayList<ClassTable> classTables;

    @FXML
    private void onClickAddAttribute (ActionEvent event )throws IOException {
        ClassTable classTab = classTableChoiceBox.getValue();
        String attributeName = attrNameField.getText();
        String attributeType = (String) attrTypeChoiceBox.getValue();
        if(classTab != null && !attributeName.isEmpty()){
            if(attributeType.isEmpty()) attributeType="property";
            if(!checkAttributeExist(attributeName, classTab.getId())){
                int attributeId = aDao.createAttribute(attributeName, attributeType, classTab.getId());
                attributes.add(new Attribute(attributeId, attributeName, attributeType, classTab.getId()));

                Utils.alTotv(attrListView, attributes);
            }

        }

    }

    public boolean checkAttributeExist(String attrName, int classId){
        for(Attribute a:attributes){
            if(a.getName().equals(attrName) && (a.getTableId() == classId)) return true;
        }
        return false;
    }
    @FXML
    void onChangeCube() throws IOException {
        Cube c = cubeChoiceBox.getValue();

        setTablesBasedOnCube(c);

    }

    public void setTablesBasedOnCube(Cube c){
        try {
            ArrayList<ClassTable> tabs = ctDao.getAllTables(c.getId());
            ObservableList<ClassTable> list = FXCollections.observableArrayList(tabs);
            classTableChoiceBox.setItems(list);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        attributes  = aDao.getAllAttributes();
        Utils.alTotv(attrListView, attributes);
        ArrayList<String> attrTypes = new ArrayList<String>(){{
            add("property");
            add("measure");
            add("primary key");
            add("foreign key");
        }};


            ObservableList<String> attrTypeList = FXCollections.observableArrayList(attrTypes);
            attrTypeChoiceBox.setItems(attrTypeList);


        ArrayList<Cube> cubes = cubeDao.getAllCubes();
        ObservableList<Cube> cubeList = FXCollections.observableArrayList(cubes);

        cubeChoiceBox.setItems(cubeList);


    }
}
