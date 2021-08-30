/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.abiti.esystem.egym.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author matia
 */
@Entity
@Table(name = "cuenta_producto")
@NamedQueries({
    @NamedQuery(name = "CuentaProducto.findAll", query = "SELECT c FROM CuentaProducto c"),
    @NamedQuery(name = "CuentaProducto.findByCuentaProducto", query = "SELECT c FROM CuentaProducto c WHERE c.cuentaProducto = :cuentaProducto")})
public class CuentaProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cuenta_producto")
    private Integer cuentaProducto;
    @JoinColumn(name = "cuenta", referencedColumnName = "cuenta")
    @ManyToOne(optional = false)
    private Cuenta cuenta;
    @JoinColumn(name = "producto", referencedColumnName = "producto")
    @ManyToOne(optional = false)
    private Producto producto;

    public CuentaProducto() {
    }

    public CuentaProducto(Integer cuentaProducto) {
        this.cuentaProducto = cuentaProducto;
    }

    public Integer getCuentaProducto() {
        return cuentaProducto;
    }

    public void setCuentaProducto(Integer cuentaProducto) {
        this.cuentaProducto = cuentaProducto;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cuentaProducto != null ? cuentaProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuentaProducto)) {
            return false;
        }
        CuentaProducto other = (CuentaProducto) object;
        if ((this.cuentaProducto == null && other.cuentaProducto != null) || (this.cuentaProducto != null && !this.cuentaProducto.equals(other.cuentaProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.abiti.esystem.egym.model.CuentaProducto[ cuentaProducto=" + cuentaProducto + " ]";
    }
    
}
