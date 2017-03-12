package project;

import java.io.Serializable;

class Car implements Serializable{
	
	private int nodeID;			//자동차가 위치한 노드위치
	private int[] neighborNode;
	private int second;			//자동차 위치 송신으로 부터 흐른시간
	private int carId;			//자동차 고유번호
	
	private double point_x;		//자동차 위치
	private double point_y;
	private double km;			//자동차 시속
	
	/**
	 * 자동차 고유번호
	 */
	Car(int carId){
		this.carId = carId;
	}
	
	/**
	 * 자동차 고유번호 반환
	 */
	public int get_Car_Id(){
		return carId;
	}
	
	
	/**
	 * 차가 위치할 노드의 위치아이디
	 */
	public void setNodeID(int data) {
    	nodeID = data;
    }
	
	/**
	 * 차량의 시속
	 */
	public void setKm(int km){
		this.km = km;
		this.km = km;
	}
	public double getKm(){
		return km;
	}
	
	
	
	/**
	 * 차량 위치
	 */
	public void setPoint_x(double x){
		point_x = x;
	}
	public void setPoint_y(double y){
		point_y = y;
	}
	public double getPoint_x(){
		return point_x;
	}
	public double getPoint_y(){
		return point_y;
	}
	public int getNodeID(){
		return nodeID;
	}
	
	/**
	 *  차량이 갈 수 있는 주변 노드
	 */
	public void setNeighborNode(int nodeCount){
		neighborNode = new int[nodeCount];
		for(int i=0; i<nodeCount; i++){
			//nodeCount[i] = 
		}
	}
	
	
}
