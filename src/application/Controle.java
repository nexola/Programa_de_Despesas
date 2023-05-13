package application;

import entities.Despesa;
import entities.DespesaPessoal;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Controle {
    private Integer dia;
    private Integer mes;
    private Integer ano;
    private String itemDespesa;
    private Double valor;

    public Controle() {
    }

    public void entradaDia() {
        dia = Integer.parseInt(JOptionPane.showInputDialog("Dia [00]"));
    }

    public void entradaMes() {
        mes = Integer.parseInt(JOptionPane.showInputDialog("Mês [00]"));
    }

    public void entradaAno() {
        ano = Integer.parseInt(JOptionPane.showInputDialog("Ano [0000]"));
    }

    public void itemDespesa() {
        itemDespesa = JOptionPane.showInputDialog("Descrição da despesa");
    }

    public void valor() {
        valor = Double.parseDouble(JOptionPane.showInputDialog("Valor"));
    }

    public void entradaDespesa(DespesaPessoal despesas) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        entradaDia();
        entradaMes();
        entradaAno();
        LocalDate data = LocalDate.parse(String.format("%02d", dia) + "/" + String.format("%02d", mes) + "/" + ano, dtf);

        itemDespesa();

        valor();

        Despesa despesa = new Despesa(data, itemDespesa, valor);

        despesas.getDespesas().add(despesa);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src\\archives\\doc.txt", true))) {

            bw.append("\n").append(despesa.getData().format(dtf)).append(",").append(despesa.getDescDespesa()).append(",").append(despesa.getValor().toString());

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        JOptionPane.showMessageDialog(null, "Despesa criada com sucesso!");

    }

    public static void main(String[] args) {

        DespesaPessoal despesas = new DespesaPessoal(JOptionPane.showInputDialog("CPF"));

        Controle controleDespesas = new Controle();

        boolean programa = true;

        while (programa) {
            int opcao = Integer.parseInt(JOptionPane.showInputDialog("""
                    1 - Lançamento de despesa
                    2 - Total geral de despesas
                    3 - Total de despesas mês/ano
                    4 - Total de despesas dia/mês/ano
                    5 - Imprimir todas as despesas
                    6 - Imprimir despesas de um mês/ano
                    7 - Imprimir despesas de um dia/mês/ano
                    8 - Atualizar despesa
                    9 - Excluir despesa
                    0 - Sair"""));

            switch (opcao) {
                case (1):
                    controleDespesas.entradaDespesa(despesas);
                    break;
                case (2):
                    JOptionPane.showMessageDialog(null, despesas.getTotal());
                    break;
                case (3):
                    controleDespesas.entradaMes();
                    controleDespesas.entradaAno();
                    JOptionPane.showMessageDialog(null, despesas.getTotal(controleDespesas.mes, controleDespesas.ano).toString());
                    break;
                case (4):
                    controleDespesas.entradaDia();
                    controleDespesas.entradaMes();
                    controleDespesas.entradaAno();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate data = LocalDate.parse(String.format("%02d", controleDespesas.dia) + "/" + String.format("%02d", controleDespesas.mes) + "/" + controleDespesas.ano, dtf);
                    JOptionPane.showMessageDialog(null, despesas.getTotal(data));
                    break;
                case (5):
                    despesas.imprime();
                    break;
                case (6):
                    controleDespesas.entradaMes();
                    controleDespesas.entradaAno();
                    despesas.imprime(controleDespesas.mes, controleDespesas.ano);
                    break;
                case (7):
                    controleDespesas.entradaDia();
                    controleDespesas.entradaMes();
                    controleDespesas.entradaAno();
                    dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    data = LocalDate.parse(String.format("%02d", controleDespesas.dia) + "/" + String.format("%02d", controleDespesas.mes) + "/" + controleDespesas.ano, dtf);

                    despesas.imprime(data);
                    break;
                case (8):
                    String listaDespesas = "Insira o ID da despesa que deseja alterar\n\n";
                    for (int i = 0; i < despesas.getDespesas().size(); i++) {
                        listaDespesas = listaDespesas.concat(i + 1 + " - " + despesas.getDespesas().get(i).toString());
                    }
                    int id = Integer.parseInt(JOptionPane.showInputDialog(listaDespesas));

                    controleDespesas.entradaDia();
                    controleDespesas.entradaMes();
                    controleDespesas.entradaAno();
                    dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    data = LocalDate.parse(String.format("%02d", controleDespesas.dia) + "/" + String.format("%02d", controleDespesas.mes) + "/" + controleDespesas.ano, dtf);

                    controleDespesas.itemDespesa();

                    controleDespesas.valor();

                    despesas.getDespesas().get(id - 1).setData(data);
                    despesas.getDespesas().get(id - 1).setDescDespesa(controleDespesas.itemDespesa);
                    despesas.getDespesas().get(id - 1).setValor(controleDespesas.valor);

                    try (BufferedWriter bw = new BufferedWriter(new FileWriter("src\\archives\\doc.txt", false))) {

                        for (int i = 0; i < despesas.getDespesas().size(); i++) {

                            dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            bw.append(despesas.getDespesas().get(i).getData().format(dtf)).append(",").append(despesas.getDespesas().get(i).getDescDespesa()).append(",").append(despesas.getDespesas().get(i).getValor().toString());
                            bw.newLine();
                        }

                        JOptionPane.showMessageDialog(null, "Despesa alterada com sucesso!");

                        System.out.println(despesas.getDespesas());

                    } catch (IOException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case (9):
                    listaDespesas = "Insira o ID da despesa que deseja excluir\n\n";
                    for (int i = 0; i < despesas.getDespesas().size(); i++) {
                        listaDespesas = listaDespesas.concat(i + 1 + " - " + despesas.getDespesas().get(i).toString());
                    }
                    id = Integer.parseInt(JOptionPane.showInputDialog(listaDespesas));

                    despesas.getDespesas().remove(id - 1);

                    try (BufferedWriter bw = new BufferedWriter(new FileWriter("src\\archives\\doc.txt", false))) {

                        for (int i = 0; i < despesas.getDespesas().size(); i++) {

                            dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            bw.append(despesas.getDespesas().get(i).getData().format(dtf)).append(",").append(despesas.getDespesas().get(i).getDescDespesa()).append(",").append(despesas.getDespesas().get(i).getValor().toString());
                            bw.newLine();
                        }

                        JOptionPane.showMessageDialog(null, "Despesa excluída com sucesso!");

                    } catch (IOException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case (0):
                    programa = false;
            }
        }
    }
}
