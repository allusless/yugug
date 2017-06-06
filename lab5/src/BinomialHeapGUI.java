import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

class Drawheap extends JPanel{
	private static final long serialVersionUID = 1L;
	private static int SIZE_OF_NODE = 20;
	private final static int SPACE_OF_NODE = 10;
	private BinomialHeap<Integer, Double> heap;
	
	public Drawheap(BinomialHeap<Integer, Double> heap){
		this.heap = heap;
	}
	public void setheap(BinomialHeap<Integer, Double> heap){
		this.heap = heap;
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		int x1 = 10;
		int x2 = getWidth()-10;
		if(heap.head != null){
			int size = heap.head.getSize();
			if(size != 0){
				drawNode(heap.head, x1, x2, 0, size, g);
			}
		}
	}
	
	public boolean[] toBooleanBinary(int num){
		int maxDegree = 1;
		int count = 1;
		while(maxDegree*2 <= num){
			maxDegree *= 2;
			count++;
		}
		boolean splitDegree[] = new boolean[count];
		for(int i = count-1, degree = 0, maxDeg = maxDegree; i >= 0; i--, maxDeg /= 2){
			if(degree + maxDeg <= num){
				splitDegree[i] = true;
				degree+=maxDeg;
			}
		}
		return splitDegree;
	}
	
	public void drawNode(BinomialHeapNode<Integer, Double> node, int x1, int x2, int lvl, int size, Graphics g){
		if(node != null){
			g.setColor(Color.red);
			boolean[] res = toBooleanBinary(size);
			BinomialHeapNode<Integer, Double> now = node;
			int start = 0, pow, newX1, newX2;
			for(int i = 0; i < res.length; i++){
				if(res[i]){
					if(now != null){
						if(lvl == 0)
							pow = (int)Math.pow(2, i);
						else
							pow = (int)Math.pow(2, res.length - 1 - i);
						newX1 = x1+(x2-x1)*start/size; 
						newX2 = x1+(x2-x1)*(start+pow)/size;
						if(lvl != 0) //&& now == node 
							g.drawLine(x2-SIZE_OF_NODE+10, (lvl-1)*(SIZE_OF_NODE+10)+10, newX2 - SIZE_OF_NODE, lvl*(SIZE_OF_NODE+10)+10);
						if(lvl == 0)
							g.drawLine(x2-SIZE_OF_NODE, (lvl)*(SIZE_OF_NODE+10)+10, newX2 - SIZE_OF_NODE+10, (lvl)*(SIZE_OF_NODE+10)+10);	
						g.drawOval(newX2 - SIZE_OF_NODE, lvl*(SIZE_OF_NODE+10), SIZE_OF_NODE, SIZE_OF_NODE);
						g.fillOval(newX2 - SIZE_OF_NODE, lvl*(SIZE_OF_NODE+10), SIZE_OF_NODE, SIZE_OF_NODE);
						g.setColor(Color.black);
						g.drawString(" "+now.key, newX2-SIZE_OF_NODE, (lvl)*(SIZE_OF_NODE+10)+15);
						g.setColor(Color.BLUE);
						if(now.child != null){
							drawNode(now.child, newX1, newX2, lvl+1, pow-1, g);
						}
						start += pow; 
						now = now.sibling;
					}
				}
			}
		}
	}
}

public class BinomialHeapGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private BinomialHeap<Integer, Double> heap = new BinomialHeap<Integer, Double>();
	private Drawheap draw = new Drawheap(heap);
	private JTextField keyInsertTextField;
	private JTextField dataInsertTextField;
	private JTextField keyDeleteTextField;
	private JTextField keyFindTextField;
	private JTextField keyHeightTextField;
	private JTextField keyDepthTextField;
	private JLabel resultOfDeleteNodeLabel;
	private JLabel resultOfFindNodeLabel;
	private JLabel resultOfHeightNodeLabel;
	private JLabel resultOfDepthNodeLabel;
	
	private JMenuBar menu = new JMenuBar();
	private JMenu[] menus = {new JMenu("Create"), new JMenu("Node")};
	private JMenuItem[] create = {new JMenuItem("New Heap"), new JMenuItem("Random Heap")};
	private JMenuItem[] node = {new JMenuItem("Insert"), new JMenuItem("Delete")};
	
	private JFrame insertNode = new JFrame("Insert node");
	private JFrame deleteNode = new JFrame("Delete node");

	private ActionListener newGraphMenuAL = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			heap = new BinomialHeap<Integer, Double>();
			draw.setheap(heap);
			draw.repaint();
		}
	};
	private ActionListener randomGraphMenuAL = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			heap = new BinomialHeap<Integer, Double>();
			draw.setheap(heap);
			Random num = new Random();
			for(int i = 0; i<15; i++){
				heap.insert(new BinomialHeapNode<Integer, Double>(Math.abs(new Integer(num.nextInt()%50)), new Double(((int)(num.nextDouble()*10000))/100)));
			}
			draw.repaint();
		}
	};
	private ActionListener insertNodeMenuAL = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			insertNode.setVisible(true);


		}
	};
	private ActionListener insertNodeButtonAL = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			heap.insert(new BinomialHeapNode<Integer, Double>(new Integer(keyInsertTextField.getText()), new Double(dataInsertTextField.getText())));
			draw.repaint();
		}
	};
	private ActionListener deleteNodeMenuAL = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			deleteNode.setVisible(true);
		}
	};
	private ActionListener deleteNodeButtonAL = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(!heap.isEmpty()){
				BinomialHeapNode<Integer, Double> search = heap.search(new Integer(keyDeleteTextField.getText()));
				if(search != null){
					heap.delete(search);
					draw.repaint();
					resultOfDeleteNodeLabel.setText("Node was deleted");
				}else{
					resultOfDeleteNodeLabel.setText("Node was not found");
				}
			}else{
				resultOfDeleteNodeLabel.setText("heap is empty");
			}
		}
	};
	
	public BinomialHeapGUI(){
		heap = new BinomialHeap<Integer, Double>();
		draw = new Drawheap(heap);
		add(draw);
		for(int i = 0; i<2; i++){
			menu.add(menus[i]);
		}


		for(int i = 0; i<2; i++){
			menus[0].add(create[i]);
		}
		for(int i = 0; i<2; i++){
			menus[1].add(node[i]);
		}

		create[0].addActionListener(newGraphMenuAL);
		create[1].addActionListener(randomGraphMenuAL);
		
		node[0].addActionListener(insertNodeMenuAL);
		JPanel keyInsertPanel = new JPanel();
		keyInsertPanel.setLayout(new BorderLayout());
		JLabel keyInsertLabel = new JLabel("Key ");
		keyInsertPanel.add(BorderLayout.WEST, keyInsertLabel);
		keyInsertTextField = new JTextField();
		keyInsertTextField.setColumns(10);
		keyInsertPanel.add(BorderLayout.EAST, keyInsertTextField);

		JPanel dataInsertPanel = new JPanel();
		dataInsertPanel.setLayout(new BorderLayout());
		JLabel dataLabel = new JLabel("Data ");
		dataInsertPanel.add(BorderLayout.WEST, dataLabel);
		dataInsertTextField = new JTextField();
		dataInsertTextField.setColumns(10);
		dataInsertPanel.add(BorderLayout.EAST, dataInsertTextField);
		
		JButton insertButton = new JButton("Insert");
		insertButton.addActionListener(insertNodeButtonAL);
		
		insertNode.setLayout(new FlowLayout());
		insertNode.add(keyInsertPanel);
		insertNode.add(dataInsertPanel);
		insertNode.add(insertButton);
		insertNode.setSize(200,100);
		
		node[1].addActionListener(deleteNodeMenuAL);
		deleteNode.setLayout(new FlowLayout());
		JPanel keyDeletePanel = new JPanel();
		keyDeletePanel.setLayout(new BorderLayout());
		JLabel keyDeleteLabel = new JLabel("Key ");
		keyInsertPanel.add(BorderLayout.WEST, keyDeleteLabel);
		keyDeleteTextField = new JTextField();
		keyDeleteTextField.setColumns(10);
		keyDeletePanel.add(BorderLayout.EAST, keyDeleteTextField);
		deleteNode.add(keyDeletePanel);
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(deleteNodeButtonAL);
		resultOfDeleteNodeLabel = new JLabel();
		deleteNode.add(deleteButton);
		deleteNode.add(resultOfDeleteNodeLabel);
		deleteNode.setSize(200,100);

		setJMenuBar(menu);
		
	}
	
	public static void run(JFrame f, int width, int height){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				f.setTitle(f.getClass().getSimpleName());
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setSize(width, height);
				f.setVisible(true);
			}
		});
	}
	
	public static void main(String[] args){
		run(new BinomialHeapGUI(), 700, 400);
	}
}