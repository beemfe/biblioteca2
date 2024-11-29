package br.edu.fateczl.biblioteca2.controller;

import android.content.Context;
import java.util.List;
import br.edu.fateczl.biblioteca2.model.Livro;
import br.edu.fateczl.biblioteca2.persistence.LivroDAO;

public class LivroController implements ICRUDController<Livro> {
    private LivroDAO dao;

    public LivroController(Context context) {
        dao = new LivroDAO(context);
    }

    @Override
    public void insert(Livro livro) throws Exception {
        if (livro == null || livro.getCodigo() <= 0 ||
                livro.getISBN() == null || livro.getISBN().trim().isEmpty()) {
            throw new Exception("Dados do livro inválidos");
        }
        dao.open();
        try {
            dao.insert(livro);
        } finally {
            dao.close();
        }
    }

    @Override
    public int update(Livro livro) throws Exception {
        if (livro == null || livro.getCodigo() <= 0) {
            throw new Exception("Código inválido");
        }
        dao.open();
        try {
            return dao.update(livro);
        } finally {
            dao.close();
        }
    }

    @Override
    public void delete(Livro livro) throws Exception {
        if (livro == null || livro.getCodigo() <= 0) {
            throw new Exception("Código inválido");
        }
        dao.open();
        try {
            dao.delete(livro);
        } finally {
            dao.close();
        }
    }

    @Override
    public Livro findOne(Livro livro) throws Exception {
        dao.open();
        try {
            return dao.findOne(livro);
        } finally {
            dao.close();
        }
    }

    @Override
    public List<Livro> findAll() throws Exception {
        dao.open();
        try {
            return dao.findAll();
        } finally {
            dao.close();
        }
    }
}