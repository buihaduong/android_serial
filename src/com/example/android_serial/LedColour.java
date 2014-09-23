package com.example.android_serial;

public enum LedColour {
	
	Black((byte)0x00),
	Red((byte)0x01),
	Green((byte)0x02),
	Orange((byte)0x03);
	
	LedColour(byte colour)
	{
		this.setLedColour(colour);
	}
	
	public byte getLedColour() {
		return LedColour;
	}

	public void setLedColour(byte ledColour) {
		LedColour = ledColour;
	}

	private byte LedColour;
}
