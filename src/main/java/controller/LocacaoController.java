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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import model.Cliente;
import model.Funcionario;
import model.Locacao;
import model.Veiculo;
import repository.GenericRepository;

@ManagedBean
@SessionScoped
public class LocacaoController implements Serializable {

    private Locacao locacao;
    private List<Class> locacoes;
    private Veiculo auxVeiculo;
    private Cliente auxCliente;
    private List<Locacao> locacoesCliente;
    private String auxSituacao = "";
    private Funcionario auxFuncionario = null;
    private List<Locacao> locacoesAux;

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
        this.setAuxVeiculo(null);
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
        this.setAuxVeiculo(null);
    }

    public void alterar() {
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        //Definimos aqui a data de Devolução
        this.getLocacao().setDataDevolucao(new Date());
        //Mudamos a situação para FECHADO
        this.getLocacao().setSituacao(SituacaoType.FECHADO);

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

    /**
     * Método para Calcular o desconto da Locaçao
     */
    public void calcularValorFinal() {
        Float desconto = 0F;
        desconto = (this.getLocacao().getValor() * this.getLocacao().getDesconto()) / 100;
        this.getLocacao().setValorTotal(this.getLocacao().getValor() - desconto);
        System.out.println(this.getLocacao().getValorTotal());

    }

    public static EntityManager getEntityManager() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        EntityManager entityManager = (EntityManager) FacesContext.getCurrentInstance().
                getApplication().getELResolver().getValue(elContext, null, "entityManager");
        return entityManager;
    }

    public List<Locacao> locacoesFechadas() {
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);

        return repository.buscaLocacoesFechado();
    }

    public List<Locacao> locacoesAndamento() {
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);

        return repository.buscaLocacoesAndamento();
    }

    public void locacoesPeloCliente() throws IOException {
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);

        this.setLocacoesCliente(repository.buscaLocacoesPeloCliente(this.getAuxCliente().getId()));

        FacesContext.getCurrentInstance().getExternalContext().redirect("/Rental/locacao/listaPeloCliente.xhtml");
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

    /**
     * @return the auxCliente
     */
    public Cliente getAuxCliente() {
        return auxCliente;
    }

    /**
     * @param auxCliente the auxCliente to set
     */
    public void setAuxCliente(Cliente auxCliente) {
        this.auxCliente = auxCliente;
    }

    /**
     * @return the locacoesCliente
     */
    public List<Locacao> getLocacoesCliente() {
        return locacoesCliente;
    }

    /**
     * @param locacoesCliente the locacoesCliente to set
     */
    public void setLocacoesCliente(List<Locacao> locacoesCliente) {
        this.locacoesCliente = locacoesCliente;
    }

    /**
     * @return the auxFuncionario
     */
    public Funcionario getAuxFuncionario() {
        return auxFuncionario;
    }

    /**
     * @param auxFuncionario the auxFuncionario to set
     */
    public void setAuxFuncionario(Funcionario auxFuncionario) {
        this.auxFuncionario = auxFuncionario;
    }

    /**
     * @return the auxSituacao
     */
    public String getAuxSituacao() {
        return auxSituacao;
    }

    /**
     * @param auxSituacao the auxSituacao to set
     */
    public void setAuxSituacao(String auxSituacao) {
        this.auxSituacao = auxSituacao;
    }

    /**
     * @return the locacoesAux
     */
    public List<Locacao> getLocacoesAux() {
        return locacoesAux;
    }

    /**
     * @param locacoesAux the locacoesAux to set
     */
    public void setLocacoesAux(List<Locacao> locacoesAux) {
        this.locacoesAux = locacoesAux;
    }
}
