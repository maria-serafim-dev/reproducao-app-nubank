package com.example.nubank.model;

import java.io.Serializable;

public class Purchase implements Serializable {


    public Purchase(String nomeEmpresa, String dataCompra, String horarioCompra, int numeroCartao, double valorCompra, int qtdeParcelas, int tipoCompra, int id) {
        this.nomeEmpresa = nomeEmpresa;
        this.dataCompra = dataCompra;
        this.horarioCompra = horarioCompra;
        this.numeroCartao = numeroCartao;
        this.valorCompra = valorCompra;
        this.qtdeParcelas = qtdeParcelas;
        this.tipoCompra = tipoCompra;
        this.id = id;
        this.valorParcela = this.valorCompra / this.qtdeParcelas;
    }

    private String nomeEmpresa;
    private String dataCompra;
    private String horarioCompra;
    private int numeroCartao;
    private double valorCompra;
    private double valorParcela;
    private int qtdeParcelas;
    private int tipoCompra;
    private int id;

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(String dataCompra) {
        this.dataCompra = dataCompra;
    }

    public String getHorarioCompra() {
        return horarioCompra;
    }

    public void setHorarioCompra(String horarioCompra) {
        this.horarioCompra = horarioCompra;
    }

    public int getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(int numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public double getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(double valorCompra) {
        this.valorCompra = valorCompra;
    }

    public int getQtdeParcelas() {
        return qtdeParcelas;
    }

    public void setQtdeParcelas(int qtdeParcelas) {
        this.qtdeParcelas = qtdeParcelas;
    }

    public int getTipoCompra() {
        return tipoCompra;
    }

    public void setTipoCompra(int tipoCompra) {
        this.tipoCompra = tipoCompra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(double valorParcela) {
        this.valorParcela = valorParcela;
    }
}
