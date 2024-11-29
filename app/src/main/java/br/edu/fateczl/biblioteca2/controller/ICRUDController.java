package br.edu.fateczl.biblioteca2.controller;

import java.util.List;

public interface ICRUDController<T> {
    void insert(T t) throws Exception;
    int update(T t) throws Exception;
    void delete(T t) throws Exception;
    T findOne(T t) throws Exception;
    List<T> findAll() throws Exception;
}