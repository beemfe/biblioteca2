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
import br.edu.fateczl.biblioteca2.model.Aluguel;

public class AluguelDAO implements ICRUDDao<Aluguel> {
    private SQLiteDatabase db;
    private DatabaseHelper helper;

    public AluguelDAO(Context context) {
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

    public void insert(Aluguel aluguel) throws SQLException {
        ContentValues values = new ContentValues();
        values.put("exemplarCodigo", aluguel.getExemplarCodigo());
        values.put("alunoRA", aluguel.getAlunoRA());
        values.put("data_retirada", aluguel.getDataRetirada());
        values.put("data_devolucao", aluguel.getDataDevolucao());
        db.insert("aluguel", null, values);
    }

    public int update(Aluguel aluguel) throws SQLException {
        ContentValues values = new ContentValues();
        values.put("data_devolucao", aluguel.getDataDevolucao());

        String[] whereArgs = {
                String.valueOf(aluguel.getExemplarCodigo()),
                String.valueOf(aluguel.getAlunoRA()),
                aluguel.getDataRetirada()
        };

        return db.update("aluguel", values,
                "exemplarCodigo = ? AND alunoRA = ? AND data_retirada = ?",
                whereArgs);
    }

    public void delete(Aluguel aluguel) throws SQLException {
        String[] whereArgs = {
                String.valueOf(aluguel.getExemplarCodigo()),
                String.valueOf(aluguel.getAlunoRA()),
                aluguel.getDataRetirada()
        };

        db.delete("aluguel",
                "exemplarCodigo = ? AND alunoRA = ? AND data_retirada = ?",
                whereArgs);
    }

    public Aluguel findOne(Aluguel aluguel) throws SQLException {
        String[] whereArgs = {
                String.valueOf(aluguel.getExemplarCodigo()),
                String.valueOf(aluguel.getAlunoRA()),
                aluguel.getDataRetirada()
        };

        Cursor cursor = db.query("aluguel", null,
                "exemplarCodigo = ? AND alunoRA = ? AND data_retirada = ?",
                whereArgs, null, null, null);

        if (cursor.moveToFirst()) {
            Aluguel a = new Aluguel();
            a.setExemplarCodigo(cursor.getInt(0));
            a.setAlunoRA(cursor.getInt(1));
            a.setDataRetirada(cursor.getString(2));
            a.setDataDevolucao(cursor.getString(3));
            cursor.close();
            return a;
        }
        cursor.close();
        return null;
    }

    public List<Aluguel> findAll() throws SQLException {
        List<Aluguel> alugueis = new ArrayList<>();
        Cursor cursor = db.query("aluguel", null,
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Aluguel a = new Aluguel();
                a.setExemplarCodigo(cursor.getInt(0));
                a.setAlunoRA(cursor.getInt(1));
                a.setDataRetirada(cursor.getString(2));
                a.setDataDevolucao(cursor.getString(3));
                alugueis.add(a);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return alugueis;
    }
}