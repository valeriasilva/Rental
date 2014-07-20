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
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import model.Locacao;
import model.Manutencao;
import model.Veiculo;
import repository.GenericRepository;

@ManagedBean
@SessionScoped
public class VeiculoController implements Serializable {

    private Veiculo veiculo;
    private List<Class> veiculos;
    private List<Class> modelosFiltrados;
    @Inject
    private ManutencaoController manutencaoController;
    @Inject
    private LocacaoController locacaoController;

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
            this.setModelosFiltrados(repository.listModelosByMarca(this.getVeiculo().getMarca().getId()));

        } catch (Exception e) {
            System.out.println("Exception " + e);
        }
        return this.getModelosFiltrados();
    }

    public List<Veiculo> veiculosIndisponiveis() {
        List<Veiculo> veiculosVinculados = new ArrayList();
        List<Locacao> locacoesAndamento = new ArrayList();
        locacoesAndamento = this.locacaoController.locacoesAndamento();
        List<Manutencao> manutencoesAndamento = new ArrayList();
        manutencoesAndamento = this.manutencaoController.manutencoesAndamento();

        if (locacoesAndamento != null) {
            for (Locacao locacao : locacoesAndamento) {
                veiculosVinculados.add(locacao.getVeiculo());
            }
        } if(manutencoesAndamento != null){
            for(Manutencao manu : manutencoesAndamento){
                veiculosVinculados.add(manu.getVeiculo());
                System.out.println("Manuu "+manu.getVeiculo().getPlaca());
            }
        }
        
        System.out.println("lo, man: "+ locacoesAndamento.size()+", "+ manutencoesAndamento.size());

        return veiculosVinculados;

    }
    
    public boolean verificaSituacao(Veiculo v){
        List<Veiculo> listaV = this.veiculosIndisponiveis();
        if(listaV.contains(v)){
            return false;
        }else
        return true;
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

        EntityManager manager = this.getEntityManager();
        GenericRepository repository = new GenericRepository(manager);
        this.setVeiculos(repository.buscaTodos("Veiculo"));

        return this.veiculos;
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

    /**
     * @return the manutencaoController
     */
    public ManutencaoController getManutencaoController() {
        return manutencaoController;
    }

    /**
     * @param manutencaoController the manutencaoController to set
     */
    public void setManutencaoController(ManutencaoController manutencaoController) {
        this.manutencaoController = manutencaoController;
    }

    /**
     * @return the locacaoController
     */
    public LocacaoController getLocacaoController() {
        return locacaoController;
    }

    /**
     * @param locacaoController the locacaoController to set
     */
    public void setLocacaoController(LocacaoController locacaoController) {
        this.locacaoController = locacaoController;
    }
}
