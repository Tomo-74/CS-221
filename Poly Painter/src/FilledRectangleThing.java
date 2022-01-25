import java.awt.Color;
import java.awt.Graphics;

public class FilledRectangleThing extends RectangleThing {
	private Color fillColor;

	public FilledRectangleThing(int anchorX, int anchorY, int width, int height, Color primaryColor, Color fillColor) {
		super(anchorX, anchorY, width, height, primaryColor);
		this.fillColor = fillColor;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(fillColor);
		g.fillRect(anchorX, anchorY, width, height);
		super.draw(g);
	}
	
	@Override
	public String toString() {
		return "Filled" + super.toString() + ", fillColor: " + fillColor.toString();
	}
}
