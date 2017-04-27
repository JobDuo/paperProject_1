package project;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

class AddMapData {


	ArrayList<String[]> edges;	//엣지
	ArrayList<String[]> nodes;  //로드
	LoadFile loadFile = new LoadFile();//파일 로드

	
	Car car;
	ArrayList<Car> carArray = new ArrayList<Car>();			//실제 자동차 객체
	ArrayList<Car> nearCarArray = new ArrayList<Car>();			//실제 자동차 객체
	
	
	Random random = new Random();

	private static File cedgeFile = new File("./s_edge.txt");
	private static File cnodeFile = new File("./s_node.txt");
	ArrayList<SF_cnode> nodeArray = new ArrayList<SF_cnode>();		//지도 점 정보
	ArrayList<SF_cedge> edgeArray = new ArrayList<SF_cedge>();		//지도 선 정보
	ArrayList<SF_cnode> randCarArray = new ArrayList<SF_cnode>();
	
	int temp;
	
	/**
	 * 생성자 노드와 엣지 생성
	 */
	public AddMapData() {
		// TODO Auto-generated constructor stub
		add_Node();
		add_Edge();
	}
	
	/**
	 * 노드 추가
	 */
	public void add_Node(){
		
		nodes = loadFile.LoadFile(cnodeFile, 3);
		 for (int i = 0; i < nodes.size(); i++) {
	            String[] sArray = (String[])nodes.get(i);
	            SF_cnode theNode = new SF_cnode();
	            theNode.setNodeID((new Integer(sArray[0])).intValue());
	            theNode.setNormalizedX((new Double(sArray[1])).doubleValue());
	            theNode.setNormalizedY((new Double(sArray[2])).doubleValue());
	            
	            nodeArray.add(theNode);
	    
	        }
		
		//********************************************************************************************************************
	}
	/**
	 * 노드 반환
	 */
	public ArrayList<SF_cnode> get_Node(){
		 return nodeArray;
	}
	
	public void add_Edge(){
		 /**
		  * 엣지 추가
		  */
		 		edges = loadFile.LoadFile(cedgeFile, 4);
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
	}
	/**
	 * 엣지 반환
	 */
	public ArrayList<SF_cedge> get_Edge(){
		 return edgeArray;
	}
	
	/**
	 * 차량 추가
	 * @return 
	 */
	public ArrayList<Car> addRandCar(int count){
		randCarArray.clear();
		//임의 차량 추가
		
		
		/*
		for(int i=0; i<count; i++){
			temp = random.nextInt(nodeArray.size());	//랜덤 차량 생성
			//temp = 16482;
			randCarArray.add(nodeArray.get(temp));		//차량의 노드 위치
			
			car = new Car(i);
			car.setNodeID(randCarArray.get(i).getNodeID());
			car.setPoint_x(randCarArray.get(i).getNormalizedX());
			car.setPoint_y(randCarArray.get(i).getNormalizedY());
			carArray.add(car);
			
		}
		*/
		
		randCarArray.add(nodeArray.get(5412));		//차량의 노드 위치
		car = new Car(0);
		car.setNodeID(randCarArray.get(0).getNodeID());
		car.setPoint_x(randCarArray.get(0).getNormalizedX());
		car.setPoint_y(randCarArray.get(0).getNormalizedY());
		
		car.set_Car_Dist(130);
		carArray.add(car);
		
		randCarArray.add(nodeArray.get(16648));		//차량의 노드 위치
		car = new Car(1);
		car.setNodeID(randCarArray.get(1).getNodeID());
		car.setPoint_x(randCarArray.get(1).getNormalizedX());
		car.setPoint_y(randCarArray.get(1).getNormalizedY());
		carArray.add(car);
		
		
		randCarArray.add(nodeArray.get(6268));		//차량의 노드 위치
		car = new Car(2);
		car.setNodeID(randCarArray.get(2).getNodeID());
		car.setPoint_x(randCarArray.get(2).getNormalizedX());
		car.setPoint_y(randCarArray.get(2).getNormalizedY());
		carArray.add(car);
		
		

		randCarArray.add(nodeArray.get(9259));		//차량의 노드 위치
		car = new Car(3);
		car.setNodeID(randCarArray.get(3).getNodeID());
		car.setPoint_x(randCarArray.get(3).getNormalizedX());
		car.setPoint_y(randCarArray.get(3).getNormalizedY());
		carArray.add(car);
		

		randCarArray.add(nodeArray.get(5959));		//차량의 노드 위치
		car = new Car(4);
		car.setNodeID(randCarArray.get(4).getNodeID());
		car.setPoint_x(randCarArray.get(4).getNormalizedX());
		car.setPoint_y(randCarArray.get(4).getNormalizedY());
		carArray.add(car);
		
		
		randCarArray.add(nodeArray.get(17863));		//차량의 노드 위치
		car = new Car(5);
		car.setNodeID(randCarArray.get(5).getNodeID());
		car.setPoint_x(randCarArray.get(5).getNormalizedX());
		car.setPoint_y(randCarArray.get(5).getNormalizedY());
		carArray.add(car);
		
		randCarArray.add(nodeArray.get(16020));		//차량의 노드 위치
		car = new Car(6);
		car.setNodeID(randCarArray.get(6).getNodeID());
		car.setPoint_x(randCarArray.get(6).getNormalizedX());
		car.setPoint_y(randCarArray.get(6).getNormalizedY());
		carArray.add(car);
		
		randCarArray.add(nodeArray.get(16602));		//차량의 노드 위치
		car = new Car(7);
		car.setNodeID(randCarArray.get(7).getNodeID());
		car.setPoint_x(randCarArray.get(7).getNormalizedX());
		car.setPoint_y(randCarArray.get(7).getNormalizedY());
		carArray.add(car);


		randCarArray.add(nodeArray.get(7085));		//차량의 노드 위치
		car = new Car(8);
		car.setNodeID(randCarArray.get(8).getNodeID());
		car.setPoint_x(randCarArray.get(8).getNormalizedX());
		car.setPoint_y(randCarArray.get(8).getNormalizedY());
		
		car.set_Car_Dist(130);
		
		carArray.add(car);
		
		
		
		
		
		return carArray;
	}

	
	/**
	 * 통신으로 차량추가
	 */
	//int transportation_Car_Count = 0;	//통신 차량 대수
	public void set_Transportation_Car(Car car2){
		
		car = new Car(car2.get_Car_Id());
		car.setNodeID(car2.getNodeID());
		
		//randCarArray.add(nodeArray.get(random.nextInt(nodeArray.size())));		//차량의 노드 위치 -> 차량이 자신의 노드 위치 정보도 함께 보내게 되면 아래 2줄도 변경해야함 현재는 랜덤값이기 때문에 가능
		// 수정 필요함
		
		car.setPoint_x(nodeArray.get(car.getNodeID()).getNormalizedX());	//노드 위치를 통해 위치 확인
		car.setPoint_y(nodeArray.get(car.getNodeID()).getNormalizedY());
		
		carArray.add(car);
		
		

		System.out.println("carArray = " + carArray.size());
		//return car;
		//서버 백그라운드에서 본 함수를 호출해서 차량 추가한후 메인뷰에서 그려준다.
	}
	//통신을 통해 셋팅된 차량을 리턴한다.
	public ArrayList<Car> get_Transportation_Car(){
		return carArray;	
	}
	
	
	
	/**
	 * 그래프 생성
	 */
	
	/**
	 * 그래프 생성 함수
	 * @param theEdge
	 */
	 private Graph.mapLine[] GRAPH = new Graph.mapLine[23874];
	 public Graph.mapLine[] edge2Graph(SF_cedge theEdge)
	 {
	    	SF_cnode theNode1 = nodeArray.get(theEdge.getStart_NodeID());
	               
	        assert theNode1 != null;
	        SF_cnode theNode2 = nodeArray.get(theEdge.getEnd_NodeID());
	        
	        assert theNode2 != null;
	    		GRAPH[theEdge.getEdgeID()] = new Graph.mapLine(theEdge.getStart_NodeID(),theEdge.getEnd_NodeID()
	    				,theEdge.getL2Distance());
	    		
	        return GRAPH;
	  }
	//********************************************************************************************************************


	
	
	
	
	
	
	
	
	
}























