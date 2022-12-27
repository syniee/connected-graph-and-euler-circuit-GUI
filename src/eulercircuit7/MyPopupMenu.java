package eulercircuit7;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JPopupMenu;

public class MyPopupMenu extends JPopupMenu {
    // chuyển đổi trạng thái của một Java Object thành định dạng nào đó để lưu ở đâu đó và được sử dụng ở tiến trình khác
    private static final long serialVersionUID = 1L;
    private Point point;

    public Point getPoint() {
	return point;
    }

    public void setPoint(Point point) {
	this.point = point;
    }

    public MyPopupMenu() {
	super();
    }

    public void show(Component invoker, int x, int y) {
        point = new Point(x, y);
        super.show(invoker, x, y);
    }
}
