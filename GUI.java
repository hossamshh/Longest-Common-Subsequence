package bioProj01;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.border.TitledBorder;


public class GUI extends JFrame{
    private JTextField text1 = new JTextField("BDCABA",25);
    private JTextField text2 = new JTextField("ABCBDAB",25);
    private JButton disp = new JButton("Run");
    private JTextArea txtArea = new JTextArea();
    
    private Container c = this.getContentPane();
    
    private JPanel container = new JPanel();
    private JPanel data = new JPanel();
    private JPanel visual = new JPanel();
    private JScrollPane scVisual = new JScrollPane(visual);
    private JPanel pnlBottom = new JPanel();
    
    private BufferedImage arrowLeft, arrowUp, arrowDig, arrowDb;
    
    private ArrayList<String> result = new ArrayList<String>();
  
    public GUI() {
        init();
    }
    
    private myPanel[][] longestCommonSubsequence(char[] x, char[] y, int m, int n){
    	JLabel [][] temp = new JLabel[m+2][n+2];
    	myPanel[][] pnls = new myPanel[m+2][n+2];
    	
    	int C[][] = new int[m+1][n+1];
        
        temp[0][0] = new JLabel("");
        pnls[0][0] = new myPanel(temp[0][0], null, ' ');
        
        temp[0][1] = new JLabel("");
        pnls[0][1] = new myPanel(temp[0][1], null, ' ');
        
        temp[1][0] = new JLabel("");
        pnls[1][0] = new myPanel(temp[1][0], null, ' ');
        
        for(int i=2; i<n+2; i++) {
        	temp[0][i] = new JLabel(y[i-2]+"");
            pnls[0][i] = new myPanel(temp[0][i], null, ' ');
        }
        	
        
        for(int i=2; i<m+2; i++) {
        	temp[i][0] = new JLabel(x[i-2]+"");
            pnls[i][0] = new myPanel(temp[i][0], null, ' ');
        }
        	
        
        for (int i=1; i<m+2; i++) { 
            for (int j=1; j<n+2; j++) {
                if (i == 1 || j == 1) {
                	C[i-1][j-1] = 0;
                	temp[i][j] = new JLabel(C[i-1][j-1]+"");
                    pnls[i][j] = new myPanel(temp[i][j], null, ' ');
                }     
                else if (x[i-2] == y[j-2]) {
                	C[i-1][j-1] = C[i-2][j-2] + 1;
                	temp[i][j] = new JLabel(C[i-1][j-1]+"");
                    pnls[i][j] = new myPanel(temp[i][j], arrowDig, 'd');
                }
                else {
                	if(C[i-2][j-1] > C[i-1][j-2]){
                		C[i-1][j-1] = C[i-2][j-1];
                		temp[i][j] = new JLabel(C[i-1][j-1]+"");
                        pnls[i][j] = new myPanel(temp[i][j], arrowLeft, 'l');
                	}
                	else if(C[i-2][j-1] < C[i-1][j-2]) {
                		C[i-1][j-1] = C[i-1][j-2];
                		temp[i][j] = new JLabel(C[i-1][j-1]+"");
                        pnls[i][j] = new myPanel(temp[i][j], arrowUp, 'u');
                	}
                	else {
                		C[i-1][j-1] = C[i-2][j-1];
                		temp[i][j] = new JLabel(C[i-1][j-1]+"");
                        pnls[i][j] = new myPanel(temp[i][j], arrowDb, 'b');
                	}
                }
                     
            } 
         } 
        
        for(int i=0; i<m+1; i++) {
        	for(int j=0; j<n+1; j++) {
        		System.out.print(C[i][j]);
        	}
        	System.out.println();
        }
        
        return pnls;
    }

    private void traceback(myPanel[][] pnls, int m, int n, String s) {
    	if(m < 2 && n < 2) {
    		result.add(s);
    		return;
    	}
    	if(pnls[m][n].dis == 'd') {
    		pnls[m-1][n-1].setBackground(Color.cyan);
    		s = s.concat(pnls[m][0].lbl.getText());
    		System.out.println(s);
    		traceback(pnls, m-1, n-1, s);
    	}
    	else if(pnls[m][n].dis == 'l') {
    		pnls[m-1][n].setBackground(Color.cyan);
    		traceback(pnls, m-1, n, s);
    	}
    	else if(pnls[m][n].dis == 'u') {
    		pnls[m][n-1].setBackground(Color.cyan);
    		traceback(pnls, m, n-1, s);
    	}
    	else if(pnls[m][n].dis == 'b'){
    		pnls[m][n-1].setBackground(Color.cyan);
    		pnls[m-1][n].setBackground(Color.cyan);
    		traceback(pnls, m-1, n, s);
    		traceback(pnls, m, n-1, s);
    	}
    	else {
    		result.add(s);
    		return;
    	}
    	
    }

    private void init(){
        this.setTitle("LCS");
        //this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(300, 100, 700, 600);
        
        container.setLayout(new BorderLayout());
        container.add(data,BorderLayout.NORTH);
        container.add(scVisual,BorderLayout.CENTER);
        container.add(pnlBottom, BorderLayout.SOUTH);
        
        txtArea.setEditable(false);
        txtArea.setBackground(new Color(238, 238, 238));
        pnlBottom.setBorder(new TitledBorder("Results"));
        pnlBottom.setLayout(new BorderLayout());
        pnlBottom.add(txtArea);
        
        
        data.setBorder(new TitledBorder("Enter 2 Strings"));
        data.setPreferredSize(new Dimension(120,80));  
        data.add(text1);
        data.add(text2);
        data.add(disp);
        
        c.add(container);
        
        try {
        	arrowLeft = ImageIO.read(this.getClass().getResource("img/up.png"));
        	arrowUp = ImageIO.read(this.getClass().getResource("img/left.png"));
        	arrowDig = ImageIO.read(this.getClass().getResource("img/dig.png"));
        	arrowDb = ImageIO.read(this.getClass().getResource("img/double.png"));
        }
        catch(Exception e) {
        	System.out.println("can't open file");
        }
        
        disp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String s1 = text1.getText();
                String s2 = text2.getText();
                char[] y = s1.toCharArray(); 
                char[] x = s2.toCharArray(); 
                int m = x.length; 
                int n = y.length;
                
                myPanel[][] pnls = longestCommonSubsequence(x, y, m, n);
                visual.setLayout(new GridLayout(m+2,n+2));
                
                System.out.println();
                System.out.println();
                for(int i=0; i<m+2; i++) {
                	for(int j=0; j<n+2; j++) {
                		pnls[i][j].setPreferredSize(new Dimension(70, 35));
                		visual.add(pnls[i][j]);
                	}
                }
                visual.repaint();
                container.repaint();
                c.revalidate();
                
                pnls[m+1][n+1].setBackground(Color.cyan);
                traceback(pnls, m+1, n+1, "");
                
                System.out.println(result);
                txtArea.setText("Longest Length: " + pnls[m+1][n+1].lbl.getText() + "\n");
                for(int i=0; i<result.size(); i++) {
                	txtArea.append(i+1 + ". ");
                	for(int j=result.get(i).length()-1; j>=0; j--)
                		txtArea.append(result.get(i).charAt(j)+"");
                	txtArea.append("\n");
                }
                
            }
        });
    }
}

class myPanel extends JPanel{
	private JLabel lblImg;
	private BufferedImage img;
	
	JLabel lbl;
	char dis;
	
	public myPanel(JLabel lbl, BufferedImage img, char dis) {
		this.lbl = lbl;
		this.img = img;
		this.dis = dis;
		init();
	}
	
	private void init() {
		setLayout(null);
		
		if(img != null) {
			lblImg = new JLabel(new ImageIcon(img));
			lblImg.setBounds(0, 0, 30, 30);
			add(lblImg);
		}
		
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setVerticalAlignment(SwingConstants.CENTER);
		lbl.setBounds(0, 0, 70, 35);
		add(lbl);
	}
}