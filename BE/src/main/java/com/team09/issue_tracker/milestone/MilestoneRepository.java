package com.team09.issue_tracker.milestone;

import com.team09.issue_tracker.member.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

	long countByIdAndWriter(Long id , Member writer);

	List<Milestone> findByWriter(Member writer);
}
