package com.alexmartin.buscagemas.utilidades;

public class Utilidades {

    public static final String TABLA_SCORE = "score";
    public static final String CAMPO_GANAR = "ganar";
    public static final String CAMPO_TIEMPO = "tiempo";
    public static final String CAMPO_MODO = "modo";
    public static final String CAMPO_CANTIDAD_GEMAS = "cantidad_gemas";
    public static final String CAMPO_VIDAS_RESTANTES = "vidas_restantes";
    public static final String CAMPO_GEMAS_RESTANTES = "gemas_restantes";
    public static final String CAMPO_FECHA = "fecha";
    public static final String CAMPO_SCORE = "puntuacion";

    public static final String CREAR_TABLA_SCORE = "CREATE TABLE "+TABLA_SCORE+" ("+CAMPO_GANAR+" INTEGER, "+CAMPO_TIEMPO+" INTEGER, "+CAMPO_MODO+" INTEGER, "+CAMPO_CANTIDAD_GEMAS+" INTEGER, "+CAMPO_VIDAS_RESTANTES+" INTEGER, "+CAMPO_GEMAS_RESTANTES+" INTEGER, "+CAMPO_FECHA+" TEXT, "+CAMPO_SCORE+" INTEGER)";


}
