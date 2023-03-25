package pruebas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Notification {
    public static void main(String[] args) {
        new NotiFrame();
    }
}

class NotiFrame extends JFrame{
    
    public NotiFrame(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(800, 500);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        
        initComponents();
    }
    
    private void initComponents(){
        superior.setBackground(java.awt.Color.WHITE);
        
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setColumns(3);
        grid.setRows(3);
        inferior.setLayout(grid);
        
        for (int i = 0; i < 9; i++) {
            btns[i] = new JButton(String.valueOf(i+1));
            btns[i].setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 28));
            
            final int x = i+1;
            
            btns[i].addActionListener((ActionEvent e) -> {
                System.out.println("Boton "+x);
            });
            
            inferior.add(btns[i]);
        }
        
        superior.add(btnNotif);
        this.add(superior);
        this.add(inferior);
        
        superior.setSize(this.getContentPane().getWidth(), 80);
        inferior.setBounds(0, 80, this.getContentPane().getWidth(), this.getContentPane().getHeight()-80);
        
        //================== GLASSPANE ==================
        
        this.setGlassPane(new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(Color.red);
                g.fillOval(50, 50, 50, 50);
            }
        });
        
        notificaciones.setPreferredSize(new Dimension(200, 350));
        notificaciones.setSize(notificaciones.getPreferredSize());
        notificaciones.setBackground(new Color(204, 255, 255));
        
        
        glass = (Container)(this.getGlassPane());
        glass.setLayout(null);
        glass.add(notificaciones);
        glass.setVisible(true);
        
        glass.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                
                int minX = notificaciones.getX();
                int maxX = minX + notificaciones.getWidth();
                
                int minY = notificaciones.getY();
                int maxY = minY + notificaciones.getHeight();
                
                if((mouseX < minX || mouseX > maxX) || (mouseY < minY || mouseY > maxY)){
                    System.out.println("Fuera de los límites");
                    glass.setVisible(false);
                } else {
                    System.out.println("Dentro del panel");
                }
            }
        });
        
        btnNotif.addActionListener((ActionEvent) -> {
            notificaciones.setLocation(50, 20);
            glass.setVisible(!glass.isVisible());
        });
            
    }
    
    private final FlowLayout flow = new FlowLayout(FlowLayout.TRAILING);
    private final JPanel superior = new JPanel(flow);
    private final JButton btnNotif = new JButton("Notificaciones");
    
    private final GridLayout grid = new GridLayout();
    private final JPanel inferior = new JPanel();
    private final JButton btns[] = new JButton[9];
    
    private Container glass;
    private JPanel notificaciones = new JPanel(null);
}