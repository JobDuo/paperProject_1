package project;

import java.util.ArrayList;

class KnnAlgorithm {

	//지도에서 질의 위치로부터 직선거리로 가장 가까운 차량 k + a 대의 노드번호를 반환한다.

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
	
	//ArrayList<Car> carArray = new ArrayList<Car>();		//실제 자동차 객체
	Car[] thisCarArray;
	AddMapData addMapData = new AddMapData();
	ArrayList<Car> nearCarArray = new ArrayList<Car>();
	int temp = 1;
	public ArrayList<Car> KnnAlgorithm(SF_cnode queryP, ArrayList<Car> carArray, int k, int totalCarNum){
		
		//총 차량의 반 보다 찾고자 하는 차량의 수가 더 많으면 후보군을 뽑을 필요가없다.
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
				for(int j=0; j<k*temp; j++){	//앞에서 채운 k*2 차량의 거리중 가장 긴것 선택
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
		
		
		//addMapData.addNearCar(thisCarArray);	//가까운 차량 정보
		/**
		 * knn 알고리즘후 근접한 차량추가
		 */
			for(int i=0; i<thisCarArray.length; i++){
				nearCarArray.add(thisCarArray[i]);
			}
			
		
		return nearCarArray;
	}
	
}
/*
if(i < 3){	//배열 3개의 정보를 넣음
	   shortestCar[i] = Integer.parseInt(END);//3차량 정보를 넣음
	   shortestDist[i] = dist;				  //3차량의 거리
}else {
	   
	   itemp = Integer.parseInt(END);
	   
	   for(int j=0; j<3; j++){		//앞에서 채운 3차량의 거리중 가장 긴것 선택
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
	   //shortestCar 의 3개중 가장 큰 값을 small에 넣고 변경한다.
	   //small = dist;
	   
	   
}*/