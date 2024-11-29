/*
 *@author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.biblioteca2.model;

public class Aluguel {
    private int exemplarCodigo;
    private int alunoRA;
    private String dataRetirada;
    private String dataDevolucao;

    public Aluguel() {}

    public Aluguel(int exemplarCodigo, int alunoRA, String dataRetirada, String dataDevolucao) {
        this.exemplarCodigo = exemplarCodigo;
        this.alunoRA = alunoRA;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = dataDevolucao;
    }

    public int getExemplarCodigo() {
        return exemplarCodigo;
    }

    public void setExemplarCodigo(int exemplarCodigo) {
        this.exemplarCodigo = exemplarCodigo;
    }

    public int getAlunoRA() {
        return alunoRA;
    }

    public void setAlunoRA(int alunoRA) {
        this.alunoRA = alunoRA;
    }

    public String getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(String dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    @Override
    public String toString() {
        return "Exemplar: " + exemplarCodigo +
                ", Aluno RA: " + alunoRA +
                ", Retirada: " + dataRetirada +
                ", Devolução: " + dataDevolucao;
    }
}