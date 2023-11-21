package com.minecrafteiros.EnchantUtils.controller;

import com.minecrafteiros.EnchantUtils.model.Encantamento;
import com.minecrafteiros.EnchantUtils.model.EncantamentoDataRecord;
import com.minecrafteiros.EnchantUtils.model.EncantamentoEditRecord;
import com.minecrafteiros.EnchantUtils.model.EncatamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@RequestMapping("/home")
@Controller
public class homeController {

    @Autowired
    private EncatamentoRepository repository;

    @GetMapping()
    public String loadForm() {
        return "/views/home/home.html";
    }

    @GetMapping("/listaEncantamentos")
    public String loadListaEncantamentos(Model model) {
        model.addAttribute("listaEncantamentos", repository.findAll());
        return "/views/encantamento/listaEncantamentos";
    }

    @GetMapping("/registroEncantamentos")
    public String loadRegistrarEncantamento() {
        return "/views/encantamento/registroEncantamentos";
    }

    @RequestMapping(value="/formEncantamento") 
    public String editarEncantamento(@RequestParam Long id, Model model) {
        Encantamento encantamento = repository.findById(id).orElse(null);
        if (encantamento != null) {
            model.addAttribute("encantamento", encantamento);
            return "/views/encantamento/formEncantamento";
        } else {
            return "redirect:/views/encantamento/listaEncantamentos";
        }
    }

    @PostMapping("/registroEncantamentos")
    public String registrarEncantamento(EncantamentoDataRecord data) {
        repository.save(new Encantamento(
                data.nome(),
                data.descricao(),
                data.tesouro() == null ? false : true, /* tá vindo como 'null' em vez de false qd a checkbox fica desmarcada */
                data.nivel_max(),
                data.peso_encantamento()
            ));

        return "/views/encantamento/registroEncantamentos";
    }

    @RequestMapping(value="/resetTable")
    public String resetTable() {
        repository.deleteAll();
        fillTable();
        return "redirect:/home/listaEncantamentos";
    }


    public void fillTable() {   /* Preenche a tabela "encantamento" com todos os encantamentos do minecraft (｡◕‿‿◕｡) */
        try {
            File encantamentos = new File("src\\main\\resources\\static\\csv\\encantamentos.csv");
            Scanner aux = new Scanner(encantamentos);
            String[] data;
            while (aux.hasNextLine()) {
                data = aux.nextLine().split(";");
                repository.save(new Encantamento(
                        data[0],                       // Nome
                        data[1],                       // Descrição
                        Boolean.parseBoolean(data[4]), // Tesouro
                        Integer.parseInt(data[2]),     // Nivel Max
                        Integer.parseInt(data[3])      // Peso
                    ));
            }
            aux.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ocorreu um erro na função 'fillTable'!");
            e.printStackTrace();
        }
    }
}
