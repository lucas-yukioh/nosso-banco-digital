package com.github.lucasyukio.nossobancodigital.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.lucasyukio.nossobancodigital.dto.TransferenciaDTO;
import com.github.lucasyukio.nossobancodigital.service.TransferenciaService;

@RestController
@RequestMapping("/conta")
public class ContaController {
	
	@Autowired
	TransferenciaService transferenciaService;
	
	@Async
	@PostMapping("/receber-transferencia")
	public void receberTransferencia(@RequestBody @Valid TransferenciaDTO transferenciaDTO) {
		transferenciaService.receberTransferencia(transferenciaDTO);
	}

}
