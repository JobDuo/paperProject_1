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

	int mapPoint_x = 0;		//스크롤바 화면 조정
	int mapPoint_y = 0;
	int viewPanel_x = 0;	//패널 움직이기
	int viewPanel_y = 0;
	
	/**
	 * 패널 위에서 마우스로 노드 확인하기
	 */
	int node_Check_x = 0;
	int node_Check_y = 0;
	int node_Check_num = 0;
	boolean node_Check = false;
	
	private static int RATIO = 5;  // 지도비율에 쓸 변수
	private static int NUM_OF_SEARCHING = 5; // 찾을 수
	private static int CARCOUNT = 100;	
	private static int QUERYPOINT = 0;	
	
	
	private Graph.mapLine[] GRAPH = new Graph.mapLine[23874];
	Graph graph;
	
	
	AddMapData addMapData = new AddMapData();
	LoadFile loadFile = new LoadFile();//파일 로드
	SF_cnode userQ;
	double userQ_Max_Dist = 2000; //임시로 5000;
	
	Serch serch = new Serch();
	Random random = new Random();
	Car car = new Car();
	KnnAlgorithm knnAlgorithm = new KnnAlgorithm();
	
	Font font;
	
	ArrayList<Car> carArray = new ArrayList<Car>();		//실제 자동차 객체
	ArrayList<Car> nearCarArray = new ArrayList<Car>();		//실제 근접한 자동차 객체
	
	
	ArrayList<ArrayList<Integer>> shortest_Car_Path;	//최근접 차량 패스 저장
	ArrayList<Double> shortest_Car_Dist;	//최근접 차량 패스 저장
	

	ArrayList<SF_cnode> nodeArray = new ArrayList<SF_cnode>();		//지도 점 정보
	ArrayList<SF_cedge> edgeArray = new ArrayList<SF_cedge>();		//지도 선 정보
	
	ArrayList<Integer> carNeighborNodeIdTemp = new ArrayList<Integer>();	//자동차 근처 노드
	ArrayList<ArrayList<Integer>> carNeighborNodeId = new ArrayList<ArrayList<Integer>>();
	ArrayList<Integer> carNodeId = new ArrayList<Integer>();
	ArrayList<Integer> carNeighborNodeIdArray  = new ArrayList<Integer>();	//차량 근접 노드 아이디
	
	
	public MainView(String title) {
		// TODO Auto-generated constructor stub
		super(title);

		
		//노드추가
		nodeArray = addMapData.addNode();
		
		//엣지추가
		edgeArray = addMapData.addEdge();
		 
		/**
		 * 그래프 생성
		 */
		for (SF_cedge edg : edgeArray){ 	
        	GRAPH = addMapData.edge2Graph(edg);
		}
		graph = new Graph(GRAPH);
		
		/**
		 * 화면구성
		 */
		DrawPanel drawPanel = new DrawPanel();		//그림 그리기 패널
		DrawButtonPanel drawButtonPanel = new DrawButtonPanel();
		drawButtonPanel.setLayout(null);			//버튼위치 지정할 수 있도록
		drawPanel.setLayout(null);
		
		JButton c_Car_B = new JButton("차랜덤생성");
		JButton search_B = new JButton("찾기");
		JButton left = new JButton("<-");
		JButton right = new JButton("->");
		JButton up = new JButton("Up");
		JButton down = new JButton("Down");
		JButton zoomIn = new JButton("확대");
		JButton zoomOut = new JButton("축소");
		JButton query_Point = new JButton("쿼리 위치 지정하기");
		JTextField carCountInput = new JTextField(5); 
		JTextField searchCountInput = new JTextField(5); 
		JTextField queryPointInput = new JTextField(5); 
		
		
		/**
		 * 버튼 객체 위치 지정
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
		
		
		drawPanel.setBounds(1, 1, 1, 1); 	//패널위치및 사이즈
		drawButtonPanel.setBounds(1, 1, 1, 1); 	//패널위치및 사이즈
		
		
		JScrollPane scroll;
		scroll = new JScrollPane();  // 스크롤패널을 선언
		//scroll.setViewportView(drawPanel);

		
		JPanel viewPanel = new JPanel();	//뷰 패널안에 있는 drawPanel의 좌표를움직인다. 뷰패널은 고정되어있고 drawPanel은 아래에서 움직임
		viewPanel.setLayout(null);
		viewPanel.add(drawPanel);
		
		
		scroll.setViewportView(viewPanel);
		scroll.setBounds(50, 50, 700, 700);    // 프레임에 스크롤패널의 위치를 정한다
		
		
		add(scroll);
		//add(drawPanel);
		add(drawButtonPanel);
		
		setSize(1600, 1000);				//프레임 크기지정
		
		setVisible(true);					//프레임 보이기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//프레임 정상종료가능
		
		
		//********************************************************************************************************************
		
		
		/**
		 * drawPanel 패널에 이벤트 추가
		 */
	
		drawPanel.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
			
				
				/**
				 * 패널에 마우스 올릴때 좌표 
				 */
				for(int i=0; i<nodeArray.size(); i++){
				
					if(e.getX() >= (int)nodeArray.get(i).getNormalizedX()/RATIO-3 && e.getX() <= (int)nodeArray.get(i).getNormalizedX()/RATIO+3){
						
						if(e.getY() >= (int)nodeArray.get(i).getNormalizedY()/RATIO-3  && e.getY() <= (int)nodeArray.get(i).getNormalizedY()/RATIO+3){
							System.out.println("겹침");
							node_Check = true;		//겹칠때 노드 찍어줌
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
		//패널에 직접 그리지 않고 다른 무언가 위에 그리는것을 고려 해야 할듯 시발
		
		
		
		
		
		
		
		//쿼리 위치
		userQ = nodeArray.get(QUERYPOINT);	//쿼리위치
		
		/**
		 * 차랜덤생성 버튼
		 */
		
		c_Car_B.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				/**
				 * 차량 갯수 입력 받아옴
				 */
				try{
					CARCOUNT = Integer.parseInt(carCountInput.getText());
				}catch (Exception ex) {
					// TODO: handle exception
					System.out.println("숫자를 입력하세요.");
				}
				
				/**
				 * 차량정보 삽입
				 */
				carArray.clear();
				carArray = addMapData.addRandCar(CARCOUNT);			//차량추가
				
				/**
				 * 차의 움직임은 노드를 따라 이동하기때문에 그래프가 존재하는 클래스에서 확인할수있다.
				 * 차랴으이 움직임은 본 논문주제의 이슈가 아니다.
				 */
				
				carNeighborNodeId.clear();
				carNodeId.clear();
				carNeighborNodeIdTemp.clear();
				neighbor_Car_Path_Stop_Point.clear();
				carNeighborNodeIdArray.clear();
				
				/* 모든 차량의 주변 찍기
				for(int i=0; i<CARCOUNT; i++){
					//carNeighborNodeIdArray = graph.getLinkNode(carArray.get(i).getNodeID());
					carNeighborNodeId.add(carNeighborNodeIdArray);	//차량이 존재하는 곳의 인접 노드호출
					//carNodeId.add(carArray.get(i).getNodeID());	//차량 노드
					neighbor_Car_Path_Stop_Point.add(carNeighborNodeIdArray.size());
					//carArray.get(i).setNeighborNode(nodeCount);
				}*/
				
				s_check = false;
				
				drawPanel.repaint();	//새로그리기
			}
		});
		//********************************************************************************************************************
		
		/**
		 * 쿼리 위치 변경 버튼
		 */
		query_Point.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try{
					QUERYPOINT = Integer.parseInt(queryPointInput.getText());
					userQ = nodeArray.get(QUERYPOINT);	//쿼리위치
				}catch (Exception ex) {
					// TODO: handle exception
					System.out.println("숫자를 입력하세요.");
				}
				 
			}
		});
		
		/**
		 * 찾기 버튼
		 */
		search_B.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try{
					NUM_OF_SEARCHING = Integer.parseInt(searchCountInput.getText());
				}catch (Exception ex) {
					// TODO: handle exception
					System.out.println("숫자를 입력하세요.");
				}
				
				// TODO Auto-generated method stub
				
				/**
				 * knn 알고리즘 사용후 근접한 후보군에서 다익스트라 사용
				 */
				
				nearCarArray.clear();
				//쿼리 위치, 자동차, 찾을 개수, 자동차 개수, 쿼리로부터 직선거리 한계점: 구역나누기
				nearCarArray = knnAlgorithm.KnnAlgorithm(userQ, carArray, NUM_OF_SEARCHING, carArray.size(), userQ_Max_Dist/2);
				System.out.println("리턴사이즈 : " + nearCarArray.size());
				//serchCar();
				s_resultID = serch.serchShortestCar(graph, userQ, nearCarArray, NUM_OF_SEARCHING);	//최단거리 자동차 찾기  k 대 찾기
				shortest_Car_Path = serch.serchShortestCarPath(graph, NUM_OF_SEARCHING);				//최단거리 패스 찾기
				shortest_Car_Dist = serch.serchShortestCarDisk(graph);									//최단거리 자동차 까지의 거리
				Car_Path_Stop_Point = serch.car_Path_Stop_Point();										//패스가 이어지는 부분 제거하기위해
				
				/**
				 * 최단 거리 차량의 주변 노드 찍기
				 */
				carNeighborNodeId.clear();
				carNodeId.clear();
				carNeighborNodeIdTemp.clear();
				neighbor_Car_Path_Stop_Point.clear();
				carNeighborNodeIdArray.clear();
				
				
				
				for(int i=0; i<NUM_OF_SEARCHING; i++){
					carNeighborNodeIdArray = graph.getLinkNode(s_resultID.get(i));  //차량주변 노드
					carNodeId.add(s_resultID.get(i));					//차량 노드
					carNeighborNodeId.add(carNeighborNodeIdArray);					//차량이 존재하는 곳의 인접 노드호출
					neighbor_Car_Path_Stop_Point.add(carNeighborNodeIdArray.size());//차령 주변노드 개수
					System.out.println("1 carNodeId.get("+i+") = " + carNodeId.get(i));
				}									  

			
				
				s_check = true;
				
				drawPanel.repaint();
			}
		});
		//********************************************************************************************************************
		
		/**
		 * 왼쪽
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
		 * 오른쪽
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
		 * 위
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
		 * 아래
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
		 * 줌 인
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
		 * 줌 아웃
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
		setBounds(800, 50, 700, 700); 	//패널위치및 사이즈
		g.setColor(Color.white);
		g.fillRect(0, 0, 1000, 1000);
	}
}
	
	 
class DrawPanel extends JPanel {
	
	@Override
	protected void paintComponent(Graphics g) {
		
		/**
		 * 화면 초기화
		 */
		//setSize(2500,2500);
		
		setBounds(viewPanel_x, viewPanel_y, 3000, 3000); 	//패널위치및 사이즈
		
		g.setColor(Color.white);
		g.fillRect(0, 0, 3000, 3000);
		
		
		 		/**
		 		 * 노드 그리기, 점
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
					 * 엣지 그리기, 선
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
					 * 자동차 그리기
					 */
					
					if(!carArray.isEmpty())
					{
						
						g.setColor(Color.RED);
						for(int i=0; i<carArray.size(); i++){
							g.fillRect((int)carArray.get(i).getPoint_x()/RATIO -2, (int)carArray.get(i).getPoint_y()/RATIO-2, 25 / RATIO, 25 / RATIO);
							//System.out.println(i+"랜덤찍는다");
						}
						
						
						
						//ArrayList<ArrayList<Integer>> carNeighborNodeId = new ArrayList<ArrayList<Integer>>();
						//ArrayList<Integer> carNodeId = new ArrayList<Integer>();
						
						
						/**
						 *  자동차 주변 노드 찍기
						 */
						if(s_check){
						g.setColor(Color.yellow);
						//int neighbor_Car_Path_Stop_Point_For_Start = 0;//자동차 스톱 포인트 리스트는 연결되기 떄문에 연결부분을 제거 해주는 템프 변수
						
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
							 * 자동차 주변 엣지 그리기	
							*/
							//for(int j=neighbor_Car_Path_Stop_Point_For_Start; j<neighbor_Car_Path_Stop_Point.get(i); j++){
							for(int j=0; j<neighbor_Car_Path_Stop_Point.get(i); j++){
							g.drawLine(
									(int)nodeArray.get(carNeighborNodeIdTemp.get(j)).getNormalizedX()/RATIO, 
									(int)nodeArray.get(carNeighborNodeIdTemp.get(j)).getNormalizedY()/RATIO, 
									(int)nodeArray.get(carNodeId.get(i)).getNormalizedX()/RATIO, 
									(int)nodeArray.get(carNodeId.get(i)).getNormalizedY()/RATIO);
									
							} 
							//neighbor_Car_Path_Stop_Point_For_Start = neighbor_Car_Path_Stop_Point.get(i);
							
							
							
						}
						}
						
						
						
						
						
						/**
						 * 쿼리 위치 그리기 
						 */
						g.setColor(Color.BLUE);
						g.fillRect((int)userQ.getNormalizedX()/RATIO -2, (int)userQ.getNormalizedY()/RATIO-2, 40 / RATIO, 40 / RATIO);
						
						
						/**
						 * 쿼리 위치로부터 최단 거리 검색하는 범위
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
					 * 최단 거리 자동차표시
					 */
					if(s_check)
					{
						g.setColor(Color.GREEN);
						for(int i=0; i<carArray.size(); i++)
						{
							for(int j=0; j<NUM_OF_SEARCHING;j++)
							{
								if(carArray.get(i).getNodeID() == s_resultID.get(j)){	//최단 거리인 자동차
									g.drawRect((int)carArray.get(i).getPoint_x()/RATIO -4,
											(int)carArray.get(i).getPoint_y()/RATIO-4, 40 / RATIO, 40 / RATIO);
								
									g.drawString(shortest_Car_Dist.get(j).toString(), 
											(int)carArray.get(i).getPoint_x()/RATIO-4,
											(int)carArray.get(i).getPoint_y()/RATIO-4);
								}
								
							}
							
						}
						
						/**
						 * 최단 거리 패스 그리기
						 */
						ArrayList<Integer> pathEdge = new ArrayList<Integer>();
						
						for(int i=0; i<NUM_OF_SEARCHING; i++){
							
							pathEdge = shortest_Car_Path.get(i);
							
							for(int j=1; j<pathEdge.size(); j++){
								
								
								SF_cnode fill_t_Node1 = nodeArray.get(pathEdge.get(j));
								SF_cnode fill_t_Node2 = nodeArray.get(pathEdge.get(j-1));
								
								g.setColor(Color.blue);
								
								
								
								/**
								 * 직선으로 그려지는 라인 제거
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
					
					/**
					 * 노드 번호 찍어주기
					 */
					font = new Font("Serif", Font.BOLD, 100/RATIO);
		  			g.setFont(font);
		  			g.setColor(Color.red);
					if(node_Check){
						
						g.drawString(Integer.toString(node_Check_num) , node_Check_x, node_Check_y);
						
					}
					
					
					
					
	}

}



}


