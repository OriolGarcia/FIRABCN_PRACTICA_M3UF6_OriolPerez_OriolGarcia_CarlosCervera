package MainPackage.DAOsImplements;

import MainPackage.Accessor.BDAccessor;
import MainPackage.DAOs.EmpresesDAO;
import MainPackage.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Carlos on 3/04/17.
 */
public class EmpresesDAOImplement implements EmpresesDAO {

    private static Connection conn;
    private static PreparedStatement pstmt;
    private static BDAccessor bd = null;

    /**
     * Afegir Empresa
     * @param conn connexió a la BD
     * @param Nom Nom de la empresa
     * @param CIF CIF de la empresa
     * @param PersonaContacte Persona de contacte de la empresa
     * @param Telefon Telèfon de la empresa
     * @param Tipus Tipus d'empresa
     * @param Fira Fira a la que pertany
     * @return retorna true si s'ha afegit correctament
     */
    public boolean AddEmpresa(Connection conn, String Nom, String CIF, String PersonaContacte, String Telefon, int Tipus, int Fira){
        try {
            String cadenaSQL = "INSERT INTO Empreses(Nom,CIF,`Persona de contacte`,Telefon,Tipus,Fira)"
                    +" VALUES(?,?,?,?,?,?);";
            pstmt = conn.prepareStatement(cadenaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, Nom);
            pstmt.setString(2, CIF);
            pstmt.setString(3, PersonaContacte);
            pstmt.setString(4, Telefon);
            pstmt.setInt(5, Tipus);
            pstmt.setInt(6, Fira);
            int n = pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                while (rs.next()) {
                    System.out.println("Codi generat per getGeneratedKeys():"
                            + rs.getInt(1));
                }
            }
            conn.commit();
            System.out.println("Empreses: S'ha afegit " + n + " items");
            if (n>0)return true;
            else return false;
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }finally {
            try {
                pstmt.clearParameters();
            }catch (SQLException ex){}
        }
    }

    /**
     * Actualitzar empresa
     * @param conn connexió a la BD
     * @param id identificador de empresa
     * @param Nom Nom de la empresa
     * @param CIF CIF de la empresa
     * @param PersonaContacte Persona de contacte de la empresa
     * @param Telefon Telèfon de la empresa
     * @param Tipus Tipus d'empresa
     * @param Fira Fira a la que pertany
     * @return retorna true si s'ha modificat correctament
     */
    public boolean UpdateEmpresa(Connection conn, int id, String Nom, String CIF, String PersonaContacte, String Telefon, int Tipus, int Fira){
        try {
            String cadenaSQL = "UPDATE Empreses SET Nom=?,CIF=?,`Persona de contacte`=?,Telefon=?,Tipus=?,Fira=? WHERE EmpresaID=?;";
            pstmt = conn.prepareStatement(cadenaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, Nom);
            pstmt.setString(2, CIF);
            pstmt.setString(3, PersonaContacte);
            pstmt.setString(4, Telefon);
            pstmt.setInt(5, Tipus);
            pstmt.setInt(6, Fira);
            pstmt.setInt(7, id);
            int n = pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                while (rs.next()) {
                    System.out.println("Codi generat per getGeneratedKeys():"
                            + rs.getInt(1));
                }
            }
            conn.commit();
            if (n>0)return true;
            else return false;
        }catch (SQLException ex){

            return false;
        }finally {
            try {
                pstmt.clearParameters();
            }catch (SQLException ex){}
        }

    }

    /**
     * Eliminar empresa
     * @param conn connexió a la BD
     * @param id identificador de empresa
     * @return retorna true si s'ha eliminat correctament
     */
    public boolean DeleteEmpresa(Connection conn, int id){
        try {
            String cadenaSQL = "DELETE from Empreses Where EmpresaID = ?;";
            pstmt = conn.prepareStatement(cadenaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1,id);


            int n = pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                while (rs.next()) {
                    System.out.println("Codi generat per getGeneratedKeys():"
                            + rs.getInt(1));
                }
            }
            if(Utils.dialegConfirmacioEliminacio()) {
                conn.commit();

            }else{
                conn.rollback();
            }

            if (n>0)return true;
            else return false;
        }catch (SQLException ex){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error en eliminar");
            alert.setContentText("Per eliminar aquest registre primer has d'eliminar el seu contingut!");
            return false;
        }finally {
            try {
                pstmt.clearParameters();
            }catch (SQLException ex){}
        }

    }

    /**
     * Per cercar a empresa
     * @param conn connexió a la BD
     * @param NomSearch nom a cercar
     * @param Fira fira
     * @param tableView taula on cercar
     */
    public void findbyParams(Connection conn, String NomSearch, int Fira, TableView tableView){
        try {
            String cadenaSQL= "SELECT EmpresaID,Nom,CIF,`Persona de contacte`,Telefon,Fires.Titol, TipusEmpresa.TitolTipus as `Tipus de empresa` "
                    +" FROM Empreses"
                    +" INNER JOIN Fires ON Fires.FiraID = Empreses.Fira"
                    +" INNER JOIN TipusEmpresa ON TipusEmpresa.TipusID = Empreses.Tipus"
                    +" WHERE ((LENGTH(?) <1 or Nom like ?) and Fira=?)";

            pstmt = conn.prepareStatement(cadenaSQL);
            pstmt.setString(1, NomSearch);
            pstmt.setString(2,"%"+NomSearch+"%");
            pstmt.setInt(3,Fira);
            System.out.println("1");

            try (ResultSet resultat = pstmt.executeQuery()) {
                System.out.println("retorna resultSet");
                Utils.omplirTableView(tableView,resultat);

            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());

        }
        finally {
            try{
                pstmt.clearParameters();
            }catch (SQLException ex){

            }
        }

    }

    /**
     * Per omplir els camps al actualitzar
     * @param conn connexió a la BD
     * @param id identificador de Fira
     * @param txtFielNomEmpresa camp text nom empresa
     * @param txtFieldCIF camp text CIF
     * @param txtPersonaContacte camp text contacte
     * @param txtTelefon camp text telefon
     */
    public void omplirCamps(Connection conn, int id, TextField txtFielNomEmpresa, TextField txtFieldCIF, TextField txtPersonaContacte, TextField txtTelefon){
        try {
            String cadenaSQL= "SELECT Nom,CIF,`Persona de contacte`,Telefon,Tipus FROM Empreses WHERE EmpresaID = ?";
            pstmt = conn.prepareStatement(cadenaSQL);
            pstmt.setInt(1,id);

            try (ResultSet resultat = pstmt.executeQuery()) {

                while (resultat.next()) {

                    txtFielNomEmpresa.setText(resultat.getString(1));
                    txtFieldCIF.setText(resultat.getString(2));
                    txtPersonaContacte.setText(resultat.getString(3));
                    txtTelefon.setText(resultat.getString(4));
                    //DataFi.setValue(resultat.getDate(5).toLocalDate());
                }
                System.out.println("1");
            }
        }catch (SQLException ex){
            System.out.println(ex.getErrorCode());

        }
        finally {
            try{
                pstmt.clearParameters();
            }catch (SQLException ex){
                System.out.println("3");

            }
        }
    }

    /**
     * Per omplir el ComboBox d'empreses
     * @param conn connexió a la BD
     * @param comboBox combobox
     */
    public void omplirComboBox (Connection conn, ComboBox comboBox){
        try {
            String cadenaSQL = "SELECT EmpresaID, Nom from Empreses";
            pstmt = conn.prepareStatement(cadenaSQL);
            try (ResultSet resultat = pstmt.executeQuery()) {
                ObservableList<Item> model = FXCollections.observableArrayList();

                while (resultat.next()) {

                    model.add(new Item(resultat.getInt(1), resultat.getString(2)));
                }

                comboBox.getItems().addAll(model);
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());

        } finally {
            try {
                pstmt.clearParameters();
            } catch (SQLException ex) {
                System.out.println("3");

            }
        }
    }
}
