package com.green.blue.red.repository;

import com.green.blue.red.domain.ScoreVO;
import com.green.blue.red.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<ScoreVO,Long> {

}
