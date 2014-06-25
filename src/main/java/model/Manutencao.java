/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author labin05
 */
@Entity
public class Manutencao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataEntrada;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataSaida;
    private String defeitoReclamado;
    @ManyToOne
    private Veiculo veiculo;
    @ManyToOne
    private Oficina oficina;
    private Float valor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Manutencao)) {
            return false;
        }
        Manutencao other = (Manutencao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Manutencao[ id=" + id + " ]";
    }
    
}
