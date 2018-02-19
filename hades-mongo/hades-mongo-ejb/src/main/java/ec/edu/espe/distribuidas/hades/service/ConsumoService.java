/*
 * Hades Cruise
 * Aplicaciones Distribuidas
 * NRC: 2434 
 * Tutor: HENRY RAMIRO CORAL CORAL 
 * 2017 (c) Hades Cruise Corp.
 */
package ec.edu.espe.distribuidas.hades.service;

import ec.edu.espe.distribuidas.hades.dao.CamaroteDAO;
import ec.edu.espe.distribuidas.hades.dao.ConsumoDAO;
import ec.edu.espe.distribuidas.hades.model.Camarote;
import ec.edu.espe.distribuidas.hades.model.Consumo;
import ec.edu.espe.distribuidas.hades.model.Crucero;
import ec.edu.espe.distribuidas.hades.model.Reserva;
import ec.edu.espe.distribuidas.hades.model.TipoCamarote;
import ec.edu.espe.distribuidas.nosql.mongo.MongoPersistence;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Hades Cruise Corp.
 */
@Stateless
@LocalBean
public class ConsumoService {
    
    @EJB
    private MongoPersistence mp;
    private ConsumoDAO consumoFacade;
    
    @PostConstruct
    public void init() {
        this.consumoFacade = new ConsumoDAO(Consumo.class, mp.context());
    }
    
    public List<Consumo> obtenerTodos() {
        return this.consumoFacade.find().asList();
    }
    
    public List<Consumo> obtenerPorReserva(Reserva reserva) {
        return this.consumoFacade.findByReserva(reserva);
    }

    public void crear(Consumo consumo) {
        this.consumoFacade.save(consumo);
    }

    public void modificar(Consumo consumo) {
        Consumo aux = this.consumoFacade.findOne("codigo", consumo.getReserva());
        consumo.setId(aux.getId());
        this.consumoFacade.save(consumo);
        
    }

    /*public void eliminar(String codigo) {
        Consumo consumo = this.ConsumoDAO.findOne("codigo", codigo);
        this.consumoFacade.delete(consumo);
    }*/
}
