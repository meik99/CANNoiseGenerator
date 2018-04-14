package com.rynkbit.cannoisegenerator;

import de.fischl.usbtin.CANMessage;
import de.fischl.usbtin.USBtin;
import de.fischl.usbtin.USBtinException;

import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static volatile boolean stop = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Thread t = new Thread(() -> {
            CANMessageFactory canMessageFactory = new CANMessageFactory();
            USBtin tin = new USBtin();

            try {
                tin.connect("/dev//ttyACM1");
                tin.openCANChannel(10000, USBtin.OpenMode.ACTIVE);
                while(stop == false) {
                    CANMessage canMessage = null;
                    canMessage = canMessageFactory.generateRandomMessage();
                    System.out.println("Sending " + Arrays.toString(canMessage.getData()));
                    tin.send(canMessage);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                tin.closeCANChannel();
                tin.disconnect();
            } catch (USBtinException e) {
                e.printStackTrace();
            }
        });
        t.setDaemon(false);
        t.start();

        scanner.nextLine();
        stop = true;

    }
}
