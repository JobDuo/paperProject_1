package project;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientGui extends JFrame implements ActionListener{


	
	private Car car;											//자동차
	private ArrayList<Car> CarArray = new ArrayList<Car>();		//자동차 담을 배열
	private int Car_Count = 10;									//컨트롤 할 자동차 개수
	
	JButton car_Add_Button = new JButton("차랜덤생성");		//자동차 생성버튼
	JButton car_State_Button = new JButton("차상태전송");		//차상태전송
	JButton car_Chose_Node_Add_Button = new JButton("차1대 추가");		//차상태전송
	
	
	JTextField car_Count_Input = new JTextField(5); 	//자동차 갯수 입력 텍스트
	JTextField car_Node_Input = new JTextField(10); 	//자동차 갯수 입력 텍스트
	
	
	//gui 연동
	private ClientBackground clientBackground;
	
	public ClientGui(){
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
		setTitle("클라이언트 입니다.");
		
		car_Count_Input.setBounds(30, 30, 100, 50);
		car_Add_Button.setBounds(140, 30, 100, 50);
		car_State_Button.setBounds(30, 100, 100, 50);
		car_Node_Input.setBounds(30, 170, 100, 50);
		car_Chose_Node_Add_Button.setBounds(140, 170, 100, 50);
		
		add(car_Count_Input);
		add(car_Add_Button);
		add(car_State_Button);
		add(car_Chose_Node_Add_Button);
		add(car_Node_Input);
		
		clientBackground = new ClientBackground();
		clientBackground.setGui(this);
	
		setBounds(800,100,400,600);
		
		Random random = new Random();	
		
		/**
		 * 차량추가 버튼
		 */
		car_Add_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				/**
				 *  자동차 대수 만큼 새롭게 생성한다.
				 */
				
				Car_Count = Integer.parseInt(car_Count_Input.getText());
				
				for(int i=0; i<Car_Count; i++){
				car = new Car(i);
				
				/**
				 * 자동차의 노드를 삽입하고 전송한다.
				 * 추후에 위치 정보를 가지고 근처에있는 노드를 찾는 방식으로 변경하든지 해야 할듯 
				 * 일단 노드 정보만 랜덤으로 추가해서 서버로 전송하게되고, 서버에서 추가 정보를 삽입한다.
				 */
				int temp = random.nextInt(18263);
				car.setNodeID(temp);
				
				CarArray.add(car);
				clientBackground.connet(CarArray.get(i));
				}
			}
		});
		
		/**
		 * 차 상태 전송 버튼
		 */
		car_State_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//모든 자동차의 상태를 전송한다.
				for(int i=0; i<Car_Count; i++){
				clientBackground.sendMessage(CarArray.get(i));
				}
			}
		});
		
		/**
		 * 차 한대 노드위치받아서 추가
		 */
		car_Chose_Node_Add_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				car = new Car(Car_Count);
				car.setNodeID(Integer.parseInt(car_Node_Input.getText()));
				
				/**
				 * 자동차위치도 알아야한다.
				 */
				
				
				
				CarArray.add(car);
				clientBackground.sendMessage(CarArray.get(Car_Count));
				Car_Count++;
			}
		});
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {	//메세지 센드
		// TODO Auto-generated method stub

		
	}	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new ClientGui();
	}



}













