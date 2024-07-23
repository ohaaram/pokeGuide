package com.pokeguide.repository;

import com.pokeguide.entity.Terms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermsRepository extends JpaRepository<Terms, Integer> {
}
