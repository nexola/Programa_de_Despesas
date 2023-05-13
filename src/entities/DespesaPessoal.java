package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DespesaPessoal {
    private String cpf;

    List<Despesa> despesas = new ArrayList<>();

    public DespesaPessoal(String cpf) {
        this.cpf = cpf;
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
        despesas.forEach(System.out::print);
    }

    public void imprime(Integer mes, Integer ano) {
        despesas.stream().filter(x -> x.getData().getMonthValue() == mes && x.getData().getYear() == ano).forEach(System.out::print);
    }

    public void imprime(LocalDate data) {
        despesas.stream().filter(x -> x.getData().isEqual(data)).forEach(System.out::print);
    }

    @Override
    public String toString() {
        return "CPF: " + cpf + despesas.toString();
    }
}
