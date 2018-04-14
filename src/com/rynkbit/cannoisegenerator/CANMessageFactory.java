package com.rynkbit.cannoisegenerator;

import de.fischl.usbtin.CANMessage;

import java.util.Random;

public class CANMessageFactory {
    private Random random = new Random();
    public CANMessage generateRandomMessage() {
        byte id = (byte) 0x2f;
        byte[] data = new byte[8];

        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (0x11 + i);
        }

        return new CANMessage(id, data);
    }
}
