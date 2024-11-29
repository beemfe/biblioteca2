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
import br.edu.fateczl.biblioteca2.model.Aluno;

public class AlunoDAO implements ICRUDDao<Aluno> {
    private SQLiteDatabase db;
    private DatabaseHelper helper;

    public AlunoDAO(Context context) {
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

    public void insert(Aluno aluno) throws SQLException {
        ContentValues values = new ContentValues();
        values.put("RA", aluno.getRA());
        values.put("nome", aluno.getNome());
        values.put("email", aluno.getEmail());
        db.insert("aluno", null, values);
    }

    public int update(Aluno aluno) throws SQLException {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("email", aluno.getEmail());
        return db.update("aluno", values,
                "RA = ?", new String[]{String.valueOf(aluno.getRA())});
    }

    public void delete(Aluno aluno) throws SQLException {
        db.delete("aluno", "RA = ?",
                new String[]{String.valueOf(aluno.getRA())});
    }

    public Aluno findOne(Aluno aluno) throws SQLException {
        Cursor cursor = db.query("aluno", null,
                "RA = ?", new String[]{String.valueOf(aluno.getRA())},
                null, null, null);

        if (cursor.moveToFirst()) {
            Aluno a = new Aluno();
            a.setRA(cursor.getInt(0));
            a.setNome(cursor.getString(1));
            a.setEmail(cursor.getString(2));
            cursor.close();
            return a;
        }
        cursor.close();
        return null;
    }

    public List<Aluno> findAll() throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        Cursor cursor = db.query("aluno", null,
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Aluno a = new Aluno();
                a.setRA(cursor.getInt(0));
                a.setNome(cursor.getString(1));
                a.setEmail(cursor.getString(2));
                alunos.add(a);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return alunos;
    }
}