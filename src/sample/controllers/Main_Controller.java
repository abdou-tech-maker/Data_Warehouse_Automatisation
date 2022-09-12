package sample.controllers;

import com.jfoenix.controls.JFXButton;
import dao.AssociationDao;
import dao.ClassTableDao;
import dao.CubeDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import module.Cube;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Main_Controller  implements Initializable {



    @FXML
     BorderPane borderpane;
    @FXML
    private JFXButton BtnAssociation;

    @FXML
    private JFXButton BtnSchema;

    @FXML
    private JFXButton BtnTables;

    @FXML
    private JFXButton attributePanelBtn;


    @FXML
    private JFXButton BtnRule;



   @FXML
    private void setBtnSchema (ActionEvent event )throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("../diagram.fxml"));
        borderpane.setCenter(view);
    }
  @FXML
  private void setBtnTables (ActionEvent event )throws IOException{
      AnchorPane view = FXMLLoader.load(getClass().getResource("../Add_Class.fxml"));
      borderpane.setCenter(view);

  }

    @FXML
    private void onCLickAttributePanelBtn (ActionEvent event )throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("../newAttribute.fxml"));
        borderpane.setCenter(view);
    }

    @FXML
    private void setBtnAssociation (ActionEvent event )throws IOException{
        AnchorPane view = FXMLLoader.load(getClass().getResource("../Add_Association.fxml"));
        borderpane.setCenter(view);

    }

    @FXML
    private void setBtnCube (ActionEvent event )throws IOException{
        AnchorPane view = FXMLLoader.load(getClass().getResource("../CreateCube.fxml"));
        borderpane.setCenter(view);

    }
    @FXML
    private void setBtnRules (ActionEvent event )throws IOException{
        AnchorPane view = FXMLLoader.load(getClass().getResource("../addRule.fxml"));
        borderpane.setCenter(view);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AnchorPane view = null;
        try {
            view = FXMLLoader.load(getClass().getResource("../CreateCube.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderpane.setCenter(view);
    }
}
