package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;
import no.hvl.dat110.messages.Message;
import no.hvl.dat110.messages.PublishMsg;

public class DisplayDevice {
	
	private static final int COUNT = 10;
		
	public static void main (String[] args) {
		
		System.out.println("Display starting ...");
		
		// TODO - START
				
		// create a client object and use it to
		Client client = new Client("display", Common.BROKERHOST, Common.BROKERPORT);
		// - connect to the broker
		if (client.connect()) {
			client.createTopic(Common.TEMPTOPIC);
			client.subscribe(Common.TEMPTOPIC);

			for (int i = 0; i < COUNT; i++) {
				Message message = client.receive();
				PublishMsg msg = null;
				if (message instanceof PublishMsg) {
					msg = (PublishMsg) message;
				}
				System.out.println("DISPLAY: " + msg.getMessage());
			}
			client.unsubscribe(Common.TEMPTOPIC);

			client.disconnect();
		}
		// - create the temperature topic on the broker
		// - subscribe to the topic
		// - receive messages on the topic
		// - unsubscribe from the topic
		// - disconnect from the broker
		
		// TODO - END
		
		System.out.println("Display stopping ... ");
				
	}
}
