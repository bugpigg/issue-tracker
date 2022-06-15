package com.team09.issue_tracker.comment;

import com.team09.issue_tracker.comment.Comment;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Emogi {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emogi_id")
	private Long id;

	private String unicodeValue;

	@ManyToOne
	@JoinColumn(name = "comment_id")
	private Comment comment;

}
