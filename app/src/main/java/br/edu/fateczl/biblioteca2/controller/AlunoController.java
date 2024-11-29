package br.edu.fateczl.biblioteca2.controller;

import android.content.Context;
import java.util.List;
import br.edu.fateczl.biblioteca2.model.Aluno;
import br.edu.fateczl.biblioteca2.persistence.AlunoDAO;

public class AlunoController implements ICRUDController<Aluno> {
    private AlunoDAO dao;

    public AlunoController(Context context) {
        dao = new AlunoDAO(context);
    }

    @Override
    public void insert(Aluno aluno) throws Exception {
        if (aluno == null || aluno.getRA() <= 0 ||
                aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
            throw new Exception("Dados de aluno inválidos");
        }
        dao.open();
        try {
            dao.insert(aluno);
        } finally {
            dao.close();
        }
    }

    @Override
    public int update(Aluno aluno) throws Exception {
        if (aluno == null || aluno.getRA() <= 0) {
            throw new Exception("RA inválido");
        }
        dao.open();
        try {
            return dao.update(aluno);
        } finally {
            dao.close();
        }
    }

    @Override
    public void delete(Aluno aluno) throws Exception {
        if (aluno == null || aluno.getRA() <= 0) {
            throw new Exception("RA inválido");
        }
        dao.open();
        try {
            dao.delete(aluno);
        } finally {
            dao.close();
        }
    }

    @Override
    public Aluno findOne(Aluno aluno) throws Exception {
        dao.open();
        try {
            return dao.findOne(aluno);
        } finally {
            dao.close();
        }
    }

    @Override
    public List<Aluno> findAll() throws Exception {
        dao.open();
        try {
            return dao.findAll();
        } finally {
            dao.close();
        }
    }
}