package com.team09.issue_tracker.label;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {

	long countByIdInAndMemberId(List<Long> ids, Long memberId);

}
