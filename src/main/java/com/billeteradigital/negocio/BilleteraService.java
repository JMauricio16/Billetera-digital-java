package com.billeteradigital.negocio;

import com.billeteradigital.datos.MovimientoRepositorio;
import com.billeteradigital.modelo.Movimiento;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.ArrayList;

public class BilleteraService {
    // crea una variable para interactuar con la capa de datos 
    private final MovimientoRepositorio repositorio;

    // recibe la capa de datos para permitir la conexión entre capas
    public BilleteraService(MovimientoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    //Se encarga de procesar el movimiento
    public List<String> procesarMovimiento(HttpSession session, String tipo, String montoTexto) {
        //Valida si los datos son correctos
        List<String> errores = new ArrayList<>();

        // Limpia el tipo de movimiento para evitar errores de validación
        String tipoLimpio = limpiar(tipo);
        // Valida si el tipo de movimiento es válido
        if (!"Recarga".equals(tipoLimpio) && !"Gasto".equals(tipoLimpio)) {
            errores.add("Debe seleccionar un tipo de movimiento válido.");
        }
        // Convierte el monto a Double y valida si es válido
        Double monto = aDouble(montoTexto);
        // Valida si el monto esta vacio  o es menor o igual a cero
        if (monto == null) {
            errores.add("El monto no puede estar vacío ");
        } else if (monto <= 0) {
            errores.add("El monto debe ser mayor a cero.");
        }

        // Valida si el saldo es suficiente para realizar el gasto
        double saldoActual = repositorio.obtenerSaldo(session);
        // Valida si el tipo de movimiento es "Gasto" y si el monto es mayor al saldo actual
        if (monto != null && "Gasto".equals(tipoLimpio) && monto > saldoActual) {
            errores.add("Saldo insuficiente para realizar el gasto.");
        }

        // Si hay errores, retorna la lista de errores
        if (!errores.isEmpty()) {
            return errores; 
        }

        // Si no hay errores, agrega el movimiento al historial y actualiza el saldo
        repositorio.agregar(session, tipoLimpio, monto);
        return errores;
    }

    //Obtiene el saldo actual de la sesion del usuario
    public double obtenerSaldo(HttpSession session) {
        return repositorio.obtenerSaldo(session);
    }

    //Obtiene el historial de movimientos de la sesion del usuario
    public List<Movimiento> listar(HttpSession session) {
        return repositorio.listar(session);
    }

    // Limpia el texto eliminando espacios en blanco al inicio y al final
    private static String limpiar(String texto) {
        return texto == null ? "" : texto.trim();
    }

    // Convierte el texto a Double, si no es posible retorna null
    private static Double aDouble(String texto) {
        if (texto == null) {
            return null;
        }
        try {
            return Double.valueOf(texto.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
