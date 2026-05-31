package simulation;

import graph.*;
import algorithm.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class GraphVisualizer extends JPanel {

    Graph g;

    int selectedNode = -1;

    int accidentU = -1;
    int accidentV = -1;

    List<Integer> shortestPath =
            new ArrayList<>();

    List<Integer> alternatePath =
            new ArrayList<>();

    int[][] pos = {

            {100, 150},
            {220, 130},
            {360, 150},
            {500, 140},
            {650, 190},

            {100, 320},
            {240, 290},
            {380, 320},
            {520, 300},
            {670, 350},

            {180, 470},
            {340, 490},
            {520, 470},
            {700, 490},

            {450, 210},
            {300, 220}
    };

    public GraphVisualizer(Graph g) {

        this.g = g;

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                int clicked =
                        getClickedNode(
                                e.getX(),
                                e.getY()
                        );

                if (clicked == -1) return;

                // FIRST NODE
                if (selectedNode == -1) {

                    selectedNode = clicked;
                }

                // SECOND NODE
                else {

                    accidentU = selectedNode;
                    accidentV = clicked;

                    // BLOCK ROAD
                    g.blockEdge(
                            accidentU,
                            accidentV
                    );

                    // RECALCULATE ROUTES
                    calculateRoutes();

                    selectedNode = -1;

                    repaint();
                }
            }
        });
    }

    void calculateRoutes() {

        PathResult result =
                Dijkstra.shortestPath(
                        g,
                        accidentU
                );

        shortestPath =
                Dijkstra.buildPath(
                        result.parent,
                        accidentV
                );

        // SECOND ROUTE
        alternatePath =
                new ArrayList<>();

        if (shortestPath.size() > 2) {

            int blockU = shortestPath.get(0);
            int blockV = shortestPath.get(1);

            boolean oldState = false;

            for (Edge e : g.adj.get(blockU)) {

                if (e.to == blockV) {

                    oldState = e.blocked;

                    e.blocked = true;
                }
            }

            for (Edge e : g.adj.get(blockV)) {

                if (e.to == blockU) {

                    e.blocked = true;
                }
            }

            // RECALCULATE
            PathResult alt =
                    Dijkstra.shortestPath(
                            g,
                            accidentU
                    );

            alternatePath =
                    Dijkstra.buildPath(
                            alt.parent,
                            accidentV
                    );

            // RESTORE
            for (Edge e : g.adj.get(blockU)) {

                if (e.to == blockV) {

                    e.blocked = oldState;
                }
            }

            for (Edge e : g.adj.get(blockV)) {

                if (e.to == blockU) {

                    e.blocked = oldState;
                }
            }
        }
    }

    int getClickedNode(int mx, int my) {

        for (int i = 0; i < pos.length; i++) {

            int x = pos[i][0];
            int y = pos[i][1];

            double dist =
                    Math.sqrt(
                            (mx - x) * (mx - x)
                                    +
                                    (my - y) * (my - y)
                    );

            if (dist <= 25) {

                return i;
            }
        }

        return -1;
    }

    @Override
    protected void paintComponent(Graphics gr) {

        super.paintComponent(gr);

        Graphics2D g2 =
                (Graphics2D) gr;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        setBackground(
                new Color(245, 245, 245)
        );

        // ======================
        // NORMAL EDGES
        // ======================

        for (int u = 0; u < g.V; u++) {

            int x1 = pos[u][0];
            int y1 = pos[u][1];

            for (Edge e : g.adj.get(u)) {

                int v = e.to;

                if (u < v) {

                    int x2 = pos[v][0];
                    int y2 = pos[v][1];

                    g2.setColor(Color.BLACK);

                    g2.setStroke(
                            new BasicStroke(2)
                    );

                    g2.drawLine(
                            x1, y1,
                            x2, y2
                    );

                    int midX = (x1 + x2) / 2;
                    int midY = (y1 + y2) / 2;

                    g2.drawString(
                            String.valueOf(e.weight),
                            midX,
                            midY
                    );
                }
            }
        }

        // ======================
        // BLOCKED EDGE
        // ======================

        for (int u = 0; u < g.V; u++) {

            int x1 = pos[u][0];
            int y1 = pos[u][1];

            for (Edge e : g.adj.get(u)) {

                int v = e.to;

                if (u < v && e.blocked) {

                    int x2 = pos[v][0];
                    int y2 = pos[v][1];

                    g2.setColor(Color.RED);

                    g2.setStroke(
                            new BasicStroke(7)
                    );

                    g2.drawLine(
                            x1, y1,
                            x2, y2
                    );
                }
            }
        }

        // ======================
        // SHORTEST PATH
        // ======================

        g2.setColor(
                new Color(0, 180, 0)
        );

        g2.setStroke(
                new BasicStroke(6)
        );

        for (int i = 0;
             i < shortestPath.size() - 1;
             i++) {

            int u = shortestPath.get(i);
            int v = shortestPath.get(i + 1);

            g2.drawLine(
                    pos[u][0],
                    pos[u][1],
                    pos[v][0],
                    pos[v][1]
            );
        }

        // ======================
        // ALTERNATE PATH
        // ======================

        g2.setColor(
                new Color(50, 220, 50)
        );

        g2.setStroke(
                new BasicStroke(5)
        );

        for (int i = 0;
             i < alternatePath.size() - 1;
             i++) {

            int u = alternatePath.get(i);
            int v = alternatePath.get(i + 1);

            g2.drawLine(
                    pos[u][0],
                    pos[u][1],
                    pos[v][0],
                    pos[v][1]
            );
        }

        // ======================
        // NODES
        // ======================

        for (int i = 0; i < g.V; i++) {

            int x = pos[i][0];
            int y = pos[i][1];

            g2.setColor(
                    new Color(0, 170, 255)
            );

            g2.fillOval(
                    x - 22,
                    y - 22,
                    44,
                    44
            );

            g2.setColor(Color.BLACK);

            g2.drawOval(
                    x - 22,
                    y - 22,
                    44,
                    44
            );

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            15
                    )
            );

            g2.drawString(
                    String.valueOf(i),
                    x - 5,
                    y + 5
            );
        }

        // TITLE

        g2.setColor(Color.BLACK);

        g2.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        g2.drawString(
                "Interactive Traffic Accident Analyzer",
                160,
                40
        );

        g2.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        g2.drawString(
                "Click two connected nodes to block road",
                220,
                70
        );
    }

    public static void showGraph(Graph g) {

        JFrame frame =
                new JFrame(
                        "Traffic Analyzer"
                );

        GraphVisualizer panel =
                new GraphVisualizer(g);

        frame.add(panel);

        frame.setSize(900, 700);

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        frame.setVisible(true);
    }
}