import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ahorcado extends JFrame {
    private String[] words = {"JAVA", "PROGRAMACION", "SWING", "POO", "NETBEANS"};
    private String selectedWord;
    private char[] guessedWord;
    private int mistakes = 0;
    private JPanel hangmanPanel;
    private JLabel wordLabel;
    private JTextField inputField;
    private JButton guessButton;

    public Ahorcado() {
        // Configuración de la ventana principal
        setTitle("Juego del Ahorcado");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Seleccionar una palabra al azar
        selectedWord = words[(int) (Math.random() * words.length)];
        guessedWord = new char[selectedWord.length()];
        for (int i = 0; i < guessedWord.length; i++) {
            guessedWord[i] = '_';
        }

        // Panel superior: Mostrar la palabra oculta
        JPanel wordPanel = new JPanel();
        wordLabel = new JLabel(String.valueOf(guessedWord));
        wordLabel.setFont(new Font("Arial", Font.BOLD, 24));
        wordPanel.add(wordLabel);
        add(wordPanel, BorderLayout.NORTH);

        // Panel central: Dibujo del ahorcado
        hangmanPanel = new HangmanPanel();
        add(hangmanPanel, BorderLayout.CENTER);

        // Panel inferior: Entrada y botón de adivinar
        JPanel inputPanel = new JPanel();
        inputField = new JTextField(5);
        inputField.setFont(new Font("Arial", Font.PLAIN, 20));
        guessButton = new JButton("Adivinar");
        guessButton.setFont(new Font("Arial", Font.BOLD, 20));
        inputPanel.add(new JLabel("Letra: "));
        inputPanel.add(inputField);
        inputPanel.add(guessButton);
        add(inputPanel, BorderLayout.SOUTH);

        // Agregar acción al botón de adivinar
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guessLetter();
            }
        });
    }

    private void guessLetter() {
        String input = inputField.getText().toUpperCase();
        inputField.setText("");

        if (input.length() != 1) {
            JOptionPane.showMessageDialog(this, "Por favor, introduce una sola letra.");
            return;
        }

        char guessedLetter = input.charAt(0);
        boolean correct = false;

        // Comprobar si la letra está en la palabra
        for (int i = 0; i < selectedWord.length(); i++) {
            if (selectedWord.charAt(i) == guessedLetter && guessedWord[i] == '_') {
                guessedWord[i] = guessedLetter;
                correct = true;
            }
        }

        if (!correct) {
            mistakes++;
        }

        // Actualizar la interfaz
        wordLabel.setText(String.valueOf(guessedWord));
        hangmanPanel.repaint();

        // Comprobar si se ha ganado o perdido
        if (String.valueOf(guessedWord).equals(selectedWord)) {
            JOptionPane.showMessageDialog(this, "¡Felicidades, ganaste!");
            resetGame();
        } else if (mistakes >= 6) {
            JOptionPane.showMessageDialog(this, "¡Perdiste! La palabra era: " + selectedWord);
            resetGame();
        }
    }

    private void resetGame() {
        selectedWord = words[(int) (Math.random() * words.length)];
        guessedWord = new char[selectedWord.length()];
        for (int i = 0; i < guessedWord.length; i++) {
            guessedWord[i] = '_';
        }
        mistakes = 0;
        wordLabel.setText(String.valueOf(guessedWord));
        hangmanPanel.repaint();
    }

    // Panel personalizado para el dibujo del ahorcado
    private class HangmanPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);

            // Base de la horca
            g.drawLine(50, 400, 200, 400);
            g.drawLine(125, 400, 125, 100);
            g.drawLine(125, 100, 250, 100);
            g.drawLine(250, 100, 250, 150);

            // Dibujo del ahorcado según los errores
            if (mistakes >= 1) {
                g.drawOval(225, 150, 50, 50); // Cabeza
            }
            if (mistakes >= 2) {
                g.drawLine(250, 200, 250, 300); // Cuerpo
            }
            if (mistakes >= 3) {
                g.drawLine(250, 220, 200, 250); // Brazo izquierdo
            }
            if (mistakes >= 4) {
                g.drawLine(250, 220, 300, 250); // Brazo derecho
            }
            if (mistakes >= 5) {
                g.drawLine(250, 300, 200, 350); // Pierna izquierda
            }
            if (mistakes >= 6) {
                g.drawLine(250, 300, 300, 350); // Pierna derecha
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Ahorcado game = new Ahorcado();
            game.setVisible(true);
        });
    }
}

