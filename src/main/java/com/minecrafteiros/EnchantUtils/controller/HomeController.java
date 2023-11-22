package com.minecrafteiros.EnchantUtils.controller;

import com.minecrafteiros.EnchantUtils.anvil.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


@RequestMapping("/home")
@Controller
public class HomeController {

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
            @RequestParam("item") String item,
            @RequestParam("encantamentos") List<Long> encantamentosSelecionados,
            @RequestParam Map<String,String> enchantmentValues,
            Model model) {
        List<Encantamento> listaEncantamentosSelecionados = repository.findAllById(encantamentosSelecionados);

        Item i = new Item(item, item.toUpperCase());    //incrementar depois
        Anvil a = new Anvil();
        AnvilResponse ar = a.Request(i, convertEncantamentoToEnchantment(listaEncantamentosSelecionados, enchantmentValues));

        return "/views/encantamento/resultado";  //ainda n existe
    }

    @GetMapping("/encantar-tesouro")
    public String listarEncantamentosTesouro(Model model) {
        List<Encantamento> encantamentos = repository.findAllTesouros();
        model.addAttribute("encantamentos", encantamentos);
        return "listaEncantamentos";
    }

    @GetMapping("/encantar-todos")
    public List<Encantamento> getTodosEncantamentos() {
        return repository.findAll();
    }

    @GetMapping("/encantar-por-nome")
    public List<Encantamento> getEncantamentosPorNome(@RequestParam String nome) {
        return repository.findByNome(nome);
    }

    private ArrayList<Enchantment> convertEncantamentoToEnchantment(List<Encantamento> listaEntrada, Map<String,String> enchantmentValues) {
        ArrayList<Enchantment> listaSaida = new ArrayList<Enchantment>();
        for (Encantamento e : listaEntrada) {
            listaSaida.add(new Enchantment(
                    EnchantmentReferences.EnchantRef.get(e.getNome().toLowerCase().replace(" ", "_")),
                    e.getNome(),
                    Integer.parseInt(enchantmentValues.get("enchantmentValues[" + String.valueOf(e.getId()) + "]"))
            ));
        }
        return listaSaida;
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
        if (id != null) {
            Encantamento encantamento = repository.findById(id).orElse(null);
            model.addAttribute("encantamento", encantamento);
            // return "/views/encantamento/formEncantamento";
        }
        return "/views/encantamento/formEncantamento";
    }

    @PostMapping("/saveEncantamento")
    public String saveEncantamento(@ModelAttribute Encantamento encantamento) {
        System.out.println(encantamento);
        repository.save(encantamento);
        return "redirect:/views/encantamento/formEncantamento";
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
