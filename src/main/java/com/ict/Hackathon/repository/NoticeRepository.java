package com.ict.Hackathon.repository;

import com.ict.Hackathon.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}