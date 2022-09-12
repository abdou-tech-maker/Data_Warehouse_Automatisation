package sample.controllers;

import com.jfoenix.controls.JFXButton;
import dao.AttributeDao;
import dao.ClassTableDao;
import dao.CubeDao;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import module.ClassTable;
import module.Cube;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Class_Controller implements Initializable {

    @FXML
    private JFXButton BtnAddClass;

    @FXML
    private TextField cls;

    @FXML
    private ChoiceBox data;

    @FXML
    private TableView<ClassTableItems> classListView;

    @FXML
    ChoiceBox<Cube> cubeChoiceBox;

    private  ClassTableDao tDao = new ClassTableDao();
    private CubeDao cDao = new CubeDao();




    ArrayList<ClassTable> classTableList;


     /*  @FXML
    void clr_attr (ActionEvent event) throws IOException {
        att.clear();
        type.setValue("");
        data.setValue("");
     }*/

    @FXML
    void Add_Class (ActionEvent event) throws IOException{
        String className = cls.getText();
        Cube cube =  cubeChoiceBox.getValue();
        if(className.isEmpty() || cube == null) return;
        ClassTableDao cDao = new ClassTableDao();
        int  newClassId;
        try{
             newClassId = cDao.createClassTable(className, "dimention", cube.getId());
             ClassTable newClass = new ClassTable(newClassId, className, "dimention", cube.getId());
             classTableList.add(newClass);
             Utils.alTotv(classListView, classTableList);
             int to = classListView.getColumns().size();
             int from = to-2;
             classListView.getColumns().remove(from, to);

        }catch(Exception e){
            e.printStackTrace();
        }

        cls.clear();
    }



    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            classTableList = tDao.getAllTables();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ArrayList<Cube>  cubes = cDao.getAllCubes();
             ObservableList<Cube> cubeList = FXCollections.observableArrayList(cubes);
             cubeChoiceBox.setItems(cubeList);
             Utils.alTotv(classListView, classTableList);
            classListView.getColumns().remove(classListView.getColumns().size()-1);
            int to = classListView.getColumns().size();
            int from = to-2;
            classListView.getColumns().remove(from, to);


         /*    table.getSelectionModel().getSelectedItems().addListener((ListChangeListener<? super ClassTableItems>) change -> {
                 ClassTableItems tb=(ClassTableItems) table.getSelectionModel().getSelectedItems();
                 System.out.println(tb.getClassName());
             });*/

             
    }

}

