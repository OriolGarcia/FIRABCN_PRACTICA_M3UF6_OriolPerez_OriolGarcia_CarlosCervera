package MainPackage;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.Types;

/**
 * Created by ogs10_000 on 02/04/2017.
 */
public class Utils {

    public  static void omplirTableView(TableView tableView, ResultSet rs){
        ObservableList<ObservableList> data;
        try{
            data = FXCollections.observableArrayList();
            tableView.getItems().clear();
            tableView.getColumns().clear();
            int[] types= new int[rs.getMetaData().getColumnCount()];
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                types[i]=rs.getMetaData().getColumnType(i+1);

                System.out.println(types[i]);

                if(types[i]==Types.BOOLEAN) {
                    TableColumn<Object, Boolean> col= new TableColumn(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(new PropertyValueFactory<Object, Boolean>("checked"));

                    col.setCellFactory(new Callback<TableColumn<Object, Boolean>, TableCell<Object, Boolean>>() {
                        public TableCell<Object, Boolean> call(TableColumn<Object, Boolean> p) {
                            return new CheckBoxTableCell<Object, Boolean>();
                        }
                    });

                    tableView.getColumns().addAll(col);
                }else {
                    TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {

                            if(param.getValue().get(j)!= null)
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                            else return new SimpleStringProperty("");
                        }
                    });

                    tableView.getColumns().addAll(col);
                }
            }

            while(rs.next()){
                ObservableList<Object> row = FXCollections.observableArrayList();

                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    if(types[i-1] == Types.NUMERIC ){
                        row.add(rs.getLong(i));
                    } else if(types[i-1] == Types.BIT){
                        if(rs.getString(i).equals("0")){
                            row.add("NO");
                        }
                        else
                        row.add("SÍ");
                    } else {
                        row.add(rs.getString(i));
                    }

                }
                  data.add(row);
            }

            tableView.setItems(data);

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
}
