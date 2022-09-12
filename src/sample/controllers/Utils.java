package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Utils {
    public static void alTotv(TableView table, ArrayList list){
        if (list.isEmpty()) return;
        table.getColumns().removeAll(table.getColumns());
        table.getItems().removeAll(table.getItems());
        Field[] fields;
        fields=list.get(0).getClass().getDeclaredFields();
        ArrayList<String> l=new ArrayList<>();
        for (Field field: fields){
            if (field.getType().toString().contains("Integer") ||
                    field.getType().toString().contains("String") ||
                    field.getType().toString().contains("int") ||
                    field.getType().toString().contains("double") ||
                    field.getType().toString().contains("float") ||
                    field.getType().toString().contains("boolean") ||
                    field.getType().toString().contains("Double")
            )
            {
                l.add(field.getName());
            }
        }
        for (String s :l){
            TableColumn<Object,Integer> column=new TableColumn<>(s);
            column.setCellValueFactory(new PropertyValueFactory<>(s));
            column.setPrefWidth(table.getPrefWidth()/l.size());
            table.getColumns().add(column);
        }
        ObservableList<Object> observableList= FXCollections.observableArrayList(list);
        table.setItems(observableList);
    }
}
