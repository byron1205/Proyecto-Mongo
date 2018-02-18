/*
 * Hades Cruise
 * Aplicaciones Distribuidas
 * NRC: 2434 
 * Tutor: HENRY RAMIRO CORAL CORAL 
 * 2017 (c) Hades Cruise Corp.
 */
package ec.edu.espe.distribuidas.hades.model;

import ec.edu.espe.distribuidas.hades.enums.EstadoReservaEnum;
import ec.edu.espe.distribuidas.nosql.mongo.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author Hades Cruise Corp.
 */
@Entity(noClassnameStored = true, value = "reserva")
public class Reserva extends BaseEntity{
    
    @Indexed(options = @IndexOptions(name = "reserva_codigoUIdx", unique = true))
    private String codigo;
    private String identificacion;
    private String tipoIdentificacion;
    @Reference
    private Tour tour;
    @Reference
    private TipoTour tipoTour;
    @Reference
    private Crucero crucero;
    @Reference
    private Camarote camarote;
    @Reference
    private TipoAlimentacion tipoAlimentacion;
    @Reference
    private TipoCamarote tipoCamarote;
    @Reference
    private Cliente cliente;
    private BigDecimal valorFinal;
    private Date fechaEmision;
    private EstadoReservaEnum estado;
    
    public Reserva() {
    }

    public String getCodigo() {
        return codigo;
    }

    public TipoAlimentacion getTipoAlimentacion() {
        return tipoAlimentacion;
    }

    public void setTipoAlimentacion(TipoAlimentacion tipoAlimentacion) {
        this.tipoAlimentacion = tipoAlimentacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public TipoTour getTipoTour() {
        return tipoTour;
    }

    public void setTipoTour(TipoTour tipoTour) {
        this.tipoTour = tipoTour;
    }

    public Crucero getCrucero() {
        return crucero;
    }

    public void setCrucero(Crucero crucero) {
        this.crucero = crucero;
    }

    public Camarote getCamarote() {
        return camarote;
    }

    public void setCamarote(Camarote camarote) {
        this.camarote = camarote;
    }

    public TipoCamarote getTipoCamarote() {
        return tipoCamarote;
    }

    public void setTipoCamarote(TipoCamarote tipoCamarote) {
        this.tipoCamarote = tipoCamarote;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public EstadoReservaEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoReservaEnum estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Reserva{" + "codigo=" + codigo + ", identificacion=" + identificacion + ", tipoIdentificacion=" + tipoIdentificacion + ", tour=" + tour + ", tipoTour=" + tipoTour + ", crucero=" + crucero + ", camarote=" + camarote + ", tipoCamarote=" + tipoCamarote + ", valorFinal=" + valorFinal + ", fechaEmision=" + fechaEmision + ", estado=" + estado + '}';
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.id != null ? super.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reserva)) {
            return false;
        }
        Reserva other = (Reserva) object;
        if ((super.id == null && other.id != null) || (super.id != null && !super.id.equals(super.id))) {
            return false;
        }
        return true;
    }
}
