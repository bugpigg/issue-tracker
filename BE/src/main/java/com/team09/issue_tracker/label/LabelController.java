package com.team09.issue_tracker.label;

import com.team09.issue_tracker.label.dto.LabelRequestDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/labels")
public class LabelController {

	@GetMapping
	public void select() {

	}

	@PostMapping
	public void create(@RequestBody final LabelRequestDto labelRequestDto) {

	}

	@PatchMapping
	public void update(@RequestBody final LabelRequestDto labelRequestDto){

	}
}
