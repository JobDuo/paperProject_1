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
	double all_Dist = 0;//차량과 쿼리 지점으로부터의 최종길이
	boolean node_Check = false;
	
	private static int RATIO = 5;  // 지도비율에 쓸 변수
	private static int NUM_OF_SEARCHING = 5; // 찾을 수
	private static int CARCOUNT = 100;	
	private static int QUERYPOINT = 10671;	
	
	
	private Graph.mapLine[] GRAPH = new Graph.mapLine[23874];
	Graph graph;
	Graph graph_Node_Dist;
	
	AddMapData addMapData = new AddMapData();
	LoadFile loadFile = new LoadFile();//파일 로드
	SF_cnode userQ;
	double userQ_Max_Dist = 2000; //임시로 5000;
	
	Serch serch = new Serch();
	Random random = new Random();
	
	KnnAlgorithm knnAlgorithm = new KnnAlgorithm();
	
	Font font;
	
	ArrayList<Car> carArray = new ArrayList<Car>();		//실제 자동차 객체
	ArrayList<Car> nearCarArray = new ArrayList<Car>();		//실제 근접한 자동차 객체
	ArrayList<Car> nearCarArray_Temp = new ArrayList<Car>();		//실제 근접한 자동차 객체
	ArrayList<Car> answer_CarArray = new ArrayList<Car>();		//k 대 안에 드는 자동차 객체
	
	ArrayList<ArrayList<Integer>> shortest_Car_Path;	//최근접 차량 패스 저장
	ArrayList<Double> shortest_Car_Dist;	//최근접 차량 패스 저장
	

	ArrayList<SF_cnode> nodeArray = new ArrayList<SF_cnode>();		//지도 점 정보
	ArrayList<SF_cedge> edgeArray = new ArrayList<SF_cedge>();		//지도 선 정보
	
	ArrayList<Integer> carNeighborNodeIdTemp = new ArrayList<Integer>();	//자동차 근처 노드
	ArrayList<ArrayList<Integer>> carNeighborNodeId = new ArrayList<ArrayList<Integer>>();
	ArrayList<Integer> carNodeId = new ArrayList<Integer>();
	ArrayList<Integer> carNeighborNodeIdArray  = new ArrayList<Integer>();	//차량 근접 노드 아이디
	
	
	//서버 연동
	ServerBackground serverBackground = new ServerBackground();
	
	DrawPanel drawPanel = new DrawPanel();		//그림 그리기 패널
	
	public MainView(String title) {
		// TODO Auto-generated constructor stub
		super(title);
		
	 

		
		//노드추가
		nodeArray = addMapData.get_Node();
		
		//엣지추가
		edgeArray = addMapData.get_Edge();
		 
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
							//System.out.println("겹침");
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
				 * 차량의 움직임은 본 논문주제의 이슈가 아니다.
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
		
		
		/**
		 * 차량 추가를 자동차와 통신을 통해 추가한다.
		 */
		
		
		
		
		
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
				int NUM_OF_SEARCHING_Candidate = 0;//NUM_OF_SEARCHING 의 후보군
				NUM_OF_SEARCHING = NUM_OF_SEARCHING;
				
				// TODO Auto-generated method stub
				
				/**
				 * knn 알고리즘 사용후 근접한 후보군에서 다익스트라 사용
				 */
				
				//nearCarArray.clear();
				nearCarArray_Temp.clear();
				answer_CarArray.clear(); //가장 근접한 객체의 집합
				
				//쿼리 위치, 자동차, 찾을 개수, 자동차 개수, 쿼리로부터 직선거리 한계점: 구역나누기
				//nearCarArray = knnAlgorithm.KnnAlgorithm(userQ, carArray, NUM_OF_SEARCHING, carArray.size(), userQ_Max_Dist/2);	//knn 에서 후보군을 자도
				nearCarArray = carArray;
				//graph.dijkstra(userQ.getNodeID());	//쿼리 위치 넣어줌
				
				
				//후보군을 뽑아서 NUM_OF_SEARCHING 번째 객체 밖에 존재하는 객체 제거
				
			
				
//				for(int i=0; i<nearCarArray_Temp.size();i++){
//					
//					System.out.println(nearCarArray_Temp.get(i).getNodeID() + "노드의 직선 거리 : " + graph.printQ3(nearCarArray_Temp.get(i).getNodeID()));
//
//					
//					
//					if(!(graph.printQ3(nearCarArray_Temp.get(NUM_OF_SEARCHING-1).getNodeID()) + nearCarArray_Temp.get(NUM_OF_SEARCHING-1).get_Car_Dist() <= //기준 객체의 최단 거리 + 가중치 
//						graph.printQ3(nearCarArray_Temp.get(i).getNodeID())-nearCarArray_Temp.get(i).get_Car_Dist())){ 
//						
//						nearCarArray.add(nearCarArray_Temp.get(i));	//기준객체의 가중치 바깥에 존재하는 객체 제거함
//						
//					}
//					////////////////이 부분 작동 안함 - 자명한 객체 제외 하는 루틴
//					if(graph.printQ3(nearCarArray_Temp.get(NUM_OF_SEARCHING-1).getNodeID())-nearCarArray_Temp.get(NUM_OF_SEARCHING-1).get_Car_Dist() > //기준 객체의 최단 거리 + 가중치 
//						graph.printQ3(nearCarArray_Temp.get(i).getNodeID())+nearCarArray_Temp.get(i).get_Car_Dist()){	//자명한 객체
//						answer_CarArray.add(nearCarArray_Temp.get(i));
//						System.out.println("@@");
//					}
//					
//				}
				
				
				
				
				
				System.out.println("리턴사이즈 : " + nearCarArray.size());
				//serchCar();
				s_resultID = serch.serchShortestCar(graph, userQ, nearCarArray, nearCarArray.size());	//최단거리 자동차 찾기  k 대 찾기
				shortest_Car_Path = serch.serchShortestCarPath(graph, nearCarArray.size());				//최단거리 패스 찾기		//그림 이상해짐 로직은 맞음 그림 수정하려면 ->  nearCarArray.size() = NUM_OF_SEARCHING 로 변경 
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
				
				
				
				for(int i=0; i<nearCarArray.size(); i++){
					carNeighborNodeIdArray = graph.getLinkNode(s_resultID.get(i));  //차량주변 노드
					carNodeId.add(s_resultID.get(i));								//차량 노드
					carNeighborNodeId.add(carNeighborNodeIdArray);					//차량이 존재하는 곳의 인접 노드호출
					neighbor_Car_Path_Stop_Point.add(carNeighborNodeIdArray.size());//차령 주변노드 개수
					System.out.println("1 carNodeId.get("+i+") = " + carNodeId.get(i));
					
				
				}									  

				
				
				all_Dist = graph.printQ3(s_resultID.get((NUM_OF_SEARCHING)-1));	//차량의 주변 노드 에서 쿼리 지점까지의 최단거리 //0을 -> n번째 존재하는 차량 객체로 변경해야함
				System.out.println("감마 기준값 : " + all_Dist);
				System.out.println("감마 기준값 노드 : " + s_resultID.get((NUM_OF_SEARCHING)-1));
				
				//2배수를 불러오기때문에 찾고자 하는 차량보다 2배 이상 많이 설정해야하며 이곳에서 감마 기준값대비 자명한 객체를 골라내고 확률 을 비교해야할 객체를 골라내야한다.
				
				
				
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
		
		
		
		/**
		 * 서버 연동
		 */
		serverBackground.setGui(this);
		serverBackground.setting();		//서버셋팅
		
	}
	
	/**
	 * 서버로 부터 차량 받아오기
	 */
	public void addTransportationCar(){
		/**
		 * 차량정보 삽입
		 */
		//carArray.clear();
		//carArray = addMapData.get_Transportation_Car();			//차량추가
		
		carArray = serverBackground.get_Transportation_Car();
		drawPanel.repaint();	//새로그리기
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
		
		//System.out.println(carArray.size());
		
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
							double v_Distance = 0;	//벌텍스 길이구하기
							double v_Xtemp = 0;
							double v_Ytemp = 0;
							double v_Distance_Part = 0;
							v_All_Car_Distance = 0;
							
							
							
							//감마값 구하기 위해서 ad 를 gd함수로 변경함
							double st_Lanth = graph.printQ3(nodeArray.get(carNodeId.get(i)).getNodeID()); //직선거리
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
							 * v_Xtemp + v_Ytemp 값이 최대 가중치 거리(베타값)보다 적다면 함수호출하로 가야함 - 다른 연결된 노드를 찾아서
							 */
							
							v_Distance_Part = Math.sqrt(v_Xtemp + v_Ytemp);
							
							
							/**
							 * 차량의 감마값과 구하기 계산에 사용될 것 들
							 */
								tempG_node_Num = new ArrayList<Integer>();
								tempG_except_Node = new ArrayList<Integer>();
								short_Path = new ArrayList<Integer>();
								
								//일단 거리 50을 기준으로 계산함 추후 자동차의 속력과 통신시간을 고려해서 계산 해야함
								
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
							System.out.print(nodeArray.get(carNodeId.get(i)).getNodeID() + " 번째 차 의 총 이동 거리(Ad()) = " + v_All_Car_Distance + 
									", 직선거리 = " + graph.printQ3(nodeArray.get(carNodeId.get(i)).getNodeID()));
						   
							//중복된 감마값 제거
							if(gama_Value > v_All_Car_Distance){
								gama_Value = v_All_Car_Distance; //
							}
							
							System.out.println(", 감마값 : " + (gama_Value-input_Gama_Temp));
						
							
							gama_Value = 0;
							
							
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
									
									
									
									
									//System.out.println("최단 거리 노드 = " + carArray.get(i).getNodeID());
									
									
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
						
						/*
						// 감마 값 찾기
						System.out.println("차량 주변 노드");
						for(int j=0; j<temp_Array_Gama_Node_Candidate.size(); j++){
							System.out.print(temp_Array_Gama_Node_Candidate.get(j) + " -> ");
							//차량 주변 노드에서 q까지 최단 거리 구하기
							
						}
						System.out.println();
						System.out.println("최단 패스");
						temp_Array_Gama_Node_Candidate.clear();
						
						for(int j=0; j<shortest_Car_Path.size(); j++){
							System.out.println(shortest_Car_Path.get(j) + " -> ");
						}
						*/
					
						//감마값을 찾고자 하는 차량 주변노드와 최단패스 리스트에서 같은 값을 추출한다.
						
						
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
/**
 * 차량이 갈 수 있는 모든 거리 구하기 재귀함수 호출
 */
//Ad(carNeighborNodeIdTemp.get(j), carNodeId.get(i), v_Distance_Part);
double v_All_Car_Distance = 0;	
double gama_Value = 0;	//차량 객체의 감마값
double gama_Temp = 0;	
//ArrayList<Integer> temp_Array_Gama_Node_Candidate = new ArrayList<>();	//감마값을 알아오기 위해 차량이 갈 수있는 모든 노드를 담는다.
//ArrayList<ArrayList<Integer>> temp_Gama_Node_Check = new ArrayList<ArrayList<Integer>>();

ArrayList<Integer> tempG_node_Num;
ArrayList<Integer> tempG_except_Node;
ArrayList<Integer> short_Path;

public double Ad(int node_Num, int except_Node, double distance ,int car_num, int car_array_num){ //주위 노드 알아오고 값을 더해준다. , 제외 할 노드, 남은 거리, 차량 넘버
	
	
	
	graph.dijkstra(userQ.getNodeID());
	
	
	gama_Temp = graph.printQ3(node_Num); //all_Dist = k번째 객체와 q의 최단 거리, gama_Temp = 현 객체와 q의 최단 거리
	
	//System.out.println("all_Dist = " + all_Dist + ", gama_Temp = " + gama_Temp);
	//아랫줄 이용 감마값 구함
	//System.out.println("현재 노드: " + node_Num + "에서 q 까지의 거리 :" + gama_Temp + ", 최단 거리 all_Dist = " + all_Dist);	//차량의 주변 노드 에서 쿼리 지점까지의 최단거리
	//-> 차량의 주변 노드 구하면서 동시에 위줄 코드 사용해서 거리가 더 먼것은 제외 아닌것은 감마값 포함
	
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
	v_d_Temp = distance - v_Distance_Part;	//남은길이 - 파트길이

	//꼭짓점 감마값 더하기
	if(v_d_Temp <= 0){ //남은 거리가 <= 0 일때 다음 노드로 넘어가는 시점이다. 따라서 최상위 꼭짓점이 된다.
		//System.out.println("꼭짓점 노드 : " + node_Num);
		//이곳 노드(node_Num)와 대상 챠량 값의 직선 거리와 본체의 직선거리를 비교해서 본체의 직선거리가 더 길다면
		//이곳 노드(node_Num)와 대상 챠량 값의 직선 거리 + (이곳 노드와 본체노드 사이의 거리 - 본체.가중치) 가 본체의 직선거리 + 본체.가중치 보다 크다면 큰거에서 작은거 뺸 값을 감마값에 더함
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
		//감마값
		
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
	
	//감마값 구하기 해야 하는ㄷ ㅔ시발 안구해지네 줫같네 루프 확인 해보기(AD 루프)
	
	
	
	//System.out.println(except_Node + "의 주위노드 = " + node_Num );
	
	temp_Array_Neighbor_Node = graph.getLinkNode(node_Num);  //주위 노드를 알아온다.
	
	boolean Tb = false;
	
	/**
	 * 차량 객체 노드의 주변 노드들의 총 길이를 구하기 위해서 모든 노드를 방문해야 한다 그러나 사이클이 존재하면 중복된 노드들을 전부 계산한다.
	 * 이때 tempG_node_Num, tempG_except_Node 를 사용해서 중복되는 노드는 건너띈다.
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
	//이제 감마값을 구해야함
	//System.out.println(tempG_node_Num.size());
	
	
	
	
	
	tempG_node_Num.add(node_Num);
	tempG_except_Node.add(except_Node);
	
	if(!Tb){
	for(int i=0; i<temp_Array_Neighbor_Node.size(); i++){ //순환때문에 속도가 늦어진다.
		
		
		
		if(except_Node != nodeArray.get(temp_Array_Neighbor_Node.get(i)).getNodeID()){ //제외할 노드는 제외 -> 이미 계산된 거리가 중복 계산된다.
			
			if(v_d_Temp > 0){ //구한 파트 노드의 벌텍스 길이가 남은 길이보다 더 짧으면 재귀호출 계속 진행
	
				Ad(nodeArray.get(temp_Array_Neighbor_Node.get(i)).getNodeID(), node_Num, v_d_Temp, car_num, car_array_num);
				//순환 방지 해야한다.
				
			}
			
		}else{
			//System.out.println("제외할 노드 번호 = " + nodeArray.get(temp_Array_Neighbor_Node.get(i)).getNodeID());
		}
		
	}
	}
	//temp_Array_Gama_Node_Candidate.add(node_Num);
	return v_All_Car_Distance;
}


//감마값 구하기
public double Gd(int node_Num, int except_Node, double distance){ //주위 노드 알아오고 값을 더해준다. , 제외 할 노드, 남은 거리, 차량 넘버
	
	graph.dijkstra(userQ.getNodeID());
	
	gama_Temp = graph.printQ3(node_Num); //all_Dist = k번째 객체와 q의 최단 거리, gama_Temp = 현 객체와 q의 최단 거리
	
	//System.out.println("all_Dist = " + all_Dist + ", gama_Temp = " + gama_Temp);
	//아랫줄 이용 감마값 구함
	//System.out.println("현재 노드: " + node_Num + "에서 q 까지의 거리 :" + gama_Temp + ", 최단 거리 all_Dist = " + all_Dist);	//차량의 주변 노드 에서 쿼리 지점까지의 최단거리
	//-> 차량의 주변 노드 구하면서 동시에 위줄 코드 사용해서 거리가 더 먼것은 제외 아닌것은 감마값 포함
	
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
	v_d_Temp = distance - v_Distance_Part;	//남은길이 - 파트길이

	if(v_d_Temp > 0){
				gama_Value += v_Distance_Part;
	}else{	
				gama_Value += distance;
	}
	
	//감마값 구하기 해야 하는ㄷ ㅔ시발 안구해지네 줫같네 루프 확인 해보기(AD 루프)
	
	
	
	//System.out.println(except_Node + "의 주위노드 = " + node_Num );
	
	temp_Array_Neighbor_Node = graph.getLinkNode(node_Num);  //주위 노드를 알아온다.
	
	boolean Tb = false;
	
	/**
	 * 차량 객체 노드의 주변 노드들의 총 길이를 구하기 위해서 모든 노드를 방문해야 한다 그러나 사이클이 존재하면 중복된 노드들을 전부 계산한다.
	 * 이때 tempG_node_Num, tempG_except_Node 를 사용해서 중복되는 노드는 건너띈다.
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
	//이제 감마값을 구해야함
	//System.out.println(tempG_node_Num.size());
	
	tempG_node_Num.add(node_Num);
	tempG_except_Node.add(except_Node);
	
	if(!Tb){
	for(int i=0; i<temp_Array_Neighbor_Node.size(); i++){ //순환때문에 속도가 늦어진다.
		
		
		
		if(except_Node != nodeArray.get(temp_Array_Neighbor_Node.get(i)).getNodeID()){ //제외할 노드는 제외 -> 이미 계산된 거리가 중복 계산된다.
			
			if(v_d_Temp > 0){ //구한 파트 노드의 벌텍스 길이가 남은 길이보다 더 짧으면 재귀호출 계속 진행
	
				Gd(nodeArray.get(temp_Array_Neighbor_Node.get(i)).getNodeID(), node_Num, v_d_Temp);
				//순환 방지 해야한다.
				
			}
			
		}else{
			//System.out.println("제외할 노드 번호 = " + nodeArray.get(temp_Array_Neighbor_Node.get(i)).getNodeID());
		}
		
	}
	}
	//temp_Array_Gama_Node_Candidate.add(node_Num);
	return v_All_Car_Distance;
}







/**
 * 	서버 백그라운드에서 자동차 객체를 가져온다.
 */
public void appendCar(Car car) {
	// TODO Auto-generated method stub
	
}



}


