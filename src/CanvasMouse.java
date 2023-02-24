
import model.Point;
import model.PolyLine;
import model.RegularPolygon;
import rasterOper.Raster;
import rasterOper.RasterBufferedImage;
import renderOperation.RendererLine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * trida pro kresleni na platno: zobrazeni pixelu, ovladani mysi
 * 
 * @author PGRF FIM UHK
 * @version 2017
 */
public class CanvasMouse {

	private JPanel panel;
	private BufferedImage img;

	private RegularPolygon rp = new RegularPolygon();
	private RendererLine renderer;
	private PolyLine polyLine = new PolyLine();
	private List<Point> points= new ArrayList<>();	// points for lines


	public CanvasMouse(int width, int height) {
		JFrame frame = new JFrame();

		frame.setLayout(new BorderLayout());
		frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Raster raster = new RasterBufferedImage(img);
		renderer = new RendererLine(raster);

		panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				present(g);
			}
		};
		panel.setPreferredSize(new Dimension(width, height));

		frame.add(panel);
		JPanel pnlNorth = new JPanel();

		JRadioButton rbLine = new JRadioButton("Interactive line", true);
		JRadioButton rbRegPol  = new JRadioButton("Regular Polygon");
		JRadioButton rbIrregPol = new JRadioButton("Irregular Polygon");
		ButtonGroup rbGroup = new ButtonGroup();
		rbGroup.add(rbLine);
		rbGroup.add(rbRegPol);
		rbGroup.add(rbIrregPol);
		pnlNorth.add(rbLine);
		pnlNorth.add(rbRegPol);
		pnlNorth.add(rbIrregPol);
		frame.add(pnlNorth,BorderLayout.NORTH);
		frame.pack();
		frame.setVisible(true);

		rbLine.addActionListener(e -> clearSettings());
		rbRegPol.addActionListener(e -> clearSettings());
		rbIrregPol.addActionListener(e -> clearSettings());

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(rbLine.isSelected()){
					points.add(new Point(e.getX(),e.getY()));
				}
				if(rbRegPol.isSelected()){
					if(rp.getListSize() > 2){
						rp = new RegularPolygon();
					}
					rp.addPoint(new Point(e.getX(),e.getY()));
				}
				if(rbIrregPol.isSelected()){
					if(polyLine.getListSize() == 0){
						polyLine.addPoint(e.getX(),e.getY());
						System.out.println(new Point(e.getX(),e.getY()));
					}
				}
			}
		});
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(rbLine.isSelected()){
					points.add(new Point(e.getX(),e.getY()));
					drawSimpleLinesOnCanvas(e);	//dots
				}
				if(rbIrregPol.isSelected()){
					polyLine.addPoint(e.getX(),e.getY());
					System.out.println(new Point(e.getX(),e.getY()));
					drawIrregPolOnCanvas();
				}
			}
		});
		panel.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if(rbLine.isSelected()){
					drawSimpleLinesOnCanvas(e);
				}
				if(rbIrregPol.isSelected()){
					drawIrregPolOnCanvas();
					renderer.drawLine(polyLine.getPoint(0).getX(),polyLine.getPoint(0).getY(),
							e.getX(),e.getY(),0xff0000);		// flexible parts of polyline
					if(polyLine.getListSize() > 1){
						renderer.drawLine(polyLine.getPoint(polyLine.getListSize()-1).getX(),
								polyLine.getPoint(polyLine.getListSize()-1).getY(),
								e.getX(),e.getY(),0xff0000);	// flexible parts of polyline
					}
					panel.repaint();
				}
				if(rbRegPol.isSelected()){
					drawRegPolOnCanvas(e);
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				if(rbRegPol.isSelected()){
					if(rp.getListSize() > 0 && rp.getListSize() != 3){	//optimization
						drawRegPolOnCanvas(e);
					}
				}
			}
		});
	}

	private void drawSimpleLinesOnCanvas(MouseEvent e) {
		clear();
		for(int i=0;i<points.size()-1;i+=2){
			renderer.drawLine(points.get(i).getX(),points.get(i).getY(),points.get(i+1).getX(),points.get(i+1).getY());
		}
		renderer.drawLine(points.get(points.size()-1).getX(),points.get(points.size()-1).getY(),e.getX(),e.getY());	//flexible line
		panel.repaint();
	}

	private void drawRegPolOnCanvas(MouseEvent e) {
		clear();
		if(rp.getListSize() == 1){
			int xFromCenter = e.getX() - rp.getPoint(0).getX();	// with sign
			int yFromCenter = e.getY() - rp.getPoint(0).getY();	// with sign
			rp.setR((float)Math.sqrt(xFromCenter*xFromCenter + yFromCenter*yFromCenter));
			rp.setAlpha(Math.atan2(e.getY()- rp.getPoint(0).getY(),e.getX()- rp.getPoint(0).getX()));

			//flexible r
			renderer.drawLine(rp.getPoint(0).getX(), rp.getPoint(0).getY(),e.getX(),e.getY(),0xff0000);
		}
		if(rp.getListSize() == 2){
			int distance = Math.abs(rp.getPoint(1).getX()-e.getX());
			rp.setN(distance/10);
		}
		if(rp.getListSize() > 0){
			rp.draw(renderer);
		}
		panel.repaint();
	}

	private void clearSettings() {
		clear();
		panel.repaint();
		points.clear();
		rp = new RegularPolygon();
		polyLine = new PolyLine();
	}

	private void drawIrregPolOnCanvas() {
		clear();
		if(polyLine.getListSize() > 0){
			polyLine.draw(renderer);
		}
		panel.repaint();
	}

	public void clear() {
		Graphics gr = img.getGraphics();
		gr.setColor(new Color(0x2f2f2f));
		gr.fillRect(0, 0, img.getWidth(), img.getHeight());
		img.getGraphics().drawString("Use mouse buttons, change mode with radio buttons",
				5, img.getHeight() - 5);
	}

	public void present(Graphics graphics) {
		graphics.drawImage(img, 0, 0, null);
	}

	public void start() {
		clear();
		panel.repaint();
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new CanvasMouse(800, 600).start());
	}

}