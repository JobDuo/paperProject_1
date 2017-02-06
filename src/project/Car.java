package project;

class Car {
	
	private int nodeID;
	private int[] neighborNode;
	
	private double point_x;
	private double point_y;
	
	/**
	 * 차가 위치할 노드의 위치아이디
	 */
	public void setNodeID(int data) {
    	nodeID = data;
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
