package Hacks.LA;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class MyPanel extends JPanel implements Runnable {
    private int pIndex = 0;
    Queue<List<Coordinate>> coordinates;

    public MyPanel (Queue<List<Coordinate>> coordinates) {
        super();
        this.setBackground(new Color(0x444444));
        this.coordinates = coordinates;
    }

    public MyPanel () {
        super();
        this.setBackground(new Color(0x444444));
        this.coordinates = null;
    }

    public void addQueue (Queue<List<Coordinate>> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        while (!coordinates.isEmpty()) {
            List<Coordinate> list = coordinates.poll();
            for (Coordinate coordinate : list) {

                switch (coordinate.getState()) {
                    case HEALTHY : {
                        g.setColor(new Color(0xdddddd));
                        break;
                    }
                    case INFECTED: {
                        g.setColor(new Color(0xff0000));
                        break;
                    }
                    case HOSPITALIZED:{
                        g.setColor(new Color(0xCCFFFF));
                        break;
                    }
                    case IMMUNE: {
                        g.setColor(new Color(0x99FF99));
                        break;
                    }
                    default : {
                        g.setColor(new Color(0xA0A0A0));
                        break;
                    }
                }

                g.fillOval(coordinate.getX(), coordinate.getY(), 20, 20); //TODO: update to constants
            }
        }
    }

    @Override
    public void run() {
        MyPanel.this.repaint();

    }
}
