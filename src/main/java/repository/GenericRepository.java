/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.Cliente;

public class GenericRepository {

    private EntityManager manager;

    public GenericRepository(EntityManager manager) {
        this.manager = manager;
    }

    public void adiciona(Object obj) {
       this.manager.persist(obj);
    }

    public List<Class> buscaTodos(String table) {
        System.out.println("marcas");
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

}
