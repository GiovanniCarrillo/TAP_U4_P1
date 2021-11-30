
/**
 *
 * @author yova_
 */
public class Productos {
    int idProducto, existencia;
    String descripcion; 
    float precio;
    
    
    public Productos(){
    
    }   
    
    public Productos(String d, float p, int e){
        descripcion = d;
        existencia = e;
        precio = p;
    }

}
