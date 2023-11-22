package com.minecrafteiros.EnchantUtils.controller;

import com.minecrafteiros.EnchantUtils.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@RequestMapping("/home")
@Controller
public class homeController {

    @Autowired
    private Encantamento_ReferenciaRepository repository;
    private Item_ReferenciaRepository itemRefRepository;

    @GetMapping()
    public String loadForm() {
        return "/home/home";
    }

    @GetMapping("/encantar")
    public String loadEncantar(Model model) {
        model.addAttribute("anvilRequest", );
    }

    @GetMapping("/listaEncantamentos")
    public String loadListaEncantamentos(Model model) {
        model.addAttribute("listaEncantamentos", repository.findAll());
        fillEnchantmentTable();
        return "home/listaEncantamentos";
    }

    @GetMapping("/registroEncantamentos")
    public String loadRegistrarEncantamento() {
        return "home/registroEncantamentos";
    }

    @PostMapping("/registroEncantamentos")
    public String registrarEncantamento_Referencia(Encantamento_ReferenciaDataRecord data) {
        System.out.println(data.is_treasure() == null ? true : false);
        repository.save(new Encantamento_Referencia(        //Nota: aqui as incompatibilidades não são salvas. Elas devem se salvas depois.
            data.string_id(),
            data.display_name(),
            data.description(),
            data.is_treasure() == null ? false : true, /* tá vindo como 'null' em vez de false qd a checkbox fica desmarcada */
            data.max_level(),
            data.item_multiplier(),
            data.book_multiplier(),
            data.enchantment_weight(),
            data.primary_items(),
            data.secondary_items()
            )
        );

        return "home/registroEncantamentos";
    }
    // display_name, identifier, max_level, item_multiplier, book_multiplier, enchantment_weight,[L] primaryItems,[L] secondaryItems,[L] incompatibilities, is_treasure, description
    // String string_id, String display_name, String description, Boolean is_treasure, Integer max_level, Integer item_multiplier, Integer book_multiplier, Integer enchantment_weight, Set<Item_Referencia> primary_items, Set<Item_Referencia> secondary_items
    public void fillEnchantmentTable() {   /* Preenche a tabela "encantamento" com todos os encantamentos do minecraft (｡◕‿‿◕｡) */
        try {
            File encantamentos = new File("src\\main\\java\\com\\minecrafteiros\\EnchantUtils\\model\\enchantments_full.txt");
            Scanner aux = new Scanner(encantamentos);
            String[] data;
            while (aux.hasNextLine()) {
                data = aux.nextLine().split("|");
                repository.save(new Encantamento_Referencia(    //Nota: o encantamento_referencia não recebe as incompatibilidades; elas devem ser cadastradas depois.
                        data[1],    // Display Name
                        data[0],    // String Identifier
                        data[10],   // Descrição
                        Boolean.parseBoolean(data[9]),  // Is Treasure?
                        Integer.parseInt(data[2]),      // Max Level
                        Integer.parseInt(data[3]),      // Item Multiplier
                        Integer.parseInt(data[4]),      // Book Multiplier
                        Integer.parseInt(data[5]),      // Enchantment Weight
                        getItemsFromString(data[6]),     // Primary Items (Set) //Nota: preciso saber se essa busca retorna nulo ou set vazio
                        getItemsFromString(data[7])      // Secondary Items (Set)
                    ));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ocorreu um erro na função 'fillTable'!");
            e.printStackTrace();
        }
    }

    private Set<Item_Referencia> getItemsFromString(String input) {
        // Remove os colchetes e divide a string em uma lista
        String[] items = input.substring(1, input.length() - 1).split(",\\s*");
        Set<Item_Referencia> itemSet = new HashSet<>();
        try {
            for (String item : items) {
                itemSet.add(itemRefRepository.findByStringId(item));
            }
        } catch (NullPointerException e) {
            System.out.println("Erro na função 'getItemsFromString'; cheque se a string está bem-formada e se o item requisitado já foi cadastrado.");
            e.printStackTrace();
        }
        return itemSet;
    }
}
