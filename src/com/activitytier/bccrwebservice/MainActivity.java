package com.activitytier.bccrwebservice;

import com.localservicetier.bccrwebservice.NotificationService;
import com.logicaltier.bccrwebservice.IndicadorEconomico;
import com.logicaltier.bccrwebservice.TipoCambioWebService;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView mFecha;
	private TextView mVenta;
	private TextView mCompra;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initializeComponents();
		new ConsumeWebServiceTask().execute();
	}

	private void initializeComponents() {
		mFecha = (TextView) findViewById(R.id.txtFecha);
		mVenta = (TextView) findViewById(R.id.txtVenta);;
		mCompra = (TextView) findViewById(R.id.txtCompra);;
		Intent intent = new Intent(this, NotificationService.class);
		startService(intent);
	}

	private class ConsumeWebServiceTask extends AsyncTask<Void, Void, String[]> {
	
		@Override
		protected void onPreExecute() {
			findViewById(R.id.lyLoading).setVisibility(View.VISIBLE);
			findViewById(R.id.lyMain).setVisibility(View.GONE);
		}

		@Override
		protected String[] doInBackground(Void... params) {
			String[] result = {TipoCambioWebService.getInstance().obtainTipoCambioVenta(), TipoCambioWebService.getInstance().obtainTipoCambioCompra()};
			return result;
		}

		@Override
		public void onPostExecute(final String[] result) {
			findViewById(R.id.lyLoading).setVisibility(View.GONE);
			mFecha.setText(IndicadorEconomico.FECHA_ACTUAL);
			mVenta.setText(result[0]);
			mCompra.setText(result[1]);
			findViewById(R.id.lyMain).setVisibility(View.VISIBLE);
		}
	}
}
