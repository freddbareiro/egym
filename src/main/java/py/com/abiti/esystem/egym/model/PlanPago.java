/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.abiti.esystem.egym.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author matia
 */
@Entity
@Table(name = "plan_pago")
@NamedQueries({
    @NamedQuery(name = "PlanPago.findAll", query = "SELECT p FROM PlanPago p"),
    @NamedQuery(name = "PlanPago.findByPlanPago", query = "SELECT p FROM PlanPago p WHERE p.planPago = :planPago"),
    @NamedQuery(name = "PlanPago.findByDescripcion", query = "SELECT p FROM PlanPago p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "PlanPago.findByCosto", query = "SELECT p FROM PlanPago p WHERE p.costo = :costo")})
public class PlanPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "plan_pago")
    private Integer planPago;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "costo")
    private double costo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planPago")
    private List<Cuenta> cuentaList;

    public PlanPago() {
    }

    public PlanPago(Integer planPago) {
        this.planPago = planPago;
    }

    public PlanPago(Integer planPago, String descripcion, double costo) {
        this.planPago = planPago;
        this.descripcion = descripcion;
        this.costo = costo;
    }

    public Integer getPlanPago() {
        return planPago;
    }

    public void setPlanPago(Integer planPago) {
        this.planPago = planPago;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public List<Cuenta> getCuentaList() {
        return cuentaList;
    }

    public void setCuentaList(List<Cuenta> cuentaList) {
        this.cuentaList = cuentaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planPago != null ? planPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanPago)) {
            return false;
        }
        PlanPago other = (PlanPago) object;
        if ((this.planPago == null && other.planPago != null) || (this.planPago != null && !this.planPago.equals(other.planPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.abiti.esystem.egym.model.PlanPago[ planPago=" + planPago + " ]";
    }
    
}
