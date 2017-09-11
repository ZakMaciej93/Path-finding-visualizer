package editor;

import algorithms.AbstractAlgorithm;
import algorithms.AlgorithmFactory;
import graph.Edge;
import graph.GraphChangeEvent;
import graph.GraphChangeListener;
import graph.Vertex;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class MainWindow extends javax.swing.JFrame implements ActionListener {

    public static final byte EDIT_MODE = 0;
    public static final byte VERTEX_MODE = 1;
    public static final byte EDGE_MODE = 2;
    private static final int MIN_TIME = 200;
    private static final int MAX_TIME = 3000;
    private static final int DEF_TIME = (MIN_TIME + MAX_TIME) / 2;

    private JFrame tableWindow;
    private JFrame helpWindow;

    private Timer t;

    private AbstractAlgorithm algorithm;
    private String[] algorithms = AlgorithmFactory.getAlgorithmsArray();
    private VertexEditDialog vertexDialog;
    private EdgeEditDialog edgeDialog;
    private Vertex creatorVertex;
    private Vertex tail = null;
    private RoundTextArea algorithmLabel;

    private Vertex initialNode;
    private Vertex destinationNode;

    private byte workMode;
    private int vertexIndex = 1;
    private int edgeIndex = 1;
    private ButtonGroup buttonGroup;

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        t = new Timer(DEF_TIME, this);
        t.setRepeats(true);

        tableWindow = new JFrame("Tablica wyników");
        tableWindow.setSize(400, 300);
        tableWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        helpWindow = new JFrame("Instrukcja obsługi");
        helpWindow.setSize(400, 300);
        helpWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JTextArea helpArea = new JTextArea(helpText);
        helpArea.setEditable(false);
        helpArea.setWrapStyleWord(true);
        helpArea.setColumns(25);
        helpArea.setLineWrap(true);
        helpWindow.getContentPane().add(new JScrollPane(helpArea));

        algorithmLabel = new RoundTextArea();

        initComponents();                                   //automatycznie utworzona funkcja inicjalizacji GUI

        editorPane.add(algorithmLabel, 0);
        algorithmLabel.setLocation(-100, -100);

        DefaultComboBoxModel model = new DefaultComboBoxModel<>(algorithms);
        jComboBox1.setModel(model);

        initialNode = null;
        destinationNode = null;

        this.vertexDialog = new VertexEditDialog(this, true); //inicjalizacja okna edycji wierzcholkow
        this.edgeDialog = new EdgeEditDialog(this, true); //inicjalizacja okna edycji krweędzi
        this.buttonGroup = new ButtonGroup();   //Tworzenie grupy przycisków dla wyboru trybu pracy
        buttonGroup.add(edgeButton);    //tryb krawędzi
        buttonGroup.add(editButton);    //tryb edycji
        buttonGroup.add(vertexButton);  //tryb wierzchołków

        creatorVertex = new Vertex("", 0, 0);     //tworzy nowe wierzcholki

        //Podążanie creatorVertex za myszką w obszarze editorPane
        editorPane.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
            }

            public void mouseMoved(MouseEvent e) {
                try {
                    Point p = editorPane.getMousePosition();
                    creatorVertex.setLocation(p.x - Vertex.DEF_RADIUS, p.y - Vertex.DEF_RADIUS);
                    editorPane.repaint();
                } catch (NullPointerException ex) {
                }
            }
        });

        //Reakcja na zajście zmiany w grafie
        editorPane.addGraphChangeListener(new GraphChangeListener() {
            public void graphChanged(GraphChangeEvent evt) {
                resetAlgorithm();
                t.stop();
            }
        });

        //Podążanie creatorVertex za myszką w swoim własnym obszarze (w innym wypadku
        //ruchy są nieprecyzyjne)
        creatorVertex.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
            }

            public void mouseMoved(MouseEvent e) {
                try {
                    Point p = editorPane.getMousePosition();
                    creatorVertex.setLocation(p.x - Vertex.DEF_RADIUS, p.y - Vertex.DEF_RADIUS);
                    editorPane.repaint();
                } catch (NullPointerException ex) {
                }
            }
        });

        //Przypisanie funkcjonalności creatorVertex
        creatorVertex.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }

            //Utworzenie nowego wierzchołka na pozycji creatorVertex po nacisnieciu lewego przycisku myszki i dodanie
            //jego funkcjonalności
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    createVertex(creatorVertex.getX(), creatorVertex.getY());
                }

            }

        });
        this.editorPane.add(creatorVertex);     //dodanie cratorVertex do panelu edyctora (nie do grafu!)
        editButton.doClick();   //przejście do trybu edycji po uruchomieniu aplikacji
        itemShowVertices.doClick();
        itemShowEdgeLabels.doClick();
        itemShowEdges.doClick();
        itemShowAlgorithmSteps.doClick();
        jComboBox1.setSelectedIndex(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider();
        labelInitialNode = new javax.swing.JLabel();
        labelDestinationNode = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jSeparator4 = new javax.swing.JSeparator();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        editButton = new javax.swing.JToggleButton();
        vertexButton = new javax.swing.JToggleButton();
        edgeButton = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        editorPane = new graph.GraphPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuGraph = new javax.swing.JMenu();
        itemClearGraph = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        itemExit = new javax.swing.JMenuItem();
        menuView = new javax.swing.JMenu();
        itemShowVertices = new javax.swing.JCheckBoxMenuItem();
        itemShowCords = new javax.swing.JCheckBoxMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        itemShowEdges = new javax.swing.JCheckBoxMenuItem();
        itemShowEdgeLabels = new javax.swing.JCheckBoxMenuItem();
        itemShowEdgeWeights = new javax.swing.JCheckBoxMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        itemShowAlgorithmSteps = new javax.swing.JCheckBoxMenuItem();
        itemShowAlgorithmTable = new javax.swing.JCheckBoxMenuItem();
        menuHelp = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        itemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Wizualizacja algorytmów wyszukiwania ścieżek w grafie");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Wybrany algorytm:");

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Wierzchołek startowy (żółty):");

        jLabel3.setText("Wierzchołek docelowy (niebieski):");

        jButton1.setText("RESET");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("NASTĘPNY KROK");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Sterowanie:");

        jLabel5.setText("Tryb automatyczny:");

        jButton3.setText("START");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("STOP");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel6.setText("Opóźnienie:");

        jSlider1.setMaximum(MAX_TIME);
        jSlider1.setMinimum(MIN_TIME);
        jSlider1.setPaintLabels(true);
        jSlider1.setValue(DEF_TIME);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        labelInitialNode.setText("Nie wybrano");

        labelDestinationNode.setText("Nie wybrano");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jScrollPane2.setViewportView(jTextArea1);

        jButton5.setText("Wyczyść");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Wyczyść");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel7.setText("0,2 s");

        jLabel8.setText("3 s");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(labelInitialNode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(labelDestinationNode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6))
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(jSeparator4)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelInitialNode)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDestinationNode)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        editButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/editor/iconCursor.png"))); // NOI18N
        editButton.setToolTipText("Tryb edycji grafu");
        editButton.setFocusable(false);
        editButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        editButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(editButton);

        vertexButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/editor/iconVertex.png"))); // NOI18N
        vertexButton.setToolTipText("Tryb dodawania wierzchołków");
        vertexButton.setFocusable(false);
        vertexButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        vertexButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        vertexButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vertexButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(vertexButton);

        edgeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/editor/iconEdge.png"))); // NOI18N
        edgeButton.setToolTipText("Tryb dodawania krawędzi");
        edgeButton.setFocusable(false);
        edgeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        edgeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        edgeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edgeButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(edgeButton);

        editorPane.setOpaque(true);
        editorPane.setPreferredSize(new java.awt.Dimension(5000, 5000));
        jScrollPane1.setViewportView(editorPane);

        menuGraph.setText("Graf");

        itemClearGraph.setText("Nowy graf");
        itemClearGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemClearGraphActionPerformed(evt);
            }
        });
        menuGraph.add(itemClearGraph);
        menuGraph.add(jSeparator2);

        itemExit.setText("Zakończ");
        itemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemExitActionPerformed(evt);
            }
        });
        menuGraph.add(itemExit);

        jMenuBar1.add(menuGraph);

        menuView.setText("Widok");

        itemShowVertices.setText("Pokaż wierzchołki");
        itemShowVertices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemShowVerticesActionPerformed(evt);
            }
        });
        menuView.add(itemShowVertices);

        itemShowCords.setText("Pokaż współrzędne wierzchołków");
        itemShowCords.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemShowCordsActionPerformed(evt);
            }
        });
        menuView.add(itemShowCords);
        menuView.add(jSeparator1);

        itemShowEdges.setText("Rysuj krawędzie");
        itemShowEdges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemShowEdgesActionPerformed(evt);
            }
        });
        menuView.add(itemShowEdges);

        itemShowEdgeLabels.setText("Pokaż etykiety krawędzi");
        itemShowEdgeLabels.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemShowEdgeLabelsActionPerformed(evt);
            }
        });
        menuView.add(itemShowEdgeLabels);

        itemShowEdgeWeights.setText("Pokaż wagi krawędzi");
        itemShowEdgeWeights.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemShowEdgeWeightsActionPerformed(evt);
            }
        });
        menuView.add(itemShowEdgeWeights);
        menuView.add(jSeparator3);

        itemShowAlgorithmSteps.setText("Pokaż opis operacji algorytmu");
        itemShowAlgorithmSteps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemShowAlgorithmStepsActionPerformed(evt);
            }
        });
        menuView.add(itemShowAlgorithmSteps);

        itemShowAlgorithmTable.setText("Pokaż wyniki algorytmu");
        itemShowAlgorithmTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemShowAlgorithmTableActionPerformed(evt);
            }
        });
        menuView.add(itemShowAlgorithmTable);

        jMenuBar1.add(menuView);

        menuHelp.setText("Pomoc");

        jMenuItem1.setText("Instrukcja obsługi");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menuHelp.add(jMenuItem1);
        menuHelp.add(jSeparator5);

        itemAbout.setText("O programie");
        itemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAboutActionPerformed(evt);
            }
        });
        menuHelp.add(itemAbout);

        jMenuBar1.add(menuHelp);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        setWorkMode(EDIT_MODE);
    }//GEN-LAST:event_editButtonActionPerformed

    private void vertexButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vertexButtonActionPerformed
        setWorkMode(VERTEX_MODE);
    }//GEN-LAST:event_vertexButtonActionPerformed

    private void edgeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edgeButtonActionPerformed
        setWorkMode(EDGE_MODE);
    }//GEN-LAST:event_edgeButtonActionPerformed

    private void itemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_itemExitActionPerformed

    private void itemClearGraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemClearGraphActionPerformed
        editorPane.clearGraph();
        this.vertexIndex = 0;
        this.edgeIndex = 0;
    }//GEN-LAST:event_itemClearGraphActionPerformed

    private void itemShowVerticesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemShowVerticesActionPerformed
        editorPane.setVertexEnabled(itemShowVertices.getState());
    }//GEN-LAST:event_itemShowVerticesActionPerformed

    private void itemShowCordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemShowCordsActionPerformed
        editorPane.setCordsEnabled(itemShowCords.getState());
    }//GEN-LAST:event_itemShowCordsActionPerformed

    private void itemShowEdgesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemShowEdgesActionPerformed
        editorPane.setEdgeEnabled(itemShowEdges.getState());
    }//GEN-LAST:event_itemShowEdgesActionPerformed

    private void itemShowEdgeLabelsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemShowEdgeLabelsActionPerformed
        editorPane.setLabelEnabled(itemShowEdgeLabels.getState());
    }//GEN-LAST:event_itemShowEdgeLabelsActionPerformed

    private void itemShowEdgeWeightsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemShowEdgeWeightsActionPerformed
        editorPane.setWeightEnabled(itemShowEdgeWeights.getState());
    }//GEN-LAST:event_itemShowEdgeWeightsActionPerformed

    private void itemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAboutActionPerformed
        JOptionPane.showMessageDialog(this, "Niniejsza aplikacja została stworzona w ramach realizacji tematu pracy inzynierskiej:\n"
                + "\"Wizualizacja algorytmów wyszukiwania ścieżek w grafie\"\n\nAutor: Maciej Żak\nPromotor: Dr Tomasz Jach\n"
                + "Opiekun pracy: Mgr Iwona Polak\n\nUniwersytet Śląski w Katowicach\nWydział Informatyki i Nauki o Materiałach\nSosnowiec, 2017",
                "O programie", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_itemAboutActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        nextStep();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void itemShowAlgorithmTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemShowAlgorithmTableActionPerformed
        tableWindow.setVisible(itemShowAlgorithmTable.getState());
        resetTable();
    }//GEN-LAST:event_itemShowAlgorithmTableActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        algorithm = AlgorithmFactory.createAlgorithm(jComboBox1.getSelectedItem().toString());
        resetAlgorithm();
        jTextArea1.setText(algorithm.getDescription());
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void itemShowAlgorithmStepsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemShowAlgorithmStepsActionPerformed
        algorithmLabel.setVisible(itemShowAlgorithmSteps.getState());
    }//GEN-LAST:event_itemShowAlgorithmStepsActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        t.start();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        t.stop();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        t.setDelay(jSlider1.getValue());
        t.setInitialDelay(jSlider1.getValue());
        if (t.isRunning()) {
            t.restart();
        }
    }//GEN-LAST:event_jSlider1StateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        resetAlgorithm();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        initialNode = null;
        resetAlgorithm();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        destinationNode = null;
        resetAlgorithm();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        helpWindow.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton edgeButton;
    private javax.swing.JToggleButton editButton;
    private graph.GraphPane editorPane;
    private javax.swing.JMenuItem itemAbout;
    private javax.swing.JMenuItem itemClearGraph;
    private javax.swing.JMenuItem itemExit;
    private javax.swing.JCheckBoxMenuItem itemShowAlgorithmSteps;
    private javax.swing.JCheckBoxMenuItem itemShowAlgorithmTable;
    private javax.swing.JCheckBoxMenuItem itemShowCords;
    private javax.swing.JCheckBoxMenuItem itemShowEdgeLabels;
    private javax.swing.JCheckBoxMenuItem itemShowEdgeWeights;
    private javax.swing.JCheckBoxMenuItem itemShowEdges;
    private javax.swing.JCheckBoxMenuItem itemShowVertices;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel labelDestinationNode;
    private javax.swing.JLabel labelInitialNode;
    private javax.swing.JMenu menuGraph;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenu menuView;
    private javax.swing.JToggleButton vertexButton;
    // End of variables declaration//GEN-END:variables

    boolean isCorrectWorkMode(byte workMode) {
        return workMode == EDIT_MODE || workMode == EDGE_MODE || workMode == VERTEX_MODE;
    }

    public void resetTable() {
        if (tableWindow.isVisible()) {
            tableWindow.getContentPane().removeAll();
            if (algorithm != null) {
                JTable table = new JTable(algorithm.getRows(), algorithm.getColumns());
                JScrollPane scrollPane = new JScrollPane(table);
                table.setFillsViewportHeight(true);
                table.setEnabled(false);
                tableWindow.getContentPane().add(scrollPane);
            }
            tableWindow.revalidate();
        }
    }

    public void setWorkMode(byte workMode) {
        if (isCorrectWorkMode(workMode)) {
            this.workMode = workMode;
            if (tail != null) {
                tail.setHighlighted(false);
                tail = null;
            }
            if (workMode == VERTEX_MODE) {
                creatorVertex.setVisible(true);
            } else {
                creatorVertex.setVisible(false);
            }
        }
    }

    public void showError(String text) {
        JOptionPane.showMessageDialog(this, text, "Błąd", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        nextStep();
        if (algorithm.isFinished()) {
            t.stop();
        } else {
            t.start();
        }
    }

    public void nextStep() {
        if (!algorithm.isFinished()) {
            algorithmLabel.setText(algorithm.nextStep());
            int x = algorithm.getStepPoint().x + Vertex.DEF_RADIUS * 2;
            int y = algorithm.getStepPoint().y + Vertex.DEF_RADIUS * 2;
            algorithmLabel.setLocation(x, y);
            algorithmLabel.setVisible(itemShowAlgorithmSteps.getState());
            resetTable();
            editorPane.repaint();
        }
    }

    public void resetAlgorithm() {
        if (editorPane.getGraph().contains(initialNode)) {
            labelInitialNode.setText(initialNode.toString());
        } else {
            initialNode = null;
            labelInitialNode.setText("Nie wybrano");
        }
        if (editorPane.getGraph().contains(destinationNode)) {
            labelDestinationNode.setText(destinationNode.toString());
        } else {
            destinationNode = null;
            labelDestinationNode.setText("Nie wybrano");
        }
        algorithm.reset(editorPane.getGraph(), initialNode, destinationNode);
        algorithmLabel.setLocation(-100, -100);
        editorPane.repaint();
        resetTable();
    }

    public void createVertex(int x, int y) {
        Vertex t = new Vertex("v" + (vertexIndex++), x, y);
        //Przeciąganie wierzcholka w trybie edycji:
        t.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                if (workMode == EDIT_MODE) {
                    try {
                        Point p = editorPane.getMousePosition();
                        t.setLocation(p.x - Vertex.DEF_RADIUS, p.y - Vertex.DEF_RADIUS);
                        editorPane.updateEdges(t);
                    } catch (NullPointerException ex) {
                    }
                }
            }

            public void mouseMoved(MouseEvent e) {
            }
        });

        //Tworzenie krawedzi w trybie edycji krawedzi
        t.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && workMode == EDGE_MODE) {
                    if (tail == null) {
                        tail = t;
                        t.setHighlighted(true);
                    } else if (tail == t) {
                        t.setHighlighted(false);
                        tail = null;
                    } else {
                        createEdge(tail, t);
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        //Menu kontekstowe wierzchołka:
        JPopupMenu jp = new JPopupMenu("Edycja wierzchołka");
        JMenuItem jMI = new JMenuItem("Oznacz jako startowy");
        jMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!t.equals(destinationNode)) {
                    initialNode = t;
                    labelInitialNode.setText(t.toString());
                    algorithm.reset(editorPane.getGraph(), initialNode, destinationNode);
                } else {
                    showError("Wierzchołek " + t.toString() + " jest już wierzchołkiem docelowym");
                }
            }
        });
        jp.add(jMI);
        jMI = new JMenuItem("Oznacz jako docelowy");
        jMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!t.equals(initialNode)) {
                    destinationNode = t;
                    labelDestinationNode.setText(t.toString());
                    algorithm.reset(editorPane.getGraph(), initialNode, destinationNode);
                } else {
                    showError("Wierzchołek " + t.toString() + " jest już wierzchołkiem początkowym");
                }

            }
        });
        jp.add(jMI);
        jp.add(new JPopupMenu.Separator());
        jMI = new JMenuItem("Edytuj");
        jMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vertexDialog.setVisible(true, t);
            }
        });
        jp.add(jMI);
        jMI = new JMenuItem("Usuń");
        jMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editorPane.removeVertex(t);
                if (tail == t) {
                    tail = null;
                }
            }
        });
        jp.add(jMI);
        t.setComponentPopupMenu(jp);
        //Dodanie wierzchołka do grafu
        editorPane.addVertex(t);
    }

    public void createEdge(Vertex tail, Vertex head) {
        Edge edge = new Edge(tail, head, "e" + edgeIndex);
        if (editorPane.addEdge(edge)) {
            edgeIndex++;
            edge.addMouseMotionListener(new MouseMotionListener() {
                //Przeciąganie etykiet krawędzi
                public void mouseDragged(MouseEvent e) {
                    if (workMode == EDIT_MODE) {
                        try {
                            Point p = editorPane.getMousePosition();
                            edge.setLocation(p.x - edge.getWidth() / 2, p.y - edge.getHeight() / 2);
                            editorPane.repaint();
                        } catch (NullPointerException ex) {
                        }
                    }
                }

                public void mouseMoved(MouseEvent e) {
                }
            });
            //Menu kontekstowe krawędzi:
            JPopupMenu jp = new JPopupMenu("Edycja krawędzi");
            JMenuItem jMI = new JMenuItem("Edytuj");
            jMI.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    edgeDialog.setVisible(true, edge);
                }
            });
            jp.add(jMI);
            jMI = new JMenuItem("Usuń");
            jMI.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    editorPane.removeEdge(edge);
                }
            });
            jp.add(jMI);
            edge.setComponentPopupMenu(jp);
            tail.setHighlighted(false);
            this.tail = null;
        }
    }
    private static final String helpText = "Tworzenie wierzchołków:\nW \"Trybie dodawania"
            + " wierzchołków\" (druga ikona na pasku zadań) należy kliknąć w obszarze"
            + " roboczym na podążający za kursorem przycisk\n\nTworzenie krawędzi:\nW"
            + " \"Trybie dodawania krawędzi\" (trzecia ikona na pasku zadań) należy"
            + " zaznaczyć LPM wierzchołek początkowy krawędzi, a następnie wierzchołek"
            + " z którym ma być połączony (wielokrotne krawędzie między tymi samymi"
            + " wierzchołkami lub krawędzie łączące wierzchołek z nim samym są "
            + "niedozwolone)\n\nZaznaczanie i odznaczanie wierzchołków startowych"
            + " i docelowych:\nAby zaznaczyć wierzchołek należy kliknąć na niego PPM"
            + " i wybrać odpowiednią opcję z menu kontekstowego. Aby go odnazczyć "
            + "należy nacisnąć przycisk \"Wyczyść\" z panelu po prawej";
}
