package com.team09.issue_tracker.label.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LabelRequestDto {

	private String title;
	private String description;
	private String backgroundColor;
	private boolean isDarkMode;

}
