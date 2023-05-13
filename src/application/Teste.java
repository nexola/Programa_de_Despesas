package application;

import entities.Despesa;
import entities.DespesaPessoal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Teste {
    public static void main(String[] args) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Despesa desp1 = new Despesa(LocalDate.parse("19/04/2002", dtf), "Conta de água", 135.0);
        Despesa desp2 = new Despesa(LocalDate.parse("25/04/2002", dtf), "Conta de luz", 200.0);
        Despesa desp3 = new Despesa(LocalDate.parse("22/07/2005", dtf), "Financiamento", 2500.0);

        DespesaPessoal despesas = new DespesaPessoal("45562543385");

        despesas.getDespesas().add(desp1);
        despesas.getDespesas().add(desp2);
        despesas.getDespesas().add(desp3);

        System.out.printf("Total geral -> R$%.2f\n", despesas.getTotal());

        System.out.printf("Total de 04/2002 -> R$%.2f\n", despesas.getTotal(4, 2002));

        System.out.printf("Total de 22/07/2005 -> R$%.2f\n\n", despesas.getTotal(LocalDate.parse("22/07/2005", dtf)));

        despesas.imprime(); // Imprime todas as despesas

        System.out.println();

        despesas.imprime(4, 2002); // Imprime as despesas do mês e ano inseridos

        System.out.println();

        despesas.imprime(LocalDate.parse("22/07/2005", dtf)); // Imprime as despesas da data inserida
    }
}
