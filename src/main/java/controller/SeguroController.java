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
import model.Seguro;
import repository.GenericRepository;

@ManagedBean
@RequestScoped
public class SeguroController implements Serializable {

    private Seguro seguro;
    private List<Class> seguros;

    /**
     * Creates a new instance of SeguroController
     */
    public SeguroController() {
        this.seguro = new Seguro();
        this.seguros = new ArrayList<Class>();
    }

    public void salvar() {
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        try {
            repository.adiciona(this.getSeguro());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Seguro inserido!!"));

        } catch (Exception e) {
            System.out.println(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro! Tente mais tarde!!"));

        }

        this.setSeguro(new Seguro());
        this.setSeguros((List<Class>) new ArrayList());
    }

    public static EntityManager getEntityManager() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        EntityManager entityManager = (EntityManager) FacesContext.getCurrentInstance().
                getApplication().getELResolver().getValue(elContext, null, "entityManager");
        return entityManager;
    }

    /**
     * @return the seguro
     */
    public Seguro getSeguro() {
        return seguro;
    }

    /**
     * @param seguro the seguro to set
     */
    public void setSeguro(Seguro seguro) {
        this.seguro = seguro;
    }

    /**
     * @return the seguros
     */
    public List<Class> allSeguros() {

        return null;
    }

    /**
     * @param seguros the seguros to set
     */
    public void setSeguros(List<Class> seguros) {
        this.seguros = seguros;
    }

    /**
     * @return the seguros
     */
    public List<Class> getSeguros() {
        
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        this.setSeguros(repository.buscaTodos("Seguro"));

        return seguros;
    }
}
