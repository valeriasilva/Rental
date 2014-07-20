/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Enum.SituacaoType;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import model.Locacao;
import model.Veiculo;
import repository.GenericRepository;

@ManagedBean
@SessionScoped
public class LocacaoController implements Serializable {

    private Locacao locacao;
    private List<Class> locacoes;
    private Veiculo auxVeiculo;

    /**
     * Creates a new instance of LocacaoController
     */
    public LocacaoController() {
        this.locacao = new Locacao();
        this.locacoes = new ArrayList<Class>();
    }

    public void limpar() throws IOException {
        this.setLocacao(new Locacao());
        FacesContext.getCurrentInstance().getExternalContext().redirect("locacao/novo.xhtml");
    }

    public void newAux() throws IOException {
        this.auxVeiculo = null;
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Rental/locacao/novo.xhtml");
    }

    public void salvar() {

        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        try {
            this.getLocacao().setVeiculo(getAuxVeiculo());
            this.getLocacao().setDataLocacao(new Date());
            this.getLocacao().setSituacao(SituacaoType.ANDAMENTO);

            repository.adiciona(this.getLocacao());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Locação realizada!!"));

        } catch (Exception e) {
            System.out.println(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro! Tente mais tarde!!"));

        }

        this.setLocacao(new Locacao());
        this.setLocacoes((List<Class>) new ArrayList());
        this.auxVeiculo = null;
    }

    public void alterar() {
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        repository.update(this.getLocacao());
        this.setLocacao(new Locacao());
    }

    public void remover() {
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        repository.remove(this.getLocacao());
        this.setLocacao(new Locacao());
        this.setLocacoes(null);
        this.getLocacoes();
    }

    public void calcularValorFinal() {
        Float desconto = 0F;
        desconto = (this.locacao.getValor() * this.locacao.getDesconto()) / 100;
        this.getLocacao().setValorTotal(this.locacao.getValor() - desconto);
        System.out.println(this.getLocacao().getValorTotal());

    }

    public static EntityManager getEntityManager() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        EntityManager entityManager = (EntityManager) FacesContext.getCurrentInstance().
                getApplication().getELResolver().getValue(elContext, null, "entityManager");
        return entityManager;
    }
    
     public List<Locacao> locacoesFechadas(){
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        
        return repository.buscaLocacoesFechado();     
    }
     
       public List<Locacao> locacoesAndamento(){
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        
        return repository.buscaLocacoesAndamento();     
    }

    /**
     * @return the locacao
     */
    public Locacao getLocacao() {
        return locacao;
    }

    /**
     * @param locacao the locacao to set
     */
    public void setLocacao(Locacao locacao) {
        this.locacao = locacao;
    }

    /**
     * @return the locacoes
     */
    /**
     * @param locacoes the locacoes to set
     */
    public void setLocacoes(List<Class> locacoes) {
        this.locacoes = locacoes;
    }

    /**
     * @return the locacoes
     */
    public List<Class> getLocacoes() {

        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        this.setLocacoes(repository.buscaTodos("Locacao"));

        return this.locacoes;
    }

    /**
     * @return the auxVeiculo
     */
    public Veiculo getAuxVeiculo() {
        return auxVeiculo;
    }

    /**
     * @param auxVeiculo the auxVeiculo to set
     */
    public void setAuxVeiculo(Veiculo auxVeiculo) {
        this.auxVeiculo = auxVeiculo;
    }
}
