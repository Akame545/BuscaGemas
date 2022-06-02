package com.alexmartin.buscagemas.entidades;

public class Score {
    private Integer ganar;
    private Integer tiempo;
    private Integer modo;
    private Integer cantidad_gemas;
    private Integer vidas_restantes;
    private Integer gemas_restantes;
    private String fecha;
    private Integer score;

    public Score(Integer ganar,Integer tiempo, Integer modo, Integer cantidad_gemas, Integer vidas_restantes, Integer gemas_restantes, String fecha, Integer score) {
        this.ganar = ganar;
        this.tiempo = tiempo;
        this.modo = modo;
        this.cantidad_gemas = cantidad_gemas;
        this.vidas_restantes = vidas_restantes;
        this.gemas_restantes = gemas_restantes;
        this.fecha = fecha;
        this.score = score;
    }

    public Score() {

    }

    public Integer getGanar() {
        return ganar;
    }

    public void setGanar(Integer ganar) {
        this.ganar = ganar;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }

    public Integer getModo() {
        return modo;
    }

    public void setModo(Integer modo) {
        this.modo = modo;
    }

    public Integer getCantidad_gemas() {
        return cantidad_gemas;
    }

    public void setCantidad_gemas(Integer cantidad_gemas) {
        this.cantidad_gemas = cantidad_gemas;
    }

    public Integer getVidas_restantes() {
        return vidas_restantes;
    }

    public void setVidas_restantes(Integer vidas_restantes) {
        this.vidas_restantes = vidas_restantes;
    }

    public Integer getGemas_restantes() {
        return gemas_restantes;
    }

    public void setGemas_restantes(Integer gemas_restantes) {
        this.gemas_restantes = gemas_restantes;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
