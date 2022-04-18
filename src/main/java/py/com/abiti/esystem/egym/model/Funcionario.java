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
@Table(name = "funcionario")
@NamedQueries({
    @NamedQuery(name = "Funcionario.findAll", query = "SELECT f FROM Funcionario f"),
    @NamedQuery(name = "Funcionario.findByFuncionario", query = "SELECT f FROM Funcionario f WHERE f.funcionario = :funcionario"),
    @NamedQuery(name = "Funcionario.findByPersona", query = "SELECT f FROM Funcionario f WHERE f.persona = :persona"),
    @NamedQuery(name = "Funcionario.findByFechaInicio", query = "SELECT f FROM Funcionario f WHERE f.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Funcionario.findBySalario", query = "SELECT f FROM Funcionario f WHERE f.salario = :salario")})
public class Funcionario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "funcionario")
    private Integer funcionario;
    @Basic(optional = false)
    @Column(name = "persona")
    private int persona;
    @Basic(optional = false)
    @Column(name = "fecha inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "salario")
    private double salario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionario")
    private List<Horarios> horariosList;
    @JoinColumn(name = "cargo", referencedColumnName = "cargo")
    @ManyToOne(optional = false)
    private Cargo cargo;

    public Funcionario() {
    }

    public Funcionario(Integer funcionario) {
        this.funcionario = funcionario;
    }

    public Funcionario(Integer funcionario, int persona, Date fechaInicio, double salario) {
        this.funcionario = funcionario;
        this.persona = persona;
        this.fechaInicio = fechaInicio;
        this.salario = salario;
    }

    public Integer getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Integer funcionario) {
        this.funcionario = funcionario;
    }

    public int getPersona() {
        return persona;
    }

    public void setPersona(int persona) {
        this.persona = persona;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public List<Horarios> getHorariosList() {
        return horariosList;
    }

    public void setHorariosList(List<Horarios> horariosList) {
        this.horariosList = horariosList;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (funcionario != null ? funcionario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Funcionario)) {
            return false;
        }
        Funcionario other = (Funcionario) object;
        if ((this.funcionario == null && other.funcionario != null) || (this.funcionario != null && !this.funcionario.equals(other.funcionario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.abiti.esystem.egym.model.Funcionario[ funcionario=" + funcionario + " ]";
    }
    
}
