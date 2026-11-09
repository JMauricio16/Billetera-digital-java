package com.billeteradigital.datos;
import com.billeteradigital.modelo.Movimiento;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

public class MovimientoRepositorio {
    // Se encarga de almacenar los datos en la sesion del usuario.
    private static final String  KEY_Saldo = "saldo";
    private static final String  KEY_Historial = "historial";

    //Inicializa la sesion con saldo y historial si no existen datos almacenados
    private void inicializarSesion(HttpSession session){
       if(session.getAttribute(KEY_Saldo) == null){
            session.setAttribute(KEY_Saldo, 250.00);
            session.setAttribute(KEY_Historial, new ArrayList<Movimiento>());
       }
    }  
    
    //Obtiene el saldo actual de la sesion del usuario
    public double obtenerSaldo(HttpSession session){
        inicializarSesion(session);
        return (double) session.getAttribute(KEY_Saldo);
    }

    //Obtiene el historial de movimientos de la sesion del usuario
    @SuppressWarnings ("unchecked")
    public List<Movimiento> listar(HttpSession session){
        inicializarSesion(session);
        return (List<Movimiento>) session.getAttribute(KEY_Historial);
    }

    //Agrega un nuevo movimiento al historial y actualiza el saldo de la sesion del usuario
    public void agregar(HttpSession session, String tipo,double monto){
        // Obtiene el historial de movimientos de la sesion
        List<Movimiento> historial = listar(session);
        // Obtiene el saldo actual de la sesion
        double saldoActual = obtenerSaldo(session);

        // Actualiza el saldo según el tipo de movimiento
        if("Recarga".equalsIgnoreCase(tipo)){
            saldoActual = saldoActual + monto;
        } else {
            saldoActual = saldoActual - monto;
        }

        // Agrega el nuevo movimiento al historial y actualiza la sesion
        if (!historial.isEmpty()){
            Movimiento previo = historial.get(0);
            historial.set(0, new Movimiento( previo.getTipo(),previo.getMonto(),"Anterior"));
        }

        // Agrega el nuevo movimiento al inicio del historial
        historial.add(0, new Movimiento(tipo, monto, "Más reciente"));

        // Actualiza la sesion con el nuevo saldo y el historial actualizado
        session.setAttribute(KEY_Saldo, saldoActual);
        session.setAttribute(KEY_Historial, historial);
    }
}
