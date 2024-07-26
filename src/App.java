import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.io.*;

public class App extends JFrame{

    JButton guardarPDF;
    JPanel panel;
    JFileChooser seleccFileChooser;
    JEditorPane editor;

    public App(){
        panel = new JPanel();
        seleccFileChooser = new JFileChooser();
        editor = new JEditorPane();
        guardarPDF = new JButton("Guardar PDF");

        guardarPDF.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int opcion = seleccFileChooser.showOpenDialog(null);
                if (opcion == seleccFileChooser.APPROVE_OPTION) {
                    try {
                        OutputStream Salida_Texto = new FileOutputStream(seleccFileChooser.getSelectedFile());
                        Document doc = new Document();
                        PdfWriter.getInstance(doc, Salida_Texto);
                        doc.open();
                        doc.add(new Paragraph(editor.getText()));
                        doc.close();
                        Salida_Texto.close();
                    } catch (Exception i) {
                        // TODO: handle exception
                    }
                }
            }
        });

        panel.add(guardarPDF);
        this.add(panel,BorderLayout.NORTH);
        this.add(editor,BorderLayout.CENTER);
        this.setSize(550,750);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws Exception {
        new App();
    }
}
