/*
 *@author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.biblioteca2.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "biblioteca2.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_EXEMPLAR =
            "CREATE TABLE exemplar (" +
                    "codigo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome VARCHAR(50) NOT NULL, " +
                    "qtdPaginas INTEGER NOT NULL)";

    private static final String CREATE_TABLE_LIVRO =
            "CREATE TABLE livro (" +
                    "exemplarCodigo INTEGER PRIMARY KEY, " +
                    "ISBN VARCHAR(13) NOT NULL, " +
                    "edicao INTEGER NOT NULL, " +
                    "FOREIGN KEY (exemplarCodigo) REFERENCES exemplar(codigo) " +
                    "ON DELETE CASCADE)";

    private static final String CREATE_TABLE_REVISTA =
            "CREATE TABLE revista (" +
                    "exemplarCodigo INTEGER PRIMARY KEY, " +
                    "ISSN VARCHAR(8) NOT NULL, " +
                    "FOREIGN KEY (exemplarCodigo) REFERENCES exemplar(codigo) " +
                    "ON DELETE CASCADE)";

    private static final String CREATE_TABLE_ALUNO =
            "CREATE TABLE aluno (" +
                    "RA INTEGER PRIMARY KEY, " +
                    "nome VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(50) NOT NULL)";

    private static final String CREATE_TABLE_ALUGUEL =
            "CREATE TABLE aluguel (" +
                    "exemplarCodigo INTEGER, " +
                    "alunoRA INTEGER, " +
                    "data_retirada VARCHAR(10) NOT NULL, " +
                    "data_devolucao VARCHAR(10), " +
                    "PRIMARY KEY (exemplarCodigo, alunoRA, data_retirada), " +
                    "FOREIGN KEY (exemplarCodigo) REFERENCES exemplar(codigo) " +
                    "ON DELETE RESTRICT, " +
                    "FOREIGN KEY (alunoRA) REFERENCES aluno(RA) " +
                    "ON DELETE RESTRICT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_EXEMPLAR);
            db.execSQL(CREATE_TABLE_LIVRO);
            db.execSQL(CREATE_TABLE_REVISTA);
            db.execSQL(CREATE_TABLE_ALUNO);
            db.execSQL(CREATE_TABLE_ALUGUEL);

            db.execSQL("CREATE INDEX idx_livro_exemplar ON livro(exemplarCodigo)");
            db.execSQL("CREATE INDEX idx_revista_exemplar ON revista(exemplarCodigo)");
            db.execSQL("CREATE INDEX idx_aluguel_exemplar ON aluguel(exemplarCodigo)");
            db.execSQL("CREATE INDEX idx_aluguel_aluno ON aluguel(alunoRA)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS aluguel");
            db.execSQL("DROP TABLE IF EXISTS livro");
            db.execSQL("DROP TABLE IF EXISTS revista");
            db.execSQL("DROP TABLE IF EXISTS exemplar");
            db.execSQL("DROP TABLE IF EXISTS aluno");

            onCreate(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}