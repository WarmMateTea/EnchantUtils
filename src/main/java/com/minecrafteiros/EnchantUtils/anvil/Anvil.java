package com.minecrafteiros.EnchantUtils.anvil;

import java.util.*;

@SuppressWarnings("SpellCheckingInspection")
public class Anvil {
    Integer Cost = 0;

    // Item é uma instância de item no BD; pode possuir encantamentos
    // Encantamento é uma instância do encantamento referência que tá na memória at all times;

    // Calcula o custo de encantar um item Target com múltiplos encantamentos; assume-se automaticamente que os encantamentos estão disponíveis no nível desejado em LIVROS.
    // Recebe o resultado desejado e desmantela a lista de encantamentos em livros (Itens) para calcular o custo total
    public AnvilResponse Request(Item Target, ArrayList<Enchantment> DesiredEnchants) {
        AnvilResponse AR = new AnvilResponse();
        ArrayList<Item> EnchantmentBooks = new ArrayList<>();

        // Desmantela a lista de encantamentos em livros (Itens)
        for (Enchantment e : DesiredEnchants ) {
            Item tempBook = new Item(
                    e.getEnchantmentName(),
                    "BOOK"
            );
            tempBook.addStoredEnchantment(new Enchantment(e));
            EnchantmentBooks.add(tempBook);
        }

        //Copiar item a ser encantado, livros, e ordenar lista de livros
        EnchantmentBooks.sort(Comparator.comparing(Item::getValue));
        Integer xpCostWrapper = 0;

        // Algoritmo 1: Agrupamento binário, maior custo sacrificado menos
        Item Result = BinaryJoin(Target, EnchantmentBooks, xpCostWrapper, AR);

        AR.setCost(xpCostWrapper);
        AR.setResult(Result);

        return AR;

        // não vou pensar nesse por agora :)
        // Algoritmo 2: Não pagar 2x em encantamentos (juntar livros com 2 encantamentos direto ao item)
    }

    // Espera receber o item principal da coisa toda, mais os livros com encantamentos.
    public Item BinaryJoin(Item TargetItem, ArrayList<Item> EnchantmentBooks, Integer XPCW, AnvilResponse AR) {
        // No corpo dessa função em si, não ocorre nenhuma junção. O que realmente ocorre é uma organização da estrutura de dados.
        // A manipulação de valores só ocorre com as iterações de InnerBinaryJoin, daí a necessidade do XpCostWrapper.
        // MONTAR AS FOLHAS DA ÁRVORE
        Stack<Item> HighPrice = new Stack<>();
        Stack<Item> LowPrice = new Stack<>();

        ArrayList<Item> CurrentTreeLevel = new ArrayList<Item>();
        AR.getTreeLevels().add(CurrentTreeLevel);

        // TargetItem é diretamente colocado num slot de alto valor
        HighPrice.push(TargetItem); CurrentTreeLevel.add(TargetItem);
        EnchantmentBooks.sort(Comparator.comparing(Item::getValue));

        Boolean highSlot = false;   //<- Garante que HighPrice.size == LowPrice.size || LowPrice.size+1
        int bookListSize = EnchantmentBooks.size();
        for (int i = 0; i < bookListSize; i++) {

            Integer headOrTail = (highSlot) ? EnchantmentBooks.size()-1 : 0 ;

            (highSlot ? HighPrice : LowPrice)
                    .push(EnchantmentBooks.remove(headOrTail.intValue()));
            CurrentTreeLevel.add( (highSlot) ? HighPrice.peek() : LowPrice.peek() );    // ::::::DDDDDDD


            highSlot = !highSlot;
        }

        return InnerBinaryJoin(HighPrice, LowPrice, XPCW, AR);
    }

    private Item InnerBinaryJoin(Stack<Item> highPrice, Stack<Item> lowPrice, Integer _XPCW, AnvilResponse AR) {
        Stack<Item> _HighPrice = new Stack<>();
        Stack<Item> _LowPrice = new Stack<>();

        ArrayList<Item> CurrentTreeLevel = new ArrayList<Item>();
        AR.getTreeLevels().add(CurrentTreeLevel);

        // Tecnicamente, essas inversões são desnecessárias, mas eu resolvi fazer de qqr modo só pra ficar igual ao algoritmo na minha cuca
        highPrice.sort(
                Collections.reverseOrder(
                        Comparator.comparing(Item::getValue)));
        lowPrice.sort(
                Collections.reverseOrder(
                        Comparator.comparing(Item::getValue)));

        System.out.println("InnerBinaryJoin =================================================");
        System.out.println("HSLT: "+ (highPrice.isEmpty() ? "hpempty" : highPrice.peek()));
        System.out.println("HS size: "+ highPrice.size());
        System.out.println("LSLT: "+ (lowPrice.isEmpty() ? "lpempty" : lowPrice.peek()));
        System.out.println("LS size:"+ lowPrice.size());
        System.out.println("=================================================================");

        Boolean _highSlot = true;
        int highItemCount = highPrice.size();
        for (int i = 0; i < highItemCount; i++) {
            ((_highSlot) ? _HighPrice : _LowPrice)  .push(  Combine(highPrice.pop(), (lowPrice.isEmpty()) ? null: lowPrice.pop(), _XPCW)  );    // <- só aqui ocorre a operação de valor :D
            CurrentTreeLevel.add( (_highSlot) ? _HighPrice.peek() : _LowPrice.peek() );
            _highSlot = !_highSlot;
        }

        // Se highPrice.size for 1 e lowPrice estiver vazia, o item restante é o resultado da operação. Então, ele é retornado.
        return (_HighPrice.size() == 1 && _LowPrice.isEmpty()) ? _HighPrice.pop() : InnerBinaryJoin(_HighPrice, _LowPrice, _XPCW, AR);
    }

    // Retorna o item final (novo), e
    // Sacrifice pode ser nulo, e é importante cuidar desse caso.
    public Item Combine(Item Target, Item Sacrifice, Integer _XPCW) {
        //Experience cost = [Value of sacrificed (right placed) item] + [Work Penalty of target (left placed) item] + [Work Penalty of sacrificed (right placed) item] + [Renaming Cost] + [Refilling Durability] + [Incompatible Enchantments (Java Edition)]

        //A série de funções de custo não são null-safe, então só devolvo o item inicial caso eu não tenha um sacrifício
        if (Sacrifice == null)
            return Target;

        Integer Cost = CombiningCost(Target, Sacrifice); // Como a função combine n liga pra como ela tá combinando, ela só combina, tá ok
        _XPCW += Cost;

        return GenerateFinalItem(Target, Sacrifice);
    }

    /* Fluxo de execução (não se impora com o custo - esse trecho serve para gerar um novo item a partir dos itens que eu dou pra ela.)
    // Essa função pode retornar NULL. Nesse caso, a operação tentada é inválida.
    * If the sacrifice item has enchantments, it also tries to combine the sacrifice item's enchantments onto the target item. Regardless of whether any enchantments on the target item are actually changed, the cost is based on the total enchantments on the target item and the sacrifice item. For each enchantment on the sacrifice item:
    *
        * Condicional 1
        * If the target item has the enchantment:
            - and the level of the enchantment on the sacrifice item is greater, the enchantment level on the target item is raised to match it.
            - and the level of the enchantment on the sacrifice item is equal AND the enchantment is not already at the maximum level, the enchantment on the target item gains one level.
            - and the level of the enchantment on the sacrifice item is less, the enchantment level on the target item is unaffected.
        *
        *
        * Condicional 2
        * If the target does not have the enchantment, it gains all levels of that enchantment, unless it already has an incompatible enchantment, resulting in the anvil refusing to combine the items and a red "X" displaying on top of the arrow in the anvil GUI. Enchantments are incompatible if both are in one of the following groups:
            > Sword: Sharpness, Smite, and Bane of Arthropods
            > Tool: Fortune and Silk Touch (as of Java version 1.12.2 you can combine these; the sacrifice item's enchantment is lost)
            > Armor: Protection, Fire Protection, Blast Protection, Projectile Protection
            > Boots: Depth Strider and Frost Walker
            > Bow: Infinity and Mending
            > Crossbow: Multishot and Piercing
            > Trident: Loyalty and Riptide or Channeling and Riptide
            > Books: Silk Touch and Looting or Silk Touch and Luck of the Sea[2] (and all of the above).
            *
    */
    private Item GenerateFinalItem(Item _Target, Item _Sacrifice) {
        Item Target = new Item(_Target);
        Item Sacrifice = new Item(_Sacrifice);

        String TargetItemType = Target.getItemType();
        String SacrificeItemType = Sacrifice.getItemType();

        if (
                TargetItemType.equalsIgnoreCase(SacrificeItemType) ||
                (!TargetItemType.equalsIgnoreCase("BOOK") && SacrificeItemType.equalsIgnoreCase("BOOK"))
        )
        {
            // Aqui a diferença entre item+item/livro e livro+livro: getEnchantments() e getStoredEnchantments(); tenho que mudar a atrib no caso de item/livro tbm
            List<Enchantment> sacrificeEnchantments = ItemEnchantmentListHandler(Sacrifice);
            for (Enchantment sacrificeUsefulEnchantment : sacrificeEnchantments) {

                // se for item + *, é o ench normal (esse); se for livro + *, é o storedEnch
                Enchantment equalEnchantmentOnTarget = ItemEnchantmentListHandler(Target).stream()
                        .filter(targEnch -> targEnch.getId().equals(sacrificeUsefulEnchantment.getId()))
                        .findAny()
                        .orElse(null);

                //Target tem um encantamento que torna a operação impossível; retorna null para operação inválida
                if (Target.hasIncompatibleEnchantmentTo(sacrificeUsefulEnchantment))
                    return null;

                // Target possui uma instância do mesmo encantamento que está sendo analisado
                if (equalEnchantmentOnTarget != null) {
                    Integer TargetEnchLvl = equalEnchantmentOnTarget.getLevel();
                    Integer SacrificeEnchLvl = sacrificeUsefulEnchantment.getLevel();

                    if (SacrificeEnchLvl > TargetEnchLvl) {
                        equalEnchantmentOnTarget.setLevel(SacrificeEnchLvl);
                    } else if (SacrificeEnchLvl == TargetEnchLvl) {
                        equalEnchantmentOnTarget.setLevel( TargetEnchLvl + 1 );
                    }
                    // Target não possui o encantamento analisado
                } else {
                    List<Enchantment> targetEnchantments = ItemEnchantmentListHandler(Target);
                    targetEnchantments.add(new Enchantment(sacrificeUsefulEnchantment));
                }
            }
            System.out.println("GenerateFinalItem------------------------------------------------");
            System.out.println("CMBN: "+Target);
            System.out.println("-----------------------------------------------------------------");
            Target.setAnvilUseCount(Target.getAnvilUseCount()+1);
            return Target;
        } else {
            return null;
        }
    }

    private List<Enchantment> ItemEnchantmentListHandler(Item item) {
        if (item.getItemType().equalsIgnoreCase("BOOK"))
            return item.getStoredEnchantments();
        else
            return item.getEnchantments();
    }

    //Função CombiningCost com o parâmetro isRenaming padrão (false).
    private Integer CombiningCost(Item Target, Item Sacrifice) {
        return CombiningCost(Target, Sacrifice, false);
    }

    //Encapsula todas as funções de custo para maior clareza.
    //NÃO muta os Item recebidos, e não possui efeitos colaterais.
    //Logo, deve ser usada ANTES de quaisquer mudanças sobre os itens originais.
    private Integer CombiningCost(Item Target, Item Sacrifice, Boolean isRenaming) {
        return EnchantingCost(Target, Sacrifice) + PriorWorkCost(Target, Sacrifice) + RepairCost() + RenameCost(isRenaming);
    }

    //(This is just the enchanting cost. The total cost outline is in Combining items.)
    //Costs for combining enchantments
    //Needs a copy of the original Target and Sacrifice (or to just recieve them beforehand)
    //Will NOT mutate the Items given to it. Should have no side effects.
    private Integer EnchantingCost(Item Target, Item Sacrifice) {
        Integer cost = 0;
        //For each enchantment on the sacrifice:
        for (Enchantment e : Sacrifice.getEnchantments()) {//variação: se sacrifício é livro, getStoredEnchantments; se é item, getEnchantments.
            //Ignore any enchantment that cannot be applied to the target (e.g. Protection on a sword).
            if (!e.canBeAppliedTo(Target))
                continue;
            //Add one level for every incompatible enchantment on the target (In Java Edition).
            if (Target.hasAnyIncompatibleEnchantmentTo(e))
                cost++;
            else {
                //If the enchantment is compatible with the existing enchantments on the target:
                Enchantment enchantInQuestion = Target.findEqualEnchantmentAs(e);
                if (enchantInQuestion != null)
                {
                    Integer FinalLevel = 0, eIQLevel = enchantInQuestion.getLevel(), eLevel = e.getLevel();
                    // For Java Edition, add the final level of the enchantment on the resulting item multiplied by the multiplier from the table below.
                    if (eIQLevel > eLevel)
                        FinalLevel = eIQLevel;
                    else if (eIQLevel < eLevel)
                        FinalLevel = eLevel;
                    else
                        FinalLevel = eLevel + 1;
                    cost = FinalLevel * e.getMultiplier(Sacrifice) ;
                    // For Bedrock Edition, add the difference between the final level and the initial level on the target item multiplied by the multiplier from the table below.
                    //bedrock magic

                }
            }
        }
        return cost;
    }

    //Needs a copy of the original Target and Sacrifice (or to just recieve them beforehand)
    //Will NOT mutate the Items given to it. Should have no side effects. Which means that while this calculates the COST of prior work, it does not actually UPDATE the prior work value of the given items.
    private Integer PriorWorkCost(Item Target, Item Sacrifice) {
        return Target.getPriorWorkPenalty() + Sacrifice.getPriorWorkPenalty();
    }

    //Essa função só tá aqui pra equação manter sua forma. Efetivamente, como nenhum item será reparado no webapp, ela não importa.
    private Integer RepairCost() {
        return 0;
    }

    //Essa função é outra que praticamente não tem utilidade, mas está aqui para manter a forma da equação original.
    private Integer RenameCost(Boolean isRenaming) { return isRenaming ? 1 : 0 ;}

    private Integer PriorUsePenalty(Integer AnvilUseCount) {
        return 2 ^ (AnvilUseCount) - 1;
    }
}
