/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.abiti.esystem.egym.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author matia
 */
@Entity
@Table(name = "pago_cuenta")
@NamedQueries({
    @NamedQuery(name = "PagoCuenta.findAll", query = "SELECT p FROM PagoCuenta p"),
    @NamedQuery(name = "PagoCuenta.findByPagoCuenta", query = "SELECT p FROM PagoCuenta p WHERE p.pagoCuenta = :pagoCuenta"),
    @NamedQuery(name = "PagoCuenta.findByFPago", query = "SELECT p FROM PagoCuenta p WHERE p.fPago = :fPago"),
    @NamedQuery(name = "PagoCuenta.findByMontoPagado", query = "SELECT p FROM PagoCuenta p WHERE p.montoPagado = :montoPagado"),
    @NamedQuery(name = "PagoCuenta.findByFormaPago", query = "SELECT p FROM PagoCuenta p WHERE p.formaPago = :formaPago")})
public class PagoCuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pago_cuenta")
    private Integer pagoCuenta;
    @Basic(optional = false)
    @Column(name = "f_pago")
    @Temporal(TemporalType.DATE)
    private Date fPago;
    @Basic(optional = false)
    @Column(name = "monto_pagado")
    private double montoPagado;
    @Basic(optional = false)
    @Column(name = "forma_pago")
    private int formaPago;
    @JoinColumn(name = "cuenta", referencedColumnName = "cuenta")
    @ManyToOne(optional = false)
    private Cuenta cuenta;

    public PagoCuenta() {
    }

    public PagoCuenta(Integer pagoCuenta) {
        this.pagoCuenta = pagoCuenta;
    }

    public PagoCuenta(Integer pagoCuenta, Date fPago, double montoPagado, int formaPago) {
        this.pagoCuenta = pagoCuenta;
        this.fPago = fPago;
        this.montoPagado = montoPagado;
        this.formaPago = formaPago;
    }

    public Integer getPagoCuenta() {
        return pagoCuenta;
    }

    public void setPagoCuenta(Integer pagoCuenta) {
        this.pagoCuenta = pagoCuenta;
    }

    public Date getFPago() {
        return fPago;
    }

    public void setFPago(Date fPago) {
        this.fPago = fPago;
    }

    public double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public int getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(int formaPago) {
        this.formaPago = formaPago;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pagoCuenta != null ? pagoCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagoCuenta)) {
            return false;
        }
        PagoCuenta other = (PagoCuenta) object;
        if ((this.pagoCuenta == null && other.pagoCuenta != null) || (this.pagoCuenta != null && !this.pagoCuenta.equals(other.pagoCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.abiti.esystem.egym.model.PagoCuenta[ pagoCuenta=" + pagoCuenta + " ]";
    }
    
}
