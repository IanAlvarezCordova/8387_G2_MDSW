
package Controlador;

import com.toedter.calendar.JDateChooser;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
Ian Alvarez
 */
public class Miembros {
    
    private int ID =0;
    private String cedula = "";
    private String apellido = "";
    private String nombre = "";
    private Date fechaInicio;
    private Date fechaFin;
    private int diasRestantes = 0;
    
    //Se calculan los dias restantes y devuelve un valor int
    public int calcularDias(Date fechaFin, Date fechaActual){
        
        long tiempoTranscurrido = fechaFin.getTime() - fechaActual.getTime();
        TimeUnit unidad = TimeUnit.DAYS;
        long dias = unidad.convert(tiempoTranscurrido, TimeUnit.MILLISECONDS);
        int Dias= Math.toIntExact(dias);
        //
        if (Dias<0)
            Dias=0;
        return Dias;      
    }
    
    public void InsertarMiembro(JTextField pCedula, JTextField pApellido, JTextField pNombre, JTextField pFechaInicio, 
            JDateChooser pFechaFin ){
        
        setCedula(pCedula.getText()); //parametro 1
        setApellido(pApellido.getText()); //parametro 2
        setNombre(pNombre.getText()); //parametro 3
        SimpleDateFormat fechaformato = new SimpleDateFormat("yyyy-MM-dd");
        try{            
            setFechaInicio(fechaformato.parse(pFechaInicio.getText())); //parametro 4
            setFechaFin(fechaformato.parse(((JTextField)pFechaFin.getDateEditor().getUiComponent()).getText())); //parametro 5
        }catch(ParseException ex){
            JOptionPane.showMessageDialog(null, "Error al obtener la fecha: "+ex);
        }           
        //llamamos al constructor calcularDias con fechaInicio, fechaFin
        setDiasRestantes(calcularDias(getFechaFin(), getFechaInicio())); //parametro 6
        //try {           
            /**Date fechaInicio = fechaformato.parse(pFechaInicio.getText()); //2022/12/29
            System.out.println("FECHA INICIO: "+fechaInicio);
            Date fechaFin = fechaformato.parse(((JTextField)pFechaFin.getDateEditor().getUiComponent()).getText()); //2023/01/29
            System.out.println("FECHA FIN: "+fechaFin);*/


        
        /**} catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error al convertir fechas: "+ex);
        }*/
        
        //establecemos la conexion para guardar los valores obtenidos
        Connection con = conexion.getConexion();
        
        String consulta = ("INSERT INTO gimnasio.miembros (Cedula, Apellido, Nombre, FechaInicio, FechaFin, DiasRestantes) VALUES (?, ?, ?, ?, ?, ?)");
        
        try {
            
            CallableStatement cs = conexion.getConexion().prepareCall(consulta);
            
            cs.setString(1, getCedula());
            cs.setString(2, getApellido());
            cs.setString(3, getNombre());
            java.sql.Date FechaInicio = new java.sql.Date(getFechaInicio().getTime());
            cs.setDate(4, FechaInicio);
            java.sql.Date FechaFin = new java.sql.Date(this.getFechaFin().getTime());
            cs.setDate(5, FechaFin);                     
            cs.setInt(6, getDiasRestantes());
            
            cs.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Se ingreso el miembro correctamente");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "NO se ingreso el miembro: "+e.toString());
        }
        
    //Fin de Ingreso miembro  
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getDiasRestantes() {
        return diasRestantes;
    }

    public void setDiasRestantes(int diasRestantes) {        
        this.diasRestantes = diasRestantes;
       
    }
  
}
