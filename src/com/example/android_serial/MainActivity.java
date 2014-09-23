package com.example.android_serial;

import java.io.IOException;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	TextView txt;
	private PacketRequest packetRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txt = (TextView) findViewById(R.id.txtTest);
		txt.setText("12345");

		UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
		UsbSerialDriver driver = UsbSerialProber.acquire(manager);
		packetRequest = new PacketRequest(driver);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void buttonClicked(View view) {
		Button btn = (Button) view;
		try {
			switch (btn.getId()) {
			case R.id.led1_green:
				packetRequest.setLed(LedNumber.Led1, LedColour.Green);
				break;
			case R.id.led1_orange:
				packetRequest.setLed(LedNumber.Led1, LedColour.Orange);
				break;
			case R.id.led1_red:
				packetRequest.setLed(LedNumber.Led1, LedColour.Red);
				break;
			case R.id.led1_black:
				packetRequest.setLed(LedNumber.Led1, LedColour.Black);
				break;
			default:
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
