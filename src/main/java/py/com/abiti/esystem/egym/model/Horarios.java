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
@Table(name = "horarios")
@NamedQueries({
    @NamedQuery(name = "Horarios.findAll", query = "SELECT h FROM Horarios h"),
    @NamedQuery(name = "Horarios.findByHorario", query = "SELECT h FROM Horarios h WHERE h.horario = :horario"),
    @NamedQuery(name = "Horarios.findByFecha", query = "SELECT h FROM Horarios h WHERE h.fecha = :fecha"),
    @NamedQuery(name = "Horarios.findByDia", query = "SELECT h FROM Horarios h WHERE h.dia = :dia"),
    @NamedQuery(name = "Horarios.findByHoraEntrada", query = "SELECT h FROM Horarios h WHERE h.horaEntrada = :horaEntrada"),
    @NamedQuery(name = "Horarios.findByHoraSalida", query = "SELECT h FROM Horarios h WHERE h.horaSalida = :horaSalida")})
public class Horarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "horario")
    private Integer horario;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "dia")
    private int dia;
    @Basic(optional = false)
    @Column(name = "hora entrada")
    @Temporal(TemporalType.DATE)
    private Date horaEntrada;
    @Basic(optional = false)
    @Column(name = "hora salida")
    @Temporal(TemporalType.DATE)
    private Date horaSalida;
    @JoinColumn(name = "funcionario", referencedColumnName = "funcionario")
    @ManyToOne(optional = false)
    private Funcionario funcionario;

    public Horarios() {
    }

    public Horarios(Integer horario) {
        this.horario = horario;
    }

    public Horarios(Integer horario, Date fecha, int dia, Date horaEntrada, Date horaSalida) {
        this.horario = horario;
        this.fecha = fecha;
        this.dia = dia;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }

    public Integer getHorario() {
        return horario;
    }

    public void setHorario(Integer horario) {
        this.horario = horario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public Date getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Date horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Date getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Date horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (horario != null ? horario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Horarios)) {
            return false;
        }
        Horarios other = (Horarios) object;
        if ((this.horario == null && other.horario != null) || (this.horario != null && !this.horario.equals(other.horario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.abiti.esystem.egym.model.Horarios[ horario=" + horario + " ]";
    }
    
}
