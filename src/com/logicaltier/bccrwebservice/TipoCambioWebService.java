package com.logicaltier.bccrwebservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.util.Log;

public class TipoCambioWebService {

	// Ubicaciones URL para consumir webservice del BCCR
	private final String nameSpace = "http://ws.sdde.bccr.fi.cr";
	private final String url = "http://indicadoreseconomicos.bccr.fi.cr/indicadoreseconomicos/WebServices/wsIndicadoresEconomicos.asmx";
	private final String methodName = "ObtenerIndicadoresEconomicosXML";
	private final String soapAction = "http://ws.sdde.bccr.fi.cr/ObtenerIndicadoresEconomicosXML";

	// Parametros para el consumo de webservice
	private final String indicador = "tcIndicador";
	private final String fechaInicio = "tcFechaInicio";
	private final String fechaFinal = "tcFechaFinal";
	private final String nombre = "tcNombre";
	private final String subNivel = "tnSubNiveles";

	// Patrón Singleton
	private static TipoCambioWebService instance;

	private TipoCambioWebService() { }

	public String obtainTipoCambioVenta() {
		SoapObject request = new SoapObject(nameSpace, methodName);
		addProperties(request, IndicadorEconomico.INDICADOR_VENTA);
		String tipoCambio = getResponse(request);
		return tipoCambio;
	}

	public String obtainTipoCambioCompra() {
		SoapObject request = new SoapObject(nameSpace, methodName);
		addProperties(request, IndicadorEconomico.INDICADOR_COMPRA);
		String tipoCambio = getResponse(request);
		return tipoCambio;
	}

	private String getResponse(SoapObject request) {
		String result = IndicadorEconomico.ERROR_CONEXION;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
		try {
			Log.d("getResponse", "Am being consumed");
			androidHttpTransport.call(soapAction, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			result = XMLParser.parseDocument(response.toString());
		} catch (Exception e) {
			Log.e("doInBackground", e.toString());
		}
		return result;
	}

	private void addProperties(SoapObject request, String valorIndicador) {
		PropertyInfo propertyIndicador = new PropertyInfo();
		propertyIndicador.setName(indicador);
		propertyIndicador.setValue(valorIndicador);
		propertyIndicador.setType(String.class);
		PropertyInfo propertyFechaInicio = new PropertyInfo();
		propertyFechaInicio.setName(fechaInicio);
		propertyFechaInicio.setValue(IndicadorEconomico.FECHA_ACTUAL);
		propertyFechaInicio.setType(String.class);
		PropertyInfo propertyFechaFinal = new PropertyInfo();
		propertyFechaFinal.setName(fechaFinal);
		propertyFechaFinal.setValue(IndicadorEconomico.FECHA_ACTUAL);
		propertyFechaFinal.setType(String.class);
		PropertyInfo propertyNombre = new PropertyInfo();
		propertyNombre.setName(nombre);
		propertyNombre.setValue(IndicadorEconomico.NOMBRE);
		propertyNombre.setType(String.class);
		PropertyInfo propertySubNivel = new PropertyInfo();
		propertySubNivel.setName(subNivel);
		propertySubNivel.setValue(IndicadorEconomico.INDICADOR_NIVEL);
		propertySubNivel.setType(String.class);
		// Properties are added to the request object
		request.addProperty(propertyIndicador);
		request.addProperty(propertyFechaInicio);
		request.addProperty(propertyFechaFinal);
		request.addProperty(propertyNombre);
		request.addProperty(propertySubNivel);
	}

	public static TipoCambioWebService getInstance() {
		if(instance == null) {
			instance = new TipoCambioWebService();
		}
		return instance;
	}
}
