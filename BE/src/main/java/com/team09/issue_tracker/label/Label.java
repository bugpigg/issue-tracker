package com.team09.issue_tracker.label;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Label {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "label_id")
	private Long id;

	private String title;

	private String description;

	private boolean isDarkMode;

	private String backgroundColor;

}
