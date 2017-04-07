package MainPackage.Controllers;

import MainPackage.Accessor.BDAccessor;
import MainPackage.DAOsImplements.EmpresesDAOImplement;
import MainPackage.DAOsImplements.FiresDAOImplement;
import MainPackage.DAOsImplements.UserDAOImplement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.Connection;

/**
 * Created by oriol on 30/03/2017.
 */
public class ControllerApp{
    @FXML
    TextField txtUserSearch;
    @FXML
    TableView TbVUsers;
    @FXML
    TextField txtFiraSearch;
    @FXML
    TableView TbVFires;
    @FXML
    TableView TbVEmpreses;
    @FXML
    TableView TbVEstands;
    @FXML
    TextField txtEmpresaSearch;
    Connection connection;
    BDAccessor bdAccessor;

    public void init(Connection conn, BDAccessor bdAccessor) {
        connection = conn;
        this.bdAccessor = bdAccessor;

        initiailizeTableViewUsers();
        initiailizeTableViewFires();
        initiailizeTableViewEmpreses();
        txtUserSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                initiailizeTableViewUsers();
            }
        });
        txtFiraSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                initiailizeTableViewFires();
            }
        });
        txtEmpresaSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                initiailizeTableViewEmpreses();
            }
        });
        TbVFires.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                initiailizeTableViewEmpreses();
            }
        });
    }
    private void initiailizeTableViewUsers(){

        UserDAOImplement UserDAOImpl = new UserDAOImplement();

        UserDAOImpl.findbyUsername(connection,txtUserSearch.getText(),TbVUsers);

    }
    public void afegirUsuari(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scenes/UserAddScene.fxml"));
            Parent root = loader.load();
            Stage secondStage = new Stage();
            secondStage.setScene(new Scene(root, 560, 276));
            secondStage.show();
            ControllerUserAdd controller = loader.getController();
            controller.init(connection, bdAccessor);
            secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent we) {
                    System.out.println("S'ha tancat Add User");
                    initiailizeTableViewUsers();
                }
            });
        }catch (IOException ex){}
    }
    public void eliminarUsuari(ActionEvent event) {
        try {
            TablePosition pos = (TablePosition) TbVUsers.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVUsers.getItems().get(index).toString();
            String id = selected.substring(1, selected.indexOf(","));
            System.out.println(selected);
            UserDAOImplement UserDAOImpl = new UserDAOImplement();
            UserDAOImpl.DeleteUser(connection,id);
            initiailizeTableViewUsers();
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void generarContrasenya(ActionEvent event) {}
    public void canviarPermisos(ActionEvent event) {
        String id = "";
        boolean accs = true;

        try {
            TablePosition pos = (TablePosition) TbVUsers.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVUsers.getItems().get(index).toString();
            id = selected.substring(1, selected.indexOf(","));
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            accs = false;
        }

        if (accs) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scenes/PermisionsChangeScene.fxml"));
                Parent root = loader.load();
                Stage secondStage = new Stage();
                secondStage.setScene(new Scene(root, 560, 276));
                secondStage.show();
                ControllerPermisos controller = loader.getController();
                controller.init(connection, bdAccessor, id);
                secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent we) {
                        System.out.println("S'ha tancat Modificar permsoss");
                        initiailizeTableViewUsers();
                    }
                });
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
    public void canviarContrasenya(ActionEvent event) {}

    private void initiailizeTableViewFires(){

        FiresDAOImplement FiresDAOImpl = new FiresDAOImplement();

        FiresDAOImpl.findbyParams(connection,txtFiraSearch.getText(),TbVFires);

    }
    public void afegirFira(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scenes/FiraAddScene.fxml"));
            Parent root = loader.load();
            Stage secondStage = new Stage();
            secondStage.setScene(new Scene(root, 560, 276));
            secondStage.show();
            ControllerFiraAdd controller = loader.getController();
            controller.init(connection, bdAccessor);
            secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent we) {
                    System.out.println("S'ha tancat Add Fira");
                    initiailizeTableViewFires();

                              }
            });
        }catch (IOException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void actualitzarFira(ActionEvent event){
        int id = 0;
        boolean accs = true;
        try {
            TablePosition pos = (TablePosition) TbVFires.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVFires.getItems().get(index).toString();
            String ids = selected.substring(1, selected.indexOf(","));
            id = Integer.parseInt(ids);
            System.out.println("id: " + id);
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            accs = false;
        }

        if(accs) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scenes/FiraUpdScene.fxml"));
                Parent root = loader.load();
                Stage secondStage = new Stage();
                secondStage.setScene(new Scene(root, 560, 276));
                secondStage.show();
                ControllerFiraUpd controller = loader.getController();
                controller.init(connection, bdAccessor, id);
                secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        System.out.println("S'ha tancat Update User");
                        initiailizeTableViewFires();
                    }
                });
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    public void eliminarFira(ActionEvent event) {
        int id = 0;
        boolean accs = true;
        try{
        TablePosition pos = (TablePosition) TbVFires.getSelectionModel().getSelectedCells().get(0);
        int index = pos.getRow();
        String selected = TbVFires.getItems().get(index).toString();
        id =Integer.parseInt( selected.substring(1, selected.indexOf(",")));
        System.out.println(selected);
        FiresDAOImplement firesDAOImpl = new FiresDAOImplement();
        firesDAOImpl.DeleteFira(connection,id);
        initiailizeTableViewFires();
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            accs = false;
        }
    }

    private void initiailizeTableViewEmpreses(){
        int id = 0;
        boolean accs = true;
        try{
        TablePosition pos = (TablePosition) TbVFires.getSelectionModel().getSelectedCells().get(0);
        int index = pos.getRow();
        String selected = TbVFires.getItems().get(index).toString();
        id =Integer.parseInt( selected.substring(1, selected.indexOf(",")));


        EmpresesDAOImplement empresesDAOImpl = new EmpresesDAOImplement();

        empresesDAOImpl.findbyParams(connection,txtEmpresaSearch.getText(),id,TbVEmpreses);
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            TbVEmpreses.getItems().clear();
            TbVEmpreses.getColumns().clear();
        }
    }
    public void afegirEmpresa(ActionEvent event) {


        int id = 0;
        boolean accs = true;
        try {
            TablePosition pos = (TablePosition) TbVFires.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVFires.getItems().get(index).toString();
            String ids = selected.substring(1, selected.indexOf(","));
            id = Integer.parseInt(ids);
            System.out.println("id: " + id);
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            accs = false;
        }
        if(accs){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scenes/EmpresaAddScene.fxml"));
            Parent root = loader.load();
            Stage secondStage = new Stage();
            secondStage.setScene(new Scene(root, 560, 276));
            secondStage.show();
            ControllerEmpresaAdd controller = loader.getController();
            controller.init(connection, bdAccessor,id);
            secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent we) {
                    System.out.println("S'ha tancat Add Empresa");
                    initiailizeTableViewEmpreses();

                }
            });
        }catch (IOException ex){
            System.out.println("Error: " + ex.getMessage());
        }}
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Unselected");
            alert.setHeaderText("Fira no seleccionada.");
            alert.setContentText("Selecciona la fira on vols inserir l'empresa.");
            alert.showAndWait();
        }
    }

    public void actualitzarEmpresa(ActionEvent event){
        int id = 0;
        boolean accs = true;
        try {
            TablePosition pos = (TablePosition) TbVFires.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVFires.getItems().get(index).toString();
            String ids = selected.substring(1, selected.indexOf(","));
            id = Integer.parseInt(ids);
            System.out.println("id: " + id);
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            accs = false;
        }

        if(accs) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scenes/FiraUpdScene.fxml"));
                Parent root = loader.load();
                Stage secondStage = new Stage();
                secondStage.setScene(new Scene(root, 560, 276));
                secondStage.show();
                ControllerFiraUpd controller = loader.getController();
                controller.init(connection, bdAccessor, id);
                secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        System.out.println("S'ha tancat Update User");
                        initiailizeTableViewFires();
                    }
                });
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    public void eliminarEmpresa(ActionEvent event) {
        int id = 0;
        boolean accs = true;
        try{
            TablePosition pos = (TablePosition) TbVFires.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVFires.getItems().get(index).toString();
            id =Integer.parseInt( selected.substring(1, selected.indexOf(",")));
            System.out.println(selected);
            FiresDAOImplement firesDAOImpl = new FiresDAOImplement();
            firesDAOImpl.DeleteFira(connection,id);
            initiailizeTableViewFires();
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            accs = false;
        }
    }
}
