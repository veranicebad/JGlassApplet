package package1;


import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import static java.lang.Math.*;

/**
 *
 * @author ver
 */
public class Water {

    protected double ro, Vw, T;
    Color color;
    double originalV;
    public boolean isMixing;

    public Water(Color color, double ro, double Vw, double T, boolean isMixing) {
        this.color = color;
        this.ro = ro;
        this.Vw = Vw;
        this.T = T;
        this.originalV = Vw;
        this.isMixing=isMixing;
    }

    protected void volumeIncrease(double V) {
        originalV = originalV + V;
        Vw = Vw + V;
    }

    protected void volumeContraction(double V) {
        originalV = originalV - V;
        Vw = Vw - V;
    }
}
