/*
 * Hades Cruise
 * Aplicaciones Distribuidas
 * NRC: 2434 
 * Tutor: HENRY RAMIRO CORAL CORAL 
 * 2017 (c) Hades Cruise Corp.
 */
package ec.edu.espe.distribuidas.hades.web;

import ec.edu.espe.distribuidas.hades.enums.MenuEnum;
import ec.edu.espe.distribuidas.hades.model.Menu;
import ec.edu.espe.distribuidas.hades.model.Reserva;
import ec.edu.espe.distribuidas.hades.model.TipoTour;
import ec.edu.espe.distribuidas.hades.model.TuristaReserva;
import ec.edu.espe.distribuidas.hades.service.MenuService;
import ec.edu.espe.distribuidas.hades.service.ReservaService;
import ec.edu.espe.distribuidas.hades.service.TipoTourService;
import ec.edu.espe.distribuidas.hades.service.TuristaService;
import ec.edu.espe.distribuidas.hades.web.util.FacesUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Hades Cruise Corp.
 */
@Named
@ViewScoped
public class CheckinBean extends BaseBean implements Serializable {

    private List<TuristaReserva> listaTurista;
    private TipoTour tipoTour;
    private Reserva reserva;
    
    private String codReserva;
    private Double maximo;
    private Double limite;
    
    @Inject
    private TuristaService turistaService;
    
    @Inject
    private ReservaService reservaService;
    
    @Inject
    private TipoTourService tipoTourService;

    @PostConstruct
    public void init() {
    }

    public BigDecimal calcularValor(BigDecimal dato){
        
        Double peso = Double.parseDouble(dato.toString());
        
        Double diferencia = 0.0;
        Double valor = 0.0;
        
        if(peso<=this.limite){
            valor=0.0;
        }else{
            diferencia = peso - this.limite;
            valor=diferencia*10;
        }
        
        System.out.println(valor);
        
        return BigDecimal.valueOf(valor);
        
    }
    
    public List<TuristaReserva> getListaTurista() {
        return listaTurista;
    }

    public void buscar(){
        this.reserva = this.reservaService.obtenerPorIdentificacion(this.codReserva); 
        this.listaTurista = this.turistaService.obtenerTodos(Integer.parseInt(this.reserva.getCodigo()));
        this.tipoTour = this.tipoTourService.obtenerPorCodigo("001");
        
        if(this.tipoTour.getNombre().equals("Aventura Mágica")){
            this.limite = 25.0;    
        }else{
            this.limite = 32.0;
        }
        
        this.maximo = this.limite + 10.0; 
    }

    public void guardar(TuristaReserva turista) {
        this.turistaService.modificar(turista);
    }

    public Double getMaximo() {
        return maximo;
    }

    public void setMaximo(Double maximo) {
        this.maximo = maximo;
    }

    public String getCodReserva() {
        return codReserva;
    }

    public void setCodReserva(String codReserva) {
        this.codReserva = codReserva;
    }

    public TipoTour getTipoTour() {
        return tipoTour;
    }
    
    
}
