/*
 * Hades Cruise
 * Aplicaciones Distribuidas
 * NRC: 2434 
 * Tutor: HENRY RAMIRO CORAL CORAL 
 * 2017 (c) Hades Cruise Corp.
 */
package ec.edu.espe.distribuidas.hades.web;

import ec.edu.espe.distribuidas.hades.model.Reserva;
import ec.edu.espe.distribuidas.hades.model.TuristaReserva;
import ec.edu.espe.distribuidas.hades.service.ReservaService;
import ec.edu.espe.distribuidas.hades.service.TuristaService;
import ec.edu.espe.distribuidas.hades.web.util.FacesUtil;
import java.io.Serializable;

import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author js_cm
 */
@Named
@ViewScoped
public class TuristaBean extends BaseBean implements Serializable {
    
    private List<TuristaReserva> turistas;
    private List<TuristaReserva> turistaTemp;
    private TuristaReserva turista;
    private Reserva reserva;
    private TuristaReserva turistaSel;
    private List<Reserva> reservas;
    
    @Inject
    private ReservaService reservaService;
    @Inject
    private TuristaService turistaService;

    @PostConstruct
    public void init() {
        this.turistas = this.turistaService.obtenerTodos();
        this.turista = new TuristaReserva();
        this.reservas = this.reservaService.obtenerTodos();
    }


    @Override
    public void agregar() {
        this.turista = new TuristaReserva();
        this.reservas = this.reservaService.obtenerTodos();
        super.agregar();
        
    }

    @Override
    public void modificar() {
        super.modificar();
        this.turista = new TuristaReserva();
        this.turista.setIdentificacion(this.turistaSel.getIdentificacion());
        this.turista.setTipoIdentificacion(this.turistaSel.getTipoIdentificacion());
        this.turista.setNombre(this.turistaSel.getNombre());
        this.turista.setFechaNacimiento(this.turistaSel.getFechaNacimiento());
        this.turista.setPesoMaleta(this.turistaSel.getPesoMaleta());
        this.turista.setReserva(this.turistaSel.getReserva());
        
    }

    @Override
    public void detalles() {
        super.detalles();
        this.turista = this.turistaSel;
    }

    public void cancelar() {
        super.reset();
        this.turista = new TuristaReserva();
        this.reservas = this.reservaService.obtenerTodos();
                
    }

    public void guardar() {
       int a= new Random().nextInt(10000);
       turista.setReserva(retornaReserva(reserva));
       try {
             if (this.enAgregar) {
                this.turistaService.crear(this.turista);
                FacesUtil.addMessageInfo("Se agrego el Turista: " + this.turista.getNombre());
            } else {
                this.turistaService.modificar(this.turista);
                FacesUtil.addMessageInfo("Se modific\u00f3 la Actividad con c\u00f3digo: " + this.turista.getNombre());
            }

        } catch (Exception e) {
            FacesUtil.addMessageError(null, "Ocurrí\u00f3 un error al actualizar la informaci\u00f3n");
        }

        super.reset();
        this.turista = new TuristaReserva();
        this.turistas = this.turistaService.obtenerTodos();
        this.reservas = this.reservaService.obtenerTodos();
    }


    public List<TuristaReserva> getTuristaTemp() {
        return turistaTemp;
    }

    public void setTuristaTemp(List<TuristaReserva> turistaTemp) {
        this.turistaTemp = turistaTemp;
    }
    
    


    public List<TuristaReserva> getTuristas() {
        return turistas;
    }

    public void setTuristas(List<TuristaReserva> turistas) {
        this.turistas = turistas;
    }

    public TuristaReserva getTurista() {
        return turista;
    }

    public void setTurista(TuristaReserva turista) {
        this.turista = turista;
    }

    public TuristaReserva getTuristaSel() {
        return turistaSel;
    }

    public void setTuristaSel(TuristaReserva turistaSel) {
        this.turistaSel = turistaSel;
    }

    public Reserva retornaReserva(Reserva reserva)
    {
        Reserva aux = new Reserva();
        
        for(int i= 0; i<reservas.size();i++)
        {
            aux= reservas.get(i);
            if(aux.getCodigo().equals(reserva.getTipoTour().getCodigo()))
            {
                break;
            }
        }
        return aux;
    }
    
    
}
