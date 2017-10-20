/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.uesocc.ingenieria.prn335_2017.web.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.primefaces.event.SelectEvent;
import sv.edu.uesocc.ingenieria.prn335_2017.datos.acceso.CategoriaFacadeLocal;

import sv.edu.uesocc.ingenieria.prn335_2017.datos.definiciones.Categoria;

/**
 *
 * @author jacque
 */
@Named(value = "categoriaBean")
@ViewScoped
public class CategoriaBean implements Serializable {

    /**
     * Creates a new instance of CategoriaBean
     */
    public CategoriaBean() {
    }
    boolean activo;

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @EJB
    CategoriaFacadeLocal categoria;
    List<Categoria> lista = new ArrayList<>();
    Categoria cat = new Categoria();

    //Getters y Stters
    public CategoriaFacadeLocal getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaFacadeLocal categoria) {
        this.categoria = categoria;
    }

    public List<Categoria> getLista() {
        return lista;
    }

    public void setLista(List<Categoria> lista) {
        this.lista = lista;
    }

    public Categoria getCat() {
        return cat;
    }

    public void setCat(Categoria cat) {
        this.cat = cat;
    }

    //Metodos CRUD
    /**
     * Este metodo sirve para crear registros en la DB
     */
    public void crear() {
        try {
            categoria.create(cat);
            llenar();
            cat = new Categoria();
            showMessage("Registro agregado");
        } catch (Exception e) {
            System.out.println("Error: " + e);
            showMessage("Error registro invalido");
        }

    }

    /**
     * Este metodo elimina datos de la fila seleccionada de la tabla
     */
    public void eliminar() {
        try {
            categoria.remove(cat);
            llenar();
            btnEditar = false;
            cat = new Categoria();
            showMessage("Registro eliminado correctamente");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * Este metodo edita algun campo de la fila seleccionada de la tabla
     */
    public void editar() {
        try {
            categoria.edit(cat);
            llenar();
            btnEditar = false;
            showMessage("Registro editado correctamente");
            cat = new Categoria();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * Este metodo sirve para capturar la seleccion del checkbox
     */
    public void chkFiltro() {
        if (activo == true) {
            this.lista = obtenerUtilizados();

        } else {
            llenar();

        }
    }

    /**
     * Este metodo sirve para obtner las categorias no utilizadas
     *
     * @return devuelve las categorias no utilizadas
     */
    public List<Categoria> obtenerUtilizados() {
        List salida;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("uesocc.edu.sv.ingenieria.prn335_webproject3_war_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        Query c = em.createNamedQuery("Categoria.NotUse");
        salida = c.getResultList();

        if (salida != null) {
            return salida;
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    /**
     * Este metodo sirve para mostrar un mensaje
     *
     * @param Mensaje espera una variable de tipo string
     */
    public void showMessage(String Mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(Mensaje));
    }

/**
 * Este metodo sirve para seleccionar una fila de la tabla
 * @param event espera recibir un evento 
 */
    public void onRowSelect(SelectEvent event) {
        cat = (Categoria) event.getObject();
        btnEditar = true;
    }
    boolean btnEditar = false;
/**
 * Este metodo sirve para habilitar los panel
 * @return devuelve un valor verdadero o falso 
 */
    public boolean isBtnEditar() {
        return btnEditar;
    }
/**
 * Este metodo habilita los botones
 * @param btnEditar espera recibir un valor verdadero o falso
 */
    public void setBtnEditar(boolean btnEditar) {
        this.btnEditar = btnEditar;
    }
/**
 * Este metodo sirve para deseleccionar la fila y limpiar el formulario
 */
    public void btnCancelar() {
        cat = new Categoria();
        btnEditar = false;
    }

    @PostConstruct
    /**
     * Este metodo sirve para llenar la tabla con los datos de la lista
     */
    public void llenar() {
        if (categoria.findAll() != null) {
            this.lista = categoria.findAll();
        } else {
            this.lista = Collections.EMPTY_LIST;
        }
    }

    /**
     * Este metodo sirve para limpiar el panel de ingresar datos
     */
    public void limpiarbtn() {
        cat = new Categoria();

    }


}
