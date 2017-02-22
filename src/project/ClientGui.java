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


	
	private Car car;											//�ڵ���
	private ArrayList<Car> CarArray = new ArrayList<Car>();		//�ڵ��� ���� �迭
	private int Car_Count = 10;									//��Ʈ�� �� �ڵ��� ����
	
	JButton car_Add_Button = new JButton("����������");		//�ڵ��� ������ư
	JButton car_State_Button = new JButton("����������");		//����������
	JButton car_Chose_Node_Add_Button = new JButton("��1�� �߰�");		//����������
	
	
	JTextField car_Count_Input = new JTextField(5); 	//�ڵ��� ���� �Է� �ؽ�Ʈ
	JTextField car_Node_Input = new JTextField(10); 	//�ڵ��� ���� �Է� �ؽ�Ʈ
	
	
	//gui ����
	private ClientBackground clientBackground;
	
	public ClientGui(){
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
		setTitle("Ŭ���̾�Ʈ �Դϴ�.");
		
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
		 * �����߰� ��ư
		 */
		car_Add_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				/**
				 *  �ڵ��� ��� ��ŭ ���Ӱ� �����Ѵ�.
				 */
				
				Car_Count = Integer.parseInt(car_Count_Input.getText());
				
				for(int i=0; i<Car_Count; i++){
				car = new Car(i);
				
				/**
				 * �ڵ����� ��带 �����ϰ� �����Ѵ�.
				 * ���Ŀ� ��ġ ������ ������ ��ó���ִ� ��带 ã�� ������� �����ϵ��� �ؾ� �ҵ� 
				 * �ϴ� ��� ������ �������� �߰��ؼ� ������ �����ϰԵǰ�, �������� �߰� ������ �����Ѵ�.
				 */
				int temp = random.nextInt(18263);
				car.setNodeID(temp);
				
				CarArray.add(car);
				clientBackground.connet(CarArray.get(i));
				}
			}
		});
		
		/**
		 * �� ���� ���� ��ư
		 */
		car_State_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//��� �ڵ����� ���¸� �����Ѵ�.
				for(int i=0; i<Car_Count; i++){
				clientBackground.sendMessage(CarArray.get(i));
				}
			}
		});
		
		/**
		 * �� �Ѵ� �����ġ�޾Ƽ� �߰�
		 */
		car_Chose_Node_Add_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				car = new Car(Car_Count);
				car.setNodeID(Integer.parseInt(car_Node_Input.getText()));
				
				/**
				 * �ڵ�����ġ�� �˾ƾ��Ѵ�.
				 */
				
				
				
				CarArray.add(car);
				clientBackground.sendMessage(CarArray.get(Car_Count));
				Car_Count++;
			}
		});
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {	//�޼��� ����
		// TODO Auto-generated method stub

		
	}	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new ClientGui();
	}



}













