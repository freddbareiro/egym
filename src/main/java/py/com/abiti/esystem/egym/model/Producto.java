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
@Table(name = "producto")
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByDescripcion", query = "SELECT p FROM Producto p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Producto.findByCosto", query = "SELECT p FROM Producto p WHERE p.costo = :costo"),
    @NamedQuery(name = "Producto.findByProducto", query = "SELECT p FROM Producto p WHERE p.producto = :producto")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "costo")
    private double costo;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "producto")
    private Integer producto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private List<CuentaProducto> cuentaProductoList;

    public Producto() {
    }

    public Producto(Integer producto) {
        this.producto = producto;
    }

    public Producto(Integer producto, String descripcion, double costo) {
        this.producto = producto;
        this.descripcion = descripcion;
        this.costo = costo;
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

    public Integer getProducto() {
        return producto;
    }

    public void setProducto(Integer producto) {
        this.producto = producto;
    }

    public List<CuentaProducto> getCuentaProductoList() {
        return cuentaProductoList;
    }

    public void setCuentaProductoList(List<CuentaProducto> cuentaProductoList) {
        this.cuentaProductoList = cuentaProductoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (producto != null ? producto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.producto == null && other.producto != null) || (this.producto != null && !this.producto.equals(other.producto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.abiti.esystem.egym.model.Producto[ producto=" + producto + " ]";
    }
    
}
