import javax.swing.*;          // Componentes Swing
import java.awt.*;             // Layouts e componentes gráficos
import java.awt.event.*;       // Eventos
import java.util.*;            // List, ArrayList, Collections
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OitoRainhasProblema extends JFrame {

    static final int TAMANHO = 8;

    private JPanel tabuleiroPanel;
    private JLabel statusLabel;
    private List<int[][]> solucoes;
    private int indiceAtual = 0;

    public OitoRainhasProblema() {
        setTitle("Problema das 8 Rainhas");
        setSize(500, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Label de status (feedback visual)
        statusLabel = new JLabel("Carregando...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        add(statusLabel, BorderLayout.NORTH);

        // Painel do tabuleiro
        tabuleiroPanel = new JPanel(new GridLayout(TAMANHO, TAMANHO));
        add(tabuleiroPanel, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel();
        JButton botaoNova = new JButton("Nova Solução");
        JButton botaoAnterior = new JButton("Anterior");
        JButton botaoProxima = new JButton("Próxima");
        JButton botaoReset = new JButton("Resetar");

        painelBotoes.add(botaoAnterior);
        painelBotoes.add(botaoProxima);
        painelBotoes.add(botaoNova);
        painelBotoes.add(botaoReset);
        add(painelBotoes, BorderLayout.SOUTH);

        // Ações dos botões
        botaoNova.addActionListener(e -> mostrarSolucaoAleatoria());
        botaoAnterior.addActionListener(e -> mostrarSolucao(indiceAtual - 1));
        botaoProxima.addActionListener(e -> mostrarSolucao(indiceAtual + 1));
        botaoReset.addActionListener(e -> {
            statusLabel.setText("Resetando...");
            carregarSolucoes(); // Gera novas soluções
        });

        carregarSolucoes(); // Gera soluções ao iniciar
        setVisible(true);
    }

    // Gera todas as soluções possíveis (máx 92) usando backtracking
    private void carregarSolucoes() {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText("Carregando soluções...");
            tabuleiroPanel.removeAll();
            tabuleiroPanel.revalidate();
            tabuleiroPanel.repaint();

            solucoes = new ArrayList<>();
            resolverTodas(new int[TAMANHO][TAMANHO], 0);

            if (!solucoes.isEmpty()) {
                indiceAtual = 0;
                mostrarSolucao(indiceAtual);
                statusLabel.setText("Soluções carregadas. Total: " + solucoes.size());
            } else {
                statusLabel.setText("Nenhuma solução encontrada.");
            }
        });
    }

    // Mostra uma solução aleatória da lista
    private void mostrarSolucaoAleatoria() {
        if (solucoes == null || solucoes.isEmpty()) return;
        indiceAtual = new Random().nextInt(solucoes.size());
        mostrarSolucao(indiceAtual);
    }

    // Mostra a solução pelo índice
    private void mostrarSolucao(int indice) {
        if (solucoes == null || solucoes.isEmpty()) return;

        if (indice >= 0 && indice < solucoes.size()) {
            indiceAtual = indice;
            desenharTabuleiro(solucoes.get(indiceAtual));
            statusLabel.setText("Solução " + (indiceAtual + 1) + " de " + solucoes.size());
        }
    }

    // Desenha o tabuleiro
    private void desenharTabuleiro(int[][] tabuleiro) {
        tabuleiroPanel.removeAll();

        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                JLabel celula = new JLabel(tabuleiro[i][j] == 1 ? "♛" : "", SwingConstants.CENTER);
                celula.setOpaque(true);
                celula.setFont(new Font("SansSerif", Font.BOLD, 32));
                celula.setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.DARK_GRAY);
                celula.setForeground(Color.RED);
                tabuleiroPanel.add(celula);
            }
        }

        tabuleiroPanel.revalidate();
        tabuleiroPanel.repaint();
    }

    // Gera todas as soluções e salva na lista
    private void resolverTodas(int[][] tabuleiro, int linha) {
        if (linha == TAMANHO) {
            int[][] copia = new int[TAMANHO][TAMANHO];
            for (int i = 0; i < TAMANHO; i++) {
                copia[i] = tabuleiro[i].clone();
            }
            solucoes.add(copia);
            return;
        }

        for (int coluna = 0; coluna < TAMANHO; coluna++) {
            if (posicaoSegura(tabuleiro, linha, coluna)) {
                tabuleiro[linha][coluna] = 1;
                resolverTodas(tabuleiro, linha + 1);
                tabuleiro[linha][coluna] = 0;
            }
        }
    }

    // Verifica se é seguro colocar a rainha na posição (linha, coluna)
    public static boolean posicaoSegura(int[][] tabuleiro, int linha, int coluna) {
        for (int i = 0; i < linha; i++) {
            if (tabuleiro[i][coluna] == 1) return false;
        }

        for (int i = linha - 1, j = coluna - 1; i >= 0 && j >= 0; i--, j--) {
            if (tabuleiro[i][j] == 1) return false;
        }

        for (int i = linha - 1, j = coluna + 1; i >= 0 && j < TAMANHO; i--, j++) {
            if (tabuleiro[i][j] == 1) return false;
        }

        return true;
    }

    // Método principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OitoRainhasProblema());
    }
}

