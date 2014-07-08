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
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import model.Cliente;
import repository.GenericRepository;

@ManagedBean
@SessionScoped
public class ClienteController implements Serializable {

    private Cliente cliente;
    private List<Class> clientes;

    /**
     * Creates a new instance of ClienteController
     */
    public ClienteController() {
        this.cliente = new Cliente();
        this.clientes = new ArrayList<Class>();
    }

    public void salvar() {
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        try {
            repository.adiciona(this.getCliente());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Cliente inserido!!"));

        } catch (Exception e) {
            System.out.println(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro! Tente mais tarde!!"));

        }

        this.setCliente(new Cliente());
        this.setClientes((List<Class>) new ArrayList());
    }
    
      public void alterar() {
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        repository.update(this.cliente);
        this.setCliente(new Cliente());
    }

    public void remover() {
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        repository.remove(this.cliente);
        this.cliente = new Cliente();
        this.clientes = null;
        this.getClientes();
    }

    public static EntityManager getEntityManager() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        EntityManager entityManager = (EntityManager) FacesContext.getCurrentInstance().
                getApplication().getELResolver().getValue(elContext, null, "entityManager");
        return entityManager;
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the clientes
     */
    /**
     * @param clientes the clientes to set
     */
    public void setClientes(List<Class> clientes) {
        this.clientes = clientes;
    }

    /**
     * @return the clientes
     */
    public List<Class> getClientes() {

        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        this.setClientes(repository.buscaTodos("Cliente"));

        return this.clientes;
    }
}
