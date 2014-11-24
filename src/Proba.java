import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 27.10.2014.
 */
public class Proba extends JComponent {
    Timer t1, t2, t3, p1;

    static ArrayList<String> list = new ArrayList<String>();
    static Path path = Paths.get("1.txt");
    static Charset charset = Charset.forName("UTF-8");

    static String name;
    int count = 0;
    int de = 0;
    int dm = 0;
    int dc = 0;
    int dv = -4;
    int db = 500;
    int i = 1;
    int j = 0;
    private BufferedImage image;
    BufferedImage image2;
    BufferedImage image3;
    private Physics physics = new Physics();
    private int dx = 0;

    public Proba() throws IOException {
        setPreferredSize(new Dimension(500, 500));
        image = ImageIO.read(getClass().getResource("spritenew2.png"));
        image2 = ImageIO.read(getClass().getResource("444.jpg"));
        image3 = ImageIO.read(getClass().getResource("Barrels.png"));
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    dx = 1;
                    if (physics.getEI() >= (-5 * getWidth())) {
                        de = -1;
                        dc = 0;

                    }
                    //else {de=0;dc=1;}
                    // if (physics.getX()+102>=getWidth())dc=0;

                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    dx = -1;
                    if (physics.getEI() <= 0) {
                        de = 1;
                        dc = 0;
                    }
                    //else {de=0;dc=-1;}
                    //if (physics.getX()<=0)dc=0;

                }

                if (e.getKeyCode() == KeyEvent.VK_UP && dv == -4) {
                    p1.start();
                    dv += 1;

                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {// && j>500*1.5
                    count = 0;
                    db = 500;
                    t1.start();
                    t2.start();
                    j = 0;
                    dv = -4;
                    dx = 0;
                    de = 0;
                    dc = 0;
                    physics.izm(0, 0);
                }

            }

            public void keyReleased(KeyEvent e) {
                dx = 0;
                de = 0;
                dc = 0;

            }

        });

        t1 = new Timer(2, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                db -= 1;
                if (db < -50) {
                    db = 550;
                    count += 1;
                }
                dm += 1;
                if (dm == 8) dm = 0;
                //   repaint();
            }
        });
        t1.start();

        t2 = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                physics.update(dx, de, dc, dv, j);


//               if (dv > -4 && dv < 3) {dv += 1;}
//                else if (dv >= 3) {dv = -4;}


//                p1= new Timer(50, new ActionListener() {
//                    public void actionPerformed(ActionEvent e) {
//                        if (dv > -4 && dv < 3) {
//                            dv += 1;
//                        } else if (dv >= 3) {
//                            dv = -4;p1.stop();
//                        }
//                    }
//                });


                repaint();
            }
        });
        t2.start();
        t3 = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                j += 5;
                repaint();
            }
        });


        p1 = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (dv > -4 && dv < 3) {
                    dv += 1;
                } else if (dv >= 3) {
                    dv = -4;
                    p1.stop();
                }
            }
        });


        Files.write(path, list, charset);

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        db += physics.getMM();

        int x = getWidth() / 2 - 200 + physics.getX();
        int y = getHeight() - 200 - 50 + physics.getY();
        String str = "Ваш cчет: " + count;
        String str2 = "Чтобы повторить попытку нажмите пробел";
        int strWidth = g.getFontMetrics().stringWidth(str);
        int strWidth2 = g.getFontMetrics().stringWidth(str2);


        BufferedImage subimage = image.getSubimage(physics.getCadre() * 85, 0, 85, 151);
        BufferedImage subimage3 = image3.getSubimage(dm * 41, 0, 41, 46);
        g.drawImage(image2, physics.getEI(), 0, getWidth() * 6, getHeight(), this);
        g.drawImage(subimage, x, y, this);
        g.drawImage(subimage3, db, 354, this);
        g.setColor(Color.red);
        g.drawRect(x, y, 85, 151);
        g.drawRect(db, 354, 41, 46);
//        g.setColor(Color.blue);
//        g.fillOval(db, 352, 50, 50);


        Rectangle manRect = new Rectangle(x, y, 85, 151);
        Rectangle ballRect = new Rectangle(db, 354, 41, 46);
        Rectangle intersection = manRect.intersection(ballRect);
        //if ((354 - 45 < y + 110 && x + 60 + physics.getP() <= db + 41 && x + 60 + physics.getP() > db - 41) || (353 - 45 <= y + 110 && x < db + 41 && x > db - 41) || (354 - 45 <= y + 110 && x < db - 41 && x + 60 + physics.getP() > db + 41)) {
        if (!intersection.isEmpty()) {
            loop:
            for (int yi = 0; yi < intersection.height; yi++) {
                for (int xi = 0; xi < intersection.width; xi++) {
                    int pixel1 = subimage.getRGB(intersection.x + xi - manRect.x, intersection.y + yi - manRect.y);
                    int pixel2 = subimage3.getRGB(intersection.x + xi - ballRect.x, intersection.y + yi - ballRect.y);


                    int alpha1 = pixel1 & 0xFF000000;
                    int alpha2 = pixel2 & 0xFF000000;
                    if (alpha1 != 0 && alpha2 != 0) {
                        t1.stop();
                        t2.stop();
                        p1.stop();
                        break loop;
                    }
                }
            }


/*

           t3.start();
            g.setColor(Color.red);g.fillOval(250-j/2, 250-j/2, j, j);
            if (j>500*1.5){
                t3.stop();
                g.setColor(Color.white);
                g.drawString(str, 250 - strWidth / 2, 250 - 60);
                g.drawString(str2, 250 - strWidth2 / 2, 250 - 40);
                list.add(name+" Ваш счет: "+count);
                 try {
                Files.write(path, list, charset);
            } catch (IOException ex){
                System.out.println("Ошибочка");};
            }
            */
        }


    }

    public static void main(String[] args) throws IOException {


        JFrame frame = new JFrame("Proba");
        name = JOptionPane.showInputDialog("Введите имя пользователя");
        List<String> line = Files.readAllLines(path, charset);
        for (String linee : line) {
            list.add(linee);
        }


        frame.add(new Proba());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
