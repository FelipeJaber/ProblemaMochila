package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Item {
    double peso;
    double valor;

    public Item(double peso, double valor) {
        this.peso = peso;
        this.valor = valor;
    }
}

public class Guloso {
    public static List<Item> mochilaFracionaria(List<Item> itens, double capacidade) {
        // Ordena os itens por valor por peso em ordem decrescente
        Collections.sort(itens, new Comparator<Item>() {
            public int compare(Item item1, Item item2) {
                double ratio1 = item1.valor / item1.peso;
                double ratio2 = item2.valor / item2.peso;
                return Double.compare(ratio2, ratio1);
            }
        });

        List<Item> mochila = new ArrayList<>();
        double pesoMochila = 0.0;

        for (Item item : itens) {
            if (pesoMochila + item.peso <= capacidade) {
                mochila.add(item);
                pesoMochila += item.peso;
            } else {
                double espacoRestante = capacidade - pesoMochila;
                double fracao = espacoRestante / item.peso;
                mochila.add(new Item(item.peso * fracao, item.valor * fracao));
                break;
            }
        }

        return mochila;
    }

    public static void main(String[] args) {
        List<Item> itens = new ArrayList<>();
        itens.add(new Item(40, 200));
        itens.add(new Item(20, 20));
        itens.add(new Item(11, 110));
        itens.add(new Item(50.234, 150));
        itens.add(new Item(15.1209, 40));
        itens.add(new Item(9.40, 70));
        itens.add(new Item(10.2, 5));
        itens.add(new Item(1.8, 5));

        double capacidadeMochila = 50;

        List<Item> mochilaResultante = mochilaFracionaria(itens, capacidadeMochila);

        float valortotal = 0;
        float pesototal = 0;
        System.out.println("Itens selecionados na mochila fracionária:");
        for (Item item : mochilaResultante) {
            System.out.printf("Peso: %.2f; Valor: %.2f%n", item.peso, item.valor);
            valortotal += (float) item.valor;
            pesototal += (float) item.peso;
        }
        System.out.println("O peso ocupado na mochila foi de: " + pesototal);
        System.out.println("O valor máximo acumulado na mochila foi de: "+valortotal);
    }
}
