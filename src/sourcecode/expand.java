

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.Iterator;
import prefuse.action.RepaintAction;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.action.Action;
import prefuse.action.ActionList;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataSizeAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.action.layout.graph.NodeLinkTreeLayout;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Tuple;
import prefuse.visual.AggregateItem;
import prefuse.data.event.TupleSetListener;
import prefuse.data.search.PrefixSearchTupleSet;
import prefuse.data.search.SearchTupleSet;
import prefuse.data.tuple.TupleSet;
import prefuse.demos.TreeView.OrientAction;
//import prefuse.demos.OrientAction;
import prefuse.render.AbstractShapeRenderer;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.AggregateItem;
import prefuse.visual.VisualItem;
import prefuse.visual.expression.InGroupPredicate;
import prefuse.visual.sort.TreeDepthItemSorter;
import prefuse.action.layout.CollapsedSubtreeLayout;
import prefuse.activity.Activity;
import prefuse.controls.ControlAdapter;
import prefuse.controls.FocusControl;
import prefuse.controls.PanControl;
import prefuse.controls.DragControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;



public class expand extends Display  {
    private static final String SIZE = "size";

    private static final String LABEL = "label";

    private static final String Index = "Index";

    Graph graph;

    public static final String GRAPH = "graph";

    public static final String NODES = "graph.nodes";

    public static final String EDGES = "graph.edges";
    private int m_orientation = Constants.ORIENT_LEFT_RIGHT;
    private LabelRenderer m_nodeRenderer;
    private EdgeRenderer m_edgeRenderer;
    private String m_label = "label";

    NodeClicked aaa = new NodeClicked();
    String current1 = aaa.nodename;
    int depth = aaa.clickindex;
    public int index=0;





    public expand(int st,String n,String pindex, int indSpec) {


        // setup a visualisation
        super(new Visualization());

        System.out.println(st +"  "+n+"    "+pindex );



        // generate a graph
        initGraph(n,st,pindex,indSpec);

        //  standard labelRenderer for the given label
        LabelRenderer nodeRenderer = new LabelRenderer(LABEL);
        // rendererFactory for the visualization items
        DefaultRendererFactory rendererFactory = new
DefaultRendererFactory();
        // set the labelRenderer
        rendererFactory.setDefaultRenderer(nodeRenderer);
        m_vis.setRendererFactory(rendererFactory);

        // Color Actions
        ColorAction nodeText = new ColorAction(NODES, VisualItem.TEXTCOLOR);
        nodeText.setDefaultColor(ColorLib.gray(0));
        ColorAction nodeStroke = new ColorAction(NODES,
VisualItem.STROKECOLOR);
        nodeStroke.setDefaultColor(ColorLib.gray(100));
        ColorAction nodeFill = new ColorAction(NODES, VisualItem.FILLCOLOR);
        nodeFill.setDefaultColor(ColorLib.gray(255));
        ColorAction edgeStrokes = new ColorAction(EDGES,
VisualItem.STROKECOLOR);
        edgeStrokes.setDefaultColor(ColorLib.gray(100));

       // ColorAction fill = new ColorAction(NODES, VisualItem.FILLCOLOR,
ColorLib.rgb(200,200,255);
       //fill.add(VisualItem.FIXED, ColorLib.rgb(255,100,100));
       // fill.add(VisualItem.HIGHLIGHT, ColorLib.rgb(255,200,125));

        // bundle the color actions
        ActionList draw = new ActionList();
    //    draw.add(fill);
        draw.add(new ColorAction(NODES, VisualItem.STROKECOLOR, 0));
        draw.add(new ColorAction(NODES, VisualItem.TEXTCOLOR,
ColorLib.rgb(0,0,0)));
        draw.add(new ColorAction(EDGES, VisualItem.FILLCOLOR,
ColorLib.gray(200)));
        draw.add(new ColorAction(EDGES, VisualItem.STROKECOLOR,
ColorLib.gray(200)));
        draw.add(nodeText);
        draw.add(nodeStroke);
        draw.add(nodeFill);
        draw.add(edgeStrokes);

        ActionList animate = new ActionList(Activity.INFINITY);
     //   animate.add(fill);
        animate.add(new RepaintAction());


        m_nodeRenderer = new LabelRenderer(m_label);

m_nodeRenderer.setRenderType(AbstractShapeRenderer.RENDER_TYPE_FILL);
        m_nodeRenderer.setHorizontalAlignment(Constants.LEFT);
        m_nodeRenderer.setRoundedCorner(8,8);
        m_edgeRenderer = new EdgeRenderer(Constants.EDGE_TYPE_CURVE);

        DefaultRendererFactory rf = new
DefaultRendererFactory(m_nodeRenderer);
        m_vis.setRendererFactory(rf);






        // DataSizeAction
        DataSizeAction nodeDataSizeAction = new DataSizeAction(NODES, SIZE);
        draw.add(nodeDataSizeAction);
        m_vis.putAction("draw", draw);
        m_vis.putAction("layout", animate);

        m_vis.runAfter("draw", "layout");

        // create the layout action for the graph
        NodeLinkTreeLayout treeLayout = new
NodeLinkTreeLayout(GRAPH,m_orientation, 80,5, 10);
        treeLayout.setLayoutAnchor(new Point2D.Double(250,-150));
        m_vis.putAction("treeLayout", treeLayout);
        m_vis.addFocusGroup("selected");

        CollapsedSubtreeLayout subLayout =
                new CollapsedSubtreeLayout(GRAPH, m_orientation);
            m_vis.putAction("subLayout", subLayout);




        m_vis.run("treeLayout");
        m_vis.run("draw");


        pan(250, 250);
        setHighQuality(true);
        addControlListener(new ZoomControl());
        addControlListener(new PanControl());
        addControlListener(new DragControl(true));
        addControlListener(new NodeClicked());
        addControlListener(new FocusControl());





        registerKeyboardAction(
                new OrientAction(Constants.ORIENT_LEFT_RIGHT),
                "left-to-right", KeyStroke.getKeyStroke("ctrl 1"),
WHEN_FOCUSED);
            registerKeyboardAction(
                new OrientAction(Constants.ORIENT_TOP_BOTTOM),
                "top-to-bottom", KeyStroke.getKeyStroke("ctrl 2"),
WHEN_FOCUSED);
            registerKeyboardAction(
                new OrientAction(Constants.ORIENT_RIGHT_LEFT),
                "right-to-left", KeyStroke.getKeyStroke("ctrl 3"),
WHEN_FOCUSED);
            registerKeyboardAction(
                new OrientAction(Constants.ORIENT_BOTTOM_TOP),
                "bottom-to-top", KeyStroke.getKeyStroke("ctrl 4"),
WHEN_FOCUSED);


    }

    private void initGraph(String n,int st,String parIndex,int indSpec) {

    graph = new Graph();
        int i=0,storenum=0,len=0,x;
        String current=" ",m=" ",z=" ",l;
        graph.addColumn(LABEL, String.class);
        graph.addColumn(SIZE, int.class);
        graph.addColumn(Index, int.class);

        Node p = addNode("Start", 100,-1);
        Node v=null;
        Node prevnode=null;
        String name="Arts1";
        String arr2[],arr1[]=new String[100];;
        String strLine,strLine1="world of arts";
       System.out.println("    "+strLine1);
        
       


  
    try{
     // Open the file that is the first
     // command line parameter



      FileReader fr = new FileReader("D:/Dmoz/categories.txt");
      BufferedReader br = new BufferedReader(fr);
     while ((strLine = br.readLine()) != null)
     {
    
    arr2= strLine.split("/");
     m=str_piece(strLine,1);
     z=str_piece(strLine,0);
   
     

  
      
      if(arr2.length<=st)
      {
      	
      continue;
      
      }
 
     // System.out.println(arr2[st]+st+"   .." +n);

     if (parIndex.equalsIgnoreCase(strLine))
        {
    	 //System.out.println(parIndex);
    	  len=1;
      v=p;
      for(x=0;x<arr2.length;x++)
          {

      Node h =addNode(arr2[x]+x, 20+x,x);
         graph.addEdge(v, h);
         v=h;
          }
        }
      
      
      else if ((n.equalsIgnoreCase(arr2[st]+st)&& len==1) &&  (parIndex.equalsIgnoreCase(strLine.substring(0, parIndex.length()))) && index(strLine)<indSpec)
      {
    	  //System.out.println(parIndex);
    	  
    	  Node h =addNode(z, index(strLine)+20,index(strLine));
		  		for(int j=0;;j++) 
		         {
		        v=(getNode(j));
		       
		        current=getName(v);
		       
		       
		           		        	
		       // System.out.println("parent="+"  "+arr2[st-1]+(st-1)+parIndex);
		          		        
		        
		         if (current.compareTo(m)==0)
		        	{
		        	graph.addEdge(v, h);
		        	
		        	//System.out.println("prev="+prevnodename);	
		        	
		        break;
		        	}
		        }
		  		
      
		          
		  	
    	  
    	  
      }









        //   p = h;

     }
    
     //Close the input stream
     fr.close();
       }catch (Exception e)

       {//Catch exception if any
     System.err.println("Error: " + e.getMessage());
     }
  

  


       m_vis.addGraph(GRAPH, graph);

   }



    public class NodeClicked extends ControlAdapter
    {

    public String nodename =null;
    public int clickindex = 0;

    public void itemPressed(VisualItem item, java.awt.event.MouseEvent e)
    {			
    	if ( e.getClickCount() == 2 )
        {
    		Node v=null;
        	String pindex="/";
        	String parent="/";
        	int ind;
        	String itemname=item.getString(LABEL);
        	for (int i=0;;i++)
        	{
        		 v=(getNode(i));
    		       String current=getName(v);
    		       if (current.compareTo(itemname)==0)
           		{	ind=(int) v.get(Index);
           			if(ind<=9)
           				itemname=itemname.substring(0,itemname.length()-1);
           			else
           				itemname=itemname.substring(0,itemname.length()-2);
           			while(pindex!="Start")
           			{
           				Node h=v.getParent();
               			pindex=getName(h);
               			ind=(int) h.get(Index);
               			
               			if(pindex=="Start")
               				break;
               			
               			if(ind<=9)
               			
               			parent="/"+pindex.substring(0,pindex.length()-1)+parent;
               			
               			else
               				parent="/"+pindex.substring(0,pindex.length()-2)+parent;
               			
           				v=h;
           			}
           			break;
           		}
           		
           	}
        	
        	
        	
        //System.out.println(parent.substring(1)+itemname);

        	
        	

          urllink mainPanel = new urllink(item.getInt(Index),(item.getString(LABEL)),parent.substring(1)+itemname,10000);
   	      JFrame frame = new JFrame("Choose to expand or select a link");
   	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   	      frame.getContentPane().add(mainPanel);
   	      frame.pack();
   	      frame.setLocationByPlatform(true);
   	      frame.setVisible(true);
       		
        
    		
    		
    		
    		
        }
    	
    	
    	 
    	
    	
    	

                

                  item.setFillColor(ColorLib.rgb(0,255,0));
                  item.setStrokeColor(ColorLib.rgb(255,0,0));
                  item.getVisualization().repaint();


                }



    public void itemReleased(VisualItem item, MouseEvent evt) {


          item.setFillColor(ColorLib.rgb(95,55,255));
          item.setStrokeColor(item.getEndStrokeColor());
          item.getVisualization().repaint();
        }
    }


    


    private Node addNode(String label, int size,int index) {
        Node node = graph.addNode();
        node.setString(LABEL, label);
        node.setInt(Index, index);
        node.setInt(SIZE, size);
        return node;
    }

     private Node getNode(int no) {
        Node node = graph.getNode(no);
         return node;
    }

     private String getName(Node node1) {
         String name=node1.getString(LABEL);
         return name;
     }
     private static String str_piece(String str, int no) {
    String str_result = str;
    int num=0;


    for(int i=0;i<str.length();i++)
    {
    if(str_result.charAt(i)=='/')
         {

    num++;

    }
    }

    if(no==2)
    {
    if (num>=2)
    {
    	String arr[]=str_result.split("/");
     str_result=arr[num-no]+(num-no);}
    else
    return "Start";


    }

    else if(num!=0)
    {
    String arr[]=str_result.split("/");
         str_result=arr[num-no]+(num-no);


    //System.out.println(num);
    }

    else if(num==0)
    {
    str_result=str_result+0;

    }
        return str_result;
    }

     private static int index(String str) {
  String str_result = str;
  int num=0;


  for(int i=0;i<str.length();i++)
  {
  if(str_result.charAt(i)=='/')
      {

  num++;

  }
  }


  return num;
  //System.out.println(num);


  }







    public void setOrientation(int orientation) {
        NodeLinkTreeLayout rtl
            = (NodeLinkTreeLayout)m_vis.getAction("treeLayout");
        CollapsedSubtreeLayout stl
            = (CollapsedSubtreeLayout)m_vis.getAction("subLayout");
        switch ( orientation ) {
        case Constants.ORIENT_LEFT_RIGHT:
            m_nodeRenderer.setHorizontalAlignment(Constants.LEFT);
            m_edgeRenderer.setHorizontalAlignment1(Constants.RIGHT);
            m_edgeRenderer.setHorizontalAlignment2(Constants.LEFT);
            m_edgeRenderer.setVerticalAlignment1(Constants.CENTER);
            m_edgeRenderer.setVerticalAlignment2(Constants.CENTER);
            break;
        case Constants.ORIENT_RIGHT_LEFT:
            m_nodeRenderer.setHorizontalAlignment(Constants.RIGHT);
            m_edgeRenderer.setHorizontalAlignment1(Constants.LEFT);
            m_edgeRenderer.setHorizontalAlignment2(Constants.RIGHT);
            m_edgeRenderer.setVerticalAlignment1(Constants.CENTER);
            m_edgeRenderer.setVerticalAlignment2(Constants.CENTER);
            break;
        case Constants.ORIENT_TOP_BOTTOM:
            m_nodeRenderer.setHorizontalAlignment(Constants.CENTER);
            m_edgeRenderer.setHorizontalAlignment1(Constants.CENTER);
            m_edgeRenderer.setHorizontalAlignment2(Constants.CENTER);
            m_edgeRenderer.setVerticalAlignment1(Constants.BOTTOM);
            m_edgeRenderer.setVerticalAlignment2(Constants.TOP);
            break;
        case Constants.ORIENT_BOTTOM_TOP:
            m_nodeRenderer.setHorizontalAlignment(Constants.CENTER);
            m_edgeRenderer.setHorizontalAlignment1(Constants.CENTER);
            m_edgeRenderer.setHorizontalAlignment2(Constants.CENTER);
            m_edgeRenderer.setVerticalAlignment1(Constants.TOP);
            m_edgeRenderer.setVerticalAlignment2(Constants.BOTTOM);
            break;
        default:
            throw new IllegalArgumentException(
                "Unrecognized orientation value: "+orientation);
        }
        m_orientation = orientation;
        rtl.setOrientation(orientation);
        stl.setOrientation(orientation);
    }

    public class OrientAction extends AbstractAction {
        private int orientation;

        public OrientAction(int orientation) {
            this.orientation = orientation;
        }
        public void actionPerformed(ActionEvent evt) {
            setOrientation(orientation);
            getVisualization().cancel("orient");
            getVisualization().run("treeLayout");
            getVisualization().run("orient");
        }
    }
}






