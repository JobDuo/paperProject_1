package project;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

class AddMapData {


	ArrayList<String[]> edges;	//����
	ArrayList<String[]> nodes;  //�ε�
	LoadFile loadFile = new LoadFile();//���� �ε�

	
	Car car;
	ArrayList<Car> carArray = new ArrayList<Car>();			//���� �ڵ��� ��ü
	ArrayList<Car> nearCarArray = new ArrayList<Car>();			//���� �ڵ��� ��ü
	
	
	Random random = new Random();

	private static File cedgeFile = new File("./s_edge.txt");
	private static File cnodeFile = new File("./s_node.txt");
	ArrayList<SF_cnode> nodeArray = new ArrayList<SF_cnode>();		//���� �� ����
	ArrayList<SF_cedge> edgeArray = new ArrayList<SF_cedge>();		//���� �� ����
	ArrayList<SF_cnode> randCarArray = new ArrayList<SF_cnode>();
	
	int temp;
	
	/**
	 * ������ ���� ���� ����
	 */
	public AddMapData() {
		// TODO Auto-generated constructor stub
		add_Node();
		add_Edge();
	}
	
	/**
	 * ��� �߰�
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
	 * ��� ��ȯ
	 */
	public ArrayList<SF_cnode> get_Node(){
		 return nodeArray;
	}
	
	public void add_Edge(){
		 /**
		  * ���� �߰�
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
	 * ���� ��ȯ
	 */
	public ArrayList<SF_cedge> get_Edge(){
		 return edgeArray;
	}
	
	/**
	 * ���� �߰�
	 * @return 
	 */
	public ArrayList<Car> addRandCar(int count){
		randCarArray.clear();
		//���� ���� �߰�
		
		
		/*
		for(int i=0; i<count; i++){
			temp = random.nextInt(nodeArray.size());	//���� ���� ����
			//temp = 16482;
			randCarArray.add(nodeArray.get(temp));		//������ ��� ��ġ
			
			car = new Car(i);
			car.setNodeID(randCarArray.get(i).getNodeID());
			car.setPoint_x(randCarArray.get(i).getNormalizedX());
			car.setPoint_y(randCarArray.get(i).getNormalizedY());
			carArray.add(car);
			
		}
		*/
		
		randCarArray.add(nodeArray.get(5412));		//������ ��� ��ġ
		car = new Car(0);
		car.setNodeID(randCarArray.get(0).getNodeID());
		car.setPoint_x(randCarArray.get(0).getNormalizedX());
		car.setPoint_y(randCarArray.get(0).getNormalizedY());
		
		car.set_Car_Dist(130);
		carArray.add(car);
		
		randCarArray.add(nodeArray.get(16648));		//������ ��� ��ġ
		car = new Car(1);
		car.setNodeID(randCarArray.get(1).getNodeID());
		car.setPoint_x(randCarArray.get(1).getNormalizedX());
		car.setPoint_y(randCarArray.get(1).getNormalizedY());
		carArray.add(car);
		
		
		randCarArray.add(nodeArray.get(6268));		//������ ��� ��ġ
		car = new Car(2);
		car.setNodeID(randCarArray.get(2).getNodeID());
		car.setPoint_x(randCarArray.get(2).getNormalizedX());
		car.setPoint_y(randCarArray.get(2).getNormalizedY());
		carArray.add(car);
		
		

		randCarArray.add(nodeArray.get(9259));		//������ ��� ��ġ
		car = new Car(3);
		car.setNodeID(randCarArray.get(3).getNodeID());
		car.setPoint_x(randCarArray.get(3).getNormalizedX());
		car.setPoint_y(randCarArray.get(3).getNormalizedY());
		carArray.add(car);
		

		randCarArray.add(nodeArray.get(5959));		//������ ��� ��ġ
		car = new Car(4);
		car.setNodeID(randCarArray.get(4).getNodeID());
		car.setPoint_x(randCarArray.get(4).getNormalizedX());
		car.setPoint_y(randCarArray.get(4).getNormalizedY());
		carArray.add(car);
		
		
		randCarArray.add(nodeArray.get(17863));		//������ ��� ��ġ
		car = new Car(5);
		car.setNodeID(randCarArray.get(5).getNodeID());
		car.setPoint_x(randCarArray.get(5).getNormalizedX());
		car.setPoint_y(randCarArray.get(5).getNormalizedY());
		carArray.add(car);
		
		randCarArray.add(nodeArray.get(16020));		//������ ��� ��ġ
		car = new Car(6);
		car.setNodeID(randCarArray.get(6).getNodeID());
		car.setPoint_x(randCarArray.get(6).getNormalizedX());
		car.setPoint_y(randCarArray.get(6).getNormalizedY());
		carArray.add(car);
		
		randCarArray.add(nodeArray.get(16602));		//������ ��� ��ġ
		car = new Car(7);
		car.setNodeID(randCarArray.get(7).getNodeID());
		car.setPoint_x(randCarArray.get(7).getNormalizedX());
		car.setPoint_y(randCarArray.get(7).getNormalizedY());
		carArray.add(car);


		randCarArray.add(nodeArray.get(7085));		//������ ��� ��ġ
		car = new Car(8);
		car.setNodeID(randCarArray.get(8).getNodeID());
		car.setPoint_x(randCarArray.get(8).getNormalizedX());
		car.setPoint_y(randCarArray.get(8).getNormalizedY());
		
		car.set_Car_Dist(130);
		
		carArray.add(car);
		
		
		
		
		
		return carArray;
	}

	
	/**
	 * ������� �����߰�
	 */
	//int transportation_Car_Count = 0;	//��� ���� ���
	public void set_Transportation_Car(Car car2){
		
		car = new Car(car2.get_Car_Id());
		car.setNodeID(car2.getNodeID());
		
		//randCarArray.add(nodeArray.get(random.nextInt(nodeArray.size())));		//������ ��� ��ġ -> ������ �ڽ��� ��� ��ġ ������ �Բ� ������ �Ǹ� �Ʒ� 2�ٵ� �����ؾ��� ����� �������̱� ������ ����
		// ���� �ʿ���
		
		car.setPoint_x(nodeArray.get(car.getNodeID()).getNormalizedX());	//��� ��ġ�� ���� ��ġ Ȯ��
		car.setPoint_y(nodeArray.get(car.getNodeID()).getNormalizedY());
		
		carArray.add(car);
		
		

		System.out.println("carArray = " + carArray.size());
		//return car;
		//���� ��׶��忡�� �� �Լ��� ȣ���ؼ� ���� �߰����� ���κ信�� �׷��ش�.
	}
	//����� ���� ���õ� ������ �����Ѵ�.
	public ArrayList<Car> get_Transportation_Car(){
		return carArray;	
	}
	
	
	
	/**
	 * �׷��� ����
	 */
	
	/**
	 * �׷��� ���� �Լ�
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























