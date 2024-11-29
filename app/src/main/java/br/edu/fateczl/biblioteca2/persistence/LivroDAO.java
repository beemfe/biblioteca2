/*
 *@author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.biblioteca2.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import br.edu.fateczl.biblioteca2.model.Livro;

public class LivroDAO implements ICRUDDao<Livro> {
    private SQLiteDatabase db;
    private DatabaseHelper helper;

    public LivroDAO(Context context) {
        helper = new DatabaseHelper(context);
    }

    @Override
    public void open() throws SQLException {
        db = helper.getWritableDatabase();
    }

    @Override
    public void close() {
        helper.close();
    }

    public void insert(Livro livro) throws SQLException {
        db.beginTransaction();
        try {
            ContentValues valuesExemplar = new ContentValues();
            valuesExemplar.put("nome", livro.getNome());
            valuesExemplar.put("qtdPaginas", livro.getQtdPaginas());
            long id = db.insert("exemplar", null, valuesExemplar);

            ContentValues valuesLivro = new ContentValues();
            valuesLivro.put("exemplarCodigo", id);
            valuesLivro.put("ISBN", livro.getISBN());
            valuesLivro.put("edicao", livro.getEdicao());
            db.insert("livro", null, valuesLivro);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public int update(Livro livro) throws SQLException {
        db.beginTransaction();
        try {
            ContentValues valuesExemplar = new ContentValues();
            valuesExemplar.put("nome", livro.getNome());
            valuesExemplar.put("qtdPaginas", livro.getQtdPaginas());
            int count = db.update("exemplar", valuesExemplar,
                    "codigo = ?", new String[]{String.valueOf(livro.getCodigo())});

            ContentValues valuesLivro = new ContentValues();
            valuesLivro.put("ISBN", livro.getISBN());
            valuesLivro.put("edicao", livro.getEdicao());
            db.update("livro", valuesLivro,
                    "exemplarCodigo = ?", new String[]{String.valueOf(livro.getCodigo())});

            db.setTransactionSuccessful();
            return count;
        } finally {
            db.endTransaction();
        }
    }

    public void delete(Livro livro) throws SQLException {
        db.delete("exemplar", "codigo = ?",
                new String[]{String.valueOf(livro.getCodigo())});
    }

    public Livro findOne(Livro livro) throws SQLException {
        Cursor cursor = db.rawQuery(
                "SELECT e.*, l.ISBN, l.edicao " +
                        "FROM exemplar e " +
                        "INNER JOIN livro l ON e.codigo = l.exemplarCodigo " +
                        "WHERE e.codigo = ?",
                new String[]{String.valueOf(livro.getCodigo())});

        if (cursor.moveToFirst()) {
            Livro l = new Livro();
            l.setCodigo(cursor.getInt(0));
            l.setNome(cursor.getString(1));
            l.setQtdPaginas(cursor.getInt(2));
            l.setISBN(cursor.getString(3));
            l.setEdicao(cursor.getInt(4));
            cursor.close();
            return l;
        }
        cursor.close();
        return null;
    }

    public List<Livro> findAll() throws SQLException {
        List<Livro> livros = new ArrayList<>();
        Cursor cursor = db.rawQuery(
                "SELECT e.*, l.ISBN, l.edicao " +
                        "FROM exemplar e " +
                        "INNER JOIN livro l ON e.codigo = l.exemplarCodigo",
                null);

        if (cursor.moveToFirst()) {
            do {
                Livro l = new Livro();
                l.setCodigo(cursor.getInt(0));
                l.setNome(cursor.getString(1));
                l.setQtdPaginas(cursor.getInt(2));
                l.setISBN(cursor.getString(3));
                l.setEdicao(cursor.getInt(4));
                livros.add(l);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return livros;
    }
}