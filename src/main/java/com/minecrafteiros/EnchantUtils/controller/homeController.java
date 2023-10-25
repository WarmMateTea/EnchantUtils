package com.minecrafteiros.EnchantUtils.controller;

import com.minecrafteiros.EnchantUtils.model.Encantamento;
import com.minecrafteiros.EnchantUtils.model.EncantamentoDataRecord;
import com.minecrafteiros.EnchantUtils.model.EncatamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/home")
@Controller
public class homeController {

    @Autowired
    private EncatamentoRepository repository;

    @GetMapping()
    public String loadForm() {
        return "/home/home";
    }

    @GetMapping("/listaEncantamentos")
    public String loadListaEncantamentos(Model model) {
        model.addAttribute("listaEncantamentos", repository.findAll());
        return "home/listaEncantamentos";
    }

    @GetMapping("/registroEncantamentos")
    public String loadRegistrarEncantamento() {
        return "home/registroEncantamentos";
    }

    @PostMapping
    public String registrarEncantamento(EncantamentoDataRecord data) {
        repository.save(new Encantamento(data));

        return "home/registroEncantamentos";
    }
}//
