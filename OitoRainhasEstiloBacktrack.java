public class OitoRainhasEstiloBacktrack {

    static final int N = 8; // Número de rainhas/tabuleiro (8x8)
    static int totalSolucoes = 0; // Contador de soluções válidas encontradas

    public static void main(String[] args) {
        int[] a = new int[N + 1]; // Vetor onde a[i] representa a coluna da rainha na linha i
        backtrack(a, 0); // Começa o processo de backtracking a partir do nível 0
        System.out.println("\nTotal de soluções encontradas: " + totalSolucoes);
    }

    /**
     * Função principal de backtracking.
     * Tenta colocar rainhas linha por linha, verificando candidatos possíveis.
     */
    static void backtrack(int[] a, int k) {
        int[] c = new int[N]; // Vetor de candidatos para a próxima posição
        int ncandidatos;

        if (eUmaSolucao(k)) {
            processarSolucao(a, k); // Se é uma solução completa, imprime
        } else {
            k = k + 1; // Avança para o próximo nível (linha)
            ncandidatos = construirCandidatos(a, k, c); // Gera candidatos possíveis para a linha k
            for (int i = 0; i < ncandidatos; i++) {
                a[k] = c[i]; // Escolhe o candidato c[i] como a coluna da rainha na linha k
                backtrack(a, k); // Chamada recursiva para a próxima linha
            }
        }
    }

    /**
     * Verifica se a configuração atual (nível k) é uma solução completa.
     */
    static boolean eUmaSolucao(int k) {
        return k == N; // Se já colocamos N rainhas, temos uma solução
    }

    /**
     * Quando uma solução é encontrada, ela é processada (impressa).
     */
    static void processarSolucao(int[] a, int k) {
        totalSolucoes++;
        System.out.println("Solução #" + totalSolucoes + ":");
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j < N; j++) {
                if (a[i] == j) {
                    System.out.print(" Q "); // Rainha nesta posição
                } else {
                    System.out.print(" . "); // Espaço vazio
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Gera os candidatos válidos para a linha k, levando em conta:
     * - Colunas ainda não ocupadas
     * - Nenhum conflito pela diagonal**/
    static int construirCandidatos(int[] a, int k, int[] c) {
        boolean[] usado = new boolean[N]; // Marca colunas já utilizadas
        for (int i = 1; i < k; i++) {
            usado[a[i]] = true; // Marca coluna ocupada por uma rainha
        }

        int ncandidatos = 0;
        for (int i = 0; i < N; i++) {
            if (!usado[i] && lugarSeguro(a, k, i)) {
                c[ncandidatos++] = i; // Adiciona coluna i como candidata
            }
        }
        return ncandidatos;
    }
    /**
     * Verifica se a posição (linha k, coluna col) é segura:
     * - Não está em conflito com outras rainhas nas diagonais  */
    
    static boolean lugarSeguro(int[] a, int k, int col) {
        for (int i = 1; i < k; i++) {
            // Verifica se está na mesma diagonal de alguma rainha anterior
            if (Math.abs(i - k) == Math.abs(a[i] - col)) {
                return false; // Conflito na diagonal
            }
        }
        return true;
    }
}
