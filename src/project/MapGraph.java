package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;



public class MapGraph {
	

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MyFrame myFrame = new MyFrame("Map");
		
		
	}
	
	
	
	
}

class MyFrame extends JFrame{
	
	private Graph.mapLine[] GRAPH = new Graph.mapLine[23874];
	private static final int RATIO = 5;  // ���������� �� ����
	private static final int NUM_OF_SEARCHING = 5; // ã�� ��
	
	private static File cedgeFile = new File("./s_edge.txt");
	private static File cnodeFile = new File("./s_node.txt");
	ArrayList<SF_cnode> nodeArray = new ArrayList<SF_cnode>();		//���� �� ����
	ArrayList<SF_cedge> edgeArray = new ArrayList<SF_cedge>();		//���� �� ����
	
	ArrayList<SF_cnode> randCarArray = new ArrayList<SF_cnode>();
	
	ArrayList<ArrayList<Integer>> shortest_Car_Path;	//�ֱ��� ���� �н� ����
	
	SF_cnode userQ;
	int s_resultID[] = new int[NUM_OF_SEARCHING];
	boolean s_check= false;
	
	int temp;
	int[] Car_Path_Stop_Point;
	Random random;
	
	public MyFrame(String title) {
		// TODO Auto-generated constructor stub
		super(title);

		/**
		 * ȭ�鱸��
		 */
		MyPanel myPanel = new MyPanel();
		JScrollPane pane = new JScrollPane(myPanel);
		JButton c_Car_B = new JButton("����������");
		JButton search_B = new JButton("ã��");
		c_Car_B.setBounds(0, 0, 80, 50);
		search_B.setBounds(0,60,80,50);
		myPanel.add(c_Car_B);
		myPanel.add(search_B);
		//********************************************************************************************************************
		
		/**
		 * ���������� ��ư
		 */
		c_Car_B.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				s_check = false;
				randCarArray.clear();
				random = new Random();
				
				
				//���� ���� �߰�
				for(int i=0; i<100; i++){
					
					temp = random.nextInt(nodeArray.size());	//���� ���� ����
					randCarArray.add(nodeArray.get(temp));		//������ ��� ��ġ
								
				}
				
				temp= random.nextInt(nodeArray.size());
				
				userQ = nodeArray.get(1000);	//������ġ

				//myPanel.revalidate();
				myPanel.repaint();
				System.out.println("���� ������ư"); //���� ������ư
			}
		});
		//********************************************************************************************************************
		
		
		/**
		 * ã�� ��ư
		 */
		search_B.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				for (SF_cedge edg : edgeArray){ 	
	            	edge2Graph(edg);
				}
				
				Graph g = new Graph(GRAPH);
				g.dijkstra(userQ.getNodeID());
				
				shortest_Car_Path = new ArrayList<ArrayList<Integer>>();	//k���� ���� ���� �н��� ����
				Car_Path_Stop_Point = new int[NUM_OF_SEARCHING];		//�н� �׸��� ������ ������ ù ���� ����Ǵ°� ���ֱ� ���ؼ�
				
				g.clear_List();	//Ŭ���� �Լ� �����ָ� �н� �ߺ��ȴ�.
				s_resultID = g.printQ1(NUM_OF_SEARCHING,randCarArray);
				
				
				/**
				 * ���� ������ ã�ƿ� ���� ��ü�� ���̵� ���� �н��� �׸�
				 */
				shortest_Car_Path.clear();
				for(int i=0; i<NUM_OF_SEARCHING; i++){
					ArrayList<Integer> temp = new ArrayList<Integer>();
					temp = g.printQ2(s_resultID[i]);
					shortest_Car_Path.add(temp);
					Car_Path_Stop_Point[i] = temp.size();
					
					
					
				}

				s_check = true;
				System.out.println("ã�� ��ư");
				myPanel.repaint();
			}
		});
		//********************************************************************************************************************
		
		
		/**
		 * ȭ�鱸��
		 */
		add(myPanel, BorderLayout.CENTER);
		setSize(2000, 1000);				//������ ũ������
		setVisible(true);					//������ ���̱�
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//������ �������ᰡ��
		//********************************************************************************************************************
		
		
	}
	
	/**
	 * �׷��� ���� �Լ�
	 * @param theEdge
	 */
	 private void edge2Graph(SF_cedge theEdge)
	 {
	    	SF_cnode theNode1 = nodeArray.get(theEdge.getStart_NodeID());
	               
	        assert theNode1 != null;
	        SF_cnode theNode2 = nodeArray.get(theEdge.getEnd_NodeID());
	        assert theNode2 != null;
	    		GRAPH[theEdge.getEdgeID()] = new Graph.mapLine(theEdge.getStart_NodeID(),theEdge.getEnd_NodeID()
	    				,theEdge.getL2Distance());
	           
	  }
	//********************************************************************************************************************

	
	 
class MyPanel extends JPanel{
	
	@Override
	protected void paintComponent(Graphics g) {
		
		LoadFile loadFile = new LoadFile();
		ArrayList<String[]> nodes = loadFile.LoadFile(cnodeFile, 3);
		
		/**
		 * ȭ�� �ʱ�ȭ
		 */
		g.setColor(Color.white);
		g.fillRect(0, 0, 2000, 1000);
		
		/**
		 * ��� �߰�
		 */
		 for (int i = 0; i < nodes.size(); i++) {
	            String[] sArray = (String[])nodes.get(i);
	            SF_cnode theNode = new SF_cnode();
	            theNode.setNodeID((new Integer(sArray[0])).intValue());
	            theNode.setNormalizedX((new Double(sArray[1])).doubleValue());
	            theNode.setNormalizedY((new Double(sArray[2])).doubleValue());
	            
	            nodeArray.add(theNode);
	    
	        }
		//********************************************************************************************************************
		 
		 /**
		  * ���� �߰�
		  */
		 		ArrayList<String[]> edges = loadFile.LoadFile(cedgeFile, 4);
		 		for (int i = 0; i < edges.size(); i++) {
	            String[] sArray = (String[])edges.get(i);
	            
	            
	            SF_cedge theEdge = new SF_cedge();
	           
	            theEdge.setEdgeID((new Integer(sArray[0])).intValue());
	            theEdge.setStart_NodeID((new Integer(sArray[1])).intValue());
	            theEdge.setEnd_NodeID((new Integer(sArray[2])).intValue());
	            theEdge.setL2Distance((new Double(sArray[3])).doubleValue());
	            edgeArray.add(theEdge);
	            
	        }
		 //********************************************************************************************************************
		 			
		 		/**
		 		 * ��� �׸���, ��
		 		 */
		  			g.setColor(Color.BLACK);
					for(int i=0; i<nodeArray.size(); i++){
						g.fillRect(
								(int)nodeArray.get(i).getNormalizedX()/RATIO, 
								(int)nodeArray.get(i).getNormalizedY()/RATIO, 
								1, 
								1);
					}
					//********************************************************************************************************************	
					
					
					/**
					 * ���� �׸���, ��
					 */
					g.setColor(Color.black);
					for(int i=0; i<edgeArray.size(); i++){
						
						SF_cnode fill_t_Node1 = nodeArray.get(edgeArray.get(i).getStart_NodeID());
						SF_cnode fill_t_Node2 = nodeArray.get(edgeArray.get(i).getEnd_NodeID());
						
						g.drawLine(
								(int)fill_t_Node1.getNormalizedX()/RATIO,
								(int)fill_t_Node1.getNormalizedY()/RATIO,
								(int)fill_t_Node2.getNormalizedX()/RATIO,
								(int)fill_t_Node2.getNormalizedY()/RATIO);
					}
					//********************************************************************************************************************
					
					
					/**
					 * �ڵ��� �׸���
					 */
					g.setColor(Color.RED);
					if(!randCarArray.isEmpty())
					{
						
						
						for(int i=0; i<randCarArray.size(); i++){
							g.fillRect((int)randCarArray.get(i).getNormalizedX()/RATIO -2, (int)randCarArray.get(i).getNormalizedY()/RATIO-2, 25 / RATIO, 25 / RATIO);
							//System.out.println(i+"������´�");
						}
						
						/**
						 * ���� ��ġ �׸���
						 */
						g.setColor(Color.BLUE);
						g.fillRect((int)userQ.getNormalizedX()/RATIO -2, (int)userQ.getNormalizedY()/RATIO-2, 40 / RATIO, 40 / RATIO);
						
					}
					//********************************************************************************************************************
					
					/**
					 * �ִ� �Ÿ� �ڵ���ǥ��
					 */
					if(s_check)
					{
						g.setColor(Color.GREEN);
						for(int i=0; i<randCarArray.size(); i++)
						{
							for(int j=0; j<NUM_OF_SEARCHING;j++)
							{
								if(randCarArray.get(i).getNodeID() == s_resultID[j])
									g.drawRect((int)randCarArray.get(i).getNormalizedX()/RATIO -4,
											(int)randCarArray.get(i).getNormalizedY()/RATIO-4, 40 / RATIO, 40 / RATIO);
								
							}
							
						}
						
						/**
						 * �ִ� �Ÿ� �н� �׸���
						 */
						ArrayList<Integer> pathEdge = new ArrayList<Integer>();
					
						
						for(int i=0; i<NUM_OF_SEARCHING; i++){
							
							pathEdge = shortest_Car_Path.get(i);
							
							for(int j=1; j<pathEdge.size(); j++){
								
								
								SF_cnode fill_t_Node1 = nodeArray.get(pathEdge.get(j));
								SF_cnode fill_t_Node2 = nodeArray.get(pathEdge.get(j-1));
								
								g.setColor(Color.blue);
								
								/**
								 * �������� �׷����� ���� ����
								 */
								boolean drawLineBoolean = true;
								for(int k=0; k<NUM_OF_SEARCHING; k++){
									if(Car_Path_Stop_Point[k] == j){
										drawLineBoolean = false;
									}
								}
								
								if(drawLineBoolean)
								g.drawLine(
										(int)fill_t_Node1.getNormalizedX()/RATIO,
										(int)fill_t_Node1.getNormalizedY()/RATIO,
										(int)fill_t_Node2.getNormalizedX()/RATIO,
										(int)fill_t_Node2.getNormalizedY()/RATIO);
								
								
							}
							
						}
						
						
					}
					//********************************************************************************************************************
					
					
					
					
					
					//********************************************************************************************************************
					
	}
	
	
}

}


















