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
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import model.Oficina;
import model.Veiculo;
import repository.GenericRepository;


@ManagedBean
@SessionScoped
public class OficinaController implements Serializable {

    private Oficina oficina;
    private List<Class> oficinas;

    /**
     * Creates a new instance of OficinaController
     */
    public OficinaController() {
        this.oficina = new Oficina();
        this.oficinas = new ArrayList<Class>();
    }

    public void salvar() {
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        try {
            repository.adiciona(this.getOficina());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Oficina inserido!!"));

        } catch (Exception e) {
            System.out.println(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro! Tente mais tarde!!"));

        }

        this.setOficina(new Oficina());
        this.setOficinas((List<Class>) new ArrayList());
    }
    
     public static EntityManager getEntityManager() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        EntityManager entityManager = (EntityManager) FacesContext.getCurrentInstance().
                getApplication().getELResolver().getValue(elContext, null, "entityManager");
        return entityManager;
    }

    /**
     * @return the oficina
     */
    public Oficina getOficina() {
        return oficina;
    }

    /**
     * @param oficina the oficina to set
     */
    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

    /**
     * @param oficinas the oficinas to set
     */
    public void setOficinas(List<Class> oficinas) {
        this.oficinas = oficinas;
    }

    /**
     * @return the oficinas
     */
    public List<Class> getOficinas() {
            EntityManager manager = this.getEntityManager();
            GenericRepository repository = new GenericRepository(manager);
            this.setOficinas(repository.buscaTodos("Oficina"));
        return this.oficinas;
    }
}
