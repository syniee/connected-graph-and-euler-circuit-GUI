package eulercircuit7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


public class MyFrame extends JFrame implements ActionListener {
    // chuyển đổi trạng thái của một Java Object thành định dạng nào đó để lưu ở đâu đó và được sử dụng ở tiến trình khác
    private static final long serialVersionUID = 1L;

    // frame
    private JFrame frameAbout, frameHelp;
    private String data[][], head[];
        
    private JButton btnEulerCircuit, btnTutorial, btnCon;
    private JButton btnPoint, btnLine, btnUpdate, btnMove, btnOpen, btnSave,btnNew;
	
    private JTable tableMatrix;// bảng ma trận
    private JTable tableLog;// bảng đường đi

    private JPanel drawPanel = new JPanel();// panel vẽ
    // graph
    private MyDraw myDraw = new MyDraw();
    // log
    private JTextArea textLog;
    private JTextArea textMatrix;

    private JTextField textNumerPoint;

    private MyPopupMenu popupMenu;

    private int indexBeginPoint = 0, indexEndPoint = 0;
    private int step = 0;


    int WIDTH_SELECT, HEIGHT_SELECT;

    MyEuler euler = new MyEuler();
        
    Font font1 = new Font("SansSerif", Font.BOLD, 30);
    Font font2 = new Font("SansSerif", Font.BOLD, 20);
    Font font3 = new Font("SansSerif", Font.BOLD, 15);
    public MyFrame(String title) {
        setTitle(title);
        setLayout(new BorderLayout(5, 5));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
            
        add(creatSelectPanel(), BorderLayout.EAST);
        add(creatPaintPanel(), BorderLayout.CENTER);
        add(creatLogPanel(), BorderLayout.PAGE_END);
            
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
                
        // set logo
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon/logoEuler.png")));
    }

    private JPanel creatSelectPanel() {
        // tạo panel lớn chứa panel top và bottom
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelTop = new JPanel(new GridLayout(4, 1, 5, 5));// GidLayout(số dòng, số cột, chiều ngang,chiều dọc)
        JPanel panelBottom = new JPanel(new BorderLayout());
        // tạo panel chọn hình thức duyệt
        JPanel panelRunTemp = new JPanel(new GridLayout(1, 2, 40, 5));
        panelRunTemp.setBorder(new EmptyBorder(0, 15, 0, 5));
        panelRunTemp.add(btnCon = createButton("Số miền liên thông"));
        btnCon.setBackground(Color.black);
        btnCon.setForeground(Color.WHITE);
        btnCon.setFont(font3);
        panelRunTemp.add(btnEulerCircuit = createButton("Chu trình Euler"));
        btnEulerCircuit.setBackground(Color.black);
        btnEulerCircuit.setForeground(Color.WHITE);
        btnEulerCircuit.setFont(font3);
        JPanel panelRun = new JPanel(new BorderLayout());
        panelRun.setBorder(new TitledBorder("Giải thuật đồ thị"));
        panelRun.add(panelRunTemp);
            
        panelTop.add(btnTutorial = createButton("Hướng dẫn"));
        btnTutorial.setBackground(Color.white);
        btnTutorial.setForeground(Color.black);
        btnTutorial.setFont(font2);
        btnTutorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(frameHelp == null){
                    frameHelp = new Help(0, "Hướng dẫn");
                }
                frameHelp.setVisible(true); 
            }
        });
                
//        panelTop.add(panelSearch);
//        panelTop.add(panelInputMethod);
//        panelTop.add(panelSelectPoint);
        panelTop.add(panelRun);
       
        JScrollPane scroll = new JScrollPane(tableMatrix = createTable());
        scroll.setPreferredSize(panelTop.getPreferredSize());
        panelBottom.add(scroll);
        panelBottom.setBorder(new TitledBorder("Ma trận Kề"));
              
        panel.add(panelTop, BorderLayout.PAGE_START);
        panel.add(panelBottom, BorderLayout.CENTER);
        panel.setBorder(new EmptyBorder(0, 5, 0, 0));
        WIDTH_SELECT = (int) panel.getPreferredSize().getWidth();
        HEIGHT_SELECT = (int) panel.getPreferredSize().getHeight();
        return panel;
    }

    private JPanel creatPaintPanel() {
        drawPanel.setLayout(new BoxLayout(drawPanel, BoxLayout.Y_AXIS));
        drawPanel.setBorder(new EmptyBorder(0, 10, 0, 10));// EmptyBorder(trên, trái, dưới, phải)
	drawPanel.setBackground(null);
                
	Icon icon;
	// String link = File.separator + "icon" + File.separator;
	String link = "/icon/";

        icon = getIcon(link + "iconUpdate1.png");
        drawPanel.add(btnUpdate = createButtonImage(icon, "Cập nhật đồ thị"));

	icon = getIcon(link + "iconPoint2.png");
	drawPanel.add(btnPoint = createButtonImage(icon, "Vẽ đỉnh"));

	icon = getIcon(link + "iconLine.png");
	drawPanel.add(btnLine = createButtonImage(icon, "Vẽ cạnh"));

	icon = getIcon(link + "iconMove2.png");
	drawPanel.add(btnMove = createButtonImage(icon, "Di chuyển đỉnh"));

	icon = getIcon(link + "iconOpen.png");
	drawPanel.add(btnOpen = createButtonImage(icon, "Mở đồ thị"));
        
	icon = getIcon(link + "iconSave.png");
	drawPanel.add(btnSave = createButtonImage(icon, "Lưu đồ thị"));

	icon = getIcon(link + "iconNew3.png");
	drawPanel.add(btnNew = createButtonImage(icon, "Tạo đồ thị mới"));

	popupMenu = createPopupMenu();
	myDraw.setComponentPopupMenu(popupMenu);

	JPanel panel = new JPanel();
	panel.setLayout(new BorderLayout());
	panel.add(drawPanel, BorderLayout.WEST);
	panel.add(myDraw, BorderLayout.CENTER);
                
	return panel;
    }

    private ImageIcon getIcon(String link){
	return new ImageIcon(getClass().getResource(link));
    }

    private JPanel creatLogPanel() {
	textLog = new JTextArea("Hiển thị kết quả...");
        textLog.setFont(font1);
        textLog.setRows(3);
	textLog.setEditable(false);
	JScrollPane scrollPath = new JScrollPane(textLog);
	JScrollPane scroll = new JScrollPane(tableLog = createTable());

	JPanel panel = new JPanel(new BorderLayout());
	panel.setBorder(new TitledBorder("Bảng thông báo"));
	panel.add(scrollPath, BorderLayout.CENTER);
//          panel.add(scroll, BorderLayout.CENTER);
	panel.setPreferredSize(new Dimension(WIDTH_SELECT * 7 / 2, HEIGHT_SELECT / 2));
	return panel;
    }

    private JMenuItem createMenuItem(String title, int keyEvent, int event) {
	JMenuItem mi = new JMenuItem(title);
	mi.setMnemonic(keyEvent);
	mi.setAccelerator(KeyStroke.getKeyStroke(keyEvent, event));
	mi.addActionListener(this);
	return mi;
    }

    private MyPopupMenu createPopupMenu() {
	MyPopupMenu popup = new MyPopupMenu();
//          popup.add(createMenuItem("Change cost", 0, 0));
	popup.add(createMenuItem("Xóa", 0, 0));
        return popup;
    }

    // create radioButton on group btnGroup and add to panel
    private JRadioButton createRadioButton(String lable, Boolean select) {
	JRadioButton rad = new JRadioButton(lable);
	rad.addActionListener(this);
	rad.setSelected(select);
	return rad;
    }

    // create button and add to panel
    private JButton createButton(String lable) {
	JButton btn = new JButton(lable);
	btn.addActionListener(this);
	return btn;
    }

    // create buttonImage and add to panel
    private JButton createButtonImage(Icon icon, String toolTip) {//Tao nut
	JButton btn = new JButton(icon);
	btn.setMargin(new Insets(10, 10, 10, 10));
	btn.addActionListener(this);
	btn.setToolTipText(toolTip);
        btn.setBackground(new Color(49,49,49));// mau cua button tao graph
	return btn;
    }

    // create comboBox and add to panel
    private JComboBox<String> createComboxBox(String title) {
    	String list[] = { title };
	JComboBox<String> cbb = new JComboBox<String>(list);
	cbb.addActionListener(this);
	cbb.setEditable(false);
	cbb.setMaximumRowCount(5);
	return cbb;
    }

    // create matrix panel with cardLayout
    private JTable createTable() {
	JTable table = new JTable();
	return table;
    }
    // ------------------ Action ------------------//
    private void actionUpdate(){
	updateListPoint();
	resetDataDijkstra();
	setDrawResultOrStep(false);
	reDraw();
	loadMatrix();
	clearLog();
    }

    private void actionDrawPoint(){
	myDraw.setDraw(1);
	setDrawResultOrStep(false);
    }

    private void actionDrawLine(){
	myDraw.setDraw(2);
	setDrawResultOrStep(false);
    }

    private void actionOpen(){
	JFileChooser fc = new JFileChooser();
	fc.setDialogTitle("Mở đồ thị");
	int select = fc.showOpenDialog(this);
	if(select == 0){
            String path = fc.getSelectedFile().getPath();
            System.out.println(path);
            myDraw.readFile(path);
            actionUpdate();
	}
    }

    private void actionSave(){
	JFileChooser fc = new JFileChooser();
	fc.setDialogTitle("Lưu đồ thị");
	int select = fc.showSaveDialog(this);
	if(select == 0){
	String path = fc.getSelectedFile().getPath();
	System.out.println(path);
	myDraw.write(path);
	}
    }

    private void actionNew(){
	setDrawResultOrStep(false);
	myDraw.setResetGraph(true);
	myDraw.repaint();
	myDraw.init();
	updateListPoint();
	clearLog();
	clearMatrix();
    }

    private void actionChoosePoint(){
	resetDataDijkstra();
	setDrawResultOrStep(false);
	reDraw();
	clearLog();
    }

    private void showDialogDelete(){
	int index = myDraw.indexPointContain(popupMenu.getPoint());
	if(index <= 0){
            index = myDraw.indexLineContain(popupMenu.getPoint());
            if(index > 0){
            // show message dialog
                MyLine ml = myDraw.getData().getArrMyLine().get(index);
                String message = "Bạn muốn xóa cạnh từ đỉnh "+ ml.getIndexPointA() + " đến " + ml.getIndexPointB();
                int select = JOptionPane.showConfirmDialog(this, message, "Xóa cạnh", JOptionPane.OK_CANCEL_OPTION);
                if(select == 0){
                    myDraw.deleteLine(index);
                    actionUpdate();
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Không có đỉnh hoặc cạnh nào được chọn!");
            }
	}
        else{
            // show message dialog
            String message = "Bạn muốn xóa đỉnh " + index;
            int select = JOptionPane.showConfirmDialog(this, message, "Xóa đỉnh", JOptionPane.OK_CANCEL_OPTION);
            if(select == 0){
                myDraw.deletePoint(index);
		actionUpdate();
            }
	}
    }

    private void updateListPoint(){
	int size = myDraw.getData().getArrMyPoint().size();
	String listPoint[] = new String[size];
	listPoint[0] = "Bắt đầu";
	for(int i = 1; i < listPoint.length; i++){
            listPoint[i] = String.valueOf(i);
	}
        if(size > 1){
            listPoint = new String[size + 1];
            listPoint[0] = "Kết thúc";
            for(int i = 1; i < listPoint.length; i++){
                listPoint[i] = String.valueOf(i);
            }
            listPoint[listPoint.length - 1] = "All";
        }
        else{
            listPoint = new String[1];
            listPoint[0] = "Kết thúc";
	}
    }

    private void setEnableMapType(boolean mapType){
	setDrawResultOrStep(false);
	myDraw.repaint();
	resetDataDijkstra();
	loadMatrix();
    }

    private void setDrawResultOrStep(boolean check){
	myDraw.setDrawResult(check);
	myDraw.setDrawStep(check);
    }

    private void resetDataDijkstra(){
	step = 0;
	euler = new MyEuler();
	euler.setArrMyPoint(myDraw.getData().getArrMyPoint());
	euler.setArrMyLine(myDraw.getData().getArrMyLine());
	euler.input();
	euler.processInput();
    }

    private void reDraw() {
	myDraw.setReDraw(true);
	myDraw.repaint();
    }

    private void clearMatrix() {
	DefaultTableModel model = new DefaultTableModel();
	tableMatrix.setModel(model);
    }

    private void clearLog() {
        DefaultTableModel model = new DefaultTableModel();
	tableLog.setModel(model);
	clearPath();
    }

    private void clearPath() {
	textLog.setText("Hiển thị kết quả...");
        textLog.setFont(font1);
    }
    // tải lên ma trận
    private void loadMatrix() {
	final int width = 35;
	final int col = WIDTH_SELECT / width - 1;
	int infinity = euler.getInfinity();
	int a[][] = euler.getA();
	head = new String[a.length - 1];
	data = new String[a[0].length - 1][a.length - 1];
	for(int i = 1; i < a[0].length; i++){
            head[i - 1] = String.valueOf(i);
            for(int j = 1; j < a.length; j++){
		if(a[i][j] == infinity){
//                  data[i - 1][j - 1] = "∞";
                    data[i - 1][j - 1] = "0";
		}
                else{
                    data[i - 1][j - 1] = String.valueOf(a[i][j]);
		}
            }
	}
	DefaultTableModel model = new DefaultTableModel(data, head);
	tableMatrix.setModel(model);
	if(tableMatrix.getColumnCount() > col){
            for(int i = 0; i < head.length; i++){
		TableColumn tc = tableMatrix.getColumnModel().getColumn(i);
		tc.setPreferredWidth(width);
            }
            tableMatrix.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
        else{
            tableMatrix.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}
    }
    // tải lên log
    private void loadLog(boolean isStep){
	final int width = 70;
	final int col = tableLog.getWidth() / width - 1;
	int infinity = euler.getInfinity();
	int logLen[][] = euler.getLogLen();
	int logP[][] = euler.getLogP();
	head = new String[logLen.length - 1];
	data = new String[euler.getNumberPointChecked()][logLen.length - 1];
	boolean check[] = new boolean[logLen.length - 1];

	for(int i = 0; i < logLen.length - 1; i++){
            head[i] = String.valueOf(i + 1);
            check[i] = false;
            data[0][i] = "[∞, ∞]";
	}

	data[0][indexBeginPoint - 1] = "[0, " + indexBeginPoint + "]";

        for(int i = 1; i < data.length; i++){
            int min = infinity, indexMin = -1;
            // // check "*" for min len
            for(int j = 1; j < logLen.length; j++){
		if(min > logLen[i][j] && !check[j - 1]){
                    min = logLen[i][j];
                    indexMin = j - 1;
		}
            }
            if(indexMin > -1){
		check[indexMin] = true;
            }
            for(int j = 1; j < logLen.length; j++){
                if(min > logLen[i][j] && !check[j - 1]){
                    min = logLen[i][j];
                    indexMin = j - 1;
		}

                String p = "∞";
		if(logP[i][j] > 0){
                    p = logP[i][j] + "";
		}
		if(check[j - 1]){
                    data[i][j - 1] = "-";
		}
                else if(logLen[i][j] == infinity){
                    data[i][j - 1] = "[∞, " + p + "]";
		}
                else{
                    data[i][j - 1] = "[" + logLen[i][j] + ", " + p + "]";
		}
            }
            if(indexMin > -1){
		data[i - 1][indexMin] = "*" + data[i - 1][indexMin];
            }
	}
        // check "*" for min len of row last
	int min = infinity, indexMin = -1;
	for(int j = 1; j < logLen.length; j++){
            if(min > logLen[data.length - 1][j] && !check[j - 1]){
		min = logLen[data.length - 1][j];
		indexMin = j - 1;
            }
	}
	if(indexMin > -1){
            check[indexMin] = true;
            data[data.length - 1][indexMin] = "*" + data[data.length - 1][indexMin];
	}

	// update data for table log
	DefaultTableModel model = new DefaultTableModel(data, head);
	tableLog.setModel(model);
	if(tableLog.getColumnCount() > col){
            for(int i = 0; i < head.length; i++){
		TableColumn tc = tableLog.getColumnModel().getColumn(i);
		tc.setPreferredWidth(width);
            }
	}
        else{
	}
    }
    // đầu vào ma trận
    private void processInputMatrix() {
	int numberPoint = 0;
	boolean isSuccess = true;
	try {
            numberPoint = Integer.parseInt(textNumerPoint.getText());
            int a[][] = new int[numberPoint][numberPoint];
            String temp = textMatrix.getText();
            Scanner scan = new Scanner(temp);
            for(int i = 0; i < numberPoint; i++){
		for(int j = 0; j < numberPoint; j++){
                    try {
			a[i][j] = scan.nextInt();
                    } catch (InputMismatchException e) {
                        JOptionPane.showMessageDialog(null, "Enter your matrix!");
			isSuccess = false;
			break;
                    }
		}
		if(!isSuccess){
                    break;
		}
            }
            for(int i = 0; i < numberPoint; i++){
		for(int j = 0; j < numberPoint; j++){
                    System.out.printf("%3d", a[i][j]);
		}
		System.out.println();
            }
            scan.close();
            myDraw.setA(a);
            myDraw.convertMatrixToData();
            myDraw.repaint();
            euler.setA(a);
	} catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Enter one integer number < 30!");
	}
    }

    private boolean checkRun() {
	int size = myDraw.getData().getArrMyPoint().size() - 1;
	if(indexEndPoint == size + 1){ // all Point
            indexEndPoint = -1;
	}
        if(size < 1 || indexBeginPoint == 0 || indexEndPoint == 0){
            JOptionPane.showMessageDialog(null, "Lỗi chọn đỉnh hoặc không cập nhật đồ thị cho các đỉnh đã chọn",
                    "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
	}
	return true;
    }

    private void setBeginEndPoint() {
	myDraw.setIndexBeginPoint(indexBeginPoint);
	myDraw.setIndexEndPoint(indexEndPoint);
	euler.setBeginPoint(indexBeginPoint);
	euler.setEndPoint(indexEndPoint);
    }
    // hiển thị số miền liên thông
    private void countConnected(){
        textLog.setText(euler.findConnectedComponent());
    }
    // hiển thị kiểm tra chu trình Euler
    private void checkEulerCycle(){
        textLog.setText(euler.checkEulerCircuit());
    }
    @Override
    public void actionPerformed(ActionEvent e) {
	String command = e.getActionCommand();
	// select button in paint
	if(e.getSource() == btnUpdate){
            actionUpdate();
        }
        if(e.getSource() == btnPoint){
            actionDrawPoint();
        }
        if(e.getSource() == btnLine){
            actionDrawLine();
	}
	if(e.getSource() == btnMove){
            myDraw.setDraw(3);
	}
        if(e.getSource() == btnNew){
            actionNew();
	}
        // select menu bar
	if(command == "New"){
            actionNew();
	}
	if(command == "Open" || e.getSource() == btnOpen){
            actionOpen();
	}
	if(command == "Save" || e.getSource() == btnSave){
            actionSave();
	}
	if(command == "Exit"){
            System.exit(0);
	}
	if(command == "Xóa"){
            showDialogDelete();
	}
        if (e.getSource() == btnCon) {
//            euler.inDFS();
//            euler.outputMatrix();
//            euler.test();
//            euler.dfs(1);
            countConnected();
	}
        if (e.getSource() == btnEulerCircuit) {
//            euler.inDFS();
//            if(euler.isConnected()){
//                System.out.println("la do thi lien thong");
//            }
//            else System.out.println("khong nhe");
//              if(euler.isEulerCircuit()){
//                  System.out.println("do thi co ton tai chu trinh Euler");
//              }
//              else System.out.println("khong co chu trinh Euler");
//            euler.tinhbac();
            checkEulerCycle();
        }
    }
}