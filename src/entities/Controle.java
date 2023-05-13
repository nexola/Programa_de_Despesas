package entities;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        itemDespesa = JOptionPane.showInputDialog("Descrição");
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

        JOptionPane.showMessageDialog(null, "Despesa criada com sucesso!");

    }

    public static void main(String[] args) {

        DespesaPessoal despesas = new DespesaPessoal(JOptionPane.showInputDialog("CPF"));

        Controle controleDespesas = new Controle();

        boolean programa = true;

        while (programa) {
            int opcao = Integer.parseInt(JOptionPane.showInputDialog(
                    """
                            1 - Lançamento de despesa
                            2 - Total geral de despesas
                            3 - Total de despesas mês/ano
                            4 - Total de despesas dia/mês/ano
                            5 - Imprimir todas as despesas
                            6 - Imprimir despesas de um mês/ano
                            7 - Imprimir despesas de um dia/mês/ano
                            8 - Sair"""));

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
                    String listaDespesas = "";
                    for (Despesa despesa : despesas.getDespesas()) {
                        listaDespesas = listaDespesas.concat(despesa.toString());
                    }
                    JOptionPane.showMessageDialog(null, listaDespesas);
                    break;
                case (6):
                    controleDespesas.entradaMes();
                    controleDespesas.entradaAno();
                    List<Despesa> newDespesas = despesas.getDespesas().stream().filter(x -> x.getData().getMonthValue() == controleDespesas.mes && x.getData().getYear() == controleDespesas.ano).toList();
                    listaDespesas = "";
                    for (Despesa despesa : newDespesas) {
                        listaDespesas = listaDespesas.concat(despesa.toString());
                    }
                    JOptionPane.showMessageDialog(null, listaDespesas);
                    break;
                case (7):
                    controleDespesas.entradaDia();
                    controleDespesas.entradaMes();
                    controleDespesas.entradaAno();
                    dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    data = LocalDate.parse(String.format("%02d", controleDespesas.dia) + "/" + String.format("%02d", controleDespesas.mes) + "/" + controleDespesas.ano, dtf);
                    newDespesas = despesas.getDespesas().stream().filter(x -> x.getData().isEqual(data)).toList();
                    listaDespesas = "";
                    for (Despesa despesa : newDespesas) {
                        listaDespesas = listaDespesas.concat(despesa.toString());
                    }
                    JOptionPane.showMessageDialog(null, listaDespesas);
                    break;
                case (8):
                    programa = false;
            }
        }
    }
}
