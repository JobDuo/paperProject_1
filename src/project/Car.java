package project;

import java.io.Serializable;

class Car implements Serializable{
	
	private int nodeID;			//�ڵ����� ��ġ�� �����ġ
	private int[] neighborNode;
	private int second;			//�ڵ��� ��ġ �۽����� ���� �帥�ð�
	private int carId;			//�ڵ��� ������ȣ
	
	private double point_x;		//�ڵ��� ��ġ
	private double point_y;
	private double km;			//�ڵ��� �ü�
	
	/**
	 * �ڵ��� ������ȣ
	 */
	Car(int carId){
		this.carId = carId;
	}
	
	/**
	 * �ڵ��� ������ȣ ��ȯ
	 */
	public int get_Car_Id(){
		return carId;
	}
	
	
	/**
	 * ���� ��ġ�� ����� ��ġ���̵�
	 */
	public void setNodeID(int data) {
    	nodeID = data;
    }
	
	/**
	 * ������ �ü�
	 */
	public void setKm(int km){
		this.km = km;
		this.km = km;
	}
	public double getKm(){
		return km;
	}
	
	
	
	/**
	 * ���� ��ġ
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
	 *  ������ �� �� �ִ� �ֺ� ���
	 */
	public void setNeighborNode(int nodeCount){
		neighborNode = new int[nodeCount];
		for(int i=0; i<nodeCount; i++){
			//nodeCount[i] = 
		}
	}
	
	
}
