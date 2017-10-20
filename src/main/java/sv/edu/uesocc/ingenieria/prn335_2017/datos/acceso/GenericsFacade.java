/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.uesocc.ingenieria.prn335_2017.datos.acceso;

import java.util.List;


/**
 *
 * @author jacque
 */
public interface GenericsFacade<T> {
   void create(T e);
   void edit(T e);

    void remove(T e);
    
    

    T find(Object id);

    List<T> findAll();

    List<T> findRange(int[] range);

    int count();
    

    
}
