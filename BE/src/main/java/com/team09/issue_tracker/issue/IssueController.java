package com.team09.issue_tracker.issue;

import com.team09.issue_tracker.issue.dto.IssueCreateRequestDto;
import com.team09.issue_tracker.issue.dto.IssueUpdateAllRequestDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/issues")
public class IssueController {

	@GetMapping
	public void findAll() {

	}

	@GetMapping("/{id}")
	public void findById(@PathVariable final Long id) {

	}

	@PostMapping
	public void create(@RequestBody IssueCreateRequestDto issueCreateRequestDto) {

	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable final Long id) {

	}

	@PatchMapping("/{id}")
	public void updateState(@PathVariable final Long id, @RequestParam final Boolean setClose) {

	}

	@GetMapping
	public void findByTitle(@RequestParam final String title) {

	}

	@GetMapping
	public void findBySearchCondition(@ModelAttribute final String dto) {

	}

	@PatchMapping
	public void updateAllState(@RequestParam final Boolean setClose, @RequestBody final IssueUpdateAllRequestDto issueUpdateAllRequestDto) {

	}

}
