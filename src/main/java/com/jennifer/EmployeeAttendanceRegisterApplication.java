package com.jennifer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeAttendanceRegisterApplication {



	public static void main(String[] args) {
		String workingDir = System.getProperty("user.dir");

		System.out.println("Working Directory = " + workingDir);
		SpringApplication.run(EmployeeAttendanceRegisterApplication.class, args);
	}


}
