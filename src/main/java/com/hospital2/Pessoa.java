package com.hospital2;
import java.io.Serializable;

public class Pessoa implements Serializable {
    private String nome;
    private String cpf;
    private int idade;
    private char Genero;
    private String endereco;
    private String Celular;
    private int ficha;
    private ListaEncadeada historico = new ListaEncadeada();
    private String Planodesaude;
    private String Grupo_Sanguíneo;
    private EstadoPaciente estado;


public Pessoa(String nome, String cpf, int idade, char Genero, String endereco, String Celular, int ficha, ListaEncadeada historico, String Planodesaude, String Grupo_Sanguíneo, EstadoPaciente estado){
    this.nome = nome;
    this.cpf = cpf;
    this.idade = idade;
    this.Genero = Genero;
    this.endereco = endereco;
    this.Celular = Celular;
    this.ficha = ficha;
    this.historico = historico;
    this.Planodesaude = Planodesaude;
    this.Grupo_Sanguíneo = Grupo_Sanguíneo;
    this.estado = estado;
}

    public Pessoa() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public char getGenero() {
        return Genero;
    }

    public void setGenero(char genero) {
        Genero = genero;
    }

    public int getFicha() {
        return ficha;
    }

    public void setFicha(int ficha) {
        this.ficha = ficha;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }

    public EstadoPaciente getEstado() {
        return estado;
    }

    public void setEstado(EstadoPaciente estado) {
        this.estado = estado;
    }

    public String getPlanodesaude() {
        return Planodesaude;
    }

    public void setPlanodesaude(String planodesaude) {
        Planodesaude = planodesaude;
    }

    public String getGrupo_Sanguíneo() {
        return Grupo_Sanguíneo;
    }

    public void setGrupo_Sanguíneo(String grupo_Sanguíneo) {
        Grupo_Sanguíneo = grupo_Sanguíneo;
    }

    public ListaEncadeada getHistorico() {
        return historico;
    }

    public void setHistorico(ListaEncadeada historico) {
        this.historico = historico;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", idade=" + idade +
                ", Genero=" + Genero +
                ", endereco='" + endereco + '\'' +
                ", Celular='" + Celular + '\'' +
                ", ficha=" + ficha +
                ", historico=" + historico +
                ", Planodesaude='" + Planodesaude + '\'' +
                ", Grupo_Sanguíneo='" + Grupo_Sanguíneo + '\'' +
                ", estado=" + estado +
                '}';
    }
}

