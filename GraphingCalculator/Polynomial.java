
public class Polynomial
{
    /** The polynomial as a string. */
    String sToParse;
    
    /** An array of  strings that will hold each term. And 'n' is the size of the array. */
    public String[] terms; //Each term, including x's and ^'s.
    int n = 0;      //Size of the array ^
    
    /** Array of the coefficients of the polynomial. */
    double[] coeffs;
    /** Array of the degrees of the polynomial. */
    int[] degrees;
    
    /**The min and maax X values to loop over when finding the roots.*/
    public double min = -10,max = 10;
    
    
    //Parse the terms in the string to doubles and add the coefficients to the array coeffs.
    /** Constructor. Takes the polynomial as a string and adds 
     * each individual term to the array. */
    public Polynomial(String s) {
        setLengthOfTerms(s);
        sToParse = s;
        
        int j = 0;
        int k = 0;
        
        for(int i = 0; i < s.length() - 1; i++) {
            if(s.substring(i,i+1).equals("+") || s.substring(i,i+1).equals("-")) {
                terms[j] = s.substring(k,i);
                j++;
                k = i;
            }
        }
        terms[j] = s.substring(k);
        
        addToCoeffs();
    }
    
    /** Loops through all the terms to find the different degrees. 
     * Also checks for the highest degree. 
       * This is just used for subtracting polynomials.
       */
    private void addToDegrees() {
        int highestDegree = 0;
        
        for(int i = 0; i < terms.length; i++) {
            String term = terms[i];
            int deg = 0;
            
            if(!term.equals("")) {
                if(term.indexOf("x") > -1) {
                    if(term.indexOf("^") > -1) {
                        deg = Integer.parseInt(term.substring(term.indexOf("^")+1));
                    } else {
                        deg = 1;
                    }
                } else {
                    deg = 0;
                }
                
                if(deg > highestDegree) {
                    highestDegree = deg;
                }
            }
        }
        this.degrees = new int[highestDegree+1];
        
    } //End of method
    
    /** Adds all of the coefficients and degrees to 
     * their respective arrays. */
    private void addToCoeffs() {
        int highestDegree = 0;
        for(int i = 0; i < terms.length; i++) {
            String term = terms[i];
            int deg = 0;
            
            if(!term.equals("")) {
                if(term.indexOf("x") > -1) {
                    
                    if(term.indexOf("^") > -1) {
                        deg = Integer.parseInt(term.substring(term.indexOf("^")+1));
                    } else {
                        deg = 1;
                    }
                } else {
                    deg = 0;
                }
                
                if(deg > highestDegree) {
                    highestDegree = deg;
                }
            }
        }
        //Set the length to the highest degree
        coeffs = new double[highestDegree+1];
        degrees = new int[highestDegree+1];
        
        
        for(int i = 0; i < terms.length; i++) {
            String term = terms[i];
            double cof = 0;
            int deg = 0;
            
            if(!term.equals("")) {
                if(term.indexOf("x") > -1) {
                    
                    if(term.substring(0,1).equals("x")) {
                        cof = 1;
                    } else if(term.substring(0,2).equals("-x")) {
                        cof = -1;
                    } else {
                        if(term.indexOf("-") > -1) {
                            cof = Double.parseDouble(term.substring(0,term.indexOf("x")));
                        } else {
                            cof = Double.parseDouble(term.substring(0,term.indexOf("x")));
                        }
                    }
                    
                    if(term.indexOf("^") > -1) {
                        deg = Integer.parseInt(term.substring(term.indexOf("^")+1));
                    } else {
                        deg = 1;
                    }
                } else {
                    cof = Double.parseDouble(term.substring(0));
                    deg = 0;
                }
            }
            
            //Add each value before the end of the for-loop
            coeffs[deg] = cof;
            degrees[deg] = deg;
            
        } //End of for-loop
    }
    
    /** Returns the polynomial as a string. */
    public String toString() {
        return sToParse;
    }
    
    /** Sets the length of the array of terms. */
    private void setLengthOfTerms(String s) {
        for(int i = 0; i < s.length(); i++) {            
            if(s.substring(i,i+1).equals("+") || s.substring(i,i+1).equals("-")) {
                n++;
            }
        }
        n++;
        terms = new String[n];
    }
    
    /** Evaluates the polynomial at 'x', like an 'f(x)' equation, 
     * and returns the 'y' value. */
    public double evaluate(double x) {
        double ans = 0;
        
        for(int i = 0; i < coeffs.length; i++) {
            double multiplier = 0;
            if(degrees[i] != 0) {
                multiplier = Math.pow(x,degrees[i]);
                ans += coeffs[i]*multiplier;
            }
            if(degrees[i] == 0){
                ans += coeffs[i];
            }
        }
        
        return ans;
    }
    
    /**
     * Used for subtracting another polynomial from this one. 
     * Returns a new polynomial that is the difference between the first two.
       */
    public Polynomial subtract(Polynomial p) {
        Polynomial ply = new Polynomial("");
        
        //The size of the coefficient and degree arrays depend on whichever polynomial had the higher degree.
        if(this.coeffs.length > p.coeffs.length) {
            ply.coeffs = new double[this.coeffs.length];
            ply.degrees = new int[this.degrees.length];
        } else {
            ply.coeffs = new double[p.coeffs.length];
            ply.degrees = new int[p.degrees.length];
        }
        
        /*Loop through the coefficients.
         * If the step you are on is bigger than the number of terms in the first polynomial's
         * coefficient array, then just add the negative of the other polynomial's. If the other
         * polynomial's coefficient array length is not big enough, then just add the first polynomial's
         * coefficient. If there is a value for both, then just subtract them.
         */
        for(int i = 0; i < ply.coeffs.length; i++) {
            if(this.coeffs.length < i+1) {
                ply.coeffs[i] = -p.coeffs[i];
            } else if(p.coeffs.length < i+1) {
                ply.coeffs[i] = this.coeffs[i];
            } else {
                ply.coeffs[i] = this.coeffs[i] - p.coeffs[i];
            }
        }
        
        /*Loop through the polynomial's degree array and if the coefficient array at 'i' is not a zero 
         * then add it to the array. */
        for(int i = 0; i < ply.degrees.length; i++) {
            if(ply.coeffs[i] != 0) {
                ply.degrees[i] = i;
            }
        }
        
        
        ply.sToParse = this.toString() + "-" + p.toString();
        
        
        return ply;
    }
    
    /** Returns the highest degree of this polynomial object. */
    private int HighestDegree() {
        int hd = 0;
        for(int i = 0; i < degrees.length; i++) {
            if(degrees[i] > hd) {
                hd = degrees[i];
            }
        }
        
        return hd;
    }
    
    /** Returns the lower bound of a particular polynomial's roots. */
    public double getLowerBound() {
        return -getUpperBound();
    }
    /** Returns the upper bound of a particular polynomial's roots. */
    public double getUpperBound() {
        //Coefficient of largest magnitude
        double aMax = 0;
        //Highest degree in the polynomial
        int highestDeg = 0;
        //Coefficient with the highest degree
        double aN = 0;
        
        //Loop through the coefficients to find the one of greatest magnitude
        for(int i = 0; i < coeffs.length; i++) {
            if(Math.abs(coeffs[i]) > aMax) {
                aMax = coeffs[i];
            }
        }
        
        //Loop through to find the coefficient of largest degree
        for(int i = 0; i < degrees.length; i++) {
            if(degrees[i] > highestDeg) {
                highestDeg = degrees[i];
            }
        }
        aN = coeffs[highestDeg];
        
        
        double bound = 0;        
        bound = 1 + Math.abs(aMax/aN);
        
        
        return bound;
    }
    
    /**
     * Returns all the real roots of the polynomial. Returns an array of doubles.
       */
    public double[] roots() {
        //Create a really small step to use for moving along the x-axis.
        double step = Math.abs((getUpperBound()-getLowerBound())/10000);
        //Array of roots
        double[] rs = new double[this.HighestDegree()];
        int n = 0;
        
        /* Loop through to see if the sign changes between two points when they are evaluated. */
        for(double i = min; i < max; i += step) {
            if((evaluate(i) < 0 && evaluate(i+step) > 0) || (evaluate(i) > 0 && evaluate(i+step) < 0)) {
                double root = (i + (i+step))/2;
                rs[n] = root;
                n++;
            }
        }
        
        //Create an array of all of the real roots.
        double[] realRoots = new double[n];
        
        //Loop through 'n', since that is the number of real roots, and fill the array with those.
        for(int i = 0; i < n; i++) {
            realRoots[i] = rs[i];
        }
        
        return realRoots;
    }
    
    
    
} //End of class
