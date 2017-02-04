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
	private static final int RATIO = 5;  // 지도비율에 쓸 변수
	private static final int NUM_OF_SEARCHING = 5; // 찾을 수
	
	private static File cedgeFile = new File("./s_edge.txt");
	private static File cnodeFile = new File("./s_node.txt");
	ArrayList<SF_cnode> nodeArray = new ArrayList<SF_cnode>();		//지도 점 정보
	ArrayList<SF_cedge> edgeArray = new ArrayList<SF_cedge>();		//지도 선 정보
	
	ArrayList<SF_cnode> randCarArray = new ArrayList<SF_cnode>();
	
	ArrayList<ArrayList<Integer>> shortest_Car_Path;	//최근접 차량 패스 저장
	
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
		 * 화면구성
		 */
		MyPanel myPanel = new MyPanel();
		JScrollPane pane = new JScrollPane(myPanel);
		JButton c_Car_B = new JButton("차랜덤생성");
		JButton search_B = new JButton("찾기");
		c_Car_B.setBounds(0, 0, 80, 50);
		search_B.setBounds(0,60,80,50);
		myPanel.add(c_Car_B);
		myPanel.add(search_B);
		//********************************************************************************************************************
		
		/**
		 * 차랜덤생성 버튼
		 */
		c_Car_B.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				s_check = false;
				randCarArray.clear();
				random = new Random();
				
				
				//임의 차량 추가
				for(int i=0; i<100; i++){
					
					temp = random.nextInt(nodeArray.size());	//랜덤 차량 생성
					randCarArray.add(nodeArray.get(temp));		//차량의 노드 위치
								
				}
				
				temp= random.nextInt(nodeArray.size());
				
				userQ = nodeArray.get(1000);	//쿼리위치

				//myPanel.revalidate();
				myPanel.repaint();
				System.out.println("차량 생성버튼"); //차량 생성버튼
			}
		});
		//********************************************************************************************************************
		
		
		/**
		 * 찾기 버튼
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
				
				shortest_Car_Path = new ArrayList<ArrayList<Integer>>();	//k대의 근접 차량 패스를 저장
				Car_Path_Stop_Point = new int[NUM_OF_SEARCHING];		//패스 그릴때 마지막 지점과 첫 지점 연결되는것 없애기 위해서
				
				g.clear_List();	//클리어 함수 안해주면 패스 중복된다.
				s_resultID = g.printQ1(NUM_OF_SEARCHING,randCarArray);
				
				
				/**
				 * 가장 가깝게 찾아온 차량 객체의 아이디를 통해 패스를 그림
				 */
				shortest_Car_Path.clear();
				for(int i=0; i<NUM_OF_SEARCHING; i++){
					ArrayList<Integer> temp = new ArrayList<Integer>();
					temp = g.printQ2(s_resultID[i]);
					shortest_Car_Path.add(temp);
					Car_Path_Stop_Point[i] = temp.size();
					
					
					
				}

				s_check = true;
				System.out.println("찾기 버튼");
				myPanel.repaint();
			}
		});
		//********************************************************************************************************************
		
		
		/**
		 * 화면구성
		 */
		add(myPanel, BorderLayout.CENTER);
		setSize(2000, 1000);				//프레임 크기지정
		setVisible(true);					//프레임 보이기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//프레임 정상종료가능
		//********************************************************************************************************************
		
		
	}
	
	/**
	 * 그래프 생성 함수
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
		 * 화면 초기화
		 */
		g.setColor(Color.white);
		g.fillRect(0, 0, 2000, 1000);
		
		/**
		 * 노드 추가
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
		  * 엣지 추가
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
					g.setColor(Color.RED);
					if(!randCarArray.isEmpty())
					{
						
						
						for(int i=0; i<randCarArray.size(); i++){
							g.fillRect((int)randCarArray.get(i).getNormalizedX()/RATIO -2, (int)randCarArray.get(i).getNormalizedY()/RATIO-2, 25 / RATIO, 25 / RATIO);
							//System.out.println(i+"랜덤찍는다");
						}
						
						/**
						 * 쿼리 위치 그리기
						 */
						g.setColor(Color.BLUE);
						g.fillRect((int)userQ.getNormalizedX()/RATIO -2, (int)userQ.getNormalizedY()/RATIO-2, 40 / RATIO, 40 / RATIO);
						
					}
					//********************************************************************************************************************
					
					/**
					 * 최단 거리 자동차표시
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
					
					
					
					
					
					//********************************************************************************************************************
					
	}
	
	
}

}


















