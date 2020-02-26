package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;

public class TemperatureDevice {

    private static final int COUNT = 10;

    public static void main(String[] args) {

        // simulated / virtual temperature sensor
        TemperatureSensor sn = new TemperatureSensor();

        // TODO - start
        System.out.println("Temperature device starting ...");

        // create a client object and use it to
        Client client = new Client("temperaturesensor", Common.BROKERHOST, Common.BROKERPORT);

        // - connect to the broker
        if (client.connect()) {

            for (int i = 0; i < COUNT; i++) {
                int temp = sn.read();
                System.out.println("READING: " + temp);
                client.publish(Common.TEMPTOPIC, Integer.toString(temp));

                // venter i 1 sekund mellom kvar lesing
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
			}

			client.disconnect();
		}
		// - publish the temperature(s)
		// - disconnect from the broker

		// TODO - end

		System.out.println("Temperature device stopping ... ");

	}
}
