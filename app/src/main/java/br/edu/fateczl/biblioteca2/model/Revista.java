/*
 *@author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.biblioteca2.model;

public class Revista extends Exemplar {
    private String ISSN;

    public Revista() {
        super();
    }

    public Revista(int codigo, String nome, int qtdPaginas, String ISSN) {
        super(codigo, nome, qtdPaginas);
        this.ISSN = ISSN;
    }

    public String getISSN() {
        return ISSN;
    }

    public void setISSN(String ISSN) {
        this.ISSN = ISSN;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", ISSN: " + ISSN;
    }
}