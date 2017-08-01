//******************************************************************************
// Copyright (C) 2016 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Tue Feb  9 20:33:16 2016 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20160209 [weaver]:	Original file.
//
//******************************************************************************
// Notes:
//
//******************************************************************************

package edu.ou.cs.cg.homework;

//import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Random;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;

import com.jogamp.opengl.*;
import javax.media.opengl.GL2;
import java.lang.Math;



//******************************************************************************

/**
 * The <CODE>Homework02</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class Homework02
	implements GLEventListener
{
	//**********************************************************************
	// Public Class Members
	//**********************************************************************

	public static final GLU		GLU = new GLU();
	public static final GLUT	GLUT = new GLUT();
	public static final Random	RANDOM = new Random();
    
	//**********************************************************************
	// Private Members
	//**********************************************************************

	// State (internal) variables
	private int				k = 0;		// Just an animation counter

	private int				w;			// Canvas width
	private int				h;			// Canvas height
	private TextRenderer	renderer;

	//**********************************************************************
	// Main
	//**********************************************************************

	public static void main(String[] args)
	{
		GLProfile		profile = GLProfile.getDefault();
		GLCapabilities	capabilities = new GLCapabilities(profile);
		GLCanvas		canvas = new GLCanvas(capabilities);
		JFrame			frame = new JFrame("Koby's Quick Portrayal");
        
		frame.setBounds(50, 50, 543, 326);
		frame.getContentPane().add(canvas);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});

		canvas.addGLEventListener(new Homework02());

		FPSAnimator		animator = new FPSAnimator(canvas, 60);

		animator.start();
	}

	//**********************************************************************
	// Override Methods (GLEventListener)
	//**********************************************************************

	public void		init(GLAutoDrawable drawable)
	{
		w = drawable.getWidth();
		h = drawable.getHeight();

		renderer = new TextRenderer(new Font("Serif", Font.PLAIN, 18),
									true, true);
	}

	public void		dispose(GLAutoDrawable drawable)
	{
		renderer = null;
	}

	public void		display(GLAutoDrawable drawable)
	{
		update();
		render(drawable);
	}

	public void		reshape(GLAutoDrawable drawable, int x, int y, int w, int h)
	{
		this.w = w;
		this.h = h;
	}

	//**********************************************************************
	// Private Methods (Rendering)
	//**********************************************************************

	private void	update()
	{
		k++;									// Counters are useful, right?
	}

	private void	render(GLAutoDrawable drawable)
	{
		GL2		gl = drawable.getGL().getGL2();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT);		// Clear the buffer
		drawSomething(gl);						// Draw something
		drawSomeText(drawable);					// Draw some text
	}

	//**********************************************************************
	// Private Methods (Scene)
	//**********************************************************************

	// This page is helpful (scroll down to "Drawing Lines and Polygons"):
	// http://www.linuxfocus.org/English/January1998/article17.html
	private void	drawSomething(GL2 gl)
	{
        // Create landscape
            // Ground
                // Gradient filled color - green to black
        gl.glBegin(GL2.GL_POLYGON);
                // Green color
        gl.glColor3f(getcolorf(82), getcolorf(163), getcolorf(102));
        gl.glVertex2d(-0.999, -.649);
        gl.glVertex2d(1.0, -.649);
        
                // Dark gray almost black
        gl.glColor3f(getcolorf(40), getcolorf(40), getcolorf(40));
        gl.glVertex2d(1.0, -.15);
        gl.glVertex2d(-.999, -.15);
		gl.glEnd();
        
        // Create sky
        gl.glBegin(GL2.GL_POLYGON);
            // Sky - TODO
                // Gradient filled color - brown to black
                // Brown
        gl.glColor3f(getcolorf(204), getcolorf(175), getcolorf(110));
        gl.glVertex2d(-.999, -.15);
        gl.glVertex2d(1.0, -.15);
                // Blue
        gl.glColor3f(getcolorf(29), getcolorf(42), getcolorf(52));
        gl.glVertex2d(1.0, .2);
        gl.glVertex2d(-.999,.2);
        gl.glEnd();
        
        gl.glBegin(GL2.GL_POLYGON);
                // Blue
        gl.glColor3f(getcolorf(29), getcolorf(42), getcolorf(52));
        gl.glVertex2d(-.999, .2);
        gl.glVertex2d(1.0, .2);
                // Black
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glVertex2d(1.0  , 1.0);
        gl.glVertex2d(-.999, 1.0);
        gl.glEnd();
        
        // Create side walk
        // Gray Filled Concrete
        gl.glBegin(GL2.GL_POLYGON);
        gl.glColor3f(.5f, .5f, .5f);
        gl.glVertex2d(-.999, -0.999);
        gl.glVertex2d(-.999, -0.65);
        gl.glVertex2d(1.0, -0.65);
        gl.glVertex2d(1.0, -.999);
        gl.glEnd();
        // HopScotch
        hopScotch(gl);
        // Lines
            // Straight line
        gl.glBegin(gl.GL_LINES);
        gl.glColor3f(1f,1f,1f);
        gl.glVertex2d(-.999, -.65);
        gl.glVertex2d(1.0, -.65);
            // Diagonal Lines
        double x1 = -.9;
        double x2 = -.94;
        double y1 = -.65;
        double y2 = -1.0;
        for(int i = 0; i < 16; i++)
        {
            gl.glVertex2d(x1, y1);
            gl.glVertex2d(x2, y2);
            x1 = x1 + .12;
            x2 = x2 + .12;
        }
        gl.glEnd();
        
            // Mouse pointer
        double sx = .375;
        double sy = .6;
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2d(sx, sy);
        sy += .015;
        gl.glVertex2d(sx, sy);
        sx -= .01;
        gl.glVertex2d(sx, sy);
        sx += .012; sy += .03;
        gl.glVertex2d(sx, sy);
        sx += .012; sy -= .03;
        gl.glVertex2d(sx, sy);
        sx -= .01;
        gl.glVertex2d(sx, sy);
        sy -= .015;
        gl.glVertex2d(sx, sy);
        gl.glVertex2d(.375, .6);
        gl.glEnd();
            // outline mouse pointer
        sx = .375;
        sy = .6;
        gl.glColor3f(getcolorf(255), getcolorf(255), getcolorf(255));
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex2d(sx, sy);
        sy += .015;
        gl.glVertex2d(sx, sy);
        sx -= .01;
        gl.glVertex2d(sx, sy);
        sx += .012; sy += .03;
        gl.glVertex2d(sx, sy);
        sx += .012; sy -= .03;
        gl.glVertex2d(sx, sy);
        sx -= .01;
        gl.glVertex2d(sx, sy);
        sy -= .015;
        gl.glVertex2d(sx, sy);
        gl.glVertex2d(.375, .6);
        gl.glEnd();
        
            // Chimneys
        buildChimneys(gl);
            // Fence Posts
        buildFence(gl);
            // Homes
        buildHomes(gl);
            // Doors
        buildDoors(gl);
            // Windows
        buildWindows(gl);
            // Kite
        buildKite(gl);
            // build stars
        buildStars(gl);
            // Build gray moon
        buildMoon(gl);
        
        
	}
    private void buildMoon(GL2 gl)
    {
        gl.glColor3f(getcolorf(255), getcolorf(255), getcolorf(255));
        drawFilledCircle(gl, -.85f, .75f, .1f, 1, 361);
        gl.glColor3f(getcolorf(90), getcolorf(90), getcolorf(90));
        drawFilledCircle(gl, -.825f, .75f, .1f, 1, 361);
    }
    private void buildStars(GL2 gl)
    {
        // 2nd house star
        gl.glColor3f(getcolorf(247), getcolorf(244), getcolorf(40));
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glVertex2d(.18, 0);
        gl.glVertex2d(.17, -.05);
        gl.glVertex2d(.19, -.05);
        
        gl.glVertex2d(.17, -.05);
        gl.glVertex2d(.14, -.05);
        gl.glVertex2d(.17, -.08);
        
        gl.glVertex2d(.17, -.08);
        gl.glVertex2d(.15, -.11);
        gl.glVertex2d(.18, -.10);
        
        gl.glVertex2d(.18, -.10);
        gl.glVertex2d(.21, -.11);
        gl.glVertex2d(.19, -.08);
        
        gl.glVertex2d(.19, -.08);
        gl.glVertex2d(.22, -.05);
        gl.glVertex2d(.19, -.05);
        gl.glEnd();
            // fill in center with square
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2d(.17, -.05);
        gl.glVertex2d(.17, -.10);
        gl.glVertex2d(.199, -.10);
        gl.glVertex2d(.199, -.05);
        gl.glEnd();
            // outline star
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex2d(.18, 0);
        gl.glVertex2d(.17, -.05);
        gl.glVertex2d(.14, -.05);
        gl.glVertex2d(.17, -.08);
        gl.glVertex2d(.15, -.11);
        gl.glVertex2d(.18, -.10);
        gl.glVertex2d(.21, -.11);
        gl.glVertex2d(.2, -.08);
        gl.glVertex2d(.22, -.05);
        gl.glVertex2d(.19, -.05);
        gl.glEnd();
        
            // other stars in sky
        gl.glColor3f(getcolorf(247), getcolorf(244), getcolorf(40));
        gl.glBegin(GL.GL_TRIANGLES);
        double x = .4;
        double y = .9;
            // top
        gl.glVertex2d(x,y);
        gl.glVertex2d(x + .01, y + .08);
        gl.glVertex2d(x + .02, y);
            //bottom
        gl.glVertex2d(x,y);
        gl.glVertex2d(x + .01, y - .08);
        gl.glVertex2d(x + .02, y);
            // left
        gl.glVertex2d(x,y);
        gl.glVertex2d(x - .03, y - .02);
        gl.glVertex2d(x + .0015, y - .025);
            // right
        gl.glVertex2d(x,y);
        gl.glVertex2d(x + .06, y + .015);
        gl.glVertex2d(x, y - .03);
        
        
        x = .6;
        y = .7;
        // top
        gl.glVertex2d(x,y);
        gl.glVertex2d(x + .01, y + .08);
        gl.glVertex2d(x + .02, y);
        //bottom
        gl.glVertex2d(x,y);
        gl.glVertex2d(x + .01, y - .08);
        gl.glVertex2d(x + .02, y);
        // left
        gl.glVertex2d(x,y);
        gl.glVertex2d(x - .03, y - .02);
        gl.glVertex2d(x + .0015, y - .025);
        // right
        gl.glVertex2d(x,y);
        gl.glVertex2d(x + .06, y + .015);
        gl.glVertex2d(x, y - .03);
        
        x = .85;
        y = .8;
        // top
        gl.glVertex2d(x,y);
        gl.glVertex2d(x + .01, y + .08);
        gl.glVertex2d(x + .02, y);
        //bottom
        gl.glVertex2d(x,y);
        gl.glVertex2d(x + .01, y - .08);
        gl.glVertex2d(x + .02, y);
        // left
        gl.glVertex2d(x,y);
        gl.glVertex2d(x - .03, y - .02);
        gl.glVertex2d(x + .0015, y - .025);
        // right
        gl.glVertex2d(x,y);
        gl.glVertex2d(x + .06, y + .015);
        gl.glVertex2d(x, y - .03);
        
        x = .9;
        y = .5;
        // top
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glColor4d(getcolorf(247), getcolorf(244), getcolorf(40), 100f);
        gl.glVertex2d(x,y);
        gl.glVertex2d(x + .01, y + .08);
        gl.glVertex2d(x + .02, y);
        //bottom
        gl.glVertex2d(x,y);
        gl.glVertex2d(x + .01, y - .08);
        gl.glVertex2d(x + .02, y);
        // left
        gl.glVertex2d(x,y);
        gl.glVertex2d(x - .03, y - .02);
        gl.glVertex2d(x + .0015, y - .025);
        // right
        gl.glVertex2d(x,y);
        gl.glVertex2d(x + .06, y + .015);
        gl.glVertex2d(x, y - .03);
        
        x = .78;
        y = .23;
        // top
        gl.glVertex2d(x,y);
        gl.glVertex2d(x + .01, y + .08);
        gl.glVertex2d(x + .02, y);
        //bottom
        gl.glVertex2d(x,y);
        gl.glVertex2d(x + .01, y - .08);
        gl.glVertex2d(x + .02, y);
        // left
        gl.glVertex2d(x,y);
        gl.glVertex2d(x - .03, y - .02);
        gl.glVertex2d(x + .0015, y - .025);
        // right
        gl.glVertex2d(x,y);
        gl.glVertex2d(x + .06, y + .015);
        gl.glVertex2d(x, y - .03);
        gl.glEnd();
        
        
    }
    private void buildKite(GL2 gl)
    {
        // Only build the idiotic kite string
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glColor3f(getcolorf(240), getcolorf(240), getcolorf(240));
        double startx = .625;
        double starty = -.31;
        gl.glVertex2d(startx, starty);
        startx -= .08;
        starty += .06;
        gl.glVertex2d(startx, starty);
        startx -= .1;
        starty += .2;
        gl.glVertex2d(startx, starty);
        starty += .175;
        gl.glVertex2d(startx, starty);
        startx -= .05;
        starty += .05;
        gl.glVertex2d(startx, starty);
        starty += .07;
        gl.glVertex2d(startx, starty);
        startx += .05;
        starty += .0240;
        gl.glVertex2d(startx, starty);
        startx += .033;
        starty += .1;
        gl.glVertex2d(startx, starty);
        gl.glEnd();
        
        // Now build the blue dumb kite trangle fan
        // center vertext is:
        // x .478
        // y .369
        double myX = .478;
        double myY = .369;
        gl.glColor3f(getcolorf(0), getcolorf(127), getcolorf(212));
        drawFilledCircle(gl, (float) myX, (float) myY, .175f, -100f, 0f);
        drawFilledCircle(gl, (float) myX, (float) myY, .175f, 85f, 185f);
        
        // gray lines on kite
        gl.glColor3f(getcolorf(100), getcolorf(100), getcolorf(100));
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(myX,myY);
        gl.glVertex2d(.42, .52);
        
        gl.glVertex2d(myX, myY);
        gl.glVertex2d(.35, .5);
        
        gl.glVertex2d(myX, myY);
        gl.glVertex2d(.3, .4);
        
        gl.glVertex2d(myX,myY);
        gl.glVertex2d(.65, .35);
        
        gl.glVertex2d(myX, myY);
        gl.glVertex2d(.6, .24);
        
        gl.glVertex2d(myX, myY);
        gl.glVertex2d(.51, .19);
        gl.glEnd();

        

    }
    private void hopScotch(GL2 gl)
    {
        gl.glColor3f(getcolorf(232), getcolorf(231), getcolorf(186));
        // Build the hopscotch squares
        double xleft = .1;
        double xright = .15;
        double ytop = -.75;
        double ybottom = -.8;
            // First square
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
            // second Square
        xleft = .15;
        xright = .2;
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
            // third square
        xleft = .2;
        xright = .25;
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
            // fourth square -1st part
        gl.glBegin(GL2.GL_QUADS);
        xleft = .25;
        xright = .3;
        double yftop = -.725;
        double yfbottom = -.775;
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2d(xleft, yfbottom);
        gl.glVertex2d(xleft, yftop);
        gl.glVertex2d(xright, yftop);
        gl.glVertex2d(xright, yfbottom);
        gl.glEnd();
        yftop = -.775;
        yfbottom = -.825;
            // fourth square - second part
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2d(xleft, yfbottom);
        gl.glVertex2d(xleft, yftop);
        gl.glVertex2d(xright, yftop);
        gl.glVertex2d(xright, yfbottom);
        gl.glEnd();
            // fifth square
        xleft = .3;
        xright = .35;
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
            // sixth square
        gl.glBegin(GL2.GL_QUADS);
        xleft = .35;
        xright = .4;
        yftop = -.725;
        yfbottom = -.775;
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2d(xleft, yfbottom);
        gl.glVertex2d(xleft, yftop);
        gl.glVertex2d(xright, yftop);
        gl.glVertex2d(xright, yfbottom);
        gl.glEnd();
        yftop = -.775;
        yfbottom = -.825;
        // sixth square - second part
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2d(xleft, yfbottom);
        gl.glVertex2d(xleft, yftop);
        gl.glVertex2d(xright, yftop);
        gl.glVertex2d(xright, yfbottom);
        gl.glEnd();
            // seventh square
        xleft = .4;
        xright = .45;
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
        /////// outlines for hop scotch
        gl.glColor3f(getcolorf(255), getcolorf(255), getcolorf(255));
        // First square
        xleft = .1;
        xright = .15;
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
        // second Square
        xleft = .15;
        xright = .2;
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
        // third square
        xleft = .2;
        xright = .25;
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
        // fourth square -1st part
        gl.glBegin(GL2.GL_LINE_LOOP);
        xleft = .25;
        xright = .3;
        yftop = -.725;
        yfbottom = -.775;;
        gl.glVertex2d(xleft, yfbottom);
        gl.glVertex2d(xleft, yftop);
        gl.glVertex2d(xright, yftop);
        gl.glVertex2d(xright, yfbottom);
        gl.glEnd();
        yftop = -.775;
        yfbottom = -.825;
        // fourth square - second part
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2d(xleft, yfbottom);
        gl.glVertex2d(xleft, yftop);
        gl.glVertex2d(xright, yftop);
        gl.glVertex2d(xright, yfbottom);
        gl.glEnd();
        // fifth square
        xleft = .3;
        xright = .35;
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
        // sixth square -1st part
        gl.glBegin(GL2.GL_LINE_LOOP);
        xleft = .35;
        xright = .4;
        yftop = -.725;
        yfbottom = -.775;;
        gl.glVertex2d(xleft, yfbottom);
        gl.glVertex2d(xleft, yftop);
        gl.glVertex2d(xright, yftop);
        gl.glVertex2d(xright, yfbottom);
        gl.glEnd();
        yftop = -.775;
        yfbottom = -.825;
        // sixty square - second part
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2d(xleft, yfbottom);
        gl.glVertex2d(xleft, yftop);
        gl.glVertex2d(xright, yftop);
        gl.glVertex2d(xright, yfbottom);
        gl.glEnd();
        // seventh square
        xleft = .4;
        xright = .45;
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
    }
    private void buildChimneys(GL2 gl)
    {
        double xleft = -.65;
        double xright = -.619;
        double ybottom = .0305;
        // build 1st house smoke
        gl.glColor3f(getcolorf(125), getcolorf(125), getcolorf(125));
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2d(xleft, ybottom);
        ybottom += .05;
        gl.glVertex2d(xleft, ybottom);
        xleft += .025; ybottom += .05;
        gl.glVertex2d(xleft, ybottom);
        ybottom += .1;
        gl.glVertex2d(xleft, ybottom);
        xleft += .04; ybottom += .1;
        gl.glVertex2d(xleft, ybottom);
        
        gl.glEnd();
        
        xleft = -.665;
        xright = -.62;
        double ytop = 0.03;
        ybottom = -.15;
            // Build 1st house chimney
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(getcolorf(158), getcolorf(0), getcolorf(0));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
            // outline first house chimney
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
            // Build 2nd house chimney
        xleft = .09;
        xright = .14;
        ytop = .08;
        ybottom = -.15;
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(getcolorf(158), getcolorf(0), getcolorf(0));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
        // outline second house chimney
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
            // Build 3rd house chimney
//        xleft = .66;
//        xright = .92;
        xleft = .71;
        xright = .76;
        ytop = .03;
        ybottom = -.15;
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(getcolorf(158), getcolorf(0), getcolorf(0));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
        // outline third house chimney
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
    }
    private void buildDoors(GL2 gl)
    {
        gl.glColor3f(getcolorf(194), getcolorf(129), getcolorf(25));
        // 1st two doors are equal, third has a window on it
            // 1st door
        double xleft = -.8;
        double xright = -.75;
        double ytop = -.4;
        double ybottom = -.64;
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
            // Draw 1st door knob
        gl.glColor3f(getcolorf(255), getcolorf(255), getcolorf(255));
        drawFilledCircle(gl,-.79f,-.52f,.005f, 1,361);
            // outline first door
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glBegin(gl.GL_LINE_LOOP);
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
            // 2nd door
        xleft = .06;
        xright = .11;
        ytop = -.34;
        ybottom = -.58;
        gl.glColor3f(getcolorf(194), getcolorf(129), getcolorf(25));
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
            // 2nd door knob
        gl.glColor3f(getcolorf(255), getcolorf(255), getcolorf(255));
        drawFilledCircle(gl,.07f,-.46f,.005f,1,361);
            // outline first door
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glBegin(gl.GL_LINE_LOOP);
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
            // 3rd door
        xleft = .68;
        xright = .73;
        ytop = -.4;
        ybottom = -.64;
        gl.glColor3f(getcolorf(194), getcolorf(129), getcolorf(25));
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
            // 3rd knob
        gl.glColor3f(getcolorf(255), getcolorf(255), getcolorf(255));
        drawFilledCircle(gl,.69f,-.52f,.005f,1,361);
            // outline third door
        // outline first door
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glBegin(gl.GL_LINE_LOOP);
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
            // 3rd door window
        gl.glColor3f(getcolorf(242), getcolorf(235), getcolorf(136));
        drawFilledCircle(gl,.705f,-.44f,.02f,1f,361f);
    }
    private void buildWindows(GL2 gl)
    {
        // First build the square windows
            // 1st house only window
        double xleft = -.66;
        double xright = -.61;
        double ybottom = -.35;
        double ytop = -.25;
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(getcolorf(242), getcolorf(235), getcolorf(136));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
            // 1st house window blinds
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glColor3f(getcolorf(250), getcolorf(255), getcolorf(250));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glEnd();
            // outline 1st house window blinds
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glEnd();
            // 1st house window outline
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(xleft, (ybottom+ytop)/2);
        gl.glVertex2d(xright, (ybottom+ytop)/2);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ybottom);
        gl.glEnd();
        
            // 2nd house left window
        xleft = .17;
        xright = .22;
        ybottom = -.45;
        ytop = -.35;
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(getcolorf(242), getcolorf(235), getcolorf(136));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
            // 2nd house left window blinds
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glColor3f(getcolorf(242), getcolorf(213), getcolorf(245));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glEnd();
            // outline 2nd house right window blinds
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glEnd();
            // 2nd house left window outline
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(xleft, (ybottom+ytop)/2);
        gl.glVertex2d(xright, (ybottom+ytop)/2);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ybottom);
        gl.glEnd();
            // 2nd house right window
        xleft = .23;
        xright = .28;
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(getcolorf(242), getcolorf(235), getcolorf(136));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
            // 2nd house left window blinds
        gl.glBegin(GL.GL_TRIANGLES);
       gl.glColor3f(getcolorf(242), getcolorf(213), getcolorf(245));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glEnd();
            // outline 2nd house right window blinds
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glEnd();
            // 2nd house right window outline
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(xleft, (ybottom+ytop)/2);
        gl.glVertex2d(xright, (ybottom+ytop)/2);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ybottom);
        gl.glEnd();
            // 3rd house left window
        xleft = .79;
        xright = .84;
        ytop = -.42;
        ybottom = -.52;
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(getcolorf(242), getcolorf(235), getcolorf(136));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
            // 3rd house left window blinds
        gl.glBegin(GL.GL_TRIANGLES);
       gl.glColor3f(getcolorf(242), getcolorf(213), getcolorf(245));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glEnd();
            // outline 3rd house right window blinds
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glEnd();
            // 3rd house left window outline
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(xleft, (ybottom+ytop)/2);
        gl.glVertex2d(xright, (ybottom+ytop)/2);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ybottom);
        gl.glEnd();
            // 3rd house right window
        xleft = .85;
        xright = .9;
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(getcolorf(242), getcolorf(235), getcolorf(136));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
            // 3rd house left window blinds
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glColor3f(getcolorf(242), getcolorf(213), getcolorf(245));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glEnd();
            // outline 3rd house right window blinds
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glEnd();
            // 3rd house right window outline
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(xleft, (ybottom+ytop)/2);
        gl.glVertex2d(xright, (ybottom+ytop)/2);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ytop);
        gl.glVertex2d(((xleft+xright)/2)-.0009, ybottom);
        gl.glEnd();
        
    }
    private void buildHomes(GL2 gl)
    {
        double xleft = -.84;
        double xright = -.58;
        double xpeak = -.71;
        
        double ybottom = -.64;
        double ytop = -.2;
        double ypeak = 0;
        
        // 1st house Green house fill
        gl.glBegin(GL2.GL_POLYGON);
        gl.glColor3f(getcolorf(83), getcolorf(105), getcolorf(17));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
        
        // 1st house roof fill
        gl.glColor3f(getcolorf(74), getcolorf(60), getcolorf(4));
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xpeak, ypeak);
        gl.glVertex2d(xright, ytop);
        gl.glEnd();
        
        // 1st house triangle outlines
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glVertex2d(xleft + .05, ytop);
        gl.glVertex2d(xleft + .1, ytop);
        gl.glVertex2d(xpeak, ypeak);
        gl.glVertex2d(xleft + .05, ytop);
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glVertex2d(xright - .05, ytop);
        gl.glVertex2d(xright - .1, ytop);
        gl.glVertex2d(xpeak, ypeak);
        gl.glVertex2d(xright - .05, ytop);
        gl.glEnd();
        
        
        // 1st house outline
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xpeak, ypeak);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ytop);
        gl.glEnd();
        
        
        ////////////////////////////////////
        
        xleft = .05;
        xright = .31;
        xpeak = .18;
        
        ybottom = -.58;
        ytop = -.14;
        ypeak = .06;
        
        // 2nd house Green house fill
        gl.glBegin(GL2.GL_POLYGON);
        gl.glColor3f(getcolorf(133), getcolorf(89), getcolorf(20));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xpeak, ypeak);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
        
        // 2nd house outline
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xpeak, ypeak);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
        

        ////////////////////////////////////
        
        xleft = .66;
        xright = .92;
        xpeak = .79;
        
        ybottom = -.64;
        ytop = -.2;
        ypeak = 0;
        
        gl.glColor3f(getcolorf(133), getcolorf(89), getcolorf(20));
        
        // 3nd house orange house fill
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xpeak, ypeak);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
        
        // 3nd house outline
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xpeak, ypeak);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
    }
    
    // drawFilledCircle is code inspired from https://gist.github.com/strife25/803118
    // They explain how to make a circle using open GL triangle fan in C++
    // I have converted it to use in java
    /*
    private void drawFilledCircle(GL2 gl, float x, float y, float radius, float angle, float desired)
    {
        float newX; float newY;
        int triangles = 30;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        float twicePi = 2.0f * (float) Math.PI;
        gl.glVertex2d(x,y);
        for(; angle < desired; angle += .2)
        {
            
            gl.glVertex2d(x+Math.sin(Math.toRadians(angle))*radius, y+Math.cos(Math.toRadians(angle))*radius);
        
        }
        gl.glEnd();
    }*/
    
    private void drawFilledCircle(GL2 gl, float x, float y, float radius, float angle, float desired)
    {
        gl.glBegin(GL.GL_LINE_LOOP);
        
        gl.glVertex2d(x,y);
        int n = 32;
        for(int i = 0; i < n-2; i++)
        {
            angle += (float) ((2 * Math.PI * i) / n);
            x += radius *  Math.cos(angle);
            y -= radius *  Math.sin(angle);
            
            gl.glVertex2d(x, y);
        }
        gl.glEnd();
    }
    
    // Builds a fence post - just a method to sort things out nothing special
    private void buildFence(GL2 gl)
    {
        double xleft = -.99;
        double xright = -.955;
        double ybottom = -.64;
        double ytop = -.35;
        double ypeak = -.31;
        
        for(int i = 0; i < 4; i++)
        {
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(getcolorf(201), getcolorf(193), getcolorf(173));
            gl.glVertex2d(xleft, ybottom);
            gl.glVertex2d(xleft, ytop);
            gl.glVertex2d(xright, ypeak);
            gl.glVertex2d(xright, ybottom);
            gl.glEnd();
            
            gl.glBegin(GL.GL_LINE_LOOP);
            gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
            gl.glVertex2d(xleft, ybottom);
            gl.glVertex2d(xleft, ytop);
            gl.glVertex2d(xright, ypeak);
            gl.glVertex2d(xright, ybottom);
            gl.glEnd();
            xleft += .035;
            xright += .035;
        }
        
        // next set of fences happen here
        
        xleft = -.57;
        xright = -.535;
        
        for(int i = 0; i < 8; i++)
        {
            if(i == 4)
            {
                xleft += .01;
                xright += .01;
            }
            
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(getcolorf(201), getcolorf(193), getcolorf(173));
            gl.glVertex2d(xleft, ybottom);
            gl.glVertex2d(xleft, ytop);
            gl.glVertex2d(xright, ypeak);
            gl.glVertex2d(xright, ybottom);
            gl.glEnd();
            
            gl.glBegin(GL.GL_LINE_LOOP);
            gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
            gl.glVertex2d(xleft, ybottom);
            gl.glVertex2d(xleft, ytop);
            gl.glVertex2d(xright, ypeak);
            gl.glVertex2d(xright, ybottom);
            gl.glEnd();
            xleft += .035;
            xright += .035;
        }
        
        xleft = .955;
        xright = .99;
        ybottom = -.64;
        ytop = -.35;
        ypeak = -.31;
        
        // Back to back fences. First is right side, second is left
            // actual fences on far right
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(getcolorf(201), getcolorf(193), getcolorf(173));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ypeak);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
            
        xleft -= .035;
        xright -= .035;
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ypeak);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
        
            // outline fences on far right
        xleft = .955;
        xright = .99;

        gl.glBegin(GL2.GL_LINES);
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ypeak);
        gl.glVertex2d(xright, ytop);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
        
        xleft -= .035;
        xright -= .035;
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex2d(xleft, ybottom);
        gl.glVertex2d(xleft, ytop);
        gl.glVertex2d(xright, ypeak);
        gl.glVertex2d(xright, ybottom);
        gl.glEnd();
        
            // Now start a loop for the adjacent fences
        xleft = .625;
        xright = .66;
        gl.glColor3f(getcolorf(201), getcolorf(193), getcolorf(173));
        for(int i = 0; i < 4; i++)
        {
            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2d(xleft, ybottom);
            gl.glVertex2d(xleft, ypeak);
            gl.glVertex2d(xright, ytop);
            gl.glVertex2d(xright, ybottom);
            gl.glEnd();
            
            xleft -= .035;
            xright -= .035;
            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2d(xleft, ybottom);
            gl.glVertex2d(xleft, ytop);
            gl.glVertex2d(xright, ypeak);
            gl.glVertex2d(xright, ybottom);
            gl.glEnd();
            xleft -= .035;
            xright -= .035;
        }
        
            // outline the fences again
        xleft = .625;
        xright = .66;
        gl.glColor3f(getcolorf(0), getcolorf(0), getcolorf(0));
        for(int i = 0; i < 4; i++)
        {
            gl.glBegin(GL2.GL_LINES);
            gl.glVertex2d(xleft, ybottom);
            gl.glVertex2d(xleft, ypeak);
            gl.glVertex2d(xright, ytop);
            gl.glVertex2d(xright, ybottom);
            gl.glEnd();
            
            xleft -= .035;
            xright -= .035;
            gl.glBegin(GL2.GL_LINES);
            gl.glVertex2d(xleft, ybottom);
            gl.glVertex2d(xleft, ytop);
            gl.glVertex2d(xright, ypeak);
            gl.glVertex2d(xright, ybottom);
            gl.glEnd();
            xleft -= .035;
            xright -= .035;
        }
    }
    // Converts from int RGB to float RGB
    private float getcolorf(int myInt)
    {
        return (1.0f/255)*myInt;
    }
    

	// This example on this page is long but helpful:
	// http://jogamp.org/jogl-demos/src/demos/j2d/FlyingText.java
	private void	drawSomeText(GLAutoDrawable drawable)
	{
//		renderer.beginRendering(drawable.getWidth(), drawable.getHeight());
//		renderer.setColor(1.0f, 1.0f, 0, 1.0f);
//		renderer.draw("This is a", w/2 + 8, h/2 - 5);
//		renderer.endRendering();
	}
}

//******************************************************************************
