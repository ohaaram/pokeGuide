package com.pokeguide.controller.user;

import com.pokeguide.dto.TermsDTO;
import com.pokeguide.service.TermsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
public class TermsController {

    private final TermsService termsService;

    @GetMapping("/user/Terms")
        public ResponseEntity<?> selectTerms() {
            List<TermsDTO> termsList = termsService.getAllTerms();
            return ResponseEntity.ok(termsList);
        }
    }
