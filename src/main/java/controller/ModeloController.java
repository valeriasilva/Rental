/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.el.ELContext;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import model.Marca;
import model.Modelo;
import repository.GenericRepository;


@ManagedBean
@RequestScoped
public class ModeloController implements Serializable {

    private Modelo modelo;
    private List<Class> modelos;

    /**
     * Creates a new instance of ModeloController
     */
    public ModeloController() {
        this.modelo = new Modelo();
        this.modelos = new ArrayList<Class>();
    }

    public void salvar() {
        System.out.println("tt");
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        try {
            repository.adiciona(this.getModelo());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Modelo inserido!!"));

        } catch (Exception e) {
            System.out.println(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro! Tente mais tarde!!"));

        }

        this.setModelo(new Modelo());
        this.setModelos((List<Class>) new ArrayList());
    }
    
     private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute("entityManager");

        return manager;
    }
    /**
     * @return the modelo
     */
    public Modelo getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the modelos
     */
    public List<Class> allModelos() {
        if (this.getModelos() == null) {
            EntityManager manager = this.getEntityManager();
            GenericRepository repository = new GenericRepository(manager);
            this.setModelos(repository.buscaTodos("Modelo"));
        }
        return this.getModelos();
    }

    /**
     * @param modelos the modelos to set
     */
    public void setModelos(List<Class> modelos) {
        this.modelos = modelos;
    }

    /**
     * @return the modelos
     */
    public List<Class> getModelos() {
        return modelos;
    }
}
