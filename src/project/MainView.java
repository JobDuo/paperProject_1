package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;


class MainView extends JFrame{
	
	boolean s_check= false;
	
	ArrayList<Integer> s_resultID = new ArrayList<Integer>();
	int temp;
	int[] Car_Path_Stop_Point;	//
	ArrayList<Integer> neighbor_Car_Path_Stop_Point = new ArrayList<Integer>();

	int mapPoint_x = 0;		//��ũ�ѹ� ȭ�� ����
	int mapPoint_y = 0;
	int viewPanel_x = 0;	//�г� �����̱�
	int viewPanel_y = 0;
	
	/**
	 * �г� ������ ���콺�� ��� Ȯ���ϱ�
	 */
	int node_Check_x = 0;
	int node_Check_y = 0;
	int node_Check_num = 0;
	double all_Dist = 0;//������ ���� �������κ����� ��������
	boolean node_Check = false;
	
	private static int RATIO = 5;  // ���������� �� ����
	private static int NUM_OF_SEARCHING = 5; // ã�� ��
	private static int CARCOUNT = 100;	
	private static int QUERYPOINT = 10671;	
	
	
	private Graph.mapLine[] GRAPH = new Graph.mapLine[23874];
	Graph graph;
	Graph graph_Node_Dist;
	
	AddMapData addMapData = new AddMapData();
	LoadFile loadFile = new LoadFile();//���� �ε�
	SF_cnode userQ;
	double userQ_Max_Dist = 2000; //�ӽ÷� 5000;
	
	Serch serch = new Serch();
	Random random = new Random();
	
	KnnAlgorithm knnAlgorithm = new KnnAlgorithm();
	
	Font font;
	
	ArrayList<Car> carArray = new ArrayList<Car>();		//���� �ڵ��� ��ü
	ArrayList<Car> nearCarArray = new ArrayList<Car>();		//���� ������ �ڵ��� ��ü
	ArrayList<Car> nearCarArray_Temp = new ArrayList<Car>();		//���� ������ �ڵ��� ��ü
	ArrayList<Car> answer_CarArray = new ArrayList<Car>();		//k �� �ȿ� ��� �ڵ��� ��ü
	
	ArrayList<ArrayList<Integer>> shortest_Car_Path;	//�ֱ��� ���� �н� ����
	ArrayList<Double> shortest_Car_Dist;	//�ֱ��� ���� �н� ����
	

	ArrayList<SF_cnode> nodeArray = new ArrayList<SF_cnode>();		//���� �� ����
	ArrayList<SF_cedge> edgeArray = new ArrayList<SF_cedge>();		//���� �� ����
	
	ArrayList<Integer> carNeighborNodeIdTemp = new ArrayList<Integer>();	//�ڵ��� ��ó ���
	ArrayList<ArrayList<Integer>> carNeighborNodeId = new ArrayList<ArrayList<Integer>>();
	ArrayList<Integer> carNodeId = new ArrayList<Integer>();
	ArrayList<Integer> carNeighborNodeIdArray  = new ArrayList<Integer>();	//���� ���� ��� ���̵�
	
	
	//���� ����
	ServerBackground serverBackground = new ServerBackground();
	
	DrawPanel drawPanel = new DrawPanel();		//�׸� �׸��� �г�
	
	public MainView(String title) {
		// TODO Auto-generated constructor stub
		super(title);
		
	 

		
		//����߰�
		nodeArray = addMapData.get_Node();
		
		//�����߰�
		edgeArray = addMapData.get_Edge();
		 
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
		JButton query_Point = new JButton("���� ��ġ �����ϱ�");
		JTextField carCountInput = new JTextField(5); 
		JTextField searchCountInput = new JTextField(5); 
		JTextField queryPointInput = new JTextField(5); 
		
		
		/**
		 * ��ư ��ü ��ġ ����
		 */
		carCountInput.setBounds(30, 30, 100, 50);
		c_Car_B.setBounds(140, 30, 100, 50);
		query_Point.setBounds(140, 90, 150, 50);
		queryPointInput.setBounds(30, 90, 100, 50);
		searchCountInput.setBounds(30, 150, 100, 50);
		search_B.setBounds(140, 150, 100, 50);
		left.setBounds(30, 210, 100, 50);
		right.setBounds(30, 270, 100, 50);
		up.setBounds(140, 210, 100, 50);
		down.setBounds(140, 270, 100, 50);
		zoomIn.setBounds(30, 330, 100, 50);
		zoomOut.setBounds(140, 330, 100, 50);
		
		drawButtonPanel.add(carCountInput);
		drawButtonPanel.add(searchCountInput);
		drawButtonPanel.add(query_Point);
		drawButtonPanel.add(queryPointInput);
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
		
		
		/**
		 * drawPanel �гο� �̺�Ʈ �߰�
		 */
	
		drawPanel.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
			
				
				/**
				 * �гο� ���콺 �ø��� ��ǥ 
				 */
				for(int i=0; i<nodeArray.size(); i++){
				
					if(e.getX() >= (int)nodeArray.get(i).getNormalizedX()/RATIO-3 && e.getX() <= (int)nodeArray.get(i).getNormalizedX()/RATIO+3){
						
						if(e.getY() >= (int)nodeArray.get(i).getNormalizedY()/RATIO-3  && e.getY() <= (int)nodeArray.get(i).getNormalizedY()/RATIO+3){
							//System.out.println("��ħ");
							node_Check = true;		//��ĥ�� ��� �����
							node_Check_x = e.getX();
							node_Check_y = e.getY();
							
							node_Check_num = nodeArray.get(i).getNodeID();
							
							drawPanel.repaint();
							break;
						}else {
							node_Check = false;
						}
						
						
					}
					
				}
				
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		//JScrollPane scrollPane = new JScrollPane(drawPanel);
		//�гο� ���� �׸��� �ʰ� �ٸ� ���� ���� �׸��°��� ��� �ؾ� �ҵ� �ù�
		
		
		
		
		
		
		
		//���� ��ġ
		userQ = nodeArray.get(QUERYPOINT);	//������ġ
		
		/**
		 * ���������� ��ư
		 */
		c_Car_B.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				/**
				 * ���� ���� �Է� �޾ƿ�
				 */
				try{
					CARCOUNT = Integer.parseInt(carCountInput.getText());
				}catch (Exception ex) {
					// TODO: handle exception
					System.out.println("���ڸ� �Է��ϼ���.");
				}
				
				/**
				 * �������� ����
				 */
				carArray.clear();
				carArray = addMapData.addRandCar(CARCOUNT);			//�����߰�
				
				
				/**
				 * ���� �������� ��带 ���� �̵��ϱ⶧���� �׷����� �����ϴ� Ŭ�������� Ȯ���Ҽ��ִ�.
				 * ������ �������� �� �������� �̽��� �ƴϴ�.
				 */
				
				carNeighborNodeId.clear();
				carNodeId.clear();
				carNeighborNodeIdTemp.clear();
				neighbor_Car_Path_Stop_Point.clear();
				carNeighborNodeIdArray.clear();
				
				/* ��� ������ �ֺ� ���
				for(int i=0; i<CARCOUNT; i++){
					//carNeighborNodeIdArray = graph.getLinkNode(carArray.get(i).getNodeID());
					carNeighborNodeId.add(carNeighborNodeIdArray);	//������ �����ϴ� ���� ���� ���ȣ��
					//carNodeId.add(carArray.get(i).getNodeID());	//���� ���
					neighbor_Car_Path_Stop_Point.add(carNeighborNodeIdArray.size());
					//carArray.get(i).setNeighborNode(nodeCount);
				}*/
				
				s_check = false;
				
				drawPanel.repaint();	//���α׸���
			}
		});
		
		
		/**
		 * ���� �߰��� �ڵ����� ����� ���� �߰��Ѵ�.
		 */
		
		
		
		
		
		//********************************************************************************************************************
		
		/**
		 * ���� ��ġ ���� ��ư
		 */
		query_Point.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try{
					QUERYPOINT = Integer.parseInt(queryPointInput.getText());
					userQ = nodeArray.get(QUERYPOINT);	//������ġ
				}catch (Exception ex) {
					// TODO: handle exception
					System.out.println("���ڸ� �Է��ϼ���.");
				}
				 
			}
		});
		
		/**
		 * ã�� ��ư
		 */
	
		search_B.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try{
					NUM_OF_SEARCHING = Integer.parseInt(searchCountInput.getText());
				}catch (Exception ex) {
					// TODO: handle exception
					System.out.println("���ڸ� �Է��ϼ���.");
				}
				int NUM_OF_SEARCHING_Candidate = 0;//NUM_OF_SEARCHING �� �ĺ���
				NUM_OF_SEARCHING = NUM_OF_SEARCHING;
				
				// TODO Auto-generated method stub
				
				/**
				 * knn �˰��� ����� ������ �ĺ������� ���ͽ�Ʈ�� ���
				 */
				
				//nearCarArray.clear();
				nearCarArray_Temp.clear();
				answer_CarArray.clear(); //���� ������ ��ü�� ����
				
				//���� ��ġ, �ڵ���, ã�� ����, �ڵ��� ����, �����κ��� �����Ÿ� �Ѱ���: ����������
				//nearCarArray = knnAlgorithm.KnnAlgorithm(userQ, carArray, NUM_OF_SEARCHING, carArray.size(), userQ_Max_Dist/2);	//knn ���� �ĺ����� �ڵ�
				nearCarArray = carArray;
				//graph.dijkstra(userQ.getNodeID());	//���� ��ġ �־���
				
				
				//�ĺ����� �̾Ƽ� NUM_OF_SEARCHING ��° ��ü �ۿ� �����ϴ� ��ü ����
				
			
				
//				for(int i=0; i<nearCarArray_Temp.size();i++){
//					
//					System.out.println(nearCarArray_Temp.get(i).getNodeID() + "����� ���� �Ÿ� : " + graph.printQ3(nearCarArray_Temp.get(i).getNodeID()));
//
//					
//					
//					if(!(graph.printQ3(nearCarArray_Temp.get(NUM_OF_SEARCHING-1).getNodeID()) + nearCarArray_Temp.get(NUM_OF_SEARCHING-1).get_Car_Dist() <= //���� ��ü�� �ִ� �Ÿ� + ����ġ 
//						graph.printQ3(nearCarArray_Temp.get(i).getNodeID())-nearCarArray_Temp.get(i).get_Car_Dist())){ 
//						
//						nearCarArray.add(nearCarArray_Temp.get(i));	//���ذ�ü�� ����ġ �ٱ��� �����ϴ� ��ü ������
//						
//					}
//					////////////////�� �κ� �۵� ���� - �ڸ��� ��ü ���� �ϴ� ��ƾ
//					if(graph.printQ3(nearCarArray_Temp.get(NUM_OF_SEARCHING-1).getNodeID())-nearCarArray_Temp.get(NUM_OF_SEARCHING-1).get_Car_Dist() > //���� ��ü�� �ִ� �Ÿ� + ����ġ 
//						graph.printQ3(nearCarArray_Temp.get(i).getNodeID())+nearCarArray_Temp.get(i).get_Car_Dist()){	//�ڸ��� ��ü
//						answer_CarArray.add(nearCarArray_Temp.get(i));
//						System.out.println("@@");
//					}
//					
//				}
				
				
				
				
				
				System.out.println("���ϻ����� : " + nearCarArray.size());
				//serchCar();
				s_resultID = serch.serchShortestCar(graph, userQ, nearCarArray, nearCarArray.size());	//�ִܰŸ� �ڵ��� ã��  k �� ã��
				shortest_Car_Path = serch.serchShortestCarPath(graph, nearCarArray.size());				//�ִܰŸ� �н� ã��		//�׸� �̻����� ������ ���� �׸� �����Ϸ��� ->  nearCarArray.size() = NUM_OF_SEARCHING �� ���� 
				shortest_Car_Dist = serch.serchShortestCarDisk(graph);									//�ִܰŸ� �ڵ��� ������ �Ÿ�
				Car_Path_Stop_Point = serch.car_Path_Stop_Point();										//�н��� �̾����� �κ� �����ϱ�����
				
				/**
				 * �ִ� �Ÿ� ������ �ֺ� ��� ���
				 */
				carNeighborNodeId.clear();
				carNodeId.clear();
				carNeighborNodeIdTemp.clear();
				neighbor_Car_Path_Stop_Point.clear();
				carNeighborNodeIdArray.clear();
				
				
				
				for(int i=0; i<nearCarArray.size(); i++){
					carNeighborNodeIdArray = graph.getLinkNode(s_resultID.get(i));  //�����ֺ� ���
					carNodeId.add(s_resultID.get(i));								//���� ���
					carNeighborNodeId.add(carNeighborNodeIdArray);					//������ �����ϴ� ���� ���� ���ȣ��
					neighbor_Car_Path_Stop_Point.add(carNeighborNodeIdArray.size());//���� �ֺ���� ����
					System.out.println("1 carNodeId.get("+i+") = " + carNodeId.get(i));
					
				
				}									  

				
				
				all_Dist = graph.printQ3(s_resultID.get((NUM_OF_SEARCHING)-1));	//������ �ֺ� ��� ���� ���� ���������� �ִܰŸ� //0�� -> n��° �����ϴ� ���� ��ü�� �����ؾ���
				System.out.println("���� ���ذ� : " + all_Dist);
				System.out.println("���� ���ذ� ��� : " + s_resultID.get((NUM_OF_SEARCHING)-1));
				
				//2����� �ҷ����⶧���� ã���� �ϴ� �������� 2�� �̻� ���� �����ؾ��ϸ� �̰����� ���� ���ذ���� �ڸ��� ��ü�� ��󳻰� Ȯ�� �� ���ؾ��� ��ü�� ��󳻾��Ѵ�.
				
				
				
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
		
		
		
		/**
		 * ���� ����
		 */
		serverBackground.setGui(this);
		serverBackground.setting();		//��������
		
	}
	
	/**
	 * ������ ���� ���� �޾ƿ���
	 */
	public void addTransportationCar(){
		/**
		 * �������� ����
		 */
		//carArray.clear();
		//carArray = addMapData.get_Transportation_Car();			//�����߰�
		
		carArray = serverBackground.get_Transportation_Car();
		drawPanel.repaint();	//���α׸���
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
	
	 
class DrawPanel extends JPanel {
	
	@Override
	protected void paintComponent(Graphics g) {
		
		//System.out.println(carArray.size());
		
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
						if(s_check){
						g.setColor(Color.yellow);
						//int neighbor_Car_Path_Stop_Point_For_Start = 0;//�ڵ��� ���� ����Ʈ ����Ʈ�� ����Ǳ� ������ ����κ��� ���� ���ִ� ���� ����
						
						for(int i=0; i<NUM_OF_SEARCHING; i++){
							
							carNeighborNodeIdTemp = carNeighborNodeId.get(i);
							//System.out.println("carNeighborNodeId.get(i) = " + carNeighborNodeId.get(i));
							
							
							for(int j=0; j<carNeighborNodeIdTemp.size(); j++){
								g.fillRect(
										(int)nodeArray.get(carNeighborNodeIdTemp.get(j)).getNormalizedX()/RATIO, 
										(int)nodeArray.get(carNeighborNodeIdTemp.get(j)).getNormalizedY()/RATIO, 
										50 / RATIO, 50 / RATIO);
								
							}
							/**
							 * �ڵ��� �ֺ� ���� �׸���	
							*/
							
							//for(int j=neighbor_Car_Path_Stop_Point_For_Start; j<neighbor_Car_Path_Stop_Point.get(i); j++){
							double v_Distance = 0;	//���ؽ� ���̱��ϱ�
							double v_Xtemp = 0;
							double v_Ytemp = 0;
							double v_Distance_Part = 0;
							v_All_Car_Distance = 0;
							
							
							
							//������ ���ϱ� ���ؼ� ad �� gd�Լ��� ������
							double st_Lanth = graph.printQ3(nodeArray.get(carNodeId.get(i)).getNodeID()); //�����Ÿ�
							double input_Gama_Temp = 1;
							if(all_Dist - st_Lanth > carArray.get(i).get_Car_Dist()){
								input_Gama_Temp = carArray.get(i).get_Car_Dist();
							}else{
								input_Gama_Temp = all_Dist - st_Lanth;
							}
							
							
							
							for(int j=0; j<neighbor_Car_Path_Stop_Point.get(i); j++){
							
							g.drawLine(
									(int)nodeArray.get(carNeighborNodeIdTemp.get(j)).getNormalizedX()/RATIO, 
									(int)nodeArray.get(carNeighborNodeIdTemp.get(j)).getNormalizedY()/RATIO, 
									(int)nodeArray.get(carNodeId.get(i)).getNormalizedX()/RATIO, 
									(int)nodeArray.get(carNodeId.get(i)).getNormalizedY()/RATIO);
							
							v_Xtemp = (int)nodeArray.get(carNeighborNodeIdTemp.get(j)).getNormalizedX()/RATIO - (int)nodeArray.get(carNodeId.get(i)).getNormalizedX()/RATIO;
							v_Ytemp = (int)nodeArray.get(carNeighborNodeIdTemp.get(j)).getNormalizedY()/RATIO - (int)nodeArray.get(carNodeId.get(i)).getNormalizedY()/RATIO;
							v_Xtemp = Math.pow(v_Xtemp, 2);
							v_Ytemp = Math.pow(v_Ytemp, 2);
							
							/**
							 * v_Xtemp + v_Ytemp ���� �ִ� ����ġ �Ÿ�(��Ÿ��)���� ���ٸ� �Լ�ȣ���Ϸ� ������ - �ٸ� ����� ��带 ã�Ƽ�
							 */
							
							v_Distance_Part = Math.sqrt(v_Xtemp + v_Ytemp);
							
							
							/**
							 * ������ �������� ���ϱ� ��꿡 ���� �� ��
							 */
								tempG_node_Num = new ArrayList<Integer>();
								tempG_except_Node = new ArrayList<Integer>();
								short_Path = new ArrayList<Integer>();
								
								//�ϴ� �Ÿ� 50�� �������� ����� ���� �ڵ����� �ӷ°� ��Žð��� ����ؼ� ��� �ؾ���
								
								v_All_Car_Distance = Ad(nodeArray.get(nodeArray.get(carNeighborNodeIdTemp.get(j)).getNodeID()).getNodeID(), nodeArray.get(carNodeId.get(i)).getNodeID(), 
										carArray.get(i).get_Car_Dist(), 
										carNodeId.get(i), i);
								
								
								
								tempG_node_Num = new ArrayList<Integer>();
								tempG_except_Node = new ArrayList<Integer>();
								short_Path = new ArrayList<Integer>();
								
							
								Gd(nodeArray.get(nodeArray.get(carNeighborNodeIdTemp.get(j)).getNodeID()).getNodeID(), nodeArray.get(carNodeId.get(i)).getNodeID(), 
										input_Gama_Temp
										);
								
							}	
								
								
							//neighbor_Car_Path_Stop_Point_For_Start = neighbor_Car_Path_Stop_Point.get(i);
							System.out.print(nodeArray.get(carNodeId.get(i)).getNodeID() + " ��° �� �� �� �̵� �Ÿ�(Ad()) = " + v_All_Car_Distance + 
									", �����Ÿ� = " + graph.printQ3(nodeArray.get(carNodeId.get(i)).getNodeID()));
						   
							//�ߺ��� ������ ����
							if(gama_Value > v_All_Car_Distance){
								gama_Value = v_All_Car_Distance; //
							}
							
							System.out.println(", ������ : " + (gama_Value-input_Gama_Temp));
						
							
							gama_Value = 0;
							
							
						}	
					
						} 	
						
						
						
						
						
						/**
						 * ���� ��ġ �׸��� 
						 */
						g.setColor(Color.BLUE);
						g.fillRect((int)userQ.getNormalizedX()/RATIO -2, (int)userQ.getNormalizedY()/RATIO-2, 40 / RATIO, 40 / RATIO);
						
						
						/**
						 * ���� ��ġ�κ��� �ִ� �Ÿ� �˻��ϴ� ����
						 */
						//userQ_Max_Dist
						g.drawOval(
								(int)userQ.getNormalizedX()/RATIO - (int)(userQ_Max_Dist/RATIO/2), 
								(int)userQ.getNormalizedY()/RATIO - (int)(userQ_Max_Dist/RATIO/2), 
								(int)(userQ_Max_Dist/RATIO), 
								(int)(userQ_Max_Dist/RATIO));
						
						
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
								if(carArray.get(i).getNodeID() == s_resultID.get(j)){	//�ִ� �Ÿ��� �ڵ���
									g.drawRect((int)carArray.get(i).getPoint_x()/RATIO -4,
											(int)carArray.get(i).getPoint_y()/RATIO-4, 40 / RATIO, 40 / RATIO);
								
									g.drawString(shortest_Car_Dist.get(j).toString(), 
											(int)carArray.get(i).getPoint_x()/RATIO-4,
											(int)carArray.get(i).getPoint_y()/RATIO-4);
									
									
									
									
									//System.out.println("�ִ� �Ÿ� ��� = " + carArray.get(i).getNodeID());
									
									
								}
								
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
						
						/*
						// ���� �� ã��
						System.out.println("���� �ֺ� ���");
						for(int j=0; j<temp_Array_Gama_Node_Candidate.size(); j++){
							System.out.print(temp_Array_Gama_Node_Candidate.get(j) + " -> ");
							//���� �ֺ� ��忡�� q���� �ִ� �Ÿ� ���ϱ�
							
						}
						System.out.println();
						System.out.println("�ִ� �н�");
						temp_Array_Gama_Node_Candidate.clear();
						
						for(int j=0; j<shortest_Car_Path.size(); j++){
							System.out.println(shortest_Car_Path.get(j) + " -> ");
						}
						*/
					
						//�������� ã���� �ϴ� ���� �ֺ����� �ִ��н� ����Ʈ���� ���� ���� �����Ѵ�.
						
						
					}
					//********************************************************************************************************************
					
					/**
					 * ��� ��ȣ ����ֱ�
					 */
					font = new Font("Serif", Font.BOLD, 100/RATIO);
		  			g.setFont(font);
		  			g.setColor(Color.red);
					if(node_Check){
						
						g.drawString(Integer.toString(node_Check_num) , node_Check_x, node_Check_y);
						
					}
					
					
					
					
	}

}
/**
 * ������ �� �� �ִ� ��� �Ÿ� ���ϱ� ����Լ� ȣ��
 */
//Ad(carNeighborNodeIdTemp.get(j), carNodeId.get(i), v_Distance_Part);
double v_All_Car_Distance = 0;	
double gama_Value = 0;	//���� ��ü�� ������
double gama_Temp = 0;	
//ArrayList<Integer> temp_Array_Gama_Node_Candidate = new ArrayList<>();	//�������� �˾ƿ��� ���� ������ �� ���ִ� ��� ��带 ��´�.
//ArrayList<ArrayList<Integer>> temp_Gama_Node_Check = new ArrayList<ArrayList<Integer>>();

ArrayList<Integer> tempG_node_Num;
ArrayList<Integer> tempG_except_Node;
ArrayList<Integer> short_Path;

public double Ad(int node_Num, int except_Node, double distance ,int car_num, int car_array_num){ //���� ��� �˾ƿ��� ���� �����ش�. , ���� �� ���, ���� �Ÿ�, ���� �ѹ�
	
	
	
	graph.dijkstra(userQ.getNodeID());
	
	
	gama_Temp = graph.printQ3(node_Num); //all_Dist = k��° ��ü�� q�� �ִ� �Ÿ�, gama_Temp = �� ��ü�� q�� �ִ� �Ÿ�
	
	//System.out.println("all_Dist = " + all_Dist + ", gama_Temp = " + gama_Temp);
	//�Ʒ��� �̿� ������ ����
	//System.out.println("���� ���: " + node_Num + "���� q ������ �Ÿ� :" + gama_Temp + ", �ִ� �Ÿ� all_Dist = " + all_Dist);	//������ �ֺ� ��� ���� ���� ���������� �ִܰŸ�
	//-> ������ �ֺ� ��� ���ϸ鼭 ���ÿ� ���� �ڵ� ����ؼ� �Ÿ��� �� �հ��� ���� �ƴѰ��� ������ ����
	
	double v_Xtemp = 0;
	double v_Ytemp = 0;
	double v_Distance = 0;
	double v_Distance_Part = 0;
	double v_d_Temp = 0;
	boolean gama_Value_B = true;
	
	ArrayList<Integer> temp_Array_Neighbor_Node = new ArrayList<Integer>();
	
	v_Xtemp = nodeArray.get(except_Node).getNormalizedX() - nodeArray.get(node_Num).getNormalizedX();
	v_Ytemp = nodeArray.get(except_Node).getNormalizedY() - nodeArray.get(node_Num).getNormalizedY();
	v_Xtemp = Math.pow(v_Xtemp, 2);
	v_Ytemp = Math.pow(v_Ytemp, 2);
	v_Distance_Part = Math.sqrt(v_Xtemp + v_Ytemp);
	v_d_Temp = distance - v_Distance_Part;	//�������� - ��Ʈ����

	//������ ������ ���ϱ�
	if(v_d_Temp <= 0){ //���� �Ÿ��� <= 0 �϶� ���� ���� �Ѿ�� �����̴�. ���� �ֻ��� �������� �ȴ�.
		//System.out.println("������ ��� : " + node_Num);
		//�̰� ���(node_Num)�� ��� í�� ���� ���� �Ÿ��� ��ü�� �����Ÿ��� ���ؼ� ��ü�� �����Ÿ��� �� ��ٸ�
		//�̰� ���(node_Num)�� ��� í�� ���� ���� �Ÿ� + (�̰� ���� ��ü��� ������ �Ÿ� - ��ü.����ġ) �� ��ü�� �����Ÿ� + ��ü.����ġ ���� ũ�ٸ� ū�ſ��� ������ �A ���� �������� ����
		graph.dijkstra(car_num);
		double dgTemp = graph.printQ3(node_Num);
		if(dgTemp > gama_Temp){
			if(dgTemp + (dgTemp-gama_Temp-carArray.get(car_array_num).get_Car_Dist()) > gama_Temp + carArray.get(car_array_num).get_Car_Dist()){
				gama_Value += (dgTemp + (dgTemp-gama_Temp-carArray.get(car_array_num).get_Car_Dist())) - gama_Temp + carArray.get(car_array_num).get_Car_Dist();
			}
		}
		
	}
	
	
	
	
	
	if(v_d_Temp > 0){
		v_All_Car_Distance += v_Distance_Part;
		//System.out.println("all_Dist = " + all_Dist + ", gama_Temp : " + gama_Temp + ", car_Num : " + car_Num);
		//������
		
		if(all_Dist >= gama_Temp){
		
			
		
			if(gama_Value_B){
				//gama_Value += v_Distance_Part;
			}
			
		}

		
	}else{
		v_All_Car_Distance += distance;
		
			if(all_Dist >= gama_Temp){
						
						
				if(gama_Value_B){
					//gama_Value += distance;
				}
			
		}
	}
	
	//������ ���ϱ� �ؾ� �ϴ¤� �Ľù� �ȱ������� �Z���� ���� Ȯ�� �غ���(AD ����)
	
	
	
	//System.out.println(except_Node + "�� ������� = " + node_Num );
	
	temp_Array_Neighbor_Node = graph.getLinkNode(node_Num);  //���� ��带 �˾ƿ´�.
	
	boolean Tb = false;
	
	/**
	 * ���� ��ü ����� �ֺ� ������ �� ���̸� ���ϱ� ���ؼ� ��� ��带 �湮�ؾ� �Ѵ� �׷��� ����Ŭ�� �����ϸ� �ߺ��� ������ ���� ����Ѵ�.
	 * �̶� tempG_node_Num, tempG_except_Node �� ����ؼ� �ߺ��Ǵ� ���� �ǳʶ��.
	 */
	for(int i=0; i<tempG_node_Num.size(); i++){
		if(tempG_node_Num.get(i) == node_Num){
			
			for(int j=0; j<tempG_except_Node.size();j++){
				if(tempG_except_Node.get(j) == except_Node){
					Tb = true;
					//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					break;
				}
			}
			if(Tb){
			break;
			}
			
		}else if(tempG_node_Num.get(i) == except_Node){
			
			for(int j=0; j<tempG_except_Node.size(); j++){
				if(tempG_except_Node.get(j) == node_Num){
					Tb = true;
					//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					break;
				}
			}
			if(Tb){
				break;
			}
			
		}
	}
	//���� �������� ���ؾ���
	//System.out.println(tempG_node_Num.size());
	
	
	
	
	
	tempG_node_Num.add(node_Num);
	tempG_except_Node.add(except_Node);
	
	if(!Tb){
	for(int i=0; i<temp_Array_Neighbor_Node.size(); i++){ //��ȯ������ �ӵ��� �ʾ�����.
		
		
		
		if(except_Node != nodeArray.get(temp_Array_Neighbor_Node.get(i)).getNodeID()){ //������ ���� ���� -> �̹� ���� �Ÿ��� �ߺ� ���ȴ�.
			
			if(v_d_Temp > 0){ //���� ��Ʈ ����� ���ؽ� ���̰� ���� ���̺��� �� ª���� ���ȣ�� ��� ����
	
				Ad(nodeArray.get(temp_Array_Neighbor_Node.get(i)).getNodeID(), node_Num, v_d_Temp, car_num, car_array_num);
				//��ȯ ���� �ؾ��Ѵ�.
				
			}
			
		}else{
			//System.out.println("������ ��� ��ȣ = " + nodeArray.get(temp_Array_Neighbor_Node.get(i)).getNodeID());
		}
		
	}
	}
	//temp_Array_Gama_Node_Candidate.add(node_Num);
	return v_All_Car_Distance;
}


//������ ���ϱ�
public double Gd(int node_Num, int except_Node, double distance){ //���� ��� �˾ƿ��� ���� �����ش�. , ���� �� ���, ���� �Ÿ�, ���� �ѹ�
	
	graph.dijkstra(userQ.getNodeID());
	
	gama_Temp = graph.printQ3(node_Num); //all_Dist = k��° ��ü�� q�� �ִ� �Ÿ�, gama_Temp = �� ��ü�� q�� �ִ� �Ÿ�
	
	//System.out.println("all_Dist = " + all_Dist + ", gama_Temp = " + gama_Temp);
	//�Ʒ��� �̿� ������ ����
	//System.out.println("���� ���: " + node_Num + "���� q ������ �Ÿ� :" + gama_Temp + ", �ִ� �Ÿ� all_Dist = " + all_Dist);	//������ �ֺ� ��� ���� ���� ���������� �ִܰŸ�
	//-> ������ �ֺ� ��� ���ϸ鼭 ���ÿ� ���� �ڵ� ����ؼ� �Ÿ��� �� �հ��� ���� �ƴѰ��� ������ ����
	
	double v_Xtemp = 0;
	double v_Ytemp = 0;
	double v_Distance = 0;
	double v_Distance_Part = 0;
	double v_d_Temp = 0;
	
	ArrayList<Integer> temp_Array_Neighbor_Node = new ArrayList<Integer>();
	
	v_Xtemp = nodeArray.get(except_Node).getNormalizedX() - nodeArray.get(node_Num).getNormalizedX();
	v_Ytemp = nodeArray.get(except_Node).getNormalizedY() - nodeArray.get(node_Num).getNormalizedY();
	v_Xtemp = Math.pow(v_Xtemp, 2);
	v_Ytemp = Math.pow(v_Ytemp, 2);
	v_Distance_Part = Math.sqrt(v_Xtemp + v_Ytemp);
	v_d_Temp = distance - v_Distance_Part;	//�������� - ��Ʈ����

	if(v_d_Temp > 0){
				gama_Value += v_Distance_Part;
	}else{	
				gama_Value += distance;
	}
	
	//������ ���ϱ� �ؾ� �ϴ¤� �Ľù� �ȱ������� �Z���� ���� Ȯ�� �غ���(AD ����)
	
	
	
	//System.out.println(except_Node + "�� ������� = " + node_Num );
	
	temp_Array_Neighbor_Node = graph.getLinkNode(node_Num);  //���� ��带 �˾ƿ´�.
	
	boolean Tb = false;
	
	/**
	 * ���� ��ü ����� �ֺ� ������ �� ���̸� ���ϱ� ���ؼ� ��� ��带 �湮�ؾ� �Ѵ� �׷��� ����Ŭ�� �����ϸ� �ߺ��� ������ ���� ����Ѵ�.
	 * �̶� tempG_node_Num, tempG_except_Node �� ����ؼ� �ߺ��Ǵ� ���� �ǳʶ��.
	 */
	for(int i=0; i<tempG_node_Num.size(); i++){
		if(tempG_node_Num.get(i) == node_Num){
			
			for(int j=0; j<tempG_except_Node.size();j++){
				if(tempG_except_Node.get(j) == except_Node){
					Tb = true;
					//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					break;
				}
			}
			if(Tb){
			break;
			}
			
		}else if(tempG_node_Num.get(i) == except_Node){
			
			for(int j=0; j<tempG_except_Node.size(); j++){
				if(tempG_except_Node.get(j) == node_Num){
					Tb = true;
					//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					break;
				}
			}
			if(Tb){
				break;
			}
			
		}
	}
	//���� �������� ���ؾ���
	//System.out.println(tempG_node_Num.size());
	
	tempG_node_Num.add(node_Num);
	tempG_except_Node.add(except_Node);
	
	if(!Tb){
	for(int i=0; i<temp_Array_Neighbor_Node.size(); i++){ //��ȯ������ �ӵ��� �ʾ�����.
		
		
		
		if(except_Node != nodeArray.get(temp_Array_Neighbor_Node.get(i)).getNodeID()){ //������ ���� ���� -> �̹� ���� �Ÿ��� �ߺ� ���ȴ�.
			
			if(v_d_Temp > 0){ //���� ��Ʈ ����� ���ؽ� ���̰� ���� ���̺��� �� ª���� ���ȣ�� ��� ����
	
				Gd(nodeArray.get(temp_Array_Neighbor_Node.get(i)).getNodeID(), node_Num, v_d_Temp);
				//��ȯ ���� �ؾ��Ѵ�.
				
			}
			
		}else{
			//System.out.println("������ ��� ��ȣ = " + nodeArray.get(temp_Array_Neighbor_Node.get(i)).getNodeID());
		}
		
	}
	}
	//temp_Array_Gama_Node_Candidate.add(node_Num);
	return v_All_Car_Distance;
}







/**
 * 	���� ��׶��忡�� �ڵ��� ��ü�� �����´�.
 */
public void appendCar(Car car) {
	// TODO Auto-generated method stub
	
}



}


