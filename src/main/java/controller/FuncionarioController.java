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
import model.Funcionario;
import repository.GenericRepository;


@ManagedBean
@SessionScoped
public class FuncionarioController implements Serializable {

    private Funcionario funcionario;
    private List<Class> funcionarios;

    /**
     * Creates a new instance of FuncionarioController
     */
    public FuncionarioController() {
        this.funcionario = new Funcionario();
        this.funcionarios = new ArrayList<Class>();
    }

    public void salvar() {
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        try {
            repository.adiciona(this.getFuncionario());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Funcionario inserido!!"));

        } catch (Exception e) {
            System.out.println(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro! Tente mais tarde!!"));

        }

        this.setFuncionario(new Funcionario());
        this.setFuncionarios((List<Class>) new ArrayList());
    }
         public void alterar() {
             System.out.println(this.funcionario.getNome());
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        repository.update(this.funcionario);
        this.setFuncionario(new Funcionario());
    }
         public void remover() {
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        repository.remove(this.funcionario);
        this.funcionario = new Funcionario();
        this.funcionarios = null;
        this.getFuncionarios();
    }
    
    public static EntityManager getEntityManager() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        EntityManager entityManager = (EntityManager) FacesContext.getCurrentInstance().
                getApplication().getELResolver().getValue(elContext, null, "entityManager");
        return entityManager;
    }

   
    public List<Class> allFuncionarios() {
        if (this.getFuncionarios() == null) {
            EntityManager manager = this.getEntityManager();
            GenericRepository repository = new GenericRepository(manager);
            this.setFuncionarios(repository.buscaTodos("Funcionario"));
        }
        return this.getFuncionarios();
    }

        /**
     * @return the funcionario
     */
    public Funcionario getFuncionario() {
        return funcionario;
    }

    /**
     * @param funcionario the funcionario to set
     */
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    /**
     * @return the funcionarios
     */
    /**
     * @param funcionarios the funcionarios to set
     */
    public void setFuncionarios(List<Class> funcionarios) {
        this.funcionarios = funcionarios;
    }

    /**
     * @return the funcionarios
     */
    public List<Class> getFuncionarios() {

        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        this.setFuncionarios(repository.buscaTodos("Funcionario"));

        return this.funcionarios;
    }
}
