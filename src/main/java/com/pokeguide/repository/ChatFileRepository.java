package com.pokeguide.repository;

import com.pokeguide.entity.ChatFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatFileRepository extends JpaRepository<ChatFile,String> {
}
