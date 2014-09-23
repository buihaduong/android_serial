package com.example.android_serial;

import android.util.Log;

public class PacketBuilder {
	
	private volatile short curSetVelocity; //set by user
	
	public PacketBuilder() {
		curSetVelocity = 0; 
	}
	
	public final byte[] buildBaseControlPacket(short velocity, short radius) { // Units = mm
		curSetVelocity = velocity;
    	byte[] go = new byte[10]; 
    	go[0] = (byte) 0xAA; // Header
    	go[1] = 0x55; // Header
    	go[2] = 0x06; // Length of payload
    	go[3] = 0x01; // Header of payload
    	go[4] = 0x04; // Length sub-payload
    	go[5] = (byte)(velocity & 0xff); // Byte 2 of 2 velocity
    	go[6] = (byte)((velocity >> 8) & 0xff); // Byte 1 of 2 velocity
    	go[7] = (byte)(radius & 0xff); // Byte 2 of 2 radius
    	go[8] = (byte)((radius >> 8) & 0xff); // Byte 1 of 2 radius
    	go[9] = (byte) (go[2]^go[3]^go[4]^go[5]^go[6]^go[7]^go[8]); // Checksum
		return go; // Send back go
	}
	
	public final byte[] buildSoundSequence(byte seq) {
		byte[] sound = new byte[7];
		sound[0] = (byte) 0xAA;
		sound[1] = 0x55;
		sound[2] = 0x03;
		sound[3] = 0x04;
		sound[4] = 0x01;
		sound[5] = seq;
		sound[6] = (byte) (sound[2]^sound[3]^sound[4]^sound[5]); // Checksum
		return sound;
	}
	
	public final byte[] buildSetPower(byte first, byte second) { //######!!!!!UNTESTED
		byte[] power = new byte[8];
		power[0] = (byte) 0xAA;
		power[1] = 0x55;
		power[2] = 0x04;
		power[3] = 0x08;
		power[4] = 0x02;
		power[5] = first; // not sure if this is right
		power[6] = second; // not sure if this is right
		power[7] = (byte) (power[2]^power[3]^power[4]^power[5]^power[6]);
		return power;
	}

	/**
	 * - Led1 Red    : 0x0100
	 * - Led1 Green  : 0x0200
	 * - Led1 Orange : 0x0300
	 * - Led2 Red    : 0x0400
	 * - Led2 Green  : 0x0800
	 * - Led2 Orange : 0x0c00
	 * @return
	 */
	public final byte[] buildLed(LedNumber number, LedColour coulour) {
		
		byte data = coulour.getLedColour();
		if (number == LedNumber.Led2)
			data = (byte) (data << 2);
		
		Log.i("Led colour", Byte.toString(data));
		
		byte[] led = new byte[8];
		led[0] = (byte) 0xAA;
		led[1] = 0x55;
		led[2] = 0x04;
		led[3] = 0x0C;
		led[4] = 0x02;
		led[5] = 0x00;
		led[6] = data;
		led[7] = (byte) (led[2]^led[3]^led[4]^led[5]^led[6]);
		
		return led;
	}
	
	/**
	 * @return the curSetVelocity
	 */
	public final short getCurSetVelocity() {
		return curSetVelocity;
	}	
}
