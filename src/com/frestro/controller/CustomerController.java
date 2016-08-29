package com.frestro.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frestro.dto.CustomerDTO;
import com.frestro.dto.RestaurantDTO;
import com.frestro.model.Customer;
import com.frestro.model.Dish;
import com.frestro.model.Event;
import com.frestro.model.Owner;
import com.frestro.model.ResetPasswordToken;
import com.frestro.model.Restaurant;
import com.frestro.model.SpecialOffer;
import com.frestro.model.VerificationToken;
import com.frestro.services.AdminServices;
import com.frestro.services.CustomerServices;
import com.frestro.services.OwnerServices;
import com.frestro.services.ResetPasswordTokenServices;
import com.frestro.services.RestaurantServices;
import com.frestro.services.SpecialOfferServices;
import com.frestro.services.VerificationTokenServices;

import flexjson.JSONSerializer;

@Controller
@RequestMapping("/customer")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerController {

	@Autowired
	CustomerServices customerServices;
	
	@Autowired
	OwnerServices ownerServices;
	
	@Autowired
	RestaurantServices restaurantServices;
	
	@Autowired
	SpecialOfferServices specialOfferServices;
	
	@Autowired
    VerificationTokenServices verificationTokenServices;
 
	@Autowired
	ResetPasswordTokenServices resetPasswordTokenServices;
	
	@Autowired
	AdminServices adminServices;
	
	public static HashMap<String, String> customerMap = new HashMap<String, String>();

	@RequestMapping(value = "/signupCustomer/",method = RequestMethod.POST,  headers = "content-type=application/json")
	public @ResponseBody
	void add(@RequestBody CustomerDTO customerDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
			Map<String,Object> obj = new HashMap<String,Object>();
		  
			 if((!customerServices.checkUniqueEmail(customerDTO.getEmailID()))  || (!customerServices.checkUniquePhone(customerDTO.getPhoneNO())) || (!restaurantServices.checkUniquePhone(customerDTO.getPhoneNO())) || (!restaurantServices.checkUniqueEmail(customerDTO.getEmailID())) ||  
					 (!ownerServices.checkUniqueEmail(customerDTO.getEmailID())) || (!ownerServices.checkUniquePhoneNO(customerDTO.getPhoneNO())))
				{
				   obj.put("customer", "fail");
				   obj.put("reason", "username/email must be  Unique");
				}			
				else
				{ 
					Customer customer = new Customer(customerDTO);
					customer.setEnabled(false);
					String token = UUID.randomUUID().toString();
					VerificationToken myToken = new VerificationToken(token,false);
					customer.setVerificationToken(myToken);
					myToken.setCustomer(customer);
					myToken.setVerified(false);
					if(customerServices.addOrUpdateCustomer(customer)){
						obj.put("customer", "added");
						verificationTokenServices.createOrUpdateVerificationToken(myToken);
				         
						final String username = "testemail141193@gmail.com";
						final String password = "kumodinee@123";

						Properties props = new Properties();
						props.put("mail.smtp.auth", "true");
						props.put("mail.smtp.starttls.enable", "true");
						props.put("mail.smtp.host", "smtp.gmail.com");
						props.put("mail.smtp.port", "587");
						props.put("mail.smtp.user", username); // User name
						props.put("mail.smtp.password", password); 
						
						Session session = Session.getInstance(props,
						  new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(username, password);
							}
						  });

						try {
							//String url = "http://localhost:8080/FrestroBackend/customer/activate?userToken="+token;
							String url = "http://localhost:8080/RestroApp/js/SetPassword.jsp?token="+token;
							Message message = new MimeMessage(session);
							message.setFrom(new InternetAddress("testemail141193@gmail.com"));
							message.setRecipients(Message.RecipientType.TO,
								InternetAddress.parse(customer.getEmailID()));
							message.setSubject("Activite account");
							message.setText("Dear "+customer.getName()
								+ "\n verify your account for activation by clicking following link, please!"
								+"\n\n\n"+url);

							Transport.send(message);

							System.out.println("Done");

						} catch (MessagingException e) {
							throw new RuntimeException(e);
						}
					}	
				}
			
				response.setContentType("application/json; charset=UTF-8"); 
				response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));
	}
	
	@RequestMapping(value = "/activate/", method = RequestMethod.POST)
	public @ResponseBody
	void activate(@RequestBody CustomerDTO customerDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		/*Map<String,Object> obj = new HashMap<String,Object>();
			String token = request.getParameter("userToken");
			System.out.println(token);
			VerificationToken vt = verificationTokenServices.getVerificationTokenByToken(token);
			vt.setVerified(true);
			Customer customer = vt.getCustomer();
			if(customer.isEnabled()){
				obj.put("customer", "already activated.");
			}else{
				customer.setEnabled(true);
				boolean customerFlag = customerServices.addOrUpdateCustomer(customer);
				boolean vtFlag = verificationTokenServices.createOrUpdateVerificationToken(vt);
				if(customerFlag && vtFlag){
					obj.put("customer", "activate");
				}else{
					obj.put("customer", "not activate");
				}
			}
			response.setContentType("application/json; charset=UTF-8"); 
			response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));	*/
		
		Map<String,Object> obj = new HashMap<String,Object>();
		VerificationToken vt = verificationTokenServices.getVerificationTokenByToken(customerDTO.getToken());
		if(vt != null && (!vt.isVerified())){
			Customer customer = null;
			customer=vt.getCustomer();
			if(customer != null){
				customer.setPassword(customerDTO.getPassword());
				vt.setVerified(true);
				customer.setEnabled(true);
				if(customerServices.addOrUpdateCustomer(customer) && verificationTokenServices.createOrUpdateVerificationToken(vt)){
					obj.put("password", "set successfull.");
					/*verificationTokenServices.deleteVerificationToken(vt.getId());*/
				}else{
					obj.put("password", "set unsuccessfull.");
				}
			}else{
				Owner owner = null;
				owner=vt.getOwner();
				if(owner != null){
					owner.setPassword(customerDTO.getPassword());
					owner.setEnabled(true);
					vt.setVerified(true);
					if(ownerServices.addOrUpdateOwner(owner) && verificationTokenServices.createOrUpdateVerificationToken(vt)){
						obj.put("password", "set successfull.");
						/*verificationTokenServices.deleteVerificationToken(vt.getId());*/
					}else{
						obj.put("password", "set unsuccessfull.");
					}
				}
			}
			
		}else if(vt.isVerified()){
			obj.put("password", "already set...");
		}
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));	
	}
	
	
	@RequestMapping(value = "/login/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void login(@RequestBody CustomerDTO customerDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		 String sessionId = null;
			if(customerServices.login(customerDTO))
			{	
				 HttpSession sessionn = request.getSession();
				 sessionId = sessionn.getId();
				 customerMap.put(sessionId, sessionId);
				 Customer customer1 = customerServices.getCustomerByEmail(customerDTO.getEmailID());
				 String strI = Long.toString((customer1.getId()));
				 String customerId = "customerId"+sessionId;
				 customerMap.put(customerId,strI);
				 obj.put("sessionId", sessionId);
				 obj.put("name", customer1.getName());
				 obj.put("login", "successful");
			}
			else if(ownerServices.login(customerDTO)){
				HttpSession sessionn = request.getSession();
				 sessionId = sessionn.getId();
				 Owner owner1 = ownerServices.getOwnerByEmail(customerDTO.getEmailID());
				
				 String strI = Long.toString((owner1.getId()));
				 String ownerId = "ownerId"+sessionId;
				 OwnerController oc = new OwnerController();
				obj =  oc.login(sessionId, ownerId, strI, owner1, true);
			}
			else
			{
				obj.put("login", "unsuccessful");
				obj.put("reason", "username/password is wrong or account is not activated.");
			}
			response.setContentType("application/json; charset=UTF-8"); 
			response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));		
	}
	
	@RequestMapping(value = "/logout/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void logout(@RequestBody CustomerDTO customerDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		CustomerController cc = new CustomerController();
		HashMap<String, String> user = cc.getCustomermap();
		Object key = user.get(customerDTO.getSessionId());
		Map<String,Object> obj = new HashMap<String,Object>();
		if(key != null){
				user.remove(customerDTO.getSessionId());
				user.remove("customerId"+customerDTO.getSessionId());
				obj.put("logout", "successful");
		}else{
			obj.put("logout", "unsuccessful");
		}
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));	
	}
	
	@RequestMapping(value = "/updateCustomer/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void updateCustomer(@RequestBody CustomerDTO customerDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		CustomerController cc = new CustomerController();
		HashMap<String, String> user = cc.getCustomermap();
		Object key = user.get(customerDTO.getSessionId());
		boolean isUsername = true;
		boolean isEmail = true;
		boolean isPhoneNO = true;
		if(key != null){
			Object keyId = user.get("customerId"+customerDTO.getSessionId());
			long customerId = Long.parseLong(keyId.toString());
			Customer oldCustomer = customerServices.getCustomerById(customerId);
			
			if(oldCustomer != null){
				//if username does not change
				/*if(oldCustomer.getUsername().equals(customerDTO.getUsername())){
					if((!ownerServices.checkUnique(customerDTO.getUsername())) || (!adminServices.checkUniqueUsername(customerDTO.getUsername()))){
						obj.put("customer", "fail");
						obj.put("reason", "username must be  Unique");
						isUsername = false;
					}else{
						oldCustomer.setUsername(customerDTO.getUsername());
					}
				}else{
					if((!customerServices.checkUniqueUsername(customerDTO.getUsername()))
							|| (!ownerServices.checkUnique(customerDTO.getUsername()))){
							   obj.put("customer", "fail");
							   obj.put("reason", "username must be  Unique");
							   isUsername = false;
					}else{
						oldCustomer.setUsername(customerDTO.getUsername());
					}
				}*/
				//if emaild does not change
				if(oldCustomer.getEmailID().equals(customerDTO.getEmailID())){
					if((!restaurantServices.checkUniqueEmail(customerDTO.getEmailID())) || (!ownerServices.checkUniqueEmail(customerDTO.getEmailID())) || (!ownerServices.checkUniquePhoneNO(customerDTO.getPhoneNO()))){
						obj.put("customer", "fail");
						obj.put("reason", "email must be  Unique");
						isEmail = false;
					}else{
						oldCustomer.setEmailID(customerDTO.getEmailID());
					}
				}else{
					if((!restaurantServices.checkUniqueEmail(customerDTO.getEmailID())) || (!customerServices.checkUniqueEmail( customerDTO.getEmailID())) 
							|| (!ownerServices.checkUniqueEmail(customerDTO.getEmailID())) || (!ownerServices.checkUniquePhoneNO(customerDTO.getPhoneNO()))){
						obj.put("customer", "fail");
						obj.put("reason", "email must be  Unique");
						isEmail = false;
					}else{
						oldCustomer.setEmailID(customerDTO.getEmailID());
					}
				}
				//if phoneNO does not change
				if(oldCustomer.getPhoneNO() == customerDTO.getPhoneNO()){
					if((!restaurantServices.checkUniquePhone(customerDTO.getPhoneNO())) || (!ownerServices.checkUniquePhoneNO(customerDTO.getPhoneNO()))){
						obj.put("customer", "fail");
						obj.put("reason", "phoneNO must be  Unique");
						isPhoneNO = false;
					}else{
						oldCustomer.setPhoneNO(customerDTO.getPhoneNO());
					}
				}else{
					if((!restaurantServices.checkUniquePhone(customerDTO.getPhoneNO())) || (!customerServices.checkUniquePhone(customerDTO.getPhoneNO())) || (!ownerServices.checkUniquePhoneNO(customerDTO.getPhoneNO())) ){
						obj.put("customer", "fail");
						obj.put("reason", "phoneNO must be  Unique");
						isPhoneNO = false;
					}else{
						oldCustomer.setPhoneNO(customerDTO.getPhoneNO());
					}
				}
				if(isEmail && isPhoneNO){
					oldCustomer.setName(customerDTO.getName());
					oldCustomer.setPassword(customerDTO.getPassword());
					if(customerServices.addOrUpdateCustomer(oldCustomer)){
						obj.put("customer", "updated");
					}
				}
			}
			response.setContentType("application/json; charset=UTF-8"); 
			response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));	
		}
	}
	
	@RequestMapping(value = "/listRestaurantByUserAddress/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void listRestaurantByUserAddress(@RequestBody CustomerDTO customerDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		CustomerController cc = new CustomerController();
		HashMap<String, String> user = cc.getCustomermap();
		Object key = user.get(customerDTO.getSessionId());
		
		List<Double> distances = new ArrayList<Double>(); 
		
		List<Restaurant> restaurants = customerServices.getRestaurantList();
		Map<Long,Double> restaurantDistanceMap =new HashMap<Long,Double>();  
		
		for (Restaurant restaurant : restaurants) {
			System.out.println(restaurant.getName()+"  "+ restaurant.getId());
			restaurantDistanceMap.put(restaurant.getId(), customerServices.getDistance(restaurant.getLat(), restaurant.getLon(), customerDTO.getLat1(), customerDTO.getLon2(), "K"));
			distances.add(customerServices.getDistance(restaurant.getLat(), restaurant.getLon(), customerDTO.getLat1(), customerDTO.getLon2(), "K"));
		}
		
		/*Comparator comparator = Collections.reverseOrder();
		Collections.sort(distances,comparator);*/
		
		Collections.sort(distances);
		 
		// Getting a Set of Key-value pairs
	    Set entrySet = restaurantDistanceMap.entrySet();
	 
	    // Obtaining an iterator for the entry set
	    Iterator it = entrySet.iterator();
		
		
		List<Restaurant> restaList = new ArrayList<Restaurant>();
		
		for (Double distance : distances) {
			System.out.println("distance:"+distance);
		}
		
		for (Map.Entry<Long, Double> me : restaurantDistanceMap.entrySet()) {
			System.out.println("key:"+Long.parseLong(me.getKey().toString())+"  value:"+Double.parseDouble(me.getValue().toString()));
		}
		
		for (Double distance : distances) {
			
			for (Map.Entry<Long, Double> me : restaurantDistanceMap.entrySet()) {
				
				if(Double.parseDouble(me.getValue().toString())==distance){
					Object keyId = me.getKey();
					long restaurantId = Long.parseLong(keyId.toString());
					Restaurant restaurant = restaurantServices.getRestaurantById(restaurantId);
					restaList.add(restaurant);
					
				}
			}
		}
		//We can get current date using default constructor
	    Date currentDate = new Date();
	    //toString would print the full date time string
	    System.out.println(currentDate.toString());
	    
	    System.out.println("date: "+currentDate.getDate()+"\t"+"day: "+currentDate.getDay()+"\t"+"hours: "+currentDate.getHours()+"\t"+"minutes: "+currentDate.getMinutes()+"\t"+currentDate.getMonth());
	    
		boolean flag = true;
		HashMap<String, Object> specialOffers = new HashMap<String,Object>();
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		for (Restaurant restaurant : restaList) {
			/*System.out.println("rID"+restaurant.getId());
			List<SpecialOffer> so = specialOfferServices.getSpecialOfferByRestaurant(restaurant.getId());
			for (SpecialOffer specialOffer : so) {
				
				System.out.println("sID:"+specialOffer.getId()+" "+specialOffer.getName());
			}*/
			
			int hourFrom=0;
			int minFrom = 0;
			int toHour=0;
			int toMin = 0;
			HashMap<String, Object> obj = new HashMap<String,Object>();
			
			List<SpecialOffer> so = specialOfferServices.getSpecialOfferByRestaurant(restaurant.getId());
			if(!so.isEmpty()){
			List<HashMap<String,Object>> offerList = new ArrayList<HashMap<String,Object>>();
			obj.put("name", restaurant.getName());
			for (SpecialOffer specialOffer : so) {
				
				HashMap<String, Object> offer = new HashMap<String,Object>();
					if(specialOffer.getFromTime().matches(".*PM")){
						String str = specialOffer.getFromTime().replaceAll("\\D+","");
						String strHour = str.substring(0, Math.min(str.length(), 2));
						hourFrom = Integer.parseInt(strHour);
						
						if(hourFrom == 12){
							hourFrom = 12;
						}else{
							hourFrom=12+hourFrom;
						}
						String strMin = str.substring(2, Math.min(str.length(), 4));
						minFrom = Integer.parseInt(strMin);
					}else{
						String str = specialOffer.getFromTime().replaceAll("\\D+","");
						String strHour = str.substring(0, Math.min(str.length(), 2));
						String strMin = str.substring(2, Math.min(str.length(), 4));
						
						hourFrom = Integer.parseInt(strHour);
						if(hourFrom == 12){
							hourFrom = 0;
						}
						minFrom = Integer.parseInt(strMin);
					}
					if(specialOffer.getToTime().matches(".*PM")){
						String str = specialOffer.getToTime().replaceAll("\\D+","");
						
						String strHr = str.substring(0, Math.min(str.length(), 2));
						String strMin = str.substring(2, Math.min(str.length(), 4));
						
						toHour = Integer.parseInt(strHr);
						
						if(toHour == 12){
							toHour = 12;
						}else{
							toHour=12+toHour;
						}
						toMin = Integer.parseInt(strMin);
					}else{
						String str = specialOffer.getToTime().replaceAll("\\D+","");
						String strHr = str.substring(0, Math.min(str.length(), 2));
						String strMin = str.substring(2, Math.min(str.length(), 4));
						toHour = Integer.parseInt(strHr);
						if(toHour == 12){
							toHour = 0;
						}
						toMin = Integer.parseInt(strMin);
					}
					
					Date dateFrom = format.parse(specialOffer.getFromDate());
					dateFrom.setHours(hourFrom);
					dateFrom.setMinutes(minFrom);
					
					Date dateTo = format.parse(specialOffer.getToDate());
					dateTo.setHours(toHour);
					dateTo.setMinutes(toMin);
					
					System.out.println("dateFrom:"+dateFrom);
					System.out.println("dateTo:"+dateTo);
					
					boolean isAfter =   currentDate.compareTo(dateFrom) >= 0; 
					boolean isBefore =  currentDate.compareTo(dateTo) <= 0;
					boolean isHourAfter = dateFrom.getHours()<=currentDate.getHours();
					boolean isHourBefore = dateTo.getHours()>=currentDate.getHours();
					boolean isMinAfter = false;
					if(isHourAfter){
						isMinAfter = true;
					}else{
						 isMinAfter =  dateFrom.getMinutes()<=currentDate.getMinutes();
					}
					
					boolean isMinBefore =false;
					if(isHourBefore){
						isMinBefore = true;
					}else{
						isMinBefore =dateTo.getMinutes()>=currentDate.getMinutes();
					}
					if(isAfter && isBefore && isHourAfter && isHourBefore && isMinAfter && isMinBefore){
						System.out.println("sID:"+specialOffer.getId());
						flag = true;
						for (HashMap<String, Object> hashMap : offerList) {
							Object value= hashMap.get("id");
							long longValue = Long.parseLong(value.toString());
							System.out.println("longValue"+longValue);
							if(longValue == specialOffer.getId()){
								flag = false;
							}
						}
						if(flag){
						offer.put("offer", specialOffer.getName());
						offer.put("price", specialOffer.getPrice());
						offer.put("imageData",specialOffer.getImageData());
						offer.put("timeFrom", specialOffer.getFromTime());
						offer.put("timeTo", specialOffer.getToTime());
						offer.put("id", specialOffer.getId());
						offerList.add(offer);
						}
					}				
					
			}
			obj.put("currentOffers", offerList);
			list.add(obj);
			}
		}
		specialOffers.put("specialOffers", list);
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(specialOffers));	
	}
	
	@RequestMapping(value = "/getImage/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void getImage(@RequestBody CustomerDTO customerDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		System.out.println(customerDTO.getImageData());
		Customer customer = new Customer();
		customer.setImageData(customerDTO.getImageData());
		customerServices.addOrUpdateCustomer(customer);
		
	}

	@RequestMapping(value = "/forgetPassword/",method = RequestMethod.POST,  headers = "content-type=application/json")
	public @ResponseBody
	void forgetPassword(@RequestBody CustomerDTO customerDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		Customer customer = null;
		Owner owner = null;
		customer = customerServices.getCustomerByEmail(customerDTO.getEmailID());
		owner = ownerServices.getOwnerByEmail(customerDTO.getEmailID());
		boolean flag = false;
		if(customer != null || owner != null){
			
			String token = UUID.randomUUID().toString();
			
			final String username = "testemail141193@gmail.com";
			final String password = "kumodinee@123";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  });

			try {
				ResetPasswordToken resetPasswordToken = new ResetPasswordToken(token);
				if(customer != null){
					resetPasswordToken.setCustomer(customer);
					resetPasswordToken.setChanged(false);
					resetPasswordTokenServices.createOrUpdateResetPasswordToken(resetPasswordToken);
					
					String url = "http://localhost:8080/RestroApp/js/resetPassword.jsp?token="+token;
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("testemail141193@gmail.com"));
					message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(customer.getEmailID()));
					message.setSubject("Reset account");
					message.setText("Dear "+customer.getName()
						+ "\n reset password by clicking following link, please!"
						+"\n\n\n"+url);
	
					Transport.send(message);
					flag = true;
					obj.put("mail", "sent");
				}else{
					resetPasswordToken.setOwner(owner);
					resetPasswordToken.setChanged(false);
					resetPasswordTokenServices.createOrUpdateResetPasswordToken(resetPasswordToken);
					
					String url = "http://localhost:8080/RestroApp/js/resetPassword.jsp?token="+token;
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("testemail141193@gmail.com"));
					message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(owner.getEmailID()));
					message.setSubject("Reset account");
					message.setText("Dear "+owner.getName()
						+ "\n reset password by clicking following link, please!"
						+"\n\n\n"+url);
	
					Transport.send(message);
					flag = true;
					obj.put("mail", "sent");
				}
			
				if(!flag){
					obj.put("mail", "failed");
				}
				response.setContentType("application/json; charset=UTF-8"); 
				response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));	

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
			
		}
		
	}
	
	@RequestMapping(value = "/resetPassword/",method = RequestMethod.POST,  headers = "content-type=application/json")
	public @ResponseBody
	void resetPassword(@RequestBody CustomerDTO customerDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		ResetPasswordToken rpt = resetPasswordTokenServices.getResetPasswordTokenByToken(customerDTO.getToken());
		if(rpt != null && (!rpt.isChanged())){
			Customer customer = null;
			customer=rpt.getCustomer();
			if(customer != null){
				customer.setPassword(customerDTO.getPassword());
				rpt.setChanged(true);
				if(customerServices.addOrUpdateCustomer(customer) && resetPasswordTokenServices.createOrUpdateResetPasswordToken(rpt)){
					obj.put("password", "reset successfull.");
					
				}else{
					obj.put("password", "reset unsuccessfull.");
				}
			}else{
				Owner owner = null;
				owner=rpt.getOwner();
				if(owner != null){
					owner.setPassword(customerDTO.getPassword());
					rpt.setChanged(true);
					if(ownerServices.addOrUpdateOwner(owner) && resetPasswordTokenServices.createOrUpdateResetPasswordToken(rpt)){
						obj.put("password", "reset successfull.");
						
					}else{
						obj.put("password", "reset unsuccessfull.");
					}
				}
			}
			
		}
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));	
	}
	
	
	@RequestMapping(value = "/findByCity/",method = RequestMethod.POST,  headers = "content-type=application/json")
	public @ResponseBody
	void findByCity(@RequestBody RestaurantDTO restaurantDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Set<Restaurant> restaurantList = restaurantServices.getRestaurantByCity(restaurantDTO.getCity());
		Set<Map<String,Object>> restaurantSet = new HashSet<Map<String,Object>>();
		for (Restaurant restaurant : restaurantList) {
			Map<String,Object> obj = new HashMap<String,Object>();
			obj.put("resId", restaurant.getId());
			obj.put("name", restaurant.getName());
			obj.put("address", restaurant.getAddress());
			obj.put("area", restaurant.getArea());
			obj.put("city", restaurant.getCity());
			obj.put("cuisine", restaurant.getCuisine());
			restaurantSet.add(obj);
		}
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(restaurantSet));
	}
	
	@RequestMapping(value = "/findRestaurants/",method = RequestMethod.POST,  headers = "content-type=application/json")
	public @ResponseBody
	void findByCuisine(@RequestBody RestaurantDTO[] restaurantDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Set<Map<String,Object>> restaurantSet = new HashSet<Map<String,Object>>();
		for (RestaurantDTO restaurantDTO2 : restaurantDTO) {
			Set<Restaurant> restaurantList = null;
			if((restaurantDTO2.getArea() == null) && (restaurantDTO2.getCuisineName() != null)){
			 restaurantList = restaurantServices.getRestaurantByCuisine(restaurantDTO2.getCity(), restaurantDTO2.getCuisineName());
			}else if((restaurantDTO2.getCuisineName() == null) && (restaurantDTO2.getArea() != null)){
				 restaurantList = restaurantServices.getRestaurantByArea(restaurantDTO2.getCity(), restaurantDTO2.getArea());
			}else {
				restaurantList = restaurantServices.getRestaurantByAreaCuisine(restaurantDTO2.getCity(), restaurantDTO2.getArea(), restaurantDTO2.getCuisineName());
			}
			for (Restaurant restaurant : restaurantList) {
				Map<String,Object> obj = new HashMap<String,Object>();
				obj.put("resId", restaurant.getId());
				obj.put("name", restaurant.getName());
				obj.put("address", restaurant.getAddress());
				obj.put("area", restaurant.getArea());
				obj.put("city", restaurant.getCity());
				obj.put("cuisine", restaurant.getCuisine());
				restaurantSet.add(obj);
			}
		}
		
		
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(restaurantSet));
	}
	
	@RequestMapping(value = "/findByArea/",method = RequestMethod.POST,  headers = "content-type=application/json")
	public @ResponseBody
	void findByArea(@RequestBody RestaurantDTO[] restaurantDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Set<Map<String,Object>> restaurantSet = new HashSet<Map<String,Object>>();
		for (RestaurantDTO restaurantDTO2 : restaurantDTO) {
			Set<Restaurant> restaurantList = restaurantServices.getRestaurantByArea(restaurantDTO2.getCity(), restaurantDTO2.getArea());
			
			for (Restaurant restaurant : restaurantList) {
				Map<String,Object> obj = new HashMap<String,Object>();
				obj.put("resId", restaurant.getId());
				obj.put("name", restaurant.getName());
				obj.put("address", restaurant.getAddress());
				obj.put("area", restaurant.getArea());
				obj.put("city", restaurant.getCity());
				obj.put("cuisine", restaurant.getCuisine());
				restaurantSet.add(obj);
			}
		}
		
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(restaurantSet));
	}
	
	@RequestMapping(value = "/findByAreaCuisine/",method = RequestMethod.POST,  headers = "content-type=application/json")
	public @ResponseBody
	void findByAreaCuisine(@RequestBody RestaurantDTO[] restaurantDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Set<Map<String,Object>> restaurantSet = new HashSet<Map<String,Object>>();
		for (RestaurantDTO restaurantDTO2 : restaurantDTO) {
			Set<Restaurant> restaurantList = restaurantServices.getRestaurantByAreaCuisine(restaurantDTO2.getCity(), restaurantDTO2.getArea(), restaurantDTO2.getCuisineName());
			for (Restaurant restaurant : restaurantList) {
				Map<String,Object> obj = new HashMap<String,Object>();
				obj.put("resId", restaurant.getId());
				obj.put("name", restaurant.getName());
				obj.put("address", restaurant.getAddress());
				obj.put("area", restaurant.getArea());
				obj.put("city", restaurant.getCity());
				obj.put("cuisine", restaurant.getCuisine());
				restaurantSet.add(obj);
			}
		}
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(restaurantSet));
	}
	
	@RequestMapping(value = "/getArea/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void getArea(@RequestBody RestaurantDTO restaurantDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Set<Map<String,Object>> areaSet = new HashSet<Map<String,Object>>();
		Set<String> areas = restaurantServices.getAreaByCity(restaurantDTO.getCity());
		
		for (String string : areas) {
			Map<String,Object> obj = new HashMap<String,Object>();
			obj.put(string, string);
			areaSet.add(obj);
		}
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(areaSet));
	}
	
	@RequestMapping(value = "/getRestaurantByID/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void getRestaurant(@RequestBody RestaurantDTO restaurantDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Set<Map<String,Object>> restaurantSet = new HashSet<Map<String,Object>>();
		Map<String,Object> restaurantobj = new HashMap<String,Object>();
		Map<String,Object> resobj = new HashMap<String,Object>();
		Restaurant restaurant = restaurantServices.getRestaurantById(restaurantDTO.getId());
		resobj.put("id", restaurant.getId());
		resobj.put("address", restaurant.getAddress());
		resobj.put("area", restaurant.getArea());
		resobj.put("city", restaurant.getCity());
		resobj.put("emailID", restaurant.getEmailID());
		resobj.put("fromTime", restaurant.getFromTime());
		resobj.put("name", restaurant.getName());
		resobj.put("rating", restaurant.getOverallRating());
		resobj.put("ownerName", restaurant.getOwner().getName());
		resobj.put("phoneNo", restaurant.getPhoneNO());
		resobj.put("toTime", restaurant.getToTime());
		resobj.put("cuisine", restaurant.getCuisine());
		restaurantobj.put("restaurant", resobj);
		
		
		Set<Map<String,Object>> dishSet = new HashSet<Map<String,Object>>();
		Set<Dish> dishes = restaurant.getDishes();
		for (Dish dish : dishes) {
			Map<String,Object> obj = new HashMap<String,Object>();
			obj.put("foodType", dish.getFoodType());
			obj.put("id", dish.getId());
			obj.put("ingredients", dish.getIngredients());
			obj.put("menuType", dish.getMenuType());
			obj.put("name", dish.getName());
			obj.put("price", dish.getPrice());
			dishSet.add(obj);
		}
		
		restaurantobj.put("dishes", dishSet);
		
		Set<Map<String,Object>> eventSet = new HashSet<Map<String,Object>>();
		Set<Event> events = restaurant.getEvents();
		for (Event event : events) {
			Map<String,Object> obj = new HashMap<String,Object>();
			obj.put("id", event.getId());
			obj.put("description", event.getDescription());
			obj.put("fromDate", event.getFromDate());
			obj.put("fromTime", event.getFromTime());
			obj.put("name", event.getName());
			obj.put("toDate", event.getToDate());
			obj.put("toTime", event.getToTime());
			eventSet.add(obj);
		}
		restaurantobj.put("events",eventSet);
		
		Set<Map<String,Object>> soSet = new HashSet<Map<String,Object>>();
		Set<SpecialOffer> speialOffers = restaurant.getSpecialOffers();
		for (SpecialOffer specialOffer : speialOffers) {
			Map<String,Object> obj = new HashMap<String,Object>();
			obj.put("id", specialOffer.getId());
			obj.put("description", specialOffer.getDescription());
			obj.put("fromDate", specialOffer.getFromDate());
			obj.put("fromTime", specialOffer.getFromTime());
			obj.put("name", specialOffer.getName());
			obj.put("price", specialOffer.getPrice());
			obj.put("toDate", specialOffer.getToDate());
			obj.put("toTime", specialOffer.getToTime());
			soSet.add(obj);
		}
		
		restaurantobj.put("specialOffers",soSet);
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(restaurantobj));
	}
	
	public HashMap<String, String> getCustomermap() {    
	    return customerMap;
	}
}
