package com.example.android_serial;

import java.io.IOException;
import java.net.Socket;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements
		Button.OnTouchListener {

	TextView txt;
	private PacketRequest packetRequest;
	private int v = 0;
	private SeekBar vBar;
	private Handler mHandler = new Handler();
	private Button currentButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txt = (TextView) findViewById(R.id.txtTest);
		txt.setText("12345");

		vBar = (SeekBar) findViewById(R.id.velocity);
		vBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				v = progress;
				txt.setText(Integer.toString(v));
			}
		});

		UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
		UsbSerialDriver driver = UsbSerialProber.acquire(manager);
		packetRequest = new PacketRequest(driver);
		
		InitButton();
	}

	public void InitButton() {
		Button btnUp = (Button) findViewById(R.id.btnUp);
		Button btnDown = (Button) findViewById(R.id.btnDown);
		Button btnLeft = (Button) findViewById(R.id.btnLeft);
		Button btnRight = (Button) findViewById(R.id.btnRight);

		btnUp.setOnTouchListener(this);
		btnDown.setOnTouchListener(this);
		btnLeft.setOnTouchListener(this);
		btnRight.setOnTouchListener(this);
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
		if (packetRequest == null)
			return;
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
			case R.id.led2_green:
				packetRequest.setLed(LedNumber.Led2, LedColour.Green);
				break;
			case R.id.led2_orange:
				packetRequest.setLed(LedNumber.Led2, LedColour.Orange);
				break;
			case R.id.led2_red:
				packetRequest.setLed(LedNumber.Led2, LedColour.Red);
				break;
			case R.id.led2_black:
				packetRequest.setLed(LedNumber.Led2, LedColour.Black);
				break;
//			case R.id.btnUp:
//				packetRequest.baseControl(v, 0);
//				break;
//			case R.id.btnDown:
//				packetRequest.baseControl(-v, 0);
//				break;
//			case R.id.btnLeft:
//				packetRequest.baseControl(v, v);
//				break;
//			case R.id.btnRight:
//				packetRequest.baseControl(v, -v);
//				break;
			default:
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Runnable mUpdateTaskSendCommand = new Runnable() {
		public void run() {
			if (packetRequest == null)
			{
				v++;
				txt.setText(Integer.toString(v));
				return;
			}
			try {
				switch (currentButton.getId()) {
				case R.id.btnUp:
					packetRequest.baseControl(v, 0);
					break;
				case R.id.btnDown:
					packetRequest.baseControl(-v, 0);
					break;
				case R.id.btnLeft:
					packetRequest.baseControl(v, v);
					break;
				case R.id.btnRight:
					packetRequest.baseControl(v, -v);
					break;
				default:
					break;
				}
			} catch (IOException ex) {
			}
			mHandler.post(this);
		}
	};

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Button clickedBtn = (Button) v;
		currentButton = clickedBtn;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.i("Button", "MotionEvent.ACTION_DOWN "
					+ clickedBtn.getText().toString());
			mHandler.post(mUpdateTaskSendCommand);
			break;
		case MotionEvent.ACTION_UP:
			Log.i("Button", "MotionEvent.ACTION_UP "
					+ clickedBtn.getText().toString());
			mHandler.removeCallbacks(mUpdateTaskSendCommand);
			break;
		}
		return false;
	}
}
