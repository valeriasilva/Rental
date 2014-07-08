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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import model.Veiculo;
import repository.GenericRepository;

@ManagedBean
@SessionScoped
public class VeiculoController implements Serializable {

    private Veiculo veiculo;
    private List<Class> veiculos;
    private List<Class> modelosFiltrados;

    /**
     * Creates a new instance of VeiculoController
     */
    public VeiculoController() {
        this.veiculo = new Veiculo();
        this.veiculos = new ArrayList<Class>();
    }

    public void salvar() {
        System.out.println("entrou");
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        try {
            repository.adiciona(this.getVeiculo());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Veiculo inserido!!"));

        } catch (Exception e) {
            System.out.println(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro! Tente mais tarde!!"));

        }

        this.setVeiculo(new Veiculo());
        this.setVeiculos((List<Class>) new ArrayList());
    }

    public List<Class> modelosByMarca(AjaxBehaviorEvent event) {

        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        try {
            this.modelosFiltrados = (repository.listModelosByMarca(this.veiculo.getMarca().getId()));

        } catch (Exception e) {
            System.out.println("Exception " + e);
        }
        return this.modelosFiltrados;
    }

    /**
     * @return the veiculo
     */
    public Veiculo getVeiculo() {
        return veiculo;
    }

    /**
     * @param veiculo the veiculo to set
     */
    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    /**
     * @return the veiculos
     */
    public List<Class> allVeiculos() {
        if (this.getVeiculos() == null) {
            EntityManager manager = this.getEntityManager();
            GenericRepository repository = new GenericRepository(manager);
            this.setVeiculos(repository.buscaTodos("Veiculo"));
        }
        return this.getVeiculos();
    }
    
     private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute("entityManager");

        return manager;
    }

    /**
     * @param veiculos the veiculos to set
     */
    public void setVeiculos(List<Class> veiculos) {
        this.veiculos = veiculos;
    }

    /**
     * @return the veiculos
     */
    public List<Class> getVeiculos() {
        return veiculos;
    }

    /**
     * @return the modelosFiltrados
     */
    public List<Class> getModelosFiltrados() {
        return modelosFiltrados;
    }

    /**
     * @param modelosFiltrados the modelosFiltrados to set
     */
    public void setModelosFiltrados(List<Class> modelosFiltrados) {
        this.modelosFiltrados = modelosFiltrados;
    }
}
