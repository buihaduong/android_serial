package com.example.android_serial;

import java.io.IOException;

import com.hoho.android.usbserial.driver.UsbSerialDriver;

public class PacketRequest {
	private UsbSerialDriver driver;
	private PacketBuilder packetBuilder;

	public PacketRequest(UsbSerialDriver driver) {
		this.driver = driver;
		packetBuilder = new PacketBuilder();

		openPort();
	}

	public void openPort() {
		try {
			if (driver != null) {
				driver.open();
				driver.setBaudRate(115200);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closePort() {
		if (driver != null)
			try {
				driver.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public void setLed(LedNumber number, LedColour colour) throws IOException {
		driver.write(packetBuilder.buildLed(number, colour),8);
	}
}
