package Editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import javax.swing.*;
import java.awt.event.*;

public class Editor_007 extends JFrame {
	public static int[][][] TileArray = new int[1024][512][136];
	//--------------------Objects----------------------
	public static String[] ObjectsName = new String[7208];
	public static int[][] ObjectsInfo = new int[7208][7];// Размер и точка привязки объекта
	public static int [][] numbOfObjects = new int[40][40];// Количество объектов на плитке
	public static int numbOfObjectsAll = 0;
	public static int [][] ObjectsAll = new int[24000][13];// Объекты
	public static int [][] sizeOfObjects = new int[7300][2];// Размер объектов
	public static int [] copyObj = new int[36];// Временный массив для копирования объекта [8]
	public static int countObjects = 0;
	public static int [][] Objects = new int[200000][28];
	public static String objectName = "";
	//-------------------------------------------------
	public static int [][] TexturesFull = new int[40][40];// Полные текстуры
	public static int [][] TexturesHalf = new int[40][40];// Половинчатые текстуры
	public static int [][] TileType = new int[40][40];// Тип плитки
	public static int countObjectsInFolder = 7208;
	public static int centerPanelSizeX = 700;
	public static int centerPanelSizeY = 700;
	public static int yCor = 909;// Координата нулевой плитки
	public static int xCor = 148;// Координата нулевой плитки
	public static int scrOffsetY = 6;//Смещение экрана по Y
	public static int scrOffsetX = 4;//Смещение экрана по X
	public static int dXY = 0;// Положение объекта
	public static int dY = 0;// Положение объекта
	public static int dX = 0;// Положение объекта
	public static int selectedObject = -1;// Выбранный объектa
	public static int mouseX = 0;
	public static int mouseY = 0;
	public static String title ="Window"; 
	public static String Text = "floor//000000.png";
	public static String Text3 = "";
	public static String gameDirectory = "";
	public static String saveName = "";
	public static String fileExt = "";
	public static String inpFile = "F:\\SteamLibrary\\steamapps\\common\\divine_divinity\\savegames\\Test_10\\world.x0";
	public static String outFile = "F:\\SteamLibrary\\steamapps\\common\\divine_divinity\\savegames\\Test_10\\world.x0";
	public static String ObjFile = "F:\\SteamLibrary\\steamapps\\common\\divine_divinity\\savegames\\Test_10\\objects.x0";
	public static String inpObjFile = "objects.de";
	public static String consoleText = "";
	public static String infoText = "info";
	
	public static int texturePage = 0; // Страница текстур
	public static int objectPage = 0; // Страница объектов
	public static int [] selectedTextures = new int[32]; //Список выбранных текстур, нулевой элемент - кол-во выбранных текстур
	public static int selectObject = -1;
	//--------------------FLAGS----------------------
	public static boolean showObjectFormTile = true;// Показать\убрать рамку объектов на плитке
	public static boolean showObjectForm = false;// Показать\убрать рамку всех объектов
	public static boolean showObj = true;// Показать\убрать количество объектов на плитке
	public static boolean showTileType = false;// Показать\убрать тип плитки (туман, пол...)
	public static boolean showGridTile = false;// Показать\убрать сетку плитки
	public static boolean showHeight = false;// Показать\убрать высоту
	public static boolean readyToCopy = false;// В временном массиве есть готовый к копированию объект
	public static boolean selectionTextures = false;
	public static boolean selectionObjects = true;
	//--------------------Keys----------------------
    private boolean isLeft = false;
    private boolean isRight = false;
    private boolean isUp = false;
    private boolean isDown = false;
    private boolean isShift = false;
    private boolean isCtrl = false;
	//-----------------------------------------------
	private static final long serialVersionUID = 1L;

	public static void takeTextursAndObjects(int y, int x, int[][][] TileArray){
		numbOfObjectsAll = 0;
		for (int i = y; i < y+centerPanelSizeY/64+1+scrOffsetY; i++) {
			for (int j = x; j < x+centerPanelSizeX/64+1+scrOffsetX; j++) {
				TexturesFull[i-y][j-x] = TileArray[i][j][1]*256 + TileArray[i][j][0];
				TexturesHalf[i-y][j-x] = TileArray[i][j][3]*256 + TileArray[i][j][2];
				TileType[i-y][j-x] = TileArray[i][j][7];
				numbOfObjects[i-y][j-x] = TileArray[i][j][6];// Количество объектов на плитке

				if (TileArray[i][j][6] > 0 && TileArray[i][j][6] < 16) {
					for (int k = 0; k < TileArray[i][j][6]; k++) {
						int numderObject = TileArray[i][j][k*8+22]*64 + TileArray[i][j][k*8+21]/4;
						int objectBiasY = 0;
						int objectBiasX = 0;
						objectBiasX = ObjectsInfo[numderObject][2];
						objectBiasY = ObjectsInfo[numderObject][3];
						dXY = TileArray[i][j][k*8+16] + TileArray[i][j][k*8+17]%16*4*64;
    					dY = dXY/64+(i-yCor - scrOffsetY)*64 + objectBiasY;
    					dX = dXY%64+(j-xCor - scrOffsetX)*64 + objectBiasX;
    					dXY = dX+(64*dY*centerPanelSizeY);
						ObjectsAll [numbOfObjectsAll][0] = numderObject;// номер объекта
						ObjectsAll [numbOfObjectsAll][1] = dXY;
						ObjectsAll [numbOfObjectsAll][2] = dY;//
						ObjectsAll [numbOfObjectsAll][3] = dX;//
						ObjectsAll [numbOfObjectsAll][4] = TileArray[i][j][k*8+20];// высота
						ObjectsAll [numbOfObjectsAll][5] = objectBiasY;// точка положения объекта
						ObjectsAll [numbOfObjectsAll][6] = objectBiasX;// точка положения объекта
						ObjectsAll [numbOfObjectsAll][7] = k;// Номер объекта на плитке
						ObjectsAll [numbOfObjectsAll][8] = i - yCor;//Координата плитки
						ObjectsAll [numbOfObjectsAll][9] = j - xCor;//Координата плитки
						ObjectsAll [numbOfObjectsAll][10] = ObjectsInfo[numderObject][1];
						ObjectsAll [numbOfObjectsAll][11] = ObjectsInfo[numderObject][0];
						ObjectsAll [numbOfObjectsAll][12] = TileArray[i][j][6];//Всего объектов на плитке
						numbOfObjectsAll ++;
					}
				}
				if ((i == (yCor+scrOffsetY+centerPanelSizeY/64/2)) && (j == (xCor+scrOffsetX+centerPanelSizeX/64/2))) {
					infoText = Integer.toString(yCor+scrOffsetY+centerPanelSizeY/64/2) + " / " + Integer.toString(xCor+scrOffsetX+centerPanelSizeX/64/2) + "\n";
					infoText += "Full Textures: " + Integer.toString(TexturesFull[i-y][j-x]) + "\n";
					infoText += "Half Textures: " + Integer.toString(TexturesHalf[i-y][j-x]) + "\n";
					infoText += "Floor type: " + Integer.toString(TileType[i-y][j-x]) + "\n";
					for (int k = 0; k < numbOfObjects[scrOffsetY+centerPanelSizeY/64/2][scrOffsetX+centerPanelSizeX/64/2]; k++) {
						if (selectedObject >= numbOfObjects[scrOffsetY + centerPanelSizeY/64/2][scrOffsetX + centerPanelSizeX/64/2]) selectedObject = 0;
						if (k == selectedObject) infoText += "======="+(k+1)+"=======" + "\n";
						else infoText += "-----------"+(k+1)+"-----------" + "\n";
                		infoText += ObjectsName[TileArray[i][j][k*8+22]*64 + TileArray[i][j][k*8+21]/4] + "\n";
                		infoText += TileArray[i][j][k*8+22]*64 + TileArray[i][j][k*8+21]/4 + "\n";
						dXY = TileArray[i][j][k*8+16] + TileArray[i][j][k*8+17]%16*4*64;
						dY = dXY/64;
						dX = dXY%64;
						infoText += "dXY= " + dXY + "\n";
						infoText += "Y/X= " + dY + "/"+ dX +"\n";
						infoText += "height= "+Integer.toString(TileArray[i][j][k*8+20]) + "\n";
						infoText += "order= "+Integer.toString(TileArray[i][j][k*8+17]/16 + TileArray[i][j][k*8+18]*16 + TileArray[i][j][k*8+19]*4096) + "\n";
						infoText += "X "+Integer.toHexString(dX+j*64).toUpperCase() + "\n";
						infoText += "Y "+Integer.toHexString(dY+i*64).toUpperCase() + "\n";
						consoleText = "";
                    	for (int a = 16; a < 24; a++) {
                    		if (TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+a] < 16) consoleText += "0";
    						consoleText += Integer.toHexString(TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+a]).toUpperCase() + " ";
                    	}
                    	consoleText += "    ";
                    	for (int a = 0; a < 28; a++) {
                    		if (Objects[TileArray[i][j][selectedObject*8+17]/16 + TileArray[i][j][selectedObject*8+18]*16 + TileArray[i][j][selectedObject*8+19]*4096][a] < 16) consoleText += "0";
                    		consoleText += Integer.toHexString(Objects[TileArray[i][j][selectedObject*8+17]/16 + TileArray[i][j][selectedObject*8+18]*16 + TileArray[i][j][selectedObject*8+19]*4096][a]).toUpperCase() + " ";
                    	}
					}
				}
			}
		}
	}
	
	public Editor_007()		//Конструктор класса
	{
		setBounds(500, 100, 1000, 800);
		setMinimumSize(new Dimension(1050, 800));
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		// Создание строки главного меню
        JMenuBar menuBar = new JMenuBar();
        // Добавление в главное меню выпадающих пунктов меню  
        menuBar.add(createViewMenu());
        // Подключаем меню к интерфейсу приложения
        setJMenuBar(menuBar);
		setTitle(title);
		//-----------------Левая панель-------------------------
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		JTextArea infoArea = new JTextArea(10,12);
		infoArea.setFocusable(false);
		JScrollPane leftScroll = new JScrollPane(infoArea);
		infoArea.append(infoText);
		infoText = "";
		leftPanel.add(leftScroll, BorderLayout.CENTER);
		add(leftPanel, BorderLayout.LINE_START);
		//-----------------Центральная панель-------------------
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.LINE_AXIS));
		centerPanel.add(new CenterShowWindow(), BorderLayout.LINE_START);
		//centerPanel.setFocusable(true);		//Ставим фокус на панель с текстурами
		centerPanel.addMouseListener(new MouseAdapter() { //Обработка клика на центральную панель
			@Override
		     public void mousePressed(MouseEvent me) {
				if (selectionTextures == true) {
					if (selectedTextures[0] > 0 && isShift == false) {
		    			mouseX = me.getX();
		    			mouseY = me.getY();
		    			Random random = new Random();
		    			int randTextures = random.nextInt(selectedTextures[0])+1;
		    			TileArray[yCor+scrOffsetY+mouseY/64][xCor+scrOffsetX+mouseX/64][0] = selectedTextures[randTextures] % 256;
		    			TileArray[yCor+scrOffsetY+mouseY/64][xCor+scrOffsetX+mouseX/64][1] = selectedTextures[randTextures] / 256;
		    			takeTextursAndObjects(yCor,xCor,TileArray);
					}
					if (selectedTextures[0] > 0 && isShift == true) {
						mouseX = me.getX()-64;
						mouseY = me.getY()-64;
						Random random = new Random();
						for( int t = 0; t < 9; t=t+3) {
							int randTextures = random.nextInt(selectedTextures[0])+1;
							TileArray[yCor+scrOffsetY+mouseY/64+t/3][xCor+scrOffsetX+mouseX/64+0][0] = selectedTextures[randTextures] % 256;
							TileArray[yCor+scrOffsetY+mouseY/64+t/3][xCor+scrOffsetX+mouseX/64+0][1] = selectedTextures[randTextures] / 256;
							randTextures = random.nextInt(selectedTextures[0])+1;
							TileArray[yCor+scrOffsetY+mouseY/64+t/3][xCor+scrOffsetX+mouseX/64+1][0] = selectedTextures[randTextures] % 256;
							TileArray[yCor+scrOffsetY+mouseY/64+t/3][xCor+scrOffsetX+mouseX/64+1][1] = selectedTextures[randTextures] / 256;
							randTextures = random.nextInt(selectedTextures[0])+1;
							TileArray[yCor+scrOffsetY+mouseY/64+t/3][xCor+scrOffsetX+mouseX/64+2][0] = selectedTextures[randTextures] % 256;
							TileArray[yCor+scrOffsetY+mouseY/64+t/3][xCor+scrOffsetX+mouseX/64+2][1] = selectedTextures[randTextures] / 256;
							takeTextursAndObjects(yCor,xCor,TileArray);
						}
					}
				}
				if (selectObject >=0) {
					mouseX = me.getX();
	    			mouseY = me.getY();
	    			int n = TileArray[yCor+scrOffsetY+mouseY/64][xCor+scrOffsetX+mouseX/64][6];
					if(n < 15 ) {
						System.out.println("Past object " + countObjects);
						copyObj[3] = countObjects << 12;
						copyObj[3] = copyObj[3] >> 24;
						copyObj[2] = countObjects << 20;
						copyObj[2] = copyObj[2] >> 24;
						copyObj[1] = (countObjects % 16)*16;
						TileArray[yCor+scrOffsetY+mouseY/64][xCor+scrOffsetX+mouseX/64][(n)*8+16] = 0;
						TileArray[yCor+scrOffsetY+mouseY/64][xCor+scrOffsetX+mouseX/64][(n)*8+17] = copyObj[1];
						TileArray[yCor+scrOffsetY+mouseY/64][xCor+scrOffsetX+mouseX/64][(n)*8+18] = copyObj[2];
						TileArray[yCor+scrOffsetY+mouseY/64][xCor+scrOffsetX+mouseX/64][(n)*8+19] = copyObj[3];
						TileArray[yCor+scrOffsetY+mouseY/64][xCor+scrOffsetX+mouseX/64][(n)*8+20] = 0;
						TileArray[yCor+scrOffsetY+mouseY/64][xCor+scrOffsetX+mouseX/64][(n)*8+21] = (selectObject % 64)*4;
						TileArray[yCor+scrOffsetY+mouseY/64][xCor+scrOffsetX+mouseX/64][(n)*8+22] = selectObject / 64;
						TileArray[yCor+scrOffsetY+mouseY/64][xCor+scrOffsetX+mouseX/64][(n)*8+23] = 0;
						TileArray[yCor+scrOffsetY+mouseY/64][xCor+scrOffsetX+mouseX/64][6]++;
						for (int a = 0; a < 28; a++) {
							Objects[countObjects][a] = 0;
						}
						int YCor = (yCor+scrOffsetY+mouseY/64)*64;
						int XCor = (xCor+scrOffsetX+mouseX/64)*64;
						Objects[countObjects][20] = XCor % 256;
						Objects[countObjects][21] = XCor / 256;
						Objects[countObjects][22] = YCor % 256;
						Objects[countObjects][23] = YCor / 256;
						Objects[countObjects][26] = TileArray[yCor+scrOffsetY+mouseY/64][xCor+scrOffsetX+mouseX/64][(n)*8+21];
						Objects[countObjects][27] = TileArray[yCor+scrOffsetY+mouseY/64][xCor+scrOffsetX+mouseX/64][(n)*8+22];
						countObjects++;
						repaint();
				}
				}
		    	repaint();
			}
		}); 
		centerPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
            	if (selectedTextures[0] > 0 && isShift == false) {
		    		 mouseX = e.getX();
		    		 mouseY = e.getY();
		    		 Random random = new Random();
		    		 int randTextures = random.nextInt(selectedTextures[0])+1;
		    		 TileArray[yCor+scrOffsetY+mouseY/64][xCor+scrOffsetX+mouseX/64][0] = selectedTextures[randTextures] % 256;
		    		 TileArray[yCor+scrOffsetY+mouseY/64][xCor+scrOffsetX+mouseX/64][1] = selectedTextures[randTextures] / 256;
		    		 takeTextursAndObjects(yCor,xCor,TileArray);
		    		 try {
						Thread.sleep(10);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    	 }
		    	 if (selectedTextures[0] > 0 && isShift == true) {
		    		 mouseX = e.getX()-64;
		    		 mouseY = e.getY()-64;
		    		 Random random = new Random();
		    		 for( int t = 0; t < 9; t=t+3) {
		    		 int randTextures = random.nextInt(selectedTextures[0])+1;
		    		 TileArray[yCor+scrOffsetY+mouseY/64+t/3][xCor+scrOffsetX+mouseX/64+0][0] = selectedTextures[randTextures] % 256;
		    		 TileArray[yCor+scrOffsetY+mouseY/64+t/3][xCor+scrOffsetX+mouseX/64+0][1] = selectedTextures[randTextures] / 256;
		    		 randTextures = random.nextInt(selectedTextures[0])+1;
		    		 TileArray[yCor+scrOffsetY+mouseY/64+t/3][xCor+scrOffsetX+mouseX/64+1][0] = selectedTextures[randTextures] % 256;
		    		 TileArray[yCor+scrOffsetY+mouseY/64+t/3][xCor+scrOffsetX+mouseX/64+1][1] = selectedTextures[randTextures] / 256;
		    		 randTextures = random.nextInt(selectedTextures[0])+1;
		    		 TileArray[yCor+scrOffsetY+mouseY/64+t/3][xCor+scrOffsetX+mouseX/64+2][0] = selectedTextures[randTextures] % 256;
		    		 TileArray[yCor+scrOffsetY+mouseY/64+t/3][xCor+scrOffsetX+mouseX/64+2][1] = selectedTextures[randTextures] / 256;
		    		 takeTextursAndObjects(yCor,xCor,TileArray);
		    		 try {
							Thread.sleep(10);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		    		 }
		    	 }
		    	 repaint();
            }
        });
		add(centerPanel, BorderLayout.CENTER);
		//-----------------Правая панель------------------------
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.setPreferredSize(new Dimension(196, centerPanelSizeY));
		rightPanel.setBackground(new Color(170, 170, 170));
		JPanel rightPanelButton = new JPanel();
		rightPanelButton.setLayout(new BoxLayout(rightPanelButton, BoxLayout.LINE_AXIS));
		rightPanel.add(rightPanelButton, BorderLayout.PAGE_START);
		JButton butTextures = new JButton("Textures");
		butTextures.setFocusable(false);
		JButton butObjects = new JButton("Objects");
		butObjects.setFocusable(false);
		rightPanelButton.add(butTextures);
		rightPanelButton.add(butObjects);
		rightPanel.add(new RightShowWindow(), BorderLayout.CENTER);
		//final JScrollPane scrollPane = new JScrollPane(rightPanel);
		JPanel rightPanelButton2 = new JPanel();
		rightPanelButton2.setLayout(new BoxLayout(rightPanelButton2, BoxLayout.LINE_AXIS));
		rightPanel.add(rightPanelButton2, BorderLayout.PAGE_END);
		JButton butUp = new JButton(" Up ");
		butUp.setFocusable(false);
		JButton butDown = new JButton("Down");
		butDown.setFocusable(false);
		JButton butClear = new JButton("Clear");
		butClear.setFocusable(false);
		rightPanelButton2.add(butUp);
		rightPanelButton2.add(butDown);
		rightPanelButton2.add(butClear);
		rightPanel.addMouseListener(new MouseAdapter() { 
		public void mousePressed(MouseEvent me) { // Выбираем текстуру\объекты на правой панели
			if (selectionTextures == true){
				if (isShift == true) {
					mouseX = me.getX();
					mouseY = me.getY();
					int selectTile = (mouseY-26)/64*3+mouseX/64+texturePage;
					if (selectedTextures[0] < 31) {
						selectedTextures[0]++;
						selectedTextures[selectedTextures[0]] = selectTile;
					}
				}
				if (isShift == false) {
					mouseX = me.getX();
					mouseY = me.getY();
					int selectTile = (mouseY-26)/64*3+mouseX/64+texturePage;
					selectedTextures[0] = 1;
					selectedTextures[1] = selectTile;
				}
			}
			if (selectionObjects == true) {
				mouseY = me.getY();
				if (mouseY < centerPanelSizeY - 230) selectObject = (mouseY-26)/16+objectPage;
			}
		    repaint();
		} 
		}); 
		//add(scrollPane, BorderLayout.LINE_END);
		add(rightPanel, BorderLayout.LINE_END);
		//-----------------Верхняя панель-----------------------
		JPanel upPanel = new JPanel();
		upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.LINE_AXIS));
		JButton butRead = new JButton("Read");
		butRead.setFocusable(false);
		upPanel.add(butRead);
		JButton butWrite = new JButton("Write");
		butWrite.setFocusable(false);
		butWrite.setEnabled(false);
		upPanel.add(butWrite);
		JButton editHexWorld = new JButton("Edit HEX World");
		editHexWorld.setFocusable(false);
		upPanel.add(editHexWorld);
		JButton editHexObj = new JButton("Edit HEX Object");
		editHexObj.setFocusable(false);
		upPanel.add(editHexObj);
		JButton delObjects = new JButton("Delete object");
		delObjects.setFocusable(false);
		upPanel.add(delObjects);
		JButton butCopyObj = new JButton("Kopy object");
		butCopyObj.setFocusable(false);
		upPanel.add(butCopyObj);
		JButton butPasteObj = new JButton("Paste object");
		butPasteObj.setFocusable(false);
		upPanel.add(butPasteObj);
		JButton butTest = new JButton("Test");
		butTest.setFocusable(false);
		upPanel.add(butTest);
		add(upPanel, BorderLayout.PAGE_START);
		//-----------------Нижняя панель------------------------
		JPanel downPanel = new JPanel();
		downPanel.setLayout(new BorderLayout());
		JTextArea consoleArea = new JTextArea(5,5);
		consoleArea.setFocusable(false);
		consoleArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		JScrollPane DownScroll = new JScrollPane(consoleArea);
		consoleArea.append(consoleText);
		downPanel.add(DownScroll, BorderLayout.CENTER);
		add(downPanel, BorderLayout.PAGE_END);
		//------------------------------------------------------
		pack();
		setSize(800, 600);
		setVisible(true);
		centerPanelSizeX = centerPanel.getWidth();
		centerPanelSizeY = centerPanel.getHeight();
		//------------------------------------------------------
		butRead.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				TileArray = ReadWorldFile.readWorld(inpFile);
				ObjectsInfo = ReadObjectsInfo.readObjectsInfo(inpObjFile);
				//ObjectsName = ReadObjectsInfo.readObjectsName(inpObjFile);
				countObjects = ReadObjectsFile.readObjectsCount(ObjFile);
				Objects = ReadObjectsFile.readObjects(ObjFile, countObjects);
				System.out.println("Completed");
				butWrite.setEnabled(true);
				centerPanel.repaint();
			}  
		}); 
		butWrite.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				WriteWorldFile.writeWorld(inpFile, TileArray);
				WriteObjectsFile.writeObjects(ObjFile, Objects, countObjects);
				System.out.println("Completed");
			}  
		}); 
		editHexWorld.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(selectedObject >= 0){ // Редактировать hex объекта
					int numObj = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+17]/16 + TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+18]*16 + TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+19]*4096;
					consoleArea.setText(consoleArea.getText() + "\n" + "Edit HEX object " + numObj + " " + ObjectsName[TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+22]*64 + TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+21]/4]);
					String DateTile = "";
                	DateTile += Integer.toHexString(TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+16]) + " ";
                	DateTile += Integer.toHexString(TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+17]) + " ";
                	DateTile += Integer.toHexString(TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+18]) + " ";
                	DateTile += Integer.toHexString(TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+19]) + " ";
                	DateTile += Integer.toHexString(TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+20]) + " ";
                	DateTile += Integer.toHexString(TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+21]) + " ";
                	DateTile += Integer.toHexString(TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+22]) + " ";
                	DateTile += Integer.toHexString(TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+23]);
                	String newDateTile = DateTile;
                	consoleArea.setText(consoleArea.getText() + "\n" + "Old " + DateTile);
                	newDateTile = JOptionPane.showInputDialog("Новое HEX значение объекта",newDateTile);
                	if (!DateTile.equals(newDateTile)){
                		if(newDateTile != null && newDateTile.length() < 24 && newDateTile.length() > 15) {
                			consoleArea.setText(consoleArea.getText() + "\n" + "New ");
                			int r = 0;
                			String[] words = newDateTile.split(" ");
                			for(String word : words){
                				TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+16+r] = Integer.parseInt(word, 16);
                				consoleArea.setText(consoleArea.getText() + word + " ");
                				r++;
                			}
                		}
                	}
                	infoArea.setText("");
                	infoArea.append(infoText);
                	repaint();
				}  
			}
		});
		editHexObj.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(selectedObject >= 0){ // Редактировать hex объекта
					int numObj = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+17]/16 + TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+18]*16 + TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+19]*4096;
					consoleArea.setText(consoleArea.getText() + "\n" + "Edit HEX object " + numObj + " " + ObjectsName[TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+22]*64 + TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+21]/4]);
					String DateTile = "";
					for (int i = 0; i < 27; i++) {
						DateTile += Integer.toHexString(Objects[numObj][i]) + " ";
					}
					DateTile += Integer.toHexString(Objects[numObj][27]);
                	String newDateTile = DateTile;
                	consoleArea.setText(consoleArea.getText() + "\n" + "Old " + DateTile);
                	newDateTile = JOptionPane.showInputDialog("Новое HEX значение объекта",newDateTile);
                	if (!DateTile.equals(newDateTile)){
                		if(newDateTile != null && newDateTile.length() < 84 && newDateTile.length() > 55) {
                			consoleArea.setText(consoleArea.getText() + "\n" + "New ");
                			int r = 0;
                			String[] words = newDateTile.split(" ");
                			for(String word : words){
                				Objects[numObj][r] = Integer.parseInt(word, 16);
                				consoleArea.setText(consoleArea.getText() + word + " ");
                				r++;
                			}
                		}
                	}
                	infoArea.setText("");
                	infoArea.append(infoText);
                	repaint();
				}  
			}
		});
		delObjects.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
				if(selectedObject >= 0) {
					int numObj = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+17]/16 + TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+18]*16 + TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+19]*4096;
					for (int a = 0; a < 28; a++) {
						Objects[numObj][a] = 255;
					}
					consoleArea.setText(consoleArea.getText() + "\n" + "Object " + (numObj) + " del");
					TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][6]--;
					for(int o = selectedObject; o < numbOfObjects[scrOffsetY+centerPanelSizeY/64/2][scrOffsetX+centerPanelSizeX/64/2]-1; o++) {
						TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][o*8+16] = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][(o+1)*8+16];
						TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][o*8+17] = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][(o+1)*8+17];
						TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][o*8+18] = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][(o+1)*8+18];
						TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][o*8+19] = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][(o+1)*8+19];
						TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][o*8+20] = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][(o+1)*8+20];
						TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][o*8+21] = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][(o+1)*8+21];
						TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][o*8+22] = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][(o+1)*8+22];
						TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][o*8+23] = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][(o+1)*8+23];
					}
					if (numObj == countObjects - 1) {countObjects--; System.out.println("Del last object");} //Если удаляется последний объект, то удаляем его и из Objects
				}
				infoArea.setText("");
				infoArea.append(infoText);
				repaint();
			}
		});
		butCopyObj.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				if(selectedObject >= 0) {
					int numObj = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+17]/16 + TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+18]*16 + TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+19]*4096;
					consoleArea.setText(consoleArea.getText() + "\n" + "Copy object " + numObj);
					copyObj[0] = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+16];
					copyObj[1] = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+17];
					copyObj[2] = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+18];
					copyObj[3] = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+19];
					copyObj[4] = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+20];
					copyObj[5] = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+21];
					copyObj[6] = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+22];
					copyObj[7] = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+23];
					for (int a = 0; a < 28; a++) {
						copyObj[a+8] = Objects[numObj][a];
					}
					readyToCopy = true;
				}
			}  
		}); 
		butPasteObj.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				int n = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][6];
					if(n < 15 && readyToCopy == true) {
						consoleArea.setText(consoleArea.getText() + "\n" + "Past object " + countObjects);
						copyObj[3] = countObjects << 12;
						copyObj[3] = copyObj[3] >> 24;
						copyObj[2] = countObjects << 20;
						copyObj[2] = copyObj[2] >> 24;
						copyObj[1] = copyObj[1] % 16 + (countObjects % 16)*16;
						TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][(n)*8+16] = copyObj[0];
						TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][(n)*8+17] = copyObj[1];
						TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][(n)*8+18] = copyObj[2];
						TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][(n)*8+19] = copyObj[3];
						TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][(n)*8+20] = copyObj[4];
						TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][(n)*8+21] = copyObj[5];
						TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][(n)*8+22] = copyObj[6];
						TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][(n)*8+23] = copyObj[7];
						TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][6]++;
						for (int a = 0; a < 28; a++) {
							Objects[countObjects][a] = copyObj[a+8];
						}
						int XYCor = copyObj[0] + copyObj[1]%16*4*64;
						int YCor = XYCor/64 + (yCor+scrOffsetY+centerPanelSizeY/64/2)*64;
						int XCor = XYCor%64 + (xCor+scrOffsetX+centerPanelSizeX/64/2)*64;
						Objects[countObjects][20] = XCor % 256;
						Objects[countObjects][21] = XCor / 256;
						Objects[countObjects][22] = YCor % 256;
						Objects[countObjects][23] = YCor / 256;
						countObjects++;
						repaint();
				}
			}  
		});
		butTest.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
				String[] objName = new String[7208];
				int[][] objSize = new int[7208][7];
				String b;
				try {
					byte[] buf = Files.readAllBytes(Paths.get("objects.de"));
					int k = 0;
					for (int i = 0; i < 7208; i++) {
						objName[i] = "";
						while (buf[k] != 9) {
							char c = 0;
							c += buf[k];
							objName[i] += c;
							k++;
						}
						k++;
						
						for (int j = 0; j < 6; j++) {
							b = "";
							while (buf[k] != 9) {
								b += (char) (buf[k] & 0xFF);
								k++;
							}
							k++;
							objSize[i][j] = Integer.parseInt(b);
						}
						
						b = "";
						while (buf[k] != 13 ) {
							b += (char) (buf[k] & 0xFF);
							k++;
						}
						k++;
						objSize[i][6] = Integer.parseInt(b);
						
						k++;
						if (i > 7100) {
						System.out.print(objName[i] + " ");
						System.out.print(objSize[i][0] + " ");
						System.out.print(objSize[i][1] + " ");
						System.out.print(objSize[i][2] + " ");
						System.out.print(objSize[i][3] + " ");
						System.out.print(objSize[i][4] + " ");
						System.out.print(objSize[i][5] + " ");
						System.out.println(objSize[i][6]);
						}
					}
					} catch (IOException z) {
					z.printStackTrace();
					}
			}  
		}); 
		butTextures.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				selectionTextures = true;
				selectionObjects = false;
				selectObject = -1;
				repaint();
			}  
		});
		butObjects.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				selectionTextures = false;
				selectionObjects = true;
				for(int r = 0; r < 32; r++) selectedTextures[r] = 0;
				repaint();
			}  
		});
		butUp.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
				
				if (selectionTextures == true && texturePage >= 12) texturePage-=12;
				if (selectionObjects == true && objectPage >= 30) objectPage-=30;
				repaint();
			}  
		}); 
		butDown.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				if (selectionTextures == true && texturePage < 3330) texturePage+=12;
				if (selectionObjects == true && objectPage < 7175) objectPage+=30;
				repaint();
			}  
		}); 
		butClear.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
				if (selectionTextures == true) for(int r = 0; r < 32; r++) selectedTextures[r] = 0;
				if (selectionObjects == true) selectObject = -1;
				repaint();
			}  
		}); 
		centerPanel.addComponentListener(new ComponentListener(){ //Интерфейс для отслеживания размера centerPanel
			@Override
			public void componentResized(ComponentEvent e) {
				centerPanelSizeX = centerPanel.getWidth();
				centerPanelSizeY = centerPanel.getHeight();
			}
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Автоматически созданная заглушка метода
			}
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Автоматически созданная заглушка метода
			}
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Автоматически созданная заглушка метода
			}
		});
		addKeyListener(new KeyAdapter() {  //Проверяем события нажатий клавишь
			@Override
		    public void keyReleased(KeyEvent e) {
		        if (e.getKeyCode()==65) isLeft = false; //клавиша A
		        if (e.getKeyCode()==68) isRight = false; //клавиша D
		        if (e.getKeyCode()==87) isUp = false; //клавиша W
		        if (e.getKeyCode()==83) isDown = false; //клавиша S
		        if (e.getKeyCode()==16) isShift	 = false; //клавиша Shift	
		        if (e.getKeyCode()==17) isCtrl	 = false; //клавиша Ctrl
		    }
			@Override
            public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==16) isShift = true; //клавиша Shift	
				if (e.getKeyCode()==17) isCtrl	 = true; //клавиша Ctrl
                if ((e.getKeyCode()==87) && isLeft == false && isRight == false) { // Сместить экран вверх
                	isUp = true;
                	if (yCor > scrOffsetY) yCor--;
                	selectedObject = -1;
                }
                if ((e.getKeyCode()==87) && isLeft == true && isRight == false) { // Сместить экран вверх и влево
                	isUp = true;
                	if (yCor > scrOffsetY) yCor--;
                	if (xCor > scrOffsetX) xCor--;
                	selectedObject = -1;
                }
                if ((e.getKeyCode()==87) && isLeft == false && isRight == true) { // Сместить экран вверх и вправо
                	isUp = true;
                	if (yCor > scrOffsetY) yCor--;
                	if (xCor < (512 - centerPanelSizeX/64-1-scrOffsetX)) xCor++;
                	selectedObject = -1;
                }
                if ((e.getKeyCode()==83) && isLeft == false && isRight == false) { // Сместить экран вниз
                	isDown = true;
                	if (yCor < (1024 - centerPanelSizeY/64-1-scrOffsetY)) yCor++;
                	selectedObject = -1;
                }
                if ((e.getKeyCode()==83) && isLeft == true && isRight == false) { // Сместить экран вниз и влево
                	isDown = true;
                	if (yCor < (1024 - centerPanelSizeY/64-1-scrOffsetY)) yCor++;
                	if (xCor > scrOffsetX) xCor--;
                	selectedObject = -1;
                }
                if ((e.getKeyCode()==83) && isLeft == false && isRight == true) { // Сместить экран вниз и вправо
                	isDown = true;
                	if (yCor < (1024 - centerPanelSizeY/64-1-scrOffsetY)) yCor++;
                	if (xCor < (512 - centerPanelSizeX/64-1-scrOffsetX)) xCor++;
                	selectedObject = -1;
                }
                if ((e.getKeyCode()==65) && isUp == false && isDown == false) { // Сместить экран влево
                	isLeft = true;
                	if (xCor > scrOffsetX) xCor--;
                	selectedObject = -1;
                }
                if ((e.getKeyCode()==65) && isUp == true && isDown == false) { // Сместить экран влево и вверх
                	isLeft = true;
                	if (yCor > scrOffsetY) yCor--;
                	if (xCor > scrOffsetX) xCor--;
                	selectedObject = -1;
                }
                if ((e.getKeyCode()==65) && isUp == false && isDown == true) { // Сместить экран влево и вниз
                	isLeft = true;
                	if (yCor < (1024 - centerPanelSizeY/64-1-scrOffsetY)) yCor++;
                	if (xCor > scrOffsetX) xCor--;
                	selectedObject = -1;
                }
                if ((e.getKeyCode()==68) && isUp == false && isDown == false) { // Сместить экран вправо
                	isRight = true;
                	if (xCor < (512 - centerPanelSizeX/64-1-scrOffsetX)) xCor++;
                	selectedObject = -1;
                }
                if ((e.getKeyCode()==68) && isUp == true && isDown == false) { // Сместить экран вправо и вверх
                	isRight = true;
                	if (yCor > scrOffsetY) yCor--;
                	if (xCor < (512 - centerPanelSizeX/64-1-scrOffsetX)) xCor++;
                	selectedObject = -1;
                }
                if ((e.getKeyCode()==68) && isUp == false && isDown == true) { // Сместить экран вправо и вниз
                	isRight = true;
                	if (yCor < (1024 - centerPanelSizeY/64-1-scrOffsetY)) yCor++;
                	if (xCor < (512 - centerPanelSizeX/64-1-scrOffsetX)) xCor++;
                	selectedObject = -1;
                }
                if (e.getKeyCode()==82) { // Выбор объекта клавиша R
                	selectedObject++;
                	takeTextursAndObjects(yCor,xCor,TileArray);
                    if(selectedObject >= 0){ // вывод НЕХ объекта в консоль
                    	consoleArea.setText(consoleArea.getText() + "\n" + consoleText);
                    }
                }
                if (e.getKeyCode()==37 && selectedObject >= 0){// Переместить объект влево
                	int dXY = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+16] + TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+17]%16*4*64;
					int dY = dXY/64;
					int dX = dXY%64;
					if (dX > 0) dX--;
					dXY = dX+(64*dY);
					TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+16] = dXY%256;
					TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+17] = (TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+17]/16)*16+ dXY/256;
                }
                if (e.getKeyCode()==38 && selectedObject >= 0){// Переместить объект вверх
                	if (isCtrl == true) {
                		if (TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+20] < 255 ) TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+20] ++;
                	}
                	if (isCtrl == false) {
                		int dXY = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+16] + TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+17]%16*4*64;
                		int dY = dXY/64;
                		int dX = dXY%64;
                		if (dY > 0) dY--;
                		dXY = dX+(64*dY);
                		TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+16] = dXY%256;
                		TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+17] = (TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+17]/16)*16+ dXY/256;
                	}
                }
                if (e.getKeyCode()==39 && selectedObject >= 0){// Переместить объект вправо
                	int dXY = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+16] + TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+17]%16*4*64;
					int dY = dXY/64;
					int dX = dXY%64;
					if (dX < 63) dX++;
					dXY = dX+(64*dY);
					TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+16] = dXY%256;
					TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+17] = (TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+17]/16)*16+ dXY/256;
                }
                if (e.getKeyCode()==40 && selectedObject >= 0){// Переместить объект вниз
                	if (isCtrl == true) {
                		if(TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+20] > 0 )TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+20] --;
                	}
                	if (isCtrl == false) {
                		int dXY = TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+16] + TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+17]%16*4*64;
                		int dY = dXY/64;
                		int dX = dXY%64;
                		if (dY < 63) dY++;
                		dXY = dX+(64*dY);
                		TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+16] = dXY%256;
                		TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+17] = (TileArray[yCor+scrOffsetY+centerPanelSizeY/64/2][xCor+scrOffsetX+centerPanelSizeX/64/2][selectedObject*8+17]/16)*16+ dXY/256;
                	}
                }
                	
                takeTextursAndObjects(yCor,xCor,TileArray);
                infoArea.setText("");
                infoArea.append(infoText);
                repaint();
            }});
		setVisible(true);
		setFocusable(true);

	}
	private JMenu createViewMenu()
    {
        JMenu viewMenu = new JMenu("view");
        JCheckBoxMenuItem grid  = new JCheckBoxMenuItem("net");
        JCheckBoxMenuItem type  = new JCheckBoxMenuItem("tile type");
        JCheckBoxMenuItem numObj = new JCheckBoxMenuItem("show objects");
        JCheckBoxMenuItem form = new JCheckBoxMenuItem("contour of objects on a tile");
        JCheckBoxMenuItem formAll = new JCheckBoxMenuItem("outline of objects on all tiles");
        JCheckBoxMenuItem Height = new JCheckBoxMenuItem("height (not working yet)");
        numObj.setState(true);
        form.setState(true);
        viewMenu.add(grid);
        viewMenu.add(type);
        viewMenu.add(numObj);
        viewMenu.add(form);
        viewMenu.add(formAll);
        viewMenu.add(Height);
        
        ActionListener aListener = new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		if(grid.isSelected()) showGridTile = true;
        		else showGridTile = false;
        		if(type.isSelected()) showTileType = true;
        		else showTileType = false;
        		if(numObj.isSelected()) showObj = true;
        		else showObj = false;
        		if(form.isSelected()) showObjectFormTile = true;
        		else showObjectFormTile = false;
        		if(formAll.isSelected()) showObjectForm = true;
        		else showObjectForm = false;
        		if(Height.isSelected()) showHeight = true;
        		else showHeight = false;
        		repaint();
        	}
        };
        grid.addActionListener(aListener);
        type.addActionListener(aListener);
        numObj.addActionListener(aListener);
        form.addActionListener(aListener);
        formAll.addActionListener(aListener);
        return viewMenu;
    }
	
    public class CenterShowWindow extends JComponent {
        private static final long serialVersionUID = 1L;
        CenterShowWindow() {
            setPreferredSize(new Dimension(centerPanelSizeX, centerPanelSizeY));
        	setAlignmentX(0);
        }
        @Override
        public void paintComponent(Graphics CHW) {
            super.paintComponent(CHW);
            takeTextursAndObjects(yCor,xCor,TileArray);
            CHW.setColor(Color.white);
            // Выводим текстуры         
            String textNameFull;
            String textNameHalf;
            Image FullTextures;
            Image HalfTextures;
            Image objectTextures;
            for (int i = 0; i < centerPanelSizeY/64+1; i++) {
            	for (int j = 0; j < centerPanelSizeX/64+1; j++) {
            		textNameFull = Integer.toString(TexturesFull[i+scrOffsetY][j+scrOffsetX]);
            		if (textNameFull.length() < 4) textNameFull = "0"+textNameFull;
            		if (textNameFull.length() < 4) textNameFull = "0"+textNameFull;
            		if (textNameFull.length() < 4) textNameFull = "0"+textNameFull;
            		if (textNameFull.length() < 4) textNameFull = "0"+textNameFull;
            		if (textNameFull.length() < 4) textNameFull = "0"+textNameFull;
            		textNameFull = "floor//00"+textNameFull+".png";
            		FullTextures = new ImageIcon(textNameFull).getImage();  
            		CHW.drawImage(FullTextures, j*64, i*64, null);
            		textNameHalf = Integer.toString(TexturesHalf[i+scrOffsetY][j+scrOffsetX]);
            		if (textNameHalf.length() < 4) textNameHalf = "0"+textNameHalf;
            		if (textNameHalf.length() < 4) textNameHalf = "0"+textNameHalf;
            		if (textNameHalf.length() < 4) textNameHalf = "0"+textNameHalf;
            		if (textNameHalf.length() < 4) textNameHalf = "0"+textNameHalf;
            		if (textNameHalf.length() < 4) textNameHalf = "0"+textNameHalf;
            		textNameHalf = "floor//00"+textNameHalf+".png";
            		HalfTextures = new ImageIcon(textNameHalf).getImage(); 
            		CHW.drawImage(HalfTextures, j*64, i*64, null);
            		if (showGridTile == true) {
            			CHW.setColor(Color.gray);
            			CHW.drawRect(j*64, i*64, 64, 64);
            		}
            		if (showTileType == true && TileType[i+scrOffsetY][j+scrOffsetX] > 0) {
    					CHW.setColor(Color.gray);
    					CHW.drawOval(j*64+32-TileType[i+scrOffsetY][j+scrOffsetX]/4-3, i*64+32-TileType[i+scrOffsetY][j+scrOffsetX]/4-3, 6+TileType[i+scrOffsetY][j+scrOffsetX]/2, 6+TileType[i+scrOffsetY][j+scrOffsetX]/2);
    				}
            	}
            }
            // Выводим объекты
            if (showObj == true) {
            	boolean sorted = false;
            	int  temp;
            	while(!sorted) {
            		sorted = true;
            		for (int a = 0; a < numbOfObjectsAll - 1; a++) {
            			if (ObjectsAll[a][1]> ObjectsAll[a+1][1]) {
            				for (int t = 0; t < 13; t++) {
            					temp = ObjectsAll[a][t];
            					ObjectsAll[a][t] = ObjectsAll[a+1][t];
            					ObjectsAll[a+1][t] = temp;
            					sorted = false;
            				}
            			}
            		}
            	}
    	
            	for (int i = 0; i < numbOfObjectsAll; i++) {
            		objectName = Integer.toString(ObjectsAll[i][0]);
            		if (objectName.length() < 4) objectName = "0"+objectName;
            		if (objectName.length() < 4) objectName = "0"+objectName;
            		if (objectName.length() < 4) objectName = "0"+objectName;
            		objectName = "objects//00"+objectName+".png";
            		dY = ObjectsAll[i][2]-ObjectsAll[i][4];
            		dX = ObjectsAll[i][3];
            		objectTextures = new ImageIcon(objectName).getImage(); 
            		CHW.drawImage(objectTextures, dX - ObjectsAll[i][6], dY - ObjectsAll[i][5], null);
            		if (showObjectForm == true) {
            			CHW.setColor(Color.white);
            			CHW.drawRect(dX - ObjectsAll[i][6], dY - ObjectsAll[i][5], ObjectsAll[i][11], ObjectsAll[i][10]);
            		}
            		if (showObjectFormTile == true && ObjectsAll[i][8] == scrOffsetY + centerPanelSizeY/64/2 && ObjectsAll[i][9] == scrOffsetX + centerPanelSizeX/64/2) {
            			if (selectedObject >= ObjectsAll[i][12]) selectedObject = 0;
            			if (selectedObject == ObjectsAll[i][7]) CHW.setColor(Color.yellow);
            			else CHW.setColor(Color.white);
            			CHW.drawRect(dX - ObjectsAll[i][6], dY - ObjectsAll[i][5], ObjectsAll[i][11], ObjectsAll[i][10]);
            			CHW.setColor(Color.white);
            		}
            	}
            }
            CHW.setColor(Color.white);
			CHW.drawRect(centerPanelSizeX/64/2*64, centerPanelSizeY/64/2*64, 64, 64);
        }
    }
    
	//-----------------------------------------------------------------------------------------------------------------
    public class RightShowWindow extends JComponent {
        private static final long serialVersionUID = 1L;
        RightShowWindow() {
        	setAlignmentX(0);
        }
        @Override
        public void paintComponent(Graphics RHW) {
            super.paintComponent(RHW);
            String TexName = "";
            String ObjName = "";
            RHW.setColor(Color.yellow);
            if (selectionTextures == true) {
            	for (int i = 0; i < ((centerPanelSizeY - 35)/69)*3; i=i+3) {
            		TexName = Integer.toString(i+texturePage);
            		for (int a = 1; a <= selectedTextures[0]; a++) {
            			if (i+texturePage == selectedTextures[a]) RHW.drawRect(0, 0 + i/3*65, 65, 65);
            		}
            		if (TexName.length() < 4) TexName = "0"+TexName;
            		if (TexName.length() < 4) TexName = "0"+TexName;
            		if (TexName.length() < 4) TexName = "0"+TexName;
            		Image HalfTextures = new ImageIcon("floor//00"+TexName+".png").getImage(); 
            		RHW.drawImage(HalfTextures, 1, 1 + i/3*65, null);
            		TexName = Integer.toString((i+1)+texturePage);
            		for (int a = 1; a <= selectedTextures[0]; a++) {
            			if ((i+1)+texturePage == selectedTextures[a]) RHW.drawRect(65, 0 + i/3*65, 65, 65);
            		}
            		if (TexName.length() < 4) TexName = "0"+TexName;
            		if (TexName.length() < 4) TexName = "0"+TexName;
            		if (TexName.length() < 4) TexName = "0"+TexName;
            		Image HalfTextures2 = new ImageIcon("floor//00"+TexName+".png").getImage(); 
            		RHW.drawImage(HalfTextures2, 66, 1 + i/3*65, null);
            		TexName = Integer.toString((i+2)+texturePage);
            		for (int a = 1; a <= selectedTextures[0]; a++) {
            			if ((i+2)+texturePage == selectedTextures[a]) RHW.drawRect(130, 0 + i/3*65, 65, 65);
            		}
            		if (TexName.length() < 4) TexName = "0"+TexName;
            		if (TexName.length() < 4) TexName = "0"+TexName;
            		if (TexName.length() < 4) TexName = "0"+TexName;
            		Image HalfTextures3 = new ImageIcon("floor//00"+TexName+".png").getImage(); 
            		RHW.drawImage(HalfTextures3, 131, 1 + i/3*65, null);
            	}
            }
            if (selectionObjects == true) {
            	RHW.setColor(Color.black);
            	RHW.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
            	for (int i=0; i < (centerPanelSizeY-250)/16; i++) {
            		if (i+objectPage == selectObject) RHW.setColor(Color.yellow);
            		else RHW.setColor(Color.black);
            		if (i+objectPage < 7208) RHW.drawString((i+objectPage)+" "+ObjectsName[i+objectPage], 2, i*16+15);
            	}
            	RHW.setColor(new Color(220, 220, 220));
            	RHW.drawRect(0,centerPanelSizeY-250,196,centerPanelSizeY);
            	RHW.fillRect(0,centerPanelSizeY-250,196,centerPanelSizeY);
            	RHW.setColor(Color.black);
            	RHW.drawLine(0,centerPanelSizeY-250,196,centerPanelSizeY-250);
            	if (selectObject >=0) {
            		ObjName = Integer.toString(selectObject);
            		if (ObjName.length() < 4) ObjName = "0"+ObjName;
            		if (ObjName.length() < 4) ObjName = "0"+ObjName;
            		if (ObjName.length() < 4) ObjName = "0"+ObjName;
            		Image object = new ImageIcon("objects//00"+ObjName+".png").getImage();
            		if (sizeOfObjects[selectObject][1] > 200 && sizeOfObjects[selectObject][0] > 200) RHW.drawImage(object, 0, centerPanelSizeY-250, 200, 200, null);
            		if (sizeOfObjects[selectObject][1] > 200 && sizeOfObjects[selectObject][0] < 200) RHW.drawImage(object, 0, centerPanelSizeY-250+100-sizeOfObjects[selectObject][0]/2, 200, sizeOfObjects[selectObject][0], null);
            		if (sizeOfObjects[selectObject][1] < 200 && sizeOfObjects[selectObject][0] > 200) RHW.drawImage(object, 100-sizeOfObjects[selectObject][1]/2, centerPanelSizeY-250, sizeOfObjects[selectObject][1], 200, null);
            		if (sizeOfObjects[selectObject][1] < 200 && sizeOfObjects[selectObject][0] < 200) RHW.drawImage(object, 100-sizeOfObjects[selectObject][1]/2, centerPanelSizeY-250+100-sizeOfObjects[selectObject][0]/2, null);
            	}
            }
        }
    }
    
	//-----------------------------------------------------------------------------------------------------------------
	class ExitAction extends AbstractAction		//Вложенный класс завершения работы приложения 
	{ 
		private static final long serialVersionUID = 1L;
		ExitAction() {putValue(NAME, "Выход");}
		public void actionPerformed(ActionEvent e) {System.exit(0);}
	}
	
	public static void readConfig() {
	    System.out.print("Читаем config...");
	    int countNamb = 0;
	    int a = 0;
	    int buffer;
		try {
			byte[] iniBuf = Files.readAllBytes(Paths.get("config.ini"));
			
			while(a < iniBuf.length-1) {
				while (iniBuf[a] != 10 && a < iniBuf.length-1) {
					a++;
				}
				a++;
				if (iniBuf[a] != 47) {
					while (iniBuf[a] != 13 && a < iniBuf.length-1) {
						buffer = iniBuf[a];
						if (countNamb == 0) gameDirectory += (char)buffer;
						if (countNamb == 1) saveName += (char)buffer;
						if (countNamb == 2) fileExt += (char)buffer;
						a++;
					}
					countNamb++;
					a++;
				}
			}
		} catch (IOException z) {
			z.printStackTrace();
		  }
		System.out.println("OK");
	}
	
	public static void main(String[] args) throws IOException {
		readConfig();
		System.out.println(gameDirectory);
		System.out.println(saveName);
		System.out.println(fileExt);
		inpFile = gameDirectory + "\\savegames\\" + saveName +"\\world." +fileExt;
		outFile = gameDirectory + "\\savegames\\" + saveName +"\\world." +fileExt;
		ObjFile = gameDirectory + "\\savegames\\" + saveName +"\\objects." + fileExt;
		ObjectsName = ReadObjectsInfo.readObjectsName(inpObjFile);
		Editor_007 mainFrame = new Editor_007();
		mainFrame.setFocusable(true);
	}
}