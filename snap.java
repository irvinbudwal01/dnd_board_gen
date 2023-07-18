package src;

import java.awt.Rectangle;

public class snap extends Rectangle{
    
    Rectangle[][] sections;

    public snap(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        sections = new Rectangle[2][2];
        sections[0][0] = new Rectangle(x, y, width / 2, height / 2);
        sections[0][1] = new Rectangle(x + (width / 2), y, width / 2, height / 2);
        sections[1][0] = new Rectangle(x, y + (height / 2), width / 2, height / 2);
        sections[1][1] = new Rectangle(x + (width / 2), y + (height / 2), width / 2, height / 2);
      }

}
