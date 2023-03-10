package eulercircuit7;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.io.Serializable;

class MyLine implements Serializable {
    // chuyển đổi trạng thái của một Java Object thành định dạng nào đó để lưu ở đâu đó và được sử dụng ở tiến trình khác
    private static final long serialVersionUID = 1L;
    private Line2D.Double l = new Line2D.Double();
    private int indexPointA, indexPointB;

    final int barb = 10;
    final int r = 15;
    final double phi = Math.PI / 6;

    public MyLine(Line2D.Double l, int indexPointA, int indexPointB) {
	this.indexPointA = indexPointA;
	this.indexPointB = indexPointB;
	this.l = l;
    }

    public void drawLine(Graphics2D g, Point p1, Point p2, Color colorLine, int size) {
	g.setColor(colorLine);
	g.setStroke(new BasicStroke(size));
	double theta = Math.atan2(p2.y - p1.y, p2.x - p1.x);
	g.draw(l);
		
	double x = p2.x - r * Math.cos(theta);
	double y = p2.y - r * Math.sin(theta);
    }

    public boolean containerPoint(Point p) {
	Polygon poly = createPolygon(l);
	for(int i = 0; i < poly.npoints; i++){
            double temp = (p.x - poly.xpoints[i])
                            * (poly.ypoints[(i + 1) % poly.npoints] - poly.ypoints[i])
                            - (p.y - poly.ypoints[i])
                            * (poly.xpoints[(i + 1) % poly.npoints] - poly.xpoints[i]);
            if(temp < 0)
		return false;
	}
	return true;
    }

    private Polygon createPolygon(Line2D line) {
	int barb = 5;
	double phi = Math.PI / 2;
	double theta = Math.atan2(line.getY2() - line.getY1(), line.getX2()
                	- line.getX1());
	int x[] = new int[4];
	int y[] = new int[4];
	x[0] = (int) (line.getX1() - barb * Math.cos(theta + phi));
	y[0] = (int) (line.getY1() - barb * Math.sin(theta + phi));
	x[1] = (int) (line.getX1() - barb * Math.cos(theta - phi));
	y[1] = (int) (line.getY1() - barb * Math.sin(theta - phi));

	x[2] = (int) (line.getX2() - barb * Math.cos(theta - phi));
	y[2] = (int) (line.getY2() - barb * Math.sin(theta - phi));
	x[3] = (int) (line.getX2() - barb * Math.cos(theta + phi));
	y[3] = (int) (line.getY2() - barb * Math.sin(theta + phi));
	Polygon poly = new Polygon(x, y, 4);
	return poly;
    }

    public Line2D.Double getL() {
	return l;
    }

    public void setL(Line2D.Double l) {
	this.l = l;
    }

    public int getIndexPointA() {
    	return indexPointA;
    }

    public void setIndexPointA(int indexPointA) {
	this.indexPointA = indexPointA;
    }

    public int getIndexPointB() {
	return indexPointB;
    }

    public void setIndexPointB(int indexPointB) {
    	this.indexPointB = indexPointB;
    }

}