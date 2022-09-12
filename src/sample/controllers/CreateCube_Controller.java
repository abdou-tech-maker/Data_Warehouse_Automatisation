package sample.controllers;

import com.jfoenix.controls.JFXButton;
import dao.CubeDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import module.Association;
import module.Cube;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateCube_Controller implements Initializable {

    @FXML
    private JFXButton BtnCreate;
    @FXML
    private TextField cubeName;

    @FXML
    public   Integer  nbrTables = 0;

    @FXML
    public   Integer  nbrAssociations =0;
    @FXML
    private TableView<?> table;

    private CubeDao cDao = new CubeDao();





    ArrayList<Cube> globalList=new ArrayList();
    @FXML
    private void setBtnCube (ActionEvent event )throws IOException {

        if(!cubeName.getText().isEmpty()){
            int cubeId = cDao.createCube(cubeName.getText());
            Cube cube = new Cube(cubeId, cubeName.getText());
            globalList.add(cube);


            Utils.alTotv(table, globalList);
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Cube> cubes = cDao.getAllCubes();
            globalList.addAll(cubes);
        Utils.alTotv(table, globalList);


    }
}
