/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.hades.dao;

import ec.edu.espe.distribuidas.hades.model.TipoCamarote;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author hendrix
 */
public class TipoCamaroteDAO extends BasicDAO<TipoCamarote, ObjectId> {
    
    public TipoCamaroteDAO(Class<TipoCamarote> entityClass, Datastore ds) {
        super(entityClass, ds);
    }
    
    public  TipoCamarote findByNombre(String nombre){
        Query<TipoCamarote> qry = getDatastore().createQuery(TipoCamarote.class);
        qry.criteria("nombre").equal(nombre);
        return  qry.get();
    }
}
