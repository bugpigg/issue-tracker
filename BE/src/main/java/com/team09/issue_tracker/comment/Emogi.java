package com.team09.issue_tracker.comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Emogi {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emogi_id")
	private Long id;

	private String unicodeValue;

}
