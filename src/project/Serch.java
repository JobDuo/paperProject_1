package project;

import java.util.ArrayList;

class Serch {

	/**
	 * ���� ���� ã��
	 */

	ArrayList<ArrayList<Integer>> shortest_Car_Path;	//�ֱ��� ���� �н� ����
	ArrayList<Integer> s_resultID;
	int[] Car_Path_Stop_Point;
	
	/**
	 * ���� ������ ���� k �븦 ã�´�.
	 */
	public ArrayList<Integer> serchShortestCar(Graph graph, SF_cnode userQ, ArrayList<Car> CarArray, int NUM_OF_SEARCHING){
		//s_resultID = new int[NUM_OF_SEARCHING];
		s_resultID = new ArrayList<Integer>();
		
		graph.dijkstra(userQ.getNodeID());
		
		shortest_Car_Path = new ArrayList<ArrayList<Integer>>();	//k���� ���� ���� �н��� ����
		
		graph.clear_List();	//Ŭ���� �Լ� �����ָ� �н� �ߺ��ȴ�.
		s_resultID = graph.printQ1(NUM_OF_SEARCHING, CarArray);
		
		/**
		 * ���� ������ ã�ƿ� ���� ��ü�� ���̵� ���� �н��� �׸�
		 */
		//System.out.println(graph.getLinkNode());
		
		return s_resultID;
	}

	
	
	/**
	 * ���� ������ ������ �Ÿ� ǥ��
	 */
	ArrayList<Double> shortestDist = new ArrayList<Double>();
	public ArrayList<Double> serchShortestCarDisk(Graph graph){
		shortestDist = graph.get_Shortest_K_Car_Dist();
		return shortestDist;
	}
	
	
	/**
	 * ������ �н��� �����´�.
	 */
	public ArrayList<ArrayList<Integer>> serchShortestCarPath(Graph graph, int NUM_OF_SEARCHING){

		Car_Path_Stop_Point = new int[NUM_OF_SEARCHING];			//�н� �׸��� ������ ������ ù ���� ����Ǵ°� ���ֱ� ���ؼ�
		
		for(int i=0; i<NUM_OF_SEARCHING; i++){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp = graph.printQ2(s_resultID.get(i));
			shortest_Car_Path.add(temp);
			Car_Path_Stop_Point[i] = temp.size();
		}
		
		
		return shortest_Car_Path;
	}
	
	/**
	 * �н��� ����Ǵ� �κ��� �ľ��ϱ� ���� ���
	 * @return
	 */
	public int[] car_Path_Stop_Point(){
		
		return Car_Path_Stop_Point;
	}
	
}
