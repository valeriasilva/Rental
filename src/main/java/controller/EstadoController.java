/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import model.Estado;
import repository.GenericRepository;

@ManagedBean
@RequestScoped
public class EstadoController implements Serializable {

    private Estado estado;
    private List<Class> estados;

    /**
     * Creates a new instance of EstadoController
     */
    public EstadoController() {
        this.estado = new Estado();
        this.estados = new ArrayList<Class>();
    }

    public void salvar() {
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        try {
            repository.adiciona(this.getEstado());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Estado inserido!!"));

        } catch (Exception e) {
            System.out.println(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro! Tente mais tarde!!"));

        }

        this.setEstado(new Estado());
        this.setEstados((List<Class>) new ArrayList());
    }

    public static EntityManager getEntityManager() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        EntityManager entityManager = (EntityManager) FacesContext.getCurrentInstance().
                getApplication().getELResolver().getValue(elContext, null, "entityManager");
        return entityManager;
    }

    /**
     * @return the estado
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /**
     * @return the estados
     */
    public List<Class> allEstados() {

        return null;
    }

    /**
     * @param estados the estados to set
     */
    public void setEstados(List<Class> estados) {
        this.estados = estados;
    }

    /**
     * @return the estados
     */
    public List<Class> getEstados() {
        
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        this.setEstados(repository.buscaTodos("Estado"));

        return estados;
    }
}
