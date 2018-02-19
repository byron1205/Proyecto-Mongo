/*
 * Hades Cruise
 * Aplicaciones Distribuidas
 * NRC: 2434 
 * Tutor: HENRY RAMIRO CORAL CORAL 
 * 2017 (c) Hades Cruise Corp.
 */
package ec.edu.espe.distribuidas.hades.web;

import ec.edu.espe.distribuidas.hades.model.Camarote;
import ec.edu.espe.distribuidas.hades.model.Cliente;
import ec.edu.espe.distribuidas.hades.model.Crucero;
import ec.edu.espe.distribuidas.hades.model.Reserva;
import ec.edu.espe.distribuidas.hades.model.TipoAlimentacion;
import ec.edu.espe.distribuidas.hades.model.TipoCamarote;
import ec.edu.espe.distribuidas.hades.model.TipoTour;
import ec.edu.espe.distribuidas.hades.model.Tour;
import ec.edu.espe.distribuidas.hades.service.CamaroteService;
import ec.edu.espe.distribuidas.hades.service.ClienteService;
import ec.edu.espe.distribuidas.hades.service.CruceroService;
import ec.edu.espe.distribuidas.hades.service.ReservaService;
import ec.edu.espe.distribuidas.hades.service.TipoAlimentacionService;
import ec.edu.espe.distribuidas.hades.service.TipoCamaroteService;
import ec.edu.espe.distribuidas.hades.service.TipoTourService;
import ec.edu.espe.distribuidas.hades.service.TourService;
import ec.edu.espe.distribuidas.hades.web.util.FacesUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
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
public class ReservaBean extends BaseBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(ReservaBean.class.getName());

    private List<Reserva> reservas;
    private List<TipoTour> tiposTours;
    private List<Crucero> cruceros;
    private List<TipoCamarote> tiposCamarote;
    private List<Tour> tours;
    private List<Cliente> clientes;
    private List<Camarote> camarotes;
    private List<TipoAlimentacion> tiposAlimentacion;

    private Reserva reserva;
    private Reserva reservaSel;
    private Tour tour;
    private Tour tourSel;
    private Cliente cliente;
    private Cliente clienteSel;
    private Camarote camarote;
    private Camarote camaroteSel;
    private TipoAlimentacion tipoAlimentacion;
    private TipoAlimentacion tipoAlimentacionSel;

    private String filtro;
    private String tipoTourBusqueda;
    private boolean enBusquedaPorTipo;
    private Date fechaInicioBusqueda;
    private Date fechaFinBusqueda;
    private String identificacionBusqueda;
    private String codigoTipoAlimentacion;
    private boolean enTourElegido;
    private boolean enEncontrado;

    @Inject
    private ReservaService reservaService;
    @Inject
    private TourService tourService;
    @Inject
    private TipoTourService tipoTourService;
    @Inject
    private CruceroService cruceroService;
    @Inject
    private CamaroteService camaroteService;
    @Inject
    private TipoCamaroteService tipoCamaroteService;
    @Inject
    private ClienteService clienteService;
    @Inject
    private TipoAlimentacionService tipoAlimentacionService;

    @PostConstruct
    public void init() {
        this.filtro = "TIP";
        this.enBusquedaPorTipo = true;
        this.tours = this.tourService.obtenerTodos();
        //this.camarotes = this.camaroteService.obtenerTodos();
        this.tiposTours = this.tipoTourService.obtenerTodos();
        this.cruceros = this.cruceroService.obtenerTodos();
        this.clientes = this.clienteService.obtenerTodos();
        this.reservas = this.reservaService.obtenerTodos();
        this.tiposAlimentacion = this.tipoAlimentacionService.obtenerTodos();
        this.reserva = new Reserva();
        this.enEncontrado = false;
        this.enTourElegido = false;

    }

    public void cambiarFiltro() {
        this.enBusquedaPorTipo = !this.enBusquedaPorTipo;
//        System.out.println("Valor para enbusqueda: "+this.enBusquedaPorTipo);
    }

    public void elegirTour() {
        LOG.info("TourSel=" + this.tourSel.getCrucero().getNombre());
        //this.reserva.setTour(this.tourSel);
        this.enTourElegido = true;
        if (this.tourSel != null) {
            this.camarotes = this.camaroteService.obtenerPorCrucero(this.tourSel.getCrucero());
        }
    }

    public void buscarTour() {
        if (this.enBusquedaPorTipo) {
            TipoTour tipoTour = new TipoTour();
            tipoTour.setCodigo(this.tipoTourBusqueda);
            //this.tours = this.tourService.buscarPorTipo(recuperaTipoTour(tipoTour));
        } else {
            //this.tours = this.tourService.buscarPorFechas(this.fechaInicioBusqueda, this.fechaFinBusqueda);
        }
    }

    public void buscarCliente() {
        if (this.identificacionBusqueda != null) {
            this.cliente = this.clienteService.obtenerPorIdentificacion(this.identificacionBusqueda);
            if (cliente != null) {
                enEncontrado = true;
                //this.camarotes = this.camaroteService.obtenerPorCrucero(this.tourSel.getCrucero());
            } else {
                FacesUtil.addMessageInfo("No se encontro cliente, verifique la identificacion");
            }
        }

    }

    public void buscarCamarote() {
        LOG.info("TourSel=" + this.tourSel.getCrucero().getNombre());
        if (this.tourSel != null) {
            this.camarotes = this.camaroteService.obtenerTodos();
        }
    }

    @Override
    public void agregar() {
        this.reserva = new Reserva();
        this.clientes = clienteService.obtenerTodos();
        this.tours = tourService.obtenerTodos();
        this.cruceros = cruceroService.obtenerTodos();

        super.agregar();
    }

    public void cancelar() {
        super.reset();
        this.reserva = new Reserva();
        this.clientes = clienteService.obtenerTodos();
        this.tours = tourService.obtenerTodos();
        this.cruceros = cruceroService.obtenerTodos();

    }

    public void generarCodigo() {
        Integer codigo = this.reservaService.obtenerTodos().size() + 1;
//        String res="RS0";
//        StringBuilder codigofinal = new StringBuilder();
//        if (codigo==0) {
//            codigo = 1;
//        } 
//        codigofinal.append(res);
//        codigofinal.append(codigo.toString());
//        return codigofinal.toString();
        this.reserva.setCodigo("RES00" + codigo.toString());
    }

    @Override
    public void modificar() {
        super.modificar();
        this.reserva = new Reserva();
        this.reserva.setCodigo(this.reservaSel.getCodigo());
        this.reserva.setTour(this.reservaSel.getTour());
        //this.reserva.setCrucero(this.reservaSel.getCrucero());
        this.reserva.setEstado(this.reservaSel.getEstado());
    }

//    public void eliminar() {
//        try {
//            this.tourService.eliminar(this.tourSel.getCodigo().toString());
//            this.tours = this.tourService.obtenerTodos();
//            FacesUtil.addMessageInfo("Se elimino el registro");
//            this.tourSel = null;
//        } catch (Exception e) {
//            FacesUtil.addMessageError(null, "No se puede eliminar el registro seleccionado. Verifique que no tenga informaci\u00f3n relacionada.");
//        }
//    }
    public void guardar() {

        try {
            LOG.info("CamaroteSel=" + this.camaroteSel.toString());
            LOG.info("TouSel=" + this.tourSel.toString());
            generarCodigo();
            this.reserva.setCamarote(this.camaroteSel);
            this.reserva.setTour(this.tourSel);
            this.reserva.setTipoAlimentacion(this.tipoAlimentacionService.obtenerPorCodigo(this.codigoTipoAlimentacion));
            this.reserva.setCliente(this.cliente);
            LOG.info("TipoAlimentacion=" + this.tipoAlimentacionService.obtenerPorCodigo(this.codigoTipoAlimentacion));
            LOG.info("Cliente=" + this.cliente.toString());
            LOG.info("Reserva=" + this.reserva.toString());
            this.reservaService.crear(this.reserva);
            FacesUtil.addMessageInfo("Se agrego La reserva: " + this.reserva.getCodigo());

        } catch (Exception e) {
            FacesUtil.addMessageError(null, "Ocurrí\u00f3 un error al actualizar la informaci\u00f3n");
        }

        
        
        super.reset();
        this.tour = new Tour();
        this.tourSel= new Tour();
        this.cliente = new Cliente();
        this.tipoAlimentacionSel= new TipoAlimentacion();
        this.reserva = new Reserva();
        this.tours = this.tourService.obtenerTodos();
        this.tiposTours = this.tipoTourService.obtenerTodos();
        this.cruceros = this.cruceroService.obtenerTodos();
        this.init();
    }

    public boolean isEnTourElegido() {
        return enTourElegido;
    }

    public void setEnTourElegido(boolean enTourElegido) {
        this.enTourElegido = enTourElegido;
    }

    public boolean isEnEncontrado() {
        return enEncontrado;
    }

    public void setEnEncontrado(boolean enEncontrado) {
        this.enEncontrado = enEncontrado;
    }

    public String getCodigoTipoAlimentacion() {
        return codigoTipoAlimentacion;
    }

    public void setCodigoTipoAlimentacion(String codigoTipoAlimentacion) {
        this.codigoTipoAlimentacion = codigoTipoAlimentacion;
    }

    public String getIdentificacionBusqueda() {
        return identificacionBusqueda;
    }

    public void setIdentificacionBusqueda(String identificacionBusqueda) {
        this.identificacionBusqueda = identificacionBusqueda;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Reserva getReservaSel() {
        return reservaSel;
    }

    public void setReservaSel(Reserva reservaSel) {
        this.reservaSel = reservaSel;
    }

    public CruceroService getCruceroService() {
        return cruceroService;
    }

    public List<Tour> getTours() {
        return tours;
    }

    public List<TipoTour> getTiposTours() {
        return tiposTours;
    }

    public List<Crucero> getCruceros() {
        return cruceros;
    }

    public List<TipoCamarote> getTiposCamarote() {
        return tiposCamarote;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Camarote> getCamarotes() {
        return camarotes;
    }

    public List<TipoAlimentacion> getTiposAlimentacion() {
        return tiposAlimentacion;
    }

    public void setTiposAlimentacion(List<TipoAlimentacion> tiposAlimentacion) {
        this.tiposAlimentacion = tiposAlimentacion;
    }

    public Tour getTourSel() {
        return tourSel;
    }

    public void setTourSel(Tour tourSel) {
        this.tourSel = tourSel;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getClienteSel() {
        return clienteSel;
    }

    public void setClienteSel(Cliente clienteSel) {
        this.clienteSel = clienteSel;
    }

    public Camarote getCamarote() {
        return camarote;
    }

    public void setCamarote(Camarote camarote) {
        this.camarote = camarote;
    }

    public Camarote getCamaroteSel() {
        return camaroteSel;
    }

    public void setCamaroteSel(Camarote camaroteSel) {
        this.camaroteSel = camaroteSel;
    }

    public TipoAlimentacion getTipoAlimentacion() {
        return tipoAlimentacion;
    }

    public void setTipoAlimentacion(TipoAlimentacion tipoAlimentacion) {
        this.tipoAlimentacion = tipoAlimentacion;
    }

    public TipoAlimentacion getTipoAlimentacionSel() {
        return tipoAlimentacionSel;
    }

    public void setTipoAlimentacionSel(TipoAlimentacion tipoAlimentacionSel) {
        this.tipoAlimentacionSel = tipoAlimentacionSel;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public String getTipoTourBusqueda() {
        return tipoTourBusqueda;
    }

    public void setTipoTourBusqueda(String tipoTourBusqueda) {
        this.tipoTourBusqueda = tipoTourBusqueda;
    }

    public boolean isEnBusquedaPorTipo() {
        return enBusquedaPorTipo;
    }

    public void setEnBusquedaPorTipo(boolean enBusquedaPorTipo) {
        this.enBusquedaPorTipo = enBusquedaPorTipo;
    }

    public Date getFechaInicioBusqueda() {
        return fechaInicioBusqueda;
    }

    public void setFechaInicioBusqueda(Date fechaInicioBusqueda) {
        this.fechaInicioBusqueda = fechaInicioBusqueda;
    }

    public Date getFechaFinBusqueda() {
        return fechaFinBusqueda;
    }

    public void setFechaFinBusqueda(Date fechaFinBusqueda) {
        this.fechaFinBusqueda = fechaFinBusqueda;
    }

    public Tour retornaTour(Reserva reserva) {
        Tour aux = new Tour();

        for (int i = 0; i < tours.size(); i++) {
            aux = tours.get(i);
            if (aux.getCodigo().equals(reserva.getTour().getCodigo())) {
                break;
            }
        }
        return aux;
    }

//    public Crucero retornaCrucero(Reserva reserva) {
//        Crucero aux = new Crucero();
//
//        for (int i = 0; i < reservas.size(); i++) {
//            aux = cruceros.get(i);
//            if (aux.getCodigo().equals(reserva.getCrucero().getCodigo())) {
//                break;
//            }
//        }
//        return aux;
//    }

    public Cliente retornaCliente(Reserva reserva) {
        Cliente aux = new Cliente();

        for (int i = 0; i < reservas.size(); i++) {
            aux = clientes.get(i);
            if (aux.getIdentificacion().equals(reserva.getCliente().getIdentificacion())) {
                break;
            }
        }
        return aux;
    }

    public TipoTour recuperaTipoTour(TipoTour tipoTour) {
        TipoTour aux = new TipoTour();

        for (int i = 0; i < tiposTours.size(); i++) {
            aux = tiposTours.get(i);
            if (aux.getCodigo().equals(tipoTour.getCodigo())) {
                break;
            }
        }
        return aux;
    }

}
