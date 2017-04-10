package MainPackage.Accessor;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by ogs10_000 on 27/03/2017.
 */
public class BDAccessor {


    private String url = null;
    private String usuari = null;
    private String contrasenya = null;

    public BDAccessor() {
    }

    /**
     * Constructor amb tots els parÃ metres.
     *
     * @param url
     * @param usuari
     * @param contrasenya
     *
     */
    public BDAccessor(String url, String usuari, String contrasenya) {
        this.url = url;
        this.usuari = usuari;
        this.contrasenya = contrasenya;
    }

    /**
     * ObtÃ© una connexiÃ³ de la BD. Primer de tot llegeix el fitxer de configuraciÃ³ (database.properties).
     *
     * @return La connexiÃ³ a la BD.
     * @throws IOException Si no troba el fitxer de parÃ metres de la connexiÃ³
     * @throws SQLException Si no pot obtenir la connexiÃ³ a BD.
     * @throws ClassNotFoundException Si no troba la classe del driver de BD
     */
    public Connection obtenirConnexio() throws SQLException, IOException, ClassNotFoundException {
        Connection conn = null;
        // Llegeix el fitxer de configuraciÃ³ de la BD o els paramÃ¨tres del constructor.
        try {
            assignarPropietatsBD();
        } catch (IOException ex) {
            System.err.println("ERROR: No s'ha pogut llegir el fitxer de parametres");
        }

        // CÃ rrega manual del driver no fem servir el contingut al fitxer de propietats
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.err.println("ERROR: No s'ha pogut carregar el driver JDBC");
            throw ex;
        }

        // ConnexiÃ³
        try {
            conn = DriverManager.getConnection(url, usuari, contrasenya);
            System.out.println("connexiÃ³ efectuada");
        } catch (SQLException e) {
            System.err.println("ERROR: No s'ha pogut connectar amb el SGBD");
            throw e;
        }

        conn.setAutoCommit(false);

        return conn;
    }

    /**
     * Tanca la connexiÃ³ a la BD.
     *
     * @param conn ConnexiÃ³ a la BD.
     */
    public static void tancarConnexio(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error tancant la connexio a la BD");
                System.err.println(e.getMessage());
                System.exit(-1);
            }
        }
    }

    /**
     * Tanca el PreparedStatement si encara estÃ  obert.
     *
     * @param pstmt
     */
    public static void tancarPreparedStatement(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error en tancar el PreparedStatement");
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Llegeix un fitxer de propietats i el retorna en un objecte Properties
     *
     * @param fitxer Nom del fitxer de propietats
     * @return Properties Objecte propietats carregat amb els continguts del fitxer
     * @throws IOException Si no troba el fitxer
     */
    private static Properties carregarParametres(String fitxer) throws IOException {
        Properties propietats = new Properties();
        try (FileInputStream fitxerEntrada = new FileInputStream(fitxer)) {
            propietats.load(fitxerEntrada);
        }
        return propietats;
    }

    /**
     * Carrega el fitxer de propietats de la BD i les assigna als atributs de la classe.
     *
     * @throws IOException Si no llegeix el fitxer de configuraciÃ³.
     */
    private void assignarPropietatsBD() throws IOException {
        // Obtenim la configuraciÃ³ de la base de dades
        Properties propietats = null;
        try {
            propietats = carregarParametres(System.getProperty("user.dir") + "/src/MainPackage/Accessor/database.properties");
            url = propietats.getProperty("jdbc.url");
            usuari = propietats.getProperty("jdbc.username");
            contrasenya = propietats.getProperty("jdbc.password");
        } catch (IOException e) {
            //els valors del constructor
            System.err.println("No s'ha trobat el fitxer basedades.properties");
            throw e;
        }
    }

    /*
     * MÃ¨tode principal per a efectuar un joc de proves
     */
    public static void main(String[] args) {
        //BDAccessor bd = new BDAccessor("jdbc:mysql://localhost:3306/daw2","root","root");
        BDAccessor bd = new BDAccessor();

        try (Connection conn = bd.obtenirConnexio()){
        } catch (SQLException | IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }
}
