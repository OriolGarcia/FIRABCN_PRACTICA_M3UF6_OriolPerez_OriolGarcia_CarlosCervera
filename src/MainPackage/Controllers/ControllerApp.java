package MainPackage.Controllers;

import MainPackage.Accessor.BDAccessor;
import MainPackage.DAOsImplements.*;
import MainPackage.JPA.ServeiEconomiaFira;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.stage.WindowEvent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by oriol on 30/03/2017.
 */
public class ControllerApp {
    @FXML
    TableView TbVUsers;
    @FXML
    TableView TbVFires;
    @FXML
    TableView TbVEmpreses;
    @FXML
    TableView TbVEstands;
    @FXML
    TableView TbVFiresEco;
    @FXML
    TableView TbVEconomia;

    @FXML
    TextField txtUserSearch;
    @FXML
    TextField txtFiraSearch;
    @FXML
    TextField txtEmpresaSearch;
    @FXML
    TextField txtEstandSearch;
    @FXML
    TextField txtFiraEcoSearch;
    @FXML
    TextField txtEconomiaSearch;

    @FXML
    Button btAfegirFira;
    @FXML
    Button btEliminarFira;
    @FXML
    Button btActualitzarFira;
    @FXML
    Button btAfegirEmpresa;
    @FXML
    Button btEliminarEmpresa;
    @FXML
    Button btActualitzarEmpresa;
    @FXML
    Button btAfegirEstand;
    @FXML
    Button btEliminarEstand;
    @FXML
    Button btActualitzarEstand;
    @FXML
    Button btAfegirEconomia;
    @FXML
    Button btActualitzarEconomia;
    @FXML
    Button btEliminarEconomia;

    @FXML
    Pane AdminUsuaris;
    @FXML
    Label lbEconomiaFiraSeleccionada;

    Connection connection;
    BDAccessor bdAccessor;

    /**
     * Funció que inicia la app principal
     * @param conn connexió amb la bd
     * @param bdAccessor paràmetre d'accès a la bd
     * @param usuari usuari actual
     * @param pass contrasenya de l'usuari
     */
    public void init(Connection conn, BDAccessor bdAccessor, String usuari, String pass) {
        connection = conn;
        this.bdAccessor = bdAccessor;

        initiailizeTableViewUsers();
        initiailizeTableViewFires();
        initiailizeTableViewEmpreses();
        initiailizeTableViewEstands();
        initiailizeTableViewFiresEconomia();
        initiailizeTableViewEconomia();
        UserDAOImplement UserDAOImpl = new UserDAOImplement();

        if (!UserDAOImpl.getPermissions(connection, usuari)) {
            btAfegirFira.setVisible(false);
            btEliminarFira.setVisible(false);
            btActualitzarFira.setVisible(false);
            btAfegirEmpresa.setVisible(false);
            btEliminarEmpresa.setVisible(false);
            btActualitzarEmpresa.setVisible(false);
            btAfegirEstand.setVisible(false);
            btEliminarEstand.setVisible(false);
            btActualitzarEstand.setVisible(false);
            AdminUsuaris.setVisible(false);

        }

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

        txtEstandSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                initiailizeTableViewEstands();
            }
        });

        txtFiraEcoSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                initiailizeTableViewFiresEconomia();
            }
        });

        txtEconomiaSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                initiailizeTableViewEconomia();
            }
        });

        TbVFires.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                initiailizeTableViewEmpreses();
            }
        });

        TbVEmpreses.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                initiailizeTableViewEstands();
            }
        });
        TbVFiresEco.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                initiailizeTableViewEconomia();

            }
        });
    }

    /**
     * Inicia la taula d'usuaris
     */
    private void initiailizeTableViewUsers() {

        UserDAOImplement UserDAOImpl = new UserDAOImplement();

        UserDAOImpl.findbyUsername(connection, txtUserSearch.getText(), TbVUsers);

    }

    /**
     * Per afegir un usuari
     * @param event
     */
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
        } catch (IOException ex) {
        }
    }

    /**
     * Per eliminar un usuari
     * @param event
     */
    public void eliminarUsuari(ActionEvent event) {
        try {
            TablePosition pos = (TablePosition) TbVUsers.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVUsers.getItems().get(index).toString();
            String id = selected.substring(1, selected.indexOf(","));
            System.out.println(selected);
            UserDAOImplement UserDAOImpl = new UserDAOImplement();
            UserDAOImpl.DeleteUser(connection, id);
            initiailizeTableViewUsers();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Per generar la contrasenya
     * @param event
     */
    public void generarContrasenya(ActionEvent event) {
    }

    /**
     * Per canviar els permissos d'un usuari
     * @param event
     */
    public void canviarPermisos(ActionEvent event) {
        String id = "";
        boolean accs = true;

        try {
            TablePosition pos = (TablePosition) TbVUsers.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVUsers.getItems().get(index).toString();
            id = selected.substring(1, selected.indexOf(","));
        } catch (Exception e) {
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

    /**
     * Per canviar la contrasenya
     * @param event
     */
    public void canviarContrasenya(ActionEvent event) {
    }

    /**
     * Per iniciar la taula de fires
     */
    private void initiailizeTableViewFires() {

        FiresDAOImplement FiresDAOImpl = new FiresDAOImplement();

        FiresDAOImpl.findbyParams(connection, txtFiraSearch.getText(), TbVFires);

    }

    /**
     * Per afegir una fira
     * @param event
     */
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
                    initiailizeTableViewFiresEconomia();

                }
            });
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Per actualitzar una fira
     * @param event
     */
    public void actualitzarFira(ActionEvent event) {
        int id = 0;
        boolean accs = true;
        try {
            TablePosition pos = (TablePosition) TbVFires.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVFires.getItems().get(index).toString();
            String ids = selected.substring(1, selected.indexOf(","));
            id = Integer.parseInt(ids);
            System.out.println("id: " + id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            accs = false;
        }

        if (accs) {
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
                        System.out.println("S'ha tancat Update Fires");
                        initiailizeTableViewFires();
                        initiailizeTableViewFiresEconomia();
                    }
                });
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Unselected");
            alert.setHeaderText("Fira no seleccionada.");
            alert.setContentText("Selecciona la fira que vols actualitzar.");
            alert.showAndWait();
        }
    }

    /**
     * Per eliminar una fira
     * @param event
     */
    public void eliminarFira(ActionEvent event) {
        int id = 0;
        boolean accs = true;

        try {
            TablePosition pos = (TablePosition) TbVFires.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVFires.getItems().get(index).toString();
            id = Integer.parseInt(selected.substring(1, selected.indexOf(",")));

            FiresDAOImplement firesDAOImpl = new FiresDAOImplement();
            firesDAOImpl.DeleteFira(connection, id);
            initiailizeTableViewFires();
            initiailizeTableViewFiresEconomia();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            accs = false;
        }
    }

    /**
     * Per iniciar la taula Empreses
     */
    private void initiailizeTableViewEmpreses() {
        int id = 0;
        boolean accs = true;
        try {
            TablePosition pos = (TablePosition) TbVFires.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVFires.getItems().get(index).toString();
            id = Integer.parseInt(selected.substring(1, selected.indexOf(",")));

            EmpresesDAOImplement empresesDAOImpl = new EmpresesDAOImplement();

            empresesDAOImpl.findbyParams(connection, txtEmpresaSearch.getText(), id, TbVEmpreses);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            TbVEmpreses.getItems().clear();
            TbVEmpreses.getColumns().clear();
        }
    }

    /**
     * Per afegir una empresa
     * @param event
     */
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
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            accs = false;
        }
        if (accs) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scenes/EmpresaAddScene.fxml"));
                Parent root = loader.load();
                Stage secondStage = new Stage();
                secondStage.setScene(new Scene(root, 560, 276));
                secondStage.show();
                ControllerEmpresaAdd controller = loader.getController();
                controller.init(connection, bdAccessor, id);
                secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent we) {
                        System.out.println("S'ha tancat Add Empresa");
                        initiailizeTableViewEmpreses();

                    }
                });
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Unselected");
            alert.setHeaderText("Fira no seleccionada.");
            alert.setContentText("Selecciona la fira on vols inserir l'empresa.");
            alert.showAndWait();
        }
    }

    /**
     * Per actualitzar una empresa
     * @param event
     */
    public void actualitzarEmpresa(ActionEvent event) {
        int id = 0;
        boolean accs = true;
        try {
            TablePosition pos = (TablePosition) TbVEmpreses.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVEmpreses.getItems().get(index).toString();
            String ids = selected.substring(1, selected.indexOf(","));
            id = Integer.parseInt(ids);
            System.out.println("id: " + id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            accs = false;
        }

        if (accs) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scenes/EmpresaUpdScene.fxml"));
                Parent root = loader.load();
                Stage secondStage = new Stage();
                secondStage.setScene(new Scene(root, 560, 276));
                secondStage.show();
                ControllerEmpresaUpd controller = loader.getController();
                controller.init(connection, bdAccessor, id);
                secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        System.out.println("S'ha tancat Update Empresa");
                        initiailizeTableViewEmpreses();
                    }
                });
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Unselected");
            alert.setHeaderText("Empresa no seleccionada.");
            alert.setContentText("Selecciona l'empresa que vols actualitzar.");
            alert.showAndWait();
        }
    }

    /**
     * Per eliminar una empresa
     * @param event
     */
    public void eliminarEmpresa(ActionEvent event) {
        int id = 0;
        boolean accs = true;
        try {
            TablePosition pos = (TablePosition) TbVEmpreses.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVEmpreses.getItems().get(index).toString();
            id = Integer.parseInt(selected.substring(1, selected.indexOf(",")));
            System.out.println(selected);
            EmpresesDAOImplement empDAOImpl = new EmpresesDAOImplement();
            empDAOImpl.DeleteEmpresa(connection, id);
            initiailizeTableViewEmpreses();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            accs = false;
        }
    }

    /**
     * Per iniciar la taula d'Estands
     */
    private void initiailizeTableViewEstands() {
        int id = 0;
        boolean accs = true;
        try {
            TablePosition pos = (TablePosition) TbVEmpreses.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVEmpreses.getItems().get(index).toString();
            id = Integer.parseInt(selected.substring(1, selected.indexOf(",")));

            EstandsDAOImplement estDAOImpl = new EstandsDAOImplement();

            estDAOImpl.findbyParams(connection, txtEstandSearch.getText(), id, TbVEstands);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            TbVEstands.getItems().clear();
            TbVEstands.getColumns().clear();
        }
    }

    /**
     * Per afegir un estand
     * @param event
     */
    public void afegirEstand(ActionEvent event) {

        int id = 0;
        boolean accs = true;
        try {
            TablePosition pos = (TablePosition) TbVEmpreses.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVEmpreses.getItems().get(index).toString();
            String ids = selected.substring(1, selected.indexOf(","));
            id = Integer.parseInt(ids);
            System.out.println("id: " + id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            accs = false;
        }

        int idFira = 0;
        boolean accsFira = true;
        try {
            TablePosition pos = (TablePosition) TbVFires.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVFires.getItems().get(index).toString();
            String ids = selected.substring(1, selected.indexOf(","));
            idFira = Integer.parseInt(ids);
            System.out.println("id: " + id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            accsFira = false;
        }

        if (accs && accsFira) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scenes/EstandAddScene.fxml"));
                Parent root = loader.load();

                Stage secondStage = new Stage();
                secondStage.setScene(new Scene(root, 560, 276));
                secondStage.show();

                ControllerEstandAdd controller = loader.getController();
                controller.init(connection, bdAccessor, id, idFira);

                secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent we) {
                        System.out.println("S'ha tancat Add Estand");
                        initiailizeTableViewEstands();

                    }
                });
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Unselected");
            alert.setHeaderText("Empresa no seleccionada.");
            alert.setContentText("Selecciona la empresa on vols inserir l'estand.");
            alert.showAndWait();
        }
    }

    /**
     * Per actualitzar un estand
     * @param event
     */
    public void actualitzarEstand(ActionEvent event) {
        int id = 0;
        int FiraID = 0;
        int EmpresaID = 0;
        boolean accs = true;
        try {
            TablePosition pos = (TablePosition) TbVEstands.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVEstands.getItems().get(index).toString();
            String ids = selected.substring(1, selected.indexOf(","));
            id = Integer.parseInt(ids);
            ids = selected.substring(ordinalIndexOf(selected, ",", 7) + 2, ordinalIndexOf(selected, ",", 8));
            EmpresaID = Integer.parseInt(ids);
            ids = selected.substring(ordinalIndexOf(selected, ",", 8) + 2, selected.length() - 1);
            FiraID = Integer.parseInt(ids);
            System.out.println("id: " + id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            accs = false;
        }
        if (accs) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scenes/EstandUpdScene.fxml"));
                Parent root = loader.load();

                Stage secondStage = new Stage();
                secondStage.setScene(new Scene(root, 560, 276));
                secondStage.show();

                ControllerEstandUpd controller = loader.getController();
                controller.init(connection, bdAccessor, id, FiraID, EmpresaID);

                secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent we) {
                        System.out.println("S'ha tancat Upd Estand");
                        initiailizeTableViewEstands();

                    }
                });
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Unselected");
            alert.setHeaderText("Estand no seleccionat.");
            alert.setContentText("Selecciona l'estand que vols actualitzar.");
            alert.showAndWait();
        }
    }

    /**
     * Per eliminar un estand
     * @param event
     */
    public void eliminarEstand(ActionEvent event) {
        int id = 0;
        boolean accs = true;
        try {
            TablePosition pos = (TablePosition) TbVEstands.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVEstands.getItems().get(index).toString();
            id = Integer.parseInt(selected.substring(1, selected.indexOf(",")));
            System.out.println(selected);
            EstandsDAOImplement estDAOImpl = new EstandsDAOImplement();
            estDAOImpl.DeleteEstand(connection, id);
            initiailizeTableViewEstands();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            accs = false;
        }
    }

    /**
     * Per obtenir la posició d'un substring segons un index d'aparició
     * @param str string a analitzar
     * @param substr substring per analitzar
     * @param n index de substring
     * @return retorna la posició de la substring en int
     */
    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }

    /**
     * Per iniciar la taula de Fires a Economia
     */
    private void initiailizeTableViewFiresEconomia() {

        FiresDAOImplement FiresDAOImpl = new FiresDAOImplement();

        FiresDAOImpl.findbyParams(connection, txtFiraEcoSearch.getText(), TbVFiresEco);

    }

    /**
     * Per iniciar la taula d'Economia
     */
    private void initiailizeTableViewEconomia(){
        int id = 0;
        boolean accs = true;
        try {
            TablePosition pos = (TablePosition) TbVFiresEco.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVFiresEco.getItems().get(index).toString();
            id = Integer.parseInt(selected.substring(1, selected.indexOf(",")));
          //  EntityManagerFactory emf =Persistence.createEntityManagerFactory("ServeiEconomiaFira");
           // EntityManager em = emf.createEntityManager();
          //  ServeiEconomiaFira se = new ServeiEconomiaFira(em);
           // lbEconomiaFiraSeleccionada.setText(se.EconomiaFiraToString(id));
            EconomiaDAOImplement ecoDAOImpl = new EconomiaDAOImplement();

            ecoDAOImpl.findbyParams(connection, txtEconomiaSearch.getText(), id, TbVEconomia);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            TbVEconomia.getItems().clear();
            TbVEconomia.getColumns().clear();
        }
    }

    /**
     * Per afegir un dia a economia
     * @param event
     */
    public void AfegirEconomia(ActionEvent event){
        int id = 0;
        boolean accs = true;

        try {
            TablePosition pos = (TablePosition) TbVFiresEco.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVFiresEco.getItems().get(index).toString();
            String ids = selected.substring(1, selected.indexOf(","));
            id = Integer.parseInt(ids);
            System.out.println("id: " + id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            accs = false;
        }
        if (accs) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scenes/EconomiaAddScene.fxml"));
                Parent root = loader.load();
                Stage secondStage = new Stage();
                secondStage.setScene(new Scene(root, 560, 276));
                secondStage.show();
                ControllerEconomiaAdd controller = loader.getController();
                controller.init(connection, bdAccessor, id);
                secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent we) {
                        System.out.println("S'ha tancat Add Economia");
                        initiailizeTableViewEconomia();

                    }
                });
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Unselected");
            alert.setHeaderText("Fira no seleccionada.");
            alert.setContentText("Selecciona la fira on vols inserir el dia.");
            alert.showAndWait();
        }
    }

    /**
     * Per actualitzar un dia a economia
     * @param event
     */
    public void ActualitzarEconomia (ActionEvent event){
        int id = 0;
        Date dateID = new Date();
        boolean accs = true;

        try {
            TablePosition pos = (TablePosition) TbVEconomia.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVEconomia.getItems().get(index).toString();
            String ids = selected.substring(1, selected.indexOf(","));
            id = Integer.parseInt(ids);
            System.out.println("id: " + id);

            ids = selected.substring(ordinalIndexOf(selected, ",", 1) + 2, ordinalIndexOf(selected, ",", 2));
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            dateID = formatter.parse(ids);
            System.out.println("Date: " + formatter.format(dateID));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            accs = false;
        }

        if (accs) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scenes/EconomiaUpdScene.fxml"));
                Parent root = loader.load();
                Stage secondStage = new Stage();
                secondStage.setScene(new Scene(root, 560, 276));
                secondStage.show();
                ControllerEconomiaUpd controller = loader.getController();
                controller.init(connection, bdAccessor, id, dateID);
                secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        System.out.println("S'ha tancat Update Economia");
                        initiailizeTableViewEconomia();
                    }
                });
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Unselected");
            alert.setHeaderText("Dia no seleccionat.");
            alert.setContentText("Selecciona el dia que vols actualitzar.");
            alert.showAndWait();
        }
    }

    /**
     * Per eliminar un dia a economia
     * @param event
     */
    public void eliminarEconmia(ActionEvent event) {
        int id = 0;
        Date dateID = new Date();
        boolean accs = true;

        try {
            TablePosition pos = (TablePosition) TbVEconomia.getSelectionModel().getSelectedCells().get(0);
            int index = pos.getRow();
            String selected = TbVEconomia.getItems().get(index).toString();
            String ids = selected.substring(1, selected.indexOf(","));
            id = Integer.parseInt(ids);
            System.out.println("id: " + id);

            ids = selected.substring(ordinalIndexOf(selected, ",", 1) + 2, ordinalIndexOf(selected, ",", 2));
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            dateID = formatter.parse(ids);
            System.out.println("Date: " + formatter.format(dateID));

            EconomiaDAOImplement ecoDAOImpl = new EconomiaDAOImplement();
            ecoDAOImpl.DeleteEconomia(connection, id, dateID);
            initiailizeTableViewEconomia();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            accs = false;
        }
    }
}
