import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class App {
    private static JFrame frame = new JFrame();

    private static JLabel encodeTitle = new JLabel("Mã hóa");
    private static JLabel decodeTitle = new JLabel("Giải mã");

    private static JButton importFileButton1 = new JButton("File");
    private static JButton importFileButton2 = new JButton("File");
    private static JFileChooser fileChooser = new JFileChooser();

    private static JButton encodeButton = new JButton("Mã hóa");
    private static JButton decodeButton = new JButton("Giải mã");

    private static JLabel decodedTextLabel1 = new JLabel("Bản rõ:");
    private static JTextArea decodedTextArea1 = new JTextArea();
    private static JLabel encodedTextLabel1 = new JLabel("Bản mã:");
    private static JTextArea encodedTextArea1 = new JTextArea();

    private static JLabel decodedTextLabel2 = new JLabel("Bản rõ:");
    private static JTextArea decodedTextArea2 = new JTextArea();
    private static JLabel encodedTextLabel2 = new JLabel("Bản mã:");
    private static JTextArea encodedTextArea2 = new JTextArea();

    private static JButton changeButton = new JButton("Chuyển");

    private static JButton saveButton1 = new JButton("Lưu");
    private static JButton saveButton2 = new JButton("Lưu");

    private static Elgamal elgamal = new Elgamal();

    public static void main(String[] args) throws Exception {
        elgamal.init();

        frame.setTitle("Elgamal Demo");
        frame.setSize(1200, 700);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        prepareEncodePanel();
        prepareDecodePanel();
    }

    public static void prepareEncodePanel() {
        encodeTitle.setFont(encodeTitle.getFont().deriveFont(20f));
        encodeTitle.setBounds(250, 10, 100, 20);

        decodedTextArea1.setBounds(60, 70, 210, 180);// x axis, y axis, width, height
        decodedTextArea1.setMargin(new Insets(10, 10, 10, 10));
        decodedTextArea1.setFont(decodedTextArea1.getFont().deriveFont(14f));
        decodedTextArea1.setLineWrap(true);

        decodedTextLabel1.setBounds(60, 40, 100, 20);
        decodedTextLabel1.setFont(decodedTextLabel1.getFont().deriveFont(16f));

        importFileButton1.setBounds(320, 90, 100, 40);// x axis, y axis, width, height
        importFileButton1.addMouseListener(new ImportFileMouseListener(decodedTextArea1));

        encodeButton.setBounds(250, 280, 100, 40);// x axis, y axis, width, height
        encodeButton.addMouseListener(new EncodeButtonMouseListener(decodedTextArea1, encodedTextArea1));

        encodedTextArea1.setBounds(60, 370, 210, 180);// x axis, y axis, width, height
        encodedTextArea1.setMargin(new Insets(10, 10, 10, 10));
        encodedTextArea1.setFont(encodedTextArea1.getFont().deriveFont(14f));
        encodedTextArea1.setLineWrap(true);
        encodedTextArea1.setEditable(false);

        encodedTextLabel1.setBounds(60, 340, 100, 20);
        encodedTextLabel1.setFont(encodedTextLabel1.getFont().deriveFont(16f));

        changeButton.setBounds(320, 400, 100, 40);// x axis, y axis, width, height
        changeButton.addMouseListener(new ChangeCodeButtonMouseListener(encodedTextArea1, encodedTextArea2));

        saveButton1.setBounds(320, 470, 100, 40);// x axis, y axis, width, height
        saveButton1.addMouseListener(new ExportFileMouseListener(encodedTextArea1));

        frame.add(encodeTitle);
        frame.add(importFileButton1);
        frame.add(encodeButton);
        frame.add(decodedTextArea1);
        frame.add(decodedTextLabel1);
        frame.add(encodedTextArea1);
        frame.add(encodedTextLabel1);
        frame.add(changeButton);
        frame.add(saveButton1);
    }

    public static void prepareDecodePanel() {
        decodeTitle.setFont(encodeTitle.getFont().deriveFont(20f));
        decodeTitle.setBounds(850, 10, 100, 20);

        encodedTextArea2.setBounds(660, 70, 210, 180);// x axis, y axis, width, height
        encodedTextArea2.setMargin(new Insets(10, 10, 10, 10));
        encodedTextArea2.setFont(encodedTextArea1.getFont().deriveFont(14f));
        encodedTextArea2.setLineWrap(true);

        encodedTextLabel2.setBounds(660, 40, 100, 20);
        encodedTextLabel2.setFont(decodedTextLabel1.getFont().deriveFont(16f));

        importFileButton2.setBounds(920, 90, 100, 40);// x axis, y axis, width, height
        importFileButton2.addMouseListener(new ImportFileMouseListener(encodedTextArea2));

        decodeButton.setBounds(850, 280, 100, 40);// x axis, y axis, width, height
        decodeButton.addMouseListener(new DecodeButtonMouseListener(encodedTextArea2, decodedTextArea2));

        decodedTextArea2.setBounds(660, 370, 210, 180);// x axis, y axis, width, height
        decodedTextArea2.setMargin(new Insets(10, 10, 10, 10));
        decodedTextArea2.setFont(decodedTextArea1.getFont().deriveFont(14f));
        decodedTextArea2.setLineWrap(true);
        decodedTextArea2.setEditable(false);

        decodedTextLabel2.setBounds(660, 340, 100, 20);
        decodedTextLabel2.setFont(decodedTextLabel1.getFont().deriveFont(16f));

        saveButton2.setBounds(920, 470, 100, 40);// x axis, y axis, width, height
        saveButton2.addMouseListener(new ExportFileMouseListener(decodedTextArea2));

        frame.add(decodeTitle);
        frame.add(importFileButton2);
        frame.add(decodeButton);
        frame.add(decodedTextArea2);
        frame.add(decodedTextLabel2);
        frame.add(encodedTextArea2);
        frame.add(encodedTextLabel2);
        frame.add(saveButton2);
    }

    private static class EncodeButtonMouseListener implements MouseListener {
        private JTextArea decodedTextArea;
        private JTextArea encodedTextArea;

        public EncodeButtonMouseListener(JTextArea decodedTextArea, JTextArea encodedTextArea) {
            this.decodedTextArea = decodedTextArea;
            this.encodedTextArea = encodedTextArea;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            String message = decodedTextArea.getText();
            String encryptedMessage = elgamal.encrypt(message, elgamal.getP(), elgamal.getG(), elgamal.getY());
            encodedTextArea.setText(encryptedMessage);
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    private static class DecodeButtonMouseListener implements MouseListener {
        private JTextArea encodedTextArea;
        private JTextArea decodedTextArea;

        public DecodeButtonMouseListener(JTextArea encodedTextArea, JTextArea decodedTextArea) {
            this.encodedTextArea = encodedTextArea;
            this.decodedTextArea = decodedTextArea;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            String message = encodedTextArea.getText();
            String decryptedMessage = elgamal.decrypt(message, elgamal.getP(), elgamal.getX());
            decodedTextArea.setText(decryptedMessage);
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    private static class ChangeCodeButtonMouseListener implements MouseListener {
        private JTextArea encodedTextArea1;
        private JTextArea encodedTextArea2;

        public ChangeCodeButtonMouseListener(JTextArea encodedTextArea1, JTextArea encodedTextArea2) {
            this.encodedTextArea1 = encodedTextArea1;
            this.encodedTextArea2 = encodedTextArea2;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            encodedTextArea2.setText(encodedTextArea1.getText());
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    private static class ImportFileMouseListener implements MouseListener {
        private JTextArea textArea;

        public ImportFileMouseListener(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    Scanner scanner = new Scanner(file);
                    StringBuilder content = new StringBuilder();

                    while (scanner.hasNextLine()) {
                        content.append(scanner.nextLine()).append("\n");
                    }
                    content.deleteCharAt(content.length() - 1);

                    textArea.setText(content.toString());

                    scanner.close();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }

    private static class ExportFileMouseListener implements MouseListener {
        private JTextArea textArea;

        public ExportFileMouseListener(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                int result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    PrintWriter writer = new PrintWriter(fileChooser.getSelectedFile());
                    writer.print(textArea.getText());

                    writer.close();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }
}
