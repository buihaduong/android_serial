package com.example.android_serial;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;
import android.os.IBinder;
import android.util.Log;

public class KobukiService extends Service {

	private final KobukiReceiver kobukiReceiver = new KobukiReceiver();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.d("robotbase.action.MOVE", "START SERVICE");
		
		UsbManager manager = (UsbManager) this.getSystemService(Context.USB_SERVICE);
		UsbSerialDriver driver = UsbSerialProber.acquire(manager);
		kobukiReceiver.setPacketRequest(new PacketRequest(driver));
		Log.d("robotbase.action.MOVE", "GET SERIAL PORT");
		
		IntentFilter intentFilter = new IntentFilter("robotbase.action.MOVE");
		registerReceiver(kobukiReceiver, intentFilter);
		Log.d("robotbase.action.MOVE", "REGISTER RECEIVER");
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(kobukiReceiver);
		Log.d("robotbase.action.MOVE", "DESTROY SERVICE");
	}
}
