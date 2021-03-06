package com.postalweb.service;

import java.util.List;

import com.postalweb.model.Client;

public interface ClientService {
	public Client addlead(Client client);
	public Client login(Client client);
	public Client delete(String clientid);
	public Client delete(Client client);
	public Client leadcount(Client client);
	public List<Client> list();
	public List<Client> atelist();
	public List<Client> complete();
}
