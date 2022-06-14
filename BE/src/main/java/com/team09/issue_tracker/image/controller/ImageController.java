package com.team09.issue_tracker.image.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

	@GetMapping("/images")
	public void findPreSignedUrl() {

	}
}
