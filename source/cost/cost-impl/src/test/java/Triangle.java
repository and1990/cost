/**
 * 注释：三角形对象
 * 时间：2014年05月19日 下午5:18
 * 作者：刘腾飞[liutengfei]
 */
public class Triangle {
    private int a;
    private int b;
    private int c;

    public Triangle(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * 判断是否是三角形
     *
     * @return
     */
    public boolean isLegal(Triangle triangle) {
        int a = triangle.a;
        int b = triangle.b;
        int c = triangle.c;
        if (a + b <= c || a + c <= b || b + c <= a) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否是等腰三角形
     *
     * @param triangle
     * @return
     */
    public boolean isIsoscelesTriangle(Triangle triangle) {
        int a = triangle.a;
        int b = triangle.b;
        int c = triangle.c;
        if (a == b || a == c || b == c) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是等边三角戏
     *
     * @param triangle
     * @return
     */
    public boolean isEqualSides(Triangle triangle) {
        int a = triangle.a;
        int b = triangle.b;
        int c = triangle.c;
        if (a == b && b == c) {
            return true;
        }
        return false;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }
}
