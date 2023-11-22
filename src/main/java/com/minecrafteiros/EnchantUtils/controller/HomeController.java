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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@RequestMapping("/home")
@Controller
public class HomeController {

    @Autowired
    private EncatamentoRepository repository;

    @GetMapping()
    public String loadForm() {
        return "/home/home";
    }

    @GetMapping("/listaEncantamentos")
    public String loadListaEncantamentos(Model model) {
        model.addAttribute("listaEncantamentos", repository.findAll());
        fillTable();
        return "home/listaEncantamentos";
    }

    @GetMapping("/registroEncantamentos")
    public String loadRegistrarEncantamento() {
        return "home/registroEncantamentos";
    }

    @PostMapping("/registroEncantamentos")
    public String registrarEncantamento(EncantamentoDataRecord data) {
        System.out.println(data.tesouro() == null ? true : false);
        repository.save(new Encantamento(
                data.nome(),
                data.descricao(),
                data.tesouro() == null ? false : true, /* tá vindo como 'null' em vez de false qd a checkbox fica desmarcada */
                data.nivel_max(),
                data.peso_encantamento()
            ));

        return "home/registroEncantamentos";
    }

    public void fillTable() {   /* Preenche a tabela "encantamento" com todos os encantamentos do minecraft (｡◕‿‿◕｡) */
        try {
            File encantamentos = new File("src\\main\\java\\com\\minecrafteiros\\EnchantUtils\\model\\encantamentos.txt");
            Scanner aux = new Scanner(encantamentos);
            String[] data;
            while (aux.hasNextLine()) {
                data = aux.nextLine().split("#");
                repository.save(new Encantamento(
                        data[0],                       // Nome
                        data[1],                       // Descrição
                        Boolean.parseBoolean(data[4]), // Tesouro
                        Integer.parseInt(data[2]),     // Nivel Max
                        Integer.parseInt(data[3])      // Peso
                    ));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ocorreu um erro na função 'fillTable'!");
            e.printStackTrace();
        }
    }
}
