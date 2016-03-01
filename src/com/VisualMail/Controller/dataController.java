package com.VisualMail.Controller;
import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.VisualMail.Model.Container;
import com.VisualMail.Model.MensajeVO;
import com.VisualMail.Model.Node;
import com.VisualMail.Model.UserVO;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


@Controller
@RequestMapping("/data")
public class dataController {
		@Autowired
	  	private Container containerVM;
		int sessionVM=0;
		Node Root;
		UserVO userActive;
		@RequestMapping(value = "/init", method = RequestMethod.POST, headers = {"Content-type=application/json"})
		public @ResponseBody Node CargarInJSON(HttpSession session) {
			
			userActive= (UserVO)session.getAttribute("account");
			if (containerVM.getTreads().size()>0)
			{				
				// session;
			Root=containerVM.getTreads().get(0);
			}else
			{			
				
			
				 /*
				 
				  Node node = new Node(1,"Felipe","?","yellow","¿mejor plataforma de desarrollo web?",0,0,0);
				  Root=node;
				  containerVM.setCountNodes(1);
				  containerVM.addChildren(Root);
				  */
			}		
			
			sessionVM=0;
			return Root;
		}
		@RequestMapping(method = RequestMethod.GET)
		public @ResponseBody Node CargarInJSONGET(HttpSession session) {
			return Root;
		}
		
		
		@RequestMapping(value = "/send", method = RequestMethod.POST, headers = {"Content-type=application/json"})
		public @ResponseBody Node sendNodeJSON(@RequestBody MensajeVO Mensaje) throws JsonGenerationException, JsonMappingException, IOException
		{
		
		
	    int parent =Mensaje.getParent();
	    int iduser =Mensaje.getIduser();
		String color = Mensaje.getColor();
		String name= Mensaje.getName();
		String element=Mensaje.getElement();
		String mensaje =Mensaje.getMensaje();
	   
	    color = "#" + color;
		if (containerVM.getTreads().size() > 0) {
			if (sessionVM == 0) {
				containerVM.setCountSessions(containerVM.getCountSessions() + 1);
				sessionVM = containerVM.getCountSessions();
			} else {
				sessionVM = containerVM.getCountSessions();
				if ((containerVM.getNodeR(parent, Root).getSession() == sessionVM)) {
					containerVM.setCountSessions(containerVM.getCountSessions() + 1);
				} else {
					if ((sessionVM - containerVM.getNodeR(parent, Root).getSession()) >= 1) {
						if  (! new String(containerVM.getNodeR(containerVM.getCountNodes(), Root).getName()).equals(name))  {
							containerVM.setCountSessions(containerVM.getCountSessions() + 1);
						}else
						{
							
						}

					}
				}

				sessionVM = containerVM.getCountSessions();
				// session++;

			}
					  
			  containerVM.setCountNodes(containerVM.getCountNodes()+1);
			  int nivel=containerVM.getNodeR(parent,Root).getNivel()+1;
			  UserVO userOwner= containerVM.getListUser().findUser(iduser);
			  Node nodoNuevo = new Node(containerVM.getCountNodes(),name,element,color,mensaje,parent,nivel,10,sessionVM,userOwner);			  
			  containerVM.getNodeR(parent,Root).addChildren(nodoNuevo);	
			  containerVM.addChildren(Root);				  
			}else
			{				
				 
				 //revisar
				  UserVO userOwner= containerVM.getListUser().findUser(iduser);
				  Node node = new Node(1,name,element,color,mensaje,parent,1,10,0,userOwner);
				  Root=node;
				  containerVM.setCountNodes(1);
				  containerVM.addChildren(Root);
			    //  ObjectMapper mapper = new ObjectMapper();
				 // mapper.writeValue(new File("visualMailLog.json"), Root);
				  
				  
			}			
				
			  return Root;

		}
		@RequestMapping("/recv")
		public @ResponseBody Node recvNodeJSON() throws JsonGenerationException, JsonMappingException, IOException
		{
			
			  Root=containerVM.getTreads().get(0);
			  return Root;

		}
		@RequestMapping("/saveconf")
		//public @ResponseBody Node InserNodeJSON(String name,String element, String color,String Padre)
		public @ResponseBody Node saveConfJSON() throws JsonGenerationException, JsonMappingException, IOException
		{
			
				Root=containerVM.getTreads().get(0);
			    ObjectMapper mapper = new ObjectMapper();
			    Root.setCurrentSessionThread(sessionVM+1);
				mapper.writeValue(new File("visualMailLog.json"), Root);
				
			    return Root;

		}
		@RequestMapping("/loadconf")
		//public @ResponseBody Node InserNodeJSON(String name,String element, String color,String Padre)
		public @ResponseBody Node loadConfJSON() throws JsonGenerationException, JsonMappingException, IOException
		{
			    ObjectMapper mapper = new ObjectMapper();
				containerVM.getTreads().clear();		    
			    Root= mapper.readValue(new File("visualMailLog.json"), Node.class );			    
			    containerVM.CountNodes(Root);
			    containerVM.setCountNodes(Root.getdescendants()+1);			  
			    containerVM.addChildren(Root);
			    containerVM.setCountSessions(Root.getCurrentSessionThread());  
			    return Root;

		}
		@RequestMapping("/clear")
		//public @ResponseBody Node InserNodeJSON(String name,String element, String color,String Padre)
		public @ResponseBody Node clearNodeJSON() throws JsonGenerationException, JsonMappingException, IOException
		{
			containerVM.setCountSessions(0);
			containerVM.getTreads().clear();
			Root =null;
			return Root;

		}
		
		
		
		
	
		
		
	}