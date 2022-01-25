import java.awt.Color;
import java.awt.Graphics;

public class RectangleThing extends PaintableThing {
	
	public RectangleThing(int anchorX, int anchorY, int width, int height, Color primaryColor) {
		super(anchorX, anchorY, width, height, primaryColor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(primaryColor);
		g.drawRect(anchorX, anchorY, width, height);
	}
	
	@Override
	public String toString() {
		return "RectangleThing --> " + super.toString();
	}
}
