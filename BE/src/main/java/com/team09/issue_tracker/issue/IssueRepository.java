package com.team09.issue_tracker.issue;

import com.team09.issue_tracker.issue.domain.Issue;
import com.team09.issue_tracker.milestone.Milestone;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IssueRepository extends JpaRepository<Issue, Long> {

	@EntityGraph(attributePaths = {"milestone", "issueLabels", "issueLabels.label"})
	List<Issue> findByMemberIdAndIsOpened(Long memberId, boolean isOpened);

	Stream<Issue> findByMilestone(Milestone milestone);

	@Query(value = "SELECT DISTINCT I "
		+ "FROM Issue AS I "
		+ "LEFT JOIN I.comments AS C "
		+ "LEFT JOIN I.issueAssignees AS IA "
		+ "LEFT JOIN IA.writer AS A "
		+ "LEFT JOIN FETCH I.issueLabels AS IL "
		+ "LEFT JOIN FETCH IL.label AS L "
		+ "LEFT JOIN FETCH I.milestone as M "
		+ "WHERE (:opened = false OR I.isOpened = :opened) "
		+ "AND (:commentByMe = false OR C.writer.id = :currentMemberId) "
		+ "AND (:assignedToMe = false OR A.id = :currentMemberId) "
		+ "AND (coalesce(:writers, NULL) IS NULL OR I.memberId IN (:writers)) "
		+ "AND (coalesce(:labels, NULL) IS NULL OR L.id IN (:labels)) "
		+ "AND (coalesce(:milestones, NULL) IS NULL OR M.id IN (:milestones)) "
		+ "AND (:title IS NULL OR I.title LIKE %:title%)"
	)
	List<Issue> findBySearchCondition(@Param("opened") boolean opened,
		@Param("currentMemberId") Long currentMemberId,
		@Param("commentByMe") boolean commentByMe,
		@Param("assignedToMe") boolean assignedToMe,
		@Param("writers") List<Long> writers,
		@Param("labels") List<Long> labels,
		@Param("milestones") List<Long> milestones,
		@Param("title") String title);
}
