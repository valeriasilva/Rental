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
import model.Cidade;
import repository.GenericRepository;


@ManagedBean
@RequestScoped
public class CidadeController implements Serializable {

    private Cidade cidade;
    private List<Class> cidades;

    /**
     * Creates a new instance of CidadeController
     */
    public CidadeController() {
        this.cidade = new Cidade();
        this.cidades = new ArrayList<Class>();
    }

    public void salvar() {
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        try {
            repository.adiciona(this.getCidade());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Cidade inserido!!"));

        } catch (Exception e) {
            System.out.println(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro! Tente mais tarde!!"));

        }

        this.setCidade(new Cidade());
        this.setCidades((List<Class>) new ArrayList());
    }
    
     public static EntityManager getEntityManager() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        EntityManager entityManager = (EntityManager) FacesContext.getCurrentInstance().
                getApplication().getELResolver().getValue(elContext, null, "entityManager");
        return entityManager;
    }

    /**
     * @return the cidade
     */
    public Cidade getCidade() {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    /**
     * @return the cidades
     */
    public List<Class> allCidades() {
        if (this.getCidades() == null) {
            EntityManager manager = this.getEntityManager();
            GenericRepository repository = new GenericRepository(manager);
            this.setCidades(repository.buscaTodos("Cidade"));
        }
        return this.getCidades();
    }

    /**
     * @param cidades the cidades to set
     */
    public void setCidades(List<Class> cidades) {
        this.cidades = cidades;
    }

    /**
     * @return the cidades
     */
    public List<Class> getCidades() {
        return cidades;
    }
}
