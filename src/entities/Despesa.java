package entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Despesa {
    private LocalDate data;
    private String descDespesa;
    private Double valor;

    public Despesa(LocalDate data, String descDespesa, Double valor) {
        this.data = data;
        this.descDespesa = descDespesa;
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescDespesa() {
        return descDespesa;
    }

    public void setDescDespesa(String descDespesa) {
        this.descDespesa = descDespesa;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Data: " + data.format(dtf) + " | Despesa: " + descDespesa + String.format(" | Valor: R$%.2f\n", valor);
    }
}
