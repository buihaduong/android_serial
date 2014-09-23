package com.example.android_serial;

public enum LedNumber {
	Led1(0),
	Led2(1);
	
	private int LedNumber;
	
	LedNumber(int number)
	{
		this.setLedNumber(number);
	}

	public int getLedNumber() {
		return LedNumber;
	}

	public void setLedNumber(int ledNumber) {
		LedNumber = ledNumber;
	}
}
