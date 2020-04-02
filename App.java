import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import java.io.IOException;
import java.lang.*;
import java.net.MalformedURLException;
import java.net.URL;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.visual_recognition.v3.model.ClassifyOptions;

public class App /*extends JFrame*/ {
    private JPanel panel;
    private JButton run;
    private JTextField url;
    private JPanel imagePanel;
    private JPanel textPanel;

    public App() {
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (url.getText() != null && !url.getText().isEmpty()) {

                    System.out.println(url.getText());
                    imagePanel.setLayout(new BorderLayout());
                    Image image = null;

                    String urlImage = url.getText();
                    try {
                        URL urlImg = new URL(urlImage);
                        image = ImageIO.read(urlImg);
                    } catch (MalformedURLException ex) {
                        System.out.println("Malformed URL");
                    } catch (IOException ex) {
                        System.out.println("Cannot load image");
                    }

                    JLabel label = new JLabel(new ImageIcon(image));
                    imagePanel.add(label, BorderLayout.WEST);

                    IamAuthenticator authenticator = new IamAuthenticator("PQNuZwCyBaiu2nZkCzwxJI6ExiAMSxyxw08IoYzeyZQX");
                    VisualRecognition visualRecognition = new VisualRecognition("2018-03-19", authenticator);
                    visualRecognition.setServiceUrl("https://api.us-south.visual-recognition.watson.cloud.ibm.com/instances/827967ac-1a24-4f50-8f50-44b55426f7d3");

                    ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                            .url(url.getText())
                            .build();
                    ClassifiedImages result = visualRecognition.classify(classifyOptions).execute().getResult();
                    //JOptionPane.showMessageDialog(null, result);
                    //summary.setText(String.valueOf(result));

                    textPanel.setLayout(new BorderLayout());
                    JTextArea textArea = new JTextArea(result.getImages().toString());
                    textArea.setFont(new Font("Serif", Font.ITALIC, 18));
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    textArea.setOpaque(false);
                    textArea.setEditable(false);
                    JScrollPane scrollPanel = new JScrollPane(textArea);
                    textPanel.add(scrollPanel);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Ingrese la url de una imagen.");
                }
            }
        });

    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Imagen Recognition");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new App().panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
