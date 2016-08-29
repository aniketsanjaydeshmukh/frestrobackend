package com.frestro.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frestro.dto.DishDTO;
import com.frestro.dto.EventDTO;
import com.frestro.dto.OwnerDTO;
import com.frestro.dto.RestaurantDTO;
import com.frestro.dto.SpecialOfferDTO;
import com.frestro.dto.TablesDTO;
import com.frestro.model.Dish;
import com.frestro.model.Event;
import com.frestro.model.Owner;
import com.frestro.model.Restaurant;
import com.frestro.model.SpecialOffer;
import com.frestro.model.Tables;
import com.frestro.model.VerificationToken;
import com.frestro.services.AdminServices;
import com.frestro.services.CustomerServices;
import com.frestro.services.DishServices;
import com.frestro.services.EventServices;
import com.frestro.services.OwnerServices;
import com.frestro.services.RestaurantServices;
import com.frestro.services.SpecialOfferServices;
import com.frestro.services.TablesServices;
import com.frestro.services.VerificationTokenServices;

import flexjson.JSONSerializer;

@Controller
@RequestMapping("/owner")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OwnerController {

	@Autowired
	OwnerServices ownerServices;
	
	@Autowired
	RestaurantServices restaurantServices;
	
	@Autowired
	CustomerServices customerServices; 
	
	@Autowired
	DishServices dishServices;
	
	@Autowired
	SpecialOfferServices specialOfferServices;
	
	@Autowired
	EventServices eventServices;
	
	@Autowired
	AdminServices adminServices;
	
	@Autowired
	VerificationTokenServices verificationTokenServices;
	
	@Autowired
	TablesServices tablesServices;
	
	public static HashMap<String, String> ownerMap = new HashMap<String, String>();
	
	@RequestMapping(value = "/signupOwner/",method = RequestMethod.POST,  headers = "content-type=application/json")
	public @ResponseBody
	void add(@RequestBody OwnerDTO ownerDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
			Map<String,Object> obj = new HashMap<String,Object>();
		  System.out.println(ownerDTO.getUsername());
			 if((!customerServices.checkUniqueEmail(ownerDTO.getEmailID())) || (!ownerServices.checkUniqueEmail(ownerDTO.getEmailID()))
					 || (!customerServices.checkUniquePhone(ownerDTO.getPhoneNO())) || (!ownerServices.checkUniquePhoneNO(ownerDTO.getPhoneNO()))){
				   obj.put("owner", "fail");
				   obj.put("reason", "username/email must be  Unique");
				}			
				else{ 
					Owner owner = new Owner(ownerDTO);
					owner.setEnabled(false);
					String token = UUID.randomUUID().toString();
					VerificationToken myToken = new VerificationToken(token,false);
					owner.setVerificationToken(myToken);
					myToken.setOwner(owner);
					myToken.setVerified(false);
					if(ownerServices.addOrUpdateOwner(owner)){
						obj.put("owner", "added");
						verificationTokenServices.createOrUpdateVerificationToken(myToken);
				         
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
							//String url = "http://localhost:8080/FrestroBackend/owner/activate?userToken="+token;
							String url = "http://localhost:8080/RestroApp/js/SetPassword.jsp?token="+token;
							Message message = new MimeMessage(session);
							message.setFrom(new InternetAddress("testemail141193@gmail.com"));
							message.setRecipients(Message.RecipientType.TO,
								InternetAddress.parse(owner.getEmailID()));
							message.setSubject("Activite account");
							message.setText("Dear "+owner.getName()
								+ "\n verify your account for activation by clicking following link, please!"
								+"\n\n\n"+url);

							Transport.send(message);

							System.out.println("Done");

						} catch (MessagingException e) {
							throw new RuntimeException(e);
						}
					}	
					/*Owner owner = new Owner(ownerDTO);
					if(ownerServices.addOrUpdateOwner(owner)){
						obj.put("owner", "added");
					}	*/
				}
				response.setContentType("application/json; charset=UTF-8"); 
				response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));
	}
	
	@RequestMapping(value = "/activate", method = RequestMethod.GET)
	public @ResponseBody
	void activate(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
			String token = request.getParameter("userToken");
			System.out.println(token);
			VerificationToken vt = verificationTokenServices.getVerificationTokenByToken(token);
			vt.setVerified(true);
			Owner owner = vt.getOwner();
			if(owner.isEnabled()){
				obj.put("owner", "already activated.");
			}else{
				owner.setEnabled(true);
				boolean ownerFlag = ownerServices.addOrUpdateOwner(owner);
				boolean vtFlag = verificationTokenServices.createOrUpdateVerificationToken(vt);
				if(ownerFlag && vtFlag){
					obj.put("owner", "activate");
				}else{
					obj.put("owner", "not activate");
				}
			}
			response.setContentType("application/json; charset=UTF-8"); 
			response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));	
	}
	
	/*@RequestMapping(value = "/login/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void login(@RequestBody OwnerDTO ownerDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		 String sessionId = null;
			if(ownerServices.login(ownerDTO)){
				 HttpSession sessionn = request.getSession();
				 sessionId = sessionn.getId();
				 ownerMap.put(sessionId, sessionId);
				 Owner owner1 = ownerServices.getOwnerByUsername(ownerDTO.getUsername());
				 String strI = Long.toString((owner1.getId()));
				 String customerId = "ownerId"+sessionId;
				 ownerMap.put(customerId,strI);
				 obj.put("sessionId", sessionId);
				 obj.put("name", owner1.getName());
				 obj.put("login", "successful");
			}else{
				obj.put("login", "unsuccessful");
				obj.put("reason", "username/password is wrong or account is not activated.");
			}
			response.setContentType("application/json; charset=UTF-8"); 
			response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));		
	}*/
	
	
	public  Map<String,Object> login(String sessionId,String ownerId,String strI,Owner owner1,boolean isLogin) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		 
			
				 if(isLogin){
				 ownerMap.put(sessionId, sessionId);
				 
				 
				 ownerMap.put(ownerId,strI);
				 obj.put("sessionId", sessionId);
				 obj.put("name", owner1.getName());
				 obj.put("login", "successful");
			}else{
				obj.put("login", "unsuccessful");
				obj.put("reason", "username/password is wrong or account is not activated.");
			}
			
			
			return obj;
	}
	
	@RequestMapping(value = "/logout/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void logout(@RequestBody OwnerDTO ownerDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		OwnerController oc = new OwnerController();
		HashMap<String, String> user = oc.getOwnermap();
		Object key = user.get(ownerDTO.getSessionId());
		Map<String,Object> obj = new HashMap<String,Object>();
		if(key != null){
				user.remove(ownerDTO.getSessionId());
				user.remove("ownerId"+ownerDTO.getSessionId());
				obj.put("logout", "successful");
		}else{
			obj.put("logout", "unsuccessful");
		}
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));	
	}


	@RequestMapping(value = "/updateOwner/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void updateOwner(@RequestBody OwnerDTO ownerDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		OwnerController oc = new OwnerController();
		HashMap<String, String> user = oc.getOwnermap();
		Object key = user.get(ownerDTO.getSessionId());
		boolean isEmail = true;
		boolean isPhone = true;
		if(key != null){
			Object keyId = user.get("ownerId"+ownerDTO.getSessionId());
			long checkerId = Long.parseLong(keyId.toString());
			Owner oldOwner = ownerServices.getOwnerById(checkerId);
			System.out.println(ownerDTO.getUsername());
			if(oldOwner != null){
				/*if(oldOwner.getUsername().equals(ownerDTO.getUsername())){
					if(!customerServices.checkUniqueUsername(ownerDTO.getUsername()) || (!adminServices.checkUniqueUsername(ownerDTO.getUsername()))){
						obj.put("owner", "fail");
						obj.put("reason", "username must be  Unique");
						isEmail = false;
					}else{
						oldOwner.setUsername(ownerDTO.getUsername());
					}
				}else{
					if((!ownerServices.checkUnique(ownerDTO.getUsername()))
							|| (!customerServices.checkUniqueUsername(ownerDTO.getUsername()))){
							   obj.put("owner", "fail");
							   obj.put("reason", "username must be  Unique");
							   isEmail = false;
					}else{
						oldOwner.setUsername(ownerDTO.getUsername());
					}
				}*/
				//check email unique
				if(oldOwner.getEmailID().equals(ownerDTO.getEmailID())){
					if((!customerServices.checkUniqueEmail(ownerDTO.getEmailID())) || (!restaurantServices.checkUniqueEmail(ownerDTO.getEmailID()))){
						obj.put("owner", "fail");
						obj.put("reason", "email must be  Unique");
						isEmail = false;
					}else{
						oldOwner.setEmailID(ownerDTO.getEmailID());
					}
				}else{
					if((!customerServices.checkUniqueEmail(ownerDTO.getEmailID())) || (!ownerServices.checkUniqueEmail(ownerDTO.getEmailID())) || (!restaurantServices.checkUniqueEmail(ownerDTO.getEmailID()))){
						obj.put("owner", "fail");
						obj.put("reason", "email must be  Unique");
						isEmail = false;
					}else{
						oldOwner.setEmailID(ownerDTO.getEmailID());
					}
				}
				//check phone no unique
				if(oldOwner.getPhoneNO() == ownerDTO.getPhoneNO()){
					if((!customerServices.checkUniquePhone(ownerDTO.getPhoneNO())) || (!restaurantServices.checkUniquePhone(ownerDTO.getPhoneNO()))){
						obj.put("owner", "fail");
						obj.put("reason", "phoneNO must be  Unique");
						isPhone = false;
					}else{
						oldOwner.setPhoneNO(ownerDTO.getPhoneNO());
					}
				}else{
					if((!customerServices.checkUniquePhone(ownerDTO.getPhoneNO())) || (!restaurantServices.checkUniquePhone(ownerDTO.getPhoneNO())) || (!ownerServices.checkUniquePhoneNO(ownerDTO.getPhoneNO())) ){
						obj.put("owner", "fail");
						obj.put("reason", "phoneNO must be  Unique");
						isPhone = false;
					}else{
						oldOwner.setPhoneNO(ownerDTO.getPhoneNO());
					}
				}
				
				if(isEmail && isPhone){
					oldOwner.setName(ownerDTO.getName());
					oldOwner.setPassword(ownerDTO.getPassword());
					if(ownerServices.addOrUpdateOwner(oldOwner)){
						obj.put("owner", "updated");
					}
				}
			}
			response.setContentType("application/json; charset=UTF-8"); 
			response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));	
		}
	}
	
	@RequestMapping(value = "/addRestaurant/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void addRestaurant(@RequestBody RestaurantDTO restaurantDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		OwnerController oc = new OwnerController();
		HashMap<String, String> user = oc.getOwnermap();
		Object key = user.get(restaurantDTO.getSessionId());
		if(key != null){
			Object keyId = user.get("ownerId"+restaurantDTO.getSessionId());
			long checkerId = Long.parseLong(keyId.toString());
			Owner owner = ownerServices.getOwnerById(checkerId);
			//long phoneNO=Long.parseLong(restaurantDTO.getPhoneNO());
			
			if(owner != null){
				if((!restaurantServices.checkUniqueEmail(restaurantDTO.getEmailID())) || (!ownerServices.checkUniqueEmail(restaurantDTO.getEmailID()))
					|| (!restaurantServices.checkUniquePhone(restaurantDTO.getPhoneNO())) || (!ownerServices.checkUniquePhoneNO(restaurantDTO.getPhoneNO())) || (!customerServices.checkUniqueEmail(restaurantDTO.getEmailID()) || (!customerServices.checkUniquePhone(restaurantDTO.getPhoneNO())) )
					){
					   obj.put("restaurant", "fail");
					   obj.put("reason", "phoneNO/email must be  Unique");
					}			
					else{ 
						Restaurant restaurant = new Restaurant(restaurantDTO);
						restaurant.setOwner(owner);
						if(restaurantServices.addOrUpdateRestaurant(restaurant)){
							
							obj.put("restaurant", "added");
						}
					}
			}
		}
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));
	}
	
	@RequestMapping(value = "/updateRestaurant/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void updateRestaurant(@RequestBody RestaurantDTO restaurantDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		OwnerController oc = new OwnerController();
		HashMap<String, String> user = oc.getOwnermap();
		Object key = user.get(restaurantDTO.getSessionId());
		boolean isEmail = true;
		boolean isPhone = true;
		if(key != null){
			Object keyId = user.get("ownerId"+restaurantDTO.getSessionId());
			long checkerId = Long.parseLong(keyId.toString());
			Owner owner = ownerServices.getOwnerById(checkerId);
			
			if(owner != null){
				Restaurant oldRestaurant = restaurantServices.getRestaurantById(restaurantDTO.getId());
				
				if(oldRestaurant != null){
					//check emailID unique
					if(oldRestaurant.getEmailID().equals(restaurantDTO.getEmailID())){
						if((!customerServices.checkUniqueEmail(restaurantDTO.getEmailID())) || (!ownerServices.checkUniqueEmail(restaurantDTO.getEmailID()))){
							obj.put("restaurant", "not updated");
							obj.put("reason", "email must be  Unique");
							isEmail = false;
						}else{
							oldRestaurant.setEmailID(restaurantDTO.getEmailID());
						}
					}else{
						
						if((!customerServices.checkUniqueEmail(restaurantDTO.getEmailID())) || (!ownerServices.checkUniqueEmail(restaurantDTO.getEmailID())) ||(!restaurantServices.checkUniqueEmail(restaurantDTO.getEmailID()))){
							obj.put("restaurant", "not updated");
							obj.put("reason", "email must be  Unique");
							isEmail = false;
						}else{
							oldRestaurant.setEmailID(restaurantDTO.getEmailID());
						}
					}
					//check phoneNO unique
					if(oldRestaurant.getPhoneNO() == restaurantDTO.getPhoneNO()){
						if((!customerServices.checkUniquePhone(restaurantDTO.getPhoneNO())) || (!ownerServices.checkUniquePhoneNO(restaurantDTO.getPhoneNO()))){
							obj.put("restaurant", "not updated");
							obj.put("reason", "phoneNO must be  Unique");
							isPhone = false;
						}else{
							oldRestaurant.setPhoneNO(restaurantDTO.getPhoneNO());
						}
					}else{
						if((!restaurantServices.checkUniquePhone(restaurantDTO.getPhoneNO())) || (!customerServices.checkUniquePhone(restaurantDTO.getPhoneNO())) || (!ownerServices.checkUniquePhoneNO(restaurantDTO.getPhoneNO()))){
							obj.put("restaurant", "not updated");
							obj.put("reason", "phoneNO must be  Unique");
							isPhone = false;
						}else{
							oldRestaurant.setPhoneNO(restaurantDTO.getPhoneNO());
						}
					}
					if(isEmail && isPhone){
						oldRestaurant.setAddress(restaurantDTO.getAddress());
						oldRestaurant.setFromTime(restaurantDTO.getFromTime());
						oldRestaurant.setName(restaurantDTO.getName());
						oldRestaurant.setOffDays(restaurantDTO.getOffDays());
						oldRestaurant.setToTime(restaurantDTO.getToTime());
						if(restaurantServices.addOrUpdateRestaurant(oldRestaurant)){
							obj.put("restaurant", "updated");
						}
					}
				}
				
			}
		}
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));
	}
	
	@RequestMapping(value = "/deleteRestaurant/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void deleteRestaurant(@RequestBody RestaurantDTO restaurantDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		OwnerController oc = new OwnerController();
		HashMap<String, String> user = oc.getOwnermap();
		Object key = user.get(restaurantDTO.getSessionId());
		if(key != null){
			Object keyId = user.get("ownerId"+restaurantDTO.getSessionId());
			long checkerId = Long.parseLong(keyId.toString());
			Owner owner = ownerServices.getOwnerById(checkerId);
			
			if(owner != null){
				Restaurant restaurant = restaurantServices.getRestaurantById(restaurantDTO.getId());
				if(restaurant != null){
					if(restaurantServices.deleteRestaurant(restaurant.getId())){
						List<Dish> dishes = dishServices.getDishByRestaurant(restaurant.getId());
						for (Dish dish : dishes) {
							dishServices.deleteDish(dish.getId());
						}
						obj.put("restaurant", "delete");
					}else{
						obj.put("restaurant", "not delete");
					}
				}
			}
		}
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));
	}
	
	@RequestMapping(value = "/addDishes/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void addDishes(@RequestBody DishDTO[] dishDTOs,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		OwnerController oc = new OwnerController();
		HashMap<String, String> user = oc.getOwnermap();
		for (DishDTO dishDTO : dishDTOs) {
			Object key = user.get(dishDTO.getSessionId());
			if(key != null){
				Object keyId = user.get("ownerId"+dishDTO.getSessionId());
				long ownerId = Long.parseLong(keyId.toString());
				Owner owner = ownerServices.getOwnerById(ownerId);
			
				
				if(owner != null){
					Restaurant restaurant = restaurantServices.getRestaurantById(dishDTO.getRestaurantId());
					if(restaurant != null){
						Dish dish = new Dish(dishDTO);
						dish.setRestaurant(restaurant);
						Set<Dish> dishes = restaurant.getDishes();
						dishes.add(dish);
						restaurant.setDishes(dishes);
						if(dishServices.addOrUpdateDish(dish)){
							obj.put("dish", "added");
							//restaurantServices.addOrUpdateRestaurant(restaurant);
						}else{
							obj.put("dish", "not added");
						}
					}
				}
			}
		}
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));
	}
		
	@RequestMapping(value = "/getMenuCard/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void getMenuCard(@RequestBody RestaurantDTO restaurantDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		OwnerController oc = new OwnerController();
		HashMap<String, String> user = oc.getOwnermap();
		Object key = user.get(restaurantDTO.getSessionId());
		List<HashMap<String,Object>> menuCard = new ArrayList<HashMap<String,Object>>();
		if(key != null){
			Object keyId = user.get("ownerId"+restaurantDTO.getSessionId());
			long ownerId = Long.parseLong(keyId.toString());
			Owner owner = ownerServices.getOwnerById(ownerId);
			
			
			if(owner != null){
				Restaurant restaurant = restaurantServices.getRestaurantById(restaurantDTO.getId());
			
				if(restaurant != null){
					List<Dish> dishes = dishServices.getDishByRestaurant(restaurant.getId());
					for (Dish dish : dishes) {
						HashMap<String, Object> obj = new HashMap<String,Object>();
						obj.put("id", dish.getId());
						obj.put("name", dish.getName());
						obj.put("price", dish.getPrice());
						obj.put("ingredients", dish.getIngredients());
						obj.put("foodType", dish.getFoodType());
						obj.put("menuType", dish.getMenuType());
						menuCard.add(obj);
					}
				}
			}
		}
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(menuCard));
	}
	
	
	@RequestMapping(value = "/updateMenuCard/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void updateMenuCard(@RequestBody DishDTO[] dishDTOs,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		OwnerController oc = new OwnerController();
		HashMap<String, String> user = oc.getOwnermap();
		for (DishDTO dishDTO : dishDTOs) {
			Object key = user.get(dishDTO.getSessionId());
			if(key != null){
				Object keyId = user.get("ownerId"+dishDTO.getSessionId());
				long ownerId = Long.parseLong(keyId.toString());
				Owner owner = ownerServices.getOwnerById(ownerId);
				
				if(owner != null){
					Restaurant restaurant = restaurantServices.getRestaurantById(dishDTO.getRestaurantId());
					if(restaurant != null){
						Dish dish = dishServices.getDishById(dishDTO.getId());
						if(dish != null){
							if(dishDTO.getIngredients() != null){
								dish.setIngredients(dishDTO.getIngredients());
							}
							if(dishDTO.getFoodType() != null){
								dish.setFoodType(dishDTO.getFoodType());
							}
							if(dishDTO.getMenuType()!= null){
								dish.setMenuType(dishDTO.getMenuType());
							}
							dish.setName(dishDTO.getName());
							dish.setPrice(dishDTO.getPrice());
							if(dishServices.addOrUpdateDish(dish)){
								obj.put("dish", "updated");
							}else{
								obj.put("dish", "not updated");
							}
						}
					}
				}
			}
		}
	
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));
	}
	
	
	@RequestMapping(value = "/deleteMenuCard/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void deleteMenuCard(@RequestBody DishDTO[] dishDTOs,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		OwnerController oc = new OwnerController();
		HashMap<String, String> user = oc.getOwnermap();
		for (DishDTO dishDTO : dishDTOs) {
			Object key = user.get(dishDTO.getSessionId());
			if(key != null){
				Object keyId = user.get("ownerId"+dishDTO.getSessionId());
				long ownerId = Long.parseLong(keyId.toString());
				Owner owner = ownerServices.getOwnerById(ownerId);
				
				if(owner != null){
					Restaurant restaurant = restaurantServices.getRestaurantById(dishDTO.getRestaurantId());
					if(restaurant != null){
						Dish dish = dishServices.getDishById(dishDTO.getId());
						if(dish != null){
							if(dishServices.deleteDish(dish.getId())){
								obj.put("dish", "deleted");
							}else{
								obj.put("dish", "not deleted");
							}
						}
					}
				}
			}
		}
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));
	}
	
	@RequestMapping(value = "/addSpecialDish/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void addDishes(@RequestBody SpecialOfferDTO specialOfferDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		OwnerController oc = new OwnerController();
		HashMap<String, String> user = oc.getOwnermap();
		
			Object key = user.get(specialOfferDTO.getSessionId());
			if(key != null){
				Object keyId = user.get("ownerId"+specialOfferDTO.getSessionId());
				long ownerId = Long.parseLong(keyId.toString());
				Owner owner = ownerServices.getOwnerById(ownerId);
			
				if(owner != null){
					Restaurant restaurant = restaurantServices.getRestaurantById(specialOfferDTO.getRestaurantId());
					if(restaurant != null){
						SpecialOffer specialOffer = new SpecialOffer(specialOfferDTO);
						specialOffer.setRestaurant(restaurant);
						long dishIDs[] = specialOfferDTO.getDishes();
						for (long dishId : dishIDs) {
							Dish dish = dishServices.getDishById(dishId);
							specialOffer.getDishes().add(dish);
						}
						if(specialOfferServices.addOrUpdateSpecialOffer(specialOffer)){
							obj.put("specialOffer", "added");
						}else{
							obj.put("specialOffer", "notadded");
						}
					}
				}
			}
			response.setContentType("application/json; charset=UTF-8"); 
			response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));
	}
	
	@RequestMapping(value = "/listSpecialDish/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void listSpecialDish(@RequestBody SpecialOfferDTO specialOfferDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		List<HashMap<String,Object>> specialDishList = new ArrayList<HashMap<String,Object>>();
		OwnerController oc = new OwnerController();
		HashMap<String, String> user = oc.getOwnermap();
		
			Object key = user.get(specialOfferDTO.getSessionId());
			if(key != null){
				Object keyId = user.get("ownerId"+specialOfferDTO.getSessionId());
				long ownerId = Long.parseLong(keyId.toString());
				Owner owner = ownerServices.getOwnerById(ownerId);
			
				if(owner != null){
					Restaurant restaurant = restaurantServices.getRestaurantById(specialOfferDTO.getRestaurantId());
					if(restaurant != null){
						List<SpecialOffer> specialOffers = specialOfferServices.getSpecialOfferByRestaurant(restaurant.getId());
						for (SpecialOffer specialOffer : specialOffers) {
							HashMap<String, Object> so = new HashMap<String,Object>();
							so.put("id", specialOffer.getId());
							so.put("description", specialOffer.getDescription());
							Set<Dish> dishes = specialOffer.getDishes();
							for (Dish dish : dishes) {
								String k = "dish"+String.valueOf(dish.getId());
								so.put(k, dish.getName());
							}
							
							so.put("fromdate", specialOffer.getFromDate());
							so.put("fromTime", specialOffer.getFromTime());
							so.put("name", specialOffer.getName());
							so.put("price", specialOffer.getPrice());
							so.put("toDate", specialOffer.getToDate());
							so.put("toTime", specialOffer.getToTime());
							specialDishList.add(so);
						}
					}
				}
				response.setContentType("application/json; charset=UTF-8"); 
				response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(specialDishList));
			}
	}
	
	@RequestMapping(value = "/addEvent/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void addEvent(@RequestBody EventDTO eventDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		OwnerController oc = new OwnerController();
		HashMap<String, String> user = oc.getOwnermap();
		
			Object key = user.get(eventDTO.getSessionId());
			if(key != null){
				Object keyId = user.get("ownerId"+eventDTO.getSessionId());
				long ownerId = Long.parseLong(keyId.toString());
				Owner owner = ownerServices.getOwnerById(ownerId);
			
				if(owner != null){
					Restaurant restaurant = restaurantServices.getRestaurantById(eventDTO.getRestaurantId());
					if(restaurant != null){
						Event event = new Event(eventDTO);
						event.setRestaurant(restaurant);
						if(eventServices.addOrUpdateEvent(event)){
							obj.put("event", "added");
						}else{
							obj.put("event", "not added");
						}
					}
				}
			}
			response.setContentType("application/json; charset=UTF-8"); 
			response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));
	}
	
	@RequestMapping(value = "/listEvent/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void listEvent(@RequestBody EventDTO eventDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		List<HashMap<String,Object>> eventList = new ArrayList<HashMap<String,Object>>();
		OwnerController oc = new OwnerController();
		HashMap<String, String> user = oc.getOwnermap();
		
			Object key = user.get(eventDTO.getSessionId());
			if(key != null){
				Object keyId = user.get("ownerId"+eventDTO.getSessionId());
				long ownerId = Long.parseLong(keyId.toString());
				Owner owner = ownerServices.getOwnerById(ownerId);
			
				if(owner != null){
					Restaurant restaurant = restaurantServices.getRestaurantById(eventDTO.getRestaurantId());
					if(restaurant != null){
						List<Event> events = eventServices.getEventListByRestaurant(restaurant.getId());
						for (Event event : events) {
							HashMap<String, Object> obj = new HashMap<String,Object>();
							obj.put("description", event.getDescription());
							obj.put("fromDate", event.getFromDate());
							obj.put("fromTime", event.getFromTime());
							obj.put("id", event.getId());
							obj.put("name", event.getName());
							obj.put("toDate", event.getToDate());
							obj.put("toTime", event.getToTime());
							eventList.add(obj);
						}
					}
				}
				response.setContentType("application/json; charset=UTF-8"); 
				response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(eventList));
			}
	}
	
	@RequestMapping(value = "/addTable/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void addTable(@RequestBody TablesDTO tablesDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		OwnerController oc = new OwnerController();
		HashMap<String, String> user = oc.getOwnermap();
		Object key = user.get(tablesDTO.getSessionId());
		if(key != null){
			Object keyId = user.get("ownerId"+tablesDTO.getSessionId());
			long checkerId = Long.parseLong(keyId.toString());
			Owner owner = ownerServices.getOwnerById(checkerId);
			
			if(owner != null){
				Restaurant restaurant = restaurantServices.getRestaurantById(tablesDTO.getRestaurantId());
				if(restaurant != null){
					for (int i = 0; i < tablesDTO.getNoOfTable(); i++) {
						long tableNo = 0;
						Tables lastTable = tablesServices.getLastTable(restaurant.getId());
						if(lastTable != null){
							tableNo = lastTable.getTableNo() + 1;
						}else{
							tableNo = 1;
						}
						tablesDTO.setTableNo(tableNo);
						Tables table = new Tables(tablesDTO);
						table.setRestaurant(restaurant);
						if(tablesServices.addOrUpdateTable(table)){
							obj.put("table", "added");
						}else{
							obj.put("table", "not added");
						}
					}
				}
			}
		
		}
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));
	}
	
	
	@RequestMapping(value = "/listTable/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void listTable(@RequestBody TablesDTO tablesDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Set<Map<String,Object>> listTable = new HashSet<Map<String,Object>>();
		
		OwnerController oc = new OwnerController();
		HashMap<String, String> user = oc.getOwnermap();
		Object key = user.get(tablesDTO.getSessionId());
		if(key != null){
			Object keyId = user.get("ownerId"+tablesDTO.getSessionId());
			long checkerId = Long.parseLong(keyId.toString());
			Owner owner = ownerServices.getOwnerById(checkerId);
			
			if(owner != null){
				Restaurant restaurant = restaurantServices.getRestaurantById(tablesDTO.getRestaurantId());
				if(restaurant != null){
					Set<Tables> setTables = tablesServices.getTablesByRestaurant(tablesDTO.getRestaurantId());
					for (Tables tables : setTables) {
						Map<String,Object> obj = new HashMap<String,Object>();
						obj.put("tableId", tables.getId());
						obj.put("tableNo", tables.getTableNo());
						obj.put("capacity", tables.getCapacity());
						obj.put("occupied", tables.isOccupied());
						listTable.add(obj);
					}
					
				}
			}
		}
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(listTable));
	}
	
	@RequestMapping(value = "/deleteTable/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void deleteTable(@RequestBody TablesDTO[] tablesDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		
		OwnerController oc = new OwnerController();
		HashMap<String, String> user = oc.getOwnermap();
		Object key = user.get(tablesDTO[0].getSessionId());
		if(key != null){
			Object keyId = user.get("ownerId"+tablesDTO[0].getSessionId());
			long checkerId = Long.parseLong(keyId.toString());
			Owner owner = ownerServices.getOwnerById(checkerId);
			
			if(owner != null){
				Restaurant restaurant = restaurantServices.getRestaurantById(tablesDTO[0].getRestaurantId());
				if(restaurant != null){
					for (TablesDTO tablesDTO2 : tablesDTO) {
						if(tablesServices.deleteTables(tablesDTO2.getId())){
							obj.put("table", "delete");
						}else{
							obj.put("table", "not delete");
						}
					}
				}
			}
		}
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));
	}
	
	@RequestMapping(value = "/updateTable/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void updateTable(@RequestBody TablesDTO tablesDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		OwnerController oc = new OwnerController();
		HashMap<String, String> user = oc.getOwnermap();
		Object key = user.get(tablesDTO.getSessionId());
		if(key != null){
			Object keyId = user.get("ownerId"+tablesDTO.getSessionId());
			long checkerId = Long.parseLong(keyId.toString());
			Owner owner = ownerServices.getOwnerById(checkerId);
			
			if(owner != null){
				
					Tables table = tablesServices.getTablesById(tablesDTO.getId());
					table.setCapacity(tablesDTO.getCapacity());
					if(tablesServices.addOrUpdateTable(table)){
						obj.put("table", "updated");
					}else{
						obj.put("table", "not updated");
					}
				
			}
		}
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));
	}
	
	public HashMap<String, String> getOwnermap() {    
	    return ownerMap;
	}
}
