/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.abiti.esystem.egym.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author matia
 */
@Entity
@Table(name = "cuenta")
@NamedQueries({
    @NamedQuery(name = "Cuenta.findAll", query = "SELECT c FROM Cuenta c"),
    @NamedQuery(name = "Cuenta.findByCuenta", query = "SELECT c FROM Cuenta c WHERE c.cuenta = :cuenta"),
    @NamedQuery(name = "Cuenta.findByVencimiento", query = "SELECT c FROM Cuenta c WHERE c.vencimiento = :vencimiento"),
    @NamedQuery(name = "Cuenta.findByMatricula", query = "SELECT c FROM Cuenta c WHERE c.matricula = :matricula"),
    @NamedQuery(name = "Cuenta.findByVencAterior", query = "SELECT c FROM Cuenta c WHERE c.vencAterior = :vencAterior"),
    @NamedQuery(name = "Cuenta.findByMontoProducto", query = "SELECT c FROM Cuenta c WHERE c.montoProducto = :montoProducto"),
    @NamedQuery(name = "Cuenta.findByMontoPlan", query = "SELECT c FROM Cuenta c WHERE c.montoPlan = :montoPlan"),
    @NamedQuery(name = "Cuenta.findByMontoTotal", query = "SELECT c FROM Cuenta c WHERE c.montoTotal = :montoTotal")})
public class Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cuenta")
    private Integer cuenta;
    @Basic(optional = false)
    @Column(name = "vencimiento")
    @Temporal(TemporalType.DATE)
    private Date vencimiento;
    @Basic(optional = false)
    @Column(name = "matricula")
    private double matricula;
    @Basic(optional = false)
    @Column(name = "venc_aterior")
    @Temporal(TemporalType.DATE)
    private Date vencAterior;
    @Basic(optional = false)
    @Column(name = "monto_producto")
    private double montoProducto;
    @Basic(optional = false)
    @Column(name = "monto_plan")
    private double montoPlan;
    @Basic(optional = false)
    @Column(name = "monto_total")
    private double montoTotal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuenta")
    private List<PagoCuenta> pagoCuentaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuenta")
    private List<CuentaProducto> cuentaProductoList;
    @JoinColumn(name = "cliente", referencedColumnName = "cliente")
    @ManyToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "plan_pago", referencedColumnName = "plan_pago")
    @ManyToOne(optional = false)
    private PlanPago planPago;

    public Cuenta() {
    }

    public Cuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public Cuenta(Integer cuenta, Date vencimiento, double matricula, Date vencAterior, double montoProducto, double montoPlan, double montoTotal) {
        this.cuenta = cuenta;
        this.vencimiento = vencimiento;
        this.matricula = matricula;
        this.vencAterior = vencAterior;
        this.montoProducto = montoProducto;
        this.montoPlan = montoPlan;
        this.montoTotal = montoTotal;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public double getMatricula() {
        return matricula;
    }

    public void setMatricula(double matricula) {
        this.matricula = matricula;
    }

    public Date getVencAterior() {
        return vencAterior;
    }

    public void setVencAterior(Date vencAterior) {
        this.vencAterior = vencAterior;
    }

    public double getMontoProducto() {
        return montoProducto;
    }

    public void setMontoProducto(double montoProducto) {
        this.montoProducto = montoProducto;
    }

    public double getMontoPlan() {
        return montoPlan;
    }

    public void setMontoPlan(double montoPlan) {
        this.montoPlan = montoPlan;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public List<PagoCuenta> getPagoCuentaList() {
        return pagoCuentaList;
    }

    public void setPagoCuentaList(List<PagoCuenta> pagoCuentaList) {
        this.pagoCuentaList = pagoCuentaList;
    }

    public List<CuentaProducto> getCuentaProductoList() {
        return cuentaProductoList;
    }

    public void setCuentaProductoList(List<CuentaProducto> cuentaProductoList) {
        this.cuentaProductoList = cuentaProductoList;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public PlanPago getPlanPago() {
        return planPago;
    }

    public void setPlanPago(PlanPago planPago) {
        this.planPago = planPago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cuenta != null ? cuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta other = (Cuenta) object;
        if ((this.cuenta == null && other.cuenta != null) || (this.cuenta != null && !this.cuenta.equals(other.cuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.abiti.esystem.egym.model.Cuenta[ cuenta=" + cuenta + " ]";
    }
    
}
