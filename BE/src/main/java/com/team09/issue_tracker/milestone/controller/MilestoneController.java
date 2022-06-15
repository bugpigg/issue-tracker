package com.team09.issue_tracker.milestone.controller;

import com.team09.issue_tracker.milestone.MilestoneRequestDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/milestones")
public class MilestoneController {

	@GetMapping
	public void select() {

	}

	@PostMapping
	public void create(@RequestBody final MilestoneRequestDto milestoneRequestDto) {

	}

	@PatchMapping("/{id}")
	public void update(@RequestBody final MilestoneRequestDto milestoneRequestDto) {

	}
}
