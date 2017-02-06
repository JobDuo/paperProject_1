package project;

import java.util.ArrayList;

class KnnAlgorithm {

	//�������� ���� ��ġ�κ��� �����Ÿ��� ���� ����� ���� k + a ���� ����ȣ�� ��ȯ�Ѵ�.

	int[] nodeNum;
	int bigTemp;
	
	double query_x;
	double query_y;
	double carElement_x;
	double carElement_y;
	double base;
	double side;
	double diagonal;
	double big;
	
	double[] shortestDist;
	
	//ArrayList<Car> carArray = new ArrayList<Car>();		//���� �ڵ��� ��ü
	Car[] thisCarArray;
	AddMapData addMapData = new AddMapData();
	ArrayList<Car> nearCarArray = new ArrayList<Car>();
	int temp = 1;
	public ArrayList<Car> KnnAlgorithm(SF_cnode queryP, ArrayList<Car> carArray, int k, int totalCarNum){
		
		//�� ������ �� ���� ã���� �ϴ� ������ ���� �� ������ �ĺ����� ���� �ʿ䰡����.
		if(totalCarNum/2 >= k){
			temp = 2;
		}else {
			temp = 1;
		}
		nodeNum = new int[k*temp];
		thisCarArray = new Car[k*temp];
		shortestDist = new double[k*temp];
		
		
		query_x = queryP.getNormalizedX();
		query_y = queryP.getNormalizedY();
		
		for(int i=0; i<carArray.size(); i++){
			
			
			carElement_x = carArray.get(i).getPoint_x();
			carElement_y = carArray.get(i).getPoint_y();
			
			base = Math.pow(query_x - carElement_x, 2);
			side = Math.pow(query_y - carElement_y, 2);
			diagonal = base + side;
			
			if(i < (k*temp)){
				thisCarArray[i] = carArray.get(i);
				thisCarArray[i].setNodeID(carArray.get(i).getNodeID());
				shortestDist[i] = diagonal;
			}else {
				for(int j=0; j<k*temp; j++){	//�տ��� ä�� k*2 ������ �Ÿ��� ���� ��� ����
					if(big < shortestDist[j]){
						   big = shortestDist[j];
						   bigTemp = j;
					   }
				}big = 0;
				
				if(shortestDist[bigTemp] > diagonal){
					   shortestDist[bigTemp] = diagonal;
					   thisCarArray[bigTemp].setNodeID(carArray.get(i).getNodeID());
				   }
				
			}
		}
		
		
		//addMapData.addNearCar(thisCarArray);	//����� ���� ����
		/**
		 * knn �˰����� ������ �����߰�
		 */
			for(int i=0; i<thisCarArray.length; i++){
				nearCarArray.add(thisCarArray[i]);
			}
			
		
		return nearCarArray;
	}
	
}
/*
if(i < 3){	//�迭 3���� ������ ����
	   shortestCar[i] = Integer.parseInt(END);//3���� ������ ����
	   shortestDist[i] = dist;				  //3������ �Ÿ�
}else {
	   
	   itemp = Integer.parseInt(END);
	   
	   for(int j=0; j<3; j++){		//�տ��� ä�� 3������ �Ÿ��� ���� ��� ����
		   if(big < shortestDist[j]){
			   big = shortestDist[j];
			   bigTemp = j;
		   }
	   }big = 0;
	   
	   if(shortestDist[bigTemp] > dist){
		   shortestDist[bigTemp] = dist;
		   shortestCar[bigTemp] = itemp;
	   }
	   
	   
	   //shortestCar[0] = Integer.parseInt(END);
	   //shortestCar �� 3���� ���� ū ���� small�� �ְ� �����Ѵ�.
	   //small = dist;
	   
	   
}*/