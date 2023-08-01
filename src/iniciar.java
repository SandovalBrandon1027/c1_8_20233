import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class iniciar {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton deleteButton;
    private JButton iniciarButton;
    private JPanel Registro;
    private JButton agregarButton;
    private JButton actualizarButton;
    static final String DB_URL = "jdbc:mysql://localhost/estudiantes";
    static final String USER = "root";
    static final String PASS = "root_bas3";
    static final String QUERY = "SELECT * FROM datos_estudiantes WHERE nombre=? AND password=?";
    static final String DELETE_QUERY = "DELETE FROM datos_estudiantes WHERE nombre=? AND password=?";
    static final String INSERT_QUERY = "INSERT INTO datos_estudiantes (nombre, password, edad, cedula, ciudad) VALUES (?, ?, ?, ?, ?)";

    public iniciar() {
        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = textField1.getText();
                String contraseña = new String(passwordField1.getPassword());

                // Intentamos establecer la conexión con la base de datos.
                try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                     PreparedStatement stmt = conn.prepareStatement(QUERY)) {

                    // Establecemos los parámetros de la consulta.
                    stmt.setString(1, usuario);
                    stmt.setString(2, contraseña);

                    // Ejecutamos la consulta.
                    ResultSet rs = stmt.executeQuery();

                    // Verificamos si hay resultados.
                    if (rs.next()) {
                        String Id = rs.getString("Id");
                        String nombre = rs.getString("nombre");
                        String edad = rs.getString("edad");
                        String cedula = rs.getString("cedula");
                        String ciudad = rs.getString("ciudad");

                        System.out.println("Datos del usuario:");
                        System.out.println("Id: " + Id);
                        System.out.println("Nombre: " + nombre);
                        System.out.println("edad: " + edad);
                        System.out.println("cedula: " + cedula);
                        System.out.println("ciudad: " + ciudad);
                        System.out.println("-------------------------");

                        JOptionPane.showMessageDialog(Registro, "Inicio de sesión exitoso.");
                    } else {
                        JOptionPane.showMessageDialog(Registro, "Credenciales incorrectas. Inténtalo de nuevo.",
                                "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Registro, "Error al intentar conectarse a la base de datos.",
                            "Error de conexión", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = textField1.getText();
                String contraseña = new String(passwordField1.getPassword());

                // Intentamos establecer la conexión con la base de datos.
                try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                     PreparedStatement stmt = conn.prepareStatement(DELETE_QUERY)) {

                    // Establecemos los parámetros de la consulta.
                    stmt.setString(1, usuario);
                    stmt.setString(2, contraseña);

                    // Ejecutamos la consulta de eliminación.
                    int deletedRows = stmt.executeUpdate();

                    if (deletedRows > 0) {
                        JOptionPane.showMessageDialog(Registro, "Usuario eliminado exitosamente.");
                        textField1.setText("");
                        passwordField1.setText("");
                    } else {
                        JOptionPane.showMessageDialog(Registro, "No se encontró ningún usuario para eliminar.",
                                "Error de eliminación", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Registro, "Error al intentar conectarse a la base de datos.",
                            "Error de conexión", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = textField1.getText();
                String contraseña = new String(passwordField1.getPassword());
                String edad = "18"; // Puedes obtener este valor desde un campo de texto o cualquier otra fuente de datos
                String cedula = "123456789"; // Puedes obtener este valor desde un campo de texto o cualquier otra fuente de datos
                String ciudad = "Ciudad"; // Puedes obtener este valor desde un campo de texto o cualquier otra fuente de datos

                // Intentamos establecer la conexión con la base de datos.
                try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                     PreparedStatement stmt = conn.prepareStatement(INSERT_QUERY)) {

                    // Establecemos los parámetros de la consulta.
                    stmt.setString(1, usuario);
                    stmt.setString(2, contraseña);
                    stmt.setString(3, edad);
                    stmt.setString(4, cedula);
                    stmt.setString(5, ciudad);

                    // Ejecutamos la consulta de inserción.
                    int insertedRows = stmt.executeUpdate();

                    if (insertedRows > 0) {
                        JOptionPane.showMessageDialog(Registro, "Usuario agregado exitosamente.");
                        textField1.setText("");
                        passwordField1.setText("");
                    } else {
                        JOptionPane.showMessageDialog(Registro, "Error al agregar el usuario.",
                                "Error de inserción", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Registro, "Error al intentar conectarse a la base de datos.",
                            "Error de conexión", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Iniciar");
        frame.setContentPane(new iniciar().Registro);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
