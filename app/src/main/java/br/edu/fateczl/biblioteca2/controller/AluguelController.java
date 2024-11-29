package br.edu.fateczl.biblioteca2.controller;

import android.content.Context;
import java.util.List;
import br.edu.fateczl.biblioteca2.model.Aluguel;
import br.edu.fateczl.biblioteca2.persistence.AluguelDAO;

public class AluguelController implements ICRUDController<Aluguel> {
    private AluguelDAO dao;

    public AluguelController(Context context) {
        dao = new AluguelDAO(context);
    }

    @Override
    public void insert(Aluguel aluguel) throws Exception {
        if (aluguel == null || aluguel.getExemplarCodigo() <= 0 ||
                aluguel.getAlunoRA() <= 0 || aluguel.getDataRetirada() == null) {
            throw new Exception("Dados de aluguel inválidos");
        }
        try {
            dao.open();
            dao.insert(aluguel);
        } finally {
            dao.close();
        }
    }

    @Override
    public int update(Aluguel aluguel) throws Exception {
        if (aluguel == null || aluguel.getExemplarCodigo() <= 0 ||
                aluguel.getAlunoRA() <= 0) {
            throw new Exception("Dados de aluguel inválidos");
        }
        try {
            dao.open();
            return dao.update(aluguel);
        } finally {
            dao.close();
        }
    }

    @Override
    public void delete(Aluguel aluguel) throws Exception {
        if (aluguel == null || aluguel.getExemplarCodigo() <= 0 ||
                aluguel.getAlunoRA() <= 0) {
            throw new Exception("Dados de aluguel inválidos");
        }
        try {
            dao.open();
            dao.delete(aluguel);
        } finally {
            dao.close();
        }
    }

    @Override
    public Aluguel findOne(Aluguel aluguel) throws Exception {
        try {
            dao.open();
            return dao.findOne(aluguel);
        } finally {
            dao.close();
        }
    }

    @Override
    public List<Aluguel> findAll() throws Exception {
        try {
            dao.open();
            return dao.findAll();
        } finally {
            dao.close();
        }
    }
}