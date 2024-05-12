package com.example.SZP.test;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SZP.test.Tutorial;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
    List<Tutorial> findByPublished(boolean published);

    List<Tutorial> findByTitleContaining(String title);
}
