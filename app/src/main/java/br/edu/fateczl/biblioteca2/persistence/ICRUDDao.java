/*
 *@author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.biblioteca2.persistence;

import android.database.SQLException;
import java.util.List;

public interface ICRUDDao<T> {
    void open() throws SQLException;
    void close();
    void insert(T t) throws SQLException;
    int update(T t) throws SQLException;
    void delete(T t) throws SQLException;
    T findOne(T t) throws SQLException;
    List<T> findAll() throws SQLException;
}