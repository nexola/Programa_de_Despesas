package entities;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DespesaPessoal {
    private String cpf;

    List<Despesa> despesas = new ArrayList<>();

    public DespesaPessoal(String cpf) {
        this.cpf = cpf;

        try (BufferedReader br = new BufferedReader(new FileReader("src\\archives\\doc.txt"))) {

            String line = br.readLine();

            while (line != null) {
                String[] list = line.split(",");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                despesas.add(new Despesa(LocalDate.parse(list[0], dtf), list[1], Double.parseDouble(list[2])));
                line = br.readLine();
            }

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Despesa> getDespesas() {
        return despesas;
    }

    public Double getTotal() {
        return this.despesas.stream()
                .map(Despesa::getValor)
                .reduce(0.0, Double::sum);
    }

    public Double getTotal(Integer mes, Integer ano) {
        return this.despesas.stream()
                .filter(x -> x.getData().getMonthValue() == mes && x.getData().getYear() == ano)
                .map(Despesa::getValor)
                .reduce(0.0, Double::sum);
    }

    public Double getTotal(LocalDate data) {
        return this.despesas.stream()
                .filter(x -> x.getData().isEqual(data))
                .map(Despesa::getValor)
                .reduce(0.0, Double::sum);
    }

    public void imprime() {
        String listaDespesas = "";
        for (int i = 0; i < despesas.size(); i++) {
            listaDespesas = listaDespesas.concat(i+1 + " - " + despesas.get(i).toString());
        }
        JOptionPane.showMessageDialog(null, listaDespesas);
    }

    public void imprime(Integer mes, Integer ano) {
        List<Despesa> newDespesas = despesas.stream().filter(x -> x.getData().getMonthValue() == mes && x.getData().getYear() == ano).toList();
        String listaDespesas = "";
        for (int i = 0; i < newDespesas.size(); i++) {
            listaDespesas = listaDespesas.concat(i+1 + " - " + newDespesas.get(i).toString());
        }
        JOptionPane.showMessageDialog(null, listaDespesas);
    }

    public void imprime(LocalDate data) {
        List<Despesa> newDespesas = despesas.stream().filter(x -> x.getData().isEqual(data)).toList();
        String listaDespesas = "";
        for (int i = 0; i < newDespesas.size(); i++) {
            listaDespesas = listaDespesas.concat(i+1 + " - " + newDespesas.get(i).toString());
        }
        JOptionPane.showMessageDialog(null, listaDespesas);
    }

    @Override
    public String toString() {
        return "CPF: " + cpf + despesas.toString();
    }
}
