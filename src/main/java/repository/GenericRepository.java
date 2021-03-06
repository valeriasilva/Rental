/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.Locacao;
import model.Manutencao;
import model.Veiculo;

public class GenericRepository {

    private EntityManager manager;

    public GenericRepository(EntityManager manager) {
        this.manager = manager;
    }

    public void adiciona(Object obj) {
        this.manager.persist(obj);
    }

    public List<Class> buscaTodos(String table) {
        Query query = this.manager.createQuery(" select x from " + table + " x");
        List<Class> listResult = query.getResultList();
        return listResult;
    }

    public List<Class> listModelosByMarca(Long idMarca) {
        Query query = this.manager.createQuery(" select x from Modelo x where x.marca.id = " + idMarca);
        List<Class> listResult = query.getResultList();
        return listResult;
    }

    public boolean remove(Object obj) {
        try {
            this.manager.remove(this.manager.merge(obj));
            this.manager.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void update(Object obj) {
        this.manager.merge(obj);
        // this.manager.
    }

    public List<Manutencao> buscaManutencoesAndamento() {

        Query query = this.manager.createQuery(" select x from Manutencao x where x.situacao like '%ANDAMENTO%'");
        List<Manutencao> listResult = query.getResultList();
        return listResult;
    }

    public List<Manutencao> buscaManutencoesFechado() {

        Query query = this.manager.createQuery(" select x from Manutencao x where x.situacao like '%FECHADO%'");
        List<Manutencao> listResult = query.getResultList();
        return listResult;
    }

    public List<Locacao> buscaLocacoesAndamento() {

        Query query = this.manager.createQuery(" select x from Locacao x where x.situacao like '%ANDAMENTO%'");
        List<Locacao> listResult = query.getResultList();
        return listResult;
    }

    public List<Locacao> buscaLocacoesFechado() {

        Query query = this.manager.createQuery(" select x from Locacao x where x.situacao like '%FECHADO%'");
        List<Locacao> listResult = query.getResultList();
        return listResult;
    }

    public List<Locacao> buscaLocacoesPeloCliente(Long id) {
        Query query = this.manager.createQuery(" select x from Locacao x where x.cliente.id = " + id);
        List<Locacao> listResult = query.getResultList();
        return listResult;
    }

    public List<Veiculo> buscaPorModelo(Long id) {
        Query query = this.manager.createQuery(" select x from Veiculo x where x.modelo.id = " + id);
        List<Veiculo> listResult = query.getResultList();
        return listResult;
    }

    public List<Manutencao> manutencaoPorSituacao(String auxSituacao) {
        Query query = this.manager.createQuery(" select x from Manutencao x where x.situacao like '%" + auxSituacao + "%'");
        List<Manutencao> listResult = query.getResultList();
        return listResult;
    }

    public List<Locacao> locacaoPorSituacao(String auxSituacao) {
        Query query = this.manager.createQuery(" select x from Locacao x where x.situacao like '%" + auxSituacao + "%'");
        List<Locacao> listResult = query.getResultList();
        return listResult;
    }

    public List<Locacao> locacaoPorFuncionario(Long id) {
        Query query = this.manager.createQuery(" select x from Locacao x where x.funcionario.id = " + id);
        List<Locacao> listResult = query.getResultList();
        return listResult;
    }

    public List<Locacao> locacaoPorVeiculo(Long id) {
        Query query = this.manager.createQuery(" select x from Locacao x where x.veiculo.id = " + id);
        List<Locacao> listResult = query.getResultList();
        return listResult;
    }
}