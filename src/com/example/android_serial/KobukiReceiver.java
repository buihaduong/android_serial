package com.example.android_serial;

import java.io.IOException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class KobukiReceiver extends BroadcastReceiver {

	private PacketRequest PacketRequest;

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle data = intent.getBundleExtra(KobukiConstanst.BUNDLE_KEY);
		KobukiCommand command = (KobukiCommand) KobukiCommand.values()[data.getInt(KobukiConstanst.COMMAND_KEY)];
		Log.d(KobukiConstanst.BASE, command.getCommand());

		int v = 70;
		try {
			switch (command) {
			case FORWARD:
				PacketRequest.baseControl(v, 0);
				break;
			case BACKWARD:
				PacketRequest.baseControl(-v, 0);
				break;
			case LEFT:
				PacketRequest.baseControl(v, v);
				break;
			case RIGHT:
				PacketRequest.baseControl(v, -v);
				break;
			default:
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PacketRequest getPacketRequest() {
		return PacketRequest;
	}

	public void setPacketRequest(PacketRequest packetRequest) {
		PacketRequest = packetRequest;
	}

}
