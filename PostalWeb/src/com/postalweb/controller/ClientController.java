package com.postalweb.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.postalweb.model.Error;
import com.postalweb.model.Client;
import com.postalweb.service.ClientService;

@Controller
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	

	@RequestMapping(value = "/addlead", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<Client> loglast(@RequestBody Client client) {
			System.out.println("beatlogin_query:sdfcvgbhnnm"+client);
		    Client idj = clientService.addlead(client);
		    
			return new ResponseEntity<Client>(idj, HttpStatus.OK);
		}
   
	/*@RequestMapping(value = "/login", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<Client> login(@RequestBody Client client) {
			System.out.println("beatlogin_query:sdfcvgbhnnm"+client);
		    Client idj = clientService.login(client);
		    System.out.println("beatlogin_query:sdfcvgbhnnm"+client);
			return new ResponseEntity<Client>(idj, HttpStatus.OK);
		}*/
	@RequestMapping(value = "/delete", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<Client> delete(@RequestParam String clientid) {
			System.out.println("beatlogin_query:sdfcvgbhnnm"+clientid);
		    Client idj = clientService.delete(clientid);
		    
			return new ResponseEntity<Client>(idj, HttpStatus.OK);
		}
	/*@RequestMapping(value = "/logout", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<Client> logout(@RequestParam String clientid) {
			System.out.println("beatlogin_query:sdfcvgbhnnm"+clientid);
		    Client idj = clientService.delete(clientid);
		    
			return new ResponseEntity<Client>(idj, HttpStatus.OK);
		}*/
	@RequestMapping(value = "/deleter", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<Client> deleter(@RequestBody Client client) {
			System.out.println("beatlogin_query:sdfcvgbhnnm"+client);
		    Client idj = clientService.delete(client);
		    System.out.println("beatlogin_query:sdfcvgbhnnm"+client);
			return new ResponseEntity<Client>(idj, HttpStatus.OK);
		}
	@RequestMapping(value = "/logout", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> logout(HttpSession session){
		String username=(String)session.getAttribute("clientname");
		System.out.println("Name of the user is"+ username);
		if(username==null){
			Error error=new Error(6,"Unauthorized access..please login..");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		
		
		session.removeAttribute("username");
		
		return new ResponseEntity<Client>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody Client client,HttpSession session){
		Client validUser=clientService.login(client);
		if(validUser==null){
			Error error=new Error(4," Invalid Username/Password...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
	
	
		session.setAttribute("clientname", validUser.getClientname());
		return new ResponseEntity<Client>(validUser,HttpStatus.OK);
	}
	@RequestMapping(value="/getlist",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getlist(HttpSession session){
		/*String clientname=(String)session.getAttribute("clientname");
		 if(clientname==null){
			Error error=new Error(6,"Unauthorized access...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}*/
		 List<Client> list=clientService.list();
		 return new ResponseEntity<List<Client>>(list,HttpStatus.OK);
}
	@RequestMapping(value="/getatelist",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getatelist(HttpSession session){
		/*String clientname=(String)session.getAttribute("clientname");
		 if(clientname==null){
			Error error=new Error(6,"Unauthorized access...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}*/
		 List<Client> atelist=clientService.atelist();
		 return new ResponseEntity<List<Client>>(atelist,HttpStatus.OK);
}
	@RequestMapping(value="/complete",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> complete(HttpSession session){
		/*String clientname=(String)session.getAttribute("clientname");
		 if(clientname==null){
			Error error=new Error(6,"Unauthorized access...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}*/
		 List<Client> complete=clientService.complete();
		 return new ResponseEntity<List<Client>>(complete,HttpStatus.OK);
}
}
