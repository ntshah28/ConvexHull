/**
 * SortByY  --  PART 1 of the lab  (YOU EDIT THIS FILE)
 *
 * Name: ______________________
 *
 * JOB: find the START point p of the convex hull.
 *      p is the LOWEST point (smallest y). If two points tie for lowest,
 *      p is the one further LEFT (smaller x).
 *
 * Why the lowest point?  Nothing is below it, so the fence (rubber band)
 * has to touch it. The lowest point is ALWAYS on the convex hull.
 *
 * You will write two methods:
 *   1a. compareByY(a, b)     the RULE: which of two points comes first?
 *   1b. selectionSortByY(pts) the SORT: use the rule to order every point
 *
 * Check your work:  run SortTester.java  (tests 1a, then 1b)
 */
public class SortByY {

    /**
     * 1a. THE RULE  --  compare two points by height.
     *
     * Java's compare convention (the same one compareTo() uses):
     *     return a NEGATIVE number  ->  a comes BEFORE b   (a is lower)
     *     return a POSITIVE number  ->  a comes AFTER b    (a is higher)
     *     return 0                  ->  a and b are the same point
     *
     * Steps:
     *   1. If a's y is smaller than b's y, a is lower      -> return -1
     *   2. If a's y is bigger than b's y,  a is higher     -> return +1
     *   3. Otherwise the y's are TIED. Break the tie with x:
     *        smaller x comes first -> return -1
     *        bigger x comes after  -> return +1
     *   4. Same x AND same y: it's the same point         -> return 0
     *
     * Examples:
     *   compareByY( (3, 1), (2, 5) )  ->  -1    1 < 5, so (3,1) is lower
     *   compareByY( (2, 5), (3, 1) )  ->  +1    5 > 1
     *   compareByY( (4, 1), (2, 1) )  ->  +1    tie on y = 1, and 4 > 2
     *   compareByY( (2, 1), (2, 1) )  ->   0    same point
     *
     * Use a.x(), a.y(), b.x(), b.y() to get the coordinates.
     */
    public static int compareByY(Point2D a, Point2D b) {
        // TODO 1a: replace this line with your four steps
        if(a.y()<b.y()) {
            return -1;
        } else if (a.y()>b.y()) {
            return 1;
        } else if(a.x()>b.x()) {
            return 1;
        } else if(a.x()<b.x()) {
            return -1;
        }
        return 0;


    }

    /**
     * 1b. THE SORT  --  selection sort, lowest point first.
     *
     * Idea: SELECT the smallest remaining point and swap it into place.
     *
     *   for each position i, from 0 to n-1:
     *       min = i                         (guess: pts[i] is the smallest)
     *       for each j from i+1 to n-1:      (look at everything to the right)
     *           if pts[j] comes before pts[min]     <- use compareByY(...) < 0
     *               min = j                 (found a new smallest)
     *       exch(pts, i, min)               (swap the smallest into position i)
     *
     * Worked example (A..E are labels so you can follow the points):
     *
     *   start      A(3,5)  B(1,2)  C(4,1)  D(2,1)  E(5,4)
     *   i = 0      lowest of all is D (ties C on y=1, but D has smaller x)
     *              swap pts[0] and pts[3]
     *              D(2,1)| B(1,2)  C(4,1)  A(3,5)  E(5,4)
     *   i = 1      lowest of B C A E is C -> swap pts[1] and pts[2]
     *              D(2,1)  C(4,1)| B(1,2)  A(3,5)  E(5,4)
     *   i = 2      lowest of B A E is B, already in place
     *              D(2,1)  C(4,1)  B(1,2)| A(3,5)  E(5,4)
     *   i = 3      lowest of A E is E -> swap pts[3] and pts[4]
     *              D(2,1)  C(4,1)  B(1,2)  E(5,4)| A(3,5)
     *   done!      pts[0] = D(2,1) is p, the start point
     *
     *   ( | marks the boundary: everything left of it is in its final spot )
     */
    public static void selectionSortByY(Point2D[] pts) {
        // TODO 1b: write selection sort here
        for(int i = 0; i < pts.length;i++) {
            int m = i;
            for(int  j = i+1; j<pts.length;j++) {
                if(compareByY(pts[m],pts[j])>0) {
                    m = j;
                }
            }
            exch(pts,i,m);
        }



    }

    /** Swap pts[i] and pts[j]. (Given -- use it!) */
    private static void exch(Point2D[] pts, int i, int j) {
        Point2D temp = pts[i];
        pts[i] = pts[j];
        pts[j] = temp;
    }
}
