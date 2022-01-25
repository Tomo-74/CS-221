import java.awt.Color;
import java.awt.Graphics;

/**
 * A thing that can be painted on a given Graphics object.
 * 
 * @author mvail
 */
public abstract class PaintableThing {
	// instance variables common to all paintable objects
	protected int anchorX;
	protected int anchorY;
	protected int width;
	protected int height;
	protected Color primaryColor;
	
	public PaintableThing(int anchorX, int anchorY, int width, int height, Color primaryColor) {
		this.anchorX = anchorX;
		this.anchorY = anchorY;
		this.width = width;
		this.height = height;
		this.primaryColor = primaryColor;
	}
	
	/**
	 * Draws this thing on the given Graphics object.
	 * @param g Graphics context on which to draw
	 */
	public abstract void draw(Graphics g);

	/**
	 * Override default toString() to show name of shape and attributes.
	 */
	public String toString() {
		String str = String.format("x: %d, y: %d, width: %d, height: %d, primaryColor: %s",
				anchorX, anchorY, width, height, primaryColor.toString());
		return str;
	}
}