package com.example.nubank.model;

public class Purchase {

    public Purchase(String nomeEmpresa, String dataCompra, String horarioCompra, int numeroCartao, double valorCompra, int qtdeParcelas, int tipoCompra) {
        this.nomeEmpresa = nomeEmpresa;
        this.dataCompra = dataCompra;
        this.horarioCompra = horarioCompra;
        this.numeroCartao = numeroCartao;
        this.valorCompra = valorCompra;
        this.qtdeParcelas = qtdeParcelas;
        this.tipoCompra = tipoCompra;
    }

    private String nomeEmpresa;
    private String dataCompra;
    private String horarioCompra;
    private int numeroCartao;
    private double valorCompra;
    private int qtdeParcelas;
    private int tipoCompra;

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
}
