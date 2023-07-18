package src;
import javax.swing.*;

import java.awt.*;

public class icons extends ImageIcon {

        ImageIcon image;

        JTextField text;
    
        int img_height;
        int img_width;
      
        Point image_corner = new Point(0,0);
        Rectangle bounds;

        String path;
    
        public icons(String fileName) {
          this.image = new ImageIcon(fileName);
          this.path = fileName;
          //this.image.setImage(this.image.getImage());
          //this.text = new JTextField("ah");
          img_height = image.getIconHeight();
          img_width = image.getIconWidth();
          loadImage(this.image.getImage());
        }

        public icons(Image image, String fileName) {
          this.image = new ImageIcon(image);
          this.path = fileName;
          this.text = new JTextField(".");
          this.text.setOpaque(false);
          this.text.setBorder(null);
          this.text.setBackground(null);
          img_height = this.image.getIconHeight();
          img_width = this.image.getIconWidth();
          loadImage(this.image.getImage());
      }

}
    
