import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author yova_
 */
public class BaseDatos {
    Connection conexion;
    Statement transaccion;
    ResultSet cursor;
    
    public BaseDatos(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tap_practica1_u4?zeroDateTimeBehavior=CONVERT_TO_NULL","root","");
            transaccion = conexion.createStatement();
        }catch(SQLException ex){
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean insertar(Productos prod){
        String SQL_Insert = "INSERT INTO producto VALUES(NULL,'%DESC%',"+prod.precio+","+prod.existencia+")";
        SQL_Insert = SQL_Insert.replaceAll("%DESC%", prod.descripcion);
    
        try {
            transaccion.execute(SQL_Insert);
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }
    
    public ArrayList<String[]> consultarTodos(){
        ArrayList<String[]> resultado = new ArrayList<String[]>();
        
        try {
            cursor = transaccion.executeQuery("SELECT * FROM PRODUCTO");
            if(cursor.next()){
                do{
                    String [] renglon = { cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)};
                    resultado.add(renglon);
                }while(cursor.next()); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    
    public boolean eliminar(String ID){
        try {
            transaccion.execute("DELETE FROM PRODUCTO WHERE IDPRODUCTOS="+ID);
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    public Productos consultarID(String ID){
        Productos productoResultado = new Productos();
        try {
            cursor = transaccion.executeQuery("SELECT * FROM PRODUCTO WHERE IDPRODUCTOS ="+ID);
        
            if(cursor.next()){
                productoResultado.descripcion = cursor.getString(2);
                productoResultado.precio = Float.parseFloat(cursor.getString(3));
                productoResultado.existencia  = Integer.parseInt(cursor.getString(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productoResultado;
    }
    
    public boolean actualizar(Productos prod){
        String update = "UPDATE producto SET DESCRIPCION = '%DESC%', PRECIO = "+prod.precio+", EXISTENCIA = "+prod.existencia+" WHERE IDPRODUCTOS = "+prod.idProducto;
        update = update.replaceAll("%DESC%", prod.descripcion);
    
        try {
            transaccion.execute(update);
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }
}
