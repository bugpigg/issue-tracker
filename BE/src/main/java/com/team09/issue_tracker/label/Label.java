package com.team09.issue_tracker.label;

import com.team09.issue_tracker.label.dto.LabelSelectResponseDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Label {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "label_id")
	private Long id;

	private String title;

	private String description;

	private Boolean isDarkMode;

	private String backgroundColor;

	private Long memberId;

	public Long getId() {
		return id;
	}

	public static Label of(Long labelid) {
		return Label.builder()
			.id(labelid)
			.build();
	}

	public LabelSelectResponseDto toResponseDto() {
		return LabelSelectResponseDto.builder()
			.id(id)
			.title(title)
			.description(description)
			.backgroundColor(backgroundColor)
			.isDarkMode(isDarkMode)
			.build();
	}
}
