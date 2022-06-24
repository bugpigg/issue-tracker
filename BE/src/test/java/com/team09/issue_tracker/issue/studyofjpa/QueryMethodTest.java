package com.team09.issue_tracker.issue.studyofjpa;


import static org.assertj.core.api.Assertions.assertThat;

import com.team09.issue_tracker.issue.ISelectableLabel;
import com.team09.issue_tracker.issue.IssueLabelRepository;
import com.team09.issue_tracker.issue.IssueRepository;
import com.team09.issue_tracker.issue.domain.Issue;
import com.team09.issue_tracker.label.LabelRepository;
import com.team09.issue_tracker.member.Member;
import com.team09.issue_tracker.milestone.Milestone;
import com.team09.issue_tracker.milestone.MilestoneRepository;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("메서드 쿼리 학습테스트")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class QueryMethodTest {

	@Autowired
	IssueRepository issueRepository;
	@Autowired
	MilestoneRepository milestoneRepository;
	@Autowired
	LabelRepository labelRepository;
	@Autowired
	IssueLabelRepository issueLabelRepository;


	final Long MEMBER_ID = 1L;
	final Long MILESTONE_ID = 1L;
	final Long ISSUE_ID = 1L;

	@Test
	@DisplayName("IssueLabelRepository.선택하거나 가능한 레벨 목록 조회")
	void findBySelectable() {

		//when
		List<ISelectableLabel> findIssueLabels = issueLabelRepository.findBySelectable(ISSUE_ID,
			MEMBER_ID);

		//then
		assertThat(findIssueLabels).isNotNull();
	}

	@Test
	@DisplayName("Property Expressions 테스트")
	void findByMilestone() {
		//given
		Milestone milestone = Milestone.of(1L);

		//when
		Stream<Issue> findIssue = issueRepository.findByMilestone(milestone);

		//then
		assertThat(findIssue.anyMatch(Objects::nonNull)).isTrue();
	}

	@Test
	@DisplayName("count 쿼리메서드 테스트")
	void countByMilestoneIdAndMemberId() {
		//given
		Member member = Member.of(MEMBER_ID);

		//when
		long count = milestoneRepository.countByIdAndWriter(MILESTONE_ID, member);

		//then
		assertThat(count).isEqualTo(1);
	}

	@Test
	@DisplayName("existByIdsAndMemberId()")
	void existByIdsAndMemberId() {
		//given
		Set<Long> labelIds = Set.of(1L, 2L, 3L);

		//when
		boolean existMyLabels = labelRepository.existsByIdInAndMemberId(labelIds, MEMBER_ID);

		//then
		assertThat(existMyLabels).isFalse();
	}

	@Test
	@DisplayName("existsByIdAndMemberId()")
	void existsByIdAndMemberId() {
		//given
		Long labelId = 3L;

		//when
		boolean existMyLabels = labelRepository.existsByIdAndMemberId(labelId, MEMBER_ID);

		//then
		assertThat(existMyLabels).isFalse();
	}

	@Test
	void countByIdInAndMemberId() {
		//given
		List<Long> labelIds = List.of(1L, 2L, 3L);

		//when
		long labelCount = labelRepository.countByIdInAndMemberId(labelIds, MEMBER_ID);

		//then
		Assertions.assertAll(
			() -> assertThat(labelCount).isNotEqualTo(3),
			() -> assertThat(labelCount).isEqualTo(2),
			() -> assertThat(labelCount).isNotEqualTo(labelIds.size())
		);
	}
}
