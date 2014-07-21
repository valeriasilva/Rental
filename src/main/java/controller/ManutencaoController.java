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
import model.Manutencao;
import model.Veiculo;
import repository.GenericRepository;

@ManagedBean
@SessionScoped
public class ManutencaoController implements Serializable {

    private Manutencao manutencao;
    private List<Class> manutencoes;
    private Veiculo auxVeiculo;
    private String auxSituacao;
    private List<Manutencao> auxManutencoes;

    /**
     * Creates a new instance of ManutencaoController
     */
    public ManutencaoController() {
        this.manutencao = new Manutencao();
        this.manutencoes = new ArrayList<Class>();
    }

    public void limpar() throws IOException {
        this.setManutencao(new Manutencao());
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Rental/oficina/enviarManutencao.xhtml");
    }

    public void newAux() throws IOException {
        this.setAuxVeiculo(null);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Rental/manutencao/novo.xhtml");
    }

    public void salvar() {
        System.out.println("salvar");
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        try {

            this.getManutencao().setDataEntrada(new Date());
            this.getManutencao().setSituacao(SituacaoType.ANDAMENTO);

            repository.adiciona(this.getManutencao());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Enviado para manutenção!!"));

        } catch (Exception e) {
            System.out.println(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro! Tente mais tarde!!"));

        }

        this.setManutencao(new Manutencao());
        this.setManutencoes((List<Class>) new ArrayList());
        this.setAuxVeiculo(null);
    }

    public List<Manutencao> manutencoesAndamento() {
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        return repository.buscaManutencoesAndamento();
    }

    public List<Manutencao> manutencoesFechadas() {
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        return repository.buscaManutencoesFechado();
    }

    public void alterar() {
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        //Definimos aqui a data de Devolução
        this.getManutencao().setDataSaida(new Date());
        //Mudamos a situação para FECHADO
        this.getManutencao().setSituacao(SituacaoType.FECHADO);

        repository.update(this.getManutencao());
        this.setManutencao(new Manutencao());
    }
    /* public void alterar() {
     EntityManager manager = this.getEntityManager();
     GenericRepository repository = new GenericRepository(manager);
     repository.update(this.getManutencao());
     this.setManutencao(new Manutencao());
     }*/

    public void remover() {
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        repository.remove(this.getManutencao());
        this.setManutencao(new Manutencao());
        this.setManutencoes(null);
        this.getManutencoes();
    }

    public static EntityManager getEntityManager() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        EntityManager entityManager = (EntityManager) FacesContext.getCurrentInstance().
                getApplication().getELResolver().getValue(elContext, null, "entityManager");
        return entityManager;
    }

    public void listaPorSituacao() throws IOException {
        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        this.setAuxManutencoes(repository.manutencaoPorSituacao(this.auxSituacao));

        FacesContext.getCurrentInstance().getExternalContext().redirect("/Rental/oficina/listaBuscas.xhtml");

    }

    /**
     * @return the manutencao
     */
    public Manutencao getManutencao() {
        return manutencao;
    }

    /**
     * @param manutencao the manutencao to set
     */
    public void setManutencao(Manutencao manutencao) {
        this.manutencao = manutencao;
    }

    /**
     * @return the manutencoes
     */
    /**
     * @param manutencoes the manutencoes to set
     */
    public void setManutencoes(List<Class> manutencoes) {
        this.manutencoes = manutencoes;
    }

    /**
     * @return the manutencoes
     */
    public List<Class> getManutencoes() {

        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        this.setManutencoes(repository.buscaTodos("Manutencao"));

        return this.manutencoes;
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
     * @return the auxManutencoes
     */
    public List<Manutencao> getAuxManutencoes() {
        return auxManutencoes;
    }

    /**
     * @param auxManutencoes the auxManutencoes to set
     */
    public void setAuxManutencoes(List<Manutencao> auxManutencoes) {
        this.auxManutencoes = auxManutencoes;
    }
}
