/**
 * SortByPolarAngle  --  PART 2 of the lab  (YOU EDIT THIS FILE)
 *
 * Name: ______________________
 *
 * JOB: put the other points in the order the Graham scan should visit them.
 *      Stand at p and sweep a flashlight counterclockwise, starting from
 *      "straight right" (3 o'clock), up over the top, to "straight left".
 *      Points are sorted in the order the beam hits them.
 *
 *                      C
 *            A         |          E      the beam hits:
 *              \       |        /        B first (angle 0)
 *                \     |      D          then D and E (same angle; D is closer)
 *                  \   |    /            then C (straight up)
 *                    \ |  /              then A (up and to the left)
 *                      p -------- B
 *
 * The angle a point makes with p is called its POLAR ANGLE.
 *
 * You will write two methods:
 *   2a. compareByPolarAngle(p, a, b)        the RULE
 *   2b. insertionSortByPolarAngle(pts, p)   the SORT
 *
 * Check your work:  run SortTester.java  (tests 2a, then 2b)
 */
public class SortByPolarAngle {

    /**
     * 2a. THE RULE  --  which point does the beam hit first, a or b?
     *
     *     return a NEGATIVE number  ->  a comes BEFORE b   (a's angle is smaller)
     *     return a POSITIVE number  ->  a comes AFTER b    (a's angle is bigger)
     *
     * No trigonometry needed! Use the ccw() test from lecture.
     * Walk p -> a -> b. Which way do you turn at a?
     *
     *     LEFT turn  (ccw = +1):  b is further counterclockwise than a,
     *                             so the beam hits a first   -> return -1
     *     RIGHT turn (ccw = -1):  b is less counterclockwise than a,
     *                             so the beam hits b first   -> return +1
     *     STRAIGHT   (ccw =  0):  a and b are on the SAME ray from p.
     *                             The CLOSER one comes first:
     *                               a closer to p  -> return -1
     *                               b closer to p  -> return +1
     *                               same distance  -> return 0
     *
     *          b                   a
     *          |                   |
     *          |  a                |  b             b
     *          | /                 | /             /
     *          |/                  |/             a
     *          p                   p             p
     *     p->a->b LEFT       p->a->b RIGHT     STRAIGHT, a closer
     *     a first: -1        b first: +1       a first: -1
     *
     * Steps:
     *   1. int turn = Point2D.ccw(p, a, b);
     *   2. if turn is +1 return -1
     *   3. if turn is -1 return +1
     *   4. otherwise compare p.distanceSquaredTo(a) with p.distanceSquaredTo(b)
     *
     * Examples with p = (0, 0):
     *   compareByPolarAngle(p, (3,0), (0,4))  ->  -1   (0 degrees before 90)
     *   compareByPolarAngle(p, (-2,2), (1,1)) ->  +1   (135 degrees after 45)
     *   compareByPolarAngle(p, (1,1), (2,2))  ->  -1   (same angle, (1,1) closer)
     *
     * Why does this work?  Because p is the LOWEST point, every other point
     * is above p (or level with it), so all angles are between 0 and 180
     * degrees. In that half-circle, "left turn" always means "bigger angle".
     */
    public static int compareByPolarAngle(Point2D p, Point2D a, Point2D b) {
        // TODO 2a: replace this line with your four steps
        int turn = Point2D.ccw(p,a,b);
        if(turn>0) {
            return -1;
        } else if(turn<0) {
            return 1;
        } else {
            if(p.distanceSquaredTo(a)>p.distanceSquaredTo(b)) {
                return 1;
            } else if(p.distanceSquaredTo(a)<p.distanceSquaredTo(b)) {
                return -1;
            }
        }
        return 0;
    }

    /**
     * 2b. THE SORT  --  insertion sort by polar angle.
     *
     * p is already in pts[0] (Part 1 put it there). LEAVE IT THERE.
     * Sort only pts[1] .. pts[n-1].
     *
     * Idea: like sorting a hand of cards. Take the next card and slide it
     * LEFT past every card that should come after it.
     *
     *   for each i from 2 to n-1:          (pts[1] alone is already "sorted")
     *       j = i
     *       while j > 1  AND  pts[j] comes before pts[j-1]
     *                                       <- compareByPolarAngle(p, pts[j], pts[j-1]) < 0
     *           exch(pts, j, j-1)           (slide it one spot left)
     *           j = j - 1
     *
     *   Why j > 1 and not j > 0?  So we never swap anything into pts[0],
     *   which would move p.
     *
     * Worked example, p = (0,0)   (angles in degrees shown in [ ])
     *
     *   start   p | A(-2,2)[135]  B(3,0)[0]  C(0,4)[90]  D(1,1)[45]  E(2,2)[45]
     *   i = 2   B slides left past A
     *           p | B[0]  A[135]  C[90]  D[45]  E[45]
     *   i = 3   C slides past A, stops at B (0 < 90)
     *           p | B[0]  C[90]  A[135]  D[45]  E[45]
     *   i = 4   D slides past A and C, stops at B
     *           p | B[0]  D[45]  C[90]  A[135]  E[45]
     *   i = 5   E slides past A and C, stops at D (same angle, D is closer)
     *           p | B[0]  D[45]  E[45]  C[90]  A[135]
     *   done!   the beam order: B, D, E, C, A
     */
    public static void insertionSortByPolarAngle(Point2D[] pts, Point2D p) {
        // TODO 2b: write insertion sort here

        for(int i =0; i<pts.length;i++) {
            for(int j = i; j >0; j--) {
                if(compareByPolarAngle(p,pts[j],pts[j-1])<0) {
                    exch(pts,j,j-1);
                }
            }
        }

    }

    /** Swap pts[i] and pts[j]. (Given -- use it!) */
    private static void exch(Point2D[] pts, int i, int j) {
        Point2D temp = pts[i];
        pts[i] = pts[j];
        pts[j] = temp;
    }
}
