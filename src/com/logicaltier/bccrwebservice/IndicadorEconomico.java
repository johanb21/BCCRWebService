package com.logicaltier.bccrwebservice;

import java.util.Calendar;

public class IndicadorEconomico {

	public final static String INDICADOR_VENTA = "318";
	public final static String INDICADOR_COMPRA = "317";
	public final static String FECHA_ACTUAL = getFechaActual();
	public final static String NOMBRE = "n/a";
	public final static String INDICADOR_NIVEL = "n";
	public final static String ERROR_CONEXION = "Sin Conexión";
	
	private static String getFechaActual() {
		Calendar calendar = Calendar.getInstance();
		String fechaActual = calendar.get(Calendar.DATE) 
				+ "/" + (calendar.get(Calendar.MONTH) + 1) 
				+ "/" + calendar.get(Calendar.YEAR);
		return fechaActual;
	}
}
