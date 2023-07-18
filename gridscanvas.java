package src;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;

import java.util.*;

import java.awt.*;
import java.awt.event.*;



import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;

class GridsCanvas extends JPanel {
  int width, height;

  int rows;

  int cols;

  Vector <icons> createdIcons = new Vector <icons>();

  snap[][] matrix;

  Point previous_point;

  boolean snapped = false;

  public void resetFocus() {
    this.requestFocusInWindow(true);
}

  GridsCanvas(int w, int h, int r, int c) {
    setSize(width = w, height = h);
    rows = r;
    cols = c;
    
    matrix = new snap[rows][cols];

    //int areaPerSquare = (width + height) / (rows * cols);

    int rowHt = height / (rows);

    int rowWid = width / (cols);

    snap temp;

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        temp = new snap(i * rowWid, j * rowHt, rowWid, rowHt);
        matrix[i][j] = temp;
        //matrix[i][j] = Rectangle[2][2] new Rectangle();//new Rectangle(i * rowWid, j * rowHt, rowWid, rowHt);
      }
    }

    ClickListener CL = new ClickListener();
    this.addMouseListener(CL);
  
    DragListener DL = new DragListener();
    this.addMouseMotionListener(DL);

    this.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("9"), "increaseSize");
    this.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("0"), "decreaseSize");

    this.getActionMap().put("increaseSize", new AbstractAction("increaseSize") {
      public void actionPerformed(ActionEvent e) {
        if(!createdIcons.isEmpty()) {
        icons tempIcon = new icons(createdIcons.get(0).path);
        Image tempImage = tempIcon.image.getImage();
        int tempW = createdIcons.get(0).img_width;
        int tempH = createdIcons.get(0).img_height;
        Point tempCorner = createdIcons.get(0).image_corner;
        
        if(tempW <= 400 || tempH <= 400) {

          remove(createdIcons.get(0).text);
          createdIcons.remove(0);

          Image newimg = tempImage.getScaledInstance(tempW + 25, tempH + 25, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
          icons toAdd = new icons(newimg, tempIcon.path);  // transform it back

          toAdd.image_corner = tempCorner;
  
          createdIcons.add(0, toAdd);
          repaint();
        }

        }
      }

    });

    this.getActionMap().put("decreaseSize", new AbstractAction("decreaseSize") {
      public void actionPerformed(ActionEvent e) {
        if(!createdIcons.isEmpty()) {
        icons tempIcon = new icons(createdIcons.get(0).path);
        Image tempImage = tempIcon.image.getImage();
        int tempW = createdIcons.get(0).img_width;
        int tempH = createdIcons.get(0).img_height;
        Point tempCorner = createdIcons.get(0).image_corner;
        
        if(tempW >= 50 || tempH >= 50) {
        remove(createdIcons.get(0).text);
        createdIcons.remove(0);

        Image newimg = tempImage.getScaledInstance(tempW - 25, tempH - 25, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        icons toAdd = new icons(newimg, tempIcon.path);  // transform it back

        toAdd.image_corner = tempCorner;

        createdIcons.add(0, toAdd);
        repaint();
        }

        }
      }

    });

  }

  public void paint(Graphics g) {
    super.paint(g);

    Graphics2D g2 = (Graphics2D) g;

    g2.setStroke(new BasicStroke(3));

    int i;
    width = getSize().width;
    height = getSize().height;

    // draw the rows
    int rowHt = height / (rows);
    for (i = 0; i < rows; i++)
      g2.drawLine(0, i * rowHt, width, i * rowHt);

    // draw the columns
    int rowWid = width / (cols);
    for (i = 0; i < cols; i++)
      g2.drawLine(i * rowWid, 0, i * rowWid, height);

    for (icons icon : createdIcons) {
        icon.image.paintIcon(this, g,(int)icon.image_corner.getX(), (int)icon.image_corner.getY());
        icon.text.setBounds((int)icon.image_corner.getX() + (icon.img_width / 3) - (icon.text.getText().length() * 3/2), (int)icon.image_corner.getY() + icon.img_height, 20 + (icon.text.getText().length() * 13/2), 30);
    }

      repaint();

  }

  private class ClickListener extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
      resetFocus();
      previous_point = e.getPoint();
    for (icons icon : createdIcons) {
      icon.bounds = new Rectangle(icon.image_corner, new Dimension(icon.img_height, icon.img_width));
        if (icon.bounds.contains(previous_point)) {
          add(icon.text);
          int indexCurrent = createdIcons.indexOf(icon);
          icons top = createdIcons.firstElement();
          createdIcons.set(0, icon);
          createdIcons.setElementAt(top, indexCurrent);
          if(e.isShiftDown()) {
            createdIcons.remove(icon);
            remove(icon.text);
          }
          if(e.isAltDown()){
            snapped = true;
          }
          break;
        }
      }
      // for(int i = 0; i < rows; i++) {
      //     for(int j = 0; j < cols; j++) {
      //       if(matrix[i][j].contains(previous_point)) {
      //         //System.out.println("Clicked in rectangle: " + i + "/" + j);
      //         for(int k = 0; k < 2; k++) {

      //           for(int l = 0; l < 2; l++) {
      //             if(matrix[i][j].sections[k][l].contains(previous_point)) {
      //               System.out.println("Within bounds of: " + k + "/" + l);
      //             }
      //           }
      //       }

      //       }
      //   }
      // }
    
   }
    public void mouseReleased(MouseEvent e) {
      snapped = false;
    }
  }

private class DragListener extends MouseMotionAdapter {
    public void mouseDragged(MouseEvent e) {
    Point currentPoint = e.getPoint();
     for (icons icon : createdIcons) {
      icon.bounds = new Rectangle(icon.image_corner, new Dimension(icon.img_width, icon.img_height));
      
      //icon.text.setBounds((int)(e.getPoint().getX() - previous_point.getX()), (int)(e.getPoint().getY() - previous_point.getY()) + 200, 200, 30);
       if (icon.bounds.contains(previous_point)) {
        icon.image_corner.translate((int)(e.getPoint().getX() - previous_point.getX()), (int)(e.getPoint().getY() - previous_point.getY()));
        icon.text.getBounds().translate((int)(e.getPoint().getX() - previous_point.getX()), (int)(e.getPoint().getY() - previous_point.getY()) + 200);
        previous_point = currentPoint;
        repaint();

        if(snapped) {
        for(int i = 0; i < rows; i++) {
           for(int j = 0; j < cols; j++) {
            for(int k = 0; k < 2; k++) {
              for(int l = 0; l < 2; l++) {
                if(matrix[i][j].sections[k][l].contains(previous_point)) {
                  //System.out.println("Current grid: " + i + "/" + j);
                  //System.out.println("Current section: " + k + "/" + l);
                  //System.out.println((int)matrix[i][j].sections[k][l].getCenterX() + " " + (int)matrix[i][j].sections[k][l].getCenterY());
                  icon.image_corner.setLocation((int)(matrix[i][j].sections[k][l].getX()), (int)matrix[i][j].sections[k][l].getY());
                  previous_point = currentPoint;
                  repaint();
             }
           }
          }
          }
         }
       }
      }
      //  icon.bounds = new Rectangle(icon.image_corner, new Dimension(icon.img_height, icon.img_width));
     }
  }
}

}