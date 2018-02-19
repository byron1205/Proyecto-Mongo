/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.hades.dao;

import ec.edu.espe.distribuidas.hades.model.Camarote;
import ec.edu.espe.distribuidas.hades.model.Consumo;
import ec.edu.espe.distribuidas.hades.model.Crucero;
import ec.edu.espe.distribuidas.hades.model.Menu;
import ec.edu.espe.distribuidas.hades.model.Reserva;
import ec.edu.espe.distribuidas.hades.model.TipoCamarote;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author hendrix
 */
public class ConsumoDAO  extends BasicDAO<Consumo, ObjectId> {
    
    public ConsumoDAO(Class<Consumo> objectEntity, Datastore ds) {
        super(objectEntity, ds);
    }
    
    public List<Consumo> findByReserva(Reserva reserva) {
        Query<Consumo> qry = getDatastore().createQuery(Consumo.class);
        qry.criteria("reserva").equal(reserva);
        return qry.asList();
    }
    
     public List<Consumo> findByReservaMenu(Reserva reserva, Menu menu) {
        Query<Consumo> qry = getDatastore().createQuery(Consumo.class);
        qry.and(
                qry.criteria("reserva").equal(reserva),
                qry.criteria("menu").equal(menu)
        );
        return qry.asList();
    }
     
      public List<Consumo> findFecha(Date dateI, Date dateF) {
        Query<Consumo> qry = getDatastore().createQuery(Consumo.class);
        qry.filter("fecha >=", dateI);
        qry.filter("fecha <=", dateF);
        return qry.asList();
    }
    
    
    
}
