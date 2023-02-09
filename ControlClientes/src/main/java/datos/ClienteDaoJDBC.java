package datos;

import dominio.Cliente;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteDaoJDBC {

    private static final String SQL_SELECT = " SELECT id_cliente,nombre,apellido,email,telefono,saldo FROM cliente";
    private static final String SQL_SELECT_BY_ID = " SELECT id_cliente, nombre, apellido,email,telefono,saldo FROM cliente WHERE id_cliente=?";

    private static final String SQL_INSERT = " INSERT INTO cliente(nombre,apellido,email,telefono,saldo) VALUES (?,?,?,?,?)";

    private static final String SQL_UPDATE = " UPDATE cliente SET nombre=?,apellido=?,email=?,telefono=?,saldo=? WHERE id_cliente=?";

    private static final String SQL_DELETE = " DELETE FROM cliente WHERE id_cliente=? ";

    public List<Cliente> listar() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cliente cliente = null;
        List<Cliente> clientes = new ArrayList<>();

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                // Un loop para recorrer cada cliente que encuentre la query
                // y guardo todas en las variables, el atributo que trae de la bd
                int id_cliente = rs.getInt("id_cliente");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                double saldo = rs.getDouble("saldo");

                //Creo un nuevo cliente y lo agrego a la Lista que voy a retornar
                cliente = new Cliente(id_cliente, nombre, apellido, email, telefono, saldo);
                clientes.add(cliente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //Cierro todo
            Conexion.close(conn);
            Conexion.close(stmt);
            Conexion.close(rs);
        }
        return clientes;
    }

    public Cliente encontrar(Cliente cliente) {

        Connection conn = null;

        PreparedStatement stmt = null;

        ResultSet rs = null;

        try {

            conn = Conexion.getConnection();

            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);

            stmt.setInt(1, cliente.getId_cliente());

            rs = stmt.executeQuery();

            rs.next();

            //rs.absolute(1); //Nos posicionamos en el primer registro devuelto
            String nombre = rs.getString("nombre");

            String apellido = rs.getString("apellido");

            String email = rs.getString("email");

            String telefono = rs.getString("telefono");

            double saldo = rs.getDouble("saldo");

            cliente.setNombre(nombre);

            cliente.setApellido(apellido);

            cliente.setEmail(email);

            cliente.setTelefono(telefono);

            cliente.setSaldo(saldo);

        } catch (SQLException ex) {

            ex.printStackTrace(System.out);

        } finally {

        //Conexion.close(rs);

            Conexion.close(stmt);

            Conexion.close(conn);

        }

        return cliente;

    }

    public int insertar(Cliente cliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);

            // Tengo que pasar todos los parametros de la query INSERT
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefono());
            stmt.setDouble(5, cliente.getSaldo());

            rows = stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //Cierro todo
            Conexion.close(conn);
            Conexion.close(stmt);

        }
        return rows;
    }

    public int actualizar(Cliente cliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);

            // Tengo que pasar todos los parametros de la query UPDATE
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefono());
            stmt.setDouble(5, cliente.getSaldo());

            stmt.setInt(6, cliente.getId_cliente());

            rows = stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //Cierro todo
            Conexion.close(conn);
            Conexion.close(stmt);

        }
        return rows;
    }

    public int eliminar(Cliente cliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);

            // Solo paso el id para eliminar
            stmt.setInt(1, cliente.getId_cliente());

            rows = stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //Cierro todo
            Conexion.close(conn);
            Conexion.close(stmt);

        }
        return rows;
    }
}
