
/**
 * Created by student on 27.10.2014.
 */
public class Physics {

    private int x = 0;

    private int mm = 0;

    private int p=0;
    private int cadre = 0;
    private int ei=0;
    private  double y=0;

    void update(int dx,int de, int dc, int dv, int i) {
        int newcadre = cadre + dx;
        if (newcadre < 0) {
            cadre = 7;
        } else if (newcadre >= 8) {
            cadre = 0;
        } else {
            cadre = newcadre;
        }
        x += dc * 10;
        ei=ei+ de*10;


        if (ei<=(-5*500)){
            ei=-500;
        }
       else if (ei>(0)){
            ei=-500*4;
        }




        mm=de*10;
//if (i==0){x=0;ei=0;i=1;}


        //if (dv>-3)y=-(-(dv*dv)+4)*10;
        if (dv>-4)y=-(-(dv*dv)+9)*10;
        else y=0;
        switch (cadre) {
            case 0:
            case 3:
                p=-45;
                break;
            case 1:
            case 4:
                p=-35;
                break;
            case 2 :
            case 5:
                p=-3;
                break;
        }
    }

    public int getX() {
        return x;
    }

    public int getMM() {
        return mm;
    }

    public int getP() {
        return p;
    }
    public int getY() { return (int)y; }
    public int getEI() {
        return ei;
    }
    public int getCadre() {
        return cadre;
    }
    public void izm(int xm,int eim){
      x= xm;
      ei=eim;
    }
}
