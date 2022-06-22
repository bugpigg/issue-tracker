package com.team09.issue_tracker.login.controller;

import com.team09.issue_tracker.login.controller.dto.LoginResponseDto;
import com.team09.issue_tracker.login.controller.dto.TokenReissueRequestDto;
import com.team09.issue_tracker.login.controller.dto.TokenReissueResponseDto;
import com.team09.issue_tracker.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginController {

	private final LoginService loginService;

	@GetMapping("/oauth/{provider}")
	public ResponseEntity<LoginResponseDto> login(@PathVariable String provider,
		@RequestParam String code) {
		LoginResponseDto loginResponse = loginService.login(provider, code);
		return ResponseEntity.ok(loginResponse);
	}

	@GetMapping("/oauth/reissue")
	public ResponseEntity<TokenReissueResponseDto> reissue(
		@RequestBody TokenReissueRequestDto reissueRequestDto) {
		return ResponseEntity.ok(loginService.reissue(reissueRequestDto));
	}
}
