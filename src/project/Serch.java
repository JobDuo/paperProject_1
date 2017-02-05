package project;

import java.util.ArrayList;

class Serch {

	/**
	 * 근접 차량 찾기
	 */

	ArrayList<ArrayList<Integer>> shortest_Car_Path;	//최근접 차량 패스 저장
	int s_resultID[] ;
	int[] Car_Path_Stop_Point;
	
	/**
	 * 가장 근접한 차량 k 대를 찾는다.
	 */
	public int[] serchShortestCar(Graph graph, SF_cnode userQ, ArrayList<SF_cnode> randCarArray, int NUM_OF_SEARCHING){
		s_resultID = new int[NUM_OF_SEARCHING];
		
		
		graph.dijkstra(userQ.getNodeID());
		
		shortest_Car_Path = new ArrayList<ArrayList<Integer>>();	//k대의 근접 차량 패스를 저장
		
		graph.clear_List();	//클리어 함수 안해주면 패스 중복된다.
		s_resultID = graph.printQ1(NUM_OF_SEARCHING,randCarArray);
		
		/**
		 * 가장 가깝게 찾아온 차량 객체의 아이디를 통해 패스를 그림
		 */
		
		
		
		
		return s_resultID;
	}
	
	/**
	 * 차량의 패스를 가져온다.
	 */
	
	public ArrayList<ArrayList<Integer>> serchShortestCarPath(Graph graph, int NUM_OF_SEARCHING){

		Car_Path_Stop_Point = new int[NUM_OF_SEARCHING];			//패스 그릴때 마지막 지점과 첫 지점 연결되는것 없애기 위해서
		
		for(int i=0; i<NUM_OF_SEARCHING; i++){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp = graph.printQ2(s_resultID[i]);
			shortest_Car_Path.add(temp);
			Car_Path_Stop_Point[i] = temp.size();
		}
		
		
		return shortest_Car_Path;
	}
	
	/**
	 * 패스가 연결되는 부분을 파악하기 위해 사용
	 * @return
	 */
	public int[] car_Path_Stop_Point(){
		
		
		return Car_Path_Stop_Point;
	}
	
}
