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
@Table(name = "cliente")
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByCliente", query = "SELECT c FROM Cliente c WHERE c.cliente = :cliente"),
    @NamedQuery(name = "Cliente.findByFInicio", query = "SELECT c FROM Cliente c WHERE c.fInicio = :fInicio"),
    @NamedQuery(name = "Cliente.findByMatricula", query = "SELECT c FROM Cliente c WHERE c.matricula = :matricula"),
    @NamedQuery(name = "Cliente.findByHabilitado", query = "SELECT c FROM Cliente c WHERE c.habilitado = :habilitado"),
    @NamedQuery(name = "Cliente.findByActivo", query = "SELECT c FROM Cliente c WHERE c.activo = :activo")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cliente")
    private Integer cliente;
    @Basic(optional = false)
    @Column(name = "f_inicio")
    @Temporal(TemporalType.DATE)
    private Date fInicio;
    @Basic(optional = false)
    @Column(name = "matricula")
    private boolean matricula;
    @Basic(optional = false)
    @Column(name = "habilitado")
    private boolean habilitado;
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    @JoinColumn(name = "persona", referencedColumnName = "persona")
    @ManyToOne(optional = false)
    private Persona persona;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Cuenta> cuentaList;

    public Cliente() {
    }

    public Cliente(Integer cliente) {
        this.cliente = cliente;
    }

    public Cliente(Integer cliente, Date fInicio, boolean matricula, boolean habilitado, boolean activo) {
        this.cliente = cliente;
        this.fInicio = fInicio;
        this.matricula = matricula;
        this.habilitado = habilitado;
        this.activo = activo;
    }

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public Date getFInicio() {
        return fInicio;
    }

    public void setFInicio(Date fInicio) {
        this.fInicio = fInicio;
    }

    public boolean getMatricula() {
        return matricula;
    }

    public void setMatricula(boolean matricula) {
        this.matricula = matricula;
    }

    public boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
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
        hash += (cliente != null ? cliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.cliente == null && other.cliente != null) || (this.cliente != null && !this.cliente.equals(other.cliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.abiti.esystem.egym.model.Cliente[ cliente=" + cliente + " ]";
    }
    
}
