package org.example;

import java.util.Arrays;
import java.util.Random;

public class MetaHeuristica {

    private static final int TAMANHO_POPULACAO = 50;
    private static final double TAXA_MUTACAO = 0.2;
    private static final int NUMERO_GERACOES = 1000;

    private static final int capacidadeMochila = 50;
    private static final int[] pesos = {10, 20, 30};
    private static final int[] valores = {60, 100, 120};
    private static final int numeroItens = pesos.length;

    private static Random random = new Random();

    private static class Individuo {
        int[] cromossomo;
        int fitness;

        Individuo() {
            this.cromossomo = new int[numeroItens];
            for (int i = 0; i < numeroItens; i++) {
                this.cromossomo[i] = random.nextInt(2); // 0 ou 1
            }
            calcularFitness();
        }

        void calcularFitness() {
            int pesoTotal = 0;
            int valorTotal = 0;
            for (int i = 0; i < numeroItens; i++) {
                if (cromossomo[i] == 1) {
                    pesoTotal += pesos[i];
                    valorTotal += valores[i];
                }
            }

            if (pesoTotal <= capacidadeMochila) {
                this.fitness = valorTotal;
            } else {
                this.fitness = 0; // Penalizar soluções inválidas
            }
        }
    }

    public static void main(String[] args) {
        Individuo[] populacao = new Individuo[TAMANHO_POPULACAO];

        for (int i = 0; i < TAMANHO_POPULACAO; i++) {
            populacao[i] = new Individuo();
        }

        for (int geracao = 0; geracao < NUMERO_GERACOES; geracao++) {
            Arrays.sort(populacao, (a, b) -> Integer.compare(b.fitness, a.fitness));

            // Seleção dos melhores indivíduos
            Individuo[] novaPopulacao = Arrays.copyOfRange(populacao, 0, TAMANHO_POPULACAO / 2);

            // Cruzamento (crossover)
            for (int i = 0; i < TAMANHO_POPULACAO / 2; i += 2) {
                int pontoCorte = random.nextInt(numeroItens);
                System.arraycopy(populacao[i].cromossomo, 0, novaPopulacao[i + TAMANHO_POPULACAO / 2].cromossomo, 0, pontoCorte);
                System.arraycopy(populacao[i + 1].cromossomo, pontoCorte, novaPopulacao[i + TAMANHO_POPULACAO / 2].cromossomo, pontoCorte, numeroItens - pontoCorte);

                System.arraycopy(populacao[i + 1].cromossomo, 0, novaPopulacao[i + 1 + TAMANHO_POPULACAO / 2].cromossomo, 0, pontoCorte);
                System.arraycopy(populacao[i].cromossomo, pontoCorte, novaPopulacao[i + 1 + TAMANHO_POPULACAO / 2].cromossomo, pontoCorte, numeroItens - pontoCorte);
            }

            // Mutação
            for (Individuo individuo : novaPopulacao) {
                if (random.nextDouble() < TAXA_MUTACAO) {
                    int posicaoMutacao = random.nextInt(numeroItens);
                    individuo.cromossomo[posicaoMutacao] = 1 - individuo.cromossomo[posicaoMutacao]; // Inverte o bit
                }
            }

            populacao = novaPopulacao;

            // Avaliar a população
            for (Individuo individuo : populacao) {
                individuo.calcularFitness();
            }

            System.out.println("Geração " + geracao + ": Melhor valor encontrado = " + populacao[0].fitness);
        }

        System.out.println("\nMelhor solução encontrada:");
        System.out.println("Cromossomo: " + Arrays.toString(populacao[0].cromossomo));
        System.out.println("Valor: " + populacao[0].fitness);
    }


}
