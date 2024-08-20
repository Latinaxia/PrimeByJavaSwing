package test.Prime;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PrimeGraph {
    private JFrame frame=new JFrame("PrimeGraph");

    //定义画图区的宽和高
    private static final int AREA_WIDTH=700;
    private static final int AREA_HEIGHT=600;

    //点的半径
    private final int BALL_SIZE=16;

    //点的坐标
    private int ballX=-1;
    private int ballY=-1;
    //存储所有点
    private static List<Point> points=new ArrayList<>();
    //所有点的次序，从0开始
    private static int[] pointsstr=new int[100];
    private int i=0;//i表示点的次序，因为for循环最后i+1但未打印，因此最后i也可以表示点的个数，即邻接矩阵的边长

    //存储所有连线的点，两个一对
    private static Point[] twopoints =new Point[100];
    public static int[] twopointsstr=new int[100];//对应twopoints，第几个点和第几个点

    private static int[]we=new int[]{100,100,100,100,100,100,100,100,100,100,100,100,100,100,100};//临时存储边的权值的we数组
    private int w,ew=0;//w接收输入的边的权值，ew作为we数组的下标
    private int m,n;

    //画点
    private void drawPoints(Graphics g){
        int i=0;
        g.setColor(Color.RED);
        for(Point point:points){
            g.fillOval(point.x,point.y,BALL_SIZE,BALL_SIZE);
            g.drawString(String.valueOf(i),point.x,point.y);
            i++;
        }
    }
    //显示所有点
    private void showPoints(Graphics g){
        int i=0;
        for(Point point:points){
            g.fillOval(point.x,point.y,BALL_SIZE,BALL_SIZE);
            g.drawString(String.valueOf(pointsstr[i]),point.x,point.y);
            System.out.printf("点%d坐标%f %f",i,point.getX(),point.getY());
            System.out.printf("\n");
            i++;
        }
    }
    //画出所有的线
    private void drawLines(Graphics g){
        int j = 0,i=0;
        for (; j < flag; j=j+2) {
            g.setColor(Color.BLUE);
            g.drawLine((int) twopoints[j].getX(), (int) twopoints[j].getY()+5, (int) twopoints[j+1].getX(), (int) twopoints[j+1].getY()+5);
            if(we[i]!=100){
                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(we[i]),(int) (twopoints[j].getX()+twopoints[j+1].getX())/2,  (int) (twopoints[j].getY()+twopoints[j+1].getY())/2);
                i++;
            }
//            g.drawString(String.valueOf(w),(int) (twopoints[j].getX()+twopoints[j+1].getX())/2,  (int) (twopoints[j].getY()+twopoints[j+1].getY())/2);
        }
        System.out.printf("这是点 %d 和点 %d之间的连线",twopointsstr[j-2],twopointsstr[j-1]);
    }

    private class DrawCanvas extends Canvas{
        @Override
        public void paint(Graphics g) {
            //画线
            if(k==1){
                drawLines(g);
                System.out.println("画线成功！");
            }
            //画点
            drawPoints(g);
        }
    }
    //绘制最小生成树
    private class ShowCanvas extends Canvas{
        @Override
        public void paint(Graphics g) {
                //根据primepoint数组中的下标找到点，依次连线
            if(kkk==1){
                g.setColor(Color.BLUE);
                for (int i = 0; i < primepoint.length; i++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    g.fillOval((int) points.get(primepoint[i]).getX(), (int) points.get(primepoint[i]).getY(),BALL_SIZE,BALL_SIZE);
                    if(i%2==0){
                        g.drawLine((int) points.get(primepoint[i]).getX(), (int) points.get(primepoint[i]).getY()+5, (int) points.get(primepoint[i+1]).getX(), (int) points.get(primepoint[i+1]).getY()+5);
                    }
                }
            }
            }
    }
    DrawCanvas drawArea=new DrawCanvas();
    ShowCanvas showArea= new ShowCanvas();

    //按压鼠标左键画点
    //鼠标右键按压第一个点，再按压另一个点，然后点击按钮，在这两点之间画线
    private int flag=0;
    private int k=-1;//是否要画线的“开关”
    private class DrawListener extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e) {
            //按压左键在左键出生成一个点
            if(e.getButton()==MouseEvent.BUTTON1){
                //记录左键坐标
                ballX=e.getX();
                ballY=e.getY();
                System.out.println("X:"+ballX);
                System.out.println("Y:"+ballY);
                //将该点存入List
                Point point=e.getPoint();
                points.add(point);
                pointsstr[i]=i;
                drawArea.repaint();
                i++;
            }
            //按压两点，记录两点的坐标到twopoint数组中，然后点击按钮，在两点之间画线
            if (e.getButton()==MouseEvent.BUTTON3) {
                Point p=e.getPoint();
                //遍历已有点的集合
                m=0;n=0;
                for(Point point:points){
                    if(Math.abs(p.getX()-point.getX())<=16&&Math.abs(p.getY()-point.getY())<=16){
                        //确定是哪两个个点要连线
                        if(flag%2==0){
                            //这对点的第一个
                            twopoints[flag]=point;
                            twopointsstr[flag]=pointsstr[m];
                            System.out.println(point+"点"+m);
                            flag++;
                        } else{
                            //这对点的第二个
                            twopoints[flag]=point;
                            twopointsstr[flag]=pointsstr[n];
                            System.out.println(point+"点"+n);
                            flag++;
                        }
                    }
                    m++;
                    n++;
                }
            }
        }
    }

    //Draw Line按钮的监听器，按下该按钮，遍历twopoint数组，取出第一个点和第二个点画线
    //按下按钮是弹出弹窗输入该边的权值
    private class DrawLineListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            k=1;
            String input=JOptionPane.showInputDialog(frame,"请输入这条边的权值");
            w= Integer.parseInt(input);
            System.out.println("输入的权值是"+w);
            //将边录入we数组
            we[ew++]=w;
            drawArea.repaint();
            showArea.repaint();
        }
    }

    //PrimeStart按钮的监听器
    private static int[] primepoint;//存储最小生成树的边，以两个点为一组，在两点之间画边，存储两点的下标
    private static int kkk=-1;
    private class PrimeStartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            drawArea.repaint();
            showArea.repaint();
            System.out.println("Prime Start");
            kkk=1;
            //构建邻接矩阵
            int[][] weight=new int[i][i];//邻接矩阵
            //初始化邻接矩阵
            for (int j = 0; j < i; j++) {
                for (int l = 0; l < i; l++) {
                    weight[j][l]=100;
                }
            }
            //传入边的数据
            for (int j = 0,z=0; j < flag; j=j+2,z=z+1) {
                weight[twopointsstr[j]][twopointsstr[j+1]]=we[z];
                weight[twopointsstr[j+1]][twopointsstr[j]]=we[z];
            }
            System.out.println(Arrays.deepToString(weight));
            //构建点的char组data
            char[] data =new char[i];
            for (int j = 0; j < i; j++) {
                data[j]= (char) pointsstr[j];
            }
            //创建带权无向图
            MGraph graph=new MGraph(i);
            //创建最小生成树对象
            CreatMinTree minTree=new CreatMinTree();
            minTree.creatGraph(graph,i,data,weight);
            //输出带权无向图
            minTree.showGraph(graph);
            //prime算法实现最小生成树
            primepoint=minTree.prime(graph,0);//从第0个点开始创建最小生成树
            System.out.println(Arrays.toString(primepoint));
        }
    }

    //可以绘图的
    public void init(){
        JFrame frame=new JFrame();
        frame.setBounds(200,150,2*AREA_WIDTH,AREA_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //设置背景颜色为白色
        drawArea.setBackground(Color.YELLOW);
        showArea.setBackground(Color.WHITE);

        //画图的监听器
        DrawListener listener1=new DrawListener();
        //为Frame和drawArea注册监听器
        frame.addMouseListener(listener1);
        drawArea.addMouseListener(listener1);

        drawArea.setPreferredSize(new Dimension(AREA_WIDTH,AREA_HEIGHT));
        showArea.setPreferredSize(new Dimension(AREA_WIDTH,AREA_HEIGHT));

        JPanel drawPanel=new JPanel();
        JPanel showPanel=new JPanel();
        drawPanel.add(drawArea);
        showPanel.add(showArea);

        JButton button=new JButton("Prime Start");
        button.addActionListener(new PrimeStartListener());

        JButton button1=new JButton("Draw Line");
        button1.addActionListener(new DrawLineListener());
        frame.add(drawPanel,BorderLayout.WEST);
        frame.add(showPanel,BorderLayout.EAST);
        frame.add(button,BorderLayout.SOUTH);
        frame.add(button1,BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);
    }

    //固定图的
    //点集
    public static List<Point> egpoints=new ArrayList<>();
    private void drawegPoints(Graphics g)//画样例图的点
    {
        //样例图点的集合
        egpoints.add(new Point(310,86));
        egpoints.add(new Point(78,229));
        egpoints.add(new Point(295,288));
        egpoints.add(new Point(511,227));
        egpoints.add(new Point(189,441));
        egpoints.add(new Point(430,448));
        g.setColor(Color.RED);
        int i=0;
        g.setColor(Color.RED);
        for(Point point:egpoints){
            g.fillOval(point.x,point.y,BALL_SIZE,BALL_SIZE);
            g.drawString(String.valueOf(i),point.x,point.y);
            i++;
        }
    }
    private void drawegLines(Graphics g)//画样例图的线
    {
        g.setColor(Color.BLUE);
        g.drawLine((int) egpoints.get(0).getX(), (int) egpoints.get(0).getY()+5, (int) egpoints.get(1).getX(), (int) egpoints.get(1).getY()+5);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(6),(int) (egpoints.get(0).getX()+egpoints.get(1).getX())/2,  (int) (egpoints.get(0).getY()+egpoints.get(1).getY())/2);

        g.setColor(Color.BLUE);
        g.drawLine((int) egpoints.get(0).getX(), (int) egpoints.get(0).getY()+5, (int) egpoints.get(2).getX(), (int) egpoints.get(2).getY()+5);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(1),(int) (egpoints.get(0).getX()+egpoints.get(2).getX())/2,  (int) (egpoints.get(0).getY()+egpoints.get(2).getY())/2);

        g.setColor(Color.BLUE);
        g.drawLine((int) egpoints.get(0).getX(), (int) egpoints.get(0).getY()+5, (int) egpoints.get(3).getX(), (int) egpoints.get(3).getY()+5);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(5),(int) (egpoints.get(0).getX()+egpoints.get(3).getX())/2,  (int) (egpoints.get(0).getY()+egpoints.get(3).getY())/2);

        g.setColor(Color.BLUE);
        g.drawLine((int) egpoints.get(2).getX(), (int) egpoints.get(2).getY()+5, (int) egpoints.get(1).getX(), (int) egpoints.get(1).getY()+5);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(5),(int) (egpoints.get(2).getX()+egpoints.get(1).getX())/2,  (int) (egpoints.get(2).getY()+egpoints.get(1).getY())/2);

        g.setColor(Color.BLUE);
        g.drawLine((int) egpoints.get(2).getX(), (int) egpoints.get(2).getY()+5, (int) egpoints.get(3).getX(), (int) egpoints.get(3).getY()+5);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(5),(int) (egpoints.get(2).getX()+egpoints.get(3).getX())/2,  (int) (egpoints.get(2).getY()+egpoints.get(3).getY())/2);

        g.setColor(Color.BLUE);
        g.drawLine((int) egpoints.get(2).getX(), (int) egpoints.get(2).getY()+5, (int) egpoints.get(4).getX(), (int) egpoints.get(4).getY()+5);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(6),(int) (egpoints.get(2).getX()+egpoints.get(4).getX())/2,  (int) (egpoints.get(2).getY()+egpoints.get(4).getY())/2);

        g.setColor(Color.BLUE);
        g.drawLine((int) egpoints.get(2).getX(), (int) egpoints.get(2).getY()+5, (int) egpoints.get(5).getX(), (int) egpoints.get(5).getY()+5);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(4),(int) (egpoints.get(2).getX()+egpoints.get(5).getX())/2,  (int) (egpoints.get(2).getY()+egpoints.get(5).getY())/2);

        g.setColor(Color.BLUE);
        g.drawLine((int) egpoints.get(1).getX(), (int) egpoints.get(1).getY()+5, (int) egpoints.get(4).getX(), (int) egpoints.get(4).getY()+5);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(3),(int) (egpoints.get(1).getX()+egpoints.get(4).getX())/2,  (int) (egpoints.get(1).getY()+egpoints.get(4).getY())/2);

        g.setColor(Color.BLUE);
        g.drawLine((int) egpoints.get(3).getX(), (int) egpoints.get(3).getY()+5, (int) egpoints.get(5).getX(), (int) egpoints.get(5).getY()+5);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(2),(int) (egpoints.get(3).getX()+egpoints.get(5).getX())/2,  (int) (egpoints.get(3).getY()+egpoints.get(5).getY())/2);

        g.setColor(Color.BLUE);
        g.drawLine((int) egpoints.get(4).getX(), (int) egpoints.get(4).getY()+5, (int) egpoints.get(5).getX(), (int) egpoints.get(5).getY()+5);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(6),(int) (egpoints.get(4).getX()+egpoints.get(5).getX())/2,  (int) (egpoints.get(4).getY()+egpoints.get(5).getY())/2);

    }
    private void drawegMinTree(Graphics g) throws InterruptedException//画样例的最小生成树
     {
        g.setColor(Color.BLUE);
        g.fillOval((int) egpoints.get(0).getX(), (int) egpoints.get(0).getY(),BALL_SIZE,BALL_SIZE);
         Thread.sleep(1000);
        g.drawString(String.valueOf(1),(int) (egpoints.get(0).getX()+egpoints.get(2).getX())/2,  (int) (egpoints.get(0).getY()+egpoints.get(2).getY())/2);
        g.drawLine((int) egpoints.get(0).getX(), (int) egpoints.get(0).getY()+5, (int) egpoints.get(2).getX(), (int) egpoints.get(2).getY()+5);
        Thread.sleep(1000);

        g.fillOval((int) egpoints.get(2).getX(), (int) egpoints.get(2).getY(),BALL_SIZE,BALL_SIZE);
         Thread.sleep(1000);
        g.drawString(String.valueOf(4),(int) (egpoints.get(2).getX()+egpoints.get(5).getX())/2,  (int) (egpoints.get(2).getY()+egpoints.get(5).getY())/2);
        g.drawLine((int) egpoints.get(2).getX(), (int) egpoints.get(2).getY()+5, (int) egpoints.get(5).getX(), (int) egpoints.get(5).getY()+5);
        Thread.sleep(1000);

        g.fillOval((int) egpoints.get(5).getX(), (int) egpoints.get(5).getY(),BALL_SIZE,BALL_SIZE);
         Thread.sleep(1000);
        g.drawString(String.valueOf(2),(int) (egpoints.get(5).getX()+egpoints.get(3).getX())/2,  (int) (egpoints.get(5).getY()+egpoints.get(3).getY())/2);
        g.drawLine((int) egpoints.get(5).getX(), (int) egpoints.get(5).getY()+5, (int) egpoints.get(3).getX(), (int) egpoints.get(3).getY()+5);
        Thread.sleep(1000);

        g.fillOval((int) egpoints.get(3).getX(), (int) egpoints.get(3).getY(),BALL_SIZE,BALL_SIZE);
         Thread.sleep(1000);
        g.drawString(String.valueOf(5),(int) (egpoints.get(2).getX()+egpoints.get(1).getX())/2,  (int) (egpoints.get(2).getY()+egpoints.get(1).getY())/2);
        g.drawLine((int) egpoints.get(2).getX(), (int) egpoints.get(2).getY()+5, (int) egpoints.get(1).getX(), (int) egpoints.get(1).getY()+5);
        Thread.sleep(1000);

        g.fillOval((int) egpoints.get(1).getX(), (int) egpoints.get(1).getY(),BALL_SIZE,BALL_SIZE);
         Thread.sleep(1000);
        g.drawString(String.valueOf(3),(int) (egpoints.get(1).getX()+egpoints.get(4).getX())/2,  (int) (egpoints.get(1).getY()+egpoints.get(4).getY())/2);
        g.drawLine((int) egpoints.get(1).getX(), (int) egpoints.get(1).getY()+5, (int) egpoints.get(4).getX(), (int) egpoints.get(4).getY()+5);
        Thread.sleep(1000);

        g.fillOval((int) egpoints.get(4).getX(), (int) egpoints.get(4).getY(),BALL_SIZE,BALL_SIZE);
    }

    private class drawegarea extends Canvas{
        @Override
        public void paint(Graphics g) {
            drawegPoints(g);
            drawegLines(g);
        }
    }

    private static int xxx=0;
    private class showegarea extends Canvas{
        @Override
        public void paint(Graphics g) {
            if(xxx==1){
                try {
                    drawegMinTree(g);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    drawegarea d=new drawegarea();
    showegarea s=new showegarea();

    private class PrimeStartListener2 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            d.repaint();
            s.repaint();
            //样例图的邻接矩阵
            int[][] weight=new int[][]{
                    {100, 6, 1, 5, 100, 100},
                    {6, 100, 5, 100, 3, 100},
                    {1, 5, 100, 5, 6, 4},
                    {5, 100, 5, 100, 100, 2},
                    {100, 3, 6, 100, 100, 6},
                    {100, 100, 4, 2, 6, 100}, };
            xxx=1;
            System.out.printf("""
                    边<0,2>权值为:1
                    边<2,5>权值为:4
                    边<5,3>权值为:2
                    边<2,1>权值为:5
                    边<1,4>权值为:3
                    """);
        }
    }

    public void init2(){
        JFrame frame=new JFrame();
        frame.setBounds(200,150,2*AREA_WIDTH,AREA_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //设置背景颜色为白色
        d.setBackground(Color.YELLOW);
        s.setBackground(Color.WHITE);

        d.setPreferredSize(new Dimension(AREA_WIDTH,AREA_HEIGHT));
        s.setPreferredSize(new Dimension(AREA_WIDTH,AREA_HEIGHT));

        JPanel drawPanel=new JPanel();
        JPanel showPanel=new JPanel();
        drawPanel.add(d);
        showPanel.add(s);

        JButton button=new JButton("Prime Start");
        button.addActionListener(new PrimeStartListener2());
        frame.add(drawPanel,BorderLayout.WEST);
        frame.add(showPanel,BorderLayout.EAST);
        frame.add(button,BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String[] args) throws IOException {
        JFrame frame=new JFrame();
        frame.setBounds(200,150,2*AREA_WIDTH,AREA_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel=new JPanel();
        panel.setLayout(new GridLayout(1,2));

        JButton button=new JButton("手动绘图");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PrimeGraph().init();
            }
        });

        JButton button1=new JButton("使用样例图");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PrimeGraph().init2();
            }
        });

        panel.add(button);
        panel.add(button1);
        panel.setBounds(frame.getWidth()-panel.getWidth()/2,(frame.getHeight()-panel.getHeight())/2,AREA_WIDTH,AREA_HEIGHT);

        BackGround backGround=new BackGround();
        frame.add(backGround);
        frame.add(panel,BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }
}

class BackGround extends JPanel{
    private BufferedImage backgroundimage;

    public BackGround() throws IOException {
        backgroundimage= ImageIO.read(new File("C:\\p2.jpg"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundimage,0,0,null);
    }
}
