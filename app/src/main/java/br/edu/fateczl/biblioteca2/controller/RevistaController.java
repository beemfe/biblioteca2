package br.edu.fateczl.biblioteca2.controller;

import android.content.Context;
import java.util.List;
import br.edu.fateczl.biblioteca2.model.Revista;
import br.edu.fateczl.biblioteca2.persistence.RevistaDAO;

public class RevistaController implements ICRUDController<Revista> {
    private RevistaDAO dao;

    public RevistaController(Context context) {
        dao = new RevistaDAO(context);
    }

    @Override
    public void insert(Revista revista) throws Exception {
        if (revista == null || revista.getCodigo() <= 0 ||
                revista.getISSN() == null || revista.getISSN().trim().isEmpty()) {
            throw new Exception("Dados da revista inválidos");
        }
        dao.open();
        try {
            dao.insert(revista);
        } finally {
            dao.close();
        }
    }

    @Override
    public int update(Revista revista) throws Exception {
        if (revista == null || revista.getCodigo() <= 0) {
            throw new Exception("Código inválido");
        }
        dao.open();
        try {
            return dao.update(revista);
        } finally {
            dao.close();
        }
    }

    @Override
    public void delete(Revista revista) throws Exception {
        if (revista == null || revista.getCodigo() <= 0) {
            throw new Exception("Código inválido");
        }
        dao.open();
        try {
            dao.delete(revista);
        } finally {
            dao.close();
        }
    }

    @Override
    public Revista findOne(Revista revista) throws Exception {
        dao.open();
        try {
            return dao.findOne(revista);
        } finally {
            dao.close();
        }
    }

    @Override
    public List<Revista> findAll() throws Exception {
        dao.open();
        try {
            return dao.findAll();
        } finally {
            dao.close();
        }
    }
}