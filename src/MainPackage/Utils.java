package MainPackage;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.ResultSet;

/**
 * Created by ogs10_000 on 02/04/2017.
 */
public class Utils {

    public  static void omplirTableView(TableView tableView, ResultSet rs){
     ObservableList<ObservableList> data;
        try{
        data = FXCollections.observableArrayList();

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){

                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));

                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {

                        return new SimpleStringProperty(param.getValue().get(j).toString());

                    }

                });



                tableView.getColumns().addAll(col);



            }



            while(rs.next()){

                ObservableList<String> row = FXCollections.observableArrayList();

                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){

                    row.add(rs.getString(i));

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
