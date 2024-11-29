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
import br.edu.fateczl.biblioteca2.model.Revista;

public class RevistaDAO implements ICRUDDao<Revista> {
    private SQLiteDatabase db;
    private DatabaseHelper helper;

    public RevistaDAO(Context context) {
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

    public void insert(Revista revista) throws SQLException {
        db.beginTransaction();
        try {
            ContentValues valuesExemplar = new ContentValues();
            valuesExemplar.put("nome", revista.getNome());
            valuesExemplar.put("qtdPaginas", revista.getQtdPaginas());
            long id = db.insert("exemplar", null, valuesExemplar);

            ContentValues valuesRevista = new ContentValues();
            valuesRevista.put("exemplarCodigo", id);
            valuesRevista.put("ISSN", revista.getISSN());
            db.insert("revista", null, valuesRevista);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public int update(Revista revista) throws SQLException {
        db.beginTransaction();
        try {
            ContentValues valuesExemplar = new ContentValues();
            valuesExemplar.put("nome", revista.getNome());
            valuesExemplar.put("qtdPaginas", revista.getQtdPaginas());
            int count = db.update("exemplar", valuesExemplar,
                    "codigo = ?", new String[]{String.valueOf(revista.getCodigo())});

            ContentValues valuesRevista = new ContentValues();
            valuesRevista.put("ISSN", revista.getISSN());
            db.update("revista", valuesRevista,
                    "exemplarCodigo = ?", new String[]{String.valueOf(revista.getCodigo())});

            db.setTransactionSuccessful();
            return count;
        } finally {
            db.endTransaction();
        }
    }

    public void delete(Revista revista) throws SQLException {
        db.delete("exemplar", "codigo = ?",
                new String[]{String.valueOf(revista.getCodigo())});
    }

    public Revista findOne(Revista revista) throws SQLException {
        Cursor cursor = db.rawQuery(
                "SELECT e.*, r.ISSN " +
                        "FROM exemplar e " +
                        "INNER JOIN revista r ON e.codigo = r.exemplarCodigo " +
                        "WHERE e.codigo = ?",
                new String[]{String.valueOf(revista.getCodigo())});

        if (cursor.moveToFirst()) {
            Revista r = new Revista();
            r.setCodigo(cursor.getInt(0));
            r.setNome(cursor.getString(1));
            r.setQtdPaginas(cursor.getInt(2));
            r.setISSN(cursor.getString(3));
            cursor.close();
            return r;
        }
        cursor.close();
        return null;
    }

    public List<Revista> findAll() throws SQLException {
        List<Revista> revistas = new ArrayList<>();
        Cursor cursor = db.rawQuery(
                "SELECT e.*, r.ISSN " +
                        "FROM exemplar e " +
                        "INNER JOIN revista r ON e.codigo = r.exemplarCodigo",
                null);

        if (cursor.moveToFirst()) {
            do {
                Revista r = new Revista();
                r.setCodigo(cursor.getInt(0));
                r.setNome(cursor.getString(1));
                r.setQtdPaginas(cursor.getInt(2));
                r.setISSN(cursor.getString(3));
                revistas.add(r);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return revistas;
    }
}