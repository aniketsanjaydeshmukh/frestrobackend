package com.frestro.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frestro.dto.AdminDTO;
import com.frestro.dto.OwnerDTO;
import com.frestro.model.Admin;
import com.frestro.model.Owner;
import com.frestro.services.AdminServices;
import com.frestro.services.CustomerServices;
import com.frestro.services.OwnerServices;

import flexjson.JSONSerializer;

@Controller
@RequestMapping("/admin")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminController {
	
	@Autowired
	AdminServices adminServices;
	
	@Autowired
	OwnerServices ownerServices;
	
	@Autowired
	CustomerServices customerServices;
	
	
	public static HashMap<String, String> adminMap = new HashMap<String, String>();
	
	@RequestMapping(value = "/signupAdmin/",method = RequestMethod.POST,  headers = "content-type=application/json")
	public @ResponseBody
	void add(@RequestBody AdminDTO adminDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
			Map<String,Object> obj = new HashMap<String,Object>();
			 if((!ownerServices.checkUnique(adminDTO.getUsername()))
				|| (!customerServices.checkUniqueUsername(adminDTO.getUsername()))
				|| (!adminServices.checkUniqueUsername(adminDTO.getUsername()))
					 ){
				   obj.put("admin", "fail");
				   obj.put("reason", "username must be  Unique");
				}			
				else{ 
					Admin admin = new Admin(adminDTO);
					if(adminServices.addOrUpdateAdmin(admin)){
						obj.put("admin", "added");
					}	
				}
				response.setContentType("application/json; charset=UTF-8"); 
				response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));
	}
	
	@RequestMapping(value = "/login/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void login(@RequestBody AdminDTO adminDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		 String sessionId = null;
			if(adminServices.login(adminDTO)){
				 HttpSession sessionn = request.getSession();
				 sessionId = sessionn.getId();
				 adminMap.put(sessionId, sessionId);
				 Admin admin1 = adminServices.getAdminByUsername(adminDTO.getUsername());
				 String strI = Long.toString((admin1.getId()));
				 String adminId = "adminId"+sessionId;
				 adminMap.put(adminId,strI);
				 obj.put("sessionId", sessionId);
				 obj.put("name", admin1.getName());
				 obj.put("login", "successful");
			}else{
				obj.put("login", "unsuccessful");
			}
			response.setContentType("application/json; charset=UTF-8"); 
			response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));		
	}

	public HashMap<String, String> getAdminmap() {    
	    return adminMap;
	}
}
