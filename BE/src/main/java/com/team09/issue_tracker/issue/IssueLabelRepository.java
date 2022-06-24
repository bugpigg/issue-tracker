package com.team09.issue_tracker.issue;

import com.team09.issue_tracker.issue.domain.IssueLabel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface IssueLabelRepository extends JpaRepository<IssueLabel, Long> {

	@Query("select l.id as labelId, l.title as title, l.backgroundColor as backgroundColor, "
		+ "l.isDarkMode as darkMode, "
		+ "l.memberId as memberId, i.issue.id as issueId "
		+ "from IssueLabel i "
		+ "right join i.label l "
		+ "where i.label.memberId = :memberId "
		+ "and i.issue.id = :issueId "
		+ "or (i.label.memberId = :memberId and i.issue.id is null)")
	List<ISelectableLabel> findBySelectable(@Param("issueId") Long issueId,
		@Param("memberId") Long memberId);

}
