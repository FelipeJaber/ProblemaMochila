package org.example;

public class ProgDinamica {
    public static int resolverMochila(int capacidadeMochila, int pesos[], int valores[], int n) {
        int[][] tabela = new int[n + 1][capacidadeMochila + 1];

        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= capacidadeMochila; w++) {

                if (i == 0 || w == 0) {

                    tabela[i][w] = 0;

                } else if (pesos[i - 1] <= w) {

                    int incluirItem = valores[i - 1] + tabela[i - 1][w - pesos[i - 1]];
                    int naoIncluirItem = tabela[i - 1][w];
                    if (incluirItem > naoIncluirItem) {
                        tabela[i][w] = incluirItem;
                    } else {
                        tabela[i][w] = naoIncluirItem;
                    }
                } else {
                    tabela[i][w] = tabela[i - 1][w];
                }
            }
        }

        return tabela[n][capacidadeMochila];
    }

    public static void main(String[] args) {
        int capacidadeMochila = 50;
        int valores[] = {13, 29, 35 ,10 ,1, 32};
        int pesos[] = {10, 20, 30 , 23, 1, 34};
        int n = valores.length;

        int resultado = resolverMochila(capacidadeMochila, pesos, valores, n);
        System.out.println("O valor máximo que pode ser colocado na mochila é: " + resultado);
    }
}