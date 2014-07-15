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
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import model.Marca;
import repository.GenericRepository;


@ManagedBean
@SessionScoped
public class MarcaController implements Serializable {

    private Marca marca;
    private List<Class> marcas;

    /**
     * Creates a new instance of MarcaController
     */
    public MarcaController() {
        this.marca = new Marca();
        this.marcas = new ArrayList<Class>();
    }

    public void salvar() {
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        try {
            repository.adiciona(this.getMarca());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Marca inserido!!"));

        } catch (Exception e) {
            System.out.println(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro! Tente mais tarde!!"));

        }

        this.setMarca(new Marca());
        this.setMarcas((List<Class>) new ArrayList());
    }
    
     public static EntityManager getEntityManager() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        EntityManager entityManager = (EntityManager) FacesContext.getCurrentInstance().
                getApplication().getELResolver().getValue(elContext, null, "entityManager");
        return entityManager;
    }

    /**
     * @return the marca
     */
    public Marca getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    /**
     * @param marcas the marcas to set
     */
    public void setMarcas(List<Class> marcas) {
        this.marcas = marcas;
    }

    /**
     * @return the marcas
     */
    public List<Class> getMarcas() {
       
            EntityManager manager = this.getEntityManager();
            GenericRepository repository = new GenericRepository(manager);
            this.marcas = repository.buscaTodos("Marca");
        System.out.println(marcas.size()+" size");
        return this.marcas;
    }
}
