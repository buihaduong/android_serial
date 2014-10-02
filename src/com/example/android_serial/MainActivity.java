package com.example.android_serial;

import java.io.IOException;
import java.net.Socket;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

		InitButton();
		Intent intent = new Intent(this, KobukiService.class);
		startService(intent);
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

	public void broadcastIntent(View view) {
		Intent intent = new Intent("robotbase.action.MOVE");
		intent.putExtra("command", "GO");
		sendBroadcast(intent);
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
			default:
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Runnable mUpdateTaskSendCommand = new Runnable() {
		public void run() {
			Bundle data = new Bundle();
			int data_control = 0;
			switch (currentButton.getId()) {
			case R.id.btnUp:
				// packetRequest.baseControl(v, 0);
				data_control = KobukiCommand.FORWARD.ordinal();
				break;
			case R.id.btnDown:
				// packetRequest.baseControl(-v, 0);
				data_control = KobukiCommand.BACKWARD.ordinal();
				break;
			case R.id.btnLeft:
				// packetRequest.baseControl(v, v);
				data_control = KobukiCommand.LEFT.ordinal();
				break;
			case R.id.btnRight:
				// packetRequest.baseControl(v, -v);
				data_control = KobukiCommand.RIGHT.ordinal();
				break;
			default:
				break;
			}
			data.putInt(KobukiConstanst.COMMAND_KEY, data_control);
			Intent intent = new Intent(KobukiConstanst.COMMAND_MOVE);
			intent.putExtra(KobukiConstanst.BUNDLE_KEY, data);
			sendBroadcast(intent);
			
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
