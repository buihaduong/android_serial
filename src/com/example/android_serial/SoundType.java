package com.example.android_serial;

public enum SoundType {
	On((byte)0x0),
	Off((byte)0x1),
	Recharge((byte)0x2),
	Button((byte)0x3),
	Error((byte)0x4),
	CleaningStart((byte)0x5),
	CleaningEnd((byte)0x6);
	
	private byte Sound;
	
	SoundType(byte sound) {
		this.setSound(sound);
	}

	public byte getSound() {
		return Sound;
	}

	public void setSound(byte sound) {
		Sound = sound;
	}	
}
