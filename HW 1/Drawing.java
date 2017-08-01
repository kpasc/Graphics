//******************************************************************************
// Last modified: Fri Jan 29 17:30:31 2016 by Chris Weaver
//******************************************************************************
//
// Skeleton for experimenting with Java2D.
//
//******************************************************************************

//package graphics.foo;

// Only these imports are allowed in homework #1!
//import java.lang.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

//******************************************************************************

/**
 * The <CODE>Drawing</CODE> class. It draws stuff. Something fishy's going on.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class Drawing
{
	private static final Rectangle	BOUNDS = new Rectangle(50, 50, 400, 420);

	public static void	main(String[] argv)
	{
		JFrame	frame = new JFrame("Drawing");
		JPanel	panel = new Panel();

		frame.getContentPane().add(panel);
		frame.setVisible(true);
		frame.setBounds(BOUNDS);
		frame.validate();

		panel.revalidate();
		panel.repaint();
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
	}

	/**
	 *
	 * @author kobypascual
	 *
	 */
	private static final class Panel extends JPanel
	{
		// For homework 1, only change what's in here. (You can add methods to
		// call from here if you want.) Document your code thoroughly! Graphics
		// code is usually easy to understand syntactically but hard to imagine
		// what will happen at each point when it runs.
		@Override
		public void		paintComponent(Graphics graphics)
		{
			Graphics2D g = (Graphics2D) graphics;
			Color greenOval = new Color(25,158,153);
			// Original stroke
			Stroke defaultStroke = g.getStroke();
			AffineTransform defaultTransform = g.getTransform();
			AffineTransform myTransform = new AffineTransform();

			// Create the bottom Rect with gray (0,350)
			g.setColor(Color.GRAY);
			g.fillRect(0,338,400,60);

			// Create rectangle/arrow pointers
				// create yellow rectangles
			g.setColor(Color.yellow);
					// rect 1
			g.fillRect(65, 360, 31, 12);
					// rect 2
			g.fillRect(185, 360, 31, 12);
					// rect 3
			g.fillRect(305, 360, 31, 12);
				// add yellow arrows on each end of rectangles
				// note all y coordinates will be unchanged.
			int[] arrowY = {365,347,385};
					// left,1
			int[] leftX1 = {45,65,65};
			g.fillPolygon(leftX1,arrowY,3);
					// right,1
			int[] rightX1 = {116,96,96};
			g.fillPolygon(rightX1,arrowY,3);
					// left,2
			int[] leftX2 = {165,185,185};
			g.fillPolygon(leftX2, arrowY, 3);
					// right,2
			int[] rightX2 = {236, 216, 216};
			g.fillPolygon(rightX2, arrowY, 3);
					// left,3
			int[] leftX3 = {285,305,305};
			g.fillPolygon(leftX3, arrowY, 3);
					// right,3
			int[] rightX3 = {356,336,336};
			g.fillPolygon(rightX3, arrowY, 3);
				// Create the black outlines for the yellow/arrow rectangles
			g.setColor(Color.black);
					// rect 1
			g.drawRect(65, 360, 31, 12);
					// rect 2
			g.drawRect(185, 360, 31, 12);
					// rect 3
			g.drawRect(305, 360, 31, 12);
					// 1
			g.drawPolygon(leftX1, arrowY, 3);
			g.drawPolygon(rightX1, arrowY, 3);
					// 2
			g.drawPolygon(leftX2, arrowY,3);
			g.drawPolygon(rightX2, arrowY,3);
					// 3
			g.drawPolygon(leftX3, arrowY, 3);
			g.drawPolygon(rightX3, arrowY, 3);
				// I cheat here by creating a yellow line
				// To erase the outlines that merge the arrowheads and rectangles
			g.setColor(Color.yellow);
					// cover 1
			g.drawLine(65, 361, 65, 371);
			g.drawLine(96, 361, 96, 371);
					// cover 2
			g.drawLine(185, 361, 185, 371);
			g.drawLine(216, 361, 216, 371);
					// cover 3
			g.drawLine(305, 361, 305, 371);
			g.drawLine(336, 361, 336, 371);

			// Create Green-to-pink gradient fill rectangle (ground)
			Color firstGround = new Color(180,150,50);
			Color secondGround = new Color(190,100,100);
			g.setPaint(new GradientPaint(0,30,firstGround,65,25,secondGround,true));
			g.fillRect(0,210,400,128);

			// Create the sky gradient fill rectangle
			Color firstSky = new Color(143,204,245);
			Color secondSky = new Color(215,251,255);
			g.setPaint(new GradientPaint(200,0,firstSky,200,150,secondSky,false));
			g.fillRect(0,0,400,210);

			// Create the tilted green rectangles
			Color greenRect = new Color(34,135,4);
			g.setColor(greenRect);
			g.setStroke(new BasicStroke(3));
			int x1 = 400;
			int x2 = 415;
			int y1 = 337;
			int y2 = 300;
			for(int i = 0; i< 105; i++)
			{
				// Dry grass as a line
				g.drawLine(x1,y1,x2,y2);
				x1 = x1-4;
				x2 = x2-4;
			}
			g.setStroke(defaultStroke);
			// Create the tree
				// Create the brown rectangle (Trunk)
			g.setColor(new Color(119,85,5));
			g.fillRect(40, 180, 15, 140);
				// Outline the tree trunk
			g.setColor(Color.black);
			g.setStroke(new BasicStroke(1));
			g.drawRect(40, 180, 15, 140);
			g.setStroke(defaultStroke);
				// Create the green triangle (tree branches)
			int[] treeX = {15,47,80};
			int[] treeY = {235,110,235};
			g.setColor(new Color(34,135,4,215));
			g.fillPolygon(treeX, treeY, 3);
				// Create outline green triangle
			g.setStroke(new BasicStroke(4));
			g.setColor(new Color(35,88,41));
			g.drawPolygon(treeX,treeY,3);
			g.setStroke(defaultStroke);

			// Create Ball & shadow
				// ball
			g.setPaint(new GradientPaint(165,310,new Color(230,61,42),255,240,Color.white));
			g.fillOval(140, 240, 42, 42);
				// shadow
			g.setColor(new Color(0,0,0,90));
			g.fillOval(110, 277, 41, 20);

			// Create Cat
			Color cat = new Color(185,233,235);
			g.setColor(cat);
				// Create back ear
			int[] backEarX = {217,222,227};
			int[] backEarY = {204,193,204};
			g.fillPolygon(backEarX,backEarY,3);
				// outline back ear
			g.setColor(Color.black);
			g.drawPolygon(backEarX,backEarY,3);
				// Create head
			g.setColor(cat);
			g.setColor(new Color(185,233,235));
			g.fillOval(185,200,50,32);
				// outline head
			g.setColor(Color.black);
			g.drawOval(185,200,50,32);
				// Create front ear
			g.setColor(cat);
			int[] frontEarX = {210,215,220};
			int[] frontEarY = {205,193,205};
			g.fillPolygon(frontEarX,frontEarY,3);
				// outline front ear
			g.setColor(Color.BLACK);
			g.drawPolygon(frontEarX, frontEarY, 3);
				// Create outer eye
			g.setColor(Color.white);
			g.fillOval(197, 210, 9, 7);
				// outline outer eye
			g.setColor(Color.black);
			g.drawOval(197, 210, 9,7);
				// create inner eye
			g.setColor(greenOval);
			g.fillOval(198, 212, 4, 4);
				// outline inner eye
			g.setColor(Color.black);
			g.drawOval(198,212,4,4);
				// Draw legs as lines
			g.setStroke(new BasicStroke(6));
			g.drawLine(223,255,195,270); // front leg
			g.drawLine(235,262,235,284); // second leg
			g.drawLine(285,262,295,284); // third leg
			g.drawLine(292,255,315,285); // back leg
			g.setStroke(defaultStroke);
				// draw feet as circles
			g.fillOval(188,262,15,15);
			g.fillOval(227,283,15,15);
			g.fillOval(290, 283, 15, 15);
			g.fillOval(311, 283, 15, 15);

			g.setStroke(defaultStroke);
				// Create body
			g.setColor(cat);
			g.fillOval(215,215,90,50);
				// outline body
			g.setColor(Color.black);
			g.drawOval(215,215,90,50);
				// make whiskers
			//g.drawArc(x, y, width, height, startAngle, arcAngle);
			g.drawArc(168,222,40,25,80,80);
			g.drawArc(176,222,40,25,80,80);
			g.drawArc(184,222,40,25,80,80);

			// Create weird flowers
				// Create yellow stems
			g.setColor(new Color(253,192,51));
			g.fillRect(330,200,5,40);	// left stem
			g.fillRect(348, 185, 5, 40);
			g.fillRect(360, 190, 5, 40); // middle right stem
			g.fillRect(370,200,5,50);	// right stem
				// Create purple squares and rotate them
			myTransform.rotate(Math.toRadians(45),324,190);
			g.setColor(new Color(165,156,240));
			g.setTransform(myTransform);
			g.fillRect(326,180,18,18); // left diamond
			g.fillRect(331,160,18,18); // left middle diamond
			g.setColor(Color.black);
			g.setStroke(new BasicStroke(1.5f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER,10,new float[]{2,2},0.0f));
			g.drawRect(331,160,18,18);
			g.setColor(new Color(165,156,240));
			g.setStroke(defaultStroke);
			g.fillRect(343,155,18,18); // right middle diamond
			g.fillRect(364,160,18,18); // right diamond
				// Create black dashed outline
			g.setColor(Color.black);
			g.setStroke(new BasicStroke(1.5f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER,10,new float[]{2,2},0.0f));
			g.drawRect(326,180,18,18);
			g.drawRect(343,155,18,18);
			g.drawRect(364,160,18,18);
			g.setStroke(defaultStroke);
			g.setTransform(defaultTransform);

			// Create text
				// Create so
			Font font = new Font("times new roman", Font.PLAIN,24);
			g.setFont(font);
			g.setColor(Color.black);
			g.drawString("so",85,128);

				// Create line underneath
			g.setStroke(new BasicStroke((float) 1.5));
			g.drawLine(85,130,105,130);
			g.setStroke(defaultStroke); // return stroke to default
				// Create much & rectangle around it
			g.drawRect(120, 125, 64, 33);
			g.setColor(Color.RED);
			g.drawString("much", 125, 150);

			// Create color! & oval/fill around it
				// Outline
			g.setStroke(new BasicStroke(4));
			g.setColor(Color.black);
			g.drawArc(190, 145, 40, 30, 90, 180);
			g.drawArc(225, 145, 40, 30, 90, -180);
			g.drawRect(210, 145, 35, 30);
				// Create oval center
			g.setStroke(defaultStroke);
			g.setColor(greenOval);
			g.fillArc(190,145,40,30,90, 180);
			g.fillArc(225,145,40,30,90,-180);
			g.fillRect(210,145,35,30);
				// Add color! Text
			g.setFont(new Font("times new roman", Font.ITALIC,24));
			g.setColor(Color.white);
			g.drawString("color!",198,167);
			// Create clouds
				// Inner cloud
			Color transparentCloud = new Color(190,190,190,135);
			g.setColor(transparentCloud);
			g.fillOval(80,50,80,40); // First cloud
			g.fillOval(110,40,80,40); // Second cloud
			g.fillOval(120,65,80,40); // Third cloud
			g.fillOval(145,40,80,40); // Fourth cloud
			g.fillOval(70,60,80,40); // Fifth cloud
			g.fillOval(60,35,80,40); // Sixth cloud

				// Outline of cloud
			Color outlineCloud = new Color(190,190,190);
			g.setColor(outlineCloud);
			g.drawOval(80,50,80,40); // First cloud outline
			g.drawOval(110,40,80,40); // Second cloud outline
			g.drawOval(120,65,80,40); // Third cloud outline
			g.drawOval(145,40,80,40); // Fourth cloud outline
			g.drawOval(70,60,80,40); // Fifth cloud outline
			g.drawOval(60,35,80,40); // Sixth cloud outline

				// Create bonus image
				// Create stem
			Color brown = new Color(159,109,7);
			Color tan = new Color(210,170,89);
			g.setPaint(new GradientPaint(345,257,brown,380,257,tan,false));
			g.fillRect(345, 222, 5, 35);

			// Create radial gradient paint
			Point2D center = new Point2D.Float(352,222);
			float radius = 10;
			float[] dist = {0.0f,.2f,1f};
			Color[] colors = {Color.blue, Color.yellow, Color.green};
			g.setPaint(new RadialGradientPaint(center,radius,dist,colors));
			g.fillOval(347, 215, 18, 18);


			// Create sun (image)
			Image sun = fullyLoadImage("sunwithglasses.png");
			g.drawImage(sun,285,20,null);

		}

		// Use this to load images (and make sure they're done loading). The
		// filename must be relative to the directory containing this .java file.
		// It's easiest to just put them in the same directory as this file.
		private Image	fullyLoadImage(String filename)
		{
			Image	image = null;

			// We're going to do IO and thread stuff, so catch some exceptions
			try
			{
				// Load an image file into an image object
				image = Toolkit.getDefaultToolkit().createImage(filename);

				// This class watches for media loads & calculations to finish
				MediaTracker	tracker = new MediaTracker(this);

				tracker.addImage(image, 0);		// Track loading of the image
				tracker.waitForAll();			// Block until it's fully loaded
			}
			catch (Exception ex)
			{
				System.err.println("that wasn't supposed to happen");
				ex.printStackTrace();
				System.exit(1);
			}

			return image;
		}
	}

	// This
	private void greenRect()
	{

	}
}

//******************************************************************************
