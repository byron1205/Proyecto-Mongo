/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.hades.dao;

import ec.edu.espe.distribuidas.hades.model.Usuario;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author Edward
 */
public class UsuarioDAO extends BasicDAO<Usuario, ObjectId> {
    
    public UsuarioDAO(Class<Usuario> objectEntity, Datastore ds) {
        super(objectEntity, ds);
    }
    
    public Usuario findByCodigo(String codigo){
        
        Query<Usuario> qry = getDatastore().createQuery(Usuario.class);
        qry.criteria("codUsuario").equal(codigo);
        return  qry.get();
        //        Usuario u = new Usuario();
//        u.setClave("12345");
//        u.setCodUsuario("byrong1205");
//        u.setNombre("Byron Guanochanga");
//        u.setEmail("bg@.com");
//        return u;
    }
    
}
