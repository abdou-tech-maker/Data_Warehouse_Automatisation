package sample.controllers;
import com.jfoenix.controls.JFXButton;
import dao.AssociationDao;
import dao.ClassTableDao;
import dao.CubeDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import module.Association;
import module.ClassTable;
import module.Cube;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddAssociation_Controller implements Initializable {

    @FXML
    private JFXButton addAssoc;

    @FXML
    private ChoiceBox<Cube> cube;

    @FXML
    private ChoiceBox <ClassTable> cls1;

    @FXML
    private ChoiceBox<ClassTable> cls2;
    @FXML
    private TableView<AssocTableItems> tableview;


    private ArrayList<Association> associations = new ArrayList<> ();

    private AssociationDao aDao = new AssociationDao();
    private ClassTableDao cDao = new ClassTableDao();
    private CubeDao cubeDao = new CubeDao();


    ArrayList<AssocTableItems> globalList=new ArrayList();

    @FXML
    void onChangeCube() throws IOException {
        Cube c = cube.getValue();
        associations = aDao.getAssociationsByCube(c.getId());

        setTablesBasedOnCube(c);

    }



    @FXML
    void Add_Association (ActionEvent event) throws IOException {
        ClassTable c1 = cls1.getValue();
        ClassTable c2 = cls2.getValue();

        // check association exist

        if((c1 != null && c2 != null) &&
                !checkAssociationExist(c1.getId(), c2.getId())
                && !checkAssociationExist(c2.getId(), c1.getId())
                && (c1 != c2)
        ){
            int assocId = aDao.createAssociation(c1.getId(), c2.getId());
            AssocTableItems assocTableItems = new AssocTableItems(assocId, c1.getClassName(),c2.getClassName());
            globalList.add(assocTableItems);

            Utils.alTotv(tableview,globalList);

            cls1.setValue(null);
            cls2.setValue(null);
        }

    }

    public void setTablesBasedOnCube(Cube c){
        associations = aDao.getAssociationsByCube(c.getId());
        System.out.println(associations);
        globalList.clear();
        for(Association a:associations){
            globalList.add(new AssocTableItems(a.getId(), a.getClass1Name(), a.getClass2Name()));
        }
        Utils.alTotv(tableview, globalList);
        try {
            ArrayList<ClassTable> tabs = cDao.getAllTables(c.getId());
            ObservableList<ClassTable> list = FXCollections.observableArrayList(tabs);
            cls1.setItems(list);
            cls2.setItems(list);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean checkAssociationExist(int class1, int class2){
        for(Association a:associations){
            if(a.getClass1() == class1 && a.getClass2() == class2) return true;
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Cube> cubes = cubeDao.getAllCubes();
        ObservableList<Cube> cubeList = FXCollections.observableArrayList(cubes);

        cube.setItems(cubeList);
        // Set first cube as the default one





    }
}
