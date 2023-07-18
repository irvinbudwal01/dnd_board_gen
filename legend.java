package src;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;

import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

class Legend extends JPanel {
  int width, height;

  int rows;

  int cols;

  //Vector <icons> createdIcons = new Vector <icons>();

  Vector <icons> loadedIcons = new Vector <icons>(); //fill from GC

  Point previous_point;

  GridsCanvas GC;

  int dX, dY;

  //Point legendCorner = new Point(0, 0);

  Rectangle legendBounds;

  int toScale;

  public icons resize(icons toResize, int toScale) {
    toScale = (GC.width + GC.height) / (GC.rows * GC.cols);
    icons tempIcon = new icons(toResize.path);
    Image tempImage = tempIcon.image.getImage(); // transform it
    Image newimg = tempImage.getScaledInstance(toScale * 7/10, toScale * 7/10, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
    toResize = new icons(newimg, toResize.path);  // transform it back

    if(toResize.img_width <= 50) {
      toResize = resize(toResize);
    }

    return toResize;
  }

  public icons resize(icons toResize) {
    icons tempIcon = new icons(toResize.path);
    Image tempImage = tempIcon.image.getImage(); // transform it
    Image newimg = tempImage.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
    toResize = new icons(newimg, toResize.path);  // transform it back

    return toResize;
  }

  public Legend(int h, int r, int c, GridsCanvas GC, Vector <icons> addedIcons) {

    this.GC = GC; //link the two panels

    this.loadedIcons = addedIcons;

    for (int i = 0; i < this.loadedIcons.size(); i++) {
      icons toChange = this.loadedIcons.elementAt(i);
      toChange = resize(toChange);
      toChange.image_corner = new Point(50 * i, 0);
      toChange.bounds = new Rectangle(toChange.image_corner, new Dimension(toChange.image.getIconWidth() - 10, toChange.image.getIconWidth() - 10));

      this.loadedIcons.remove(i);
      this.loadedIcons.add(i, toChange);
    }

    setSize(width = 50 * addedIcons.size(), height = h);
    rows = r;
    cols = c;
    setBounds(0, 0, width, height);

    legendBounds = new Rectangle(0 , 0, width, height);

    // icon1 = resize(icon1);

    // icon2 = resize(icon2);

    // icon3 = resize(icon3);

    // icon4 = resize(icon4);

    // icon1.image_corner = new Point(0,0);
    // icon2.image_corner = new Point(50,0);
    // icon3.image_corner = new Point(100,0);
    // icon4.image_corner = new Point(150, 0);
    ClickListener CL = new ClickListener();
    this.addMouseListener(CL);
  
    DragListener DL = new DragListener();
    this.addMouseMotionListener(DL);

    this.setBackground(Color.LIGHT_GRAY);

    Border blackline = BorderFactory.createLineBorder(Color.black);
    this.setBorder(blackline);
  }

  public void paint(Graphics g) {
    super.paint(g);
    // icon1.image.paintIcon(this, g,(int)icon1.image_corner.getX(), (int)icon1.image_corner.getY());
    // icon2.image.paintIcon(this, g,(int)icon2.image_corner.getX(), (int)icon2.image_corner.getY());
    // icon3.image.paintIcon(this, g,(int)icon3.image_corner.getX(), (int)icon3.image_corner.getY());
    // icon4.image.paintIcon(this, g,(int)icon4.image_corner.getX(), (int)icon4.image_corner.getY());

    for (icons icon : this.loadedIcons) {
      //System.out.println("Resized icons" + icon.image.getIconWidth() + icon.image.getIconHeight());
      icon.image.paintIcon(this, g,(int)icon.image_corner.getX(), (int)icon.image_corner.getY());
    }
  }

    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            previous_point = e.getLocationOnScreen();
            
            // Rectangle bounds1 = new Rectangle(icon1.image_corner, new Dimension(icon1.image.getIconWidth() - 15, icon1.image.getIconHeight() - 15));
            // Rectangle bounds2 = new Rectangle(icon2.image_corner, new Dimension(icon2.image.getIconWidth() - 15, icon2.image.getIconHeight() - 15));
            // Rectangle bounds3 = new Rectangle(icon3.image_corner, new Dimension(icon3.image.getIconWidth() - 15, icon3.image.getIconHeight() - 15));
            // Rectangle bounds4 = new Rectangle(icon4.image_corner, new Dimension(icon4.image.getIconWidth() - 15, icon4.image.getIconHeight() - 15));

            for (icons icon : loadedIcons) {
              icon.bounds = new Rectangle(icon.image_corner, new Dimension(icon.image.getIconWidth() - 15, icon.image.getIconWidth() - 15));
            }

            for(icons icon : loadedIcons) {
              if(icon.bounds.contains(e.getPoint())) {
                icons temp = new icons(icon.image.getImage(), icon.path);
                
                temp = resize(temp, toScale);
                temp.image_corner = new Point(500 + GC.createdIcons.size(), 100 + GC.createdIcons.size());
                temp.bounds = new Rectangle(temp.image_corner, new Dimension(temp.img_height, temp.img_width));
                GC.createdIcons.add(temp);
              }
            }

      //           if (bounds1.contains(e.getPoint())) {
      //               System.out.println("Green circle clicked!");
      //               icons tempo = new icons("src/pics/green_circle.png");
      //               tempo = resize(tempo, toScale);
      //               tempo.image_corner = new Point(500 + GC.createdIcons.size(),100 + GC.createdIcons.size());
      //               tempo.bounds = new Rectangle(tempo.image_corner, new Dimension(tempo.img_height, tempo.img_width));
      //               //tempo.image.paintIcon(getFocusCycleRootAncestor(), getGraphics(),(int)tempo.image_corner.getX(), (int)tempo.image_corner.getY());
      //               //createdIcons.add(tempo);
      //               GC.createdIcons.add(tempo);
      //       }
      //           if (bounds2.contains(e.getPoint())) {
      //               System.out.println("Red circle clicked!");
      //               icons tempo = new icons("src/pics/red_circle.png");
      //               tempo = resize(tempo, toScale);
      //               tempo.image_corner = new Point(500 + GC.createdIcons.size(),200 + GC.createdIcons.size());
      //               tempo.bounds = new Rectangle(tempo.image_corner, new Dimension(tempo.img_height, tempo.img_width));
      //               //tempo.image.paintIcon(getFocusCycleRootAncestor(), getGraphics(),(int)tempo.image_corner.getX(), (int)tempo.image_corner.getY());
      //               //createdIcons.add(tempo);
      //               GC.createdIcons.add(tempo);
      // }
      //           if (bounds3.contains(e.getPoint())) {
      //             System.out.println("House clicked!");
      //             icons tempo = new icons("src/pics/house.png");
      //             tempo = resize(tempo, toScale);
      //             tempo.image_corner = new Point(500 + GC.createdIcons.size(),300 + GC.createdIcons.size());
      //             tempo.bounds = new Rectangle(tempo.image_corner, new Dimension(tempo.img_height, tempo.img_width));
      //             //tempo.image.paintIcon(getFocusCycleRootAncestor(), getGraphics(), (int)tempo.image_corner.getX(), (int)tempo.image_corner.getY());
      //             //createdIcons.add(tempo);
      //             GC.createdIcons.add(tempo);
      // }
      //           if (bounds4.contains(e.getPoint())) {
      //             System.out.println("Tree clicked!");
      //             icons tempo = new icons("src/pics/tree.png");
      //             tempo = resize(tempo, toScale);
      //             tempo.image_corner = new Point(500 + GC.createdIcons.size(),400 + GC.createdIcons.size());
      //             tempo.bounds = new Rectangle(tempo.image_corner, new Dimension(tempo.img_height, tempo.img_width));
      //             //tempo.image.paintIcon(getFocusCycleRootAncestor(), getGraphics(), (int)tempo.image_corner.getX(), (int)tempo.image_corner.getY());
      //             //createdIcons.add(tempo);
      //             GC.createdIcons.add(tempo);
      //     }

                if (legendBounds.contains(e.getPoint())) {
                  dX = e.getLocationOnScreen().x - (int)getX();
                  dY = e.getLocationOnScreen().y - (int)getY();
                  }
    }
}

    private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
        Point currentPoint = e.getLocationOnScreen();
        if(legendBounds.contains(e.getPoint())) {
          setLocation((int)(currentPoint.getX() - dX), (int)(currentPoint.getY() - dY));
          dX = e.getLocationOnScreen().x - getX();
          dY = e.getLocationOnScreen().y - getY();

          if(getX() < -(getWidth())) {
            setLocation(-(getWidth()) + 10, (int)currentPoint.getY() - dY); //try not to drag off screen
          }

          else if(getY() < -(getHeight())) {
            setLocation((int)(currentPoint.getX() - dX), -(getHeight()) + 10);
          }
          repaint();
        }
      }
    }
}