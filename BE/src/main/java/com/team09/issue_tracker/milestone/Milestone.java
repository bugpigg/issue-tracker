package com.team09.issue_tracker.milestone;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Milestone {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "milestone_id")
	private Long id;

	private String title;

	private String description;

	private LocalDate completionDate;

}
