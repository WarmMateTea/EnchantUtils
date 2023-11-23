package com.minecrafteiros.EnchantUtils.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minecrafteiros.EnchantUtils.anvil.*;
import com.minecrafteiros.EnchantUtils.model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


@RequestMapping("/home")
@Controller
public class HomeController {
    Long idGlobal = Long.valueOf(0);

    @Autowired
    private EncatamentoRepository repository;

    @GetMapping()
    public String loadForm() {
        return "/views/home/home.html"; //colocar plano de fundo bonitinho do luigi
    }

    @GetMapping("/encantar")
    public String loadEncantar(Model model) {
        List<Encantamento> listaEncantamentos = repository.findAll();
        model.addAttribute("encantamentos", listaEncantamentos);
        return "/views/encantamento/encantar";
    }

    @PostMapping("/encantar")
    public String encantarItens(
            @ModelAttribute List<EncantamentoDTO> ledto,
            @RequestParam("item") String item,
            Model model) {

//        List<EncantamentoDTO> ledto = new ArrayList<>();
//        for (Map<String, Object> encantamento : dadosBotoesSelecionados) {
//            String nome = (String) encantamento.get("nome");
//            Long id = (Long) encantamento.get("id");
//            Integer nivel = (Integer) encantamento.get("nivel");
//            ledto.add(new EncantamentoDTO(nome, id, nivel));
//        }

        Item i = new Item(item, item.toUpperCase());    //incrementar depois
        Anvil a = new Anvil();
        AnvilResponse ar = a.Request(i, convertEncantamentoToEnchantment(ledto));
        model.addAttribute("listaExterna",ar.getTreeLevels());
        model.addAttribute("custo",ar.getCost());
        return "/views/encantamento/resultado";  //ainda n existe
    }

    private ArrayList<Enchantment> convertEncantamentoToEnchantment(List<EncantamentoDTO> dbs) {
        ArrayList<Enchantment> l = new ArrayList<>();
        for (EncantamentoDTO edto : dbs) {
            l.add(new Enchantment(EnchantmentReferences.EnchantRef.get(edto.getId()), edto.getNome().toLowerCase().replace(' ', '_'), edto.getNivel()));
        }
        return l;
    }

    @GetMapping("/tesouro")
    public String listarEncantamentosTesouro(Model model) {
        List<Encantamento> encantamentos = repository.findAllTesouros();
        model.addAttribute("encantamentos", encantamentos);
        return "/views/encantamento/encantar";
    }

    @GetMapping("/todos")
    public String getTodosEncantamentos(Model model) {
        List<Encantamento> encantamentos = repository.findAll();
        model.addAttribute("encantamentos", encantamentos);
        return "/views/encantamento/encantar";
    }

    @GetMapping("/por-nome")
    public String getEncantamentosPorNome(@RequestParam String nome, Model model) {
        List<Encantamento> encantamentos = repository.findByNome(nome);
        model.addAttribute("encantamentos", encantamentos);
        return "/views/encantamento/encantar";
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


    @GetMapping("/formEncantamento")
    public String editarEncantamento(Long id, Model model) {
        Encantamento encantamento = new Encantamento();
        if (id != null) {
            encantamento = repository.findById(id).orElse(null);
            idGlobal = id;
        }
        model.addAttribute("encantamento", encantamento);
        return "/views/encantamento/formEncantamento";
    }

    @PostMapping("/saveEncantamento")
    public String saveEncantamento(@ModelAttribute Encantamento encantamento) {
        if (idGlobal != 0) {
            repository.deleteById(idGlobal);
        }
        repository.save(encantamento);
        return "redirect:/home/listaEncantamentos";
    }

    @GetMapping("/deleteEncantamento/{id}")
    public String deleteEncantamento(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/home/listaEncantamentos";
    }

    @PostMapping("/registroEncantamentos")
    public String registrarEncantamento(EncantamentoDataRecord data) {
        repository.save(new Encantamento(
                data.nome(),
                data.descricao(),
                data.tesouro() == null ? false : true, /*
                                                        * tá vindo como 'null' em vez de false qd a checkbox fica
                                                        * desmarcada
                                                        */
                data.nivel_max(),
                data.peso_encantamento()));

        return "/views/encantamento/registroEncantamentos";
    }

    @RequestMapping(value = "/resetTable")
    public String resetTable() {
        repository.deleteAll();
        fillTable();
        return "redirect:/home/listaEncantamentos";
    }

    public void fillTable() { /*
                               * Preenche a tabela "encantamento" com todos os encantamentos do minecraft
                               * (｡◕‿‿◕｡)
                               */
        try {
            File encantamentos = new File("src\\main\\resources\\static\\csv\\encantamentos.csv");
            Scanner aux = new Scanner(encantamentos);
            String[] data;
            while (aux.hasNextLine()) {
                data = aux.nextLine().split(";");
                repository.save(new Encantamento(
                        data[0], // Nome
                        data[1], // Descrição
                        Boolean.parseBoolean(data[4]), // Tesouro
                        Integer.parseInt(data[2]), // Nivel Max
                        Integer.parseInt(data[3]) // Peso
                ));
            }
            aux.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ocorreu um erro na função 'fillTable'!");
            e.printStackTrace();
        }
    }
}
