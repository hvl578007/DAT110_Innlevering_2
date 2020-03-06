package no.hvl.dat110.broker;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.common.TODO;
import no.hvl.dat110.messages.Message;
import no.hvl.dat110.common.Logger;
import no.hvl.dat110.messagetransport.Connection;

public class Storage {

	// data structure for managing subscriptions
	// maps from user to set of topics subscribed to by user
	protected ConcurrentHashMap<String, Set<String>> subscriptions;
	
	// data structure for managing currently connected clients
	// maps from user to corresponding client session object
	
	protected ConcurrentHashMap<String, ClientSession> clients;

	//task E message buffer
	// held på meldingar om den brukaren er fråkopla
	// mapper frå ein brukar til eit sett av meldingar
	// evt frå ein ClientSession?
	protected ConcurrentHashMap<String, Set<Message>> savedMessages;

	public Storage() {
		subscriptions = new ConcurrentHashMap<String, Set<String>>();
		clients = new ConcurrentHashMap<String, ClientSession>();
		savedMessages = new ConcurrentHashMap<String, Set<Message>>();
	}

	public Collection<ClientSession> getSessions() {
		return clients.values();
	}

	public Set<String> getTopics() {

		return subscriptions.keySet();

	}

	//task E
	//hent lagra meldingar på ein brukar (og slett dei frå mappet)
	public Set<Message> getSavedMessages(String user) {
		Set<Message> messages = savedMessages.get(user);
		savedMessages.remove(user);
		return messages;
	}

	//task E
	//lagarer ein melding på ein fråkopla brukar
	public void saveMessage(String user, Message msg) {
		Set<Message> messages = savedMessages.get(user);
		if (messages != null) {
			messages.add(msg);
		} else {
			messages = ConcurrentHashMap.newKeySet();
			savedMessages.put(user, messages);
			messages.add(msg);
		}
	}

	// get the session object for a given user
	// session object can be used to send a message to the user
	
	public ClientSession getSession(String user) {

		ClientSession session = clients.get(user);

		return session;
	}

	public Set<String> getSubscribers(String topic) {

		return (subscriptions.get(topic));

	}

	public void addClientSession(String user, Connection connection) {

		// TODO: add corresponding client session to the storage - ok
		//sjekke først?
		//if (!clients.containsKey(user))
		ClientSession session = new ClientSession(user, connection);
		this.clients.put(user, session);				
	}

	public void removeClientSession(String user) {

		// TODO: remove client session for user from the storage ok

		this.clients.remove(user);
		
	}

	public void createTopic(String topic) {

		// TODO: create topic in the storage ok

		if (!this.subscriptions.containsKey(topic)) {
			Set<String> subscribers = ConcurrentHashMap.newKeySet();
			this.subscriptions.put(topic, subscribers);	
		}
	}

	public void deleteTopic(String topic) {

		// TODO: delete topic from the storage - ok
		this.subscriptions.remove(topic);
		
	}

	public void addSubscriber(String user, String topic) {

		// TODO: add the user as subscriber to the topic - ok
		Set<String> subscribers = this.subscriptions.get(topic);
		if(subscribers != null) {
			subscribers.add(user);
		}
		
	}

	public void removeSubscriber(String user, String topic) {

		// TODO: remove the user as subscriber to the topic - ok
		Set<String> subscribers = this.subscriptions.get(topic);
		if(subscribers != null) {
			subscribers.remove(user);
		}
	}
}
