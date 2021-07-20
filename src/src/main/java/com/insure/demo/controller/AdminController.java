package com.insure.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.insure.demo.entity.Admin;
import com.insure.demo.service.AdminService;
import com.insure.demo.service.UserService;

@CrossOrigin(origins = "*")
@EnableAspectJAutoProxy(proxyTargetClass = true)

@RestController
@RequestMapping("/Defender/Admin")
//api/Users
public class AdminController {

	
	 @Autowired
	   private AdminService adminService;
	 
	 @PostMapping(value = "/", headers = "Accept=application/json")
		public ResponseEntity<Admin> create(@RequestBody Admin user) {

			Admin actual = adminService.create(user);
			HttpHeaders headers = new HttpHeaders();
			// headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(team.getTeamid()).toUri());
			return new ResponseEntity<>(actual, headers, HttpStatus.CREATED);
		}

		@PostMapping(value = "/login")
		public ResponseEntity<Admin> login(@RequestBody Admin current) {
			System.out.println(current.getAdmin());

			Admin user = adminService.login(current);
			if (user == null) {
				System.out.println("no data");

				return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(user, HttpStatus.OK);
		}

		@GetMapping("/")
		public @ResponseBody ResponseEntity<List<Admin>> all() {
			return new ResponseEntity<>(adminService.get(), HttpStatus.OK);
		}

		@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Admin> getTeamById(@PathVariable("id") long id) {
			Admin user = adminService.findById(id);
			if (user == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(user, HttpStatus.OK);
		}

		@PutMapping(value = "/{id}", headers = "Accept=application/json")
		public ResponseEntity<Admin> update(@PathVariable("id") long id, @RequestBody Admin current) {
			Admin user = adminService.update(id, current);
			return new ResponseEntity<>(user, HttpStatus.OK);
		}

		@DeleteMapping(value = "/{id}", headers = "Accept=application/json")
		public ResponseEntity<Admin> delete(@PathVariable("id") Long id) {
			Admin user = adminService.findById(id);
			if (user == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			adminService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
}
