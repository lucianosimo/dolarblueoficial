package com.lucianosimo.dolar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.lucianosimo.dolar.pageradapter.TabsViewPagerFragmentActivity;

public class Splash extends Activity {
	
	private final int SPLASH_DISPLAY_LENGHT = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		new Handler().postDelayed(new Runnable() {
			
			public void run() {
				Intent intent = new Intent(Splash.this,TabsViewPagerFragmentActivity.class);
				Splash.this.startActivity(intent);
				Splash.this.finish();
			}
		}, SPLASH_DISPLAY_LENGHT);
	}
	
	

}
