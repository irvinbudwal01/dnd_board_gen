package src;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import java.io.File;
import java.io.IOError;

import javax.swing.border.Border;

import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.geom.*;

public class window extends JFrame {

    JTextField rowField;

    JTextField columnField;

    JButton button;// = new JButton("Generate");

    JButton addImage;// = new JButton("Add image");

    JButton removeImage;

    //JPanel legendToLoad = new JPanel();

    ImageIcon dndLogo = new ImageIcon("pics/dnd_logo.png");

    static class PrimaryPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) { 
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth(), h = getHeight();
        Color color1 = new Color(250, 217, 185);
        Color color2 = Color.WHITE;
        Point2D center = new Point2D.Float(w / 2, h / 2);
        float radius = 500;
        float[] dist = {0.05f, .95f};
        Color[] colors = {color2, color1};

        RadialGradientPaint gp = new RadialGradientPaint(center, radius, dist, colors, CycleMethod.NO_CYCLE); //this graident needs to be from the center
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
    }//= new JPanel();

    JFrame frame = new JFrame();

    int row = 1;

    int col = 1;

    Vector<icons> addedIcons = new Vector<icons>(); //holds user added icons

    static class LegendToLoad extends JPanel {

    Vector<icons> legendIcons = new Vector<icons>();

    LegendToLoad(Vector<icons> temp) {
        //this.setBackground(new Color(250, 217, 185));
        legendIcons = temp;

        for(icons icon: legendIcons) {
            icons test = icon;

            Image tempImage = test.image.getImage(); // transform it
            Image newimg = tempImage.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            icons finalImg = new icons(newimg, icon.path);  // transform it back

            JLabel temp123 = new JLabel(finalImg.image);

            add(temp123);
        }
        
    }

    public void refreshLoadedIcons() {

        this.removeAll();

        for (icons icon: legendIcons) {
        icons test = icon;
        
        Image tempImage = test.image.getImage(); // transform it
        Image newimg = tempImage.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        icons finalImg = new icons(newimg, icon.path);  // transform it back

        JLabel temp123 = new JLabel(finalImg.image);
        
        add(temp123);

        }
        paintAll(getGraphics());
    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(3));


        Border blackline = BorderFactory.createLineBorder(Color.black);
        //this.setBorder(blackline);

        g2.drawLine(450 - ((legendIcons.size() - 1) * 54) , 3, 550 + ((legendIcons.size() - 1) * 54), 3);
        g2.drawLine(450 - ((legendIcons.size() - 1) * 54) , 105, 550 + ((legendIcons.size() - 1) * 54), 105);
        g2.drawLine(450 - ((legendIcons.size() - 1) * 54) , 3, 450 - ((legendIcons.size() - 1) * 54), 105);
        g2.drawLine(550 + ((legendIcons.size() - 1) * 54) , 3, 550 + ((legendIcons.size() - 1) * 54), 105);

        //yeh maybe find perimeter of the line like p = x1 - x2

        int perimeter = (550 + ((legendIcons.size() - 1) * 55)) - (450 - ((legendIcons.size() - 1) * 55));

        int startPoint = 450 - ((legendIcons.size() - 1) * 54);

        int endPoint = 550 + ((legendIcons.size() - 1) * 55);

        int numOfLines = legendIcons.size() - 1;

        for (int i = 0; i < numOfLines + 1; i++) {
            //int xToDraw = perimeter / numOfLines;
            g2.drawLine(startPoint + (106 * i), 3, startPoint + (106 * i), 105);
        }

    }
}

    public void init() {

        PrimaryPanel panel = new PrimaryPanel();

        panel.setLayout(null);

        rowField = new JTextField("2", 1);

        columnField = new JTextField("2", 1);

        //rowField.setBounds(50, 300, 200, 200);

        //columnField.setBounds(50, 500, 200, 200);

        Font bigFont = rowField.getFont().deriveFont(Font.PLAIN, 45f);

        rowField.setFont(bigFont);

        columnField.setFont(bigFont);

        // button.setSize(30, 30);

        // button.setBounds(50, 50, 20, 20);

        
        icons icon1 = new icons("pics/green_circle.png");

        icons icon2 = new icons("pics/red_circle.png");

        icons icon3 = new icons("pics/house.png");

        icons icon4 = new icons("pics/tree.png");

        icons icon5 = new icons("pics/Red_circle_frame_transparent.png");

        addedIcons.add(icon1);
        addedIcons.add(icon2);
        addedIcons.add(icon3);
        addedIcons.add(icon4);
        addedIcons.add(icon5);

        LegendToLoad legendToLoad = new LegendToLoad(addedIcons);

        button = new JButton(new AbstractAction("Generate") {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    row = Integer.parseInt(rowField.getText());
                    col = Integer.parseInt(columnField.getText());

                    if(row <= 20 && col <= 20) {

                        frame.setVisible(false);
                
                        frame.remove(panel);
                
                        frame.setLayout(new BorderLayout());
                
                        GridsCanvas grid = new GridsCanvas(1000, 800, row, col);
                
                        grid.setBounds(0, 0, frame.getWidth(), frame.getWidth());
                
                        grid.setLayout(null);
                
                        Legend legendGrid = new Legend(50, 1, 5, grid, addedIcons);
                
                        //legendGrid.setBounds(0, 650, 300, 300);
                
                        //grid.setOpaque(true);
                
                        //legendGrid.setOpaque(true);
                
                        grid.add(legendGrid);
                
                        frame.add(grid, BorderLayout.CENTER);
                
                        pack();
                
                        frame.setVisible(true);

                    }
                    else {
                        JOptionPane.showMessageDialog(panel, "Row and Col values must be 20 or lower!");
                    }
                }       
                
                catch (NumberFormatException error) {
                    JOptionPane.showMessageDialog(panel, error);
                }
        
            }
        });

        //LegendToLoad legendToLoad = new LegendToLoad(addedIcons);


        addImage = new JButton(new AbstractAction("Add Image") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ImageIcon selected =
                getImageIcon();
                //legendToLoad.legendIcons = addedIcons;
                legendToLoad.refreshLoadedIcons();
            }
            private void getImageIcon() {
                if (Desktop.isDesktopSupported()) {
                    File directory = new File("src/pics/");
                    JFileChooser chooser = new JFileChooser(directory);
                    chooser.setDialogTitle("Choose an image");
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG/PNG", "jpg", "png");
                    chooser.setFileFilter(filter);
                    int returnVal = chooser.showOpenDialog(addImage);
                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                        System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
                        File temp = chooser.getSelectedFile(); //have to get path of selected picture and then set temp2 to that image in the selected directory to push to legend
                        icons temp2 = new icons(temp.getAbsolutePath());
                        addedIcons.add(temp2);
                    }
                  }
                }
        });

        removeImage = new JButton(new AbstractAction("Remove Image") {
            public void actionPerformed(ActionEvent e) {
                if(!addedIcons.isEmpty()) {
                addedIcons.remove(addedIcons.size() - 1);
                legendToLoad.refreshLoadedIcons();
                //panel.repaint();
                legendToLoad.repaint();
                }
            }
        });
        
        // for(int i = 0; i < addedIcons.size(); i++) {
        //     legendToLoad.add(addedIcons.elementAt(i));
        // }
        
        legendToLoad.setBounds(0, 500, 1000, 300);
        panel.setBounds(0, 0, 1000, 500);
        


        JLabel rowText = new JLabel("Rows:");
        JLabel colText = new JLabel("Columns:    ");
        JLabel title = new JLabel("D&D Board Generator");
        title.setFont(bigFont);

        title.setBounds(300, -200, 600, 600);
        panel.add(title);

        //c.fill = GridBagConstraints.HORIZONTAL;

        Font tempFont = rowField.getFont().deriveFont(Font.ITALIC);

        rowText.setFont(tempFont);
        colText.setFont(tempFont);

        rowText.setBounds(200, -100, 600, 600);

        panel.add(rowText);

        colText.setBounds(200, 0, 600, 600);

        panel.add(colText);

        rowField.setBounds(400, 175, 100, 50);

        panel.add(rowField);

        columnField.setBounds(400, 275, 100, 50);

        panel.add(columnField);

        button.setBounds(600, 150, 150, 50);

        panel.add(button);

        addImage.setBounds(600, 250, 150, 50);

        panel.add(addImage);

        removeImage.setBounds(600, 350, 150, 50);

        panel.add(removeImage);

        Image newimg = dndLogo.getImage().getScaledInstance(240, 135, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way

        JLabel resizeLogo = new JLabel(new ImageIcon(newimg));

        resizeLogo.setBounds(0, -25, 240, 135);

        panel.add(resizeLogo);

        //panel.add(legendToLoad);

        legendToLoad.setOpaque(false);

        frame.add(legendToLoad);
        frame.add(panel);

        frame.setTitle("D&D Board Generator");
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        pack();
    }

    // public void actionPerformed(ActionEvent e) {

    //     row = Integer.parseInt(rowField.getText());
    //     col = Integer.parseInt(columnField.getText());

    //     frame.setVisible(false);

    //     frame.remove(panel);

    //     frame.setLayout(new BorderLayout());

    //     GridsCanvas grid = new GridsCanvas(1000, 800, row, col);

    //     grid.setBounds(0, 0, frame.getWidth(), frame.getWidth());

    //     grid.setLayout(null);

    //     Legend legendGrid = new Legend(200, 50, 1, 5, grid);

    //     //legendGrid.setBounds(0, 650, 300, 300);

    //     //grid.setOpaque(true);

    //     //legendGrid.setOpaque(true);

    //     grid.add(legendGrid);

    //     frame.add(grid, BorderLayout.CENTER);

    //     pack();

    //     frame.setVisible(true);

    // }
    
}
/*
    REFACTOR LEGEND.JAVA TO SUPPORT MULTIPLE ICONS (SET BOUNDS AUTOMATICALLY/WHEN CLICKED SET NEW ICON TO THE LEGEND ICON'S IMAGE ATTRIBUTE/PAINT SHOULD ALSO BE IN A LOOP/
    RESIZE AND SET IMAGE CORNER SHOULD ALSO BE IN A LOOP)

    LET WINDOW.JAVA HOLD VECTOR OF ICONS TO BE SENT TO LEGEND.JAVA (DISPLAY ICONS IN WINDOW.JAVA AS WELL)
    LET FIRST 4 BE DEFAULTS FOR NOW


    ADD PICS INTO WINDOW.JAVA -> LEGEND WILL DISPLAY ALL OF THE LOADED IN ICONS -> GRIDSCANVAS WILL OBTAIN THE PICTURE TO THEN BE DISPLAYED (ADD IMAGE OBSERVER FOR GIFS)
 */