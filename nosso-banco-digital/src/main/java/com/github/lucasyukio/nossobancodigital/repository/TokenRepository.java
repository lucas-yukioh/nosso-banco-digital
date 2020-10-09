package com.github.lucasyukio.nossobancodigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.lucasyukio.nossobancodigital.model.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {

}
