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
		Log.d(KobukiConstanst.BASE, "START SERVICE");
		
		UsbManager manager = (UsbManager) this.getSystemService(Context.USB_SERVICE);
		UsbSerialDriver driver = UsbSerialProber.acquire(manager);
		kobukiReceiver.setPacketRequest(new PacketRequest(driver));
		Log.d(KobukiConstanst.BASE, "GET SERIAL PORT");
		
		IntentFilter intentMove = new IntentFilter(KobukiConstanst.COMMAND_MOVE);
		IntentFilter intentLed = new IntentFilter(KobukiConstanst.COMMAND_LED);
		IntentFilter intentSound = new IntentFilter(KobukiConstanst.COMMAND_SOUND);
		
		registerReceiver(kobukiReceiver, intentMove);
		registerReceiver(kobukiReceiver, intentLed);
		registerReceiver(kobukiReceiver, intentSound);
		
		Log.d(KobukiConstanst.BASE, "REGISTER RECEIVER");
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(kobukiReceiver);
		Log.d(KobukiConstanst.BASE, "DESTROY SERVICE");
	}
}
