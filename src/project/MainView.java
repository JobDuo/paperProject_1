package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


class MainView extends JFrame{
	
	boolean s_check= false;
	
	int s_resultID[] = new int[NUM_OF_SEARCHING];
	int temp;
	int[] Car_Path_Stop_Point;	//
	ArrayList<Integer> neighbor_Car_Path_Stop_Point = new ArrayList<Integer>();

	int mapPoint_x = 0;		//��ũ�ѹ� ȭ�� ����
	int mapPoint_y = 0;
	int viewPanel_x = 0;	//�г� �����̱�
	int viewPanel_y = 0;
	
	
	
	private static int RATIO = 5;  // ���������� �� ����
	private static final int NUM_OF_SEARCHING = 5; // ã�� ��
	private static int CARCOUNT = 100;	
	
	private Graph.mapLine[] GRAPH = new Graph.mapLine[23874];
	Graph graph;
	
	
	AddMapData addMapData = new AddMapData();
	LoadFile loadFile = new LoadFile();//���� �ε�
	SF_cnode userQ;
	Serch serch = new Serch();
	Random random = new Random();
	Car car = new Car();
	
	
	ArrayList<Car> carArray = new ArrayList<Car>();		//���� �ڵ��� ��ü
	
	
	ArrayList<ArrayList<Integer>> shortest_Car_Path;	//�ֱ��� ���� �н� ����

	ArrayList<SF_cnode> nodeArray = new ArrayList<SF_cnode>();		//���� �� ����
	ArrayList<SF_cedge> edgeArray = new ArrayList<SF_cedge>();		//���� �� ����
	
	ArrayList<Integer> carNeighborNodeIdTemp = new ArrayList<Integer>();	//�ڵ��� ��ó ���
	
	ArrayList<ArrayList<Integer>> carNeighborNodeId = new ArrayList<ArrayList<Integer>>();
	ArrayList<Integer> carNodeId = new ArrayList<Integer>();
	
	
	public MainView(String title) {
		// TODO Auto-generated constructor stub
		super(title);

		
		//����߰�
		nodeArray = addMapData.addNode();
		
		//�����߰�
		edgeArray = addMapData.addEdge();
		 
		/**
		 * �׷��� ����
		 */
		for (SF_cedge edg : edgeArray){ 	
        	GRAPH = addMapData.edge2Graph(edg);
		}
		graph = new Graph(GRAPH);
		
		/**
		 * ȭ�鱸��
		 */
		DrawPanel drawPanel = new DrawPanel();		//�׸� �׸��� �г�
		DrawButtonPanel drawButtonPanel = new DrawButtonPanel();
		drawButtonPanel.setLayout(null);			//��ư��ġ ������ �� �ֵ���
		drawPanel.setLayout(null);
		
		JButton c_Car_B = new JButton("����������");
		JButton search_B = new JButton("ã��");
		JButton left = new JButton("<-");
		JButton right = new JButton("->");
		JButton up = new JButton("Up");
		JButton down = new JButton("Down");
		JButton zoomIn = new JButton("Ȯ��");
		JButton zoomOut = new JButton("���");
		
		
		/**
		 * ��ư ��ü ��ġ ����
		 */
		c_Car_B.setBounds(30, 30, 100, 50);
		search_B.setBounds(30, 90, 100, 50);
		left.setBounds(30, 150, 100, 50);
		right.setBounds(30, 210, 100, 50);
		up.setBounds(140, 150, 100, 50);
		down.setBounds(140, 210, 100, 50);
		zoomIn.setBounds(30, 270, 100, 50);
		zoomOut.setBounds(140, 270, 100, 50);
		
		
		drawButtonPanel.add(c_Car_B);
		drawButtonPanel.add(search_B);
		drawButtonPanel.add(left);
		drawButtonPanel.add(right);
		drawButtonPanel.add(up);
		drawButtonPanel.add(down);
		drawButtonPanel.add(zoomIn);
		drawButtonPanel.add(zoomOut);
		
		
		drawPanel.setBounds(1, 1, 1, 1); 	//�г���ġ�� ������
		drawButtonPanel.setBounds(1, 1, 1, 1); 	//�г���ġ�� ������
		
		
		JScrollPane scroll;
		scroll = new JScrollPane();  // ��ũ���г��� ����
		//scroll.setViewportView(drawPanel);

		
		JPanel viewPanel = new JPanel();	//�� �гξȿ� �ִ� drawPanel�� ��ǥ�������δ�. ���г��� �����Ǿ��ְ� drawPanel�� �Ʒ����� ������
		viewPanel.setLayout(null);
		viewPanel.add(drawPanel);
		
		
		scroll.setViewportView(viewPanel);
		scroll.setBounds(50, 50, 700, 700);    // �����ӿ� ��ũ���г��� ��ġ�� ���Ѵ�
		
		
		add(scroll);
		//add(drawPanel);
		add(drawButtonPanel);
		
		setSize(1600, 1000);				//������ ũ������
		
		setVisible(true);					//������ ���̱�
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//������ �������ᰡ��
		
		
		//********************************************************************************************************************
		
		
		
		
		
		
		
		
		//JScrollPane scrollPane = new JScrollPane(drawPanel);
		//�гο� ���� �׸��� �ʰ� �ٸ� ���� ���� �׸��°��� ��� �ؾ� �ҵ� �ù�
		
		
		
		
		
		
		
		//���� ��ġ
		userQ = nodeArray.get(1000);	//������ġ
		
		/**
		 * ���������� ��ư
		 */
		
		
		
		c_Car_B.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				/**
				 * �������� ����
				 */
				carArray.clear();
				carArray = addMapData.addCar(CARCOUNT);			//�����߰�
				
				/**
				 * ���� �������� ��带 ���� �̵��ϱ⶧���� �׷����� �����ϴ� Ŭ�������� Ȯ���Ҽ��ִ�.
				 * �������� �������� �� �������� �̽��� �ƴϴ�.
				 */
			
				carNeighborNodeId.clear();
				carNodeId.clear();
				carNeighborNodeIdTemp.clear();
				neighbor_Car_Path_Stop_Point.clear();
				
				
				ArrayList<Integer> carNeighborNodeIdArray = new ArrayList<Integer>();
				for(int i=0; i<CARCOUNT; i++){
					
					carNeighborNodeIdArray = graph.getLinkNode(carArray.get(i).getNodeID());
					carNeighborNodeId.add(carNeighborNodeIdArray);	//������ �����ϴ� ���� ���� ���ȣ��
					carNodeId.add(carArray.get(i).getNodeID());	//���� ���
					neighbor_Car_Path_Stop_Point.add(carNeighborNodeIdArray.size());
					//carArray.get(i).setNeighborNode(nodeCount);
				}
				
				s_check = false;
				
				drawPanel.repaint();	//���α׸���
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
				
				//serchCar();
				s_resultID = serch.serchShortestCar(graph, userQ, carArray, NUM_OF_SEARCHING);	//�ִܰŸ� �ڵ��� ã��  k �� ã��
				shortest_Car_Path = serch.serchShortestCarPath(graph, NUM_OF_SEARCHING);			//�ִܰŸ� �н� ã��
				Car_Path_Stop_Point = serch.car_Path_Stop_Point();									//�н��� �̾����� �κ� �����ϱ�����
				
				
				s_check = true;
				
				drawPanel.repaint();
			}
		});
		//********************************************************************************************************************
		
		/**
		 * ����
		 */
		left.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				viewPanel_x += 200;
				drawPanel.repaint();
			}
			
		});
		
		/**
		 * ������
		 */
		right.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				viewPanel_x -= 200;
				drawPanel.repaint();
			}
			
		});
		/**
		 * ��
		 */
		up.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				viewPanel_y += 200;
				drawPanel.repaint();
			}
			
		});
		
		/**
		 * �Ʒ�
		 */
		down.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				viewPanel_y -= 200;
				drawPanel.repaint();
			}
			
		});
		
		/**
		 * �� ��
		 */
		zoomIn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				RATIO--;
				drawPanel.repaint();
			}
			
		});
		
		/**
		 * �� �ƿ�
		 */
		zoomOut.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				RATIO++;
				drawPanel.repaint();
			}
			
		});
		
	}

class DrawButtonPanel extends JPanel{
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		//setSize(500,500);
		setBounds(800, 50, 700, 700); 	//�г���ġ�� ������
		g.setColor(Color.white);
		g.fillRect(0, 0, 1000, 1000);
	}
}
	
	 
class DrawPanel extends JLabel {
	
	@Override
	protected void paintComponent(Graphics g) {
		
		/**
		 * ȭ�� �ʱ�ȭ
		 */
		//setSize(2500,2500);
		
		setBounds(viewPanel_x, viewPanel_y, 3000, 3000); 	//�г���ġ�� ������
		
		g.setColor(Color.white);
		g.fillRect(0, 0, 3000, 3000);
		
		
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
					
					if(!carArray.isEmpty())
					{
						
						g.setColor(Color.RED);
						for(int i=0; i<carArray.size(); i++){
							g.fillRect((int)carArray.get(i).getPoint_x()/RATIO -2, (int)carArray.get(i).getPoint_y()/RATIO-2, 25 / RATIO, 25 / RATIO);
							//System.out.println(i+"������´�");
						}
						
						
						
						//ArrayList<ArrayList<Integer>> carNeighborNodeId = new ArrayList<ArrayList<Integer>>();
						//ArrayList<Integer> carNodeId = new ArrayList<Integer>();
						
						
						/**
						 *  �ڵ��� �ֺ� ��� ���
						 */
						
						
						g.setColor(Color.yellow);
						for(int i=0; i<CARCOUNT; i++){
							
							carNeighborNodeIdTemp = carNeighborNodeId.get(i);
							
							for(int j=0; j<carNeighborNodeIdTemp.size(); j++){
								g.fillRect(
										(int)nodeArray.get(carNeighborNodeIdTemp.get(j)).getNormalizedX()/RATIO, 
										(int)nodeArray.get(carNeighborNodeIdTemp.get(j)).getNormalizedY()/RATIO, 
										25 / RATIO, 25 / RATIO);
								
								
								//�� ���� ���ڳ� 
								/*
								if(i>0)
								if((neighbor_Car_Path_Stop_Point.get(i)) % i == 1)
								g.drawLine(
										(int)nodeArray.get(carNeighborNodeIdTemp.get(j)).getNormalizedX()/RATIO, 
										(int)nodeArray.get(carNeighborNodeIdTemp.get(j)).getNormalizedY()/RATIO, 
										(int)nodeArray.get(carNodeId.get(i)).getNormalizedX()/RATIO, 
										(int)nodeArray.get(carNodeId.get(i)).getNormalizedY()/RATIO);
								*/
							}
							
							
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
						for(int i=0; i<carArray.size(); i++)
						{
							for(int j=0; j<NUM_OF_SEARCHING;j++)
							{
								if(carArray.get(i).getNodeID() == s_resultID[j])
									g.drawRect((int)carArray.get(i).getPoint_x()/RATIO -4,
											(int)carArray.get(i).getPoint_y()/RATIO-4, 40 / RATIO, 40 / RATIO);
								
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
					
	}

}



}


