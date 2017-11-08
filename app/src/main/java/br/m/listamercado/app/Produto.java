package br.m.listamercado.app;

import com.orm.SugarRecord;

/**
 * Created by 16254851 on 25/10/2017.
 */

public class Produto extends SugarRecord{

    //Declaração de variaveis
    private String nome;
    private boolean ativo;

    //Construtores obrigatório por causa do ORM
    public Produto(){}

    public Produto(String nome, boolean ativo){
        this.nome = nome;
        this.ativo = ativo;
    }

    //Get and Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
