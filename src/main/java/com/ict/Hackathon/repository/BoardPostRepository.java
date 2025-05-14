package com.ict.Hackathon.repository;

import com.ict.Hackathon.entity.BoardPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardPostRepository extends JpaRepository<BoardPost, Long> {

	Page<BoardPost> findAllByCategoryId(int categoryId, Pageable pageable);
}