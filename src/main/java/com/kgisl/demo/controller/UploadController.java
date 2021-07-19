package com.kgisl.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kgisl.demo.entity.User;
import com.kgisl.demo.service.UserService;



@CrossOrigin(origins = "*")
//@EnableAspectJAutoProxy(proxyTargetClass = true)

@Controller
@RequestMapping("/Defender/Upload")
//api/Users


public class UploadController {
	@Autowired
	private UserService userService;

	// @Autowired
	// private ModelMapper modelMapper;
	public String EXCEL_FILE_PATH;

	@Value("${app.upload.dir:${user.home}}")
	public String uploadDir;

	public String uploadFile(MultipartFile file) {
		String path = null;
		try {
		//	//Path path1 = Paths.get(StringUtils.cleanPath(file.getOriginalFilename()));
		//	System.out.println("hii" + path1.toAbsolutePath() );
			System.out.println("hii2" +  StringUtils.cleanPath(file.getOriginalFilename()));
			Path copyLocation = Paths
					.get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
			copyLocation = Paths.get(uploadDir);
			Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
			// Files.copy(copyLocation,uploadDir,
			// StandardCopyOption.REPLACE_EXISTING);
		

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Could not store file " + file.getOriginalFilename() + ". Please try again!");
		}
		return uploadDir;
	}
	String message = "";
	@PostMapping("/uploadFile")
	public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request,
	        HttpServletResponse response) {

		String filePath = uploadFile(file);
		EXCEL_FILE_PATH = filePath;
		redirectAttributes.addFlashAttribute("message",
				"You have successfully uploaded '" + file.getOriginalFilename() + "' !");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<User> excelDataAsList = getExcelDataAsList();
		String message = validate(excelDataAsList);
		ModelAndView mav = new ModelAndView();
		if(message=="") {
			int noOfRecords = userService.saveExcelData(excelDataAsList);
			// model.addAttribute("noOfRecords",noOfRecords);

			for (User u : excelDataAsList) {
				System.out.println(u);
			}
			  return "redirect:/#!/agent";
		}
		else {
			  mav.addObject("message", message);	
		}
		
		System.out.println("message"+message);
	    mav.setViewName("table.html");

	    return "redirect:/#!/error";
	}
	@RequestMapping(value = "/error", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public String index() {
		
		System.out.println("message::::::::"+message);
        return message;
    }
	Workbook workbook;

	public List<User> getExcelDataAsList() {

		List<String> list = new ArrayList<String>();

		// Create a DataFormatter to format and get each cell's value as String
		DataFormatter dataFormatter = new DataFormatter();

		// Create the Workbook
		try {
			workbook = WorkbookFactory.create(new File(EXCEL_FILE_PATH));
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
		}

		// Retrieving the number of sheets in the Workbook
		System.out.println("-------Workbook has '" + workbook.getNumberOfSheets() + "' Sheets-----");

		// Getting the Sheet at index zero
		Sheet sheet = workbook.getSheetAt(0);

		// Getting number of columns in the Sheet
		int noOfColumns = sheet.getRow(0).getLastCellNum();
		System.out.println("-------Sheet has '" + noOfColumns + "' columns------");

		// Using for-each loop to iterate over the rows and columns
		for (Row row : sheet) {
			for (Cell cell : row) {
				String cellValue = dataFormatter.formatCellValue(cell);
				list.add(cellValue);
			}
		}

		// filling excel data and creating list as List<Invoice>
		List<User> invList = createList(list, noOfColumns);

		// Closing the workbook
		try {
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return invList;
	}

	private String validate(List<User> invList) {
		String msg ="";
		for(User u: invList) {
		 if (!validateEmail(u.getUserEmail())) {
			 if(msg=="") {
				 msg = ("--- invalid email ---");  
			 }
			 else {
				 msg = msg + ("invalid email ---"); 
			 }
		 }
		 if(!isValidPanCardNo(u.getIcNo())) {
			 if(msg=="") {
				 msg = ("--- invalid PAN number ---");  
			 }
			 else {
				 msg = msg + ("invalid PAN number ---"); 
			 }
		 }
//		 if (!isValidMobileNo(u.getMobileNo())){
//			 if(msg=="") {
//				 msg = ("--- invalid Mobile number ---");  
//			 }
//			 else {
//				 msg = msg + ("invalid Mobile number ---"); 
//			 }
//		 }
		 if(!isValidPinCode(u.getPostCode())) {
			 if(msg=="") {
				 msg = ("--- invalid POST CODE number ---");  
			 }
			 else {
				 msg = msg + ("invalid POST CODE number ---"); 
			 }
		 }
//		 if(!isValidDOBDate(u.getDOB())) {
//			 System.out.println("u.getDOB()"+u.getDOB());
//			 if(msg=="") {
//				 msg = ("--- invalid DOB format ---");  
//			 }
//			 else {
//				 msg = msg + ("invalid DOB format ---"); 
//			 }
//		 }
		 if(u.getGender().equals("F") || u.getGender().equals("M") || u.getGender().equals("O")) {
			 
		 }
		 else {
			 if(msg=="") {
				 msg = ("--- PLEASE ENTER THE CORRECT GENDER ---");  
			 }
			 else {
				 msg = msg + ("PLEASE ENTER THE CORRECT GENDER ---"); 
			 }
		 }
		}
		
		return msg;
	}

	public boolean validateEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
                              
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
	private boolean isValidPanCardNo(String panCardNo)
	    {
	 
	        // Regex to check valid PAN Card number.
	        String regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
	 
	        // Compile the ReGex
	        Pattern p = Pattern.compile(regex);
	 
	        // If the PAN Card number
	        // is empty return false
	        if (panCardNo == null)
	        {
	            return false;
	        }
	 
	        // Pattern class contains matcher() method
	        // to find matching between given
	        // PAN Card number using regular expression.
	        Matcher m = p.matcher(panCardNo);
	 
	        // Return if the PAN Card number
	        // matched the ReGex
	        return m.matches();
	    }
	public static boolean isValidMobileNo(String s)
	{
	     
	    // The given argument to compile() method
	    // is regular expression. With the help of
	    // regular expression we can validate mobile
	    // number.
	    // 1) Begins with 0 or 91
	    // 2) Then contains 7 or 8 or 9.
	    // 3) Then contains 9 digits
	    Pattern p = Pattern.compile("[7-9][0-9]{10}");
	 
	    // Pattern class contains matcher() method
	    // to find matching between given number
	    // and regular expression
	    Matcher m = p.matcher(s);
	    return (m.find() && m.group().equals(s));
	}
	// Function to validate the pin code of India.
    public static boolean isValidPinCode(String pinCode)
    {
 
        // Regex to check valid pin code of India.
        String regex
            = "^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$";
 
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
 
        // If the pin code is empty
        // return false
        if (pinCode == null) {
            return false;
        }
 
        // Pattern class contains matcher() method
        // to find matching between given pin code
        // and regular expression.
        Matcher m = p.matcher(pinCode);
 
        // Return if the pin code
        // matched the ReGex
        return m.matches();
    }
    public static boolean isValidDOBDate(String d)
    {
        String regex = "^(1[0-2]|0[1-9])/(3[01]"
                       + "|[12][0-9]|0[1-9])/[0-9]{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher((CharSequence)d);
        return matcher.matches();
    }
	private List<User> createList(List<String> excelData, int noOfColumns) {
          
		ArrayList<User> invList = new ArrayList<>();

		int i = noOfColumns;
		do {
			User inv = new User();
			inv.setUserName(excelData.get(i));
			inv.setUserEmail(excelData.get(i + 1));
			inv.setAgentCode(excelData.get(i + 2));
			inv.setAgentName(excelData.get(i + 3));
			inv.setIcNo(excelData.get(i + 4));
			// inv.setBusinessRegNo(excelData.get(i + 5));
			inv.setAddress1(excelData.get(i + 5));
			inv.setAddress2(excelData.get(i + 6));	
			inv.setPostCode(excelData.get(i + 7));
			inv.setState(excelData.get(i + 8));
			inv.setMobileNo(excelData.get(i + 9));
			int age =  Integer.parseInt(excelData.get(i + 11));
			inv.setAge(age);
			inv.setGender(excelData.get(i + 10));
			inv.setMaritalStatus(excelData.get(i + 12));
			invList.add(inv);
			i = i + (noOfColumns);

		} while (i < excelData.size());
		return invList;
	}

	@GetMapping("/saveData")
	public String saveExcelData(Model model) {

		List<User> excelDataAsList = getExcelDataAsList();
		int noOfRecords = userService.saveExcelData(excelDataAsList);
		model.addAttribute("noOfRecords", noOfRecords);

		for (User u : excelDataAsList) {
			System.out.println(u);
		}
		return "success";
	}
	
	
}
