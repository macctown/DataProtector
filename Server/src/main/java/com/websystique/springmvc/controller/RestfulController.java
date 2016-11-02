package com.websystique.springmvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websystique.springmvc.model.FileUpload;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.model.UserCert;
import com.websystique.springmvc.service.UserCertService;
import com.websystique.springmvc.service.UserService;

@RestController
@RequestMapping("/rest")
@SessionAttributes("roles")
public class RestfulController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserCertService userCertService;
	
	
	//USER
	
	//get user info by ID
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String getUserById(@PathVariable String id) {
 
		int user_id = Integer.valueOf(id);
		
		User user = userService.findById(user_id);
		
		JSONObject outputJsonObj = new JSONObject();
		
		ObjectMapper m = new ObjectMapper();
		Map<String,Object> userMap = m.convertValue(user, Map.class);
		User anotherBean = m.convertValue(userMap, User.class);
		
		if(user!=null){
			outputJsonObj.put("status", "SUCCESS");
			outputJsonObj.put("statusDesc", "Get User Successfully");
			outputJsonObj.put("user", userMap);
		}
		else{
			outputJsonObj.put("status", "FAILED");
			outputJsonObj.put("statusDesc", "User Not Found");
		}
		
   	 	return outputJsonObj.toString();
 
    }
 
	//login
	//need to call getCsrfToken first, then inject the csrftoken you get to your request header
	@RequestMapping(value = "/user/login", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String loginUser(HttpServletRequest request,
            HttpServletResponse response, Model model) {
 
		
		String ssoId = request.getParameter("ssoId");
		String password = request.getParameter("password");
		System.out.println(ssoId+ " " +password);
		
		JSONObject outputJsonObj = new JSONObject();
		User user = userService.findBySSO(ssoId);
		ObjectMapper m = new ObjectMapper();
		Map<String,Object> userMap = m.convertValue(user, Map.class);
		User anotherBean = m.convertValue(userMap, User.class);
		
		if(user == null){
			outputJsonObj.put("status", "FAILED");
			outputJsonObj.put("statusDesc", "User Not Found");
		}
		else if(user.getPassword().equals(password)){
			outputJsonObj.put("status", "SUCCESS");
			outputJsonObj.put("statusDesc", "User Login Successfully");
			outputJsonObj.put("user", userMap);
		}
		else{
			outputJsonObj.put("status", "FAILED");
			outputJsonObj.put("statusDesc", "Invalid SSOID or Password");
		}
		
 
		return outputJsonObj.toString();
	}
	
	@RequestMapping(value="/csrf-token", method=RequestMethod.GET)
	public @ResponseBody String getCsrfToken(HttpServletRequest request) {
	    CsrfToken token = (CsrfToken)request.getAttribute(CsrfToken.class.getName());
	    return token.getToken();
	}
	
	//File
	
	//get certs list by userId
	@RequestMapping(value = "/certs/{userId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getCertsByUserId(@PathVariable String userId) {
	 
			int user_id = Integer.valueOf(userId);
			
			List<UserCert> userCertList = userCertService.findByUserId(user_id);
			
			JSONObject outputJsonObj = new JSONObject();
			
			
			
			if(userCertList!=null && userCertList.size()!=0){
				outputJsonObj.put("status", "SUCCESS");
				outputJsonObj.put("statusDesc", "Get Cert Successfully");
				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>(); 
				for(UserCert cert : userCertList){
					ObjectMapper m = new ObjectMapper();
					Map<String,Object> userCertMap = m.convertValue(cert, Map.class);
					UserCert anotherBean = m.convertValue(userCertMap, UserCert.class);
					list.add(userCertMap);
				}
				outputJsonObj.put("certsList", new JSONArray(list));
				
			}
			else if(userCertList.size()==0){
				outputJsonObj.put("status", "FAILED");
				outputJsonObj.put("statusDesc", "Current User Does Not Have Any Cert");
			}
			else{

				outputJsonObj.put("status", "FAILED");
				outputJsonObj.put("statusDesc", "Get Certs of Current User Falied");
			}
			
	   	 	return outputJsonObj.toString();
	    }
	
	//get cert by id
	@RequestMapping(value = "/cert/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String getCertById(@PathVariable String id) {
 
		int cert_id = Integer.valueOf(id);
		
		UserCert userCert = userCertService.findByCertId(cert_id);
		
		JSONObject outputJsonObj = new JSONObject();
		
		ObjectMapper m = new ObjectMapper();
		Map<String,Object> userCertMap = m.convertValue(userCert, Map.class);
		UserCert anotherBean = m.convertValue(userCertMap, UserCert.class);
		
		if(userCert!=null){
			outputJsonObj.put("status", "SUCCESS");
			outputJsonObj.put("statusDesc", "Get Cert Successfully");
			outputJsonObj.put("cert", userCertMap);
		}
		else{
			outputJsonObj.put("status", "FAILED");
			outputJsonObj.put("statusDesc", "Cert Not Found");
		}
		
   	 	return outputJsonObj.toString();
    }
	
	//delete cert by id
	@RequestMapping(value = "/cert/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody String deleteCertById(@PathVariable String id) {
	 
			int cert_id = Integer.valueOf(id);
			JSONObject outputJsonObj = new JSONObject();
			
			UserCert userCert = userCertService.findByCertId(cert_id);
			
			if(userCert == null){
				outputJsonObj.put("status", "FAILED");
				outputJsonObj.put("statusDesc", "Cert Not Found");
			}
			else{
				try{
					userCertService.deleteByCertId(cert_id);
					outputJsonObj.put("status", "SUCCESS");
					outputJsonObj.put("statusDesc", "Delete Cert Successfully");
				}
				catch(Exception e){
					outputJsonObj.put("status", "FAILED");
					outputJsonObj.put("statusDesc", "Delete Cert Failed");
				}
			}			
			
	   	 	return outputJsonObj.toString();
	}
	
	//auth cert
	//need to call getCsrfToken first, then inject the csrftoken you get to your request header
	@RequestMapping(value = "/cert/auth", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String authCert(@Valid FileUpload fileUpload,
            BindingResult result, ModelMap model, HttpServletRequest request) {
		
		JSONObject outputJsonObj = new JSONObject();
		
		if (result.hasErrors()) {
            System.out.println("validation errors");
            outputJsonObj.put("status", "FAILED");
			outputJsonObj.put("statusDesc", "File Upload Failed, Pleas Check Your File");
			
        } else {
            System.out.println("Fetching file");
            // Now do something with file...
            //FileCopyUtils.copy(fileUpload.getFile().getBytes(), new File( UPLOAD_LOCATION + fileUpload.getFile().getOriginalFilename()));

    		String certId = request.getParameter("certId");
            String hash_value = fileUpload.getHash();
            int cert_id = Integer.valueOf(certId);
            
            UserCert cert = userCertService.findByCertId(cert_id);
            if(cert == null){
            	outputJsonObj.put("status", "FAILED");
     			outputJsonObj.put("statusDesc", "Authenticate Cert Failed, Pleas Check CertID");
            }
            else{
            	if(userCertService.authUserCert(cert_id, hash_value)){
            		//the cert is authenticate
            		outputJsonObj.put("status", "SUCCESS");
         			outputJsonObj.put("statusDesc", "Authenticate Cert Successfully");
         			outputJsonObj.put("authResult", true);
                }
                else{
                	//the cert is NOT authenticate
                	outputJsonObj.put("status", "FAILED");
         			outputJsonObj.put("statusDesc", "Authenticate Cert Failed");
         			outputJsonObj.put("authResult", false);
                }
            }
   
        }
		
		return outputJsonObj.toString();
	}
	
	//upload file
	//need to call getCsrfToken first, then inject the csrftoken you get to your request header
	@RequestMapping(value = "/cert", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String uploadCert(@Valid FileUpload fileUpload,
            BindingResult result, ModelMap model, HttpServletRequest request) throws Exception {

		JSONObject outputJsonObj = new JSONObject();
		
		
		if (result.hasErrors()) {
            System.out.println("validation errors");
            outputJsonObj.put("status", "FAILED");
			outputJsonObj.put("statusDesc", "File Upload Failed, Pleas Check Your File");
        } else {
            System.out.println("Fetching file");
            MultipartFile file = fileUpload.getFile();
            // Now do something with file...
            //FileCopyUtils.copy(fileUpload.getFile().getBytes(), new File( UPLOAD_LOCATION + fileUpload.getFile().getOriginalFilename()));
            String file_name = file.getOriginalFilename();
            String hash_value = fileUpload.getHash();
            String file_type = file.getContentType();
            

    		String ssoId = request.getParameter("ssoId");
            User currentUser = userService.findBySSO(ssoId);
            if(currentUser!=null){
            	UserCert newCert = new UserCert();
                newCert.setFile_name(file_name);
                newCert.setHash_value(hash_value);
                newCert.setUser_id(currentUser.getId());
                newCert.setFile_type(file_type);
                
                try{
                	userCertService.saveUser(newCert);
                	
                	ObjectMapper m = new ObjectMapper();
            		Map<String,Object> userCertMap = m.convertValue(newCert, Map.class);
            		UserCert anotherBean = m.convertValue(userCertMap, UserCert.class);
            		
                	outputJsonObj.put("status", "SUCCESS");
    				outputJsonObj.put("statusDesc", "Upload File Successfully");
    				outputJsonObj.put("newCert", userCertMap);
                }
                catch(Exception e){
                	outputJsonObj.put("status", "FAILED");
        			outputJsonObj.put("statusDesc", "Generate Certificate Failed");
                }
            }
            else{
            	outputJsonObj.put("status", "FAILED");
    			outputJsonObj.put("statusDesc", "Upload File Faled, Please Check SSOID");
            }

       }         

		return outputJsonObj.toString();
	}
	
}
