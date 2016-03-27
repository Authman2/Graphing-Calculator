import javax.swing.*;
import java.awt.*;

public class Screen extends JPanel {

    CalcPanel cp;
    
    double minx,maxx,miny,maxy;

    double[] xpoints;
    double[] ypoints;
    
    double[] xpoints2;
    double[] ypoints2;
    int n;
    
    
    public Screen(Color backColor, CalcPanel c) {
        setBackground(backColor);
        cp = c;
    }
    
    public void setColor(Color c) {
        setBackground(c);
    }
 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        redrawAxes(g);
        
        if(ypoints != null) {
            g.setColor(Color.black);
            redrawAxes(g);
            drawCurve(g);
        }
        repaint();
    }
    
    public void redrawAxes(Graphics g) {
        if(xpoints != null) {
            int midpoint = 0;
            int midpointy = 0;
            
            if(minx < 0) {
                midpoint = (int)(maxx+minx);
            } else {
                midpoint = (int)(maxx-minx);
            }
            if(miny < 0) {
                midpointy = (int)(maxy+miny);
            } else {
                midpointy = (int)(maxy-miny);
            }
            
            int newX = (int)((getWidth()/(maxx-minx))*(maxx - midpoint));
            int newY = (int)((getHeight()/(maxy-miny))*(midpointy - miny));
                
            //Y-axis
            g.drawLine(newX,0,newX,getHeight());
            //X-axis
            g.drawLine(0,newY,getWidth(),newY);
        } else {
            //Y-axis
            g.drawLine(getWidth()/2, 0, getWidth()/2, getHeight());
            //X-axis
            g.drawLine(0,getHeight()/2, getWidth(), getHeight()/2);
        }
    }
    
    /* Draws the actual graph
       */
    public void drawCurve(Graphics g) {
        //Draw the first polynomial
        if(xpoints != null) {
            for(int i = 0; i < xpoints.length - 1; i++) {
                int firstX = (int)((getWidth()/(maxx-minx))*(xpoints[i] - minx));
                int firstY = (int)((getHeight()/(maxy-miny))*(maxy - ypoints[i]));
                
                g.setColor(Color.black);
                g.drawLine(firstX,firstY,firstX,firstY);
            }
        }
        //Draw the second polynomial
        if(xpoints2 != null) {
            for(int i = 0; i < xpoints2.length - 1; i++) {
                int firstX = (int)((getWidth()/(maxx-minx))*(xpoints2[i] - minx));
                int firstY = (int)((getHeight()/(maxy-miny))*(maxy - ypoints2[i]));
                
                g.setColor(Color.gray);
                g.drawLine(firstX,firstY,firstX,firstY);
            }
        }
    }
   
    
   /*This is responsible for finding all the x-values and y-values that need to be plotted, then adds them 
    * to the respective arrays.
      */
    public void findPointsToPlot(double mina, double maxa, double minya, double maxya) {
        /*
         * Finds the minimum and maximum values for the x- and y-axes. Also gets the scale factor.
         */
        minx = mina;
        maxx = maxa;
        miny = minya;
        maxy = maxya;
        
        int num = 1000;
        
        n = 0;  //Number of terms in each array
        
        //X and Y coordinates for the first polynomial
        xpoints = new double[num*(int)((maxx-minx)+1)];
        ypoints = new double[num*(int)((maxx-minx)+1)];
        
        //X and Y coordinates for the second polynomial
        xpoints2 = new double[num*(int)((maxx-minx)+1)];
        ypoints2 = new double[num*(int)((maxx-minx)+1)];
        
        for(double i = minx; i <= maxx; i += 1.0/num) { 
            // 'i' is the x value and 'cp.p.evaluate(i)' is the y value
            xpoints[n] = i;
            ypoints[n] = cp.p.evaluate(i);
            
            xpoints2[n] = i;
            ypoints2[n] = cp.p2.evaluate(i);
            
            n++;
        }
        
        intersectionsX();
    }
    
    public double fOf(double x) {
        return cp.p.evaluate(x);
    }
    public double gOf(double x) {
        return cp.p2.evaluate(x);
    }
    
    public void intersectionsX() {
        if(!cp.p.sToParse.equals("") && !cp.p2.sToParse.equals("")) {
            Polynomial poly = cp.p.subtract(cp.p2);
            //Array of roots from the new polynomial when you subtract the first two.
            double[] polyroots = poly.roots();
            
            
            //Digit to round to
            int digit = 3;
            //String for displaying the intersections.
            String istring = "";
            
            //Loop through that array
            for(int i = 0; i < polyroots.length; i++) {
                double l = polyroots[i];
                double o = fOf(l);
                
                l = ((double)((int)(poly.roots()[i]*Math.pow(10, digit) + 0.5)))/Math.pow(10, digit);
                o = ((double)((int)(fOf(l)*Math.pow(10, digit) + 0.5)))/Math.pow(10, digit);
                
                istring += "(" + l + ", " + o + ")";
            }
            
            cp.intersectionsTF.setText(istring);
        }
    }
    
} //End of class
