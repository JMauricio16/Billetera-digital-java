package com.billeteradigital.modelo;

public class Movimiento {
    private String tipo;
    private double monto;
    private String orden;

    //Constructor= se encarga de inicializar los atributos de la clase
    public Movimiento(String tipo, double monto, String orden){
        this.tipo=tipo;
        this.monto=monto;
        this.orden=orden;
    }

    // Getters= obtener el valor de los atributos
    public String getTipo() {
        return tipo;
    }

    public double getMonto(){
        return monto;
    }
    public String getOrden(){
        return orden;
    }
}
