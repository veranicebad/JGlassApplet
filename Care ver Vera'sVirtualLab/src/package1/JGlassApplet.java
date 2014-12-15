package package1;


import java.awt.image.BufferedImage;
import java.awt.Image;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.*;
import java.util.Map;
import javax.swing.Timer;

/**
 *
 * @author ver
 */
public class JGlassApplet extends Applet {

    private Scene scene = null;
    private Glass glass = null;
    private Scale scale = null;
    private Scale scale2 = null;
    private Scale scale3 = null;
    private Scale scaleBigGlass = null;
    private Glass glass2 = null;
    private Glass glass3 = null;
    private Water water = null;
    private Water water1 = null;
    private Water water2 = null;
    private Water water3 = null;
    private Water waterBigGlass = null;
    private Stream stream = null;
    private Stream stream2 = null;
    private Stream stream3 = null;
    private Stream streamTap = null;
    private Stream streamTap2 = null;
    private Stream streamPipe1 = null;
    private Stream streamPipe2 = null;
    private Pipe pipe1 = null;
    private Pipe pipe2 = null;
    private Stream streamBigGlass = null;
    private Tap tap = null;
    private Tap tap2 = null;
    private Ball ball = null;
    private Ball ball2 = null;
    private Cube cube = null;
    private Glass bigGlass = null;
    private Balance balance = null;
    private Image buffer;
    private final int TIMER_DELAY = 50;
    private Timer timer;
    private Timer timerWater;
    private final int TIMERWATER_DELAY = 20;
    private int width = 800, height = 600;
    //private long c;
    //public double dA = 0, dR = 0, dG = 0, dB = 0;

    @Override
    public void init() {
        super.init();
        setSize(800, 600);
        float m1=0;
        try {
            Map<String,String> params = AppletParameters.decode(getParameter("params"));
            m1 = Float.parseFloat(params.get("m1"));
        } catch (Exception ex) {
            System.err.println("[ERROR] " + ex.getMessage());
            //return;
        }

        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        scene = new Scene(getWidth(), getHeight());
        water = new Water(Color.decode("#BBFFFF"), 1000.0, 25000.0, 20.0, true);
        water1 = new Water(Color.decode("#BBFFFF"), 1000.0, 9000.0, 20.0, true);
        water2 = new Water(Color.BLUE, 1000.0, 2500.0, 20.0, true);
        water3 = new Water(Color.YELLOW, 1000.0, 7000.0, 20.0, true);
        waterBigGlass = new Water(Color.decode("#BBFFFF"), 1000.0, 25000.0, 20.0, true);
        scale = new Scale(70, 185, 30, 10, 20, 175);
        scale2 = new Scale(100, 175, 50, 10, 20, 169);
        scale3 = new Scale(90, 150, 10, 10, 20, 241);
        scaleBigGlass = new Scale(150, 250, 0, 10, 1, 240);
        stream = new Stream(150, 150, 0, 0, water1);
        stream2 = new Stream(150, 150, 0, 0, water2);
        stream3 = new Stream(150, 150, 0, 0, water3);
        streamBigGlass = new Stream(150, 150, 0, 0, waterBigGlass);
        streamPipe1 = new Stream(150, 150, 10, 0, waterBigGlass);
        streamPipe2 = new Stream(150, 150, 10, 0, waterBigGlass);
        glass = new Glass(1 / 10, 520, 300, 70, 185, 30, 0, water1, 0, scale, stream,
                true, false, true, false, null, null, true);
        glass2 = new Glass(1 / 10, 600, 310, 100, 175, 50, 0, water2, 0, scale2,
                stream2, true, true, true, false, null, null, true);
        glass3 = new Glass(1 / 10, 700, 385, 90, 100, 10, 0, water3, 0, scale3,
                stream3, false, false, true, false, null, null, true);
        pipe1 = new Pipe(0, scene.getSink2().x - 150 + 0 / 2
                - scene.getSink2().height / 2 - 4 + 150, scene.getSink2().y
                + scene.getSink2().height / 2 - 250 + 250, 12, 225, false,
                (int) (scene.getSink2().x + scene.getSink2().width / 2
                - (scene.getSink2().x - 150 + 0 / 2 - scene.getSink2().height / 2
                - 4) - 150), true, streamPipe1);
        pipe2 = new Pipe(0, scene.getSink2().x - 150 + 0 / 2
                - scene.getSink2().height / 2 - 4 + 150, scene.getSink2().y
                + scene.getSink2().height / 2 - 250 + 250, 10, 180, false, 30,
                false, streamPipe2);
        bigGlass = new Glass(0, scene.getSink2().x - 150 + 0 / 2
                - scene.getSink2().height / 2 - 4, scene.getSink2().y
                + scene.getSink2().height / 2 - 250, 150, 250, 0, -1,
                waterBigGlass, 0, scaleBigGlass, streamBigGlass,
                true, true, false, true, pipe1, pipe2, true);
        streamTap = new Stream(scene.getSink().x + scene.getSink().width / 2,
                70, 15, 0, water);
        streamTap2 = new Stream(bigGlass.x + bigGlass.dwidth / 2 + 60, 25, 25,
                0, water);
        tap2 = new Tap(0, bigGlass.x + bigGlass.dwidth / 2 + 60, 70, 25, 50,
                streamTap2, false, false, true);
        tap = new Tap(0, scene.getSink().x + scene.getSink().width / 2, 90, 15, 30,
                streamTap, false, true, true);
        // tap.isExisting = true;
        ball = new Ball(m1, 10, 450, 30, 30, 1000, true);
        ball2 = new Ball(0, 45, 460, 20, 20, 1000, true);
        cube = new Cube(0, 230, 450, 30, 30, 6, 1500, true);
        int size = getWidth();
        balance = new Balance(100, 3 * getWidth() / 5 + 30,
                20 + getHeight() / 2, -1, (int) (size / 2.28), (int) (size / 2.66), false);
        int yLevel = 8 * getHeight() / 10;
        int x = getWidth() / 10;
        //int size = getWidth();
        scene.addObject(new Weight(1, x, yLevel, 0, size / 53, "1", true));
        scene.addObject(new Weight(1, x, yLevel + 10, 1, size / 53, "1", true));
        x += size / 40;
        scene.addObject(new Weight(2, x, yLevel, 0, size / 40, "2", true));
        scene.addObject(new Weight(2, x, yLevel + 10, 1, size / 40, "2", true));
        x += size / 32;
        scene.addObject(new Weight(5, x, yLevel, 0, size / 32, "5", true));
        scene.addObject(new Weight(5, x, yLevel + 10, 1, size / 32, "5", true));
        x += size / 26;
        scene.addObject(new Weight(10, x, yLevel, 0, size / 26, "10", true));
        scene.addObject(new Weight(10, x, yLevel + 10, 1, size / 26, "10", true));
        x += size / 22;
        scene.addObject(new Weight(20, x, yLevel, 0, size / 23, "20", true));
        scene.addObject(new Weight(20, x, yLevel + 10, 1, size / 23, "20", true));
        scene.addObject(balance);
        scene.addObject(glass);
        scene.addObject(glass2);
        scene.addObject(glass3);
        scene.addObject(bigGlass);
        scene.addObject(ball);
        scene.addObject(ball2);
        scene.addObject(cube);
        if (tap.isExisting) {
            scene.addObject(tap);
        }
        scene.addObject(pipe1);
        scene.addObject(pipe2);
        if (tap2.isExisting) {
            scene.addObject(tap2);
        }
        timer = new Timer(TIMER_DELAY, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                paint(getGraphics());
            }
        });
        timerWater = new Timer(TIMERWATER_DELAY, new ActionListener() {

            public void setVariableStreamTap(Tap tap) {
                tap.stream.height = 0;
                if (tap.isTurnedOn) {
                    if (tap.right) {
                        tap.stream.y = tap.y + tap.height;
                        if (!scene.getSink().isEmpty) {
                            double tg = scene.getSink().glass.dwidth
                                    / (scene.getSink().glass.height * 2.0);
                            double alfa = atan(tg);
                            if (!scene.getSink().glass.outpouring()) {
                                tap.stream.height = (int) (scene.getSink().glass.y1
                                        - (tap.y + tap.height) + scene.getSink().glass.height - scene.getSink().glass.calcwaterh1orl1()
                                        * cos(alfa));
                            } else {
                                tap.stream.height = (int) (scene.getSink().glass.y1
                                        - (tap.y + tap.height));
                            }
                        }
                        if (scene.getSink().isEmpty) {
                            tap.stream.height = (int) (scene.getSink().y
                                    + scene.getSink().height / 2 - (tap.y + tap.height));
                        }
                    } else {
                        tap.stream.y = tap.y + tap.height;
                        double tg = bigGlass.dwidth / (bigGlass.height * 2.0);
                        double alfa = atan(tg);
                        tap.stream.height = (int) (bigGlass.y1 - (tap.y
                                + tap.height) + bigGlass.height
                                - bigGlass.calcwaterh1orl1() * cos(alfa));
                    }
                }
            }

            public Color mixtureColor(Color color1, double V1, Color color2, double V2) {
                int R, G, B, A;
                A = (int) ((color1.getAlpha() * V1 + color2.getAlpha() * V2) / (V1 + V2));
                R = (int) ((color1.getRed() * V1 + color2.getRed() * V2) / (V1 + V2));
                G = (int) ((color1.getGreen() * V1 + color2.getGreen() * V2) / (V1 + V2));
                B = (int) ((color1.getBlue() * V1 + color2.getBlue() * V2) / (V1 + V2));
                scene.dR = scene.dR + (((color1.getRed() * V1 + color2.getRed()
                        * V2) / (V1 + V2)) - (int) ((color1.getRed() * V1
                        + color2.getRed() * V2) / (V1 + V2)));
                scene.dG = scene.dG + (((color1.getGreen() * V1 + color2.getGreen()
                        * V2) / (V1 + V2)) - (int) ((color1.getGreen() * V1
                        + color2.getGreen() * V2) / (V1 + V2)));
                scene.dB = scene.dB + (((color1.getBlue() * V1 + color2.getBlue()
                        * V2) / (V1 + V2)) - (int) ((color1.getBlue() * V1
                        + color2.getBlue() * V2) / (V1 + V2)));
                if (scene.dR > 1) {
                    R = R + 1;
                    scene.dR = scene.dR - (int) scene.dR;
                }
                if (scene.dG > 1) {
                    G = G + 1;
                    scene.dG = scene.dG - (int) scene.dG;
                }
                if (scene.dB > 1) {
                    B = B + 1;
                    scene.dB = scene.dB - (int) scene.dB;
                }
                Color color = new Color(R, G, B, A);
                return color;
            }

            public double mixtureDensity(double ro1, double v1, double ro2, double v2) {
                double roMixture = (ro1 * v1 + ro2 * v2) / (v1 + v2);
                return roMixture;
            }

            public void volumeIncreaseByTap(Tap tap, Glass glass) {
                glass.isPrime = true;
                glass.sourceDopant = tap.stream.water;
                glass.water.volumeIncrease(70);
                glass.water.color =
                        mixtureColor(scene.getTap().stream.water.color, 70,
                        glass.water.color, glass.water.Vw);
                glass.water.ro = mixtureDensity(scene.getTap().stream.water.ro,
                        70, glass.water.ro, glass.water.Vw);
            }

            public void transvase(Sink snk, Glass sourceGlass, Glass filledGlass) {
                if (sourceGlass.gamma == 0) {
                    sourceGlass.stream.height = sourceGlass.height;
                    sourceGlass.water.volumeContraction(70);
                    if (snk.glass == sourceGlass) {
                        snk.color = sourceGlass.water.color;
                    }
                } else {
                    if (sourceGlass.gamma > 0) {
                        if (snk.glass != null) {
                            if ((sourceGlass.y2 < filledGlass.y1)
                                    && (sourceGlass.x2 > filledGlass.x1)
                                    && (sourceGlass.x2 < filledGlass.x2)) {
                                double tg = filledGlass.dwidth / (filledGlass.height * 2.0);
                                double alfa = atan(tg);
                                sourceGlass.stream.height = (int) (filledGlass.y1 - sourceGlass.y2
                                        + filledGlass.height - filledGlass.calcwaterh1orl1()
                                        * cos(alfa));
                                filledGlass.isPrime = true;
                                filledGlass.sourceDopant = sourceGlass.water;
                                sourceGlass.water.volumeContraction(70);
                                filledGlass.water.volumeIncrease(70);
                                filledGlass.water.color = mixtureColor(filledGlass.water.color,
                                        filledGlass.water.Vw, sourceGlass.water.color, 70);
                                filledGlass.water.ro = mixtureDensity(filledGlass.water.ro,
                                        filledGlass.water.Vw, sourceGlass.water.ro, 70);
                            } else {
                                sourceGlass.stream.height = (int) (snk.y
                                        + snk.height / 2 - sourceGlass.y2);
                                sourceGlass.water.volumeContraction(70);
                                snk.color = sourceGlass.water.color;
                            }
                        } else {
                            sourceGlass.stream.height = (int) (snk.y
                                    + snk.height / 2 - sourceGlass.y2);
                            sourceGlass.water.volumeContraction(70);
                            snk.color = sourceGlass.water.color;
                        }
                    } else {
                        if (snk.glass != null) {
                            if ((sourceGlass.y1 < filledGlass.y1) && (sourceGlass.x1 > filledGlass.x1)
                                    && (sourceGlass.x1 < filledGlass.x2)) {
                                double tg = filledGlass.dwidth / (filledGlass.height * 2.0);
                                double alfa = atan(tg);
                                sourceGlass.stream.height = (int) (filledGlass.y1 - sourceGlass.y1
                                        + filledGlass.height - filledGlass.calcwaterh1orl1()
                                        * cos(alfa));
                                filledGlass.isPrime = true;
                                filledGlass.sourceDopant = sourceGlass.water;
                                sourceGlass.water.volumeContraction(70);
                                filledGlass.water.volumeIncrease(70);
                                filledGlass.water.color = mixtureColor(filledGlass.water.color,
                                        filledGlass.water.Vw, sourceGlass.water.color, 70);
                                filledGlass.water.ro = mixtureDensity(filledGlass.water.ro,
                                        filledGlass.water.Vw, sourceGlass.water.ro, 70);
                            } else {
                                sourceGlass.stream.height = (int) (snk.y
                                        + snk.height / 2 - sourceGlass.y1);
                                sourceGlass.water.volumeContraction(70);
                                snk.color = sourceGlass.water.color;
                            }
                        } else {
                            sourceGlass.stream.height = (int) (snk.y
                                    + snk.height / 2 - sourceGlass.y1);
                            sourceGlass.water.volumeContraction(70);
                            snk.color = sourceGlass.water.color;
                        }
                    }
                }
            }

            @Override
            public void actionPerformed(ActionEvent e) {
//                if (scene.getSink2().glass != null) {
//                    //if (scene.getSink2().glass.isPrime) {
//                    System.out.println(scene.getSink2().glass.water.Vw + " ");
//                    //}
//                }
                setVariableStreamTap(tap);
                setVariableStreamTap(tap2);
                if (tap2.isTurnedOn) {
                    volumeIncreaseByTap(tap2, bigGlass);
                }
                if (tap.isTurnedOn) {
                    if (!scene.getSink().isEmpty) {
                        volumeIncreaseByTap(tap, scene.getSink().glass);
                    } else {
                        scene.getSink().color = scene.getTap().stream.water.color;
                    }
                }
                if (streamPipe2.isStream) {
                    pipe2.stream.height = pipe2.height + pipe2.width / 2;
                    streamPipe2.paintStreamPipe(getGraphics(), pipe2);
                    bigGlass.water.volumeContraction(60);
                    scene.getSink2().color = streamPipe2.water.color;
                } else {
                    if (!streamPipe1.isStream) {
                        scene.getSink2().color = Color.white;
                    }
                }
                if (bigGlass.outpouring()) {
                    pipe1.isTurnedOn = true;
                }
                if (streamPipe1.isStream) {
                    if (scene.getSink2().isEmpty) {
                        pipe1.stream.height = pipe1.height + pipe1.width / 2;
                        scene.getSink2().color = streamPipe1.water.color;
                        bigGlass.water.volumeContraction(70);
                    } else {
                        if (!scene.getSink2().glass.outpouring()) {
                            pipe1.stream.height = (int) (pipe1.height
                                    - scene.getSink2().glass.calcwaterh1orl1()
                                    + pipe1.width / 2);
                        } else {
                            pipe1.stream.height = (int) (pipe1.height
                                    - scene.getSink2().glass.height + pipe1.width / 2);
                        }
                        scene.getSink2().color = Color.white;
                        scene.getSink2().glass.isPrime = true;
                        scene.getSink2().glass.sourceDopant = bigGlass.water;
                        scene.getSink2().glass.water.volumeIncrease(70);
                        bigGlass.water.volumeContraction(70);
                        scene.getSink2().glass.water.color = mixtureColor(
                                bigGlass.water.color, 70,
                                scene.getSink2().glass.water.color,
                                scene.getSink2().glass.water.Vw);
                        scene.getSink2().glass.water.ro = mixtureDensity(
                                bigGlass.water.ro, 70,
                                scene.getSink2().glass.water.ro,
                                scene.getSink2().glass.water.Vw);
                    }
                    streamPipe1.paintStreamPipe(getGraphics(), pipe1);
                    if (!scene.getSink2().isEmpty) {
                        double xs1 = scene.getSink2().glass.x
                                + scene.getSink2().glass.width / 2;
                        double ys1 = scene.getSink2().glass.y
                                + scene.getSink2().glass.height;
                        rePaintFront(getGraphics(), scene.getSink2().glass,
                                scene.getSink2().glass.x, scene.getSink2().glass.y,
                                xs1, ys1, (int) scene.getSink2().glass.scale.length,
                                scene.getSink2().glass.cx, scene.getSink2().glass.cy,
                                scene.getSink2().glass.gamma);
                        if (scene.getSink2().glass.outpouring()) {
                            scene.getSink2().color = scene.getSink2().glass.water.color;
                        }
                    }
                } else {
                    if (!streamPipe2.isStream) {
                        scene.getSink2().color = Color.white;
                    }
                }
                if (stream2.isStream == true) {
                    //
                    if (scene.getSink().glass != null) {
                        if (scene.getSink().glass.outpouring()
                                || scene.getBaulk().isVisible
                                || scene.getBaulk2().isVisible
                                || scene.getBaulk3().isVisible) {
                            transvase(scene.getSink(), glass2, scene.getSink().glass);
                        }
                    } else {
                        if (scene.getBaulk().isVisible
                                || scene.getBaulk2().isVisible
                                || scene.getBaulk3().isVisible) {
                            transvase(scene.getSink(), glass2, scene.getSink().glass);
                        }
                    }
                    if (scene.getSink2().glass != null) {
                        if (scene.getSink2().glass.outpouring()
                                || scene.getBaulk5().isVisible
                                || scene.getBaulk4().isVisible) {
                            transvase(scene.getSink2(), glass2, scene.getSink2().glass);
                        }
                    } else {
                        if (scene.getBaulk5().isVisible
                                || scene.getBaulk4().isVisible) {
                            transvase(scene.getSink2(), glass2, scene.getSink2().glass);
                        }
                    }
                } else {
                    if (stream3.isStream == true) {
                        stream3.paintStreamGlass(getGraphics(), glass3.gamma, glass3.x1,
                                glass3.x2, glass3.y1, glass3.y2);
                        //
                        if (scene.getSink().glass != null) {
                            if (scene.getSink().glass.outpouring()
                                    || scene.getBaulk().isVisible || scene.getBaulk2().isVisible
                                    || scene.getBaulk3().isVisible) {
                                transvase(scene.getSink(), glass3, scene.getSink().glass);
                            }
                        } else {
                            if (scene.getBaulk().isVisible || scene.getBaulk2().isVisible
                                    || scene.getBaulk3().isVisible) {
                                transvase(scene.getSink(), glass3, scene.getSink().glass);
                            }
                        }
                        if (scene.getSink2().glass != null) {
                            if (scene.getSink2().glass.outpouring()
                                    || scene.getBaulk5().isVisible
                                    || scene.getBaulk4().isVisible) {
                                transvase(scene.getSink2(), glass3, scene.getSink2().glass);
                            }
                        } else {
                            if (scene.getBaulk5().isVisible || scene.getBaulk4().isVisible) {
                                transvase(scene.getSink2(), glass3, scene.getSink2().glass);
                            }
                        }
                    } else {
                        if (streamBigGlass.isStream == true) {
                            streamBigGlass.paintStreamGlass(getGraphics(),
                                    bigGlass.gamma, bigGlass.x1,
                                    bigGlass.x2, bigGlass.y1, bigGlass.y2);
                            //!!!!!
                            //transvase(scene.getSink2(), bigGlass, scene.getSink2().glass);
                        } else {
                            if (stream.isStream == true) {
                                stream.paintStreamGlass(getGraphics(), glass.gamma, glass.x1,
                                        glass.x2, glass.y1, glass.y2);
                                //
                                if (scene.getSink().glass != null) {
                                    if (scene.getSink().glass.outpouring()
                                            || scene.getBaulk().isVisible
                                            || scene.getBaulk2().isVisible
                                            || scene.getBaulk3().isVisible) {
                                        transvase(scene.getSink(), glass, scene.getSink().glass);
                                    }
                                } else {
                                    if (scene.getBaulk().isVisible
                                            || scene.getBaulk2().isVisible
                                            || scene.getBaulk3().isVisible) {
                                        transvase(scene.getSink(), glass, scene.getSink().glass);
                                    }
                                }
                                if (scene.getSink2().glass != null) {
                                    if (scene.getSink2().glass.outpouring()
                                            || scene.getBaulk5().isVisible
                                            || scene.getBaulk4().isVisible) {
                                        transvase(scene.getSink2(), glass, scene.getSink2().glass);
                                    }
                                } else {
                                    if (scene.getBaulk5().isVisible
                                            || scene.getBaulk4().isVisible) {
                                        transvase(scene.getSink2(), glass, scene.getSink2().glass);
                                    }
                                }
                            } else {
                                if (!tap.isTurnedOn) {
                                    scene.getSink().color = Color.WHITE;
                                }
                            }
                        }
                    }
                }
                glass.isPrime = false;
                glass2.isPrime = false;
                glass3.isPrime = false;
                glass.sourceDopant = null;
                glass2.sourceDopant = null;
                glass3.sourceDopant = null;
            }
        });
    }

    public void rePaintFront(Graphics gr, Glass glass, double x, double y, double xs1, double ys1, int length, double cx, double cy, double gamma) {
        double tg = glass.dwidth / (glass.height * 2.0);
        double alfa = atan(tg);
        if (glass.existScale) {
            glass.scale.paintScale(gr, x, y, xs1, ys1, length, cx, cy, gamma);
        }
        if (glass.existLabels) {
            glass.scale.paintLabel(gr, x, y, xs1, ys1, length, cx, cy, gamma);
        }
        gr.drawArc((int) (glass.x), (int) (glass.y - 5), glass.width, 10, 180, 180);
        if (!glass.outpouring()) {
            gr.drawArc((int) (glass.x + glass.dwidth / 2 - glass.calcwaterh1orl1() * sin(alfa)),
                    (int) (glass.y + glass.height - glass.calcwaterh1orl1() * cos(alfa) - 5),
                    (int) (glass.width - 2 * sin(alfa) * (glass.height / cos(alfa)
                    - glass.calcwaterh1orl1())), 10, 180, 180);
        }
    }

    public void setOutsideGlassForMatObj(MaterialObject matObj) {
        boolean n = false;
        if (matObj.outside == null) {
            n = true;
            if (matObj.isOverGlass(glass)) {
                if (matObj.y + matObj.height < glass.y) {
                    matObj.outside = glass;
                } else {
                    matObj.outside = null;
                }
            } else {
                if (matObj.isOverGlass(glass2)) {
                    if (matObj.y + matObj.height < glass2.y) {
                        matObj.outside = glass2;
                    } else {
                        matObj.outside = null;
                    }
                } else {
                    if (matObj.isOverGlass(glass3)) {
                        if (matObj.y + matObj.height < glass3.y) {
                            matObj.outside = glass3;
                        } else {
                            matObj.outside = null;
                        }
                    } else {
                        if (matObj.isOverGlass(bigGlass)) {
                            if (matObj.y + matObj.height < bigGlass.y) {
                                matObj.outside = bigGlass;
                            } else {
                                matObj.outside = null;
                            }
                        } else {
                            matObj.outside = null;
                        }
                    }
                }
            }
        } else {
            if (matObj.isOverGlass(glass)) {
                matObj.outside = glass;
            } else {
                if (matObj.isOverGlass(glass2)) {
                    matObj.outside = glass2;
                } else {
                    if (matObj.isOverGlass(glass3)) {
                        matObj.outside = glass3;
                    } else {
                        if (matObj.isOverGlass(bigGlass)) {
                            matObj.outside = bigGlass;
                        } else {
                            matObj.outside = null;
                        }
                    }
                }
            }
        }
        if (n && (scene.getSink().glass != matObj.outside
                && scene.getSink2().glass != matObj.outside)
                && (matObj.outside != bigGlass)) {
            matObj.outside = null;
        }
        if (matObj.outside != null) {
            if (!matObj.outside.isFill) {
                matObj.outside = null;
            }
        }
    }

    public void divingMatObj(MaterialObject matObj, Glass glass) {
        double g = 0;
        if (matObj.isDiving) {
            g = 100;
        }
        double dt = 0.1;
        double tg = glass.dwidth / (glass.height * 2.0);
        double alfa = atan(tg);
        matObj.hDeep = matObj.y + matObj.height - (glass.y + glass.height
                - glass.calcwaterh1orl1() * cos(alfa));
        if (matObj.hDeep < 0) {
            matObj.hDeep = 0;
        } else {
            if (matObj.hDeep > matObj.height) {
                matObj.hDeep = matObj.height;
            }
        }
        matObj.a = matObj.getAccelerationY();
        matObj.v = matObj.v + matObj.a * dt;
        if (matObj.y + matObj.v * dt + matObj.a * dt * dt / 2 + matObj.height
                >= matObj.outside.y + matObj.outside.height) {
            // минус 1 потому что иначе в методе matObjMoveTo попадает в ситуацию 
            //когда мешает стенка и тело не передвигается, погрешность 
            //вычисления уравнения прямой и расстояния до нее
            scene.matObjMoveTo(matObj, matObj.x, matObj.outside.y
                    + matObj.outside.height - matObj.height - 1);
            matObj.v = 0;
            matObj.a = 0;
            matObj.isDiving = false;
        }
        scene.matObjMoveTo(matObj, matObj.x, matObj.y + matObj.v * dt + matObj.a * dt * dt / 2);
        //System.out.println("a=" + matObj.a + "V=" + matObj.v + "H=" + matObj.hDeep);
        if (!matObj.isInList) {
            matObj.isInList = true;
            glass.innerObjects.add(matObj);
        }
    }

    public void improvementDivingMatObj(MaterialObject matObj) {
        if (matObj.outside != null) {
            matObj.outside.innerObjects.remove(matObj);
            matObj.isInList = false;
        }
        setOutsideGlassForMatObj(matObj);
        //System.out.println(ball.outside);
        if (matObj.outside != null) {
            if (matObj.outside.isFill) {
                divingMatObj(matObj, matObj.outside);
                double xs1 = matObj.outside.x + matObj.outside.width / 2;
                double ys1 = matObj.outside.y + matObj.outside.height;
                //if ((ball.v!=0) || (scene.mouseClicked)) {
                rePaintFront(getGraphics(), matObj.outside, matObj.outside.x,
                        matObj.outside.y, xs1, ys1, (int) matObj.outside.scale.length,
                        matObj.outside.cx, matObj.outside.cy, matObj.outside.gamma);
            }
        }
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        scene.paint(buffer.getGraphics());
        g.drawImage(buffer, 0, 0, this);
        improvementDivingMatObj(ball);
        improvementDivingMatObj(ball2);
        improvementDivingMatObj(cube);
        if (scene.getSink().glass != null) {
            if (stream.isStream || stream2.isStream || streamTap.isStream || stream3.isStream) {
                double xs1 = scene.getSink().glass.x + scene.getSink().glass.width / 2;
                double ys1 = scene.getSink().glass.y + scene.getSink().glass.height;
                rePaintFront(getGraphics(), scene.getSink().glass,
                        scene.getSink().glass.x, scene.getSink().glass.y, xs1,
                        ys1, (int) scene.getSink().glass.scale.length,
                        scene.getSink().glass.cx, scene.getSink().glass.cy,
                        scene.getSink().glass.gamma);
            }
        }

        if (streamTap2.isStream) {
            double xs1 = bigGlass.x + bigGlass.width / 2;
            double ys1 = bigGlass.y + bigGlass.height;
            rePaintFront(getGraphics(), bigGlass, bigGlass.x, bigGlass.y, xs1,
                    ys1, (int) bigGlass.scale.length, bigGlass.cx, bigGlass.cy,
                    bigGlass.gamma);
        }
    }

    @Override
    public boolean mouseDown(Event evt, int x, int y) {
        return scene.mouseDown(evt, x, y, getGraphics());
    }

    @Override
    public boolean mouseUp(Event evt, int x, int y) {
        return scene.mouseUp(evt, x, y);
    }

    @Override
    public boolean mouseDrag(Event evt, int x, int y) {
        return scene.mouseDrag(evt, x, y, getGraphics());
    }

    @Override
    public void start() {
        timer.start();
        timerWater.start();
    }

    @Override
    public void stop() {
        timer.stop();
        timerWater.stop();

    }
}
