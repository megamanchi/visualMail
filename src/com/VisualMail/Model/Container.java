package com.VisualMail.Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class Container {
	private String name;
	private int CountNodes=0; 
	private  UserListVO ListUser = new UserListVO();
	

	
	public UserListVO getListUser() {
		return ListUser;
	}
	public void setListUser(UserListVO listUser) {
		ListUser = listUser;
	}
	public int getCountSessions() {
		return CountSessions;
	}
	public void setCountSessions(int countSessions) {
		CountSessions = countSessions;
	}

	private int CountSessions=0; 
	
	public int getCountNodes() {
		return CountNodes;
	}
	public void setCountNodes(int countNodes) {
		CountNodes = countNodes;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Node> getTreads() {
		return Treads;
	}
	public void setTreads(ArrayList<Node> treads) {
		Treads = treads;
	}

	private ArrayList<Node> Treads = new ArrayList<Node>();//creamos el objeto lista
	public void addChildren(Node node)
	{
		Treads.add(node);
	}
	public void removeChildren(Node node)
	{
		Treads.remove(node);
	}
	
	public Container() {
		
	} 
	
	public Node getNode(int id)
	{
		
		  
		  if (id==1)
		  {
			  return  this.getTreads().get(0);
		  }else
		  {
			  int i =0;
			  while (i< this.getTreads().size())
			  {
				  if (this.getTreads().get(0).getChildren().get(i).getId()==id)
				  {
					  return this.getTreads().get(0).getChildren().get(i);
				  }else
				  {
					  
				  }
					  
				  i++;
			  }

		  }
		return null;
		  
				
	}
	public Node getNodeR(int id,Node root)
	{
		
		  
		  if (id==1)
		  {
			  return  this.getTreads().get(0);
		  }else
		  {
			  int i =0;
			  while (i< root.getChildren().size())
			  {
				  if (root.getChildren().get(i).getId()==id)
				  {
					  return root.getChildren().get(i);
				  }else
				  {
					  
					  if  (getNodeR(id, root.getChildren().get(i)) != null)
					  {
						  return getNodeR(id, root.getChildren().get(i));
					  }
						  
				  }
					  
				  i++;
			  }

		  }
		return null;
		  
				
	}
	
	public void CountNodes(Node root)
	{
		   
		
	
			for (Node node: root.getChildren()) {	
				
				CountNodes(node);
				root.setdescendants(root.getdescendants()+ 1+node.getdescendants());
				
			}
			
		  
				
	}
	
	public void saveJsonUser() 
	{
		   try{
			   ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(new File("visualMailUsers.json"), this.getListUser());
	        }
	        catch (Exception e) { System.out.println("Instrucciones a ejecutar cuando se produce un error");  }
	       finally{ System.out.println("Instrucciones a ejecutar finalmente tanto si se producen errores como si no."); }
		
		
	}
	public void loadJsonUser() 
	{
		   try{
			   ObjectMapper mapper = new ObjectMapper();
			    this.getListUser().getUsers().clear();	
			    //
			  //  Root= mapper.readValue(new File("visualMailUsers.json"), Node.class );			    
			  //  containerVM.CountNodes(Root);
	        }
	        catch (Exception e) { System.out.println("Instrucciones a ejecutar cuando se produce un error");  }
	       finally{ System.out.println("Instrucciones a ejecutar finalmente tanto si se producen errores como si no."); }
		
		
	}
	
	
	
	
	 

	

}
