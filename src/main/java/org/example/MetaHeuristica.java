package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MetaHeuristica {

    // Definição do problema da mochila
    private static final int[] pesos = {23, 31, 29, 44, 53, 38, 63, 85, 89, 82};
    private static final int[] valores = {92, 57, 49, 68, 60, 43, 67, 84, 87, 72};
    private static final int capacidadeMochila = 165;
    private static final int tamanhoPopulacao = 100;
    private static final double taxaMutacao = 0.1;
    private static final int numGeracoes = 100;

    // Classe que representa um indivíduo da população
    private static class Individuo implements Comparable<Individuo> {
        private int[] cromossomo;
        private int fitness;

        public Individuo(int[] cromossomo) {
            this.cromossomo = cromossomo;
            this.fitness = calcularFitness();
        }

        public int[] getCromossomo() {
            return cromossomo;
        }

        public int getFitness() {
            return fitness;
        }

        // Função de fitness
        private int calcularFitness() {
            int pesoTotal = 0;
            int valorTotal = 0;
            for (int i = 0; i < cromossomo.length; i++) {
                if (cromossomo[i] == 1) {
                    pesoTotal += pesos[i];
                    valorTotal += valores[i];
                }
            }
            if (pesoTotal > capacidadeMochila) {
                return 0;
            } else {
                return valorTotal;
            }
        }

        // Implementação da interface Comparable para ordenação da população
        @Override
        public int compareTo(Individuo outro) {
            return Integer.compare(outro.getFitness(), this.fitness);
        }
    }

    // Algoritmo genético
    private static int[] algoritmoGenetico() {
        // Inicialização da população
        List<Individuo> populacao = new ArrayList<>();
        for (int i = 0; i < tamanhoPopulacao; i++) {
            int[] cromossomo = new int[pesos.length];
            for (int j = 0; j < cromossomo.length; j++) {
                cromossomo[j] = new Random().nextInt(2);
            }
            populacao.add(new Individuo(cromossomo));
        }

        // Execução das gerações
        for (int geracao = 0; geracao < numGeracoes; geracao++) {
            // Ordenação da população por fitness
            Collections.sort(populacao);
            // Elitismo: seleção dos melhores indivíduos
            List<Individuo> novaPopulacao = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                novaPopulacao.add(populacao.get(i));
            }
            // Cruzamento e mutação
            while (novaPopulacao.size() < tamanhoPopulacao) {
                Individuo pai1 = populacao.get(new Random().nextInt(tamanhoPopulacao));
                Individuo pai2 = populacao.get(new Random().nextInt(tamanhoPopulacao));
                int pontoCorte = new Random().nextInt(pesos.length);
                int[] filho = new int[pesos.length];
                for (int i = 0; i < pontoCorte; i++) {
                    filho[i] = pai1.getCromossomo()[i];
                }
                for (int i = pontoCorte; i < filho.length; i++) {
                    filho[i] = pai2.getCromossomo()[i];
                }
                for (int i = 0; i < filho.length; i++) {
                    if (new Random().nextDouble() < taxaMutacao) {
                        filho[i] = 1 - filho[i];
                    }
                }
                novaPopulacao.add(new Individuo(filho));
            }
            populacao = novaPopulacao;
        }

        // Retorno da melhor solução encontrada
        Individuo melhorSolucao = Collections.max(populacao);
        return melhorSolucao.getCromossomo();
    }

    public static void main(String[] args) {
        int[] melhorSolucao = algoritmoGenetico();
        System.out.print("Melhor solução encontrada: ");
        for (int i = 0; i < melhorSolucao.length; i++) {
            System.out.print(melhorSolucao[i] + " ");
        }
        System.out.println();
        System.out.println("Melhor fitness: " + new Individuo(melhorSolucao).getFitness());
    }

}