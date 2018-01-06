package com.postalweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.postalweb.dao.ClientDao;
import com.postalweb.model.Client;

public class ClientServiceImpl implements ClientService {
	
	@Override
	public Client leadcount(Client client) {
		// TODO Auto-generated method stub
		return clientDao.leadcount(client);
	}
	@Autowired
	ClientDao clientDao;
	@Override
	public Client addlead(Client client) {
		// TODO Auto-generated method stub
		return clientDao.addlead(client);
	}
	@Override
	public Client login(Client client) {
		// TODO Auto-generated method stub
		return clientDao.login(client);
	}
	@Override
	public Client delete(String clientid) {
		// TODO Auto-generated method stub
		return clientDao.delete(clientid);
	}
	@Override
	public Client delete(Client client) {
		// TODO Auto-generated method stub
		return clientDao.delete(client);
	}
	@Override
	public List<Client> list() {
		// TODO Auto-generated method stub
		return clientDao.list();
	}
	@Override
	public List<Client> atelist() {
		// TODO Auto-generated method stub
		return clientDao.atelist();
	}
	@Override
	public List<Client> complete() {
		// TODO Auto-generated method stub
		return clientDao.complete();
	}
	

}
