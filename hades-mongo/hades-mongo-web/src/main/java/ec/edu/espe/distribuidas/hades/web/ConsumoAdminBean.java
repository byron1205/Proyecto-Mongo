/*
 * Hades Cruise
 * Aplicaciones Distribuidas
 * NRC: 2434 
 * Tutor: HENRY RAMIRO CORAL CORAL 
 * 2017 (c) Hades Cruise Corp.
 */
package ec.edu.espe.distribuidas.hades.web;

import ec.edu.espe.distribuidas.hades.enums.MenuEnum;
import ec.edu.espe.distribuidas.hades.model.Camarote;
import ec.edu.espe.distribuidas.hades.model.Consumo;
import ec.edu.espe.distribuidas.hades.model.Crucero;
import ec.edu.espe.distribuidas.hades.model.Menu;
import ec.edu.espe.distribuidas.hades.model.Reserva;
import ec.edu.espe.distribuidas.hades.model.TipoCamarote;
import ec.edu.espe.distribuidas.hades.service.CamaroteService;
import ec.edu.espe.distribuidas.hades.service.ConsumoService;
import ec.edu.espe.distribuidas.hades.service.CruceroService;
import ec.edu.espe.distribuidas.hades.service.MenuService;
import ec.edu.espe.distribuidas.hades.service.ReservaService;
import ec.edu.espe.distribuidas.hades.service.TipoCamaroteService;
import ec.edu.espe.distribuidas.hades.web.util.FacesUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
public class ConsumoAdminBean extends BaseBean implements Serializable {

    private String filtro;
    private String tipoMenuBusqueda;
    private String menuBusqueda;
    private String codMenu;
    private String codReserva;
    private String tipoMenu;

    private List<Menu> listaMenu;
    private List<Menu> tiposMenu;
    private List<Menu> tiposMenuB;

    private List<Consumo> consumos;
    private List<Consumo> consumosSel;
    private List<Consumo> consumosFil;

    private Consumo consumo;
    private Consumo consumoSel;
    private Menu menu;
    private Reserva reserva;
    private Menu menuSel;

    private Double valor;

    private String tipoTourBusqueda;
    private boolean enBusquedaPorFecha;
    private boolean enBusquedaPorMenu;
    private Date fechaInicioBusqueda;
    private Date fechaFinBusqueda;

    private static final Logger LOG = Logger.getLogger(ConsumoAdminBean.class.getName());

    @Inject
    private MenuService menuService;
    @Inject
    private ConsumoService consumoService;
    @Inject
    private ReservaService reservaService;

    @PostConstruct
    public void init() {
        this.filtro = "RES";
        //this.consumos = this.consumoService.obtenerTodos();
        //this.listaMenu = this.menuService.obtenerTodos();
        this.tiposMenu = new ArrayList<>();
        this.tiposMenuB = new ArrayList<>();
        this.consumosSel = new ArrayList<>();
        this.consumosFil = new ArrayList<>();
        this.menu = new Menu();
        this.consumo = new Consumo();
        this.reserva = new Reserva();
        this.valor = 0.0;
        this.codMenu = "";
        this.codReserva = "";
        this.tipoMenu = "";

        //this.tiposMenu = this.menuService.obtenerTodos();
        //this.camarotes = this.camaroteService.obtenerTodos();
    }

    public void buscar() {
        //this.listaMenu = this.menuService.obtenerPorTipo(this.tipoMenuBusqueda);
        //this.camarotes = this.camaroteService.obtenerPorTipo(this.tipoCamaroteService.obtenerPorCodigo(this.tipoCamaroteBusqueda));
        //LOG.info("cruceroBusaueda: "+this.cruceroBusqueda+ ", tipoCamaroteBusqueda: "+this.tipoCamaroteBusqueda);

        System.out.println("Filtro: " + this.filtro);
        System.out.println("Tipo menu: " + this.menu.getTipo());
        System.out.println("Cod menu: " + this.codMenu);
        System.out.println("Cod reserva: " + this.codReserva);

        this.consumosSel = new ArrayList<>();
        this.consumosFil = new ArrayList<>();
        this.consumos = this.consumoService.obtenerTodos();
        //this.buscarPorTipo();

        for (Consumo con : consumos) {
            if (con.getReserva().getCodigo().equals(this.codReserva)) {
                this.consumosSel.add(con);
                System.out.println("Consumos por Reserva: " + con.getMenu().getNombre());
            }
        }

        if (!this.filtro.equals("RES")) {
            if (this.filtro.equals("MEN")) {
                if (!"".equals(this.codMenu)) {
                    System.out.println("Entre MEN");
                    for (Consumo con : consumosSel) {
                        if (con.getMenu().getCodigo().toString().equals(this.codMenu)) {
                            this.consumosFil.add(con);
                            System.out.println("Consumos por Menu: " + con.getMenu().getNombre());
                        }
                    }
                    this.consumosSel = this.consumosFil;
                }
            } else {
                System.out.println("Fecha Inicio: " + this.fechaInicioBusqueda);
                System.out.println("Fecha Fin: " + this.fechaFinBusqueda);
                if (this.fechaInicioBusqueda != null && this.fechaFinBusqueda != null) {
                    System.out.println("Entre fechas");
                    for (Consumo con : consumosSel) {
                        if (con.getFecha() != null) {
                            if (con.getFecha().getTime() >= this.fechaInicioBusqueda.getTime()
                                    && con.getFecha().getTime() <= this.fechaFinBusqueda.getTime()) {
                                this.consumosFil.add(con);
                                System.out.println("Consumos por Fecha: " + con.getMenu().getNombre());
                            }
                        }

                    }
                    this.consumosSel = this.consumosFil;
                }
            }
        }

        this.consumos = this.consumosSel;

    }

    public void buscarPorTipoMenu() {
        this.tiposMenuB = new ArrayList<>();
        this.listaMenu = this.menuService.obtenerTodos();
        this.tiposMenu = new ArrayList<>();
        //System.out.println(this.menu.getTipo().toString());
        for (Menu me : listaMenu) {
            if (me.getTipo().toString().equals(this.tipoMenu)) {
                this.tiposMenu.add(me);
                System.out.println(me.getNombre());
            }
        }
        this.tiposMenuB = this.tiposMenu;
    }

    public void buscarPorTipo() {
        this.tiposMenuB = new ArrayList<>();
        this.listaMenu = this.menuService.obtenerTodos();
        this.tiposMenu = new ArrayList<>();
        //System.out.println(this.menu.getTipo().toString());
        for (Menu me : listaMenu) {
            if (me.getTipo().toString().equals(this.menu.getTipo().toString())) {
                this.tiposMenu.add(me);
                System.out.println(me.getNombre());
            }
        }
        this.listaMenu = this.tiposMenu;

        this.consumo.setValor(BigDecimal.ZERO);
        this.menu.setCodigo(0);
        this.menu.setPrecio(BigDecimal.ZERO);
        this.calcularValor();

        //this.camarotes = this.camaroteService.obtenerPorTipo(this.tipoCamaroteService.obtenerPorCodigo(this.tipoCamaroteBusqueda));
        //LOG.info("cruceroBusaueda: "+this.cruceroBusqueda+ ", tipoCamaroteBusqueda: "+this.tipoCamaroteBusqueda);
    }

    public void calcularValor() {

        //System.out.println(this.menu.getTipo());
        //System.out.println("menu:" + this.codMenu);
        //this.menu.setCodigo(Integer.parseInt(this.codMenu));
        System.out.println(this.menu.getCodigo());
        //System.out.println(this.consumo.getCantidad());

        this.valor = 0.0;

        if (this.menu.getCodigo().equals(0)) {
            this.consumo.setCantidad(0);
            System.out.println("0: " + this.consumo.getCantidad());
        } else if (this.consumo.getCantidad() <= 0) {
            this.consumo.setCantidad(1);
            System.out.println("!0: " + this.consumo.getCantidad());
        }
        if (this.menu.getCodigo() != null) {
            for (Menu me : listaMenu) {
                if (me.getCodigo().toString().equals(this.menu.getCodigo().toString())) {
                    String precio = me.getPrecio().toString();
                    if (this.consumo.getCantidad() != 0) {
                        this.valor = this.consumo.getCantidad() * Double.parseDouble(precio);
                    } else {
                        this.valor = 0.0;
                    }
                }
            }

        }

        this.consumo.setValor(BigDecimal.valueOf(this.valor));

    }

    /*public TipoCamarote retornaTipoTour(Camarote camarote)
    {
        TipoCamarote aux = new TipoCamarote();
        
        for(int i= 0; i<tiposCamarote.size();i++)
        {
            aux= tiposCamarote.get(i);
            if(aux.getCodigo().equals(camarote.getTipo().getCodigo()))
            {
                break;
            }
        }
        return aux;
    }*/
 /*public Crucero retornaCrucero(Camarote camarote)
    {
        Crucero aux = new Crucero();
        
        for(int i= 0; i<camarotes.size();i++)
        {
            aux= cruceros.get(i);
            if(aux.getCodigo().equals(camarote.getCrucero().getCodigo()))
            {
                break;
            }
        }
        return aux;
    }*/
    public void cancelar() {
        super.reset();
        this.consumo = new Consumo();
        this.menu = new Menu();
        this.reserva = new Reserva();
    }

    public void guardar() {
        try {
            if (this.enAgregar) {
                Calendar cal = Calendar.getInstance();

                this.reserva.setId(this.buscarReserva(this.reserva.getCodigo()).getId());
                this.menu.setId(this.buscarMenu(this.menu.getCodigo()).getId());
                this.consumo.setReserva(this.reserva);
                this.consumo.setMenu(this.menu);
                this.consumo.setFecha(cal.getTime());
                calcularValor();

                this.consumoService.crear(this.consumo);
                FacesUtil.addMessageInfo("Se agreg\u00f3n el Consumo con c\u00f3digo de menu: " + this.consumo.getMenu().getCodigo());
            } else {
                this.consumoService.modificar(this.consumo);
                FacesUtil.addMessageInfo("Se modific\u00f3 el consumo con c\u00f3digo de menu: " + this.consumo.getMenu().getCodigo());
            }
        } catch (Exception ex) {
            FacesUtil.addMessageError(null, "Ocurrí\u00f3 un error al actualizar la informaci\u00f3n");
        }
        
        super.reset();
        this.consumo = new Consumo();
        this.menu = new Menu();
        this.reserva = new Reserva();
        this.buscar();
        //this.listaMenu = this.menuService.obtenerTodos();

    }

    @Override
    public void agregar() {
        this.consumo = new Consumo();
        this.menu = new Menu();
        this.menu.setCodigo(0);
        this.menu.setPrecio(BigDecimal.ZERO);
        this.reserva = new Reserva();
        this.menu.setTipo(MenuEnum.ENT);
        this.consumo.setCantidad(1);
        this.buscarPorTipo();
        super.agregar();
    }

    @Override
    public void modificar() {
        super.modificar();
        this.consumo = new Consumo();
        this.consumo.setFecha(this.consumoSel.getFecha());
        this.consumo.setId(this.consumoSel.getId());
        this.consumo.setValor(this.consumoSel.getValor());
        this.consumo.setCantidad(this.consumoSel.getCantidad());
        this.consumo.setMenu(this.consumoSel.getMenu());
        this.consumo.setReferencia(this.consumoSel.getReferencia());
        this.consumo.setReserva(this.consumoSel.getReserva());
        this.menu.setTipo(this.consumoSel.getMenu().getTipo());
        this.menu.setCodigo(this.consumoSel.getMenu().getCodigo());
        this.reserva.setCodigo(this.consumoSel.getReserva().getCodigo());
        this.consumo.setMenu(this.menu);
        this.consumo.setReserva(this.reserva);
    }

    public void cambiarFiltro() {

        if (this.getFiltro().equals("RES")) {
            this.enBusquedaPorFecha = false;
            this.enBusquedaPorMenu = false;
        } else if (this.getFiltro().equals("MEN")) {
            this.enBusquedaPorMenu = true;
            this.enBusquedaPorFecha = false;
        } else {
            this.enBusquedaPorMenu = false;
            this.enBusquedaPorFecha = true;
        }

        System.out.println("filtro: " + this.getFiltro());
        System.out.println("MENU: " + this.enBusquedaPorMenu);
        System.out.println("Fecha: " + this.enBusquedaPorFecha);
    }

    public void eliminar() {
        try {
            //this.consumoService.eliminar(this.consumoSel.getReserva());
            //this.tiposTour = this.tipoTourService.obtenerTodos();
            FacesUtil.addMessageInfo("Se elimino el registro ");
            //this.tipoTourSel = null;  
        } catch (Exception e) {
            FacesUtil.addMessageError(null, "No se puede eliminar el registro seleccionado. Verifique que no tenga informacion relacionada.");
        }
    }

    public Reserva buscarReserva(String codigo) {
        return this.reservaService.obtenerPorIdentificacion(codigo);
    }

    public Menu buscarMenu(Integer codigo) {
        return this.menuService.obtenerPorCodigo(codigo);
    }

    public String getTipoMenuBusqueda() {
        return tipoMenuBusqueda;
    }

    public void setTipoMenuBusqueda(String tipoMenuBusqueda) {
        this.tipoMenuBusqueda = tipoMenuBusqueda;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Menu getMenuSel() {
        return menuSel;
    }

    public void setMenuSel(Menu menuSel) {
        this.menuSel = menuSel;
    }

    public Consumo getConsumo() {
        return consumo;
    }

    public void setConsumo(Consumo consumo) {
        this.consumo = consumo;
    }

    public String getMenuBusqueda() {
        return menuBusqueda;
    }

    public void setMenuBusqueda(String menuBusqueda) {
        this.menuBusqueda = menuBusqueda;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public List<Menu> getListaMenu() {
        return listaMenu;
    }

    public List<Consumo> getConsumos() {
        return consumos;
    }

    public List<Menu> getTiposMenu() {
        return tiposMenu;
    }

    public Consumo getConsumoSel() {
        return consumoSel;
    }

    public void setConsumoSel(Consumo consumoSel) {
        this.consumoSel = consumoSel;
    }

    public String getTipoTourBusqueda() {
        return tipoTourBusqueda;
    }

    public void setTipoTourBusqueda(String tipoTourBusqueda) {
        this.tipoTourBusqueda = tipoTourBusqueda;
    }

    /*public boolean isEnBusquedaPorTipo() {
        return enBusquedaPorTipo;
    }

    public void setEnBusquedaPorTipo(boolean enBusquedaPorTipo) {
        this.enBusquedaPorTipo = enBusquedaPorTipo;
    }*/
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

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public String getCodMenu() {
        return codMenu;
    }

    public void setCodMenu(String codMenu) {
        this.codMenu = codMenu;
    }

    public String getCodReserva() {
        return codReserva;
    }

    public void setCodReserva(String codReserva) {
        this.codReserva = codReserva;
    }

    public List<Consumo> getConsumosSel() {
        return consumosSel;
    }

    public String getTipoMenu() {
        return tipoMenu;
    }

    public void setTipoMenu(String tipoMenu) {
        this.tipoMenu = tipoMenu;
    }

    public List<Menu> getTiposMenuB() {
        return tiposMenuB;
    }

    public void setTiposMenuB(List<Menu> tiposMenuB) {
        this.tiposMenuB = tiposMenuB;
    }

    public List<Consumo> getConsumosFil() {
        return consumosFil;
    }

    public void setConsumosFil(List<Consumo> consumosFil) {
        this.consumosFil = consumosFil;
    }

    public boolean isEnBusquedaPorFecha() {
        return enBusquedaPorFecha;
    }

    public void setEnBusquedaPorFecha(boolean enBusquedaPorFecha) {
        this.enBusquedaPorFecha = enBusquedaPorFecha;
    }

    public boolean isEnBusquedaPorMenu() {
        return enBusquedaPorMenu;
    }

    public void setEnBusquedaPorMenu(boolean enBusquedaPorMenu) {
        this.enBusquedaPorMenu = enBusquedaPorMenu;
    }

}
